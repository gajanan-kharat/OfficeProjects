package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;


/**
 * The Class GenomveTVVRuleJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "GenomeTVVRule")
public class GenomveTVVRuleJpaRepository extends BaseJpaRepository<GenomeTVVRule, Long> implements GenomeTVVRuleRepository {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(GenomveTVVRuleJpaRepository.class);

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#saveGenomeTVVRule(com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule)
	 */
	@Override
	public void saveGenomeTVVRule(GenomeTVVRule ruleObject) {

		// Checking Code Value is present in LCDV Code value Table
		List<GenomeLCDVCodeValue> existingCodeValues = ifLCDVCodeValueExist(entityManager, ruleObject);

		if (existingCodeValues != null && !existingCodeValues.isEmpty())
			ruleObject.setGenomeLcdvCodeValue(existingCodeValues.get(0));

		GenomeTVVRule obj = super.save(ruleObject);

		logger.info("Object Saved Id : {} ", obj.getEntityId());
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#saveGenomeTVVRule(java.util.List)
	 */
	@Override
	public void saveGenomeTVVRule(List<GenomeTVVRule> ruleList) {

		for (GenomeTVVRule tvvRule : ruleList) {

			super.save(tvvRule);
		}
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#saveOrUpdateGenomeTVVRule(java.util.List)
	 */
	@Override
	public void saveOrUpdateGenomeTVVRule(List<GenomeTVVRule> tvvRuleList) {

		entityManager.setFlushMode(FlushModeType.COMMIT);

		for (GenomeTVVRule tvvRule : tvvRuleList) {

			GenomeTVVRule ruleObj = ifRuleExists(entityManager, tvvRule);

			if (null == ruleObj) {
				saveGenomeTVVRule(tvvRule);

			} else {

				ruleObj.setKmat(tvvRule.getKmat());
				ruleObj.setLcdvCodeName(tvvRule.getLcdvCodeName());
				ruleObj.setLcdvCodeValue(tvvRule.getLcdvCodeValue());
				ruleObj.setRuleId(tvvRule.getRuleId());

				Long i = entityManager.merge(ruleObj).getEntityId();

				logger.info("Updating Genome TVV Rule Object, Id : {} ", i);
			}

		}

	}

	/**
	 * If rule exists.
	 *
	 * @param em the em
	 * @param tvvRule the tvv rule
	 * @return the genome tvv rule
	 */
	public GenomeTVVRule ifRuleExists(EntityManager em, GenomeTVVRule tvvRule) {

		final int NOT_EXISTS = 0;
		final int EXISTS = 1;

		TypedQuery<GenomeTVVRule> queryResult = em.createQuery("SELECT tvvRule FROM GenomeTVVRule tvvRule WHERE tvvRule.kmat = ?1 AND tvvRule.lcdvCodeName = ?2 AND tvvRule.ruleId = ?3 AND tvvRule.lcdvCodeValue = ?4 ", GenomeTVVRule.class);

		queryResult.setParameter(1, tvvRule.getKmat());
		queryResult.setParameter(2, tvvRule.getLcdvCodeName());
		queryResult.setParameter(3, tvvRule.getRuleId());
		queryResult.setParameter(4, tvvRule.getLcdvCodeValue());

		List<GenomeTVVRule> results = queryResult.getResultList();

		switch (results.size()) {

			case NOT_EXISTS:
				return null;

			case EXISTS:
				return results.get(0);

			default:
				throw new NonUniqueResultException("Unexpected query results, " + results.size());
		}

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#ifLCDVCodeValueExist(javax.persistence.EntityManager, com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule)
	 */
	@Override
	public List<GenomeLCDVCodeValue> ifLCDVCodeValueExist(EntityManager em, GenomeTVVRule tvvRule) {

		String query = "SELECT new " + GenomeLCDVCodeValue.class.getName() + "(v1.frLabel, v1.z2Label, v1.lcdvCodeValue, v1.entityId, v1.genomeLcdvCode) " + "FROM GenomeLCDVDictionary d1 left join d1.genomeLcdvCodeList c1 left join c1.genomeLcdvCodeValueList v1 " + "WHERE d1.kmat = ?1 AND c1.codeName = ?2 AND v1.lcdvCodeValue = ?3";

		TypedQuery<GenomeLCDVCodeValue> queryResult = this.entityManager.createQuery(query, GenomeLCDVCodeValue.class);

		queryResult.setParameter(1, tvvRule.getKmat());
		queryResult.setParameter(2, tvvRule.getLcdvCodeName());
		queryResult.setParameter(3, tvvRule.getLcdvCodeValue());

		List<GenomeLCDVCodeValue> resultSet = queryResult.getResultList();

		if (resultSet.isEmpty())
			return null;
		else
			return resultSet;

	}

	/**
	 * If lcdv code exist.
	 *
	 * @param em the em
	 * @param tvvRule the tvv rule
	 * @return the list
	 */
	public List<GenomeLCDVCode> ifLCDVCodeExist(EntityManager em, GenomeTVVRule tvvRule) {

		String query = "SELECT lcdvCode FROM GenomeLCDVCode lcdvCode WHERE lcdvCode.codeName = ?1 ";

		TypedQuery<GenomeLCDVCode> queryResult = em.createQuery(query, GenomeLCDVCode.class);

		queryResult.setParameter(1, tvvRule.getLcdvCodeName());

		List<GenomeLCDVCode> resultSet = queryResult.getResultList();

		if (resultSet.isEmpty())
			return null;
		else
			return resultSet;

	}

	/**
	 * If dictionary exist.
	 *
	 * @param em the em
	 * @param tvvRule the tvv rule
	 * @return the list
	 */
	public List<GenomeLCDVDictionary> ifDictionaryExist(EntityManager em, GenomeTVVRule tvvRule) {

		String query = "SELECT dictionary FROM GenomeLCDVDictionary dictionary WHERE dictionary.kmat = ?1 ";

		TypedQuery<GenomeLCDVDictionary> queryResult = em.createQuery(query, GenomeLCDVDictionary.class);

		queryResult.setParameter(1, tvvRule.getKmat());

		List<GenomeLCDVDictionary> resultSet = queryResult.getResultList();

		if (resultSet.isEmpty())
			return null;
		else
			return resultSet;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return entityManager.createQuery("DELETE FROM GenomeTVVRule").executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#count()
	 */
	@Override
	public long count() {
		Query query = entityManager.createQuery("select count(*) from GenomeTVVRule");
		Long count = (Long) query.getSingleResult();
		return count;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository#persistGenomeTVVRule(com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule)
	 */
	@Override
	public void persistGenomeTVVRule(GenomeTVVRule tvvRule) {
		logger.info("Pesist {}", GenomeTVVRule.class);
		super.persist(tvvRule);
	}

}

package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUsesRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class RuleOfUsesJpaRepository.
 */
public class RuleOfUsesJpaRepository extends BaseJpaRepository<RuleOfUses, Long> implements RuleOfUsesRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(RuleOfUsesJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.ruleofuses.RuleOfUsesRepository#saveRule(org.seedstack.pv2.domain.ruleofuses.RuleOfUses)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public RuleOfUses saveRule(RuleOfUses rule) {
		return super.save(rule);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.ruleofuses.RuleOfUsesRepository#persistRule(org.seedstack.pv2.domain.ruleofuses.RuleOfUses)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistRule(RuleOfUses rule) {
		super.persist(rule);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.ruleofuses.RuleOfUsesRepository#findAllRuleByFamilyId(org.seedstack.pv2.domain.pictofamily.PictoFamily, java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public RuleOfUses findAllRuleByFamilyId(PictoFamily familyID, String name) {
		RuleOfUses rulesEntity = null;

		String p_RuleQuery = "select distinct r from RuleOfUses  r " + " where r.familyId = :rule_id and r.name=:name";
		try {
			TypedQuery<RuleOfUses> p_TypedQuery = m_entityManager.createQuery(p_RuleQuery, RuleOfUses.class);
			p_TypedQuery.setParameter("rule_id", familyID);
			p_TypedQuery.setParameter("name", name);
			List<RuleOfUses> p_ListOfRules = p_TypedQuery.getResultList();

			if (p_ListOfRules != null && p_ListOfRules.size() >= 0) {
				for (RuleOfUses l_Rule : p_ListOfRules) {
					rulesEntity = l_Rule;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the Rules ", e);
		}
		return rulesEntity;
	}

}

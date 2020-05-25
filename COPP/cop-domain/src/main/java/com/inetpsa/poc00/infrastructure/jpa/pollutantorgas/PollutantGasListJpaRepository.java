/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.pollutantorgas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;

/**
 * The Class PollutantGasListJpaRepository.
 */
public class PollutantGasListJpaRepository extends BaseJpaRepository<PollutantGasLimitList, Long> implements PollutantGasLimitListRepository {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PollutantGasListJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#savePollutantGasLimitList(com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList)
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#savePollutantGasLimitList(com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasLimitList savePollutantGasLimitList(PollutantGasLimitList object) {

		try {
			if (object.getEntityId() != null && object.getEntityId() != 0)
				return super.save(object);

			super.persist(object);
			entityManager.flush();
		} catch (Exception e) {
			LOGGER.error(" **** End: Exception occured while running querry", e);
		}
		return this.load(object.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#persistPollutantGasLimitList(com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList)
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#persistPollutantGasLimitList(com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList)
	 */
	@Override
	public void persistPollutantGasLimitList(PollutantGasLimitList object) {
		super.persist(object);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#deletePollutantGasList(long)
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#deletePollutantGasList(long)
	 */
	@Override
	public void deletePollutantGasList(long entityId) {
		super.delete(entityId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#deleteAll()
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#count()
	 */
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param emsId the ems id
	 * @return the latest emission standard dep lists
	 */
	@Override
	public List<PollutantGasLimitList> getLatestEmissionStandardDepLists(Long emsId) {
		List<PollutantGasLimitList> resultList = new ArrayList<>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from PollutantGasLimitList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<PollutantGasLimitList> query = entityManager.createQuery("select t  from PollutantGasLimitList t where t.entityId =" + id, PollutantGasLimitList.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository#getEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<PollutantGasLimitList> getEmissionStandardDepLists(Long esEntityId) {
		TypedQuery<PollutantGasLimitList> query = entityManager.createQuery("select t from PollutantGasLimitList t where t.emissionStandard.entityId= " + esEntityId, PollutantGasLimitList.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getMaxVersionForLabel(java.lang.String)
	 */
	@Override
	public Double getMaxVersionForLabel(String label) {

		Query query = entityManager.createNativeQuery("SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM COPQTPLL T WHERE T.LABEL = ?");

		query.setParameter(1, label);
		if (query.getSingleResult() != null) {
			return ((BigDecimal) query.getSingleResult()).doubleValue();
		}
		return null;
	}
}

/*
 * Creation : Nov 22, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;

/**
 * The PollutantGasResultSetJpaRepository class.
 *
 * @author mehaj
 */
public class PollutantGasResultSetJpaRepository extends BaseJpaRepository<PollutantGasResultSet, Long> implements PollutantGasResultSetRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.infrastructure.jpa.PollutantGasResultSetJpaRepository#getPollutantGasResultSets(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PollutantGasResultSet> getPollutantGasResultSets(Long vehicleFileId) {
		TypedQuery<PollutantGasResultSet> query = entityManager.createQuery("select t from PollutantGasResultSet t where t.vehicleFile.entityId =" + vehicleFileId, PollutantGasResultSet.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository#savePollutantGasResultSet(com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet)
	 */

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasResultSet savePollutantGasResultSet(PollutantGasResultSet pollutantGasResultSetObj) {

		return super.save(pollutantGasResultSetObj);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository#getMaxOrder(com.inetpsa.poc00.domain.statisticalsample.StatisticalSample)
	 */
	@Override
	public Integer getMaxOrder(StatisticalSample currentSample) {
		TypedQuery<Integer> query = entityManager.createQuery("select Max(statisticalOrder) from PollutantGasResultSet resultSet where resultSet.statisticalSample.entityId =" + currentSample.getEntityId(), Integer.class);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository#getLastIResultSetsForSample(com.inetpsa.poc00.domain.statisticalsample.StatisticalSample,
	 *      int)
	 */
	@Override
	public List<PollutantGasResultSet> getLastIResultSetsForSample(StatisticalSample currentSample, int i) {
		TypedQuery<PollutantGasResultSet> query = entityManager.createQuery("select resultSet from PollutantGasResultSet resultSet where resultSet.statisticalSample.entityId =" + currentSample.getEntityId() + "and  resultSet.keptInStatSample is TRUE and resultSet.inQuarantine is FALSE ORDER BY resultSet.statisticalOrder DESC ", PollutantGasResultSet.class);
		query.setMaxResults(i);
		return query.getResultList();

	}

}

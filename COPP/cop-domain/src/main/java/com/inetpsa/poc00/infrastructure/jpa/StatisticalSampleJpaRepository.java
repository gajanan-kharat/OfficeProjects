/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class StatisticalSampleJpaRepository.
 */
public class StatisticalSampleJpaRepository extends BaseJpaRepository<StatisticalSample, Long> implements StatisticalSampleRepository {

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#saveStatisticalSample(com.inetpsa.poc00.domain.statisticalsample.StatisticalSample)
	 */
	@Override
	public StatisticalSample saveStatisticalSample(StatisticalSample newSample) {

		if (newSample.getEntityId() != null && newSample.getEntityId() != 0)
			return super.save(newSample);

		super.persist(newSample);
		entityManager.flush();

		return super.load(newSample.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getTechnicalCase(java.lang.Long)
	 */
	@Override
	public TechnicalCase getTechnicalCase(Long statisticalSampleEntityId) {

		TypedQuery<TechnicalCase> query = entityManager.createQuery("SELECT technicalCase FROM StatisticalSample sample join TechnicalCase technicalCase where sample.entityId =" + statisticalSampleEntityId + "and sample.technicalCase.entityId = technicalCase.entityId", TechnicalCase.class);

		if (!query.getResultList().isEmpty()) {
			return query.getResultList().get(0);
		}
		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getTVV(java.lang.Long)
	 */
	@Override
	public TVV getTVV(Long statisticalSampleEntityId) {

		TypedQuery<TVV> query = entityManager.createQuery("SELECT sample.technicalCase.tvv FROM StatisticalSample sample where sample.entityId =" + statisticalSampleEntityId, TVV.class);

		if (!query.getResultList().isEmpty()) {
			return query.getResultList().get(0);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getSampleForVehicleFile(long, long,
	 *      long)
	 */
	@Override
	public List<StatisticalSample> getSampleForVehicleFile(long technicalCaseId, long factoryId, long typeOftestId) {

		TypedQuery<StatisticalSample> query = entityManager.createQuery("SELECT sample FROM StatisticalSample sample where sample.technicalCase.entityId =" + technicalCaseId + " AND sample.carFactory.entityId= " + factoryId + " AND sample.testType.entityId= " + typeOftestId + " AND sample.closed is FALSE", StatisticalSample.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getResultAverage(com.inetpsa.poc00.domain.statisticalsample.StatisticalSample,
	 *      com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit)
	 */
	@Override

	public double getResultAverage(StatisticalSample currentSample, TvvValuedPollutantGasLimit tvvValuedPollutantGasLimit) {

		TypedQuery<Double> query = entityManager.createQuery("SELECT avg(result.value) FROM StatisticalSample sample join sample.pollutantGasResultSet resultSet join resultSet.pollutantGasTestResult result where sample.entityId =" + currentSample.getEntityId() + " AND result.tvvValuedPollGasLimit.entityId= " + tvvValuedPollutantGasLimit.getEntityId(), Double.class);

		return query.getSingleResult();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPollutantGasResultSet(java.lang.Long)
	 */
	@Override

	public List<PollutantGasResultSet> getPollutantGasResultSet(Long statisticalSampleEntityId) {

		TypedQuery<PollutantGasResultSet> query = entityManager.createQuery("SELECT pgrs FROM PollutantGasResultSet pgrs where pgrs.keptInStatSample = TRUE and  pgrs.inQuarantine = FALSE and pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, PollutantGasResultSet.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getVehicleChassisNumber(java.lang.Long)
	 */
	@Override
	public List<String> getVehicleChassisNumber(Long technicalCaseEntityId) {

		TypedQuery<String> query = entityManager.createQuery("SELECT vehicle.chasisNumber FROM Vehicle vehicle where vehicle.technicalCase.entityId =" + technicalCaseEntityId, String.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPollutantGasTestResultStatisticalResult(java.lang.Long)
	 */
	@Override

	public List<Long> getPollutantGasTestResultStatisticalResult(Long statisticalSampleEntityId) {

		TypedQuery<Long> query = entityManager.createQuery("SELECT pgtr.statisticalResult FROM PollutantGasTestResult  pgtr  JOIN   pgtr.pollutantGasResultSet pgrs where pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, Long.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPollutantGasTestResultStatisticalDecision(java.lang.Long)
	 */
	@Override
	public List<String> getPollutantGasTestResultStatisticalDecision(Long statisticalSampleEntityId) {

		TypedQuery<String> query = entityManager.createQuery("SELECT pgtr.statisticalDecision FROM PollutantGasTestResult pgtr JOIN   pgtr.pollutantGasResultSet pgrs where pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, String.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getVehicleFileResultStatisticalDecision(java.lang.Long)
	 */
	@Override
	public List<String> getVehicleFileResultStatisticalDecision(Long technicalCaseEntityId) {

		TypedQuery<String> query = entityManager.createQuery("SELECT vf.statisticalDecision FROM  VehicleFile vf JOIN 	 vf.vehicle vehicle  where  vehicle.technicalCase.entityId =" + technicalCaseEntityId, String.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPollutantGasTestForVehicles(java.lang.Long)
	 */
	@Override
	public List<PollutantGasTestResult> getPollutantGasTestForVehicles(Long vehicleEntityID) {

		TypedQuery<PollutantGasTestResult> query = entityManager.createQuery("SELECT pgtr FROM Vehicle v JOIN  v.vehicleFile vf JOIN  vf.pgResultSet prs JOIN  prs.pollutantGasTestResult pgtr where v.entityId = " + vehicleEntityID, PollutantGasTestResult.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPollutantGasLimitForVehicleFile(java.lang.Long)
	 */
	@Override
	public TvvValuedPollutantGasLimit getPollutantGasLimitForVehicleFile(Long vehicleFileEntityID) {

		TypedQuery<TvvValuedPollutantGasLimit> query = entityManager.createQuery("SELECT pgtr.tvvValuedPollGasLimit FROM PollutantGasTestResult pgtr JOIN	  pgtr.pollutantGasResultSet pgrs where pgrs.vehicleFile.entityId =" + vehicleFileEntityID, TvvValuedPollutantGasLimit.class);
		if (query != null) {
			return query.getResultList().get(0);
		}
		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getVehicle(java.lang.Long)
	 */
	@Override

	public List<Vehicle> getVehicle(Long technicalCaseEntityId) {

		TypedQuery<Vehicle> query = entityManager.createQuery("SELECT vehicle FROM Vehicle vehicle where vehicle.technicalCase.entityId =" + technicalCaseEntityId, Vehicle.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getStatisticalSampleForStatCurves(java.lang.Long)
	 */
	@Override
	public StatisticalSample getStatisticalSampleForStatCurves(Long statisticalSampleEntityId) {

		TypedQuery<StatisticalSample> query = entityManager.createQuery("SELECT pgrs.statisticalSample FROM PollutantGasResultSet pgrs  where pgrs.keptInStatSample = TRUE	  and  pgrs.inQuarantine = FALSE and  pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, StatisticalSample.class);
		List<StatisticalSample> sampleList = query.getResultList();

		if (!sampleList.isEmpty()) {
			return sampleList.get(0);
		}
		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getVehicleFile(java.lang.Long)
	 */
	@Override
	public List<VehicleFile> getVehicleFile(Long statisticalSampleEntityId) {

		TypedQuery<VehicleFile> query = entityManager.createQuery("SELECT pgrs.vehicleFile FROM PollutantGasResultSet pgrs  where pgrs.keptInStatSample	= TRUE and  pgrs.inQuarantine = FALSE	and  pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, VehicleFile.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getVehicleBasedOnPgResult(java.lang.Long)
	 */
	@Override
	public List<Vehicle> getVehicleBasedOnPgResult(Long statisticalSampleEntityId) {

		TypedQuery<Vehicle> query = entityManager.createQuery("SELECT pgrs.vehicleFile.vehicle FROM PollutantGasResultSet pgrs  where pgrs.keptInStatSample	= TRUE and  pgrs.inQuarantine = FALSE	and  pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, Vehicle.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getPgTestResult(java.lang.Long)
	 */
	// pollutantGasTestResult
	@Override
	public List<PollutantGasTestResult> getPgTestResult(Long statisticalSampleEntityId) {

		TypedQuery<PollutantGasTestResult> query = entityManager.createQuery("SELECT pgrs.pollutantGasTestResult FROM PollutantGasResultSet pgrs  where pgrs.keptInStatSample	= TRUE  and  pgrs.inQuarantine = FALSE	and  pgrs.statisticalSample.entityId =" + statisticalSampleEntityId, PollutantGasTestResult.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository#getSamplebySampleParameter(java.lang.Long)
	 */
	@Override
	public List<StatisticalSample> getSamplebySampleParameter(Long spEntityId) {

		logger.info("getStatisticalSample by SampleParameter---------->");
		TypedQuery<StatisticalSample> query = entityManager.createQuery("SELECT ss FROM StatisticalSample ss LEFT JOIN ss.technicalCase.technicalGroup.regulationGroup.statisticalCalculationRule.statisticalCalculationParameters scp where ss.closed	= FALSE and scp.entityId	=" + spEntityId, StatisticalSample.class);
		return query.getResultList();
	}

}

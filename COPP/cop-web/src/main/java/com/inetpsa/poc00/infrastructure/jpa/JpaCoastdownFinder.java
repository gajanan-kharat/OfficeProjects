package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.coastdown.CoastdownFinder;
import com.inetpsa.poc00.rest.coastdown.CoastdownRepresentation;

/**
 * The Class JpaCoastdownFinder.
 */
public class JpaCoastdownFinder implements CoastdownFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/* 
	 * @see com.inetpsa.poc00.rest.costdown.CoastdownFinder#getAllCoastdownRepresentation()
	 */
	@Override
	public List<CoastdownRepresentation> getAllCoastdownRepresentation() {

		TypedQuery<CoastdownRepresentation> query = entityManager.createQuery(
				"SELECT  new " + CoastdownRepresentation.class.getName()
						+ "(coastdown.pSAReference,coastdown.roadLaw,coastdown.theoricalBenchF0,coastdown.theoricalBenchF1,coastdown.theoricalBenchF2,coastdown.computedBenchF0,coastdown.computedBenchF1,coastdown.computedBenchF2,coastdown.version,coastdown.inertia.value,coastdown.latestversion,coastdown.entityId) from CoastDown coastdown ORDER BY coastdown.pSAReference,coastdown.roadLaw,coastdown.inertia.value,coastdown.version",
				CoastdownRepresentation.class);

		logger.info("Returning Coast Down data");

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.coastdown.CoastdownFinder#getAllCoastdownLabels()
	 */
	@Override
	public List<String> getAllCoastdownLabels() {
		TypedQuery<String> query = entityManager.createQuery("SELECT  distinct  CONCAT(coastdown.pSAReference,coastdown.version) from CoastDown coastdown ", String.class);

		logger.info("returning Coastdown data");
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.coastdown.CoastdownFinder#getCoastdownTvv()
	 */
	@Override
	public List<CoastdownRepresentation> getCoastdownTvv() {

		TypedQuery<CoastdownRepresentation> query = entityManager.createQuery("SELECT  new " + CoastdownRepresentation.class.getName() + "(coastdown.entityId,coastdown.pSAReference) from CoastDown coastdown ORDER BY coastdown.pSAReference", CoastdownRepresentation.class);

		logger.info("Returning Coast Down data");

		return query.getResultList();
	}

}

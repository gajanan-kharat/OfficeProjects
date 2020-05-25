/*
 * Creation : Jun 8, 2016
 */

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.valuedcoastdown.ValuedCoastDownFinder;

/**
 * The Class JpaCoastdownFinder.
 */
public class JpaValuedCoastDownFinder implements ValuedCoastDownFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.valuedcoastdown.ValuedCoastDownFinder#getAllCoastdownLabels()
	 */
	@Override
	public List<String> getAllCoastdownLabels() {
		TypedQuery<String> query = entityManager.createQuery("SELECT distinct CONCAT(coastdown.pSAReference,coastdown.version) from TvvValuedCoastDown coastdown ", String.class);

		logger.info("returning Valued Coastdown data");
		return query.getResultList();
	}

}

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.inertia.Inertia;
import com.inetpsa.poc00.rest.inertia.InertiaFinder;

/**
 * The Class JpaInertiaFinder.
 */
public class JpaInertiaFinder implements InertiaFinder {

	/** The logger. */
	@Logging
	private Logger logger;

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.inertia.InertiaFinder#getinertiaObj(int)
	 */
	@Override
	public List<Inertia> getinertiaObj(int value) {

		logger.info("Query running to get Inertia Object where value:", value);

		String query1 = "SELECT ina " + "FROM Inertia ina " + "WHERE ina.value = ?1 ";

		TypedQuery<Inertia> queryResult = entityManager.createQuery(query1, Inertia.class);

		queryResult.setParameter(1, value);

		List<Inertia> results = queryResult.getResultList();
		logger.info("Returning Inertia Object");
		return results;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.inertia.InertiaFinder#getAllInertiaValues()
	 */
	@Override
	public List<Integer> getAllInertiaValues() {
		TypedQuery<Integer> query = entityManager.createQuery("select distinct i.value" + " from Inertia i ", Integer.class);
		return query.getResultList();

	}

}

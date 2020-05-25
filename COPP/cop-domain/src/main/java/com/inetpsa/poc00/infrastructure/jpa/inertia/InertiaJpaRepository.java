package com.inetpsa.poc00.infrastructure.jpa.inertia;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.inertia.Inertia;
import com.inetpsa.poc00.domain.inertia.InertiaRepository;

/**
 * The Class InertiaJpaRepository.
 */
public class InertiaJpaRepository extends BaseJpaRepository<Inertia, Long> implements InertiaRepository {

	/** The logger. */
	@Logging
	private Logger logger;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.inertia.InertiaRepository#saveInertia(com.inetpsa.poc00.domain.inertia.Inertia)
	 */
	@Override
	public Inertia saveInertia(Inertia inertia) {

		super.save(inertia);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.inertia.InertiaRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.inertia.InertiaRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.inertia.InertiaRepository#getinertiaByValue(int)
	 */
	@Override
	public Inertia getInertiaByValue(Integer value) {

		String queryString = "SELECT ina FROM Inertia ina WHERE ina.value = ?1 ";

		TypedQuery<Inertia> queryResult = entityManager.createQuery(queryString, Inertia.class);

		queryResult.setParameter(1, value);

		List<Inertia> results = queryResult.getResultList();

		if (!results.isEmpty()) {
			return results.get(0);
		} else
			return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.inertia.InertiaRepository#getinertiaObj(int)
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

}

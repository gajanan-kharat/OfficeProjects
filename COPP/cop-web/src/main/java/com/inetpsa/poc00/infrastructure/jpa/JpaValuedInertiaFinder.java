/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.rest.valuedinertia.ValuedInertiaFinder;


/**
 * The Class JpaValuedInertiaFinder.
 */
public class JpaValuedInertiaFinder implements ValuedInertiaFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.valuedinertia.ValuedInertiaFinder#getAllInertiaValues()
	 */
	@Override
	public List<Integer> getAllInertiaValues() {
		TypedQuery<Integer> query = entityManager.createQuery("select distinct i.value" + " from ValuedInertia i ", Integer.class);
		return query.getResultList();

	}
}

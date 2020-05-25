/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.inetpsa.poc00.domain.tirebrand.TireBrand;
import com.inetpsa.poc00.rest.tirebrand.TireBrandFinder;

/**
 * The Class JpaTireBrandFinder.
 */
public class JpaTireBrandFinder implements TireBrandFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tirebrand.TireBrandFinder#getAllTireBrands()
	 */
	@Override
	public List<TireBrand> getAllTireBrands() {

		return entityManager.createQuery("SELECT tb FROM TireBrand tb").getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tirebrand.TireBrandFinder#getTireBrandByLabel(java.lang.String)
	 */
	@Override
	public List<TireBrand> getTireBrandByLabel(String label) {

		return entityManager.createQuery("SELECT tb FROM TireBrand tb where tb.label=:plabel").setParameter("plabel", label).getResultList();

	}
}
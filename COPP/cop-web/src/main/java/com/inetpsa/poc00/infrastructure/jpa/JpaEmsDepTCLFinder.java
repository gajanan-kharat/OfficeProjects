/*
 * Creation : Apr 13, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLRepresentation;


/**
 * The Class JpaEmsDepTCLFinder.
 */
public class JpaEmsDepTCLFinder implements EmsDepTCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getAllEmsDepTCL()
	 */
	@Override
	public List<EmsDepTCLRepresentation> getAllEmsDepTCL() {

		List<EmsDepTCLRepresentation> resultList = new ArrayList<>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTCL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmsDepTCLRepresentation> query = entityManager.createQuery("select new " + EmsDepTCLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from EmissionStdDepTCL t where t.entityId =" + id, EmsDepTCLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getEMSDepLists(long)
	 */
	@Override
	public List<EmsDepTCLRepresentation> getEMSDepLists(long emsId) {
		List<EmsDepTCLRepresentation> resultList = new ArrayList<>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTCL t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmsDepTCLRepresentation> query = entityManager.createQuery("select new " + EmsDepTCLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,t.emissionStandard.entityId)" + " from EmissionStdDepTCL t where t.entityId =" + id + " and t.emissionStandard.entityId= " + emsId, EmsDepTCLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<EmissionStdDepTCL> getEmissionStandardDepLists(Long entityId) {

		TypedQuery<EmissionStdDepTCL> query = entityManager.createQuery("select t from EmissionStdDepTCL t where t.emissionStandard.entityId= " + entityId, EmissionStdDepTCL.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getLatestEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<EmissionStdDepTCL> getLatestEmissionStandardDepLists(Long emsId) {
		List<EmissionStdDepTCL> resultList = new ArrayList<>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTCL t1 where t1.emissionStandard.entityId = " + emsId + "  group by t1.label) ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmissionStdDepTCL> query = entityManager.createQuery("select t from EmissionStdDepTCL t where t.entityId =" + id, EmissionStdDepTCL.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder#getConditionsForLabel(long, java.lang.String)
	 */
	@Override
	public List<EmsDepTCLRepresentation> getConditionsForLabel(long entityId, String emsId) {

		TypedQuery<EmsDepTCLRepresentation> query = entityManager.createQuery("select new " + EmsDepTCLRepresentation.class.getName() + "(t.entityId) from EmissionStdDepTCL t where t.label ='" + emsId + "' and t.emissionStandard.entityId= " + entityId, EmsDepTCLRepresentation.class);
		return query.getResultList();

	}

}

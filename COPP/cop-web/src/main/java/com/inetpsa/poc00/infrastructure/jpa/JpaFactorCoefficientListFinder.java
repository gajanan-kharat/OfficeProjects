/*
 * Creation : Apr 18, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentListRepresentation;

/**
 * The Class JpaFactorCoefficientListFinder.
 */
public class JpaFactorCoefficientListFinder implements FactorCoefficentListFinder {

	/** The entity manager. */
	@Inject
	EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getAllEmsDepFCL()
	 */
	@Override
	public List<FactorCoefficentListRepresentation> getAllEmsDepFCL() {

		List<FactorCoefficentListRepresentation> resultList = new ArrayList<FactorCoefficentListRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from FactorCoefficentList t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<FactorCoefficentListRepresentation> query = entityManager.createQuery("select new " + FactorCoefficentListRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from FactorCoefficentList t where t.entityId =" + id, FactorCoefficentListRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getEMSDepLists(long)
	 */
	@Override
	public List<FactorCoefficentListRepresentation> getEMSDepLists(long emsId) {

		List<FactorCoefficentListRepresentation> resultList = new ArrayList<FactorCoefficentListRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from FactorCoefficentList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<FactorCoefficentListRepresentation> query = entityManager.createQuery("select new " + FactorCoefficentListRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,t.emissionStandard.entityId)" + " from FactorCoefficentList t where t.entityId =" + id, FactorCoefficentListRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<FactorCoefficentList> getEmissionStandardDepLists(Long entityId) {
		TypedQuery<FactorCoefficentList> query = entityManager.createQuery("select t   from FactorCoefficentList t where t.emissionStandard.entityId= " + entityId, FactorCoefficentList.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getFCAppTypeForLabel(long, java.lang.String)
	 */
	@Override
	public List<FactorCoefficentListRepresentation> getFCAppTypeForLabel(long entityId, String label) {
		TypedQuery<FactorCoefficentListRepresentation> query = entityManager.createQuery("select new " + FactorCoefficentListRepresentation.class.getName() + "(t.entityId) from FactorCoefficentList t where t.label = '" + label + "' and t.emissionStandard.entityId= " + entityId, FactorCoefficentListRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder#getLatestEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<FactorCoefficentList> getLatestEmissionStandardDepLists(Long emsId) {

		List<FactorCoefficentList> resultList = new ArrayList<FactorCoefficentList>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from FactorCoefficentList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<FactorCoefficentList> query = entityManager.createQuery("select t from FactorCoefficentList t where t.entityId =" + id, FactorCoefficentList.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

}

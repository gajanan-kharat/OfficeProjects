/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

/**
 * The Class JpaTvvDepTCLFinder.
 */
public class JpaTvvDepTCLFinder implements TvvDepTCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder#getAllTvvDepTCL()
	 */
	@Override
	public List<TvvDepTCLRepresentation> getAllTvvDepTCL() {

		List<TvvDepTCLRepresentation> resultList = new ArrayList<TvvDepTCLRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTCL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<TvvDepTCLRepresentation> query = entityManager.createQuery("select new " + TvvDepTCLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvDepTCL t where t.entityId =" + id, TvvDepTCLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder#getAllTvvDependentLists()
	 */
	@Override
	public List<TvvDepTCL> getAllTvvDependentLists() {
		List<TvvDepTCL> resultList = new ArrayList<TvvDepTCL>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTCL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<TvvDepTCL> query = entityManager.createQuery("select t from TvvDepTCL t where t.entityId =" + id, TvvDepTCL.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder#getTvvDepTCLLabel(java.lang.String)
	 */
	@Override
	public List<TvvDepTCLRepresentation> getTvvDepTCLLabel(String label) {

		TypedQuery<TvvDepTCLRepresentation> query = entityManager.createQuery("select new " + TvvDepTCLRepresentation.class.getName() + "(t.entityId) from TvvDepTCL t where t.label ='" + label + "'", TvvDepTCLRepresentation.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder#getAllTvvDependentTCLLists()
	 */
	@Override
	public List<TvvStructureRepresentation> getAllTvvDependentTCLLists() {

		List<TvvStructureRepresentation> resultList = new ArrayList<TvvStructureRepresentation>();

		TypedQuery<TvvStructureRepresentation> query = entityManager.createQuery("select new " + TvvStructureRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,'TVV_DEP_TCL') from TvvDepTCL t", TvvStructureRepresentation.class);

		return query.getResultList();

	}

}

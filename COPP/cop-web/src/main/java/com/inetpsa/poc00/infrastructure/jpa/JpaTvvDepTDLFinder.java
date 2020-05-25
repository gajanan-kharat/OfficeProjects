package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

/**
 * The Class JpaTvvDepTDLFinder.
 */
public class JpaTvvDepTDLFinder implements TvvDepTDLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder#getAllTvvDepTDL()
	 */
	@Override
	public List<TvvDepTDLRepresentation> getAllTvvDepTDL() {
		List<TvvDepTDLRepresentation> resultList = new ArrayList<TvvDepTDLRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTDL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<TvvDepTDLRepresentation> query = entityManager.createQuery("select new " + TvvDepTDLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from TvvDepTDL t where t.entityId =" + id, TvvDepTDLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder#getAllTvvDependentLists()
	 */
	@Override
	public List<TvvDepTDL> getAllTvvDependentLists() {
		List<TvvDepTDL> resultList = new ArrayList<TvvDepTDL>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTDL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<TvvDepTDL> query = entityManager.createQuery("select t from TvvDepTDL t where t.entityId =" + id, TvvDepTDL.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder#getAllTvvDependentTDLLists()
	 */
	// for MANDATORY TVV STRUCTURE
	@Override
	public List<TvvStructureRepresentation> getAllTvvDependentTDLLists() {

		List<TvvStructureRepresentation> resultList = new ArrayList<TvvStructureRepresentation>();

		TypedQuery<TvvStructureRepresentation> query = entityManager.createQuery("select new " + TvvStructureRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,'TVV_DEP_TDL') from TvvDepTDL t", TvvStructureRepresentation.class);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder#getTvvDepTDLLabel(java.lang.String)
	 */
	@Override
	public List<TvvDepTDLRepresentation> getTvvDepTDLLabel(String label) {

		TypedQuery<TvvDepTDLRepresentation> query = entityManager.createQuery("select new " + TvvDepTDLRepresentation.class.getName() + "(t.entityId) from TvvDepTDL t where t.label ='" + label + "'", TvvDepTDLRepresentation.class);
		return query.getResultList();

	}

}
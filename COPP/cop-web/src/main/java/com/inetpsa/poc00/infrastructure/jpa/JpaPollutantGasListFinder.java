/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitListRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder;

/**
 * The Class JpaPollutantGasListFinder.
 */
public class JpaPollutantGasListFinder implements PollutantGasListFinder {

	/** The entity manager. */
	@Inject
	EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getAllEmsDepPGL()
	 */
	@Override
	public List<PollutantGasLimitListRepresentation> getAllEmsDepPGL() {

		List<PollutantGasLimitListRepresentation> resultList = new ArrayList<PollutantGasLimitListRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from PollutantGasList t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<PollutantGasLimitListRepresentation> query = entityManager.createQuery("select new " + PollutantGasLimitListRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from PollutantGasList t where t.entityId =" + id, PollutantGasLimitListRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getEMSDepLists(long)
	 */
	@Override
	public List<PollutantGasLimitListRepresentation> getEMSDepLists(long emsId) {

		List<PollutantGasLimitListRepresentation> resultList = new ArrayList<PollutantGasLimitListRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from PollutantGasLimitList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<PollutantGasLimitListRepresentation> query = entityManager.createQuery("select new " + PollutantGasLimitListRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,t.emissionStandard.entityId)" + " from PollutantGasLimitList t where t.entityId =" + id, PollutantGasLimitListRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<PollutantGasLimitList> getEmissionStandardDepLists(Long entityId) {
		TypedQuery<PollutantGasLimitList> query = entityManager.createQuery("select t from PollutantGasLimitList t where t.emissionStandard.entityId= " + entityId, PollutantGasLimitList.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getPGListForLabel(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<PollutantGasLimitListRepresentation> getPGListForLabel(long entityId, String label) {
		TypedQuery<PollutantGasLimitListRepresentation> query = entityManager.createQuery("select new " + PollutantGasLimitListRepresentation.class.getName() + "(t.entityId) from PollutantGasLimitList t where t.label= '" + label + "' and t.emissionStandard.entityId= " + entityId, PollutantGasLimitListRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder#getLatestEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<PollutantGasLimitList> getLatestEmissionStandardDepLists(Long emsId) {
		List<PollutantGasLimitList> resultList = new ArrayList<>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from PollutantGasLimitList t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<PollutantGasLimitList> query = entityManager.createQuery("select t  from PollutantGasLimitList t where t.entityId =" + id, PollutantGasLimitList.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

}

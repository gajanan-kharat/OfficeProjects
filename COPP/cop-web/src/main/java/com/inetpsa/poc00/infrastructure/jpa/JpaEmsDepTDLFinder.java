/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder;
import com.inetpsa.poc00.emsdeptdl.EmsDepTDLRepresentation;

/**
 * The Class JpaEmsDepTDLFinder.
 */
public class JpaEmsDepTDLFinder implements EmsDepTDLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** Logger log4j to write messages. */
	private static Logger logger = LoggerFactory.getLogger(JpaEmsDepTDLFinder.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getAllEmsDepTDL()
	 */
	@Override
	public List<EmsDepTDLRepresentation> getAllEmsDepTDL() {

		List<EmsDepTDLRepresentation> resultList = new ArrayList<EmsDepTDLRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTDL t1 group by t1.label)");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmsDepTDLRepresentation> query = entityManager.createQuery("select new " + EmsDepTDLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label)" + " from EmissionStdDepTDL t where t.entityId =" + id, EmsDepTDLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getEMSDepLists(long)
	 */
	@Override
	public List<EmsDepTDLRepresentation> getEMSDepLists(long emsId) {
		List<EmsDepTDLRepresentation> resultList = new ArrayList<EmsDepTDLRepresentation>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTDL t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmsDepTDLRepresentation> query = entityManager.createQuery("select new " + EmsDepTDLRepresentation.class.getName() + "(t.entityId, t.description,t.version,t.label,t.emissionStandard.entityId)" + " from EmissionStdDepTDL t where t.entityId =" + id + " and t.emissionStandard.entityId= " + emsId, EmsDepTDLRepresentation.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<EmissionStdDepTDL> getEmissionStandardDepLists(Long entityId) {
		TypedQuery<EmissionStdDepTDL> query = entityManager.createQuery("select t from EmissionStdDepTDL t where t.emissionStandard.entityId= " + entityId, EmissionStdDepTDL.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#getLatestEmissionStandardDepLists(java.lang.Long)
	 */
	@Override
	public List<EmissionStdDepTDL> getLatestEmissionStandardDepLists(Long emsId) {
		List<EmissionStdDepTDL> resultList = new ArrayList<EmissionStdDepTDL>();
		Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from EmissionStdDepTDL t1 where t1.emissionStandard.entityId= " + emsId + " group by t1.label ");
		List<Long> idList = idSubQuery.getResultList();
		for (Long id : idList) {
			TypedQuery<EmissionStdDepTDL> query = entityManager.createQuery("select t from EmissionStdDepTDL t where t.entityId =" + id + " and t.emissionStandard.entityId= " + emsId, EmissionStdDepTDL.class);
			resultList.add(query.getSingleResult());
		}
		return resultList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder#checkLabel(java.lang.String, java.lang.Long)
	 */
	@Override
	public Long checkLabel(String label, Long emsId) {

		try {
			Query query = entityManager.createNativeQuery(Constants.EMS_DEP_PLL_LABEL_QUERY);

			query.setParameter(1, label);
			query.setParameter(2, emsId);

			Object obj = query.getSingleResult();

			return emsId;
		} catch (NoResultException e) {
			// This exception should not be logged as this is valid scenario
			logger.error("No result, Method : checkLabel()");
			return null;
		}
	}
}

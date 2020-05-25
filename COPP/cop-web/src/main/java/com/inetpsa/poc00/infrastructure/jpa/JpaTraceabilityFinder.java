package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.traceability.TraceabilityFinder;
import com.inetpsa.poc00.traceability.TraceabilityRepresentation;

/**
 * The Class JpaTraceabilityFinder.
 */
public class JpaTraceabilityFinder implements TraceabilityFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.traceability.TraceabilityFinder#getAllHistory()
	 */
	@Override
	public List<TraceabilityRepresentation> getAllHistory() {

		TypedQuery<TraceabilityRepresentation> query = entityManager.createQuery("SELECT  new " + TraceabilityRepresentation.class.getName() + "(htry.entityId, htry.userId,htry.userProfile,htry.description,htry.newValue,htry.oldValue,htry.updationDate) " + "from Traceability htry Order by htry.updationDate", TraceabilityRepresentation.class);

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.traceability.TraceabilityFinder#getAllHistory(java.lang.String)
	 * 
	 * GET THE HISTORY DATA BASED ON SCREEN ID
	 */
	@Override
	public List<TraceabilityRepresentation> getAllHistory(String screenId) {

		TypedQuery<TraceabilityRepresentation> query = entityManager.createQuery("SELECT  new " + TraceabilityRepresentation.class.getName() + "(htry.entityId, htry.userId,htry.userProfile,htry.description,htry.newValue,htry.oldValue,htry.updationDate) " + "from Traceability htry where htry.screenId = ?1 Order by htry.updationDate", TraceabilityRepresentation.class);

		query.setParameter(1, screenId);

		return query.getResultList();
	}

	/**
	 * Gets the vehicle file history.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the vehicle file history
	 */
	@Override
	public List<TraceabilityRepresentation> getVehicleFileHistory(Long vehicleFileId) {

		TypedQuery<TraceabilityRepresentation> query = entityManager.createQuery("SELECT  new " + TraceabilityRepresentation.class.getName() + "(htry.entityId, htry.userId,htry.userProfile,htry.description,htry.newValue,htry.oldValue,htry.updationDate) " + "from Traceability htry where htry.screenId = ?1 and htry.vehicleFileHistory.entityId = ?2 Order by htry.updationDate desc",
				TraceabilityRepresentation.class);

		query.setParameter(1, Constants.VEHICLEFILE_SCREEN_ID);
		query.setParameter(2, vehicleFileId);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.traceability.TraceabilityFinder#getTvvHistory(java.lang.Long)
	 */
	@Override
	public List<TraceabilityRepresentation> getTvvHistory(Long tvvEntityId) {

		TypedQuery<TraceabilityRepresentation> query = entityManager.createQuery("SELECT  new " + TraceabilityRepresentation.class.getName() + "(htry.entityId, htry.userId,htry.userProfile,htry.description,htry.newValue,htry.oldValue,htry.updationDate) " + "from Traceability htry where htry.screenId = ?1 and htry.objectId = ?2 Order by htry.updationDate desc", TraceabilityRepresentation.class);

		query.setParameter(1, Constants.TVV_SCREEN_ID);
		query.setParameter(2, tvvEntityId);

		return query.getResultList();
	}

}

/*
 * Creation : Sep 22, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusRepresentation;

/**
 * The Class JpaVehicleFileStatusFinder.
 */
public class JpaVehicleFileStatusFinder implements VehicleFileStatusFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder#getVehicleFileStatusbyLabel(java.lang.String)
	 */
	@Override
	public VehicleFileStatus getVehicleFileStatusbyLabel(String label) {
		TypedQuery<VehicleFileStatus> query = entityManager.createQuery("from VehicleFileStatus where LABEL= '" + label + "'", VehicleFileStatus.class);

		List<VehicleFileStatus> vehicleFileStatusList = query.getResultList();

		if (vehicleFileStatusList.isEmpty())
			return null;

		return vehicleFileStatusList.get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder#getVehicleFileStatusList()
	 */
	@Override
	public List<VehicleFileStatusRepresentation> getVehicleFileStatusList() {
		TypedQuery<VehicleFileStatusRepresentation> query = entityManager.createQuery("SELECT new " + VehicleFileStatusRepresentation.class.getName() + "(vf.entityId,vf.label,vf.guiLabel)" + " FROM VehicleFileStatus vf", VehicleFileStatusRepresentation.class);
		return query.getResultList();
	}
}

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.rest.vehiclecoefficent.VehicleCoeffFinder;
import com.inetpsa.poc00.rest.vehiclecoefficent.VehicleCoeffRepresentation;

/**
 * The Class JpaVehicleCoeffFinder.
 */
public class JpaVehicleCoeffFinder implements VehicleCoeffFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.vehiclecoefficent.VehicleCoeffFinder#getAllVehicleCoefficents()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleCoeffRepresentation> getAllVehicleCoefficents() {

		TypedQuery<VehicleCoeffRepresentation> query = entityManager.createQuery("SELECT  new " + VehicleCoeffRepresentation.class.getName() + "(vehcof.entityId, vehcof.label, vehcof.description, vehcof.f0Coeffiecient, vehcof.f1Coeffiecient, vehcof.f2Coeffiecient) from VehicleCoefficents vehcof ", VehicleCoeffRepresentation.class);

		return query.getResultList();

	}

}

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.fueltype.FuelTypeFinder;
import com.inetpsa.poc00.rest.fueltype.FuelTypeRepresentation;

/**
 * The Class JpaFuelTypeFinder.
 */
public class JpaFuelTypeFinder implements FuelTypeFinder {

	/** The entity manager. */
	@Inject
	EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fueltype.FuelTypeFinder#findAllCarburantData()
	 */
	/* 
	 * @see com.inetpsa.poc00.rest.referential.globalReference.FuelTypeFinder#findAllCarburantData()
	 */
	@Override
	public List<FuelTypeRepresentation> findAllCarburantData() {

		logger.info("Query running to get Fuel Type value");

		TypedQuery<FuelTypeRepresentation> query = entityManager.createQuery(
				"select new " + FuelTypeRepresentation.class.getName() + "(t1.lcdvCodeValue,t1.kmat,t1.genomeLcdvCodeValue.frLabel,tp.fuelTypeLable,t1.entityId, tp.entityId)" + " from GenomeTVVRule t1 " + "left join t1.fuelType tp " + "where t1.lcdvCodeName='DCD' and t1.lcdvCodeValue = t1.genomeLcdvCodeValue.lcdvCodeValue GROUP BY t1.lcdvCodeValue,t1.kmat ORDER BY t1.entityId asc",
				FuelTypeRepresentation.class);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fueltype.FuelTypeFinder#findFuelTypeDataByLabel(java.lang.String)
	 */
	@Override
	public List<FuelTypeRepresentation> findFuelTypeDataByLabel(String label) {

		logger.info("Querry running to check if label present");

		TypedQuery<FuelTypeRepresentation> query = entityManager.createQuery("select new " + FuelTypeRepresentation.class.getName() + "(tp.fuelTypeLable,tp.entityId) from FuelType tp where tp.fuelTypeLable='" + label + "'", FuelTypeRepresentation.class);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fueltype.FuelTypeFinder#findAllFuelTypeData()
	 */
	@Override
	public List<FuelTypeRepresentation> findAllFuelTypeData() {
		TypedQuery<FuelTypeRepresentation> query = entityManager.createQuery("select new " + FuelTypeRepresentation.class.getName() + "( tp.entityId,tp.fuelTypeLable) " + " from FuelType tp ", FuelTypeRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.fueltype.FuelTypeFinder#findAllFuelTypeLabel()
	 */
	@Override
	public List<String> findAllFuelTypeLabel() {
		TypedQuery<String> query = entityManager.createQuery("SELECT DISTINCT tp.fuelTypeLable FROM FuelType tp ", String.class);
		return query.getResultList();

	}

}

/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;

/**
 * The Class JpaFuelInjectionTypeFinder.
 */
public class JpaFuelInjectionTypeFinder implements FuelInjectionTypeFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#getAllFuelInjectionTypes()
	 */
	/* 
	 * @see com.inetpsa.poc00.rest.FuelInjectionType.FuelInjectionTypeFinder#getAllFuelInjectionTypes()
	 */
	@Override
	public List<FuelInjectionTypeRepresentation> getAllFuelInjectionTypes() {
		TypedQuery<FuelInjectionTypeRepresentation> query = entityManager.createQuery("select new " + FuelInjectionTypeRepresentation.class.getName() + "(t.entityId,t.label, t.description)" + " from FuelInjectionType t ", FuelInjectionTypeRepresentation.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#findFuelInjectionTypeDataByLabel(java.lang.String)
	 */
	@Override
	public List<FuelInjectionTypeRepresentation> findFuelInjectionTypeDataByLabel(String label) {
		logger.info("querry running to check if label present");

		TypedQuery<FuelInjectionTypeRepresentation> query = entityManager.createQuery("select new " + FuelInjectionTypeRepresentation.class.getName() + "(f.entityId) from FuelInjectionType f where f.label='" + label + "'", FuelInjectionTypeRepresentation.class);

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#getAllFITypesForES(long)
	 */
	/* 
	 * @see com.inetpsa.poc00.rest.FuelInjectionType.FuelInjectionTypeFinder#getAllFITypesForES(long)
	 */
	@Override
	public List<FuelInjectionTypeRepresentation> getAllFITypesForES(long emsId) {
		TypedQuery<FuelInjectionTypeRepresentation> query = entityManager.createQuery("select new " + FuelInjectionTypeRepresentation.class.getName() + "(t.entityId,t.label, t.description )" + " from FuelInjectionType t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, FuelInjectionTypeRepresentation.class);
		return query.getResultList();
	}

	/** The logger. */
	@Logging
	private Logger logger;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#findAllFuelInjectionTypeData()
	 */
	/* 
	 * @see com.inetpsa.poc00.rest.FuelInjectionType.FuelInjectionTypeFinder#findAllFuelInjectionTypeData()
	 */
	@Override
	public List<FuelInjectionTypeRepresentation> findAllFuelInjectionTypeData() {

		logger.info("querry running to get FuelInjectionTypeRepresentation value");
		TypedQuery<FuelInjectionTypeRepresentation> query = entityManager.createQuery("select new " + FuelInjectionTypeRepresentation.class.getName() + "(fit.entityId,fit.label)" + " from FuelInjectionType fit", FuelInjectionTypeRepresentation.class);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#getAllFITypesForCopiedES(java.lang.Long)
	 */
	@Override
	public List<FuelInjectionType> getAllFITypesForCopiedES(Long emsId) {
		TypedQuery<FuelInjectionType> query = entityManager.createQuery("select t from FuelInjectionType t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, FuelInjectionType.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#getAllFITypeForCopiedPGList(java.lang.Long)
	 */
	@Override
	public List<FuelInjectionType> getAllFITypeForCopiedPGList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTFIT C JOIN COPQTMPI L ON (L.FUELIT_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID = ?1", FuelInjectionType.class);

		query.setParameter(1, entityId);

		List<FuelInjectionType> result = query.getResultList();

		return result;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder#getAllFITypeForCopiedFCList(java.lang.Long)
	 */
	@Override
	public List<FuelInjectionType> getAllFITypeForCopiedFCList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTFIT C JOIN COPQTMFI L ON (L.FUELITID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID =?1", FuelInjectionType.class);

		query.setParameter(1, entityId);

		List<FuelInjectionType> result = query.getResultList();

		return result;
	}

}
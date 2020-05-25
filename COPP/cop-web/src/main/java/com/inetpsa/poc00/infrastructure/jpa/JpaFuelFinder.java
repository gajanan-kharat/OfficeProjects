/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.fueltype.FuelTypeFactory;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.rest.fuel.FuelFinder;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fueltype.FuelTypeRepresentation;

/**
 * The Class JpaFuelFinder.
 */
public class JpaFuelFinder implements FuelFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The fuel repository. */
	@Inject
	private FuelTypeRepository fuelTypeRepository;

	@Inject
	private FuelTypeFactory fuelTypeFactory;

	@Inject
	private FuelFactory fuelFactory;

	@Inject
	private FuelRepository fuelRepository;

	/** The logger. */
	@Logging
	private Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuels()
	 */
	/*
	 * @see com.inetpsa.poc00.rest.Fuel.FuelFinder#getAllFuels()
	 */
	@Override
	public List<FuelRepresentation> getAllFuels() {
		TypedQuery<FuelRepresentation> query = entityManager.createQuery("select new " + FuelRepresentation.class.getName() + "(t.entityId, t.label,t.description, t.defaultFuel)" + " from Fuel t ", FuelRepresentation.class);
		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuel()
	 */
	/*
	 * @see com.inetpsa.poc00.rest.Fuel.FuelFinder#getAllFuel()
	 */
	@Override
	public List<FuelRepresentation> getAllFuel() {

		TypedQuery<FuelRepresentation> query = entityManager.createQuery("SELECT  new " + FuelRepresentation.class.getName() + "(fl.entityId, fl.label,fl.description,fl.defaultFuel,ft.entityId,ft.fuelTypeLable) from Fuel fl left join fl.fuelType ft", FuelRepresentation.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelsForES(long)
	 */
	/*
	 * @see com.inetpsa.poc00.rest.Fuel.FuelFinder#getAllFuelsForES(long)
	 */
	@Override
	public List<FuelRepresentation> getAllFuelsForES(long emsId) {
		TypedQuery<FuelRepresentation> query = entityManager.createQuery("select new " + FuelRepresentation.class.getName() + "(t.entityId,t.label, t.description ,t.defaultFuel)" + " from Fuel t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, FuelRepresentation.class);
		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelsForFCList(long)
	 */
	/*
	 * @see com.inetpsa.poc00.rest.Fuel.FuelFinder#getAllFuelsForFCList(long)
	 */
	@Override
	public List<FuelRepresentation> getAllFuelsForFCList(long entityId) {
		TypedQuery<FuelRepresentation> query = entityManager.createQuery("select new " + FuelRepresentation.class.getName() + "(t.entityId,t.label , t.description,t.defaultFuel)" + " from Fuel t INNER JOIN t.factorCoefficentList f where f.entityId = " + entityId, FuelRepresentation.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelsForCopiedES(java.lang.Long)
	 */
	@Override
	public List<Fuel> getAllFuelsForCopiedES(Long emsId) {
		TypedQuery<Fuel> query = entityManager.createQuery("select t from Fuel t INNER JOIN t.emissionStandards e where e.entityId = " + emsId, Fuel.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelForCopiedPGList(java.lang.Long)
	 */
	@Override
	public List<Fuel> getAllFuelForCopiedPGList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTAFL C JOIN COPQTMPF L ON (L.FUEL_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID = ?1", Fuel.class);

		query.setParameter(1, entityId);

		List<Fuel> result = query.getResultList();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelForCopiedFCList(java.lang.Long)
	 */
	@Override
	public List<Fuel> getAllFuelForCopiedFCList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTAFL C JOIN COPQTMFF L ON (L.FUELID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1", Fuel.class);

		query.setParameter(1, entityId);

		List<Fuel> result = query.getResultList();

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelNames()
	 */
	@Override
	public List<String> getAllFuelNames() {
		TypedQuery<String> query = entityManager.createQuery("select distinct t.label from Fuel t ", String.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#FuelData()
	 */
	@Override
	public List<FuelRepresentation> getFuelData() {
		logger.info("querry running to get fuelData");

		TypedQuery<FuelRepresentation> query = entityManager.createQuery("select new " + FuelRepresentation.class.getName() + "(f.entityId,f.label) from Fuel f", FuelRepresentation.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.fuel.FuelFinder#getAllFuelDataForTVV(java.util.List)
	 */
	@Override
	public List<FuelRepresentation> getAllFuelDataForTVV(List<String> kmatList) {

		TypedQuery<FuelRepresentation> query = entityManager.createQuery("select new " + FuelRepresentation.class.getName() + "(f.entityId,f.label) from Fuel f join f.fuelType ftype  where ftype.genomeTvvRule.kmat in (:kmatList)", FuelRepresentation.class);
		query.setParameter("kmatList", kmatList);

		List<FuelRepresentation> fuelResult = query.getResultList();

		if (fuelResult == null || fuelResult.isEmpty()) {

			TypedQuery<String> temp = entityManager.createQuery("select distinct codeValue.frLabel from GenomeLCDVDictionary d join d.genomeLcdvCodeList lcdvcode join lcdvcode.genomeLcdvCodeValueList codeValue where lcdvcode.codeName = 'DCD' AND d.kmat in (:kmatList)", String.class);
			temp.setParameter("kmatList", kmatList);

			List<String> frLabel = temp.getResultList();

			for (String fuelTypeLabel : frLabel) {
				TypedQuery<FuelTypeRepresentation> fuelTypeLabelList = entityManager.createQuery("select new " + FuelTypeRepresentation.class.getName() + "(fl.entityId) from FuelType fl where fl.fuelTypeLable ='" + fuelTypeLabel + "' ", FuelTypeRepresentation.class);

				List<FuelTypeRepresentation> obj = fuelTypeLabelList.getResultList();

				if (obj == null || obj.isEmpty()) {
					FuelType fueltype = fuelTypeFactory.createFuelType();
					fueltype.setFuelTypeLable(fuelTypeLabel);
					FuelType tempFuelType = fuelTypeRepository.saveFuelType(fueltype);

					Fuel fuel = fuelFactory.createFuel();
					fuel.setFuelType(tempFuelType);
					fuel.setLabel(fuelTypeLabel);
					fuel.setDescription("Default Fuel");
					fuelRepository.saveFuel(fuel);
				}
			}

			TypedQuery<Fuel> query2 = entityManager.createQuery("select fuel from GenomeLCDVDictionary d inner join d.genomeLcdvCodeList lcdvcode inner join lcdvcode.genomeLcdvCodeValueList codeValue,Fuel fuel join fuel.fuelType fuelType where lcdvcode.codeName = 'DCD' AND d.kmat in (:kmatList) AND codeValue.frLabel = fuelType.fuelTypeLable", Fuel.class);
			query2.setParameter("kmatList", kmatList);
			List<Fuel> fuelList = query2.getResultList();

			fuelResult = new ArrayList<FuelRepresentation>();

			List<Long> entityId = new ArrayList<Long>();

			for (Fuel obj : fuelList) {

				if (!entityId.contains(obj.getEntityId())) {
					FuelRepresentation fuelRep = new FuelRepresentation();
					fuelRep.setEntityId(obj.getEntityId());
					fuelRep.setLabel(obj.getLabel());

					fuelResult.add(fuelRep);
					entityId.add(obj.getEntityId());
				}

			}

		}

		return fuelResult;

	}

}

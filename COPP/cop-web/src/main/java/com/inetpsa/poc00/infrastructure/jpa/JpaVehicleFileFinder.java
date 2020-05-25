/*
 * Creation : Sep 20, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.rest.bodywork.BodyWorkFinder;
import com.inetpsa.poc00.rest.carbrand.CarBrandFinder;
import com.inetpsa.poc00.rest.country.CountryFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder;
import com.inetpsa.poc00.rest.engine.EngineFinder;
import com.inetpsa.poc00.rest.fuel.FuelFinder;
import com.inetpsa.poc00.rest.fueltype.FuelTypeFinder;
import com.inetpsa.poc00.rest.gearbox.GearBoxFinder;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder;
import com.inetpsa.poc00.rest.vehicle.VehicleModelRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleSearchRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileAssembler;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder;

/**
 * The Class JpaVehicleFileFinder.
 */
public class JpaVehicleFileFinder implements VehicleFileFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The project code family finder. */
	@Inject
	ProjectCodeFamilyFinder projectCodeFamilyFinder;

	/** The vehicle file finder. */
	@Inject
	VehicleFileFinder vehicleFileFinder;

	/** The body work finder. */
	@Inject
	BodyWorkFinder bodyWorkFinder;

	/** The car brand finder. */
	@Inject
	CarBrandFinder carBrandFinder;

	/** The emission standard finder. */
	@Inject
	EmissionStandardFinder emissionStandardFinder;

	/** The engine finder. */
	@Inject
	EngineFinder engineFinder;

	/** The gear box finder. */
	@Inject
	GearBoxFinder gearBoxFinder;

	/** The fuel finder. */
	@Inject
	FuelFinder fuelFinder;

	/** The fuel type finder. */
	@Inject
	FuelTypeFinder fuelTypeFinder;

	/** The country finder. */
	@Inject
	CountryFinder countryFinder;

	/** The vehicle file assembler. */
	@Inject
	VehicleFileAssembler vehicleFileAssembler;

	/** The vehicle file status finder. */
	@Inject
	VehicleFileStatusFinder vehicleFileStatusFinder;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JpaVehicleFileFinder.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleFile(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFile getVehicleFile(Long vehicleId, Long typeOfTestId) {

		TypedQuery query = entityManager.createQuery("from VehicleFile vf where vf.vehicle.entityId= " + vehicleId + " and vf.typeOfTest.entityId= " + typeOfTestId + " and vf.vehicleFileStatus.label != '" + Constants.VEHICLEFILESTATUS_ARCHIVED + "'", VehicleFile.class);
		List<VehicleFile> vehicleFileList = query.getResultList();

		if (vehicleFileList.isEmpty()) {
			return null;
		}

		return vehicleFileList.get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleFileList(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleFileRepresentation> getVehicleFileList(Long vehicleId) {

		TypedQuery<VehicleFileRepresentation> query = entityManager.createQuery("select new " + VehicleFileRepresentation.class.getName()
				+ "(vf.entityId,vf.vehicle.chasisNumber, vf.vehicle.counterMark, vf.vehicle.registrationNumber, vf.creationDate,vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel,vf.vehicle.technicalCase.tvv.projectCodeFamily.vehicleFamilyLabel,vf.typeOfTest.entityId,vf.typeOfTest.label) from VehicleFile vf where vf.vehicle.entityId = " + vehicleId
				+ " and vf.vehicleFileStatus.label != '" + Constants.VEHICLEFILESTATUS_ARCHIVED + "' ORDER BY vf.entityId DESC", VehicleFileRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleCount(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Long getVehicleCount(Long basketId) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(vf) FROM VehicleFile vf WHERE vf.basket.entityId =" + basketId + " and vf.vehicleFileStatus.label != '" + Constants.VEHICLEFILESTATUS_ARCHIVED + "'", Long.class);

		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleFileByBaket(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleFileRepresentation> getVehicleFileByBaket(Long basketId) {
		TypedQuery<VehicleFileRepresentation> query = entityManager.createQuery("SELECT new " + VehicleFileRepresentation.class.getName()
				+ "(vf.entityId,vf.vehicle.counterMark,vf.vehicle.chasisNumber,vf.vehicle.registrationNumber,vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel,vf.vehicle.technicalCase.tvv.projectCodeFamily.vehicleFamilyLabel,vf.vehicleFileStatus.guiLabel,vf.typeOfTest.label,vf.vehicleFileStatus.label) FROM VehicleFile vf WHERE vf.basket.entityId = " + basketId
				+ " and vf.vehicleFileStatus.label != '" + Constants.VEHICLEFILESTATUS_ARCHIVED + "'", VehicleFileRepresentation.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleFileStatus()
	 */
	@Override
	public List<VehicleFileStatus> getVehicleFileStatus() {
		TypedQuery<VehicleFileStatus> query = entityManager.createQuery("Select vf.vehicleFileStatus from VehicleFile vf", VehicleFileStatus.class);
		List<VehicleFileStatus> vehicleFilestatusList = query.getResultList();

		if (vehicleFilestatusList.isEmpty()) {
			return new ArrayList<>();
		}

		return vehicleFilestatusList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#searchVehicleFile(com.inetpsa.poc00.rest.vehicle.VehicleSearchRepresentation)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<VehicleFileRepresentation> searchVehicleFile(VehicleSearchRepresentation vehicleSearchRepresentation) {
		List<VehicleFile> vehicleFileList;
		List<VehicleFileRepresentation> vehicleResultRepSet = new ArrayList<>();
		try {
			StringBuilder queryString = new StringBuilder("Select vf from VehicleFile vf");
			buildJoinClause(vehicleSearchRepresentation, queryString);
			// build Where Clause
			String whereClauseSearchVF = buildWhereClause(vehicleSearchRepresentation);
			if (whereClauseSearchVF.length() > 0) {
				queryString.append(" WHERE ");
				if (whereClauseSearchVF.trim().endsWith("AND")) {
					queryString.append(whereClauseSearchVF.substring(0, whereClauseSearchVF.lastIndexOf("AND")));
				} else {
					queryString.append(whereClauseSearchVF);
				}
			}
			// Query Creation
			TypedQuery<VehicleFile> typedQuery = entityManager.createQuery(queryString.toString(), VehicleFile.class);
			setSearchParametersVF(vehicleSearchRepresentation, typedQuery);
			logger.info("Sql Statement : " + typedQuery);
			// Query Execution
			vehicleFileList = typedQuery.getResultList();
			for (VehicleFile vehicleFile : vehicleFileList) {
				VehicleFileRepresentation vehicleFileRepresentation = new VehicleFileRepresentation();
				vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRepresentation, vehicleFile);
				vehicleResultRepSet.add(vehicleFileRepresentation);
			}

		} catch (Exception e) {
			logger.error("Exception occurred ", e);
		}
		return vehicleResultRepSet;
	}

	/**
	 * Sets the search parameters vf.
	 *
	 * @param vehicleSearchRepresentation the vehicle search representation
	 * @param typedQuery the typed query
	 */
	// SET Parameters
	private void setSearchParametersVF(VehicleSearchRepresentation vehicleSearchRepresentation, TypedQuery<VehicleFile> typedQuery) {

		if (vehicleSearchRepresentation.getTvvLabel() != null && !vehicleSearchRepresentation.getTvvLabel().isEmpty())
			typedQuery.setParameter("tvvLabel", vehicleSearchRepresentation.getTvvLabel());
		if (vehicleSearchRepresentation.getProjectCodeFamilyList() != null && !vehicleSearchRepresentation.getProjectCodeFamilyList().isEmpty()) {
			typedQuery.setParameter("pcfList", vehicleSearchRepresentation.getProjectCodeFamilyList());
		}
		if (vehicleSearchRepresentation.getBodyWorkList() != null && !vehicleSearchRepresentation.getBodyWorkList().isEmpty()) {
			typedQuery.setParameter("bodyWorkList", vehicleSearchRepresentation.getBodyWorkList());
		}
		if (vehicleSearchRepresentation.getCarBrandList() != null && !vehicleSearchRepresentation.getCarBrandList().isEmpty()) {
			typedQuery.setParameter("carBrandList", vehicleSearchRepresentation.getCarBrandList());
		}
		if (vehicleSearchRepresentation.getEmsList() != null && !vehicleSearchRepresentation.getEmsList().isEmpty()) {
			typedQuery.setParameter("EmsList", vehicleSearchRepresentation.getEmsList());
		}
		if (vehicleSearchRepresentation.getEngineList() != null && !vehicleSearchRepresentation.getEngineList().isEmpty()) {
			typedQuery.setParameter("engineIdList", vehicleSearchRepresentation.getEngineList());
		}
		if (vehicleSearchRepresentation.getGearBoxList() != null && !vehicleSearchRepresentation.getGearBoxList().isEmpty()) {
			typedQuery.setParameter("gearBoxId", vehicleSearchRepresentation.getGearBoxList());
		}
		if (vehicleSearchRepresentation.getFuelTypeList() != null && !vehicleSearchRepresentation.getFuelTypeList().isEmpty()) {
			typedQuery.setParameter("fuelTypeList", vehicleSearchRepresentation.getFuelTypeList());
		}
		if (vehicleSearchRepresentation.getFuelList() != null && !vehicleSearchRepresentation.getFuelList().isEmpty()) {
			typedQuery.setParameter("fuelList", vehicleSearchRepresentation.getFuelList());
		}

		if (vehicleSearchRepresentation.getCountryList() != null && !vehicleSearchRepresentation.getCountryList().isEmpty()) {
			typedQuery.setParameter("countryList", vehicleSearchRepresentation.getCountryList());

		}

		if (vehicleSearchRepresentation.getModelYear() != null && !vehicleSearchRepresentation.getModelYear().isEmpty()) {
			typedQuery.setParameter("modelYearList", vehicleSearchRepresentation.getModelYear());
		}
		if (vehicleSearchRepresentation.getChasisNumber() != null && !vehicleSearchRepresentation.getChasisNumber().isEmpty()) {
			typedQuery.setParameter("chasisNumber", vehicleSearchRepresentation.getChasisNumber());
		}
		if (vehicleSearchRepresentation.getCounterMark() != null && !vehicleSearchRepresentation.getCounterMark().isEmpty()) {
			typedQuery.setParameter("counterMark", vehicleSearchRepresentation.getCounterMark());
		}
		if (vehicleSearchRepresentation.getRegistrationNumber() != null && !vehicleSearchRepresentation.getRegistrationNumber().isEmpty()) {
			typedQuery.setParameter("registrationNumber", vehicleSearchRepresentation.getRegistrationNumber());
		}
		if (vehicleSearchRepresentation.getStatusList() != null && !vehicleSearchRepresentation.getStatusList().isEmpty()) {
			typedQuery.setParameter("statusId", vehicleSearchRepresentation.getStatusList());
		}
		if (vehicleSearchRepresentation.getUserId() != null && !vehicleSearchRepresentation.getUserId().isEmpty()) {
			typedQuery.setParameter("usrId", vehicleSearchRepresentation.getUserId());
		}
	}

	/**
	 * Builds the where clause.
	 *
	 * @param vehicleSearchRepresentation the vehicle search representation
	 * @return the string
	 */
	private String buildWhereClause(VehicleSearchRepresentation vehicleSearchRepresentation) {
		StringBuilder queryString = new StringBuilder();

		if (vehicleSearchRepresentation.getTvvLabel() != null && !vehicleSearchRepresentation.getTvvLabel().isEmpty())
			queryString.append("  tvv.label like :tvvLabel AND");
		if (vehicleSearchRepresentation.getProjectCodeFamilyList() != null && !vehicleSearchRepresentation.getProjectCodeFamilyList().isEmpty()) {
			queryString.append("  CONCAT (pc.projectCodeLabel,pc.vehicleFamilyLabel) in(:pcfList) AND");
		}
		if (vehicleSearchRepresentation.getBodyWorkList() != null && !vehicleSearchRepresentation.getBodyWorkList().isEmpty()) {
			queryString.append("  bw.label in(:bodyWorkList) AND");
		}
		if (vehicleSearchRepresentation.getCarBrandList() != null && !vehicleSearchRepresentation.getCarBrandList().isEmpty()) {
			queryString.append("  CONCAT(cb.brandLabel,cb.makerLabel) in(:carBrandList) AND");

		}
		if (vehicleSearchRepresentation.getEmsList() != null && !vehicleSearchRepresentation.getEmsList().isEmpty()) {
			queryString.append("  vf.vehicle.technicalCase.emissionStandard.entityId in(:EmsList) AND");
		}
		if (vehicleSearchRepresentation.getEngineList() != null && !vehicleSearchRepresentation.getEngineList().isEmpty()) {
			queryString.append(" vf.vehicle.technicalCase.tvv.engine.engineLabel in (:engineIdList) AND");
		}
		if (vehicleSearchRepresentation.getGearBoxList() != null && !vehicleSearchRepresentation.getGearBoxList().isEmpty()) {
			queryString.append(" vf.vehicle.technicalCase.tvv.gearBox.label in(:gearBoxId) AND");
		}
		if (vehicleSearchRepresentation.getFuelTypeList() != null && !vehicleSearchRepresentation.getFuelTypeList().isEmpty()) {
			queryString.append(" ft.fuelTypeLable in(:fuelTypeList) AND");
		}
		if (vehicleSearchRepresentation.getFuelList() != null && !vehicleSearchRepresentation.getFuelList().isEmpty()) {
			queryString.append(" fuel.entityId in(:fuelList) AND");
		}

		if (vehicleSearchRepresentation.getCountryList() != null && !vehicleSearchRepresentation.getCountryList().isEmpty()) {
			queryString.append(" vf.vehicle.destinationCountry.entityId in(:countryList) AND");
		}

		if (vehicleSearchRepresentation.getModelYear() != null && !vehicleSearchRepresentation.getModelYear().isEmpty()) {
			queryString.append(" vf.vehicle.modelYear in(:modelYearList) AND");
		}
		if (vehicleSearchRepresentation.getChasisNumber() != null && !vehicleSearchRepresentation.getChasisNumber().isEmpty()) {
			queryString.append(" vf.vehicle.chasisNumber LIKE :chasisNumber AND");
		}
		if (vehicleSearchRepresentation.getCounterMark() != null && !vehicleSearchRepresentation.getCounterMark().isEmpty()) {
			queryString.append(" vf.vehicle.counterMark LIKE :counterMark AND");
		}
		if (vehicleSearchRepresentation.getRegistrationNumber() != null && !vehicleSearchRepresentation.getRegistrationNumber().isEmpty()) {
			queryString.append(" vf.vehicle.registrationNumber LIKE :registrationNumber AND");
		}

		if (vehicleSearchRepresentation.getStatusList() != null && !vehicleSearchRepresentation.getStatusList().isEmpty()) {
			queryString.append(" vf.vehicleFileStatus.entityId in(:statusId) AND");
		}
		if (vehicleSearchRepresentation.getUserId() != null && !vehicleSearchRepresentation.getUserId().isEmpty()) {
			queryString.append(" vf.basket.user.userId LIKE :usrId AND");
		}

		return queryString.toString();

	}

	/**
	 * Builds the join clause.
	 * 
	 * @param vehicleSearchRepresentation the vehicle search representation
	 * @param queryString the l content of jpql query
	 */
	public void buildJoinClause(VehicleSearchRepresentation vehicleSearchRepresentation, StringBuilder queryString) {
		boolean tvvJoinDone = false;
		if (vehicleSearchRepresentation.getTvvLabel() != null && !vehicleSearchRepresentation.getTvvLabel().isEmpty()) {
			queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv");
			tvvJoinDone = true;
		}

		if (vehicleSearchRepresentation.getBodyWorkList() != null && !vehicleSearchRepresentation.getBodyWorkList().isEmpty()) {
			if (tvvJoinDone) {
				queryString.append(" join tvv.bodyWork bw");
			} else {
				queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv join tvv.bodyWork bw");
				tvvJoinDone = true;
			}
		}
		if (vehicleSearchRepresentation.getFuelTypeList() != null && !vehicleSearchRepresentation.getFuelTypeList().isEmpty()) {
			if (tvvJoinDone) {
				queryString.append(" join tvv.fuel.fuelType ft");
			} else {
				queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv join tvv.fuel.fuelType ft");
				tvvJoinDone = true;
			}
		}
		if (vehicleSearchRepresentation.getFuelList() != null && !vehicleSearchRepresentation.getFuelList().isEmpty()) {
			if (tvvJoinDone) {
				queryString.append(" join tvv.fuel fuel");
			} else {
				queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv join tvv.fuel fuel");
				tvvJoinDone = true;
			}
		}
		if (vehicleSearchRepresentation.getProjectCodeFamilyList() != null && !vehicleSearchRepresentation.getProjectCodeFamilyList().isEmpty()) {
			if (tvvJoinDone) {
				queryString.append(" join tvv.projectCodeFamily pc");
			} else {
				queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv join tvv.projectCodeFamily pc");
			}
		}
		if (vehicleSearchRepresentation.getCarBrandList() != null && !vehicleSearchRepresentation.getCarBrandList().isEmpty()) {
			if (tvvJoinDone) {
				queryString.append(" join tvv.carBrandSet cb");
			} else {
				queryString.append(" join vf.vehicle v join v.technicalCase tc join tc.tvv tvv join tvv.carBrandSet cb");

			}
		}

	}

	/**
	 * Get Filter values to view.
	 *
	 * @return VehicleSearchRepresentation
	 */
	@Override
	public VehicleModelRepresentation getvehicleSearchRep() {
		VehicleModelRepresentation vehicleModelRepresentation = new VehicleModelRepresentation();
		List<String> modelYear = getModelYearList();
		if (modelYear != null && !modelYear.isEmpty()) {
			vehicleModelRepresentation.setModelYear(modelYear);
		}
		vehicleModelRepresentation.setProjectCodeFamilyList(projectCodeFamilyFinder.getAllProjectFamilyNames());
		vehicleModelRepresentation.setStatusList(vehicleFileStatusFinder.getVehicleFileStatusList());
		vehicleModelRepresentation.setBodyWorkList(bodyWorkFinder.getAllBodyWorkNames());
		vehicleModelRepresentation.setCarBrandList(carBrandFinder.findAllCarBrandLabel());
		vehicleModelRepresentation.setEmsList(emissionStandardFinder.getAllEmissionStandards());
		vehicleModelRepresentation.setEngineList(engineFinder.findAllEngineNames());
		vehicleModelRepresentation.setGearBoxList(gearBoxFinder.getAllGearBoxNames());
		vehicleModelRepresentation.setFuelList(fuelFinder.getAllFuels());
		vehicleModelRepresentation.setFuelTypeList(fuelTypeFinder.findAllFuelTypeLabel());
		vehicleModelRepresentation.setCountryList(countryFinder.getCountryData());

		return vehicleModelRepresentation;
	}

	/**
	 * Get List of model yesr for filter.
	 * 
	 * @return Model Year List
	 */
	public List<String> getModelYearList() {
		TypedQuery<String> query = entityManager.createQuery("SELECT distinct vehicle.modelYear FROM Vehicle vehicle", String.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getVehicleFile(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFileRepresentation getVehicleFileById(Long vehicleId) {

		TypedQuery query = entityManager.createQuery("from VehicleFile vf where vf.entityId= " + vehicleId, VehicleFile.class);
		List<VehicleFile> vehicleFileList = query.getResultList();

		if (vehicleFileList.isEmpty()) {
			return null;
		}
		VehicleFileRepresentation vehicleFileRepresetation = new VehicleFileRepresentation();
		vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRepresetation, vehicleFileList.get(0));
		return vehicleFileRepresetation;
	}

	@Override
	public List<VehicleFile> getVehiclefileList(String getVehicleCondition) {
		TypedQuery<VehicleFile> query = entityManager.createQuery("From VehicleFile vf where " + getVehicleCondition, VehicleFile.class);

		List<VehicleFile> vehicleFileObj = query.getResultList();
		if (!vehicleFileObj.isEmpty())
			return vehicleFileObj;

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder#getAllReturedVehicleFiles()
	 */
	@Override
	public List<VehicleFileRepresentation> getAllReturedVehicleFiles() {

		TypedQuery<VehicleFileRepresentation> query = entityManager.createQuery(
				"SELECT new " + VehicleFileRepresentation.class.getName() + "(vf.entityId,vf.vehicle.counterMark,vf.vehicle.chasisNumber,vf.vehicle.registrationNumber,vf.vehicle.technicalCase.tvv.projectCodeFamily.projectCodeLabel,vf.vehicle.technicalCase.tvv.projectCodeFamily.vehicleFamilyLabel,vf.vehicleFileStatus.guiLabel,vf.typeOfTest.label,vf.vehicleFileStatus.label) FROM VehicleFile vf  "
						+ "WHERE vf.vehicleFileStatus.label = '" + Constants.VEHICLEFILESTATUS_TO_RETURN + "'",
				VehicleFileRepresentation.class);

		return query.getResultList();

	}
}

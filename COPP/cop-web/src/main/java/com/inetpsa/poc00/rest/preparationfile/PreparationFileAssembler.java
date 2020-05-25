package com.inetpsa.poc00.rest.preparationfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckListFactory;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureFactory;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileFactory;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.rest.codecisionparameters.CODecisionParametersFinder;
import com.inetpsa.poc00.rest.lambdadecisionparameters.LambdaDecisionParametersFinder;
import com.inetpsa.poc00.rest.opacitydecisionparameters.OpacityDecisionParametersFinder;
import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureAssembler;
import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureRepresentation;
import com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListAssembler;
import com.inetpsa.poc00.rest.preparationchecklist.PreparationCheckListRepresentation;
import com.inetpsa.poc00.rest.preparationresult.PreparationResultRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileAssembler;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class PreparationFileAssembler.
 */
public class PreparationFileAssembler extends BaseAssembler<PreparationFile, PreparationFileRepresentation> {

	/** The tdl label marque. */
	private static final String MARQUE = "MARQUE";

	/** The tdl label nhard. */
	private static final String NHARD = "N° HARD";

	/** The tdl label nsoft. */
	private static final String NSOFT = "N° SOFT";

	/** The Constant OPACITE. */
	private static final String OPACITE = "Opacité";

	/** The Constant OPACITE_REGLEMENTAIRE. */
	private static final String OPACITE_REGLEMENTAIRE = "Opacité réglementaire";

	/** The Constant CALCULATOR_MOTEUR. */
	private static final String CALCULATOR_MOTEUR = "Calculateur Moteur";

	/** The Constant CALCULATOR_BVA. */
	private static final String CALCULATOR_BVA = "Calculateur BVA";

	/** The Constant CONTROL_TECHNIQUE. */
	private static final String CONTROL_TECHNIQUE = "Contrôle Technique DIESEL";

	/** The prep check list factory. */
	@Inject
	PreparationCheckListFactory prepCheckListFactory;

	/** The prep check list assembler. */
	@Inject
	PreparationCheckListAssembler prepCheckListAssembler;

	/** The prep file struct assembler. */
	@Inject
	PreparationFileStructureAssembler prepFileStructAssembler;

	/** The prep file struct factory. */
	@Inject
	PreparationFileStructureFactory prepFileStructFactory;

	/** The vehicle file assembler. */
	@Inject
	VehicleFileAssembler vehicleFileAssembler;

	/** The vehicle file factory. */
	@Inject
	VehicleFileFactory vehicleFileFactory;

	/** The vehicle file repo. */
	@Inject
	VehicleFileRepository vehicleFileRepo;

	/** The co dec param finder. */
	@Inject
	CODecisionParametersFinder coDecParamFinder;

	/** The lambda dec param finder. */
	@Inject
	LambdaDecisionParametersFinder lambdaDecParamFinder;

	@Inject
	OpacityDecisionParametersFinder opacDecParamFinder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PreparationFileRepresentation targetDto, PreparationFile sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setModificationDate(sourceAggregate.getModificationDate());
		targetDto.setCreationDate(sourceAggregate.getCreationDate());

		setHeaderData(targetDto, sourceAggregate.getVehicleFile());

		List<PreparationCheckListRepresentation> prepClRepresentationList = new ArrayList<>();
		List<PreparationCheckList> preparationCheckList = sourceAggregate.getPreparationCheckList();

		if (preparationCheckList != null && !preparationCheckList.isEmpty()) {
			for (PreparationCheckList pcl : preparationCheckList) {
				PreparationCheckListRepresentation pclRep = new PreparationCheckListRepresentation();
				prepCheckListAssembler.doAssembleDtoFromAggregate(pclRep, pcl);
				prepClRepresentationList.add(pclRep);
			}
			targetDto.setPrepCheckListRepresentation(prepClRepresentationList);
		}

		if (sourceAggregate.getVehicleFile() != null) {
			VehicleFileRepresentation vfRep = new VehicleFileRepresentation();
			vehicleFileAssembler.doAssembleDtoFromAggregate(vfRep, sourceAggregate.getVehicleFile());
			targetDto.setVehicleFileRepresentation(vfRep);
		}

		if (sourceAggregate.getPrepFileStructure() != null) {
			PreparationFileStructureRepresentation pfStructRep = new PreparationFileStructureRepresentation();
			prepFileStructAssembler.doAssembleDtoFromAggregate(pfStructRep, sourceAggregate.getPrepFileStructure());
			targetDto.setPrepFileStructRepresentation(pfStructRep);
		}

		List<TvvValuedTvvDepTDL> tdl = sourceAggregate.getVehicleFile().getVehicle().getTechnicalCase().getTvv().getTvvValuedTvvDepTDL();

		if (tdl != null && !tdl.isEmpty()) {
			for (TvvValuedTvvDepTDL obj : tdl) {
				setTvvValuedTDLData(targetDto, obj);
			}
		}
	}

	/**
	 * Sets the header data.
	 * 
	 * @param targetDto the target dto
	 * @param sourceAggregate the source aggregate
	 */
	public void setHeaderData(PreparationFileRepresentation targetDto, VehicleFile sourceAggregate) {

		/* THIS METHOD CONTAINS NULL CHECKS ONLY FOR THOSE FIELDS WHICH ARE NOT MANDATORY */
		targetDto.setRegistrationNumber(sourceAggregate.getVehicle().getRegistrationNumber());
		targetDto.setVehicleFileStatusGui(sourceAggregate.getVehicleFileStatus().getGuiLabel());
		targetDto.setVehicleFileStatus(sourceAggregate.getVehicleFileStatus().getLabel());
		targetDto.setVin(sourceAggregate.getVehicle().getVin());
		targetDto.setTypeOfTest(sourceAggregate.getTypeOfTest().getLabel());
		targetDto.setModelYear(sourceAggregate.getVehicle().getModelYear());
		targetDto.setCounterMark(sourceAggregate.getVehicle().getCounterMark());
		targetDto.setChasisNumber(sourceAggregate.getVehicle().getChasisNumber());
		targetDto.setEsLabel(sourceAggregate.getVehicle().getTechnicalCase().getEmissionStandard().getEsLabel());
		targetDto.setBodyWorkLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getBodyWork().getLabel());
		targetDto.setEngineLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getEngine().getEngineLabel());
		targetDto.setPowerCV(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getEngine().getPowerCv());
		targetDto.setTorque(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getEngine().getTorque());
		targetDto.setPowerKW(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getEngine().getPowerKw());
		targetDto.setFuelInjectionType(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getFuelInjectionType().getLabel());
		if (sourceAggregate.getVehicle().getTechnicalCase().getTvv().getFuel().getFuelType() != null && sourceAggregate.getVehicle().getTechnicalCase().getTvv().getFuel().getFuelType().getFuelTypeLable() != null) {

			targetDto.setFuelTypeLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getFuel().getFuelType().getFuelTypeLable());
		}

		targetDto.setFuelLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getFuel().getLabel());
		targetDto.setGearBoxLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getGearBox().getLabel());
		targetDto.setProjectCodeFamilyLabel(sourceAggregate.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily().getVehicleFamilyLabel() + sourceAggregate.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily().getProjectCodeLabel());
		targetDto.setCountryLabel(null);

		Iterator<CarBrand> itr = sourceAggregate.getVehicle().getTechnicalCase().getTvv().getCarBrandSet().iterator();
		Set<String> carBrandMaker = new HashSet<>();

		while (itr.hasNext()) {
			carBrandMaker.add(itr.next().toString());
		}
		targetDto.setCarBrandMaker(carBrandMaker);

		if (targetDto.getFuelTypeLabel() != null) {
			setDecisionParameters(targetDto);
		}
		VehicleFileRepresentation vfRep = new VehicleFileRepresentation();
		vehicleFileAssembler.doAssembleDtoFromAggregate(vfRep, sourceAggregate);
		targetDto.setVehicleFileRepresentation(vfRep);

	}

	/**
	 * Sets the decision parameters.
	 * 
	 * @param targetDto the new decision parameters
	 */
	private void setDecisionParameters(PreparationFileRepresentation targetDto) {

		List<CODecisionParameters> coDecisionParam = coDecParamFinder.getCODecParamByFuelTypeLabel(targetDto.getFuelTypeLabel());

		if (coDecisionParam != null && !coDecisionParam.isEmpty()) {
			targetDto.setCoDecisionHigherLim(coDecisionParam.get(0).getHigherLimit());
			targetDto.setCoDecisionLowerLim(coDecisionParam.get(0).getLowerLimit());
		}

		List<LambdaDecisionParameters> lamdaDecisionParam = lambdaDecParamFinder.getLambdaDecisionParameterByLabel(targetDto.getFuelTypeLabel());
		if (lamdaDecisionParam != null && !lamdaDecisionParam.isEmpty()) {
			targetDto.setLambdaDecisionHigherLim(lamdaDecisionParam.get(0).getHigherLimit());
			targetDto.setLambdaDecisionLowerLim(lamdaDecisionParam.get(0).getLowerLimit());
		}

		List<OpacityDecisionParameters> opacDecisionParam = opacDecParamFinder.getOpacityDecisionParameterByLabel(targetDto.getFuelTypeLabel());
		if (opacDecisionParam != null && !opacDecisionParam.isEmpty()) {
			targetDto.setOpacDecHigherLim(opacDecisionParam.get(0).getHigherLimit());
			targetDto.setOpacDecLowerLim(opacDecisionParam.get(0).getLowerLimit());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PreparationFile targetAggregate, PreparationFileRepresentation sourceDto) {
		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setCreationDate(sourceDto.getCreationDate());

		List<PreparationCheckList> prepCheckList = new ArrayList<>();
		List<PreparationCheckListRepresentation> prepCheckListRepresentation = sourceDto.getPrepCheckListRepresentation();

		targetAggregate.setVehicleFile(vehicleFileRepo.load(sourceDto.getVehicleFileRepresentation().getEntityId()));

		if (sourceDto.getPrepFileStructRepresentation() != null) {

			PreparationFileStructure pfs = prepFileStructFactory.createPreparationFileStructure();
			prepFileStructAssembler.prepareEntityFromDto(pfs, sourceDto.getPrepFileStructRepresentation());
			targetAggregate.setPrepFileStructure(pfs);
		}

		if (prepCheckListRepresentation != null && !prepCheckListRepresentation.isEmpty()) {

			for (PreparationCheckListRepresentation pcl : prepCheckListRepresentation) {
				PreparationCheckList tempObj = prepCheckListFactory.createPreparationCheckList();
				tempObj.setPreparationFile(targetAggregate);
				prepCheckListAssembler.doMergeAggregateWithDto(tempObj, pcl);
				prepCheckList.add(tempObj);
			}

		}

		targetAggregate.setPreparationCheckList(prepCheckList);
	}

	/**
	 * Sets the tvv valued tdl data.
	 * 
	 * @param targetDto the target dto
	 * @param obj the obj
	 */
	private void setTvvValuedTDLData(PreparationFileRepresentation targetDto, TvvValuedTvvDepTDL obj) {

		if (CALCULATOR_MOTEUR.equalsIgnoreCase(obj.getLabel())) {
			getTvvVldTDLCalMot(obj, targetDto);
		} else if (CALCULATOR_BVA.equalsIgnoreCase(obj.getLabel())) {
			getTvvVldTDLCalBVA(obj, targetDto);
		} else if (CONTROL_TECHNIQUE.equalsIgnoreCase(obj.getLabel())) {
			getTvvVldTDLControlTech(obj, targetDto);
		}
	}

	/**
	 * Gets the tvv vld tdl control tech.
	 * 
	 * @param tvvVldDepTdl the tvv vld dep tdl
	 * @param targetDto the target dto
	 */
	private void getTvvVldTDLControlTech(TvvValuedTvvDepTDL tvvVldDepTdl, PreparationFileRepresentation targetDto) {

		for (TvvValuedTechData obj : tvvVldDepTdl.getValuedTechnicalData()) {

			if (OPACITE.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CONTROL_TECHNIQUE, OPACITE_REGLEMENTAIRE);
			}
		}
	}

	/**
	 * Gets the TVV valued TDL data.
	 * 
	 * @param tvvVldDepTdl the tvv vld dep tdl
	 * @param targetDto the target dto TVV VALUED TVV DEPENDENT TDL LABEL = "Calculateur Moteur"
	 */
	private void getTvvVldTDLCalMot(TvvValuedTvvDepTDL tvvVldDepTdl, PreparationFileRepresentation targetDto) {

		for (TvvValuedTechData obj : tvvVldDepTdl.getValuedTechnicalData()) {

			if (MARQUE.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_MOTEUR, MARQUE);

			} else if (NHARD.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_MOTEUR, NHARD);

			} else if (NSOFT.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_MOTEUR, NSOFT);
			}
		}

	}

	/**
	 * Gets the tvv valued tdl data bva.
	 * 
	 * @param tvvVldDepTdl the tvv vld dep tdl
	 * @param targetDto the target dto TVV VALUED TVV DEPENDENT TDL LABEL = "Calculateur BVA" NOTE : Written this method
	 *        to reduce execution of number of loop
	 */
	private void getTvvVldTDLCalBVA(TvvValuedTvvDepTDL tvvVldDepTdl, PreparationFileRepresentation targetDto) {

		for (TvvValuedTechData obj : tvvVldDepTdl.getValuedTechnicalData()) {

			if (MARQUE.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_BVA, MARQUE);

			} else if (NHARD.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_BVA, NHARD);

			} else if (NSOFT.equalsIgnoreCase(obj.getLabel())) {
				setTvvVldTDLInResults(obj.getValue(), targetDto, CALCULATOR_BVA, NSOFT);
			}
		}

	}

	/**
	 * Sets the tvv vld tdl in results.
	 * 
	 * @param tvvVldDepTdlValue the tvv vld dep tdl value
	 * @param targetDto the target dto
	 * @param checkListLabel the check list label
	 * @param resultLabel the result label
	 */
	private void setTvvVldTDLInResults(String tvvVldDepTdlValue, PreparationFileRepresentation targetDto, String checkListLabel, String resultLabel) {

		for (PreparationCheckListRepresentation pclR : targetDto.getPrepCheckListRepresentation()) {
			if (checkListLabel.equalsIgnoreCase(pclR.getLabel())) {
				setTvvVldData(tvvVldDepTdlValue, resultLabel, pclR);
				break;
			}
		}

	}

	/**
	 * Sets the tvv vld data.
	 * 
	 * @param tvvVldDepTdlValue the tvv vld dep tdl value
	 * @param resultLabel the result label
	 * @param pclR the pcl r
	 */
	private void setTvvVldData(String tvvVldDepTdlValue, String resultLabel, PreparationCheckListRepresentation pclR) {

		for (PreparationResultRepresentation prr : pclR.getPreparationResultList()) {

			if (resultLabel.equalsIgnoreCase(OPACITE_REGLEMENTAIRE)) {
				prr.setValue(prr.getValue());
			} else if (resultLabel.equalsIgnoreCase(prr.getLabel())) {
				prr.setTvvValuedTDLValue(tvvVldDepTdlValue);
			}

		}
	}

}

/*
 * Creation : Dec 28, 2016
 */
package com.inetpsa.poc00.infrastructure.emissionstandard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLFactory;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLFactory;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffFactory;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The Class EmissionStandardServiceImpl.
 */
public class EmissionStandardServiceImpl implements EmissionStandardService {

    /** The emission standard repository. */
    @Inject
    private EmissionStandardRepository emissionStandardRepository;

    /** The status repository. */
    @Inject
    private StatusRepository statusRepository;

    /** The emission standard factory. */
    @Inject
    private EmissionStandardFactory emissionStandardFactory;

    /** The category repository. */
    @Inject
    private CategoryRepository categoryRepository;

    /** The ems dep tdl repository. */
    @Inject
    private EmissionStdDepTDLRepository emsDepTDLRepository;

    /** The generic technical data repository. */
    @Inject
    private GenericTechDataRepository genericTechnicalDataRepository;

    /** The ems dep tdl factory. */
    @Inject
    private EmissionStdDepTDLFactory emsDepTDLFactory;

    /** The generic technical data factory. */
    @Inject
    private GenericTechDataFactory genericTechnicalDataFactory;

    /** The ems dep tcl factory. */
    @Inject
    private EmissionStdDepTCLFactory emsDepTCLFactory;

    /** The ems dep tcl repository. */
    @Inject
    private EmissionStdDepTCLRepository emsDepTCLRepository;

    /** The generic test condition factory. */
    @Inject
    private GenericTestConditionFactory genericTestConditionFactory;

    /** The generic test condition repository. */
    @Inject
    private GenericTestConditionRepository genericTestConditionRepository;

    /** The pollutant gas list finder. */
    @Inject
    private EmissionStdDepTDLRepository pollutantGasListFinder;

    /** The pollutant gas list factory. */
    @Inject
    private PollutantGasLimitListFactory pollutantGasListFactory;

    /** The pollutant gas list repository. */
    @Inject
    private PollutantGasLimitListRepository pollutantGasListRepository;

    /** The pollutant gas limit repository. */
    @Inject
    private PollutantGasLimitRepository pollutantGasLimitRepository;

    /** The pollutant gas factory. */
    @Inject
    private PollutantGasLimitFactory pollutantGasFactory;

    /** The factor coefficent list repository. */
    @Inject
    private FactorCoeffListRepository factorCoefficentListRepository;

    /** The factor coefficient list factory. */
    @Inject
    private FactorCoeffListFactory factorCoefficientListFactory;

    /** The factor coeffi repository. */
    @Inject
    private FactorCoeffRepository factorCoeffiRepository;

    /** The factor coeff label factory. */
    @Inject
    private FactorCoeffLabelFactory factorCoeffLabelFactory;

    /** The factor coefficent factory. */
    @Inject
    private FactorCoeffFactory factorCoefficentFactory;

    /** The fi type repository. */
    @Inject
    private FuelInjctnTypeRepository fiTypeRepository;

    /** The fuel repository. */
    @Inject
    private FuelRepository fuelRepository;

    /** The v technology repository. */
    @Inject
    private VehicleTechRepository vTechnologyRepository;

    /** The clasz repository. */
    @Inject
    private ClaszRepository claszRepository;

    /** The factor coefficient list repository. */
    @Inject
    private FactorCoeffListRepository factorCoefficientListRepository;

    /** The trace service. */
    @Inject
    TraceabilityService traceService;

    /** The logger. */
    @Logging
    Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.emissionstandard.EmissionStandardService#createEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    // @Override
    // @Transactional
    // @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard createEmissionStandard(EmissionStandard emissionStandard) {
        emissionStandard.setVersion("1.0");

        Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.DRAFT);
        if (status == null)
            return null;

        emissionStandard.setStatus(status);

        return emissionStandardRepository.saveEmissionStandard(emissionStandard);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.emissionstandard.EmissionStandardService#copyEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard copyEmissionStandard(EmissionStandard emissionStandard) {
        EmissionStandard oldObj = emissionStandardRepository.loadEmission(emissionStandard.getEntityId());

        EmissionStandard newOBJ = emissionStandardFactory.createEmissonStandard(emissionStandard.getEsLabel(), emissionStandard.getDescription(),
                "1.0");

        Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.DRAFT);
        newOBJ.setStatus(status);

        newOBJ = emissionStandardRepository.saveEmissionStandard(newOBJ);

        /* TECHNICAL DATA LIST STARTS */
        List<EmissionStdDepTDL> tDlSet;
        tDlSet = copyESDepTDL(oldObj.getEntityId(), newOBJ);
        newOBJ.setEmissionStdDepTDLists(tDlSet);
        /* TECHNICAL DATA LIST END */

        /* TEST CONDITION LIST STARTS */
        List<EmissionStdDepTCL> tClSet;
        tClSet = copyESDepTCL(oldObj.getEntityId(), newOBJ);
        newOBJ.setEmissionStdDepTCLists(tClSet);
        /* TEST CONDITION LIST END */

        /* POLLUTANT GAS LIMIT LIST START */
        List<PollutantGasLimitList> pglSet;
        pglSet = copyESDepPGL(oldObj.getEntityId(), newOBJ);
        newOBJ.setPgLists(pglSet);
        /* POLLUTANT GAS LIMIT LIST END */

        /* FACTOR COEFFICIENT LIST START */
        List<FactorCoefficentList> fcllSet;
        fcllSet = copyESDepFCL(oldObj.getEntityId(), newOBJ);
        newOBJ.setFclists(fcllSet);
        /* FACTOR COEFFICIENT LIST END */

        Set<Category> categorySet = new HashSet<>();
        for (Category category : categoryRepository.getAllCategoriesForCopiedES(oldObj.getEntityId())) {
            categorySet.add(category);
        }
        newOBJ.setCategories(categorySet);

        Set<FuelInjectionType> fiTypeSet = new HashSet<>();
        for (FuelInjectionType fiType : oldObj.getFuelInjectionTypes()) {
            fiTypeSet.add(fiType);
        }
        newOBJ.setFuelInjectionTypes(fiTypeSet);

        Set<Fuel> fuelSet = new HashSet<>();
        for (Fuel fuel : oldObj.getFuels()) {
            fuelSet.add(fuel);
        }
        newOBJ.setFuels(fuelSet);

        Set<VehicleTechnology> vehicleTechnologySet = new HashSet<>();
        for (VehicleTechnology fiType : oldObj.getVehicleTechnologySet()) {
            vehicleTechnologySet.add(fiType);
        }
        newOBJ.setVehicleTechnologySet(vehicleTechnologySet);

        return emissionStandardRepository.saveEmissionStandard(newOBJ);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.emissionstandard.EmissionStandardService#changeEmissionStandardVersion(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStandard changeEmissionStandardVersion(Long esEntityId) {
        EmissionStandard emissionStandard = emissionStandardRepository.load(esEntityId);

        Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.VALID);

        emissionStandard.setStatus(status);
        return emissionStandardRepository.saveEmissionStandard(emissionStandard);
    }

    /**
     * Copy es dep tdl.
     * 
     * @param esEntityId the es entity id
     * @param newObj the new obj
     * @return the list
     */
    @Override
    public List<EmissionStdDepTDL> copyESDepTDL(Long esEntityId, EmissionStandard newObj) {

        List<EmissionStdDepTDL> tdlSet = new ArrayList<>();
        for (EmissionStdDepTDL emsDepTDL : emsDepTDLRepository.getEmissionStandardDepLists(esEntityId)) {

            EmissionStdDepTDL newTDLOBJ = emsDepTDLFactory.createEmissonStdDepTDL(emsDepTDL.getLabel(), emsDepTDL.getDescription(),
                    emsDepTDL.getVersion());
            newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);
            newTDLOBJ.setGenericTechnicalData(new ArrayList<GenericTechnicalData>());
            for (GenericTechnicalData data : genericTechnicalDataRepository.getAllGenericDataForEmsList(emsDepTDL.getEntityId())) {

                GenericTechnicalData dataToSave = genericTechnicalDataFactory.createGenericTechData(data.getDataType(), data.getDefaultValue(),
                        data.getLabel(), data.getUnit().getValue());
                dataToSave.setEmsDepTDL(newTDLOBJ);
                genericTechnicalDataRepository.saveGenericTechData(dataToSave);

                newTDLOBJ.getGenericTechnicalData().add(dataToSave);

            }
            newTDLOBJ.setEmissionStandard(newObj);
            newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);
            tdlSet.add(newTDLOBJ);
        }

        return tdlSet;
    }

    /**
     * Copy es dep tcl.
     * 
     * @param esEntityId the es entity id
     * @param newObj the new obj
     * @return the list
     */
    @Override
    public List<EmissionStdDepTCL> copyESDepTCL(Long esEntityId, EmissionStandard newObj) {

        List<EmissionStdDepTCL> tClSet = new ArrayList<>();
        for (EmissionStdDepTCL emsDepTCL : emsDepTCLRepository.getEmissionStandardDepLists(esEntityId)) {

            EmissionStdDepTCL newTCLOBJ = emsDepTCLFactory.createEmissonStdDepTCL(emsDepTCL.getLabel(), emsDepTCL.getDescription(),
                    emsDepTCL.getVersion());
            newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);
            newTCLOBJ.setGenericTestCondition(new ArrayList<GenericTestCondition>());
            for (GenericTestCondition data : genericTestConditionRepository.getAllGenericConditionsForEmsList(emsDepTCL.getEntityId())) {

                GenericTestCondition dataToSave = genericTestConditionFactory.createGenericTestCondition(data.getDataType(), data.getDefaultValue(),
                        data.getLabel(), data.getUnit().getValue());
                dataToSave.setEmsDepTCL(newTCLOBJ);
                genericTestConditionRepository.saveGenericTestCondition(dataToSave);

                newTCLOBJ.getGenericTestCondition().add(dataToSave);

            }
            newTCLOBJ.setEmissionStandard(newObj);
            newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);

            tClSet.add(newTCLOBJ);
        }

        return tClSet;

    }

    /**
     * Copy es dep pgl.
     * 
     * @param esEntityId the es entity id
     * @param newObj the new obj
     * @return the list
     */
    @Override
    public List<PollutantGasLimitList> copyESDepPGL(Long esEntityId, EmissionStandard newObj) {

        List<PollutantGasLimitList> pglSet = new ArrayList<>();
        for (PollutantGasLimitList pollutantGasList : pollutantGasListRepository.getEmissionStandardDepLists(esEntityId)) {

            PollutantGasLimitList newpGList = pollutantGasListFactory.createPollutantGasLimitList(pollutantGasList.getLabel(),
                    pollutantGasList.getDescription(), pollutantGasList.getVersion());
            newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);
            newpGList.setPollutantGasLimit(new ArrayList<PollutantGasLimit>());
            // set all pollutant or Gas limits to pollutant or gas objects
            for (PollutantGasLimit data : pollutantGasLimitRepository.getAllPollutantsForDepList(pollutantGasList.getEntityId())) {

                PollutantGasLimit dataToSave = pollutantGasFactory.createPollutantGasLimits(data.getPgLabel(), data.getMaxDValRule(),
                        data.getMaxDValue(), data.getMinDValRule(), data.getMinDValue());
                dataToSave.setPollutantGasLimitList(newpGList);
                pollutantGasLimitRepository.savePollutantGasLimit(dataToSave);

                newpGList.getPollutantGasLimit().add(dataToSave);

            }

            Set<Category> categorySet = new HashSet<>();
            for (Category category : categoryRepository.getAllCategoriesForCopiedPGList(pollutantGasList.getEntityId())) {
                categorySet.add(category);
            }
            newpGList.setCategories(categorySet);

            Set<FuelInjectionType> fiTypeSet = new HashSet<>();
            for (FuelInjectionType fiType : fiTypeRepository.getAllFITypeForCopiedPGList(pollutantGasList.getEntityId())) {
                fiTypeSet.add(fiType);
            }
            newpGList.setFuelInjectionTypes(fiTypeSet);

            Set<Fuel> fuelSet = new HashSet<>();
            for (Fuel fuel : fuelRepository.getAllFuelForCopiedPGList(pollutantGasList.getEntityId())) {
                fuelSet.add(fuel);
            }
            newpGList.setFuels(fuelSet);

            Set<VehicleTechnology> vehicleTechnologySet = new HashSet<>();
            for (VehicleTechnology fiType : vTechnologyRepository.getAllVTForCopiedPGList(pollutantGasList.getEntityId())) {
                vehicleTechnologySet.add(fiType);
            }
            newpGList.setVehicleTechnologySet(vehicleTechnologySet);

            Set<Clasz> claszSet = new HashSet<>();
            for (Clasz clasz : claszRepository.getAllClaszForCopiedPGList(pollutantGasList.getEntityId())) {
                claszSet.add(clasz);
            }
            newpGList.setClasses(claszSet);

            newpGList.setEmissionStandard(newObj);
            newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);
            pglSet.add(newpGList);
        }

        return pglSet;

    }

    /**
     * Copy es dep fcl.
     * 
     * @param esEntityId the es entity id
     * @param newObj the new obj
     * @return the list
     */
    @Override
    public List<FactorCoefficentList> copyESDepFCL(Long esEntityId, EmissionStandard newObj) {
        List<FactorCoefficentList> fcllSet = new ArrayList<>();
        for (FactorCoefficentList factorCoefficientList : factorCoefficentListRepository.getEmissionStandardDepLists(esEntityId)) {

            Set<FactorCoeffApplicationType> fCoeffAppType = new HashSet<>();
            fCoeffAppType.addAll(factorCoefficientList.getFcApplicationTypes());

            FactorCoefficentList newFCList = factorCoefficientListFactory.createFactorCoeffList(factorCoefficientList.getLabel(),
                    factorCoefficientList.getDescription(), factorCoefficientList.getVersion(), fCoeffAppType);
            newFCList = factorCoefficentListRepository.saveFactorCoeffList(newFCList);
            newFCList.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());
            // set factor and coefficients in newly created list
            for (FactorCoefficents data : factorCoeffiRepository.getAllFActorsForDepList(factorCoefficientList.getEntityId())) {
                FactorCoefficents dataToSave;
                if (data.getFclabel() != null) {
                    FactorCoefficentsLabel fcLabel = factorCoeffLabelFactory.createFactorCoeffLabel(data.getFclabel().getLabel());
                    dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), fcLabel, data.getPgLabel());
                } else {
                    dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), data.getPgLabel());
                }

                dataToSave.setFcList(newFCList);
                factorCoeffiRepository.saveFactorCoefficents(dataToSave);

                newFCList.getFactorOrCoeffiecients().add(dataToSave);

            }

            Set<Category> categorySet = new HashSet<>();
            for (Category category : categoryRepository.getAllCategoriesForCopiedFCList(factorCoefficientList.getEntityId())) {
                categorySet.add(category);
            }
            newFCList.setCategories(categorySet);

            List<FuelInjectionType> fiTypeSet = new ArrayList<>();
            for (FuelInjectionType fiType : fiTypeRepository.getAllFITypeForCopiedFCList(factorCoefficientList.getEntityId())) {
                fiTypeSet.add(fiType);
            }
            newFCList.setFuelInjectionTypes(fiTypeSet);

            List<Fuel> fuelSet = new ArrayList<>();
            for (Fuel fuel : fuelRepository.getAllFuelForCopiedFCList(factorCoefficientList.getEntityId())) {
                fuelSet.add(fuel);
            }
            newFCList.setFuels(fuelSet);

            List<VehicleTechnology> vehicleTechnologySet = new ArrayList<>();
            for (VehicleTechnology fiType : vTechnologyRepository.getAllVTForCopiedFCList(factorCoefficientList.getEntityId())) {
                vehicleTechnologySet.add(fiType);
            }
            newFCList.setVehicleTechnologySet(vehicleTechnologySet);

            Set<Clasz> claszSet = new HashSet<>();
            for (Clasz clasz : claszRepository.getAllClaszForCopiedFCList(factorCoefficientList.getEntityId())) {
                claszSet.add(clasz);
            }
            newFCList.setClasses(claszSet);

            newFCList.setEmissionStandard(newObj);
            newFCList = factorCoefficientListRepository.saveFactorCoeffList(newFCList);
            fcllSet.add(newFCList);
        }

        return fcllSet;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.emissionstandard.EmissionStandardService#saveEmissionStandard(com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    public EmissionStandard saveEmissionStandard(EmissionStandard emissionStandard) {
        EmissionStandard esResponse;
        List<EmissionStandard> emissionStandardData = emissionStandardRepository.getEmissionStandardByLabel(emissionStandard.getEsLabel());
        if (!emissionStandardData.isEmpty()) {

            if (emissionStandardData.get(0).getEntityId() == emissionStandard.getEntityId()) {
                // update
                EmissionStandard oldEs = emissionStandardRepository.loadEmission(emissionStandard.getEntityId());
                traceService.historyForUpdate(oldEs, emissionStandard, ConstantsApp.COMMONGENOME_SCREEN_ID);
                esResponse = emissionStandardRepository.persistEmissionStandard(emissionStandard);

            } else {
                return null;
            }
        } else if (emissionStandard.getEntityId() == null) {
            // save
            emissionStandard.setVersion("1.0");

            Status status = statusRepository.getStatusForEmissionStandard(ConstantsApp.DRAFT);
            if (status == null)
                return null;

            emissionStandard.setStatus(status);

            esResponse = emissionStandardRepository.saveEmissionStd(emissionStandard);

            traceService.historyForSave(esResponse, ConstantsApp.COMMONGENOME_SCREEN_ID);

        } else {
            // update
            EmissionStandard oldEs = emissionStandardRepository.load(emissionStandard.getEntityId());
            traceService.historyForUpdate(oldEs, emissionStandard, ConstantsApp.COMMONGENOME_SCREEN_ID);
            esResponse = emissionStandardRepository.persistEmissionStandard(emissionStandard);
        }
        return esResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.emissionstandard.EmissionStandardService#deleteEmissionStandard(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public boolean deleteEmissionStandard(Long emsId) {
        EmissionStandard objToDelete = emissionStandardRepository.loadEmission(emsId);

        if (objToDelete.getRegulationGrp().isEmpty() && objToDelete.getTechnicalCases().isEmpty() && objToDelete.getPgLists().isEmpty()
                && objToDelete.getFclists().isEmpty()) {
            int deletedrows = emissionStandardRepository.deleteAll(emsId);
            if (deletedrows > 0) {
                logger.info("Sucessfully deleted EmissionStandard data");
                traceService.historyForDelete(objToDelete, ConstantsApp.COMMONGENOME_SCREEN_ID);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates the new ems version.
     * 
     * @param emissionStandard the emission standard
     * @return the emission standard
     */
    @Override
    public EmissionStandard createNewEMSVersion(EmissionStandard emissionStandard) {

        Double version = emissionStandardRepository.getMaxVersionForLabel(emissionStandard.getEsLabel()) + 1;
        emissionStandard.setVersion(version.toString());
        EmissionStandard newOBJ = emissionStandardFactory.createEmissonStandard(emissionStandard.getEsLabel(), emissionStandard.getDescription(),
                emissionStandard.getVersion());
        newOBJ.setCategories(emissionStandard.getCategories());
        newOBJ.setFuelInjectionTypes(emissionStandard.getFuelInjectionTypes());
        newOBJ.setStatus(emissionStandard.getStatus());
        newOBJ.setFuels(emissionStandard.getFuels());
        newOBJ.setVehicleTechnologySet(emissionStandard.getVehicleTechnologySet());

        return newOBJ;
    }
}

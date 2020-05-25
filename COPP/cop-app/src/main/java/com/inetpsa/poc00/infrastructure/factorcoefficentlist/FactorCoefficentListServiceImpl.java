/**
 * 
 */
package com.inetpsa.poc00.infrastructure.factorcoefficentlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffFactory;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoeffRepository;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Class FactorCoefficentListServiceImpl.
 * 
 * @author ankurp
 */
public class FactorCoefficentListServiceImpl implements FactorCoefficentListService {

    /** The history service. */
    @Inject
    EmissionStandardService esService;

    /** The emission standard repository. */
    @Inject
    private EmissionStandardRepository emissionStandardRepository;

    /** The factor coefficent repository. */
    @Inject
    private FactorCoeffListRepository factorCoefficientListRepository;

    /** The factor coefficient list factory. */
    @Inject
    private FactorCoeffListFactory factorCoefficientListFactory;

    /** The pollutant gas label factory. */
    @Inject
    private PollutantGasLabelFactory pollutantGasLabelFactory;

    /** The factor coefficents label factory. */
    @Inject
    private FactorCoeffLabelFactory factorCoefficentsLabelFactory;

    /** The factor coefficent factory. */
    @Inject
    private FactorCoeffFactory factorCoefficentFactory;

    /** The history service. */
    @Inject
    TraceabilityService historyService;

    /** The factor coeffi repository. */
    @Inject
    private FactorCoeffRepository factorCoeffiRepository;

    /** The es repo. */
    @Inject
    EmissionStandardRepository esRepo;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService#updateEmsDepFCL(com.inetpsa.poc00.domain.factorcoeffList.
     * FactorCoefficentList, com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList updateEmsDepFCL(FactorCoefficentList factorCoefficientList, EmissionStandard newVersionObject) {

        Double majoMinorVersion = factorCoefficientListRepository.getMaxVersionForLabel(factorCoefficientList.getLabel()) + 1;

        FactorCoefficentList newFCList = factorCoefficientListFactory.createFactorCoeffList(factorCoefficientList.getLabel(),
                factorCoefficientList.getDescription(), majoMinorVersion.toString());

        newFCList.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());

        if (factorCoefficientList.getFactorOrCoeffiecients() != null) {
            for (FactorCoefficents data : factorCoefficientList.getFactorOrCoeffiecients()) {
                if (!"TRUE".equalsIgnoreCase(data.getIsDeleted())) {

                    PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
                    pgLabel.setEntityId(data.getPgLabel().getEntityId());
                    pgLabel.setLabel(data.getPgLabel().getLabel());

                    FactorCoefficents dataToSave;

                    if (data.getFclabel() != null) {
                        FactorCoefficentsLabel fcLabel = factorCoefficentsLabelFactory.createFactorCoeffLabel();
                        fcLabel.setEntityId(data.getFclabel().getEntityId());
                        fcLabel.setLabel(data.getFclabel().getLabel());

                        dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), fcLabel, pgLabel);

                    } else {
                        dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), pgLabel);
                    }
                    dataToSave.setFcList(newFCList);
                    newFCList.getFactorOrCoeffiecients().add(dataToSave);
                }
            }
        }

        newFCList.setCategories(factorCoefficientList.getCategories());
        newFCList.setFuelInjectionTypes(factorCoefficientList.getFuelInjectionTypes());
        newFCList.setFuels(factorCoefficientList.getFuels());
        newFCList.setVehicleTechnologySet(factorCoefficientList.getVehicleTechnologySet());
        newFCList.setClasses(factorCoefficientList.getClasses());
        newFCList.setFcApplicationType(factorCoefficientList.getFcApplicationTypes());

        // ------------------------------CHANGE Emission Standard VERSION AS WELL----------------------
        newFCList = factorCoefficientListRepository.saveFactorCoeffList(newFCList);
        historyService.historyForSave(newFCList, ConstantsApp.EMS_DEPENDENT_FCL_SCREEN);
        newVersionObject.getFclists().add(newFCList);

        newFCList.setEmissionStandard(newVersionObject);
        factorCoefficientListRepository.saveFactorCoeffList(newFCList);

        return newFCList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService#createEmsDepFCL(com.inetpsa.poc00.domain.es.EmissionStandard,
     * com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList createEmsDepFCL(EmissionStandard newVersionObject, FactorCoefficentList factorCoefficientList) {

        factorCoefficientList.setEmissionStandard(newVersionObject);

        factorCoefficientListRepository.saveFactorCoeffList(factorCoefficientList);

        factorCoefficientList.setVersion("1.0");

        historyService.historyForSave(factorCoefficientList, ConstantsApp.EMS_DEPENDENT_FCL_SCREEN);

        return factorCoefficientList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.factorcoefficentlist.FactorCoefficentListService#deleteEmsDepFCL(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteEmsDepFCL(Long entityId) {

        factorCoefficientListRepository.deletefactorCoefficientList(entityId);

        historyService.historyForDelete(entityId, ConstantsApp.EMS_DEPENDENT_FCL_SCREEN);

    }

    /**
     * Gets the emission standard object.
     * 
     * @param oldEsEntityId the old es entity id
     * @param changeEmsVersion the change ems version
     * @param emissionStandard the emission standard
     * @return the emission standard object
     */
    @Override
    public EmissionStandard getEmissionStandardObject(long oldEsEntityId, boolean changeEmsVersion, EmissionStandard emissionStandard) {

        EmissionStandard newVersionObject;

        if (changeEmsVersion) {

            newVersionObject = esService.createNewEMSVersion(emissionStandard);
            newVersionObject.setFclists(new ArrayList<FactorCoefficentList>());

            newVersionObject = esRepo.saveEmissionStandard(newVersionObject);

            /****** START : COPY OTHER LIST in New Emission Standard Version ******/
            List<EmissionStdDepTDL> tdlSet = esService.copyESDepTDL(oldEsEntityId, newVersionObject);
            newVersionObject.setEmissionStdDepTDLists(tdlSet);

            List<EmissionStdDepTCL> tclSet = esService.copyESDepTCL(oldEsEntityId, newVersionObject);
            newVersionObject.setEmissionStdDepTCLists(tclSet);

            List<PollutantGasLimitList> pglSet = esService.copyESDepPGL(oldEsEntityId, newVersionObject);
            newVersionObject.setPgLists(pglSet);
            /****** END : COPY OTHER LIST in New Emission Standard Version ******/

        } else {

            newVersionObject = emissionStandardRepository.load(oldEsEntityId);
            newVersionObject.setFclists(new ArrayList<FactorCoefficentList>());
        }

        return newVersionObject;
    }

    /**
     * Copy single fcl.
     * 
     * @param factorCoefficientList the factor coefficient list
     * @param newVersionObject the new version object
     * @return the factor coefficent list
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList copySingleFCL(FactorCoefficentList factorCoefficientList, EmissionStandard newVersionObject) {

        Set<FactorCoeffApplicationType> fCoeffAppType = new HashSet<>();
        fCoeffAppType.addAll(factorCoefficientList.getFcApplicationTypes());

        FactorCoefficentList newFCList = factorCoefficientListFactory.createFactorCoeffList(factorCoefficientList.getLabel(),
                factorCoefficientList.getDescription(), factorCoefficientList.getVersion(), fCoeffAppType);
        newFCList = factorCoefficientListRepository.saveFactorCoeffList(newFCList);

        newFCList.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());

        if (factorCoefficientList.getFactorOrCoeffiecients() != null && !factorCoefficientList.getFactorOrCoeffiecients().isEmpty()) {
            for (FactorCoefficents data : factorCoefficientList.getFactorOrCoeffiecients()) {
                FactorCoefficents dataToSave;
                if (data.getFclabel() != null) {
                    FactorCoefficentsLabel fcLabel = factorCoefficentsLabelFactory.createFactorCoeffLabel(data.getFclabel().getLabel());
                    dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), fcLabel, data.getPgLabel());
                } else {
                    dataToSave = factorCoefficentFactory.createFactorCoefficents(data.getDefaultValue(), data.getPgLabel());
                }

                dataToSave.setFcList(newFCList);
                factorCoeffiRepository.saveFactorCoefficents(dataToSave);

                newFCList.getFactorOrCoeffiecients().add(dataToSave);

            }
        }

        newFCList.setCategories(factorCoefficientList.getCategories());

        newFCList.setFuelInjectionTypes(factorCoefficientList.getFuelInjectionTypes());

        newFCList.setFuels(factorCoefficientList.getFuels());

        newFCList.setVehicleTechnologySet(factorCoefficientList.getVehicleTechnologySet());

        newFCList.setClasses(factorCoefficientList.getClasses());

        newFCList.setEmissionStandard(newVersionObject);

        newFCList = factorCoefficientListRepository.saveFactorCoeffList(newFCList);

        return newFCList;
    }

    /**
     * Gets the all used pg labels.
     *
     * @param emissionStdId the emission std id
     * @return the all used pg labels
     */
    public List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId) {
        List<PollutantGasLabel> pgLabelSet = factorCoefficientListRepository.getAllUsedPgLabels(emissionStdId);
        if (pgLabelSet != null) {

            return pgLabelSet;
        }

        return Collections.emptyList();

    }
}

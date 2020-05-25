package com.inetpsa.poc00.infrastructure.emsdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLFactory;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Class EmsDepTDLServiceImpl.
 */
public class EmsDepTCLServiceImpl implements EmsDepTCLService {

    /** The history service. */
    @Inject
    TraceabilityService historyService;

    /** The history service. */
    @Inject
    EmissionStandardService esService;

    /** The emission standard repository. */
    @Inject
    EmissionStandardRepository emissionStandardRepository;

    /** The ems dep tdl repository. */
    @Inject
    private EmissionStdDepTCLRepository emsDepTCLRepository;

    /** The ems dep tdl factory. */
    @Inject
    private EmissionStdDepTCLFactory emsDepTCLFactory;

    /** The generic test condition factory. */
    @Inject
    private GenericTestConditionFactory genericTestConditionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService#updateEmsDepTCL(com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL,
     * com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTCL updateEmsDepTCL(EmissionStdDepTCL emsDepTCL, EmissionStandard newVersionObject) {

        Double majoMinorVersion = emsDepTCLRepository.getMaxVersionForLabel(emsDepTCL.getLabel()) + 1;

        EmissionStdDepTCL newTCLOBJ = emsDepTCLFactory.createEmissonStdDepTCL(emsDepTCL.getLabel(), emsDepTCL.getDescription(),
                majoMinorVersion.toString());

        newTCLOBJ.setGenericTestCondition(new ArrayList<GenericTestCondition>());

        if (emsDepTCL.getGenericTestCondition() != null) {
            for (GenericTestCondition data : emsDepTCL.getGenericTestCondition()) {
                if (!"TRUE".equalsIgnoreCase(data.getIsDeleted())) {
                    GenericTestCondition dataToSave = genericTestConditionFactory.createGenericTestCondition(data.getDataType(),
                            data.getDefaultValue(), data.getLabel(), data.getUnit().getValue());
                    dataToSave.setEmsDepTCL(newTCLOBJ);
                    newTCLOBJ.getGenericTestCondition().add(dataToSave);
                }
            }
        }

        newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);
        historyService.historyForSave(newTCLOBJ, ConstantsApp.EMS_DEPENDENT_TDL_SCREEN);

        emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);

        newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);

        if (newVersionObject != null) {
            newVersionObject.getEmissionStdDepTCLists().add(newTCLOBJ);
        }
        newTCLOBJ.setEmissionStandard(newVersionObject);
        emissionStandardRepository.saveEmissionStandard(newVersionObject);

        return newTCLOBJ;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService#getEmissionStandardObject(long, boolean,
     * com.inetpsa.poc00.domain.es.EmissionStandard)
     */
    @Override
    public EmissionStandard getEmissionStandardObject(long oldEsEntityId, boolean changeEmsVersion, EmissionStandard emissionStandard) {

        EmissionStandard newVersionObject;

        if (changeEmsVersion) {

            newVersionObject = esService.createNewEMSVersion(emissionStandard);
            newVersionObject.setEmissionStdDepTCLists(new ArrayList<EmissionStdDepTCL>());

            newVersionObject = emissionStandardRepository.saveEmissionStandard(newVersionObject);

            /****** START : COPY OTHER LIST in New Emission Standard Version ******/
            List<EmissionStdDepTDL> tdlSet = esService.copyESDepTDL(oldEsEntityId, newVersionObject);
            newVersionObject.setEmissionStdDepTDLists(tdlSet);

            List<PollutantGasLimitList> pglSet = esService.copyESDepPGL(oldEsEntityId, newVersionObject);
            newVersionObject.setPgLists(pglSet);

            List<FactorCoefficentList> fclSet = esService.copyESDepFCL(oldEsEntityId, newVersionObject);
            newVersionObject.setFclists(fclSet);
            /****** END : COPY OTHER LIST in New Emission Standard Version ******/

        } else {
            newVersionObject = emissionStandardRepository.load(oldEsEntityId);
            newVersionObject.setEmissionStdDepTCLists(new ArrayList<EmissionStdDepTCL>());

        }
        return newVersionObject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService#createEmsDepTCL(com.inetpsa.poc00.domain.es.EmissionStandard,
     * com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTCL createEmsDepTCL(EmissionStandard newVersionObject, EmissionStdDepTCL emsDepTCL) {

        if (newVersionObject != null) {
            emsDepTCL.setEmissionStandard(newVersionObject);
        }

        emsDepTCLRepository.saveEmissonStdDepTCL(emsDepTCL);
        emsDepTCL.setVersion("1.0");
        historyService.historyForSave(emsDepTCL, ConstantsApp.EMS_DEPENDENT_TCL_SCREEN);

        return emsDepTCL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService#deleteEmsDepTCL(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteEmsDepTCL(Long entityId) {

        emsDepTCLRepository.deleteEmsDepTCL(entityId);

        historyService.historyForDelete(entityId, ConstantsApp.EMS_DEPENDENT_TCL_SCREEN);

    }

    /**
     * Copy single tcl.
     * 
     * @param emsDepTCL the ems dep tcl
     * @param newVersionObject the new version object
     * @return the emission std dep tcl
     */
    @Override
    public EmissionStdDepTCL copySingleTCL(EmissionStdDepTCL emsDepTCL, EmissionStandard newVersionObject) {

        EmissionStdDepTCL newTCLOBJ = emsDepTCLFactory.createEmissonStdDepTCL(emsDepTCL.getLabel(), emsDepTCL.getDescription(),
                emsDepTCL.getVersion());
        newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);
        newTCLOBJ.setGenericTestCondition(new ArrayList<GenericTestCondition>());
        if (emsDepTCL.getGenericTestCondition() != null) {
            for (GenericTestCondition data : emsDepTCL.getGenericTestCondition()) {

                GenericTestCondition dataToSave = genericTestConditionFactory.createGenericTestCondition(data.getDataType(), data.getDefaultValue(),
                        data.getLabel(), data.getUnit().getValue());
                dataToSave.setEmsDepTCL(newTCLOBJ);

                newTCLOBJ.getGenericTestCondition().add(dataToSave);

            }
        }
        newTCLOBJ.setEmissionStandard(newVersionObject);
        newTCLOBJ = emsDepTCLRepository.saveEmissonStdDepTCL(newTCLOBJ);

        return newTCLOBJ;
    }

}

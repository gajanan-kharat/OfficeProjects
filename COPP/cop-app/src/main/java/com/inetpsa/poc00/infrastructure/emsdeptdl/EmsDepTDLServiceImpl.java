package com.inetpsa.poc00.infrastructure.emsdeptdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLFactory;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Class EmsDepTDLServiceImpl.
 */
public class EmsDepTDLServiceImpl implements EmsDepTDLService {

    /** The ems dep tdl repository. */
    @Inject
    private EmissionStdDepTDLRepository emsDepTDLRepository;

    /** The ems dep tdl factory. */
    @Inject
    private EmissionStdDepTDLFactory emsDepTDLFactory;

    /** The generic technical data factory. */
    @Inject
    private GenericTechDataFactory genericTechnicalDataFactory;

    /** The emission standard repository. */
    @Inject
    EmissionStandardRepository emissionStandardRepository;

    /** The factor coeff label factory. */
    @Inject
    FactorCoeffLabelFactory factorCoeffLabelFactory;

    /** The history service. */
    @Inject
    TraceabilityService historyService;

    /** The history service. */
    @Inject
    EmissionStandardService esService;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService#updateEmsDepTDL()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTDL updateEmsDepTDL(EmissionStdDepTDL emsDepTDL, EmissionStandard newVersionObject) {

        Double majoMinorVersion = emsDepTDLRepository.getMaxVersionForLabel(emsDepTDL.getLabel()) + 1;

        EmissionStdDepTDL newTDLOBJ = emsDepTDLFactory.createEmissonStdDepTDL(emsDepTDL.getLabel(), emsDepTDL.getDescription(),
                majoMinorVersion.toString());

        newTDLOBJ.setGenericTechnicalData(new ArrayList<GenericTechnicalData>());

        if (emsDepTDL != null && emsDepTDL.getGenericTechnicalData() != null) {
            for (GenericTechnicalData data : emsDepTDL.getGenericTechnicalData()) {
                if (!"TRUE".equalsIgnoreCase(data.getIsDeleted())) {
                    GenericTechnicalData dataToSave = genericTechnicalDataFactory.createGenericTechData(data.getDataType(), data.getDefaultValue(),
                            data.getLabel(), data.getUnit().getValue());
                    dataToSave.setEmsDepTDL(newTDLOBJ);
                    newTDLOBJ.getGenericTechnicalData().add(dataToSave);
                }
            }
        }

        newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);
        historyService.historyForSave(newTDLOBJ, ConstantsApp.EMS_DEPENDENT_TDL_SCREEN);

        emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);

        newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);

        if (newVersionObject != null) {
            newVersionObject.getEmissionStdDepTDLists().add(newTDLOBJ);
        }
        newTDLOBJ.setEmissionStandard(newVersionObject);
        emissionStandardRepository.saveEmissionStandard(newVersionObject);

        return newTDLOBJ;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService#createEmsDepTDL(com.inetpsa.poc00.domain.es.EmissionStandard,
     * com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTDL createEmsDepTDL(EmissionStandard newVersionObject, EmissionStdDepTDL emsDepTDL) {

        if (newVersionObject != null) {
            emsDepTDL.setEmissionStandard(newVersionObject);
        }

        emsDepTDLRepository.saveEmissonStdDepTDL(emsDepTDL);
        emsDepTDL.setVersion("1.0");
        historyService.historyForSave(emsDepTDL, ConstantsApp.EMS_DEPENDENT_TDL_SCREEN);

        return emsDepTDL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService#deleteEmsDepTDL()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteEmsDepTDL(Long entityId) {

        emsDepTDLRepository.deleteEmsDepTDL(entityId);

        historyService.historyForDelete(entityId, ConstantsApp.EMS_DEPENDENT_TDL_SCREEN);
    }

    /**
     * This method returns appropriate emissionStandard object.If new version is to created it will create new Emission Standard object and return
     * otherwise it will return old one
     * 
     * @param oldEmissionStandard
     * @param changeEmsVersion
     * @return EmissionStandard
     */
    @Override
    public EmissionStandard getEmissionStandardObject(long oldEsEntityId, boolean changeEmsVersion, EmissionStandard emissionStandard) {

        EmissionStandard newVersionObject;

        if (changeEmsVersion) {

            newVersionObject = esService.createNewEMSVersion(emissionStandard);
            newVersionObject.setEmissionStdDepTDLists(new ArrayList<EmissionStdDepTDL>());

            newVersionObject = emissionStandardRepository.saveEmissionStandard(newVersionObject);

            /****** START : COPY OTHER LIST in New Emission Standard Version ******/
            List<EmissionStdDepTCL> tclSet = esService.copyESDepTCL(oldEsEntityId, newVersionObject);
            newVersionObject.setEmissionStdDepTCLists(tclSet);

            List<PollutantGasLimitList> pglSet = esService.copyESDepPGL(oldEsEntityId, newVersionObject);
            newVersionObject.setPgLists(pglSet);

            List<FactorCoefficentList> fclSet = esService.copyESDepFCL(oldEsEntityId, newVersionObject);
            newVersionObject.setFclists(fclSet);
            /****** END : COPY OTHER LIST in New Emission Standard Version ******/

        } else {
            newVersionObject = emissionStandardRepository.load(oldEsEntityId);
            newVersionObject.setEmissionStdDepTDLists(new ArrayList<EmissionStdDepTDL>());

        }
        return newVersionObject;
    }

    /**
     * This method is used to copy a Single TDL (create new TDL and associate it to new version of EMS)
     * 
     * @param emsDepTDL
     * @param newVersionObject
     * @return EmissionStdDepTDL
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public EmissionStdDepTDL copySingleTDL(EmissionStdDepTDL emsDepTDL, EmissionStandard newVersionObject) {
        // Version is 1.0 because new object is created
        EmissionStdDepTDL newTDLOBJ = emsDepTDLFactory.createEmissonStdDepTDL(emsDepTDL.getLabel(), emsDepTDL.getDescription(),
                emsDepTDL.getVersion());
        newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);
        newTDLOBJ.setGenericTechnicalData(new ArrayList<GenericTechnicalData>());
        if (emsDepTDL.getGenericTechnicalData() != null) {
            for (GenericTechnicalData data : emsDepTDL.getGenericTechnicalData()) {

                GenericTechnicalData dataToSave = genericTechnicalDataFactory.createGenericTechData(data.getDataType(), data.getDefaultValue(),
                        data.getLabel(), data.getUnit().getValue());
                dataToSave.setEmsDepTDL(newTDLOBJ);

                newTDLOBJ.getGenericTechnicalData().add(dataToSave);

            }
        }
        newTDLOBJ.setEmissionStandard(newVersionObject);
        newTDLOBJ = emsDepTDLRepository.saveEmissonStdDepTDL(newTDLOBJ);
        return newTDLOBJ;
    }

}

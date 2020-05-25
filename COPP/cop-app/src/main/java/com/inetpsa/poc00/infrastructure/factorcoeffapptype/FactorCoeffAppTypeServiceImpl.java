/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.infrastructure.factorcoeffapptype;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.factorcoeffapptype.FactorCoeffAppTypeService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppRepository;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

public class FactorCoeffAppTypeServiceImpl implements FactorCoeffAppTypeService {
    /** The fca repo. */
    @Inject
    FactorCoeffAppRepository fcaRepo;
    @Inject
    TraceabilityService traceabilityService;
    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(FactorCoeffAppTypeServiceImpl.class);

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public String saveFactorCoeffApplicationType(FactorCoeffApplicationType factorCoeffApplicationObj) {

        List<FactorCoeffApplicationType> factorCoeffApplicationTypeData = fcaRepo
                .getFactorCoeffAppTypeDataByLabel(factorCoeffApplicationObj.getLabel());
        if (!factorCoeffApplicationTypeData.isEmpty()) {

            if (factorCoeffApplicationTypeData.get(0).getEntityId() != factorCoeffApplicationObj.getEntityId()) {
                return factorCoeffApplicationTypeData.get(0).getLabel();
            }
        } else if (factorCoeffApplicationObj.getEntityId() == null) {
            if (factorCoeffApplicationObj.getLabel() != null && factorCoeffApplicationObj.getLabel() != "") {
                // save
                FactorCoeffApplicationType newFactorCoeffApplicationType = fcaRepo.saveFactorCoeffAppType(factorCoeffApplicationObj);
                traceabilityService.historyForSave(newFactorCoeffApplicationType, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            }
        } else {
            // update
            FactorCoeffApplicationType oldFactorCoeffApplicationType = fcaRepo.load(factorCoeffApplicationObj.getEntityId());
            traceabilityService.historyForUpdate(oldFactorCoeffApplicationType, factorCoeffApplicationObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            fcaRepo.persistFactorCoeffAppType(factorCoeffApplicationObj);

        }

        return ConstantsApp.SUCCESS;

    }

    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    @Override
    public String deleteFactorCoeffApplicationType(long factorCoeffApplicationTypeId) {

        FactorCoeffApplicationType objToDelete = fcaRepo.load(factorCoeffApplicationTypeId);

        long deletedrows = fcaRepo.deleteFactorCoeffAppType(factorCoeffApplicationTypeId);
        if (deletedrows > 0) {
            logger.info("Sucessfully deleted Country data");
            traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            return ConstantsApp.SUCCESS;
        }
        logger.error(" Error occured while deleting data  :", factorCoeffApplicationTypeId);
        return ConstantsApp.FAILURE;

    }

}

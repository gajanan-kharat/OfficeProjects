/*
 * Creation : Jan 5, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.vehicletechnology;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.vehicletechnology.VehicleTechnologyService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;

/**
 * The VehicleTechnologyServiceImpl.
 *
 * @author mehaj
 */
public class VehicleTechnologyServiceImpl implements VehicleTechnologyService {

    /** The veh tech repo. */
    @Inject
    VehicleTechRepository vehicleTechRepository;

    /** The traceability service. */
    @Inject
    TraceabilityService traceabilityService;
    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(VehicleTechnologyServiceImpl.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.vehicletechnology.VehicleTechnologyService#saveVehicleTechnology(com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public String saveVehicleTechnology(VehicleTechnology vehicleTechnologyObj) {

        List<VehicleTechnology> vehicleTechnologyData = vehicleTechRepository.getVehicleTechnologyByLabel(vehicleTechnologyObj.getLabel());
        if (!vehicleTechnologyData.isEmpty()) {
            if (vehicleTechnologyData.get(0).getEntityId() == vehicleTechnologyObj.getEntityId()) {

                VehicleTechnology oldVehicleTechnology = vehicleTechRepository.load(vehicleTechnologyObj.getEntityId());

                traceabilityService.historyForUpdate(oldVehicleTechnology, vehicleTechnologyObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                vehicleTechRepository.saveVehicleTechnology(vehicleTechnologyObj);

            } else {
                return vehicleTechnologyData.get(0).getLabel();
            }
        } else if (vehicleTechnologyObj.getEntityId() == null) {
            if (vehicleTechnologyObj.getLabel() != null && vehicleTechnologyObj.getLabel() != "") {
                // save
                VehicleTechnology newVehicleTechnology = vehicleTechRepository.saveVehicleTechnology(vehicleTechnologyObj);
                traceabilityService.historyForSave(newVehicleTechnology, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            }
        } else {
            // update
            VehicleTechnology oldVehicleTechnology = vehicleTechRepository.load(vehicleTechnologyObj.getEntityId());
            traceabilityService.historyForUpdate(oldVehicleTechnology, vehicleTechnologyObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
            vehicleTechRepository.saveVehicleTechnology(vehicleTechnologyObj);

        }

        return ConstantsApp.SUCCESS;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.vehicletechnology.VehicleTechnologyService#deleteVehicleTechnology(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public String deleteVehicleTechnology(long vehicleTechnologyId) {

        VehicleTechnology objToDelete = vehicleTechRepository.load(vehicleTechnologyId);
        try {
            long deletedrows = vehicleTechRepository.deleteVehicleTechnology(vehicleTechnologyId);
            if (deletedrows > 0) {
                logger.info("Sucessfully deleted TypeApprovalArea data");
                traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
                return ConstantsApp.SUCCESS;
            }
            logger.error(" Error occured while deleting data  :", vehicleTechnologyId);
        } catch (Exception e) {

            logger.error(" Error occured while deleting data ", e);
            return ConstantsApp.FAILURE;
        }
        return ConstantsApp.FAILURE;

    }
}

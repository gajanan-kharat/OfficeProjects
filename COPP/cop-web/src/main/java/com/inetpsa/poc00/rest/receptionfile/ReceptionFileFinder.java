package com.inetpsa.poc00.rest.receptionfile;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface ReceptionFileFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ReceptionFileFinder {

    /**
     * Gets the all reception files.
     *
     * @param userId the user id
     * @return the all reception files
     */
    List<ReceptionFileRepresentation> getAllReceptionFiles(String userId);

    /**
     * Gets the vehicle file data.
     *
     * @param id the id
     * @return the vehicle file data
     */
    public ReceptionFileRepresentation getVehicleFileData(String id);

}

/*
 * Creation : Feb 13, 2017
 */
package com.inetpsa.poc00.rest.archivebox;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.archivebox.ArchiveBox;

// TODO: Auto-generated Javadoc
/**
 * The Interface ArchiveBoxFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ArchiveBoxFinder {

    /**
     * Search archive box.
     *
     * @param typeOfTestId the type of test id
     * @param modelYear the model year
     * @param projectFamilyId the project family id
     * @param fuelId the fuel id
     * @return the archive box
     */
    public ArchiveBox searchArchiveBox(Long typeOfTestId, String modelYear, Long projectFamilyId, Long fuelId);

    /**
     * Gets the archive box by number.
     *
     * @param archiveBoxNumber the archive box number
     * @return the archive box by number
     */
    public ArchiveBox getArchiveBoxByNumber(Long archiveBoxNumber);

    /**
     * Gets the all archive box.
     *
     * @return the all archive box
     */
    public List<ArchiveBox> getAllArchiveBox();

}

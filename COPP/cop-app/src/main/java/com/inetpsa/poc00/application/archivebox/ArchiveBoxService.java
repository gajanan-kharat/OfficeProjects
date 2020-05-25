/*
 * Creation : Feb 10, 2017
 */
package com.inetpsa.poc00.application.archivebox;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * The Interface ArchiveBoxService.
 */
@Service
public interface ArchiveBoxService {

    /**
     * Creates the archive box.
     *
     * @param typeOfTest the type of test
     * @param modelYear the model year
     * @param projecFamily the projec family
     * @param fuel the fuel
     * @return the archive box
     */
    public ArchiveBox createArchiveBox(TypeOfTest typeOfTest, String modelYear, ProjectCodeFamily projecFamily, Fuel fuel);

    /**
     * Close archive box.
     *
     * @param archiveBoxId the archive box id
     * @return the archive box
     */
    public ArchiveBox closeArchiveBox(Long archiveBoxId);

}

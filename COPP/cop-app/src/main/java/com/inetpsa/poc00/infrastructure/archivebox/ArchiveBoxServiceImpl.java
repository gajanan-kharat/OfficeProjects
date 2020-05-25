/*
 * Creation : Feb 10, 2017
 */
package com.inetpsa.poc00.infrastructure.archivebox;

import java.util.Date;

import javax.inject.Inject;

import com.inetpsa.poc00.application.archivebox.ArchiveBoxService;
import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.archivebox.ArchiveBoxFactory;
import com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * The Class ArchiveBoxServiceImpl.
 */
public class ArchiveBoxServiceImpl implements ArchiveBoxService {

    /** The archive box factory. */
    @Inject
    private ArchiveBoxFactory archiveBoxFactory;

    /** The archive box repo. */
    @Inject
    private ArchiveBoxRepository archiveBoxRepo;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.archivebox.ArchiveBoxService#createArchiveBox(com.inetpsa.poc00.domain.typeoftest.TypeOfTest,
     *      java.lang.String, com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily, com.inetpsa.poc00.domain.fuel.Fuel)
     */
    @Override
    public ArchiveBox createArchiveBox(TypeOfTest typeOfTest, String modelYear, ProjectCodeFamily projecFamily, Fuel fuel) {

        Long archiveBoxNumber = archiveBoxRepo.getArchiveBoxNumber();
        ArchiveBox archiveToCreate = archiveBoxFactory.createArchiveBox();

        archiveToCreate.setArchiveBoxNumber(archiveBoxNumber + 1);
        archiveToCreate.setModelYear(modelYear);
        archiveToCreate.setProjectCodeFamily(projecFamily);
        archiveToCreate.setTypeOfTest(typeOfTest);
        archiveToCreate.setFuel(fuel);
        archiveToCreate.setIsOpen(true);
        archiveToCreate.setOpeningDate(new Date());

        return archiveBoxRepo.saveArchievBox(archiveToCreate);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.application.archivebox.ArchiveBoxService#closeArchiveBox(java.lang.Long)
     */
    @Override
    public ArchiveBox closeArchiveBox(Long archiveBoxId) {

        ArchiveBox archiveBox = archiveBoxRepo.load(archiveBoxId);

        archiveBox.setIsOpen(false);
        archiveBox.setClosingDate(new Date());

        ArchiveBox archiveUpdated = archiveBoxRepo.updateArchiveBox(archiveBox);

        return archiveUpdated;
    }

}

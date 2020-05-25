/*
 * Creation : Feb 13, 2017
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.rest.archivebox.ArchiveBoxFinder;

/**
 * The Class JpaArchiveBoxFinder.
 */
public class JpaArchiveBoxFinder implements ArchiveBoxFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.archivebox.ArchiveBoxFinder#searchArchiveBox(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
     */
    @Override
    public ArchiveBox searchArchiveBox(Long typeOfTestId, String modelYear, Long projectFamilyId, Long fuelId) {
        TypedQuery<ArchiveBox> query = entityManager.createQuery(
                "FROM ArchiveBox ab WHERE ab.typeOfTest.entityId= ?1 AND ab.modelYear= ?2 AND ab.projectCodeFamily.entityId= ?3 AND ab.fuel.entityId = ?4 AND ab.isOpen = true",
                ArchiveBox.class);
        query.setParameter(1, typeOfTestId);
        query.setParameter(2, modelYear);
        query.setParameter(3, projectFamilyId);
        query.setParameter(4, fuelId);

        List<ArchiveBox> archiveBoxList = query.getResultList();

        if (!archiveBoxList.isEmpty())
            return archiveBoxList.get(0);

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.archivebox.ArchiveBoxFinder#getArchiveBoxByNumber(java.lang.Long)
     */
    @Override
    public ArchiveBox getArchiveBoxByNumber(Long archiveBoxNumber) {
        TypedQuery<ArchiveBox> query = entityManager.createQuery("FROM ArchiveBox ab WHERE ab.archiveBoxNumber= ?1 AND ab.isOpen = true",
                ArchiveBox.class);
        query.setParameter(1, archiveBoxNumber);

        List<ArchiveBox> archiveBoxList = query.getResultList();
        if (!archiveBoxList.isEmpty())
            return archiveBoxList.get(0);

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.archivebox.ArchiveBoxFinder#getAllArchiveBox()
     */
    @Override
    public List<ArchiveBox> getAllArchiveBox() {

        TypedQuery<ArchiveBox> query = entityManager.createQuery("FROM ArchiveBox", ArchiveBox.class);
        return query.getResultList();
    }

}

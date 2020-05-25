/*
 * Creation : Feb 10, 2017
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository;

/**
 * The Class ArchiveBoxJpaRepository.
 */
public class ArchiveBoxJpaRepository extends BaseJpaRepository<ArchiveBox, Long> implements ArchiveBoxRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository#getArchiveBoxNumber()
     */
    @Override
    public Long getArchiveBoxNumber() {

        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(ab.archiveBoxNumber) FROM ArchiveBox ab", Long.class);

        List<Long> archiveBoxNumber = query.getResultList();
        if (!archiveBoxNumber.isEmpty())
            return archiveBoxNumber.get(0);

        return 0L;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository#saveArchievBox(com.inetpsa.poc00.domain.archivebox.ArchiveBox)
     */
    @Override
    public ArchiveBox saveArchievBox(ArchiveBox archiveBox) {
        return super.save(archiveBox);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository#updateArchiveBox(com.inetpsa.poc00.domain.archivebox.ArchiveBox)
     */
    @Override
    public ArchiveBox updateArchiveBox(ArchiveBox archiveBox) {
        return entityManager.merge(archiveBox);
    }

}

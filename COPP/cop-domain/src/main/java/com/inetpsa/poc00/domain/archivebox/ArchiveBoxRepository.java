package com.inetpsa.poc00.domain.archivebox;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface ArchiveBoxRepository.
 */
public interface ArchiveBoxRepository extends GenericRepository<ArchiveBox, Long> {

    /**
     * Gets the archive box number.
     *
     * @return the archive box number
     */
    public Long getArchiveBoxNumber();

    /**
     * Save archiev box.
     *
     * @param archiveBox the archive box
     * @return the archive box
     */
    public ArchiveBox saveArchievBox(ArchiveBox archiveBox);

    /**
     * Update archive box.
     *
     * @param archiveBox the archive box
     * @return the archive box
     */
    public ArchiveBox updateArchiveBox(ArchiveBox archiveBox);

}

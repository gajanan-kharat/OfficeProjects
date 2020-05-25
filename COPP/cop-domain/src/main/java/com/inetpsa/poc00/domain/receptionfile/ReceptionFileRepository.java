package com.inetpsa.poc00.domain.receptionfile;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface ReceptionFileRepository.
 */
public interface ReceptionFileRepository extends GenericRepository<ReceptionFile, Long> {

	/**
	 * Save reception file.
	 *
	 * @param receptionFile the reception file
	 * @return the reception file
	 */
	ReceptionFile saveReceptionFile(ReceptionFile receptionFile);

	/**
	 * Update reception file.
	 *
	 * @param receptionFile the reception file
	 * @return the int
	 */
	int updateReceptionFile(ReceptionFile receptionFile);

	/**
	 * Persist reception file.
	 *
	 * @param receptionFile the reception file
	 */
	void persistReceptionFile(ReceptionFile receptionFile);

	/**
	 * Delete all.
	 *
	 * @return the int
	 */
	int deleteAll();

	/**
	 * Count.
	 *
	 * @return the long
	 */
	long count();

	/**
	 * Gets the all reception files.
	 *
	 * @return the all reception files
	 */
	List<ReceptionFile> getAllReceptionFiles();

	/**
	 * Delete reception file.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteReceptionFile(Long id);

}

package com.inetpsa.poc00.domain.technicalgroup;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.status.Status;

/**
 * Repository interface of TechnicalGroup.
 */

public interface TechGroupRepository extends GenericRepository<TechnicalGroup, Long> {

	/**
	 * Saves the TechnicalGroup.
	 *
	 * @param object the TechnicalGroup to save
	 * @return the TechnicalGroup
	 */
	TechnicalGroup saveTechGroup(TechnicalGroup object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 *
	 * @return TechnicalGroup count
	 */
	long count();

	/**
	 * Delete technical group.
	 *
	 * @param id the id
	 */
	void deleteTechnicalGroup(long id);

	/**
	 * Gets the all technical group.
	 *
	 * @return the all technical group
	 */
	List<TechnicalGroup> getAllTechnicalGroup();

	/**
	 * Gets the status.
	 *
	 * @param statusLabel the status label
	 * @return the status
	 */
	public Status getStatus(String statusLabel);

	/**
	 * Lod technical group.
	 *
	 * @param technicalGroupId the technical group id
	 * @return the technical group
	 */
	public TechnicalGroup lodTechnicalGroup(long technicalGroupId);

	/**
	 * Gets the technical group.
	 *
	 * @param label the label
	 * @return the technical group
	 */
	public List<TechnicalGroup> getTechnicalGroup(String label);

	/**
	 * Gets the technical group for technical case.
	 *
	 * @param entityId the entity id
	 * @return the technical group for technical case
	 */
	TechnicalGroup getTechnicalGroupForTechnicalCase(Long entityId);

}

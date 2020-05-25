package com.inetpsa.poc00.domain.regulationgroup;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of RegulationGroup.
 */

public interface RegulationGroupRepository extends GenericRepository<RegulationGroup, Long> {

	/**
	 * Saves the RegulationGroup.
	 *
	 * @param object the RegulationGroup to save
	 * @return the RegulationGroup
	 */
	RegulationGroup saveRegulationGroup(RegulationGroup object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 *
	 * @return RegulationGroup count
	 */
	long count();

	/**
	 * Delete regulation group.
	 *
	 * @param regulationGrpId the regulation grp id
	 */
	public void deleteRegulationGroup(Long regulationGrpId);

	/**
	 * Load regulation group.
	 *
	 * @param regulationGroupId the regulation group id
	 * @return the regulation group
	 */
	public RegulationGroup loadRegulationGroup(long regulationGroupId);

	/**
	 * Gets the regulation group for tg.
	 *
	 * @param entityId the entity id
	 * @return the regulation group for tg
	 */
	RegulationGroup getRegulationGroupForTG(Long entityId);

	/**
	 * Gets the regulation group.
	 *
	 * @param label the label
	 * @return the regulation group
	 */
	List<RegulationGroup> getRegulationGroup(String label);
}

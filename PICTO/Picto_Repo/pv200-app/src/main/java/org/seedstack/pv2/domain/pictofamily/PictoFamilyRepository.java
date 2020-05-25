package org.seedstack.pv2.domain.pictofamily;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of PictoFamily.
 */

public interface PictoFamilyRepository extends
		GenericRepository<PictoFamily, Long> {

	/**
	 * Saves the pictoFamily.
	 *
	 * @param pictoFamily
	 *            the pictoFamily to save
	 * @return the PictoFamily
	 */
	PictoFamily savePictoFamily(PictoFamily pictoFamily);

	/**
	 * Persists the pictoFamily.
	 *
	 * @param pictoFamily
	 *            the pictoFamily to persist
	 */
	void persistPictoFamily(PictoFamily pictoFamily);

	/**
	 * Find the picto family with reference number
	 * 
	 * @param referenceNum
	 * @return the PictoFamily
	 */
	PictoFamily findAllPictoFamilyByRefNumber(String referenceNum);

}

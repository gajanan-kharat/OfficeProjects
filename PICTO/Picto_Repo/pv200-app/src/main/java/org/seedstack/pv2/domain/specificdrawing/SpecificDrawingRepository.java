package org.seedstack.pv2.domain.specificdrawing;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * Repository interface of SpecificDrawing.
 */

public interface SpecificDrawingRepository extends
		GenericRepository<SpecificDrawing, Long> {

	/**
	 * Saves the SpecificDrawing.
	 *
	 * @param spDrawing
	 *            the spDrawing to save
	 * @return the SpecificDrawing
	 */
	SpecificDrawing saveSpecificDrawing(SpecificDrawing spDrawing);

	/**
	 * Persists the SpecificDrawing.
	 *
	 * @param spDrawing
	 *            the spDrawing to persist
	 */
	void persistSpecificDrawing(SpecificDrawing spDrawing);
	
	/**
	 * Find the SpecificDrawing by family id
	 *
	 * @param pictoFamilyID
	 *            the pictoFamilyID 
	 * @param name
	 *            the name 
	 * @return the SpecificDrawing          
	 */
	SpecificDrawing findSpeDrawingByFamilyId(PictoFamily  pictoFamilyID,String name);

}

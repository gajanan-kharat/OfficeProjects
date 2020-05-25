package org.seedstack.pv2.domain.specificdrawing;

import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * SpecificDrawing Factory interface.
 */
public interface SpecificDrawingFactory extends GenericFactory<SpecificDrawing> {

	/**
	 * Specific Drawing factory create method
	 * 
	 * @param id
	 * @param name
	 * @param commentsFR
	 * @param commentsEN
	 * @param familyId
	 * @return
	 */
	public SpecificDrawing createSpecificDrawing(Long id, String name,
			String commentsFR, String commentsEN, PictoFamily familyId);

}

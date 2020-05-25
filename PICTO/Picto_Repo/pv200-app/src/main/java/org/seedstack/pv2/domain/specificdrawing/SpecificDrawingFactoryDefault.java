package org.seedstack.pv2.domain.specificdrawing;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * SpecificDrawing Factory implementation.
 */

public class SpecificDrawingFactoryDefault extends BaseFactory<SpecificDrawing>
		implements SpecificDrawingFactory {

	@Override
	public SpecificDrawing createSpecificDrawing(Long id, String name,
			String commentsFR, String commentsEN, PictoFamily familyId) {
		return new SpecificDrawing(id, name, commentsFR, commentsEN, familyId);
	}

}

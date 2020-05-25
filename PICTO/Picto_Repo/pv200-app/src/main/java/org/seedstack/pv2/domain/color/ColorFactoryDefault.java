package org.seedstack.pv2.domain.color;

import org.seedstack.business.domain.BaseFactory;

/**
 * Color Factory implementation.
 */

public class ColorFactoryDefault extends BaseFactory<Color> implements
		ColorFactory {

	@Override
	public Color createColor(Long id, String color) {
		return new Color(id, color);
	}

}

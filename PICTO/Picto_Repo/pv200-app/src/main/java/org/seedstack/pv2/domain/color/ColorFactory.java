package org.seedstack.pv2.domain.color;

import org.seedstack.business.domain.GenericFactory;

/**
 * Color Factory interface.
 */
public interface ColorFactory extends GenericFactory<Color> {

	/**
	 * Color factory create method
	 * 
	 * @param id
	 * @param color
	 * @return Color
	 */
	Color createColor(Long id, String color);

}

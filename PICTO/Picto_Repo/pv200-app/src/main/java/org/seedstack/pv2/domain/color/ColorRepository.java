package org.seedstack.pv2.domain.color;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Color.
 */

public interface ColorRepository extends GenericRepository<Color, Long> {

	/**
	 * Saves the color.
	 *
	 * @param color
	 *            the color to save
	 * @return the Color
	 */
	Color saveColor(Color color);

	/**
	 * Persists the color.
	 *
	 * @param color
	 *            the color to persist
	 */
	void persistColor(Color color);

	/**
	 * @param color
	 * @return the Color
	 */
	public Color findColorByLable(String color);

}

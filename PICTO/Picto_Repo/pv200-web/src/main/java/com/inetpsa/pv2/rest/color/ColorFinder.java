package com.inetpsa.pv2.rest.color;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.seed.transaction.Transactional;

/**
 * interface:ColorFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ColorFinder {

	/**
	 * Gets the all color.
	 *
	 * @return the all color
	 */
	List<ColorRepresentation> getAllColor();

}

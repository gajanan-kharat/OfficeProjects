/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.rest.valuedcoastdown;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ValuedCoastDownFinder {

	public List<String> getAllCoastdownLabels();

}

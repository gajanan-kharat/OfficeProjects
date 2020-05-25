/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.rest.valuedinertia;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ValuedInertiaFinder {

	List<Integer> getAllInertiaValues();

}

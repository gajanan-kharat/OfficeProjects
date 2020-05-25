package com.inetpsa.poc00.rest.inertia;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.inertia.Inertia;

/**
 * The Interface InertiaFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface InertiaFinder {

	/**
	 * Gets the inertia obj.
	 * 
	 * @param value the value
	 * @return the inertia obj
	 */
	List<Inertia> getinertiaObj(int value);

	/**
	 * Gets the all inertia values.
	 * 
	 * @return the all inertia values
	 */
	List<Integer> getAllInertiaValues();

}

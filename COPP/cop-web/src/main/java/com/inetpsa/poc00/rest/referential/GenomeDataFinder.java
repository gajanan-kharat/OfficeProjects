package com.inetpsa.poc00.rest.referential;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Interface GenomeDataFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenomeDataFinder {

	/**
	 * Gets the TVV ruleby ID.
	 *
	 * @param id the id
	 * @return the TVV ruleby ID
	 */
	List<GenomeTVVRule> getTVVRulebyID(Long id);

	/**
	 * Gets the fuel injection typeby ID.
	 *
	 * @param id the id
	 * @return the fuel injection typeby ID
	 */
	List<FuelInjectionType> getFuelInjectionTypebyID(long id);

	/**
	 * Gets the TVV rule objby ID.
	 *
	 * @param id the id
	 * @return the TVV rule objby ID
	 */
	GenomeTVVRule getTVVRuleObjbyID(Long id);
}

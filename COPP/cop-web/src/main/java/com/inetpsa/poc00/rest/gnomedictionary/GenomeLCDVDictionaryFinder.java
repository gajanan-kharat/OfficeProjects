/*
 * Creation : Jun 21, 2016
 */
package com.inetpsa.poc00.rest.gnomedictionary;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;


/**
 * The Interface GenomeLCDVDictionaryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenomeLCDVDictionaryFinder {

	/**
	 * Gets the dictionary for TVV.
	 *
	 * @param tvvLabel the tvv label
	 * @return the dictionary for TVV
	 */
	List<GenomeLCDVDictionary> getDictionaryForTVV(String tvvLabel);

	/**
	 * Gets the kmat dictionary for TVV.
	 *
	 * @param tvvLabel the tvv label
	 * @return the kmat dictionary for TVV
	 */
	List<String> getKmatDictionaryForTVV(String tvvLabel);

}

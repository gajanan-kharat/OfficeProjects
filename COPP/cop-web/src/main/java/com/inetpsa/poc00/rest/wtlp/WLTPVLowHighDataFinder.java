package com.inetpsa.poc00.rest.wtlp;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;


/**
 * The Interface WLTPVLowHighDataFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface WLTPVLowHighDataFinder {

	/**
	 * Gets the WLTP data.
	 *
	 * @return the WLTP data
	 */
	public WLTPVLowHighData getWLTPData();
}

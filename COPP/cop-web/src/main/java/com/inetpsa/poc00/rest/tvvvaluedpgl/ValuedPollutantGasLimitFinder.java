/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;

@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ValuedPollutantGasLimitFinder {

	public List<TvvValuedPollutantGasLimit> getAllLimitsForEmsDepList(long listId);
}

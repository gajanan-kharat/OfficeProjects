/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ValuedFactorCoefficentFinder {

	List<TvvValuedFactorCoefficents> getAllFactorsForEmsDepList(long entityId);

}

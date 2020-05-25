/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfcl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

public interface TvvValuedFactorCoefficentRepository extends GenericRepository<TvvValuedFactorCoefficents, Long> {

	List<TvvValuedFactorCoefficents> getAllFactorsForEmsDepList(Long entityId);

}

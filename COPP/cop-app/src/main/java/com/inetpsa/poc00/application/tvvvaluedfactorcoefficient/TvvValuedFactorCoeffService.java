/*
 * Creation : Feb 13, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.application.tvvvaluedfactorcoefficient;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

/**
 * The Interface TvvValuedFactorCoeffService.
 */
@Service
public interface TvvValuedFactorCoeffService {
	TvvValuedFactorCoefficents saveTvvValuedFactorCoefficient(TvvValuedFactorCoefficents tvvValuedFactorCoeffObj);
}

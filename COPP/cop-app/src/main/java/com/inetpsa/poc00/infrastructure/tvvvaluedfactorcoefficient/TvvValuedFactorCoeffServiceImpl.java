/*
 * Creation : Feb 13, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.tvvvaluedfactorcoefficient;

import javax.inject.Inject;

import com.inetpsa.poc00.application.tvvvaluedfactorcoefficient.TvvValuedFactorCoeffService;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedFactorCoefficentRepository;

/**
 * 
 * 
 * @author mehaj
 *
 */

public class TvvValuedFactorCoeffServiceImpl implements TvvValuedFactorCoeffService {
	@Inject
	TvvValuedFactorCoefficentRepository tvvValuedFactorCoefficentRepository;

	@Override
	public TvvValuedFactorCoefficents saveTvvValuedFactorCoefficient(TvvValuedFactorCoefficents tvvValuedFactorCoeffObj) {
		return tvvValuedFactorCoefficentRepository.save(tvvValuedFactorCoeffObj);
	}

}

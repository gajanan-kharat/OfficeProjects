/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfactorcoeff;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;

public interface TvvValuedEsDepFCLRepository extends GenericRepository<TvvValuedEsDepFCL, Long> {

	List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId, Long entityId);

}

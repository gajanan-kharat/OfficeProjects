package com.inetpsa.poc00.domain.tvvvaluedpgl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

public interface TvvValuedEsDepPGLRepository extends GenericRepository<TvvValuedEsDepPGL, Long> {

	List<TvvValuedEsDepPGL> getAllValuedEsDepPGL(long tvvId, Long entityId);

}

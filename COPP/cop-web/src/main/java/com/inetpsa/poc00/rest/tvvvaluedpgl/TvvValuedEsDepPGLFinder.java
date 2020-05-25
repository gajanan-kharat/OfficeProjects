package com.inetpsa.poc00.rest.tvvvaluedpgl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;

/**
 * The Interface TvvValuedEsDepPGLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedEsDepPGLFinder {

    /**
     * Gets the all valued pgl.
     * 
     * @param tvvID the tvv id
     * @param emsID the ems id
     * @return the all valued pgl
     */
    List<TvvValuedEsDepPGLRepresentation> getAllValuedPGL(long tvvID, long emsID);

    /**
     * Gets the all valued es dep pgl.
     * 
     * @param tvvId the tvv id
     * @param entityId the entity id
     * @return the all valued es dep pgl
     */
    List<TvvValuedEsDepPGL> getAllValuedEsDepPGL(long tvvId, long entityId);

}

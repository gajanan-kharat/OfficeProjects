/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationitem;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface PreparationItemFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PreparationItemFinder {

    /**
     * Gets the preparation itembygpc id.
     *
     * @param gpcId the gpc id
     * @return the preparation itembygpc id
     */
    List<PreparationItemRepresentation> getPreparationItembygpcId(Long gpcId);

}

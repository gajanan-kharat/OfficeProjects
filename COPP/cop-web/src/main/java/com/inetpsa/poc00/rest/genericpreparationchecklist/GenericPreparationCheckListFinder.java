/*
 * Creation : Oct 14, 2016
 */
package com.inetpsa.poc00.rest.genericpreparationchecklist;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface GenericPreparationCheckListFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenericPreparationCheckListFinder {

    /**
     * Gets the GPC listbypfs id.
     *
     * @param pfsId the pfs id
     * @return the GPC listbypfs id
     */
    List<GenericPreparationCheckListRepresentation> getGPCListbypfsId(Long pfsId);

}

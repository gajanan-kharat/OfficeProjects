/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.application.regulationgroup;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

/**
 * The Interface RegulationGroupService.
 */
@Service
public interface RegulationGroupService {

    /**
     * Checks if is regulation group exist.
     *
     * @param label the label
     * @return true, if is regulation group exist
     */
    public boolean isRegulationGroupExist(String label);

    /**
     * Save regulation group.
     *
     * @param regulationGroup the regulation group
     * @return the regulation group
     */
    public RegulationGroup saveRegulationGroup(RegulationGroup regulationGroup);

    /**
     * Delete regulation group.
     *
     * @param regulationGroupId the regulation group id
     */
    public void deleteRegulationGroup(List<TechnicalGroup> technicalGrpList, long regulationGroupId);

    /**
     * Delete technical group.
     *
     * @param technicalGroupId the technical group id
     */
    public void deleteTechnicalGroup(long technicalGroupId);

    /**
     * Save regulation group for tg.
     *
     * @param regulationId the regulation id
     * @param technicalGroupId the technical group id
     * @param validateForEs the validate for es
     * @return the technical case
     */
    public TechnicalCase saveRegulationGroupForTg(long regulationId, long technicalGroupId, boolean validateForEs);

    /**
     * Delete old rg valued tcl.
     *
     * @param rgValuedTcl the rg valued tcl
     */
    public void deleteOldRgValuedTCL(List<RGValuedESDependentTCL> rgValuedTcl);

}

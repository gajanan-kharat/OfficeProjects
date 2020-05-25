/*
 * Creation : Dec 27, 2016
 */
package com.inetpsa.poc00.infrastructure.regulationgroup;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.inetpsa.poc00.application.regulationgroup.RegulationGroupService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupFactory;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

/**
 * The Class RegulationGroupServiceImpl.
 */
public class RegulationGroupServiceImpl implements RegulationGroupService {

    /** The regulation group repository. */
    @Inject
    RegulationGroupRepository regulationGroupRepository;

    /** The status repository. */
    @Inject
    private StatusRepository statusRepository;

    /** The technical group repository. */
    @Inject
    private TechGroupRepository technicalGroupRepository;

    /** The technical case repository. */
    @Inject
    private TechCaseRepository technicalCaseRepository;

    /** The technical grp factory. */
    @Inject
    private TechGroupFactory technicalGrpFactory;

    /** The rg valued es dependent tcl repository. */
    @Inject
    private RGValuedESDependentTCLRepository rgValuedESDependentTCLRepository;

    /**
     * Checks if is regulation group exist.
     *
     * @param label the label
     * @return true, if is regulation group exist
     */
    @Override
    public boolean isRegulationGroupExist(String label) {

        boolean isRgPresent;
        List<RegulationGroup> regulationGroupList = regulationGroupRepository.getRegulationGroup(label);
        if (!regulationGroupList.isEmpty())
            isRgPresent = true;
        else
            isRgPresent = false;
        return isRgPresent;
    }

    /**
     * Save regulation group.
     *
     * @param regulationGroup the regulation group
     * @return the regulation group
     */
    @Override

    public RegulationGroup saveRegulationGroup(RegulationGroup regulationGroup) {
        RegulationGroup regulationGroupobj;
        List<RegulationGroup> searchedRegulationGroup = regulationGroupRepository.getRegulationGroup(regulationGroup.getLabel());
        if (searchedRegulationGroup.isEmpty()) {
            Status status = statusRepository.getStatus(ConstantsApp.DRAFT);
            regulationGroup.setRegulationgroupstatus(status);
            regulationGroup.setVersion("1.0");
            Date creationDate = new Date();
            regulationGroup.setCreationDate(creationDate);
            regulationGroup.setModificationDate(creationDate);
            regulationGroupobj = regulationGroupRepository.saveRegulationGroup(regulationGroup);
        } else {
            regulationGroupobj = regulationGroupRepository.loadRegulationGroup(searchedRegulationGroup.get(0).getEntityId());
        }
        return regulationGroupobj;
    }

    /**
     * Delete regulation group.
     *
     * @param regulationGroupId the regulation group id
     */
    @Override
    public void deleteRegulationGroup(List<TechnicalGroup> technicalGrpList, long regulationGroupId) {

        for (TechnicalGroup technicalGroup : technicalGrpList) {
            technicalGroup.setRegulationGroup(null);
            technicalGroupRepository.saveTechGroup(technicalGroup);

        }
        regulationGroupRepository.deleteRegulationGroup(regulationGroupId);
    }

    /**
     * Delete technical group.
     *
     * @param technicalGroupId the technical group id
     */
    @Override
    public void deleteTechnicalGroup(long technicalGroupId) {
        TechnicalGroup technicalGroup = technicalGroupRepository.lodTechnicalGroup(technicalGroupId);
        technicalGroup.setRegulationGroup(null);
        technicalGroupRepository.saveTechGroup(technicalGroup);

    }

    /**
     * Save regulation group for tg.
     *
     * @param regulationId the regulation id
     * @param technicalGroupId the technical group id
     * @param validateForEs the validate for es
     * @return the technical case
     */
    @Override
    public TechnicalCase saveRegulationGroupForTg(long regulationId, long technicalGroupId, boolean validateForEs) {

        RegulationGroup regulationGroup = regulationGroupRepository.loadRegulationGroup(regulationId);

        TechnicalGroup technicalGroup = technicalGroupRepository.lodTechnicalGroup(technicalGroupId);

        TechnicalCase techCase = null;
        if (validateForEs) {

            List<TechnicalCase> technicalCaseList = technicalCaseRepository.getTechnicalCasesForTG(technicalGroup.getEntityId());
            if (technicalCaseList != null && !technicalCaseList.isEmpty()) {
                for (TechnicalCase tCase : technicalCaseList) {
                    if (tCase.getEmissionStandard().getEntityId() != regulationGroup.getEmissionStandardforRg().getEntityId()) {
                        techCase = tCase;

                    }
                }
            }

        } else {
            technicalGroup.setRegulationGroup(regulationGroup);
            technicalGroupRepository.save(technicalGroup);
        }
        return techCase;
    }

    /**
     * Delete old rg valued tcl.
     *
     * @param rgValuedTcl the rg valued tcl
     */
    @Override
    public void deleteOldRgValuedTCL(List<RGValuedESDependentTCL> rgValuedTcl) {
        for (RGValuedESDependentTCL tclObj : rgValuedTcl) {
            rgValuedESDependentTCLRepository.deleteRegGrpValEmStdDepTCL(tclObj.getEntityId());
        }

    }

}

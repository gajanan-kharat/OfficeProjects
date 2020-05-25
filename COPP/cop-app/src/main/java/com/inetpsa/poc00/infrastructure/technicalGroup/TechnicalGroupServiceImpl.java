/*
 * Creation : Nov 8, 2016
 */
package com.inetpsa.poc00.infrastructure.technicalGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.technicalgroup.TechnicalGroupService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVRepository;

public class TechnicalGroupServiceImpl implements TechnicalGroupService {

    @Inject
    TechGroupRepository technicalGroupRepository;

    @Inject
    private TechCaseRepository technicalCaseRepository;

    @Inject
    private TVVRepository tvvRepository;

    @Inject
    private RegulationGroupRepository regulationGroupRepository;

    @Inject

    private StatusRepository statusRepository;

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup saveTechGroup(TechnicalGroup technicalGroup) {
        TechnicalGroup techGroup;
        List<TechnicalGroup> searchedTechnicalGruop = technicalGroupRepository.getTechnicalGroup(technicalGroup.getLabel());
        if (searchedTechnicalGruop.isEmpty()) {

            Status status = technicalGroupRepository.getStatus(ConstantsApp.DRAFT);
            technicalGroup.setTechgroupstatus(status);
            technicalGroup.setVersion("1.0");
            Date creationDate = new Date();
            technicalGroup.setCreationDate(creationDate);
            technicalGroup.setModificationDate(creationDate);
            techGroup = technicalGroupRepository.saveTechGroup(technicalGroup);
        }

        else {
            techGroup = loadTechnicalGroup(searchedTechnicalGruop.get(0).getEntityId());
        }

        return techGroup;

    }

    @Override
    public TechnicalGroup loadTechnicalGroup(long technicalGroupId) {
        return technicalGroupRepository.load(technicalGroupId);
    }

    @Override

    public void deleteTechnicalGroup(List<TechnicalCase> techCase, long technicalGroupId) {

        if (!techCase.isEmpty()) {
            for (TechnicalCase technicalCase : techCase) {
                technicalCase.setTechnicalGroup(null);
                technicalCaseRepository.saveTechCase(technicalCase);
            }

        }
        technicalGroupRepository.deleteTechnicalGroup(technicalGroupId);

    }

    @Override
    public void deleteTvvFromTG(long tvvEntityId) {
        TVV tvvObj = tvvRepository.load(tvvEntityId);
        TechnicalCase technicalCase = tvvObj.getTechnicalCase();
        technicalCase.setTechnicalGroup(null);
        technicalCase.setTvvWorstCase(false);
        technicalCaseRepository.saveTechCase(technicalCase);
    }

    @Override
    public TechnicalGroup getVersionedTechnicalGroup(TechnicalGroup technicalGroup, String samplingLabel, Date creattionDate) {
        Status status = technicalGroupRepository.getStatus(ConstantsApp.DRAFT);
        technicalGroup.setTechgroupstatus(status);
        technicalGroup.setSamplingLabel(samplingLabel);
        Date modificationDate = new Date();
        technicalGroup.setCreationDate(creattionDate);
        technicalGroup.setModificationDate(modificationDate);
        technicalGroup.setEntityId(null);
        technicalGroup.setRegulationGroup(null);
        return technicalGroup;

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup saveEditedTechnicalGroup(TechnicalGroup technicalGroup, Status status, boolean newVersion, String samplingLabel,
            Date creattionDate, RegulationGroup regulationGroup) {

        Status statusobj = technicalGroupRepository.getStatus(status.getLabel());
        technicalGroup.setTechgroupstatus(statusobj);
        technicalGroup.setSamplingLabel(samplingLabel);
        technicalGroup.setCreationDate(creattionDate);
        Date modificationDate = new Date();
        technicalGroup.setModificationDate(modificationDate);
        if (regulationGroup != null)
            technicalGroup.setRegulationGroup(regulationGroupRepository.loadRegulationGroup(regulationGroup.getEntityId()));
        if (newVersion) {

            TechnicalGroup versionedTechGroup = getVersionedTechnicalGroup(technicalGroup, samplingLabel, creattionDate);
            technicalGroup = versionedTechGroup;
        }

        return technicalGroupRepository.saveTechGroup(technicalGroup);
    }

    @Override
    public boolean isTechnicalGroupExist(String techGroupLabel) {
        boolean isTgPesent;
        List<TechnicalGroup> searchedTechnicalGruop = technicalGroupRepository.getTechnicalGroup(techGroupLabel);
        if (!searchedTechnicalGruop.isEmpty())
            isTgPesent = true;
        else
            isTgPesent = false;

        return isTgPesent;
    }

    @Override
    public List<Status> getStatusForTechnicalGroup() {
        List<Status> statusList = statusRepository.getAllStatus();
        List<Status> statusListTosend = new ArrayList<>();
        for (Status status : statusList) {
            if (status.getLabel().equalsIgnoreCase(ConstantsApp.DRAFT) || status.getLabel().equalsIgnoreCase(ConstantsApp.VALID)) {

                statusListTosend.add(status);

            }
        }

        return statusListTosend;
    }

    @Override
    public TechnicalCase saveTechnicalCase(long entityId, TechnicalGroup technicalGroup, boolean isWorstCase) {
        TechnicalCase technicalCaseObj = null;
        TVV tvvObj = tvvRepository.load(entityId);
        TechnicalCase technicalCase = tvvObj.getTechnicalCase();
        if (technicalGroup != null)
            technicalCase.setTechnicalGroup(technicalGroup);
        if (isWorstCase)
            technicalCase.setTvvWorstCase(true);
        else
            technicalCase.setTvvWorstCase(false);
        if (technicalGroup != null && technicalCase.getTechnicalGroup().getEntityId().equals(technicalGroup.getEntityId())) {
            technicalCaseObj = technicalCaseRepository.saveTechCase(technicalCase);
        }

        return technicalCaseObj;
    }

}

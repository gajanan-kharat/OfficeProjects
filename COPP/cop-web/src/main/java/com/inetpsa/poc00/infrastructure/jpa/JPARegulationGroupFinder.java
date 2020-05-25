package com.inetpsa.poc00.infrastructure.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.rest.regulationgroup.ManageSearchRGRequestDto;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvFinderUtil;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;

/**
 * The Class JPARegulationGroupFinder.
 */
public class JPARegulationGroupFinder implements RegulationGroupFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The tvv util. */
    TvvFinderUtil tvvUtil = new TvvFinderUtil();
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPARegulationGroupFinder.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getRegulationGroup(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<RegulationGroupRepresentation> getRegulationGroup(String label) {
        String querry = "select new " + RegulationGroupRepresentation.class.getName()
                + "(regulationGrp.entityId, regulationGrp.description,regulationGrp.label,regulationGrp.version,regulationGrp.creationDate,regulationGrp.modificationDate,regulationGrp.regulationgroupstatus.entityId,regulationGrp.regulationgroupstatus.label , regulationGrp.regulationgroupstatus.guiLabel)"
                + "from RegulationGroup regulationGrp where regulationGrp.label=?1";

        TypedQuery<RegulationGroupRepresentation> queryResult = entityManager.createQuery(querry, RegulationGroupRepresentation.class);
        queryResult.setParameter(1, label);

        return queryResult.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getMaxVersionForRGLabel()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Double getMaxVersionForRGLabel(String label) {
        Query query = entityManager.createNativeQuery(Constants.MAX_REGULATION_GROUP_TABLE_VERSION_QUERY);
        query.setParameter(1, label);

        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getSearchedRegulationGroup(java.util.List, java.lang.String)
     */
    @Override
    public List<RegulationGroupRepresentation> getSearchedRegulationGroup(List<String> tvvLableList, String searchLabel, boolean sortAlphabetically,
            boolean sortByDate) {

        StringBuilder whereClause = new StringBuilder();
        StringBuilder queryBuilderString = new StringBuilder("select new " + RegulationGroupRepresentation.class.getName()
                + "(rgrp.entityId,rgrp.description,rgrp.label,rgrp.version,rgrp.creationDate,rgrp.modificationDate,status.entityId,status.label,status.guiLabel)");

        queryBuilderString.append(" from RegulationGroup rgrp join rgrp.regulationgroupstatus status ");

        if (tvvLableList != null && !tvvLableList.isEmpty()) {
            queryBuilderString.append(" join rgrp.emissionStandardforRg emission join emission.technicalCases techcase join techcase.tvv tvv ");
            whereClause.append("  tvv.label In:tvvLableList AND ");
        }

        if (searchLabel != null && !searchLabel.isEmpty()) {
            whereClause.append(" rgrp.label Like:searchLabel AND");
        }

        if (whereClause.length() > 0) {
            queryBuilderString.append(" WHERE ");
            if (whereClause.toString().trim().endsWith("AND")) {
                queryBuilderString.append(whereClause.substring(0, whereClause.lastIndexOf("AND")));
            } else
                queryBuilderString.append(whereClause);

        }

        if (sortAlphabetically) {
            queryBuilderString.append(" order by techgroup.label");
        } else if (sortByDate) {
            queryBuilderString.append(" order by techgroup.modificationDate desc");
        }

        TypedQuery<RegulationGroupRepresentation> lTypedQuery;
        try {
            lTypedQuery = entityManager.createQuery(queryBuilderString.toString(), RegulationGroupRepresentation.class);
        } catch (Exception e) {
            LOGGER.error("Error in searching RegulationGroup", e);
            return new ArrayList<>();
        }

        if (searchLabel != null && !searchLabel.isEmpty()) {
            lTypedQuery.setParameter("searchLabel", searchLabel);
        }

        if (tvvLableList != null && !tvvLableList.isEmpty()) {
            lTypedQuery.setParameter("tvvLableList", tvvLableList);
        }
        return lTypedQuery.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getSearchedRegulationGroupForTg(java.util.List, java.lang.String)
     */
    public List<RegulationGroup> getSearchedRegulationGroupForTg(List<String> tvvLableList, String searchLabel) {
        TypedQuery<RegulationGroup> query = entityManager.createQuery(
                "select distinct new " + RegulationGroup.class.getName() + " (regulationGroup)" + " from RegulationGroup regulationGroup   "
                        + " where  regulationGroup.label Like:searchLabel  Or  regulationGroup.description Like:searchLabel Or regulationGroup.emissionStandardforRg.esLabel Like:searchLabel ",
                RegulationGroup.class);
        query.setParameter("searchLabel", "%" + searchLabel + "%");

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getSelectedTechnicalGroupsForRg(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TechnicalGroupRepresentation> getSelectedTechnicalGroupsForRg(Long regulationGrpId) {
        String querry = "select new " + TechnicalGroupRepresentation.class.getName()
                + "(techgroup.entityId, techgroup.label,techgroup.description,techgroup.version,techgroup.samplingLabel,techgroup.techgroupstatus.entityId,techgroup.techgroupstatus.label,techgroup.techgroupstatus.guiLabel,techgroup.creationDate,techgroup.modificationDate,techgroup.regulationGroup.entityId)"
                + "from TechnicalGroup techgroup where techgroup.regulationGroup.entityId=?1";

        TypedQuery<TechnicalGroupRepresentation> queryResult = entityManager.createQuery(querry, TechnicalGroupRepresentation.class);
        queryResult.setParameter(1, regulationGrpId);

        return queryResult.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getRGValuedEsdTC(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<RGValuedESDependentTCLRepresentation> getRGValuedEsdTC(Long regulationGrpId) {
        String querry = "select new " + RGValuedESDependentTCLRepresentation.class.getName()
                + "(RGvaluedTcl.entityId,RGvaluedTcl.description,RGvaluedTcl.label,RGvaluedTcl.version,RGvaluedTcl.rgValuedGenericTestCondition)"
                + "from RGValuedESDependentTCL RGvaluedTcl where  RGvaluedTcl.regulationGroup.entityId=?1";

        TypedQuery<RGValuedESDependentTCLRepresentation> queryResult = entityManager.createQuery(querry, RGValuedESDependentTCLRepresentation.class);
        queryResult.setParameter(1, regulationGrpId);

        return queryResult.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getRegulationGroupForTG(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public RegulationGroup getRegulationGroupForTG(long technicalGroupId) {
        RegulationGroup rGroup = null;
        try {
            String query = "select regulationGrp from RegulationGroup regulationGrp join regulationGrp.technicalGroups tg where tg.entityId=?1";

            TypedQuery<RegulationGroup> queryResult = entityManager.createQuery(query, RegulationGroup.class);
            queryResult.setParameter(1, technicalGroupId);

            rGroup = queryResult.getSingleResult();
        } catch (Exception e) {

            // Exception is removed from logger as this is valid scenario,Cannot log Exception for this case
            LOGGER.error("Error in retriving Regulation Group" + e);
        }
        return rGroup;
    }

    /**
     * Find all regulation groups.
     * 
     * @return the list
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<RegulationGroupRepresentation> findAllRegulationGroups() {
        String querry = "select new " + RegulationGroupRepresentation.class.getName()
                + "(regulationGrp.entityId, regulationGrp.description,regulationGrp.label,regulationGrp.version,regulationGrp.creationDate,regulationGrp.modificationDate,regulationGrp.regulationgroupstatus.entityId,regulationGrp.regulationgroupstatus.label , regulationGrp.regulationgroupstatus.guiLabel)"
                + "from RegulationGroup regulationGrp ";

        TypedQuery<RegulationGroupRepresentation> queryResult = entityManager.createQuery(querry, RegulationGroupRepresentation.class);
        return queryResult.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder#getSearchedRegulationGroup(java.lang.String,
     *      com.inetpsa.poc00.rest.regulationgroup.ManageSearchRGRequestDto)
     */
    @Override
    public List<RegulationGroupRepresentation> getSearchedRegulationGroup(String searchLabel, ManageSearchRGRequestDto searchRGRepresentation) {

        String whereClauseSearchTVV;
        StringBuilder whereCluase = new StringBuilder();
        TvvSearchRepresentation searchRepresentation = searchRGRepresentation.getTvvSearchRepresentation();

        StringBuilder queryBuilderString = new StringBuilder("select  new " + RegulationGroupRepresentation.class.getName()
                + "(regulationGrp.entityId, regulationGrp.description,regulationGrp.label,regulationGrp.version,regulationGrp.creationDate,regulationGrp.modificationDate,regulationGrp.regulationgroupstatus.entityId,regulationGrp.regulationgroupstatus.label , regulationGrp.regulationgroupstatus.guiLabel) from RegulationGroup regulationGrp ");

        queryBuilderString.append(" Join regulationGrp.regulationgroupstatus status ");
        boolean searchTGWithTVV = tvvUtil.isSearchRepresentationEmpty(searchRepresentation);
        if (searchTGWithTVV)
            searchRepresentation = null;
        if (searchRepresentation != null) {
            queryBuilderString.append("join regulationGrp.technicalGroups techGroup Join techGroup.technicalCase techcase Join techcase.tvv t ");
            tvvUtil.buildJoinClause(searchRepresentation, queryBuilderString);
            whereClauseSearchTVV = tvvUtil.buildWhereclauseSearchTVV(searchRepresentation);
            whereCluase = new StringBuilder(whereClauseSearchTVV);

        }
        if (searchLabel != null && searchLabel.length() > 0) {
            whereCluase.append(" regulationGrp.label Like:searchLabel  AND");
        }

        if (whereCluase.length() > 0) {
            queryBuilderString.append(" WHERE ");
            if (whereCluase.toString().trim().endsWith("AND")) {
                queryBuilderString.append(whereCluase.substring(0, whereCluase.lastIndexOf("AND")));
            } else
                queryBuilderString.append(whereCluase);

        }

        if (searchRGRepresentation.isSortAlphabetically()) {
            queryBuilderString.append(" order by regulationGrp.label");
        } else if (searchRGRepresentation.isSortByDate()) {
            queryBuilderString.append(" order by regulationGrp.modificationDate desc");
        }

        TypedQuery<RegulationGroupRepresentation> typedQuery = null;
        try {
            typedQuery = entityManager.createQuery(queryBuilderString.toString(), RegulationGroupRepresentation.class);
        } catch (Exception e) {
            LOGGER.error("Error in searching Regulation Group", e);
            return new ArrayList<>();
        }

        if (searchRepresentation != null) {
            setSearchParametersRG(searchRepresentation, typedQuery);
        }
        if (searchLabel != null && searchLabel.length() > 0) {

            typedQuery.setParameter("searchLabel", searchLabel);

        }

        return typedQuery.getResultList();

    }

    /**
     * Sets the search parameters rg.
     * 
     * @param searchRepresentation the search representation
     * @param typedQuery the l_ typed query
     */
    private void setSearchParametersRG(TvvSearchRepresentation searchRepresentation, TypedQuery<RegulationGroupRepresentation> typedQuery) {
        // SET Parameters
        if (searchRepresentation.getTvvLabel() != null)
            typedQuery.setParameter("tvvLabel", searchRepresentation.getTvvLabel());
        if (searchRepresentation.getGearBoxList() != null && !searchRepresentation.getGearBoxList().isEmpty())
            typedQuery.setParameter("gearboxList", searchRepresentation.getGearBoxList());
        if (searchRepresentation.getBodyWorkList() != null && !searchRepresentation.getBodyWorkList().isEmpty())
            typedQuery.setParameter("bodyworkList", searchRepresentation.getBodyWorkList());
        if (searchRepresentation.getEngineList() != null && !searchRepresentation.getEngineList().isEmpty())
            typedQuery.setParameter("engineList", searchRepresentation.getEngineList());
        if (searchRepresentation.getFuelList() != null && !searchRepresentation.getFuelList().isEmpty())
            typedQuery.setParameter("fuelList", searchRepresentation.getFuelList());
        if (searchRepresentation.getPcFamilyList() != null && !searchRepresentation.getPcFamilyList().isEmpty())
            typedQuery.setParameter("pcFamilylist", searchRepresentation.getPcFamilyList());
        if (searchRepresentation.getEmsList() != null && !searchRepresentation.getEmsList().isEmpty())
            typedQuery.setParameter("emsList", searchRepresentation.getEmsList());
        if (searchRepresentation.getPgMaxLimits() != null && !searchRepresentation.getPgMaxLimits().isEmpty())
            typedQuery.setParameter("maxLimits", searchRepresentation.getPgMaxLimits());
        if (searchRepresentation.getPgMinLimits() != null && !searchRepresentation.getPgMinLimits().isEmpty())
            typedQuery.setParameter("minLimits", searchRepresentation.getPgMinLimits());
        if (searchRepresentation.getCoastDownList() != null && !searchRepresentation.getCoastDownList().isEmpty())
            typedQuery.setParameter("coastDownList", searchRepresentation.getCoastDownList());
        if (searchRepresentation.getInertiaLIst() != null && !searchRepresentation.getInertiaLIst().isEmpty())
            typedQuery.setParameter("inertiaList", searchRepresentation.getInertiaLIst());
        if (searchRepresentation.getTypeApprovalAreaList() != null && !searchRepresentation.getTypeApprovalAreaList().isEmpty())
            typedQuery.setParameter("areaList", searchRepresentation.getTypeApprovalAreaList());
        if (searchRepresentation.getCountryList() != null && !searchRepresentation.getCountryList().isEmpty())
            typedQuery.setParameter("countryList", searchRepresentation.getCountryList());
        if (searchRepresentation.isSearchForTg())
            typedQuery.setParameter("statuslabel", searchRepresentation.getStatuslabel());
    }

}

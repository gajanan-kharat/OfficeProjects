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
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.rest.technicalgroup.ManageSearchTGRequestDto;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvFinderUtil;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;

/**
 * The Class JPATechnicalGroupFinder.
 */
public class JPATechnicalGroupFinder implements TechnicalGroupFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPATechnicalGroupFinder.class);

    /** The tvv util. */
    TvvFinderUtil tvvUtil = new TvvFinderUtil();

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getTechnicalGroup(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TechnicalGroupRepresentation> getTechnicalGroup(String label) {
        String querry = "select new " + TechnicalGroupRepresentation.class.getName()
                + "(techgroup.entityId, techgroup.label,techgroup.description,techgroup.version,techgroup.samplingLabel,techgroup.techgroupstatus.entityId,techgroup.techgroupstatus.label,techgroup.techgroupstatus.guiLabel,techgroup.creationDate,techgroup.modificationDate,techgroup.regulationGroup.entityId)"
                + "from TechnicalGroup techgroup where techgroup.label=?1";

        TypedQuery<TechnicalGroupRepresentation> queryResult = entityManager.createQuery(querry, TechnicalGroupRepresentation.class);

        queryResult.setParameter(1, label);

        return queryResult.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#findTechnicalGroupById(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroupRepresentation findTechnicalGroupById(long id) {
        String querry = "select new " + TechnicalGroupRepresentation.class.getName()
                + "(techgroup.entityId, techgroup.label,techgroup.description,techgroup.version,"
                + " techgroup.samplingLabel,techgroup.techgroupstatus.entityId,techgroup.techgroupstatus.guiLabel,techgroup.techgroupstatus.label,techgroup.creationDate,"
                + " techgroup.modificationDate, techgroup.regulationGroup.entityId)" + "from TechnicalGroup techgroup where techgroup.entityId=?1";

        TypedQuery<TechnicalGroupRepresentation> queryResult = entityManager.createQuery(querry, TechnicalGroupRepresentation.class);

        queryResult.setParameter(1, id);

        List<TechnicalGroupRepresentation> tgRepresentationlist = queryResult.getResultList();
        if (tgRepresentationlist != null && !tgRepresentationlist.isEmpty()) {
            return tgRepresentationlist.get(0);
        } else {
            return null;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getMaxVersionForTGLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Double getMaxVersionForTGLabel(String tgLabel) {
        Query query = entityManager.createNativeQuery(Constants.MAX_TEHNICAL_GROUP_TABLE_VERSION_QUERY);
        query.setParameter(1, tgLabel);

        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getSearchedTechnicalGroup(com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation,
     * java.lang.String, com.inetpsa.poc00.rest.technicalgroup.ManageSearchTGRequestDto)
     */
    @Override
    public List<TechnicalGroupRepresentation> getSearchedTechnicalGroup(TvvSearchRepresentation searchRepresentation, String searchLabel,
            ManageSearchTGRequestDto searchTgRepresentation) {

        String statusOrtvvLabel = searchLabel;
        String whereClauseSearchTVV = null;
        StringBuilder whereCluase = new StringBuilder();

        StringBuilder queryBuilderString = new StringBuilder("select  new " + TechnicalGroupRepresentation.class.getName()
                + "(techgroup.entityId,techgroup.label,techgroup.description,techgroup.version,techgroup.samplingLabel,status.entityId,status.guiLabel,status.label,techgroup.creationDate,techgroup.modificationDate,techgroup.regulationGroup.entityId) from TechnicalGroup techgroup ");

        queryBuilderString.append(" Join techgroup.techgroupstatus status ");
        boolean searchTGWithTVV = tvvUtil.isSearchRepresentationEmpty(searchRepresentation);
        if (searchTGWithTVV)
            searchRepresentation = null;
        if (searchRepresentation != null) {
            queryBuilderString.append("Join techgroup.technicalCase techcase Join techcase.tvv t ");
            tvvUtil.buildJoinClause(searchRepresentation, queryBuilderString);
            whereClauseSearchTVV = tvvUtil.buildWhereclauseSearchTVV(searchRepresentation);
            whereCluase = new StringBuilder(whereClauseSearchTVV);

        }
        if (searchLabel != null && searchLabel.length() > 0) {
            whereCluase.append(" techgroup.label Like:searchLabel  AND");
        }

        if (searchTgRepresentation.isSearchForRG()) {

            whereCluase.append(" techgroup.techgroupstatus.label like(:statuslabel) and techgroup.regulationGroup IS NULL AND");

        }

        if (whereCluase.length() > 0) {
            queryBuilderString.append(" WHERE ");
            if (whereCluase.toString().trim().endsWith("AND")) {
                queryBuilderString.append(whereCluase.substring(0, whereCluase.lastIndexOf("AND")));
            } else
                queryBuilderString.append(whereCluase);

        }

        if (searchTgRepresentation.isSortAlphabetically()) {
            queryBuilderString.append(" order by techgroup.label");
        } else if (searchTgRepresentation.isSortByDate()) {
            queryBuilderString.append(" order by techgroup.modificationDate desc");
        }

        TypedQuery<TechnicalGroupRepresentation> l_TypedQuery = null;
        try {
            l_TypedQuery = entityManager.createQuery(queryBuilderString.toString(), TechnicalGroupRepresentation.class);
        } catch (Exception e) {
            LOGGER.error("Error in searching TechnicalGroup", e);
            return new ArrayList<TechnicalGroupRepresentation>();
        }

        if (searchRepresentation != null) {
            setSearchParametersTG(searchRepresentation, l_TypedQuery);
        }
        if (searchLabel != null && searchLabel.length() > 0) {

            l_TypedQuery.setParameter("searchLabel", searchLabel);

        }

        if (searchTgRepresentation.isSearchForRG()) {
            l_TypedQuery.setParameter("statuslabel", Constants.VALID);
        }
        return l_TypedQuery.getResultList();

    }

    /**
     * Sets the search parameters TG.
     * 
     * @param searchRepresentation the search representation
     * @param typedQuery the l typed query
     */
    private void setSearchParametersTG(TvvSearchRepresentation searchRepresentation, TypedQuery<TechnicalGroupRepresentation> typedQuery) {
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
        if ((searchRepresentation.getPgMaxLimits() != null && !searchRepresentation.getPgMaxLimits().isEmpty()))
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

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getTechnicalGroupToDelete(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TechnicalGroup> getTechnicalGroupToDelete(long regulationGroupId) {
        String querry = "select new " + TechnicalGroup.class.getName() + "(techgroup)"
                + "from TechnicalGroup techgroup where techgroup.regulationGroup.entityId=?1";

        TypedQuery<TechnicalGroup> queryResult = entityManager.createQuery(querry, TechnicalGroup.class);

        queryResult.setParameter(1, regulationGroupId);

        return queryResult.getResultList();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getTvvsForTechnicalGroup(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TvvRepresentation> getTvvsForTechnicalGroup(long technicalGroupId) {
        String querry = "select new " + TvvRepresentation.class.getName()
                + "(techcase.tvv.entityId,techcase.tvv.version,techcase.tvv.label,techcase.tvv.modificationDate,techcase.tvv.status.entityId,techcase.tvv.status.guiLabel,techcase.tvv.status.label,techcase.tvvWorstCase)"
                + "from TechnicalCase techcase  where techcase.technicalGroup.entityId=?1";

        TypedQuery<TvvRepresentation> queryResult = entityManager.createQuery(querry, TvvRepresentation.class);

        queryResult.setParameter(1, technicalGroupId);
        List<TvvRepresentation> tvvrepresentList = queryResult.getResultList();

        return tvvrepresentList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getTechnicalGroupForTechnicalCase(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup getTechnicalGroupForTechnicalCase(Long technicalCaseId) {
        TechnicalGroup tGroup = null;
        try {
            String queryString = "select new " + TechnicalGroup.class.getName() + "(techgroup)"
                    + "from TechnicalGroup techgroup join techgroup.technicalCase techCase where techCase.entityId=?1";

            TypedQuery<TechnicalGroup> query = entityManager.createQuery(queryString, TechnicalGroup.class);

            query.setParameter(1, technicalCaseId);
            tGroup = query.getSingleResult();

        } catch (Exception e) {
            // Exception is removed from logger as this is valid scenario,Cannot log Exception for this case
            LOGGER.warn("No Technical Group Found for given Technical Case");
        }
        return tGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder#getRegulationGroup(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<RegulationGroup> getRegulationGroup(long technicalGroupId) {
        String querry = "select techgroup.regulationGroup "
                + " from TechnicalGroup techgroup join techgroup.technicalCase techCase join techgroup.regulationGroup  regrp "
                + " where techgroup.entityId=?1" + " and techCase.emissionStandard.entityId=regrp.emissionStandardforRg.entityId";

        TypedQuery<RegulationGroup> query = entityManager.createQuery(querry, RegulationGroup.class);
        query.setParameter(1, technicalGroupId);

        return query.getResultList();
    }

}

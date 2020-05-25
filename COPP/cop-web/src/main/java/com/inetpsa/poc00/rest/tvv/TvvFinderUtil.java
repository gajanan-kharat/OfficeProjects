/*
 * Creation : Jul 1, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import javax.persistence.TypedQuery;

import com.inetpsa.poc00.common.Constants;

/**
 * The Class TvvFinderUtil.
 */
public class TvvFinderUtil {

    /**
     * Sets the search parameters TVV.
     * 
     * @param searchRepresentation the search representation
     * @param typedQuery the l typed query
     */
    public void setSearchParametersTVV(TvvSearchRepresentation searchRepresentation, TypedQuery<TvvRepresentation> typedQuery) {
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

    /**
     * Builds the join clause.
     * 
     * @param searchRepresentation the search representation
     * @param queryString the l content of jpql query
     */
    public void buildJoinClause(TvvSearchRepresentation searchRepresentation, StringBuilder queryString) {
        boolean caseJoinDone = false;
        boolean areaJoinDone = false;
        // Create Joins
        queryString.append(" left join t.testNature tnr ");
        // Join for Emission Standard
        if (searchRepresentation.getEmsList() != null && !searchRepresentation.getEmsList().isEmpty()) {
            // if (!caseJoinDone) {
            queryString.append(" join t.technicalCase tc join tc.emissionStandard standard ");
            caseJoinDone = true;
            /*
             * } else { l_ContentOfJpqlQuery.append(" join tc.emissionStandard standard "); caseJoinDone = true; }
             */
        }
        // Join for Pollutant Gas Limit
        if ((searchRepresentation.getPgMaxLimits() != null && !searchRepresentation.getPgMaxLimits().isEmpty())
                || (searchRepresentation.getPgMinLimits() != null && !searchRepresentation.getPgMinLimits().isEmpty())) {
            queryString.append(" join t.tvvValuedEsDepPGL pgl join pgl.pollutantGasLimit pglimit ");
        }

        if (searchRepresentation.getInertiaLIst() != null && !searchRepresentation.getInertiaLIst().isEmpty()) {
            queryString.append(" join t.tvvValuedCoastDown vcd join vcd.inertia inr");

        }
        // Join for Type Approval Area
        if (searchRepresentation.getTypeApprovalAreaList() != null && !searchRepresentation.getTypeApprovalAreaList().isEmpty()) {
            if (!caseJoinDone) {
                // if (!areaJoinDone)
                // {
                queryString.append(" join t.technicalCase tc join tc.technicalGroup tg join tg.regulationGroup.typeApprovalArea area");
                areaJoinDone = true;
                caseJoinDone = true;
            } else {
                queryString.append(" join tc.technicalGroup tg join tg.regulationGroup rg join rg.typeApprovalArea area");
                areaJoinDone = true;
            }

            // }
        }
        // Join for Country
        if (searchRepresentation.getCountryList() != null && !searchRepresentation.getCountryList().isEmpty()) {
            if (!areaJoinDone) {
                if (!caseJoinDone) {
                    queryString.append(" join t.technicalCase tc join tc.tvv tvv join tvv.countrySet cty");
                    areaJoinDone = true;
                    caseJoinDone = true;
                } else {
                    queryString.append(" join tc.tvv tvv join tvv.countrySet cty");
                    areaJoinDone = true;
                }

            } else {

                queryString.append(" t.technicalCase tc join tc.tvv tvv join tvv.countrySet cty");

            }
        }

    }

    /**
     * Builds the whereclause search TVV.
     * 
     * @param searchRepresentation the search representation
     * @return the string
     */
    public String buildWhereclauseSearchTVV(TvvSearchRepresentation searchRepresentation) {
        StringBuilder queryString = new StringBuilder();

        // Create WhereClause
        if (searchRepresentation.getTvvLabel() != null) {
            queryString.append("  t.label like :tvvLabel AND");
        }
        // For Emission Standard
        if (searchRepresentation.getEmsList() != null && !searchRepresentation.getEmsList().isEmpty()) {
            queryString.append(" CONCAT(standard.esLabel,standard.version)  in(:emsList) AND ");
        }
        // For GearBox
        if (searchRepresentation.getGearBoxList() != null && !searchRepresentation.getGearBoxList().isEmpty()) {
            queryString.append(" t.gearBox.label in(:gearboxList) AND ");
        }
        // For BodyWork
        if (searchRepresentation.getBodyWorkList() != null && !searchRepresentation.getBodyWorkList().isEmpty()) {
            queryString.append(" t.bodyWork.label in(:bodyworkList) AND ");
        }
        // For Engine
        if (searchRepresentation.getEngineList() != null && !searchRepresentation.getEngineList().isEmpty()) {
            queryString.append(" t.engine.engineLabel in(:engineList) AND ");
        }
        // For Fuel
        if (searchRepresentation.getFuelList() != null && !searchRepresentation.getFuelList().isEmpty()) {
            queryString.append(" t.fuel.label in(:fuelList) AND ");
        }
        // //For ProjectCode\vehicle Family
        if (searchRepresentation.getPcFamilyList() != null && !searchRepresentation.getPcFamilyList().isEmpty()) {
            queryString.append(" CONCAT (t.projectCodeFamily.projectCodeLabel,t.projectCodeFamily.vehicleFamilyLabel) in(:pcFamilylist) AND ");
        }
        // For Pollutant Gas Max Limit
        if ((searchRepresentation.getPgMaxLimits() != null && !searchRepresentation.getPgMaxLimits().isEmpty())) {
            queryString.append("  (pglimit.pgLabel.label='" + Constants.MAX_CO2_LIMIT + "' AND pglimit.maxDValue in(:maxLimits)) AND ");
        }
        // For Pollutant Gas MIN Limit
        if (searchRepresentation.getPgMinLimits() != null && !searchRepresentation.getPgMinLimits().isEmpty()) {
            queryString.append("  (pglimit.pgLabel.label='" + Constants.MIN_CO2_LIMIT + "' AND pglimit.minDValue in(:minLimits)) AND ");
        }
        // For Coast Down
        if (searchRepresentation.getCoastDownList() != null && !searchRepresentation.getCoastDownList().isEmpty()) {
            queryString.append(" CONCAT(t.tvvValuedCoastDown.pSAReference,t.tvvValuedCoastDown.version) in(:coastDownList) AND ");
        }
        // For Type Approval Area
        if (searchRepresentation.getTypeApprovalAreaList() != null && !searchRepresentation.getTypeApprovalAreaList().isEmpty()) {
            queryString.append(" area.label in(:areaList) AND ");
        }
        // // For Inertia
        if (searchRepresentation.getInertiaLIst() != null && !searchRepresentation.getInertiaLIst().isEmpty()) {
            queryString.append(" inr.value in(:inertiaList) AND ");
        }
        // For Country
        if (searchRepresentation.getCountryList() != null && !searchRepresentation.getCountryList().isEmpty()) {
            queryString.append(" cty.label in(:countryList) AND ");
        }
        // For TechnicalGroup
        if (searchRepresentation.isSearchForTg()) {
            queryString.append(" t.status.label like(:statuslabel) and t.technicalCase.technicalGroup IS NULL ");

        }

        return queryString.toString();
    }

    /**
     * Checks if is search representation empty.
     * 
     * @param tvvSearchRepresentation the tvv search representation
     * @return true, if is search representation empty
     */
    public boolean isSearchRepresentationEmpty(TvvSearchRepresentation tvvSearchRepresentation) {
        boolean isRepresentationEmpty = true;
        if (tvvSearchRepresentation.getTvvLabel() != null && tvvSearchRepresentation.getTvvLabel().length() > 0)
            isRepresentationEmpty = false;

        else if (tvvSearchRepresentation.getBodyWorkList() != null && !tvvSearchRepresentation.getBodyWorkList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getCoastDownList() != null && !tvvSearchRepresentation.getCoastDownList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getCountryList() != null && !tvvSearchRepresentation.getCountryList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getEmsList() != null && !tvvSearchRepresentation.getEmsList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getEngineList() != null && !tvvSearchRepresentation.getEngineList().isEmpty())
            isRepresentationEmpty = false;

        else if (tvvSearchRepresentation.getFuelList() != null && !tvvSearchRepresentation.getFuelList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getGearBoxList() != null && !tvvSearchRepresentation.getGearBoxList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getInertiaLIst() != null && !tvvSearchRepresentation.getInertiaLIst().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getPcFamilyList() != null && !tvvSearchRepresentation.getPcFamilyList().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getPgMaxLimits() != null && !tvvSearchRepresentation.getPgMaxLimits().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getPgMinLimits() != null && !tvvSearchRepresentation.getPgMinLimits().isEmpty())
            isRepresentationEmpty = false;
        else if (tvvSearchRepresentation.getTypeApprovalAreaList() != null && !tvvSearchRepresentation.getTypeApprovalAreaList().isEmpty())
            isRepresentationEmpty = false;

        return isRepresentationEmpty;
    }
}

/*
 * Creation : 31 May 2017
 */
package com.psa.PSATEStructCompare;

import java.util.Date;

public class PSAFilterEntity {

    private String filterName;
    private String filterRev;
    private String filterMode;
    private Date filterCreationDate;
    private Date filterStartDate;
    private Date filterNextLaunchDate;
    private String filterUser;
    private Date filterModificationDate;

    @Override
    public String toString() {
        return "PSAFilterEntity [m_strPSAFilterName=" + filterName + ", m_strPSAFilterRev=" + filterRev + ", m_strPSAFilterMode=" + filterMode
                + ", m_strPSAFilterCreationDate=" + filterCreationDate + ", m_strPSAFilterStartDate=" + filterStartDate
                + ", m_strPSAFilterNextLaunchDate=" + filterNextLaunchDate + ", m_strPSAFilterUser=" + filterUser
                + ", m_strPSAFilterModificationDate=" + filterModificationDate + "]";
    }

    public String getUser() {
        return filterUser = System.getenv("USER");
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterRev() {
        return filterRev;
    }

    public void setFilterRev(String filterRev) {
        this.filterRev = filterRev;
    }

    public String getFilterMode() {
        return filterMode;
    }

    public void setFilterMode(String filterMode) {
        this.filterMode = filterMode;
    }

    public Date getFilterCreationDate() {
        return filterCreationDate;
    }

    public void setFilterCreationDate(Date filterCreationDate) {
        this.filterCreationDate = filterCreationDate;
    }

    public Date getFilterStartDate() {
        return filterStartDate;
    }

    public void setFilterStartDate(Date filterStartDate) {
        this.filterStartDate = filterStartDate;
    }

    public Date getFilterNextLaunchDate() {
        return filterNextLaunchDate;
    }

    public void setFilterNextLaunchDate(Date filterNextLaunchDate) {
        this.filterNextLaunchDate = filterNextLaunchDate;
    }

    public String getFilterUser() {
        return filterUser;
    }

    public void setFilterUser(String filterUser) {
        this.filterUser = filterUser;
    }

    public Date getFilterModificationDate() {
        return filterModificationDate;
    }

    public void setFilterModificationDate(Date filterModificationDate) {
        this.filterModificationDate = filterModificationDate;
    }

}

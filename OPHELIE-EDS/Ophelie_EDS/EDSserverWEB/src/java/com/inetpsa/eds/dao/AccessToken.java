/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao;

import com.inetpsa.eds.dao.model.EdsUser;
import java.util.Date;

/**
 * This class provide access token
 * 
 * @author Geometric Ltd.
 */
public class AccessToken {
    // PUBLIC
    /**
     * Static class providing access token ID
     * 
     * @author Geometric Ltd.
     */
    public static class AccessTokenId {
        /**
         * Parameterized constructor
         * 
         * @param edsId Eds id
         * @param formId Form id
         */
        public AccessTokenId(String edsId, String formId) {
            this.edsId = edsId;
            this.formId = formId;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final AccessTokenId other = (AccessTokenId) obj;
            if ((this.edsId == null) ? (other.edsId != null) : !this.edsId.equals(other.edsId)) {
                return false;
            }
            if ((this.formId == null) ? (other.formId != null) : !this.formId.equals(other.formId)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + (this.edsId != null ? this.edsId.hashCode() : 0);
            hash = 89 * hash + (this.formId != null ? this.formId.hashCode() : 0);
            return hash;
        }

        /**
         * Variable to hold value of EDS id
         */
        private String edsId;
        /**
         * Variable to hold value of Form id
         */
        private String formId;
    }

    /**
     * Parameterized constructor
     * 
     * @param edsId Eds id
     * @param formId Form id
     * @param endDate End date
     * @param callerUser Eds user details
     */
    public AccessToken(String edsId, String formId, Date endDate, EdsUser callerUser) {
        this.edsId = edsId;
        this.formId = formId;
        this.endDate = endDate;
        this.callerUser = callerUser;

        init();
    }

    /**
     * This method returns Eds user details
     * 
     * @return Eds user details
     */
    public EdsUser getCallerUser() {
        return callerUser;
    }

    /**
     * This method returns End date
     * 
     * @return End date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method returns Eds id
     * 
     * @return Eds id
     */
    public String getEdsId() {
        return edsId;
    }

    /**
     * This method return form id
     * 
     * @return form id
     */
    public String getFormId() {
        return formId;
    }

    /**
     * This method Set Eds user details
     * 
     * @param callerUser Eds user details
     */
    public void setCallerUser(EdsUser callerUser) {
        this.callerUser = callerUser;
    }

    /**
     * This method Set End date
     * 
     * @param endDate End date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method create access token id
     * 
     * @return access token id
     */
    public AccessTokenId createId() {
        return new AccessTokenId(edsId, formId);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Eds id
     */
    private String edsId;
    /**
     * Variable to hold value of Form id
     */
    private String formId;
    /**
     * Variable to hold value of End date
     */
    private Date endDate;
    /**
     * Variable to hold value of Eds User details
     */
    private EdsUser callerUser;

    /**
     * Initialize AccessToken
     */
    private void init() {
    }
}

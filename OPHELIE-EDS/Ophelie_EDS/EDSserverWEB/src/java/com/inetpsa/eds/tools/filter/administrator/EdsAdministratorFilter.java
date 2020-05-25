package com.inetpsa.eds.tools.filter.administrator;

import java.text.MessageFormat;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter for an administrator sheet.
 * 
 * @author Geometric Ltd.
 */
public class EdsAdministratorFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsManager Eds user
     * @param controller Controller of EDS application
     */
    public EdsAdministratorFilter(EdsUser edsManager, EdsApplicationController controller) {
        super(controller);
        this.edsAdmin = edsManager;
        init();
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @param criteriaQuery The application in which to add the current filter.
     * @return The modified query.
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        return criteriaQuery.createAlias(propertyName, "admin").add(Restrictions.eq("admin.id", edsAdmin.getUId()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-admin-title"), edsAdmin.toFullIdentity());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + getClass().getName().hashCode();
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return describe();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of Property name
     */
    private static final String propertyName = "edsUserByEdsAdminId";
    /**
     * Variable to hold value of Eds user
     */
    private EdsUser edsAdmin;

    /**
     * Initialize Eds administrator filter
     */
    private void init() {
    }
}

package com.inetpsa.eds.tools.filter.manager;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.dao.model.EdsUser;
import java.text.MessageFormat;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in a PSA responsible.
 * 
 * @author Geometric Ltd.
 */
public class EdsManagerFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsManager Eds user details
     * @param controller Controller of EDS application
     */
    public EdsManagerFilter(EdsUser edsManager, EdsApplicationController controller) {
        super(controller);
        this.edsManager = edsManager;
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
        return criteriaQuery.createAlias(propertyName, "manager").add(Restrictions.eq("manager.id", edsManager.getUId()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-resp-name"), edsManager.toFullIdentity());
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
        int hash = 7;
        hash = 59 * hash + getClass().getName().hashCode();
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
     * Constant to hold value of property name
     */
    private static final String propertyName = "edsUserByEdsManagerId";
    /**
     * Variable to hold value of EdsUser
     */
    private EdsUser edsManager;

    /**
     * Initialize filter for PSA responsible.
     */
    private void init() {
    }
}

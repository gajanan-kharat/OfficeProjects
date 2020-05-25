package com.inetpsa.eds.tools.filter.owner;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsUser;
import java.text.MessageFormat;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug according to the owner of the card. This filter is fixed and is not available in the filter editor.
 * 
 * @author Geometric Ltd.
 */
public class EdsOwnerFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsManager Eds user details
     * @param controller Controller of EDS application
     */
    public EdsOwnerFilter(EdsUser edsManager, EdsApplicationController controller) {
        super(controller);
        this.edsOwner = edsManager;
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
        Junction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.sqlRestriction("{alias}.EDS_ADMIN_ID=?", edsOwner.getUId(), Hibernate.STRING));
        disjunction.add(Restrictions.sqlRestriction("{alias}.EDS_MANAGER_ID=?", edsOwner.getUId(), Hibernate.STRING));
        disjunction.add(Restrictions.sqlRestriction("{alias}.EDS_OFFICER_ID=?", edsOwner.getUId(), Hibernate.STRING));

        return criteriaQuery.add(disjunction).add(Restrictions.ne("edsState", EdsEds.FROZEN_STATE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-owner-name"), edsOwner.toFullIdentity());
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
     * Constant to hold value of admin property name
     */
    private static final String adminPropertyName = "edsUserByEdsAdminId";
    /**
     * Constant to hold value of manager property name
     */
    private static final String managerPropertyName = "edsUserByEdsManagerId";
    /**
     * Constant to hold value of officer property name
     */
    private static final String officerPropertyName = "edsUserByEdsOfficerId";
    /**
     * Variable to hold value of EdsUser
     */
    private EdsUser edsOwner;

    /**
     * Initialize filter plug according to the owner of the card
     */
    private void init() {
        this.setRemoveable(false);
    }
}

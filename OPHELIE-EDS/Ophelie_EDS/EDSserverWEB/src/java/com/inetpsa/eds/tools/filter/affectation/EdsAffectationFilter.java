package com.inetpsa.eds.tools.filter.affectation;

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
 * Filter plug according to the assignment of the plug.
 * 
 * @author Geometric Ltd.
 */
public class EdsAffectationFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsAffectation Eds User
     * @param controller Controller of EDS application
     */
    public EdsAffectationFilter(EdsUser edsAffectation, EdsApplicationController controller) {
        super(controller);
        this.edsAffectation = edsAffectation;
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
        disjunction.add(Restrictions.sqlRestriction("{alias}.EDS_AFF_USER_ID=?", edsAffectation.getUId(), Hibernate.STRING));

        return criteriaQuery.add(disjunction).add(Restrictions.ne("edsState", EdsEds.FROZEN_STATE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-affectation-name"), edsAffectation.toFullIdentity());
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
     * Constant to hold value of affectationPropertyName
     */
    private static final String affectationPropertyName = "edsUserByEdsAffectationUserId";
    /**
     * Variable to hold value of Eds user.
     */
    private EdsUser edsAffectation;

    /**
     * Initialize Eds affectation filter
     */
    private void init() {
    }
}

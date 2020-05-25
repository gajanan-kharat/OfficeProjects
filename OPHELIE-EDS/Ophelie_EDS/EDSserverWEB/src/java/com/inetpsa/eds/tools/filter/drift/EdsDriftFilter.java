package com.inetpsa.eds.tools.filter.drift;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in according to Drifted EDS
 * 
 * @author Geometric Ltd.
 */
public class EdsDriftFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public EdsDriftFilter(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        return criteriaQuery.add(Restrictions.eq("edsHasDrift", 1)).add(Restrictions.ne("edsState", EdsEds.FROZEN_STATE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return controller.getBundle().getString("menu-app-der");
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

    // PROTECTED
    // PRIVATE
    /**
     * Initialize filter for Drifted EDS
     */
    private void init() {
        this.setRemoveable(false);
    }
}

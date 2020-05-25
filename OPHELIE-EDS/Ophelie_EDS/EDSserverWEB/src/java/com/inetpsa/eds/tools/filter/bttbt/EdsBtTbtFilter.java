package com.inetpsa.eds.tools.filter.bttbt;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 * Filter plug according to a network type.
 * 
 * @author Geometric Ltd.
 */
public class EdsBtTbtFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsIsBttbt Variable for Network type
     * @param controller Controller of EDS application
     */
    public EdsBtTbtFilter(Integer edsIsBttbt, EdsApplicationController controller) {
        super(controller);
        this.edsIsBttbt = edsIsBttbt;
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
        return criteriaQuery.add(name.eq(edsIsBttbt));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        switch (edsIsBttbt) {
        case 0:
            return controller.getBundle().getString("filter-no-bt-tbt");
        case 1:
            return controller.getBundle().getString("filter-bt-tbt");
        default:
            throw new IllegalStateException(controller.getBundle().getString("filter-bt-tbt-error") + ": " + edsIsBttbt);
        }
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
        hash = 41 * hash + getClass().getName().hashCode();
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
     * Constant to hold value for property name
     */
    private static final Property name = Property.forName("edsIsBttbt");
    /**
     * Variable to hold value of network type
     */
    private Integer edsIsBttbt;

    /**
     * Initialize filter for Network type
     */
    private void init() {
    }
}

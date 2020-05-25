package com.inetpsa.eds.tools.filter.number96k;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import java.text.MessageFormat;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in a number 96XXX.
 * 
 * @author Geometric Ltd.
 */
public class Eds96KFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param n96K String to hold value of n96k
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public Eds96KFilter(String n96K, int filterType, EdsApplicationController controller) {
        super(controller);
        this.filterType = filterType;
        this.n96K = n96K;
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
        switch (filterType) {
        case EdsFilterManager.FILTER_EQUALS:
            return criteriaQuery.createAlias(propertyName, "n96").add(Restrictions.eq("n96.NValue", n96K));
        case EdsFilterManager.FILTER_ISLIKE:
            return criteriaQuery.createAlias(propertyName, "n96").add(Restrictions.like("n96.NValue", '%' + n96K + '%'));
        default:
            throw new IllegalArgumentException(MessageFormat.format(controller.getBundle().getString("filter-eds-name-messsage"), getClass()
                    .getSimpleName(), EdsFilterManager.getFilterTypeName(filterType), filterType));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        switch (filterType) {
        case EdsFilterManager.FILTER_EQUALS:
            return MessageFormat.format(controller.getBundle().getString("filter-eds-96k-equal"), n96K);
        case EdsFilterManager.FILTER_ISLIKE:
            return MessageFormat.format(controller.getBundle().getString("filter-eds-96k-in"), n96K);
        default:
            return null;
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
        int hash = 5;
        hash = 17 * hash + getClass().getName().hashCode();
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
    private static final String propertyName = "edsNumber96ks";
    /**
     * Variable to hold value of type of filter
     */
    private int filterType;

    /**
     * Variable to hold value of n96K
     */
    private String n96K;

    /**
     * Initialize filter for a number 96XXX.
     */
    private void init() {
    }
}

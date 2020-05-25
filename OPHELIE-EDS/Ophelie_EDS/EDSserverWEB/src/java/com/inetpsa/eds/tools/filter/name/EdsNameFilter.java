package com.inetpsa.eds.tools.filter.name;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import java.text.MessageFormat;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 * Filter plug according to a name.
 * 
 * @author Geometric Ltd.
 */
public class EdsNameFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsName Name of EDS
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public EdsNameFilter(String edsName, int filterType, EdsApplicationController controller) {
        super(controller);
        this.filterType = filterType;
        this.edsName = edsName;
        init();
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @param criteriaQuery The application in which to add the filter.
     * @return The modified query.
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        switch (filterType) {
        case EdsFilterManager.FILTER_EQUALS:
            return criteriaQuery.add(name.eq(edsName));
        case EdsFilterManager.FILTER_ISLIKE:
            return criteriaQuery.add(name.like('%' + edsName + '%'));
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
            return MessageFormat.format(controller.getBundle().getString("filter-eds-name-equal"), edsName);
        case EdsFilterManager.FILTER_ISLIKE:
            return MessageFormat.format(controller.getBundle().getString("filter-eds-name-in"), edsName);
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
    private static final Property name = Property.forName("edsName");
    /**
     * Variable to hold value of type of filter
     */
    private int filterType;
    /**
     * Variable to hold value of name of EDS
     */
    private String edsName;

    /**
     * Initialize filter for name
     */
    private void init() {
    }
}

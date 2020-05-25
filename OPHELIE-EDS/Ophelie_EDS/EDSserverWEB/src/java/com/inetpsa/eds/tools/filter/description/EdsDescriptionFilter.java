package com.inetpsa.eds.tools.filter.description;

import java.text.MessageFormat;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;

/**
 * Filter plug according to a description.
 */
public class EdsDescriptionFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsDescription Description of EDS
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public EdsDescriptionFilter(String edsDescription, int filterType, EdsApplicationController controller) {
        super(controller);
        this.filterType = filterType;
        this.edsDescription = edsDescription;
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
            return criteriaQuery.add(description.eq(edsDescription));
        case EdsFilterManager.FILTER_ISLIKE:
            return criteriaQuery.add(description.like('%' + edsDescription + '%'));
        default:
            throw new IllegalArgumentException(MessageFormat.format(controller.getBundle().getString("filter-eds-description-messsage"), getClass()
                    .getSimpleName(), EdsFilterManager.getFilterTypeName(filterType), filterType));
        }
    }

    @Override
    public String describe() {
        switch (filterType) {
        case EdsFilterManager.FILTER_EQUALS:
            return MessageFormat.format(controller.getBundle().getString("filter-eds-description-equal"), edsDescription);
        case EdsFilterManager.FILTER_ISLIKE:
            return MessageFormat.format(controller.getBundle().getString("filter-eds-description-in"), edsDescription);
        default:
            return null;
        }
    }

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + getClass().getName().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return describe();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of property description
     */
    private static final Property description = Property.forName("edsDescription");
    /**
     * Variable to hold value of type of filter
     */
    private int filterType;
    /**
     * Variable to hold value of name of EDS
     */
    private String edsDescription;

    /**
     * Initialize filter for name
     */
    private void init() {
    }
}

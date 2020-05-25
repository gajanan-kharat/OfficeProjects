package com.inetpsa.eds.tools.filter.chs.nbembases;

import java.text.MessageFormat;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.Type;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.ChsPin;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;

/**
 * Filter plug according to a name.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class ChsNbEmbasesFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param edsName Name of EDS
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public ChsNbEmbasesFilter(String edsName, int filterType, EdsApplicationController controller) {
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
        DetachedCriteria a = DetachedCriteria.forClass(ChsPin.class);
        // Might remove portability advantages but it was the best solution found.
        String groupBy = "COMPONENT_ID having count(DISTINCT CONNECTION_NAME) = " + Integer.parseInt(edsName);
        String[] alias = new String[1];
        alias[0] = "COMPONENT_ID";
        Type[] types = new Type[1];
        types[0] = Hibernate.STRING;

        a.setProjection(Projections.sqlGroupProjection("COMPONENT_ID", groupBy, alias, types));
        criteriaQuery.add(Subqueries.propertyIn("componentId", a));

        return criteriaQuery;
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
            return MessageFormat.format(controller.getBundle().getString("chs-filter-embnum-equal"), edsName);
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
    private static final Property name = Property.forName("cavities");
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

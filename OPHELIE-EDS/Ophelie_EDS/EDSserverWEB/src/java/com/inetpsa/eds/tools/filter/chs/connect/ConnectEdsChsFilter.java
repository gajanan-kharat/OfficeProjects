package com.inetpsa.eds.tools.filter.chs.connect;

import org.hibernate.criterion.DetachedCriteria;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug according to a CHS Description.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class ConnectEdsChsFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param chs Name of EDS
     * @param filterType Type of filter
     * @param controller Controller of EDS application
     */
    public ConnectEdsChsFilter(Chs chs, EdsApplicationController controller) {
        super(controller);
        this.chs = chs;
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
        return null;
        // switch (filterType) {
        // case EdsFilterManager.FILTER_EQUALS:
        // return criteriaQuery.add(name.eq(edsName));
        // case EdsFilterManager.FILTER_ISLIKE:
        // return criteriaQuery.add(name.like('%' + edsName + '%'));
        // default:
        // throw new IllegalArgumentException(MessageFormat.format(controller.getBundle().getString("filter-eds-name-messsage"), getClass()
        // .getSimpleName(), EdsFilterManager.getFilterTypeName(filterType), filterType));
        // }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return chs.getPartNumber();
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
        ConnectEdsChsFilter other = (ConnectEdsChsFilter) obj;
        if (!getChs().equals(other.getChs())) {
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
     * Variable to hold value of name of EDS
     */
    private Chs chs;

    /**
     * Initialize filter for name
     */
    private void init() {
    }

    public Chs getChs() {
        return chs;
    }

    public void setChs(Chs chs) {
        this.chs = chs;
    }

}

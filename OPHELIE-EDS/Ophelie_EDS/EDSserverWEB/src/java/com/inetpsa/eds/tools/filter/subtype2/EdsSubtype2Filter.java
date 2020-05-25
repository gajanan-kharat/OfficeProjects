package com.inetpsa.eds.tools.filter.subtype2;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import java.text.MessageFormat;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 * Filter plug in according to subtype 2.
 * 
 * @author Geometric Ltd.
 */
public class EdsSubtype2Filter extends A_EdsFilter {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param subtype Subtype value
     * @param controller Controller of EDSPratibha.Ahirwar@geometricglobal.com application
     */
    public EdsSubtype2Filter(Integer subtype, EdsApplicationController controller) {
        super(controller);
        this.subtype = subtype;
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
        return criteriaQuery.add(edsSubtype1.eq(subtype));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-type1-name"), EdsApplicationController.getSubtype2Text(subtype));
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
        final EdsSubtype2Filter other = (EdsSubtype2Filter) obj;
        if (this.subtype != other.subtype && (this.subtype == null || !this.subtype.equals(other.subtype))) {
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
        hash = 71 * hash + getClass().getName().hashCode();
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
     * Constant variable that hold eds subtype1 property value
     */
    private static final Property edsSubtype1 = Property.forName("edsSubtype2");
    /**
     * Variable to hold value of subtype
     */
    private Integer subtype;

    /**
     * Initialize EDS subtype 2 filter
     */
    private void init() {
    }
}

package com.inetpsa.eds.tools.filter.unmodifdate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in for date not to be changed
 * 
 * @author Geometric Ltd.
 */
public class EdsUnmodifDateFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param startDate Start date
     * @param endDate End date
     * @param controller Controller of EDS application
     */
    public EdsUnmodifDateFilter(Date startDate, Date endDate, EdsApplicationController controller) {
        super(controller);
        this.startDate = startDate;
        this.endDate = endDate;
        init();
    }

    /**
     * This method set the interval i.e, start date and end date
     * 
     * @param startDate Start date
     * @param endDate End date
     */
    public void setInterval(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @param criteriaQuery The application in which to add the filter.
     * @return The modified query.
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        if (startDate == null) {
            if (endDate == null) {
                throw new IllegalStateException(String.format(controller.getBundle().getString("filter-date-messge-error"), null, null));
            }
            return criteriaQuery.add(Restrictions.not(modifDate.lt(endDate)));
        } else if (endDate == null) {
            return criteriaQuery.add(Restrictions.not(modifDate.ge(startDate)));
        } else if (startDate.after(endDate)) {
            throw new IllegalStateException(String.format(controller.getBundle().getString("filter-date-messge-error"), null, null));
        }

        return criteriaQuery.add(Restrictions.or(modifDate.lt(startDate), modifDate.gt(endDate)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        if (startDate == null) {
            if (endDate == null) {
                throw new IllegalStateException(String.format(controller.getBundle().getString("filter-date-messge-error"), null, null));
            }
            return MessageFormat.format(controller.getBundle().getString("filter-not-modified-date-befor"), df.format(endDate));
        } else if (endDate == null) {
            return MessageFormat.format(controller.getBundle().getString("filter-not-modified-date-after"), df.format(startDate));
        }
        return MessageFormat.format(controller.getBundle().getString("filter-not-modified-date-between"), df.format(startDate), df.format(endDate));
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
        hash = 53 * hash + getClass().getName().hashCode();
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
     * Constant variable to hold modifDate property value
     */
    private static final Property modifDate = Property.forName("edsModifDate");
    /**
     * Variable to hold simple date formatter value
     */
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Variable to hold start date
     */
    private Date startDate;
    /**
     * Variable to hold end date
     */
    private Date endDate;

    /**
     * Initialize EDS unmodified date filter
     */
    private void init() {
    }
}

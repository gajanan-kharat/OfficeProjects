package com.inetpsa.eds.tools.filter.fixedunmodifieddate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.unmodifdate.EdsUnmodifDateFilter;
import com.inetpsa.eds.dao.model.EdsEds;
import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in for a date not to be changed.
 * 
 * @author Geometric Ltd.
 */
public class EdsFixedUnmodifDateFilter extends EdsUnmodifDateFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param startDate Start date
     * @param endDate End date
     * @param controller Controller of EDS application
     */
    public EdsFixedUnmodifDateFilter(Date startDate, Date endDate, EdsApplicationController controller) {
        super(startDate, endDate, controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.unmodifdate.EdsUnmodifDateFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        return super.buildQuery(criteriaQuery.add(Restrictions.eq("edsState", EdsEds.DEFAULT_STATE)));
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize filter for date not to be changed
     */
    private void init() {
        this.setRemoveable(false);
    }
}

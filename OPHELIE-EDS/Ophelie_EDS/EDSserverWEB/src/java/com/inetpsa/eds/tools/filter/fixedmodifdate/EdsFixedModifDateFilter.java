package com.inetpsa.eds.tools.filter.fixedmodifdate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.modifdate.EdsModifDateFilter;
import com.inetpsa.eds.dao.model.EdsEds;
import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in for a modification date.
 * 
 * @author Geometric Ltd.
 */
public class EdsFixedModifDateFilter extends EdsModifDateFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param startDate Start date
     * @param endDate eds date
     * @param controller Controller of EDS application
     */
    public EdsFixedModifDateFilter(Date startDate, Date endDate, EdsApplicationController controller) {
        super(startDate, endDate, controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.modifdate.EdsModifDateFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        return super.buildQuery(criteriaQuery.add(Restrictions.eq("edsState", EdsEds.DEFAULT_STATE)));
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize filter for fixed modification date
     */
    private void init() {
        this.setRemoveable(false);
    }
}

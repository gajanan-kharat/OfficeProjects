package com.inetpsa.eds.tools.filter.pending;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in EDS in process.This filter is fixed and is not available in the filter editor.
 * 
 * @author Geometric Ltd.
 */
public class EdsPendingFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsPendingFilter(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        DetachedCriteria lowValidationCriteria = DetachedCriteria.forClass(EdsLowValidationFormData.class, "lvfd");
        lowValidationCriteria.add(Restrictions.ne("lvfdStage", EdsApplicationController.CLOSED_STAGE)).setProjection(Property.forName("edsEds.id"));

        DetachedCriteria highValidationCriteria = DetachedCriteria.forClass(EdsHighValidationFormData.class, "hvfd");
        highValidationCriteria.add(Restrictions.ne("hvfdStage", EdsApplicationController.CLOSED_STAGE)).setProjection(Property.forName("edsEds.id"));

        Junction disjunction = Restrictions.disjunction();
        disjunction.add(Property.forName("edsId").in(lowValidationCriteria));
        disjunction.add(Property.forName("edsId").in(highValidationCriteria));

        return criteriaQuery.add(disjunction).add(Restrictions.ne("edsState", EdsEds.FROZEN_STATE))
                .add(Restrictions.ne("edsState", EdsEds.ABORTED_STATE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return controller.getBundle().getString("menu-app-dur-Fich");
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
        int hash = 3;
        hash = 67 * hash + getClass().getName().hashCode();
        return hash;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Filter plug in EDS in process
     */
    private void init() {
        this.setRemoveable(false);
    }
}

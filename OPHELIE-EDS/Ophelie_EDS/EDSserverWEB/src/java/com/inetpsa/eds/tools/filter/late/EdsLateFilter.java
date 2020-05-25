package com.inetpsa.eds.tools.filter.late;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug in for Delayed Eds.
 * 
 * @author Geometric Ltd.
 */
public class EdsLateFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsLateFilter(EdsApplicationController controller) {
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
        DetachedCriteria lowValidationPreliminaryCriteria = DetachedCriteria.forClass(EdsLowValidationFormData.class, "lvfd");
        lowValidationPreliminaryCriteria.add(Restrictions.lt("lvfdStage", EdsApplicationController.ROBUST_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        DetachedCriteria highValidationPreliminaryCriteria = DetachedCriteria.forClass(EdsHighValidationFormData.class, "hvfd");
        highValidationPreliminaryCriteria.add(Restrictions.lt("hvfdStage", EdsApplicationController.ROBUST_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        DetachedCriteria lowValidationRobusteCriteria = DetachedCriteria.forClass(EdsLowValidationFormData.class, "lvfd");
        lowValidationRobusteCriteria.add(Restrictions.lt("lvfdStage", EdsApplicationController.SOLID_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        DetachedCriteria highValidationRobusteCriteria = DetachedCriteria.forClass(EdsHighValidationFormData.class, "hvfd");
        highValidationRobusteCriteria.add(Restrictions.lt("hvfdStage", EdsApplicationController.SOLID_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        DetachedCriteria lowValidationConsolideCriteria = DetachedCriteria.forClass(EdsLowValidationFormData.class, "lvfd");
        lowValidationConsolideCriteria.add(Restrictions.lt("lvfdStage", EdsApplicationController.CLOSED_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        DetachedCriteria highValidationConsolideCriteria = DetachedCriteria.forClass(EdsHighValidationFormData.class, "hvfd");
        highValidationConsolideCriteria.add(Restrictions.lt("hvfdStage", EdsApplicationController.CLOSED_STAGE)).setProjection(
                Property.forName("edsEds.id"));

        Junction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.and(Property.forName("edsId").in(lowValidationPreliminaryCriteria),
                Restrictions.lt("ep.PDateFirstStage", new Date())));

        disjunction.add(Restrictions.and(Property.forName("edsId").in(highValidationPreliminaryCriteria),
                Restrictions.lt("ep.PDateFirstStage", new Date())));

        disjunction.add(Restrictions.and(Property.forName("edsId").in(lowValidationRobusteCriteria),
                Restrictions.lt("ep.PDateSecondStage", new Date())));

        disjunction.add(Restrictions.and(Property.forName("edsId").in(highValidationRobusteCriteria),
                Restrictions.lt("ep.PDateSecondStage", new Date())));

        disjunction.add(Restrictions.and(Property.forName("edsId").in(lowValidationConsolideCriteria),
                Restrictions.lt("ep.PDateThirdStage", new Date())));

        disjunction.add(Restrictions.and(Property.forName("edsId").in(highValidationConsolideCriteria),
                Restrictions.lt("ep.PDateThirdStage", new Date())));

        criteriaQuery.createAlias("edsProject", "ep");

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
        return controller.getBundle().getString("menu-app-late");
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
     * Initialize filter for Delayed EDS
     */
    private void init() {
        this.setRemoveable(false);
    }
}

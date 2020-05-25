package com.inetpsa.eds.tools.filter.stage;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import java.text.MessageFormat;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Filter plug according to a validation stage.
 * 
 * @author Geometric Ltd.
 */
public class EdsStageFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param stage Stage value
     * @param controller Controller of EDS application
     */
    public EdsStageFilter(Integer stage, EdsApplicationController controller) {
        super(controller);
        this.stage = stage;
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
        DetachedCriteria lowValidationCriteria = DetachedCriteria.forClass(EdsLowValidationFormData.class, "lvfd");
        lowValidationCriteria.add(Restrictions.eq("lvfdStage", stage)).setProjection(Property.forName("edsEds.id"));

        DetachedCriteria highValidationCriteria = DetachedCriteria.forClass(EdsHighValidationFormData.class, "hvfd");
        highValidationCriteria.add(Restrictions.eq("hvfdStage", stage)).setProjection(Property.forName("edsEds.id"));

        Junction disjunction = Restrictions.disjunction();
        disjunction.add(Property.forName("edsId").in(lowValidationCriteria));
        disjunction.add(Property.forName("edsId").in(highValidationCriteria));

        return criteriaQuery.add(disjunction).add(Restrictions.ne("edsState", EdsEds.FROZEN_STATE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        if (stage == null) {
            throw new IllegalStateException(MessageFormat.format(controller.getBundle().getString("filter-stage-error"), stage));
        }
        return MessageFormat.format(controller.getBundle().getString("filter-stage-name"), EdsApplicationController.getStageText(stage));
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
     * Variable to hold value of edsStage property
     */
    private static final Property edsStage = Property.forName("edsStage");
    /**
     * variable to hold Stage value
     */
    private Integer stage;

    /**
     * Initialize Eds Stage Filter
     */
    private void init() {
    }
}

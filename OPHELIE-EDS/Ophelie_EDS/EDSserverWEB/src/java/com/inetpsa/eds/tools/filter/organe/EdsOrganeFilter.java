package com.inetpsa.eds.tools.filter.organe;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Locale;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug according to a body.
 * 
 * @author Geometric Ltd.
 */
public class EdsOrganeFilter extends A_EdsFilter {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsOrganeFilter(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param organe EdsWording
     * @param controller Controller of EDS application
     */
    EdsOrganeFilter(EdsWording organe, EdsApplicationController controller) {
        super(controller);
        this.organe = organe;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsWording
     */
    private EdsWording organe;

    /**
     * Initialize filter plug according to a body.
     */
    private void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#buildQuery(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public DetachedCriteria buildQuery(DetachedCriteria criteriaQuery) {
        HashSet<String> elligibleEdses = new HashSet<String>();

        // Retrieving Eds whose launcher project is project
        DetachedCriteria mainProjectSubQuery = DetachedCriteria.forClass(EdsEds.class);
        mainProjectSubQuery.setProjection(Property.forName("edsId"));
        mainProjectSubQuery.createAlias("edsOrganFamily", "organe");
        mainProjectSubQuery.add(Restrictions.eq("organe.id", organe.getWId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(mainProjectSubQuery, String.class));

        elligibleEdses.add("fix-bug");
        return criteriaQuery.add(Restrictions.in("id", elligibleEdses));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-organe-name"), organe.getValueByLocale(Locale.FRENCH));
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
}

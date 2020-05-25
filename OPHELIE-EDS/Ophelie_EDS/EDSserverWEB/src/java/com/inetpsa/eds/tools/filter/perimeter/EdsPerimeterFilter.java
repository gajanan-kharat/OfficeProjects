package com.inetpsa.eds.tools.filter.perimeter;

import java.text.MessageFormat;
import java.util.HashSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug according to a partner.
 * 
 * @author Geometric Ltd.
 */
public class EdsPerimeterFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param perimeter Eds parameter details
     * @param controller Controller of EDS application
     */
    public EdsPerimeterFilter(EdsPerimeter perimeter, EdsApplicationController controller) {
        super(controller);
        this.perimeter = perimeter;
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
        if (perimeter == null) {
            return criteriaQuery.add(Restrictions.isEmpty(propertyName));
        }

        HashSet<String> elligibleEdses = new HashSet<String>();

        DetachedCriteria perimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
        perimeterSubQuery.setProjection(Property.forName("edsId"));
        perimeterSubQuery.createAlias("edsPerimeters", "prestrict");
        perimeterSubQuery.add(Restrictions.eq("prestrict.id", perimeter.getPeId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(perimeterSubQuery, String.class));

        DetachedCriteria followerProjectsPerimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
        followerProjectsPerimeterSubQuery.setProjection(Property.forName("edsId"));
        followerProjectsPerimeterSubQuery.createAlias("edsProjectEdses", "eperestrict");
        followerProjectsPerimeterSubQuery.createAlias("eperestrict.edsProject", "p_eperestrict");
        followerProjectsPerimeterSubQuery.createAlias("p_eperestrict.edsPerimeters", "pe_p_eperestrict");
        followerProjectsPerimeterSubQuery.add(Restrictions.eq("pe_p_eperestrict.id", perimeter.getPeId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(followerProjectsPerimeterSubQuery, String.class));

        DetachedCriteria setterProjectPerimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
        setterProjectPerimeterSubQuery.setProjection(Property.forName("edsId"));
        setterProjectPerimeterSubQuery.createAlias("edsProject", "sprestrict");
        setterProjectPerimeterSubQuery.createAlias("sprestrict.edsPerimeters", "pe_sprestrict");
        setterProjectPerimeterSubQuery.add(Restrictions.eq("pe_sprestrict.id", perimeter.getPeId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(setterProjectPerimeterSubQuery, String.class));
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
        if (perimeter == null) {
            return controller.getBundle().getString("filter-partner-none");
        } else {
            return MessageFormat.format(controller.getBundle().getString("filter-partner-name"), perimeter.getPeName());
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
        int hash = 7;
        hash = 97 * hash + getClass().getName().hashCode();
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
     * Constant Variable to hold value of property name
     */
    private static final String propertyName = "edsPerimeters";

    /**
     * Variable to hold value of Eds parameter details
     */
    private EdsPerimeter perimeter;

    /**
     * Initialize Eds parameter filter
     */
    private void init() {
    }
}

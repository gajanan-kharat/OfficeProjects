package com.inetpsa.eds.tools.filter.search;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.HibernateUtil;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.EdsChs;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug in for a textual search. This filter is fixed and is not available in the filter editor.
 * 
 * @author Geometric Ltd.
 */

public class EdsSearchFilter extends A_EdsFilter {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param search Text to be search
     * @param controller Controller od EDS application
     */
    public EdsSearchFilter(String search, EdsApplicationController controller) {
        super(controller);
        this.search = search;
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
        DetachedCriteria refCriteria = DetachedCriteria.forClass(EdsEds.class).setProjection(Property.forName("edsId"));
        refCriteria.add(Restrictions.ilike("edsRef", search, MatchMode.ANYWHERE));

        DetachedCriteria nameCriteria = DetachedCriteria.forClass(EdsEds.class).setProjection(Property.forName("edsId"));
        nameCriteria.add(Restrictions.ilike("edsName", search, MatchMode.ANYWHERE));

        DetachedCriteria projNameCriteria = DetachedCriteria.forClass(EdsProject.class).setProjection(Property.forName("PId"))
                .add(Restrictions.ilike("PName", search, MatchMode.ANYWHERE));

        DetachedCriteria supplierCriteria = DetachedCriteria.forClass(EdsSupplier.class).setProjection(Property.forName("SId"))
                .add(Restrictions.ilike("SName", search, MatchMode.ANYWHERE));
        DetachedCriteria serviceCriteria = DetachedCriteria.forClass(EdsUser.class).setProjection(Property.forName("UId"))
                .add(Restrictions.ilike("UService", search, MatchMode.ANYWHERE));

        // Criteria doesn't seem to allow collection query (other table)
        DetachedCriteria subChsCriteria = DetachedCriteria.forClass(Chs.class, "c");
        subChsCriteria.setProjection(Projections.property("c.componentId"));
        subChsCriteria.add(Restrictions.ilike("c.description", search, MatchMode.ANYWHERE));

        Criteria chs = HibernateUtil.getSession().createCriteria(EdsChs.class, "e");
        chs.setProjection(Projections.property("e.edsId"));
        chs.add(Subqueries.propertyIn("e.componentId", subChsCriteria));
        List<Object> connectivityChs = chs.list();

        Junction disjunction = Restrictions.disjunction();
        disjunction.add(Property.forName("edsId").in(refCriteria));
        disjunction.add(Property.forName("edsId").in(nameCriteria));
        disjunction.add(Property.forName("edsProject").in(projNameCriteria));
        disjunction.add(Property.forName("edsSupplier").in(supplierCriteria));
        disjunction.add(Property.forName("edsUserByEdsOfficerId").in(serviceCriteria));
        if (connectivityChs.size() > 0) {
            disjunction.add(Property.forName("edsId").in(connectivityChs));
        }

        return criteriaQuery.add(disjunction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-search-name"), search);
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
     * Variable to hold value of text to be searched
     */
    private String search;

    /**
     * Initialize EdsSearchFilter
     */
    private void init() {
    }
}

package com.inetpsa.eds.tools.filter.connectivity;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.CHSdao;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.EdsChs;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug according to a follower project.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class ChsConnectivityFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param project Eds project details
     * @param controller Controller of EDS application
     */
    public ChsConnectivityFilter(Chs project, EdsApplicationController controller) {
        super(controller);
        this.project = project;
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
        CHSdao chs = CHSdao.getInstance();
        Criteria c = chs.getCurrentSession().createCriteria(EdsChs.class);
        c.setProjection(Property.forName("edsId"));
        c.add(Restrictions.eq("componentId", project.getComponentId()));
        List<Object> edsList = c.list();
        criteriaQuery.add(Property.forName("edsId").in(edsList));
        return criteriaQuery;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_EdsFilter#describe()
     */
    @Override
    public String describe() {
        return MessageFormat.format(controller.getBundle().getString("filter-connectivity-name"), project.getPartNumber());
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
        hash = 19 * hash + getClass().getName().hashCode();
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
     * Constant to hold value of property value
     */
    private static final String propertyName = "edsProjectEdses";
    /**
     * Variable to hold value of Eds peoject
     */
    private Chs project;

    /**
     * Initialize follower project filter
     */
    private void init() {
    }
}

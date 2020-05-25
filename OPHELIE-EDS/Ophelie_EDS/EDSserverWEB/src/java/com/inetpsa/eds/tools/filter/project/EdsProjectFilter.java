package com.inetpsa.eds.tools.filter.project;

import java.text.MessageFormat;
import java.util.HashSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.filter.A_EdsFilter;

/**
 * Filter plug on a project.
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectFilter extends A_EdsFilter {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param project Eds project details
     * @param controller Controller of Eds application
     */
    public EdsProjectFilter(EdsProject project, EdsApplicationController controller) {
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
        HashSet<String> elligibleEdses = new HashSet<String>();

        // Retrieving Eds whose launcher project is project
        DetachedCriteria mainProjectSubQuery = DetachedCriteria.forClass(EdsEds.class);
        mainProjectSubQuery.setProjection(Property.forName("edsId"));
        mainProjectSubQuery.createAlias("edsProject", "proj");
        mainProjectSubQuery.add(Restrictions.eq("proj.id", project.getPId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(mainProjectSubQuery, String.class));

        // Retrieving Eds followers projects including a project id
        DetachedCriteria followerProjectsSubQuery = DetachedCriteria.forClass(EdsEds.class);
        followerProjectsSubQuery.setProjection(Property.forName("edsId"));
        followerProjectsSubQuery.createAlias("edsProjectEdses", "epe").createAlias("epe.edsProject", "fproj");
        followerProjectsSubQuery.add(Restrictions.eq("fproj.id", project.getPId()));

        elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(followerProjectsSubQuery, String.class));

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
        return MessageFormat.format(controller.getBundle().getString("filter-project-name"), project.getPName());
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
        hash = 83 * hash + getClass().getName().hashCode();
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
     * variable to hold value of Eds project details
     */
    private EdsProject project;

    /**
     * Initialize Eds Project filter
     */
    private void init() {
    }
}

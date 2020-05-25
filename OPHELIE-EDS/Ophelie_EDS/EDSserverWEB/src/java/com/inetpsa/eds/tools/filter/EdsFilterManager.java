package com.inetpsa.eds.tools.filter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.collections.set.ListOrderedSet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsUser;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

/**
 * Model filter manager. It contains data on user-selectable filters and the composite filter constructed using the principle provided by the
 * Hibernate API.
 * 
 * @author Geometric Ltd.
 * @see EdsFilterManagerView, DetachedCriteria
 */
public class EdsFilterManager implements Serializable {
    // PUBLIC
    /**
     * Constant to represent FILTER_EQUALS
     */
    public static final int FILTER_EQUALS = 1;
    /**
     * Constant to represent FILTER_ISLIKE
     */
    public static final int FILTER_ISLIKE = 2;

    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public EdsFilterManager(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This method returns the Eds Filter
     * 
     * @param filter Eds filter object
     * @return Eds filter
     */
    public A_EdsFilter getFilter(A_EdsFilter filter) {
        return (A_EdsFilter) filters.get(filters.indexOf(filter));
    }

    /**
     * This method returns collection of filters
     * 
     * @return Filter collection
     */
    public Collection<A_EdsFilter> getAllFilters() {
        return filters;
    }

    /**
     * This method add filter in Eds filters list
     * 
     * @param filter Eds Filter to be added
     * @return check If filter is added
     */
    public boolean addFilter(A_EdsFilter filter) {
        return filters.add(filter);
    }

    /**
     * This method removes the filter from Eds filters list
     * 
     * @param filter Eds filter to be removed
     * @return Check if filter is removed
     */
    public boolean removeFilter(A_EdsFilter filter) {
        return filters.remove(filter);
    }

    /**
     * This method removes the filter using index from Eds filters list
     * 
     * @param index Index of filter to be removed
     */
    public void removeFilter(int index) {
        filters.remove(index);
    }

    /**
     * This method removes all the filters from filters list.
     */
    public void clearFilters() {
        filters.clear();
    }

    /**
     * This method used to build hibernate queries.
     * 
     * @return The modified query.
     */
    public DetachedCriteria buildQuery() {
        DetachedCriteria criteria = DetachedCriteria.forClass(EdsEds.class);

        EdsUser user = controller.getAuthenticatedUser();

        if (user.getEdsRole().getAllAppRights().contains(EdsRight.APP_EDS_SEE_PRIMARY_ORGANS)) {
            if (!user.getEdsRole().getAllAppRights().contains(EdsRight.APP_EDS_SEE_SECONDARY_ORGANS)) {
                criteria.add(Restrictions.eq("edsIsBttbt", 1));
            }
        } else if (user.getEdsRole().getAllAppRights().contains(EdsRight.APP_EDS_SEE_SECONDARY_ORGANS)) {
            criteria.add(Restrictions.eq("edsIsBttbt", 0));
        } else {
            // Does not match any record.
            criteria.add(Restrictions.eq("edsIsBttbt", -1));
        }

        if (user.getEdsRole().isSupplier()) {
            criteria.add(Restrictions.eq("edsSupplier.id", user.getEdsSupplier().getSId()));
        } else if (user.getEdsRole().isPerimeter()) {
            HashSet<String> elligibleEdses = new HashSet<String>();

            DetachedCriteria perimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
            perimeterSubQuery.setProjection(Property.forName("edsId"));
            perimeterSubQuery.createAlias("edsPerimeters", "prestrict");
            perimeterSubQuery.add(Restrictions.eq("prestrict.id", user.getEdsPerimeter().getPeId()));

            elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(perimeterSubQuery, String.class));

            DetachedCriteria followerProjectsPerimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
            followerProjectsPerimeterSubQuery.setProjection(Property.forName("edsId"));
            followerProjectsPerimeterSubQuery.createAlias("edsProjectEdses", "eperestrict");
            followerProjectsPerimeterSubQuery.createAlias("eperestrict.edsProject", "p_eperestrict");
            followerProjectsPerimeterSubQuery.createAlias("p_eperestrict.edsPerimeters", "pe_p_eperestrict");
            followerProjectsPerimeterSubQuery.add(Restrictions.eq("pe_p_eperestrict.id", user.getEdsPerimeter().getPeId()));

            elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(followerProjectsPerimeterSubQuery, String.class));

            DetachedCriteria setterProjectPerimeterSubQuery = DetachedCriteria.forClass(EdsEds.class);
            setterProjectPerimeterSubQuery.setProjection(Property.forName("edsId"));
            setterProjectPerimeterSubQuery.createAlias("edsProject", "sprestrict");
            setterProjectPerimeterSubQuery.createAlias("sprestrict.edsPerimeters", "pe_sprestrict");
            setterProjectPerimeterSubQuery.add(Restrictions.eq("pe_sprestrict.id", user.getEdsPerimeter().getPeId()));

            elligibleEdses.addAll(EDSdao.getInstance().executeCriteria(setterProjectPerimeterSubQuery, String.class));
            elligibleEdses.add("fix-bug");

            criteria.add(Restrictions.in("id", elligibleEdses));
        }

        Iterator<A_EdsFilter> it = filters.iterator();
        while (it.hasNext()) {
            criteria = it.next().buildQuery(criteria);
        }

        return criteria;
    }

    /**
     * This method returns filter type name.
     * 
     * @param filterType Filter type
     * @return Filter type name
     */
    public static String getFilterTypeName(int filterType) {
        switch (filterType) {
        case FILTER_EQUALS:
            return "égal à";// controller.getBundle().getString( "filter-egal");
        case FILTER_ISLIKE:
            return "contient";// controller.getBundle().getString( "filter-contain");
        default:
            return null;
        }
    }

    /**
     * This method builds filter type combo box
     * 
     * @param listener Listener for change in value
     * @return Combo box with filter type
     */
    public static ComboBox buildFilterTypeComboBox(ValueChangeListener listener) {
        ComboBox vCMBfilterType = new ComboBox("Type de filtre :", Arrays.asList(FILTER_ISLIKE, FILTER_EQUALS));
        vCMBfilterType.setItemCaption(FILTER_ISLIKE, getFilterTypeName(FILTER_ISLIKE));
        vCMBfilterType.setItemCaption(FILTER_EQUALS, getFilterTypeName(FILTER_EQUALS));
        vCMBfilterType.setNullSelectionAllowed(false);
        vCMBfilterType.setTextInputAllowed(false);
        vCMBfilterType.select(FILTER_EQUALS);

        if (listener != null) {
            vCMBfilterType.addListener(listener);
        }

        vCMBfilterType.setImmediate(true);

        return vCMBfilterType;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of list ordered set of filters
     */
    private ListOrderedSet filters;

    /**
     * Initialize Eds filter manager
     */
    private void init() {
        this.filters = new ListOrderedSet();
    }
}

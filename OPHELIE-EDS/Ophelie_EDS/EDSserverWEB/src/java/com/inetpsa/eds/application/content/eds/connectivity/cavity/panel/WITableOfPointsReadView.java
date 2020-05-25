package com.inetpsa.eds.application.content.eds.connectivity.cavity.panel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

import com.inetpsa.dbelec.model.wiredinterface.Profil;
import com.inetpsa.dbelec.model.wiredinterface.WiredInterface;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.activationprofile.JFreeChartBuilder;
import com.inetpsa.eds.dao.model.EdsWIProperty;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Read view for the profile's list of points.
 * 
 * @author jcosta @ Alter Frame
 */
public class WITableOfPointsReadView extends HorizontalLayout {

    private static final long serialVersionUID = -6585040099750781328L;

    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    private Set<EdsWIProperty> wiProperty;

    /**
     * Constructor.
     * 
     * @param controller The EDS controller.
     * @param profile The profile object.
     */
    public WITableOfPointsReadView(EdsApplicationController controller, WiredInterface wire) {
        this(controller, getPropertiesWI(wire));
    }

    public WITableOfPointsReadView(EdsApplicationController controller, Set<EdsWIProperty> wiProperty) {
        this.controller = controller;
        this.wiProperty = wiProperty;
        init();
    }

    /**
     * Initialize the panel components with data.
     */
    private void init() {
        VerticalLayout contentLayout = new VerticalLayout();
        HorizontalLayout subLayout = new HorizontalLayout();
        subLayout.setMargin(true);
        subLayout.setSpacing(true);
        subLayout.setSizeFull();

        XYSeries serie = new XYSeries(controller.getBundle().getString("Activ-profil-courant-title"), false);
        Double current;
        List<EdsWIProperty> properties = new ArrayList<EdsWIProperty>(wiProperty);
        Collections.sort(properties, new Comparator<EdsWIProperty>() {
            @Override
            public int compare(EdsWIProperty o1, EdsWIProperty o2) {
                return Double.compare(o1.getTime(), o2.getTime());
            }

        });

        for (EdsWIProperty property : properties) {

            current = property.getCurrent();
            if (current != null) {
                serie.add(Double.valueOf(current), Double.valueOf(property.getTime()));
            }
        }
        JFreeChartWrapper graph = new JFreeChartWrapper(JFreeChartBuilder.buildCustomNoLegendCurrentProfile(serie, controller));
        subLayout.addComponent(graph);
        contentLayout.addComponent(subLayout);

        this.addComponent(contentLayout);
    }

    private static Set<EdsWIProperty> getPropertiesWI(WiredInterface wi) {
        Set<EdsWIProperty> properties = new HashSet<EdsWIProperty>();
        List<Profil> profils = new ArrayList<Profil>(wi.getAll());
        Collections.sort(profils, new Comparator<Profil>() {
            @Override
            public int compare(Profil o1, Profil o2) {
                return o1.getID_2().compareTo(o2.getID_2());
            }

        });
        EdsWIProperty item;
        for (Profil profil : profils) {
            item = new EdsWIProperty();
            Double current = profil.getProperties().getValueDouble("current", true);
            if (current != null) {
                item.setTime(profil.getID_2());
                item.setCurrent(current);
                item.setWi(null);
                properties.add(item);
            }
        }
        return properties;
    }
}

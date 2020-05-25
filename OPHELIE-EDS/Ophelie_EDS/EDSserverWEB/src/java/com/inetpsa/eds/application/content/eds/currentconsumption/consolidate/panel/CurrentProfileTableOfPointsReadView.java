package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import org.jfree.data.xy.XYSeries;
import org.vaadin.addon.JFreeChartWrapper;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.activationprofile.JFreeChartBuilder;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.dao.model.EdsConsSupProfileListOfPoints;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Read view for the profile's list of points.
 * 
 * @author gvillerez @ Alter Frame
 */
public class CurrentProfileTableOfPointsReadView extends HorizontalLayout {

    private static final long serialVersionUID = -6585040099750781328L;

    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * List of points, from the profile object.
     */
    private EdsConsSupProfileListOfPoints points;

    /**
     * Constructor.
     * 
     * @param controller The EDS controller.
     * @param profile The profile object.
     */
    public CurrentProfileTableOfPointsReadView(EdsApplicationController controller, EdsConsSupProfile profile) {
        this.controller = controller;
        this.points = profile.getCspListOfPoint();

        if (this.points == null) {
            this.points = new EdsConsSupProfileListOfPoints(profile);
            profile.setCspListOfPoint(this.points);
        }

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

        Table vTBcustomProfileData = new Table(controller.getBundle().getString("Activ-profil-courant-complexe"));

        vTBcustomProfileData.addContainerProperty(controller.getBundle().getString("eds-time-label"), Float.class, null);
        vTBcustomProfileData.addContainerProperty(controller.getBundle().getString("eds-courant-label"), Float.class, null);

        Label lMesureTempValue = new Label(points.getCsppMesureTemp() != null ? points.getCsppMesureTemp().toString() : "");
        Label lYAxisValueValue = new Label(points.getCsppYAxisValue() != null ? points.getCsppYAxisValue().toString() : "");
        Label lTimeAxisValueValue = new Label(points.getCsppTimeAxisValue() != null ? points.getCsppTimeAxisValue().toString() : "");
        Label lSourceImpedanceValue = new Label(points.getCsppSourceImpedance() != null ? points.getCsppSourceImpedance().toString() : "");

        Label lMesureTemp = new Label(controller.getBundle().getString("profile-mesure-temperature") + " : ");
        Label lYAxisValue = new Label(controller.getBundle().getString("profile-y-axis-value") + " : ");
        Label lTimeAxisValue = new Label(controller.getBundle().getString("profile-time-axis-value") + " : ");
        Label lSourceImpedance = new Label(controller.getBundle().getString("profile-impedance-value") + " : ");

        String xyValue = points.getCsppXyData();
        XYSeries serie = new XYSeries(controller.getBundle().getString("Activ-profil-courant-title"), false);
        if (xyValue != null) {
            int i = 1;
            for (String xyPoint : xyValue.split(";")) {
                String[] xyData = xyPoint.split(":");
                vTBcustomProfileData.addItem(new Object[] { Float.parseFloat(xyData[0]), Float.parseFloat(xyData[1]) }, new Integer(i));
                serie.add(Double.parseDouble(xyData[1]), Double.parseDouble(xyData[0]));
                i++;
            }
        }

        subLayout.addComponent(vTBcustomProfileData);
        subLayout.addComponent(new JFreeChartWrapper(JFreeChartBuilder.buildCustomCurrentProfile(serie, controller)));
        contentLayout.addComponent(subLayout);

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        HorizontalLayout row4 = new HorizontalLayout();
        row1.setMargin(false, false, true, false);
        row2.setMargin(false, false, true, false);
        row3.setMargin(false, false, true, false);
        row4.setMargin(false, false, true, false);

        lMesureTempValue.setWidth(200, UNITS_PIXELS);
        lYAxisValueValue.setWidth(200, UNITS_PIXELS);
        lTimeAxisValueValue.setWidth(200, UNITS_PIXELS);
        lSourceImpedanceValue.setWidth(200, UNITS_PIXELS);

        lMesureTemp.setWidth(300, UNITS_PIXELS);
        lYAxisValue.setWidth(300, UNITS_PIXELS);
        lTimeAxisValue.setWidth(300, UNITS_PIXELS);
        lSourceImpedance.setWidth(300, UNITS_PIXELS);

        row1.addComponent(lMesureTemp);
        row1.addComponent(lMesureTempValue);

        row2.addComponent(lYAxisValue);
        row2.addComponent(lYAxisValueValue);

        row3.addComponent(lTimeAxisValue);
        row3.addComponent(lTimeAxisValueValue);

        row4.addComponent(lSourceImpedance);
        row4.addComponent(lSourceImpedanceValue);

        contentLayout.addComponent(row1);
        contentLayout.addComponent(row2);
        contentLayout.addComponent(row3);
        contentLayout.addComponent(row4);

        this.addComponent(contentLayout);
    }
}

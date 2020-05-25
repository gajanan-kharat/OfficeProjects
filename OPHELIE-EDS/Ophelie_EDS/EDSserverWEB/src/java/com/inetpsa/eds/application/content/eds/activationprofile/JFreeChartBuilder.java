package com.inetpsa.eds.application.content.eds.activationprofile;

import java.util.ResourceBundle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Chart Builder "Activation profile" using JFree Chart plug-in.
 * 
 * @author Geometric Ltd.
 */
public class JFreeChartBuilder {
    // PUBLIC
    /**
     * This method is used to build a 2D Line chart using JFree Chart plug-in. It uses 'Current (A)' as x-axis measure and 'Time (s)' as y-axis
     * measure.
     * 
     * @param serie Represents a sequence of zero or more data items in the form (x, y).
     * @param controller EDS application controller object.
     * @return 2D Line chart.
     */
    public static JFreeChart buildCustomCurrentProfile(XYSeries serie, EdsApplicationController controller) {

        return ChartFactory.createXYLineChart("", controller.getBundle().getString("eds-courant-label"),
                controller.getBundle().getString("eds-time-label"), new XYSeriesCollection(serie), PlotOrientation.HORIZONTAL, true, true, false);
    }

    /**
     * This method is used to build a 2D Line chart with no legend using JFree Chart plug-in. It uses 'Current (A)' as x-axis measure and 'Time (s)'
     * as y-axis measure.
     * 
     * @param serie Represents a sequence of zero or more data items in the form (x, y).
     * @param controller EDS application controller object.
     * @return 2D Line chart.
     */
    public static JFreeChart buildCustomNoLegendCurrentProfile(XYSeries serie, EdsApplicationController controller) {

        return ChartFactory.createXYLineChart("", controller.getBundle().getString("eds-courant-label"),
                controller.getBundle().getString("eds-time-label"), new XYSeriesCollection(serie), PlotOrientation.HORIZONTAL, false, true, false);
    }

    /**
     * This method is used to build a 2D Line chart using JFree Chart plug-in.
     * 
     * @param imax max current.
     * @param inom normal current.
     * @param tmax Max time.
     * @param controller EDS application controller object.
     * @return 2D Line chart.
     */
    public static JFreeChart buildSimpleCurrentProfile(double imax, double inom, double tmax, EdsApplicationController controller) {
        XYSeries serie = new XYSeries(controller.getBundle().getString("Activ-profil-courant-title"), false);
        serie.add(imax, 0);
        serie.add(imax, tmax);
        serie.add(inom, tmax);
        serie.add(inom, 4 * tmax);
        return ChartFactory.createXYLineChart("", controller.getBundle().getString("eds-courant-label"),
                controller.getBundle().getString("eds-time-label"), new XYSeriesCollection(serie), PlotOrientation.HORIZONTAL, true, true, false);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Resource bundle to retrieve configuration details.
     */
    private static ResourceBundle bundle;

    /**
     * Default constructor.
     */
    private JFreeChartBuilder() {
        init();
    }

    /**
     * Initialization method.
     */
    private void init() {
    }
}

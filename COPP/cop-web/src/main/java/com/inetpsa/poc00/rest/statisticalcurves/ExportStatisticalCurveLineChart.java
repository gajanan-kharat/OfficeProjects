package com.inetpsa.poc00.rest.statisticalcurves;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Font.FontFamily;

/**
 * The Class ExportStatisticalCurveLineChart.
 */
public class ExportStatisticalCurveLineChart {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ExportStatisticalCurveLineChart.class);

	/**
	 * Instantiates a new export statistical curve line chart.
	 */
	private ExportStatisticalCurveLineChart() {
		super();
	}

	/**
	 * Generate line chart.
	 * 
	 * @param dataSet the data set
	 * @param seriesName the series name
	 * @param vehicleNames the vehicle names
	 * @param chartTitle the chart title
	 * @param showLegend the show legend
	 * @return the j free chart
	 */
	public static JFreeChart generateLineChart(List<Map<Integer, Long>> dataSet, List<String> seriesName, Set<String> vehicleNames, String chartTitle, Boolean showLegend) {

		logger.info(" START  : ****** CHART CREATION ******");
		
		Font font = new Font(FontFamily.TIMES_ROMAN.toString(), Font.ROMAN_BASELINE, 8);

		XYSeries series;
		int count = 0;

		XYDataset dataset = new XYSeriesCollection();

		for (Map<Integer, Long> temp : dataSet) {

			series = new XYSeries(seriesName.get(count++));

			for (Entry<Integer, Long> temp2 : temp.entrySet()) {
				series.add(temp2.getKey(), temp2.getValue());
			}

			((XYSeriesCollection) dataset).addSeries(series);
		}

		SymbolAxis symbolAxis = new SymbolAxis("", vehicleNames.toArray(new String[vehicleNames.size()]));

		symbolAxis.setGridBandsVisible(false);
		symbolAxis.setTickLabelFont(font);

		JFreeChart chart = ChartFactory.createXYLineChart("", "", "", dataset, PlotOrientation.VERTICAL, true, true, true);
		
		TextTitle legendText = new TextTitle(chartTitle);
		legendText.setPosition(RectangleEdge.TOP);
		legendText.setFont(font);
		chart.addSubtitle(legendText);
		
		chart.getTitle().setFont(font);
		chart.getLegend().setPosition(RectangleEdge.TOP);
		chart.getLegend().setItemFont(font);
		chart.getLegend().setBorder(0.75, 0.75, 0.75, 0.75);
		chart.getLegend().setVerticalAlignment(VerticalAlignment.TOP);
		chart.getLegend().setItemLabelPadding(new RectangleInsets(2.0, 3.0, 2.0, 10.0));

		if (!showLegend) {
			//chart.removeLegend();
			chart.getLegend().setVisible(Boolean.FALSE);
		}

		final XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.getDomainAxis().setTickLabelFont(font);
		plot.setDomainAxis(symbolAxis);
		plot.getDomainAxis().setVerticalTickLabels(true);
		plot.getRangeAxis().setTickLabelFont(font);

		final Paint[] paintArray;
		paintArray = ChartColor.createDefaultPaintArray();
		paintArray[0] = Color.BLACK;
		paintArray[1] = Color.RED;
		paintArray[2] = new Color(255, 0, 255);
		paintArray[3] = new Color(0, 128, 0);
		paintArray[4] = Color.green;

		plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(0.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		renderer.setBaseShapesVisible(true);
		renderer.setBaseLegendTextFont(font);

		logger.info(" END  : ****** CHART CREATION ******");

		return chart;

	}
}
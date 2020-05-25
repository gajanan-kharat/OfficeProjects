/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.util;

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

/**
 * The Class CalculationUtil.
 */
public class CalculationUtil {

	/**
	 * Instantiates a new calculation util.
	 */
	private CalculationUtil() {
		// Default Constructor
	}

	/**
	 * Calculate average.
	 *
	 * @param valueList the value list
	 * @return the double
	 */
	public static double calculateAverage(List<Double> valueList) {
		double[] inputArray = convertToArray(valueList);
		return StatUtils.mean(inputArray);
	}

	/**
	 * Calculate deviation.
	 *
	 * @param valueList the value list
	 * @return the double
	 */
	public static double calculateDeviation(List<Double> valueList) {
		double[] inputArray = convertToArray(valueList);
		StandardDeviation std = new StandardDeviation();
		return std.evaluate(inputArray);
	}

	/**
	 * Convert to array.
	 *
	 * @param valueList the value list
	 * @return the double[]
	 */
	private static double[] convertToArray(List<Double> valueList) {
		double[] inputArray = new double[valueList.size()];
		for (int i = 0; i < valueList.size(); i++) {
			inputArray[i] = valueList.get(i);
		}
		return inputArray;
	}

	/**
	 * Calculate standard deviation.
	 *
	 * @param valueList the value list
	 * @param averageValue the average value
	 * @param i is number of elements in Set considered while calculating Standard Deviation
	 * @return the double
	 */
	public static double calculateStandardDeviation(List<Double> valueList, double averageValue, int i) {
		double[] inputArray = convertToArray(valueList);
		double result = 0;
		for (int j = 0; j < inputArray.length; j++) {
			double squareValue = calculateSquaredDifference(inputArray[j], averageValue);
			result = Math.sqrt(squareValue / i - 1);
		}
		return result;
	}

	/**
	 * This method returns Square of difference among given values.
	 *
	 * @param d the d
	 * @param averageValue the average value
	 * @return the double
	 */
	private static double calculateSquaredDifference(double d, double averageValue) {
		double difference = d - averageValue;
		return difference * difference;
	}

	/**
	 * Calculate variance.
	 *
	 * @param valueList the value list
	 * @param averageValue the average value
	 * @param i the i
	 * @return the double
	 */
	public static double calculateVariance(List<Double> valueList, double averageValue) {
		Variance var = new Variance();
		double[] inputArray = convertToArray(valueList);
		return var.evaluate(inputArray, averageValue);

	}

	/**
	 * Calculate average.
	 *
	 * @param value the value
	 * @return the double
	 */
	public static double calculateAverage(double[] value) {
		return StatUtils.mean(value);
	}
}

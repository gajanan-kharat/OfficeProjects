/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.util.CalculationUtil;

@RunWith(SeedITRunner.class)
public class UtilIT {

	@Test
	public void testAverage() {
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(100.00);
		inputList.add(200.00);
		inputList.add(300.00);
		inputList.add(400.00);

		double average = CalculationUtil.calculateAverage(inputList);
		assertEquals(average, 250.01, 0.01);
	}

	@Test
	public void testDeviation() {
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(100.15);
		inputList.add(200.15);
		inputList.add(300.15);
		inputList.add(400.15);

		double deviation = CalculationUtil.calculateDeviation(inputList);
		assertEquals(deviation, 129.09944487358, 0.0011);
	}

	@Test
	public void testVariance() {
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(100.15);
		inputList.add(200.15);
		inputList.add(300.15);
		inputList.add(400.15);

		double variance = CalculationUtil.calculateVariance(inputList, CalculationUtil.calculateAverage(inputList));
		assertEquals(variance, 16666.666666667, 0.0011);
	}

}

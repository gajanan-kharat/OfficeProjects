/*
 * @(#)SpecITStrategyServiceImplTest.java October 10, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.strategy.services.impl;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.strategy.services.impl.SpecITStrategyServiceImpl;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementsType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelsType;

public class SpecITStrategyServiceImplTest extends TestCase {

	private SpecITStrategyServiceImpl specITStrategyServiceImpl;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Check for organizeInput, Whether the list of requirements and tranferModels for single level are properly organise or not.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testOrganizeInput() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

		RequirementsType rqmts = new RequirementsType();

		RequirementType rt1 = new RequirementType();
		rt1.setParentId("TM Y1");
		rt1.setId("X1.1");
		rt1.setName("X1.1");
		rqmts.getRequirement().add(rt1);
		RequirementType rt2 = new RequirementType();
		rt2.setParentId("TM Y1");
		rt2.setId("X1.2");
		rt2.setName("X1.2");
		rqmts.getRequirement().add(rt2);
		RequirementType rt3 = new RequirementType();
		rt3.setParentId("TM Y1");
		rt3.setId("X1.3");
		rt3.setName("X1.3");
		rqmts.getRequirement().add(rt3);
		RequirementType rt4 = new RequirementType();
		rt4.setParentId("Y1");
		rt4.setId("Y1");
		rt4.setName("Y1");
		rqmts.getRequirement().add(rt4);
		RequirementType rt5 = new RequirementType();
		rt5.setParentId("TM Y2");
		rt5.setId("X2.1");
		rt5.setName("X2.1");
		rqmts.getRequirement().add(rt5);
		RequirementType rt6 = new RequirementType();
		rt6.setParentId("TM Y2");
		rt6.setId("X2.2");
		rt6.setName("X2.2");
		rqmts.getRequirement().add(rt6);
		RequirementType rt7 = new RequirementType();
		rt7.setParentId("Y2");
		rt7.setId("Y2");
		rt7.setName("Y2");
		rqmts.getRequirement().add(rt7);
		RequirementType rt8 = new RequirementType();
		rt8.setParentId("Y3");
		rt8.setId("Y3");
		rt8.setName("Y3");
		rqmts.getRequirement().add(rt8);
		RequirementType rt9 = new RequirementType();
		rt9.setParentId("TM Y3");
		rt9.setId("X3.1");
		rt9.setName("X3.1");
		rqmts.getRequirement().add(rt9);
		RequirementType rt10 = new RequirementType();
		rt10.setParentId("TM Y3");
		rt10.setId("X3.2");
		rt10.setName("X3.2");
		rqmts.getRequirement().add(rt10);
		RequirementType rt11 = new RequirementType();
		rt11.setParentId("TM Y2");
		rt11.setId("X9.9");
		rt11.setName("X9.9");
		rqmts.getRequirement().add(rt11);
		RequirementType rt12 = new RequirementType();
		rt12.setParentId("TM Y3");
		rt12.setId("X9.9");
		rt12.setName("X9.9");
		rqmts.getRequirement().add(rt12);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmt1 = new TransferModelType();
		tmt1.setParentId("Y1");
		tmt1.setId("TM Y1");
		transferModels.getTransferModel().add(tmt1);
		TransferModelType tmt2 = new TransferModelType();
		tmt2.setParentId("Y2");
		tmt2.setId("TM Y2");
		transferModels.getTransferModel().add(tmt2);
		TransferModelType tmt3 = new TransferModelType();
		tmt3.setParentId("Y3");
		tmt3.setId("TM Y3");
		transferModels.getTransferModel().add(tmt3);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.SPEC_IT);
		calculation.setRequirements(rqmts);
		calculation.setTransferModels(transferModels);

		this.specITStrategyServiceImpl = new SpecITStrategyServiceImpl(calculation, Calendar.getInstance().getTime());
		this.specITStrategyServiceImpl.organizeInput();

		Field f = SpecITStrategyServiceImpl.class.getDeclaredField("motherList");
		f.setAccessible(true);
		List<SciMotherRequirementType> motherList = (List<SciMotherRequirementType>) f.get(specITStrategyServiceImpl);

		assertNotNull(motherList);
		assertEquals(3, motherList.size());
		short tmCount = 0;
		short contributorsCount = 0;
		for (SciMotherRequirementType sciMotherRequirementType : motherList) {
			System.out.print(sciMotherRequirementType.getId() + "  ->  " + sciMotherRequirementType.getTransferModel().getId() + "  ->  ");
			tmCount++;
			for (SciRequirementType contributor : sciMotherRequirementType.getContributorList()) {
				System.out.print(contributor.getId() + "    ");
				contributorsCount++;
			}
			System.out.println();
		}
		assertEquals(3, tmCount);
		assertEquals(9, contributorsCount);

	}

	/**
	 * Check for contributor reference code, Whether it is present in the formula.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckInput() throws SecurityException, NoSuchFieldException {
		RequirementsType requirements = new RequirementsType();
		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("1001.10001.1001.10001");
		requirement1.setId("1001.10001.1001.10001");
		requirement1.setName("UR-0000001");
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("1001.10001.1001.10001");
		tm1.setId("9009.90001.9009.90001");
		tm1.setFormula("sqrt(#UR-0000011# + #UR-0000012#)");
		tm1.setReportFormula("sqrt(NORMAL + SPEC1)");
		transferModels.getTransferModel().add(tm1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("9009.90001.9009.90001");
		contributor1.setId("2002.10001.2002.10011");
		contributor1.setCode("NORMAL");
		contributor1.setName("UR-0000011");
		requirements.getRequirement().add(contributor1);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.SPEC_IT);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.specITStrategyServiceImpl = new SpecITStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.specITStrategyServiceImpl.organizeInput();

		this.specITStrategyServiceImpl.checkInput();

		Field motherRequirement = SpecITStrategyServiceImpl.class.getDeclaredField("motherList");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("11"), calculation.getResult().getStatus());
		assertEquals("Invalid contributor", calculation.getResult().getDescription());
		assertEquals("sqrt(NORMAL + SPEC1)", calculation.getResult().getFormula());
		assertEquals(new BigInteger("15"), calculation.getResult().getPosition());
		assertEquals("Error", calculation.getResult().getLevel());
		assertEquals("null,UR-0000001,UR-0000011,UR-0000011", calculation.getResult().getImpactedObject());
	}

	/**
	 * Check mean of requirements, Whether it is infinite or not.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckOutput1() throws SecurityException, NoSuchFieldException {
		double numerator = 1.0;
		double denominator = 0.0;
		double result = numerator / denominator;
		Double myResult = new Double(result);

		RequirementsType requirements = new RequirementsType();
		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("Y1");
		requirement1.setId("Y1");
		requirement1.setName("Y1");
		requirement1.setCap(1.023);
		requirement1.setCpk(0.123);
		requirement1.setTnc(107.0);
		requirement1.setCenteringMax(0.765);
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("Y1");
		tm1.setId("TM Y1");
		transferModels.getTransferModel().add(tm1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM Y1");
		contributor1.setId("X1.1");
		contributor1.setName("X1.1");
		contributor1.setValueInf(1.2);
		contributor1.setValueSup(1.008);
		requirements.getRequirement().add(contributor1);

		RequirementType requirement2 = new RequirementType();
		requirement2.setParentId("Y2");
		requirement2.setId("Y2");
		requirement2.setName("Y2");
		requirement2.setMean(1.1);
		requirement2.setStandardDeviation(1.2);
		requirement2.setCap(1.023);
		requirement2.setCpk(0.123);
		requirement2.setTnc(1.07);
		requirement2.setCenteringMax(0.765);
		requirements.getRequirement().add(requirement2);

		TransferModelType tm2 = new TransferModelType();
		tm2.setParentId("Y2");
		tm2.setId("TM Y2");
		transferModels.getTransferModel().add(tm2);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y2");
		contributor2.setId("X2.1");
		contributor2.setName("X2.1");
		contributor2.setValueInf(myResult);
		contributor2.setValueSup(1.034);
		requirements.getRequirement().add(contributor2);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.SPEC_IT);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.specITStrategyServiceImpl = new SpecITStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.specITStrategyServiceImpl.organizeInput();

		this.specITStrategyServiceImpl.checkOutput();

		Field motherRequirement = SpecITStrategyServiceImpl.class.getDeclaredField("motherList");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("3020"), calculation.getResult().getStatus());
		assertEquals("INFINITE_SPEC_IT_OUTPUT", calculation.getResult().getDescription());
		assertEquals("X2.1", calculation.getResult().getImpactedObject());
		assertEquals("Error", calculation.getResult().getLevel());
	}

	/**
	 * Check statisticalValueInf of contributors, Whether it is infinite or not.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckOutput2() throws SecurityException, NoSuchFieldException {
		double value = 1.23;
		double numerator = 1.0;
		double denominator = 0.0;
		double result = numerator / denominator;
		Double myResult = new Double(result);

		RequirementsType requirements = new RequirementsType();
		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("Y1");
		requirement1.setId("Y1");
		requirement1.setName("Y1");
		requirement1.setCap(value);
		requirement1.setCpk(value);
		requirement1.setTnc(value);
		requirement1.setCenteringMax(value);
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("Y1");
		tm1.setId("TM Y1");
		transferModels.getTransferModel().add(tm1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM Y1");
		contributor1.setId("X1.1");
		contributor1.setName("X1.1");
		contributor1.setValueInf(myResult);
		contributor1.setValueSup(value);
		requirements.getRequirement().add(contributor1);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y1");
		contributor2.setId("X1.2");
		contributor2.setName("X1.2");
		contributor2.setValueInf(value);
		contributor2.setValueSup(myResult);
		requirements.getRequirement().add(contributor2);

		RequirementType requirement2 = new RequirementType();
		requirement2.setParentId("Y2");
		requirement2.setId("Y2");
		requirement2.setName("Y2");
		requirement2.setCap(value);
		requirement2.setCpk(value);
		requirement2.setTnc(value);
		requirement2.setCenteringMax(value);
		requirements.getRequirement().add(requirement2);

		TransferModelType tm2 = new TransferModelType();
		tm2.setParentId("Y2");
		tm2.setId("TM Y2");
		transferModels.getTransferModel().add(tm2);

		RequirementType contributor3 = new RequirementType();
		contributor3.setParentId("TM Y2");
		contributor3.setId("X2.1");
		contributor3.setName("X2.1");
		contributor3.setValueInf(myResult);
		contributor3.setValueSup(value);
		requirements.getRequirement().add(contributor3);

		RequirementType contributor4 = new RequirementType();
		contributor4.setParentId("TM Y2");
		contributor4.setId("X2.2");
		contributor4.setName("X2.2");
		contributor4.setValueInf(value);
		contributor4.setValueSup(myResult);
		requirements.getRequirement().add(contributor4);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.SPEC_IT);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.specITStrategyServiceImpl = new SpecITStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.specITStrategyServiceImpl.organizeInput();

		this.specITStrategyServiceImpl.checkOutput();

		Field motherRequirement = SpecITStrategyServiceImpl.class.getDeclaredField("motherList");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("3020"), calculation.getResult().getStatus());
		assertEquals("INFINITE_SPEC_IT_OUTPUT", calculation.getResult().getDescription());
		assertEquals("X1.2,X1.1,X2.2,X2.1", calculation.getResult().getImpactedObject());
		assertEquals("Error", calculation.getResult().getLevel());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.specITStrategyServiceImpl = null;
	}

}

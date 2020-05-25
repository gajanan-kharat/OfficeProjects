/*
 * @(#)MonteCarloStrategyServiceImplTest.java October 02, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.strategy.services.impl;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementsType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelsType;

public class MonteCarloStrategyServiceImplTest extends TestCase {

	private MonteCarloStrategyServiceImpl monteCarloStrategyServiceImpl;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Check for the empty formula or formula value is null.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckInput1() throws SecurityException, NoSuchFieldException {

		RequirementsType requirements = new RequirementsType();

		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		requirements.getRequirement().add(y1);

		RequirementType x1 = new RequirementType();
		x1.setParentId("TM Y1");
		x1.setId("X1.1");
		x1.setName("X1.1");
		requirements.getRequirement().add(x1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("");
		tmY1.setReportFormula("");
		transferModels.getTransferModel().add(tmY1);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		this.monteCarloStrategyServiceImpl.checkInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);

		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("1"), calculation.getResult().getStatus());
		assertEquals("Empty formula", calculation.getResult().getDescription());
		assertEquals("null,X1.1,X1.1", calculation.getResult().getImpactedObject());
		List<TransferModelType> transferModelsList = calculation.getTransferModels().getTransferModel();
		for (TransferModelType transferModelType : transferModelsList) {
			if (transferModelType.getId().equals(tmY1.getId())) {
				assertEquals(new Boolean(false), transferModelType.isStatus());
			}

		}
	}

	/**
	 * Check for unCertainty, Whether it's less than zero or zero.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckInput2() throws SecurityException, NoSuchFieldException {

		RequirementsType requirements = new RequirementsType();

		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		y1.setCode("Y1");
		requirements.getRequirement().add(y1);

		RequirementType x1 = new RequirementType();
		x1.setParentId("TM Y1");
		x1.setId("01A01-211");
		x1.setName("01A01-211");
		x1.setCode("01A01-211");
		requirements.getRequirement().add(x1);

		RequirementType x2 = new RequirementType();
		x2.setParentId("TM Y1");
		x2.setId("01A01-212");
		x2.setName("01A01-212");
		x2.setCode("01A01-212");
		requirements.getRequirement().add(x2);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000211#*#UR-0000212#");
		tmY1.setReportFormula("01A01-211*01A01-212");
		tmY1.setUncertainty(-1);
		transferModels.getTransferModel().add(tmY1);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		this.monteCarloStrategyServiceImpl.checkInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);

		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("2000"), calculation.getResult().getStatus());
		assertEquals("Monte Carlo input", calculation.getResult().getDescription());
		assertEquals("null,01A01-211,01A01-211,01A01-212,01A01-212", calculation.getResult().getImpactedObject());
		List<TransferModelType> transferModelsList = calculation.getTransferModels().getTransferModel();
		for (TransferModelType transferModelType : transferModelsList) {
			if (transferModelType.getId().equals(tmY1.getId())) {
				assertEquals(new Boolean(false), transferModelType.isStatus());
			}
		}
	}

	/**
	 * Check for law paramter's, Whether empty or null.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckInput3() throws SecurityException, NoSuchFieldException {

		RequirementsType requirements = new RequirementsType();

		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		requirements.getRequirement().add(y1);

		RequirementType x1 = new RequirementType();
		x1.setParentId("TM Y1");
		x1.setId("01A01-211");
		x1.setName("UR-0000211");
		x1.setCode("01A01-211");
		x1.setParamLaw1(null);
		x1.setParamLaw2(null);
		x1.setLaw(LawType.NORMAL_STD);
		requirements.getRequirement().add(x1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000211# + 20");
		tmY1.setReportFormula("01A01-211 + 20");
		tmY1.setUncertainty(3.14);
		transferModels.getTransferModel().add(tmY1);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		this.monteCarloStrategyServiceImpl.checkInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);

		assertNotNull(motherRequirement);
		assertEquals("Normal distribution", calculation.getResult().getDescription());
		assertEquals(new BigInteger("100"), calculation.getResult().getStatus());
		assertEquals("UR-0000211,UR-0000211", calculation.getResult().getImpactedObject());
		List<RequirementType> requirementList = calculation.getRequirements().getRequirement();
		for (RequirementType requirementType : requirementList) {
			if (requirementType.getId().equals(x1.getId())) {
				assertEquals(new Boolean(false), requirementType.isStatus());
			}
		}
	}

	/**
	 * Check for lower and upper values of requirement, Whether empty or null.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void testCheckInput4() throws SecurityException, NoSuchFieldException {

		RequirementsType requirements = new RequirementsType();

		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		requirements.getRequirement().add(y1);

		RequirementType x1 = new RequirementType();
		x1.setParentId("TM Y1");
		x1.setId("01A01-211");
		x1.setName("UR-0000211");
		x1.setCode("01A01-211");
		x1.setParamLaw1(1.23);
		x1.setParamLaw2(2.5);
		x1.setLaw(LawType.NORMAL_STD);
		x1.setItInf(null);
		x1.setItSup(null);
		requirements.getRequirement().add(x1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000211# + 20");
		tmY1.setReportFormula("01A01-211 + 20");
		tmY1.setUncertainty(3.14);
		transferModels.getTransferModel().add(tmY1);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		this.monteCarloStrategyServiceImpl.checkInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("2000"), calculation.getResult().getStatus());
		assertEquals("UR-0000211", calculation.getResult().getImpactedObject());
		List<RequirementType> requirementList = calculation.getRequirements().getRequirement();
		for (RequirementType requirementType : requirementList) {
			if (requirementType.getId().equals(x1.getId())) {
				assertEquals(new Boolean(false), requirementType.isStatus());
			}
		}
	}

	/**
	 * If correct input is provided the calculation must be performed successfully and should return the status "0".
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void testCheckInput5() throws SecurityException, NoSuchFieldException {
		RequirementsType requirements = new RequirementsType();
		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		y1.setValueInf(new Double(-5));
		y1.setValueSup(new Double(4));
		requirements.getRequirement().add(y1);
		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000111# + #UR-0000112# + #UR-0000113#");
		tmY1.setReportFormula("LOG + UNIFORM + BINOMIAL");
		tmY1.setUncertainty(0.0);
		transferModels.getTransferModel().add(tmY1);
		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM Y1");
		contributor1.setId("LOG");
		contributor1.setName("UR-0000111");
		contributor1.setCode("LOG");
		contributor1.setNominal(new Double(0.5));
		contributor1.setValueInf(new Double(-5));
		contributor1.setValueSup(new Double(2));
		contributor1.setItInf(new Double(-5.5));
		contributor1.setItSup(new Double(1.5));
		contributor1.setLaw(LawType.LOG);
		contributor1.setParamLaw1(new Double(0.8));
		contributor1.setParamLaw2(new Double(1.0));
		requirements.getRequirement().add(contributor1);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y1");
		contributor2.setId("UNIFORM");
		contributor2.setName("UR-0000112");
		contributor2.setCode("UNIFORM");
		contributor2.setNominal(new Double(0.5));
		contributor2.setValueInf(new Double(-5));
		contributor2.setValueSup(new Double(2));
		contributor2.setItInf(new Double(-5.5));
		contributor2.setItSup(new Double(1.5));
		contributor2.setLaw(LawType.UNIFORM);
		contributor2.setParamLaw1(new Double(-3.0));
		contributor2.setParamLaw2(new Double(1.8));
		requirements.getRequirement().add(contributor2);

		RequirementType contributor3 = new RequirementType();
		contributor3.setParentId("TM Y1");
		contributor3.setId("BINOMIAL");
		contributor3.setName("UR-0000113");
		contributor3.setCode("BINOMIAL");
		contributor3.setNominal(new Double(0.5));
		contributor3.setValueInf(new Double(-5));
		contributor3.setValueSup(new Double(2));
		contributor3.setItInf(new Double(-5.5));
		contributor3.setItSup(new Double(1.5));
		contributor3.setLaw(LawType.BINOMIAL);
		contributor3.setParamLaw1(new Double(2));
		contributor3.setParamLaw2(new Double(0.8));
		requirements.getRequirement().add(contributor3);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		this.monteCarloStrategyServiceImpl.checkInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);

		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("0"), calculation.getResult().getStatus());
		for (RequirementType requirementType : calculation.getRequirements().getRequirement()) {
			assertEquals(new Boolean(true), requirementType.isStatus());
		}
		for (TransferModelType transferModelType : calculation.getTransferModels().getTransferModel()) {
			assertEquals(new Boolean(true), transferModelType.isStatus());
		}
	}

	public void testCheckOutputOfIntermediateContributor() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		double value = 1.23;
		RequirementsType requirements = new RequirementsType();

		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		y1.setCode("Y1");
		y1.setMean(value);
		y1.setStandardDeviation(value);
		y1.setTnc(value);
		y1.setValueInf(value);
		y1.setValueSup(value);
		y1.setCap(value);
		y1.setCpk(value);
		y1.setCenteringMax(value);
		requirements.getRequirement().add(y1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000211#*5");
		tmY1.setReportFormula("01A01-211*5");
		tmY1.setUncertainty(-1);
		transferModels.getTransferModel().add(tmY1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM Y1");
		contributor1.setId("01A01-211");
		contributor1.setName("UR-0000211");
		contributor1.setCode("01A01-211");
		contributor1.setCap(value);
		contributor1.setCpk(value);
		contributor1.setLaw(LawType.NORMAL_STD);
		contributor1.setParamLaw1(1.23);
		contributor1.setParamLaw2(1.5);
		requirements.getRequirement().add(contributor1);

		TransferModelType tmY2 = new TransferModelType();
		tmY2.setParentId("01A01-211");
		tmY2.setId("TM Y2");
		tmY2.setFormula("5*#UR-0000212#");
		tmY2.setReportFormula("5*01A01-212");
		tmY2.setUncertainty(-1);
		transferModels.getTransferModel().add(tmY2);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y1");
		contributor2.setId("01A01-212");
		contributor2.setName("UR-0000212");
		contributor2.setCode("01A01-212");
		contributor2.setCap(value);
		contributor2.setCpk(value);
		requirements.getRequirement().add(contributor2);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();
		this.monteCarloStrategyServiceImpl.checkOutput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);

		assertNotNull(motherRequirement);
		assertEquals(new BigInteger("2010"), calculation.getResult().getStatus());
		assertEquals("Monte Carlo limited", calculation.getResult().getDescription());
		assertEquals("Warning", calculation.getResult().getLevel());
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		SciRequirementType contributorWithDistribution = sciMotherRequirement.getContributorList().get(0);
		assertFalse(contributorWithDistribution instanceof SciMotherRequirementType);
		assertEquals(1, sciMotherRequirement.getDepth());
	}

	public void testCheckOutput() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		double numerator = 1.0;
		double denominator = 0.0;
		double result = numerator / denominator;
		Double myResult = new Double(result);

		RequirementsType requirements = new RequirementsType();
		RequirementType y1 = new RequirementType();
		y1.setParentId("Y1");
		y1.setId("Y1");
		y1.setName("Y1");
		y1.setCode("Y1");
		y1.setMean(myResult);
		y1.setStandardDeviation(1.2);
		y1.setTnc(1.023);
		y1.setValueInf(0.123);
		y1.setValueSup(1.07);
		y1.setCap(0.765);
		y1.setCpk(0.856);
		y1.setCenteringMax(1.23);
		requirements.getRequirement().add(y1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tmY1 = new TransferModelType();
		tmY1.setParentId("Y1");
		tmY1.setId("TM Y1");
		tmY1.setFormula("#UR-0000211#*5");
		tmY1.setReportFormula("01A01-211*5");
		tmY1.setUncertainty(0);
		transferModels.getTransferModel().add(tmY1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM Y1");
		contributor1.setId("01A01-211");
		contributor1.setName("UR-0000211");
		contributor1.setCode("01A01-211");
		contributor1.setCap(0.657);
		contributor1.setCpk(0.778);
		requirements.getRequirement().add(contributor1);

		TransferModelType tmY2 = new TransferModelType();
		tmY2.setParentId("01A01-211");
		tmY2.setId("TM Y2");
		tmY2.setFormula("5*#UR-0000212#");
		tmY2.setReportFormula("5*01A01-212");
		tmY2.setUncertainty(0);
		transferModels.getTransferModel().add(tmY2);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y2");
		contributor2.setId("01A01-212");
		contributor2.setName("UR-0000212");
		contributor2.setCode("01A01-212");
		contributor2.setCap(myResult);
		contributor2.setCpk(0.887);
		requirements.getRequirement().add(contributor2);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Multi level test------------------------------------------ \n");
		printRecursive(sciMotherRequirement);
		assertEquals(2, sciMotherRequirement.getDepth());
		System.out.println("\n-----------------Displaying the levels and corrosponding sciMotherRequirement at that particular level--------------------------- \n");
		Field levelsMap = MonteCarloStrategyServiceImpl.class.getDeclaredField("levelsMap");
		levelsMap.setAccessible(true);
		Map<Integer, List<SciMotherRequirementType>> levelsMap1 = (HashMap<Integer, List<SciMotherRequirementType>>) levelsMap.get(this.monteCarloStrategyServiceImpl);
		for (Integer key : levelsMap1.keySet()) {
			System.out.print("Level = " + key + " and SciMotherRequirement = ");
			for (SciMotherRequirementType smrt : levelsMap1.get(key)) {
				System.out.print(" " + smrt.getId());
			}
			System.out.println();
		}
		System.out.println("\n-----------------------Status of sciMotherRequirement, whether already processed or not.------------------- \n");
		sciMotherReqiurementSysoutCheck(sciMotherRequirement, levelsMap1);

		this.monteCarloStrategyServiceImpl.checkOutput();
		assertEquals(new BigInteger("2020"), calculation.getResult().getStatus());
		assertEquals("INFINITE_MONTE_CARLO_OUTPUT", calculation.getResult().getDescription());
		assertEquals("Y1,UR-0000212", calculation.getResult().getImpactedObject());
		assertEquals("Error", calculation.getResult().getLevel());
	}

	/**
	 * Check for organizeInput, Whether the list of requirements and tranferModels for single level are properly organise or not.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testOrganizeInput() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

		RequirementsType requirements = new RequirementsType();

		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("Y1");
		requirement1.setId("Y1");
		requirement1.setName("Y1");
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
		requirements.getRequirement().add(contributor1);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM Y1");
		contributor2.setId("X1.2");
		contributor2.setName("X1.2");
		requirements.getRequirement().add(contributor2);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Single level test----------------------------------------------------------- \n");
		printRecursive(sciMotherRequirement);
		assertEquals(1, sciMotherRequirement.getDepth());
	}

	/**
	 * Check for organizeInput, Whether the list of requirements and tranferModels for multiple Level are properly organise or not.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testOrganizeInputModified() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

		RequirementsType requirements = new RequirementsType();

		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("SMRT");
		requirement1.setId("SMRT");
		requirement1.setName("SMRT");
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("SMRT");
		tm1.setId("TM1");
		transferModels.getTransferModel().add(tm1);

		RequirementType contributor1 = new RequirementType();
		contributor1.setParentId("TM1");
		contributor1.setId("C1");
		contributor1.setName("C1");
		requirements.getRequirement().add(contributor1);

		RequirementType contributor2 = new RequirementType();
		contributor2.setParentId("TM1");
		contributor2.setId("C2");
		contributor2.setName("C2");
		requirements.getRequirement().add(contributor2);

		RequirementType contributor3 = new RequirementType();
		contributor3.setParentId("TM1");
		contributor3.setId("C3");
		contributor3.setName("C3");
		requirements.getRequirement().add(contributor3);

		RequirementType contributor4 = new RequirementType();
		contributor4.setParentId("TM1");
		contributor4.setId("C4");
		contributor4.setName("C4");
		requirements.getRequirement().add(contributor4);

		TransferModelType tm2 = new TransferModelType();
		tm2.setParentId("C2");
		tm2.setId("TM2");
		transferModels.getTransferModel().add(tm2);

		RequirementType contributor5 = new RequirementType();
		contributor5.setParentId("TM2");
		contributor5.setId("C5");
		contributor5.setName("C5");
		requirements.getRequirement().add(contributor5);

		RequirementType contributor6 = new RequirementType();
		contributor6.setParentId("TM2");
		contributor6.setId("C6");
		contributor6.setName("C6");
		requirements.getRequirement().add(contributor6);

		RequirementType contributor7 = new RequirementType();
		contributor7.setParentId("TM2");
		contributor7.setId("C7");
		contributor7.setName("C7");
		requirements.getRequirement().add(contributor7);

		TransferModelType tm3 = new TransferModelType();
		tm3.setParentId("C4");
		tm3.setId("TM3");
		transferModels.getTransferModel().add(tm3);

		RequirementType contributor8 = new RequirementType();
		contributor8.setParentId("TM3");
		contributor8.setId("C8");
		contributor8.setName("C8");
		requirements.getRequirement().add(contributor8);

		RequirementType contributor9 = new RequirementType();
		contributor9.setParentId("TM3");
		contributor9.setId("C9");
		contributor9.setName("C9");
		requirements.getRequirement().add(contributor9);

		RequirementType contributor10 = new RequirementType();
		contributor10.setParentId("TM3");
		contributor10.setId("C10");
		contributor10.setName("C10");
		requirements.getRequirement().add(contributor10);

		TransferModelType tm4 = new TransferModelType();
		tm4.setParentId("C7");
		tm4.setId("TM4");
		transferModels.getTransferModel().add(tm4);

		RequirementType contributor11 = new RequirementType();
		contributor11.setParentId("TM4");
		contributor11.setId("C11");
		contributor11.setName("C11");
		requirements.getRequirement().add(contributor11);

		RequirementType contributor12 = new RequirementType();
		contributor12.setParentId("TM4");
		contributor12.setId("C12");
		contributor12.setName("C12");
		requirements.getRequirement().add(contributor12);

		TransferModelType tm5 = new TransferModelType();
		tm5.setParentId("C10");
		tm5.setId("TM5");
		transferModels.getTransferModel().add(tm5);

		RequirementType contributor13 = new RequirementType();
		contributor13.setParentId("TM5");
		contributor13.setId("C13");
		contributor13.setName("C13");
		requirements.getRequirement().add(contributor13);

		RequirementType contributor14 = new RequirementType();
		contributor14.setParentId("TM5");
		contributor14.setId("C14");
		contributor14.setName("C14");
		requirements.getRequirement().add(contributor14);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Multi level test----------------------------------------------------------- \n");
		printRecursive(sciMotherRequirement);

		assertEquals(3, sciMotherRequirement.getDepth());
		System.out.println("\n---------------------Displaying the levels and corrosponding sciMotherRequirement at that particular level------------------------ \n");
		Field levelsMap = MonteCarloStrategyServiceImpl.class.getDeclaredField("levelsMap");
		levelsMap.setAccessible(true);
		Map<Integer, List<SciMotherRequirementType>> levelsMap1 = (HashMap<Integer, List<SciMotherRequirementType>>) levelsMap.get(this.monteCarloStrategyServiceImpl);
		for (Integer key : levelsMap1.keySet()) {
			System.out.print("Level = " + key + " and SciMotherRequirement = ");
			for (SciMotherRequirementType smrt : levelsMap1.get(key)) {
				System.out.print(" " + smrt.getId());
			}
			System.out.println();
		}
		sciMotherReqiurementSysoutCheck(sciMotherRequirement, levelsMap1);
	}

	/**
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testRunMultiLevelMonteCarlo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		RequirementsType requirements = new RequirementsType();
		// ---------------------------------------------------------- TM Y ------------------------------------------
		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("Y");
		requirement1.setId("Y");
		requirement1.setName("Y");
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("Y");
		tm1.setId("TM Y");
		transferModels.getTransferModel().add(tm1);

		RequirementType X1 = new RequirementType();
		X1.setParentId("TM Y");
		X1.setId("X1");
		X1.setName("X1");
		requirements.getRequirement().add(X1);
		RequirementType X2 = new RequirementType();
		X2.setParentId("TM Y");
		X2.setId("X2");
		X2.setName("X2");
		requirements.getRequirement().add(X2);
		RequirementType X3 = new RequirementType();
		X3.setParentId("TM Y");
		X3.setId("X3");
		X3.setName("X3");
		requirements.getRequirement().add(X3);
		// ---------------------------------------------------------- TM X1 ------------------------------------------
		TransferModelType tmX1 = new TransferModelType();
		tmX1.setParentId("X1");
		tmX1.setId("TM X1");
		transferModels.getTransferModel().add(tmX1);
		RequirementType X11 = new RequirementType();
		X11.setParentId("TM X1");
		X11.setId("X11");
		X11.setName("X11");
		requirements.getRequirement().add(X11);
		RequirementType X12 = new RequirementType();
		X12.setParentId("TM X1");
		X12.setId("X12");
		X12.setName("X12");
		requirements.getRequirement().add(X12);
		// ---------------------------------------------------------- TM X11 ------------------------------------------
		TransferModelType tmX11 = new TransferModelType();
		tmX11.setParentId("X11");
		tmX11.setId("TM X11");
		transferModels.getTransferModel().add(tmX11);
		RequirementType X111 = new RequirementType();
		X111.setParentId("TM X11");
		X111.setId("X111");
		X111.setName("X111");
		requirements.getRequirement().add(X111);
		RequirementType X112 = new RequirementType();
		X112.setParentId("TM X11");
		X112.setId("X112");
		X112.setName("X112");
		requirements.getRequirement().add(X112);
		// ---------------------------------------------------------- TM X12 ------------------------------------------
		TransferModelType tmX12 = new TransferModelType();
		tmX12.setParentId("X12");
		tmX12.setId("TM X12");
		transferModels.getTransferModel().add(tmX12);
		RequirementType X121 = new RequirementType();
		X121.setParentId("TM X12");
		X121.setId("X121");
		X121.setName("X121");
		requirements.getRequirement().add(X121);
		RequirementType X122 = new RequirementType();
		X122.setParentId("TM X12");
		X122.setId("X122");
		X122.setName("X122");
		requirements.getRequirement().add(X122);
		// ---------------------------------------------------------- TM X2 ------------------------------------------
		TransferModelType tmX2 = new TransferModelType();
		tmX2.setParentId("X2");
		tmX2.setId("TM X2");
		transferModels.getTransferModel().add(tmX2);
		RequirementType X31222 = new RequirementType();
		X31222.setParentId("TM X2");
		X31222.setId("X31222");
		X31222.setName("X31222");
		requirements.getRequirement().add(X31222);
		RequirementType X3122 = new RequirementType();
		X3122.setParentId("TM X2");
		X3122.setId("X3122");
		X3122.setName("X3122");
		requirements.getRequirement().add(X3122);
		RequirementType X21 = new RequirementType();
		X21.setParentId("TM X2");
		X21.setId("X21");
		X21.setName("X21");
		requirements.getRequirement().add(X21);
		RequirementType X22 = new RequirementType();
		X22.setParentId("TM X2");
		X22.setId("X22");
		X22.setName("X22");
		requirements.getRequirement().add(X22);

		// ---------------------------------------------------------- TM X21 ------------------------------------------
		TransferModelType tmX21 = new TransferModelType();
		tmX21.setParentId("X21");
		tmX21.setId("TM X21");
		transferModels.getTransferModel().add(tmX21);
		RequirementType X211 = new RequirementType();
		X211.setParentId("TM X21");
		X211.setId("X211");
		X211.setName("X211");
		requirements.getRequirement().add(X211);
		RequirementType X212 = new RequirementType();
		X212.setParentId("TM X21");
		X212.setId("X212");
		X212.setName("X212");
		requirements.getRequirement().add(X212);

		// ---------------------------------------------------------- TM X22 ------------------------------------------
		TransferModelType tmX22 = new TransferModelType();
		tmX22.setParentId("X22");
		tmX22.setId("TM X22");
		transferModels.getTransferModel().add(tmX22);
		RequirementType X221 = new RequirementType();
		X221.setParentId("TM X22");
		X221.setId("X221");
		X221.setName("X221");
		requirements.getRequirement().add(X221);
		RequirementType X222 = new RequirementType();
		X222.setParentId("TM X22");
		X222.setId("X222");
		X222.setName("X222");
		requirements.getRequirement().add(X222);
		// ---------------------------------------------------------- TM X24 ------------------------------------------
		TransferModelType tmX24 = new TransferModelType();
		tmX24.setParentId("X3122");
		tmX24.setId("TM X24");
		transferModels.getTransferModel().add(tmX24);
		RequirementType X31221 = new RequirementType();
		X31221.setParentId("TM X24");
		X31221.setId("X31221");
		X31221.setName("X31221");
		requirements.getRequirement().add(X31221);
		RequirementType X31222Rev = new RequirementType();
		X31222Rev.setParentId("TM X24");
		X31222Rev.setId("X31222");
		X31222Rev.setName("X31222");
		requirements.getRequirement().add(X31222Rev);

		// ---------------------------------------------------------- TM X3 ------------------------------------------
		TransferModelType tmX3 = new TransferModelType();
		tmX3.setParentId("X3");
		tmX3.setId("TM X3");
		transferModels.getTransferModel().add(tmX3);
		RequirementType X31 = new RequirementType();
		X31.setParentId("TM X3");
		X31.setId("X31");
		X31.setName("X31");
		requirements.getRequirement().add(X31);
		RequirementType X32 = new RequirementType();
		X32.setParentId("TM X3");
		X32.setId("X32");
		X32.setName("X32");
		requirements.getRequirement().add(X32);
		// ---------------------------------------------------------- TM X31 ------------------------------------------
		TransferModelType tmX31 = new TransferModelType();
		tmX31.setParentId("X31");
		tmX31.setId("TM X31");
		transferModels.getTransferModel().add(tmX31);
		RequirementType X311 = new RequirementType();
		X311.setParentId("TM X31");
		X311.setId("X311");
		X311.setName("X311");
		requirements.getRequirement().add(X311);
		RequirementType X312 = new RequirementType();
		X312.setParentId("TM X31");
		X312.setId("X312");
		X312.setName("X312");
		requirements.getRequirement().add(X312);
		// ---------------------------------------------------------- TM X311 ------------------------------------------
		TransferModelType tmX311 = new TransferModelType();
		tmX311.setParentId("X311");
		tmX311.setId("TM X311");
		transferModels.getTransferModel().add(tmX311);
		RequirementType X3111 = new RequirementType();
		X3111.setParentId("TM X311");
		X3111.setId("X3111");
		X3111.setName("X3111");
		requirements.getRequirement().add(X3111);
		RequirementType X3112 = new RequirementType();
		X3112.setParentId("TM X311");
		X3112.setId("X3112");
		X3112.setName("X3112");
		requirements.getRequirement().add(X3112);
		// ---------------------------------------------------------- TM X3111 ------------------------------------------
		TransferModelType tmX3111 = new TransferModelType();
		tmX3111.setParentId("X3111");
		tmX3111.setId("TM X3111");
		transferModels.getTransferModel().add(tmX3111);
		RequirementType X31111 = new RequirementType();
		X31111.setParentId("TM X3111");
		X31111.setId("X31111");
		X31111.setName("X31111");
		requirements.getRequirement().add(X31111);
		RequirementType X31112 = new RequirementType();
		X31112.setParentId("TM X3111");
		X31112.setId("X31112");
		X31112.setName("X31112");
		requirements.getRequirement().add(X31112);
		// ---------------------------------------------------------- TM X312 ------------------------------------------
		TransferModelType tmX312 = new TransferModelType();
		tmX312.setParentId("X312");
		tmX312.setId("TM X312");
		transferModels.getTransferModel().add(tmX312);
		RequirementType X3121 = new RequirementType();
		X3121.setParentId("TM X312");
		X3121.setId("X3121");
		X3121.setName("X3121");
		requirements.getRequirement().add(X3121);
		RequirementType X3122Rev = new RequirementType();
		X3122Rev.setParentId("TM X312");
		X3122Rev.setId("X3122");
		X3122Rev.setName("X3122");
		requirements.getRequirement().add(X3122Rev);
		// ---------------------------------------------------------- TM X3121 ------------------------------------------
		TransferModelType tmX3121 = new TransferModelType();
		tmX3121.setParentId("X3121");
		tmX3121.setId("TM X3121");
		transferModels.getTransferModel().add(tmX3121);
		RequirementType X31211 = new RequirementType();
		X31211.setParentId("TM X3121");
		X31211.setId("X31211");
		X31211.setName("X31211");
		requirements.getRequirement().add(X31211);
		RequirementType X31212 = new RequirementType();
		X31212.setParentId("TM X3121");
		X31212.setId("X31212");
		X31212.setName("X31212");
		requirements.getRequirement().add(X31212);
		// ---------------------------------------------------------- TM X3122 ------------------------------------------
		TransferModelType tmX3122 = new TransferModelType();
		tmX3122.setParentId("X3122");
		tmX3122.setId("TM X3122");
		transferModels.getTransferModel().add(tmX3122);
		RequirementType X31221Rev = new RequirementType();
		X31221Rev.setParentId("TM X3122");
		X31221Rev.setId("X31221");
		X31221Rev.setName("X31221");
		requirements.getRequirement().add(X31221Rev);
		RequirementType X31222Rev2 = new RequirementType();
		X31222Rev2.setParentId("TM X3122");
		X31222Rev2.setId("X31222");
		X31222Rev2.setName("X31222");
		requirements.getRequirement().add(X31222Rev2);
		// ---------------------------------------------------------- TM X32 ------------------------------------------
		TransferModelType tmX32 = new TransferModelType();
		tmX32.setParentId("X32");
		tmX32.setId("TM X32");
		transferModels.getTransferModel().add(tmX32);
		RequirementType X321 = new RequirementType();
		X321.setParentId("TM X32");
		X321.setId("X321");
		X321.setName("X321");
		requirements.getRequirement().add(X321);
		RequirementType X322 = new RequirementType();
		X322.setParentId("TM X32");
		X322.setId("X322");
		X322.setName("X322");
		requirements.getRequirement().add(X322);
		RequirementType X323 = new RequirementType();
		X323.setParentId("TM X32");
		X323.setId("X323");
		X323.setName("X323");
		requirements.getRequirement().add(X323);
		// -------------------------------------------------------------------------------------------------------------------
		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();

		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Multi level test------------------------------------------ \n");
		printRecursive(sciMotherRequirement);

		assertEquals(5, sciMotherRequirement.getDepth());
		System.out.println("\n-----------------Displaying the levels and corrosponding sciMotherRequirement at that particular level--------------------------- \n");
		Field levelsMap = MonteCarloStrategyServiceImpl.class.getDeclaredField("levelsMap");
		levelsMap.setAccessible(true);
		Map<Integer, List<SciMotherRequirementType>> levelsMap1 = (HashMap<Integer, List<SciMotherRequirementType>>) levelsMap.get(this.monteCarloStrategyServiceImpl);
		for (Integer key : levelsMap1.keySet()) {
			System.out.print("Level = " + key + " and SciMotherRequirement = ");
			for (SciMotherRequirementType smrt : levelsMap1.get(key)) {
				System.out.print(" " + smrt.getId());
			}
			System.out.println();
		}
		sciMotherReqiurementSysoutCheck(sciMotherRequirement, levelsMap1);
	}

	public void testRunSingleLevelMonteCarlo1() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		RequirementsType requirements = new RequirementsType();

		RequirementType motherRequirement = new RequirementType();
		motherRequirement.setName("UR-0000001");
		motherRequirement.setCode("UR-0000001");
		motherRequirement.setParentId("UR-0000001");
		motherRequirement.setId("UR-0000001");
		motherRequirement.setMean(0.1234);
		motherRequirement.setCenteringMax(1.2);
		motherRequirement.setTnc(1.023);
		motherRequirement.setCap(0.765);
		motherRequirement.setCpk(0.856);
		motherRequirement.setValueInf(new Double(0));
		motherRequirement.setValueSup(new Double(2));
		requirements.getRequirement().add(motherRequirement);

		TransferModelsType transferModels = new TransferModelsType();

		TransferModelType transferModel = new TransferModelType();
		transferModel.setParentId("UR-0000001");
		transferModel.setId("TM1");
		transferModel.setFormula("Rugo - C006 + (C002 / RFNR)");
		transferModel.setUncertainty(0.0);
		transferModels.getTransferModel().add(transferModel);

		RequirementType contributor1 = new RequirementType();
		contributor1.setName("Rugo");
		contributor1.setParentId("TM1");
		contributor1.setId("Rugo");
		contributor1.setLaw(LawType.NORMAL_STD);
		contributor1.setParamLaw1(new Double(1.25));
		contributor1.setParamLaw2(new Double(0.2));
		contributor1.setValueInf(new Double(0.0));
		contributor1.setValueSup(new Double(2.0));
		requirements.getRequirement().add(contributor1);

		RequirementType contributor2 = new RequirementType();
		contributor2.setName("C002");
		contributor2.setParentId("TM1");
		contributor2.setId("C002");
		contributor2.setLaw(LawType.UNIFORM);
		contributor2.setParamLaw1(new Double(0.2));
		contributor2.setParamLaw2(new Double(1.25));
		requirements.getRequirement().add(contributor2);

		RequirementType contributor3 = new RequirementType();
		contributor3.setName("RFNR");
		contributor3.setParentId("TM1");
		contributor3.setId("RFNR");
		contributor3.setLaw(LawType.UNIFORM);
		contributor3.setParamLaw1(new Double(0.2));
		contributor3.setParamLaw2(new Double(1.25));
		requirements.getRequirement().add(contributor3);

		RequirementType contributor4 = new RequirementType();
		contributor4.setName("RBUT");
		contributor4.setParentId("TM1");
		contributor4.setId("RBUT");
		contributor4.setLaw(LawType.UNIFORM);
		contributor4.setParamLaw1(new Double(0.2));
		contributor4.setParamLaw2(new Double(1.25));
		requirements.getRequirement().add(contributor4);

		RequirementType contributor5 = new RequirementType();
		contributor5.setName("C006");
		contributor5.setParentId("TM1");
		contributor5.setId("C006");
		contributor5.setLaw(LawType.WEIBULL);
		contributor5.setParamLaw1(new Double(12));
		contributor5.setParamLaw2(new Double(13));
		contributor5.setValueInf(new Double(0));
		contributor5.setValueSup(new Double(1));
		requirements.getRequirement().add(contributor5);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();
		Field motherRequirement1 = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement1.setAccessible(true);
		assertNotNull(motherRequirement1);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement1.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Single level test------------------------------------------ \n");
		printRecursive(sciMotherRequirement);
		assertEquals(1, sciMotherRequirement.getDepth());
		System.out.println("\n-----------------Displaying the levels and corrosponding sciMotherRequirement at that particular level--------------------------- \n");
		Field levelsMap = MonteCarloStrategyServiceImpl.class.getDeclaredField("levelsMap");
		levelsMap.setAccessible(true);
		Map<Integer, List<SciMotherRequirementType>> levelsMap1 = (HashMap<Integer, List<SciMotherRequirementType>>) levelsMap.get(this.monteCarloStrategyServiceImpl);
		for (Integer key : levelsMap1.keySet()) {
			System.out.print("Level = " + key + " and SciMotherRequirement = ");
			for (SciMotherRequirementType smrt : levelsMap1.get(key)) {
				System.out.print(" " + smrt.getId());
			}
			System.out.println();
		}
		System.out.println("\n-----------------------Status of sciMotherRequirement, whether already processed or not.------------------- \n");
		this.monteCarloStrategyServiceImpl.runCalculation();
		Calculation calculationResult = this.monteCarloStrategyServiceImpl.prepareOutput();
		// System.out.println("Mother reqiurement status = " + this.monteCarloStrategyServiceImpl.motherRqmtProcessingStatus);
		System.out.println("-----------------------------------------------Calculation Result-----------------------------------------------------------");
		System.out.println("Calculated Mean = " + calculationResult.getRequirements().getRequirement().get(0).getMean());
		System.out.println("Calculated Cpk = " + calculationResult.getRequirements().getRequirement().get(0).getCpk());
		System.out.println("Calculated Cap = " + calculationResult.getRequirements().getRequirement().get(0).getCap());
		System.out.println("Calculated CenteringMax = " + calculationResult.getRequirements().getRequirement().get(0).getCenteringMax());
		System.out.println("Calculated Tnc = " + calculationResult.getRequirements().getRequirement().get(0).getTnc());
		System.out.println("Calculated ValueInf = " + calculationResult.getRequirements().getRequirement().get(0).getValueInf());
		System.out.println("Calculated ValueSup = " + calculationResult.getRequirements().getRequirement().get(0).getValueSup());
		sciMotherReqiurementSysoutCheck(sciMotherRequirement, levelsMap1);
	}

	public void testRunMultiLevelMonteCarlo1() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		RequirementsType requirements = new RequirementsType();
		// ---------------------------------------------------------- TM Y ------------------------------------------
		RequirementType requirement1 = new RequirementType();
		requirement1.setParentId("4704.51127.5376.90021");
		requirement1.setId("4704.51127.5376.90021");
		requirement1.setName("UR-0000111");
		requirement1.setValueInf(new Double(0));
		requirement1.setValueSup(new Double(2));
		requirements.getRequirement().add(requirement1);

		TransferModelsType transferModels = new TransferModelsType();
		TransferModelType tm1 = new TransferModelType();
		tm1.setParentId("4704.51127.5376.90021");
		tm1.setId("4704.51127.5376.90031");
		tm1.setFormula("UR-0001111 * UR-0001112");
		tm1.setUncertainty(0.0);
		transferModels.getTransferModel().add(tm1);

		RequirementType X1 = new RequirementType();
		X1.setParentId("4704.51127.5376.90031");
		X1.setId("4704.51127.5376.11111");
		X1.setName("UR-0001111");
		X1.setCode("01A01-1111");
		X1.setNominal(0.5);
		X1.setValueInf(new Double(-5));
		X1.setValueSup(new Double(2));
		X1.setItInf(-5.5);
		X1.setItSup(1.5);
		requirements.getRequirement().add(X1);

		RequirementType X2 = new RequirementType();
		X2.setParentId("4704.51127.5376.90031");
		X2.setId("4704.51127.5376.11112");
		X2.setName("UR-0001112");
		X2.setCode("01A01-1112");
		X2.setNominal(0.5);
		X2.setValueInf(new Double(-5));
		X2.setValueSup(new Double(2));
		X2.setItInf(-5.5);
		X2.setItSup(1.5);
		requirements.getRequirement().add(X2);

		// ---------------------------------------------------------- TM X1 ------------------------------------------
		TransferModelType tmX1 = new TransferModelType();
		tmX1.setParentId("4704.51127.5376.11111");
		tmX1.setId("4704.51127.5376.90041");
		tmX1.setFormula("(2 * UR-5555555) * (2 * UR-5555555)");
		tmX1.setUncertainty(0.0);
		transferModels.getTransferModel().add(tmX1);

		RequirementType X11 = new RequirementType();
		X11.setParentId("4704.51127.5376.90041");
		X11.setId("4704.51127.5376.55555");
		X11.setName("UR-5555555");
		X11.setCode("K");
		X11.setNominal(0.5);
		X11.setValueInf(new Double(-5));
		X11.setValueSup(new Double(2));
		X11.setItInf(-5.5);
		X11.setItSup(1.5);
		X11.setLaw(LawType.NORMAL_STD);
		X11.setParamLaw1(new Double(0.0));
		X11.setParamLaw2(new Double(1.0));
		requirements.getRequirement().add(X11);

		TransferModelType tmX2 = new TransferModelType();
		tmX2.setParentId("4704.51127.5376.11112");
		tmX2.setId("4704.51127.5376.90042");
		tmX2.setFormula("4 * UR-5555555");
		tmX2.setUncertainty(0.0);
		transferModels.getTransferModel().add(tmX2);

		RequirementType X11_2 = new RequirementType();
		X11_2.setParentId("4704.51127.5376.90042");
		X11_2.setId("4704.51127.5376.55555");
		X11_2.setName("UR-5555555");
		X11.setCode("K");
		X11_2.setNominal(0.5);
		X11_2.setValueInf(new Double(-5));
		X11_2.setValueSup(new Double(2));
		X11_2.setItInf(-5.5);
		X11_2.setItSup(1.5);
		X11_2.setLaw(LawType.NORMAL_STD);
		X11_2.setParamLaw1(new Double(0.0));
		X11_2.setParamLaw2(new Double(1.0));
		requirements.getRequirement().add(X11_2);

		Calculation calculation = new Calculation();
		calculation.setProjectName("PSA Geometric Test");
		calculation.setCalculationType(CalculationType.MONTE_CARLO);
		calculation.setRequirements(requirements);
		calculation.setTransferModels(transferModels);

		this.monteCarloStrategyServiceImpl = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		this.monteCarloStrategyServiceImpl.organizeInput();
		Field motherRequirement = MonteCarloStrategyServiceImpl.class.getDeclaredField("motherRequirement");
		motherRequirement.setAccessible(true);
		assertNotNull(motherRequirement);
		SciMotherRequirementType sciMotherRequirement = (SciMotherRequirementType) motherRequirement.get(monteCarloStrategyServiceImpl);
		System.out.println("\n -------------------------------Multi level test------------------------------------------ \n");
		printRecursive(sciMotherRequirement);
		assertEquals(2, sciMotherRequirement.getDepth());
		System.out.println("\n-----------------Displaying the levels and corrosponding sciMotherRequirement at that particular level--------------------------- \n");
		Field levelsMap = MonteCarloStrategyServiceImpl.class.getDeclaredField("levelsMap");
		levelsMap.setAccessible(true);
		Map<Integer, List<SciMotherRequirementType>> levelsMap1 = (HashMap<Integer, List<SciMotherRequirementType>>) levelsMap.get(this.monteCarloStrategyServiceImpl);
		for (Integer key : levelsMap1.keySet()) {
			System.out.print("Level = " + key + " and SciMotherRequirement = ");
			for (SciMotherRequirementType smrt : levelsMap1.get(key)) {
				System.out.print(" " + smrt.getId());
			}
			System.out.println();
		}
		System.out.println("\n-----------------------Status of sciMotherRequirement, whether already processed or not.------------------- \n");
		sciMotherReqiurementSysoutCheck(sciMotherRequirement, levelsMap1);

	}

	/**
	 * 
	 */
	private void sciMotherReqiurementSysoutCheck(SciMotherRequirementType sciMotherRequirement, Map<Integer, List<SciMotherRequirementType>> levelsMap1) {
		for (int i = sciMotherRequirement.getDepth(); i > 0; i--) {
			List<SciMotherRequirementType> motherRequirementsList = levelsMap1.get(i);
			for (SciMotherRequirementType sciMotherRequirementType : motherRequirementsList) {
				System.out.println((sciMotherRequirementType).getName());
			}
		}
	}

	/**
	 * Prints the hierarchy of the reqiurementList , transferModel and contributors.
	 * 
	 * @param sciMotherRequirement
	 */
	private void printRecursive(SciMotherRequirementType sciMotherRequirement) {
		System.out.println("MotherRequirement = " + sciMotherRequirement.getName() + "  -> Transfer Model =  " + sciMotherRequirement.getTransferModel().getId() + "  ->  ");
		System.out.print("Their children:  ");
		for (SciRequirementType contributor : sciMotherRequirement.getContributorList()) {
			System.out.print(contributor.getName() + "    ");
		}
		System.out.println();
		System.out.println();
		for (SciRequirementType contributor : sciMotherRequirement.getContributorList()) {
			if (contributor instanceof SciMotherRequirementType) {
				printRecursive((SciMotherRequirementType) contributor);
			}
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.monteCarloStrategyServiceImpl = null;
	}

}

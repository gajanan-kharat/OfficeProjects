/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcurves;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class ExportStatisticalCurvesDataRepresentation.
 */
public class ExportStatisticalCurvesDataRepresentation {

	/*
	 * ************************************************
	 *
	 * Properties common to PDF and Excel
	 * 
	 * *************************************
	 * 
	 */

	/** The tvv label. */
	private String tvvLabel;

	/** The vehicle family label. */
	private String vehicleFamilyLabel;

	/** The project code label. */
	private String projectCodeLabel;

	/** The moteur label. */
	private String moteurLabel;

	/** The gear box label. */
	private String gearBoxLabel;

	/** The psa reference. */
	private String psaReference;

	/** The inertia value. */
	private int inertiaValue;

	/** The sample entity id. */
	private Long sampleEntityId;

	/** The regulation group label. */
	private String regulationGroupLabel;

	/** The statistical calculation rule label. */
	private String statisticalCalculationRuleLabel;

	/** The type of test label. */
	private String typeOfTestLabel;

	/** The vehicle factory label. */
	private String vehicleFactoryLabel;

	/** The emission standard label. */
	private String emissionStandardLabel;

	/** The emission standard version. */
	private String emissionStandardVersion;

	/** The vehicle file representations list. */
	private List<VehicleFileRepresentation> vehicleFileRepresentationsList;

	/** The debut date. */
	private Date debutDate;

	/** The fin date. */
	private Date finDate;

	/** The calculator value. */
	private String calculatorValue;

	/** The pg gas label list. */
	private List<String> pgGasLabelList;

	/** The vehicle chassis number. */
	/*
	 * ************************************************
	 *
	 * Properties Specific to excel 
	 * 
	 * 
	 * ******************************************************
	 */
	private List<String> vehicleChassisNumber;

	/** The result set bench cell list. */
	private List<String> resultSetBenchCellList;

	/** The result set obd test list. */
	private List<String> resultSetOBDTestList;

	/** The result set set order list. */
	private List<Integer> resultSetSetOrderList;

	/** The results set creation date. */
	private List<Date> resultsSetCreationDate;

	/** The vehicle file statistical decision. */
	private List<String> vehicleFileStatisticalDecision;

	/** The vehicle based pollutant gas test result. */
	private Map<String, Map<String, PollutantGasTestResultRepresentation>> vehicleBasedPollutantGasTestResult;

	/**
	 * Instantiates a new export statistical curves data representation.
	 */
	public ExportStatisticalCurvesDataRepresentation() {
		super();
	}

	/**
	 * Gets the tvv label.
	 * 
	 * @return the tvv label
	 */
	public String getTvvLabel() {
		return tvvLabel;
	}

	/**
	 * Sets the tvv label.
	 * 
	 * @param tvvLabel the new tvv label
	 */
	public void setTvvLabel(String tvvLabel) {
		this.tvvLabel = tvvLabel;
	}

	/**
	 * Gets the vehicle family label.
	 * 
	 * @return the vehicle family label
	 */
	public String getVehicleFamilyLabel() {
		return vehicleFamilyLabel;
	}

	/**
	 * Sets the vehicle family label.
	 * 
	 * @param vehicleFamilyLabel the new vehicle family label
	 */
	public void setVehicleFamilyLabel(String vehicleFamilyLabel) {
		this.vehicleFamilyLabel = vehicleFamilyLabel;
	}

	/**
	 * Gets the project code label.
	 * 
	 * @return the project code label
	 */
	public String getProjectCodeLabel() {
		return projectCodeLabel;
	}

	/**
	 * Sets the project code label.
	 * 
	 * @param projectCodeLabel the new project code label
	 */
	public void setProjectCodeLabel(String projectCodeLabel) {
		this.projectCodeLabel = projectCodeLabel;
	}

	/**
	 * Gets the moteur label.
	 * 
	 * @return the moteur label
	 */
	public String getMoteurLabel() {
		return moteurLabel;
	}

	/**
	 * Sets the moteur label.
	 * 
	 * @param moteurLabel the new moteur label
	 */
	public void setMoteurLabel(String moteurLabel) {
		this.moteurLabel = moteurLabel;
	}

	/**
	 * Gets the gear box label.
	 * 
	 * @return the gear box label
	 */
	public String getGearBoxLabel() {
		return gearBoxLabel;
	}

	/**
	 * Sets the gear box label.
	 * 
	 * @param gearBoxLabel the new gear box label
	 */
	public void setGearBoxLabel(String gearBoxLabel) {
		this.gearBoxLabel = gearBoxLabel;
	}

	/**
	 * Gets the psa reference.
	 * 
	 * @return the psa reference
	 */
	public String getPsaReference() {
		return psaReference;
	}

	/**
	 * Sets the psa reference.
	 * 
	 * @param psaReference the new psa reference
	 */
	public void setPsaReference(String psaReference) {
		this.psaReference = psaReference;
	}

	/**
	 * Gets the inertia value.
	 * 
	 * @return the inertia value
	 */
	public int getInertiaValue() {
		return inertiaValue;
	}

	/**
	 * Sets the inertia value.
	 * 
	 * @param inertiaValue the new inertia value
	 */
	public void setInertiaValue(int inertiaValue) {
		this.inertiaValue = inertiaValue;
	}

	/**
	 * Gets the regulation group label.
	 * 
	 * @return the regulation group label
	 */
	public String getRegulationGroupLabel() {
		return regulationGroupLabel;
	}

	/**
	 * Sets the regulation group label.
	 * 
	 * @param regulationGroupLabel the new regulation group label
	 */
	public void setRegulationGroupLabel(String regulationGroupLabel) {
		this.regulationGroupLabel = regulationGroupLabel;
	}

	/**
	 * Gets the statistical calculation rule label.
	 * 
	 * @return the statistical calculation rule label
	 */
	public String getStatisticalCalculationRuleLabel() {
		return statisticalCalculationRuleLabel;
	}

	/**
	 * Sets the statistical calculation rule label.
	 * 
	 * @param statisticalCalculationRuleLabel the new statistical calculation rule label
	 */
	public void setStatisticalCalculationRuleLabel(String statisticalCalculationRuleLabel) {
		this.statisticalCalculationRuleLabel = statisticalCalculationRuleLabel;
	}

	/**
	 * Gets the type of test label.
	 * 
	 * @return the type of test label
	 */
	public String getTypeOfTestLabel() {
		return typeOfTestLabel;
	}

	/**
	 * Sets the type of test label.
	 * 
	 * @param typeOfTestLabel the new type of test label
	 */
	public void setTypeOfTestLabel(String typeOfTestLabel) {
		this.typeOfTestLabel = typeOfTestLabel;
	}

	/**
	 * Gets the vehicle factory label.
	 * 
	 * @return the vehicle factory label
	 */
	public String getVehicleFactoryLabel() {
		return vehicleFactoryLabel;
	}

	/**
	 * Sets the vehicle factory label.
	 * 
	 * @param vehicleFactoryLabel the new vehicle factory label
	 */
	public void setVehicleFactoryLabel(String vehicleFactoryLabel) {
		this.vehicleFactoryLabel = vehicleFactoryLabel;
	}

	/**
	 * Gets the emission standard label.
	 * 
	 * @return the emission standard label
	 */
	public String getEmissionStandardLabel() {
		return emissionStandardLabel;
	}

	/**
	 * Sets the emission standard label.
	 * 
	 * @param emissionStandardLabel the new emission standard label
	 */
	public void setEmissionStandardLabel(String emissionStandardLabel) {
		this.emissionStandardLabel = emissionStandardLabel;
	}

	/**
	 * Gets the emission standard version.
	 * 
	 * @return the emission standard version
	 */
	public String getEmissionStandardVersion() {
		return emissionStandardVersion;
	}

	/**
	 * Sets the emission standard version.
	 * 
	 * @param emissionStandardVersion the new emission standard version
	 */
	public void setEmissionStandardVersion(String emissionStandardVersion) {
		this.emissionStandardVersion = emissionStandardVersion;
	}

	/**
	 * Gets the debut date.
	 * 
	 * @return the debut date
	 */
	public Date getDebutDate() {
		return debutDate;
	}

	/**
	 * Sets the debut date.
	 * 
	 * @param debutDate the new debut date
	 */
	public void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}

	/**
	 * Gets the fin date.
	 * 
	 * @return the fin date
	 */
	public Date getFinDate() {
		return finDate;
	}

	/**
	 * Sets the fin date.
	 * 
	 * @param finDate the new fin date
	 */
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}

	/**
	 * Gets the vehicle file representations list.
	 * 
	 * @return the vehicle file representations list
	 */
	public List<VehicleFileRepresentation> getVehicleFileRepresentationsList() {
		return vehicleFileRepresentationsList;
	}

	/**
	 * Sets the vehicle file representations list.
	 * 
	 * @param vehicleFileRepresentationsList the new vehicle file representations list
	 */
	public void setVehicleFileRepresentationsList(List<VehicleFileRepresentation> vehicleFileRepresentationsList) {
		this.vehicleFileRepresentationsList = vehicleFileRepresentationsList;
	}

	/**
	 * Gets the result set bench cell list.
	 * 
	 * @return the result set bench cell list
	 */
	public List<String> getResultSetBenchCellList() {
		return resultSetBenchCellList;
	}

	/**
	 * Sets the result set bench cell list.
	 * 
	 * @param resultSetBenchCellList the new result set bench cell list
	 */
	public void setResultSetBenchCellList(List<String> resultSetBenchCellList) {
		this.resultSetBenchCellList = resultSetBenchCellList;
	}

	/**
	 * Gets the result set obd test list.
	 * 
	 * @return the result set obd test list
	 */
	public List<String> getResultSetOBDTestList() {
		return resultSetOBDTestList;
	}

	/**
	 * Sets the result set obd test list.
	 * 
	 * @param resultSetOBDTestList the new result set obd test list
	 */
	public void setResultSetOBDTestList(List<String> resultSetOBDTestList) {
		this.resultSetOBDTestList = resultSetOBDTestList;
	}

	/**
	 * Gets the result set set order list.
	 * 
	 * @return the result set set order list
	 */
	public List<Integer> getResultSetSetOrderList() {
		return resultSetSetOrderList;
	}

	/**
	 * Sets the result set set order list.
	 * 
	 * @param resultSetSetOrderList the new result set set order list
	 */
	public void setResultSetSetOrderList(List<Integer> resultSetSetOrderList) {
		this.resultSetSetOrderList = resultSetSetOrderList;
	}

	/**
	 * Gets the results set creation date.
	 * 
	 * @return the results set creation date
	 */
	public List<Date> getResultsSetCreationDate() {
		return resultsSetCreationDate;
	}

	/**
	 * Sets the results set creation date.
	 * 
	 * @param resultsSetCreationDate the new results set creation date
	 */
	public void setResultsSetCreationDate(List<Date> resultsSetCreationDate) {
		this.resultsSetCreationDate = resultsSetCreationDate;
	}

	/**
	 * Gets the calculator value.
	 * 
	 * @return the calculator value
	 */
	public String getCalculatorValue() {
		return calculatorValue;
	}

	/**
	 * Sets the calculator value.
	 * 
	 * @param calculatorValue the new calculator value
	 */
	public void setCalculatorValue(String calculatorValue) {
		this.calculatorValue = calculatorValue;
	}

	/**
	 * Gets the vehicle chassis number.
	 * 
	 * @return the vehicle chassis number
	 */
	public List<String> getVehicleChassisNumber() {
		return vehicleChassisNumber;
	}

	/**
	 * Sets the vehicle chassis number.
	 * 
	 * @param vehicleChassisNumber the new vehicle chassis number
	 */
	public void setVehicleChassisNumber(List<String> vehicleChassisNumber) {
		this.vehicleChassisNumber = vehicleChassisNumber;
	}

	/**
	 * Gets the vehicle file statistical decision.
	 * 
	 * @return the vehicle file statistical decision
	 */
	public List<String> getVehicleFileStatisticalDecision() {
		return vehicleFileStatisticalDecision;
	}

	/**
	 * Sets the vehicle file statistical decision.
	 * 
	 * @param vehicleFileStatisticalDecision the new vehicle file statistical decision
	 */
	public void setVehicleFileStatisticalDecision(List<String> vehicleFileStatisticalDecision) {
		this.vehicleFileStatisticalDecision = vehicleFileStatisticalDecision;
	}

	/**
	 * Gets the vehicle based pollutant gas test result.
	 * 
	 * @return the vehicle based pollutant gas test result
	 */
	public Map<String, Map<String, PollutantGasTestResultRepresentation>> getVehicleBasedPollutantGasTestResult() {
		return vehicleBasedPollutantGasTestResult;
	}

	/**
	 * Sets the vehicle based pollutant gas test result.
	 * 
	 * @param vehicleBasedPollutantGasTestResult the vehicle based pollutant gas test result
	 */
	public void setVehicleBasedPollutantGasTestResult(Map<String, Map<String, PollutantGasTestResultRepresentation>> vehicleBasedPollutantGasTestResult) {
		this.vehicleBasedPollutantGasTestResult = vehicleBasedPollutantGasTestResult;
	}

	/**
	 * Gets the sample entity id.
	 * 
	 * @return the sample entity id
	 */
	public Long getSampleEntityId() {
		return sampleEntityId;
	}

	/**
	 * Sets the sample entity id.
	 * 
	 * @param sampleEntityId the new sample entity id
	 */
	public void setSampleEntityId(Long sampleEntityId) {
		this.sampleEntityId = sampleEntityId;
	}

	/**
	 * Gets the pg gas label list.
	 * 
	 * @return the pg gas label list
	 */
	public List<String> getPgGasLabelList() {
		return pgGasLabelList;
	}

	/**
	 * Sets the pg gas label list.
	 * 
	 * @param pgGasLabelList the new pg gas label list
	 */
	public void setPgGasLabelList(List<String> pgGasLabelList) {
		this.pgGasLabelList = pgGasLabelList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExportStatisticalCurvesDataRepresentation [tvvLabel=" + tvvLabel + ", vehicleFamilyLabel=" + vehicleFamilyLabel + ", moteurLabel=" + moteurLabel + ", gearBoxLabel=" + gearBoxLabel + "]";
	}

}

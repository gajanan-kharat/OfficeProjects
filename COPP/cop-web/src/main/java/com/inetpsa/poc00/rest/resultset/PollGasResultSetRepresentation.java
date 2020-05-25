/*
 * Creation : Nov 28, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.resultset;

import java.util.List;

import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;

/**
 * PollGasResultSetRepresentation.
 *
 * @author mehaj
 */
public class PollGasResultSetRepresentation extends BaseRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The is validated. */
	private boolean isValidated;

	/** The updatedate. */
	private String updatedate;

	/** The in quarantine. */
	private boolean inQuarantine;

	/** The vehiclefile id. */
	private Long vehiclefileId;

	/** The obd test. */
	private String obdTest;

	/** The bench cell. */
	private String benchCell;

	/** The kept in statistical sample. */
	private boolean keptInStatisticalSample;

	/** The veh file statistical decision. */
	private String vehFileStatisticalDecision;

	/** The statisticalcalc rule label. */
	private String statisticalcalcRuleLabel;

	/** The reference santorin. */
	private String referenceSantorin;

	/** The statistical order. */
	private String statisticalOrder;

	/** The sample id. */
	private String sampleId;

	/** The is cell enabled. */
	private boolean isCellEnabled;

	/** The result set modified. */
	private boolean resultSetModified;

	/** The set order. */
	private Integer setOrder;

	/** The pollutant gas test result representation list. */
	private List<PollutantGasTestResultRepresentation> pollutantGasTestResultRepresentationList;

	/**
	 * Getter entityId.
	 *
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId.
	 *
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter pollutantGasTestResultRepresentationList.
	 *
	 * @return the pollutantGasTestResultRepresentationList
	 */
	public List<PollutantGasTestResultRepresentation> getPollutantGasTestResultRepresentationList() {
		return pollutantGasTestResultRepresentationList;
	}

	/**
	 * Setter pollutantGasTestResultRepresentationList.
	 *
	 * @param pollutantGasTestResultRepresentationList the pollutantGasTestResultRepresentationList to set
	 */
	public void setPollutantGasTestResultRepresentationList(List<PollutantGasTestResultRepresentation> pollutantGasTestResultRepresentationList) {
		this.pollutantGasTestResultRepresentationList = pollutantGasTestResultRepresentationList;
	}

	/**
	 * Getter isValidated.
	 *
	 * @return the isValidated
	 */
	public boolean isValidated() {
		return isValidated;
	}

	/**
	 * Setter isValidated.
	 *
	 * @param isValidated the isValidated to set
	 */
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	/**
	 * Getter updatedate.
	 *
	 * @return the updatedate
	 */
	public String getUpdatedate() {
		return updatedate;
	}

	/**
	 * Setter updatedate.
	 *
	 * @param updatedate the updatedate to set
	 */
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * Getter inQuarantine.
	 *
	 * @return the inQuarantine
	 */
	public boolean isInQuarantine() {
		return inQuarantine;
	}

	/**
	 * Setter inQuarantine.
	 *
	 * @param inQuarantine the inQuarantine to set
	 */
	public void setInQuarantine(boolean inQuarantine) {
		this.inQuarantine = inQuarantine;
	}

	/**
	 * Getter vehiclefileId.
	 *
	 * @return the vehiclefileId
	 */
	public Long getVehiclefileId() {
		return vehiclefileId;
	}

	/**
	 * Setter vehiclefileId.
	 *
	 * @param vehiclefileId the vehiclefileId to set
	 */
	public void setVehiclefileId(Long vehiclefileId) {
		this.vehiclefileId = vehiclefileId;
	}

	/**
	 * Getter obdTest.
	 *
	 * @return the obdTest
	 */
	public String getObdTest() {
		return obdTest;
	}

	/**
	 * Setter obdTest.
	 *
	 * @param obdTest the obdTest to set
	 */
	public void setObdTest(String obdTest) {
		this.obdTest = obdTest;
	}

	/**
	 * Getter benchCell.
	 *
	 * @return the benchCell
	 */
	public String getBenchCell() {
		return benchCell;
	}

	/**
	 * Setter benchCell.
	 *
	 * @param benchCell the benchCell to set
	 */
	public void setBenchCell(String benchCell) {
		this.benchCell = benchCell;
	}

	/**
	 * Getter keptInStatisticalSample.
	 *
	 * @return the keptInStatisticalSample
	 */
	public boolean isKeptInStatisticalSample() {
		return keptInStatisticalSample;
	}

	/**
	 * Setter keptInStatisticalSample.
	 *
	 * @param keptInStatisticalSample the keptInStatisticalSample to set
	 */
	public void setKeptInStatisticalSample(boolean keptInStatisticalSample) {
		this.keptInStatisticalSample = keptInStatisticalSample;
	}

	/**
	 * Getter vehFileStatisticalDecision.
	 *
	 * @return the vehFileStatisticalDecision
	 */
	public String getVehFileStatisticalDecision() {
		return vehFileStatisticalDecision;
	}

	/**
	 * Setter vehFileStatisticalDecision.
	 *
	 * @param vehFileStatisticalDecision the vehFileStatisticalDecision to set
	 */
	public void setVehFileStatisticalDecision(String vehFileStatisticalDecision) {
		this.vehFileStatisticalDecision = vehFileStatisticalDecision;
	}

	/**
	 * Getter statisticalcalcRuleLabel.
	 *
	 * @return the statisticalcalcRuleLabel
	 */
	public String getStatisticalcalcRuleLabel() {
		return statisticalcalcRuleLabel;
	}

	/**
	 * Setter statisticalcalcRuleLabel.
	 *
	 * @param statisticalcalcRuleLabel the statisticalcalcRuleLabel to set
	 */
	public void setStatisticalcalcRuleLabel(String statisticalcalcRuleLabel) {
		this.statisticalcalcRuleLabel = statisticalcalcRuleLabel;
	}

	/**
	 * Getter referenceSantorin.
	 *
	 * @return the referenceSantorin
	 */
	public String getReferenceSantorin() {
		return referenceSantorin;
	}

	/**
	 * Setter referenceSantorin.
	 *
	 * @param referenceSantorin the referenceSantorin to set
	 */
	public void setReferenceSantorin(String referenceSantorin) {
		this.referenceSantorin = referenceSantorin;
	}

	/**
	 * Getter isCellEnabled.
	 *
	 * @return the isCellEnabled
	 */
	public boolean getIsCellEnabled() {
		return isCellEnabled;
	}

	/**
	 * Setter isCellEnabled.
	 *
	 * @param isCellEnabled the isCellEnabled to set
	 */
	public void setIsCellEnabled(boolean isCellEnabled) {
		this.isCellEnabled = isCellEnabled;
	}

	/**
	 * Gets the sets the order.
	 *
	 * @return the sets the order
	 */
	public Integer getSetOrder() {
		return setOrder;
	}

	/**
	 * Sets the sets the order.
	 *
	 * @param setOrder the new sets the order
	 */
	public void setSetOrder(Integer setOrder) {
		this.setOrder = setOrder;
	}

	/**
	 * Gets the statistical order.
	 *
	 * @return the statistical order
	 */
	public String getStatisticalOrder() {
		return statisticalOrder;
	}

	/**
	 * Sets the statistical order.
	 *
	 * @param statisticalOrder the new statistical order
	 */
	public void setStatisticalOrder(String statisticalOrder) {
		this.statisticalOrder = statisticalOrder;
	}

	/**
	 * Gets the sample id.
	 *
	 * @return the sample id
	 */
	public String getSampleId() {
		return sampleId;
	}

	/**
	 * Sets the sample id.
	 *
	 * @param sampleId the new sample id
	 */
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	/**
	 * Checks if is result set modified.
	 *
	 * @return true, if is result set modified
	 */
	public boolean isResultSetModified() {
		return resultSetModified;
	}

	/**
	 * Sets the result set modified.
	 *
	 * @param resultSetModified the new result set modified
	 */
	public void setResultSetModified(boolean resultSetModified) {
		this.resultSetModified = resultSetModified;
	}

}

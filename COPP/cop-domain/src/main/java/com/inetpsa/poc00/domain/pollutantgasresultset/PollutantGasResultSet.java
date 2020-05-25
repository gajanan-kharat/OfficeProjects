package com.inetpsa.poc00.domain.pollutantgasresultset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class PollutantGasResultSet.
 */
@Entity
@Table(name = "COPQTPRS")
public class PollutantGasResultSet extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The bench cell. */
	@Column(name = "BENCH_CELL")
	private String benchCell;

	/** The in quarantine. */
	@Column(name = "IN_QUARANTINE")
	private Boolean inQuarantine;

	/** The kept in stat sample. */
	@Column(name = "KEPT_IN_STAT_SAMPLE")
	private Boolean keptInStatSample;

	/** The obd test. */
	@Column(name = "OBD_TEST")
	private String obdTest;

	/** The set order. */
	@Column(name = "SET_ORDER")
	private Integer setOrder;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Column(name = "STATISTICAL_ORDER")
	private Integer statisticalOrder;

	/** The pollutant gas test result. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pollutantGasResultSet")
	private List<PollutantGasTestResult> pollutantGasTestResult = new ArrayList<>();

	/** The vehicle file. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_FILE_ID")
	private VehicleFile vehicleFile;

	/** The statistical sample. */
	@ManyToOne
	@JoinColumn(name = "SAMPLE_ID")
	private StatisticalSample statisticalSample;

	/**
	 * Sets the last update.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

	/*
	
	Gets the
	entity id.**@return
	the entityId*/

	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the vehicle file.
	 * 
	 * @return the vehicleFile
	 */
	public VehicleFile getVehicleFile() {
		return vehicleFile;
	}

	/**
	 * Sets the vehicle file.
	 * 
	 * @param vehicleFile the vehicleFile to set
	 */
	public void setVehicleFile(VehicleFile vehicleFile) {
		this.vehicleFile = vehicleFile;
	}

	/**
	 * Gets the bench cell.
	 * 
	 * @return the benchCell
	 */
	public String getBenchCell() {
		return benchCell;
	}

	/**
	 * Sets the bench cell.
	 * 
	 * @param benchCell the benchCell to set
	 */
	public void setBenchCell(String benchCell) {
		this.benchCell = benchCell;
	}

	/**
	 * Gets the in quarantine.
	 * 
	 * @return the inQuarantine
	 */
	public Boolean getInQuarantine() {
		return inQuarantine;
	}

	/**
	 * Sets the in quarantine.
	 * 
	 * @param inQuarantine the inQuarantine to set
	 */
	public void setInQuarantine(Boolean inQuarantine) {
		this.inQuarantine = inQuarantine;
	}

	/**
	 * Gets the kept in stat sample.
	 * 
	 * @return the keptInStatSample
	 */
	public Boolean getKeptInStatSample() {
		return keptInStatSample;
	}

	/**
	 * Sets the kept in stat sample.
	 * 
	 * @param keptInStatSample the keptInStatSample to set
	 */
	public void setKeptInStatSample(Boolean keptInStatSample) {
		this.keptInStatSample = keptInStatSample;
	}

	/**
	 * Gets the obd test.
	 * 
	 * @return the obdTest
	 */
	public String getObdTest() {
		return obdTest;
	}

	/**
	 * Sets the obd test.
	 * 
	 * @param obdTest the obdTest to set
	 */
	public void setObdTest(String obdTest) {
		this.obdTest = obdTest;
	}

	/**
	 * Gets the sets the order.
	 * 
	 * @return the setOrder
	 */
	public Integer getSetOrder() {
		return setOrder;
	}

	/**
	 * Sets the sets the order.
	 * 
	 * @param setOrder the setOrder to set
	 */
	public void setSetOrder(Integer setOrder) {
		this.setOrder = setOrder;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the update date.
	 * 
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Getter pollutantGasTestResult
	 * 
	 * @return the pollutantGasTestResult
	 */
	public List<PollutantGasTestResult> getPollutantGasTestResult() {
		return pollutantGasTestResult;
	}

	/**
	 * Setter pollutantGasTestResult
	 * 
	 * @param pollutantGasTestResult the pollutantGasTestResult to set
	 */
	public void setPollutantGasTestResult(List<PollutantGasTestResult> pollutantGasTestResult) {
		this.pollutantGasTestResult = pollutantGasTestResult;
	}

	/**
	 * Gets the statistical sample.
	 *
	 * @return the statistical sample
	 */
	public StatisticalSample getStatisticalSample() {
		return statisticalSample;
	}

	/**
	 * Sets the statistical sample.
	 *
	 * @param statisticalSample the new statistical sample
	 */
	public void setStatisticalSample(StatisticalSample statisticalSample) {
		this.statisticalSample = statisticalSample;
	}

	/**
	 * Sets the update date.
	 *
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatisticalOrder() {
		return statisticalOrder;
	}

	public void setStatisticalOrder(Integer statisticalOrder) {
		this.statisticalOrder = statisticalOrder;
	}

}

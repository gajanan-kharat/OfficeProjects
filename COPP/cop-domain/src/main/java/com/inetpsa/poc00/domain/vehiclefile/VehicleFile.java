package com.inetpsa.poc00.domain.vehiclefile;

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
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.archivebox.ArchiveBox;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.conditioningresult.ConditioningResult;
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResult;
import com.inetpsa.poc00.domain.opacityresult.OpacityResult;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.restitutionfile.RestitutionFile;
import com.inetpsa.poc00.domain.schedule.Schedule;
import com.inetpsa.poc00.domain.specialtestcondition.SpecialTestCondition;
import com.inetpsa.poc00.domain.testconditioncomment.TestConditionComment;
import com.inetpsa.poc00.domain.traceability.Traceability;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;

/**
 * The Class VehicleFile.
 */
@Entity
@Table(name = "COPQTVHF")
public class VehicleFile extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The statistical decision. */
	@Column(name = "STATISTICAL_DECISION")
	private String statisticalDecision;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate = new Date();

	/** The vehicle file santorin reference. */
	@Column(name = "SANTORIN_REFERENCE")
	private String santorinReference;

	/** The schedule. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SCHEDULE_ID")
	private Schedule schedule;

	/** The reception file. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RECEPTION_FILE_ID")
	private ReceptionFile receptionFile;

	/** The restitution file. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESTITUTION_FILE_ID")
	private RestitutionFile restitutionFile;

	/** The opacity result. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "OPACITY_RESULT_ID")
	private OpacityResult opacityResult;

	/** The vehicle file. */
	@OneToOne(mappedBy = "vehicleFile")
	private PreparationFile preparationFile;

	/** The vehicle file status. */
	@ManyToOne
	@JoinColumn(name = "VFILE_STATUS_ID")
	private VehicleFileStatus vehicleFileStatus;

	/** The vehicle file status. */
	@ManyToOne
	@JoinColumn(name = "PREVIOUS_VF_STATUS_ID")
	private VehicleFileStatus previousVFStatus;

	/** The history list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFileHistory")
	private List<Traceability> historyList = new ArrayList<>();

	/** The pg result set. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFile")
	private List<PollutantGasResultSet> pgResultSet = new ArrayList<>();

	/** The pg result set. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFileExp")
	private List<ExpertiseResult> expertiseResultList = new ArrayList<>();

	/** The special test condition. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFile")
	private List<SpecialTestCondition> specialTestCondition = new ArrayList<>();

	/** The special test condition. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFile")
	private List<TestConditionComment> testConditionComment = new ArrayList<>();

	/** The cond result list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleFile")
	private List<ConditioningResult> condResultList = new ArrayList<>();

	/** The basket. */
	@ManyToOne
	@JoinColumn(name = "BASKET_ID")
	private Basket basket;

	/** The vehicle. */
	@ManyToOne
	@JoinColumn(name = "VEHICLE_ID")
	private Vehicle vehicle;

	/** The test type. */
	@ManyToOne
	@JoinColumn(name = "TYPE_TEST_ID")
	private TypeOfTest typeOfTest;

	/** The archive box. */
	@ManyToOne
	@JoinColumn(name = "ARCHIVE_BOX_ID")
	private ArchiveBox archiveBox;

	/**
	 * Gets the entity id.
	 * 
	 * @return the entityId
	 */
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
	 * Gets the vehicle.
	 * 
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * Sets the vehicle.
	 * 
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * Gets the schedule.
	 * 
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Sets the schedule.
	 * 
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * Gets the reception file.
	 * 
	 * @return the receptionFile
	 */
	public ReceptionFile getReceptionFile() {
		return receptionFile;
	}

	/**
	 * Sets the reception file.
	 * 
	 * @param receptionFile the receptionFile to set
	 */
	public void setReceptionFile(ReceptionFile receptionFile) {
		this.receptionFile = receptionFile;
	}

	/**
	 * Gets the restitution file.
	 * 
	 * @return the restitutionFile
	 */
	public RestitutionFile getRestitutionFile() {
		return restitutionFile;
	}

	/**
	 * Sets the restitution file.
	 * 
	 * @param restitutionFile the restitutionFile to set
	 */
	public void setRestitutionFile(RestitutionFile restitutionFile) {
		this.restitutionFile = restitutionFile;
	}

	/**
	 * Gets the archive box.
	 * 
	 * @return the archiveBox
	 */
	public ArchiveBox getArchiveBox() {
		return archiveBox;
	}

	/**
	 * Sets the archive box.
	 * 
	 * @param archiveBox the archiveBox to set
	 */
	public void setArchiveBox(ArchiveBox archiveBox) {
		this.archiveBox = archiveBox;
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
	 * Sets the update date.
	 * 
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Sets the last update.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

	/**
	 * Gets the statistical decision.
	 * 
	 * @return the statisticalDecision
	 */
	public String getStatisticalDecision() {
		return statisticalDecision;
	}

	/**
	 * Sets the statistical decision.
	 * 
	 * @param statisticalDecision the statisticalDecision to set
	 */
	public void setStatisticalDecision(String statisticalDecision) {
		this.statisticalDecision = statisticalDecision;
	}

	/**
	 * Gets the vehicle file status.
	 * 
	 * @return the vehicle file status
	 */
	public VehicleFileStatus getVehicleFileStatus() {
		return vehicleFileStatus;
	}

	/**
	 * Sets the vehicle file status.
	 * 
	 * @param vehicleFileStatus the new vehicle file status
	 */
	public void setVehicleFileStatus(VehicleFileStatus vehicleFileStatus) {
		this.vehicleFileStatus = vehicleFileStatus;
	}

	/**
	 * Gets the history list.
	 * 
	 * @return the historyList
	 */
	public List<Traceability> getHistoryList() {
		return historyList;
	}

	/**
	 * Gets the opacity result.
	 * 
	 * @return the opacityResult
	 */
	public OpacityResult getOpacityResult() {
		return opacityResult;
	}

	/**
	 * Sets the opacity result.
	 * 
	 * @param opacityResult the opacityResult to set
	 */
	public void setOpacityResult(OpacityResult opacityResult) {
		this.opacityResult = opacityResult;
	}

	/**
	 * Gets the pg result set.
	 * 
	 * @return the pgResultSet
	 */
	public List<PollutantGasResultSet> getPgResultSet() {
		return pgResultSet;
	}

	/**
	 * Sets the history list.
	 * 
	 * @param historyList the historyList to set
	 */
	public void setHistoryList(List<Traceability> historyList) {
		this.historyList = historyList;
	}

	/**
	 * Gets the type of test.
	 * 
	 * @return the type of test
	 */
	public TypeOfTest getTypeOfTest() {
		return typeOfTest;
	}

	/**
	 * Sets the type of test.
	 * 
	 * @param typeOfTest the new type of test
	 */
	public void setTypeOfTest(TypeOfTest typeOfTest) {
		this.typeOfTest = typeOfTest;
	}

	/**
	 * Gets the basket.
	 * 
	 * @return the basket
	 */
	public Basket getBasket() {
		return basket;
	}

	/**
	 * Sets the basket.
	 * 
	 * @param basket the new basket
	 */
	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	/**
	 * Getter santorinReference.
	 * 
	 * @return the santorinReference
	 */
	public String getSantorinReference() {
		return santorinReference;
	}

	/**
	 * Setter santorinReference.
	 * 
	 * @param santorinReference the santorinReference to set
	 */
	public void setSantorinReference(String santorinReference) {
		this.santorinReference = santorinReference;
	}

	/**
	 * Gets the preparation file.
	 *
	 * @return the preparation file
	 */
	public PreparationFile getPreparationFile() {
		return preparationFile;
	}

	/**
	 * Sets the preparation file.
	 *
	 * @param preparationFile the new preparation file
	 */
	public void setPreparationFile(PreparationFile preparationFile) {
		this.preparationFile = preparationFile;
	}

	/**
	 * Sets the pg result set.
	 *
	 * @param pgResultSet the new pg result set
	 */
	public void setPgResultSet(List<PollutantGasResultSet> pgResultSet) {
		this.pgResultSet = pgResultSet;
	}

	/**
	 * Getter expertiseResultList.
	 *
	 * @return the expertiseResultList
	 */
	public List<ExpertiseResult> getExpertiseResultList() {
		return expertiseResultList;
	}

	/**
	 * Setter expertiseResultList.
	 *
	 * @param expertiseResultList the expertiseResultList to set
	 */
	public void setExpertiseResultList(List<ExpertiseResult> expertiseResultList) {
		this.expertiseResultList = expertiseResultList;
	}

	/**
	 * Getter specialTestCondition.
	 *
	 * @return the specialTestCondition
	 */
	public List<SpecialTestCondition> getSpecialTestCondition() {
		return specialTestCondition;
	}

	/**
	 * Setter specialTestCondition.
	 *
	 * @param specialTestCondition the specialTestCondition to set
	 */
	public void setSpecialTestCondition(List<SpecialTestCondition> specialTestCondition) {
		this.specialTestCondition = specialTestCondition;
	}

	/**
	 * Getter testConditionComment.
	 *
	 * @return the testConditionComment
	 */
	public List<TestConditionComment> getTestConditionComment() {
		return testConditionComment;
	}

	/**
	 * Setter testConditionComment.
	 *
	 * @param testConditionComment the testConditionComment to set
	 */
	public void setTestConditionComment(List<TestConditionComment> testConditionComment) {
		this.testConditionComment = testConditionComment;
	}

	/**
	 * Getter condResultList
	 * 
	 * @return the condResultList
	 */
	public List<ConditioningResult> getCondResultList() {
		return condResultList;
	}

	/**
	 * Setter condResultList
	 * 
	 * @param condResultList the condResultList to set
	 */
	public void setCondResultList(List<ConditioningResult> condResultList) {
		this.condResultList = condResultList;
	}

	/**
	 * Getter previousVFStatus
	 * 
	 * @return the previousVFStatus
	 */
	public VehicleFileStatus getPreviousVFStatus() {
		return previousVFStatus;
	}

	/**
	 * Setter previousVFStatus
	 * 
	 * @param previousVFStatus the previousVFStatus to set
	 */
	public void setPreviousVFStatus(VehicleFileStatus previousVFStatus) {
		this.previousVFStatus = previousVFStatus;
	}

}

package com.inetpsa.poc00.rest.receptionfile;

import java.util.Date;

import com.inetpsa.poc00.rest.team.TeamRepresentation;
import com.inetpsa.poc00.rest.user.UserRepresentation;

/**
 * The Class ReceptionFileRepresentation.
 */
public class ReceptionFileRepresentation {

	/** The entity id. */
	private Long receptionFileId;

	/** The edited. */
	private boolean edited = false;

	/** The counter mark. */
	private String counterMark;

	/** The chasis number. */
	private String chasisNumber;

	/** The registration. */
	private String registration;

	/** The vehicle. */
	private String vehicle;

	/** The user id last name first name. */
	private String userIdLastNameFirstName;

	/** The gender test. */
	private String genderTest;

	/** The reservation. */
	private String reservation;

	/** The parking number. */
	private String parkingNumber;

	/** The vehicle entity id. */
	private Long vehicleFileEntityId;

	/** The parking lot id. */
	private Long parkingLotId;

	/** The vehicle id. */
	private Long vehicleId;

	/** The publishing user representation. */
	private static UserRepresentation publishingUserRepresentation;

	/** The publishing team representation. */
	private static TeamRepresentation publishingTeamRepresentation;

	/** The email id list. */
	private String[] emailIdList;

	/** The arrival date. */
	private Date arrivalDate;

	/**
	 * Instantiates a new reception file representation.
	 */
	public ReceptionFileRepresentation() {
		super();
	}

	/**
	 * Instantiates a new reception file representation.
	 * 
	 * @param receptionFileEntityId the entity id
	 * @param counterMark the counter mark
	 * @param chasisNumber the chasis number
	 * @param registration the registration
	 * @param vehicle the vehicle
	 * @param genderTest the gender test
	 * @param reservation the reservation
	 * @param vehicleFileEntityId the vehicle entity id
	 * @param userIdLastNameFirstName the user id last name first name
	 * @param parkingNumber the parking number
	 * @param vehicleId the vehicle id
	 * @param parkingLotId the parking lot id
	 */

	public ReceptionFileRepresentation(Long receptionFileEntityId, String counterMark, String chasisNumber, String registration, String vehicle, String genderTest, String reservation, Long vehicleFileEntityId, String userIdLastNameFirstName, String parkingNumber, Long vehicleId, Long parkingLotId, Date arrivalDate) {
		this.receptionFileId = receptionFileEntityId;
		this.counterMark = counterMark;
		this.chasisNumber = chasisNumber;
		this.registration = registration;
		this.vehicle = vehicle;
		this.genderTest = genderTest;
		this.reservation = reservation;
		this.parkingNumber = parkingNumber;
		this.vehicleFileEntityId = vehicleFileEntityId;
		this.userIdLastNameFirstName = userIdLastNameFirstName;
		this.vehicleId = vehicleId;
		this.parkingLotId = parkingLotId;
		this.arrivalDate = arrivalDate;
	}

	/**
	 * Gets the reception file id.
	 * 
	 * @return the reception file id
	 */
	public Long getReceptionFileId() {
		return receptionFileId;
	}

	/**
	 * Sets the reception file id.
	 * 
	 * @param receptionFileId the new reception file id
	 */
	public void setReceptionFileId(Long receptionFileId) {
		this.receptionFileId = receptionFileId;
	}

	/**
	 * Gets the vehicle file entity id.
	 * 
	 * @return the vehicle file entity id
	 */
	public Long getVehicleFileEntityId() {
		return vehicleFileEntityId;
	}

	/**
	 * Sets the vehicle file entity id.
	 * 
	 * @param vehicleFileEntityId the new vehicle file entity id
	 */
	public void setVehicleFileEntityId(Long vehicleFileEntityId) {
		this.vehicleFileEntityId = vehicleFileEntityId;
	}

	/**
	 * Gets the parking lot id.
	 * 
	 * @return the parking lot id
	 */
	public Long getParkingLotId() {
		return parkingLotId;
	}

	/**
	 * Sets the parking lot id.
	 * 
	 * @param parkingLotId the new parking lot id
	 */
	public void setParkingLotId(Long parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	/**
	 * Gets the vehicle id.
	 * 
	 * @return the vehicle id
	 */
	public Long getVehicleId() {
		return vehicleId;
	}

	/**
	 * Sets the vehicle id.
	 * 
	 * @param vehicleId the new vehicle id
	 */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public Long getEntityId() {
		return receptionFileId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Long entityId) {
		this.receptionFileId = entityId;
	}

	/**
	 * Checks if is edited.
	 * 
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 * 
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Gets the counter mark.
	 * 
	 * @return the counter mark
	 */
	public String getCounterMark() {
		return counterMark;
	}

	/**
	 * Sets the counter mark.
	 * 
	 * @param counterMark the new counter mark
	 */
	public void setCounterMark(String counterMark) {
		this.counterMark = counterMark;
	}

	/**
	 * Gets the registration.
	 * 
	 * @return the registration
	 */
	public String getRegistration() {
		return registration;
	}

	/**
	 * Sets the registration.
	 * 
	 * @param registration the new registration
	 */
	public void setRegistration(String registration) {
		this.registration = registration;
	}

	/**
	 * Gets the vehicle.
	 * 
	 * @return the vehicle
	 */
	public String getVehicle() {
		return vehicle;
	}

	/**
	 * Sets the vehicle.
	 * 
	 * @param vehicle the new vehicle
	 */
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * Gets the user id last name first name.
	 * 
	 * @return the user id last name first name
	 */
	public String getUserIdLastNameFirstName() {
		return userIdLastNameFirstName;
	}

	/**
	 * Sets the user id last name first name.
	 * 
	 * @param userIdLastNameFirstName the new user id last name first name
	 */
	public void setUserIdLastNameFirstName(String userIdLastNameFirstName) {
		this.userIdLastNameFirstName = userIdLastNameFirstName;
	}

	/**
	 * Gets the gender test.
	 * 
	 * @return the gender test
	 */
	public String getGenderTest() {
		return genderTest;
	}

	/**
	 * Sets the gender test.
	 * 
	 * @param genderTest the new gender test
	 */
	public void setGenderTest(String genderTest) {
		this.genderTest = genderTest;
	}

	/**
	 * Gets the reservation.
	 * 
	 * @return the reservation
	 */
	public String getReservation() {
		return reservation;
	}

	/**
	 * Sets the reservation.
	 * 
	 * @param reservation the new reservation
	 */
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	/**
	 * Gets the parking number.
	 * 
	 * @return the parking number
	 */
	public String getParkingNumber() {
		return parkingNumber;
	}

	/**
	 * Sets the parking number.
	 * 
	 * @param parkingNumber the new parking number
	 */
	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	/**
	 * Gets the chasis number.
	 * 
	 * @return the chasis number
	 */
	public String getChasisNumber() {
		return chasisNumber;
	}

	/**
	 * Sets the chasis number.
	 * 
	 * @param chasisNumber the new chasis number
	 */
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	/**
	 * Gets the vehicle entity id.
	 * 
	 * @return the vehicle entity id
	 */
	public Long getVehicleEntityId() {
		return vehicleFileEntityId;
	}

	/**
	 * Sets the vehicle entity id.
	 * 
	 * @param vehicleEntityId the new vehicle entity id
	 */
	public void setVehicleEntityId(Long vehicleEntityId) {
		this.vehicleFileEntityId = vehicleEntityId;
	}

	/**
	 * Gets the publishing user representation.
	 * 
	 * @return the publishing user representation
	 */
	public static UserRepresentation getPublishingUserRepresentation() {
		return publishingUserRepresentation;
	}

	/**
	 * Sets the publishing user representation.
	 * 
	 * @param publishingUserRepresentation the new publishing user representation
	 */
	public static void setPublishingUserRepresentation(UserRepresentation publishingUserRepresentation) {
		ReceptionFileRepresentation.publishingUserRepresentation = publishingUserRepresentation;
	}

	/**
	 * Gets the publishing team representation.
	 * 
	 * @return the publishing team representation
	 */
	public static TeamRepresentation getPublishingTeamRepresentation() {
		return publishingTeamRepresentation;
	}

	/**
	 * Sets the publishing team representation.
	 * 
	 * @param publishingTeamRepresentation the new publishing team representation
	 */
	public static void setPublishingTeamRepresentation(TeamRepresentation publishingTeamRepresentation) {
		ReceptionFileRepresentation.publishingTeamRepresentation = publishingTeamRepresentation;
	}

	/**
	 * Gets the email id list.
	 *
	 * @return the email id list
	 */
	public String[] getEmailIdList() {
		return emailIdList;
	}

	/**
	 * Sets the email id list.
	 *
	 * @param emailIdList the new email id list
	 */
	public void setEmailIdList(String[] emailIdList) {
		this.emailIdList = emailIdList;
	}

	/**
	 * Gets the arrival date.
	 *
	 * @return the arrival date
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * Sets the arrival date.
	 *
	 * @param arrivalDate the new arrival date
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReceptionFileRepresentation [entityId=" + receptionFileId + ", edited=" + edited + ", counterMark=" + counterMark + ", chasisNumber=" + chasisNumber + ", registration=" + registration + ", vehicle=" + vehicle + ", userIdLastNameFirstName=" + userIdLastNameFirstName + "genderTest=" + genderTest + ", reservation=" + reservation + ", parkingNumber=" + parkingNumber
				+ ", vehicleEntityId=" + vehicleFileEntityId + "]";
	}

}

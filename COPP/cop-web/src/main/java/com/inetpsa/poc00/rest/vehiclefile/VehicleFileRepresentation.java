/*
 * Creation : Aug 26, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.rest.archivebox.ArchiveBoxRepresentation;
import com.inetpsa.poc00.rest.preparationfile.PreparationFileRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleRepresentation;
import com.inetpsa.poc00.traceability.TraceabilityRepresentation;

/**
 * The Class VehicleFileRepresentation.
 */
public class VehicleFileRepresentation {

    /** The entity id. */
    private Long entityId;

    /** The chasis number. */
    private String chasisNumber;

    /** The counter mark. */
    private String counterMark;

    /** The registration number. */
    private String registrationNumber;

    /** The creation date. */
    private Date creationDate;

    /** The update date. */
    private Date updateDate;

    /** The project code label. */
    private String projectCodeLabel;

    /** The vehicle familylabel. */
    private String vehicleFamilylabel;

    /** The type of test id. */
    private Long typeOfTestId;

    /** The type of test label. */
    private String typeOfTestLabel;

    /** The vehicle file status label. */
    private String vehicleFileStatusLabel;

    /** The user id. */
    private Long userId;

    /** The user name. */
    private String userName;

    /** The guidisply label. */
    private String guidisplyLabel;
    /** The guidisply label. */
    private String guidisplyLabelForResultScr;
    /** The display title. */
    private String displayTitle;

    /** The in basket. */
    private boolean inBasket;

    /** The tvv Label. */
    private String tvvLabel;

    /** The VIN. */
    private String vin;

    /** The referencesantorin. */
    private String referencesantorin;

    /** The referencesantorin. */
    private String vehFileStatisticalDecision;

    /** The vehicle file history. */
    private List<TraceabilityRepresentation> vehicleFileHistory;

    /** The prep file representation. */
    private PreparationFileRepresentation prepFileRepresentation;

    /** The vehicle file status label eng. */
    private String vehicleFileStatusLabelEng;

    /** The vehicle representation. */
    private VehicleRepresentation vehicleRepresentation;

    /** The archive box representation. */
    private ArchiveBoxRepresentation archiveBoxRepresentation;

    /**
     * Instantiates a new vehicle file representation.
     */
    public VehicleFileRepresentation() {
        super();
    }

    /**
     * Instantiates a new vehicle file representation.
     * 
     * @param entityId the entity id
     * @param counterMark the counter mark
     * @param chasisNumber the chasis number
     * @param registrationNumber the registration number
     * @param typeOfTestLabel the type of test label
     */
    public VehicleFileRepresentation(Long entityId, String counterMark, String chasisNumber, String registrationNumber, String typeOfTestLabel) {
        this.entityId = entityId;
        this.chasisNumber = chasisNumber;
        this.counterMark = counterMark;
        this.registrationNumber = registrationNumber;
        this.typeOfTestLabel = typeOfTestLabel;
    }

    /**
     * Instantiates a new vehicle file representation.
     * 
     * @param entityId the entity id
     * @param chasisNumber the chasis number
     * @param counterMark the counter mark
     * @param registrationNumber the registration number
     * @param creationDate the creation date
     * @param projectCodeLabel the project code label
     * @param vehicleFamilyLabel the vehicle family label
     * @param typeOfTestId the type of test id
     * @param typeOfTestLabel the type of test label
     */
    public VehicleFileRepresentation(Long entityId, String chasisNumber, String counterMark, String registrationNumber, Date creationDate,
            String projectCodeLabel, String vehicleFamilyLabel, Long typeOfTestId, String typeOfTestLabel) {
        this.entityId = entityId;
        this.chasisNumber = chasisNumber;
        this.counterMark = counterMark;
        this.registrationNumber = registrationNumber;
        this.creationDate = creationDate;
        this.projectCodeLabel = projectCodeLabel;
        this.vehicleFamilylabel = vehicleFamilyLabel;
        this.typeOfTestId = typeOfTestId;
        this.typeOfTestLabel = typeOfTestLabel;
    }

    /**
     * Instantiates a new vehicle file representation.
     *
     * @param entityId the entity id
     * @param counterMark the counter mark
     * @param chasisNumber the chasis number
     * @param registrationNumber the registration number
     * @param projectCodeLabel the project code label
     * @param vehicleFamilyLabel the vehicle family label
     * @param vehicleFileStatusLabel the vehicle file status label
     * @param typeOfTestLabel the type of test label
     * @param vehicleFileStatusLabelEng the vehicle file status label eng
     */
    public VehicleFileRepresentation(Long entityId, String counterMark, String chasisNumber, String registrationNumber, String projectCodeLabel,
            String vehicleFamilyLabel, String vehicleFileStatusLabel, String typeOfTestLabel, String vehicleFileStatusLabelEng) {

        this.entityId = entityId;
        this.counterMark = counterMark;
        this.chasisNumber = chasisNumber;
        this.registrationNumber = registrationNumber;
        this.projectCodeLabel = projectCodeLabel;
        this.vehicleFamilylabel = vehicleFamilyLabel;
        this.vehicleFileStatusLabel = vehicleFileStatusLabel;
        this.typeOfTestLabel = typeOfTestLabel;
        this.vehicleFileStatusLabelEng = vehicleFileStatusLabelEng;
    }

    /**
     * Gets the entity id.
     * 
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     * 
     * @param entityId the new entity id
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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
     * Gets the registration number.
     * 
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number.
     * 
     * @param registrationNumber the new registration number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * 
     * @param creationDate the new creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
     * Gets the type of test id.
     * 
     * @return the type of test id
     */
    public Long getTypeOfTestId() {
        return typeOfTestId;
    }

    /**
     * Sets the type of test id.
     * 
     * @param typeOfTestId the new type of test id
     */
    public void setTypeOfTestId(Long typeOfTestId) {
        this.typeOfTestId = typeOfTestId;
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
     * Gets the vehicle file status label.
     * 
     * @return the vehicle file status label
     */
    public String getVehicleFileStatusLabel() {
        return vehicleFileStatusLabel;
    }

    /**
     * Sets the vehicle file status label.
     * 
     * @param vehicleFileStatusLabel the new vehicle file status label
     */
    public void setVehicleFileStatusLabel(String vehicleFileStatusLabel) {
        this.vehicleFileStatusLabel = vehicleFileStatusLabel;
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the guidisply label.
     * 
     * @return the guidisply label
     */
    public String getGuidisplyLabel() {
        return guidisplyLabel;
    }

    /**
     * Sets the guidisply label.
     * 
     * @param guidisplyLabel the new guidisply label
     */
    public void setGuidisplyLabel(String guidisplyLabel) {
        this.guidisplyLabel = guidisplyLabel;
    }

    /**
     * Gets the user name.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     * 
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter displayTitle.
     * 
     * @return the displayTitle
     */
    public String getDisplayTitle() {
        return displayTitle;
    }

    /**
     * Setter displayTitle.
     * 
     * @param displayTitle the displayTitle to set
     */
    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    /**
     * Getter inBasket.
     * 
     * @return the inBasket
     */
    public boolean isInBasket() {
        return inBasket;
    }

    /**
     * Setter inBasket.
     * 
     * @param inBasket the inBasket to set
     */
    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
    }

    /**
     * Gets the vehicle familylabel.
     * 
     * @return the vehicle familylabel
     */
    public String getVehicleFamilylabel() {
        return vehicleFamilylabel;
    }

    /**
     * Sets the vehicle familylabel.
     * 
     * @param vehicleFamilylabel the new vehicle familylabel
     */
    public void setVehicleFamilylabel(String vehicleFamilylabel) {
        this.vehicleFamilylabel = vehicleFamilylabel;
    }

    /**
     * Getter tvvLabel.
     * 
     * @return the tvvLabel
     */
    public String getTvvLabel() {
        return tvvLabel;
    }

    /**
     * Setter tvvLabel.
     * 
     * @param tvvLabel the tvvLabel to set
     */
    public void setTvvLabel(String tvvLabel) {
        this.tvvLabel = tvvLabel;
    }

    /**
     * Getter vin.
     * 
     * @return the vin
     */
    public String getVin() {
        return vin;
    }

    /**
     * Setter vin.
     * 
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * Getter referencesantorin.
     * 
     * @return the referencesantorin
     */
    public String getReferencesantorin() {
        return referencesantorin;
    }

    /**
     * Setter referencesantorin.
     * 
     * @param referencesantorin the referencesantorin to set
     */
    public void setReferencesantorin(String referencesantorin) {
        this.referencesantorin = referencesantorin;
    }

    /**
     * Gets the vehicle file history.
     * 
     * @return the vehicle file history
     */
    public List<TraceabilityRepresentation> getVehicleFileHistory() {
        return vehicleFileHistory;
    }

    /**
     * Sets the vehicle file history.
     * 
     * @param vehicleFileHistory the new vehicle file history
     */
    public void setVehicleFileHistory(List<TraceabilityRepresentation> vehicleFileHistory) {
        this.vehicleFileHistory = vehicleFileHistory;
    }

    /**
     * Gets the prep file representation.
     * 
     * @return the prep file representation
     */
    public PreparationFileRepresentation getPrepFileRepresentation() {
        return prepFileRepresentation;
    }

    /**
     * Sets the prep file representation.
     * 
     * @param prepFileRepresentation the new prep file representation
     */
    public void setPrepFileRepresentation(PreparationFileRepresentation prepFileRepresentation) {
        this.prepFileRepresentation = prepFileRepresentation;
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
     * Getter guidisplyLabelForResultScr.
     *
     * @return the guidisplyLabelForResultScr
     */
    public String getGuidisplyLabelForResultScr() {
        return guidisplyLabelForResultScr;
    }

    /**
     * Setter guidisplyLabelForResultScr.
     *
     * @param guidisplyLabelForResultScr the guidisplyLabelForResultScr to set
     */
    public void setGuidisplyLabelForResultScr(String guidisplyLabelForResultScr) {
        this.guidisplyLabelForResultScr = guidisplyLabelForResultScr;
    }

    /**
     * Gets the vehicle file status label eng.
     *
     * @return the vehicle file status label eng
     */
    public String getVehicleFileStatusLabelEng() {
        return vehicleFileStatusLabelEng;
    }

    /**
     * Sets the vehicle file status label eng.
     *
     * @param vehicleFileStatusLabelEng the new vehicle file status label eng
     */
    public void setVehicleFileStatusLabelEng(String vehicleFileStatusLabelEng) {
        this.vehicleFileStatusLabelEng = vehicleFileStatusLabelEng;
    }

    /**
     * Gets the vehicle representation.
     *
     * @return the vehicle representation
     */
    public VehicleRepresentation getVehicleRepresentation() {
        return vehicleRepresentation;
    }

    /**
     * Sets the vehicle representation.
     *
     * @param vehicleRepresentation the new vehicle representation
     */
    public void setVehicleRepresentation(VehicleRepresentation vehicleRepresentation) {
        this.vehicleRepresentation = vehicleRepresentation;
    }

    /**
     * Gets the archive box representation.
     *
     * @return the archive box representation
     */
    public ArchiveBoxRepresentation getArchiveBoxRepresentation() {
        return archiveBoxRepresentation;
    }

    /**
     * Sets the archive box representation.
     *
     * @param archiveBoxRepresentation the new archive box representation
     */
    public void setArchiveBoxRepresentation(ArchiveBoxRepresentation archiveBoxRepresentation) {
        this.archiveBoxRepresentation = archiveBoxRepresentation;
    }

    /**
     * Gets the update date.
     *
     * @return the update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the update date.
     *
     * @param updateDate the new update date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}

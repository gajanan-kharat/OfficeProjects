/*
 * Creation : Aug 22, 2016
 */
package com.inetpsa.poc00.rest.vehicle;

import com.inetpsa.poc00.domain.parkinglot.ParkingLot;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.country.CountryRepresentation;

/**
 * The Class VehicleRepresentation.
 */
public class VehicleRepresentation {

    /** The entity id. */
    private Long entityId;

    /** The vin. */
    private String vin;

    /** The counter mark. */
    private String counterMark;

    /** The registration number. */
    private String registrationNumber;

    /** The chasis number. */
    private String chasisNumber;

    /** The description. */
    private String description;

    /** The flow type. */
    private String flowType;

    /** The destination country. */
    private CountryRepresentation destinationCountry;

    /** The priority indicator. */
    private String priorityIndicator;

    /** The model year. */
    private String modelYear;

    /** The m c o2 i. */
    private Double mCO2I;

    /** The parking lot. */
    private ParkingLot parkingLot;

    /** The test nature. */
    private String testNature;

    /** The vehicle family label. */
    private String vehicleFamilyLabel;

    /** The vehicle file status. */
    private String vehicleFileStatus;

    /** The car factory id. */
    private Long carFactoryId;

    /** The type of test id. */
    private Long typeOfTestId;

    /** The technical case id. */
    private Long technicalCaseId;

    /** The is in basket. */
    private boolean isInBasket;

    /** The fuel label. */
    private String fuelLabel;

    /** The project family label. */
    private String projectFamilyLabel;

    /** The car factoryrepresentation. */
    private CarFactoryRepresentation carFactoryrepresentation;

    /**
     * Instantiates a new vehicle representation.
     * 
     * @param entityId the entity id
     */
    public VehicleRepresentation(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new vehicle representation.
     */
    public VehicleRepresentation() {
        super();
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
     * Gets the vin.
     * 
     * @return the vin
     */
    public String getVin() {
        return vin;
    }

    /**
     * Sets the vin.
     * 
     * @param vin the new vin
     */
    public void setVin(String vin) {
        this.vin = vin;
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
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the flow type.
     * 
     * @return the flow type
     */
    public String getFlowType() {
        return flowType;
    }

    /**
     * Sets the flow type.
     * 
     * @param flowType the new flow type
     */
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    /**
     * Gets the destination country.
     *
     * @return the destination country
     */
    public CountryRepresentation getDestinationCountry() {
        return destinationCountry;
    }

    /**
     * Sets the destination country.
     *
     * @param destinationCountry the new destination country
     */
    public void setDestinationCountry(CountryRepresentation destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    /**
     * Gets the priority indicator.
     * 
     * @return the priority indicator
     */
    public String getPriorityIndicator() {
        return priorityIndicator;
    }

    /**
     * Sets the priority indicator.
     * 
     * @param priorityIndicator the new priority indicator
     */
    public void setPriorityIndicator(String priorityIndicator) {
        this.priorityIndicator = priorityIndicator;
    }

    /**
     * Gets the model year.
     * 
     * @return the model year
     */
    public String getModelYear() {
        return modelYear;
    }

    /**
     * Sets the model year.
     * 
     * @param modelYear the new model year
     */
    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    /**
     * Gets the m c o2 i.
     * 
     * @return the m c o2 i
     */
    public Double getmCO2I() {
        return mCO2I;
    }

    /**
     * Sets the m c o2 i.
     * 
     * @param mCO2I the new m c o2 i
     */
    public void setmCO2I(Double mCO2I) {
        this.mCO2I = mCO2I;
    }

    /**
     * Gets the parking lot.
     * 
     * @return the parking lot
     */
    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    /**
     * Sets the parking lot.
     * 
     * @param parkingLot the new parking lot
     */
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * Gets the test nature.
     * 
     * @return the test nature
     */
    public String getTestNature() {
        return testNature;
    }

    /**
     * Sets the test nature.
     * 
     * @param testNature the new test nature
     */
    public void setTestNature(String testNature) {
        this.testNature = testNature;
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
     * Gets the vehicle file status.
     * 
     * @return the vehicle file status
     */
    public String getVehicleFileStatus() {
        return vehicleFileStatus;
    }

    /**
     * Sets the vehicle file status.
     * 
     * @param vehicleFileStatus the new vehicle file status
     */
    public void setVehicleFileStatus(String vehicleFileStatus) {
        this.vehicleFileStatus = vehicleFileStatus;
    }

    /**
     * Gets the car factory id.
     * 
     * @return the car factory id
     */
    public Long getCarFactoryId() {
        return carFactoryId;
    }

    /**
     * Sets the car factory id.
     * 
     * @param carFactoryId the new car factory id
     */
    public void setCarFactoryId(Long carFactoryId) {
        this.carFactoryId = carFactoryId;
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
     * Gets the technical case id.
     * 
     * @return the technical case id
     */
    public Long getTechnicalCaseId() {
        return technicalCaseId;
    }

    /**
     * Sets the technical case id.
     * 
     * @param technicalCaseId the new technical case id
     */
    public void setTechnicalCaseId(Long technicalCaseId) {
        this.technicalCaseId = technicalCaseId;
    }

    /**
     * Checks if is in basket.
     * 
     * @return true, if is in basket
     */
    public boolean isInBasket() {
        return isInBasket;
    }

    /**
     * Sets the in basket.
     * 
     * @param isInBasket the new in basket
     */
    public void setInBasket(boolean isInBasket) {
        this.isInBasket = isInBasket;
    }

    /**
     * Gets the car factoryrepresentation.
     *
     * @return the car factoryrepresentation
     */
    public CarFactoryRepresentation getCarFactoryrepresentation() {
        return carFactoryrepresentation;
    }

    /**
     * Sets the car factoryrepresentation.
     *
     * @param carFactoryrepresentation the new car factoryrepresentation
     */
    public void setCarFactoryrepresentation(CarFactoryRepresentation carFactoryrepresentation) {
        this.carFactoryrepresentation = carFactoryrepresentation;
    }

    /**
     * Gets the fuel label.
     *
     * @return the fuel label
     */
    public String getFuelLabel() {
        return fuelLabel;
    }

    /**
     * Sets the fuel label.
     *
     * @param fuelLabel the new fuel label
     */
    public void setFuelLabel(String fuelLabel) {
        this.fuelLabel = fuelLabel;
    }

    /**
     * Gets the project family label.
     *
     * @return the project family label
     */
    public String getProjectFamilyLabel() {
        return projectFamilyLabel;
    }

    /**
     * Sets the project family label.
     *
     * @param projectFamilyLabel the new project family label
     */
    public void setProjectFamilyLabel(String projectFamilyLabel) {
        this.projectFamilyLabel = projectFamilyLabel;
    }

}

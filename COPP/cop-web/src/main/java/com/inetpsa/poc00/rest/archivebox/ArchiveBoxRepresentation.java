/*
 * Creation : Feb 9, 2017
 */
package com.inetpsa.poc00.rest.archivebox;

import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;

/**
 * The Class ArchiveBoxRepresentation.
 */
public class ArchiveBoxRepresentation {

    /** The entity id. */
    private Long entityId;

    /** The archive box number. */
    private Long archiveBoxNumber;

    /** The is open. */
    private Boolean isOpen;

    /** The opening date. */
    private Date openingDate;

    /** The closing date. */
    private Date closingDate;

    /** The model year. */
    private String modelYear;

    /** The vehicle files representation. */
    private List<VehicleFileRepresentation> vehicleFilesRepresentation;

    /** The fuel representation. */
    private FuelRepresentation fuelRepresentation;

    /** The project code family reprsentation. */
    private ProjectCodeFamilyRepresentation projectCodeFamilyReprsentation;

    /** The type of test representation. */
    private TypeOfTestRepresentation typeOfTestRepresentation;

    /**
     * Instantiates a new archive box representation.
     */
    public ArchiveBoxRepresentation() {
        super();
    }

    /**
     * Instantiates a new archive box representation.
     *
     * @param archiveBoxNumber the archive box number
     * @param modelYear the model year
     * @param pcf the pcf
     * @param fuel the fuel
     */
    public ArchiveBoxRepresentation(Long archiveBoxNumber, String modelYear, ProjectCodeFamily pcf, Fuel fuel) {
        this.archiveBoxNumber = archiveBoxNumber;
        this.modelYear = modelYear;
        this.projectCodeFamilyReprsentation = new ProjectCodeFamilyRepresentation(pcf.getEntityId(), pcf.getProjectCodeLabel(),
                pcf.getVehicleFamilyLabel());
        this.fuelRepresentation = new FuelRepresentation(fuel.getEntityId(), fuel.getLabel());
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
     * Gets the archive box number.
     *
     * @return the archive box number
     */
    public Long getArchiveBoxNumber() {
        return archiveBoxNumber;
    }

    /**
     * Sets the archive box number.
     *
     * @param archiveBoxNumber the new archive box number
     */
    public void setArchiveBoxNumber(Long archiveBoxNumber) {
        this.archiveBoxNumber = archiveBoxNumber;
    }

    /**
     * Gets the checks if is open.
     *
     * @return the checks if is open
     */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
     * Sets the checks if is open.
     *
     * @param isOpen the new checks if is open
     */
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * Gets the opening date.
     *
     * @return the opening date
     */
    public Date getOpeningDate() {
        return openingDate;
    }

    /**
     * Sets the opening date.
     *
     * @param openingDate the new opening date
     */
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Gets the closing date.
     *
     * @return the closing date
     */
    public Date getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the closing date.
     *
     * @param closingDate the new closing date
     */
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
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
     * Gets the vehicle files representation.
     *
     * @return the vehicle files representation
     */
    public List<VehicleFileRepresentation> getVehicleFilesRepresentation() {
        return vehicleFilesRepresentation;
    }

    /**
     * Sets the vehicle files representation.
     *
     * @param vehicleFilesRepresentation the new vehicle files representation
     */
    public void setVehicleFilesRepresentation(List<VehicleFileRepresentation> vehicleFilesRepresentation) {
        this.vehicleFilesRepresentation = vehicleFilesRepresentation;
    }

    /**
     * Gets the fuel representation.
     *
     * @return the fuel representation
     */
    public FuelRepresentation getFuelRepresentation() {
        return fuelRepresentation;
    }

    /**
     * Sets the fuel representation.
     *
     * @param fuelRepresentation the new fuel representation
     */
    public void setFuelRepresentation(FuelRepresentation fuelRepresentation) {
        this.fuelRepresentation = fuelRepresentation;
    }

    /**
     * Gets the project code family reprsentation.
     *
     * @return the project code family reprsentation
     */
    public ProjectCodeFamilyRepresentation getProjectCodeFamilyReprsentation() {
        return projectCodeFamilyReprsentation;
    }

    /**
     * Sets the project code family reprsentation.
     *
     * @param projectCodeFamilyReprsentation the new project code family reprsentation
     */
    public void setProjectCodeFamilyReprsentation(ProjectCodeFamilyRepresentation projectCodeFamilyReprsentation) {
        this.projectCodeFamilyReprsentation = projectCodeFamilyReprsentation;
    }

    /**
     * Gets the type of test representation.
     *
     * @return the type of test representation
     */
    public TypeOfTestRepresentation getTypeOfTestRepresentation() {
        return typeOfTestRepresentation;
    }

    /**
     * Sets the type of test representation.
     *
     * @param typeOfTestRepresentation the new type of test representation
     */
    public void setTypeOfTestRepresentation(TypeOfTestRepresentation typeOfTestRepresentation) {
        this.typeOfTestRepresentation = typeOfTestRepresentation;
    }

}

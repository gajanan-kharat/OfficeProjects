package com.inetpsa.poc00.domain.archivebox;

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
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class ArchiveBox.
 */
@Entity
@Table(name = "COPQTARB")
public class ArchiveBox extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The archive box number. */
    @Column(name = "ARCHIVE_BOX_NUMBER")
    private Long archiveBoxNumber;

    /** The is open. */
    @Column(name = "IS_OPEN")
    private Boolean isOpen;

    /** The opening date. */
    @Column(name = "OPENING_DATE")
    private Date openingDate;

    /** The closing date. */
    @Column(name = "CLOSING_DATE")
    private Date closingDate;

    /** The model year. */
    @Column(name = "MODEL_YEAR")
    private String modelYear;

    /** The vehicle files. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "archiveBox")
    private List<VehicleFile> vehicleFiles;

    /** The fuel. */
    @ManyToOne
    @JoinColumn(name = "FUEL_ID")
    private Fuel fuel;

    /** The project code family. */
    @ManyToOne
    @JoinColumn(name = "PROJECT_CODE_FAMILY_ID")
    private ProjectCodeFamily projectCodeFamily;

    /** The type of test. */
    @ManyToOne
    @JoinColumn(name = "TYPE_OF_TEST_ID")
    private TypeOfTest typeOfTest;

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
     * Gets the archive box number.
     * 
     * @return the archiveBoxNumber
     */
    public Long getArchiveBoxNumber() {
        return archiveBoxNumber;
    }

    /**
     * Sets the archive box number.
     * 
     * @param archiveBoxNumber the archiveBoxNumber to set
     */
    public void setArchiveBoxNumber(Long archiveBoxNumber) {
        this.archiveBoxNumber = archiveBoxNumber;
    }

    /**
     * Gets the vehicle files.
     * 
     * @return the vehicleFiles
     */
    public List<VehicleFile> getVehicleFiles() {
        return vehicleFiles;
    }

    /**
     * Sets the vehicle files.
     * 
     * @param vehicleFiles the vehicleFiles to set
     */
    public void setVehicleFiles(List<VehicleFile> vehicleFiles) {
        this.vehicleFiles = vehicleFiles;
    }

    /**
     * Getter openingDate.
     *
     * @return the openingDate
     */
    public Date getOpeningDate() {
        return openingDate;
    }

    /**
     * Setter openingDate.
     *
     * @param openingDate the openingDate to set
     */
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Getter closingDate.
     *
     * @return the closingDate
     */

    public Date getClosingDate() {
        return closingDate;
    }

    /**
     * Setter closingDate.
     *
     * @param closingDate the closingDate to set
     */
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    /**
     * Getter fuel
     * 
     * @return the fuel
     */
    public Fuel getFuel() {
        return fuel;
    }

    /**
     * Setter fuel
     * 
     * @param fuel the fuel to set
     */
    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    /**
     * Getter projectCodeFamily
     * 
     * @return the projectCodeFamily
     */
    public ProjectCodeFamily getProjectCodeFamily() {
        return projectCodeFamily;
    }

    /**
     * Setter projectCodeFamily
     * 
     * @param projectCodeFamily the projectCodeFamily to set
     */
    public void setProjectCodeFamily(ProjectCodeFamily projectCodeFamily) {
        this.projectCodeFamily = projectCodeFamily;
    }

    /**
     * Getter typeOfTest
     * 
     * @return the typeOfTest
     */
    public TypeOfTest getTypeOfTest() {
        return typeOfTest;
    }

    /**
     * Setter typeOfTest
     * 
     * @param typeOfTest the typeOfTest to set
     */
    public void setTypeOfTest(TypeOfTest typeOfTest) {
        this.typeOfTest = typeOfTest;
    }

    /**
     * Getter isOpen
     * 
     * @return the isOpen
     */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
     * Setter isOpen
     * 
     * @param isOpen the isOpen to set
     */
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}

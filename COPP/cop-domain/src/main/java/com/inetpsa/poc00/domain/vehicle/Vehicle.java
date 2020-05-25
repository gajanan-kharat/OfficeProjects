package com.inetpsa.poc00.domain.vehicle;

import java.util.ArrayList;
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
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.parkinglot.ParkingLot;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Class Vehicle.
 */
@Entity
@Table(name = "COPQTVHL")
public class Vehicle extends BaseAggregateRoot<Long> {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long entityId;

    /** The vin string. */
    @Column(name = "VIN", unique = true)
    private String vin;

    /** The counter mark. */
    @Column(name = "COUNTERMARK", unique = true)
    private String counterMark;

    /** The registration number. */
    @Column(name = "REGISTRATION_NUMBER", unique = true)
    private String registrationNumber;

    /** The chases number. */
    @Column(name = "CHASIS_NUMBER", unique = true)
    private String chasisNumber;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The flow type. */
    @Column(name = "FLOW_TYPE")
    private String flowType;

    /** The destination country. */
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country destinationCountry;

    /** The priority indicator. */
    @Column(name = "PRIORITY_INDICATOR")
    private String priorityIndicator;

    /** The model year. */
    @Column(name = "MODEL_YEAR")
    private String modelYear;

    /** The m c o2 i. */
    @Column(name = "MCO2_I")
    private Double mCO2I;

    /** The parking lot. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARKING_LOT_ID")
    private ParkingLot parkingLot;

    /** The vehicle file. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private List<VehicleFile> vehicleFile = new ArrayList<>();

    /** The factory. */
    @ManyToOne
    @JoinColumn(name = "VEHICLE_FACTORY_ID")
    private CarFactory carfactory;

    /** The technical case. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TECHINICAL_CASE_ID")
    private TechnicalCase technicalCase;

    /**
     * Instantiates a new vehicle.
     * 
     * @param entityId the entity id
     */
    public Vehicle(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * Instantiates a new vehicle.
     */
    public Vehicle() {
        super();
    }

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
     * Gets the parking lot.
     * 
     * @return the parkingLot
     */
    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    /**
     * Sets the parking lot.
     * 
     * @param parkingLot the parkingLot to set
     */
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * Gets the counter mark.
     * 
     * @return the counterMark
     */
    public String getCounterMark() {
        return counterMark;
    }

    /**
     * Sets the counter mark.
     * 
     * @param counterMark the counterMark to set
     */
    public void setCounterMark(String counterMark) {
        this.counterMark = counterMark;
    }

    /**
     * Gets the registration number.
     * 
     * @return the registrationNumber
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number.
     * 
     * @param registrationNumber the registrationNumber to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the chasis number.
     * 
     * @return the chasisNumber
     */
    public String getChasisNumber() {
        return chasisNumber;
    }

    /**
     * Sets the chasis number.
     * 
     * @param chasisNumber the chasisNumber to set
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
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the flow type.
     * 
     * @return the flowType
     */
    public String getFlowType() {
        return flowType;
    }

    /**
     * Sets the flow type.
     * 
     * @param flowType the flowType to set
     */
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    /**
     * Gets the destination country.
     *
     * @return the destination country
     */
    public Country getDestinationCountry() {
        return destinationCountry;
    }

    /**
     * Sets the destination country.
     *
     * @param destinationCountry the new destination country
     */
    public void setDestinationCountry(Country destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    /**
     * Gets the priority indicator.
     * 
     * @return the priorityIndicator
     */
    public String getPriorityIndicator() {
        return priorityIndicator;
    }

    /**
     * Sets the priority indicator.
     * 
     * @param priorityIndicator the priorityIndicator to set
     */
    public void setPriorityIndicator(String priorityIndicator) {
        this.priorityIndicator = priorityIndicator;
    }

    /**
     * Gets the model year.
     * 
     * @return the modelYear
     */
    public String getModelYear() {
        return modelYear;
    }

    /**
     * Sets the model year.
     * 
     * @param modelYear the modelYear to set
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
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * Gets the vehicle file.
     * 
     * @return the vehicleFile
     */
    public List<VehicleFile> getVehicleFile() {
        return vehicleFile;
    }

    /**
     * Gets the carfactory.
     * 
     * @return the carfactory
     */
    public CarFactory getCarfactory() {
        return carfactory;
    }

    /**
     * Sets the carfactory.
     * 
     * @param carfactory the carfactory to set
     */
    public void setCarfactory(CarFactory carfactory) {
        this.carfactory = carfactory;
    }

    /**
     * Gets the technical case.
     * 
     * @return the technical case
     */
    public TechnicalCase getTechnicalCase() {
        return technicalCase;
    }

    /**
     * Sets the technical case.
     * 
     * @param technicalCase the new technical case
     */
    public void setTechnicalCase(TechnicalCase technicalCase) {
        this.technicalCase = technicalCase;
    }

}

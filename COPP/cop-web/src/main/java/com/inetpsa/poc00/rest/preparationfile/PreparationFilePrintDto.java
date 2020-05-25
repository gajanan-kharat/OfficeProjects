/*
 * Creation : Feb 20, 2017
 */
package com.inetpsa.poc00.rest.preparationfile;

import java.util.Date;

import com.inetpsa.poc00.domain.preparationfile.PreparationFile;

/**
 * The Class PreparationFilePrintDto.
 */
public class PreparationFilePrintDto {

	/** The registration number. */
	private String registrationNumber;

	/** The chasis number. */
	private String chasisNumber;

	/** The counter mark. */
	private String counterMark;

	/** The vehicle identificaiton. */
	private String vehicleIdentificaiton;

	/** The user name. */
	private String userName;

	/** The signature. */
	private String signature;

	/** The vin. */
	private String vin;

	/** The tvv label. */
	private String tvvLabel;

	/** The engine lable. */
	private String engineLable;

	/** The indice. */
	private String indice;

	/** The gear box label. */
	private String gearBoxLabel;

	/** The vehicle factory. */
	// Car Factory Label
	private String vehicleFactory;

	/** The model year. */
	private String modelYear;

	/** The project family label. */
	private String projectFamilyLabel;

	/** The destination country. */
	private String destinationCountry;

	/** The preparation list comments. */
	private String preparationListComments;

	/** The preparation file. */
	private PreparationFile preparationFile;

	/** The is prep complete. */
	private Boolean isPrepComplete = Boolean.FALSE;

	/** The is in expertise. */
	private Boolean isInExpertise = Boolean.FALSE;

	/** The preparation file create. */
	private Date preparationFileCreate;

	/**
	 * Instantiates a new preparation file print dto.
	 */
	public PreparationFilePrintDto() {
		super();
	}

	/**
	 * Getter registrationNumber.
	 *
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Setter registrationNumber.
	 *
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Getter chasisNumber.
	 *
	 * @return the chasisNumber
	 */
	public String getChasisNumber() {
		return chasisNumber;
	}

	/**
	 * Setter chasisNumber.
	 *
	 * @param chasisNumber the chasisNumber to set
	 */
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	/**
	 * Getter counterMark.
	 *
	 * @return the counterMark
	 */
	public String getCounterMark() {
		return counterMark;
	}

	/**
	 * Setter counterMark.
	 *
	 * @param counterMark the counterMark to set
	 */
	public void setCounterMark(String counterMark) {
		this.counterMark = counterMark;
	}

	/**
	 * Getter userName.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter userName.
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getter signature.
	 *
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Setter signature.
	 *
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
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
	 * Getter engineLable.
	 *
	 * @return the engineLable
	 */
	public String getEngineLable() {
		return engineLable;
	}

	/**
	 * Setter engineLable.
	 *
	 * @param engineLable the engineLable to set
	 */
	public void setEngineLable(String engineLable) {
		this.engineLable = engineLable;
	}

	/**
	 * Getter indice.
	 *
	 * @return the indice
	 */
	public String getIndice() {
		return indice;
	}

	/**
	 * Setter indice.
	 *
	 * @param indice the indice to set
	 */
	public void setIndice(String indice) {
		this.indice = indice;
	}

	/**
	 * Getter gearBoxLabel.
	 *
	 * @return the gearBoxLabel
	 */
	public String getGearBoxLabel() {
		return gearBoxLabel;
	}

	/**
	 * Setter gearBoxLabel.
	 *
	 * @param gearBoxLabel the gearBoxLabel to set
	 */
	public void setGearBoxLabel(String gearBoxLabel) {
		this.gearBoxLabel = gearBoxLabel;
	}

	/**
	 * Getter vehicleFactory.
	 *
	 * @return the vehicleFactory
	 */
	public String getVehicleFactory() {
		return vehicleFactory;
	}

	/**
	 * Setter vehicleFactory.
	 *
	 * @param vehicleFactory the vehicleFactory to set
	 */
	public void setVehicleFactory(String vehicleFactory) {
		this.vehicleFactory = vehicleFactory;
	}

	/**
	 * Getter modelYear.
	 *
	 * @return the modelYear
	 */
	public String getModelYear() {
		return modelYear;
	}

	/**
	 * Setter modelYear.
	 *
	 * @param modelYear the modelYear to set
	 */
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	/**
	 * Getter destinationCountry.
	 *
	 * @return the destinationCountry
	 */
	public String getDestinationCountry() {
		return destinationCountry;
	}

	/**
	 * Setter destinationCountry.
	 *
	 * @param destinationCountry the destinationCountry to set
	 */
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	/**
	 * Getter preparationListComments.
	 *
	 * @return the preparationListComments
	 */
	public String getPreparationListComments() {
		return preparationListComments;
	}

	/**
	 * Setter preparationListComments.
	 *
	 * @param preparationListComments the preparationListComments to set
	 */
	public void setPreparationListComments(String preparationListComments) {
		this.preparationListComments = preparationListComments;
	}

	/**
	 * Getter preparationFile.
	 *
	 * @return the preparationFile
	 */
	public PreparationFile getPreparationFile() {
		return preparationFile;
	}

	/**
	 * Setter preparationFile.
	 *
	 * @param preparationFile the preparationFile to set
	 */
	public void setPreparationFile(PreparationFile preparationFile) {
		this.preparationFile = preparationFile;
	}

	/**
	 * Getter isPrepComplete.
	 *
	 * @return the isPrepComplete
	 */
	public Boolean getIsPrepComplete() {
		return isPrepComplete;
	}

	/**
	 * Setter isPrepComplete.
	 *
	 * @param isPrepComplete the isPrepComplete to set
	 */
	public void setIsPrepComplete(Boolean isPrepComplete) {
		this.isPrepComplete = isPrepComplete;
	}

	/**
	 * Getter isInExpertise.
	 *
	 * @return the isInExpertise
	 */
	public Boolean getIsInExpertise() {
		return isInExpertise;
	}

	/**
	 * Setter isInExpertise.
	 *
	 * @param isInExpertise the isInExpertise to set
	 */
	public void setIsInExpertise(Boolean isInExpertise) {
		this.isInExpertise = isInExpertise;
	}

	/**
	 * Getter vehicleIdentificaiton.
	 *
	 * @return the vehicleIdentificaiton
	 */
	public String getVehicleIdentificaiton() {
		return vehicleIdentificaiton;
	}

	/**
	 * Setter vehicleIdentificaiton.
	 *
	 * @param vehicleIdentificaiton the vehicleIdentificaiton to set
	 */
	public void setVehicleIdentificaiton(String vehicleIdentificaiton) {
		this.vehicleIdentificaiton = vehicleIdentificaiton;
	}

	/**
	 * Getter projectFamilyLabel.
	 *
	 * @return the projectFamilyLabel
	 */
	public String getProjectFamilyLabel() {
		return projectFamilyLabel;
	}

	/**
	 * Setter projectFamilyLabel.
	 *
	 * @param projectFamilyLabel the projectFamilyLabel to set
	 */
	public void setProjectFamilyLabel(String projectFamilyLabel) {
		this.projectFamilyLabel = projectFamilyLabel;
	}

	/**
	 * Getter preparationFileCreate.
	 *
	 * @return the preparationFileCreate
	 */
	public Date getPreparationFileCreate() {
		return preparationFileCreate;
	}

	/**
	 * Setter preparationFileCreate.
	 *
	 * @param preparationFileCreate the preparationFileCreate to set
	 */
	public void setPreparationFileCreate(Date preparationFileCreate) {
		this.preparationFileCreate = preparationFileCreate;
	}

}

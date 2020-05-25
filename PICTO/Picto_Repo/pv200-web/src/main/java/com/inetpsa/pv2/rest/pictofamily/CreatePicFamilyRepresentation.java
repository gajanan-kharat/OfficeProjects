package com.inetpsa.pv2.rest.pictofamily;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.color.ColorRepresentation;
import com.inetpsa.pv2.rest.picto.CreatePictoRepresentation;
import com.inetpsa.pv2.rest.type.TypeRepresentation;

/**
 * Class: CreatePicFamilyRepresentation
 *
 */
public class CreatePicFamilyRepresentation {

	private String referenceNum;
	private String name;
	private String informationTypeLabel;
	private String informationFR;
	private String informationEN;
	private String adminInfo;
	private String functionFR;
	private String functionEN;
	private TypeRepresentation typeID;
	private String keywordFR;
	private String keywordEN;
	private CategoryRepresentation categoryID;
	private CreatePictoRepresentation pictos;

	private String refCharte;
	private Boolean command;
	private String commandInformation;
	private Boolean isRHNWitness;
	private Boolean isRHNActive;
	private Boolean isRHNAlert;
	private Boolean isRHNDefault;
	private List<ColorRepresentation> witnessActive = new ArrayList<ColorRepresentation>();;
	private List<ColorRepresentation> witnessAlert = new ArrayList<ColorRepresentation>();
	private List<ColorRepresentation> witnessFailure = new ArrayList<ColorRepresentation>();
	private String rhnInfoFR;
	private String rhnInfoEN;
	private String validationLevel;

	/**
	 * @return the referenceNum
	 */
	public String getReferenceNum() {
		return referenceNum;
	}

	/**
	 * @param referenceNum the referenceNum to set
	 */
	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the informationFR
	 */
	public String getInformationFR() {
		return informationFR;
	}

	/**
	 * @param informationFR the informationFR to set
	 */
	public void setInformationFR(String informationFR) {
		this.informationFR = informationFR;
	}

	/**
	 * @return the informationEN
	 */
	public String getInformationEN() {
		return informationEN;
	}

	/**
	 * @param informationEN the informationEN to set
	 */
	public void setInformationEN(String informationEN) {
		this.informationEN = informationEN;
	}

	/**
	 * @return the adminInfo
	 */
	public String getAdminInfo() {
		return adminInfo;
	}

	/**
	 * @param adminInfo the adminInfo to set
	 */
	public void setAdminInfo(String adminInfo) {
		this.adminInfo = adminInfo;
	}

	/**
	 * @return the functionFR
	 */
	public String getFunctionFR() {
		return functionFR;
	}

	/**
	 * @param functionFR the functionFR to set
	 */
	public void setFunctionFR(String functionFR) {
		this.functionFR = functionFR;
	}

	/**
	 * @return the functionEN
	 */
	public String getFunctionEN() {
		return functionEN;
	}

	/**
	 * @param functionEN the functionEN to set
	 */
	public void setFunctionEN(String functionEN) {
		this.functionEN = functionEN;
	}

	/**
	 * @return the keywordFR
	 */
	public String getKeywordFR() {
		return keywordFR;
	}

	/**
	 * @param keywordFR the keywordFR to set
	 */
	public void setKeywordFR(String keywordFR) {
		this.keywordFR = keywordFR;
	}

	/**
	 * @return the keywordEN
	 */
	public String getKeywordEN() {
		return keywordEN;
	}

	/**
	 * @param keywordEN the keywordEN to set
	 */
	public void setKeywordEN(String keywordEN) {
		this.keywordEN = keywordEN;
	}

	/**
	 * @return the informationTypeLabel
	 */
	public String getInformationTypeLabel() {
		return informationTypeLabel;
	}

	/**
	 * @param informationTypeLabel the informationTypeLabel to set
	 */
	public void setInformationTypeLabel(String informationTypeLabel) {
		this.informationTypeLabel = informationTypeLabel;
	}

	/**
	 * @return the typeID
	 */
	public TypeRepresentation getTypeID() {
		return typeID;
	}

	/**
	 * @param typeID the typeID to set
	 */
	public void setTypeID(TypeRepresentation typeID) {
		this.typeID = typeID;
	}

	/**
	 * @return the categoryID
	 */
	public CategoryRepresentation getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(CategoryRepresentation categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @param pictos the pictos to set
	 */
	public void setPictos(CreatePictoRepresentation pictos) {
		this.pictos = pictos;
	}

	/**
	 * @return the pictos
	 */
	public CreatePictoRepresentation getPictos() {
		return pictos;
	}

	/**
	 * @return the refCharte
	 */
	public String getRefCharte() {
		return refCharte;
	}

	/**
	 * @param refCharte the refCharte to set
	 */
	public void setRefCharte(String refCharte) {
		this.refCharte = refCharte;
	}

	/**
	 * @return the command
	 */
	public Boolean getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Boolean command) {
		this.command = command;
	}

	/**
	 * @return the commandInformation
	 */
	public String getCommandInformation() {
		return commandInformation;
	}

	/**
	 * @param commandInformation the commandInformation to set
	 */
	public void setCommandInformation(String commandInformation) {
		this.commandInformation = commandInformation;
	}

	/**
	 * @return the witnessActive
	 */
	public List<ColorRepresentation> getWitnessActive() {
		return witnessActive;
	}

	/**
	 * @param witnessActive the witnessActive to set
	 */
	public void setWitnessActive(List<ColorRepresentation> witnessActive) {
		this.witnessActive = witnessActive;
	}

	/**
	 * @return the witnessAlert
	 */
	public List<ColorRepresentation> getWitnessAlert() {
		return witnessAlert;
	}

	/**
	 * @param witnessAlert the witnessAlert to set
	 */
	public void setWitnessAlert(List<ColorRepresentation> witnessAlert) {
		this.witnessAlert = witnessAlert;
	}

	/**
	 * @return the witnessFailure
	 */
	public List<ColorRepresentation> getWitnessFailure() {
		return witnessFailure;
	}

	/**
	 * @param witnessFailure the witnessFailure to set
	 */
	public void setWitnessFailure(List<ColorRepresentation> witnessFailure) {
		this.witnessFailure = witnessFailure;
	}

	/**
	 * @return the rhnInfoFR
	 */
	public String getRhnInfoFR() {
		return rhnInfoFR;
	}

	/**
	 * @param rhnInfoFR the rhnInfoFR to set
	 */
	public void setRhnInfoFR(String rhnInfoFR) {
		this.rhnInfoFR = rhnInfoFR;
	}

	/**
	 * @return the rhnInfoEN
	 */
	public String getRhnInfoEN() {
		return rhnInfoEN;
	}

	/**
	 * @param rhnInfoEN the rhnInfoEN to set
	 */
	public void setRhnInfoEN(String rhnInfoEN) {
		this.rhnInfoEN = rhnInfoEN;
	}

	/**
	 * @return the validationLevel
	 */
	public String getValidationLevel() {
		return validationLevel;
	}

	/**
	 * @param validationLevel the validationLevel to set
	 */
	public void setValidationLevel(String validationLevel) {
		this.validationLevel = validationLevel;
	}

	/**
	 * @return the isRHNWitness
	 */
	public Boolean getIsRHNWitness() {
		return isRHNWitness;
	}

	/**
	 * @param isRHNWitness the isRHNWitness to set
	 */
	public void setIsRHNWitness(Boolean isRHNWitness) {
		this.isRHNWitness = isRHNWitness;
	}

	/**
	 * @return the isRHNActive
	 */
	public Boolean getIsRHNActive() {
		return isRHNActive;
	}

	/**
	 * @param isRHNActive the isRHNActive to set
	 */
	public void setIsRHNActive(Boolean isRHNActive) {
		this.isRHNActive = isRHNActive;
	}

	/**
	 * @return the isRHNAlert
	 */
	public Boolean getIsRHNAlert() {
		return isRHNAlert;
	}

	/**
	 * @param isRHNAlert the isRHNAlert to set
	 */
	public void setIsRHNAlert(Boolean isRHNAlert) {
		this.isRHNAlert = isRHNAlert;
	}

	/**
	 * @return the isRHNDefault
	 */
	public Boolean getIsRHNDefault() {
		return isRHNDefault;
	}

	/**
	 * @param isRHNDefault the isRHNDefault to set
	 */
	public void setIsRHNDefault(Boolean isRHNDefault) {
		this.isRHNDefault = isRHNDefault;
	}

}

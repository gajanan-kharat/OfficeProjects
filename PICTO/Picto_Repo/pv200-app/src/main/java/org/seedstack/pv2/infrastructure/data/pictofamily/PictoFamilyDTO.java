package org.seedstack.pv2.infrastructure.data.pictofamily;

import java.util.Set;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.color.ColorDTO;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;
import org.seedstack.pv2.infrastructure.data.ruleofuses.RuleOfUsesDTO;
import org.seedstack.pv2.infrastructure.data.specificdrawing.SpecificDrawingDTO;
import org.seedstack.pv2.infrastructure.data.type.TypeDTO;

/**
 * Class: PictoFamilyDTO
 *
 */
public class PictoFamilyDTO {

	private Long familyId;
	private String referenceNum;
	private String name;
	private String informationType;
	private String informationFR;
	private String informationEN;
	private String validationLevel;
	private String adminInfo;
	private String functionFR;
	private String functionEN;
	private TypeDTO typeID;
	private String refCharte;
	private Boolean Command;
	private String commandInformation;
	private Boolean isRHNWitness;
	private Boolean isRHNActive;
	private Boolean isRHNAlert;
	private Boolean isRHNDefault;
	private ColorDTO witnessActive;
	private ColorDTO witnessAlert;
	private ColorDTO witnessFailure;
	private String rhnInfoFR;
	private String rhnInfoEN;
	private Long categoryID;
	private String keywordFR;
	private String keywordEN;

	private Set<PictoDTO> pictos;
	private Set<SpecificDrawingDTO> specificDrawings;
	private Set<RuleOfUsesDTO> rules;

	/**
	 * @return the familyId
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getFamilyId() {
		return familyId;
	}

	/**
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	/**
	 * @return the referenceNum
	 */

	@MatchingFactoryParameter(index = 1)
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

	@MatchingFactoryParameter(index = 2)
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
	 * @return the informationType
	 */

	@MatchingFactoryParameter(index = 3)
	public String getInformationType() {
		return informationType;
	}

	/**
	 * @param informationType the informationType to set
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	/**
	 * @return the informationFR
	 */

	@MatchingFactoryParameter(index = 4)
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

	@MatchingFactoryParameter(index = 5)
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
	 * @return the validationLevel
	 */

	@MatchingFactoryParameter(index = 6)
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
	 * @return the adminInfo
	 */

	@MatchingFactoryParameter(index = 7)
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

	@MatchingFactoryParameter(index = 8)
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

	@MatchingFactoryParameter(index = 9)
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
	 * @return the typeID
	 */

	@MatchingFactoryParameter(index = 10)
	public TypeDTO getTypeID() {
		return typeID;
	}

	/**
	 * @param l the typeID to set
	 */
	public void setTypeID(TypeDTO typeID) {
		this.typeID = typeID;
	}

	/**
	 * @return the refCharte
	 */

	@MatchingFactoryParameter(index = 11)
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

	@MatchingFactoryParameter(index = 12)
	public Boolean getCommand() {
		return Command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Boolean command) {
		Command = command;
	}

	/**
	 * @return the commandInformation
	 */

	@MatchingFactoryParameter(index = 13)
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

	@MatchingFactoryParameter(index = 14)
	public ColorDTO getWitnessActive() {
		return witnessActive;
	}

	/**
	 * @param witnessActive the witnessActive to set
	 */
	public void setWitnessActive(ColorDTO witnessActive) {
		this.witnessActive = witnessActive;
	}

	/**
	 * @return the witnessAlert
	 */

	@MatchingFactoryParameter(index = 15)
	public ColorDTO getWitnessAlert() {
		return witnessAlert;
	}

	/**
	 * @param witnessAlert the witnessAlert to set
	 */
	public void setWitnessAlert(ColorDTO witnessAlert) {
		this.witnessAlert = witnessAlert;
	}

	/**
	 * @return the witnessFailure
	 */

	@MatchingFactoryParameter(index = 16)
	public ColorDTO getWitnessFailure() {
		return witnessFailure;
	}

	/**
	 * @param witnessFailure the witnessFailure to set
	 */
	public void setWitnessFailure(ColorDTO witnessFailure) {
		this.witnessFailure = witnessFailure;
	}

	/**
	 * @return the rhnInfoFR
	 */

	@MatchingFactoryParameter(index = 17)
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

	@MatchingFactoryParameter(index = 18)
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
	 * @return the categoryID
	 */

	@MatchingFactoryParameter(index = 19)
	public Long getCategoryID() {
		return categoryID;
	}

	/**
	 * @param i the categoryID to set
	 */
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the keywordFR
	 */

	@MatchingFactoryParameter(index = 20)
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

	@MatchingFactoryParameter(index = 21)
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
	 * @return the pictos
	 */

	@MatchingFactoryParameter(index = 22)
	public Set<PictoDTO> getPictos() {
		return pictos;
	}

	/**
	 * @param pictos the pictos to set
	 */
	public void setPictos(Set<PictoDTO> pictos) {
		this.pictos = pictos;
	}

	/**
	 * @return the specificDrawings
	 */
	@MatchingFactoryParameter(index = 23)
	public Set<SpecificDrawingDTO> getSpecificDrawings() {
		return specificDrawings;
	}

	/**
	 * @param specificDrawings the specificDrawings to set
	 */
	public void setSpecificDrawings(Set<SpecificDrawingDTO> specificDrawings) {
		this.specificDrawings = specificDrawings;
	}

	/**
	 * @return the rules
	 */
	@MatchingFactoryParameter(index = 24)
	public Set<RuleOfUsesDTO> getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(Set<RuleOfUsesDTO> rules) {
		this.rules = rules;
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

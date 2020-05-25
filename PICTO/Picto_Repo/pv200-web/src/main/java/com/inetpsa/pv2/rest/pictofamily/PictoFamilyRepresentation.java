package com.inetpsa.pv2.rest.pictofamily;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.color.ColorRepresentation;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.ruleofuses.RuleOfUsesRepresentation;
import com.inetpsa.pv2.rest.specificdrawing.SpecificDrawingRepresentation;
import com.inetpsa.pv2.rest.type.TypeRepresentation;
import com.inetpsa.pv2.rest.user.UserRepresentation;

/**
 * Class : PictoFamilyRepresentation.
 */
public class PictoFamilyRepresentation {

    /** The family id. */
    private Long familyId;

    /** The reference num. */
    private String referenceNum;

    /** The name. */
    private String name;

    /** The information type. */
    private int informationType;

    /** The information type label. */
    private String informationTypeLabel;

    /** The information fr. */
    private String informationFR;

    /** The information en. */
    private String informationEN;

    /** The validation level. */
    private String validationLevel;

    /** The admin info. */
    private String adminInfo;

    /** The function fr. */
    private String functionFR;

    /** The function en. */
    private String functionEN;

    /** The type id. */
    private TypeRepresentation typeID;

    /** The ref charte. */
    private String refCharte;

    /** The command. */
    private Boolean command;

    /** The command information. */
    private String commandInformation;

    /** The is rhn witness. */
    private Boolean isRHNWitness;

    /** The is rhn active. */
    private Boolean isRHNActive;

    /** The is rhn alert. */
    private Boolean isRHNAlert;

    /** The is rhn default. */
    private Boolean isRHNDefault;

    /** The witness active. */
    private List<ColorRepresentation> witnessActive = new ArrayList<ColorRepresentation>();

    /** The witness alert. */
    private List<ColorRepresentation> witnessAlert = new ArrayList<ColorRepresentation>();

    /** The witness failure. */
    private List<ColorRepresentation> witnessFailure = new ArrayList<ColorRepresentation>();

    /** The rhn info fr. */
    private String rhnInfoFR;

    /** The rhn info en. */
    private String rhnInfoEN;

    /** The category id. */
    private CategoryRepresentation categoryID;

    /** The keyword fr. */
    private String keywordFR;

    /** The keyword en. */
    private String keywordEN;

    /** The deleted pictos list. */
    private List<PictoRepresentation> deletedPictosList = new ArrayList<PictoRepresentation>();

    /** The deleted spec draw list. */
    private List<SpecificDrawingRepresentation> deletedSpecDrawList = new ArrayList<SpecificDrawingRepresentation>();

    /** The pictos. */
    private List<PictoRepresentation> pictos;

    /** The specific drawings. */
    private List<SpecificDrawingRepresentation> specificDrawings;

    /** The rules. */
    private List<RuleOfUsesRepresentation> rules;

    /** The user. */
    private UserRepresentation user;

    /** The favorite flag. */
    private Boolean favoriteFlag = false;

    /**
     * Instantiates a new picto family representation.
     */
    public PictoFamilyRepresentation() {
        // Default Constructor

    }

    /**
     * Instantiates a new picto family representation.
     *
     * @param familyId the family id
     * @param referenceNum the reference num
     * @param name the name
     * @param informationType the information type
     * @param validationLevel the validation level
     * @param refCharte the ref charte
     */
    public PictoFamilyRepresentation(Long familyId, String referenceNum, String name, String informationType, String validationLevel,
            String refCharte) {
        this.familyId = familyId;
        this.referenceNum = referenceNum;
        this.name = name;
        this.informationTypeLabel = informationType;
        this.validationLevel = validationLevel;
        this.refCharte = refCharte;

    }

    /**
     * Gets the family id.
     *
     * @return the familyId
     */
    public Long getFamilyId() {
        return familyId;
    }

    /**
     * Sets the family id.
     *
     * @param familyId the familyId to set
     */
    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    /**
     * Gets the reference num.
     *
     * @return the referenceNum
     */
    public String getReferenceNum() {
        return referenceNum;
    }

    /**
     * Sets the reference num.
     *
     * @param referenceNum the referenceNum to set
     */
    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the information type.
     *
     * @return the informationType
     */
    public int getInformationType() {
        return informationType;
    }

    /**
     * Sets the information type.
     *
     * @param informationType the informationType to set
     */
    public void setInformationType(int informationType) {
        this.informationType = informationType;
    }

    /**
     * Gets the information fr.
     *
     * @return the informationFR
     */
    public String getInformationFR() {
        return informationFR;
    }

    /**
     * Sets the information fr.
     *
     * @param informationFR the informationFR to set
     */
    public void setInformationFR(String informationFR) {
        this.informationFR = informationFR;
    }

    /**
     * Gets the information en.
     *
     * @return the informationEN
     */
    public String getInformationEN() {
        return informationEN;
    }

    /**
     * Sets the information en.
     *
     * @param informationEN the informationEN to set
     */
    public void setInformationEN(String informationEN) {
        this.informationEN = informationEN;
    }

    /**
     * Gets the validation level.
     *
     * @return the validationLevel
     */
    public String getValidationLevel() {
        return validationLevel;
    }

    /**
     * Sets the validation level.
     *
     * @param validationLevel the validationLevel to set
     */
    public void setValidationLevel(String validationLevel) {
        this.validationLevel = validationLevel;
    }

    /**
     * Gets the admin info.
     *
     * @return the adminInfo
     */
    public String getAdminInfo() {
        return adminInfo;
    }

    /**
     * Sets the admin info.
     *
     * @param adminInfo the adminInfo to set
     */
    public void setAdminInfo(String adminInfo) {
        this.adminInfo = adminInfo;
    }

    /**
     * Gets the function fr.
     *
     * @return the functionFR
     */
    public String getFunctionFR() {
        return functionFR;
    }

    /**
     * Sets the function fr.
     *
     * @param functionFR the functionFR to set
     */
    public void setFunctionFR(String functionFR) {
        this.functionFR = functionFR;
    }

    /**
     * Gets the function en.
     *
     * @return the functionEN
     */
    public String getFunctionEN() {
        return functionEN;
    }

    /**
     * Sets the function en.
     *
     * @param functionEN the functionEN to set
     */
    public void setFunctionEN(String functionEN) {
        this.functionEN = functionEN;
    }

    /**
     * Gets the type id.
     *
     * @return the typeID
     */
    public TypeRepresentation getTypeID() {
        return typeID;
    }

    /**
     * Sets the type id.
     *
     * @param typeID the typeID to set
     */
    public void setTypeID(TypeRepresentation typeID) {
        this.typeID = typeID;
    }

    /**
     * Gets the ref charte.
     *
     * @return the refCharte
     */
    public String getRefCharte() {
        return refCharte;
    }

    /**
     * Sets the ref charte.
     *
     * @param refCharte the refCharte to set
     */
    public void setRefCharte(String refCharte) {
        this.refCharte = refCharte;
    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    public Boolean getCommand() {
        return command;
    }

    /**
     * Sets the command.
     *
     * @param command the command to set
     */
    public void setCommand(Boolean command) {
        this.command = command;
    }

    /**
     * Gets the command information.
     *
     * @return the commandInformation
     */
    public String getCommandInformation() {
        return commandInformation;
    }

    /**
     * Sets the command information.
     *
     * @param commandInformation the commandInformation to set
     */
    public void setCommandInformation(String commandInformation) {
        this.commandInformation = commandInformation;
    }

    /**
     * Gets the witness active.
     *
     * @return the witnessActive
     */
    public List<ColorRepresentation> getWitnessActive() {
        return witnessActive;
    }

    /**
     * Sets the witness active.
     *
     * @param witnessActive the witnessActive to set
     */
    public void setWitnessActive(List<ColorRepresentation> witnessActive) {
        this.witnessActive = witnessActive;
    }

    /**
     * Gets the witness alert.
     *
     * @return the witnessAlert
     */
    public List<ColorRepresentation> getWitnessAlert() {
        return witnessAlert;
    }

    /**
     * Sets the witness alert.
     *
     * @param witnessAlert the witnessAlert to set
     */
    public void setWitnessAlert(List<ColorRepresentation> witnessAlert) {
        this.witnessAlert = witnessAlert;
    }

    /**
     * Gets the witness failure.
     *
     * @return the witnessFailure
     */
    public List<ColorRepresentation> getWitnessFailure() {
        return witnessFailure;
    }

    /**
     * Sets the witness failure.
     *
     * @param witnessFailure the witnessFailure to set
     */
    public void setWitnessFailure(List<ColorRepresentation> witnessFailure) {
        this.witnessFailure = witnessFailure;
    }

    /**
     * Gets the rhn info fr.
     *
     * @return the rhnInfoFR
     */
    public String getRhnInfoFR() {
        return rhnInfoFR;
    }

    /**
     * Sets the rhn info fr.
     *
     * @param rhnInfoFR the rhnInfoFR to set
     */
    public void setRhnInfoFR(String rhnInfoFR) {
        this.rhnInfoFR = rhnInfoFR;
    }

    /**
     * Gets the rhn info en.
     *
     * @return the rhnInfoEN
     */
    public String getRhnInfoEN() {
        return rhnInfoEN;
    }

    /**
     * Sets the rhn info en.
     *
     * @param rhnInfoEN the rhnInfoEN to set
     */
    public void setRhnInfoEN(String rhnInfoEN) {
        this.rhnInfoEN = rhnInfoEN;
    }

    /**
     * Gets the category id.
     *
     * @return the categoryID
     */
    public CategoryRepresentation getCategoryID() {
        return categoryID;
    }

    /**
     * Sets the category id.
     *
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(CategoryRepresentation categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Gets the keyword fr.
     *
     * @return the keywordFR
     */
    public String getKeywordFR() {
        return keywordFR;
    }

    /**
     * Sets the keyword fr.
     *
     * @param keywordFR the keywordFR to set
     */
    public void setKeywordFR(String keywordFR) {
        this.keywordFR = keywordFR;
    }

    /**
     * Gets the keyword en.
     *
     * @return the keywordEN
     */
    public String getKeywordEN() {
        return keywordEN;
    }

    /**
     * Sets the keyword en.
     *
     * @param keywordEN the keywordEN to set
     */
    public void setKeywordEN(String keywordEN) {
        this.keywordEN = keywordEN;
    }

    /**
     * Gets the pictos.
     *
     * @return the pictos
     */
    public List<PictoRepresentation> getPictos() {
        return pictos;
    }

    /**
     * Sets the pictos.
     *
     * @param pictos the pictos to set
     */
    public void setPictos(List<PictoRepresentation> pictos) {
        this.pictos = pictos;
    }

    /**
     * Gets the specific drawings.
     *
     * @return the specificDrawings
     */
    public List<SpecificDrawingRepresentation> getSpecificDrawings() {
        return specificDrawings;
    }

    /**
     * Sets the specific drawings.
     *
     * @param specificDrawings the specificDrawings to set
     */
    public void setSpecificDrawings(List<SpecificDrawingRepresentation> specificDrawings) {
        this.specificDrawings = specificDrawings;
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public List<RuleOfUsesRepresentation> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param rules the rules to set
     */
    public void setRules(List<RuleOfUsesRepresentation> rules) {
        this.rules = rules;
    }

    /**
     * Gets the information type label.
     *
     * @return the informationTypeLabel
     */
    public String getInformationTypeLabel() {
        return informationTypeLabel;
    }

    /**
     * Sets the information type label.
     *
     * @param informationTypeLabel the informationTypeLabel to set
     */
    public void setInformationTypeLabel(String informationTypeLabel) {
        this.informationTypeLabel = informationTypeLabel;
    }

    /**
     * Gets the deleted pictos list.
     *
     * @return the deletedPictosList
     */
    public List<PictoRepresentation> getDeletedPictosList() {
        return deletedPictosList;
    }

    /**
     * Sets the deleted pictos list.
     *
     * @param deletedPictosList the deletedPictosList to set
     */
    public void setDeletedPictosList(List<PictoRepresentation> deletedPictosList) {
        this.deletedPictosList = deletedPictosList;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public UserRepresentation getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user to set
     */
    public void setUser(UserRepresentation user) {
        this.user = user;
    }

    /**
     * Gets the checks if is rhn witness.
     *
     * @return the isRHNWitness
     */
    public Boolean getIsRHNWitness() {
        return isRHNWitness;
    }

    /**
     * Sets the checks if is rhn witness.
     *
     * @param isRHNWitness the isRHNWitness to set
     */
    public void setIsRHNWitness(Boolean isRHNWitness) {
        this.isRHNWitness = isRHNWitness;
    }

    /**
     * Gets the checks if is rhn active.
     *
     * @return the isRHNActive
     */
    public Boolean getIsRHNActive() {
        return isRHNActive;
    }

    /**
     * Sets the checks if is rhn active.
     *
     * @param isRHNActive the isRHNActive to set
     */
    public void setIsRHNActive(Boolean isRHNActive) {
        this.isRHNActive = isRHNActive;
    }

    /**
     * Gets the checks if is rhn alert.
     *
     * @return the isRHNAlert
     */
    public Boolean getIsRHNAlert() {
        return isRHNAlert;
    }

    /**
     * Sets the checks if is rhn alert.
     *
     * @param isRHNAlert the isRHNAlert to set
     */
    public void setIsRHNAlert(Boolean isRHNAlert) {
        this.isRHNAlert = isRHNAlert;
    }

    /**
     * Gets the checks if is rhn default.
     *
     * @return the isRHNDefault
     */
    public Boolean getIsRHNDefault() {
        return isRHNDefault;
    }

    /**
     * Sets the checks if is rhn default.
     *
     * @param isRHNDefault the isRHNDefault to set
     */
    public void setIsRHNDefault(Boolean isRHNDefault) {
        this.isRHNDefault = isRHNDefault;
    }

    /**
     * Gets the deleted spec draw list.
     *
     * @return the deletedSpecDrawList
     */
    public List<SpecificDrawingRepresentation> getDeletedSpecDrawList() {
        return deletedSpecDrawList;
    }

    /**
     * Sets the deleted spec draw list.
     *
     * @param deletedSpecDrawList the deletedSpecDrawList to set
     */
    public void setDeletedSpecDrawList(List<SpecificDrawingRepresentation> deletedSpecDrawList) {
        this.deletedSpecDrawList = deletedSpecDrawList;
    }

    /**
     * Gets the favorite flag.
     *
     * @return the favoriteFlag
     */
    public Boolean getFavoriteFlag() {
        return favoriteFlag;
    }

    /**
     * Sets the favorite flag.
     *
     * @param favoriteFlag the favoriteFlag to set
     */
    public void setFavoriteFlag(Boolean favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }

}

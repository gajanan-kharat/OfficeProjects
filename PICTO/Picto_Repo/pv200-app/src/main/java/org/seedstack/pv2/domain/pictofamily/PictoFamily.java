package org.seedstack.pv2.domain.pictofamily;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.notificationContrib.NotificationContrib;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;
import org.seedstack.pv2.domain.type.Type;
import org.seedstack.pv2.domain.user.User;

/**
 * Class : Picto Family.
 */
@Entity
@Table(name = "PV2QTPFM")
public class PictoFamily extends BaseAggregateRoot<Long> {

	/** The family id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long familyId;

	/** Picto Reference Number. */
	@Column(name = "REF_NUM")
	private String referenceNum;

	/** Picto Name. */

	@Column(name = "NAME")
	private String name;

	/** Picto Information Type. */

	@Column(name = "INFO_TYPE")
	private String informationType;

	/** Picto Information Type in French. */

	@Column(name = "INFO_LABEL_FR")
	private String informationLabelFR;

	/** Picto Information Type in English. */
	@Column(name = "INFO_LABEL_EN")
	private String informationLabelEN;

	/** Picto Validation. */

	@Column(name = "VALIDATION_LEVEL")
	private String validationLevel;

	/** Picto Admin Information. */

	@Column(name = "ADMIN_INFO")
	private String adminInfo;

	/** Picto Function in French. */
	@Column(name = "FUNCTION_FR")
	private String functionFR;

	/** Picto Function in English. */
	@Column(name = "FUNCTION_EN")
	private String functionEN;

	/** Picto Type ID. */
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private Type typeID;

	/** Picto Ref Charte. */
	@Column(name = "REF_CHARTE")
	private String refCharte;

	/** Picto Command. */
	@Column(name = "IS_COMMAND")
	private Boolean isCommand;

	/** Picto Command Information. */

	@Column(name = "COMMAND_INFO")
	private String commandInformation;

	/** The is RHN witness. */
	@Column(name = "IS_RHN_WITNESS")
	private Boolean isRHNWitness;

	/** The is RHN active. */
	@Column(name = "IS_RHN_ACTIVATION")
	private Boolean isRHNActive;

	/** The is RHN alert. */
	@Column(name = "IS_RHN_ALERT")
	private Boolean isRHNAlert;

	/** The is RHN default. */
	@Column(name = "IS_RHN_DEFAULT")
	private Boolean isRHNDefault;

	/** The witness active list. */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PV2QTACT", joinColumns = @JoinColumn(name = "PFM_ID", referencedColumnName = "ID"), inverseJoinColumns = { @JoinColumn(name = "COLOR_ID", referencedColumnName = "ID") })
	private List<Color> witnessActiveList;

	/** The witness alert list. */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PV2QTALR", joinColumns = @JoinColumn(name = "PFM_ID", referencedColumnName = "ID"), inverseJoinColumns = { @JoinColumn(name = "COLOR_ID", referencedColumnName = "ID") })
	private List<Color> witnessAlertList;

	/** The witness failure list. */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PV2QTFAI", joinColumns = @JoinColumn(name = "PFM_ID", referencedColumnName = "ID"), inverseJoinColumns = { @JoinColumn(name = "COLOR_ID", referencedColumnName = "ID") })
	private List<Color> witnessFailureList;

	/** Picto RHN information in French. */
	@Column(name = "RHN_INFO_FR")
	private String rhnInfoFR;

	/** Picto RHN information in English. */
	@Column(name = "RHN_INFO_EN")
	private String rhnInfoEN;

	/** Picto Category ID. */
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category categoryID;

	/** Picto Keyword French. */
	@Column(name = "KEYWORD_FR")
	private String keywordFR;

	/** Picto Keyword English. */

	@Column(name = "KEYWORD_EN")
	private String keywordEN;

	/** The specific drawings. */
	@OneToMany(mappedBy = "familyId", targetEntity = SpecificDrawing.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SpecificDrawing> specificDrawings;

	/** The pictos. */
	@OneToMany(mappedBy = "pictoFamilyID", targetEntity = Picto.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Picto> pictos;

	/** The rules. */
	@OneToMany(mappedBy = "familyId", targetEntity = RuleOfUses.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RuleOfUses> rules;

	/** The notifications. */
	@OneToMany(mappedBy = "familyID", targetEntity = NotificationContrib.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<NotificationContrib> notifications;

	/** The users list favorites. */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersListFavorites")
	private List<User> usersListFavorites;

	/**
	 * Instantiates a new picto family.
	 */
	public PictoFamily() {

	}

	/**
	 * Constructor.
	 *
	 * @param familyId the family id
	 * @param referenceNum the reference num
	 * @param name the name
	 */

	public PictoFamily(Long familyId, String referenceNum, String name) {
		this.familyId = familyId;
		this.referenceNum = referenceNum;
		this.name = name;

	}

	/**
	 * Constructor.
	 *
	 * @param familyId the family id
	 * @param referenceNum the reference num
	 * @param name the name
	 * @param informationType the information type
	 * @param informationLabelFR the information label FR
	 * @param informationLabelEN the information label EN
	 * @param validationLevel the validation level
	 * @param adminInfo the admin info
	 * @param functionFR the function FR
	 * @param functionEN the function EN
	 * @param typeID the type ID
	 * @param refCharte the ref charte
	 * @param isCommand the is command
	 * @param commandInformation the command information
	 * @param witnessActive the witness active
	 * @param witnessAlert the witness alert
	 * @param witnessFailure the witness failure
	 * @param rhnInfoFR the rhn info FR
	 * @param rhnInfoEN the rhn info EN
	 * @param categoryID the category ID
	 * @param keywordFR the keyword FR
	 * @param keywordEN the keyword EN
	 */

	public PictoFamily(Long familyId, String referenceNum, String name, String informationType, String informationLabelFR, String informationLabelEN, String validationLevel, String adminInfo, String functionFR, String functionEN, Type typeID, String refCharte, Boolean isCommand, String commandInformation, Color witnessActive, Color witnessAlert, Color witnessFailure, String rhnInfoFR,
			String rhnInfoEN, Category categoryID, String keywordFR, String keywordEN) {
		super();
		this.familyId = familyId;
		this.referenceNum = referenceNum;
		this.name = name;
		this.informationType = informationType;
		this.informationLabelFR = informationLabelFR;
		this.informationLabelEN = informationLabelEN;
		this.validationLevel = validationLevel;
		this.adminInfo = adminInfo;
		this.functionFR = functionFR;
		this.functionEN = functionEN;
		this.typeID = typeID;
		this.refCharte = refCharte;
		this.isCommand = isCommand;
		this.commandInformation = commandInformation;
		this.rhnInfoFR = rhnInfoFR;
		this.rhnInfoEN = rhnInfoEN;
		this.categoryID = categoryID;
		this.keywordFR = keywordFR;
		this.keywordEN = keywordEN;

	}

	/**
	 * Gets the entity id.
	 *
	 * @return the pictoFamilyId
	 */
	@Override
	public Long getEntityId() {
		return familyId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param familyId the new entity id
	 */
	public void setEntityId(Long familyId) {
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
	public String getInformationType() {
		return informationType;
	}

	/**
	 * Sets the information type.
	 *
	 * @param informationType the informationType to set
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	/**
	 * Gets the information label FR.
	 *
	 * @return the informationLabelFR
	 */
	public String getInformationLabelFR() {
		return informationLabelFR;
	}

	/**
	 * Sets the information label FR.
	 *
	 * @param informationLabelFR the informationLabelFR to set
	 */
	public void setInformationLabelFR(String informationLabelFR) {
		this.informationLabelFR = informationLabelFR;
	}

	/**
	 * Gets the information label EN.
	 *
	 * @return the informationLabelEN
	 */
	public String getInformationLabelEN() {
		return informationLabelEN;
	}

	/**
	 * Sets the information label EN.
	 *
	 * @param informationLabelEN the informationLabelEN to set
	 */
	public void setInformationLabelEN(String informationLabelEN) {
		this.informationLabelEN = informationLabelEN;
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
	 * Gets the function FR.
	 *
	 * @return the functionFR
	 */
	public String getFunctionFR() {
		return functionFR;
	}

	/**
	 * Sets the function FR.
	 *
	 * @param functionFR the functionFR to set
	 */
	public void setFunctionFR(String functionFR) {
		this.functionFR = functionFR;
	}

	/**
	 * Gets the function EN.
	 *
	 * @return the functionEN
	 */
	public String getFunctionEN() {
		return functionEN;
	}

	/**
	 * Sets the function EN.
	 *
	 * @param functionEN the functionEN to set
	 */
	public void setFunctionEN(String functionEN) {
		this.functionEN = functionEN;
	}

	/**
	 * Gets the type ID.
	 *
	 * @return the typeID
	 */
	public Type getTypeID() {
		return typeID;
	}

	/**
	 * Sets the type ID.
	 *
	 * @param typeID the typeID to set
	 */
	public void setTypeID(Type typeID) {
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
	 * Gets the checks if is command.
	 *
	 * @return the isCommand
	 */
	public Boolean getIsCommand() {
		return isCommand;
	}

	/**
	 * Sets the checks if is command.
	 *
	 * @param isCommand the isCommand to set
	 */
	public void setIsCommand(Boolean isCommand) {
		this.isCommand = isCommand;
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
	public List<Color> getWitnessActive() {
		return witnessActiveList;
	}

	/**
	 * Sets the witness active.
	 *
	 * @param witnessActive the witnessActive to set
	 */
	public void setWitnessActive(List<Color> witnessActive) {
		this.witnessActiveList = witnessActive;
	}

	/**
	 * Gets the witness alert.
	 *
	 * @return the witnessAlert
	 */
	public List<Color> getWitnessAlert() {
		return witnessAlertList;
	}

	/**
	 * Sets the witness alert.
	 *
	 * @param witnessAlert the witnessAlert to set
	 */
	public void setWitnessAlert(List<Color> witnessAlert) {
		this.witnessAlertList = witnessAlert;
	}

	/**
	 * Gets the witness failure.
	 *
	 * @return the witnessFailure
	 */
	public List<Color> getWitnessFailure() {
		return witnessFailureList;
	}

	/**
	 * Sets the witness failure.
	 *
	 * @param witnessFailure the witnessFailure to set
	 */
	public void setWitnessFailure(List<Color> witnessFailure) {
		this.witnessFailureList = witnessFailure;
	}

	/**
	 * Gets the rhn info FR.
	 *
	 * @return the rhnInfoFR
	 */
	public String getRhnInfoFR() {
		return rhnInfoFR;
	}

	/**
	 * Sets the rhn info FR.
	 *
	 * @param rhnInfoFR the rhnInfoFR to set
	 */
	public void setRhnInfoFR(String rhnInfoFR) {
		this.rhnInfoFR = rhnInfoFR;
	}

	/**
	 * Gets the rhn info EN.
	 *
	 * @return the rhnInfoEN
	 */
	public String getRhnInfoEN() {
		return rhnInfoEN;
	}

	/**
	 * Sets the rhn info EN.
	 *
	 * @param rhnInfoEN the rhnInfoEN to set
	 */
	public void setRhnInfoEN(String rhnInfoEN) {
		this.rhnInfoEN = rhnInfoEN;
	}

	/**
	 * Gets the category ID.
	 *
	 * @return the categoryID
	 */
	public Category getCategoryID() {
		return categoryID;
	}

	/**
	 * Sets the category ID.
	 *
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(Category categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * Gets the keyword FR.
	 *
	 * @return the keywordFR
	 */
	public String getKeywordFR() {
		return keywordFR;
	}

	/**
	 * Sets the keyword FR.
	 *
	 * @param keywordFR the keywordFR to set
	 */
	public void setKeywordFR(String keywordFR) {
		this.keywordFR = keywordFR;
	}

	/**
	 * Gets the keyword EN.
	 *
	 * @return the keywordEN
	 */
	public String getKeywordEN() {
		return keywordEN;
	}

	/**
	 * Sets the keyword EN.
	 *
	 * @param keywordEN the keywordEN to set
	 */
	public void setKeywordEN(String keywordEN) {
		this.keywordEN = keywordEN;
	}

	/**
	 * Gets the specific drawings.
	 *
	 * @return the specificDrawings
	 */
	public List<SpecificDrawing> getSpecificDrawings() {
		return specificDrawings;
	}

	/**
	 * Sets the specific drawings.
	 *
	 * @param specificDrawings the specificDrawings to set
	 */
	public void setSpecificDrawings(List<SpecificDrawing> specificDrawings) {
		this.specificDrawings = specificDrawings;
	}

	/**
	 * Gets the pictos.
	 *
	 * @return the pictos
	 */
	public List<Picto> getPictos() {
		return pictos;
	}

	/**
	 * Sets the pictos.
	 *
	 * @param pictos the pictos to set
	 */
	public void setPictos(List<Picto> pictos) {
		this.pictos = pictos;
	}

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public List<RuleOfUses> getRules() {
		return rules;
	}

	/**
	 * Sets the rules.
	 *
	 * @param rules the rules to set
	 */
	public void setRules(List<RuleOfUses> rules) {
		this.rules = rules;
	}

	/**
	 * Gets the users list favorites.
	 *
	 * @return the usersListFavorites
	 */
	public List<User> getUsersListFavorites() {
		return usersListFavorites;
	}

	/**
	 * Sets the users list favorites.
	 *
	 * @param usersListFavorites the usersListFavorites to set
	 */
	public void setUsersListFavorites(List<User> usersListFavorites) {
		this.usersListFavorites = usersListFavorites;
	}

	/**
	 * Gets the notifications.
	 *
	 * @return the notifications
	 */
	public Set<NotificationContrib> getNotifications() {
		return notifications;
	}

	/**
	 * Sets the notifications.
	 *
	 * @param notifications the notifications to set
	 */
	public void setNotifications(Set<NotificationContrib> notifications) {
		this.notifications = notifications;
	}

	/**
	 * Gets the checks if is RHN witness.
	 *
	 * @return the isRHNWitness
	 */
	public Boolean getIsRHNWitness() {
		return isRHNWitness;
	}

	/**
	 * Sets the checks if is RHN witness.
	 *
	 * @param isRHNWitness the isRHNWitness to set
	 */
	public void setIsRHNWitness(Boolean isRHNWitness) {
		this.isRHNWitness = isRHNWitness;
	}

	/**
	 * Gets the checks if is RHN active.
	 *
	 * @return the isRHNActive
	 */
	public Boolean getIsRHNActive() {
		return isRHNActive;
	}

	/**
	 * Sets the checks if is RHN active.
	 *
	 * @param isRHNActive the isRHNActive to set
	 */
	public void setIsRHNActive(Boolean isRHNActive) {
		this.isRHNActive = isRHNActive;
	}

	/**
	 * Gets the checks if is RHN alert.
	 *
	 * @return the isRHNAlert
	 */
	public Boolean getIsRHNAlert() {
		return isRHNAlert;
	}

	/**
	 * Sets the checks if is RHN alert.
	 *
	 * @param isRHNAlert the isRHNAlert to set
	 */
	public void setIsRHNAlert(Boolean isRHNAlert) {
		this.isRHNAlert = isRHNAlert;
	}

	/**
	 * Gets the checks if is RHN default.
	 *
	 * @return the isRHNDefault
	 */
	public Boolean getIsRHNDefault() {
		return isRHNDefault;
	}

	/**
	 * Sets the checks if is RHN default.
	 *
	 * @param isRHNDefault the isRHNDefault to set
	 */
	public void setIsRHNDefault(Boolean isRHNDefault) {
		this.isRHNDefault = isRHNDefault;
	}

}

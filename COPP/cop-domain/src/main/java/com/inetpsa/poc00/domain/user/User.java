package com.inetpsa.poc00.domain.user;

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

import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.conditioningresult.ConditioningResult;
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResult;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.restitutionfile.RestitutionFile;
import com.inetpsa.poc00.domain.specialtestcondition.SpecialTestCondition;
import com.inetpsa.poc00.domain.team.Team;
import com.inetpsa.poc00.domain.testconditioncomment.TestConditionComment;

/**
 * The Class User.
 */
@Entity
@Table(name = "COPQTUSR")
public class User extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The user id. */
	@Column(name = "USER_ID")
	private String userId;

	/** The first name. */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/** The last name. */
	@Column(name = "LAST_NAME")
	private String lastName;

	/** The basket. */
	@OneToOne(mappedBy = "user")
	private Basket basket;

	/** The user reception list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userRcp")
	private List<ReceptionFile> userReceptionList = new ArrayList<>();

	/** The user restitution list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userRst")
	private List<RestitutionFile> userRestitutionList = new ArrayList<>();

	/** The user preparation file. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userPrepFile")
	private List<PreparationFile> userPreparationFile = new ArrayList<>();

	/** The generic test condition. */
	@OneToMany(mappedBy = "user")
	private List<TestConditionComment> testConditionComment;

	/** The expertise result list. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user")
	private List<ExpertiseResult> expertiseResultList = new ArrayList<>();

	/** The Conditioning result list. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user")
	private List<ConditioningResult> conditioningResultList = new ArrayList<>();

	/** The special test condn list. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user")
	private List<SpecialTestCondition> specialTestCondnList = new ArrayList<>();

	/** The team. */
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

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
	 * Gets the user id.
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the team.
	 * 
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the team.
	 * 
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Gets the user preparation file.
	 *
	 * @return the user preparation file
	 */
	public List<PreparationFile> getUserPreparationFile() {
		return userPreparationFile;
	}

	/**
	 * Sets the user preparation file.
	 *
	 * @param userPreparationFile the new user preparation file
	 */
	public void setUserPreparationFile(List<PreparationFile> userPreparationFile) {
		this.userPreparationFile = userPreparationFile;
	}

	/**
	 * Getter basket.
	 *
	 * @return the basket
	 */
	public Basket getBasket() {
		return basket;
	}

	/**
	 * Setter basket.
	 *
	 * @param basket the basket to set
	 */
	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	/**
	 * Getter userReceptionList.
	 *
	 * @return the userReceptionList
	 */
	public List<ReceptionFile> getUserReceptionList() {
		return userReceptionList;
	}

	/**
	 * Setter userReceptionList.
	 *
	 * @param userReceptionList the userReceptionList to set
	 */
	public void setUserReceptionList(List<ReceptionFile> userReceptionList) {
		this.userReceptionList = userReceptionList;
	}

	/**
	 * Getter userRestitutionList.
	 *
	 * @return the userRestitutionList
	 */
	public List<RestitutionFile> getUserRestitutionList() {
		return userRestitutionList;
	}

	/**
	 * Setter userRestitutionList.
	 *
	 * @param userRestitutionList the userRestitutionList to set
	 */
	public void setUserRestitutionList(List<RestitutionFile> userRestitutionList) {
		this.userRestitutionList = userRestitutionList;
	}

	/**
	 * Getter testConditionComment.
	 *
	 * @return the testConditionComment
	 */
	public List<TestConditionComment> getTestConditionComment() {
		return testConditionComment;
	}

	/**
	 * Setter testConditionComment.
	 *
	 * @param testConditionComment the testConditionComment to set
	 */
	public void setTestConditionComment(List<TestConditionComment> testConditionComment) {
		this.testConditionComment = testConditionComment;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	/* (non-Javadoc)
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return "User Id : " + userId;
	}

	/**
	 * Getter conditioningResultList.
	 *
	 * @return the conditioningResultList
	 */
	public List<ConditioningResult> getConditioningResultList() {
		return conditioningResultList;
	}

	/**
	 * Setter conditioningResultList.
	 *
	 * @param conditioningResultList the conditioningResultList to set
	 */
	public void setConditioningResultList(List<ConditioningResult> conditioningResultList) {
		conditioningResultList = conditioningResultList;
	}

	/**
	 * Getter expertiseResultList.
	 *
	 * @return the expertiseResultList
	 */
	public List<ExpertiseResult> getExpertiseResultList() {
		return expertiseResultList;
	}

	/**
	 * Setter expertiseResultList.
	 *
	 * @param expertiseResultList the expertiseResultList to set
	 */
	public void setExpertiseResultList(List<ExpertiseResult> expertiseResultList) {
		this.expertiseResultList = expertiseResultList;
	}

	/**
	 * Getter specialTestCondnList.
	 *
	 * @return the specialTestCondnList
	 */
	public List<SpecialTestCondition> getSpecialTestCondnList() {
		return specialTestCondnList;
	}

	/**
	 * Setter specialTestCondnList.
	 *
	 * @param specialTestCondnList the specialTestCondnList to set
	 */
	public void setSpecialTestCondnList(List<SpecialTestCondition> specialTestCondnList) {
		this.specialTestCondnList = specialTestCondnList;
	}

}

package com.inetpsa.poc00.domain.team;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.user.User;

/**
 * The Class Team.
 */
@Entity
@Table(name = "COPQTTEM")
public class Team extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The users list. */
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "team")
	private List<User> usersList = new ArrayList<User>();

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
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the users list.
	 * 
	 * @return the usersList
	 */
	public List<User> getUsersList() {
		return usersList;
	}

	/**
	 * Sets the users list.
	 * 
	 * @param usersList the usersList to set
	 */
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return "Team Label : " + label;
	}
}

package org.seedstack.pv2.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

@Entity
@Table(name = "PV2QTUSR")
public class User extends BaseAggregateRoot<String> {

	/**
	 * User Id
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_ID")
	private String userId;

	/**
	 * First name of user
	 */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/**
	 * Name of the user
	 */
	@Column(name = "LAST_NAME")
	private String lastName;

    @Column(name = "IS_THICK_CLIENT")
    private boolean isThickClient;

	@Column(name = "EMAIL")
	private String eMAIL;

	@Column(name = "PHONE_NUM")
	private String phoneNum;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	/*
	 * @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersListFavorites")
	 * private List<PictoFamily> usersListFavorites;
	 */

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PV2QTFAV", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "ID"), inverseJoinColumns = { @JoinColumn(name = "PFM_ID", referencedColumnName = "ID") })
	private List<PictoFamily> usersListFavorites;
	

	@OneToMany(mappedBy = "userId")
	private List<PictoClient> pictoClient;

	// bi-directional many-to-many association to Pv2qtpic
	/*
	 * @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersListShopCarts")
	 * private List<Picto> usersListShopCarts;
	 */
	
	@ManyToMany
	@JoinTable(name = "PV2QTSHP", joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PIC_ID", referencedColumnName = "ID"))
	private List<Picto> usersListShopCarts;


	@OneToMany(mappedBy = "lastModifiedUsr")
	private List<Picto> lastModifiedUsr;

	@OneToMany(mappedBy = "lastUpdatedUsr")
	private List<Picto> lastUpdatedUsr;

	public User() {

	}

	User(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.firstName = lastName;

	}

	User(String userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return the userId
	 */
	public String getEntityId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setEntityId(String userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    public boolean getIsThickClient() {
        return this.isThickClient;
    }

    public void setIsThickClient(boolean isThickClient) {
        this.isThickClient = isThickClient;
    }

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<PictoClient> getPictoClient() {
		return this.pictoClient;
	}

	public void setPictoClient(List<PictoClient> pictoClient) {
		this.pictoClient = pictoClient;
	}

	/**
	 * @return the usersListFavorites
	 */
	public List<PictoFamily> getUsersListFavorites() {
		return usersListFavorites;
	}

	/**
	 * @param usersListFavorites
	 *            the usersListFavorites to set
	 */
	public void setUsersListFavorites(List<PictoFamily> usersListFavorites) {
		this.usersListFavorites = usersListFavorites;
	}

	public List<Picto> getLastModifiedUsr() {
		return this.lastModifiedUsr;
	}

	public void setLastModifiedUsr(List<Picto> lastModifiedUsr) {
		this.lastModifiedUsr = lastModifiedUsr;
	}

	/**
	 * @return the usersListShopCarts
	 */
	public List<Picto> getUsersListShopCarts() {
		return usersListShopCarts;
	}

	/**
	 * @param usersListShopCarts
	 *            the usersListShopCarts to set
	 */
	public void setUsersListShopCarts(List<Picto> usersListShopCarts) {
		this.usersListShopCarts = usersListShopCarts;
	}

	public List<Picto> getLastUpdatedUsr() {
		return this.lastUpdatedUsr;
	}

	public void setLastUpdatedUsr(List<Picto> lastUpdatedUsr) {
		this.lastUpdatedUsr = lastUpdatedUsr;
	}

	/**
	 * @return the uSER_ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param uSER_ID
	 *            the uSER_ID to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the eMAIL
	 */
	public String geteMAIL() {
		return eMAIL;
	}

	/**
	 * @param eMAIL
	 *            the eMAIL to set
	 */
	public void seteMAIL(String eMAIL) {
		this.eMAIL = eMAIL;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum
	 *            the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}

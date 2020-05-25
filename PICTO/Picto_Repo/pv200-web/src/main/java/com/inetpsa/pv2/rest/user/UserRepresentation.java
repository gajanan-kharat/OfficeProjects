package com.inetpsa.pv2.rest.user;


/* Class: UserRepresentation*/
public class UserRepresentation {

    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private boolean isThickClient;

    private String eMAIL;
    private String phoneNum;
    private Boolean isActive;


	public UserRepresentation() {

	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

    /**
     * @return the isThickClient
     */
    public boolean getIsThickClient() {
        return isThickClient;
    }

    /**
     * @param isThickClient the isThickClient to set
     */
    public void setIsThickClient(boolean isThickClient) {
        this.isThickClient = isThickClient;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
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
	 * @param eMAIL the eMAIL to set
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
	 * @param phoneNum the phoneNum to set
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
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}

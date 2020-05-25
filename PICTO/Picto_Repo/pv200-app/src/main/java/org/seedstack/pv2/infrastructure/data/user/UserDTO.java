package org.seedstack.pv2.infrastructure.data.user;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;


/**
 * The Class UserDTO.
 */
public class UserDTO {

	/** The user id. */
	private Long userId;
	
	/** The name. */
	private String name;
	
	/** The first name. */
	private String firstName;

	/**
	 * Gets the user id.
	 *
	 * @return the userId
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@MatchingFactoryParameter(index = 1)
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
	 * Gets the first name.
	 *
	 * @return the firstName
	 */
	@MatchingFactoryParameter(index = 2)
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

}

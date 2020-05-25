package com.inetpsa.poc00.rest.user;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.user.User;

/**
 * The Interface UserFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface UserFinder {

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();

	/**
	 * Gets the user by label.
	 *
	 * @param label the label
	 * @return the user by label
	 */
	List<User> getUserByLabel(String label);

	/**
	 * Gets the user by id.
	 *
	 * @param entityId the entity id
	 * @return the user by id
	 */
	User getUserById(Long entityId);

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	User getUserById(String userId);
	


}

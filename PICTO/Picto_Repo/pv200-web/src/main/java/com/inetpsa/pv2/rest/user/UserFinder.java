package com.inetpsa.pv2.rest.user;

import org.seedstack.business.finder.Finder;
import org.seedstack.pv2.domain.user.User;


/**
 * The Interface UserFinder.
 */
@Finder
public interface UserFinder {

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	User getUser();

	/**
	 * Find user by id.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User findUserById(String userId);

}

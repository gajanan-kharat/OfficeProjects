package com.inetpsa.poc00.domain.user;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating User objects.
 */
public interface UserFactory extends GenericFactory<User> {

	/**
	 * Creates a new User object.
	 *
	 * @return the user
	 */
	public User createUser();
}

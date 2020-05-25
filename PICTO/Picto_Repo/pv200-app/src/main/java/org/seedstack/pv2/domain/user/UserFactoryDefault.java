package org.seedstack.pv2.domain.user;

import org.seedstack.business.domain.BaseFactory;

/**
 * User Factory implementation
 *
 */
public class UserFactoryDefault extends BaseFactory<User> implements
		UserFactory {

	@Override
	public User createUser(Long id, String name, String firstName) {
		return new User(id, name, firstName);
	}
	
    
    @Override
	public User createUser(String userId, String firstName, String lastName) {
		return new User(userId,firstName, lastName);
	}

}
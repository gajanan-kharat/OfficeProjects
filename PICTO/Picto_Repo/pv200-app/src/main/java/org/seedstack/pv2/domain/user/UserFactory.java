package org.seedstack.pv2.domain.user;

import org.seedstack.business.domain.GenericFactory;

/**
 * User Factory interface.
 */
public interface UserFactory extends GenericFactory<User> {

	/**
	 * Factory create method
	 * 
	 * @param id
	 * @param name
	 * @param firstName
	 * @return User
	 */
	User createUser(Long id, String name, String firstName);
	
	//User createUser(Long p_login);
	
	User createUser(String userId, String firstName, String lastName);
	
   
}

/*
 * Creation : Sep 12, 2016
 */
package com.inetpsa.poc00.domain.user;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class UserFactoryDefault.
 */
public class UserFactoryDefault extends BaseFactory<User> implements UserFactory {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.User.UserFactory#createUser()
     */
    @Override
    public User createUser() {

        return new User();
    }

}

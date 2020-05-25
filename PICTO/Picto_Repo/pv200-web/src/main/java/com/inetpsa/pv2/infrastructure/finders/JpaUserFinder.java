package com.inetpsa.pv2.infrastructure.finders;

import javax.inject.Inject;

import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserFactory;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.pictofamily.PictoFamilyResource;
import com.inetpsa.pv2.rest.user.UserFinder;

/**
 * The Class JpaUserFinder.
 */
public class JpaUserFinder implements UserFinder {

    /** The security support. */
    @Inject
    SecuritySupport securitySupport;

    /** The m user repository. */
    @Inject
    UserRepository userRepository;

    /** The m user factory. */
    @Inject
    UserFactory userFactory;

    /** The logger. */
    @Logging
    private Logger logger = LoggerFactory.getLogger(PictoFamilyResource.class);

    /**
     * Get the user details.
     *
     * @return the user
     */
    @Override
    public User getUser() {
        String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();
        String userFirstName = userId;
        String userLastName = userId;
        try {
            userFirstName = securitySupport.getSimplePrincipalByName(Principals.FIRST_NAME).getPrincipal();
            userLastName = securitySupport.getSimplePrincipalByName(Principals.LAST_NAME).getPrincipal();
        } catch (Exception e) {
            logger.error("Exception occured in getUser of JpaUserFinder class", e);
        }
        User user = userRepository.findUser(userId);

        if (user == null) {
            user = userFactory.createUser(userId, userFirstName, userLastName);
            userRepository.persistUser(user);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.pv2.rest.user.UserFinder#findUserById(java.lang.String)
     */
    @Override
    public User findUserById(String userId) {
        return userRepository.findUser(userId);
    }

}

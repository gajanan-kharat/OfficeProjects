package org.seedstack.pv2.domain.user;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of User.
 */
public interface UserRepository extends GenericRepository<User, String> {

    /**
     * Saves the user.
     * 
     * @param user the user to save
     * @return the User
     */
    User saveUser(User user);

    /**
     * Persists the user.
     * 
     * @param user the user to persist
     */
    void persistUser(User user);

    User getUserById(Long userId);

    // void setThickClientLaunchFlag(Long userId);
    void setThickClientLaunchFlag(User userId, boolean isThickClientLaunch);
    
    User findUser(String UserId);

}
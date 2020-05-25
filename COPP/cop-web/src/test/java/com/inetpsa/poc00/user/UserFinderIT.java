/*
 * Creation : Feb 7, 2017
 */
package com.inetpsa.poc00.user;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserFactory;
import com.inetpsa.poc00.domain.user.UserRepository;
import com.inetpsa.poc00.rest.user.UserFinder;

@RunWith(SeedITRunner.class)
public class UserFinderIT {

    @Inject
    UserFactory userFactory;
    @Inject
    UserRepository userRepo;
    @Inject
    UserFinder userFinder;

    @Test
    public void getAllUsers() {
        User user = userFactory.createUser();
        userRepo.saveUser(user);
        List<User> userList = userFinder.getAllUsers();
        assertNotNull(userList);

    }

    @Test
    public void getUserById() {
        User user = userFactory.createUser();
        user.setUserId("123");
        User savedUser = userRepo.saveUser(user);
        User userObj = userFinder.getUserById(savedUser.getUserId());
        assertNotNull(userObj);

    }

    @Test
    public void getUserByEntityId() {
        User user = userFactory.createUser();
        User savedUser = userRepo.saveUser(user);
        User userObj = userFinder.getUserById(savedUser.getEntityId());
        assertNotNull(userObj);

    }
}

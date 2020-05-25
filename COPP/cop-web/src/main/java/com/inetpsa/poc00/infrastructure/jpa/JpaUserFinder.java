package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.rest.user.UserFinder;

/**
 * The Class JpaUserFinder.
 */
public class JpaUserFinder implements UserFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.user.UserFinder#getAllUsers()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<User> getAllUsers() {

        return entityManager.createQuery("SELECT tb FROM User tb").getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.user.UserFinder#getUserByLabel(java.lang.String)
     */
    @Override
    public List<User> getUserByLabel(String label) {

        return entityManager.createQuery("SELECT tb FROM User tb where tb.label=:plabel").setParameter("plabel", label).getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.rest.user.UserFinder#getUserById(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public User getUserById(Long entityId) {

        return (User) entityManager.createQuery("SELECT tb FROM User tb where tb.entityId=:peid").setParameter("peid", entityId).getSingleResult();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.intepsa.poc00.rest.user.UserFinder#getUserById(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public User getUserById(String userId) {

        TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.userId = :userId", User.class);

        query.setParameter("userId", userId);
        List<User> UserList = query.getResultList();

        if (UserList != null && !UserList.isEmpty()) {
            return UserList.get(0);
        }

        return null;

    }
}

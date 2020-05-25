package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.user.User;
import com.inetpsa.poc00.domain.user.UserRepository;

/**
 * The Class UserJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "User")
public class UserJpaRepository extends BaseJpaRepository<User, Long> implements UserRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(UserJpaRepository.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#deleteAll()
	 */
	@Override

	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM User t ").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#saveUser(com.inetpsa.poc00.domain.user.User)
	 */
	@Override
	public User saveUser(User user) {
		if (user.getEntityId() == null || user.getEntityId() == 0)
			return super.save(user);

		return jpaEntityManager.merge(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#persistUser(com.inetpsa.poc00.domain.user.User)
	 */
	@Override
	public void persistUser(User user) {
		saveUser(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#getAllUsers()
	 */
	@Override
	public List<User> getAllUsers() {
		TypedQuery<User> query = jpaEntityManager.createQuery("SELECT t FROM User t ", User.class);

		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#deleteUser(java.lang.Long)
	 */
	@Override
	public int deleteUser(Long id) {

		try {

			return jpaEntityManager.createQuery("DELETE FROM User t where t.entityId = " + id).executeUpdate();

		} catch (Exception userDeleteException) {
			logger.error(" Error occured while deleting user ", userDeleteException);
			return -1;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#getUsersByTeamId(java.lang.Long)
	 */
	@Override
	public List<User> getUsersByTeamId(Long id) {
		TypedQuery<User> query = jpaEntityManager.createQuery("SELECT t FROM User t  where t.team.entityId=" + id, User.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.user.UserRepository#getUserById(java.lang.String)
	 */
	@Override
	public User getUserById(String userId) {

		TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.userId = :userId", User.class);

		query.setParameter("userId", userId);

		List<User> userList = query.getResultList();

		if (userList != null && !userList.isEmpty()) {
			return userList.get(0);
		}

		return null;
	}

}

package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class UserJpaRepository.
 */
public class UserJpaRepository extends BaseJpaRepository<User, String> implements UserRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(UserJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.user.UserRepository#saveUser(org.seedstack.pv2.domain.user.User)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public User saveUser(User user) {
		return super.save(user);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.user.UserRepository#persistUser(org.seedstack.pv2.domain.user.User)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistUser(User user) {
		super.persist(user);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.user.UserRepository#getUserById(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public User getUserById(Long userId) {

		User user = null;
		String l_ContentOfJpqlQuery = "SELECT distinct user " + "FROM User  user " + "WHERE user.id = ?1";

		try {
			TypedQuery<User> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, User.class);
			l_TypedQuery.setParameter(1, userId);
			user = (User) l_TypedQuery.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Exception occured while finding the user ", e);
			user = null;
		}

		return user;

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.user.UserRepository#setThickClientLaunchFlag(org.seedstack.pv2.domain.user.User, boolean)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void setThickClientLaunchFlag(User user, boolean isThickClientLaunch) {
		LOGGER.info("==============================================================");
		LOGGER.info("######### Start: setThickClientLaunchFlag ############ ");
		LOGGER.info("==============================================================");
		LOGGER.debug("User Id :::: " + user.getUserId());
		// User admin = getUserById(userId);
		if (isThickClientLaunch) {
			user.setIsThickClient(true);
			super.save(user);
			LOGGER.debug("Flag reset to true..");
		} else {
			user.setIsThickClient(false);
			super.save(user);
			LOGGER.debug("Flag reset to false..");
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.user.UserRepository#findUser(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public User findUser(String userId) {
		User user = null;
		String l_ContentOfJpqlQuery = "SELECT distinct objectUser FROM User objectUser WHERE objectUser.userId = ?1";

		try {
			TypedQuery<User> p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, User.class);
			p_TypedQuery.setParameter(1, userId);
			List<User> p_ListOfUser = p_TypedQuery.getResultList();

			if (p_ListOfUser != null && p_ListOfUser.size() >= 0) {
				for (User l_User : p_ListOfUser) {
					user = l_User;
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the Picto ", e);
		}
		return user;
	}

}

/*
 * Creation : May 30, 2016
 */
package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.notificationContrib.NotificationContrib;
import org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NotificationContribJpaRepository.
 */
public class NotificationContribJpaRepository extends BaseJpaRepository<NotificationContrib, Long> implements NotificationContribRepository {
	
	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(NotificationContribJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository#deleteNotification(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteNotification(Long pictoFamilyId) {
		NotificationContrib notificationContrib = getNotificationContrib(pictoFamilyId);
		LOGGER.info("Delete the information for  Contributor notification : " + notificationContrib.getEntityId());
		System.out.println("Delete the information for  Contributor notification : " + notificationContrib.getEntityId());
		if (notificationContrib.getEntityId() != null) {
			try {
				m_entityManager.createQuery("DELETE FROM NotificationContrib c where c.entityId = " + notificationContrib.getEntityId()).executeUpdate();
			} catch (Exception e) {
				LOGGER.error("Exception occure while deleting the Notification ", e);
			}

		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository#getAllNotificationbyFamilyId(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public NotificationContrib getAllNotificationbyFamilyId(Long picFamilyId) {
		String l_ContentOfJpqlQuery = "select distinct notificationContrib from NotificationContrib notificationContrib where notificationContrib.familyID.familyId = :family";
		NotificationContrib notification = null;

		try {
			// Query p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery);

			TypedQuery<NotificationContrib> p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, NotificationContrib.class);
			p_TypedQuery.setParameter("family", picFamilyId);

			List<NotificationContrib> p_ListNotofication = p_TypedQuery.getResultList();
			if (p_ListNotofication != null && p_ListNotofication.size() >= 0) {
				for (NotificationContrib l_Notification : p_ListNotofication) {
					notification = l_Notification;
					break;
				}
			}

		} catch (Exception e) {
			LOGGER.error("Exception occure while fetching the notification information:", e);
			notification = null;
		}
		LOGGER.info(" Finish: return the picto family object");
		return notification;

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository#getNotificationContrib(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public NotificationContrib getNotificationContrib(Long pictoFamilyId) {
		NotificationContrib notificationContrib = null;
		String l_ContentOfJpqlQuery = "SELECT distinct notificationContrib " + "FROM NotificationContrib  notificationContrib " + "WHERE notificationContrib.familyID.familyId = ?1";
		try {
			Query l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery);
			l_TypedQuery.setParameter(1, pictoFamilyId);
			notificationContrib = (NotificationContrib) l_TypedQuery.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Exception occure while get the Notification ", e);
		}
		return notificationContrib;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.notificationContrib.NotificationContribRepository#getAllNotificationbyUser(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public NotificationContrib getAllNotificationbyUser(Long userId, Long picFamilyId) {
		String l_ContentOfJpqlQuery = "select distinct notificationContrib from NotificationContrib notificationContrib where notificationContrib.contribId.id = :user and notificationContrib.familyID.familyId = :family";
		NotificationContrib notification = null;

		try {
			// Query p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery);

			TypedQuery<NotificationContrib> p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, NotificationContrib.class);
			p_TypedQuery.setParameter("user", userId);
			p_TypedQuery.setParameter("family", picFamilyId);

			List<NotificationContrib> p_ListNotofication = p_TypedQuery.getResultList();
			if (p_ListNotofication != null && p_ListNotofication.size() >= 0) {
				for (NotificationContrib l_Notification : p_ListNotofication) {
					notification = l_Notification;
					break;
				}
			}

		} catch (Exception e) {
			LOGGER.error("Exception occure while fetching the notification information:", e);
			notification = null;
		}
		LOGGER.info(" Finish: return the picto family object");
		return notification;

	}

}

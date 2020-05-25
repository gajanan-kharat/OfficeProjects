/*
 * Creation : May 30, 2016
 */
package org.seedstack.pv2.domain.notificationContrib;

import org.seedstack.business.domain.GenericRepository;


/**
 * The Interface NotificationContribRepository.
 */
public interface NotificationContribRepository extends GenericRepository<NotificationContrib, Long> {

    /**
     * Delete notification.
     *
     * @param pictoFamilyId the picto family id
     */
    void deleteNotification(Long pictoFamilyId);

    /**
     * Gets the all notificationby user.
     *
     * @param userId the user id
     * @param picFamilyId the pic family id
     * @return the all notificationby user
     */
    NotificationContrib getAllNotificationbyUser(Long userId, Long picFamilyId);

    /**
     * Gets the notification contrib.
     *
     * @param pictoFamilyId the picto family id
     * @return the notification contrib
     */
    NotificationContrib getNotificationContrib(Long pictoFamilyId);

    /**
     * Gets the all notificationby family id.
     *
     * @param picFamilyId the pic family id
     * @return the all notificationby family id
     */
    NotificationContrib getAllNotificationbyFamilyId(Long picFamilyId);

}

/*
 * Creation : Apr 6, 2016
 */
package org.seedstack.pv2.infrastructure.jpa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictoclient.PictoClient;
import org.seedstack.pv2.domain.pictoclient.PictoClientRepository;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class PictoClientJpaRepository.
 */
public class PictoClientJpaRepository extends BaseJpaRepository<PictoClient, Long> implements PictoClientRepository {
	
	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;

	/** The picto client. */
	private PictoClient pictoClient;

	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(PictoClientJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#getPictoByUserId(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<PictoClient> getPictoByUserId(long id) {
		Long usrid = 2L;
		List<PictoClient> allPictos = null;
		String l_ContentOfJpqlQuery = "SELECT distinct pictoClient " + "FROM PictoClient  pictoClient " + "WHERE pictoClient.userId.id = ?1 and pictoClient.downloadFlag = 0";
		try {
			TypedQuery<PictoClient> l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, PictoClient.class);
			l_TypedQuery.setParameter(1, usrid);
			allPictos = l_TypedQuery.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception occure while get the picto client ", e);
			allPictos = null;
		}
		return allPictos;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#getPictoClient(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PictoClient getPictoClient(User adminId, Picto pictoId) {
		String l_ContentOfJpqlQuery = "SELECT distinct pictoClient " + "FROM PictoClient  pictoClient " + "WHERE pictoClient.userId = ?1 and pictoClient.pictoId = ?2";
		try {
			Query l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery);
			l_TypedQuery.setParameter(1, adminId);
			l_TypedQuery.setParameter(2, pictoId);
			pictoClient = (PictoClient) l_TypedQuery.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Exception occure while get the picto client ", e);
		}
		return pictoClient;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#getWorkingAdminList(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<String> getWorkingAdminList(long pictoId) {
		List<String> userIds;
		String l_ContentOfJpqlQuery = "SELECT pictoClient.userId.firstName " + "FROM PictoClient  pictoClient " + "WHERE pictoClient.pictoId.pictoId = ?1";

		Query l_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery);
		l_TypedQuery.setParameter(1, pictoId);
		userIds = l_TypedQuery.getResultList();

		return userIds;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#resetDownloadFlag(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void resetDownloadFlag(User adminId, Picto pictoId) {
		byte byteVal = 1;
		pictoClient = getPictoClient(adminId, pictoId);
		if (pictoClient != null) {
			pictoClient.setDownloadFlag(byteVal);
			entityManager.merge(pictoClient).getEntityId();
		}
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#deletePictoClient(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deletePictoClient(User user, Picto picto) {
		pictoClient = getPictoClient(user, picto);
		LOGGER.info("Delete the information for  Picto Client  id: " + picto.getEntityId());
		if (pictoClient != null) {
			super.delete(pictoClient);
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#addPicto(org.seedstack.pv2.domain.picto.Picto, org.seedstack.pv2.domain.user.User)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void addPicto(Picto picto, User user) {
		Date dwndate = null;
		LOGGER.info("Insert row in PV2QTDIS table for download picto : " + picto.getEntityId());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// get current date time with Date()
		Date date = new Date();
		String downloadDate = dateFormat.format(date);
		try {
			dwndate = dateFormat.parse(downloadDate);
		} catch (ParseException e) {
			LOGGER.error("Error occurred while parsing date . ", e);
		}
		PictoClient pictoClientNew = new PictoClient();
		PictoClient pictoClientOld = new PictoClient();
		pictoClientNew.setPicto(picto);
		pictoClientNew.setDownloadDate(dwndate);
		pictoClientNew.setUserId(user);
		pictoClientNew.setDownloadFlag((byte) 0);
		List<PictoClient> l_PictoClient = getPictoClientInfo(user.getId(), picto.getEntityId());
		if (l_PictoClient.size() != 0) {
			pictoClientOld = l_PictoClient.get(0);
			pictoClientOld.setPicto(picto);
			pictoClientOld.setUserId(user);
			pictoClientOld.setDownloadDate(dwndate);
			pictoClientOld.setDownloadFlag((byte) 0);
			entityManager.merge(pictoClientOld).getEntityId();
		} else {
			super.save(pictoClientNew);
		}

		LOGGER.info("Data saved successfully in PV2QTDIS table.");

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#getPictoClientInfo(java.lang.Long, java.lang.Long)
	 */
	@Override
	@JpaUnit(Config.JPA_UNIT)
	public List<PictoClient> getPictoClientInfo(Long userId, Long pictoId) {

		String l_ContentOfJpqlQuery = "select objectClient from PictoClient  objectClient" + " where objectClient.userId.id = :usrId and objectClient.pictoId.pictoId= :picId ";

		List<PictoClient> p_ListPictoClient = new ArrayList<PictoClient>();
		try {

			TypedQuery<PictoClient> p_TypedQuery = m_entityManager.createQuery(l_ContentOfJpqlQuery, PictoClient.class);
			p_TypedQuery.setParameter("usrId", userId);
			p_TypedQuery.setParameter("picId", pictoId);
			List<PictoClient> p_ListPictoId = p_TypedQuery.getResultList();
			if (p_ListPictoId != null && p_ListPictoId.size() >= 0) {
				for (PictoClient l_PictoClient : p_ListPictoId) {
					p_ListPictoClient.add(l_PictoClient);

				}
			} else {

				LOGGER.debug("Null List in getPictoClientInfo of JPA Picto Class Finder");
			}

			LOGGER.info("Get Picto Info in getPictoClientInfo method in JpaPictoClientFinder class completed sucessfully...");
		} catch (Exception e) {

			LOGGER.error("Error in getting Picto Info in method getPictoClientInfo of JpaPictoClientFinder class ", e);
		}
		return p_ListPictoClient;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#updateOpenLocalImgFlag(org.seedstack.pv2.domain.picto.Picto, org.seedstack.pv2.domain.user.User, boolean)
	 */
	@Override
	public void updateOpenLocalImgFlag(Picto picto, User user, boolean isOpenLocalImgFlag) {

		// byte byteVal = 1;
		pictoClient = getPictoClient(user, picto);
		if (pictoClient != null) {
			pictoClient.setIsOpenLocalImg(isOpenLocalImgFlag);
			entityManager.merge(pictoClient).getEntityId();
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.pictoclient.PictoClientRepository#resetOpenImgFlag(java.lang.Long)
	 */
	@Override
	public void resetOpenImgFlag(Long userId) {

		String contentOfJpqlQuery = "select distinct objectClient from PictoClient  objectClient" + " where objectClient.userId.id = :id";
		try {
			LOGGER.info("Start method getAllPictosById for getting all Pictos By ID in JpaPictoClientFinder class...");
			TypedQuery<PictoClient> p_TypedQuery = entityManager.createQuery(contentOfJpqlQuery, PictoClient.class);
			p_TypedQuery.setParameter("id", userId);
			List<PictoClient> listPictoId = p_TypedQuery.getResultList();
			if (listPictoId != null && listPictoId.size() >= 0) {
				for (PictoClient pictoClient : listPictoId) {
					pictoClient.setIsOpenLocalImg(false);
					entityManager.merge(pictoClient).getEntityId();
				}
			}
			LOGGER.info("Getting all Pictos By ID in getAllPictosById method in JpaPictoClientFinder class completed sucessfully...");
		} catch (Exception e) {

			LOGGER.error("Error in getting all Pictos in method getAllPictosById of JpaPictoClientFinder class ", e);

		}

	}
}

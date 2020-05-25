package com.inetpsa.pv2.rest.pictoclient;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.seed.transaction.Transactional;

/**
 * The Interface PictoClientFinder.
 */
@Finder
@Transactional
public interface PictoClientFinder {

	/**
	 * Gets the picto by id.
	 *
	 * @param userId the user id
	 * @return the picto by id
	 */
	public List<PictoClientRepresentation> getPictoById(Long userId);

	/**
	 * Gets the all pictos by id.
	 *
	 * @param userId the user id
	 * @return the all pictos by id
	 */
	public List<PictoClientRepresentation> getAllPictosById(Long userId);

	/**
	 * Gets the picto client info.
	 *
	 * @param adminId the admin id
	 * @param pictoId the picto id
	 * @return the picto client info
	 */
	public List<PictoClientRepresentation> getPictoClientInfo(Long adminId, Long pictoId);

	/**
	 * Gets the working admin list.
	 *
	 * @param pictoId the picto id
	 * @return the working admin list
	 */
	List<String> getWorkingAdminList(long pictoId);

	/**
	 * Gets the downloaded picto info.
	 *
	 * @param userId the user id
	 * @param pictoId the picto id
	 * @return the downloaded picto info
	 */
	List<Long> getDownloadedPictoInfo(Long userId, Long pictoId);

	/**
	 * Gets the download AI list.
	 *
	 * @return the download AI list
	 */
	List<PictoClientRepresentation> getDownloadAIList();

}

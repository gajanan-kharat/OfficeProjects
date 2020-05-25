package org.seedstack.pv2.application;

import java.util.List;

import org.seedstack.business.Service;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;

/**
 * The Interface ThickClientService.
 */
@Service
public interface ThickClientService {

	/**
	 * Gets the list picto.
	 *
	 * @return the list picto
	 */
	List<PictoDTO> getListPicto();

	/**
	 * Update picto.
	 *
	 * @param picto the picto
	 */
	void updatePicto(Picto picto);

	/**
	 * Reset download flag.
	 *
	 * @param adminId the admin id
	 * @param pictoId the picto id
	 */
	public void resetDownloadFlag(User adminId, Picto pictoId);

	/**
	 * Delete picto.
	 *
	 * @param adminId the admin id
	 * @param pictoId the picto id
	 */
	public void deletePicto(User adminId, Picto pictoId);

	/**
	 * Update AI file info.
	 *
	 * @param userId the user id
	 * @param pictoId the picto id
	 */
	public void updateAIFileInfo(User userId, Picto pictoId);

	/**
	 * Sets the thick client launch flag.
	 *
	 * @param user the user
	 * @param isThickClientLaunched the is thick client launched
	 */
	void setThickClientLaunchFlag(User user, boolean isThickClientLaunched);

}
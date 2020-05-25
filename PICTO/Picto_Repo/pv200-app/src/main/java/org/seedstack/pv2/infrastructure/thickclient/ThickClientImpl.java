package org.seedstack.pv2.infrastructure.thickclient;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.pv2.application.ThickClientService;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictoclient.PictoClientRepository;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.pv2.domain.user.UserRepository;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ThickClientImpl.
 */
public class ThickClientImpl implements ThickClientService {

	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(ThickClientImpl.class);

	/** The m picto client repository. */
	@Inject
	private PictoClientRepository m_PictoClientRepository;

	/** The m user jpa repository. */
	@Inject
	private UserRepository m_UserJpaRepository;

	/** The m picto repository. */
	@Inject
	private PictoRepository m_PictoRepository;

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#getListPicto()
	 */
	public List<PictoDTO> getListPicto() {

		return null;

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#updatePicto(org.seedstack.pv2.domain.picto.Picto)
	 */
	public void updatePicto(Picto picto) {

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#resetDownloadFlag(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	public void resetDownloadFlag(User user, Picto picto) {
		m_PictoClientRepository.resetDownloadFlag(user, picto);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#deletePicto(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	public void deletePicto(User user, Picto picto) {
		m_PictoClientRepository.deletePictoClient(user, picto);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#updateAIFileInfo(org.seedstack.pv2.domain.user.User, org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	public void updateAIFileInfo(User user, Picto picto) {
		m_PictoRepository.updateAIFileInfo(user, picto);
		m_PictoClientRepository.deletePictoClient(user, picto);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.ThickClientService#setThickClientLaunchFlag(org.seedstack.pv2.domain.user.User, boolean)
	 */
	@Override
	public void setThickClientLaunchFlag(User user, boolean isThickClientLaunched) {
		m_UserJpaRepository.setThickClientLaunchFlag(user, isThickClientLaunched);

	}

}

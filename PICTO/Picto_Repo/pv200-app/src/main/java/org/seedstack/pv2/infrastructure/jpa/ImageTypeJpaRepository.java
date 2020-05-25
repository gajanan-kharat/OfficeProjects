package org.seedstack.pv2.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.image.ImageType;
import org.seedstack.pv2.domain.image.ImageTypeRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ImageTypeJpaRepository.
 */
public class ImageTypeJpaRepository extends BaseJpaRepository<ImageType, Long> implements ImageTypeRepository {

	/** The m entity manager. */
	@Inject
	private EntityManager m_entityManager;
	
	/** The logger. */
	@Logging
	private Logger LOGGER = LoggerFactory.getLogger(ImageTypeJpaRepository.class);

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeRepository#saveImageType(org.seedstack.pv2.domain.image.ImageType)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public ImageType saveImageType(ImageType image) {
		return super.save(image);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeRepository#persistImageType(org.seedstack.pv2.domain.image.ImageType)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void persistImageType(ImageType image) {
		super.persist(image);
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeRepository#updateImageType(org.seedstack.pv2.domain.image.ImageType)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void updateImageType(ImageType image) {
		List<ImageType> img = findImageByPictoId(image.getPictoId());
		if (img.size() != 0) {
			ImageType existingImage = img.get(0);
			existingImage.setImageAIPublic(image.getImageAIPublic());
			existingImage.setImageAIWork(image.getImageAIWork());
			existingImage.setImageIgs(image.getImageIgs());
			existingImage.setImageJpg(image.getImageJpg());
			existingImage.setImagePng(image.getImagePng());
			m_entityManager.merge(existingImage).getEntityId();
			LOGGER.info("Image table updated successfully.");
		} else {
			super.save(image);
			LOGGER.info("Image table updated successfully.");
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeRepository#findImageByPictoId(org.seedstack.pv2.domain.picto.Picto)
	 */
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<ImageType> findImageByPictoId(Picto picto) {
		List<ImageType> p_ListOfImage = null;
		String p_ImageQuery = "select distinct i from ImageType  i " + " where i.pictoId = :pic ";
		try {
			TypedQuery<ImageType> p_TypedQuery = m_entityManager.createQuery(p_ImageQuery, ImageType.class);
			p_TypedQuery.setParameter("pic", picto);
			p_ListOfImage = p_TypedQuery.getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the color", e);
		}

		return p_ListOfImage;
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.domain.image.ImageTypeRepository#findAllImageByPictoId(org.seedstack.pv2.domain.picto.Picto)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public ImageType findAllImageByPictoId(Picto picto) {
		ImageType imageEntity = null;

		String p_ImageQuery = "select distinct i from ImageType  i " + " where i.pictoId = :pic ";
		try {
			TypedQuery<ImageType> p_TypedQuery = m_entityManager.createQuery(p_ImageQuery, ImageType.class);
			p_TypedQuery.setParameter("pic", picto);
			List<ImageType> p_ListOfImage = p_TypedQuery.getResultList();
			for (ImageType l_Image : p_ListOfImage) {
				imageEntity = l_Image;
				break;
			}

		} catch (Exception e) {
			LOGGER.error("Exception occure while finding the color", e);
		}

		return imageEntity;
	}

}

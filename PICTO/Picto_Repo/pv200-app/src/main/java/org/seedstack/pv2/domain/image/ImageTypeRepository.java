package org.seedstack.pv2.domain.image;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.pv2.domain.picto.Picto;


/**
 * The Interface ImageTypeRepository.
 */
public interface ImageTypeRepository extends GenericRepository<ImageType, Long> {

    /**
     * Saves the ImageType.
     *
     * @param image the image
     * @return the ImageType
     */

    ImageType saveImageType(ImageType image);

    /**
     * Persists the picto.
     *
     * @param image the image
     */
    void persistImageType(ImageType image);

    /**
     * Find all image by picto id.
     *
     * @param picto the picto
     * @return the image type
     */
    ImageType findAllImageByPictoId(Picto picto);

    /**
     * Update image type.
     *
     * @param image the image
     */
    void updateImageType(ImageType image);

    /**
     * Find image by picto id.
     *
     * @param picto the picto
     * @return the list
     */
    List<ImageType> findImageByPictoId(Picto picto);

}

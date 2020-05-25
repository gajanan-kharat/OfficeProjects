package com.inetpsa.pv2.rest.picto;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.pv2.Config;
import org.seedstack.pv2.domain.user.User;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.pictoclient.PictoClientRepresentation;

/**
 * The Interface PictoFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PictoFinder {

    /**
     * Get all pictos.
     *
     * @param user the user
     * @return PictoRepresentation list
     */
    List<PictoRepresentation> getAllPictos(User user);

    /**
     * Gets the all notification.
     * 
     * @return the all notification
     */
    List<PictoRepresentation> getAllNotification();

    /**
     * Gets the all categories.
     * 
     * @return the all categories
     */
    List<CategoryRepresentation> getAllCategories();

    /**
     * Gets the filtered pictos.
     * 
     * @param pictoFilterRepresentation the picto filter representation
     * @return the filtered pictos
     */
    List<PictoRepresentation> getFilterData(PictoFilterRepresentation pictoFilterRepresentation);

    /**
     * Gets the pictos in cart.
     * 
     * @param user the user
     * @return the pictos in cart
     */
    List<PictoRepresentation> getPictosInCart(User user);

    /**
     * Gets the all picto id.
     * 
     * @param picFamilyId the pic family id
     * @return the all picto id
     */
    List<Long> getAllPictoId(Long picFamilyId);

    /**
     * Sets the color.
     *
     * @param aiDownloadList the ai download list
     * @param pictoRepresentation the picto representation
     * @param userId the user id
     */
    void setColor(List<PictoClientRepresentation> aiDownloadList, PictoRepresentation pictoRepresentation, Long userId);

    /**
     * Removes the shop cart.
     *
     * @param userId the user id
     * @param pictoId the picto id
     */
    void removeShopCart(Long userId, Long pictoId);

}

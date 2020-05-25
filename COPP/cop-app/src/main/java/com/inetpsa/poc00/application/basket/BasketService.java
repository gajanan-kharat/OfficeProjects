/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.application.basket;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.basket.Basket;

/**
 * The Interface BasketService.
 */
@Service
public interface BasketService {

    /**
     * Delete basket.
     *
     * @param vehicleFileId the vehicle file id
     */
    public void deleteBasket(Long vehicleFileId);

    /**
     * Gets the basket for user.
     *
     * @param userId the user id
     * @return the basket for user
     */
    public Basket getBasketForUser(String userId);

    /**
     * Gets the vehicle files count for basket.
     *
     * @param userId the user id
     * @return the vehicle files count for basket
     */
    public Long getVehicleFilesCountForBasket(String userId);
}

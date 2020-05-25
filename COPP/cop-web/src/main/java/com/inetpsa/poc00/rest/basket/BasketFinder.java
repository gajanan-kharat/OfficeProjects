package com.inetpsa.poc00.rest.basket;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.basket.Basket;

/**
 * The Interface BasketFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface BasketFinder {

    /**
     * Gets the basket by user.
     * 
     * @param userId the user id
     * @return the basket by user
     */
    Basket getBasketByUserId(String userId);

    /**
     * Gets the basket by id.
     * 
     * @param basketId the basket id
     * @return the basket by id
     */
    Basket getBasketById(Long basketId);

}

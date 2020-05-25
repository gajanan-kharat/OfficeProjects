package com.inetpsa.poc00.domain.basket;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface BasketRepository.
 */
public interface BasketRepository extends GenericRepository<Basket, Long> {

	/**
	 * Save basket.
	 * 
	 * @param basket the basket
	 * @return the basket
	 */
	Basket saveBasket(Basket basket);

	/**
	 * Update basket.
	 * 
	 * @param basket the basket
	 * @return the basket
	 */
	Basket updateBasket(Basket basket);

	/**
	 * Gets the basket by user id.
	 *
	 * @param userId the user id
	 * @return the basket by user id
	 */
	Basket getBasketByUserId(String userId);

}

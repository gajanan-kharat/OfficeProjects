package com.inetpsa.poc00.domain.basket;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating Basket objects.
 */
public interface BasketFactory extends GenericFactory<Basket> {

	/**
	 * Creates a new Basket object.
	 * 
	 * @return the basket
	 */
	Basket createBasket();

}

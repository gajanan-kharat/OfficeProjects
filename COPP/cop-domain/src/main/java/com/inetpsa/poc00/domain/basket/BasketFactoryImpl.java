package com.inetpsa.poc00.domain.basket;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class BasketFactoryImpl.
 */
public class BasketFactoryImpl extends BaseFactory<Basket> implements BasketFactory {

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.basket.BasketFactory#createBasket()
	 */
	@Override
	public Basket createBasket() {
		return new Basket();
	}

}

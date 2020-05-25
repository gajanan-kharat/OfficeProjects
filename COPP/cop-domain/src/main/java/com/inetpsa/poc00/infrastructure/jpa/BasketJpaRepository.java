package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.domain.basket.BasketRepository;

/**
 * The Class BasketJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "Basket")
public class BasketJpaRepository extends BaseJpaRepository<Basket, Long> implements BasketRepository {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(BasketJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.basket.BasketRepository#saveBasket(com.inetpsa.poc00.domain.basket.Basket)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.basket.BasketRepository#saveBasket(com.inetpsa .poc00.domain.basket.Basket)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Basket saveBasket(Basket basket) {
		return super.save(basket);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.basket.BasketRepository#updateBasket(com.inetpsa.poc00.domain.basket.Basket)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.domain.basket.BasketRepository#updateBasket(com.inetpsa .poc00.domain.basket.Basket)
	 */
	@Override
	public Basket updateBasket(Basket basket) {

		return entityManager.merge(basket);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.basket.BasketRepository#getBasketByUserId(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Basket getBasketByUserId(String userId) {
		TypedQuery<Basket> query = entityManager.createQuery("FROM Basket bkt WHERE bkt.user.userId = '" + userId + "'", Basket.class);

		List<Basket> basketList = query.getResultList();

		if (basketList != null && !basketList.isEmpty()) {
			return basketList.get(0);
		}
		return null;
	}

}

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.basket.Basket;
import com.inetpsa.poc00.rest.basket.BasketFinder;

/**
 * The Class JpaBasketFinder.
 */
public class JpaBasketFinder implements BasketFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.basket.BasketRepository#getBasketByUser(java.lang.String)
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

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.basket.BasketFinder#getBasketById(java.lang.Long)
     */
    @Override
    public Basket getBasketById(Long basketId) {

        TypedQuery<Basket> query = entityManager.createQuery("SELECT bkt FROM Basket bkt WHERE bkt.entityId = :basketId", Basket.class);

        List<Basket> basketList = query.getResultList();

        if (basketList != null && !basketList.isEmpty()) {
            return basketList.get(0);
        }
        return null;
    }

}

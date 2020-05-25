/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvv;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVRepository;

/**
 * The Class TvvJpaRepository.
 */
public class TvvJpaRepository extends BaseJpaRepository<TVV, Long> implements TVVRepository {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TvvJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#saveTVV(com.inetpsa.poc00.domain.tvv.TVV)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TVV saveTVV(TVV tvvObject) {
        try {
            if (tvvObject.getEntityId() != null && tvvObject.getEntityId() != 0)
                return super.save(tvvObject);

            super.persist(tvvObject);
            entityManager.flush();
            return super.load(tvvObject.getEntityId());
        } catch (Exception e) {

            LOGGER.error("Error in saving TVV status", e);
            return tvvObject;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#persistTVV(com.inetpsa.poc00.domain.tvv.TVV)
     */
    @Override
    public void persistTVV(TVV object) {
        super.persist(object);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#getAllTVVLabel()
     */
    @Override
    public List<String> getAllTVVLabel() {

        TypedQuery<String> query = entityManager.createQuery("SELECT t.label FROM TVV t ", String.class);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#getMaxVersionForLabel(java.lang.String)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public double getMaxVersionForLabel(String tvvLabel) {
        Query query = entityManager.createNativeQuery(DomainConstants.MAX_TVV_VERSION_QUERY);

        query.setParameter(1, tvvLabel);

        return ((BigDecimal) query.getSingleResult()).doubleValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.tvv.TVVRepository#findTvvByCategoryId(java.lang.Long)
     */

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TVV> findTvvByCategoryId(Long categoryId) {
        TypedQuery<TVV> query = entityManager.createQuery("select tvv from TVV tvv where tvv.category.entityId=" + categoryId, TVV.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TVV loadTVV(long tvvId) {

        return super.load(tvvId);
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteTvv(long tvvId) {
        super.delete(tvvId);

    }

}

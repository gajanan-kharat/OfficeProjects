package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;

/**
 * The Class BodyworkJpaRepository.
 */
public class BodyworkJpaRepository extends BaseJpaRepository<Bodywork, Long> implements BodyworkRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.bodywork.BodyworkRepository#saveBodywork(com.inetpsa.poc00.domain.bodywork.Bodywork)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Bodywork saveBodywork(Bodywork bodywork) {

        return super.save(bodywork);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.bodywork.BodyworkRepository#persistBodywork(com.inetpsa.poc00.domain.bodywork.Bodywork)
     */
    @Override
    public Bodywork persistBodywork(Bodywork bodywork) {

        return entityManager.merge(bodywork);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.bodywork.BodyworkRepository#deleteAll(long)
     */
    @Override
    public int deleteAll(long id) {
        logger.info("delete value from Bodywork where id=" + id);
        try {
            return entityManager.createQuery("DELETE FROM Bodywork where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.bodywork.BodyworkRepository#getAllBodyWork()
     */
    @Override
    public List<Bodywork> getAllBodyWork() {

        TypedQuery<Bodywork> query = entityManager.createQuery("SELECT bdw FROM Bodywork bdw", Bodywork.class);

        return query.getResultList();

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Bodywork loadBodyWork(long bodyWorkId) {

        return super.load(bodyWorkId);
    }

}

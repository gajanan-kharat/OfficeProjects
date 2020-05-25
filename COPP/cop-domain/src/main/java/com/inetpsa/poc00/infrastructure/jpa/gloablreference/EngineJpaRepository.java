package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineRepository;

/**
 * The Class EngineJpaRepository.
 */
public class EngineJpaRepository extends BaseJpaRepository<Engine, Long> implements EngineRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.engine.EngineRepository#saveEngine(com.inetpsa.poc00.domain.engine.Engine)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Engine saveEngine(Engine engine) {

        return super.save(engine);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.engine.EngineRepository#persistEngine(com.inetpsa.poc00.domain.engine.Engine)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Engine persistEngine(Engine engine) {

        return entityManager.merge(engine);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.engine.EngineRepository#deleteAll(java.lang.Long)
     */
    // delete value
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteAll(Long id) {
        logger.info("delete value from Engine where id=" + id);

        int deleted = entityManager.createQuery("DELETE FROM Engine where entityId=" + id).executeUpdate();
        return deleted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.engine.EngineRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.engine.EngineRepository#getAllEngine()
     */
    @Override
    public List<Engine> getAllEngine() {

        TypedQuery<Engine> query = entityManager.createQuery("SELECT eng FROM Engine eng", Engine.class);

        List<Engine> engData = query.getResultList();

        return engData;

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Engine loadEngine(long engineId) {

        return super.load(engineId);
    }

}

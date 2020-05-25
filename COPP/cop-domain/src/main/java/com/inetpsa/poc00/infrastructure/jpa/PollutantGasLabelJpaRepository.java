package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;

/**
 * The Class PollutantGasLabelJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "PollutantGasLabel")
public class PollutantGasLabelJpaRepository extends BaseJpaRepository<PollutantGasLabel, Long> implements PollutantGasLabelRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(PollutantGasLabelJpaRepository.class);

    /** The gas limit list repository. */
    @Inject
    private PollutantGasLimitListRepository gasLimitListRepository;

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#savePollutantGasLabel(com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public PollutantGasLabel savePollutantGasLabel(PollutantGasLabel pgl) {
        if (pgl.getEntityId() == null || pgl.getEntityId() == 0)
            return super.save(pgl);

        return entityManager.merge(pgl);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#deletePollutantGasLabel(java.lang.Long)
     */
    @Override
    public int deletePollutantGasLabel(Long id) {
        try {

            return entityManager.createQuery("DELETE FROM PollutantGasLabel c where c.entityId = " + id).executeUpdate();

        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#persistPollutantGasLabel(com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel)
     */
    @Override
    public PollutantGasLabel persistPollutantGasLabel(PollutantGasLabel object) {
        return entityManager.merge(object);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#getPollutantByLabel(java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#getPollutantByLabel()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PollutantGasLabel> getPollutantByLabel(String pollutantGasLabel) {

        TypedQuery<PollutantGasLabel> query = entityManager.createQuery(
                "SELECT pollutant FROM PollutantGasLabel pollutant where pollutant.label='" + pollutantGasLabel + "'", PollutantGasLabel.class);

        return query.getResultList();

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#loadPollGasLabel(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public PollutantGasLabel loadPollGasLabel(long pollGasLabelId) {

        return super.load(pollGasLabelId);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelRepository#getAllUsedPgLabels(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId) {
        logger.info("getUsedPollutantLabels==============> emissionStdId	====>" + emissionStdId);
        List<PollutantGasLimitList> pgLimitListCollection = gasLimitListRepository.getLatestEmissionStandardDepLists(emissionStdId);

        List<PollutantGasLabel> pglabelList = new ArrayList<>();

        for (PollutantGasLimitList pgLimitList : nullGuard(pgLimitListCollection)) {

            for (PollutantGasLimit pglimit : nullGuard(pgLimitList.getPollutantGasLimit())) {

                pglabelList.add(pglimit.getPgLabel());

            }
        }

        return pglabelList;

    }

    /**
     * Null guard.
     * 
     * @param <T> the generic type
     * @param iterable the iterable
     * @return the iterable
     */
    private static <T> Iterable<T> nullGuard(Iterable<T> iterable) {

        if (iterable == null) {
            return Collections.<T> emptyList();
        }

        return iterable;
    }
}

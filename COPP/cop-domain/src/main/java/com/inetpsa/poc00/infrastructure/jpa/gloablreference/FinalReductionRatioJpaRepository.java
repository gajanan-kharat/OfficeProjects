package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository;

/**
 * The Class FinalReductionRatioJpaRepository.
 */
public class FinalReductionRatioJpaRepository extends BaseJpaRepository<FinalReductionRatio, Long> implements FinalReductionRatioRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository#saveReductionRatio(com.inetpsa.poc00.domain.finalreductionratio.
     * FinalReductionRatio)
     */
    // save value
    @Override
    public FinalReductionRatio saveReductionRatio(FinalReductionRatio finalReductionRation) {
        return super.save(finalReductionRation);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository#persistReductionRatio(com.inetpsa.poc00.domain.finalreductionratio.
     * FinalReductionRatio)
     */
    @Override
    public FinalReductionRatio persistReductionRatio(FinalReductionRatio finalReductionRation) {
        return entityManager.merge(finalReductionRation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository#deleteAll(long)
     */
    // delete value
    @Override
    public int deleteAll(long id) {
        logger.info("delete value from FinalReductionRatio where id=" + id);
        try {
            return entityManager.createQuery("DELETE FROM FinalReductionRatio where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository#getAllFinalReductionRation()
     */
    @Override
    public List<FinalReductionRatio> getAllFinalReductionRation() {

        TypedQuery<FinalReductionRatio> query = entityManager.createQuery("SELECT FRR FROM FinalReductionRatio FRR", FinalReductionRatio.class);

        List<FinalReductionRatio> frrData = query.getResultList();

        return frrData;

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FinalReductionRatio loadFinalReduction(long finalRdId) {

        return super.load(finalRdId);
    }

}

package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.traceability.Traceability;
import com.inetpsa.poc00.domain.traceability.TraceabilityRepository;

/**
 * The Class TraceabilityJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "Traceability")
public class TraceabilityJpaRepository extends BaseJpaRepository<Traceability, Long> implements TraceabilityRepository {

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.traceability.TraceabilityRepository#saveTraceability(com.inetpsa.poc00.domain.traceability.Traceability)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Traceability saveTraceability(Traceability object) {
        return super.save(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.traceability.TraceabilityRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.traceability.TraceabilityRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

}

package com.inetpsa.poc00.infrastructure.jpa.technicalcase;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.technicalcase.TechCaseRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;

/**
 * The Class TechnicalCaseJpaRepository.
 */
public class TechnicalCaseJpaRepository extends BaseJpaRepository<TechnicalCase, Long> implements TechCaseRepository {

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.technicalcase.TechCaseRepository#saveTechCase(com.inetpsa.poc00.domain.technicalcase.TechnicalCase)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalCase saveTechCase(TechnicalCase object) {
        return super.save(object);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.technicalcase.TechCaseRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.technicalcase.TechCaseRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalCase loadTechnicalCase(long entityId) {

        return super.load(entityId);
    }

    @Override
    public List<TechnicalCase> getTechnicalCasesForTG(Long technicalGroupId) {
        String querry = "select distinct techcase from TechnicalCase techcase where techcase.technicalGroup.entityId=?1";
        TypedQuery<TechnicalCase> queryResult = entityManager.createQuery(querry, TechnicalCase.class);

        queryResult.setParameter(1, technicalGroupId);
        return queryResult.getResultList();
    }

}

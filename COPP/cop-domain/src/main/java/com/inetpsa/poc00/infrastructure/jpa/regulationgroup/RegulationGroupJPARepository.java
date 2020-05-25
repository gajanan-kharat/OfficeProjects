package com.inetpsa.poc00.infrastructure.jpa.regulationgroup;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository;

/**
 * The Class RegulationGroupJPARepository.
 */
public class RegulationGroupJPARepository extends BaseJpaRepository<RegulationGroup, Long> implements RegulationGroupRepository {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegulationGroupJPARepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#saveRegulationGroup(com.inetpsa.poc00.domain.regulationgroup.RegulationGroup)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public RegulationGroup saveRegulationGroup(RegulationGroup object) {

        return super.save(object);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#deleteRegulationGroup(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteRegulationGroup(Long regulationGrpId) {
        super.delete(regulationGrpId);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#loadRegulationGroup(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public RegulationGroup loadRegulationGroup(long regulationGroupId) {

        return super.load(regulationGroupId);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.regulationgroup.RegulationGroupRepository#getRegulationGroupForTG(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public RegulationGroup getRegulationGroupForTG(Long technicalGroupId) {
        RegulationGroup rGroup = null;
        try {
            String query = "select regulationGrp from RegulationGroup regulationGrp join regulationGrp.technicalGroups tg where tg.entityId=?1";

            TypedQuery<RegulationGroup> queryResult = entityManager.createQuery(query, RegulationGroup.class);
            queryResult.setParameter(1, technicalGroupId);

            rGroup = queryResult.getSingleResult();
        } catch (Exception e) {
            // Exception is removed from logger as this is valid scenario,Cannot log Exception for this case
            LOGGER.error("Error in retriving Regulation Group " + e);
        }
        return rGroup;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<RegulationGroup> getRegulationGroup(String label) {
        String querry = "select new " + RegulationGroup.class.getName() + "(regulationGrp)"
                + "from RegulationGroup regulationGrp where regulationGrp.label=?1";

        TypedQuery<RegulationGroup> queryResult = entityManager.createQuery(querry, RegulationGroup.class);
        queryResult.setParameter(1, label);
        List<RegulationGroup> regulationGroupList = queryResult.getResultList();
        return regulationGroupList;
    }
}

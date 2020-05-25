package com.inetpsa.poc00.infrastructure.jpa.technicalgroup;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

/**
 * The Class TechnicalGroupJpaRepository.
 */
public class TechnicalGroupJpaRepository extends BaseJpaRepository<TechnicalGroup, Long> implements TechGroupRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalGroupJpaRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository#saveTechGroup(com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup saveTechGroup(TechnicalGroup object) {

        return super.save(object);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository#deleteTechnicalGroup(long)
     */
    @Override

    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteTechnicalGroup(long id) {

        super.delete(id);
    }

    @Override
    public List<TechnicalGroup> getAllTechnicalGroup() {

        TypedQuery<TechnicalGroup> query = entityManager.createQuery("SELECT TG FROM TechnicalGroup TG", TechnicalGroup.class);

        List<TechnicalGroup> tgData = query.getResultList();

        return tgData;

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Status getStatus(String statusLabel) {
        Status status = null;
        try {
            TypedQuery<Status> query = entityManager.createQuery("select s from Status s where Upper(s.label) = '" + statusLabel + "'", Status.class);
            status = query.getSingleResult();
        } catch (Exception e) {

        }
        return status;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup lodTechnicalGroup(long technicalGroupId) {

        return super.load(technicalGroupId);
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TechnicalGroup> getTechnicalGroup(String label) {
        String querry = "select new " + TechnicalGroup.class.getName() + "(techgroup)" + "from TechnicalGroup techgroup where techgroup.label=?1";

        TypedQuery<TechnicalGroup> queryResult = entityManager.createQuery(querry, TechnicalGroup.class);

        queryResult.setParameter(1, label);

        List<TechnicalGroup> technicalGroupList = queryResult.getResultList();
        return technicalGroupList;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TechnicalGroup getTechnicalGroupForTechnicalCase(Long technicalCaseId) {
        TechnicalGroup tGroup = null;
        try {
            String queryString = "select new " + TechnicalGroup.class.getName() + "(techgroup)"
                    + "from TechnicalGroup techgroup join techgroup.technicalCase techCase where techCase.entityId=?1";

            TypedQuery<TechnicalGroup> query = entityManager.createQuery(queryString, TechnicalGroup.class);

            query.setParameter(1, technicalCaseId);
            tGroup = query.getSingleResult();

        } catch (Exception e) {
            // Exception is removed from logger as this is valid scenario,Cannot log Exception for this case
            LOGGER.warn("No Technical Group Found for given Technical Case");
        }
        return tGroup;
    }

}

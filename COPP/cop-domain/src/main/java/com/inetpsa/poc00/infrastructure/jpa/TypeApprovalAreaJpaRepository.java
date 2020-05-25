package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository;

/**
 * The Class TypeApprovalAreaJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "TypeApprovalArea")
public class TypeApprovalAreaJpaRepository extends BaseJpaRepository<TypeApprovalArea, Long> implements TypeApprovalAreaRepository {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(TypeApprovalAreaJpaRepository.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#saveTypeApprovalArea(com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TypeApprovalArea saveTypeApprovalArea(TypeApprovalArea typeApp) {

        if (typeApp.getEntityId() == null || typeApp.getEntityId() == 0)
            return super.save(typeApp);

        return entityManager.merge(typeApp);

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#deleteTypeApprovalArea(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteTypeApprovalArea(long id) {
        try {
            return entityManager.createQuery("DELETE FROM TypeApprovalArea c where c.entityId = " + id).executeUpdate();

        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#persistTypeApprovalArea(com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea)
     */
    @Override
    public void persistTypeApprovalArea(TypeApprovalArea object) {
        // NOT IN USE
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#getTypeApprovalAreaByLabel(java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository#getTypeApprovalAreaByLabel()
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TypeApprovalArea> getTypeApprovalAreaByLabel(String typeappAreaLabel) {

        TypedQuery<TypeApprovalArea> query = entityManager.createQuery(
                "SELECT typeArea FROM TypeApprovalArea typeArea where typeArea.label='" + typeappAreaLabel + "'", TypeApprovalArea.class);

        return query.getResultList();

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TypeApprovalArea loadTypeApproval(long typeId) {

        return super.load(typeId);
    }
}

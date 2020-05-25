/*
 * Creation : Feb 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.tvvdependent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository;

/**
 * The Class TvvDependentTDLJpaRepository.
 */
public class TvvDependentTDLJpaRepository extends BaseJpaRepository<TvvDepTDL, Long> implements TvvDepTDLRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTDLRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#saveTvvDepTDL(com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TvvDepTDL saveTvvDepTDL(TvvDepTDL tvvDepTDL) {
        if (tvvDepTDL.getEntityId() != null && tvvDepTDL.getEntityId() != 0)
            return super.save(tvvDepTDL);

        super.persist(tvvDepTDL);
        entityManager.flush();
        return super.load(tvvDepTDL.getEntityId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#persistTvvDepTDL(com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL)
     */
    @Override
    public void persistTvvDepTDL(TvvDepTDL tvvDepTDL) {

        super.persist(tvvDepTDL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#deleteAll()
     */
    @Override
    public long deleteAll() {
        return entityManager.createQuery("DELETE FROM TvvDepTDL").executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#count()
     */
    @Override
    public long count() {
        String countQuery = "select count(*) from TvvDepTDL ";

        long queryCount = 0;

        TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery, Long.class);

        queryCount = typedQuery.getSingleResult();

        return queryCount;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#deleteTvvDepTDL(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public void deleteTvvDepTDL(long entityId) {
        try {
            super.delete(entityId);
        } catch (Exception e) {

            LOGGER.error("Error in deleting TvvDepTDL", e);
            throw e;

        }
    }

    /**
     * Update tvv dep tdl.
     *
     * @param tvvDepTDLObj the tvv dep tdl obj
     */
    protected void updateTvvDepTDL(TvvDepTDL tvvDepTDLObj) {
        if (tvvDepTDLObj != null) {
            TvvDepTDL objToSave = entityManager.find(TvvDepTDL.class, tvvDepTDLObj.getEntityId());
            if (objToSave != null) {
                objToSave.setDescription(tvvDepTDLObj.getDescription());
                objToSave.setLabel(tvvDepTDLObj.getLabel());
                objToSave.setVersion(tvvDepTDLObj.getVersion());
                super.save(objToSave);

            } else
                super.persist(tvvDepTDLObj);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#createNewVersion(com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL)
     */
    @Override
    public TvvDepTDL createNewVersion(TvvDepTDL tvvDepTDL) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository#getAllTvvDependentListByLatestVer()
     */
    @Override
    public List<TvvDepTDL> getAllTvvDependentListByLatestVer() {
        List<TvvDepTDL> resultList = new ArrayList<TvvDepTDL>();
        Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTDL t1 group by t1.label)");
        List<Long> idList = idSubQuery.getResultList();
        for (Long id : idList) {
            TypedQuery<TvvDepTDL> query = entityManager.createQuery("select t from TvvDepTDL t where t.entityId =" + id, TvvDepTDL.class);
            resultList.add(query.getSingleResult());
        }
        return resultList;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TvvDepTDL loadTvvDepTDL(long tvvDepTDLId) {

        return super.load(tvvDepTDLId);
    }

}

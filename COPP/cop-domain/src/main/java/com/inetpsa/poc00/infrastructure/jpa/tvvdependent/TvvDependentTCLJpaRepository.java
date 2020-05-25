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
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository;

/**
 * The Class TvvDependentTCLJpaRepository.
 */
public class TvvDependentTCLJpaRepository extends BaseJpaRepository<TvvDepTCL, Long> implements TvvDepTCLRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TvvDepTCLRepository.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#saveTvvDepTCL(com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL)
     */
    @Override
    public TvvDepTCL saveTvvDepTCL(TvvDepTCL tvvDepTCL) {

        if (tvvDepTCL.getEntityId() != null && tvvDepTCL.getEntityId() != 0)
            return super.save(tvvDepTCL);

        super.persist(tvvDepTCL);
        entityManager.flush();
        return super.load(tvvDepTCL.getEntityId());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#persistTvvDepTCL(com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL)
     */
    @Override
    public void persistTvvDepTCL(TvvDepTCL tvvDepTCL) {
        super.persist(tvvDepTCL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#deleteAll()
     */
    @Override
    public long deleteAll() {

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#count()
     */
    @Override
    public long count() {

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#deleteTvvDepTCL(long)
     */
    @Override
    public void deleteTvvDepTCL(long id) {
        try {
            super.delete(id);
        } catch (Exception e) {

            LOGGER.error("Error in deleting TvvDepTDL", e);
            throw e;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository#getAllTvvDependentLists()
     */
    @Override
    public List<TvvDepTCL> getAllTvvDependentLists() {
        List<TvvDepTCL> resultList = new ArrayList<TvvDepTCL>();
        Query idSubQuery = entityManager.createQuery("select MAX(t1.entityId) from TvvDepTCL t1 group by t1.label)");
        List<Long> idList = idSubQuery.getResultList();
        for (Long id : idList) {
            TypedQuery<TvvDepTCL> query = entityManager.createQuery("select t from TvvDepTCL t where t.entityId =" + id, TvvDepTCL.class);
            resultList.add(query.getSingleResult());
        }
        return resultList;
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TvvDepTCL loadTvvDepTcl(long tvvDepTclId) {

        return super.load(tvvDepTclId);
    }

}

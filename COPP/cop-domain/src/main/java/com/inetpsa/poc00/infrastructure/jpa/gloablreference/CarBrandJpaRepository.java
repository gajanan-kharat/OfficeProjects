package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;

/**
 * The Class CarBrandJpaRepository.
 */
public class CarBrandJpaRepository extends BaseJpaRepository<CarBrand, Long> implements CarBrandRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#saveCarBrand(com.inetpsa.poc00.domain.carbrand.CarBrand)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public CarBrand saveCarBrand(CarBrand carBrand) {

        return super.save(carBrand);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#persistCarBrand(com.inetpsa.poc00.domain.carbrand.CarBrand)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public CarBrand persistCarBrand(CarBrand carBrand) {
        return entityManager.merge(carBrand);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#deleteAll(long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public int deleteAll(long id) {
        logger.info("delete value from CarBrand where id=" + id);
        try {
            return entityManager.createQuery("DELETE FROM CarBrand where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#getAllCarBrand()
     */
    @Override
    public List<CarBrand> getAllCarBrand() {

        TypedQuery<CarBrand> query = entityManager.createQuery("SELECT CB FROM CarBrand CB", CarBrand.class);

        List<CarBrand> carBrand = query.getResultList();

        return carBrand;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.poc00.domain.carbrand.CarBrandRepository#getCarBrandsForTVV(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<CarBrand> getCarBrandsForTVV(Long tvvId) {
        TypedQuery<CarBrand> query = entityManager.createQuery("select cb from CarBrand cb INNER JOIN cb.tvvSet t where t.entityId=" + tvvId,
                CarBrand.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public CarBrand loadCarBrand(long carBrandId) {

        return super.load(carBrandId);
    }

}

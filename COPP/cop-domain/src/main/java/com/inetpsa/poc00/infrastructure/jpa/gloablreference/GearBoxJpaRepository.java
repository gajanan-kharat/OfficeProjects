package com.inetpsa.poc00.infrastructure.jpa.gloablreference;

import java.util.List;

import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;

/**
 * The Class GearBoxJpaRepository.
 */
public class GearBoxJpaRepository extends BaseJpaRepository<GearBox, Long> implements GearBoxRepository {

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.gearbox.GearBoxRepository#saveGearBox(com.inetpsa.poc00.domain.gearbox.GearBox)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public GearBox saveGearBox(GearBox gearBox) {
        return super.save(gearBox);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.gearbox.GearBoxRepository#persistGearBox(com.inetpsa.poc00.domain.gearbox.GearBox)
     */
    @Override
    public GearBox persistGearBox(GearBox gearBox) {
        return entityManager.merge(gearBox);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.gearbox.GearBoxRepository#deleteAll(java.lang.Long)
     */
    // delete value
    @Override
    public int deleteAll(Long id) {
        logger.info("deleting value from gearBox table where id=" + id);
        try {
            return entityManager.createQuery("DELETE FROM GearBox where entityId=" + id).executeUpdate();
        } catch (Exception e) {
            logger.error(" Error occured while deleting data ", e);
            return 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.gearbox.GearBoxRepository#count()
     */
    @Override
    public long count() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.gearbox.GearBoxRepository#getAllGearBox()
     */
    @Override
    public List<GearBox> getAllGearBox() {

        TypedQuery<GearBox> query = entityManager.createQuery("SELECT GB FROM GearBox GB", GearBox.class);

        return query.getResultList();

    }

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public GearBox loadGearBox(long gearBoxId) {

        return super.load(gearBoxId);
    }

}

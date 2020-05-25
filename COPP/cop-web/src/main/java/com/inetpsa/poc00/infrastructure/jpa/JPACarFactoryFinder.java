package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.rest.carfactory.CarFactoryFinder;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;

/**
 * The Class JPACarFactoryFinder.
 */
public class JPACarFactoryFinder implements CarFactoryFinder {

    /** The entity manager. */
    @Inject
    private EntityManager entityManager;

    /** The logger. */
    @Logging
    private Logger logger;

    /*
     * @see com.inetpsa.poc00.rest.CarFactory.CarFactoryFinder#findAllUsineData() get Country Data
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.carfactory.CarFactoryFinder#findAllUsineData()
     */
    @Override
    public List<CarFactoryRepresentation> findAllUsineData() {

        logger.info("querry running to get CarFactory value");
        TypedQuery<CarFactoryRepresentation> query = entityManager.createQuery("select new " + CarFactoryRepresentation.class.getName()
                + "(cf.label,cf.entityId)" + "from CarFactory cf" + " ORDER BY cf.entityId asc", CarFactoryRepresentation.class);

        return query.getResultList();

    }

    @Override
    public List<CarFactoryRepresentation> findCarFactoryDataByLabel(String carFactoryLabel) {
        logger.info("querry running to check if label present");

        TypedQuery<CarFactoryRepresentation> query = entityManager.createQuery("select new " + CarFactoryRepresentation.class.getName()
                + "(cf.entityId) from CarFactory cf where cf.label='" + carFactoryLabel + "'", CarFactoryRepresentation.class);

        return query.getResultList();
    }

    @Override
    public List<CarFactoryRepresentation> getFactoriesForTVV(Long tvvId) {
        TypedQuery<CarFactoryRepresentation> query = entityManager.createQuery("select new " + CarFactoryRepresentation.class.getName()
                + "(cb.label,cb.entityId)" + " from CarFactory cb INNER JOIN cb.tvvSet t where t.entityId=" + tvvId, CarFactoryRepresentation.class);
        return query.getResultList();
    }

}

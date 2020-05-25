/*
 * Creation : Sep 22, 2016
 */

package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestFinder;
import com.inetpsa.poc00.rest.typeoftest.TypeOfTestRepresentation;

/**
 * The JPATypeOfTestFinder Class
 * 
 * @author mehaj
 */
public class JPATypeOfTestFinder implements TypeOfTestFinder {
    /** The entity manager. */
    @Inject
    private EntityManager entityManager;
    /** The logger. */
    @Logging
    private Logger logger;

    /**
     * Finds All TypeOfTestData
     * 
     * @return TypeOfTestRepresentation List
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public List<TypeOfTestRepresentation> findAllTypeTestData() {

        logger.info("querry running to get CarFactory value");

        TypedQuery<TypeOfTestRepresentation> query = entityManager.createQuery("select new " + TypeOfTestRepresentation.class.getName()
                + "(t1.entityId,t1.label,t1.testNature.type)" + " from TypeOfTest t1 ORDER BY t1.entityId asc", TypeOfTestRepresentation.class);

        return query.getResultList();

    }

    /**
     * Finds TypeOfTest by Label
     * 
     * @param typeOfTestLabel TypeOfTest Label to serach
     * @return TypeOfTest entity
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public TypeOfTest findTypeOfTestForLabel(String typeOfTestLabel) {
        TypeOfTest typeOfTest = null;
        try {
            Query query = entityManager.createQuery(" from TypeOfTest typeOfTest where Upper(typeOfTest.label)  = ?1");
            query.setParameter(1, typeOfTestLabel.toUpperCase());
            List typeOfTestList = query.getResultList();
            if (typeOfTestList != null && !typeOfTestList.isEmpty()) {
                typeOfTest = (TypeOfTest) typeOfTestList.get(0);
            }
        } catch (Exception e) {
            logger.error("Error in retriving TypeOfTest", e);
        }
        return typeOfTest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.rest.testnature.TestNatureFinder#isTestNatureUsed(java.lang.Long)
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public Boolean isTypeOfTestUsed(Long typeTestId) {
        String queryString = "SELECT ID FROM COPQTVHF WHERE TYPE_TEST_ID = ?1";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(1, typeTestId);
        if (query.getResultList().isEmpty())
            return Boolean.FALSE;
        return Boolean.TRUE;
    }
}

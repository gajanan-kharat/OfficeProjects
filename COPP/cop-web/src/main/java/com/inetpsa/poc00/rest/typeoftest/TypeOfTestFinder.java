/*
 * Creation : Sep 22, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.typeoftest;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * the TypeOfTestFinder Class
 * 
 * @author mehaj
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TypeOfTestFinder {

    /**
     * Find all Type of test data.
     * 
     * @return the list of TypeOfTestRepresentation
     */
    List<TypeOfTestRepresentation> findAllTypeTestData();

    /**
     * Find Type of test for label
     * 
     * @return the TypeOfTest
     */
    TypeOfTest findTypeOfTestForLabel(String statusLabel);

    Boolean isTypeOfTestUsed(Long typeTestId);

}

/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.domain.typeoftest;

import org.seedstack.business.domain.GenericFactory;

/**
 * The TypeOfTest Factory
 * 
 * @author mehaj
 */
public interface TypeOfTestFactory extends GenericFactory<TypeOfTest> {

    /**
     * TypeOfTest Factory interface.
     */

    /**
     * Factory create method.
     * 
     * @param name
     * @return the TypeOfTest
     */
    TypeOfTest createTypeOfTest();

    TypeOfTest createTypeOfTest(String draft);

}

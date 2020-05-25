/*
 * Creation : Sep 26, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.domain.typeoftest;

import org.seedstack.business.domain.BaseFactory;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
public class TypeOfTestFactoryDefault extends BaseFactory<TypeOfTest> implements TypeOfTestFactory {

    /**
     * Factory create method.
     * 
     * @param name
     * @return the Status
     */

    @Override
    public TypeOfTest createTypeOfTest() {
        return new TypeOfTest();
    }

    @Override
    public TypeOfTest createTypeOfTest(String label) {
        return new TypeOfTest(label);
    }

}

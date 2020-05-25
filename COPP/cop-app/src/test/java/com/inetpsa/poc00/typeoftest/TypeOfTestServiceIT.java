/*
 * Creation : Nov 4, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.typeoftest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.application.testnature.TestNatureService;
import com.inetpsa.poc00.application.typeoftest.TypeOfTestService;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestFactory;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class TypeOfTestServiceIT {
    @Inject
    TypeOfTestFactory typeOfTestFactory;
    @Inject
    TestNatureFactory testNatureFactory;
    @Inject
    TestNatureService testNatureService;
    @Inject
    TypeOfTestService typeOfTestService;
    @Inject
    TypeOfTestRepository typeOfTestRepository;

    @Test
    public void saveTypeOfTest() {
        TypeOfTest typeOfTest = typeOfTestFactory.createTypeOfTest();
        typeOfTest.setLabel("TOTForUnitTest" + Calendar.getInstance().getTime());
        TestNature tn = testNatureFactory.createTestNature();
        tn.setHierarchy(8);
        tn.setLabel("testNatureForTesting" + Calendar.getInstance().getTime());
        tn.setType("type 1");
        TestNature newObj = testNatureService.saveTestNature(tn);
        typeOfTest.setTestNature(newObj);
        TypeOfTest typeOfTestnew = typeOfTestService.saveTypeOfTest(typeOfTest);
        assertNotNull(typeOfTestnew.getEntityId());
    }

    @Test
    public void deleteTypeOfTest() {

        TypeOfTest typeOfTest = typeOfTestFactory.createTypeOfTest();
        typeOfTest.setLabel("TOTForUnitTest" + Calendar.getInstance().getTime());
        TestNature tn = testNatureFactory.createTestNature();
        tn.setHierarchy(8);
        tn.setLabel("testNatureForTesting" + Calendar.getInstance().getTime());
        tn.setType("type 1" + Calendar.getInstance().getTime());
        TestNature newObj = testNatureService.saveTestNature(tn);
        typeOfTest.setTestNature(newObj);
        TypeOfTest typeOfTestnew = typeOfTestService.saveTypeOfTest(typeOfTest);
        assertNotNull(typeOfTestnew.getEntityId());
        typeOfTestService.delete(typeOfTestnew.getEntityId());
        TypeOfTest typeOfTestDeleted = typeOfTestRepository.loadTypeOfTest(typeOfTestnew.getEntityId());
        assertEquals(typeOfTestDeleted, null);
    }

}

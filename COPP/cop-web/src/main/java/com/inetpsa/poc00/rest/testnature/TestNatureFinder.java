package com.inetpsa.poc00.rest.testnature;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;

import com.inetpsa.poc00.domain.testnature.TestNature;

/**
 * The Interface TestNatureFinder.
 */
@Finder
@Transactional
public interface TestNatureFinder {

    /**
     * Gets the all test nature.
     * 
     * @return the all test nature
     */
    public List<TestNatureRepresentation> getAllTestNature();

    /**
     * Find test nature.
     * 
     * @param testNatureLabel the test nature label
     * @return the test nature
     */
    public TestNature findTestNature(String testNatureLabel);

    /**
     * Checks if is test nature used in status table.
     * 
     * @param testNatureId the test nature id
     * @return the boolean
     */

    Boolean isTestNatureUsedInStatus(Long testNatureId);

    /**
     * Checks if is test nature used in TypeOfTest table.
     * 
     * @param testNatureId the test nature id
     * @return the boolean
     */
    Boolean isTestNatureUsedInTypeTest(Long testNatureId);

    /**
     * Testnature list.
     *
     * @param testNatureHierarchy the test nature hierarchy
     * @return the list
     */
    List<Long> testnatureList(Long typeOfTestId);
}

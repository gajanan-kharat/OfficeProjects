package com.inetpsa.poc00.domain.testnature;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of TestNature.
 */

public interface TestNatureRepository extends GenericRepository<TestNature, Long> {

	/**
	 * Saves the TestNature.
	 * 
	 * @param object the TestNature to save
	 * @return the TestNature
	 */
	TestNature saveTestNature(TestNature object);

	/**
	 * Persists the TestNature.
	 * 
	 * @param object the TestNature to persist
	 */
	void persistTestNature(TestNature object);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return TestNature count
	 */
	long count();

	/**
	 * Delete test nature.
	 * 
	 * @param testId the test id
	 * @return the long
	 */
	long deleteTestNature(long testId);

	/**
	 * Gets the all test nature.
	 * 
	 * @return the all test nature
	 */
	public List<TestNature> getAllTestNature();

	/**
	 * Load test nature.
	 *
	 * @param Id the id
	 * @return the test nature
	 */
	public TestNature loadTestNature(Long Id);

}

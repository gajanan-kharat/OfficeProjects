package com.inetpsa.poc00.domain.genometvvrule;

import java.util.List;

import javax.persistence.EntityManager;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;

/**
 * The Interface GenomeTVVRuleRepository.
 */
public interface GenomeTVVRuleRepository extends GenericRepository<GenomeTVVRule, Long> {
	/**
	 * Saves the GenomeTVVRUle.
	 * 
	 * @param object the GenomeTVVRUle to save
	 * @return the GenomeTVVRUle
	 */
	void saveGenomeTVVRule(List<GenomeTVVRule> object);

	/**
	 * Save genome tvv rule.
	 *
	 * @param object the object
	 */
	void saveGenomeTVVRule(GenomeTVVRule object);

	/**
	 * Persists the GenomeTVVRUle.
	 * 
	 * @param object the GenomeTVVRUle to persist
	 */
	void persistGenomeTVVRule(GenomeTVVRule object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return GenomeTVVRUle count
	 */
	long count();

	/**
	 * Save or update genome tvv rule.
	 *
	 * @param object the object
	 */
	void saveOrUpdateGenomeTVVRule(List<GenomeTVVRule> object);

	/**
	 * If lcdv code value exist.
	 *
	 * @param em the em
	 * @param tvvRule the tvv rule
	 * @return the list
	 */
	List<GenomeLCDVCodeValue> ifLCDVCodeValueExist(EntityManager em, GenomeTVVRule tvvRule);

}

package org.seedstack.pv2.domain.ruleofuses;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * Repository interface of RuleOfUses.
 */

public interface RuleOfUsesRepository extends
		GenericRepository<RuleOfUses, Long> {

	/**
	 * Saves the rule.
	 *
	 * @param rule
	 *            the RuleOfUses to save
	 * @return the RuleOfUses
	 */
	RuleOfUses saveRule(RuleOfUses rule);

	/**
	 * Persists the rule.
	 *
	 * @param rule
	 *            the RuleOfUses to persist
	 */
	void persistRule(RuleOfUses rule);
	
	/**
	 * Find the rules by family id.
	 *
	 * @param familyID
	 *            the RuleOfUses to find
	 * @param name
	 * @return the RuleOfUses
	 */
	RuleOfUses findAllRuleByFamilyId(PictoFamily  familyID,String name);

}
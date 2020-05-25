package org.seedstack.pv2.domain.ruleofuses;

import org.seedstack.business.domain.GenericFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * RuleOfUses Factory interface.
 */
public interface RuleOfUsesFactory extends GenericFactory<RuleOfUses> {

	/**
	 * Create rule factory
	 * 
	 * @param id
	 * @param name
	 * @param docLink
	 * @param familyId
	 * @return
	 */
	public RuleOfUses createRule(Long id, String name, String docLink,
			PictoFamily familyId);
}

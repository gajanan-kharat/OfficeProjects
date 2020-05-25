package org.seedstack.pv2.domain.ruleofuses;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

/**
 * RuleOfUses Factory implementation.
 */

public class RuleOfUsesFactoryDefault extends BaseFactory<RuleOfUses> implements
		RuleOfUsesFactory {
	@Override
	public RuleOfUses createRule(Long id, String name, String docLink,
			PictoFamily familyId) {
		return new RuleOfUses(id, name, docLink, familyId);
	}

}

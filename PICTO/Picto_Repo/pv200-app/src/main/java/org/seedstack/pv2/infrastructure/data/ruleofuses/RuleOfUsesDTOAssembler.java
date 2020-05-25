package org.seedstack.pv2.infrastructure.data.ruleofuses;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;

/**
 * The Class RuleOfUsesDTOAssembler.
 */
public class RuleOfUsesDTOAssembler extends BaseAssembler<RuleOfUses, RuleOfUsesDTO> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(RuleOfUsesDTO targetDto, RuleOfUses sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		targetDto.setName(sourceAggregate.getName());
		targetDto.setDocLink(sourceAggregate.getDocLink());
		// targetDto.setFamilyId(sourceAggregate.getFamilyId());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(RuleOfUses targetAggregate, RuleOfUsesDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setName(sourceDto.getName());
		targetAggregate.setDocLink(sourceDto.getDocLink());

	}

}

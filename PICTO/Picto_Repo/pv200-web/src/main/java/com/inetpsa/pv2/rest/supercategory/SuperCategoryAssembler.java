package com.inetpsa.pv2.rest.supercategory;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.supercategory.SuperCategory;


/**
 * The Class SuperCategoryAssembler.
 */
public class SuperCategoryAssembler extends BaseAssembler<SuperCategory, SuperCategoryRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(SuperCategoryRepresentation targetDto, SuperCategory sourceEntity) {
		targetDto.setSuperCategoryId(sourceEntity.getEntityId());
		targetDto.setName(sourceEntity.getName());

	}

	/**
	 * Do merge aggregate with dto.
	 *
	 * @param targetEntity the p target entity
	 * @param sourceDto the p source dto
	 */
	@Override
	protected void doMergeAggregateWithDto(SuperCategory targetEntity, SuperCategoryRepresentation sourceDto) {

	}

}

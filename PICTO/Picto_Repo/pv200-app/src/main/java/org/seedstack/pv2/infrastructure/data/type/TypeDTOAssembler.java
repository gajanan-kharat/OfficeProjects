package org.seedstack.pv2.infrastructure.data.type;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.type.Type;

/**
 * The Class TypeDTOAssembler.
 */
public class TypeDTOAssembler extends BaseAssembler<Type, TypeDTO> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(TypeDTO targetDto, Type sourceAggregate) {
		targetDto.setTypeID(sourceAggregate.getEntityId());
		targetDto.setTypeLabel(sourceAggregate.getTypeLable());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(Type targetAggregate, TypeDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getTypeID());
		targetAggregate.setTypeLable(sourceDto.getTypeLabel());
	}
}
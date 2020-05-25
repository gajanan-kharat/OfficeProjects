package com.inetpsa.poc00.rest.basket;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.basket.Basket;

/**
 * The Class BasketAssembler.
 */
public class BasketAssembler extends BaseAssembler<Basket, BasketRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(BasketRepresentation targetDto, Basket sourceAggregate) {
			targetDto.setEntityId(sourceAggregate.getEntityId());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(Basket targetAggregate, BasketRepresentation sourceDto) {
			targetAggregate.setEntityId(sourceDto.getEntityId());
	}

}

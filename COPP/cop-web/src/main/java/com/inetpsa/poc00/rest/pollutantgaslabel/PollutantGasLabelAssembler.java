package com.inetpsa.poc00.rest.pollutantgaslabel;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Class PollutantGasLabelAssembler.
 */
public class PollutantGasLabelAssembler extends BaseAssembler<PollutantGasLabel, PollutantGasLabelRepresentation> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PollutantGasLabelRepresentation targetDto, PollutantGasLabel sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasLabel targetAggregate, PollutantGasLabelRepresentation sourceDto) {
		if (sourceDto != null) {

			targetAggregate.setEntityId(sourceDto.getEntityId());
			targetAggregate.setLabel(sourceDto.getLabel());
		}

	}

}

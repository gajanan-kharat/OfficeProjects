/*
 * Creation : Mar 1, 2016
 */
package com.inetpsa.poc00.infrastructure.data.pollutantgaslimitlist;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

public class PolllutantGasLimitListAssembler extends
		BaseAssembler<PollutantGasLimitList, PollutantGasLimitListDto> {

	@Override
	protected void doAssembleDtoFromAggregate(
			PollutantGasLimitListDto targetDto,
			PollutantGasLimitList sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());

	}

	@Override
	protected void doMergeAggregateWithDto(PollutantGasLimitList targetEntity,
			PollutantGasLimitListDto sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());

	}

}

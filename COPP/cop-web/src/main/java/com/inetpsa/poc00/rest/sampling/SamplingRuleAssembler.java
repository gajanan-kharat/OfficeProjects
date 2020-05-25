package com.inetpsa.poc00.rest.sampling;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.samplingrule.SamplingRule;

public class SamplingRuleAssembler extends

BaseAssembler < SamplingRule, SamplingRuleRepresentation > {

    @Override
    protected void doAssembleDtoFromAggregate(
        SamplingRuleRepresentation arg0, SamplingRule arg1) {
        // TODO Auto-generated method stub

    }

 /**
   Function to merge targetEntity with sourceDto
  */
    @Override
    protected void doMergeAggregateWithDto(SamplingRule targetEntity,
        SamplingRuleRepresentation sourceDto) {

    	targetEntity.setEntityId(sourceDto.getEntityId());
        targetEntity.setAmtOrPercent(sourceDto.getAmtOrPercent());
        targetEntity.setAmtType(sourceDto.getAmtType());
        targetEntity.setDescription(sourceDto.getDescription());
        targetEntity.setFrequency(sourceDto.getFrequency());
        targetEntity.setHigherLimit(sourceDto.getHigherLimit());
        targetEntity.setHigherSymbol(sourceDto.getHigherSymbol());
        targetEntity.setLowerLimit(sourceDto.getLowerLimit());
        targetEntity.setLowerSymbol(sourceDto.getLowerSymbol());
        targetEntity.setNeededAmt(sourceDto.getNeededAmt());
        targetEntity.setLabel(sourceDto.getLabel());
        targetEntity.setVersion(sourceDto.getVersion());

    }




}
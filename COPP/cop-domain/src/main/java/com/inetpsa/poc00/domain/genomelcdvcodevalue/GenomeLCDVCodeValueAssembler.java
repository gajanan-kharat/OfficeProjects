package com.inetpsa.poc00.domain.genomelcdvcodevalue;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeAssembler;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleAssembler;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleDto;

/*
 * Assembler for GenomeLCDVCodeValue and GenomeLCDVCodeValueDto
 */
public class GenomeLCDVCodeValueAssembler extends BaseAssembler<GenomeLCDVCodeValue, GenomeLCDVCodeValueDto> {

	@Inject
	GenomeLCDVCodeAssembler codeAssembler;

	@Inject
	GenomeTVVRuleAssembler ruleAssembler;

	/*
	 * (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenomeLCDVCodeValueDto targetDto, GenomeLCDVCodeValue sourceEntity) {

		// From Entity to DTO

		if (targetDto.getEntityId() == null)
			targetDto.setEntityId(sourceEntity.getEntityId());

		targetDto.setFrLabel(sourceEntity.getFrLabel());
		targetDto.setZ2Label(sourceEntity.getZ2Label());
		targetDto.setLcdvCodeValue(sourceEntity.getLcdvCodeValue());

		GenomeLCDVCodeDto obj = new GenomeLCDVCodeDto();
		codeAssembler.doAssembleDtoFromAggregate(obj, sourceEntity.getGenomeLcdvCode());
		targetDto.setGenomeLcdvCodeDto(obj);

		GenomeTVVRuleDto rule = new GenomeTVVRuleDto();
		//ruleAssembler.doAssembleDtoFromAggregate(rule, sourceEntity.getGenomeTvvRule());
		targetDto.setGenomeTvvRuleDto(rule);

	}

	/*
	 * (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenomeLCDVCodeValue targetEntity, GenomeLCDVCodeValueDto sourceDto) {

		// FROM DTO to ENTITY

		if (targetEntity.getEntityId() == null)
			targetEntity.setEntityId(sourceDto.getEntityId());

		targetEntity.setFrLabel(sourceDto.getFrLabel());
		targetEntity.setZ2Label(sourceDto.getZ2Label());
		targetEntity.setLcdvCodeValue(sourceDto.getLcdvCodeValue());

	}

}

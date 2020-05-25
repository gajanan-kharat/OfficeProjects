package com.inetpsa.poc00.domain.genomelcdvcode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueAssembler;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueDto;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueFactory;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleAssembler;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleFactory;

/**
 * The Class GenomeLCDVCodeAssembler.
 */
/*
 * Assembler for GenomeLCDVCode and GenomeLCDVCodeDto
 * 
 */
public class GenomeLCDVCodeAssembler extends BaseAssembler<GenomeLCDVCode, GenomeLCDVCodeDto> {

	/** The code value factory. */
	@Inject
	GenomeLCDVCodeValueFactory codeValueFactory;

	/** The code value assembler. */
	@Inject
	GenomeLCDVCodeValueAssembler codeValueAssembler;

	/** The rule assembler. */
	@Inject
	GenomeTVVRuleAssembler ruleAssembler;

	/** The rule factory. */
	@Inject
	GenomeTVVRuleFactory ruleFactory;

	/*
	 * (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenomeLCDVCodeDto targetDto, GenomeLCDVCode sourceEntity) {
			targetDto.setEntityId(sourceEntity.getEntityId());
	}

	/*
	 * (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenomeLCDVCode targetEntity, GenomeLCDVCodeDto sourceDto) {

		if (targetEntity.getEntityId() == null)
			targetEntity.setEntityId(sourceDto.getEntityId());

		targetEntity.setCodeName(sourceDto.getCodeName());
		targetEntity.setFrLabel(sourceDto.getFrLabel());
		targetEntity.setZ2Label(sourceDto.getZ2Label());

		if (sourceDto.getGenomeLcdvCodeValueListDto() != null && !sourceDto.getGenomeLcdvCodeValueListDto().isEmpty()) {

			List<GenomeLCDVCodeValueDto> genomeLcdvCodeSet = sourceDto.getGenomeLcdvCodeValueListDto();
			List<GenomeLCDVCodeValue> dataList = new ArrayList<>();

			for (GenomeLCDVCodeValueDto data : genomeLcdvCodeSet) {

				GenomeLCDVCodeValue tempObj = codeValueFactory.createGenomeLCDVCodeValue();

				if (targetEntity.getGenomeLcdvCodeValueList() != null && !targetEntity.getGenomeLcdvCodeValueList().isEmpty())
					for (GenomeLCDVCodeValue obj : targetEntity.getGenomeLcdvCodeValueList()) {
						if (obj.getLcdvCodeValue().equals(data.getLcdvCodeValue())) {
							tempObj = obj;
							break;
						}
					}

				tempObj.setGenomeLcdvCode(targetEntity);

				codeValueAssembler.doMergeAggregateWithDto(tempObj, data);

				dataList.add(tempObj);

			}

			targetEntity.setGenomeLcdvCodeValueList(dataList);
		}

	}
}

package com.inetpsa.poc00.domain.genomelcdvdictionary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeAssembler;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeFactory;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleAssembler;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleFactory;

/**
 * The Class GenomeLCDVDictionaryAssembler.
 */
/*
 * Assembler for GenomeLCDVDictionary and GenomeLCDVDictionaryDto
 */
public class GenomeLCDVDictionaryAssembler extends BaseAssembler<GenomeLCDVDictionary, GenomeLCDVDictionaryDto> {

	/** The code factory. */
	@Inject
	GenomeLCDVCodeFactory codeFactory;

	/** The code assembler. */
	@Inject
	GenomeLCDVCodeAssembler codeAssembler;

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
	public void doAssembleDtoFromAggregate(GenomeLCDVDictionaryDto targetDto, GenomeLCDVDictionary sourceAggregate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenomeLCDVDictionary targetEntity, GenomeLCDVDictionaryDto sourceDto) {

		if (targetEntity.getEntityId() == null)
			targetEntity.setEntityId(sourceDto.getEntityId());

		targetEntity.setFrLabel(sourceDto.getFrLabel());

		if (sourceDto.getKmat() != null) {
			targetEntity.setKmat(sourceDto.getKmat());
		}

		targetEntity.setDictionaryValue(sourceDto.getDictionaryValue());

		if (sourceDto.getGenomeLcdvCodeListDto() != null && !sourceDto.getGenomeLcdvCodeListDto().isEmpty()) {

			List<GenomeLCDVCodeDto> genomeLcdvCodeList = sourceDto.getGenomeLcdvCodeListDto();
			List<GenomeLCDVCode> dataList = new ArrayList<GenomeLCDVCode>();

			for (GenomeLCDVCodeDto data : genomeLcdvCodeList) {

				GenomeLCDVCode tempObj = codeFactory.createGenomeLCDVCode();

				if (targetEntity.getGenomeLcdvCodeList() != null && targetEntity.getGenomeLcdvCodeList().size() > 0)
					for (GenomeLCDVCode obj : targetEntity.getGenomeLcdvCodeList()) {
						if (obj.getCodeName().equals(data.getCodeName())) {
							tempObj = obj;
							break;
						}
					}

				tempObj.setGenomeLCDVDictionary(targetEntity);

				codeAssembler.doMergeAggregateWithDto(tempObj, data);

				dataList.add(tempObj);

			}

			targetEntity.setGenomeLcdvCodeList(dataList);

		}

	}
}

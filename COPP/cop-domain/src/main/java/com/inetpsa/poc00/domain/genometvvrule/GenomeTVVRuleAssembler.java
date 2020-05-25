package com.inetpsa.poc00.domain.genometvvrule;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeAssembler;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeFactory;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueAssembler;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueDto;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueFactory;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryAssembler;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryFactory;

/**
 * The Class GenomeTVVRuleAssembler.
 */
public class GenomeTVVRuleAssembler extends BaseAssembler<GenomeTVVRule, GenomeTVVRuleDto> {

	/** The code value factory. */
	@Inject
	GenomeLCDVCodeValueFactory codeValueFactory;

	/** The code value assembler. */
	@Inject
	GenomeLCDVCodeValueAssembler codeValueAssembler;

	/** The dy assembler. */
	@Inject
	GenomeLCDVDictionaryAssembler dyAssembler;

	/** The dy factory. */
	@Inject
	GenomeLCDVDictionaryFactory dyFactory;

	/** The code factory. */
	@Inject
	GenomeLCDVCodeFactory codeFactory;

	/** The code assembler. */
	@Inject
	GenomeLCDVCodeAssembler codeAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(GenomeTVVRuleDto targetDto, GenomeTVVRule sourceAggregate) {
		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setKmat(sourceAggregate.getKmat());
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(GenomeTVVRule targetEntity, GenomeTVVRuleDto sourceDto) {

		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setKmat(sourceDto.getKmat());
		targetEntity.setLcdvCodeName(sourceDto.getLcdvCodeName());
		targetEntity.setLcdvCodeValue(sourceDto.getLcdvCodeValue());
		targetEntity.setRuleId(sourceDto.getRuleId());

		if (sourceDto.getGenomeLcdvCodeValueSetDto() != null && !sourceDto.getGenomeLcdvCodeValueSetDto().isEmpty()) {
			if (sourceDto.getGenomeLcdvCodeValueSetDto() != null && !sourceDto.getGenomeLcdvCodeValueSetDto().isEmpty()) {

				List<GenomeLCDVCodeValueDto> genomeValueSet = sourceDto.getGenomeLcdvCodeValueSetDto();

				for (GenomeLCDVCodeValueDto dtoObj : genomeValueSet) {

					GenomeLCDVCodeValue tempObj = codeValueFactory.createGenomeLCDVCodeValue();
					codeValueAssembler.doMergeAggregateWithDto(tempObj, dtoObj);
					targetEntity.setGenomeLcdvCodeValue(tempObj);

				}

			}
		}

	}

}

package com.inetpsa.poc00.infrastructure.data.genomelcdvdictionary;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;

/**
 * @author
 */
public class GenomeLCDVDictionaryDTOAssembler extends
		BaseAssembler<GenomeLCDVDictionary, GenomeLCDVDictionaryDTO> {
	@Override
	protected void doAssembleDtoFromAggregate(
			GenomeLCDVDictionaryDTO targetDto,
			GenomeLCDVDictionary sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		/*targetDto.setName(sourceAggregate.getName());*/
	}

	@Override
	protected void doMergeAggregateWithDto(
			GenomeLCDVDictionary targetAggregate,
			GenomeLCDVDictionaryDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		/*targetAggregate.setName(sourceDto.getName());*/
	}
}

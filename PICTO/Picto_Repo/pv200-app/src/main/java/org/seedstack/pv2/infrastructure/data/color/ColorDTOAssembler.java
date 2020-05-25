package org.seedstack.pv2.infrastructure.data.color;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.color.Color;

/**
 * Class : Color DTO
 *
 */
public class ColorDTOAssembler extends BaseAssembler<Color, ColorDTO> {

	@Override
	protected void doAssembleDtoFromAggregate(ColorDTO targetDto, Color sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		targetDto.setColor(sourceAggregate.getColor());

	}

	@Override
	protected void doMergeAggregateWithDto(Color targetAggregate, ColorDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setColor(sourceDto.getColor());

	}
}

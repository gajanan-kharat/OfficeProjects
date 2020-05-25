package org.seedstack.pv2.infrastructure.data.specificdrawing;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;


/**
 * The Class SpecificDrawingDTOAssembler.
 */
public class SpecificDrawingDTOAssembler extends BaseAssembler<SpecificDrawing, SpecificDrawingDTO> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(SpecificDrawingDTO targetDto, SpecificDrawing sourceAggregate) {
		targetDto.setId(sourceAggregate.getEntityId());
		targetDto.setName(sourceAggregate.getName());
		targetDto.setCommentsEN(sourceAggregate.getCommentsEN());
		targetDto.setCommentsFR(sourceAggregate.getCommentsFR());
		// targetDto.setFamilyId(sourceAggregate.getFamilyId());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(SpecificDrawing targetAggregate, SpecificDrawingDTO sourceDto) {
		targetAggregate.setEntityId(sourceDto.getId());
		targetAggregate.setName(sourceDto.getName());
		targetAggregate.setCommentsEN(sourceDto.getCommentsEN());
		targetAggregate.setCommentsFR(sourceDto.getCommentsFR());
		// targetAggregate.setFamilyId(sourceDto.getFamilyId());

	}
}

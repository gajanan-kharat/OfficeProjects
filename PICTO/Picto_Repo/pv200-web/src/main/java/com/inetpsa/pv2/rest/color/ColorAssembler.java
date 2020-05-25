package com.inetpsa.pv2.rest.color;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.rest.category.CategoryAssembler;

/**
 * The ColorAssembler class extends BaseAssembler sets Entity Objects into DTO.
 *
 * @author Geometric Limited
 * @version 1.0
 */
public class ColorAssembler extends BaseAssembler<Color, ColorRepresentation> {

    /** The logger. */
    @Logging
    private final Logger logger = LoggerFactory.getLogger(CategoryAssembler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(ColorRepresentation targetDto, Color sourceEntity) {
        if (targetDto != null && sourceEntity != null) {
            try {
                targetDto.setId(sourceEntity.getEntityId());
                targetDto.setColor(sourceEntity.getColor());
                targetDto.setOrderId(sourceEntity.getOrder());
            } catch (Exception e) {
                logger.error("Exception occured in method doAssembleDtoFromAggregate of ColorAssembler class", e);

            }
        }

    }

    /**
     * Do merge aggregate with dto.
     *
     * @param targetEntity the target entity
     * @param sourceDto the source dto
     */
    @Override
    public void doMergeAggregateWithDto(Color targetEntity, ColorRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getId());
        targetEntity.setColor(sourceDto.getColor());
        targetEntity.setOrder(sourceDto.getOrderId());

    }

}
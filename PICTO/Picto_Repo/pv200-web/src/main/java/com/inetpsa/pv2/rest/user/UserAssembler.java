package com.inetpsa.pv2.rest.user;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class : UserAssembler.
 */
public class UserAssembler extends BaseAssembler<User, UserRepresentation> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(UserAssembler.class);

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(UserRepresentation targetDto, User sourceEntity) {
        try {
            if (targetDto != null && sourceEntity != null) {
                targetDto.setId(sourceEntity.getId());
                targetDto.setUserId(sourceEntity.getUserId());
                targetDto.setLastName(sourceEntity.getLastName());
                targetDto.setFirstName(sourceEntity.getFirstName());
                targetDto.setIsThickClient(sourceEntity.getIsThickClient());
            }
        } catch (Exception e) {
            logger.error("Error in setting DTO in method doAssembleDtoFromAggregate of UserAssembler class ", e);
        }
    }

    /**
     * Do merge aggregate with dto.
     *
     * @param targetEntity the target entity
     * @param sourceDto the source dto
     */
    @Override
    public void doMergeAggregateWithDto(User targetEntity, UserRepresentation sourceDto) {
        try {
            if (targetEntity != null && sourceDto != null) {
                targetEntity.setId(sourceDto.getId());
                targetEntity.setEntityId(sourceDto.getUserId());
                targetEntity.setFirstName(sourceDto.getFirstName());
                targetEntity.setLastName(sourceDto.getLastName());
                targetEntity.setIsThickClient(sourceDto.getIsThickClient());
            }
        } catch (Exception e) {
            logger.error("Error in setting Target Entity doMergeAggregateWithDto method in UserAssembler class ", e);
        }

    }
}

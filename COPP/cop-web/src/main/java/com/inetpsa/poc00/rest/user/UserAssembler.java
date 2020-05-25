package com.inetpsa.poc00.rest.user;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.user.User;

/**
 * The Class UserAssembler.
 */
public class UserAssembler extends BaseAssembler<User, UserRepresentation> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(UserRepresentation targetDto, User sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setFirstName(sourceEntity.getFirstName());
        targetDto.setLastName(sourceEntity.getLastName());
        targetDto.setUserId(sourceEntity.getUserId());
    }

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(User targetEntity, UserRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setFirstName(sourceDto.getFirstName());
		targetEntity.setLastName(sourceDto.getLastName());
		targetEntity.setUserId(sourceDto.getUserId());
	}

}

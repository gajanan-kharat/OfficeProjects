package org.seedstack.pv2.infrastructure.data.user;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.domain.user.User;

/**
 * The Class UserDTOAssembler.
 */
public class UserDTOAssembler extends BaseAssembler<User, UserDTO> {

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(UserDTO targetDto, User sourceAggregate) {
		targetDto.setFirstName(sourceAggregate.getFirstName());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(User targetAggregate, UserDTO sourceDto) {
		targetAggregate.setFirstName(sourceDto.getFirstName());

	}
}
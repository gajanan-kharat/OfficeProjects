/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.rest.status;

import com.inetpsa.poc00.domain.status.StatusNature;


/**
 * The Class StatusNatureAssembler.
 */
public class StatusNatureAssembler {

	/**
	 * Do assemble dto from aggregate.
	 *
	 * @param representation the representation
	 * @param entity the entity
	 */
	protected void doAssembleDtoFromAggregate(StatusNatureRepresentation representation, StatusNature entity) {
		representation.setEntityId(entity.getEntityId());

	}

	/**
	 * Do merge aggregate with dto.
	 *
	 * @param entity the entity
	 * @param representation the representation
	 */
	protected void doMergeAggregateWithDto(StatusNature entity, StatusNatureRepresentation representation) {
		entity.setEntityId(representation.getEntityId());
		// entity.setValue(representation.isValue());

	}
}

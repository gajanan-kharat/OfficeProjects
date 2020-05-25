/*
 * Creation : May 3, 2016
 */
package com.inetpsa.poc00.rest.unit;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.unit.Unit;

public class UnitAssembler extends BaseAssembler<Unit, UnitRepresentation> {

	@Override
	public void doAssembleDtoFromAggregate(UnitRepresentation unitDto, Unit unit) {
		unitDto.setEntityId(unit.getEntityId());
		unitDto.setValue(unit.getValue());

	}

	@Override
	public void doMergeAggregateWithDto(Unit unit, UnitRepresentation unitDto) {
		unit.setEntityId(unitDto.getEntityId());
		unit.setValue(unitDto.getValue());

	}

}

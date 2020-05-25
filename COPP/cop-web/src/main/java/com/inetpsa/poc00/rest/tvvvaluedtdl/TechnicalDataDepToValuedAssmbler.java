/*
 * Creation : May 26, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataAssembler;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;
import com.inetpsa.poc00.rest.unit.UnitRepresentation;

/**
 * The Class TechnicalDataDepToValuedAssmbler.
 */
public class TechnicalDataDepToValuedAssmbler extends BaseAssembler<GenericTechnicalData, ValuedGenericDataRepresentation> {

	/** The unit assembler. */
	@Inject
	private GenericTechnicalDataAssembler genericTechnicalDataAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ValuedGenericDataRepresentation targetDto, GenericTechnicalData sourceEntity) {
		targetDto.setEntityId(0);
		targetDto.setDataType(sourceEntity.getDataType());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());

		GenericTechnicalDataRepresentation genericData = new GenericTechnicalDataRepresentation();
		genericTechnicalDataAssembler.doAssembleDtoFromAggregate(genericData, sourceEntity);
		targetDto.setGenericData(genericData);
		UnitRepresentation unit = new UnitRepresentation();
		unit.setEntityId(0);
		unit.setValue(sourceEntity.getUnit().getValue());

		targetDto.setUnit(unit);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(GenericTechnicalData arg0, ValuedGenericDataRepresentation arg1) {
		// DO NOTHING
	}
}

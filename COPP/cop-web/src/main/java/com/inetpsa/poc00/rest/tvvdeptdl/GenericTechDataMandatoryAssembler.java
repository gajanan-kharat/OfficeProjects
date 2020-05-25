/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictechdatamandatory.GenericTechDataMandatory;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentationAssembler;

/**
 * The Class GenericTechDataMandatoryAssembler.
 */
public class GenericTechDataMandatoryAssembler extends BaseAssembler<GenericTechDataMandatory, GenericTechDataMandatoryRepresentation> {

	/** The status assembler. */
	@Inject
	StatusRepresentationAssembler statusAssembler;

	/** The test nature assembler. */
	@Inject
	TestNatureRepresentationAssembler testNatureAssembler;

	/** The status factory. */
	@Inject
	StatusFactory statusFactory;

	/** The test nature factory. */
	@Inject
	TestNatureFactory testNatureFactory;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	protected void doAssembleDtoFromAggregate(GenericTechDataMandatoryRepresentation representation, GenericTechDataMandatory entity) {
		representation.setEntityId(entity.getEntityId());
		representation.setValue(entity.getValue());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(GenericTechDataMandatory entity, GenericTechDataMandatoryRepresentation representation) {
		entity.setEntityId(representation.getEntityId());
		entity.setValue(representation.getValue());

	}
}

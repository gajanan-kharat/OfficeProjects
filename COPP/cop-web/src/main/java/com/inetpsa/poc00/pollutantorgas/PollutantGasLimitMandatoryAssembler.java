/*
 * Creation : Apr 28, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslimitmandatory.PollutantGasLmtMandatory;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentationAssembler;

/**
 * The Class PollutantGasLimitMandatoryAssembler.
 */
public class PollutantGasLimitMandatoryAssembler extends BaseAssembler<PollutantGasLmtMandatory, PollutantLimitMandatoryRepresentation> {

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
	protected void doAssembleDtoFromAggregate(PollutantLimitMandatoryRepresentation representation, PollutantGasLmtMandatory entity) {
		representation.setEntityId(entity.getEntityId());
		representation.setValue(entity.getValue());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(PollutantGasLmtMandatory entity, PollutantLimitMandatoryRepresentation representation) {
		entity.setEntityId(representation.getEntityId());
		entity.setValue(representation.isValue());

	}

}

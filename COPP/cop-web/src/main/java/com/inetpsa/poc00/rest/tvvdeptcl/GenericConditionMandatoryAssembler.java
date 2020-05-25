/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.generictestconditionmandatory.GenericTestConditionMandatory;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentationAssembler;

/**
 * The Class GenericConditionMandatoryAssembler.
 */
public class GenericConditionMandatoryAssembler extends BaseAssembler<GenericTestConditionMandatory, GenericConditionMandatoryRepresentation> {
	
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
	protected void doAssembleDtoFromAggregate(GenericConditionMandatoryRepresentation representation, GenericTestConditionMandatory entity) {
		representation.setEntityId(entity.getEntityId());
		representation.setValue(entity.getValue());
		StatusNatureRepresentation snRrpresentation = new StatusNatureRepresentation();
		/*if (entity.getStatusNature() != null) {
			snRrpresentation.setStatusId(entity.getStatusNature().getStatus().getEntityId());
			snRrpresentation.setTestNatureId(entity.getStatusNature().getTestNature().getEntityId());
			StatusRepresentation statusDto = new StatusRepresentation();
			statusAssembler.assembleDtoFromAggregate(statusDto, entity.getStatusNature().getStatus());
			snRrpresentation.setStatus(statusDto);
			TestNatureRepresentation testNature = new TestNatureRepresentation();
			testNatureAssembler.assembleDtoFromAggregate(testNature, entity.getStatusNature().getTestNature());
			snRrpresentation.setTestNature(testNature);
			representation.setStatusNature(snRrpresentation);
		}*/

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(GenericTestConditionMandatory entity, GenericConditionMandatoryRepresentation representation) {
		entity.setEntityId(representation.getEntityId());
		entity.setValue(representation.isValue());
		/*entity.setStatusNature(new StatusNature(representation.getStatusNature().getEntityId(), representation.getStatusNature().getStatusId(), representation.getStatusNature().getTestNatureId()));
		if (representation.getStatusNature() != null) {
			// StatusNature statusNature = new StatusNature();

			Status status = statusFactory.createStatus();
			statusAssembler.mergeAggregateWithDto(status, representation.getStatusNature().getStatus());
			statusNature.setStatus(status);
			TestNature testNature = testNatureFactory.createTestNature();
			testNatureAssembler.mergeAggregateWithDto(testNature, representation.getStatusNature().getTestNature());
			statusNature.setTestNature(testNature);
			entity.setStatusNature(statusNature);
		}*/
	}

}

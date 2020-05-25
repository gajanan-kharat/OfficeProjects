package com.inetpsa.poc00.rest.status;

import java.util.HashSet;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentationAssembler;

/**
 * The Class StatusRepresentationAssembler.
 */
public class StatusRepresentationAssembler extends BaseAssembler<Status, StatusRepresentation> {

	/** The testnaturefactory. */
	@Inject
	TestNatureFactory testnaturefactory;

	/** The testnatureassembler. */
	@Inject
	TestNatureRepresentationAssembler testnatureassembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(StatusRepresentation targetDTO, Status sourceEntity) {
		targetDTO.setEntityId(sourceEntity.getEntityId());
		targetDTO.setGuiLabel(sourceEntity.getGuiLabel());
		targetDTO.setLabel(sourceEntity.getLabel());
		targetDTO.setTestrepresentationdata(new HashSet<TestNatureRepresentation>());
		if (sourceEntity.getTestNatures() != null && !sourceEntity.getTestNatures().isEmpty()) {
			for (TestNature testNature : sourceEntity.getTestNatures()) {
				TestNatureRepresentation testNatureRep = new TestNatureRepresentation();
				testnatureassembler.doAssembleDtoFromAggregate(testNatureRep, testNature);
				targetDTO.getTestrepresentationdata().add(testNatureRep);

			}
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Status targetEntity, StatusRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setGuiLabel(sourceDto.getGuiLabel());
		if (sourceDto.getTestrepresentationdata() != null && !sourceDto.getTestrepresentationdata().isEmpty()) {
			for (TestNatureRepresentation testNatureRep : sourceDto.getTestrepresentationdata()) {
				TestNature testNature = testnaturefactory.createTestNature();
				testnatureassembler.doMergeAggregateWithDto(testNature, testNatureRep);
				targetEntity.getTestNatures().add(testNature);

			}
		}

	}
}

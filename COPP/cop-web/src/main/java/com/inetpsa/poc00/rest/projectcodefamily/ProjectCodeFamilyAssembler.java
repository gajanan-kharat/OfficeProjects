package com.inetpsa.poc00.rest.projectcodefamily;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;

/**
 * The Class ProjectCodeFamilyAssembler.
 */
public class ProjectCodeFamilyAssembler extends BaseAssembler<ProjectCodeFamily, ProjectCodeFamilyRepresentation> {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(ProjectCodeFamilyRepresentation targetDto, ProjectCodeFamily sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setProjectCodeLabel(sourceEntity.getProjectCodeLabel());
		targetDto.setVehicleFamilyLabel(sourceEntity.getVehicleFamilyLabel());
		if (sourceEntity.getGenomeTvvRule() != null)
			targetDto.setRuleId(sourceEntity.getGenomeTvvRule().getEntityId());
		targetDto.setTvvRuleId(sourceEntity.getGenomeTvvRule());
		targetDto.setDisplayLabel(sourceEntity.getProjectCodeLabel() + "-" + sourceEntity.getVehicleFamilyLabel());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(ProjectCodeFamily targetEntity, ProjectCodeFamilyRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setProjectCodeLabel(sourceDto.getProjectCodeLabel());
		targetEntity.setVehicleFamilyLabel(sourceDto.getVehicleFamilyLabel());
		if (sourceDto.getFamilleTvvRuleId() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepo.load(sourceDto.getFamilleTvvRuleId()));
	}

}

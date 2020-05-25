package com.inetpsa.poc00.rest.engine;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository;
import com.inetpsa.poc00.domain.fueltype.FuelTypeRepository;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class EngineAssembler.
 */
public class EngineAssembler extends BaseAssembler<Engine, EngineRepresentation> {

	/** The tvv rule repository. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepository;

	/** The fit repository. */
	@Inject
	FuelInjctnTypeRepository fitRepository;

	/** The fuel type repository. */
	@Inject
	FuelTypeRepository fuelTypeRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(EngineRepresentation targetDto, Engine sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setEngineEntityID(sourceEntity.getEntityId());
		targetDto.setEngineLabel(sourceEntity.getEngineLabel());
		targetDto.setLabelDerogation(sourceEntity.getLabelDerogation());
		targetDto.setPowerKw(sourceEntity.getPowerKw());
		targetDto.setPowerCv(sourceEntity.getPowerCv());
		targetDto.setTorque(sourceEntity.getTorque());
		targetDto.setDisplayLabel(sourceEntity.getEngineLabel() + " " + sourceEntity.getPowerCv() + " " + sourceEntity.getTorque());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Engine targetEntity, EngineRepresentation sourceDto) {
		if (sourceDto.getEntityId() != null)
			targetEntity.setEntityId(sourceDto.getEntityId());
		else
			targetEntity.setEntityId(sourceDto.getEngineEntityID());
		targetEntity.setEngineLabel(sourceDto.getEngineLabel());
		targetEntity.setLabelDerogation(sourceDto.getLabelDerogation());
		targetEntity.setPowerKw(sourceDto.getPowerKw());
		targetEntity.setPowerCv(sourceDto.getPowerCv());
		targetEntity.setTorque(sourceDto.getTorque());
		if (sourceDto.getTvvRuleIdB0F() != null)
			targetEntity.setGenomeTvvRule(tvvRuleRepository.load(sourceDto.getTvvRuleIdB0F()));

		if (sourceDto.getTvvRuleIdDOC() != null)
			targetEntity.setGenomeTvvRuleDOC(tvvRuleRepository.load(sourceDto.getTvvRuleIdDOC()));
		if (sourceDto.getFuelInjectionID() != null)
			targetEntity.setFuelInjectionType(fitRepository.load(sourceDto.getFuelInjectionID()));
		if (sourceDto.getFuelTypeID() != null)
			targetEntity.setFuelType(fuelTypeRepository.load(sourceDto.getFuelTypeID()));

	}
}

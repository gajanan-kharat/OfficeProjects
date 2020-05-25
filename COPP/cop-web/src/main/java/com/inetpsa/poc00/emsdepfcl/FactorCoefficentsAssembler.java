/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.ArrayList;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.FluentAssembler;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;


/**
 * The Class FactorCoefficentsAssembler.
 */
public class FactorCoefficentsAssembler extends BaseAssembler<FactorCoefficents, FactorCoefficentsRepresentation> {
	
	/** The pollutant gas label factory. */
	@Inject
	private PollutantGasLabelFactory pollutantGasLabelFactory;
	
	/** The factor coefficents label factory. */
	@Inject
	private FactorCoeffLabelFactory factorCoefficentsLabelFactory;
	
	/** The pollutant gas label assembler. */
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;
	
	/** The fluent assembler. */
	@Inject
	private FluentAssembler fluentAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(FactorCoefficentsRepresentation targetDto, FactorCoefficents sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		FactorCoefficentsLabelRepresntation fcLabel = new FactorCoefficentsLabelRepresntation();
		if (sourceEntity.getFclabel() != null) {
			fcLabel.setEntityId(sourceEntity.getFclabel().getEntityId());
			fcLabel.setLabel(sourceEntity.getFclabel().getLabel());

			/*fcLabel.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());
			fcLabel.getFactorOrCoeffiecients().add(sourceEntity);*/

			targetDto.setFcLabel(fcLabel);
		}
		PollutantGasLabelRepresentation pgLabel = new PollutantGasLabelRepresentation();
		pollutantGasLabelAssembler.assembleDtoFromAggregate(pgLabel, sourceEntity.getPgLabel());

		targetDto.setPgLabel(pgLabel);
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(FactorCoefficents targetEntity, FactorCoefficentsRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setIsDeleted(sourceDto.getIsDeleted());
		FactorCoefficentsLabel fcLabel = factorCoefficentsLabelFactory.createFactorCoeffLabel();
		if (sourceDto.getFcLabel() != null) {
			fcLabel.setEntityId(sourceDto.getFcLabel().getEntityId());
			fcLabel.setLabel(sourceDto.getFcLabel().getLabel());

			fcLabel.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());
			fcLabel.getFactorOrCoeffiecients().add(targetEntity);
			targetEntity.setFclabel(fcLabel);
		}

		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pollutantGasLabelAssembler.mergeAggregateWithDto(pgLabel, sourceDto.getPgLabel());
		targetEntity.setPgLabel(pgLabel);
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());

	}
}

/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.ArrayList;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.FluentAssembler;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsLabelRepresntation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

public class TvvValuedFactorCoefficentAssembler extends BaseAssembler<TvvValuedFactorCoefficents, TvvValuedFactorCoefficentsRepresentation> {
	@Inject
	private PollutantGasLabelFactory pollutantGasLabelFactory;
	@Inject
	private FactorCoeffLabelFactory factorCoefficentsLabelFactory;
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;
	@Inject
	private FluentAssembler fluentAssembler;

	@Override
	public void doAssembleDtoFromAggregate(TvvValuedFactorCoefficentsRepresentation targetDto, TvvValuedFactorCoefficents sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		FactorCoefficentsLabelRepresntation fcLabel = new FactorCoefficentsLabelRepresntation();
		if (sourceEntity.getFclabel() != null) {
			fcLabel.setEntityId(sourceEntity.getFclabel().getEntityId());
			fcLabel.setLabel(sourceEntity.getFclabel().getLabel());

			targetDto.setFcLabel(fcLabel);
		}

		/*
		 * fcLabel.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());
		 * fcLabel.getFactorOrCoeffiecients().add(sourceEntity);
		 */

		PollutantGasLabelRepresentation pgLabel = new PollutantGasLabelRepresentation();
		pollutantGasLabelAssembler.assembleDtoFromAggregate(pgLabel, sourceEntity.getPgLabel());

		targetDto.setPgLabel(pgLabel);
		targetDto.setDefaultValue(sourceEntity.getDefaultValue());

	}

	@Override
	public void doMergeAggregateWithDto(TvvValuedFactorCoefficents targetEntity, TvvValuedFactorCoefficentsRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		FactorCoefficentsLabel fcLabel = factorCoefficentsLabelFactory.createFactorCoeffLabel();
		if (sourceDto.getFcLabel() != null) {
			fcLabel.setEntityId(sourceDto.getFcLabel().getEntityId());
			fcLabel.setLabel(sourceDto.getFcLabel().getLabel());
			fcLabel.setTvvValuedFactorOrCoeffiecients(new ArrayList<TvvValuedFactorCoefficents>());
			fcLabel.getTvvValuedFactorOrCoeffiecients().add(targetEntity);
			targetEntity.setFclabel(fcLabel);
		}

		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pollutantGasLabelAssembler.doMergeAggregateWithDto(pgLabel, sourceDto.getPgLabel());
		targetEntity.setPgLabel(pgLabel);
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());

	}
}

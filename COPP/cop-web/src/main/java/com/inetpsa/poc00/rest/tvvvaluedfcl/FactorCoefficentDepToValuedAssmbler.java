/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoeffLabelFactory;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsLabelRepresntation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

public class FactorCoefficentDepToValuedAssmbler extends BaseAssembler<FactorCoefficents, TvvValuedFactorCoefficentsRepresentation> {
	@Inject
	private PollutantGasLabelFactory pollutantGasLabelFactory;
	@Inject
	private FactorCoeffLabelFactory factorCoefficentsLabelFactory;
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;

	@Override
	public void doAssembleDtoFromAggregate(TvvValuedFactorCoefficentsRepresentation targetDto, FactorCoefficents sourceEntity) {
		targetDto.setEntityId(0);
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
	public void doMergeAggregateWithDto(FactorCoefficents targetEntity, TvvValuedFactorCoefficentsRepresentation sourceDto) {
		/*targetEntity.setEntityId(sourceDto.getEntityId());
		FactorCoefficentsLabel fcLabel = factorCoefficentsLabelFactory.createFactorCoeffLabel();
		fcLabel.setEntityId(sourceDto.getFcLabel().getEntityId());
		fcLabel.setLabel(sourceDto.getFcLabel().getLabel());

		fcLabel.setFactorOrCoeffiecients(new ArrayList<FactorCoefficents>());
		fcLabel.getFactorOrCoeffiecients().add(targetEntity);
		targetEntity.setFclabel(fcLabel);

		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pollutantGasLabelAssembler.mergeAggregateWithDto(pgLabel, sourceDto.getPgLabel());
		targetEntity.setPgLabel(pgLabel);
		targetEntity.setDefaultValue(sourceDto.getDefaultValue());*/

	}
}

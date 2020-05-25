/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitmandatory.PollutantGasLMFactory;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitAssembler;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitRepresentation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

public class TvvValuedPollutantLimitAssembler extends BaseAssembler<TvvValuedPollutantGasLimit, TvvValuedPollutantLimitRepresentation> {
	@Inject
	private PollutantGasLabelFactory pollutantGasLabelFactory;
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;
	@Inject
	private PollutantGasLMFactory poullutanLimitManadatoryFactory;
	@Inject
	PollutantGasLimitAssembler pollutantGasLimitAssembler;
	@Inject
	private PollutantGasLimitFactory pollutantGasLimitFactory;

	@Override
	public void doMergeAggregateWithDto(TvvValuedPollutantGasLimit pollutantGasLimit, TvvValuedPollutantLimitRepresentation pollutantGasLimitRepresentation) {
		pollutantGasLimit.setEntityId(pollutantGasLimitRepresentation.getEntityId());

		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pollutantGasLabelAssembler.mergeAggregateWithDto(pgLabel, pollutantGasLimitRepresentation.getPgLabel());
		pollutantGasLimit.setPgLabel(pgLabel);
		if (pollutantGasLimitRepresentation.getPgLimit() != null) {
			PollutantGasLimit pollutantLimit = pollutantGasLimitFactory.createPollutantGasLimit();
			pollutantGasLimitAssembler.doMergeAggregateWithDto(pollutantLimit, pollutantGasLimitRepresentation.getPgLimit());
			pollutantGasLimit.setPollutantLimit(pollutantLimit);
		}

		pollutantGasLimit.setMaxDValRule(pollutantGasLimitRepresentation.getMaxDValRule());
		pollutantGasLimit.setMinDValue(pollutantGasLimitRepresentation.getMinDValue());
		pollutantGasLimit.setMinDValRule(pollutantGasLimitRepresentation.getMinDValRule());
		pollutantGasLimit.setMaxDValue(pollutantGasLimitRepresentation.getMaxDValue());
		pollutantGasLimit.setValue(pollutantGasLimitRepresentation.getTvvDisplayValue());

	}

	@Override
	public void doAssembleDtoFromAggregate(TvvValuedPollutantLimitRepresentation pollutantGasLimitRepresentation, TvvValuedPollutantGasLimit pollutantGasLimit) {
		pollutantGasLimitRepresentation.setEntityId(pollutantGasLimit.getEntityId());
		PollutantGasLabelRepresentation pgLabel = new PollutantGasLabelRepresentation();
		pollutantGasLabelAssembler.assembleDtoFromAggregate(pgLabel, pollutantGasLimit.getPgLabel());
		pollutantGasLimitRepresentation.setPgLabel(pgLabel);
		pollutantGasLimitRepresentation.setMaxDValRule(pollutantGasLimit.getMaxDValRule());
		pollutantGasLimitRepresentation.setMinDValue(pollutantGasLimit.getMinDValue());
		pollutantGasLimitRepresentation.setMinDValRule(pollutantGasLimit.getMinDValRule());
		pollutantGasLimitRepresentation.setMaxDValue(pollutantGasLimit.getMaxDValue());
		if (pollutantGasLimit.getPollutantLimit() != null) {
			PollutantGasLimitRepresentation pollutantLimit = new PollutantGasLimitRepresentation();
			pollutantGasLimitAssembler.doAssembleDtoFromAggregate(pollutantLimit, pollutantGasLimit.getPollutantLimit());
			pollutantGasLimitRepresentation.setPgLimit(pollutantLimit);
		}

		if (pollutantGasLimit.getValue() == null || pollutantGasLimit.getValue().length() == 0)
			pollutantGasLimitRepresentation.setTvvDisplayValue(checkAndReplaceNull(pollutantGasLimit.getMinDValue()) + checkAndReplaceNull(pollutantGasLimit.getMinDValRule())+ checkAndReplaceNull(pollutantGasLimit.getMaxDValRule())+checkAndReplaceNull(pollutantGasLimit.getMaxDValue()));
		else
			pollutantGasLimitRepresentation.setTvvDisplayValue(pollutantGasLimit.getValue());

	}

	private String checkAndReplaceNull(Double value) {
		if (value == null)
			return "";
		return value.toString();
	}

	private String checkAndReplaceNull(String value) {
		if (value == null)
			return "";
		return value.toString();
	}

}

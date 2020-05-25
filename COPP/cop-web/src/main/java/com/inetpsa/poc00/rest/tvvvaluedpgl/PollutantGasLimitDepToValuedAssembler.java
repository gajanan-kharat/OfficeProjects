/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitAssembler;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitRepresentation;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;

/**
 * The Class PollutantGasLimitDepToValuedAssembler.
 */
public class PollutantGasLimitDepToValuedAssembler extends BaseAssembler<PollutantGasLimit, TvvValuedPollutantLimitRepresentation> {

	/** The pollutant gas label factory. */

	/** The pollutant gas label assembler. */
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;

	/** The pollutant gas limit assembler. */
	@Inject
	PollutantGasLimitAssembler pollutantGasLimitAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot,
	 *      java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasLimit pollutantGasLimit, TvvValuedPollutantLimitRepresentation pollutantGasLimitRepresentation) {
		// DO NOTHING
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object,
	 *      org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvValuedPollutantLimitRepresentation pollutantGasLimitRepresentation, PollutantGasLimit pollutantGasLimit) {
		pollutantGasLimitRepresentation.setEntityId(0);
		PollutantGasLabelRepresentation pgLabel = new PollutantGasLabelRepresentation();
		pollutantGasLabelAssembler.doAssembleDtoFromAggregate(pgLabel, pollutantGasLimit.getPgLabel());
		PollutantGasLimitRepresentation pollutantLimit = new PollutantGasLimitRepresentation();
		pollutantGasLimitAssembler.doAssembleDtoFromAggregate(pollutantLimit, pollutantGasLimit);
		pollutantGasLimitRepresentation.setPgLimit(pollutantLimit);

		pollutantGasLimitRepresentation.setPgLabel(pgLabel);
		pollutantGasLimitRepresentation.setMaxDValRule(pollutantGasLimit.getMaxDValRule());
		pollutantGasLimitRepresentation.setMinDValue(pollutantGasLimit.getMinDValue());
		pollutantGasLimitRepresentation.setMinDValRule(pollutantGasLimit.getMinDValRule());
		pollutantGasLimitRepresentation.setMaxDValue(pollutantGasLimit.getMaxDValue());
		pollutantGasLimitRepresentation.setTvvDisplayValue(checkAndReplaceNull(pollutantGasLimit.getMinDValue()) + checkAndReplaceNull(pollutantGasLimit.getMinDValRule())+ checkAndReplaceNull(pollutantGasLimit.getMaxDValRule())+checkAndReplaceNull(pollutantGasLimit.getMaxDValue()));

	}

	private String checkAndReplaceNull(Double value) {
		if (value == null)
			return "";
		return value.toString();
	}

	private String checkAndReplaceNull(String value) {
		if (value == null)
			return "";
		return value;
	}

}

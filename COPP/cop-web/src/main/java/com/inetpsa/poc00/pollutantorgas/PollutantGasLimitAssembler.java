/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimitmandatory.PollutantGasLMFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitmandatory.PollutantGasLmtMandatory;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelAssembler;
import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;


/**
 * The Class PollutantGasLimitAssembler.
 */
public class PollutantGasLimitAssembler extends BaseAssembler<PollutantGasLimit, PollutantGasLimitRepresentation> {
	
	/** The pollutant gas label factory. */
	@Inject
	private PollutantGasLabelFactory pollutantGasLabelFactory;
	
	/** The pollutant gas label assembler. */
	@Inject
	private PollutantGasLabelAssembler pollutantGasLabelAssembler;
	
	/** The poullutan limit manadatory factory. */
	@Inject
	private PollutantGasLMFactory poullutanLimitManadatoryFactory;
	
	/** The pollutant gas limit assembler. */
	@Inject
	PollutantGasLimitMandatoryAssembler pollutantGasLimitAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(PollutantGasLimit pollutantGasLimit, PollutantGasLimitRepresentation pollutantGasLimitRepresentation) {
		pollutantGasLimit.setEntityId(pollutantGasLimitRepresentation.getEntityId());

		PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
		pollutantGasLabelAssembler.doMergeAggregateWithDto(pgLabel, pollutantGasLimitRepresentation.getPgLabel());
		pollutantGasLimit.setPgLabel(pgLabel);
		pollutantGasLimit.setMaxDValRule(pollutantGasLimitRepresentation.getMaxDValRule());
		pollutantGasLimit.setMinDValue(pollutantGasLimitRepresentation.getMinDValue());
		pollutantGasLimit.setMinDValRule(pollutantGasLimitRepresentation.getMinDValRule());
		pollutantGasLimit.setMaxDValue(pollutantGasLimitRepresentation.getMaxDValue());

		if (pollutantGasLimitRepresentation.getPollutantLimitMandatory() != null && !pollutantGasLimitRepresentation.getPollutantLimitMandatory().isEmpty()) {
			List<PollutantLimitMandatoryRepresentation> dataItems = pollutantGasLimitRepresentation.getPollutantLimitMandatory();
			List<PollutantGasLmtMandatory> dataList = new ArrayList<PollutantGasLmtMandatory>();
			for (PollutantLimitMandatoryRepresentation dataItem : dataItems) {
				PollutantGasLmtMandatory data = poullutanLimitManadatoryFactory.createPollGasLMandt();
				pollutantGasLimitAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setPollutantGasLimit(pollutantGasLimit);
				dataList.add(data);

			}
			pollutantGasLimit.setPollutantGasLmtMandatory(dataList);
		}

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(PollutantGasLimitRepresentation pollutantGasLimitRepresentation, PollutantGasLimit pollutantGasLimit) {
		pollutantGasLimitRepresentation.setEntityId(pollutantGasLimit.getEntityId());
		PollutantGasLabelRepresentation pgLabel = new PollutantGasLabelRepresentation();
		pollutantGasLabelAssembler.assembleDtoFromAggregate(pgLabel, pollutantGasLimit.getPgLabel());
		pollutantGasLimitRepresentation.setPgLabel(pgLabel);
		pollutantGasLimitRepresentation.setMaxDValRule(pollutantGasLimit.getMaxDValRule());
		pollutantGasLimitRepresentation.setMinDValue(pollutantGasLimit.getMinDValue());
		pollutantGasLimitRepresentation.setMinDValRule(pollutantGasLimit.getMinDValRule());
		pollutantGasLimitRepresentation.setMaxDValue(pollutantGasLimit.getMaxDValue());

		if (pollutantGasLimit.getPollutantGasLmtMandatory() != null && !pollutantGasLimit.getPollutantGasLmtMandatory().isEmpty()) {
			List<PollutantGasLmtMandatory> dataItems = pollutantGasLimit.getPollutantGasLmtMandatory();
			List<PollutantLimitMandatoryRepresentation> dataList = new ArrayList<PollutantLimitMandatoryRepresentation>();
			for (PollutantGasLmtMandatory dataItem : dataItems) {
				PollutantLimitMandatoryRepresentation data = new PollutantLimitMandatoryRepresentation();
				pollutantGasLimitAssembler.assembleDtoFromAggregate(data, dataItem);
				data.setPollutantGasLimit(pollutantGasLimitRepresentation);
				dataList.add(data);

			}
			pollutantGasLimitRepresentation.setPollutantLimitMandatory(dataList);
		}

	}

}

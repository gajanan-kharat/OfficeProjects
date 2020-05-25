package com.inetpsa.poc00.rest.regulationgroupvaluedes;

import java.util.ArrayList;
import java.util.List;

import org.seedstack.business.assembler.BaseAssembler;

import com.google.inject.Inject;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.rgvaluedtestcondition.RegGrpValdTestCondition;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValdTestConditionRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValuedTCAssembler;


/**
 * The Class RGValuedESDependantAssembler.
 */
public class RGValuedESDependantAssembler extends BaseAssembler<RGValuedESDependentTCL, RGValuedESDependentTCLRepresentation> {

	/** The reg grp valued tc assembler. */
	@Inject
	RegGrpValuedTCAssembler regGrpValuedTcAssembler;

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(RGValuedESDependentTCLRepresentation targetDto, RGValuedESDependentTCL sourceEntity) {
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setDescription(sourceEntity.getDescription());
		targetDto.setVersion(sourceEntity.getVersion());
		List<RegGrpValdTestCondition> regValuedTcList = sourceEntity.getRgValuedGenericTestCondition();
		List<RegGrpValdTestConditionRepresentation> regGrpValuedTCrpresentList = new ArrayList<RegGrpValdTestConditionRepresentation>();
		if (regValuedTcList != null && !regValuedTcList.isEmpty()) {
			for (RegGrpValdTestCondition regValuedTcEntity : regValuedTcList) {
				RegGrpValdTestConditionRepresentation regGrpTCrepresentation = new RegGrpValdTestConditionRepresentation();
				regGrpValuedTcAssembler.doAssembleDtoFromAggregate(regGrpTCrepresentation, regValuedTcEntity);
				regGrpTCrepresentation.setRgValuedEsDepTCLrepresent(targetDto);
				regGrpValuedTCrpresentList.add(regGrpTCrepresentation);
			}
		}

		targetDto.setRgValuedGenericTestConditionrepresent(regGrpValuedTCrpresentList);

	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(RGValuedESDependentTCL targetEntity, RGValuedESDependentTCLRepresentation sourceDto) {
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setDescription(sourceDto.getDescription());
		targetEntity.setVersion(sourceDto.getVersion());
		List<RegGrpValdTestCondition> regGrpValuedTCList = new ArrayList<RegGrpValdTestCondition>();
		for (RegGrpValdTestConditionRepresentation regValuedTCRepresentation : sourceDto.getRgValuedGenericTestConditionrepresent()) {

			RegGrpValdTestCondition regGrpValuedTc = new RegGrpValdTestCondition();
			regGrpValuedTcAssembler.doMergeAggregateWithDto(regGrpValuedTc, regValuedTCRepresentation);
			regGrpValuedTc.setRgValuedEsDepTCL(targetEntity);
			regGrpValuedTCList.add(regGrpValuedTc);
		}
		targetEntity.setRgValuedGenericTestCondition(regGrpValuedTCList);

	}

}

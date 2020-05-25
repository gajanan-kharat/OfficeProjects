package com.inetpsa.poc00.rest.wtlp;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;


/**
 * The Class WLTPVLowHighDataAssembler.
 */
public class WLTPVLowHighDataAssembler extends BaseAssembler<WLTPVLowHighData, WLTPVLowHighDataRepresentation>
{

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(
			WLTPVLowHighDataRepresentation targetDto, WLTPVLowHighData sourceEntity) {
		
		targetDto.setEntityId(sourceEntity.getEntityId());
		targetDto.setLabel(sourceEntity.getLabel());
		targetDto.setVersion(sourceEntity.getVersion());
		targetDto.setCrrvHigh(sourceEntity.getCrrvHigh());
		targetDto.setCrrvInd(sourceEntity.getCrrvInd());
		targetDto.setCrrvLow(sourceEntity.getCrrvLow());
		targetDto.setF0vHigh(sourceEntity.getF0vHigh());
		targetDto.setF0vInd(sourceEntity.getF0vInd());
		targetDto.setF0vLow(sourceEntity.getF0vLow());
		targetDto.setF1vHigh(sourceEntity.getF1vHigh());
		targetDto.setF1vInd(sourceEntity.getF1vHigh());
		targetDto.setF1vLow(sourceEntity.getF1vLow());
		targetDto.setF2vHigh(sourceEntity.getF2vHigh());
		targetDto.setF2vInd(sourceEntity.getF2vInd());
		targetDto.setF2vLow(sourceEntity.getF2vLow());
		targetDto.setScxVHigh(sourceEntity.getScxVHigh());
		targetDto.setScxVInd(sourceEntity.getScxVInd());
		targetDto.setScxVLow(sourceEntity.getScxVLow());
		targetDto.setMasseVhigh(sourceEntity.getMasseVhigh());
		targetDto.setMassVInd(sourceEntity.getMassVInd());
		targetDto.setMassVLow(sourceEntity.getMassVLow());
		
		
		
	}

	/* (non-Javadoc)
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(WLTPVLowHighData targetEntity,
			WLTPVLowHighDataRepresentation sourceDto) {
		
		targetEntity.setEntityId(sourceDto.getEntityId());
		targetEntity.setLabel(sourceDto.getLabel());
		targetEntity.setVersion(sourceDto.getVersion());
		targetEntity.setCrrvHigh(sourceDto.getCrrvHigh());
		targetEntity.setCrrvInd(sourceDto.getCrrvInd());
		targetEntity.setCrrvLow(sourceDto.getCrrvLow());
		targetEntity.setF0vHigh(sourceDto.getF0vHigh());
		targetEntity.setF0vInd(sourceDto.getF0vInd());
		targetEntity.setF0vLow(sourceDto.getF0vLow());
		targetEntity.setF1vHigh(sourceDto.getF1vHigh());
		targetEntity.setF1vInd(sourceDto.getF1vHigh());
		targetEntity.setF1vLow(sourceDto.getF1vLow());
		targetEntity.setF2vHigh(sourceDto.getF2vHigh());
		targetEntity.setF2vInd(sourceDto.getF2vInd());
		targetEntity.setF2vLow(sourceDto.getF2vLow());
		targetEntity.setScxVHigh(sourceDto.getScxVHigh());
		targetEntity.setScxVInd(sourceDto.getScxVInd());
		targetEntity.setScxVLow(sourceDto.getScxVLow());
		targetEntity.setMasseVhigh(sourceDto.getMasseVhigh());
		targetEntity.setMassVInd(sourceDto.getMassVInd());
		targetEntity.setMassVLow(sourceDto.getMassVLow());
	}

}

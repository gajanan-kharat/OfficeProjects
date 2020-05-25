package com.inetpsa.poc00.application.pollutantgaslimitlist;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Interface PollutantGasLimitListService.
 */
@Service
public interface PollutantGasLimitListService {

	/**
	 * Update ems pgl list.
	 * 
	 * @param pglList the pgl list
	 * @param newVersionObject the new version object
	 * @return the pollutant gas limit list
	 */
	PollutantGasLimitList updateEsDepPgl(PollutantGasLimitList pglList, EmissionStandard newVersionObject);

	/**
	 * Creates the es dep pgl.
	 * 
	 * @param newVersionObject the new version object
	 * @param emsDepTDL the ems dep tdl
	 * @return the pollutant gas limit list
	 */
	PollutantGasLimitList createEsDepPgl(EmissionStandard newVersionObject, PollutantGasLimitList emsDepTDL);
	
	/**
	 * Delete ems dep pgl.
	 *
	 * @param entityId the entity id
	 */
	void deleteEmsDepPGL(long entityId);

	/**
	 * Gets the emission standard object.
	 * 
	 * @param oldEsEntityId the old es entity id
	 * @param changeEmsVersion the change ems version
	 * @param emissionStandard the emission standard
	 * @return the emission standard object
	 */
	EmissionStandard getEmissionStandardObject(long oldEsEntityId, boolean changeEmsVersion, EmissionStandard emissionStandard);

	/**
	 * Copy single pgl.
	 * 
	 * @param pollutantGasList the pollutant gas list
	 * @param newVersionObject the new version object
	 * @return the pollutant gas limit list
	 */
	PollutantGasLimitList copySinglePGL(PollutantGasLimitList pollutantGasList, EmissionStandard newVersionObject);

}

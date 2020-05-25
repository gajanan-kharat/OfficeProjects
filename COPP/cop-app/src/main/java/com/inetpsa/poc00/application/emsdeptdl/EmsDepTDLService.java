package com.inetpsa.poc00.application.emsdeptdl;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;

/**
 * The Interface EmsDepTDLService.
 */
@Service
public interface EmsDepTDLService {

	/**
	 * Update ems dep tdl.
	 * 
	 * @param emsDepTDL the ems dep tdl
	 * @param newVersionObject the new version object
	 * @return the emission std dep tdl
	 */
	EmissionStdDepTDL updateEmsDepTDL(EmissionStdDepTDL emsDepTDL, EmissionStandard newVersionObject);

	/**
	 * Creates the ems dep tdl.
	 * 
	 * @param newVersionObject the new version object
	 * @param emsDepTDL the ems dep tdl
	 * @return the emission std dep tdl
	 */
	EmissionStdDepTDL createEmsDepTDL(EmissionStandard newVersionObject, EmissionStdDepTDL emsDepTDL);

	/**
	 * Delete ems dep tdl.
	 * 
	 * @param entityId the entity id
	 */
	void deleteEmsDepTDL(Long entityId);
	
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
	 * Copy single tdl.
	 *
	 * @param emsDepTDL the ems dep tdl
	 * @param newVersionObject the new version object
	 * @return the emission std dep tdl
	 */
	EmissionStdDepTDL copySingleTDL(EmissionStdDepTDL emsDepTDL, EmissionStandard newVersionObject);

}

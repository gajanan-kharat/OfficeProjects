package com.inetpsa.poc00.application.emsdeptcl;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;

/**
 * The Interface EmsDepTCLService.
 */
@Service
public interface EmsDepTCLService {
	
	/**
	 * Creates the ems dep tcl.
	 *
	 * @param newVersionObject the new version object
	 * @param emsDepTCL the ems dep tcl
	 * @return the emission std dep tcl
	 */
	EmissionStdDepTCL createEmsDepTCL(EmissionStandard newVersionObject, EmissionStdDepTCL emsDepTCL);
	
	/**
	 * Update ems dep tcl.
	 *
	 * @param emsDepTDL the ems dep tdl
	 * @param newVersionObject the new version object
	 * @return the emission std dep tdl
	 */
	EmissionStdDepTCL updateEmsDepTCL(EmissionStdDepTCL emsDepTDL, EmissionStandard newVersionObject);
	
	
	/**
	 * Delete ems dep tcl.
	 *
	 * @param entityId the entity id
	 */
	void deleteEmsDepTCL(Long entityId);
	
	/**
	 * Gets the emission standard object.
	 *
	 * @param oldEmissionStandard the old emission standard
	 * @param changeEmsVersion the change ems version
	 * @param emissionStandard the emission standard
	 * @return the emission standard object
	 */
	EmissionStandard getEmissionStandardObject(long oldEmissionStandard, boolean changeEmsVersion, EmissionStandard emissionStandard);
	
	/**
	 * Copy single tcl.
	 *
	 * @param emsDepTCL the ems dep tcl
	 * @param newVersionObject the new version object
	 * @return the emission std dep tcl
	 */
	EmissionStdDepTCL copySingleTCL(EmissionStdDepTCL emsDepTCL, EmissionStandard newVersionObject);

}

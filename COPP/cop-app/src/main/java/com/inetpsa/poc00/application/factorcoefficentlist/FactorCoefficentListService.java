/**
 * 
 */
package com.inetpsa.poc00.application.factorcoefficentlist;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Interface FactorCoefficentListService.
 * 
 * @author ankurp
 */
@Service
public interface FactorCoefficentListService {

	/**
	 * Creates the ems dep tcl.
	 * 
	 * @param newVersionObject the new version object
	 * @param emsDepFCL the ems dep fcl
	 * @return the factor coefficent list
	 */
	FactorCoefficentList createEmsDepFCL(EmissionStandard newVersionObject, FactorCoefficentList emsDepFCL);

	/**
	 * Update ems dep tcl.
	 * 
	 * @param emsDepFCL the ems dep fcl
	 * @param newVersionObject the new version object
	 * @return the factor coefficent list
	 */
	FactorCoefficentList updateEmsDepFCL(FactorCoefficentList emsDepFCL, EmissionStandard newVersionObject);

	/**
	 * Delete ems dep tcl.
	 * 
	 * @param entityId the entity id
	 */
	void deleteEmsDepFCL(Long entityId);

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
	 * Copy single fcl.
	 * 
	 * @param factorCoefficientList the factor coefficient list
	 * @param newVersionObject the new version object
	 * @return the factor coefficent list
	 */
	FactorCoefficentList copySingleFCL(FactorCoefficentList factorCoefficientList, EmissionStandard newVersionObject);

	/**
	 * Gets the all used pg labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the all used pg labels
	 */
	List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId);
}

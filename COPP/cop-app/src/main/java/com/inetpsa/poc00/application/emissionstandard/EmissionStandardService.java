/*
 * Creation : Dec 28, 2016
 */
package com.inetpsa.poc00.application.emissionstandard;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Interface EmissionStandardService.
 */
@Service
public interface EmissionStandardService {

	/**
	 * Creates the emission standard.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	public EmissionStandard createEmissionStandard(EmissionStandard emissionStandard);

	/**
	 * Change emission standard version.
	 * 
	 * @param esEntityId the es entity id
	 * @return the emission standard
	 */
	EmissionStandard changeEmissionStandardVersion(Long esEntityId);

	/**
	 * Copy emission standard.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	EmissionStandard copyEmissionStandard(EmissionStandard emissionStandard);

	/**
	 * Save emission standard.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	EmissionStandard saveEmissionStandard(EmissionStandard emissionStandard);

	/**
	 * Delete emission standard.
	 * 
	 * @param emsId the ems id
	 * @return true, if successful
	 */
	boolean deleteEmissionStandard(Long emsId);

	/**
	 * Copy es dep tdl.
	 * 
	 * @param esEntityId the es entity id
	 * @param newObj the new obj
	 * @return the list
	 */
	List<EmissionStdDepTDL> copyESDepTDL(Long esEntityId, EmissionStandard newObj);

	/**
	 * Copy es dep tcl.
	 * 
	 * @param esEntityId the es entity id
	 * @param newObj the new obj
	 * @return the list
	 */
	List<EmissionStdDepTCL> copyESDepTCL(Long esEntityId, EmissionStandard newObj);

	/**
	 * Copy es dep pgl.
	 * 
	 * @param esEntityId the es entity id
	 * @param newObj the new obj
	 * @return the list
	 */
	List<PollutantGasLimitList> copyESDepPGL(Long esEntityId, EmissionStandard newObj);

	/**
	 * Copy es dep fcl.
	 * 
	 * @param esEntityId the es entity id
	 * @param newObj the new obj
	 * @return the list
	 */
	List<FactorCoefficentList> copyESDepFCL(Long esEntityId, EmissionStandard newObj);

	/**
	 * Creates the new ems version.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the emission standard
	 */
	EmissionStandard createNewEMSVersion(EmissionStandard emissionStandard);
}

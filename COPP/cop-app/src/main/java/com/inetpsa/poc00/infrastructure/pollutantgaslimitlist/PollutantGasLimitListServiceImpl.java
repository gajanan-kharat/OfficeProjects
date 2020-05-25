package com.inetpsa.poc00.infrastructure.pollutantgaslimitlist;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emissionstandard.EmissionStandardService;
import com.inetpsa.poc00.application.pollutantgaslimitlist.PollutantGasLimitListService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabelFactory;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListFactory;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;

/**
 * The Class PollutantGasLimitListServiceImpl.
 */
public class PollutantGasLimitListServiceImpl implements PollutantGasLimitListService {

	/** The emission standard resource. */
	@Inject
	private EmissionStandardService emsService;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The pollutant gas list repository. */
	@Inject
	PollutantGasLimitListRepository pollutantGasListRepository;

	/** The pollutant gas label factory. */
	@Inject
	PollutantGasLabelFactory pollutantGasLabelFactory;

	/** The pollutant gas list factory. */
	@Inject
	private PollutantGasLimitListFactory pollutantGasListFactory;

	/** The pollutant gas factory. */
	@Inject
	private PollutantGasLimitFactory pollutantGasFactory;

	/** The history service. */
	@Inject
	TraceabilityService historyService;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.pollutantgaslimitlist.PollutantGasLimitListService#createEsDepPgl(com.inetpsa.poc00.domain.es.EmissionStandard, com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasLimitList createEsDepPgl(EmissionStandard newVersionObject, PollutantGasLimitList pollutantGasList) {

		pollutantGasList.setVersion("1.0");

		pollutantGasList.setEmissionStandard(newVersionObject);

		pollutantGasListRepository.savePollutantGasLimitList(pollutantGasList);

		historyService.historyForSave(pollutantGasList, ConstantsApp.EMS_DEPENDENT_PGL_SCREEN);

		return pollutantGasList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.pollutantgaslimitlist.PollutantGasLimitListService#updateEsDepPgl(com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList, com.inetpsa.poc00.domain.es.EmissionStandard)
	 */

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasLimitList updateEsDepPgl(PollutantGasLimitList pollutantGasList, EmissionStandard newVersionObject) {

		try {

			if (pollutantGasListRepository.getMaxVersionForLabel(pollutantGasList.getLabel()) == null && pollutantGasList.getEntityId() == null) {
				createEsDepPgl(newVersionObject, pollutantGasList);
				return pollutantGasList;
			}

			Double majoMinorVersion = pollutantGasListRepository.getMaxVersionForLabel(pollutantGasList.getLabel()) + 1;

			PollutantGasLimitList newpGList = pollutantGasListFactory.createPollutantGasLimitList(pollutantGasList.getLabel(), pollutantGasList.getDescription(), majoMinorVersion.toString());

			newpGList.setPollutantGasLimit(new ArrayList<PollutantGasLimit>());

			if (pollutantGasList.getPollutantGasLimit() != null) {
				for (PollutantGasLimit data : pollutantGasList.getPollutantGasLimit()) {
					if (!"TRUE".equalsIgnoreCase(data.getIsDeleted())) {

						PollutantGasLabel pgLabel = pollutantGasLabelFactory.createPollutantGasLabel();
						pgLabel.setEntityId(data.getPgLabel().getEntityId());
						pgLabel.setLabel(data.getPgLabel().getLabel());
						PollutantGasLimit dataToSave = pollutantGasFactory.createPollutantGasLimits(pgLabel, data.getMaxDValRule(), data.getMaxDValue(), data.getMinDValRule(), data.getMinDValue());
						dataToSave.setPollutantGasLimitList(newpGList);
						newpGList.getPollutantGasLimit().add(dataToSave);
					}
				}
			}

			/* Set all fields in new object */
			newpGList.setCategories(pollutantGasList.getCategories());
			newpGList.setFuelInjectionTypes(pollutantGasList.getFuelInjectionTypes());
			newpGList.setFuels(pollutantGasList.getFuels());
			newpGList.setVehicleTechnologySet(pollutantGasList.getVehicleTechnologySet());
			newpGList.setClasses(pollutantGasList.getClasses());

			/* Set Emission standard in new PGList object and vice versa */
			newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);
			newVersionObject.getPgLists().add(newpGList);

			newpGList.setEmissionStandard(newVersionObject);
			newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);

			historyService.historyForSave(newpGList, ConstantsApp.EMS_DEPENDENT_PGL_SCREEN);
			return newpGList;

		} catch (Exception pglException) {

			throw pglException;
		}

	}

	/**
	 * This method returns appropriate emissionStandard object.If new version is to created it will create new Emission
	 * Standard object and return otherwise it will return old one
	 * 
	 * @param oldEsEntityId the old es entity id
	 * @param changeEmsVersion the change ems version
	 * @param emissionStandard the emission standard
	 * @return the emission standard object
	 */
	@Override
	public EmissionStandard getEmissionStandardObject(long oldEsEntityId, boolean changeEmsVersion, EmissionStandard emissionStandard) {

		EmissionStandard newVersionObject;

		if (changeEmsVersion) {

			newVersionObject = emsService.createNewEMSVersion(emissionStandard);
			newVersionObject.setPgLists(new ArrayList<PollutantGasLimitList>());

			newVersionObject = emissionStandardRepository.saveEmissionStandard(newVersionObject);

			/****** START : COPY OTHER LIST in New Emission Standard Version ******/
			List<EmissionStdDepTDL> tdlSet = emsService.copyESDepTDL(oldEsEntityId, newVersionObject);
			newVersionObject.setEmissionStdDepTDLists(tdlSet);

			List<EmissionStdDepTCL> tclSet = emsService.copyESDepTCL(oldEsEntityId, newVersionObject);
			newVersionObject.setEmissionStdDepTCLists(tclSet);

			List<FactorCoefficentList> fclSet = emsService.copyESDepFCL(oldEsEntityId, newVersionObject);
			newVersionObject.setFclists(fclSet);
			/****** END : COPY OTHER LIST in New Emission Standard Version ******/

		} else {

			newVersionObject = emissionStandardRepository.load(oldEsEntityId);
			newVersionObject.setPgLists(new ArrayList<PollutantGasLimitList>());

		}

		return newVersionObject;
	}

	/**
	 * This method is used to copy a Single PGL (create new PGL and associate it to new version of EMS).
	 * 
	 * @param pollutantGasList the pollutant gas list
	 * @param newVersionObject the new version object
	 * @return the pollutant gas limit list
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public PollutantGasLimitList copySinglePGL(PollutantGasLimitList pollutantGasList, EmissionStandard newVersionObject) {

		PollutantGasLimitList newpGList = pollutantGasListFactory.createPollutantGasLimitList(pollutantGasList.getLabel(), pollutantGasList.getDescription(), pollutantGasList.getVersion());
		newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);
		newpGList.setPollutantGasLimit(new ArrayList<PollutantGasLimit>());

		if (pollutantGasList.getPollutantGasLimit() != null)
			for (PollutantGasLimit data : pollutantGasList.getPollutantGasLimit()) {

				PollutantGasLimit dataToSave = pollutantGasFactory.createPollutantGasLimits(data.getPgLabel(), data.getMaxDValRule(), data.getMaxDValue(), data.getMinDValRule(), data.getMinDValue());
				dataToSave.setPollutantGasLimitList(newpGList);

				newpGList.getPollutantGasLimit().add(dataToSave);

			}

		newpGList.setCategories(pollutantGasList.getCategories());
		newpGList.setFuelInjectionTypes(pollutantGasList.getFuelInjectionTypes());
		newpGList.setFuels(pollutantGasList.getFuels());
		newpGList.setVehicleTechnologySet(pollutantGasList.getVehicleTechnologySet());
		newpGList.setClasses(pollutantGasList.getClasses());

		newpGList.setEmissionStandard(newVersionObject);
		newpGList = pollutantGasListRepository.savePollutantGasLimitList(newpGList);
		return newpGList;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.pollutantgaslimitlist.PollutantGasLimitListService#deleteEmsDepPGL(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteEmsDepPGL(long entityId) {

		pollutantGasListRepository.deletePollutantGasList(entityId);

		historyService.historyForDelete(entityId, ConstantsApp.EMS_DEPENDENT_PGL_SCREEN);
	}

}

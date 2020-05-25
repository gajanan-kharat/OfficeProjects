package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;
import com.inetpsa.poc00.mandatorydata.MandatoryDataFinder;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * The Class JpaMandatoryDataFinder.
 */
public class JpaMandatoryDataFinder implements MandatoryDataFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The generic tech data finder. */
	@Inject
	private GenericTechnicalDataFinder genericTechDataFinder;

	/** The generic test condition finder. */
	@Inject
	private GenericTestConditionFinder genericTestConditionFinder;

	/** The pollutant gas limit finder. */
	@Inject
	private PollutantGasLimitsFinder pollutantGasLimitFinder;

	/** The generic tech data repo. */
	@Inject
	private GenericTechDataRepository genericTechDataRepo;

	/** The generic tc repo. */
	@Inject
	private GenericTestConditionRepository genericTCRepo;

	/** The tvv dep tdl finder. */
	@Inject
	private TvvDepTDLFinder tvvDepTDLFinder;

	/** The tvv dep tcl finder. */
	@Inject
	private TvvDepTCLFinder tvvDepTCLFinder;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(JpaMandatoryDataFinder.class);

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.mandatorydata.MandatoryDataFinder#getESTestStatusCombList()
	 */
	/*
	 * @see com.inetpsa.poc00.infrastructure.jpa.MandatoryDataFinder#getTestStatusCombList()
	 */
	@Override
	public List<MandatoryDataRepresentation> getESTestStatusCombList(EmissionStandardRepresentation emissionStandard) {

		List<MandatoryDataRepresentation> combinedList = new ArrayList<MandatoryDataRepresentation>();
		List<StatusNatureRepresentation> newStatNtrList = new ArrayList<StatusNatureRepresentation>();

		newStatNtrList = getStatuNature();

		combinedList = getAllESList(emissionStandard);

		for (MandatoryDataRepresentation mandaData : combinedList) {

			String values = mandaData.getMandatoryIdValues();

			if (values == null) {
				values = "";
			}

			mandaData.setMandatoryFlagList(getMandatoryConfig(newStatNtrList, values));
		}

		return combinedList;

	}

	/**
	 * Gets the statu nature.
	 * 
	 * @return the statu nature
	 */
	private List<StatusNatureRepresentation> getStatuNature() {

		// Fetching Mandatory Status and Nature
		Query query = entityManager.createQuery("SELECT new " + StatusNatureRepresentation.class.getName() + "(sn.entityId,sn.status.label,sn.testNature.type)" + " FROM  StatusNature sn ORDER BY ID ASC");

		List<StatusNatureRepresentation> newStatNtrList = query.getResultList();

		return newStatNtrList;
	}

	/**
	 * Gets the all es list.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the all es list
	 */
	private List<MandatoryDataRepresentation> getAllESList(EmissionStandardRepresentation emissionStandard) {

		List<MandatoryDataRepresentation> combinedList = new ArrayList<MandatoryDataRepresentation>();

		List<MandatoryDataRepresentation> allGenericTechData = genericTechDataFinder.getAllGenericTechnicalData(emissionStandard.getVersion(), emissionStandard.getEntityId());
		logger.info("Generic Tech Data Size : {}", allGenericTechData.size());

		List<MandatoryDataRepresentation> allGenericTestCondition = genericTestConditionFinder.getAllGenericTestConditionData(emissionStandard.getVersion(), emissionStandard.getEntityId());
		logger.info("Generic Test Condition Data Size : {}", allGenericTestCondition.size());

		List<MandatoryDataRepresentation> allPollutantGasLimit = pollutantGasLimitFinder.getAllPollutantGasLimit(emissionStandard.getVersion(), emissionStandard.getEntityId());
		logger.info("Pollutant Gas Limit Tech Data Size : {}", allPollutantGasLimit.size());

		// Combining All Lists
		combinedList.addAll(allGenericTechData);
		combinedList.addAll(allGenericTestCondition);
		combinedList.addAll(allPollutantGasLimit);

		return combinedList;
	}

	/**
	 * Gets the all tvv list.
	 * 
	 * @param tvvStructure the tvv structure
	 * @return the all tvv list
	 */
	private List<MandatoryDataRepresentation> getAllTvvList(TvvStructureRepresentation tvvStructure) {

		List<MandatoryDataRepresentation> combinedList = new ArrayList<MandatoryDataRepresentation>();

		if (tvvStructure != null && Constants.MANDATORY_TVVSTURCTURE_TDLIST_ID.equalsIgnoreCase(tvvStructure.getListType())) {

			List<MandatoryDataRepresentation> allGenericTechData = genericTechDataFinder.getAllTvvGenericTechnicalData(tvvStructure.getEntityId());
			combinedList.addAll(allGenericTechData);
			logger.info("Tvv Generic Tech Data Size : {}", allGenericTechData.size());

		} else if (tvvStructure != null && Constants.MANDATORY_TVVSTURCTURE_TCLIST_ID.equalsIgnoreCase(tvvStructure.getListType())) {

			List<MandatoryDataRepresentation> allGenericTestCondition = genericTestConditionFinder.getAllTvvGenericTestConditionData(tvvStructure.getEntityId());
			logger.info("Tvv Generic Test Condition Size : {}", allGenericTestCondition.size());
			combinedList.addAll(allGenericTestCondition);
		}

		return combinedList;
	}

	/**
	 * Gets the mandatory config.
	 * 
	 * @param statNatureComb the stat nature comb
	 * @param existingValue the existing value
	 * @return the mandatory configMandatoryDataRepresentationMandatoryDataRepresentation
	 */
	private List<StatusNatureRepresentation> getMandatoryConfig(List<StatusNatureRepresentation> statNatureComb, String existingValue) {

		List<StatusNatureRepresentation> mandatoryConfig = new ArrayList<StatusNatureRepresentation>();
		List<String> existingCom = Arrays.asList(existingValue.split(","));

		for (StatusNatureRepresentation statusNatureRepresentation2 : statNatureComb) {

			StatusNatureRepresentation temp = new StatusNatureRepresentation();
			temp.setEntityId(statusNatureRepresentation2.getEntityId());
			temp.setStatusLabel(statusNatureRepresentation2.getStatusLabel());
			temp.setTestNatureLabel(statusNatureRepresentation2.getTestNatureLabel());

			if (existingCom.contains(String.valueOf(statusNatureRepresentation2.getEntityId()))) {
				temp.setFlag(Boolean.TRUE);
			}
			mandatoryConfig.add(temp);
		}

		return mandatoryConfig;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.mandatorydata.MandatoryDataFinder#getTvvTestStatusCombList()
	 */
	@Override
	public List<MandatoryDataRepresentation> getTvvTestStatusCombList(TvvStructureRepresentation tvvStructure) {

		List<MandatoryDataRepresentation> combinedList = new ArrayList<MandatoryDataRepresentation>();
		List<StatusNatureRepresentation> newStatNtrList = new ArrayList<StatusNatureRepresentation>();

		newStatNtrList = getStatuNature();

		// Fetching GTD and GTC for TVV Structure
		combinedList = getAllTvvList(tvvStructure);

		for (MandatoryDataRepresentation mandaData : combinedList) {

			String values = mandaData.getMandatoryIdValues();

			if (values == null) {
				values = "";
			}

			mandaData.setMandatoryFlagList(getMandatoryConfig(newStatNtrList, values));

		}

		return combinedList;
	}

	@Override
	public Long getStatusNatureId(Long statusId, Long testId) {
		Query query = entityManager.createQuery("SELECT sn.entityId FROM  StatusNature sn where sn.status.entityId= :statusId and sn.testNature.entityId=:testId");
		query.setParameter("statusId", statusId);
		query.setParameter("testId", testId);
		return (Long) query.getSingleResult();
	}
}
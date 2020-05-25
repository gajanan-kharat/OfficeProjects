package com.inetpsa.poc00.mandatorydata;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

/**
 * The Class MandatoryDataResource.
 */
@Path("/MandatoryDataReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class MandatoryDataResource {

	/** The mandatory data finder. */
	@Inject
	MandatoryDataFinder mandatoryDataFinder;

	/** The generic tech data repo. */
	@Inject
	private GenericTechDataRepository genericTechDataRepo;

	/** The generic tc repo. */
	@Inject
	private GenericTestConditionRepository genericTCRepo;

	/** The pgl repo. */
	@Inject
	private PollutantGasLimitRepository pglRepo;

	@Column(name = "MANDATORY_STRING")
	private String mandatoryIdValues;

	@Inject
	private TvvDepTDLFinder tvvDepTDLFinder;

	@Inject
	private TvvDepTCLFinder tvvDepTCLFinder;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(MandatoryDataResource.class);

	/**
	 * Gets the ES mandatory data.
	 * 
	 * @return the ES mandatory data
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ESMandatoryData")
	public Response getESMandatoryData(EmissionStandardRepresentation emissionStandard) {
		if (logger != null)
			logger.info("Emission Standard Label : {} ,  Entity Id : {}", emissionStandard.getEsLabel(), emissionStandard.getEntityId());

		List<MandatoryDataRepresentation> mandatoryData = mandatoryDataFinder.getESTestStatusCombList(emissionStandard);

		return Response.ok(mandatoryData).build();

	}

	/**
	 * Gets the tvv mandatory data.
	 * 
	 * @return the tvv mandatory data
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvMandatoryData")
	public Response getTvvMandatoryData(TvvStructureRepresentation tvvStructure) {

		List<MandatoryDataRepresentation> mandatoryData = mandatoryDataFinder.getTvvTestStatusCombList(tvvStructure);

		return Response.ok(mandatoryData).build();
	}

	/**
	 * Gets the tvv mandatory data.
	 * 
	 * @return the tvv mandatory data
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/AllTvvStructure")
	public Response getAllTvvStructureList() {

		List<TvvStructureRepresentation> combinedList = new ArrayList<TvvStructureRepresentation>();

		List<TvvStructureRepresentation> tvvDepTDLData = tvvDepTDLFinder.getAllTvvDependentTDLLists();
		logger.info("Tvv Generic Tech Data Size : {}", tvvDepTDLData.size());

		List<TvvStructureRepresentation> tvvDepTCLData = tvvDepTCLFinder.getAllTvvDependentTCLLists();
		logger.info("Tvv Generic Tech Data Size : {}", tvvDepTCLData.size());

		combinedList.addAll(tvvDepTDLData);
		combinedList.addAll(tvvDepTCLData);

		logger.info("Size of All TvvStructure {}", combinedList.size());

		return Response.ok(combinedList).build();
	}

	/**
	 * Save es mandatory data.
	 * 
	 * @param mandatoryDataRepresentationDto the mandatory data representation dto
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/MandatoryDataEMS")
	public Response saveESMandatoryData(ManageMandatoryDataRequestDto mandatoryDataRepresentationDto) {

		List<MandatoryDataRepresentation> combinedList = mandatoryDataRepresentationDto.getGenericDataList();

		for (MandatoryDataRepresentation mandatoryData : combinedList) {

			if (mandatoryData.getObjectType().equals(Constants.TECHNICALDATA)) {

				GenericTechnicalData temp = genericTechDataRepo.load(mandatoryData.getEntityId());
				temp.setMandatoryIdValues(mandatoryData.getMandatoryIdValues());
				genericTechDataRepo.save(temp);

			} else if (mandatoryData.getObjectType().equals(Constants.TESTCONDITIONDATA)) {

				GenericTestCondition temp = genericTCRepo.load(mandatoryData.getEntityId());
				temp.setMandatoryIdValues(mandatoryData.getMandatoryIdValues());
				genericTCRepo.save(temp);

			} else if (mandatoryData.getObjectType().equals(Constants.POLLUTANTLIMITDATA)) {

				PollutantGasLimit temp = pglRepo.load(mandatoryData.getEntityId());
				temp.setMandatoryIdValues(mandatoryData.getMandatoryIdValues());
				pglRepo.save(temp);
			}
		}

		return Response.ok(combinedList).build();
	}

	/**
	 * Save tvv mandatory data.
	 * 
	 * @param mandatoryDataRepresentationDto the mandatory data representation dto
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/MandatoryDataTvv")
	public Response saveTvvMandatoryData(ManageMandatoryDataRequestDto mandatoryDataRepresentationDto) {

		List<MandatoryDataRepresentation> patch = mandatoryDataRepresentationDto.getGenericDataList();

		for (MandatoryDataRepresentation mandatoryData : patch) {

			if (mandatoryData.getObjectType().equals(Constants.TECHNICALDATA)) {

				GenericTechnicalData temp = genericTechDataRepo.load(mandatoryData.getEntityId());
				temp.setMandatoryIdValues(mandatoryData.getMandatoryIdValues());
				genericTechDataRepo.save(temp);

			} else if (mandatoryData.getObjectType().equals(Constants.TESTCONDITIONDATA)) {

				GenericTestCondition temp = genericTCRepo.load(mandatoryData.getEntityId());
				temp.setMandatoryIdValues(mandatoryData.getMandatoryIdValues());
				genericTCRepo.save(temp);
			}
		}

		return Response.ok(patch).build();
	}

}

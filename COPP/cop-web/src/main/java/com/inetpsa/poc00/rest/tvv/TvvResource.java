/*
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.tvv.TvvService;
import com.inetpsa.poc00.application.tvvvaluedfactorcoefficient.TvvValuedFactorCoeffService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.country.CountryRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureRepository;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDownFactory;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLFactory;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficentsFactory;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedFactorCoefficentRepository;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLFactory;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionFactory;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataFactory;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDLFactory;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffAppTypeAssembler;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeRepresentation;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentListFinder;
import com.inetpsa.poc00.emsdepfcl.FactorCoefficentsFinder;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder;
import com.inetpsa.poc00.emsdeptdl.EmsDepTDLFinder;
import com.inetpsa.poc00.infrastructure.tvv.ServiceResponseDto;
import com.inetpsa.poc00.mandatorydata.MandatoryDataFinder;
import com.inetpsa.poc00.pollutantorgas.PollutantGasLimitsFinder;
import com.inetpsa.poc00.pollutantorgas.PollutantGasListFinder;
import com.inetpsa.poc00.rest.bodywork.BodyWorkFinder;
import com.inetpsa.poc00.rest.bodywork.BodyWorkRepresentation;
import com.inetpsa.poc00.rest.carbrand.CarBrandAssembler;
import com.inetpsa.poc00.rest.carbrand.CarBrandFinder;
import com.inetpsa.poc00.rest.carbrand.CarBrandRepresentation;
import com.inetpsa.poc00.rest.carfactory.CarFactoryAssembler;
import com.inetpsa.poc00.rest.carfactory.CarFactoryFinder;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.category.CategoryFinder;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.country.CountryAssembler;
import com.inetpsa.poc00.rest.country.CountryRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.engine.EngineFinder;
import com.inetpsa.poc00.rest.engine.EngineRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelFinder;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeFinder;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.gearbox.GearBoxFinder;
import com.inetpsa.poc00.rest.gearbox.GearBoxRepresentation;
import com.inetpsa.poc00.rest.gnomedictionary.GenomeLCDVDictionaryFinder;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyFinder;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupFinder;
import com.inetpsa.poc00.rest.sampling.SamplingRuleFinder;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionAssembler;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.TvvDepTCLFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataAssembler;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvDepTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedestcl.EsDepToValuedTCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedTestConditionFinder;
import com.inetpsa.poc00.rest.tvvvaluedestdl.EsDepToValuedTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.EsDepToValuedFCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedfcl.FactorCoefficentDepToValuedAssmbler;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedFactorCoefficentsRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.ValuedFactorCoefficentFinder;
import com.inetpsa.poc00.rest.tvvvaluedpgl.EsDepToValuedPGLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.PollutantGasLimitDepToValuedAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLFinder;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.ValuedPollutantGasLimitFinder;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TestConditionDepToValuedAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvDepToValuedTCLAssemler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTvvDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtcl.ValuedGenericConditionRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TechnicalDataDepToValuedAssmbler;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvDepToValuedTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLFinder;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTvvDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataFinder;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;
import com.inetpsa.poc00.rest.valuedcoastdown.CoastDowntoValuedCoastDownAssembler;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyFinder;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class TvvResource.
 */
@Path("/tvvResource")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvResource {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TvvResource.class);

	/** The factor coeff app type assembler. */
	@Inject
	private FactorCoeffAppTypeAssembler factorCoeffAppTypeAssembler;
	/** The tvv dep tdl finder. */
	@Inject
	private TvvDepTDLFinder tvvDepTDLFinder;

	/** The generic data finder. */
	@Inject
	private GenericTechnicalDataFinder genericDataFinder;

	/** The Generic tech data repository. */
	@Inject
	com.inetpsa.poc00.domain.generictd.GenericTechDataRepository genericTechDataRepository;

	/** The generic technical data assembler. */
	@Inject
	GenericTechnicalDataAssembler genericTechnicalDataAssembler;

	/** The tvv assembler. */
	@Inject
	TvvAssembler tvvAssembler;

	/** The tvv factory. */
	@Inject
	TVVFactory tvvFactory;

	/** The tvv repository. */
	@Inject
	TVVRepository tvvRepository;

	/** The status finder. */
	@Inject
	private StatusFinder statusFinder;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The technical case factory. */
	@Inject
	private TechCaseFactory technicalCaseFactory;

	/** The tvv finder. */
	@Inject
	private TvvFinder tvvFinder;

	/** The tvv valued tdl assembler. */
	@Inject
	TvvValuedTDLAssembler tvvValuedTDLAssembler;

	/** The technical data dep to valued assmbler. */
	@Inject
	TechnicalDataDepToValuedAssmbler technicalDataDepToValuedAssmbler;

	/** The tvv dep to valued tdl assembler. */
	@Inject
	TvvDepToValuedTDLAssembler tvvDepToValuedTDLAssembler;

	/** The tvv valued tvv dep tdl factory. */
	@Inject
	TvvValuedTvvDepTDLFactory tvvValuedTvvDepTDLFactory;

	/** The tvv valued tdl repository. */
	@Inject
	TvvValuedTDLRepository tvvValuedTDLRepository;

	/** The tvv valued tcl assembler. */
	@Inject
	TvvValuedTCLAssembler tvvValuedTCLAssembler;

	/** The test condition dep to valued assmbler. */
	@Inject
	TestConditionDepToValuedAssembler testConditionDepToValuedAssmbler;

	/** The tvv dep to valued tcl assembler. */
	@Inject
	TvvDepToValuedTCLAssemler tvvDepToValuedTCLAssembler;

	/** The tvv valued tvv dep tcl factory. */
	@Inject
	TvvValuedTvvDepTCLFactory tvvValuedTvvDepTCLFactory;

	/** The tvv valued tcl repository. */
	@Inject
	TvvValuedTCLRepository tvvValuedTCLRepository;

	/** The tvv dep tcl finder. */
	@Inject
	private TvvDepTCLFinder tvvDepTCLFinder;

	/** The generic data finder. */
	@Inject
	private GenericTestConditionFinder genericConditionFinder;

	/** The Generic tech data repository. */
	@Inject
	GenericTestConditionRepository genericTestConditionRepository;

	/** The generic technical data assembler. */
	@Inject
	GenericTestConditionAssembler genericTestConditionAssembler;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The es dep to valued tdl assembler. */
	@Inject
	private EsDepToValuedTDLAssembler esDepToValuedTDLAssembler;

	/** The tvv valued es dep tdl factory. */
	@Inject
	private TvvValuedEsDepTDLFactory tvvValuedEsDepTDLFactory;

	/** The tvv valued es dep tdl assembler. */
	@Inject
	private TvvValuedEsDepTDLAssembler tvvValuedEsDepTDLAssembler;

	/** The tvv valued es dep tcl factory. */
	@Inject
	private TvvValuedEsDepTCLFactory tvvValuedEsDepTCLFactory;

	/** The tvv valued es dep tcl assembler. */
	@Inject
	private TvvValuedEsDepTCLAssembler tvvValuedEsDepTCLAssembler;

	/** The es dep to valued tcl assembler. */
	@Inject
	private EsDepToValuedTCLAssembler esDepToValuedTCLAssembler;

	/** The factor coefficents finder. */
	@Inject
	private FactorCoefficentsFinder factorCoefficentsFinder;

	/** The factor coefficent dep to valued assmbler. */
	@Inject
	private FactorCoefficentDepToValuedAssmbler factorCoefficentDepToValuedAssmbler;

	/** The es dep to valued fcl assembler. */
	@Inject
	private EsDepToValuedFCLAssembler esDepToValuedFCLAssembler;

	/** The tvv valued es dep fcl factory. */
	@Inject
	private TvvValuedEsDepFCLFactory tvvValuedEsDepFCLFactory;

	/** The tvv valued es dep fcl assembler. */
	@Inject
	private TvvValuedEsDepFCLAssembler tvvValuedEsDepFCLAssembler;

	/** The pollutant limit finder. */
	@Inject
	private PollutantGasLimitsFinder pollutantLimitFinder;

	/** The pollutant limit dep to valued assmbler. */
	@Inject
	private PollutantGasLimitDepToValuedAssembler pollutantLimitDepToValuedAssmbler;

	/** The es dep to valued pgl assembler. */
	@Inject
	private EsDepToValuedPGLAssembler esDepToValuedPGLAssembler;

	/** The tvv valued es dep pgl factory. */
	@Inject
	private TvvValuedEsDepPGLFactory tvvValuedEsDepPGLFactory;

	/** The tvv valued es dep pgl assembler. */
	@Inject
	private TvvValuedEsDepPGLAssembler tvvValuedEsDepPGLAssembler;

	/** The emission standard finder. */
	@Inject
	private EmissionStandardFinder emissionStandardFinder;

	/** The valued data factory. */

	@Inject
	FuelInjectionTypeFinder fuelInjectionTypeFinder;

	/** The valued data factory. */
	@Inject
	private TvvValuedTechDataFactory valuedDataFactory;

	/** The tvv valued tdl finder. */
	@Inject
	private TvvValuedTDLFinder tvvValuedTDLFinder;

	/** The tvv valued es dep tdl finder. */
	@Inject
	private TvvValuedEsDepTDLFinder tvvValuedEsDepTDLFinder;

	/** The tvv valued tcl finder. */
	@Inject
	private TvvValuedTCLFinder tvvValuedTCLFinder;

	/** The valued condtion finder. */
	@Inject
	private TvvValuedTestConditionFinder valuedCondtionFinder;

	/** The valued condition factory. */
	@Inject
	private TvvValuedTestConditionFactory valuedConditionFactory;

	/** The tvv valued es dep tcl finder. */
	@Inject
	private TvvValuedEsDepTCLFinder tvvValuedEsDepTCLFinder;

	/** The tvv valued es dep pgl finder. */
	@Inject
	private TvvValuedEsDepPGLFinder tvvValuedEsDepPGLFinder;

	/** The valued pollutant gas limit finder. */
	@Inject
	private ValuedPollutantGasLimitFinder valuedPollutantGasLimitFinder;

	/** The valued pollutant gas limi factory. */
	@Inject
	private TvvValuedPollutantGasLimitFactory valuedPollutantGasLimiFactory;

	/** The tvv valued es dep fcl finder. */
	@Inject
	private TvvValuedEsDepFCLFinder tvvValuedEsDepFCLFinder;

	/** The valued factor coefficent finder. */
	@Inject
	private ValuedFactorCoefficentFinder valuedFactorCoefficentFinder;

	/** The valued factor coefficent factory. */
	@Inject
	private TvvValuedFactorCoefficentsFactory valuedFactorCoefficentFactory;

	/** The car brand finder. */
	@Inject
	private CarBrandFinder carBrandFinder;

	/** The car brand factory. */
	@Inject
	CarBrandFactory carBrandFactory;

	/** The car brand assembler. */
	@Inject
	private CarBrandAssembler carBrandAssembler;

	/** The valued coast down factory. */
	@Inject
	private TvvValuedCoastDownFactory valuedCoastDownFactory;

	/** The coast down to valued assembler. */
	@Inject
	private CoastDowntoValuedCoastDownAssembler coastDownToValuedAssembler;

	/** The genome lcdv dictionary finder. */
	@Inject
	private GenomeLCDVDictionaryFinder genomeLCDVDictionaryFinder;

	/** The project codefamily finder. */
	@Inject
	private ProjectCodeFamilyFinder projectCodefamilyFinder;

	/** The gear box finder. */
	@Inject
	private GearBoxFinder gearBoxFinder;

	/** The engine finder. */
	@Inject
	private EngineFinder engineFinder;

	/** The body work finder. */
	@Inject
	private BodyWorkFinder bodyWorkFinder;

	/** The fuel finder. */
	@Inject
	private FuelFinder fuelFinder;

	/** The technical group finder. */
	@Inject
	private TechnicalGroupFinder technicalGroupFinder;

	/** The regulation group finder. */
	@Inject
	private RegulationGroupFinder regulationGroupFinder;

	/** The status repository. */
	@Inject
	private StatusRepository statusRepository;

	/** The ems dep tdl finder. */
	@Inject
	private EmsDepTDLFinder emsDepTDLFinder;

	/** The ems dep tcl finder. */
	@Inject
	private EmsDepTCLFinder emsDepTCLFinder;

	/** The ems dep fcl finder. */
	@Inject
	private FactorCoefficentListFinder emsDepFCLFinder;

	/** The ems dep pgl finder. */
	@Inject
	private PollutantGasListFinder emsDepPGLFinder;

	/** The tracabilty resource. */
	@Inject
	TraceabilityResource tracabiltyResource;

	/** The mandatory data finder. */
	@Inject
	private MandatoryDataFinder mandatoryDataFinder;

	/** The test nature repository. */
	@Inject
	private TestNatureRepository testNatureRepository;

	/** The category finder. */
	@Inject
	private CategoryFinder categoryFinder;

	/** The vehicle technology finder. */
	@Inject
	private VehicleTechnologyFinder vehicleTechnologyFinder;

	/** The car factory finder. */
	@Inject
	private CarFactoryFinder carFactoryFinder;

	/** The car factory factory. */
	@Inject
	private CarFactoryObjectCreation carFactoryFactory;

	/** The car factory assembler. */
	@Inject
	private CarFactoryAssembler carFactoryAssembler;

	/** The fuel inject type finder. */
	@Inject
	FuelInjectionTypeFinder fuelInjectTypeFinder;

	/** The tvv service. */
	@Inject
	private TvvService tvvService;

	/** The valued data finder. */
	@Inject
	private ValuedGenericDataFinder valuedDataFinder;

	/** The country repo. */
	@Inject
	private CountryRepository countryRepo;

	/** The tvv valued factor coefficent repository. */
	@Inject
	private TvvValuedFactorCoefficentRepository tvvValuedFactorCoefficentRepository;

	/** The tvv valued factor coeff service. */
	@Inject
	TvvValuedFactorCoeffService tvvValuedFactorCoeffService;

	/** The sampling rule finder. */
	@Inject
	SamplingRuleFinder samplingRuleFinder;

	/** The trace service. */
	@Inject
	private TraceabilityService traceService;

	/** The tvv valued TDL factory. */
	@Inject
	private TvvValuedTvvDepTDLFactory tvvValuedTDLFactory;

	/** The country assembler. */
	@Inject
	private CountryAssembler countryAssembler;

	/** The country factory. */
	@Inject
	private CountryFactory countryFactory;

	/** The tvv valued TCL factory. */
	@Inject
	private TvvValuedTvvDepTCLFactory tvvValuedTCLFactory;

	/**
	 * Creates the tvv.
	 * 
	 * @param tvvObjectToSave the tvv object to save
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TvvNew")
	public Response createTVV(TvvRepresentation tvvObjectToSave) {
		try {
			TVV tvvObj = tvvFactory.createTVV();
			tvvAssembler.doMergeAggregateWithDto(tvvObj, tvvObjectToSave);

			Status status = statusFinder.getStatusForEmissionStandard(Constants.DRAFT);
			if (status == null)
				return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
			tvvObj.setStatus(status);
			if (tvvObjectToSave.getEmissionStandard() != null) {
				EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
				emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, tvvObjectToSave.getEmissionStandard());
				TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
				technicalCase.setTvv(tvvObj);
				technicalCase.setEmissionStandard(emissionStandard);

				tvvObj.setTechnicalCase(technicalCase);
				tvvObj = copyEsDependentLists(emissionStandard, tvvObj);

			}
			tvvObj.setCreationDate(new Date());
			tvvObj.setModificationDate(new Date());
			tvvObj.setTvvValuedTvvDepTDL(copyTvvDepTDLLists(tvvObj));
			tvvObj.setTvvValuedTvvDepTCL(copyTvvDepTCLLists(tvvObj));

			tvvObj = tvvRepository.saveTVV(tvvObj);
			tracabiltyResource.historyForSave(tvvObj, Constants.TVV_SCREEN_ID);

			TvvRepresentation tvvRep = new TvvRepresentation();
			tvvAssembler.doAssembleDtoFromAggregate(tvvRep, tvvObj);

			return Response.ok(tvvRep).build();
		} catch (Exception e) {
			LOGGER.error("Error in saving TVV ", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	/**
	 * Copy es dependent lists.
	 * 
	 * @param emissionStandard the emission standard
	 * @param tvvObj the tvv obj
	 * @return the tvv
	 */
	private TVV copyEsDependentLists(EmissionStandard emissionStandard, TVV tvvObj) {
		EmissionStandard es = emissionStandardRepository.load(emissionStandard.getEntityId());
		List<EmissionStdDepTDL> esDepTDL = emsDepTDLFinder.getLatestEmissionStandardDepLists(es.getEntityId());
		List<EmissionStdDepTCL> esDepTCL = emsDepTCLFinder.getLatestEmissionStandardDepLists(es.getEntityId());
		List<FactorCoefficentList> esDepFCL = emsDepFCLFinder.getLatestEmissionStandardDepLists(es.getEntityId());
		List<PollutantGasLimitList> esDepPGL = emsDepPGLFinder.getLatestEmissionStandardDepLists(es.getEntityId());
		// Code to copy TvvValued Es dependent TDL
		List<TvvValuedEsDepTDL> copiedList = new ArrayList<>();
		for (EmissionStdDepTDL oldTDL : esDepTDL) {
			TvvValuedEsDepTDLRepresentation tdlRep = new TvvValuedEsDepTDLRepresentation();
			tdlRep.setValuedGenericData(new ArrayList<ValuedGenericDataRepresentation>());

			for (GenericTechnicalData data : genericDataFinder.getAllGenericDataForEmsList(oldTDL.getEntityId())) {
				ValuedGenericDataRepresentation valuedData = new ValuedGenericDataRepresentation();
				technicalDataDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedData, data);
				valuedData.setEmsDepTDL(tdlRep);
				tdlRep.getValuedGenericData().add(valuedData);

			}
			esDepToValuedTDLAssembler.doAssembleDtoFromAggregate(tdlRep, oldTDL);

			TvvValuedEsDepTDL newValuedTDL = tvvValuedEsDepTDLFactory.createTvvValuedEsDepTDL();
			tvvValuedEsDepTDLAssembler.doMergeAggregateWithDto(newValuedTDL, tdlRep);
			newValuedTDL.setTvvObj(tvvObj);
			newValuedTDL.setEmissionStandard(emissionStandard);

			copiedList.add(newValuedTDL);

		}
		tvvObj.setTvvValuedEsDepTDL(copiedList);

		// Copy EsDepTCL to valued list
		List<TvvValuedEsDepTCL> copiedTCList = new ArrayList<>();
		for (EmissionStdDepTCL oldTCL : esDepTCL) {
			TvvValuedEsDepTCLRepresentation tdlRep = new TvvValuedEsDepTCLRepresentation();
			tdlRep.setGenericTestCondition(new ArrayList<ValuedGenericConditionRepresentation>());

			for (GenericTestCondition condition : genericConditionFinder.getAllGenericConditionsForEmsList(oldTCL.getEntityId())) {
				ValuedGenericConditionRepresentation valuedCondition = new ValuedGenericConditionRepresentation();
				testConditionDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedCondition, condition);
				valuedCondition.setEmsDepTCL(tdlRep);
				tdlRep.getGenericTestCondition().add(valuedCondition);

			}
			esDepToValuedTCLAssembler.doAssembleDtoFromAggregate(tdlRep, oldTCL);

			TvvValuedEsDepTCL newValuedTCL = tvvValuedEsDepTCLFactory.createTvvValuedEsDepTCL();
			tvvValuedEsDepTCLAssembler.doMergeAggregateWithDto(newValuedTCL, tdlRep);
			newValuedTCL.setTvvObj(tvvObj);
			newValuedTCL.setEmissionStandard(emissionStandard);

			copiedTCList.add(newValuedTCL);

		}
		tvvObj.setTvvValuedEsDepTCL(copiedTCList);
		// Copy EsDepFCL to valued list
		List<TvvValuedEsDepFCL> copiedFCList = new ArrayList<>();

		for (FactorCoefficentList oldFCL : esDepFCL) {
			TvvValuedEsDepFCLRepresentation fclRep = new TvvValuedEsDepFCLRepresentation();
			fclRep.setFactorOrCoeffiecients(new ArrayList<TvvValuedFactorCoefficentsRepresentation>());

			for (FactorCoefficents fcObj : factorCoefficentsFinder.getAllFActorsForDepList(oldFCL.getEntityId())) {
				TvvValuedFactorCoefficentsRepresentation valuedObject = new TvvValuedFactorCoefficentsRepresentation();
				factorCoefficentDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedObject, fcObj);
				valuedObject.setFcList(fclRep);
				fclRep.getFactorOrCoeffiecients().add(valuedObject);

			}
			esDepToValuedFCLAssembler.doAssembleDtoFromAggregate(fclRep, oldFCL);

			TvvValuedEsDepFCL newValuedFCL = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL();
			Set<FactorCoeffApplicationTypeRepresentation> appTypeRepList = new HashSet<>();
			for (FactorCoeffApplicationType fcat : oldFCL.getFcApplicationTypes()) {
				FactorCoeffApplicationTypeRepresentation factorCoeffApplicationTypeRepresentation = new FactorCoeffApplicationTypeRepresentation();
				factorCoeffAppTypeAssembler.doAssembleDtoFromAggregate(factorCoeffApplicationTypeRepresentation, fcat);
				appTypeRepList.add(factorCoeffApplicationTypeRepresentation);

			}
			fclRep.setFcApplicationTypes(appTypeRepList);
			tvvValuedEsDepFCLAssembler.doMergeAggregateWithDto(newValuedFCL, fclRep);
			newValuedFCL.setTvvObj(tvvObj);
			newValuedFCL.setEmissionStandard(emissionStandard);

			copiedFCList.add(newValuedFCL);

		}
		tvvObj.setTvvValuedEsDepFCL(copiedFCList);

		// Copy EsDepPGL to valued list
		List<TvvValuedEsDepPGL> copiedPGList = new ArrayList<>();

		for (PollutantGasLimitList oldPGL : esDepPGL) {
			TvvValuedEsDepPGLRepresentation pglRep = new TvvValuedEsDepPGLRepresentation();
			pglRep.setPollutantGasLimit(new ArrayList<TvvValuedPollutantLimitRepresentation>());

			for (PollutantGasLimit pgObj : pollutantLimitFinder.getAllPollutantsForDepList(oldPGL.getEntityId())) {
				TvvValuedPollutantLimitRepresentation valuedObject = new TvvValuedPollutantLimitRepresentation();
				pollutantLimitDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedObject, pgObj);
				valuedObject.setPollutantGasLimitList(pglRep);
				pglRep.getPollutantGasLimit().add(valuedObject);

			}
			esDepToValuedPGLAssembler.doAssembleDtoFromAggregate(pglRep, oldPGL);

			TvvValuedEsDepPGL newValuedPGL = tvvValuedEsDepPGLFactory.createTvvValuedEsDepPGL();
			tvvValuedEsDepPGLAssembler.doMergeAggregateWithDto(newValuedPGL, pglRep);
			newValuedPGL.setTvvObj(tvvObj);
			newValuedPGL.setEmissionStandard(emissionStandard);

			copiedPGList.add(newValuedPGL);

		}
		tvvObj.setTvvValuedEsDepPGL(copiedPGList);
		return tvvObj;

	}

	/**
	 * Copy tvv dep tDl lists.
	 * 
	 * @param tvvObj the tvv obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTDL> copyTvvDepTDLLists(TVV tvvObj) {

		List<TvvDepTDL> tdlList = tvvDepTDLFinder.getAllTvvDependentLists();
		List<TvvValuedTvvDepTDL> copiedList = new ArrayList<>();
		for (TvvDepTDL oldTDL : tdlList) {
			TvvValuedTvvDepTDLRepresentation tdlRep = new TvvValuedTvvDepTDLRepresentation();
			tvvDepToValuedTDLAssembler.doAssembleDtoFromAggregate(tdlRep, oldTDL);
			tdlRep.setValuedGenericData(new ArrayList<ValuedGenericDataRepresentation>());

			for (GenericTechnicalData data : genericDataFinder.getAllGenericDataForList(oldTDL.getEntityId())) {
				ValuedGenericDataRepresentation valuedData = new ValuedGenericDataRepresentation();
				technicalDataDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedData, data);
				valuedData.setTvvDepTDL(tdlRep);
				tdlRep.getValuedGenericData().add(valuedData);

			}
			TvvValuedTvvDepTDL newValuedTDL = tvvValuedTvvDepTDLFactory.createTvvValuedTDL();
			tvvValuedTDLAssembler.doMergeAggregateWithDto(newValuedTDL, tdlRep);
			newValuedTDL.setTvvObj(tvvObj);

			copiedList.add(newValuedTDL);

		}
		return copiedList;

	}

	/**
	 * Copy tvv dep tcl lists.
	 * 
	 * @param tvvObj the tvv obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTCL> copyTvvDepTCLLists(TVV tvvObj) {

		List<TvvDepTCL> tdlList = tvvDepTCLFinder.getAllTvvDependentLists();
		List<TvvValuedTvvDepTCL> copiedList = new ArrayList<>();
		for (TvvDepTCL oldTCL : tdlList) {
			TvvValuedTvvDepTCLRepresentation tdlRep = new TvvValuedTvvDepTCLRepresentation();
			tvvDepToValuedTCLAssembler.doAssembleDtoFromAggregate(tdlRep, oldTCL);
			tdlRep.setGenericTestCondition(new ArrayList<ValuedGenericConditionRepresentation>());

			for (GenericTestCondition data : genericConditionFinder.getAllGenericConditionsForList(oldTCL.getEntityId())) {
				ValuedGenericConditionRepresentation valuedData = new ValuedGenericConditionRepresentation();
				testConditionDepToValuedAssmbler.doAssembleDtoFromAggregate(valuedData, data);
				valuedData.setTvvDepTCL(tdlRep);
				tdlRep.getGenericTestCondition().add(valuedData);

			}
			TvvValuedTvvDepTCL newValuedTCL = tvvValuedTvvDepTCLFactory.createTvvValuedTvvDepTCL();
			tvvValuedTCLAssembler.doMergeAggregateWithDto(newValuedTCL, tdlRep);
			newValuedTCL.setTvvObj(tvvObj);

			copiedList.add(newValuedTCL);

		}
		return copiedList;

	}

	/**
	 * Gets the tV vs with label.
	 * 
	 * @param searchRepresentation the search representation
	 * @return the tV vs with label
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvv")
	public Response getTVVsWithLabel(TvvSearchRepresentation searchRepresentation) {

		String label = searchRepresentation.getTvvLabel();

		List<TvvRepresentation> tvvList = tvvFinder.getTVVsWithLabel(label);

		return Response.ok(tvvList).build();
	}

	/**
	 * Find tv vs by label.
	 * 
	 * @param searchLabel the search label
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TVVs/{searchLabel}")
	public Response findTVVsByLabel(@PathParam("searchLabel") String searchLabel) {

		List<TvvRepresentation> tvvList = new ArrayList<>();
		if (searchLabel != null) {
			String tvvLabel = null;
			while (searchLabel.indexOf(Constants.ASTERIC) != -1) {
				tvvLabel = searchLabel.replace(Constants.ASTERIC, Constants.WILDCARD_PERCENTAGE);
			}
			tvvList = tvvFinder.findTVVsByLabel(tvvLabel);

		}

		return Response.ok(tvvList).build();
	}

	/**
	 * Find tv vs by label.
	 * 
	 * @param searchRepresentation the search representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/TVVsByCriteria")
	public Response searchTVV(TvvSearchRepresentation searchRepresentation) {
		try {
			List<TvvRepresentation> tvvList;

			String searchLabel = searchRepresentation.getTvvLabel();
			if (searchLabel != null) {

				while (searchLabel.indexOf(Constants.ASTERIC) != -1) {
					searchLabel = searchLabel.replace(Constants.ASTERIC, Constants.WILDCARD_PERCENTAGE);
				}
				searchRepresentation.setTvvLabel(searchLabel);
			}

			tvvList = tvvFinder.findByCriteria(searchRepresentation);

			return Response.ok(tvvList).build();
		} catch (Exception e) {
			LOGGER.error("Error in searching TVV ", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Gets the tvv.
	 * 
	 * @param entityId the entity id
	 * @return the tvv
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvDetails/{entityId}")
	public Response getTvv(@PathParam("entityId") String entityId) {
		try {
			TvvRepresentation tvvObj = new TvvRepresentation();
			if (entityId != null && entityId.length() > 0) {
				long tvvId = Long.parseLong(entityId);
				TVV tvv = tvvRepository.load(tvvId);
				if (tvv != null) {
					TechnicalGroup tGroup = technicalGroupFinder.getTechnicalGroupForTechnicalCase(tvv.getTechnicalCase().getEntityId());
					if (tGroup != null) {
						RegulationGroup rg = regulationGroupFinder.getRegulationGroupForTG(tGroup.getEntityId());
						if (rg != null)
							tGroup.setRegulationGroup(rg);
						tvv.getTechnicalCase().setTechnicalGroup(tGroup);
					}
					tvvAssembler.doAssembleDtoFromAggregate(tvvObj, tvv);
				} else
					return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();

			}
			return Response.ok(tvvObj).build();
		} catch (Exception e) {
			LOGGER.error("Error in retriving TVV Data", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Update tvv.
	 * 
	 * @param tvvRepresentation the tvv representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Tvv")
	public Response updateTvv(TvvRepresentation tvvRepresentation) {

		TvvRepresentation tvvObj = new TvvRepresentation();

		try {

			TVV tvv = tvvRepository.load(tvvRepresentation.getEntityId());

			TvvRepresentation tvvrepresentObj = new TvvRepresentation();
			tvvAssembler.doAssembleDtoFromAggregate(tvvrepresentObj, tvv);

			TVV updatedTvv = tvvFactory.createTVV();
			tvvAssembler.doMergeAggregateWithDto(updatedTvv, tvvRepresentation);

			TvvValuedCoastDown valuedCoastDown = null;
			if (tvvRepresentation.getCoastDownToSave() != null) {
				valuedCoastDown = valuedCoastDownFactory.createValuedCoastDown();
				coastDownToValuedAssembler.doMergeAggregateWithDto(valuedCoastDown, tvvRepresentation.getCoastDownToSave());

			}

			ServiceResponseDto responsedto = tvvService.updateTvv(tvvRepresentation.getEntityId(), updatedTvv, valuedCoastDown);
			TVV savedTvv = responsedto.getTvv();

			if (responsedto.isChangeVersion()) {

				boolean emissionStandardChanged = false;

				EmissionStandard oldEs = tvv.getTechnicalCase().getEmissionStandard();
				EmissionStandardRepresentation newEs = tvvRepresentation.getEmissionStandard();

				String oldEsLabel = oldEs.getEsLabel() + oldEs.getVersion();
				String newEsLabel = newEs.getEsLabel() + newEs.getVersion();

				if (!oldEsLabel.equalsIgnoreCase(newEsLabel)) {
					emissionStandardChanged = true;
				}

				if (!emissionStandardChanged)// If emissionStandard is not Changed copy
				{
					savedTvv.setTvvValuedEsDepTDL(copyTvvValuedEsDepTDLLists(tvvRepresentation.getTvvValuedEsDepTDL(), savedTvv));
					savedTvv.setTvvValuedEsDepTCL(copyTvvValuedEsDepTCLLists(tvvRepresentation.getTvvValuedEsDepTCL(), savedTvv));
					savedTvv.setTvvValuedEsDepPGL(copyTvvValuedEsDepPGLLists(tvvRepresentation.getTvvValuedEsDepPGL(), savedTvv));
					savedTvv.setTvvValuedEsDepFCL(copyTvvValuedEsDepFCLLists(tvvRepresentation.getTvvValuedEsDepFCL(), savedTvv));
				} else {
					EmissionStandard emissionStandard = emissionStandardRepository.load(newEs.getEntityId());
					savedTvv = copyEsDependentLists(emissionStandard, savedTvv);
				}

				getCountrySet(tvvRepresentation, savedTvv);
				savedTvv.setTvvValuedCoastDown(valuedCoastDown);
				savedTvv.setTvvValuedTvvDepTDL(copyTvvValuedTvvDepTDLLists(tvvRepresentation.getTvvValuedTvvDepTDL(), savedTvv));
				savedTvv.setTvvValuedTvvDepTCL(copyTvvValuedTvvDepTCLLists(tvvRepresentation.getTvvValuedTvvDepTCL(), savedTvv));

				tvvService.saveTvvObject(responsedto.isChangeVersion(), savedTvv, tvv);

			} else {

				TVV oldtvv = tvvFactory.createTVV();
				tvvAssembler.doMergeAggregateWithDto(oldtvv, tvvrepresentObj);

				// AS LABEL OF BELOW OBJECTS ARE BEING MANIPULATED IN TVVASSEMBLER,
				// WE HAVE TO EXPICITLY SET THE CORRECT LABEL FOR TVV HISTORY
				oldtvv.getBodyWork().setLabel(tvv.getBodyWork().getLabel());
				oldtvv.getEngine().setEngineLabel(tvv.getEngine().getEngineLabel());
				oldtvv.getGearBox().setLabel(tvv.getGearBox().getLabel());
				oldtvv.getProjectCodeFamily().setProjectCodeLabel(tvv.getProjectCodeFamily().getProjectCodeLabel());

				traceService.historyForUpdate(oldtvv, savedTvv, Constants.TVV_SCREEN_ID, Boolean.FALSE);

			}
			tvvAssembler.doAssembleDtoFromAggregate(tvvObj, savedTvv);
			return Response.ok(tvvObj).build();
		} catch (Exception e) {
			LOGGER.error("Error in updating tvv", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Copy tvv.
	 *
	 * @param tvvRepresentObject the tvv represent object
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvDuplicate")
	public Response copyTvv(TvvRepresentation tvvRepresentObject) {
		TvvRepresentation tvvObj = new TvvRepresentation();

		if (tvvRepresentObject.getEntityId() != null) {
			TvvValuedCoastDown valuedCoastDown = null;
			long tvvID = tvvRepresentObject.getEntityId();

			TVV tvv = tvvRepository.load(tvvID);

			List<CarFactoryRepresentation> factoryList = carFactoryFinder.getFactoriesForTVV(tvv.getEntityId());
			Set<CarFactory> carFactorySet = new HashSet<>();
			for (CarFactoryRepresentation carFactoryRep : factoryList) {
				CarFactory carFactory = carFactoryFactory.createCarFactoryObject();
				carFactoryAssembler.doMergeAggregateWithDto(carFactory, carFactoryRep);
				carFactorySet.add(carFactory);

			}

			List<CarBrandRepresentation> carBrandList = carBrandFinder.getCarBrandsForTVV(tvv.getEntityId());
			Set<CarBrand> carBrandSet = new HashSet<>();
			for (CarBrandRepresentation carBrandRep : carBrandList) {
				CarBrand carBrand = carBrandFactory.createCarBrand();
				carBrandAssembler.doMergeAggregateWithDto(carBrand, carBrandRep);
				carBrandSet.add(carBrand);

			}

			List<Country> countryList = countryRepo.getCountryForTVV(tvv.getEntityId());
			Set<Country> countrySet = new HashSet<>();
			for (Country country : countryList) {
				countrySet.add(country);
			}

			if (tvv.getTvvValuedCoastDown() != null) {
				valuedCoastDown = valuedCoastDownFactory.createValuedCoastDown(tvv.getTvvValuedCoastDown());

			}
			TVV newObject = tvvService.copyTvv(tvvRepresentObject.getLabel(), tvv, carFactorySet, carBrandSet, valuedCoastDown);
			newObject.setCountrySet(countrySet);
			newObject.setTvvValuedTvvDepTDL(copyTvvValuedTDLLists(tvvRepresentObject.getEntityId(), newObject));
			newObject.setTvvValuedTvvDepTCL(copyTvvValuedTCLLists(tvvRepresentObject.getEntityId(), newObject));
			newObject = copyTvvValuedEsDepLists(tvv.getEntityId(), tvv.getTechnicalCase().getEmissionStandard(), newObject);
			tvvService.saveTvvObject(true, newObject, null);

			tvvAssembler.doAssembleDtoFromAggregate(tvvObj, newObject);
			return Response.ok(tvvObj).build();
		}
		return Response.ok(tvvObj).build();
	}

	/**
	 * Creates the tvv version.
	 * 
	 * @param tvvRepresentation the tvv representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvVersion")
	public Response createTvvVersion(TvvRepresentation tvvRepresentation) {

		TvvRepresentation tvvObj = new TvvRepresentation();
		try {
			long tvvID = tvvRepresentation.getEntityId();

			TVV tvv = tvvRepository.load(tvvID);

			TVV newVersionTVV = tvvService.createVersion(tvvRepresentation.getEntityId());

			newVersionTVV.setTvvValuedTvvDepTDL(copyTvvValuedTDLLists(tvvRepresentation.getEntityId(), newVersionTVV));
			newVersionTVV.setTvvValuedTvvDepTCL(copyTvvValuedTCLLists(tvvRepresentation.getEntityId(), newVersionTVV));
			newVersionTVV = copyTvvValuedEsDepLists(tvv.getEntityId(), tvv.getTechnicalCase().getEmissionStandard(), newVersionTVV);
			tvvService.saveTvvObject(true, newVersionTVV, null);

			tvvAssembler.doAssembleDtoFromAggregate(tvvObj, newVersionTVV);

			return Response.ok(tvvObj).build();
		} catch (Exception e) {
			LOGGER.error("Error in creating new version of TVV", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Delete tvv.
	 * 
	 * @param tvvRepresentation the tvv representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public Response deleteTvv(TvvRepresentation tvvRepresentation) {
		try {

			int response = tvvService.deleteTvv(tvvRepresentation.getEntityId());
			if (response == -1)
				return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
			return Response.ok().build();
		} catch (Exception e) {
			LOGGER.error("Error in deleting TVV ", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Check mandatory data for tdl.
	 * 
	 * @param tvv the tvv
	 * @param stausNatureId the staus nature id
	 * @param validationResponseList the validation response list
	 * @return the array list
	 */
	private ArrayList<TvvValidationResponseRepresentation> checkMandatoryDataForTDL(TVV tvv, Long stausNatureId, ArrayList<TvvValidationResponseRepresentation> validationResponseList) {

		List<TvvValuedTechData> commonDataList = new ArrayList<>();
		for (TvvValuedTvvDepTDL dataList : tvv.getTvvValuedTvvDepTDL()) {
			commonDataList.addAll(dataList.getValuedTechnicalData());
		}
		for (TvvValuedEsDepTDL dataList : tvv.getTvvValuedEsDepTDL()) {
			commonDataList.addAll(dataList.getGenericTechnicalData());
		}

		for (TvvValuedTechData valuedData : commonDataList) {
			TvvValidationResponseRepresentation responseString = new TvvValidationResponseRepresentation();
			GenericTechnicalData genericData = valuedData.getGenericData();
			if (genericData != null && genericData.getMandatoryIdValues() != null) {

				String mandatoryData = genericData.getMandatoryIdValues();

				if (mandatoryData.contains(stausNatureId.toString()) && (valuedData.getValue() == null || valuedData.getValue().length() == 0)) {

					if (valuedData.getTvvDepTDL() != null)
						responseString.setListLabel(valuedData.getTvvDepTDL().getLabel());
					else
						responseString.setListLabel(valuedData.getTvvValuedEsTDL().getLabel());

					responseString.setTypeOfData("Data");
					responseString.setDataLabel(valuedData.getLabel());
					validationResponseList.add(responseString);

				}

			}
		}
		return validationResponseList;
	}

	/**
	 * Check mandatory condition for tcl.
	 * 
	 * @param tvv the tvv
	 * @param stausNatureId the staus nature id
	 * @param validationResponseList the validation response list
	 * @return the array list
	 */
	private ArrayList<TvvValidationResponseRepresentation> checkMandatoryConditionForTCL(TVV tvv, Long stausNatureId, ArrayList<TvvValidationResponseRepresentation> validationResponseList) {

		List<TvvValuedTestCondition> commonConditionList = new ArrayList<>();
		for (TvvValuedTvvDepTCL dataList : tvv.getTvvValuedTvvDepTCL()) {
			commonConditionList.addAll(dataList.getValuedTestCondition());
		}
		for (TvvValuedEsDepTCL dataList : tvv.getTvvValuedEsDepTCL()) {
			commonConditionList.addAll(dataList.getGenericTestCondition());
		}

		for (TvvValuedTestCondition valuedCondition : commonConditionList) {
			TvvValidationResponseRepresentation responseString = new TvvValidationResponseRepresentation();
			GenericTestCondition genericCondition = valuedCondition.getGenericCondition();
			if (genericCondition != null && genericCondition.getMandatoryIdValues() != null) {
				String mandatoryData = genericCondition.getMandatoryIdValues();

				if (mandatoryData.contains(stausNatureId.toString()) && (valuedCondition.getDefaultValue() == null || valuedCondition.getDefaultValue().length() == 0)) {

					if (valuedCondition.getTvvValuedTCL() != null)
						responseString.setListLabel(valuedCondition.getTvvValuedTCL().getLabel());
					else
						responseString.setListLabel(valuedCondition.getTvvValuedEsTCL().getLabel());
					responseString.setTypeOfData("Condition");
					responseString.setDataLabel(valuedCondition.getLabel());
					validationResponseList.add(responseString);

				}

			}

		}
		return validationResponseList;
	}

	/**
	 * Check mandatory limit for pgl.
	 * 
	 * @param tvv the tvv
	 * @param stausNatureId the staus nature id
	 * @param validationResponseList the validation response list
	 * @return the array list
	 */
	private ArrayList<TvvValidationResponseRepresentation> checkMandatoryLimitForPGL(TVV tvv, Long stausNatureId, ArrayList<TvvValidationResponseRepresentation> validationResponseList) {

		List<TvvValuedPollutantGasLimit> commonConditionList = new ArrayList<>();
		for (TvvValuedEsDepPGL dataList : tvv.getTvvValuedEsDepPGL()) {
			commonConditionList.addAll(dataList.getPollutantGasLimit());
		}

		for (TvvValuedPollutantGasLimit valuedLimit : commonConditionList) {
			TvvValidationResponseRepresentation responseString = new TvvValidationResponseRepresentation();
			PollutantGasLimit limit = valuedLimit.getPollutantLimit();

			if (limit != null && limit.getMandatoryIdValues() != null) {
				String mandatoryData = limit.getMandatoryIdValues();

				if (mandatoryData.contains(stausNatureId.toString()) && (valuedLimit.getValue() == null || valuedLimit.getValue().length() == 0)) {

					if (valuedLimit.getTvvValuedEsDepPGL() != null)
						responseString.setListLabel(valuedLimit.getTvvValuedEsDepPGL().getLabel());

					responseString.setTypeOfData("Limit");
					responseString.setDataLabel(valuedLimit.getPgLabel().getLabel());
					validationResponseList.add(responseString);

				}
			}

		}
		return validationResponseList;
	}

	/**
	 * Change tvv status.
	 * 
	 * @param tvvRepresentation the tvv representation
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvStatus")
	public Response changeTvvStatus(TvvRepresentation tvvRepresentation) {

		TvvRepresentation tvvObj = new TvvRepresentation();
		try {

			TVV tvv = tvvRepository.load(tvvRepresentation.getEntityId());
			ArrayList<TvvValidationResponseRepresentation> validationResponseList = new ArrayList<>();
			Status status = statusRepository.load(tvvRepresentation.getStatusToSave().getEntityId());
			TestNature testNature = testNatureRepository.load(tvvRepresentation.getStatusToSave().getTestNatureId());
			Long stausNatureId = mandatoryDataFinder.getStatusNatureId(status.getEntityId(), testNature.getEntityId());
			validationResponseList = checkMandatoryDataForTDL(tvv, stausNatureId, validationResponseList);
			validationResponseList = checkMandatoryConditionForTCL(tvv, stausNatureId, validationResponseList);
			validationResponseList = checkMandatoryLimitForPGL(tvv, stausNatureId, validationResponseList);
			if (!validationResponseList.isEmpty()) {
				MandatoryValidationResponseRepresentation responseObject = new MandatoryValidationResponseRepresentation();

				responseObject.setValidationStatus(Constants.FAIL);
				responseObject.setValidationResponseList(validationResponseList);
				return Response.ok(responseObject).build();
			}
			tvv.setStatus(status);
			tvv.setTestNature(testNature);
			tvv = tvvRepository.save(tvv);

			tvvAssembler.doAssembleDtoFromAggregate(tvvObj, tvv);

			return Response.ok(tvvObj).build();
		} catch (Exception e) {
			LOGGER.error("Error in changing Status of TVV", e);
			return Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * This method is called to fetch data reslted to TVV based on entered TVV Label.
	 * 
	 * @param tvvLabel the tvv label
	 * @return the gnome data for tvv
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvGnomeData/{tvvLabel}")
	public Response getGnomeDataForTVV(@PathParam("tvvLabel") String tvvLabel) {
		if (StringUtils.isNotBlank(tvvLabel)) {
			String tvvlabeltoSend;
			if (tvvLabel.length() > 5)
				tvvlabeltoSend = tvvLabel.substring(0, 6);
			else
				tvvlabeltoSend = tvvLabel;
			List<String> kmatList = genomeLCDVDictionaryFinder.getKmatDictionaryForTVV(tvvlabeltoSend);

			if (kmatList.isEmpty() || kmatList.size() > 1) {
				return Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
			}

			List<ProjectCodeFamilyRepresentation> pcFamilyList = projectCodefamilyFinder.getAllProjectFamiliesForTvv(kmatList);
			List<GearBoxRepresentation> gearBoxList = gearBoxFinder.getAllGearBoxValuesForTvv(kmatList);
			List<EngineRepresentation> engineList = engineFinder.getAllEnginesForTvv(kmatList);
			List<BodyWorkRepresentation> bodyWorkList = bodyWorkFinder.getAllBodyWorkDataForTvv(kmatList);
			List<CarBrandRepresentation> carBrandList = carBrandFinder.getAllCarBrandDataForTVV(kmatList);
			List<FuelRepresentation> fuelList = fuelFinder.getAllFuelDataForTVV(kmatList);

			List<CategoryRepresentation> categoryList = categoryFinder.getAllCategory();
			List<VehicleTechnologyRepresentation> vtList = vehicleTechnologyFinder.getAllVehicleTechnologies();
			List<CarFactoryRepresentation> factoryList = carFactoryFinder.findAllUsineData();
			List<EmissionStandardRepresentation> emsList = emissionStandardFinder.getAllEmissionStandardsForTVV();
			List<FuelInjectionTypeRepresentation> fuelInjectionList = fuelInjectTypeFinder.getAllFuelInjectionTypes();

			TvvDataRepresentation dataRepresentation = new TvvDataRepresentation();
			dataRepresentation.setTvvLabel(tvvLabel);
			dataRepresentation.setPcFamilyList(pcFamilyList);
			dataRepresentation.setEmissionStandardList(emsList);
			dataRepresentation.setBodyWorklist(bodyWorkList);
			dataRepresentation.setGearBoxList(gearBoxList);
			dataRepresentation.setEngineList(engineList);
			dataRepresentation.setCarBrandList(carBrandList);
			dataRepresentation.setFuelList(fuelList);
			dataRepresentation.setCategoryList(categoryList);
			dataRepresentation.setVtList(vtList);
			dataRepresentation.setFactoryList(factoryList);
			dataRepresentation.setFuelInjectionList(fuelInjectionList);

			return Response.ok(dataRepresentation).build();

		}
		return null;
	}

	/**
	 * Copy tvv valued tdl lists.
	 * 
	 * @param tvvId the tvv id
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTDL> copyTvvValuedTDLLists(long tvvId, TVV newObj) {

		List<TvvValuedTvvDepTDL> tdlList = tvvValuedTDLFinder.getAllValuedTDL(tvvId);
		List<TvvValuedTvvDepTDL> copiedList = new ArrayList<>();
		for (TvvValuedTvvDepTDL oldTDL : tdlList) {
			TvvValuedTvvDepTDL copiedTDL = tvvValuedTvvDepTDLFactory.createTvvValuedTvvDepTDL(oldTDL);
			copiedTDL.setValuedTechnicalData(new ArrayList<TvvValuedTechData>());
			for (TvvValuedTechData genericData : valuedDataFinder.getAllValuedDataForList(oldTDL.getEntityId())) {
				TvvValuedTechData copiedData = valuedDataFactory.createValuedGenericTechData(genericData);
				copiedData.setTvvDepTDL(copiedTDL);
				copiedTDL.getValuedTechnicalData().add(copiedData);
			}

			copiedTDL.setTvvObj(newObj);
			copiedList.add(copiedTDL);

		}
		return copiedList;

	}

	/**
	 * Copy tvv valued fcl lists.
	 *
	 * @param tvvId the tvv id
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedEsDepFCL> copyTvvValuedFCLLists(long tvvId, TVV newObj) {

		List<TvvValuedEsDepFCL> fclList = tvvValuedEsDepFCLFinder.getAllValuedEsDepFCL(tvvId);
		List<TvvValuedEsDepFCL> copiedList = new ArrayList<>();
		for (TvvValuedEsDepFCL oldFCL : fclList) {
			TvvValuedEsDepFCL copiedFCL = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL(oldFCL);
			copiedFCL.setFactorOrCoeffiecients(new ArrayList<TvvValuedFactorCoefficents>());
			for (TvvValuedFactorCoefficents genericData : tvvValuedFactorCoefficentRepository.getAllFactorsForEmsDepList(oldFCL.getEntityId())) {
				TvvValuedFactorCoefficents copiedData = valuedFactorCoefficentFactory.createTvvValuedFactorCoefficent(genericData);
				copiedData.setFcList(copiedFCL);
				copiedFCL.getFactorOrCoeffiecients().add(copiedData);
			}

			copiedFCL.setTvvObj(newObj);
			copiedList.add(copiedFCL);

		}
		return copiedList;

	}

	/**
	 * Copy tvv valued tcl lists.
	 * 
	 * @param tvvId the tvv id
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTCL> copyTvvValuedTCLLists(long tvvId, TVV newObj) {

		List<TvvValuedTvvDepTCL> tclList = tvvValuedTCLFinder.getAllValuedTCL(tvvId);
		List<TvvValuedTvvDepTCL> copiedList = new ArrayList<>();
		for (TvvValuedTvvDepTCL oldTCL : tclList) {
			TvvValuedTvvDepTCL copiedTCL = tvvValuedTvvDepTCLFactory.createTvvValuedTvvDepTCL(oldTCL);
			copiedTCL.setGenericTestCondition(new ArrayList<TvvValuedTestCondition>());
			for (TvvValuedTestCondition genericData : valuedCondtionFinder.getAllValuedConditionForList(oldTCL.getEntityId())) {
				TvvValuedTestCondition copiedData = valuedConditionFactory.createTvvValuedTestCondition(genericData);
				copiedData.setTvvValuedTCL(copiedTCL);
				copiedTCL.getGenericTestCondition().add(copiedData);
			}

			copiedTCL.setTvvObj(newObj);
			copiedList.add(copiedTCL);

		}
		return copiedList;

	}

	/**
	 * Copy tvv valued es dep lists.
	 * 
	 * @param tvvId the tvv id
	 * @param emissionStandard the emission standard (This emission standard is associated to new TVV-this needs to be
	 *        also associated to new lists)
	 * @param newObj the new obj (NEW TVV object to which copied lists will be associated)
	 * @return the tvv
	 */
	private TVV copyTvvValuedEsDepLists(long tvvId, EmissionStandard emissionStandard, TVV newObj) {

		// Technical Data List
		List<TvvValuedEsDepTDL> tdlList = tvvValuedEsDepTDLFinder.getAllValuedEsDepTDL(tvvId, emissionStandard.getEntityId());

		List<TvvValuedEsDepTDL> copiedList = new ArrayList<>();
		for (TvvValuedEsDepTDL oldTDL : tdlList) {
			TvvValuedEsDepTDL copiedTDL = tvvValuedEsDepTDLFactory.createTvvValuedEsDepTDL(oldTDL);
			copiedTDL.setGenericTechnicalData(new ArrayList<TvvValuedTechData>());
			for (TvvValuedTechData genericData : valuedDataFinder.getAllValuedDataForEmsDepList(oldTDL.getEntityId())) {
				TvvValuedTechData copiedData = valuedDataFactory.createValuedGenericTechData(genericData);
				copiedData.setTvvValuedEsTDL(copiedTDL);
				copiedTDL.getGenericTechnicalData().add(copiedData);
			}

			copiedTDL.setTvvObj(newObj);
			copiedTDL.setEmissionStandard(emissionStandard);
			copiedList.add(copiedTDL);

		}
		newObj.setTvvValuedEsDepTDL(copiedList);
		// Test Condition List

		List<TvvValuedEsDepTCL> tclList = tvvValuedEsDepTCLFinder.getAllValuedEsDepTCL(tvvId, emissionStandard.getEntityId());
		List<TvvValuedEsDepTCL> copiedTCList = new ArrayList<>();
		for (TvvValuedEsDepTCL oldTCL : tclList) {
			TvvValuedEsDepTCL copiedTCL = tvvValuedEsDepTCLFactory.createTvvValuedEsDepTCL(oldTCL);
			copiedTCL.setGenericTestCondition(new ArrayList<TvvValuedTestCondition>());
			for (TvvValuedTestCondition genericData : valuedCondtionFinder.getAllValuedConditionForEmsDepList(oldTCL.getEntityId())) {
				TvvValuedTestCondition copiedData = valuedConditionFactory.createTvvValuedTestCondition(genericData);
				copiedData.setTvvValuedEsTCL(copiedTCL);
				copiedTCL.getGenericTestCondition().add(copiedData);
			}

			copiedTCL.setTvvObj(newObj);
			copiedTCL.setEmissionStandard(emissionStandard);
			copiedTCList.add(copiedTCL);

		}
		newObj.setTvvValuedEsDepTCL(copiedTCList);

		// POLLUTANT OR GAS LIMIT LIST
		List<TvvValuedEsDepPGL> pglList = tvvValuedEsDepPGLFinder.getAllValuedEsDepPGL(tvvId, emissionStandard.getEntityId());
		List<TvvValuedEsDepPGL> copiedPGList = new ArrayList<>();
		for (TvvValuedEsDepPGL oldPGL : pglList) {
			TvvValuedEsDepPGL copiedPGL = tvvValuedEsDepPGLFactory.createTvvValuedEsDepPGL(oldPGL);
			copiedPGL.setPollutantGasLimit(new ArrayList<TvvValuedPollutantGasLimit>());
			for (TvvValuedPollutantGasLimit genericData : valuedPollutantGasLimitFinder.getAllLimitsForEmsDepList(oldPGL.getEntityId())) {
				TvvValuedPollutantGasLimit copiedData = valuedPollutantGasLimiFactory.createTvvValuedPollutantGasLimit(genericData);
				copiedData.setTvvValuedEsDepPGL(copiedPGL);
				copiedPGL.getPollutantGasLimit().add(copiedData);
			}

			copiedPGL.setTvvObj(newObj);
			copiedPGL.setEmissionStandard(emissionStandard);
			copiedPGList.add(copiedPGL);

		}
		newObj.setTvvValuedEsDepPGL(copiedPGList);

		// Factor OR COEFFICENT LIST
		List<TvvValuedEsDepFCL> fcList = tvvValuedEsDepFCLFinder.getAllValuedEsDepFCL(tvvId, emissionStandard.getEntityId());
		List<TvvValuedEsDepFCL> copiedFCList = new ArrayList<>();
		for (TvvValuedEsDepFCL oldFCL : fcList) {
			TvvValuedEsDepFCL copiedFCL = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL(oldFCL);
			copiedFCL.setFactorOrCoeffiecients(new ArrayList<TvvValuedFactorCoefficents>());
			for (TvvValuedFactorCoefficents genericData : valuedFactorCoefficentFinder.getAllFactorsForEmsDepList(oldFCL.getEntityId())) {
				TvvValuedFactorCoefficents copiedData = valuedFactorCoefficentFactory.createTvvValuedFactorCoefficent(genericData);
				copiedData.setFcList(copiedFCL);
				copiedFCL.getFactorOrCoeffiecients().add(copiedData);
			}

			copiedFCL.setTvvObj(newObj);
			copiedFCL.setEmissionStandard(emissionStandard);
			copiedFCList.add(copiedFCL);

		}
		newObj.setTvvValuedEsDepFCL(copiedFCList);

		return newObj;

	}

	/**
	 * Gets the tvv valued es dep fcl.
	 *
	 * @param tvvId the tvv id
	 * @return the tvv valued es dep fcl
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvvaluedesdepfactor/{tvvId}")
	public Response getTvvValuedEsDepFCL(@PathParam("tvvId") String tvvId) {
		Long tvvID = Long.parseLong(tvvId);
		List<TvvValuedEsDepFCLRepresentation> tvvValuedEsDepFCLRepresentationList = tvvValuedEsDepFCLFinder.getAllValuedEsDepFCLForTVV(tvvID);
		return Response.ok(tvvValuedEsDepFCLRepresentationList).build();
	}

	/**
	 * Gets the tvv sampling rule.
	 *
	 * @param tvvId the tvv id
	 * @return the tvv sampling rule
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvsamplingrule/{tvvId}")
	public Response getTvvSamplingRule(@PathParam("tvvId") String tvvId) {
		Long tvvID = Long.parseLong(tvvId);
		TVV tvv = tvvRepository.loadTVV(tvvID);
		SamplingRuleListRepresentation samplingRuleListRepresentation = new SamplingRuleListRepresentation();
		List<SamplingRuleRepresentation> samplingRuleRepresentationListTvv = new ArrayList<>();
		if (tvv.getTechnicalCase() != null && tvv.getTechnicalCase().getTechnicalGroup() != null) {
			samplingRuleRepresentationListTvv = tvvValuedEsDepFCLFinder.getSamplingRuleForTG(tvv.getTechnicalCase().getTechnicalGroup().getSamplingLabel());
		}
		samplingRuleListRepresentation.setSamplingRuleRepresentationListTvv(samplingRuleRepresentationListTvv);
		List<SamplingRuleRepresentation> samplingRuleRepresentationList = samplingRuleFinder.getAllSamplingRule();
		List<SamplingRuleRepresentation> samplingRuleRepresentationListNew = new ArrayList<>();
		if (samplingRuleRepresentationListTvv != null && !samplingRuleRepresentationListTvv.isEmpty()) {

			for (int i = 0; i < samplingRuleRepresentationList.size(); i++) {
				if (!samplingRuleRepresentationListTvv.isEmpty()) {
					if (!samplingRuleRepresentationList.get(i).getLabel().equals(samplingRuleRepresentationListTvv.get(0).getLabel())) {
						samplingRuleRepresentationListNew.add(samplingRuleRepresentationList.get(i));
					}
				}
			}
		} else {
			samplingRuleRepresentationListNew.addAll(samplingRuleRepresentationList);
		}
		List<SamplingRuleRepresentation> samplingListToReturn = new ArrayList<>();
		Map<String, List<String>> testMap = new HashMap<>();
		for (Iterator iterator = samplingRuleRepresentationListNew.iterator(); iterator.hasNext();) {
			SamplingRuleRepresentation samplingRule = (SamplingRuleRepresentation) iterator.next();

			/// SamplingRuleRepresentation samplingRuleToReturn = new SamplingRuleRepresentation();
			// samplingRuleToReturn.setLabel(samplingRule.getLabel());

			List<String> currentLabels = testMap.get(samplingRule.getLabel());
			if (currentLabels == null) {
				currentLabels = new ArrayList<>();
				testMap.put(samplingRule.getLabel(), currentLabels);
			}
			currentLabels.add(samplingRule.getDescription());
		}

		for (Iterator<String> iterator = testMap.keySet().iterator(); iterator.hasNext();) {
			String label = iterator.next();

			SamplingRuleRepresentation samplingRuleToReturn = new SamplingRuleRepresentation();
			List<String> descriptions = testMap.get(label);

			samplingRuleToReturn.setDescriptions(descriptions);
			samplingRuleToReturn.setLabel(label);
			samplingListToReturn.add(samplingRuleToReturn);

		}
		samplingRuleListRepresentation.setSamplingRuleRepresentationList(samplingListToReturn);
		return Response.ok(samplingRuleListRepresentation).build();
	}

	/**
	 * Save tvv sampling rule.
	 *
	 * @param tvvId the tvv id
	 * @param sampleLabel the sample label
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tvvsamplingrulelabel/{tvvId}/{sampleLabel}")
	public Response saveTvvSamplingRule(@PathParam("tvvId") String tvvId, @PathParam("sampleLabel") String sampleLabel) {
		Long tvvID = Long.parseLong(tvvId);
		TVV tvv = tvvRepository.loadTVV(tvvID);
		TVV oldTvv = tvv;
		if (("None").equals(sampleLabel)) {
			tvv.setSamplingLabel(null);
		} else
			tvv.setSamplingLabel(sampleLabel);
		tvvService.saveTvvObject(false, tvv, oldTvv);
		return Response.ok().build();
	}

	/**
	 * Copy tvv valued tvv dep tdl lists.
	 *
	 * @param tdlList the tdl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTDL> copyTvvValuedTvvDepTDLLists(List<TvvValuedTvvDepTDLRepresentation> tdlList, TVV newObj) {

		List<TvvValuedTvvDepTDL> dataList = new ArrayList<>();

		if (tdlList != null && !tdlList.isEmpty()) {
			List<TvvValuedTvvDepTDLRepresentation> dataItems = tdlList;
			newObj.setTvvValuedTvvDepTDL(null);

			for (TvvValuedTvvDepTDLRepresentation dataItem : dataItems) {
				TvvValuedTvvDepTDL data = tvvValuedTDLFactory.createTvvValuedTDL();
				tvvValuedTDLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				dataList.add(data);

			}
			newObj.setTvvValuedTvvDepTDL(dataList);
		}

		return dataList;

	}

	/**
	 * Copy tvv valued es dep tdl lists.
	 *
	 * @param tdlList the tdl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedEsDepTDL> copyTvvValuedEsDepTDLLists(List<TvvValuedEsDepTDLRepresentation> tdlList, TVV newObj) {

		List<TvvValuedEsDepTDL> dataList = new ArrayList<>();

		if (tdlList != null && !tdlList.isEmpty()) {
			List<TvvValuedEsDepTDLRepresentation> dataItems = tdlList;
			newObj.setTvvValuedEsDepTDL(null);

			for (TvvValuedEsDepTDLRepresentation dataItem : dataItems) {
				TvvValuedEsDepTDL data = tvvValuedEsDepTDLFactory.createTvvValuedEsDepTDL();
				tvvValuedEsDepTDLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				data.setEmissionStandard(newObj.getTechnicalCase().getEmissionStandard());
				dataList.add(data);

			}
			newObj.setTvvValuedEsDepTDL(dataList);
		}

		return dataList;
	}

	/**
	 * Gets the country set.
	 *
	 * @param tvvRep the tvv rep
	 * @param tvv the tvv
	 * @return the country set
	 */
	private void getCountrySet(TvvRepresentation tvvRep, TVV tvv) {

		if (tvvRep.getCountryList() != null && !tvvRep.getCountryList().isEmpty()) {
			List<CountryRepresentation> countryRepList = tvvRep.getCountryList();
			Set<Country> countryList = new HashSet<>();

			for (CountryRepresentation countryRep : countryRepList) {
				Country country = countryFactory.createCountry();
				countryAssembler.doMergeAggregateWithDto(country, countryRep);
				countryList.add(country);
			}
			tvv.setCountrySet(countryList);
		}
	}

	/**
	 * Copy tvv valued tvv dep tcl lists.
	 *
	 * @param tclList the tcl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedTvvDepTCL> copyTvvValuedTvvDepTCLLists(List<TvvValuedTvvDepTCLRepresentation> tclList, TVV newObj) {

		List<TvvValuedTvvDepTCL> dataList = new ArrayList<>();

		if (tclList != null && !tclList.isEmpty()) {
			List<TvvValuedTvvDepTCLRepresentation> dataItems = tclList;
			newObj.setTvvValuedTvvDepTCL(null);

			for (TvvValuedTvvDepTCLRepresentation dataItem : dataItems) {
				TvvValuedTvvDepTCL data = tvvValuedTCLFactory.createTvvValuedTvvDepTCL();
				tvvValuedTCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				dataList.add(data);

			}
			newObj.setTvvValuedTvvDepTCL(dataList);
		}

		return dataList;

	}

	/**
	 * Copy tvv valued es dep tdl lists.
	 *
	 * @param tclList the tdl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedEsDepTCL> copyTvvValuedEsDepTCLLists(List<TvvValuedEsDepTCLRepresentation> tclList, TVV newObj) {

		List<TvvValuedEsDepTCL> dataList = new ArrayList<>();

		if (tclList != null && !tclList.isEmpty()) {
			List<TvvValuedEsDepTCLRepresentation> dataItems = tclList;
			newObj.setTvvValuedEsDepTCL(null);

			for (TvvValuedEsDepTCLRepresentation dataItem : dataItems) {
				TvvValuedEsDepTCL data = tvvValuedEsDepTCLFactory.createTvvValuedEsDepTCL();
				tvvValuedEsDepTCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				data.setEmissionStandard(newObj.getTechnicalCase().getEmissionStandard());
				dataList.add(data);

			}
			newObj.setTvvValuedEsDepTCL(dataList);
		}

		return dataList;
	}

	/**
	 * Copy tvv valued es dep pgl lists.
	 *
	 * @param pglList the pgl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedEsDepPGL> copyTvvValuedEsDepPGLLists(List<TvvValuedEsDepPGLRepresentation> pglList, TVV newObj) {

		List<TvvValuedEsDepPGL> dataList = new ArrayList<>();
		if (pglList != null && !pglList.isEmpty()) {
			List<TvvValuedEsDepPGLRepresentation> dataItems = pglList;
			newObj.setTvvValuedEsDepPGL(null);

			for (TvvValuedEsDepPGLRepresentation dataItem : dataItems) {
				TvvValuedEsDepPGL data = tvvValuedEsDepPGLFactory.createTvvValuedEsDepPGL();
				tvvValuedEsDepPGLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				data.setEmissionStandard(newObj.getTechnicalCase().getEmissionStandard());
				dataList.add(data);

			}
			newObj.setTvvValuedEsDepPGL(dataList);
		}

		return dataList;
	}

	/**
	 * Copy tvv valued es dep fcl lists.
	 *
	 * @param fclList the fcl list
	 * @param newObj the new obj
	 * @return the list
	 */
	private List<TvvValuedEsDepFCL> copyTvvValuedEsDepFCLLists(List<TvvValuedEsDepFCLRepresentation> fclList, TVV newObj) {

		List<TvvValuedEsDepFCL> dataList = new ArrayList<>();

		if (fclList != null && !fclList.isEmpty()) {
			List<TvvValuedEsDepFCLRepresentation> dataItems = fclList;
			newObj.setTvvValuedEsDepFCL(null);

			for (TvvValuedEsDepFCLRepresentation dataItem : dataItems) {
				TvvValuedEsDepFCL data = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL();
				tvvValuedEsDepFCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(newObj);
				data.setEmissionStandard(newObj.getTechnicalCase().getEmissionStandard());
				dataList.add(data);

			}
			newObj.setTvvValuedEsDepFCL(dataList);
		}

		return dataList;
	}

}

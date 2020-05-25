package com.inetpsa.poc00.infrastructure.tvvmigration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.TvvMigrationService;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkRepository;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandRepository;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryRepository;
import com.inetpsa.poc00.domain.coastdown.CoastDown;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryRepository;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineRepository;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLRepository;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListRepository;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffAppFactory;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioRepository;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelRepository;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxRepository;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitListRepository;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusRepository;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechGroupRepository;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureRepository;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvv.TVVFactory;
import com.inetpsa.poc00.domain.tvv.TVVMigrationDto;
import com.inetpsa.poc00.domain.tvv.TVVRepository;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTCLRepository;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDL;
import com.inetpsa.poc00.domain.tvvdependent.TvvDepTDLRepository;
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
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLFactory;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimitFactory;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestConditionFactory;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechDataFactory;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDLFactory;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertia;
import com.inetpsa.poc00.domain.valuedinertia.ValuedInertiaFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechRepository;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * The Class TvvMigrationServiceImpl.
 */
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TVVMigrationServiceImpl implements TvvMigrationService {

	/** The tg repo. */
	@Inject
	private TechGroupRepository tgRepo;

	/** The project code family repository. */
	@Inject
	ProjectCodeFamilyRepository pcfRepo;

	/** The country repo. */
	@Inject
	CountryRepository countryRepo;

	/** The engine repo. */
	@Inject
	EngineRepository engineRepo;

	/** The gear box repo. */
	@Inject
	GearBoxRepository gearBoxRepo;

	/** The body work repo. */
	@Inject
	BodyworkRepository bodyWorkRepo;

	/** The ems repo. */
	@Inject
	EmissionStandardRepository emsRepo;

	/** The valued coast down repo. */
	@Inject
	CoastDownRepository coastDownRepo;

	/** The status repo. */
	@Inject
	StatusRepository statusRepo;

	/** The test nature repo. */
	@Inject
	TestNatureRepository testNatureRepo;

	/** The fuel repo. */
	@Inject
	FuelRepository fuelRepo;

	/** The car brand repo. */
	@Inject
	CarBrandRepository carBrandRepo;

	/** The frr repo. */
	@Inject
	FinalReductionRatioRepository frrRepo;

	/** The tvv repo. */
	@Inject
	TVVRepository tvvRepo;

	/** The ems dep tdl repo. */
	@Inject
	private EmissionStdDepTDLRepository emsDepTDLRepo;

	/** The ems dep tcl repository. */
	@Inject
	private EmissionStdDepTCLRepository emsDepTCLRepository;

	/** The factor coeff list repo. */
	@Inject
	private FactorCoeffListRepository factorCoeffListRepo;

	/** The pgl list repo. */
	@Inject
	private PollutantGasLimitListRepository pglListRepo;

	/** The tvv dep tdl repo. */
	@Inject
	private TvvDepTDLRepository tvvDepTDLRepo;

	/** The tvv dep tcl repo. */
	@Inject
	private TvvDepTCLRepository tvvDepTCLRepo;
	
	/** The veh tech repo. */
	@Inject 
	private VehicleTechRepository vehTechRepo;
	 
	/** The cat repo. */
	@Inject
	private CategoryRepository catRepo;

	/** The tvv valued tvv dep tdl factory. */
	@Inject
	private TvvValuedTvvDepTDLFactory tvvValuedTvvDepTDLFactory;

	/** The tvv valued tvv dep tcl factory. */
	@Inject
	private TvvValuedTvvDepTCLFactory tvvValuedTvvDepTCLFactory;

	/** The tvv factory. */
	@Inject
	TVVFactory tvvFactory;

	/** The technical case factory. */
	@Inject
	TechCaseFactory technicalCaseFactory;

	/** The tvv valued es dep fcl factory. */
	@Inject
	TvvValuedEsDepFCLFactory tvvValuedEsDepFCLFactory;

	/** The tvv valued es dep pgl factory. */
	@Inject
	TvvValuedEsDepPGLFactory tvvValuedEsDepPGLFactory;

	/** The tvv valued factor coefficents factory. */
	@Inject
	TvvValuedFactorCoefficentsFactory tvvValuedFactorCoefficentsFactory;

	/** The tvv valued pollutant gas limit factory. */
	@Inject
	TvvValuedPollutantGasLimitFactory tvvValuedPollutantGasLimitFactory;

	/** The tvv valued es dep tcl factory. */
	@Inject
	TvvValuedEsDepTCLFactory tvvValuedEsDepTCLFactory;

	/** The tvv valued es dep tdl factory. */
	@Inject
	TvvValuedEsDepTDLFactory tvvValuedEsDepTDLFactory;

	/** The tvv valued test condition factory. */
	@Inject
	TvvValuedTestConditionFactory tvvValuedTestConditionFactory;

	/** The factor coeff app factory. */
	@Inject
	FactorCoeffAppFactory factorCoeffAppFactory;

	/** The tvv valued tech data factory. */
	@Inject
	TvvValuedTechDataFactory tvvValuedTechDataFactory;

	/** The tvv valued coast down factory. */
	@Inject
	TvvValuedCoastDownFactory tvvValuedCoastDownFactory;

	/** The valued inertia factory. */
	@Inject
	ValuedInertiaFactory valuedInertiaFactory;

	/** The fnl red rtn data. */
	private Map<String, FinalReductionRatio> fnlRedRtnData;

	/** The fuel map. */
	private Map<String, Fuel> fuelData;

	/** The status repo. */
	private Map<String, Status> statusData;

	/** The tg list. */
	private Map<String, TechnicalGroup> tgData;

	/** The pcf map. */
	private Map<String, ProjectCodeFamily> pcfData;

	/** The country map. */
	private Map<String, Country> countryData;

	/** The engine map. */
	private Map<String, Engine> engineData;

	/** The gear box map. */
	private Map<String, GearBox> gearBoxData;

	/** The body work map. */
	private Map<String, Bodywork> bodyworkData;

	/** The tvv valued coast down map. */
	private Map<String, CoastDown> coastDownData;

	/** The emission standard map. */
	private Map<String, EmissionStandard> emissionStandardData;

	/** The test nature map. */
	private Map<String, TestNature> testNatureData;

	/** The car brand map. */
	private Map<String, CarBrand> carBrandData;
	
	/** The category data. */
	private Map<String, Category> categoryData;
	
	/** The veh tech data. */
	private Map<String, VehicleTechnology> vehTechData;
	
	/** The tvvlabel data. */
	List<String> tvvlabelData;
	
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(TVVMigrationServiceImpl.class);

	/** The seprator. */
	private char separator;

	/**
	 * Instantiates a new tvv migration service impl.
	 */
	public TVVMigrationServiceImpl() {
		this.separator = ';' ;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.TvvMigrationService#tvvCSVProcessor(java.io.File, java.lang.String)
	 */
	@Override
	public void tvvCSVProcessor(File file, String tvvMigrationFileDirectory) throws IOException {

		try {

			loadTVVAdminData();

			loadTVVInputCSV(file);

		} catch (Exception e) {
			logger.error("ERROR IN PROCESSING TVV DATA", e);
		}

	}

	/**
	 * Load csv.
	 *
	 * @param csvFile the csv file
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadTVVInputCSV(File csvFile) throws IOException {

		String[] nextLine;
		int lineCounter = 0;
		
		String logLine = "=============================================================================";
		
		logger.info(logLine);
		logger.info("********************** START : PROCESSING INPUT DATA  ***********************");
		logger.info(logLine);
		
		try(FileInputStream fileInputStream = new FileInputStream(csvFile);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"ISO-8859-1");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				CSVReader csvReader = new CSVReader(bufferedReader, this.separator);) {

			String[] headerRow = csvReader.readNext();

			headerRow = (String[]) ArrayUtils.remove(headerRow, headerRow.length - 1);

			if (null == headerRow) {
				logger.error("HEADER IS NOT PRESENT IN THE EXCEL FILE");
			}

			while ((nextLine = csvReader.readNext()) != null) {
				
				lineCounter++;

				for (int i = 0; i < nextLine.length; i++) {
					nextLine[i] = nextLine[i].trim(); 
				}
				
				logger.info("Number of parameter in the Line : {}, Line Number : {}", nextLine.length,lineCounter);
					
				int index = 0;
					TVVMigrationDto tvvDto = new TVVMigrationDto(
							nextLine[index++] /*Regulation Group*/	,nextLine[index++] /*Technical Group*/		,nextLine[index++] /*Type Approval Area*/,
							nextLine[index++] /*country*/			,nextLine[index++]/*vehicle Family Label*/	,nextLine[index++] /*Engine Label*/,
							nextLine[index++] /*Gear Box Label*/	,nextLine[index++]/*Tvv Label*/				,nextLine[index++] /*BodyWork Label*/,
							nextLine[index++]/*Power (CV)*/			,nextLine[index++]/* Power (kW)*/			,nextLine[index++] /*Torque (Nm)*/,
							nextLine[index++]/*Inertia*/,
							nextLine[index++]/*Catalyseur ancien*/	,nextLine[index++]/*Catalyseur actuel*/, 
							nextLine[index++]/* Particularité*/		,nextLine[index++]/* REMARQUES*/			,nextLine[index++]/*Coast-down*/,
							nextLine[index++]/*Limite CO2*/,
							nextLine[index++]/*Cycle*/,
							nextLine[index++]/*Limite*/			    ,nextLine[index++]/*Version Réglementation*/ ,nextLine[index++]/*CARBURANT*/,
							
							nextLine[index++]/*Theorical f0*/,
							nextLine[index++]/*Theorical f1*/,
							nextLine[index++]/*Theorical f2*/,
							nextLine[index++]/*Computed Bench f0*/,
							nextLine[index++]/*User Bench f0*/,
							nextLine[index++]/*Computed Bench f1*/,
							nextLine[index++] /*User Bench f1*/,
							nextLine[index++]/*Computed Bench f2*/,
							nextLine[index++]/*User Bench f2*/,
						
							nextLine[index++]/*Pente*/				,nextLine[index++]/*FD Forfaitaire*/,
							nextLine[index++]/*CO*/,
							nextLine[index++]/*HC*/,
							nextLine[index++]/*NMHC*/	,
							nextLine[index++]/*NOX*/,
							nextLine[index++]/*HC+NOX*/,
							nextLine[index++]/*PART Masse*/,
							nextLine[index++]/*PART Nombre*/,
							nextLine[index++]/*CO2*/,
							
							nextLine[index++]/*Coef Evol*/,
							nextLine[index++]/*CO*/,
							nextLine[index++]/*HC*/,
							nextLine[index++]/*NMHC*/,
							nextLine[index++]/*NOX*/,
							nextLine[index++]/*HC+NOX*/,
							nextLine[index++]/*PART Masse*/,
							nextLine[index++]/*PART Nombre*/,
							nextLine[index++]/*CO2*/,
							
							nextLine[index++]/*Coef FAP*/,
							nextLine[index++]/*CO*/,	
							nextLine[index++]/*HC*/,
							nextLine[index++]/*NOX*/,
							nextLine[index++]/*HC+NOX*/,	
							nextLine[index++]/*PART Masse*/,
							nextLine[index++]/*PART Nbre*/,
							nextLine[index++]/*CO2*/,
							
							nextLine[index++]/*HC*/,
							nextLine[index++]/*NMHC*/,
							nextLine[index++]/*NOX*/,
							nextLine[index++]/*CO*/,
							nextLine[index++]/*HC+NOX*/,
							nextLine[index++]/*PARTICULES masse*/,
							nextLine[index++]/*PARTICULES Nombre*/,	
							
							nextLine[index++]/*EOBD */				,nextLine[index++]/*IUPR*/,		
							nextLine[index++]/*N° Soft OBD ancien*/	,nextLine[index++]/*N° Soft OBD actuel*/	,nextLine[index++]/*Remarques EOBD*/,
							nextLine[index++]/*MLOG*/				,nextLine[index++]/*Calculateur*/			,nextLine[index++]/*Injection*/,
							nextLine[index++]/*Opacite*/			,nextLine[index++]/*Regime*/	  			,nextLine[index++]/*SOC Prépa*/,
							nextLine[index++]/*SOC Avant test*/		,nextLine[index++]/*Pocket BSI*/			,nextLine[index++]/*Manchon*/,
							nextLine[index++]/*APPRENTISSAGE PILOTE*/,nextLine[index++]/*PHASE 1*/				,nextLine[index++]/*PHASE 2*/,
							nextLine[index++]/*PHASE 1*/			,nextLine[index++]/*PHASE 2*/				,nextLine[index++]/*PHASE 3*/,
							nextLine[index++]/*Débit DLS*/			,nextLine[index++]/*Phases SAC*/			,nextLine[index++]/*Phases PARTICULES*/,
							nextLine[index++]/*OBSERVATIONS PREPA*/	,nextLine[index++]/*OBSERVATIONS TEST*/		,nextLine[index++]/*PV UTAC POLLU*/,
							nextLine[index++]/*PV UTAC CONSO*/		,nextLine[index++]/*Etape Emissions*/		,nextLine[index++]/*Etape OBD*/,
							nextLine[index++]/*OBSERVATIONS 1*/		,nextLine[index++]/*OBSERVATIONS 2*/		,nextLine[index++]/*Catégorie*/,
							nextLine[index++]/*Classe*/				,nextLine[index++]/*Statut*/				,nextLine[index++]/*Nature de test*/,
							nextLine[index++]/*Vehicle Technology*/	,nextLine[index++]/*Sampling Rule*/			,nextLine[index++]/*Car Brand Label*/,
							nextLine[index++]/*Factory*/		    ,nextLine[index++]/*Final Reduction Ratio*/);
					
					
					if (validateTVVData(tvvDto)) {
						/* Create TVV Object and Save */
						createTVVObject(tvvDto);

					} else {
						logger.info("############### TVV Validation Failed, Label : {}]\n", tvvDto.getTvvLabel());
					}
			}
		} catch (ParseException e) {
			logger.error("Issue in parsing, LINE IGNORED : {}", lineCounter, e);
		} catch (NumberFormatException e) {
			logger.error("LINE IGNORED : {}", lineCounter, e);
		} catch (FileNotFoundException fnf) {
			logger.error("Error in Loading file, Method : TVVDataLoader", fnf);
		} catch (IOException ioe) {
			logger.error("Error in Loading file, Method : TVVDataLoader", ioe);
		}
		
		logger.info(logLine);
		logger.info("******************** COMPLETED : PROCESSING INPUT DATA  *********************");
		logger.info(logLine);

	}

	/**
	 * Creates the tvv object.
	 * 
	 * @param tvvDto the tvv
	 */
	private void createTVVObject(TVVMigrationDto tvvDto) {
		
		logger.info("############### Creating TVV Object with [Tvv Label : {}]",tvvDto.getTvvLabel());

		TVV tvvObj = tvvFactory.createTVV();
		tvvObj.setLabel(tvvDto.getTvvLabel());
		tvvObj.setVersion("1.0");
		tvvObj.setCreationDate(new Date());
		tvvObj.setModificationDate(new Date());

		tvvObj.setBodyWork(bodyworkData.get(tvvDto.getBodyWorkLabel()));
		tvvObj.setEngine(engineData.get(tvvDto.getEngineLabel()));
		tvvObj.setProjectCodeFamily(pcfData.get(tvvDto.getVehicleFamilyLabel()));
		tvvObj.setGearBox(gearBoxData.get(tvvDto.getGearBoxLabel()));
		tvvObj.setFinalReductionRatio(fnlRedRtnData.get(tvvDto.getFinalReductionRatioLabel()));
		tvvObj.setFuel(fuelData.get(tvvDto.getFuelLabel()));
		tvvObj.setStatus(statusData.get(tvvDto.getStatusLabel()));
		tvvObj.setTestNature(testNatureData.get(tvvDto.getTestNatureLabel()));
		tvvObj.setCategory(categoryData.get(tvvDto.getCategoryLabel()));
		tvvObj.setVehicalTechnology(vehTechData.get(tvvDto.getVehicleTechnology()));

		TechnicalCase technicalCase = technicalCaseFactory.createTechCase();

		EmissionStandard es = emissionStandardData.get(tvvDto.getEmissionStandardLabel() + " - " + tvvDto.getEmissionStandardVersion());
		
		createESDepFCLToTVV(es, tvvObj);
		createESDepPGLToTVV(es, tvvObj);
		createESDepTCLToTVV(es, tvvObj);
		createESDepTDLToTVV(es, tvvObj);
		createTvvDepTDLToTVV(tvvObj);
		createTvvDepTCLToTVV(tvvObj);

		technicalCase.setEmissionStandard(es);
		technicalCase.setTechnicalGroup(tgData.get(tvvDto.getTechnicalGroup()));
		technicalCase.setTvv(tvvObj);

		tvvObj.setTechnicalCase(technicalCase);

		CoastDown coastDown = coastDownData.get(tvvDto.getCoastDownPsaReference().trim() + " - " + tvvDto.getInertiaValue());
		copyCoastDownToTvv(coastDown, tvvObj);

		String carBrandLabels = tvvDto.getCarMakerBrandLabel();
		Set<CarBrand> carBrandSet = new HashSet<>();
		carBrandSet.add(carBrandData.get(carBrandLabels));
		tvvObj.setCarBrandSet(carBrandSet);

		updateTvvValuedTvvDepTDL(tvvObj, tvvDto);
		updateTvvValuedESDepTDL(tvvObj, tvvDto);
		updateTvvValuedTvvDepTCL(tvvObj, tvvDto);
		updateTvvValuedEsDepTCL(tvvObj, tvvDto);
		updateTvvValuedEsDepPGL(tvvObj, tvvDto);
		updateTvvValuedEsDepFCL(tvvObj, tvvDto);

		tvvObj = tvvRepo.saveTVV(tvvObj);
		
		logger.info("############### Saved TVV Object with [Tvv Id : {}]",tvvObj.getEntityId());

	}

	/**
	 * Prepare tvv valued es dep fcl.
	 * 
	 * @param temp the temp
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedEsDepFCL(TVV temp, TVVMigrationDto tvvDto) {
		for (TvvValuedEsDepFCL obj : temp.getTvvValuedEsDepFCL()) {

			for (TvvValuedFactorCoefficents obj2 : obj.getFactorOrCoeffiecients()) {

				if ("CO".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCO());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCoefEvolCO());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPCO());
					}
				}

				if ("HC".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValHC());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCoefEvolHC());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPHC());
					}
				}

				if ("NMHC".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValNMHC());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCoefEvolNMHC());
					}

				}

				if ("NOX".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValNOX());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCoefEvolNOX());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPNOX());
					}
				}

				if ("HC+NOX".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValHCNOX());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						tvvDto.setTvvValdFCDefaultValCoefEvolHCNOX(obj2.getDefaultValue());
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefEvolHCNOX());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPHCNOX());
					}
				}

				if ("PART Masse".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValPartMasse());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefEvolPartMasse());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPPartMasse());
					}
				}

				if ("PART Nombre".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValPartNombre());
					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						tvvDto.setTvvValdFCDefaultValCoefEvolPartNombre(obj2.getDefaultValue());
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefEvolPartNombre());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPPartNbre());
					}
				}

				if ("CO2".equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

					if ("FD".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvVldFCDefaultValCO2());

					}

					else if ("Coef Evol".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefEvolCO2());
					}

					else if ("Coef FAP".equalsIgnoreCase(obj2.getFclabel().getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdFCDefaultValCoefFAPCO2());
					}
				}
			}
		}
	}

	/**
	 * Prepare tvv valued es dep tcl.
	 * 
	 * @param temp the temp
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedEsDepTCL(TVV temp, TVVMigrationDto tvvDto) {
		for (TvvValuedEsDepTCL obj : temp.getTvvValuedEsDepTCL()) {

			for (TvvValuedTestCondition obj2 : obj.getGenericTestCondition()) {

				if ("Cycle".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvVldTCCycle());
				}

				else if ("Pente".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvVldTCDefaultValPENTE());
				}

				else if ("MLOG".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCMLog());
				}

				else if ("Regime".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValueRegime());
				}

				else if ("Pocket BSI".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValuePocketBSI());
				}

				else if ("Manchon".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValManchon());
				}

				else if ("APPRENTISSAGE PILOTE".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValuedTCValAppren());
				}

				if ("Préparation".equalsIgnoreCase(obj.getLabel())) {

					if ("SOC Prepa".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCDefValueSOCA());
					}

					else if ("SOC Avant test".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCDefValSOCAAvant());
					}

					else if ("Opacite".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCDefaultValue());
					}
				}

				if ("Aspiration".equalsIgnoreCase(obj.getLabel())) {

					if ("PHASE 1".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValPhase1());
					}

					else if ("PHASE 2".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValPhase2());
					}

					else if ("PHASE 3".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValPhase3());
					}

					else if ("Débit DLS".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValDebitDLS());
					}

					else if ("Phases SAC".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValPhasesSAC());
					}

					else if ("Phases PARTICULES".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setDefaultValue(tvvDto.getTvvValdTCValPhasesParti());
					}
				}
			}
		}
	}

	/**
	 * Prepare tvv valued tvv dep tcl.
	 * 
	 * @param temp the temp
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedTvvDepTCL(TVV temp, TVVMigrationDto tvvDto) {
		for (TvvValuedTvvDepTCL obj : temp.getTvvValuedTvvDepTCL()) {

			for (TvvValuedTestCondition obj2 : obj.getValuedTestCondition()) {

				if ("Cycle".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvVldTCCycle());
				}

				else if ("Pente".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvVldTCDefaultValPENTE());
				}

				else if ("MLOG".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCMLog());
				}

				else if ("Regime".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValueRegime());
				}

				else if ("Pocket BSI".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValuePocketBSI());
				}

				else if ("Manchon".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValdTCValManchon());
				}

				else if ("APPRENTISSAGE PILOTE".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setDefaultValue(tvvDto.getTvvValuedTCValAppren());
				}

			}
		}
	}

	/**
	 * Prepare tvv valued es dep pgl.
	 * 
	 * @param tvvEntity the tvv entity
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedEsDepPGL(TVV tvvEntity, TVVMigrationDto tvvDto) {
		for (TvvValuedEsDepPGL obj : tvvEntity.getTvvValuedEsDepPGL()) {

			for (TvvValuedPollutantGasLimit obj2 : obj.getPollutantGasLimit()) {

				if ("Lim_CO2".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
					obj2.setMaxDValue(tvvDto.getMaxDefaultValTvvVldPGL());
				}

				if ("Limites".equalsIgnoreCase(obj.getLabel()) && obj2.getPgLabel() != null) {

					if ("HC".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValHC());
					}

					else if ("NMHC".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValNMHC());
					}

					else if ("NOX".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValNOX());
					}

					else if ("CO".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValCO());
					}

					else if ("HC+NOX".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValHCNOX());
					}

					else if ("PARTICULES masse".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValPartMasse());
					}

					else if ("PARTICULES Nombre".equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
						obj2.setMaxDValue(tvvDto.getTvvValdPGMaxDefaultValPartNombre());
					}
				}

			}

		}
	}

	/**
	 * Prepare tvv valued tvv dep tdl.
	 * 
	 * @param tvvEntity the tvv entity
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedTvvDepTDL(TVV tvvEntity, TVVMigrationDto tvvDto) {
		for (TvvValuedTvvDepTDL obj : tvvEntity.getTvvValuedTvvDepTDL()) {

			for (TvvValuedTechData obj2 : obj.getValuedTechnicalData()) {

				// Catalyseur ancien
				if ("Catalyseur ancien".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueAncien());
				}

				// Catalyseur actuel
				else if ("Catalyseur actuel".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueActuel());
				}

				// REMARQUES
				else if ("Remarques".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueRemarques());
				}

				// Calculateur
				else if ("Calculateur".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValCalclr());
				}

				// PV UTAC POLLU
				else if ("PV UTAC POLLU".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValPollu());
				}

				// PV UTAC CONSO
				else if ("PV UTAC CONSO".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValConso());
				}

				// Etape Emissions
				else if ("Etape Emissions".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValEtapeEmission());
				}

				// Etape OBD
				else if ("Etape OBD".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValEtapeOBD());
				}

				// OBSERVATIONS 1
				else if ("Observations 1".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValTDValObsrv1());
				}

				// OBSERVATIONS 2
				else if ("Observations 2".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValTDValObsrv2());
				}

			}
		}
	}

	/**
	 * Prepare tvv valued es dep tdl.
	 * 
	 * @param tvvEntity the tvv entity
	 * @param tvvDto the tvv
	 */
	private void updateTvvValuedESDepTDL(TVV tvvEntity, TVVMigrationDto tvvDto) {
		for (TvvValuedEsDepTDL obj : tvvEntity.getTvvValuedEsDepTDL()) {

			for (TvvValuedTechData obj2 : obj.getGenericTechnicalData()) {

				// Catalyseur ancien
				obj2.setValue(tvvDto.getTvvVldTDValueAncien());
				if ("Catalyseur ancien".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueAncien());
				}

				// Catalyseur actuel
				else if ("Catalyseur actuel".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueActuel());
				}

				// REMARQUES
				else if ("Remarques".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvVldTDValueRemarques());
				}

				// Calculateur
				else if ("Calculateur".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValCalclr());
				}

				// PV UTAC POLLU
				else if ("PV UTAC POLLU".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValPollu());
				}

				// PV UTAC CONSO
				else if ("PV UTAC CONSO".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValConso());
				}

				// Etape Emissions
				else if ("Etape Emissions".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValEtapeEmission());
				}

				// Etape OBD
				else if ("Etape OBD".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValdTDValEtapeOBD());
				}

				// OBSERVATIONS 1
				else if ("Observations 1".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValTDValObsrv1());
				}

				// OBSERVATIONS 2
				else if ("Observations 2".equalsIgnoreCase(obj2.getLabel())) {
					obj2.setValue(tvvDto.getTvvValTDValObsrv2());
				}

				if ("EOBD".equalsIgnoreCase(obj.getLabel())) {

					if ("EOBD".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setValue(tvvDto.getTvvValdTDValEOBD());
					}

					else if ("IUPR".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setValue(tvvDto.getTvvValdTDValIUPR());
					}

					else if ("Num_Soft_OBD_Ancien".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setValue(tvvDto.getTvvValdNSoftAncien());
					}

					else if ("Num_Soft_OBD_Actuel".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setValue(tvvDto.getTvvValdNSoftActuel());
					}

					else if ("Remarques EOBD".equalsIgnoreCase(obj2.getLabel())) {
						obj2.setValue(tvvDto.getTvvValdTDValEOBDRemarques());
					}
				}
			}

		}
	}

	/**
	 * Creates the tvv dep tcl to tvv.
	 * 
	 * @param tvvObj the tvv obj
	 */
	private void createTvvDepTCLToTVV(TVV tvvObj) {

		List<TvvValuedTvvDepTCL> valuedTvvDepTCL = new ArrayList<>();

		List<TvvDepTCL> tvvDepTCL = tvvDepTCLRepo.getAllTvvDependentLists();

		for (TvvDepTCL tcl : tvvDepTCL) {

			TvvValuedTvvDepTCL tvvValuedTvvDepTCL = tvvValuedTvvDepTCLFactory.createTvvValuedTvvDepTCL();
			tvvValuedTvvDepTCL.setValuedTestCondition(new ArrayList<TvvValuedTestCondition>());

			tvvValuedTvvDepTCL.setDescription(tcl.getLabel());
			tvvValuedTvvDepTCL.setEntityId(0L);
			tvvValuedTvvDepTCL.setLabel(tcl.getLabel());
			tvvValuedTvvDepTCL.setTvvObj(tvvObj);
			tvvValuedTvvDepTCL.setVersion(tcl.getVersion());

			for (GenericTestCondition testCondtn : tcl.getGenericTestCondition()) {

				TvvValuedTestCondition tvvValuedTC = tvvValuedTestConditionFactory.createTvvValuedTestCondition();

				tvvValuedTC.setDataType(testCondtn.getDataType());
				tvvValuedTC.setDefaultValue(testCondtn.getDefaultValue());
				tvvValuedTC.setEntityId(0L);
				tvvValuedTC.setLabel(testCondtn.getLabel());
				tvvValuedTC.setTvvValuedTCL(tvvValuedTvvDepTCL);
				tvvValuedTC.setUnit(testCondtn.getUnit());

				tvvValuedTvvDepTCL.getValuedTestCondition().add(tvvValuedTC);
			}
			valuedTvvDepTCL.add(tvvValuedTvvDepTCL);
		}
		tvvObj.setTvvValuedTvvDepTCL(valuedTvvDepTCL);
	}

	/**
	 * Creates the tvv dep tdl to tvv.
	 * 
	 * @param tvvObj the tvv obj
	 */
	private void createTvvDepTDLToTVV(TVV tvvObj) {

		List<TvvValuedTvvDepTDL> valuedTvvDepTDL = new ArrayList<>();

		List<TvvDepTDL> tvvDepTDL = tvvDepTDLRepo.getAllTvvDependentListByLatestVer();

		for (TvvDepTDL tdl : tvvDepTDL) {

			TvvValuedTvvDepTDL tvvValuedTvvDepTDL = tvvValuedTvvDepTDLFactory.createTvvValuedTDL();
			tvvValuedTvvDepTDL.setValuedTechnicalData(new ArrayList<TvvValuedTechData>());

			tvvValuedTvvDepTDL.setDescription(tdl.getDescription());
			tvvValuedTvvDepTDL.setEntityId(0L);
			tvvValuedTvvDepTDL.setLabel(tdl.getLabel());
			tvvValuedTvvDepTDL.setVersion(tdl.getVersion());
			tvvValuedTvvDepTDL.setTvvObj(tvvObj);

			for (GenericTechnicalData techData : tdl.getGenericTechnicalData()) {

				TvvValuedTechData tvvValuedTd = tvvValuedTechDataFactory.createTvvValuedTechData();

				tvvValuedTd.setDataType(techData.getDataType());
				tvvValuedTd.setEntityId(0L);
				tvvValuedTd.setLabel(techData.getLabel());
				tvvValuedTd.setTvvDepTDL(tvvValuedTvvDepTDL);
				tvvValuedTd.setUnit(techData.getUnit());
				tvvValuedTd.setValue(techData.getDefaultValue());
				tvvValuedTd.setGenericData(techData);

				tvvValuedTvvDepTDL.getValuedTechnicalData().add(tvvValuedTd);
			}
			valuedTvvDepTDL.add(tvvValuedTvvDepTDL);
		}
		tvvObj.setTvvValuedTvvDepTDL(valuedTvvDepTDL);
	}

	/**
	 * Copy coast down to tvv.
	 * 
	 * @param coastDown the coast down
	 * @param tvvObj the tvv obj
	 */
	private void copyCoastDownToTvv(CoastDown coastDown, TVV tvvObj) {

		TvvValuedCoastDown tvvValuedCoastDown = tvvValuedCoastDownFactory.createValuedCoastDown();

		tvvValuedCoastDown.setEntityId(0);
		tvvValuedCoastDown.setTvvObj(tvvObj);
		tvvValuedCoastDown.setpSAReference(coastDown.getpSAReference());
		tvvValuedCoastDown.setVersion(coastDown.getVersion());
		tvvValuedCoastDown.setRoadLaw(coastDown.getRoadLaw());
		tvvValuedCoastDown.setComputedBenchF0(coastDown.getComputedBenchF0());
		tvvValuedCoastDown.setComputedBenchF1(coastDown.getComputedBenchF1());
		tvvValuedCoastDown.setComputedBenchF2(coastDown.getComputedBenchF2());
		tvvValuedCoastDown.setTheoricalBenchF0(coastDown.getTheoricalBenchF0());
		tvvValuedCoastDown.setTheoricalBenchF1(coastDown.getTheoricalBenchF1());
		tvvValuedCoastDown.setTheoricalBenchF2(coastDown.getTheoricalBenchF2());
		tvvValuedCoastDown.setUserBenchF0(coastDown.getUserBenchF0());
		tvvValuedCoastDown.setUserBenchF1(coastDown.getUserBenchF1());
		tvvValuedCoastDown.setUserBenchF2(coastDown.getUserBenchF2());

		ValuedInertia valuedInertia = valuedInertiaFactory.createValuedInertia();
		valuedInertia.setEntityId(0);
		valuedInertia.setValue(coastDown.getInertia().getValue());

		tvvValuedCoastDown.setInertia(valuedInertia);

		tvvObj.setTvvValuedCoastDown(tvvValuedCoastDown);
	}

	/**
	 * Creates the es dep tdl to tvv.
	 * 
	 * @param es the es
	 * @param tvvObj the tvv obj
	 */
	private void createESDepTDLToTVV(EmissionStandard es, TVV tvvObj) {

		List<TvvValuedEsDepTDL> tvvValuedEsDepTDL = new ArrayList<>();

		List<EmissionStdDepTDL> esDepTDL = emsDepTDLRepo.getLatestEmissionStandardDepLists(es.getEntityId());

		for (EmissionStdDepTDL tdl : esDepTDL) {

			TvvValuedEsDepTDL valuedTdl = tvvValuedEsDepTDLFactory.createTvvValuedEsDepTDL();
			valuedTdl.setGenericTechnicalData(new ArrayList<TvvValuedTechData>());

			valuedTdl.setEntityId(0L);
			valuedTdl.setLabel(tdl.getLabel());
			valuedTdl.setDescription(tdl.getDescription());
			valuedTdl.setEmissionStandard(es);
			valuedTdl.setTvvObj(tvvObj);
			valuedTdl.setVersion(tdl.getVersion());

			for (GenericTechnicalData objData : tdl.getGenericTechnicalData()) {

				TvvValuedTechData tvvValuedTd = tvvValuedTechDataFactory.createTvvValuedTechData();

				tvvValuedTd.setEntityId(0L);
				tvvValuedTd.setDataType(objData.getDataType());
				tvvValuedTd.setLabel(objData.getLabel());
				tvvValuedTd.setTvvValuedEsTDL(valuedTdl);
				tvvValuedTd.setUnit(objData.getUnit());
				tvvValuedTd.setGenericData(objData);

				valuedTdl.getGenericTechnicalData().add(tvvValuedTd);
			}

			tvvValuedEsDepTDL.add(valuedTdl);
		}

		tvvObj.setTvvValuedEsDepTDL(tvvValuedEsDepTDL);

	}

	/**
	 * Creates the es dep tcl to tvv.
	 * 
	 * @param es the es
	 * @param tvvObj the tvv obj
	 */
	private void createESDepTCLToTVV(EmissionStandard es, TVV tvvObj) {

		List<TvvValuedEsDepTCL> tvvValuedEsDepTCL = new ArrayList<>();

		List<EmissionStdDepTCL> esDepTCL = emsDepTCLRepository.getLatestEmissionStandardDepLists(es.getEntityId());

		for (EmissionStdDepTCL tcl : esDepTCL) {

			TvvValuedEsDepTCL valuedTcl = tvvValuedEsDepTCLFactory.createTvvValuedEsDepTCL();
			valuedTcl.setGenericTestCondition(new ArrayList<TvvValuedTestCondition>());

			valuedTcl.setEntityId(0L);
			valuedTcl.setLabel(tcl.getLabel());
			valuedTcl.setDescription(tcl.getDescription());
			valuedTcl.setEmissionStandard(es);
			valuedTcl.setVersion(tcl.getVersion());
			valuedTcl.setTvvObj(tvvObj);

			for (GenericTestCondition obj : tcl.getGenericTestCondition()) {

				TvvValuedTestCondition tvvValuedTc = tvvValuedTestConditionFactory.createTvvValuedTestCondition();

				tvvValuedTc.setEntityId(0L);
				tvvValuedTc.setLabel(obj.getLabel());
				tvvValuedTc.setUnit(obj.getUnit());
				tvvValuedTc.setDataType(obj.getDataType());
				tvvValuedTc.setDefaultValue(obj.getDefaultValue());
				tvvValuedTc.setTvvValuedEsTCL(valuedTcl);
				tvvValuedTc.setGenericCondition(obj);

				valuedTcl.getGenericTestCondition().add(tvvValuedTc);
			}

			tvvValuedEsDepTCL.add(valuedTcl);
		}

		tvvObj.setTvvValuedEsDepTCL(tvvValuedEsDepTCL);
	}

	/**
	 * Creates the es dep pgl to tvv.
	 * 
	 * @param es the es
	 * @param tvvObj the tvv obj
	 */
	private void createESDepPGLToTVV(EmissionStandard es, TVV tvvObj) {

		List<TvvValuedEsDepPGL> tvvValuedEsDepPGL = new ArrayList<>();

		List<PollutantGasLimitList> esDepPGL = pglListRepo.getLatestEmissionStandardDepLists(es.getEntityId());

		for (PollutantGasLimitList pglList : esDepPGL) {

			TvvValuedEsDepPGL valuedPgl = tvvValuedEsDepPGLFactory.createTvvValuedEsDepPGL();
			valuedPgl.setPollutantGasLimit(new ArrayList<TvvValuedPollutantGasLimit>());

			valuedPgl.setEntityId(0L);
			valuedPgl.setLabel(pglList.getLabel());
			valuedPgl.setDescription(pglList.getDescription());
			valuedPgl.setEmissionStandard(es);
			valuedPgl.setVersion(pglList.getVersion());
			valuedPgl.setTvvObj(tvvObj);

			for (PollutantGasLimit pgl : pglList.getPollutantGasLimit()) {

				TvvValuedPollutantGasLimit tvvValuedPg = tvvValuedPollutantGasLimitFactory.createTvvValuedPollutantGasLimit();

				tvvValuedPg.setMaxDValRule(pgl.getMaxDValRule());
				tvvValuedPg.setMaxDValue(pgl.getMaxDValue());
				tvvValuedPg.setMinDValRule(pgl.getMinDValRule());
				tvvValuedPg.setMinDValue(pgl.getMinDValue());
				tvvValuedPg.setPgLabel(pgl.getPgLabel());
				tvvValuedPg.setTvvValuedEsDepPGL(valuedPgl);
				tvvValuedPg.setPollutantLimit(pgl);

				valuedPgl.getPollutantGasLimit().add(tvvValuedPg);
			}

			tvvValuedEsDepPGL.add(valuedPgl);
		}

		tvvObj.setTvvValuedEsDepPGL(tvvValuedEsDepPGL);

	}

	/**
	 * Creates the es dep fcl to tvv.
	 * 
	 * @param es the es
	 * @param tvvObj the tvv obj
	 */
	private void createESDepFCLToTVV(EmissionStandard es, TVV tvvObj) {

		List<TvvValuedEsDepFCL> tvvValuedEsDepFCL = new ArrayList<>();

		List<FactorCoefficentList> esDepFCL = factorCoeffListRepo.getLatestEmissionStandardDepLists(es.getEntityId());

		for (FactorCoefficentList fcl : esDepFCL) {

			TvvValuedEsDepFCL valuedFcl = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL();
			valuedFcl.setFactorOrCoeffiecients(new ArrayList<TvvValuedFactorCoefficents>());

			valuedFcl.setEntityId(0);
			valuedFcl.setEmissionStandard(es);
			valuedFcl.setLabel(fcl.getLabel());
			valuedFcl.setDescription(fcl.getDescription());
			valuedFcl.setTvvObj(tvvObj);
			valuedFcl.setVersion(fcl.getVersion());

			for (FactorCoefficents fc : fcl.getFactorOrCoeffiecients()) {

				TvvValuedFactorCoefficents tvvValuedFc = tvvValuedFactorCoefficentsFactory.createTvvValuedFactorCoefficents();
				tvvValuedFc.setDefaultValue(fc.getDefaultValue());
				tvvValuedFc.setEntityId(0L);
				tvvValuedFc.setFclabel(fc.getFclabel());
				tvvValuedFc.setPgLabel(fc.getPgLabel());

				valuedFcl.getFactorOrCoeffiecients().add(tvvValuedFc);
			}

			tvvValuedEsDepFCL.add(valuedFcl);
		}
		tvvObj.setTvvValuedEsDepFCL(tvvValuedEsDepFCL);
	}

	/**
	 * Validate tvv data.
	 * 
	 * @param tvv the tvv
	 * @return the boolean
	 */
	public Boolean validateTVVData(TVVMigrationDto tvv) {

		Boolean tvvValidationFlag = Boolean.TRUE;

		logger.info("############### VALIDATION STARTS FOR TVV : {} ", tvv.getTvvLabel());
		
		/** START : Checking for TG , RG and Type Approval Area **/
		if (!tgData.containsKey(tvv.getTechnicalGroup())) {
			logger.info("*** Technical Group does not exists in System. [Technical Group Label : {}]", tvv.getTechnicalGroup());
			tvvValidationFlag = Boolean.FALSE;

		} else {

			TechnicalGroup tg = tgData.get(tvv.getTechnicalGroup());

			if (tg != null && tg.getRegulationGroup() != null && !tvv.getRegulationGroup().equalsIgnoreCase(tg.getRegulationGroup().getLabel())) {
				logger.info("*** Regulation Group does not exists in System. [Regulation Group Label : {}]", tvv.getRegulationGroup());
				tvvValidationFlag = Boolean.FALSE;

			} else {

				RegulationGroup rg = tg.getRegulationGroup();
				TypeApprovalArea taa = rg.getTypeApprovalArea();

				if (rg != null && rg.getTypeApprovalArea() != null && !taa.getLabel().equalsIgnoreCase(rg.getTypeApprovalArea().getLabel())) {
					logger.info("*** Type Approval Area does not exists in System. [Type Approval Area Label : {}]", rg.getTypeApprovalArea().getLabel());

				} 
			}
		}
		/** COMPLETED : Checking for TG , RG and Type Approval Area **/

		if (!pcfData.containsKey(tvv.getVehicleFamilyLabel())) {
			logger.info("*** Vehicle Family Label does not exists in System. [PCF Label : {}]", tvv.getVehicleFamilyLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!countryData.containsKey(tvv.getCountryLabel())) {
			logger.info("*** Country Label does not exists in System. [Country Label : {}]", tvv.getCountryLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!engineData.containsKey(tvv.getEngineLabel())) {
			logger.info("*** Engine Label does not exists in System. [Engine Label : {}]", tvv.getEngineLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!gearBoxData.containsKey(tvv.getGearBoxLabel())) {
			logger.info("*** Gear Box does not exists in System. [Gear Box Label : {}]", tvv.getGearBoxLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (tvv.getTvvLabel() == null || tvv.getTvvLabel().isEmpty()) {
			logger.info("*** Provide Tvv Label is not Valid. [TVV Label : {}]", tvv.getTvvLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!bodyworkData.containsKey(tvv.getBodyWorkLabel())) {
			logger.info("*** BodyWork Label does not exists in System. [BodyWork Label : {}]", tvv.getBodyWorkLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!coastDownData.containsKey(tvv.getCoastDownPsaReference().trim() + " - " + tvv.getInertiaValue())) {
			logger.info("*** Coast Down PSA Reference does not exists in System. [Coast Down : {}]", tvv.getCoastDownPsaReference());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!emissionStandardData.containsKey(tvv.getEmissionStandardLabel() + " - " + tvv.getEmissionStandardVersion())) {
			logger.info("*** Emission Standard does not exists in System. [Emission Standard Label : {}]", tvv.getEmissionStandardLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!statusData.containsKey(tvv.getStatusLabel())) {
			logger.info("*** Status does not exists in System. [Status Label : {}]", tvv.getStatusLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!testNatureData.containsKey(tvv.getTestNatureLabel())) {
			logger.info("*** Test Nature does not exists in System. [Test Nature Label : {}]", tvv.getTestNatureLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!fuelData.containsKey(tvv.getFuelLabel())) {
			logger.info("*** Fuel does not exists in System. [Fuel Label : {}]", tvv.getFuelLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!carBrandData.containsKey(tvv.getCarMakerBrandLabel())) {
			logger.info("*** Car Brand Label does not exists in System. [Car Brand Label : {}]", tvv.getCarMakerBrandLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		if (!fnlRedRtnData.containsKey(tvv.getFinalReductionRatioLabel())) {
			logger.info("*** Final Reduction Ration does not exists in System. [Final Reduction Ratio Label  : {}]", tvv.getFinalReductionRatioLabel());
			tvvValidationFlag = Boolean.FALSE;
		}
		
		if (!categoryData.containsKey(tvv.getCategoryLabel())) {
			logger.info("*** Category does not exists in System. [Category Label  : {}]", tvv.getCategoryLabel());
			tvvValidationFlag = Boolean.FALSE;
		}
		
		if (!vehTechData.containsKey(tvv.getVehicleTechnology())) {
			logger.info("*** Vehicle Technology does not exists in System. [Vehicle Technology Label  : {}]", tvv.getVehicleTechnology());
			tvvValidationFlag = Boolean.FALSE;
		}
		
		if(tvvlabelData.contains(tvv.getTvvLabel())) {
			logger.info("*** TVV Label is already present in the System. [TVV Label : {}]",tvv.getTvvLabel());
			tvvValidationFlag = Boolean.FALSE;
		}

		logger.info("############### VALIDATION COMPLETED FOR TVV : {} \n", tvv.getTvvLabel());

		return tvvValidationFlag;

	}

	/**
	 * Load tvv admin data.
	 */
	public void loadTVVAdminData() {
		
		logger.info("\n=============================================================================");
		logger.info("*********************** START : LOADING ADMIN DATA   ************************");
		logger.info("=============================================================================");
		
		DateTime startTime = GenomeUtil.getTime();

		tgData = prepareTGData(tgRepo.getAllTechnicalGroup());
		logger.info("***  Technical Group Data Size     :           {}", tgData.size());

		pcfData = preparePCFData(pcfRepo.getAllProjectCodeFamily());
		logger.info("***  Project Code Family Data Size :           {}", pcfData.size());

		countryData = prepareCntryData(countryRepo.getAllCountries());
		logger.info("***  Country Data Size             :           {} ", countryData.size());

		engineData = prepareEngineData(engineRepo.getAllEngine());
		logger.info("***  Engine Data Size              :           {} ", engineData.size());

		gearBoxData = prepareGearBoxData(gearBoxRepo.getAllGearBox());
		logger.info("***  Gear Box Data Size            :           {}", gearBoxData.size());

		bodyworkData = prepareBodyworkData(bodyWorkRepo.getAllBodyWork());
		logger.info("***  Bodywork Data Size            :           {} ",  bodyworkData.size());
		
		coastDownData = prepareCoastDownData(coastDownRepo.getCoastDownByLatestVersion());
		logger.info("***  Coast Down Data Size          :           {}", coastDownData.size());

		emissionStandardData = prepareESData(emsRepo.getAllEmissionStandards());
		logger.info("***  Emission Standard Data Size   :           {}", emissionStandardData.size());
		
		statusData = prepareStatusData(statusRepo.getAllStatus());
		logger.info("***  Status Data Size              :           {}", statusData.size());

		testNatureData = prepareTestNatureData(testNatureRepo.getAllTestNature());
		logger.info("***  Test Nature Data Size         :           {} ", testNatureData.size());
		
		fuelData = prepareFuelData(fuelRepo.getAllFuel());
		logger.info("***  Fuel Data Size                :           {}", fuelData.size());

		carBrandData = prepareCarBrandData(carBrandRepo.getAllCarBrand());
		logger.info("***  Car Brand Data Size           :           {}", carBrandData.size());

		fnlRedRtnData = prepareFRRData(frrRepo.getAllFinalReductionRation());
		logger.info("***  Final Red Ration Data Size    :           {}", fnlRedRtnData.size());
		
		categoryData = prepareCategoryData(catRepo.getAllCategories());
		logger.info("***  Category Data Size            :           {}", categoryData.size());
		
		vehTechData = prepareVehTechData(vehTechRepo.getAllVehicleTechnology());
		logger.info("***  Vehicle Technology Data Size  :           {}", vehTechData.size());
		
		tvvlabelData = tvvRepo.getAllTVVLabel();
		logger.info("***  TVV Label Data Size           :           {}", tvvlabelData.size());
		
		GenomeUtil.timeDuration(startTime, GenomeUtil.getTime(), "LOADING ADMIN DATA");
		
	}
	
	/**
	 * Convert list to map.
	 * Generic Method to Convert List to Map
	 * 
	 * @param <T> the generic type
	 * @param list the list
	 * @return the map
	 */
	public <T> Map<String, T> convertListToMap(Collection<T> list) {
		Map<String, T> map = new HashMap<String, T>();
		for (T el : list) {
			map.put(el.toString().trim(), el);
		}
		return map;
	}

	/**
	 * Convert tg list to map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, TechnicalGroup> prepareTGData(List<TechnicalGroup> list) {

		Map<String, TechnicalGroup> map = new HashMap<>();
		for (TechnicalGroup tg : list) {
			map.put(tg.getLabel().trim(), tg);
		}

		return map;
	}

	/**
	 * Convert pcf list to map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, ProjectCodeFamily> preparePCFData(List<ProjectCodeFamily> list) {

		Map<String, ProjectCodeFamily> map = new HashMap<>();
		for (ProjectCodeFamily pcf : list) {
			map.put(pcf.getVehicleFamilyLabel().trim(), pcf);
		}

		return map;
	}

	/**
	 * Convert country list to map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Country> prepareCntryData(List<Country> list) {

		Map<String, Country> map = new HashMap<>();
		for (Country pcf : list) {
			map.put(pcf.getLabel().trim(), pcf);
		}

		return map;
	}

	/**
	 * Convert engine list to map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Engine> prepareEngineData(List<Engine> list) {

		Map<String, Engine> map = new HashMap<>();
		for (Engine eng : list) {
			map.put(eng.getEngineLabel().trim(), eng);
		}

		return map;
	}

	/**
	 * Convert gear box list to map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, GearBox> prepareGearBoxData(List<GearBox> list) {

		Map<String, GearBox> map = new HashMap<>();
		for (GearBox gbx : list) {
			map.put(gbx.getLabel().trim(), gbx);
		}

		return map;
	}

	/**
	 * Prepare body work map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, EmissionStandard> prepareESData(List<EmissionStandard> list) {

		Map<String, EmissionStandard> map = new HashMap<>();
		for (EmissionStandard ems : list) {
			map.put(ems.getEsLabel().trim() + " - " + ems.getVersion().trim(), ems);
		}

		return map;
	}

	/**
	 * Prepare coast down map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, CoastDown> prepareCoastDownData(List<CoastDown> list) {

		Map<String, CoastDown> map = new HashMap<>();

		for (CoastDown cd : list) {
			String key = cd.getpSAReference().trim() + " - " + cd.getInertia().getValue();
			map.put(key, cd);

		}

		return map;

	}

	/**
	 * Prepare body work map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Bodywork> prepareBodyworkData(List<Bodywork> list) {

		Map<String, Bodywork> map = new HashMap<>();
		for (Bodywork bdw : list) {
			map.put(bdw.getLabel().trim(), bdw);
		}

		return map;
	}

	/**
	 * Prepare status map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Status> prepareStatusData(List<Status> list) {

		Map<String, Status> map = new HashMap<>();
		for (Status st : list) {
			map.put(st.getLabel().trim(), st);
		}

		return map;
	}

	/**
	 * Prepare test nature map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, TestNature> prepareTestNatureData(List<TestNature> list) {

		Map<String, TestNature> map = new HashMap<>();
		for (TestNature tn : list) {
			map.put(tn.getType().trim(), tn);
		}

		return map;
	}

	/**
	 * Prepare fuel map.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Fuel> prepareFuelData(List<Fuel> list) {

		Map<String, Fuel> map = new HashMap<>();
		for (Fuel fl : list) {
			map.put(fl.getLabel().trim(), fl);
		}

		return map;
	}

	/**
	 * Load car brand data.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, CarBrand> prepareCarBrandData(List<CarBrand> list) {

		Map<String, CarBrand> map = new HashMap<>();
		for (CarBrand cb : list) {
			map.put(cb.getBrandLabel().trim(), cb);
		}

		return map;
	}

	/**
	 * Prepare frr data.
	 * 
	 * @param list the list
	 * @return the map
	 */
	public Map<String, FinalReductionRatio> prepareFRRData(List<FinalReductionRatio> list) {

		Map<String, FinalReductionRatio> map = new HashMap<>();
		for (FinalReductionRatio frr : list) {
			map.put(frr.getLabel().trim(), frr);
		}

		return map;
	}
	
	
	/**
	 * Prepare category data.
	 *
	 * @param list the list
	 * @return the map
	 */
	public Map<String, Category> prepareCategoryData(List<Category> list) {

		Map<String, Category> map = new HashMap<>();
		for (Category cat : list) {
			map.put(cat.getLabel().trim(), cat);
		}

		return map;
	}
	
	
	/**
	 * Prepare veh tech data.
	 *
	 * @param list the list
	 * @return the map
	 */
	public Map<String, VehicleTechnology> prepareVehTechData(List<VehicleTechnology> list) {

		Map<String, VehicleTechnology> map = new HashMap<>();
		for (VehicleTechnology vt : list) {
			map.put(vt.getLabel().trim(), vt);
		}

		return map;
	}

	/**
	 * Gets the seprator.
	 * 
	 * @return the seprator
	 */
	public char getSeprator() {
		return separator;
	}

	/**
	 * Sets the seprator.
	 * 
	 * @param seprator the seprator to set
	 */
	public void setSeprator(char seprator) {
		this.separator = seprator;
	}

}

/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.bodywork.BodyworkFactory;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.carbrand.CarBrandFactory;
import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.category.CategoryFactory;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;
import com.inetpsa.poc00.domain.country.Country;
import com.inetpsa.poc00.domain.country.CountryFactory;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.engine.EngineFactory;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.factory.CarFactoryObjectCreation;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatioFactory;
import com.inetpsa.poc00.domain.fuel.Fuel;
import com.inetpsa.poc00.domain.fuel.FuelFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeFactory;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.gearbox.GearBoxFactory;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.status.StatusFactory;
import com.inetpsa.poc00.domain.technicalcase.TechCaseFactory;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.testnature.TestNature;
import com.inetpsa.poc00.domain.testnature.TestNatureFactory;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDown;
import com.inetpsa.poc00.domain.tvvvaluedcoastdown.TvvValuedCoastDownFactory;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLFactory;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLFactory;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCLFactory;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDLFactory;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechFactory;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;
import com.inetpsa.poc00.rest.bodywork.BodyWorkAssembler;
import com.inetpsa.poc00.rest.bodywork.BodyWorkRepresentation;
import com.inetpsa.poc00.rest.carbrand.CarBrandAssembler;
import com.inetpsa.poc00.rest.carbrand.CarBrandRepresentation;
import com.inetpsa.poc00.rest.carfactory.CarFactoryAssembler;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.category.CategoryAssembler;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.clasz.ClaszAssembler;
import com.inetpsa.poc00.rest.clasz.ClaszFinder;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.country.CountryAssembler;
import com.inetpsa.poc00.rest.country.CountryRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.engine.EngineAssembler;
import com.inetpsa.poc00.rest.engine.EngineRepresentation;
import com.inetpsa.poc00.rest.finalreduction.FinalReductionAssembler;
import com.inetpsa.poc00.rest.finalreduction.FinalReductionRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelAssembler;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeAssembler;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.gearbox.GearBoxAssembler;
import com.inetpsa.poc00.rest.gearbox.GearBoxRepresentation;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyAssembler;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentationAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTvvDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTDLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTvvDepTDLRepresentation;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaAssembler;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;
import com.inetpsa.poc00.rest.valuedcoastdown.TvvValuedCoastDownAssembler;
import com.inetpsa.poc00.rest.valuedcoastdown.ValuedCoastDownRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyAssembler;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class TvvAssembler.
 */
public class TvvAssembler extends BaseAssembler<TVV, TvvRepresentation> {

	/** The bodywork assembler. */
	@Inject
	BodyWorkAssembler bodyworkAssembler;

	/** The final reduction assembler. */
	@Inject
	private FinalReductionAssembler finalReductionAssembler;

	/** The gear box assembler. */
	@Inject
	private GearBoxAssembler gearBoxAssembler;

	/** The engine assembler. */
	@Inject
	private EngineAssembler engineAssembler;

	/** The fuel assembler. */
	@Inject
	private FuelAssembler fuelAssembler;

	/** The bodywork factory. */
	@Inject
	private BodyworkFactory bodyworkFactory;

	/** The final reduction factory. */
	@Inject
	private FinalReductionRatioFactory finalReductionFactory;

	/** The gear box factory. */
	@Inject
	private GearBoxFactory gearBoxFactory;

	/** The engine factory. */
	@Inject
	private EngineFactory engineFactory;

	/** The fuel factory. */
	@Inject
	private FuelFactory fuelFactory;

	/** The project code family assembler. */
	@Inject
	private ProjectCodeFamilyAssembler projectCodeFamilyAssembler;

	/** The project code family factory. */
	@Inject
	private ProjectCodeFamilyFactory projectCodeFamilyFactory;

	/** The status assembler. */
	@Inject
	private StatusRepresentationAssembler statusAssembler;

	/** The status factory. */
	@Inject
	private StatusFactory statusFactory;

	/** The car brand assembler. */
	@Inject
	private CarBrandAssembler carBrandAssembler;

	/** The car brand factory. */
	@Inject
	private CarBrandFactory carBrandFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The tvv valued TDL factory. */
	@Inject
	private TvvValuedTvvDepTDLFactory tvvValuedTDLFactory;

	/** The tvv valued TDL assembler. */
	@Inject
	private TvvValuedTDLAssembler tvvValuedTDLAssembler;

	/** The tvv valued es dep TDL factory. */
	@Inject
	private TvvValuedEsDepTDLFactory tvvValuedEsDepTDLFactory;

	/** The tvv valued es dep TDL assembler. */
	@Inject
	private TvvValuedEsDepTDLAssembler tvvValuedEsDepTDLAssembler;

	/** The technical case factory. */
	@Inject
	private TechCaseFactory technicalCaseFactory;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The tvv valued TCL assembler. */
	@Inject
	private TvvValuedTCLAssembler tvvValuedTCLAssembler;

	/** The tvv valued es dep TCL assembler. */
	@Inject
	private TvvValuedEsDepTCLAssembler tvvValuedEsDepTCLAssembler;

	/** The tvv valued TCL factory. */
	@Inject
	private TvvValuedTvvDepTCLFactory tvvValuedTCLFactory;

	/** The tvv valued es dep TCL factory. */
	@Inject
	private TvvValuedEsDepTCLFactory tvvValuedEsDepTCLFactory;

	/** The tvv valued es dep FCL assembler. */
	@Inject
	private TvvValuedEsDepFCLAssembler tvvValuedEsDepFCLAssembler;

	/** The tvv valued es dep PGL assembler. */
	@Inject
	private TvvValuedEsDepPGLAssembler tvvValuedEsDepPGLAssembler;

	/** The tvv valued es dep FCL factory. */
	@Inject
	private TvvValuedEsDepFCLFactory tvvValuedEsDepFCLFactory;

	/** The tvv valued es dep PGL factory. */
	@Inject
	private TvvValuedEsDepPGLFactory tvvValuedEsDepPGLFactory;

	/** The coast down assembler. */
	@Inject
	private TvvValuedCoastDownAssembler coastDownAssembler;

	/** The valued coast down factory. */
	@Inject
	private TvvValuedCoastDownFactory valuedCoastDownFactory;

	/** The type approval area assembler. */
	@Inject
	private TypeApprovalAreaAssembler typeApprovalAreaAssembler;

	/** The test nature assembler. */
	@Inject
	private TestNatureRepresentationAssembler testNatureAssembler;

	/** The test nature factory. */
	@Inject
	private TestNatureFactory testNatureFactory;

	/** The category assembler. */
	@Inject
	private CategoryAssembler categoryAssembler;

	/** The vehicle technology assembler. */
	@Inject
	private VehicleTechnologyAssembler vehicleTechnologyAssembler;

	/** The category factory. */
	@Inject
	private CategoryFactory categoryFactory;

	/** The vehicle tech factory. */
	@Inject
	private VehicleTechFactory vehicleTechFactory;

	/** The car factory assembler. */
	@Inject
	private CarFactoryAssembler carFactoryAssembler;

	/** The car factory factory. */
	@Inject
	private CarFactoryObjectCreation carFactoryFactory;

	/** The clasz finder. */
	@Inject
	private ClaszFinder claszFinder;

	/** The clasz assembler. */
	@Inject
	private ClaszAssembler claszAssembler;

	/** The clasz factory. */
	@Inject
	private ClaszFactory claszFactory;

	/** The clasz repo. */
	@Inject
	private ClaszRepository claszRepo;

	/** The fuel injection assembler. */
	@Inject
	private FuelInjectionTypeAssembler fuelInjectionAssembler;

	/** The fuel injection factory. */
	@Inject
	private FuelInjctnTypeFactory fuelInjectionFactory;

	@Inject
	private CountryAssembler countryAssembler;

	@Inject
	private CountryFactory countryFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate (java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Override
	public void doAssembleDtoFromAggregate(TvvRepresentation tvvRep, TVV tvv) {
		tvvRep.setEntityId(tvv.getEntityId());
		tvvRep.setLabel(tvv.getLabel());
		tvvRep.setValiditiyStartDate(tvv.getValiditiyStartDate());
		tvvRep.setValidityEndDate(tvv.getValidityEndDate());
		tvvRep.setVersion(tvv.getVersion());
		tvvRep.setCreationDate(tvv.getCreationDate());
		tvvRep.setModificationDate(tvv.getModificationDate());
		tvvRep.setSampleLabel(tvv.getSamplingLabel());
		// Set BodyWork
		BodyWorkRepresentation repObj = new BodyWorkRepresentation();
		bodyworkAssembler.doAssembleDtoFromAggregate(repObj, tvv.getBodyWork());
		if (tvv.getBodyWork() != null && tvv.getBodyWork().getGenomeTvvRule() != null)
			repObj.setSilhoutteLable(tvv.getBodyWork().getGenomeTvvRule().getLcdvCodeValue() + "-" + tvv.getBodyWork().getLabel());
		tvvRep.setBodyWork(repObj);
		// Set Engine
		EngineRepresentation engObj = new EngineRepresentation();
		engineAssembler.doAssembleDtoFromAggregate(engObj, tvv.getEngine());
		if (tvv.getEngine() != null && tvv.getEngine().getGenomeTvvRule() != null)
			engObj.setEngineLabel(tvv.getEngine().getGenomeTvvRule().getLcdvCodeValue() + "-" + tvv.getEngine().getGenomeTvvRuleDOC().getLcdvCodeValue() + "-" + tvv.getEngine().getEngineLabel());
		tvvRep.setEngine(engObj);
		// Set GearBoxyWork
		GearBoxRepresentation gbObj = new GearBoxRepresentation();
		gearBoxAssembler.doAssembleDtoFromAggregate(gbObj, tvv.getGearBox());
		if (tvv.getGearBox() != null && tvv.getGearBox().getGenomeTvvRuleDBM() != null)
			gbObj.setDisplayLabel(tvv.getGearBox().getGenomeTvvRuleDBM().getLcdvCodeValue() + "-" + tvv.getGearBox().getGenomeTvvRuleB0G().getLcdvCodeValue() + "-" + tvv.getGearBox().getLabel());
		tvvRep.setGearBox(gbObj);

		// Set Fuel
		FuelRepresentation fuelObj = new FuelRepresentation();
		fuelAssembler.doAssembleDtoFromAggregate(fuelObj, tvv.getFuel());
		tvvRep.setFuel(fuelObj);

		if (tvv.getFinalReductionRatio() != null) {
			FinalReductionRepresentation frObj = new FinalReductionRepresentation();
			finalReductionAssembler.doAssembleDtoFromAggregate(frObj, tvv.getFinalReductionRatio());
			tvvRep.setFinalReductionRatio(frObj);
		}
		// Set Project\FAmily
		ProjectCodeFamilyRepresentation pcObj = new ProjectCodeFamilyRepresentation();
		projectCodeFamilyAssembler.doAssembleDtoFromAggregate(pcObj, tvv.getProjectCodeFamily());
		if (tvv.getProjectCodeFamily() != null && tvv.getProjectCodeFamily().getGenomeTvvRule() != null)
			pcObj.setDisplayLabel(tvv.getProjectCodeFamily().getGenomeTvvRule().getLcdvCodeValue() + "-" + tvv.getProjectCodeFamily().getProjectCodeLabel() + "-" + tvv.getProjectCodeFamily().getVehicleFamilyLabel());
		tvvRep.setProjectCodeFamily(pcObj);
		// set Status
		if (tvv.getStatus() != null) {

			StatusRepresentation data = new StatusRepresentation();
			statusAssembler.doAssembleDtoFromAggregate(data, tvv.getStatus());

			tvvRep.setStatus(data);

		}
		// set TestNature
		if (tvv.getTestNature() != null) {

			TestNatureRepresentation data = new TestNatureRepresentation();
			testNatureAssembler.doAssembleDtoFromAggregate(data, tvv.getTestNature());

			tvvRep.setTestNature(data);

		}

		if (tvv.getTvvValuedCoastDown() != null) {

			ValuedCoastDownRepresentation coastDown = new ValuedCoastDownRepresentation();
			coastDownAssembler.doAssembleDtoFromAggregate(coastDown, tvv.getTvvValuedCoastDown());

			tvvRep.setValuedCoastDown(coastDown);

		}
		if (tvv.getCarBrandSet() != null && !tvv.getCarBrandSet().isEmpty()) {
			Set<CarBrand> dataItems = tvv.getCarBrandSet();
			List<CarBrandRepresentation> dataList = new ArrayList<>();
			for (CarBrand dataItem : dataItems) {
				CarBrandRepresentation data = new CarBrandRepresentation();
				carBrandAssembler.doAssembleDtoFromAggregate(data, dataItem);
				if (dataItem.getGenomeTvvRule() != null)
					data.setDisplayLabel(dataItem.getGenomeTvvRule().getLcdvCodeValue() + "-" + dataItem.getBrandLabel() + "-" + dataItem.getMakerLabel());
				dataList.add(data);

			}
			tvvRep.setCarBrandList(dataList);
		}
		if (tvv.getCountrySet() != null && !tvv.getCountrySet().isEmpty()) {
			Set<Country> countryItems = tvv.getCountrySet();
			List<CountryRepresentation> countryRepList = new ArrayList<>();
			for (Country countryObj : countryItems) {
				CountryRepresentation countryRep = new CountryRepresentation();
				countryAssembler.assembleDtoFromAggregate(countryRep, countryObj);
				countryRepList.add(countryRep);
			}
			tvvRep.setCountryList(countryRepList);
		}

		if (tvv.getTechnicalCase() != null) {
			EmissionStandardRepresentation emissionStandard = new EmissionStandardRepresentation();

			emissionStandardAssembler.doAssembleDtoFromAggregate(emissionStandard, tvv.getTechnicalCase().getEmissionStandard());

			tvvRep.setEmissionStandard(emissionStandard);
			if (tvv.getTechnicalCase().getTechnicalGroup() != null) {
				RegulationGroup rGroup = tvv.getTechnicalCase().getTechnicalGroup().getRegulationGroup();
				if (rGroup != null) {
					TypeApprovalArea area = rGroup.getTypeApprovalArea();
					if (area != null) {
						TypeApprovalAreaRepresentation areaDto = new TypeApprovalAreaRepresentation();
						typeApprovalAreaAssembler.doAssembleDtoFromAggregate(areaDto, area);
						tvvRep.setTypeApprovalArea(areaDto);
					}
				}
			}
			tvvRep.setTechnicalCaseId(tvv.getTechnicalCase().getEntityId());

		}
		if (tvv.getTvvValuedTvvDepTDL() != null && !tvv.getTvvValuedTvvDepTDL().isEmpty()) {
			List<TvvValuedTvvDepTDL> dataItems = tvv.getTvvValuedTvvDepTDL();
			List<TvvValuedTvvDepTDLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedTvvDepTDL dataItem : dataItems) {
				if (dataItem.getEntityId() != null) {
					TvvValuedTvvDepTDLRepresentation data = new TvvValuedTvvDepTDLRepresentation();
					tvvValuedTDLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					data.setTvvRepresentation(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedTvvDepTDL(dataList);
		}
		if (tvv.getTvvValuedEsDepTDL() != null && !tvv.getTvvValuedEsDepTDL().isEmpty()) {
			List<TvvValuedEsDepTDL> dataItems = tvv.getTvvValuedEsDepTDL();
			List<TvvValuedEsDepTDLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedEsDepTDL dataItem : dataItems) {
				if (dataItem.getEntityId() != null) {
					TvvValuedEsDepTDLRepresentation data = new TvvValuedEsDepTDLRepresentation();
					tvvValuedEsDepTDLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					data.setTvvObj(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedEsDepTDL(dataList);
		}

		if (tvv.getTvvValuedTvvDepTCL() != null && !tvv.getTvvValuedTvvDepTCL().isEmpty()) {
			List<TvvValuedTvvDepTCL> dataItems = tvv.getTvvValuedTvvDepTCL();
			List<TvvValuedTvvDepTCLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedTvvDepTCL dataItem : dataItems) {
				TvvValuedTvvDepTCLRepresentation data = new TvvValuedTvvDepTCLRepresentation();
				if (dataItem.getEntityId() != null) {
					tvvValuedTCLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					data.setTvvObj(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedTvvDepTCL(dataList);
		}
		if (tvv.getTvvValuedEsDepTCL() != null && !tvv.getTvvValuedEsDepTCL().isEmpty()) {
			List<TvvValuedEsDepTCL> dataItems = tvv.getTvvValuedEsDepTCL();
			List<TvvValuedEsDepTCLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedEsDepTCL dataItem : dataItems) {
				TvvValuedEsDepTCLRepresentation data = new TvvValuedEsDepTCLRepresentation();
				if (dataItem.getEntityId() != null) {
					tvvValuedEsDepTCLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					data.setTvvObj(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedEsDepTCL(dataList);
		}

		if (tvv.getTvvValuedEsDepFCL() != null && !tvv.getTvvValuedEsDepFCL().isEmpty()) {
			List<TvvValuedEsDepFCL> dataItems = tvv.getTvvValuedEsDepFCL();
			List<TvvValuedEsDepFCLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedEsDepFCL dataItem : dataItems) {
				if (dataItem.getEntityId() != null) {
					TvvValuedEsDepFCLRepresentation data = new TvvValuedEsDepFCLRepresentation();
					tvvValuedEsDepFCLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					data.setTvvObj(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedEsDepFCL(dataList);
		}

		if (tvv.getTvvValuedEsDepPGL() != null && !tvv.getTvvValuedEsDepPGL().isEmpty()) {
			List<TvvValuedEsDepPGL> dataItems = tvv.getTvvValuedEsDepPGL();
			List<TvvValuedEsDepPGLRepresentation> dataList = new ArrayList<>();

			for (TvvValuedEsDepPGL dataItem : dataItems) {
				if (dataItem.getEntityId() != null) {
					TvvValuedEsDepPGLRepresentation data = new TvvValuedEsDepPGLRepresentation();
					tvvValuedEsDepPGLAssembler.doAssembleDtoFromAggregate(data, dataItem);
					if (data.getMaxCo2Limit() != null)
						tvvRep.setMaxCo2LimitTvv(data.getMaxCo2Limit());
					if (data.getMinCo2Limit() != null)
						tvvRep.setMinCo2LimitTvv(data.getMinCo2Limit());
					data.setTvvObj(tvvRep);
					dataList.add(data);
				}

			}
			tvvRep.setTvvValuedEsDepPGL(dataList);
		}

		/* Set category */
		if (tvv.getCategory() != null) {
			CategoryRepresentation categoryRep = new CategoryRepresentation();
			categoryAssembler.doAssembleDtoFromAggregate(categoryRep, tvv.getCategory());
			if (tvv.getClasz() != null)
				categoryRep.setClasz_label(tvv.getClasz().getLabel());
			tvvRep.setCategory(categoryRep);
			if (tvv.getClasz() != null) {
				ClaszRepresentation claszRepresentation = new ClaszRepresentation();
				claszAssembler.doAssembleDtoFromAggregate(claszRepresentation, tvv.getClasz());
				tvvRep.setClaszRepresentation(claszRepresentation);
			}

		}
		/* Set vehicleTechnology */
		if (tvv.getVehicalTechnology() != null) {
			VehicleTechnologyRepresentation vehicalTechRep = new VehicleTechnologyRepresentation();
			vehicleTechnologyAssembler.doAssembleDtoFromAggregate(vehicalTechRep, tvv.getVehicalTechnology());
			tvvRep.setVehicalTechnology(vehicalTechRep);
		}

		// For Factory
		if (tvv.getFactorySet() != null && !tvv.getFactorySet().isEmpty()) {
			Set<CarFactory> dataItems = tvv.getFactorySet();
			List<CarFactoryRepresentation> dataList = new ArrayList<>();
			for (CarFactory dataItem : dataItems) {
				CarFactoryRepresentation data = new CarFactoryRepresentation();
				carFactoryAssembler.doAssembleDtoFromAggregate(data, dataItem);

				dataList.add(data);

			}
			tvvRep.setCarFactoryList(dataList);
		}
		if (tvv.getFuelInjectionType() != null) {
			FuelInjectionTypeRepresentation fuelRepresentation = new FuelInjectionTypeRepresentation();
			fuelInjectionAssembler.doAssembleDtoFromAggregate(fuelRepresentation, tvv.getFuelInjectionType());
			tvvRep.setFuelInjection(fuelRepresentation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto (org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	protected void doMergeAggregateWithDto(TVV tvv, TvvRepresentation tvvRep) {
		EmissionStandard emissionStandardForList = null;
		tvv.setEntityId(tvvRep.getEntityId());
		if (("None").equals(tvvRep.getSampleLabel())) {
			tvv.setSamplingLabel(null);
		} else {
			tvv.setSamplingLabel(tvvRep.getSampleLabel());
		}
		tvv.setLabel(tvvRep.getLabel());
		tvv.setValiditiyStartDate(tvvRep.getValiditiyStartDate());
		tvv.setValidityEndDate(tvvRep.getValidityEndDate());
		tvv.setVersion(tvvRep.getVersion());
		tvv.setCreationDate(tvvRep.getCreationDate());
		tvv.setModificationDate(tvvRep.getModificationDate());
		// Set BodyWork
		Bodywork repObj = bodyworkFactory.createBodywork();
		bodyworkAssembler.doMergeAggregateWithDto(repObj, tvvRep.getBodyWork());
		tvv.setBodyWork(repObj);
		// Set BodyWork
		Engine engObj = engineFactory.createEngine();
		engineAssembler.doMergeAggregateWithDto(engObj, tvvRep.getEngine());
		tvv.setEngine(engObj);
		// Set BodyWork
		GearBox gbObj = gearBoxFactory.createGearBox();
		gearBoxAssembler.doMergeAggregateWithDto(gbObj, tvvRep.getGearBox());
		tvv.setGearBox(gbObj);
		// Set Project\FAmily
		ProjectCodeFamily pcObj = projectCodeFamilyFactory.createPCFamily();
		projectCodeFamilyAssembler.doMergeAggregateWithDto(pcObj, tvvRep.getProjectCodeFamily());
		tvv.setProjectCodeFamily(pcObj);
		// set Fuel
		Fuel fuelObj = fuelFactory.createFuel();
		fuelAssembler.doMergeAggregateWithDto(fuelObj, tvvRep.getFuel());
		tvv.setFuel(fuelObj);
		if (tvvRep.getValuedCoastDown() != null) {

			TvvValuedCoastDown coastDown = valuedCoastDownFactory.createValuedCoastDown();
			coastDownAssembler.doMergeAggregateWithDto(coastDown, tvvRep.getValuedCoastDown());

			tvv.setTvvValuedCoastDown(coastDown);
		}
		if (tvvRep.getTechnicalCaseId() != 0) {
			TechnicalCase technicalCase = technicalCaseFactory.createTechCase();
			technicalCase.setEntityId(tvvRep.getTechnicalCaseId());
			EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
			emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, tvvRep.getEmissionStandard());
			emissionStandardForList = emissionStandard;
			technicalCase.setEmissionStandard(emissionStandard);
			technicalCase.setTvv(tvv);

			tvv.setTechnicalCase(technicalCase);

		}
		if (tvvRep.getFinalReductionRatio() != null) {
			FinalReductionRatio frObj = finalReductionFactory.createReductionRatio();
			finalReductionAssembler.doMergeAggregateWithDto(frObj, tvvRep.getFinalReductionRatio());
			tvv.setFinalReductionRatio(frObj);
		}
		// set Status
		if (tvvRep.getStatus() != null) {

			Status data = statusFactory.createStatus();
			statusAssembler.doMergeAggregateWithDto(data, tvvRep.getStatus());

			tvv.setStatus(data);

		}
		// set TestNature
		if (tvvRep.getTestNature() != null) {

			TestNature testNature = testNatureFactory.createTestNature();
			testNatureAssembler.doMergeAggregateWithDto(testNature, tvvRep.getTestNature());

			tvv.setTestNature(testNature);

		}
		if (tvvRep.getCarBrandList() != null && !tvvRep.getCarBrandList().isEmpty()) {
			List<CarBrandRepresentation> dataItems = tvvRep.getCarBrandList();
			Set<CarBrand> dataList = new HashSet<>();

			for (CarBrandRepresentation dataItem : dataItems) {
				CarBrand data = carBrandFactory.createCarBrand();
				carBrandAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			tvv.setCarBrandSet(dataList);
		}
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

		if (tvvRep.getTvvValuedTvvDepTDL() != null && !tvvRep.getTvvValuedTvvDepTDL().isEmpty()) {
			List<TvvValuedTvvDepTDLRepresentation> dataItems = tvvRep.getTvvValuedTvvDepTDL();
			List<TvvValuedTvvDepTDL> dataList = new ArrayList<>();

			for (TvvValuedTvvDepTDLRepresentation dataItem : dataItems) {
				TvvValuedTvvDepTDL data = tvvValuedTDLFactory.createTvvValuedTDL();
				tvvValuedTDLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				dataList.add(data);

			}
			tvv.setTvvValuedTvvDepTDL(dataList);
		}
		if (tvvRep.getTvvValuedEsDepTDL() != null && !tvvRep.getTvvValuedEsDepTDL().isEmpty()) {
			List<TvvValuedEsDepTDLRepresentation> dataItems = tvvRep.getTvvValuedEsDepTDL();
			List<TvvValuedEsDepTDL> dataList = new ArrayList<>();

			for (TvvValuedEsDepTDLRepresentation dataItem : dataItems) {
				TvvValuedEsDepTDL data = tvvValuedEsDepTDLFactory.createTvvValuedEsDepTDL();
				tvvValuedEsDepTDLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				data.setEmissionStandard(emissionStandardForList);
				dataList.add(data);

			}
			tvv.setTvvValuedEsDepTDL(dataList);
		}

		if (tvvRep.getTvvValuedTvvDepTCL() != null && !tvvRep.getTvvValuedTvvDepTCL().isEmpty()) {
			List<TvvValuedTvvDepTCLRepresentation> dataItems = tvvRep.getTvvValuedTvvDepTCL();
			List<TvvValuedTvvDepTCL> dataList = new ArrayList<>();

			for (TvvValuedTvvDepTCLRepresentation dataItem : dataItems) {
				TvvValuedTvvDepTCL data = tvvValuedTCLFactory.createTvvValuedTvvDepTCL();
				tvvValuedTCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				dataList.add(data);

			}
			tvv.setTvvValuedTvvDepTCL(dataList);
		}
		if (tvvRep.getTvvValuedEsDepTCL() != null && !tvvRep.getTvvValuedEsDepTCL().isEmpty()) {
			List<TvvValuedEsDepTCLRepresentation> dataItems = tvvRep.getTvvValuedEsDepTCL();
			List<TvvValuedEsDepTCL> dataList = new ArrayList<>();

			for (TvvValuedEsDepTCLRepresentation dataItem : dataItems) {
				TvvValuedEsDepTCL data = tvvValuedEsDepTCLFactory.createTvvValuedEsDepTCL();
				tvvValuedEsDepTCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				data.setEmissionStandard(emissionStandardForList);
				dataList.add(data);

			}
			tvv.setTvvValuedEsDepTCL(dataList);
		}

		if (tvvRep.getTvvValuedEsDepFCL() != null && !tvvRep.getTvvValuedEsDepFCL().isEmpty()) {
			List<TvvValuedEsDepFCLRepresentation> dataItems = tvvRep.getTvvValuedEsDepFCL();
			List<TvvValuedEsDepFCL> dataList = new ArrayList<>();

			for (TvvValuedEsDepFCLRepresentation dataItem : dataItems) {
				TvvValuedEsDepFCL data = tvvValuedEsDepFCLFactory.createTvvValuedEsDepFCL();
				tvvValuedEsDepFCLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				data.setEmissionStandard(emissionStandardForList);
				dataList.add(data);

			}
			tvv.setTvvValuedEsDepFCL(dataList);
		}

		if (tvvRep.getTvvValuedEsDepPGL() != null && !tvvRep.getTvvValuedEsDepPGL().isEmpty()) {
			List<TvvValuedEsDepPGLRepresentation> dataItems = tvvRep.getTvvValuedEsDepPGL();
			List<TvvValuedEsDepPGL> dataList = new ArrayList<>();

			for (TvvValuedEsDepPGLRepresentation dataItem : dataItems) {
				TvvValuedEsDepPGL data = tvvValuedEsDepPGLFactory.createTvvValuedEsDepPGL();
				tvvValuedEsDepPGLAssembler.doMergeAggregateWithDto(data, dataItem);
				data.setTvvObj(tvv);
				data.setEmissionStandard(emissionStandardForList);
				dataList.add(data);

			}
			tvv.setTvvValuedEsDepPGL(dataList);
		}

		/* Set Category */
		if (tvvRep.getCategory() != null) {

			Category category = categoryFactory.createCategory();
			categoryAssembler.doMergeAggregateWithDto(category, tvvRep.getCategory());
			tvv.setCategory(category);
			if (tvvRep.getCategory().getClasz_label() != null) {
				List<ClaszRepresentation> claszRepresentationList = claszFinder.findClaszDataByLabel(tvvRep.getCategory().getClasz_label());
				Clasz clasz = claszRepo.load(claszRepresentationList.get(0).getEntityId());
				tvv.setClasz(clasz);

			}
		}
		/* Set VehicleTechnology */
		if (tvvRep.getVehicalTechnology() != null) {

			VehicleTechnology vehicleTech = vehicleTechFactory.createVehTechnology();
			vehicleTechnologyAssembler.doMergeAggregateWithDto(vehicleTech, tvvRep.getVehicalTechnology());
			tvv.setVehicalTechnology(vehicleTech);
		}
		if (tvvRep.getCarFactoryList() != null && !tvvRep.getCarFactoryList().isEmpty()) {
			List<CarFactoryRepresentation> dataItems = tvvRep.getCarFactoryList();
			Set<CarFactory> dataList = new HashSet<>();

			for (CarFactoryRepresentation dataItem : dataItems) {
				CarFactory data = carFactoryFactory.createCarFactoryObject();
				carFactoryAssembler.doMergeAggregateWithDto(data, dataItem);

				dataList.add(data);

			}
			tvv.setFactorySet(dataList);
		}
		if (tvvRep.getFuelInjection() != null) {
			FuelInjectionType fuelInjectionObj = fuelInjectionFactory.createFuelInjctnType();
			fuelInjectionAssembler.doMergeAggregateWithDto(fuelInjectionObj, tvvRep.getFuelInjection());
			tvv.setFuelInjectionType(fuelInjectionObj);
		}
	}
}

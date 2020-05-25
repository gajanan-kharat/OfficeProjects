/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.rest.bodywork.BodyWorkRepresentation;
import com.inetpsa.poc00.rest.carbrand.CarBrandRepresentation;
import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.category.CategoryRepresentation;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;
import com.inetpsa.poc00.rest.coastdown.CoastdownRepresentation;
import com.inetpsa.poc00.rest.country.CountryRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.engine.EngineRepresentation;
import com.inetpsa.poc00.rest.finalreduction.FinalReductionRepresentation;
import com.inetpsa.poc00.rest.fuel.FuelRepresentation;
import com.inetpsa.poc00.rest.fuelinjectiontype.FuelInjectionTypeRepresentation;
import com.inetpsa.poc00.rest.gearbox.GearBoxRepresentation;
import com.inetpsa.poc00.rest.projectcodefamily.ProjectCodeFamilyRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.testnature.TestNatureRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestcl.TvvValuedEsDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedestdl.TvvValuedEsDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtcl.TvvValuedTvvDepTCLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTvvDepTDLRepresentation;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;
import com.inetpsa.poc00.rest.valuedcoastdown.ValuedCoastDownRepresentation;
import com.inetpsa.poc00.rest.vehicletechnology.VehicleTechnologyRepresentation;

/**
 * The Class TvvRepresentation.
 */
public class TvvRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The validity end date. */
	private Date validityEndDate;

	/** The validitiy start date. */
	private Date validitiyStartDate;

	/** The creation date. */
	private Date creationDate;

	/** The modification date. */
	private Date modificationDate;

	/** The project code family. */
	private ProjectCodeFamilyRepresentation projectCodeFamily;

	/** The final reduction ratio. */
	private FinalReductionRepresentation finalReductionRatio;

	/** The gear box. */
	private GearBoxRepresentation gearBox;

	/** The engine. */
	private EngineRepresentation engine;

	/** The body work. */
	private BodyWorkRepresentation bodyWork;

	/** The fuel . */
	private FuelRepresentation fuel;

	/** The emission standard list. */
	private EmissionStandardRepresentation emissionStandard;

	/** The tvv valued tvv dep tdl. */
	private List<TvvValuedTvvDepTDLRepresentation> tvvValuedTvvDepTDL = new ArrayList<>();

	/** The tvv valued tvv dep tcl. */
	private List<TvvValuedTvvDepTCLRepresentation> tvvValuedTvvDepTCL = new ArrayList<>();

	/** The tvv valued es dep tdl. */
	private List<TvvValuedEsDepTDLRepresentation> tvvValuedEsDepTDL = new ArrayList<>();

	/** The tvv valued es dep tcl. */
	private List<TvvValuedEsDepTCLRepresentation> tvvValuedEsDepTCL = new ArrayList<>();

	/** The tvv valued es dep fcl. */
	private List<TvvValuedEsDepFCLRepresentation> tvvValuedEsDepFCL = new ArrayList<>();

	/** The tvv valued es dep pgl. */
	private List<TvvValuedEsDepPGLRepresentation> tvvValuedEsDepPGL = new ArrayList<>();

	/** The status. */
	private StatusRepresentation status;

	/** The status to save. */
	private StatusRepresentation statusToSave;

	/** The technical case id. */
	private long technicalCaseId;

	/** The coast down to save. */
	private CoastdownRepresentation coastDownToSave;

	/** The valued coast down. */
	private ValuedCoastDownRepresentation valuedCoastDown;

	/** The min co 2 limit tvv. */
	private double minCo2LimitTvv;

	/** The max co 2 limit tvv. */
	private double maxCo2LimitTvv;

	/** The type approval area. */
	private TypeApprovalAreaRepresentation typeApprovalArea;

	/** The category. */
	private CategoryRepresentation category;

	/** The vehical technology. */
	private VehicleTechnologyRepresentation vehicalTechnology;

	/** The display date. */
	private String displayDate;

	/**
	 * Sets the status.
	 */
	/** flag for tvv selection */
	private boolean tvvSelected = false;

	/** flag for worstcase. */
	private boolean worstcaseSelected;

	/** flag for worstcase associated to technicalGroup. */
	private boolean tgWorstcase;

	/** The test nature. */
	private TestNatureRepresentation testNature;

	/** The car brand list. */
	private List<CarBrandRepresentation> carBrandList;

	/** The status label. */
	private String statusLabel;

	/** The pg label. */
	private String pgLabel;
	private String sampleLabel;
	/** The car factory list. */
	private List<CarFactoryRepresentation> carFactoryList;

	/**
	 * Instantiates a new tvv representation.
	 */
	private ClaszRepresentation claszRepresentation;

	/** The fuel injection. */
	private FuelInjectionTypeRepresentation fuelInjection;

	/** The country list. */
	private List<CountryRepresentation> countryList;

	/**
	 * Instantiates a new tvv representation.
	 */
	public TvvRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv representation.
	 *
	 * @param entityId the entity id
	 * @param label the label
	 * @param esLabel the es label
	 * @param esVersion the es version
	 * @param version the version
	 * @param statusLabel the status label
	 * @param testNatureId the test nature id
	 * @param testNatureLabel the test nature label
	 * @param pgLabel the pg label
	 * @param maxDValue the max d value
	 * @param minDValue the min d value
	 * @param technicalCaseId the technical case id
	 */
	public TvvRepresentation(Long entityId, String label, String esLabel, String esVersion, String version, String statusLabel, Long testNatureId, String testNatureLabel, String pgLabel, Double maxDValue, Double minDValue, Long technicalCaseId) {
		this.entityId = entityId;
		this.label = label;
		this.version = version;
		this.emissionStandard = new EmissionStandardRepresentation(esLabel, esVersion);
		this.status = new StatusRepresentation(statusLabel);
		this.testNature = new TestNatureRepresentation(testNatureId, testNatureLabel);
		this.pgLabel = pgLabel;
		if (pgLabel.equals(Constants.PGLABEL_CO2)) {
			this.maxCo2LimitTvv = maxDValue;
			this.minCo2LimitTvv = minDValue;
		}
		this.technicalCaseId = technicalCaseId;
	}

	/**
	 * Instantiates a new tvv representation.
	 * 
	 * @param entityId the entity id
	 * @param version the version
	 * @param label the label
	 * @param modificationDate the modification date
	 * @param statusId the status id
	 * @param statusLabel the status label
	 * @param statusGuiLabel the status gui label
	 * @param testType the test type
	 */
	public TvvRepresentation(long entityId, String version, String label, Date modificationDate, long statusId, String statusLabel, String statusGuiLabel, String testType) {
		this.entityId = entityId;
		this.version = version;
		this.label = label;
		this.modificationDate = modificationDate;
		this.status = new StatusRepresentation(statusId, statusGuiLabel, statusLabel, (long) 0, "", testType);
	}

	/**
	 * Instantiates a new tvv representation.
	 * 
	 * @param entityId the entity id
	 * @param version the version
	 * @param label the label
	 * @param modificationDate the modification date
	 * @param statusId the status id
	 * @param statusLabel the status label
	 * @param statusGuiLabel the status gui label
	 * @param isWorstCase the is worst case
	 */
	public TvvRepresentation(long entityId, String version, String label, Date modificationDate, long statusId, String statusLabel, String statusGuiLabel, boolean isWorstCase) {
		this.entityId = entityId;
		this.version = version;
		this.label = label;
		this.modificationDate = modificationDate;
		this.status = new StatusRepresentation(statusId, statusGuiLabel, statusLabel);
		this.tgWorstcase = isWorstCase;
		this.worstcaseSelected = isWorstCase;

	}

	/**
	 * Instantiates a new tvv representation.
	 * 
	 * @param entityId the entity id
	 * @param version the version
	 * @param label the label
	 */
	public TvvRepresentation(long entityId, String version, String label) {
		this.entityId = entityId;
		this.version = version;
		this.label = label;

	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(StatusRepresentation status) {
		this.status = status;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the validity end date.
	 * 
	 * @return the validity end date
	 */
	public Date getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * Sets the validity end date.
	 * 
	 * @param validityEndDate the new validity end date
	 */
	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * Gets the validitiy start date.
	 * 
	 * @return the validitiy start date
	 */
	public Date getValiditiyStartDate() {
		return validitiyStartDate;
	}

	/**
	 * Sets the validitiy start date.
	 * 
	 * @param validitiyStartDate the new validitiy start date
	 */
	public void setValiditiyStartDate(Date validitiyStartDate) {
		this.validitiyStartDate = validitiyStartDate;
	}

	/**
	 * Gets the project code family.
	 * 
	 * @return the project code family
	 */
	public ProjectCodeFamilyRepresentation getProjectCodeFamily() {
		return projectCodeFamily;
	}

	/**
	 * Sets the project code family.
	 * 
	 * @param projectCodeFamily the new project code family
	 */
	public void setProjectCodeFamily(ProjectCodeFamilyRepresentation projectCodeFamily) {
		this.projectCodeFamily = projectCodeFamily;
	}

	/**
	 * Gets the final reduction ratio.
	 * 
	 * @return the final reduction ratio
	 */
	public FinalReductionRepresentation getFinalReductionRatio() {
		return finalReductionRatio;
	}

	/**
	 * Sets the final reduction ratio.
	 * 
	 * @param finalReductionRatio the new final reduction ratio
	 */
	public void setFinalReductionRatio(FinalReductionRepresentation finalReductionRatio) {
		this.finalReductionRatio = finalReductionRatio;
	}

	/**
	 * Gets the gear box.
	 * 
	 * @return the gear box
	 */
	public GearBoxRepresentation getGearBox() {
		return gearBox;
	}

	/**
	 * Sets the gear box.
	 * 
	 * @param gearBox the new gear box
	 */
	public void setGearBox(GearBoxRepresentation gearBox) {
		this.gearBox = gearBox;
	}

	/**
	 * Gets the engine.
	 * 
	 * @return the engine
	 */
	public EngineRepresentation getEngine() {
		return engine;
	}

	/**
	 * Sets the engine.
	 * 
	 * @param engine the new engine
	 */
	public void setEngine(EngineRepresentation engine) {
		this.engine = engine;
	}

	/**
	 * Gets the body work.
	 * 
	 * @return the body work
	 */
	public BodyWorkRepresentation getBodyWork() {
		return bodyWork;
	}

	/**
	 * Sets the body work.
	 * 
	 * @param bodyWork the new body work
	 */
	public void setBodyWork(BodyWorkRepresentation bodyWork) {
		this.bodyWork = bodyWork;
	}

	/**
	 * Gets the fuel type.
	 * 
	 * @return the fuel type
	 */
	public FuelRepresentation getFuel() {
		return fuel;
	}

	/**
	 * Sets the fuel type.
	 * 
	 * @param fuel the new fuel
	 */
	public void setFuel(FuelRepresentation fuel) {
		this.fuel = fuel;
	}

	/**
	 * Gets the emission standard list.
	 * 
	 * @return the emission standard list
	 */
	public EmissionStandardRepresentation getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard list.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandardRepresentation emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public StatusRepresentation getStatus() {
		return status;
	}

	/**
	 * Gets the car brand list.
	 * 
	 * @return the car brand list
	 */
	public List<CarBrandRepresentation> getCarBrandList() {
		return carBrandList;
	}

	/**
	 * Sets the car brand list.
	 * 
	 * @param carBrandList the new car brand list
	 */
	public void setCarBrandList(List<CarBrandRepresentation> carBrandList) {
		this.carBrandList = carBrandList;
	}

	/**
	 * Gets the status label.
	 * 
	 * @return the status label
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Sets the status label.
	 * 
	 * @param statusLabel the new status label
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the tvv valued tvv dep tdl.
	 * 
	 * @return the tvv valued tvv dep tdl
	 */
	public List<TvvValuedTvvDepTDLRepresentation> getTvvValuedTvvDepTDL() {
		return tvvValuedTvvDepTDL;
	}

	/**
	 * Sets the tvv valued tvv dep tdl.
	 * 
	 * @param tvvValuedTvvDepTDL the new tvv valued tvv dep tdl
	 */
	public void setTvvValuedTvvDepTDL(List<TvvValuedTvvDepTDLRepresentation> tvvValuedTvvDepTDL) {
		this.tvvValuedTvvDepTDL = tvvValuedTvvDepTDL;
	}

	/**
	 * Gets the tvv valued tvv dep tcl.
	 * 
	 * @return the tvv valued tvv dep tcl
	 */
	public List<TvvValuedTvvDepTCLRepresentation> getTvvValuedTvvDepTCL() {
		return tvvValuedTvvDepTCL;
	}

	/**
	 * Sets the tvv valued tvv dep tcl.
	 * 
	 * @param tvvValuedTvvDepTCL the new tvv valued tvv dep tcl
	 */
	public void setTvvValuedTvvDepTCL(List<TvvValuedTvvDepTCLRepresentation> tvvValuedTvvDepTCL) {
		this.tvvValuedTvvDepTCL = tvvValuedTvvDepTCL;
	}

	/**
	 * Gets the tvv valued es dep tdl.
	 * 
	 * @return the tvv valued es dep tdl
	 */
	public List<TvvValuedEsDepTDLRepresentation> getTvvValuedEsDepTDL() {
		return tvvValuedEsDepTDL;
	}

	/**
	 * Sets the tvv valued es dep tdl.
	 * 
	 * @param tvvValuedEsDepTDL the new tvv valued es dep tdl
	 */
	public void setTvvValuedEsDepTDL(List<TvvValuedEsDepTDLRepresentation> tvvValuedEsDepTDL) {
		this.tvvValuedEsDepTDL = tvvValuedEsDepTDL;
	}

	/**
	 * Gets the tvv valued es dep tcl.
	 * 
	 * @return the tvv valued es dep tcl
	 */
	public List<TvvValuedEsDepTCLRepresentation> getTvvValuedEsDepTCL() {
		return tvvValuedEsDepTCL;
	}

	/**
	 * Sets the tvv valued es dep tcl.
	 * 
	 * @param tvvValuedEsDepTCL the new tvv valued es dep tcl
	 */
	public void setTvvValuedEsDepTCL(List<TvvValuedEsDepTCLRepresentation> tvvValuedEsDepTCL) {
		this.tvvValuedEsDepTCL = tvvValuedEsDepTCL;
	}

	/**
	 * Gets the tvv valued es dep fcl.
	 * 
	 * @return the tvv valued es dep fcl
	 */
	public List<TvvValuedEsDepFCLRepresentation> getTvvValuedEsDepFCL() {
		return tvvValuedEsDepFCL;
	}

	/**
	 * Sets the tvv valued es dep fcl.
	 * 
	 * @param tvvValuedEsDepFCL the new tvv valued es dep fcl
	 */
	public void setTvvValuedEsDepFCL(List<TvvValuedEsDepFCLRepresentation> tvvValuedEsDepFCL) {
		this.tvvValuedEsDepFCL = tvvValuedEsDepFCL;
	}

	/**
	 * Gets the tvv valued es dep pgl.
	 * 
	 * @return the tvv valued es dep pgl
	 */
	public List<TvvValuedEsDepPGLRepresentation> getTvvValuedEsDepPGL() {
		return tvvValuedEsDepPGL;
	}

	/**
	 * Sets the tvv valued es dep pgl.
	 * 
	 * @param tvvValuedEsDepPGL the new tvv valued es dep pgl
	 */
	public void setTvvValuedEsDepPGL(List<TvvValuedEsDepPGLRepresentation> tvvValuedEsDepPGL) {
		this.tvvValuedEsDepPGL = tvvValuedEsDepPGL;
	}

	/**
	 * Checks if is tvv selected.
	 * 
	 * @return the tvvSelected
	 */
	public boolean isTvvSelected() {
		return tvvSelected;
	}

	/**
	 * Sets the tvv selected.
	 * 
	 * @param tvvSelected the tvvSelected to set
	 */
	public void setTvvSelected(boolean tvvSelected) {
		this.tvvSelected = tvvSelected;
	}

	/**
	 * Checks if is worstcase selected.
	 * 
	 * @return the worstcaseSelected
	 */
	public boolean isWorstcaseSelected() {
		return worstcaseSelected;
	}

	/**
	 * Sets the worstcase selected.
	 * 
	 * @param worstcaseSelected the worstcaseSelected to set
	 */
	public void setWorstcaseSelected(boolean worstcaseSelected) {
		this.worstcaseSelected = worstcaseSelected;
	}

	/**
	 * Checks if is tg worstcase.
	 * 
	 * @return the tgWorstcase
	 */
	public boolean isTgWorstcase() {
		return tgWorstcase;
	}

	/**
	 * Sets the tg worstcase.
	 * 
	 * @param tgWorstcase the tgWorstcase to set
	 */
	public void setTgWorstcase(boolean tgWorstcase) {
		this.tgWorstcase = tgWorstcase;
	}

	/**
	 * Gets the technical case id.
	 * 
	 * @return the technical case id
	 */
	public long getTechnicalCaseId() {
		return technicalCaseId;
	}

	/**
	 * Sets the technical case id.
	 * 
	 * @param technicalCaseId the new technical case id
	 */
	public void setTechnicalCaseId(long technicalCaseId) {
		this.technicalCaseId = technicalCaseId;
	}

	/**
	 * Gets the coast down to save.
	 * 
	 * @return the coast down to save
	 */
	public CoastdownRepresentation getCoastDownToSave() {
		return coastDownToSave;
	}

	/**
	 * Sets the coast down to save.
	 * 
	 * @param coastDownToSave the new coast down to save
	 */
	public void setCoastDownToSave(CoastdownRepresentation coastDownToSave) {
		this.coastDownToSave = coastDownToSave;
	}

	/**
	 * Gets the valued coast down.
	 * 
	 * @return the valued coast down
	 */
	public ValuedCoastDownRepresentation getValuedCoastDown() {
		return valuedCoastDown;
	}

	/**
	 * Sets the valued coast down.
	 * 
	 * @param valuedCoastDown the new valued coast down
	 */
	public void setValuedCoastDown(ValuedCoastDownRepresentation valuedCoastDown) {
		this.valuedCoastDown = valuedCoastDown;
	}

	/**
	 * Gets the min co 2 limit tvv.
	 * 
	 * @return the min co 2 limit tvv
	 */
	public double getMinCo2LimitTvv() {
		return minCo2LimitTvv;
	}

	/**
	 * Sets the min co 2 limit tvv.
	 * 
	 * @param minCo2LimitTvv the new min co 2 limit tvv
	 */
	public void setMinCo2LimitTvv(double minCo2LimitTvv) {
		this.minCo2LimitTvv = minCo2LimitTvv;
	}

	/**
	 * Gets the max co 2 limit tvv.
	 * 
	 * @return the max co 2 limit tvv
	 */
	public double getMaxCo2LimitTvv() {
		return maxCo2LimitTvv;
	}

	/**
	 * Sets the max co 2 limit tvv.
	 * 
	 * @param maxCo2LimitTvv the new max co 2 limit tvv
	 */
	public void setMaxCo2LimitTvv(double maxCo2LimitTvv) {
		this.maxCo2LimitTvv = maxCo2LimitTvv;
	}

	/**
	 * Gets the type approval area.
	 * 
	 * @return the type approval area
	 */
	public TypeApprovalAreaRepresentation getTypeApprovalArea() {
		return typeApprovalArea;
	}

	/**
	 * Sets the type approval area.
	 * 
	 * @param typeApprovalArea the new type approval area
	 */
	public void setTypeApprovalArea(TypeApprovalAreaRepresentation typeApprovalArea) {
		this.typeApprovalArea = typeApprovalArea;
	}

	/**
	 * Gets the display date.
	 * 
	 * @return the display date
	 */
	public String getDisplayDate() {
		return displayDate;
	}

	/**
	 * Sets the display date.
	 * 
	 * @param displayDate the new display date
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * Gets the show worst caseicon.
	 * 
	 * @return the showWorstCaseicon
	 */
	public int getShowWorstCaseicon() {
		if (tgWorstcase) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Sets the show worst caseicon.
	 * 
	 * @param showWorstCaseicon the showWorstCaseicon to set
	 */
	public void setShowWorstCaseicon(int showWorstCaseicon) {
	}

	/**
	 * Gets the test nature.
	 * 
	 * @return the test nature
	 */
	public TestNatureRepresentation getTestNature() {
		return testNature;
	}

	/**
	 * Sets the test nature.
	 * 
	 * @param testNature the new test nature
	 */
	public void setTestNature(TestNatureRepresentation testNature) {
		this.testNature = testNature;
	}

	/**
	 * Gets the status to save.
	 * 
	 * @return the status to save
	 */
	public StatusRepresentation getStatusToSave() {
		return statusToSave;
	}

	/**
	 * Sets the status to save.
	 * 
	 * @param statusToSave the new status to save
	 */
	public void setStatusToSave(StatusRepresentation statusToSave) {
		this.statusToSave = statusToSave;
	}

	/**
	 * Gets the category.
	 * 
	 * @return the category
	 */
	public CategoryRepresentation getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 * 
	 * @param category the new category
	 */
	public void setCategory(CategoryRepresentation category) {
		this.category = category;
	}

	/**
	 * Gets the vehical technology.
	 * 
	 * @return the vehical technology
	 */
	public VehicleTechnologyRepresentation getVehicalTechnology() {
		return vehicalTechnology;
	}

	/**
	 * Sets the vehical technology.
	 * 
	 * @param vehicalTechnology the new vehical technology
	 */
	public void setVehicalTechnology(VehicleTechnologyRepresentation vehicalTechnology) {
		this.vehicalTechnology = vehicalTechnology;
	}

	/**
	 * Gets the car factory list.
	 * 
	 * @return the car factory list
	 */
	public List<CarFactoryRepresentation> getCarFactoryList() {
		return carFactoryList;
	}

	/**
	 * Sets the car factory list.
	 * 
	 * @param carFactoryList the new car factory list
	 */
	public void setCarFactoryList(List<CarFactoryRepresentation> carFactoryList) {
		this.carFactoryList = carFactoryList;
	}

	/**
	 * Gets the pg label.
	 * 
	 * @return the pg label
	 */
	public String getPgLabel() {
		return pgLabel;
	}

	/**
	 * Sets the pg label.
	 * 
	 * @param pgLabel the new pg label
	 */
	public void setPgLabel(String pgLabel) {
		this.pgLabel = pgLabel;
	}

	/**
	 * Gets the clasz representation.
	 *
	 * @return the clasz representation
	 */
	public ClaszRepresentation getClaszRepresentation() {
		return claszRepresentation;
	}

	/**
	 * Sets the clasz representation.
	 *
	 * @param claszRepresentation the new clasz representation
	 */
	public void setClaszRepresentation(ClaszRepresentation claszRepresentation) {
		this.claszRepresentation = claszRepresentation;
	}

	/**
	 * Gets the fuel injection.
	 *
	 * @return the fuel injection
	 */
	public FuelInjectionTypeRepresentation getFuelInjection() {
		return fuelInjection;
	}

	/**
	 * Sets the fuel injection.
	 *
	 * @param fuelInjection the new fuel injection
	 */
	public void setFuelInjection(FuelInjectionTypeRepresentation fuelInjection) {
		this.fuelInjection = fuelInjection;
	}

	/**
	 * Gets the country list.
	 *
	 * @return the country list
	 */
	public List<CountryRepresentation> getCountryList() {
		return countryList;
	}

	/**
	 * Sets the country list.
	 *
	 * @param countryList the new country list
	 */
	public void setCountryList(List<CountryRepresentation> countryList) {
		this.countryList = countryList;
	}

	public String getSampleLabel() {
		return sampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}

}

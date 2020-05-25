package com.inetpsa.poc00.domain.genometvvrule;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.bodywork.Bodywork;
import com.inetpsa.poc00.domain.carbrand.CarBrand;
import com.inetpsa.poc00.domain.engine.Engine;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.finalreductionratio.FinalReductionRatio;
import com.inetpsa.poc00.domain.fueltype.FuelType;
import com.inetpsa.poc00.domain.gearbox.GearBox;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;

/**
 * The Class GenomeTVVRule.
 */
@Entity
@Table(name = "COPQTGTV")
public class GenomeTVVRule extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TVV_RULE_ID")
	private Long entityId;

	/** The genome lcdv code value. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LCDV_CODE_VALUE_ID")
	private GenomeLCDVCodeValue genomeLcdvCodeValue;

	/** The rule id. */
	@Column(name = "RULE_IDENTIFIANT")
	private String ruleId;

	/** The kmat. */
	@Column(name = "KMAT")
	private String kmat;

	/** The lcdv code name. */
	@Column(name = "LCDV_CODE_NAME")
	private String lcdvCodeName;

	/** The lcdv code value. */
	@Column(name = "LCDV_CODE_VALUE")
	private String lcdvCodeValue;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate = new Date();

	/** The fuel type. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = FuelType.class, cascade = CascadeType.ALL)
	private FuelType fuelType;

	/** The project code family. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = ProjectCodeFamily.class, cascade = CascadeType.ALL)
	private ProjectCodeFamily projectCodeFamily;

	/** The bodywork. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = Bodywork.class, cascade = CascadeType.ALL)
	private Bodywork bodywork;

	/** The car brand. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = CarBrand.class, cascade = CascadeType.ALL)
	private CarBrand carBrand;

	/** The final reduction ratio. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = FinalReductionRatio.class, cascade = CascadeType.ALL)
	private FinalReductionRatio finalReductionRatio;

	/** The emission standard. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = EmissionStandard.class, cascade = CascadeType.ALL)
	private EmissionStandard emissionStandard;

	/** The engine b0f. */
	@OneToOne(mappedBy = "genomeTvvRule", targetEntity = Engine.class, cascade = CascadeType.ALL)
	private Engine engineB0f;

	/** The engine doc. */
	@OneToMany(mappedBy = "genomeTvvRuleDOC", targetEntity = Engine.class, cascade = CascadeType.ALL)
	private List<Engine> engineDOC;

	/** The gear box dbm. */
	@OneToOne(mappedBy = "genomeTvvRuleDBM", targetEntity = GearBox.class)
	private GearBox gearBoxDBM;

	/** The gear box b0 g. */
	@OneToMany(mappedBy = "genomeTvvRuleB0G", targetEntity = GearBox.class)
	private List<GearBox> gearBoxB0G;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public GenomeTVVRule() {
		super();
	}

	/**
	 * Instantiates a new genome tvv rule.
	 * 
	 * @param obj the obj
	 */
	public GenomeTVVRule(GenomeTVVRule obj) {
		this.ruleId = obj.getRuleId();
		this.kmat = obj.getKmat();
		this.lcdvCodeName = obj.getLcdvCodeName();
		this.lcdvCodeValue = obj.getLcdvCodeValue();
	}

	/**
	 * Sets the last update.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

    /*
     * (non-Javadoc)
     * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the rule id.
	 * 
	 * @return the rule id
	 */
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * Sets the rule id.
	 * 
	 * @param ruleId the new rule id
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * Gets the kmat.
	 * 
	 * @return the kmat
	 */
	public String getKmat() {
		return kmat;
	}

	/**
	 * Sets the kmat.
	 * 
	 * @param kmat the new kmat
	 */
	public void setKmat(String kmat) {
		this.kmat = kmat;
	}

	/**
	 * Gets the lcdv code name.
	 * 
	 * @return the lcdv code name
	 */
	public String getLcdvCodeName() {
		return lcdvCodeName;
	}

	/**
	 * Sets the lcdv code name.
	 * 
	 * @param lcdvCodeName the new lcdv code name
	 */
	public void setLcdvCodeName(String lcdvCodeName) {
		this.lcdvCodeName = lcdvCodeName;
	}

	/**
	 * Gets the lcdv code value.
	 * 
	 * @return the lcdv code value
	 */
	public String getLcdvCodeValue() {
		return lcdvCodeValue;
	}

	/**
	 * Sets the lcdv code value.
	 * 
	 * @param lcdvCodeValue the new lcdv code value
	 */
	public void setLcdvCodeValue(String lcdvCodeValue) {
		this.lcdvCodeValue = lcdvCodeValue;
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
	 * Gets the update date.
	 * 
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Sets the update date.
	 * 
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Gets the fuel type.
	 * 
	 * @return the fuel type
	 */
	public FuelType getFuelType() {
		return fuelType;

	}

	/**
	 * Sets the fuel type.
	 * 
	 * @param fuelType the new fuel type
	 */
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;

	}

	/**
	 * Gets the project code family.
	 * 
	 * @return the project code family
	 */
	public ProjectCodeFamily getProjectCodeFamily() {
		return projectCodeFamily;

	}

	/**
	 * Sets the project code family.
	 * 
	 * @param projectCodeFamily the new project code family
	 */
	public void setProjectCodeFamily(ProjectCodeFamily projectCodeFamily) {
		this.projectCodeFamily = projectCodeFamily;

	}

	/**
	 * Gets the bodywork.
	 * 
	 * @return the bodywork
	 */
	public Bodywork getBodywork() {
		return bodywork;
	}

	/**
	 * Sets the bodywork.
	 * 
	 * @param bodywork the new bodywork
	 */
	public void setBodywork(Bodywork bodywork) {
		this.bodywork = bodywork;
	}

	/**
	 * Gets the car brand.
	 * 
	 * @return the car brand
	 */
	public CarBrand getCarBrand() {
		return carBrand;
	}

	/**
	 * Sets the car brand.
	 * 
	 * @param carBrand the new car brand
	 */
	public void setCarBrand(CarBrand carBrand) {
		this.carBrand = carBrand;
	}

	/**
	 * Gets the final reduction ratio.
	 * 
	 * @return the final reduction ratio
	 */
	public FinalReductionRatio getFinalReductionRatio() {
		return finalReductionRatio;
	}

	/**
	 * Sets the final reduction ratio.
	 * 
	 * @param finalReductionRatio the new final reduction ratio
	 */
	public void setFinalReductionRatio(FinalReductionRatio finalReductionRatio) {
		this.finalReductionRatio = finalReductionRatio;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandard getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandard emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	/**
	 * Gets the engine.
	 * 
	 * @return the engine
	 */
	public Engine getEngine() {
		return engineB0f;
	}

	/**
	 * Sets the engine.
	 * 
	 * @param engine the new engine
	 */
	public void setEngine(Engine engine) {
		this.engineB0f = engine;
	}

	/**
	 * Gets the gear box dbm.
	 * 
	 * @return the gear box dbm
	 */
	public GearBox getGearBoxDBM() {
		return gearBoxDBM;
	}

	/**
	 * Sets the gear box dbm.
	 * 
	 * @param gearBoxDBM the new gear box dbm
	 */
	public void setGearBoxDBM(GearBox gearBoxDBM) {
		this.gearBoxDBM = gearBoxDBM;
	}

	/**
	 * Gets the engine b0f.
	 * 
	 * @return the engine b0f
	 */
	public Engine getEngineB0f() {
		return engineB0f;
	}

	/**
	 * Sets the engine b0f.
	 * 
	 * @param engineB0f the new engine b0f
	 */
	public void setEngineB0f(Engine engineB0f) {
		this.engineB0f = engineB0f;
	}

	/**
	 * Gets the engine doc.
	 * 
	 * @return the engine doc
	 */
	public List<Engine> getEngineDOC() {
		return engineDOC;
	}

	/**
	 * Sets the engine doc.
	 * 
	 * @param engineDOC the new engine doc
	 */
	public void setEngineDOC(List<Engine> engineDOC) {
		this.engineDOC = engineDOC;
	}

	/**
	 * Gets the gear box b0 g.
	 * 
	 * @return the gear box b0 g
	 */
	public List<GearBox> getGearBoxB0G() {
		return gearBoxB0G;
	}

	/**
	 * Sets the gear box b0 g.
	 * 
	 * @param gearBoxB0G the new gear box b0 g
	 */
	public void setGearBoxB0G(List<GearBox> gearBoxB0G) {
		this.gearBoxB0G = gearBoxB0G;
	}

	/**
	 * @return the genomeLcdvCodeValue
	 */
	public GenomeLCDVCodeValue getGenomeLcdvCodeValue() {
		return genomeLcdvCodeValue;
	}

	/**
	 * @param genomeLcdvCodeValue the genomeLcdvCodeValue to set
	 */
	public void setGenomeLcdvCodeValue(GenomeLCDVCodeValue genomeLcdvCodeValue) {
		this.genomeLcdvCodeValue = genomeLcdvCodeValue;
	}

	/**
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return kmat + DomainConstants.VERSION_SEPARATOR + lcdvCodeName + DomainConstants.VERSION_SEPARATOR + lcdvCodeValue;
	}
}

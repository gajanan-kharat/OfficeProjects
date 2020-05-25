package com.inetpsa.poc00.export.bce;

/**
 * The Class BceDto.
 */
public class ExportBCERepresentation {

	/** The regulation group. */
	String regulationGroup;

	/** The technical group. */
	String technicalGroup;

	/** The type approval area label. */
	/* BCE FIELD : REGION HOMOLOGATION
	 * Class	 : Type Approval Area
	 * Attribute : Label
	 */
	String typeApprovalAreaLabel;

	/** The country label. */
	/* BCE FIELD : PAYS
	*  Class	 : Country
	*  Attribute : Label
	*/
	String countryLabel;

	/** The vehicle family label. */
	/* BCE FIELD : TYPE
	*  Class	 : Project Code/Family
	*  Attribute : Vehicle Family Label
	*/
	String vehicleFamilyLabel;

	/** The engine label. */
	/* BCE FIELD : MOTEUR 
	*  Class	 : ENGINE
	*  Attribute : Engine Label
	*/
	String engineLabel;

	/** The gear box label. */
	/* BCE FIELD : B.V.
	*  Class	 : Gear Box
	*  Attribute : Gear Box Label
	*/
	String gearBoxLabel;

	/** The tvv label. */
	/* BCE FIELD : TVV 
	*  Class	 : Technical Database TVV
	*  Attribute : Tvv Label
	*/
	String tvvLabel;

	/** The body work label. */
	/* BCE FIELD : SILHOUETTE	 
	*  Class	 : BodyWork
	*  Attribute : BodyWork Label
	*/
	String bodyWorkLabel;

	/** The engine power cv. */
	/* BCE FIELD : Puissance CV
	*  Class	 : Engine
	*  Attribute : Power (CV)
	*/
	String enginePowerCV;

	/** The engine powerk w. */
	/* BCE FIELD : Puissance Kw
	*  Class	 : Engine
	*  Attribute : Power (kW)
	*/
	String enginePowerkW;

	/** The engine torque nm. */
	/* BCE FIELD : Couple
	*  Class	 : Engine
	*  Attribute : Torque (Nm)
	*/
	String engineTorqueNm;

	/** The inertial value. */
	/* BCE FIELD : Inertie
	*  Class	 : Valued Inertia
	*  Attribute : Value
	*/
	Integer inertialValue;

	/** The tvv valued td value ancien. */
	/* BCE FIELD : Catalyseur ancien
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValuedTDValueAncien;

	/** The tvv valued td value actuel. */
	/* BCE FIELD : Catalyseur actuel 
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValuedTDValueActuel;

	/** The tvv valued td value remarques. */
	/* BCE FIELD : REMARQUES
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValuedTDValueRemarques;

	/** The valued cdpsa ref label. */
	/* BCE FIELD : Coast-down
	*  Class	 : Valued Coast Down List
	*  Attribute : PSA Reference
	*/
	String valuedCDPSARefLabel;

	/** The max default val tvv val pgl. */
	/* BCE FIELD : Limite CO2
	*  Class	 : TVV Valued TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String maxDefaultValTvvValPGL;

	/** The tvv valued test condition. */
	/* BCE FIELD : Cycle
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value (Label)
	*/
	String tvvValuedTCCycle;

	/** The emission standard label. */
	/* BCE FIELD : Limite
	*  Class	 : Emission Standard
	*  Attribute : Label
	*/
	String emissionStandardLabel;

	/** The emission standard Version. */
	/* BCE FIELD : Version Réglementation
	*  Class	 : Emission Standard
	*  Attribute : Version
	*/
	String emissionStandardVersion;

	/** The fuel label. */
	/* BCE FIELD : CARBURANT
	*  Class	 : Fuel
	*  Attribute : Label
	*/
	String fuelLabel;

	/** The theoricalf0. */
	/* BCE FIELD : F0
	*  Class	 : Valued Coast Down List
	*  Attribute : Theorical f0
	*/
	Double theoricalf0;

	/** The theoricalf1. */
	/* BCE FIELD : F1
	*  Class	 : Valued Coast Down List
	*  Attribute : Theorical f1
	*/
	Double theoricalf1;

	/** The theoricalf2. */
	/* BCE FIELD : F2
	*  Class	 : Valued Coast Down List
	*  Attribute : Theorical f2
	*/
	Double theoricalf2;

	/** The computed benchf0. */
	/* BCE FIELD : F0
	*  Class	 : Valued Coast Down List
	*  Attribute : Computed Bench f0
	*/
	Double computedBenchf0;

	/** The computed benchf1. */
	/* BCE FIELD : F1
	*  Class	 : Valued Coast Down List
	*  Attribute : Computed Bench f1
	*/
	Double computedBenchf1;

	/** The computed benchf2. */
	/* BCE FIELD : F2
	*  Class	 : Valued Coast Down List
	*  Attribute : Computed Bench f2
	*/
	Double computedBenchf2;

	/** The user benchf0. */
	/* BCE FIELD : valeur F0 saisie
	*  Class	 : Valued Coast Down List
	*  Attribute : User Bench f0
	*/
	Double userBenchf0;

	/** The user benchf1. */
	/* BCE FIELD : User Bench f1
	*  Class	 : Valued Coast Down List
	*  Attribute : User Bench f1
	*/
	Double userBenchf1;

	/** The user benchf2. */
	/* BCE FIELD : valeur F2 saisie
	*  Class	 : Valued Coast Down List
	*  Attribute : User Bench f2
	*/
	Double userBenchf2;

	/** The tvv vald tc default val_ pente. */
	/* BCE FIELD : Pente
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Default Value
	*/
	String tvvValdTCDefaultValPente;

	/** The fd forfaitaire. */
	String fdForfaitaire;

	/** The tvv valued fc default val_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCO;

	/** The tvv valued fc default val_ hc. */
	/* BCE FIELD : HC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValHC;

	/** The tvv valued fc default val_ nmhc. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValNMHC;

	/** The tvv valued fc default val_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValNOX;

	/** The tvv valued fc default val_ hcnox. */
	/* BCE FIELD : HC + NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValHCNOX;

	/** The tvv valued fc default val_ part masse. */
	/* BCE FIELD : PART Masse
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValPartMasse;

	/** The tvv valued fc default val_ part nombre. */
	/* BCE FIELD : PART Nombre
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValPartNombre;

	/** The tvv valued fc default val_ c o2. */
	/* BCE FIELD : CO2
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCO2;

	/** The coef evol. */
	String coefEvol;

	/** The tvv valued fc default val_coef evol_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolCO;

	/** The tvv valued fc default val_coef evol_ hc. */
	/* BCE FIELD : HC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value 
	*/
	Double tvvValdFCDefaultValCoefEvolHC;

	/** The tvv valued fc default val_coef evol_ hcnox. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolNMHC;

	/** The tvv valued fc default val_coef evol_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolNOX;

	/** The tvv valued fc default val_coef evol_ hcnox. */
	/* BCE FIELD : HC+NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolHCNOX;

	/** The tvv valued fc default val_coef evol_ part masse. */
	/* BCE FIELD : PART Masse
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolPartMasse;

	/** The tvv valued fc default val_coef evol_ part nombre. */
	/* BCE FIELD : PART Nbre
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolPartNombre;

	/** The tvv valued fc default val_coef evol_ c o2. */
	/* BCE FIELD : CO2
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvValdFCDefaultValCoefEvolCO2;

	/** The coef fap. */
	String coefFAP;

	/* BCE FIELD : CO
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ co. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPCO;

	/* BCE FIELD : HC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ hc. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPHC;

	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ nox. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPNOX;

	/* BCE FIELD : HC + NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ hcnox. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPHCNOX;

	/* BCE FIELD : PART Masse
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ part masse. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPPartMasse;

	/* BCE FIELD : PART Nbre
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ part nbre. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPPartNbre;

	/* BCE FIELD : CO2
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	/** The tvv valued fc default val_coef fa p_ c o2. */
	// TVV Valued Factor or Coefficient
	Double tvvValdFCDefaultValCoefFAPCO2;

	/** The tvv valued pg max default val_ hc. */
	/* BCE FIELD : HC
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValHC;

	/** The tvv valued pg max default val_ nmhc. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValNMHC;

	/** The tvv valued pg max default val_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValNOX;

	/** The tvv valued pg max default val_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValCO;

	/** The tvv valued pg max default val_ hcnox. */
	/* BCE FIELD : HC + NOX
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValHCNOX;

	/** The tvv valued pg max default val_ part masse. */
	/* BCE FIELD : PARTICULES masse
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValPartMasse;

	/** The tvv valued pg max default val_ part nombre. */
	/* BCE FIELD : PARTICULES Nombre
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	String tvvValdPGMaxDefaultValPartNombre;

	/** The tvv valued td value_ eobd. */
	/* BCE FIELD : EOBD 
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValEOBD;

	/** The tvv valued td value_ iupr. */
	/* BCE FIELD : IUPR
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValIUPR;

	/** The tvv valued n soft_ ancien. */
	/* BCE FIELD : N° Soft OBD ancien
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdNSoftAncien;

	/** The tvv valued n soft_ actuel. */
	/* BCE FIELD : N° Soft OBD actuel
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdNSoftActuel;

	/** The tvv valued td val_ eob d_remarques. */
	/* BCE FIELD : Remarques EOBD
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValEOBDRemarques;

	/** The tvv valued tcm log. */
	/* BCE FIELD : MLog
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValuedTCMLog;

	/** The tvv valued tech data value. */
	/* BCE FIELD : Calculateur
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValueCalclr;

	/** The fuel injection type label. */
	/* BCE FIELD : Injection
	*  Class	 : Fuel Injection Type
	*  Attribute : Label
	*/
	String fuelInjectionTypeLabel;

	/* BCE FIELD : Opacite
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Default Value
	*/
	/** The tvv valued tc default value. */
	// Opacite
	String tvvValuedTCDefaultValue;

	/* BCE FIELD : Regime
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	/** The tvv valued test condition value. */
	// Regime
	String tvvValdTCValueRegime;

	/* BCE FIELD : SOC Prépa
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Default Value
	*/
	/** The tvv valued tc def value_ soca. */
	// SOC Prepa
	String tvvValdTCDefValueSOCA;

	/* BCE FIELD : SOC Avant test
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Default Value
	*/
	/** The tvv valued tc def val_ soc a_ avant. */
	// SOC Avant test
	String tvvValdTCDefValSOCAAvant;

	/** The tvv valued tc value_ pocket bsi. */
	/* BCE FIELD : Pocket BSI
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValuePocketBSI;

	/** The tvv valued tc val_ manchon. */
	/* BCE FIELD : Manchon
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValManchon;

	/** The tvv valued tc val_ appren. */
	/* BCE FIELD : APPRENTISSAGE PILOTE
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValAppren;

	/** The tvv valued tc val_ phase1. */
	/* BCE FIELD : PHASE 1
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValPhase1;

	/** The tvv valued tc val_ phase2. */
	/* BCE FIELD : PHASE 2
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValPhase2;

	/** The tvv valued tc val_ phase3. */
	/* BCE FIELD : PHASE 3
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValPhase3;

	/** The tvv valued tc val_ debit dls. */
	/* BCE FIELD : Débit DLS
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValDebitDLS;

	/** The tvv valued tc val_ phases sac. */
	/* BCE FIELD : Phases SAC
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValPhasesSAC;

	/** The tvv valued tc val_ phases parti. */
	/* BCE FIELD : 
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValPhasesParti;

	/** The tvv valued tc val_ obsrv prepa. */
	/* BCE FIELD : OBSERVATIONS PREPA
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValObsrvPrepa;

	/** The tvv valued tc val_ obsrv test. */
	/* BCE FIELD : OBSERVATIONS TEST
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value
	*/
	String tvvValdTCValObsrvTest;

	/** The tvv valued td val_ pollu. */
	/* BCE FIELD : PV UTAC POLLU
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValPollu;

	/** The tvv valued td val_ conso. */
	/* BCE FIELD : PV UTAC CONSO
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValConso;

	/** The tvv valued td val_ etape emission. */
	/* BCE FIELD : Etape Emissions
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValEtapeEmission;

	/** The tvv valued td val_ etape obd. */
	/* BCE FIELD : Etape OBD
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValEtapeOBD;

	/** The tvv value td val_ obsrv1. */
	/* BCE FIELD : OBSERVATIONS 1
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValueTDValObsrv1;

	/** The tvv value td val_ obsrv2. */
	/* BCE FIELD : OBSERVATIONS 2
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValueTDValObsrv2;

	/** The category label. */
	/* BCE FIELD : Catégorie
	*  Class	 : Category
	*  Attribute : Label
	*/
	String categoryLabel;

	/** The class label. */
	/* BCE FIELD : Classe
	*  Class	 : Class
	*  Attribute : Label
	*/
	String classLabel;

	/** The status label. */
	/* BCE FIELD : Statut
	*  Class	 : Status
	*  Attribute : Label
	*/
	String statusLabel;

	/** The test nature label. */
	/* BCE FIELD : Nature de test
	*  Class	 : Test Nature
	*  Attribute : Value
	*/
	String testNatureLabel;

	/** The vehicle technology. */
	/* BCE FIELD : Technologie Véhicule
	*  Class	 : Vehicle Technology
	*  Attribute : Label
	*/
	String vehicleTechnology;

	/** The sampling rule. */
	/* BCE FIELD : Règle de prélèvement
	*  Class	 : Sampling Rule
	*  Attribute : Label
	*/
	String samplingRule;

	/** The car maker brand label. */
	/* BCE FIELD : Marque
	*  Class	 : Car Maker / Car Brand
	*  Attribute : Car Brand label
	*/
	String carMakerBrandLabel;

	/** The factory. */
	/* BCE FIELD : Usine
	*  Class	 : Factory
	*  Attribute : Label
	*/
	String factory;

	/** The final reduction rationlabel. */
	/* BCE FIELD : Ratio final de réduction (Rapport de pont)
	*  Class	 : Final Reduction Ratio
	*  Attribute : Label
	*/
	String finalReductionRationlabel;

	/**
	 * Gets the type approval area label.
	 * 
	 * @return the typeApprovalAreaLabel
	 */
	public String getTypeApprovalAreaLabel() {
		return typeApprovalAreaLabel;
	}

	/**
	 * Sets the type approval area label.
	 * 
	 * @param typeApprovalAreaLabel the typeApprovalAreaLabel to set
	 */
	public void setTypeApprovalAreaLabel(String typeApprovalAreaLabel) {
		this.typeApprovalAreaLabel = typeApprovalAreaLabel;
	}

	/**
	 * Gets the country label.
	 * 
	 * @return the countryLabel
	 */
	public String getCountryLabel() {
		return countryLabel;
	}

	/**
	 * Sets the country label.
	 * 
	 * @param countryLabel the countryLabel to set
	 */
	public void setCountryLabel(String countryLabel) {
		this.countryLabel = countryLabel;
	}

	/**
	 * Gets the vehicle family label.
	 * 
	 * @return the vehicleFamilyLabel
	 */
	public String getVehicleFamilyLabel() {
		return vehicleFamilyLabel;
	}

	/**
	 * Sets the vehicle family label.
	 * 
	 * @param vehicleFamilyLabel the vehicleFamilyLabel to set
	 */
	public void setVehicleFamilyLabel(String vehicleFamilyLabel) {
		this.vehicleFamilyLabel = vehicleFamilyLabel;
	}

	/**
	 * Gets the engine label.
	 * 
	 * @return the engineLabel
	 */
	public String getEngineLabel() {
		return engineLabel;
	}

	/**
	 * Sets the engine label.
	 * 
	 * @param engineLabel the engineLabel to set
	 */
	public void setEngineLabel(String engineLabel) {
		this.engineLabel = engineLabel;
	}

	/**
	 * Gets the gear box label.
	 * 
	 * @return the gearBoxLabel
	 */
	public String getGearBoxLabel() {
		return gearBoxLabel;
	}

	/**
	 * Sets the gear box label.
	 * 
	 * @param gearBoxLabel the gearBoxLabel to set
	 */
	public void setGearBoxLabel(String gearBoxLabel) {
		this.gearBoxLabel = gearBoxLabel;
	}

	/**
	 * Gets the tvv label.
	 * 
	 * @return the tvvLabel
	 */
	public String getTvvLabel() {
		return tvvLabel;
	}

	/**
	 * Sets the tvv label.
	 * 
	 * @param tvvLabel the tvvLabel to set
	 */
	public void setTvvLabel(String tvvLabel) {
		this.tvvLabel = tvvLabel;
	}

	/**
	 * Gets the body work label.
	 * 
	 * @return the bodyWorkLabel
	 */
	public String getBodyWorkLabel() {
		return bodyWorkLabel;
	}

	/**
	 * Sets the body work label.
	 * 
	 * @param bodyWorkLabel the bodyWorkLabel to set
	 */
	public void setBodyWorkLabel(String bodyWorkLabel) {
		this.bodyWorkLabel = bodyWorkLabel;
	}

	/**
	 * Gets the engine power cv.
	 * 
	 * @return the enginePowerCV
	 */
	public String getEnginePowerCV() {
		return enginePowerCV;
	}

	/**
	 * Sets the engine power cv.
	 * 
	 * @param enginePowerCV the enginePowerCV to set
	 */
	public void setEnginePowerCV(String enginePowerCV) {
		this.enginePowerCV = enginePowerCV;
	}

	/**
	 * Gets the engine powerk w.
	 * 
	 * @return the enginePowerkW
	 */
	public String getEnginePowerkW() {
		return enginePowerkW;
	}

	/**
	 * Sets the engine powerk w.
	 * 
	 * @param enginePowerkW the enginePowerkW to set
	 */
	public void setEnginePowerkW(String enginePowerkW) {
		this.enginePowerkW = enginePowerkW;
	}

	/**
	 * Gets the engine torque nm.
	 * 
	 * @return the engineTorqueNm
	 */
	public String getEngineTorqueNm() {
		return engineTorqueNm;
	}

	/**
	 * Sets the engine torque nm.
	 * 
	 * @param engineTorqueNm the engineTorqueNm to set
	 */
	public void setEngineTorqueNm(String engineTorqueNm) {
		this.engineTorqueNm = engineTorqueNm;
	}

	/**
	 * Gets the inertial value.
	 * 
	 * @return the inertialValue
	 */
	public Integer getInertialValue() {
		return inertialValue;
	}

	/**
	 * Sets the inertial value.
	 * 
	 * @param inertialValue the inertialValue to set
	 */
	public void setInertialValue(Integer inertialValue) {
		this.inertialValue = inertialValue;
	}

	/**
	 * Gets the tvv valued td value ancien.
	 * 
	 * @return the tvvValuedTDValueAncien
	 */
	public String getTvvValuedTDValueAncien() {
		return tvvValuedTDValueAncien;
	}

	/**
	 * Sets the tvv valued td value ancien.
	 * 
	 * @param tvvValuedTDValueAncien the tvvValuedTDValueAncien to set
	 */
	public void setTvvValuedTDValueAncien(String tvvValuedTDValueAncien) {
		this.tvvValuedTDValueAncien = tvvValuedTDValueAncien;
	}

	/**
	 * Gets the tvv valued td value actuel.
	 * 
	 * @return the tvvValuedTDValueActuel
	 */
	public String getTvvValuedTDValueActuel() {
		return tvvValuedTDValueActuel;
	}

	/**
	 * Sets the tvv valued td value actuel.
	 * 
	 * @param tvvValuedTDValueActuel the tvvValuedTDValueActuel to set
	 */
	public void setTvvValuedTDValueActuel(String tvvValuedTDValueActuel) {
		this.tvvValuedTDValueActuel = tvvValuedTDValueActuel;
	}

	/**
	 * Gets the tvv valued td value remarques.
	 * 
	 * @return the tvvValuedTDValueRemarques
	 */
	public String getTvvValuedTDValueRemarques() {
		return tvvValuedTDValueRemarques;
	}

	/**
	 * Sets the tvv valued td value remarques.
	 * 
	 * @param tvvValuedTDValueRemarques the tvvValuedTDValueRemarques to set
	 */
	public void setTvvValuedTDValueRemarques(String tvvValuedTDValueRemarques) {
		this.tvvValuedTDValueRemarques = tvvValuedTDValueRemarques;
	}

	/**
	 * Gets the valued cdpsa ref label.
	 * 
	 * @return the valuedCDPSARefLabel
	 */
	public String getValuedCDPSARefLabel() {
		return valuedCDPSARefLabel;
	}

	/**
	 * Sets the valued cdpsa ref label.
	 * 
	 * @param valuedCDPSARefLabel the valuedCDPSARefLabel to set
	 */
	public void setValuedCDPSARefLabel(String valuedCDPSARefLabel) {
		this.valuedCDPSARefLabel = valuedCDPSARefLabel;
	}

	/**
	 * Gets the max default val tvv val pgl.
	 * 
	 * @return the maxDefaultValTvvValPGL
	 */
	public String getMaxDefaultValTvvValPGL() {
		return maxDefaultValTvvValPGL;
	}

	/**
	 * Sets the max default val tvv val pgl.
	 * 
	 * @param maxDefaultValTvvValPGL the maxDefaultValTvvValPGL to set
	 */
	public void setMaxDefaultValTvvValPGL(String maxDefaultValTvvValPGL) {
		this.maxDefaultValTvvValPGL = maxDefaultValTvvValPGL;
	}

	/**
	 * Gets the emission standard label.
	 * 
	 * @return the emissionStandardLabel
	 */
	public String getEmissionStandardLabel() {
		return emissionStandardLabel;
	}

	/**
	 * Sets the emission standard label.
	 * 
	 * @param emissionStandardLabel the emissionStandardLabel to set
	 */
	public void setEmissionStandardLabel(String emissionStandardLabel) {
		this.emissionStandardLabel = emissionStandardLabel;
	}

	/**
	 * Gets the fuel label.
	 * 
	 * @return the fuelLabel
	 */
	public String getFuelLabel() {
		return fuelLabel;
	}

	/**
	 * Sets the fuel label.
	 * 
	 * @param fuelLabel the fuelLabel to set
	 */
	public void setFuelLabel(String fuelLabel) {
		this.fuelLabel = fuelLabel;
	}

	/**
	 * Gets the theoricalf0.
	 * 
	 * @return the theoricalf0
	 */
	public Double getTheoricalf0() {
		return theoricalf0;
	}

	/**
	 * Sets the theoricalf0.
	 * 
	 * @param theoricalf0 the theoricalf0 to set
	 */
	public void setTheoricalf0(Double theoricalf0) {
		this.theoricalf0 = theoricalf0;
	}

	/**
	 * Gets the theoricalf1.
	 * 
	 * @return the theoricalf1
	 */
	public Double getTheoricalf1() {
		return theoricalf1;
	}

	/**
	 * Sets the theoricalf1.
	 * 
	 * @param theoricalf1 the theoricalf1 to set
	 */
	public void setTheoricalf1(Double theoricalf1) {
		this.theoricalf1 = theoricalf1;
	}

	/**
	 * Gets the theoricalf2.
	 * 
	 * @return the theoricalf2
	 */
	public Double getTheoricalf2() {
		return theoricalf2;
	}

	/**
	 * Sets the theoricalf2.
	 * 
	 * @param theoricalf2 the theoricalf2 to set
	 */
	public void setTheoricalf2(Double theoricalf2) {
		this.theoricalf2 = theoricalf2;
	}

	/**
	 * Gets the computed benchf0.
	 * 
	 * @return the computedBenchf0
	 */
	public Double getComputedBenchf0() {
		return computedBenchf0;
	}

	/**
	 * Sets the computed benchf0.
	 * 
	 * @param computedBenchf0 the computedBenchf0 to set
	 */
	public void setComputedBenchf0(Double computedBenchf0) {
		this.computedBenchf0 = computedBenchf0;
	}

	/**
	 * Gets the computed benchf1.
	 * 
	 * @return the computedBenchf1
	 */
	public Double getComputedBenchf1() {
		return computedBenchf1;
	}

	/**
	 * Sets the computed benchf1.
	 * 
	 * @param computedBenchf1 the computedBenchf1 to set
	 */
	public void setComputedBenchf1(Double computedBenchf1) {
		this.computedBenchf1 = computedBenchf1;
	}

	/**
	 * Gets the computed benchf2.
	 * 
	 * @return the computedBenchf2
	 */
	public Double getComputedBenchf2() {
		return computedBenchf2;
	}

	/**
	 * Sets the computed benchf2.
	 * 
	 * @param computedBenchf2 the computedBenchf2 to set
	 */
	public void setComputedBenchf2(Double computedBenchf2) {
		this.computedBenchf2 = computedBenchf2;
	}

	/**
	 * Gets the user benchf0.
	 * 
	 * @return the userBenchf0
	 */
	public Double getUserBenchf0() {
		return userBenchf0;
	}

	/**
	 * Sets the user benchf0.
	 * 
	 * @param userBenchf0 the userBenchf0 to set
	 */
	public void setUserBenchf0(Double userBenchf0) {
		this.userBenchf0 = userBenchf0;
	}

	/**
	 * Gets the user benchf1.
	 * 
	 * @return the userBenchf1
	 */
	public Double getUserBenchf1() {
		return userBenchf1;
	}

	/**
	 * Sets the user benchf1.
	 * 
	 * @param userBenchf1 the userBenchf1 to set
	 */
	public void setUserBenchf1(Double userBenchf1) {
		this.userBenchf1 = userBenchf1;
	}

	/**
	 * Gets the user benchf2.
	 * 
	 * @return the userBenchf2
	 */
	public Double getUserBenchf2() {
		return userBenchf2;
	}

	/**
	 * Sets the user benchf2.
	 * 
	 * @param userBenchf2 the userBenchf2 to set
	 */
	public void setUserBenchf2(Double userBenchf2) {
		this.userBenchf2 = userBenchf2;
	}

	/**
	 * Gets the fd forfaitaire.
	 * 
	 * @return the fdForfaitaire
	 */
	public String getFdForfaitaire() {
		return fdForfaitaire;
	}

	/**
	 * Sets the fd forfaitaire.
	 * 
	 * @param fdForfaitaire the fdForfaitaire to set
	 */
	public void setFdForfaitaire(String fdForfaitaire) {
		this.fdForfaitaire = fdForfaitaire;
	}

	/**
	 * Gets the coef evol.
	 * 
	 * @return the coefEvol
	 */
	public String getCoefEvol() {
		return coefEvol;
	}

	/**
	 * Sets the coef evol.
	 * 
	 * @param coefEvol the coefEvol to set
	 */
	public void setCoefEvol(String coefEvol) {
		this.coefEvol = coefEvol;
	}

	/**
	 * Gets the coef fap.
	 * 
	 * @return the coefFAP
	 */
	public String getCoefFAP() {
		return coefFAP;
	}

	/**
	 * Sets the coef fap.
	 * 
	 * @param coefFAP the coefFAP to set
	 */
	public void setCoefFAP(String coefFAP) {
		this.coefFAP = coefFAP;
	}

	/**
	 * Gets the tvv valued tcm log.
	 * 
	 * @return the tvvValuedTCMLog
	 */
	public String getTvvValuedTCMLog() {
		return tvvValuedTCMLog;
	}

	/**
	 * Sets the tvv valued tcm log.
	 * 
	 * @param tvvValuedTCMLog the tvvValuedTCMLog to set
	 */
	public void setTvvValuedTCMLog(String tvvValuedTCMLog) {
		this.tvvValuedTCMLog = tvvValuedTCMLog;
	}

	/**
	 * Gets the fuel injection type label.
	 * 
	 * @return the fuelInjectionTypeLabel
	 */
	public String getFuelInjectionTypeLabel() {
		return fuelInjectionTypeLabel;
	}

	/**
	 * Sets the fuel injection type label.
	 * 
	 * @param fuelInjectionTypeLabel the fuelInjectionTypeLabel to set
	 */
	public void setFuelInjectionTypeLabel(String fuelInjectionTypeLabel) {
		this.fuelInjectionTypeLabel = fuelInjectionTypeLabel;
	}

	/**
	 * Gets the tvv valued tc default value.
	 * 
	 * @return the tvvValuedTCDefaultValue
	 */
	public String getTvvValuedTCDefaultValue() {
		return tvvValuedTCDefaultValue;
	}

	/**
	 * Sets the tvv valued tc default value.
	 * 
	 * @param tvvValuedTCDefaultValue the tvvValuedTCDefaultValue to set
	 */
	public void setTvvValuedTCDefaultValue(String tvvValuedTCDefaultValue) {
		this.tvvValuedTCDefaultValue = tvvValuedTCDefaultValue;
	}

	/**
	 * Gets the category label.
	 * 
	 * @return the categoryLabel
	 */
	public String getCategoryLabel() {
		return categoryLabel;
	}

	/**
	 * Sets the category label.
	 * 
	 * @param categoryLabel the categoryLabel to set
	 */
	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	/**
	 * Gets the class label.
	 * 
	 * @return the classLabel
	 */
	public String getClassLabel() {
		return classLabel;
	}

	/**
	 * Sets the class label.
	 * 
	 * @param classLabel the classLabel to set
	 */
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	/**
	 * Gets the status label.
	 * 
	 * @return the statusLabel
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Sets the status label.
	 * 
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Gets the test nature label.
	 * 
	 * @return the testNatureLabel
	 */
	public String getTestNatureLabel() {
		return testNatureLabel;
	}

	/**
	 * Sets the test nature label.
	 * 
	 * @param testNatureLabel the testNatureLabel to set
	 */
	public void setTestNatureLabel(String testNatureLabel) {
		this.testNatureLabel = testNatureLabel;
	}

	/**
	 * Gets the vehicle technology.
	 * 
	 * @return the vehicleTechnology
	 */
	public String getVehicleTechnology() {
		return vehicleTechnology;
	}

	/**
	 * Sets the vehicle technology.
	 * 
	 * @param vehicleTechnology the vehicleTechnology to set
	 */
	public void setVehicleTechnology(String vehicleTechnology) {
		this.vehicleTechnology = vehicleTechnology;
	}

	/**
	 * Gets the sampling rule.
	 * 
	 * @return the samplingRule
	 */
	public String getSamplingRule() {
		return samplingRule;
	}

	/**
	 * Sets the sampling rule.
	 * 
	 * @param samplingRule the samplingRule to set
	 */
	public void setSamplingRule(String samplingRule) {
		this.samplingRule = samplingRule;
	}

	/**
	 * Gets the car maker brand label.
	 * 
	 * @return the carMakerBrandLabel
	 */
	public String getCarMakerBrandLabel() {
		return carMakerBrandLabel;
	}

	/**
	 * Sets the car maker brand label.
	 * 
	 * @param carMakerBrandLabel the carMakerBrandLabel to set
	 */
	public void setCarMakerBrandLabel(String carMakerBrandLabel) {
		this.carMakerBrandLabel = carMakerBrandLabel;
	}

	/**
	 * Gets the factory.
	 * 
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * Sets the factory.
	 * 
	 * @param factory the factory to set
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	/**
	 * Gets the final reduction rationlabel.
	 * 
	 * @return the finalReductionRationlabel
	 */
	public String getFinalReductionRationlabel() {
		return finalReductionRationlabel;
	}

	/**
	 * Sets the final reduction rationlabel.
	 * 
	 * @param finalReductionRationlabel the finalReductionRationlabel to set
	 */
	public void setFinalReductionRationlabel(String finalReductionRationlabel) {
		this.finalReductionRationlabel = finalReductionRationlabel;
	}

	/**
	 * Gets the regulation group.
	 * 
	 * @return the regulationGroup
	 */
	public String getRegulationGroup() {
		return regulationGroup;
	}

	/**
	 * Sets the regulation group.
	 * 
	 * @param regulationGroup the regulationGroup to set
	 */
	public void setRegulationGroup(String regulationGroup) {
		this.regulationGroup = regulationGroup;
	}

	/**
	 * Gets the technical group.
	 * 
	 * @return the technicalGroup
	 */
	public String getTechnicalGroup() {
		return technicalGroup;
	}

	/**
	 * Sets the technical group.
	 * 
	 * @param technicalGroup the technicalGroup to set
	 */
	public void setTechnicalGroup(String technicalGroup) {
		this.technicalGroup = technicalGroup;
	}

	/**
	 * Gets the emission standard version.
	 * 
	 * @return the emissionStandardVersion
	 */
	public String getEmissionStandardVersion() {
		return emissionStandardVersion;
	}

	/**
	 * Sets the emission standard version.
	 * 
	 * @param emissionStandardVersion the emissionStandardVersion to set
	 */
	public void setEmissionStandardVersion(String emissionStandardVersion) {
		this.emissionStandardVersion = emissionStandardVersion;
	}

	/**
	 * Gets the tvv vald tc default val pente.
	 * 
	 * @return the tvvValdTCDefaultValPente
	 */
	public String getTvvValdTCDefaultValPente() {
		return tvvValdTCDefaultValPente;
	}

	/**
	 * Sets the tvv vald tc default val pente.
	 * 
	 * @param tvvValdTCDefaultValPente the tvvValdTCDefaultValPente to set
	 */
	public void setTvvValdTCDefaultValPente(String tvvValdTCDefaultValPente) {
		this.tvvValdTCDefaultValPente = tvvValdTCDefaultValPente;
	}

	/**
	 * Gets the tvv valued tc cycle.
	 * 
	 * @return the tvvValuedTCCycle
	 */
	public String getTvvValuedTCCycle() {
		return tvvValuedTCCycle;
	}

	/**
	 * Sets the tvv valued tc cycle.
	 * 
	 * @param tvvValuedTCCycle the tvvValuedTCCycle to set
	 */
	public void setTvvValuedTCCycle(String tvvValuedTCCycle) {
		this.tvvValuedTCCycle = tvvValuedTCCycle;
	}

	/**
	 * Gets the tvv vald fc default val co.
	 * 
	 * @return the tvvValdFCDefaultValCO
	 */
	public Double getTvvValdFCDefaultValCO() {
		return tvvValdFCDefaultValCO;
	}

	/**
	 * Sets the tvv vald fc default val co.
	 * 
	 * @param tvvValdFCDefaultValCO the tvvValdFCDefaultValCO to set
	 */
	public void setTvvValdFCDefaultValCO(Double tvvValdFCDefaultValCO) {
		this.tvvValdFCDefaultValCO = tvvValdFCDefaultValCO;
	}

	/**
	 * Gets the tvv vald fc default val hc.
	 * 
	 * @return the tvvValdFCDefaultValHC
	 */
	public Double getTvvValdFCDefaultValHC() {
		return tvvValdFCDefaultValHC;
	}

	/**
	 * Sets the tvv vald fc default val hc.
	 * 
	 * @param tvvValdFCDefaultValHC the tvvValdFCDefaultValHC to set
	 */
	public void setTvvValdFCDefaultValHC(Double tvvValdFCDefaultValHC) {
		this.tvvValdFCDefaultValHC = tvvValdFCDefaultValHC;
	}

	/**
	 * Gets the tvv vald fc default val nmhc.
	 * 
	 * @return the tvvValdFCDefaultValNMHC
	 */
	public Double getTvvValdFCDefaultValNMHC() {
		return tvvValdFCDefaultValNMHC;
	}

	/**
	 * Sets the tvv vald fc default val nmhc.
	 * 
	 * @param tvvValdFCDefaultValNMHC the tvvValdFCDefaultValNMHC to set
	 */
	public void setTvvValdFCDefaultValNMHC(Double tvvValdFCDefaultValNMHC) {
		this.tvvValdFCDefaultValNMHC = tvvValdFCDefaultValNMHC;
	}

	/**
	 * Gets the tvv vald fc default val nox.
	 * 
	 * @return the tvvValdFCDefaultValNOX
	 */
	public Double getTvvValdFCDefaultValNOX() {
		return tvvValdFCDefaultValNOX;
	}

	/**
	 * Sets the tvv vald fc default val nox.
	 * 
	 * @param tvvValdFCDefaultValNOX the tvvValdFCDefaultValNOX to set
	 */
	public void setTvvValdFCDefaultValNOX(Double tvvValdFCDefaultValNOX) {
		this.tvvValdFCDefaultValNOX = tvvValdFCDefaultValNOX;
	}

	/**
	 * Gets the tvv vald fc default val hcnox.
	 * 
	 * @return the tvvValdFCDefaultValHCNOX
	 */
	public Double getTvvValdFCDefaultValHCNOX() {
		return tvvValdFCDefaultValHCNOX;
	}

	/**
	 * Sets the tvv vald fc default val hcnox.
	 * 
	 * @param tvvValdFCDefaultValHCNOX the tvvValdFCDefaultValHCNOX to set
	 */
	public void setTvvValdFCDefaultValHCNOX(Double tvvValdFCDefaultValHCNOX) {
		this.tvvValdFCDefaultValHCNOX = tvvValdFCDefaultValHCNOX;
	}

	/**
	 * Gets the tvv vald fc default val part masse.
	 * 
	 * @return the tvvValdFCDefaultValPartMasse
	 */
	public Double getTvvValdFCDefaultValPartMasse() {
		return tvvValdFCDefaultValPartMasse;
	}

	/**
	 * Sets the tvv vald fc default val part masse.
	 * 
	 * @param tvvValdFCDefaultValPartMasse the tvvValdFCDefaultValPartMasse to set
	 */
	public void setTvvValdFCDefaultValPartMasse(Double tvvValdFCDefaultValPartMasse) {
		this.tvvValdFCDefaultValPartMasse = tvvValdFCDefaultValPartMasse;
	}

	/**
	 * Gets the tvv vald fc default val part nombre.
	 * 
	 * @return the tvvValdFCDefaultValPartNombre
	 */
	public Double getTvvValdFCDefaultValPartNombre() {
		return tvvValdFCDefaultValPartNombre;
	}

	/**
	 * Sets the tvv vald fc default val part nombre.
	 * 
	 * @param tvvValdFCDefaultValPartNombre the tvvValdFCDefaultValPartNombre to set
	 */
	public void setTvvValdFCDefaultValPartNombre(Double tvvValdFCDefaultValPartNombre) {
		this.tvvValdFCDefaultValPartNombre = tvvValdFCDefaultValPartNombre;
	}

	/**
	 * Gets the tvv vald fc default val c o2.
	 * 
	 * @return the tvvValdFCDefaultValCO2
	 */
	public Double getTvvValdFCDefaultValCO2() {
		return tvvValdFCDefaultValCO2;
	}

	/**
	 * Sets the tvv vald fc default val c o2.
	 * 
	 * @param tvvValdFCDefaultValCO2 the tvvValdFCDefaultValCO2 to set
	 */
	public void setTvvValdFCDefaultValCO2(Double tvvValdFCDefaultValCO2) {
		this.tvvValdFCDefaultValCO2 = tvvValdFCDefaultValCO2;
	}

	/**
	 * Gets the tvv vald fc default val coef evol co.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolCO
	 */
	public Double getTvvValdFCDefaultValCoefEvolCO() {
		return tvvValdFCDefaultValCoefEvolCO;
	}

	/**
	 * Sets the tvv vald fc default val coef evol co.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolCO the tvvValdFCDefaultValCoefEvolCO to set
	 */
	public void setTvvValdFCDefaultValCoefEvolCO(Double tvvValdFCDefaultValCoefEvolCO) {
		this.tvvValdFCDefaultValCoefEvolCO = tvvValdFCDefaultValCoefEvolCO;
	}

	/**
	 * Gets the tvv vald fc default val coef evol hc.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolHC
	 */
	public Double getTvvValdFCDefaultValCoefEvolHC() {
		return tvvValdFCDefaultValCoefEvolHC;
	}

	/**
	 * Sets the tvv vald fc default val coef evol hc.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolHC the tvvValdFCDefaultValCoefEvolHC to set
	 */
	public void setTvvValdFCDefaultValCoefEvolHC(Double tvvValdFCDefaultValCoefEvolHC) {
		this.tvvValdFCDefaultValCoefEvolHC = tvvValdFCDefaultValCoefEvolHC;
	}

	/**
	 * Gets the tvv vald fc default val coef evol nmhc.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolNMHC
	 */
	public Double getTvvValdFCDefaultValCoefEvolNMHC() {
		return tvvValdFCDefaultValCoefEvolNMHC;
	}

	/**
	 * Sets the tvv vald fc default val coef evol nmhc.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolNMHC the tvvValdFCDefaultValCoefEvolNMHC to set
	 */
	public void setTvvValdFCDefaultValCoefEvolNMHC(Double tvvValdFCDefaultValCoefEvolNMHC) {
		this.tvvValdFCDefaultValCoefEvolNMHC = tvvValdFCDefaultValCoefEvolNMHC;
	}

	/**
	 * Gets the tvv vald fc default val coef evol nox.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolNOX
	 */
	public Double getTvvValdFCDefaultValCoefEvolNOX() {
		return tvvValdFCDefaultValCoefEvolNOX;
	}

	/**
	 * Sets the tvv vald fc default val coef evol nox.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolNOX the tvvValdFCDefaultValCoefEvolNOX to set
	 */
	public void setTvvValdFCDefaultValCoefEvolNOX(Double tvvValdFCDefaultValCoefEvolNOX) {
		this.tvvValdFCDefaultValCoefEvolNOX = tvvValdFCDefaultValCoefEvolNOX;
	}

	/**
	 * Gets the tvv vald fc default val coef evol hcnox.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolHCNOX
	 */
	public Double getTvvValdFCDefaultValCoefEvolHCNOX() {
		return tvvValdFCDefaultValCoefEvolHCNOX;
	}

	/**
	 * Sets the tvv vald fc default val coef evol hcnox.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolHCNOX the tvvValdFCDefaultValCoefEvolHCNOX to set
	 */
	public void setTvvValdFCDefaultValCoefEvolHCNOX(Double tvvValdFCDefaultValCoefEvolHCNOX) {
		this.tvvValdFCDefaultValCoefEvolHCNOX = tvvValdFCDefaultValCoefEvolHCNOX;
	}

	/**
	 * Gets the tvv vald fc default val coef evol part masse.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolPartMasse
	 */
	public Double getTvvValdFCDefaultValCoefEvolPartMasse() {
		return tvvValdFCDefaultValCoefEvolPartMasse;
	}

	/**
	 * Sets the tvv vald fc default val coef evol part masse.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolPartMasse the tvvValdFCDefaultValCoefEvolPartMasse to set
	 */
	public void setTvvValdFCDefaultValCoefEvolPartMasse(Double tvvValdFCDefaultValCoefEvolPartMasse) {
		this.tvvValdFCDefaultValCoefEvolPartMasse = tvvValdFCDefaultValCoefEvolPartMasse;
	}

	/**
	 * Gets the tvv vald fc default val coef evol part nombre.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolPartNombre
	 */
	public Double getTvvValdFCDefaultValCoefEvolPartNombre() {
		return tvvValdFCDefaultValCoefEvolPartNombre;
	}

	/**
	 * Sets the tvv vald fc default val coef evol part nombre.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolPartNombre the tvvValdFCDefaultValCoefEvolPartNombre to set
	 */
	public void setTvvValdFCDefaultValCoefEvolPartNombre(Double tvvValdFCDefaultValCoefEvolPartNombre) {
		this.tvvValdFCDefaultValCoefEvolPartNombre = tvvValdFCDefaultValCoefEvolPartNombre;
	}

	/**
	 * Gets the tvv vald fc default val coef evol c o2.
	 * 
	 * @return the tvvValdFCDefaultValCoefEvolCO2
	 */
	public Double getTvvValdFCDefaultValCoefEvolCO2() {
		return tvvValdFCDefaultValCoefEvolCO2;
	}

	/**
	 * Sets the tvv vald fc default val coef evol c o2.
	 * 
	 * @param tvvValdFCDefaultValCoefEvolCO2 the tvvValdFCDefaultValCoefEvolCO2 to set
	 */
	public void setTvvValdFCDefaultValCoefEvolCO2(Double tvvValdFCDefaultValCoefEvolCO2) {
		this.tvvValdFCDefaultValCoefEvolCO2 = tvvValdFCDefaultValCoefEvolCO2;
	}

	/**
	 * Gets the tvv vald fc default val coef fapco.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPCO
	 */
	public Double getTvvValdFCDefaultValCoefFAPCO() {
		return tvvValdFCDefaultValCoefFAPCO;
	}

	/**
	 * Sets the tvv vald fc default val coef fapco.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPCO the tvvValdFCDefaultValCoefFAPCO to set
	 */
	public void setTvvValdFCDefaultValCoefFAPCO(Double tvvValdFCDefaultValCoefFAPCO) {
		this.tvvValdFCDefaultValCoefFAPCO = tvvValdFCDefaultValCoefFAPCO;
	}

	/**
	 * Gets the tvv vald fc default val coef faphc.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPHC
	 */
	public Double getTvvValdFCDefaultValCoefFAPHC() {
		return tvvValdFCDefaultValCoefFAPHC;
	}

	/**
	 * Sets the tvv vald fc default val coef faphc.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPHC the tvvValdFCDefaultValCoefFAPHC to set
	 */
	public void setTvvValdFCDefaultValCoefFAPHC(Double tvvValdFCDefaultValCoefFAPHC) {
		this.tvvValdFCDefaultValCoefFAPHC = tvvValdFCDefaultValCoefFAPHC;
	}

	/**
	 * Gets the tvv vald fc default val coef fapnox.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPNOX
	 */
	public Double getTvvValdFCDefaultValCoefFAPNOX() {
		return tvvValdFCDefaultValCoefFAPNOX;
	}

	/**
	 * Sets the tvv vald fc default val coef fapnox.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPNOX the tvvValdFCDefaultValCoefFAPNOX to set
	 */
	public void setTvvValdFCDefaultValCoefFAPNOX(Double tvvValdFCDefaultValCoefFAPNOX) {
		this.tvvValdFCDefaultValCoefFAPNOX = tvvValdFCDefaultValCoefFAPNOX;
	}

	/**
	 * Gets the tvv vald fc default val coef faphcnox.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPHCNOX
	 */
	public Double getTvvValdFCDefaultValCoefFAPHCNOX() {
		return tvvValdFCDefaultValCoefFAPHCNOX;
	}

	/**
	 * Sets the tvv vald fc default val coef faphcnox.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPHCNOX the tvvValdFCDefaultValCoefFAPHCNOX to set
	 */
	public void setTvvValdFCDefaultValCoefFAPHCNOX(Double tvvValdFCDefaultValCoefFAPHCNOX) {
		this.tvvValdFCDefaultValCoefFAPHCNOX = tvvValdFCDefaultValCoefFAPHCNOX;
	}

	/**
	 * Gets the tvv vald fc default val coef fap part masse.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPPartMasse
	 */
	public Double getTvvValdFCDefaultValCoefFAPPartMasse() {
		return tvvValdFCDefaultValCoefFAPPartMasse;
	}

	/**
	 * Sets the tvv vald fc default val coef fap part masse.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPPartMasse the tvvValdFCDefaultValCoefFAPPartMasse to set
	 */
	public void setTvvValdFCDefaultValCoefFAPPartMasse(Double tvvValdFCDefaultValCoefFAPPartMasse) {
		this.tvvValdFCDefaultValCoefFAPPartMasse = tvvValdFCDefaultValCoefFAPPartMasse;
	}

	/**
	 * Gets the tvv vald fc default val coef fap part nbre.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPPartNbre
	 */
	public Double getTvvValdFCDefaultValCoefFAPPartNbre() {
		return tvvValdFCDefaultValCoefFAPPartNbre;
	}

	/**
	 * Sets the tvv vald fc default val coef fap part nbre.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPPartNbre the tvvValdFCDefaultValCoefFAPPartNbre to set
	 */
	public void setTvvValdFCDefaultValCoefFAPPartNbre(Double tvvValdFCDefaultValCoefFAPPartNbre) {
		this.tvvValdFCDefaultValCoefFAPPartNbre = tvvValdFCDefaultValCoefFAPPartNbre;
	}

	/**
	 * Gets the tvv vald fc default val coef fapc o2.
	 * 
	 * @return the tvvValdFCDefaultValCoefFAPCO2
	 */
	public Double getTvvValdFCDefaultValCoefFAPCO2() {
		return tvvValdFCDefaultValCoefFAPCO2;
	}

	/**
	 * Sets the tvv vald fc default val coef fapc o2.
	 * 
	 * @param tvvValdFCDefaultValCoefFAPCO2 the tvvValdFCDefaultValCoefFAPCO2 to set
	 */
	public void setTvvValdFCDefaultValCoefFAPCO2(Double tvvValdFCDefaultValCoefFAPCO2) {
		this.tvvValdFCDefaultValCoefFAPCO2 = tvvValdFCDefaultValCoefFAPCO2;
	}

	/**
	 * Gets the tvv vald td val eobd.
	 * 
	 * @return the tvvValdTDValEOBD
	 */
	public String getTvvValdTDValEOBD() {
		return tvvValdTDValEOBD;
	}

	/**
	 * Sets the tvv vald td val eobd.
	 * 
	 * @param tvvValdTDValEOBD the tvvValdTDValEOBD to set
	 */
	public void setTvvValdTDValEOBD(String tvvValdTDValEOBD) {
		this.tvvValdTDValEOBD = tvvValdTDValEOBD;
	}

	/**
	 * Gets the tvv vald td val iupr.
	 * 
	 * @return the tvvValdTDValIUPR
	 */
	public String getTvvValdTDValIUPR() {
		return tvvValdTDValIUPR;
	}

	/**
	 * Sets the tvv vald td val iupr.
	 * 
	 * @param tvvValdTDValIUPR the tvvValdTDValIUPR to set
	 */
	public void setTvvValdTDValIUPR(String tvvValdTDValIUPR) {
		this.tvvValdTDValIUPR = tvvValdTDValIUPR;
	}

	/**
	 * Gets the tvv vald n soft ancien.
	 * 
	 * @return the tvvValdNSoftAncien
	 */
	public String getTvvValdNSoftAncien() {
		return tvvValdNSoftAncien;
	}

	/**
	 * Sets the tvv vald n soft ancien.
	 * 
	 * @param tvvValdNSoftAncien the tvvValdNSoftAncien to set
	 */
	public void setTvvValdNSoftAncien(String tvvValdNSoftAncien) {
		this.tvvValdNSoftAncien = tvvValdNSoftAncien;
	}

	/**
	 * Gets the tvv vald n soft actuel.
	 * 
	 * @return the tvvValdNSoftActuel
	 */
	public String getTvvValdNSoftActuel() {
		return tvvValdNSoftActuel;
	}

	/**
	 * Sets the tvv vald n soft actuel.
	 * 
	 * @param tvvValdNSoftActuel the tvvValdNSoftActuel to set
	 */
	public void setTvvValdNSoftActuel(String tvvValdNSoftActuel) {
		this.tvvValdNSoftActuel = tvvValdNSoftActuel;
	}

	/**
	 * Gets the tvv vald td val eobd remarques.
	 * 
	 * @return the tvvValdTDValEOBDRemarques
	 */
	public String getTvvValdTDValEOBDRemarques() {
		return tvvValdTDValEOBDRemarques;
	}

	/**
	 * Sets the tvv vald td val eobd remarques.
	 * 
	 * @param tvvValdTDValEOBDRemarques the tvvValdTDValEOBDRemarques to set
	 */
	public void setTvvValdTDValEOBDRemarques(String tvvValdTDValEOBDRemarques) {
		this.tvvValdTDValEOBDRemarques = tvvValdTDValEOBDRemarques;
	}

	/**
	 * Gets the tvv vald td value calclr.
	 * 
	 * @return the tvvValdTDValueCalclr
	 */
	public String getTvvValdTDValueCalclr() {
		return tvvValdTDValueCalclr;
	}

	/**
	 * Sets the tvv vald td value calclr.
	 * 
	 * @param tvvValdTDValueCalclr the tvvValdTDValueCalclr to set
	 */
	public void setTvvValdTDValueCalclr(String tvvValdTDValueCalclr) {
		this.tvvValdTDValueCalclr = tvvValdTDValueCalclr;
	}

	/**
	 * Gets the tvv vald tc value regime.
	 * 
	 * @return the tvvValdTCValueRegime
	 */
	public String getTvvValdTCValueRegime() {
		return tvvValdTCValueRegime;
	}

	/**
	 * Sets the tvv vald tc value regime.
	 * 
	 * @param tvvValdTCValueRegime the tvvValdTCValueRegime to set
	 */
	public void setTvvValdTCValueRegime(String tvvValdTCValueRegime) {
		this.tvvValdTCValueRegime = tvvValdTCValueRegime;
	}

	/**
	 * Gets the tvv vald tc def value soca.
	 * 
	 * @return the tvvValdTCDefValueSOCA
	 */
	public String getTvvValdTCDefValueSOCA() {
		return tvvValdTCDefValueSOCA;
	}

	/**
	 * Sets the tvv vald tc def value soca.
	 * 
	 * @param tvvValdTCDefValueSOCA the tvvValdTCDefValueSOCA to set
	 */
	public void setTvvValdTCDefValueSOCA(String tvvValdTCDefValueSOCA) {
		this.tvvValdTCDefValueSOCA = tvvValdTCDefValueSOCA;
	}

	/**
	 * Gets the tvv vald tc def val soca avant.
	 * 
	 * @return the tvvValdTCDefValSOCAAvant
	 */
	public String getTvvValdTCDefValSOCAAvant() {
		return tvvValdTCDefValSOCAAvant;
	}

	/**
	 * Sets the tvv vald tc def val soca avant.
	 * 
	 * @param tvvValdTCDefValSOCAAvant the tvvValdTCDefValSOCAAvant to set
	 */
	public void setTvvValdTCDefValSOCAAvant(String tvvValdTCDefValSOCAAvant) {
		this.tvvValdTCDefValSOCAAvant = tvvValdTCDefValSOCAAvant;
	}

	/**
	 * Gets the tvv vald tc value pocket bsi.
	 * 
	 * @return the tvvValdTCValuePocketBSI
	 */
	public String getTvvValdTCValuePocketBSI() {
		return tvvValdTCValuePocketBSI;
	}

	/**
	 * Sets the tvv vald tc value pocket bsi.
	 * 
	 * @param tvvValdTCValuePocketBSI the tvvValdTCValuePocketBSI to set
	 */
	public void setTvvValdTCValuePocketBSI(String tvvValdTCValuePocketBSI) {
		this.tvvValdTCValuePocketBSI = tvvValdTCValuePocketBSI;
	}

	/**
	 * Gets the tvv vald tc val manchon.
	 * 
	 * @return the tvvValdTCValManchon
	 */
	public String getTvvValdTCValManchon() {
		return tvvValdTCValManchon;
	}

	/**
	 * Sets the tvv vald tc val manchon.
	 * 
	 * @param tvvValdTCValManchon the tvvValdTCValManchon to set
	 */
	public void setTvvValdTCValManchon(String tvvValdTCValManchon) {
		this.tvvValdTCValManchon = tvvValdTCValManchon;
	}

	/**
	 * Gets the tvv vald tc val appren.
	 * 
	 * @return the tvvValdTCValAppren
	 */
	public String getTvvValdTCValAppren() {
		return tvvValdTCValAppren;
	}

	/**
	 * Sets the tvv vald tc val appren.
	 * 
	 * @param tvvValdTCValAppren the tvvValdTCValAppren to set
	 */
	public void setTvvValdTCValAppren(String tvvValdTCValAppren) {
		this.tvvValdTCValAppren = tvvValdTCValAppren;
	}

	/**
	 * Gets the tvv vald tc val phase1.
	 * 
	 * @return the tvvValdTCValPhase1
	 */
	public String getTvvValdTCValPhase1() {
		return tvvValdTCValPhase1;
	}

	/**
	 * Sets the tvv vald tc val phase1.
	 * 
	 * @param tvvValdTCValPhase1 the tvvValdTCValPhase1 to set
	 */
	public void setTvvValdTCValPhase1(String tvvValdTCValPhase1) {
		this.tvvValdTCValPhase1 = tvvValdTCValPhase1;
	}

	/**
	 * Gets the tvv vald tc val phase2.
	 * 
	 * @return the tvvValdTCValPhase2
	 */
	public String getTvvValdTCValPhase2() {
		return tvvValdTCValPhase2;
	}

	/**
	 * Sets the tvv vald tc val phase2.
	 * 
	 * @param tvvValdTCValPhase2 the tvvValdTCValPhase2 to set
	 */
	public void setTvvValdTCValPhase2(String tvvValdTCValPhase2) {
		this.tvvValdTCValPhase2 = tvvValdTCValPhase2;
	}

	/**
	 * Gets the tvv vald tc val phase3.
	 * 
	 * @return the tvvValdTCValPhase3
	 */
	public String getTvvValdTCValPhase3() {
		return tvvValdTCValPhase3;
	}

	/**
	 * Sets the tvv vald tc val phase3.
	 * 
	 * @param tvvValdTCValPhase3 the tvvValdTCValPhase3 to set
	 */
	public void setTvvValdTCValPhase3(String tvvValdTCValPhase3) {
		this.tvvValdTCValPhase3 = tvvValdTCValPhase3;
	}

	/**
	 * Gets the tvv vald tc val debit dls.
	 * 
	 * @return the tvvValdTCValDebitDLS
	 */
	public String getTvvValdTCValDebitDLS() {
		return tvvValdTCValDebitDLS;
	}

	/**
	 * Sets the tvv vald tc val debit dls.
	 * 
	 * @param tvvValdTCValDebitDLS the tvvValdTCValDebitDLS to set
	 */
	public void setTvvValdTCValDebitDLS(String tvvValdTCValDebitDLS) {
		this.tvvValdTCValDebitDLS = tvvValdTCValDebitDLS;
	}

	/**
	 * Gets the tvv vald tc val phases sac.
	 * 
	 * @return the tvvValdTCValPhasesSAC
	 */
	public String getTvvValdTCValPhasesSAC() {
		return tvvValdTCValPhasesSAC;
	}

	/**
	 * Sets the tvv vald tc val phases sac.
	 * 
	 * @param tvvValdTCValPhasesSAC the tvvValdTCValPhasesSAC to set
	 */
	public void setTvvValdTCValPhasesSAC(String tvvValdTCValPhasesSAC) {
		this.tvvValdTCValPhasesSAC = tvvValdTCValPhasesSAC;
	}

	/**
	 * Gets the tvv vald tc val phases parti.
	 * 
	 * @return the tvvValdTCValPhasesParti
	 */
	public String getTvvValdTCValPhasesParti() {
		return tvvValdTCValPhasesParti;
	}

	/**
	 * Sets the tvv vald tc val phases parti.
	 * 
	 * @param tvvValdTCValPhasesParti the tvvValdTCValPhasesParti to set
	 */
	public void setTvvValdTCValPhasesParti(String tvvValdTCValPhasesParti) {
		this.tvvValdTCValPhasesParti = tvvValdTCValPhasesParti;
	}

	/**
	 * Gets the tvv vald tc val obsrv prepa.
	 * 
	 * @return the tvvValdTCValObsrvPrepa
	 */
	public String getTvvValdTCValObsrvPrepa() {
		return tvvValdTCValObsrvPrepa;
	}

	/**
	 * Sets the tvv vald tc val obsrv prepa.
	 * 
	 * @param tvvValdTCValObsrvPrepa the tvvValdTCValObsrvPrepa to set
	 */
	public void setTvvValdTCValObsrvPrepa(String tvvValdTCValObsrvPrepa) {
		this.tvvValdTCValObsrvPrepa = tvvValdTCValObsrvPrepa;
	}

	/**
	 * Gets the tvv vald tc val obsrv test.
	 * 
	 * @return the tvvValdTCValObsrvTest
	 */
	public String getTvvValdTCValObsrvTest() {
		return tvvValdTCValObsrvTest;
	}

	/**
	 * Sets the tvv vald tc val obsrv test.
	 * 
	 * @param tvvValdTCValObsrvTest the tvvValdTCValObsrvTest to set
	 */
	public void setTvvValdTCValObsrvTest(String tvvValdTCValObsrvTest) {
		this.tvvValdTCValObsrvTest = tvvValdTCValObsrvTest;
	}

	/**
	 * Gets the tvv vald td val pollu.
	 * 
	 * @return the tvvValdTDValPollu
	 */
	public String getTvvValdTDValPollu() {
		return tvvValdTDValPollu;
	}

	/**
	 * Sets the tvv vald td val pollu.
	 * 
	 * @param tvvValdTDValPollu the tvvValdTDValPollu to set
	 */
	public void setTvvValdTDValPollu(String tvvValdTDValPollu) {
		this.tvvValdTDValPollu = tvvValdTDValPollu;
	}

	/**
	 * Gets the tvv vald td val conso.
	 * 
	 * @return the tvvValdTDValConso
	 */
	public String getTvvValdTDValConso() {
		return tvvValdTDValConso;
	}

	/**
	 * Sets the tvv vald td val conso.
	 * 
	 * @param tvvValdTDValConso the tvvValdTDValConso to set
	 */
	public void setTvvValdTDValConso(String tvvValdTDValConso) {
		this.tvvValdTDValConso = tvvValdTDValConso;
	}

	/**
	 * Gets the tvv vald td val etape emission.
	 * 
	 * @return the tvvValdTDValEtapeEmission
	 */
	public String getTvvValdTDValEtapeEmission() {
		return tvvValdTDValEtapeEmission;
	}

	/**
	 * Sets the tvv vald td val etape emission.
	 * 
	 * @param tvvValdTDValEtapeEmission the tvvValdTDValEtapeEmission to set
	 */
	public void setTvvValdTDValEtapeEmission(String tvvValdTDValEtapeEmission) {
		this.tvvValdTDValEtapeEmission = tvvValdTDValEtapeEmission;
	}

	/**
	 * Gets the tvv vald td val etape obd.
	 * 
	 * @return the tvvValdTDValEtapeOBD
	 */
	public String getTvvValdTDValEtapeOBD() {
		return tvvValdTDValEtapeOBD;
	}

	/**
	 * Sets the tvv vald td val etape obd.
	 * 
	 * @param tvvValdTDValEtapeOBD the tvvValdTDValEtapeOBD to set
	 */
	public void setTvvValdTDValEtapeOBD(String tvvValdTDValEtapeOBD) {
		this.tvvValdTDValEtapeOBD = tvvValdTDValEtapeOBD;
	}

	/**
	 * Gets the tvv value td val obsrv1.
	 * 
	 * @return the tvvValueTDValObsrv1
	 */
	public String getTvvValueTDValObsrv1() {
		return tvvValueTDValObsrv1;
	}

	/**
	 * Sets the tvv value td val obsrv1.
	 * 
	 * @param tvvValueTDValObsrv1 the tvvValueTDValObsrv1 to set
	 */
	public void setTvvValueTDValObsrv1(String tvvValueTDValObsrv1) {
		this.tvvValueTDValObsrv1 = tvvValueTDValObsrv1;
	}

	/**
	 * Gets the tvv value td val obsrv2.
	 * 
	 * @return the tvvValueTDValObsrv2
	 */
	public String getTvvValueTDValObsrv2() {
		return tvvValueTDValObsrv2;
	}

	/**
	 * Sets the tvv value td val obsrv2.
	 * 
	 * @param tvvValueTDValObsrv2 the tvvValueTDValObsrv2 to set
	 */
	public void setTvvValueTDValObsrv2(String tvvValueTDValObsrv2) {
		this.tvvValueTDValObsrv2 = tvvValueTDValObsrv2;
	}

	/**
	 * Gets the tvv vald pg max default val hc.
	 *
	 * @return the tvvValdPGMaxDefaultValHC
	 */
	public String getTvvValdPGMaxDefaultValHC() {
		return tvvValdPGMaxDefaultValHC;
	}

	/**
	 * Sets the tvv vald pg max default val hc.
	 *
	 * @param tvvValdPGMaxDefaultValHC the tvvValdPGMaxDefaultValHC to set
	 */
	public void setTvvValdPGMaxDefaultValHC(String tvvValdPGMaxDefaultValHC) {
		this.tvvValdPGMaxDefaultValHC = tvvValdPGMaxDefaultValHC;
	}

	/**
	 * Gets the tvv vald pg max default val nmhc.
	 *
	 * @return the tvvValdPGMaxDefaultValNMHC
	 */
	public String getTvvValdPGMaxDefaultValNMHC() {
		return tvvValdPGMaxDefaultValNMHC;
	}

	/**
	 * Sets the tvv vald pg max default val nmhc.
	 *
	 * @param tvvValdPGMaxDefaultValNMHC the tvvValdPGMaxDefaultValNMHC to set
	 */
	public void setTvvValdPGMaxDefaultValNMHC(String tvvValdPGMaxDefaultValNMHC) {
		this.tvvValdPGMaxDefaultValNMHC = tvvValdPGMaxDefaultValNMHC;
	}

	/**
	 * Gets the tvv vald pg max default val nox.
	 *
	 * @return the tvvValdPGMaxDefaultValNOX
	 */
	public String getTvvValdPGMaxDefaultValNOX() {
		return tvvValdPGMaxDefaultValNOX;
	}

	/**
	 * Sets the tvv vald pg max default val nox.
	 *
	 * @param tvvValdPGMaxDefaultValNOX the tvvValdPGMaxDefaultValNOX to set
	 */
	public void setTvvValdPGMaxDefaultValNOX(String tvvValdPGMaxDefaultValNOX) {
		this.tvvValdPGMaxDefaultValNOX = tvvValdPGMaxDefaultValNOX;
	}

	/**
	 * Gets the tvv vald pg max default val hcnox.
	 *
	 * @return the tvvValdPGMaxDefaultValHCNOX
	 */
	public String getTvvValdPGMaxDefaultValHCNOX() {
		return tvvValdPGMaxDefaultValHCNOX;
	}

	/**
	 * Sets the tvv vald pg max default val hcnox.
	 *
	 * @param tvvValdPGMaxDefaultValHCNOX the tvvValdPGMaxDefaultValHCNOX to set
	 */
	public void setTvvValdPGMaxDefaultValHCNOX(String tvvValdPGMaxDefaultValHCNOX) {
		this.tvvValdPGMaxDefaultValHCNOX = tvvValdPGMaxDefaultValHCNOX;
	}

	/**
	 * Gets the tvv vald pg max default val part masse.
	 *
	 * @return the tvvValdPGMaxDefaultValPartMasse
	 */
	public String getTvvValdPGMaxDefaultValPartMasse() {
		return tvvValdPGMaxDefaultValPartMasse;
	}

	/**
	 * Sets the tvv vald pg max default val part masse.
	 *
	 * @param tvvValdPGMaxDefaultValPartMasse the tvvValdPGMaxDefaultValPartMasse to set
	 */
	public void setTvvValdPGMaxDefaultValPartMasse(String tvvValdPGMaxDefaultValPartMasse) {
		this.tvvValdPGMaxDefaultValPartMasse = tvvValdPGMaxDefaultValPartMasse;
	}

	/**
	 * Gets the tvv vald pg max default val part nombre.
	 *
	 * @return the tvvValdPGMaxDefaultValPartNombre
	 */
	public String getTvvValdPGMaxDefaultValPartNombre() {
		return tvvValdPGMaxDefaultValPartNombre;
	}

	/**
	 * Sets the tvv vald pg max default val part nombre.
	 *
	 * @param tvvValdPGMaxDefaultValPartNombre the tvvValdPGMaxDefaultValPartNombre to set
	 */
	public void setTvvValdPGMaxDefaultValPartNombre(String tvvValdPGMaxDefaultValPartNombre) {
		this.tvvValdPGMaxDefaultValPartNombre = tvvValdPGMaxDefaultValPartNombre;
	}

	/**
	 * Gets the tvv vald pg max default val co.
	 *
	 * @return the tvvValdPGMaxDefaultValCO
	 */
	public String getTvvValdPGMaxDefaultValCO() {
		return tvvValdPGMaxDefaultValCO;
	}

	/**
	 * Sets the tvv vald pg max default val co.
	 *
	 * @param tvvValdPGMaxDefaultValCO the tvvValdPGMaxDefaultValCO to set
	 */
	public void setTvvValdPGMaxDefaultValCO(String tvvValdPGMaxDefaultValCO) {
		this.tvvValdPGMaxDefaultValCO = tvvValdPGMaxDefaultValCO;
	}

}

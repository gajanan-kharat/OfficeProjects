package com.inetpsa.poc00.domain.tvv;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * The Class TVVMigrationDto.
 */
public class TVVMigrationDto {

	/** The Regulation group. */
	/* BCE FIELD : Regulation Group
	 * Class	 : Regultaion Group
	 * Attribute : Label
	 */
	String regulationGroup;

	/** The technical group. */
	/* BCE FIELD : Technical Group
	 * Class	 : Technical Group
	 * Attribute : Label
	 */
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
	Integer inertiaValue;

	/** The tvv valued td value ancien. */
	/* BCE FIELD : Catalyseur ancien
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvVldTDValueAncien;

	/** The tvv valued td value actuel. */
	/* BCE FIELD : Catalyseur actuel 
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvVldTDValueActuel;
	
	/** The particularite. */
	// Ignoe this Column and Field
	String particularite;

	/** The tvv valued td value remarques. */
	/* BCE FIELD : REMARQUES
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvVldTDValueRemarques;

	/** The valued cdpsa ref label. */
	/* BCE FIELD : Coast-down
	*  Class	 : Valued Coast Down List
	*  Attribute : PSA Reference
	*/
	String coastDownPsaReference;

	/** The max default val tvv val pgl. */
	/* BCE FIELD : Limite CO2
	*  Class	 : TVV Valued TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double maxDefaultValTvvVldPGL;

	/** The tvv valued test condition. */
	/* BCE FIELD : Cycle
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Value (Label)
	*/
	String tvvVldTCCycle;

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

	/** The user benchf0. */
	/* BCE FIELD : valeur F0 saisie
	*  Class	 : Valued Coast Down List
	*  Attribute : User Bench f0
	*/
	Double userBenchf0;

	/** The computed benchf1. */
	/* BCE FIELD : F1
	*  Class	 : Valued Coast Down List
	*  Attribute : Computed Bench f1
	*/
	Double computedBenchf1;

	/** The user benchf1. */
	/* BCE FIELD : User Bench f1
	*  Class	 : Valued Coast Down List
	*  Attribute : User Bench f1
	*/
	Double userBenchf1;

	/** The computed benchf2. */
	/* BCE FIELD : F2
	*  Class	 : Valued Coast Down List
	*  Attribute : Computed Bench f2
	*/
	Double computedBenchf2;

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
	String tvvVldTCDefaultValPENTE;

	/** The fd forfaitaire. */
	/* BCE FIELD : FD Forfaitaire
	*  Class	 : 
	*  Attribute : 
	*/
	String fdForfaitaire;

	/** The tvv valued fc default val_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValCO;

	/** The tvv valued fc default val_ hc. */
	/* BCE FIELD : HC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValHC;

	/** The tvv valued fc default val_ nmhc. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValNMHC;

	/** The tvv valued fc default val_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValNOX;

	/** The tvv valued fc default val_ hcnox. */
	/* BCE FIELD : HC + NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValHCNOX;

	/** The tvv valued fc default val_ part masse. */
	/* BCE FIELD : PART Masse
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValPartMasse;

	/** The tvv valued fc default val_ part nombre. */
	/* BCE FIELD : PART Nombre
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValPartNombre;

	/** The tvv valued fc default val_ c o2. */
	/* BCE FIELD : CO2
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValCO2;

	/** The coef evol. */
	/* BCE FIELD : Coef Evol
	*  Class	 : 
	*  Attribute : 
	*/
	String coefEvol;

	/** The tvv valued fc default val_coef evol_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValCoefEvolCO;

	/** The tvv valued fc default val_coef evol_ hc. */
	/* BCE FIELD : HC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value 
	*/
	Double tvvVldFCDefaultValCoefEvolHC;

	/** The tvv valued fc default val_coef evol_ hcnox. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValCoefEvolNMHC;

	/** The tvv valued fc default val_coef evol_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Factor or Coefficient 
	*  Attribute : Default Value
	*/
	Double tvvVldFCDefaultValCoefEvolNOX;

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
	/* BCE FIELD : 	Coef FAP
	*  Class	 :  
	*  Attribute : 
	*/
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
	Double tvvValdPGMaxDefaultValHC;

	/** The tvv valued pg max default val_ nmhc. */
	/* BCE FIELD : NMHC
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValNMHC;

	/** The tvv valued pg max default val_ nox. */
	/* BCE FIELD : NOX
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValNOX;

	/** The tvv valued pg max default val_ co. */
	/* BCE FIELD : CO
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValCO;

	/** The tvv valued pg max default val_ hcnox. */
	/* BCE FIELD : HC + NOX
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValHCNOX;

	/** The tvv valued pg max default val_ part masse. */
	/* BCE FIELD : PARTICULES masse
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValPartMasse;

	/** The tvv valued pg max default val_ part nombre. */
	/* BCE FIELD : PARTICULES Nombre
	*  Class	 : TVV Valued Pollutant or Gas Limit
	*  Attribute : Maximum Default Value Float
	*/
	Double tvvValdPGMaxDefaultValPartNombre;

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
	String tvvValdTCMLog;

	/** The tvv valued tech data value. */
	/* BCE FIELD : Calculateur
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValdTDValCalclr;

	/** The fuel injection type label. */
	/* BCE FIELD : Injection
	*  Class	 : Fuel Injection Type
	*  Attribute : Label
	*/
	String fuelInjtnTypeLabel;

	/* BCE FIELD : Opacite
	*  Class	 : TVV Valued Test Condition
	*  Attribute : Default Value
	*/
	/** The tvv valued tc default value. */
	// Opacite
	String tvvValdTCDefaultValue;

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
	String tvvValuedTCValAppren;

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
	String tvvValTDValObsrv1;

	/** The tvv value td val_ obsrv2. */
	/* BCE FIELD : OBSERVATIONS 2
	*  Class	 : TVV Valued Technical Data
	*  Attribute : Value
	*/
	String tvvValTDValObsrv2;

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
	String finalReductionRatioLabel;
	
	/**
	 * Instantiates a new TVV migration dto.
	 */
	public TVVMigrationDto() {
		super();
	}

	/**
	 * Instantiates a new TVV migration dto.
	 *
	 * @param regulationGroup the regulation group
	 * @param technicalGroup the technical group
	 * @param typeApprovalArea the type approval area
	 * @param country the country
	 * @param vehicleFamilyLabel the vehicle family label
	 * @param engineLabel the engine label
	 * @param gearBoxLabel the gear box label
	 * @param tvvLabel the tvv label
	 * @param bodyWorkLabel the body work label
	 * @param enginePowerCv the engine power cv
	 * @param enginePowerKw the engine power kw
	 * @param engineTorqueNm the engine torque nm
	 * @param inertia the inertia
	 * @param catlysrAncien the catlysr ancien
	 * @param catlysrActuel the catlysr actuel
	 * @param particularite the particularite
	 * @param remarque the remarque
	 * @param coastDown the coast down
	 * @param limitCo2 the limit co2
	 * @param cycle the cycle
	 * @param emissionStandardLabel the emission standard label
	 * @param esVersion the es version
	 * @param fuel the fuel
	 * @param theoricalF0 the theorical f0
	 * @param theoricalF1 the theorical f1
	 * @param theoricalF2 the theorical f2
	 * @param computedBenchF0 the computed bench f0
	 * @param userBenchF0 the user bench f0
	 * @param computedBenchF1 the computed bench f1
	 * @param userBenchF1 the user bench f1
	 * @param computedBenchF2 the computed bench f2
	 * @param userBenchF2 the user bench f2
	 * @param pente the pente
	 * @param fdForfaitaire the fd forfaitaire
	 * @param fdForfaCO the fd forfa co
	 * @param fdForfaHC the fd forfa hc
	 * @param fdForfaNMHC the fd forfa nmhc
	 * @param fdForfaNOX the fd forfa nox
	 * @param fdForfaHCNOX the fd forfa hcnox
	 * @param fdForfaPartMasse the fd forfa part masse
	 * @param fdForfaPartNbre the fd forfa part nbre
	 * @param fdForfaCO2 the fd forfa c o2
	 * @param coefEvol the coef evol
	 * @param coEvolCO the co evol co
	 * @param coEvolHC the co evol hc
	 * @param coEvolNMHC the co evol nmhc
	 * @param coEvolNOX the co evol nox
	 * @param coEvolHCNOX the co evol hcnox
	 * @param coEvolPartMasse the co evol part masse
	 * @param coEvolParNbre the co evol par nbre
	 * @param coEvolCO2 the co evol c o2
	 * @param coefFAP the coef fap
	 * @param coefFAPCO the coef fapco
	 * @param coefFAPHC the coef faphc
	 * @param coefFAPNOX the coef fapnox
	 * @param coefFAPHCNOX the coef faphcnox
	 * @param coefFAPPartMasse the coef fap part masse
	 * @param coefFAPPartNbre the coef fap part nbre
	 * @param coefFAPCO2 the coef fapc o2
	 * @param vldpglHC the vldpgl hc
	 * @param vldpglNMHC the vldpgl nmhc
	 * @param vldpglNOX the vldpgl nox
	 * @param vldpglCO the vldpgl co
	 * @param vldpglHCNOX the vldpgl hcnox
	 * @param vldpglPartMasse the vldpgl part masse
	 * @param vldpglPartNmbre the vldpgl part nmbre
	 * @param vldtdEOBD the vldtd eobd
	 * @param vldtdIUPR the vldtd iupr
	 * @param vldtdNSoftAncien the vldtd n soft ancien
	 * @param vldtdSoftActuel the vldtd soft actuel
	 * @param remarqEOBD the remarq eobd
	 * @param vtcMLOG the vtc mlog
	 * @param vtdCalculateur the vtd calculateur
	 * @param opacite the opacite
	 * @param fuelInjtnLabel the fuel injtn label
	 * @param vtcRegime the vtc regime
	 * @param vtcSocaPrepa the vtc soca prepa
	 * @param vtcSocaAvanTest the vtc soca avan test
	 * @param vtcPocketBSI the vtc pocket bsi
	 * @param vtcManchon the vtc manchon
	 * @param vtcApprenPilote the vtc appren pilote
	 * @param vtcPhase11 the vtc phase11
	 * @param vtcPhase22 the vtc phase22
	 * @param vtcPhase1 the vtc phase1
	 * @param vtcPhase2 the vtc phase2
	 * @param vtcPhase3 the vtc phase3
	 * @param vtcDebitDls the vtc debit dls
	 * @param vtcPhaseSAC the vtc phase sac
	 * @param vtcPhaseParticules the vtc phase particules
	 * @param vtcObserPrepa the vtc obser prepa
	 * @param vtcObserTest the vtc obser test
	 * @param vtdPvPollu the vtd pv pollu
	 * @param vtdPvConso the vtd pv conso
	 * @param vtdEtapeEms the vtd etape ems
	 * @param vtdEtapeOBD the vtd etape obd
	 * @param vtdObsrvtn1 the vtd obsrvtn1
	 * @param vtdObsrvtn2 the vtd obsrvtn2
	 * @param category the category
	 * @param classe the classe
	 * @param status the status
	 * @param natureTest the nature test
	 * @param vehicleTech the vehicle tech
	 * @param samplingRule the sampling rule
	 * @param carBrandLabel the car brand label
	 * @param factory the factory
	 * @param fnlRedRation the fnl red ration
	 * @throws ParseException the parse exception
	 */
	public TVVMigrationDto(String regulationGroup, 	String technicalGroup 		, String typeApprovalArea
			  ,String country		 ,	String vehicleFamilyLabel	, String engineLabel
			  ,String gearBoxLabel	 , 	String tvvLabel				, String bodyWorkLabel
			  ,String enginePowerCv
			  ,String enginePowerKw  ,  String engineTorqueNm		, String inertia
			  ,String catlysrAncien  ,  String catlysrActuel        , String particularite
			  ,String remarque		 ,  String coastDown			, String limitCo2
			  ,String cycle			 ,  String emissionStandardLabel, String esVersion
			  ,String fuel			 ,  String theoricalF0			, String theoricalF1
			  ,String theoricalF2	 ,  String computedBenchF0		, String userBenchF0
			  ,String computedBenchF1,  String userBenchF1			, String computedBenchF2
			  ,String userBenchF2	 ,  String pente				, String fdForfaitaire
			  ,String fdForfaCO    	 ,  String fdForfaHC			, String fdForfaNMHC
			  ,String fdForfaNOX	 ,  String fdForfaHCNOX			, String fdForfaPartMasse
			  ,String fdForfaPartNbre,	String fdForfaCO2			, String coefEvol
			  ,String coEvolCO		 ,  String coEvolHC				, String coEvolNMHC
			  ,String coEvolNOX		 ,  String coEvolHCNOX			, String coEvolPartMasse
			  ,String coEvolParNbre  ,  String coEvolCO2			, String coefFAP
			  ,String coefFAPCO		 ,  String coefFAPHC			, String coefFAPNOX
			  ,String coefFAPHCNOX	 ,  String coefFAPPartMasse		, String coefFAPPartNbre
			  ,String coefFAPCO2	 ,  String vldpglHC				, String vldpglNMHC
			  ,String vldpglNOX		 ,  String vldpglCO				, String vldpglHCNOX
			  ,String vldpglPartMasse,	String vldpglPartNmbre		, String vldtdEOBD
			  ,String vldtdIUPR		 ,  String vldtdNSoftAncien		, String vldtdSoftActuel
			  ,String remarqEOBD	 ,  String vtcMLOG				, String vtdCalculateur
			  ,String opacite
			  ,String fuelInjtnLabel ,  String vtcRegime			, String vtcSocaPrepa
			  ,String vtcSocaAvanTest,  String vtcPocketBSI			, String vtcManchon
			  ,String vtcApprenPilote,  String vtcPhase11			, String vtcPhase22
			  ,String vtcPhase1		 ,  String vtcPhase2			, String vtcPhase3
			  ,String vtcDebitDls	 ,  String vtcPhaseSAC			, String vtcPhaseParticules
			  ,String vtcObserPrepa	 ,  String vtcObserTest			, String vtdPvPollu
			  ,String vtdPvConso	 ,  String vtdEtapeEms			, String vtdEtapeOBD
			  ,String vtdObsrvtn1	 ,  String vtdObsrvtn2			, String category
			  ,String classe		 ,  String status				, String natureTest
			  ,String vehicleTech	 ,  String samplingRule			, String carBrandLabel
			  ,String factory		 ,  String fnlRedRation			) throws ParseException 
			  {

		this.regulationGroup = regulationGroup;
		this.technicalGroup = technicalGroup;
		this.typeApprovalAreaLabel = typeApprovalArea;
		this.countryLabel = country;
		this.vehicleFamilyLabel = vehicleFamilyLabel;
		this.engineLabel = engineLabel;
		this.gearBoxLabel = gearBoxLabel;
		this.tvvLabel = tvvLabel;
		this.bodyWorkLabel = bodyWorkLabel;
		this.enginePowerCV = enginePowerCv;
		this.enginePowerkW = enginePowerKw;
		this.engineTorqueNm = engineTorqueNm;

		if (inertia != null && !inertia.trim().isEmpty()) {
			this.inertiaValue = NumberFormat.getNumberInstance(Locale.FRANCE).parse(inertia).intValue();
		}

		this.tvvVldTDValueAncien = catlysrAncien;
		this.tvvVldTDValueActuel = catlysrActuel;
		this.particularite = particularite;
		this.tvvVldTDValueRemarques = remarque;
		this.coastDownPsaReference = coastDown;

		if (limitCo2 != null && !limitCo2.trim().isEmpty()) {
			this.maxDefaultValTvvVldPGL = NumberFormat.getNumberInstance(Locale.FRANCE).parse(limitCo2).doubleValue();
		}

		this.tvvVldTCCycle = cycle;

		this.emissionStandardLabel = emissionStandardLabel;
		
		if(esVersion != null && !esVersion.isEmpty()) {
			Double esVer = NumberFormat.getNumberInstance(Locale.FRANCE).parse(esVersion).doubleValue();
			this.emissionStandardVersion = String.valueOf(esVer);
		}
		
		this.fuelLabel = fuel;

		if (theoricalF0 != null && !theoricalF0.trim().isEmpty()) {
			this.theoricalf0 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(theoricalF0).doubleValue();
		}

		if (theoricalF1 != null && !theoricalF1.trim().isEmpty()) {
			this.theoricalf1 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(theoricalF1).doubleValue();
		}

		if (theoricalF2 != null && !theoricalF2.trim().isEmpty()) {
			this.theoricalf2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(theoricalF2).doubleValue();
		}

		if (computedBenchF0 != null && !computedBenchF0.trim().isEmpty()) {
			this.computedBenchf0 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(computedBenchF0).doubleValue();
		}

		if (userBenchF0 != null && !userBenchF0.trim().isEmpty()) {
			this.userBenchf0 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(userBenchF0).doubleValue();
		}

		if (computedBenchF1 != null && !computedBenchF1.trim().isEmpty()) {
			this.computedBenchf1 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(computedBenchF1).doubleValue();
		}

		if (userBenchF1 != null && !userBenchF1.trim().isEmpty()) {
			this.userBenchf1 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(userBenchF1).doubleValue();
		}

		if (computedBenchF2 != null && !computedBenchF2.trim().isEmpty()) {
			this.computedBenchf2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(computedBenchF2).doubleValue();
		}

		if (userBenchF2 != null && !userBenchF2.trim().isEmpty()) {
			this.userBenchf2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(userBenchF2).doubleValue();
		}

		this.tvvVldTCDefaultValPENTE = pente;
		this.fdForfaitaire = fdForfaitaire;

		if (fdForfaCO != null && !fdForfaCO.trim().trim().isEmpty()) {
			this.tvvVldFCDefaultValCO = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaCO).doubleValue();
		}

		if (fdForfaHC != null && !fdForfaHC.trim().trim().isEmpty()) {
			this.tvvVldFCDefaultValHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaHC).doubleValue();
		}

		if (fdForfaNMHC != null && !fdForfaNMHC.trim().isEmpty()) {
			this.tvvVldFCDefaultValNMHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaNMHC).doubleValue();
		}

		if (fdForfaNOX != null && !fdForfaNOX.trim().isEmpty()) {
			this.tvvVldFCDefaultValNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaNOX).doubleValue();
		}

		if (fdForfaHCNOX != null && !fdForfaHCNOX.trim().isEmpty()) {
			this.tvvVldFCDefaultValHCNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaHCNOX).doubleValue();
		}

		if (fdForfaPartMasse != null && !fdForfaPartMasse.trim().isEmpty()) {
			this.tvvVldFCDefaultValPartMasse = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaPartMasse).doubleValue();
		}

		if (fdForfaPartNbre != null && !fdForfaPartNbre.trim().isEmpty()) {
			this.tvvVldFCDefaultValPartNombre = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaPartNbre).doubleValue();
		}

		if (fdForfaCO2 != null && !fdForfaCO2.trim().isEmpty()) {
			this.tvvVldFCDefaultValCO2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(fdForfaCO2).doubleValue();
		}

		this.coefEvol = coefEvol;

		if (coEvolCO != null && !coEvolCO.trim().isEmpty()) {
			this.tvvVldFCDefaultValCoefEvolCO = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolCO).doubleValue();
		}

		if (coEvolHC != null && !coEvolHC.trim().isEmpty()) {
			this.tvvVldFCDefaultValCoefEvolHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolHC).doubleValue();
		}

		if (coEvolNMHC != null && !coEvolNMHC.trim().isEmpty()) {
			this.tvvVldFCDefaultValCoefEvolNMHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolNMHC).doubleValue();
		}

		if (coEvolNOX != null && !coEvolNOX.trim().isEmpty()) {
			this.tvvVldFCDefaultValCoefEvolNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolNOX).doubleValue();
		}

		if (coEvolHCNOX != null && !coEvolHCNOX.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefEvolHCNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolHCNOX).doubleValue();
		}

		if (coEvolPartMasse != null && !coEvolPartMasse.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefEvolPartMasse = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolPartMasse).doubleValue();
		}

		if (coEvolParNbre != null && !coEvolParNbre.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefEvolPartNombre = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolParNbre).doubleValue();
		}

		if (coEvolCO2 != null && !coEvolCO2.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefEvolCO2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coEvolCO2).doubleValue();
		}

		this.coefFAP = coefFAP;

		if (coefFAPCO != null && !coefFAPCO.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPCO = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPCO).doubleValue();
		}

		if (coefFAPHC != null && !coefFAPHC.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPHC).doubleValue();
		}

		if (coefFAPNOX != null && !coefFAPNOX.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPNOX).doubleValue();
		}

		if (coefFAPHCNOX != null && !coefFAPHCNOX.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPHCNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPHCNOX).doubleValue();
		}

		if (coefFAPPartMasse != null && !coefFAPPartMasse.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPPartMasse = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPPartMasse).doubleValue();
		}

		if (coefFAPPartNbre != null && !coefFAPPartNbre.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPPartNbre = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPPartNbre).doubleValue();
		}

		if (coefFAPCO2 != null && !coefFAPCO2.trim().isEmpty()) {
			this.tvvValdFCDefaultValCoefFAPCO2 = NumberFormat.getNumberInstance(Locale.FRANCE).parse(coefFAPCO2).doubleValue();
		}

		if (vldpglHC != null && !vldpglHC.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglHC).doubleValue();
		}

		if (vldpglNMHC != null && !vldpglNMHC.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValNMHC = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglNMHC).doubleValue();
		}

		if (vldpglNOX != null && !vldpglNOX.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglNOX).doubleValue();
		}

		if (vldpglCO != null && !vldpglCO.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValCO = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglCO).doubleValue();
		}

		if (vldpglHCNOX != null && !vldpglHCNOX.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValHCNOX = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglHCNOX).doubleValue();
		}

		if (vldpglPartMasse != null && !vldpglPartMasse.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValPartMasse = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglPartMasse).doubleValue();
		}

		if (vldpglPartNmbre != null && !vldpglPartNmbre.trim().isEmpty()) {
			this.tvvValdPGMaxDefaultValPartNombre = NumberFormat.getNumberInstance(Locale.FRANCE).parse(vldpglPartNmbre).doubleValue();
		}

		this.tvvValdTDValEOBD = vldtdEOBD;
		this.tvvValdTDValIUPR = vldtdIUPR;
		this.tvvValdNSoftAncien = vldtdNSoftAncien;
		this.tvvValdNSoftActuel = vldtdSoftActuel;
		this.tvvValdTDValEOBDRemarques = remarqEOBD;
		this.tvvValdTCMLog = vtcMLOG;
		this.tvvValdTDValCalclr = vtdCalculateur;
		this.tvvValdTCDefaultValue = opacite;
		this.fuelInjtnTypeLabel = fuelInjtnLabel;
		this.tvvValdTCValueRegime = vtcRegime;
		this.tvvValdTCDefValueSOCA = vtcSocaPrepa;
		this.tvvValdTCDefValSOCAAvant = vtcSocaAvanTest;
		this.tvvValdTCValuePocketBSI = vtcPocketBSI;
		this.tvvValdTCValManchon = vtcManchon;

		this.tvvValuedTCValAppren = vtcApprenPilote;
		this.tvvValdTCValPhase1 = vtcPhase11;
		this.tvvValdTCValPhase2 = vtcPhase22;
		this.tvvValdTCValPhase1 = vtcPhase1;
		this.tvvValdTCValPhase2 = vtcPhase2;
		this.tvvValdTCValPhase3 = vtcPhase3;
		this.tvvValdTCValDebitDLS = vtcDebitDls;
		this.tvvValdTCValPhasesSAC = vtcPhaseSAC;
		this.tvvValdTCValPhasesParti = vtcPhaseParticules;
		this.tvvValdTCValObsrvPrepa = vtcObserPrepa;
		this.tvvValdTCValObsrvTest = vtcObserTest;
		this.tvvValdTDValPollu = vtdPvPollu;
		this.tvvValdTDValConso = vtdPvConso;
		this.tvvValdTDValEtapeEmission = vtdEtapeEms;
		this.tvvValdTDValEtapeOBD = vtdEtapeOBD;
		this.tvvValTDValObsrv1 = vtdObsrvtn1;
		this.tvvValTDValObsrv2 = vtdObsrvtn2;
		this.categoryLabel = category;
		this.classLabel = classe;
		this.statusLabel = status;

		this.testNatureLabel = natureTest;
		this.vehicleTechnology = vehicleTech;
		this.samplingRule = samplingRule;
		this.carMakerBrandLabel = carBrandLabel;
		this.factory = factory;
		this.finalReductionRatioLabel = fnlRedRation;
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
	 * Gets the tvv vld td value ancien.
	 * 
	 * @return the tvvVldTDValueAncien
	 */
	public String getTvvVldTDValueAncien() {
		return tvvVldTDValueAncien;
	}

	/**
	 * Sets the tvv vld td value ancien.
	 * 
	 * @param tvvVldTDValueAncien the tvvVldTDValueAncien to set
	 */
	public void setTvvVldTDValueAncien(String tvvVldTDValueAncien) {
		this.tvvVldTDValueAncien = tvvVldTDValueAncien;
	}

	/**
	 * Gets the tvv vld td value actuel.
	 * 
	 * @return the tvvVldTDValueActuel
	 */
	public String getTvvVldTDValueActuel() {
		return tvvVldTDValueActuel;
	}

	/**
	 * Sets the tvv vld td value actuel.
	 * 
	 * @param tvvVldTDValueActuel the tvvVldTDValueActuel to set
	 */
	public void setTvvVldTDValueActuel(String tvvVldTDValueActuel) {
		this.tvvVldTDValueActuel = tvvVldTDValueActuel;
	}

	/**
	 * Gets the tvv vld td value remarques.
	 * 
	 * @return the tvvVldTDValueRemarques
	 */
	public String getTvvVldTDValueRemarques() {
		return tvvVldTDValueRemarques;
	}

	/**
	 * Sets the tvv vld td value remarques.
	 * 
	 * @param tvvVldTDValueRemarques the tvvVldTDValueRemarques to set
	 */
	public void setTvvVldTDValueRemarques(String tvvVldTDValueRemarques) {
		this.tvvVldTDValueRemarques = tvvVldTDValueRemarques;
	}

	/**
	 * Gets the max default val tvv vld pgl.
	 * 
	 * @return the maxDefaultValTvvVldPGL
	 */
	public Double getMaxDefaultValTvvVldPGL() {
		return maxDefaultValTvvVldPGL;
	}

	/**
	 * Sets the max default val tvv vld pgl.
	 * 
	 * @param maxDefaultValTvvVldPGL the maxDefaultValTvvVldPGL to set
	 */
	public void setMaxDefaultValTvvVldPGL(Double maxDefaultValTvvVldPGL) {
		this.maxDefaultValTvvVldPGL = maxDefaultValTvvVldPGL;
	}

	/**
	 * Gets the tvv vld tc cycle.
	 * 
	 * @return the tvvVldTCCycle
	 */
	public String getTvvVldTCCycle() {
		return tvvVldTCCycle;
	}

	/**
	 * Sets the tvv vld tc cycle.
	 * 
	 * @param tvvVldTCCycle the tvvVldTCCycle to set
	 */
	public void setTvvVldTCCycle(String tvvVldTCCycle) {
		this.tvvVldTCCycle = tvvVldTCCycle;
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
	 * Gets the tvv vld tc default val pente.
	 * 
	 * @return the tvvVldTCDefaultValPENTE
	 */
	public String getTvvVldTCDefaultValPENTE() {
		return tvvVldTCDefaultValPENTE;
	}

	/**
	 * Sets the tvv vld tc default val pente.
	 * 
	 * @param tvvVldTCDefaultValPENTE the tvvVldTCDefaultValPENTE to set
	 */
	public void setTvvVldTCDefaultValPENTE(String tvvVldTCDefaultValPENTE) {
		this.tvvVldTCDefaultValPENTE = tvvVldTCDefaultValPENTE;
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
	 * Gets the tvv vld fc default val co.
	 * 
	 * @return the tvvVldFCDefaultValCO
	 */
	public Double getTvvVldFCDefaultValCO() {
		return tvvVldFCDefaultValCO;
	}

	/**
	 * Sets the tvv vld fc default val co.
	 * 
	 * @param tvvVldFCDefaultValCO the tvvVldFCDefaultValCO to set
	 */
	public void setTvvVldFCDefaultValCO(Double tvvVldFCDefaultValCO) {
		this.tvvVldFCDefaultValCO = tvvVldFCDefaultValCO;
	}

	/**
	 * Gets the tvv vld fc default val hc.
	 * 
	 * @return the tvvVldFCDefaultValHC
	 */
	public Double getTvvVldFCDefaultValHC() {
		return tvvVldFCDefaultValHC;
	}

	/**
	 * Sets the tvv vld fc default val hc.
	 * 
	 * @param tvvVldFCDefaultValHC the tvvVldFCDefaultValHC to set
	 */
	public void setTvvVldFCDefaultValHC(Double tvvVldFCDefaultValHC) {
		this.tvvVldFCDefaultValHC = tvvVldFCDefaultValHC;
	}

	/**
	 * Gets the tvv vld fc default val nmhc.
	 * 
	 * @return the tvvVldFCDefaultValNMHC
	 */
	public Double getTvvVldFCDefaultValNMHC() {
		return tvvVldFCDefaultValNMHC;
	}

	/**
	 * Sets the tvv vld fc default val nmhc.
	 * 
	 * @param tvvVldFCDefaultValNMHC the tvvVldFCDefaultValNMHC to set
	 */
	public void setTvvVldFCDefaultValNMHC(Double tvvVldFCDefaultValNMHC) {
		this.tvvVldFCDefaultValNMHC = tvvVldFCDefaultValNMHC;
	}

	/**
	 * Gets the tvv vld fc default val nox.
	 * 
	 * @return the tvvVldFCDefaultValNOX
	 */
	public Double getTvvVldFCDefaultValNOX() {
		return tvvVldFCDefaultValNOX;
	}

	/**
	 * Sets the tvv vld fc default val nox.
	 * 
	 * @param tvvVldFCDefaultValNOX the tvvVldFCDefaultValNOX to set
	 */
	public void setTvvVldFCDefaultValNOX(Double tvvVldFCDefaultValNOX) {
		this.tvvVldFCDefaultValNOX = tvvVldFCDefaultValNOX;
	}

	/**
	 * Gets the tvv vld fc default val hcnox.
	 * 
	 * @return the tvvVldFCDefaultValHCNOX
	 */
	public Double getTvvVldFCDefaultValHCNOX() {
		return tvvVldFCDefaultValHCNOX;
	}

	/**
	 * Sets the tvv vld fc default val hcnox.
	 * 
	 * @param tvvVldFCDefaultValHCNOX the tvvVldFCDefaultValHCNOX to set
	 */
	public void setTvvVldFCDefaultValHCNOX(Double tvvVldFCDefaultValHCNOX) {
		this.tvvVldFCDefaultValHCNOX = tvvVldFCDefaultValHCNOX;
	}

	/**
	 * Gets the tvv vld fc default val part masse.
	 * 
	 * @return the tvvVldFCDefaultValPartMasse
	 */
	public Double getTvvVldFCDefaultValPartMasse() {
		return tvvVldFCDefaultValPartMasse;
	}

	/**
	 * Sets the tvv vld fc default val part masse.
	 * 
	 * @param tvvVldFCDefaultValPartMasse the tvvVldFCDefaultValPartMasse to set
	 */
	public void setTvvVldFCDefaultValPartMasse(Double tvvVldFCDefaultValPartMasse) {
		this.tvvVldFCDefaultValPartMasse = tvvVldFCDefaultValPartMasse;
	}

	/**
	 * Gets the tvv vld fc default val part nombre.
	 * 
	 * @return the tvvVldFCDefaultValPartNombre
	 */
	public Double getTvvVldFCDefaultValPartNombre() {
		return tvvVldFCDefaultValPartNombre;
	}

	/**
	 * Sets the tvv vld fc default val part nombre.
	 * 
	 * @param tvvVldFCDefaultValPartNombre the tvvVldFCDefaultValPartNombre to set
	 */
	public void setTvvVldFCDefaultValPartNombre(Double tvvVldFCDefaultValPartNombre) {
		this.tvvVldFCDefaultValPartNombre = tvvVldFCDefaultValPartNombre;
	}

	/**
	 * Gets the tvv vld fc default val c o2.
	 * 
	 * @return the tvvVldFCDefaultValCO2
	 */
	public Double getTvvVldFCDefaultValCO2() {
		return tvvVldFCDefaultValCO2;
	}

	/**
	 * Sets the tvv vld fc default val c o2.
	 * 
	 * @param tvvVldFCDefaultValCO2 the tvvVldFCDefaultValCO2 to set
	 */
	public void setTvvVldFCDefaultValCO2(Double tvvVldFCDefaultValCO2) {
		this.tvvVldFCDefaultValCO2 = tvvVldFCDefaultValCO2;
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
	 * Gets the tvv vld fc default val coef evol co.
	 * 
	 * @return the tvvVldFCDefaultValCoefEvolCO
	 */
	public Double getTvvVldFCDefaultValCoefEvolCO() {
		return tvvVldFCDefaultValCoefEvolCO;
	}

	/**
	 * Sets the tvv vld fc default val coef evol co.
	 * 
	 * @param tvvVldFCDefaultValCoefEvolCO the tvvVldFCDefaultValCoefEvolCO to set
	 */
	public void setTvvVldFCDefaultValCoefEvolCO(Double tvvVldFCDefaultValCoefEvolCO) {
		this.tvvVldFCDefaultValCoefEvolCO = tvvVldFCDefaultValCoefEvolCO;
	}

	/**
	 * Gets the tvv vld fc default val coef evol hc.
	 * 
	 * @return the tvvVldFCDefaultValCoefEvolHC
	 */
	public Double getTvvVldFCDefaultValCoefEvolHC() {
		return tvvVldFCDefaultValCoefEvolHC;
	}

	/**
	 * Sets the tvv vld fc default val coef evol hc.
	 * 
	 * @param tvvVldFCDefaultValCoefEvolHC the tvvVldFCDefaultValCoefEvolHC to set
	 */
	public void setTvvVldFCDefaultValCoefEvolHC(Double tvvVldFCDefaultValCoefEvolHC) {
		this.tvvVldFCDefaultValCoefEvolHC = tvvVldFCDefaultValCoefEvolHC;
	}

	/**
	 * Gets the tvv vld fc default val coef evol nmhc.
	 * 
	 * @return the tvvVldFCDefaultValCoefEvolNMHC
	 */
	public Double getTvvVldFCDefaultValCoefEvolNMHC() {
		return tvvVldFCDefaultValCoefEvolNMHC;
	}

	/**
	 * Sets the tvv vld fc default val coef evol nmhc.
	 * 
	 * @param tvvVldFCDefaultValCoefEvolNMHC the tvvVldFCDefaultValCoefEvolNMHC to set
	 */
	public void setTvvVldFCDefaultValCoefEvolNMHC(Double tvvVldFCDefaultValCoefEvolNMHC) {
		this.tvvVldFCDefaultValCoefEvolNMHC = tvvVldFCDefaultValCoefEvolNMHC;
	}

	/**
	 * Gets the tvv vld fc default val coef evol nox.
	 * 
	 * @return the tvvVldFCDefaultValCoefEvolNOX
	 */
	public Double getTvvVldFCDefaultValCoefEvolNOX() {
		return tvvVldFCDefaultValCoefEvolNOX;
	}

	/**
	 * Sets the tvv vld fc default val coef evol nox.
	 * 
	 * @param tvvVldFCDefaultValCoefEvolNOX the tvvVldFCDefaultValCoefEvolNOX to set
	 */
	public void setTvvVldFCDefaultValCoefEvolNOX(Double tvvVldFCDefaultValCoefEvolNOX) {
		this.tvvVldFCDefaultValCoefEvolNOX = tvvVldFCDefaultValCoefEvolNOX;
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
	 * Gets the tvv vald pg max default val hc.
	 * 
	 * @return the tvvValdPGMaxDefaultValHC
	 */
	public Double getTvvValdPGMaxDefaultValHC() {
		return tvvValdPGMaxDefaultValHC;
	}

	/**
	 * Sets the tvv vald pg max default val hc.
	 * 
	 * @param tvvValdPGMaxDefaultValHC the tvvValdPGMaxDefaultValHC to set
	 */
	public void setTvvValdPGMaxDefaultValHC(Double tvvValdPGMaxDefaultValHC) {
		this.tvvValdPGMaxDefaultValHC = tvvValdPGMaxDefaultValHC;
	}

	/**
	 * Gets the tvv vald pg max default val nmhc.
	 * 
	 * @return the tvvValdPGMaxDefaultValNMHC
	 */
	public Double getTvvValdPGMaxDefaultValNMHC() {
		return tvvValdPGMaxDefaultValNMHC;
	}

	/**
	 * Sets the tvv vald pg max default val nmhc.
	 * 
	 * @param tvvValdPGMaxDefaultValNMHC the tvvValdPGMaxDefaultValNMHC to set
	 */
	public void setTvvValdPGMaxDefaultValNMHC(Double tvvValdPGMaxDefaultValNMHC) {
		this.tvvValdPGMaxDefaultValNMHC = tvvValdPGMaxDefaultValNMHC;
	}

	/**
	 * Gets the tvv vald pg max default val nox.
	 * 
	 * @return the tvvValdPGMaxDefaultValNOX
	 */
	public Double getTvvValdPGMaxDefaultValNOX() {
		return tvvValdPGMaxDefaultValNOX;
	}

	/**
	 * Sets the tvv vald pg max default val nox.
	 * 
	 * @param tvvValdPGMaxDefaultValNOX the tvvValdPGMaxDefaultValNOX to set
	 */
	public void setTvvValdPGMaxDefaultValNOX(Double tvvValdPGMaxDefaultValNOX) {
		this.tvvValdPGMaxDefaultValNOX = tvvValdPGMaxDefaultValNOX;
	}

	/**
	 * Gets the tvv vald pg max default val co.
	 * 
	 * @return the tvvValdPGMaxDefaultValCO
	 */
	public Double getTvvValdPGMaxDefaultValCO() {
		return tvvValdPGMaxDefaultValCO;
	}

	/**
	 * Sets the tvv vald pg max default val co.
	 * 
	 * @param tvvValdPGMaxDefaultValCO the tvvValdPGMaxDefaultValCO to set
	 */
	public void setTvvValdPGMaxDefaultValCO(Double tvvValdPGMaxDefaultValCO) {
		this.tvvValdPGMaxDefaultValCO = tvvValdPGMaxDefaultValCO;
	}

	/**
	 * Gets the tvv vald pg max default val hcnox.
	 * 
	 * @return the tvvValdPGMaxDefaultValHCNOX
	 */
	public Double getTvvValdPGMaxDefaultValHCNOX() {
		return tvvValdPGMaxDefaultValHCNOX;
	}

	/**
	 * Sets the tvv vald pg max default val hcnox.
	 * 
	 * @param tvvValdPGMaxDefaultValHCNOX the tvvValdPGMaxDefaultValHCNOX to set
	 */
	public void setTvvValdPGMaxDefaultValHCNOX(Double tvvValdPGMaxDefaultValHCNOX) {
		this.tvvValdPGMaxDefaultValHCNOX = tvvValdPGMaxDefaultValHCNOX;
	}

	/**
	 * Gets the tvv vald pg max default val part masse.
	 * 
	 * @return the tvvValdPGMaxDefaultValPartMasse
	 */
	public Double getTvvValdPGMaxDefaultValPartMasse() {
		return tvvValdPGMaxDefaultValPartMasse;
	}

	/**
	 * Sets the tvv vald pg max default val part masse.
	 * 
	 * @param tvvValdPGMaxDefaultValPartMasse the tvvValdPGMaxDefaultValPartMasse to set
	 */
	public void setTvvValdPGMaxDefaultValPartMasse(Double tvvValdPGMaxDefaultValPartMasse) {
		this.tvvValdPGMaxDefaultValPartMasse = tvvValdPGMaxDefaultValPartMasse;
	}

	/**
	 * Gets the tvv vald pg max default val part nombre.
	 * 
	 * @return the tvvValdPGMaxDefaultValPartNombre
	 */
	public Double getTvvValdPGMaxDefaultValPartNombre() {
		return tvvValdPGMaxDefaultValPartNombre;
	}

	/**
	 * Sets the tvv vald pg max default val part nombre.
	 * 
	 * @param tvvValdPGMaxDefaultValPartNombre the tvvValdPGMaxDefaultValPartNombre to set
	 */
	public void setTvvValdPGMaxDefaultValPartNombre(Double tvvValdPGMaxDefaultValPartNombre) {
		this.tvvValdPGMaxDefaultValPartNombre = tvvValdPGMaxDefaultValPartNombre;
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
	 * Gets the tvv vald tcm log.
	 * 
	 * @return the tvvValdTCMLog
	 */
	public String getTvvValdTCMLog() {
		return tvvValdTCMLog;
	}

	/**
	 * Sets the tvv vald tcm log.
	 * 
	 * @param tvvValdTCMLog the tvvValdTCMLog to set
	 */
	public void setTvvValdTCMLog(String tvvValdTCMLog) {
		this.tvvValdTCMLog = tvvValdTCMLog;
	}

	/**
	 * Gets the tvv vald td val calclr.
	 * 
	 * @return the tvvValdTDValCalclr
	 */
	public String getTvvValdTDValCalclr() {
		return tvvValdTDValCalclr;
	}

	/**
	 * Sets the tvv vald td val calclr.
	 * 
	 * @param tvvValdTDValCalclr the tvvValdTDValCalclr to set
	 */
	public void setTvvValdTDValCalclr(String tvvValdTDValCalclr) {
		this.tvvValdTDValCalclr = tvvValdTDValCalclr;
	}

	/**
	 * Gets the fuel injtn type label.
	 * 
	 * @return the fuelInjtnTypeLabel
	 */
	public String getFuelInjtnTypeLabel() {
		return fuelInjtnTypeLabel;
	}

	/**
	 * Sets the fuel injtn type label.
	 * 
	 * @param fuelInjtnTypeLabel the fuelInjtnTypeLabel to set
	 */
	public void setFuelInjtnTypeLabel(String fuelInjtnTypeLabel) {
		this.fuelInjtnTypeLabel = fuelInjtnTypeLabel;
	}

	/**
	 * Gets the tvv vald tc default value.
	 * 
	 * @return the tvvValdTCDefaultValue
	 */
	public String getTvvValdTCDefaultValue() {
		return tvvValdTCDefaultValue;
	}

	/**
	 * Sets the tvv vald tc default value.
	 * 
	 * @param tvvValdTCDefaultValue the tvvValdTCDefaultValue to set
	 */
	public void setTvvValdTCDefaultValue(String tvvValdTCDefaultValue) {
		this.tvvValdTCDefaultValue = tvvValdTCDefaultValue;
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
	 * Gets the tvv valued tc val appren.
	 * 
	 * @return the tvvValuedTCValAppren
	 */
	public String getTvvValuedTCValAppren() {
		return tvvValuedTCValAppren;
	}

	/**
	 * Sets the tvv valued tc val appren.
	 * 
	 * @param tvvValuedTCValAppren the tvvValuedTCValAppren to set
	 */
	public void setTvvValuedTCValAppren(String tvvValuedTCValAppren) {
		this.tvvValuedTCValAppren = tvvValuedTCValAppren;
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
	 * Gets the tvv val td val obsrv1.
	 * 
	 * @return the tvvValTDValObsrv1
	 */
	public String getTvvValTDValObsrv1() {
		return tvvValTDValObsrv1;
	}

	/**
	 * Sets the tvv val td val obsrv1.
	 * 
	 * @param tvvValTDValObsrv1 the tvvValTDValObsrv1 to set
	 */
	public void setTvvValTDValObsrv1(String tvvValTDValObsrv1) {
		this.tvvValTDValObsrv1 = tvvValTDValObsrv1;
	}

	/**
	 * Gets the tvv val td val obsrv2.
	 * 
	 * @return the tvvValTDValObsrv2
	 */
	public String getTvvValTDValObsrv2() {
		return tvvValTDValObsrv2;
	}

	/**
	 * Sets the tvv val td val obsrv2.
	 * 
	 * @param tvvValTDValObsrv2 the tvvValTDValObsrv2 to set
	 */
	public void setTvvValTDValObsrv2(String tvvValTDValObsrv2) {
		this.tvvValTDValObsrv2 = tvvValTDValObsrv2;
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
	 * Gets the coast down psa reference.
	 *
	 * @return the coastDownPsaReference
	 */
	public String getCoastDownPsaReference() {
		return coastDownPsaReference;
	}

	/**
	 * Sets the coast down psa reference.
	 *
	 * @param coastDownPsaReference the coastDownPsaReference to set
	 */
	public void setCoastDownPsaReference(String coastDownPsaReference) {
		this.coastDownPsaReference = coastDownPsaReference;
	}

	/**
	 * Gets the inertia value.
	 *
	 * @return the inertiaValue
	 */
	public Integer getInertiaValue() {
		return inertiaValue;
	}

	/**
	 * Sets the inertia value.
	 *
	 * @param inertiaValue the inertiaValue to set
	 */
	public void setInertiaValue(Integer inertiaValue) {
		this.inertiaValue = inertiaValue;
	}

	/**
	 * Gets the final reduction ratio label.
	 *
	 * @return the finalReductionRatioLabel
	 */
	public String getFinalReductionRatioLabel() {
		return finalReductionRatioLabel;
	}

	/**
	 * Sets the final reduction ratio label.
	 *
	 * @param finalReductionRatioLabel the finalReductionRatioLabel to set
	 */
	public void setFinalReductionRatioLabel(String finalReductionRatioLabel) {
		this.finalReductionRatioLabel = finalReductionRatioLabel;
	}

	/**
	 * Gets the particularite.
	 *
	 * @return the particularite
	 */
	public String getParticularite() {
		return particularite;
	}

	/**
	 * Sets the particularite.
	 *
	 * @param particularite the particularite to set
	 */
	public void setParticularite(String particularite) {
		this.particularite = particularite;
	}

}
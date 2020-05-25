package com.inetpsa.poc00.export.bce;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;
import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;

/**
 * The Class ExportBCEUtil.
 */
public class ExportBCEUtil {
	
	/**
	 * Instantiates a new export bce util.
	 */
	protected ExportBCEUtil() {
		super();
	}

	/**
	 * Prepare tvv valued es dep pgl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedEsDepPGL(TVV temp, ExportBCERepresentation tvv) {

		for (TvvValuedEsDepPGL obj : temp.getTvvValuedEsDepPGL()) {

			for (TvvValuedPollutantGasLimit obj2 : obj.getPollutantGasLimit()) {

				setTvvVldPGMaxDefaultValue(tvv, obj, obj2);

			}
		}
	}

	/**
	 * Sets the tvv vld pg max default value.
	 * 
	 * @param tvv the tvv
	 * @param obj the obj
	 * @param obj2 the obj2
	 */
	private static void setTvvVldPGMaxDefaultValue(ExportBCERepresentation tvv, TvvValuedEsDepPGL obj, TvvValuedPollutantGasLimit obj2) {

		if (Constants.LIMIT_CO2.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
			tvv.setMaxDefaultValTvvValPGL(obj2.getValue());
		}

		if (Constants.LIMITES.equalsIgnoreCase(obj.getLabel()) && obj2.getPgLabel() != null) {

			if (Constants.HC.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValHC(obj2.getValue());
			}

			if (Constants.NMHC.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValNMHC(obj2.getValue());
			}

			if (Constants.NOX.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValNOX(obj2.getValue());
			}

			if (Constants.CO.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValCO(obj2.getValue());
			}

			if (Constants.HC_NOX.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValHCNOX(obj2.getValue());
			}

			if (Constants.PARTICULES_MASSE.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValPartMasse(obj2.getValue());
			}

			if (Constants.PARTICULES_NOMBRE.equalsIgnoreCase(obj2.getPgLabel().getLabel())) {
				tvv.setTvvValdPGMaxDefaultValPartNombre(obj2.getValue());
			}
		}
	}

	/**
	 * Prepare tvv valued es dep f cl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedEsDepFCL(TVV temp, ExportBCERepresentation tvv) {

		for (TvvValuedEsDepFCL obj : temp.getTvvValuedEsDepFCL()) {

			for (TvvValuedFactorCoefficents obj2 : obj.getFactorOrCoeffiecients()) {

				setTvvValdFCDefaultValue(tvv, obj, obj2);
			}
		}
	}

	/**
	 * Sets the tvv vald fc default value.
	 * 
	 * @param tvv the tvv
	 * @param obj the obj
	 * @param obj2 the obj2
	 */
	private static void setTvvValdFCDefaultValue(ExportBCERepresentation tvv, TvvValuedEsDepFCL obj, TvvValuedFactorCoefficents obj2) {
		if (Constants.CO.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCO(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolCO(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPCO(obj2.getDefaultValue());
			}
		}

		if (Constants.HC.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValHC(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolHC(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPHC(obj2.getDefaultValue());
			}
		}

		if (Constants.NMHC.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValNMHC(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolNMHC(obj2.getDefaultValue());
			}

		}

		if (Constants.NOX.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValNOX(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolNOX(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPNOX(obj2.getDefaultValue());
			}
		}

		if (Constants.HC_NOX.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValHCNOX(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolHCNOX(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPHCNOX(obj2.getDefaultValue());
			}
		}

		if (Constants.PART_MASSE.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValPartMasse(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolPartMasse(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPPartMasse(obj2.getDefaultValue());
			}
		}

		if (Constants.PART_NOMBRE.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValPartNombre(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolPartNombre(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPPartNbre(obj2.getDefaultValue());
			}
		}

		if (Constants.CO2.equalsIgnoreCase(obj.getLabel()) && obj2.getFclabel() != null) {

			if (Constants.FD.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCO2(obj2.getDefaultValue());
			}

			if (Constants.COEF_EVOL.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefEvolCO2(obj2.getDefaultValue());
			}

			if (Constants.COEF_FAP.equalsIgnoreCase(obj2.getFclabel().getLabel())) {
				tvv.setTvvValdFCDefaultValCoefFAPCO2(obj2.getDefaultValue());
			}
		}
	}

	/**
	 * Prepare tvv valued es dep tcl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedEsDepTCL(TVV temp, ExportBCERepresentation tvv) {
		for (TvvValuedEsDepTCL obj : temp.getTvvValuedEsDepTCL()) {

			for (TvvValuedTestCondition obj2 : obj.getGenericTestCondition()) {

				setTvvValuedTCDefValue(tvv, obj, obj2);
			}
		}
	}

	/**
	 * Sets the tvv valued tc def value.
	 * 
	 * @param tvv the tvv
	 * @param obj the obj
	 * @param obj2 the obj2
	 */
	private static void setTvvValuedTCDefValue(ExportBCERepresentation tvv, TvvValuedEsDepTCL obj, TvvValuedTestCondition obj2) {

		if (Constants.CYCLE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTCCycle(obj2.getDefaultValue());
		}

		if (Constants.PENTE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCDefaultValPente(obj2.getDefaultValue());
		}

		if (Constants.MLOG.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTCMLog(obj2.getDefaultValue());
		}

		if (Constants.REGIME.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValueRegime(obj2.getDefaultValue());
		}

		if (Constants.POCKET_BSI.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValuePocketBSI(obj2.getDefaultValue());
		}

		if (Constants.MANCHON.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValManchon(obj2.getDefaultValue());
		}

		if (Constants.APPRENTISSAGE_PILOTE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValAppren(obj2.getDefaultValue());
		}

		if (Constants.PREPARATION.equalsIgnoreCase(obj.getLabel())) {

			if (Constants.SOC_PREPA.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCDefValueSOCA(obj2.getDefaultValue());
			}

			if (Constants.SOC_AVANT_TEST.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCDefValSOCAAvant(obj2.getDefaultValue());
			}

			if (Constants.OPACITE.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValuedTCDefaultValue(obj2.getDefaultValue());
			}
		}

		if (Constants.ASPIRATION.equalsIgnoreCase(obj.getLabel())) {

			if (Constants.PHASE_1.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValPhase1(obj2.getDefaultValue());
			}

			if (Constants.PHASE_2.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValPhase2(obj2.getDefaultValue());
			}

			if (Constants.PHASE_3.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValPhase3(obj2.getDefaultValue());
			}

			if (Constants.DEBIT_DLS.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValDebitDLS(obj2.getDefaultValue());
			}

			if (Constants.PHASES_SAC.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValPhasesSAC(obj2.getDefaultValue());
			}

			if (Constants.PHASES_PARTICULES.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTCValPhasesParti(obj2.getDefaultValue());
			}
		}
	}

	/**
	 * Prepare tvv valued tvv dep tcl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedTvvDepTCL(TVV temp, ExportBCERepresentation tvv) {

		for (TvvValuedTvvDepTCL obj : temp.getTvvValuedTvvDepTCL()) {

			for (TvvValuedTestCondition obj2 : obj.getValuedTestCondition()) {

				setTvvValuedDefValue(tvv, obj2);

			}
		}
	}

	/**
	 * Sets the tvv valued def value.
	 * 
	 * @param tvv the tvv
	 * @param obj2 the obj2
	 */
	private static void setTvvValuedDefValue(ExportBCERepresentation tvv, TvvValuedTestCondition obj2) {

		if (Constants.CYCLE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTCCycle(obj2.getDefaultValue());
		}

		if (Constants.PENTE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCDefaultValPente(obj2.getDefaultValue());
		}

		if (Constants.MLOG.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTCMLog(obj2.getDefaultValue());
		}

		if (Constants.REGIME.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValueRegime(obj2.getDefaultValue());
		}

		if (Constants.POCKET_BSI.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValuePocketBSI(obj2.getDefaultValue());
			tvv.setTvvValdTCValObsrvPrepa(obj2.getDefaultValue());
		}

		if (Constants.MANCHON.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValManchon(obj2.getDefaultValue());
			tvv.setTvvValdTCValObsrvTest(obj2.getDefaultValue());
		}

		if (Constants.APPRENTISSAGE_PILOTE.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTCValAppren(obj2.getDefaultValue());
		}
	}

	/**
	 * Prepare tvv valued tvv dep tdl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedTvvDepTDL(TVV temp, ExportBCERepresentation tvv) {

		for (TvvValuedTvvDepTDL obj : temp.getTvvValuedTvvDepTDL()) {

			for (TvvValuedTechData obj2 : obj.getValuedTechnicalData()) {

				// Catalyseur ancien
				if (Constants.CATALYSEUR_ANCIEN.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValuedTDValueActuel(obj2.getValue());
				}

				// Catalyseur actuel
				if (Constants.CATALYSEUR_ACTUEL.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValuedTDValueAncien(obj2.getValue());
				}

				// REMARQUES
				if (Constants.REMARQUES.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValuedTDValueRemarques(obj2.getValue());
				}

				// Calculateur
				if (Constants.CALCULATEUR.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValdTDValueCalclr(obj2.getValue());
				}

				// PV UTAC POLLU
				if (Constants.PV_UTAC_POLLU.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValdTDValPollu(obj2.getValue());
				}

				// PV UTAC CONSO
				if (Constants.PV_UTAC_CONSO.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValdTDValConso(obj2.getValue());
				}

				// Etape Emissions
				if (Constants.ETAPE_EMISSIONS.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValdTDValEtapeEmission(obj2.getValue());
				}

				// Etape OBD
				if (Constants.ETAPE_OBD.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValdTDValEtapeOBD(obj2.getValue());
				}

				// OBSERVATIONS 1
				if (Constants.OBSERVATIONS1.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValueTDValObsrv1(obj2.getValue());
				}

				// OBSERVATIONS 2
				if (Constants.OBSERVATIONS2.equalsIgnoreCase(obj2.getLabel())) {
					tvv.setTvvValueTDValObsrv2(obj2.getValue());
				}

			}
		}
	}

	/**
	 * Prepare tvv valued es dep tdl.
	 * 
	 * @param temp the temp
	 * @param tvv the tvv
	 */
	public static void prepareTvvValuedESDepTDL(TVV temp, ExportBCERepresentation tvv) {
		for (TvvValuedEsDepTDL obj : temp.getTvvValuedEsDepTDL()) {

			for (TvvValuedTechData obj2 : obj.getGenericTechnicalData()) {
				setTvvVldTDValue(tvv, obj, obj2);
			}

		}
	}

	/**
	 * Sets the tvv vld td value.
	 * 
	 * @param tvv the tvv
	 * @param obj the obj
	 * @param obj2 the obj2
	 */
	private static void setTvvVldTDValue(ExportBCERepresentation tvv, TvvValuedEsDepTDL obj, TvvValuedTechData obj2) {
		// Catalyseur ancien
		if (Constants.CATALYSEUR_ANCIEN.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTDValueActuel(obj2.getValue());
		}

		// Catalyseur actuel
		if (Constants.CATALYSEUR_ACTUEL.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTDValueAncien(obj2.getValue());
		}

		// REMARQUES
		if (Constants.REMARQUES.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValuedTDValueRemarques(obj2.getValue());
		}

		// Calculateur
		if (Constants.CALCULATEUR.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTDValueCalclr(obj2.getValue());
		}

		// PV UTAC POLLU
		if (Constants.PV_UTAC_POLLU.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTDValPollu(obj2.getValue());
		}

		// PV UTAC CONSO
		if (Constants.PV_UTAC_CONSO.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTDValConso(obj2.getValue());
		}

		// Etape Emissions
		if (Constants.ETAPE_EMISSIONS.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTDValEtapeEmission(obj2.getValue());
		}

		// Etape OBD
		if (Constants.ETAPE_OBD.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValdTDValEtapeOBD(obj2.getValue());
		}

		// OBSERVATIONS 1
		if (Constants.OBSERVATIONS1.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValueTDValObsrv1(obj2.getValue());
		}

		// OBSERVATIONS 2
		if (Constants.OBSERVATIONS2.equalsIgnoreCase(obj2.getLabel())) {
			tvv.setTvvValueTDValObsrv2(obj2.getValue());
		}

		if (Constants.EOBD.equalsIgnoreCase(obj.getLabel())) {

			if (Constants.EOBD.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTDValEOBD(obj2.getValue());
			}

			if (Constants.IUPR.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTDValIUPR(obj2.getValue());
			}

			if (Constants.NUM_SOFT_OBD_ANCIEN.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdNSoftAncien(obj2.getValue());
			}

			if (Constants.NUM_SOFT_OBD_ACTUEL.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdNSoftActuel(obj2.getValue());
			}

			if (Constants.REMARQUES_EOBD.equalsIgnoreCase(obj2.getLabel())) {
				tvv.setTvvValdTDValEOBDRemarques(obj2.getValue());
			}
		}
	}

	/**
	 * Generate header row.
	 * 
	 * @param headerRow the header row
	 * @param wb the wb
	 */
	public static void generateHeaderRow(Row headerRow, Workbook wb) {

		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setBold(Boolean.TRUE);
		font.setItalic(Boolean.TRUE);
		style.setFont(font);

		String[] myList = { "REGULATION GROUP", "TECHNICAL GROUP", "REGION HOMOLOGATION", "PAYS", "TYPE", "MOTEUR", "B.V.", "TVV", "Silhouette", "Puissance CV", "Puissance Kw", "Couple", "Inertie", Constants.CATALYSEUR_ANCIEN, Constants.CATALYSEUR_ACTUEL, "Particularité", Constants.REMARQUES, "Coast-down", "Limite CO2", Constants.CYCLE, "Limite", "Version Réglementation", "CARBURANT", "F0", "F1",
				"F2", "F0", "valeur F0 saisie", "F1", "valeur F1 saisie", "F2", "valeur F2 saisie", Constants.PENTE, "FD Forfaitaire", Constants.CO, Constants.HC, Constants.NMHC, Constants.NOX, Constants.HC_NOX, Constants.PART_MASSE, "PART Nombre", Constants.CO2, Constants.COEF_EVOL, Constants.CO, Constants.HC, Constants.NMHC, Constants.NOX, Constants.HC_NOX, Constants.PART_MASSE, "PART Nombre",
				Constants.CO2, Constants.COEF_FAP, Constants.CO, Constants.HC, Constants.NOX, Constants.HC_NOX, Constants.PART_MASSE, "PART Nbre", Constants.CO2, Constants.HC, Constants.NMHC, Constants.NOX, Constants.CO, Constants.HC_NOX, "PARTICULES masse", "PARTICULES Nombre", Constants.EOBD, Constants.IUPR, "N° Soft OBD ancien", "N° Soft OBD actuel", "Remarques EOBD", Constants.MLOG,
				Constants.CALCULATEUR, "Injection", Constants.OPACITE, Constants.REGIME, "SOC Prépa", Constants.SOC_AVANT_TEST, Constants.POCKET_BSI, Constants.MANCHON, Constants.APPRENTISSAGE_PILOTE, Constants.PHASE_1, Constants.PHASE_2, Constants.PHASE_1, Constants.PHASE_2, Constants.PHASE_3, Constants.DEBIT_DLS, Constants.PHASES_SAC, Constants.PHASES_PARTICULES, "OBSERVATIONS PREPA",
				"OBSERVATIONS TEST", Constants.PV_UTAC_POLLU, Constants.PV_UTAC_CONSO, Constants.ETAPE_EMISSIONS, Constants.ETAPE_OBD, Constants.OBSERVATIONS1, Constants.OBSERVATIONS2, "Catégorie", "Classe", "Statut", "Nature de test", "Technologie Véhicule", "Règle de prélèvement", "Marque", "Usine", "Ratio final de réduction (Rapport de pont)" };

		int colIndex = 0;

		for (String columnName : myList) {

			Cell psaRefCell = headerRow.createCell(colIndex++);
			psaRefCell.setCellValue(columnName);
			psaRefCell.setCellStyle(style);
		}

	}

}

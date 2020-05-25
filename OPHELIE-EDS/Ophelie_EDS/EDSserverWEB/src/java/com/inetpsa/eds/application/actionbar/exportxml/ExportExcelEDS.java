/*
 * Creation : 26 mai 2015
 */
package com.inetpsa.eds.application.actionbar.exportxml;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.dao.model.EdsSupply;

/**
 * EDS Excel exporter.
 * 
 * @author G-VILLEREZ
 */
public class ExportExcelEDS extends A_ExportExcel {

    /**
     * Inner enumeration used to store the line correspondences.
     * 
     * @author G-VILLEREZ
     */
    private enum LineValue {
        POWER_SUPLY_NAME, I_MAX, T_MAX, I_VEILLE, I_VEILLE_21J, I_VEILLE_30J, I_VEILLE_8H, I_REVEILLE_INACTIF_UMIN, I_REVEILLE_INACTIF_UMOY, I_REVEILLE_INACTIF_UMAX, I_REVEILLE_INACTIF, I_MODE_ECO, I_PIRE_CAS_UMOY, T_PIRE_CAS_UMOY, I_PIRE_CAS_UMAX, T_PIRE_CAS_UMAX, I_PIRE_CAS_UMIN, T_PIRE_CAS_UMIN, I_PIRE_CAS, T_PIRE_CAS, I_NOM_STAB, T_NOM_STAB, I_NOM_DEM, T_NOM_DEM, I_DYSF, T_DYSF, I_MST, T_MST, I_NOM_STAB_UMOY, T_NOM_STAB_UMOY, I_NOM_STAB_UMAX, T_NOM_STAB_UMAX, I_NOM_STAB_UMIN, T_NOM_STAB_UMIN, I_EFF, T_EFF, I_EFF_UMIN, I_EFF_UMOY, I_EFF_UMAX, I_MODE_MONTAGNE, I_PARC_MODE, I_CYCLE;

        public int getValue(int offset) {
            return this.ordinal() + offset + 5; // Constant offset
        }
    }

    /**
     * Inner enumeration used to store the current column type.
     * 
     * @author G-VILLEREZ
     */
    private enum ColumnType {
        CONSOLIDATED_THEORIC, CONSOLIDATED_MESURE, MESURE
    }

    // Running values
    private Collection<EdsEds> edses;
    private EdsApplicationController controller;
    private Sheet s;
    private int column = 4;
    private int row = START_ROW;

    // Column template position
    private static final int templateColumnRow = 0;
    private static final int templateColumnColumn = 4;
    private static final int templateColumnHeight = 47;
    private static final int templateColumnWidth = 2;

    private static final int templateRowRow = 0;
    private static final int templateRowColumn = 0;
    private static final int templateRowHeight = 48;
    private static final int templateRowWidth = 4;

    private static final int START_ROW = 0;

    /**
     * Constructor for the Excel exporter, to export only one EDS.
     * 
     * @param template The template file to use.
     * @param destination The destination local file.
     * @param controller The EDS controller.
     * @param eds The EDS value.
     */
    public ExportExcelEDS(File template, OutputStream destination, EdsApplicationController controller, EdsEds eds) {
        super(template, destination);

        this.edses = new ArrayList<EdsEds>();
        this.controller = controller;

        this.edses.add(eds);
    }

    /**
     * Constructor for the Excel exporter, to export multiple EDSes.
     * 
     * @param template The template file to use.
     * @param destination The destination local file.
     * @param controller The EDS controller.
     * @param eds The EDSes collection.
     */
    public ExportExcelEDS(File template, OutputStream destination, EdsApplicationController controller, Collection<EdsEds> edses) {
        super(template, destination);

        this.edses = new ArrayList<EdsEds>(edses);
        this.controller = controller;
    }

    @Override
    protected void writeContent() throws IOException, ExcelExportException {
        s = workbook.getSheetAt(0);

        // For each EDS to export
        for (EdsEds eds : edses) {

            // Project info
            if (row <= START_ROW)
                writeProjectsInfo(eds);

            // Get values
            ArrayList<String> supplies = new ArrayList<String>();
            HashMap<String, EdsPrimarySupply> primarySupplies = new HashMap<String, EdsPrimarySupply>();
            HashMap<String, EdsRobustSupply> robustSupplies = new HashMap<String, EdsRobustSupply>();
            HashMap<String, EdsConsolidateSupply> consolidateSupplies = new HashMap<String, EdsConsolidateSupply>();
            HashMap<String, EdsPsaMesureSupply> closedSupplies = new HashMap<String, EdsPsaMesureSupply>();

            EdsPrimaryCurent edsPrimaryCurent = controller.getFormDataModel(eds, EdsPrimaryCurent.class);
            EdsRobustCurentFormData edsRobustCurentFormData = controller.getFormDataModel(eds, EdsRobustCurentFormData.class);
            EdsConsolidateCurentFormData edsConsolidateCurentFormData = controller.getFormDataModel(eds, EdsConsolidateCurentFormData.class);

            // Get primary supplies
            for (EdsSupply supply : edsPrimaryCurent.getEdsSupplies()) {
                EdsPrimarySupply primarySupply = supply.getEdsPrimarySupply();

                if (primarySupply != null) {
                    primarySupplies.put(primarySupply.getPsedsSupplyName(), primarySupply);
                    supplies.add(primarySupply.getPsedsSupplyName());
                }
            }

            // Get robust supplies
            for (EdsSupply supply : edsRobustCurentFormData.getEdsSupplies()) {
                EdsRobustSupply robustSupply = supply.getEdsRobustSupply();

                if (robustSupply != null) {
                    robustSupplies.put(robustSupply.getRsedsSupplyName(), robustSupply);

                    if (!supplies.contains(robustSupply.getRsedsSupplyName()))
                        supplies.add(robustSupply.getRsedsSupplyName());
                }
            }

            // Get consolidated supplies
            for (EdsSupply supply : edsConsolidateCurentFormData.getEdsSupplies()) {
                EdsConsolidateSupply consolidateSupply = supply.getEdsConsolidateSupply();

                if (consolidateSupply != null) {
                    consolidateSupplies.put(consolidateSupply.getCsedsSupplyName(), consolidateSupply);

                    if (!supplies.contains(consolidateSupply.getCsedsSupplyName()))
                        supplies.add(consolidateSupply.getCsedsSupplyName());
                }
            }

            // Get closed supplies
            for (EdsSupply supply : edsConsolidateCurentFormData.getEdsSupplies()) {
                EdsPsaMesureSupply mesureSupply = supply.getEdsPsaMesureSupply();

                if (mesureSupply != null) {
                    closedSupplies.put(mesureSupply.getPmsedsSupplyName(), mesureSupply);

                    if (!supplies.contains(mesureSupply.getPmsedsSupplyName()))
                        supplies.add(mesureSupply.getPmsedsSupplyName());
                }
            }

            for (String supplyName : supplies) {
                // Prepare next supply
                if (row > START_ROW) {
                    prepareNewRow(s, eds, supplyName);
                } else {
                    writeCell(s, column - 2, row + 4, eds.getEdsName());
                    writeCell(s, column - 2, row + 5, supplyName);
                }

                writePreliminaryStage(primarySupplies.get(supplyName));
                writeRobustStage(robustSupplies.get(supplyName));
                writeConsolidatedStage(consolidateSupplies.get(supplyName));
                writeMeasureStageMesure(closedSupplies.get(supplyName));

                // Go back to the line
                row += templateRowHeight;
                column = 0;
            }
        }
    }

    /**
     * Write the launcher project, and the following projects.
     * 
     * @param eds The EDS to use to get the project informations.
     * @throws ExcelExportException
     */
    private void writeProjectsInfo(EdsEds eds) throws ExcelExportException {
        // Number of occurrence by vehicle project
        writeCell(s, 4, 5, eds.getEdsPLaunchCount());

        // Modification date
        SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        writeCell(s, 5, 5, dt.format(eds.getEdsModifDate()));

        // Export launcher project
        writeCell(s, 6, 5, eds.getEdsProject().getPName());

        int i = 7;
        for (EdsProjectEds edsProjectEds : eds.getEdsProjectEdses()) {
            writeCell(s, i++, 5, edsProjectEds.getEdsProject().getPName());
        }
    }

    /**
     * Write the preliminary stage.
     * 
     * @param edsPrimarySupply
     * @throws ExcelExportException
     */
    private void writePreliminaryStage(EdsPrimarySupply edsPrimarySupply) throws ExcelExportException {

        if (edsPrimarySupply == null)
            return;

        if (row > START_ROW)
            prepareNewColumn(s, "Preliminary", "Spécifiées", "Com'spec");

        // Supply name
        writeCellWhiteBg(s, column, LineValue.POWER_SUPLY_NAME.getValue(row), edsPrimarySupply.getPsedsSupplyName());
        writeCellWhiteBg(s, column + 1, LineValue.POWER_SUPLY_NAME.getValue(row), edsPrimarySupply.getPsedsSupplyNameComment());

        // Iveille
        writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row), edsPrimarySupply.getPsedsIVeille());
        writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row), edsPrimarySupply.getPsedsIVeilleComment());

        // IReveilleInactif
        writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), edsPrimarySupply.getPsedsIReveilleInactif());
        writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), edsPrimarySupply.getPsedsReveilleComment());

        // IpireCas
        writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsPrimarySupply.getPsedsIPirecasStab());
        writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsPrimarySupply.getPsedsIPirecasComment());

        // InomStab
        writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), edsPrimarySupply.getPsedsINomStab());
        writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), edsPrimarySupply.getPsedsINomStabComment());

        column += 2;
    }

    /**
     * Write the robust stage.
     * 
     * @param edsRobustSupply
     * @throws ExcelExportException
     */
    private void writeRobustStage(EdsRobustSupply edsRobustSupply) throws ExcelExportException {

        if (edsRobustSupply == null)
            return;

        ArrayList<EdsRobustSupplyUseCase> edsRobustSupplyUsecases = new ArrayList<EdsRobustSupplyUseCase>();

        for (EdsRobustSupplyUseCase edsRobustSupplyUseCase : edsRobustSupply.getUseCases()) {
            edsRobustSupplyUsecases.add(edsRobustSupplyUseCase);
        }

        // Main use case
        prepareNewColumn(s, "Robust", "Métier_1", "Com'Met", edsRobustSupply.getRsedsUseCaseName(), "", "");

        // Supply name
        String supplyName = edsRobustSupply.getRsedsSupplyName();
        String supplyComment = edsRobustSupply.getRsedsSupplyNameComment();
        writeCellWhiteBg(s, column, LineValue.POWER_SUPLY_NAME.getValue(row), supplyName);
        writeCellWhiteBg(s, column + 1, LineValue.POWER_SUPLY_NAME.getValue(row), supplyComment);

        // Imax
        writeCellWhiteBg(s, column, LineValue.I_MAX.getValue(row), edsRobustSupply.getRsedsIMax());
        writeCellWhiteBg(s, column + 1, LineValue.I_MAX.getValue(row), edsRobustSupply.getRsedsIMaxComment());

        // Tmax
        writeCellWhiteBg(s, column, LineValue.T_MAX.getValue(row), edsRobustSupply.getRsedsTIMax());
        writeCellWhiteBg(s, column + 1, LineValue.T_MAX.getValue(row), edsRobustSupply.getRsedsTIMaxComment());

        // Iveille
        writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row), edsRobustSupply.getRsedsIVeille());
        writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row), edsRobustSupply.getRsedsIVeilleComment());

        // IReveilleInactif
        writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), edsRobustSupply.getRsedsIReveilleInactif());
        // writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), edsRobustSupply.getRsedsIReveilleInactifComment());

        // IpireCas
        writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsRobustSupply.getRsedsIPirecasStab());
        writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsRobustSupply.getRsedsIPirecasComment());

        // TpireCas
        writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsRobustSupply.getRsedsTPireCas());
        writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsRobustSupply.getRsedsTpireCasComment());

        // Idysf
        writeCellWhiteBg(s, column, LineValue.I_DYSF.getValue(row), edsRobustSupply.getRsedsIdysf());
        writeCellWhiteBg(s, column + 1, LineValue.I_DYSF.getValue(row), edsRobustSupply.getRsedsIdysfComment());

        // Tdysf
        writeCellWhiteBg(s, column, LineValue.T_DYSF.getValue(row), edsRobustSupply.getRsedsTdysf());
        writeCellWhiteBg(s, column + 1, LineValue.T_DYSF.getValue(row), edsRobustSupply.getRsedsTdysfComment());

        // Imst
        writeCellWhiteBg(s, column, LineValue.I_MST.getValue(row), edsRobustSupply.getRsedsImst());
        writeCellWhiteBg(s, column + 1, LineValue.I_MST.getValue(row), edsRobustSupply.getRsedsImstComment());

        // Tmst
        writeCellWhiteBg(s, column, LineValue.T_MST.getValue(row), edsRobustSupply.getRsedsTmst());
        writeCellWhiteBg(s, column + 1, LineValue.T_MST.getValue(row), edsRobustSupply.getRsedsTmstComment());

        // Inomstab
        writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), edsRobustSupply.getRsedsINomStab());
        writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), edsRobustSupply.getRsedsINomStabComment());

        // Tnomstab
        writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMOY.getValue(row), edsRobustSupply.getRsedsTNomStab());
        writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMOY.getValue(row), edsRobustSupply.getRsedsTnomStabComment());

        // IconsoParc
        writeCellWhiteBg(s, column, LineValue.I_PARC_MODE.getValue(row), edsRobustSupply.getRsedsIConsoParc());
        writeCellWhiteBg(s, column + 1, LineValue.I_PARC_MODE.getValue(row), edsRobustSupply.getRsedsIConsoParcComment());

        column += 2;

        // Each use cases
        for (EdsRobustSupplyUseCase robustSupplyUseCase : edsRobustSupplyUsecases) {
            // Main use case
            prepareNewColumn(s, "Robust", "Métier_1", "Com'Met", robustSupplyUseCase.getRsucName(), "", "");

            // Supply name
            writeCellWhiteBg(s, column, LineValue.POWER_SUPLY_NAME.getValue(row), supplyName);
            writeCellWhiteBg(s, column + 1, LineValue.POWER_SUPLY_NAME.getValue(row), supplyComment);

            // Imax
            writeCellWhiteBg(s, column, LineValue.I_MAX.getValue(row), robustSupplyUseCase.getRsucIMax());
            writeCellWhiteBg(s, column + 1, LineValue.I_MAX.getValue(row), robustSupplyUseCase.getRsucIMaxComment());

            // Tmax
            writeCellWhiteBg(s, column, LineValue.T_MAX.getValue(row), robustSupplyUseCase.getRsucTIMax());
            writeCellWhiteBg(s, column + 1, LineValue.T_MAX.getValue(row), robustSupplyUseCase.getRsucTIMaxComment());

            // Iveille
            writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row), robustSupplyUseCase.getRsucIVeille());
            writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row), robustSupplyUseCase.getRsucIVeilleComment());

            // IReveilleInactif
            writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), robustSupplyUseCase.getRsucIReveilInactif());
            // writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row), edsRobustSupply.getRsedsIReveilleInactifComment());

            // IpireCas
            writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), robustSupplyUseCase.getRsucIPirecasStab());
            writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), robustSupplyUseCase.getRsucIPirecasComment());

            // TpireCas
            writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMOY.getValue(row), robustSupplyUseCase.getRsucTPireCas());
            writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMOY.getValue(row), robustSupplyUseCase.getRsucTpireCasComment());

            // Idysf
            writeCellWhiteBg(s, column, LineValue.I_DYSF.getValue(row), robustSupplyUseCase.getRsucIdysf());
            writeCellWhiteBg(s, column + 1, LineValue.I_DYSF.getValue(row), robustSupplyUseCase.getRsucIdysfComment());

            // Tdysf
            writeCellWhiteBg(s, column, LineValue.T_DYSF.getValue(row), robustSupplyUseCase.getRsucTdysf());
            writeCellWhiteBg(s, column + 1, LineValue.T_DYSF.getValue(row), robustSupplyUseCase.getRsucTdysfComment());

            // Imst
            writeCellWhiteBg(s, column, LineValue.I_MST.getValue(row), robustSupplyUseCase.getRsucImst());
            writeCellWhiteBg(s, column + 1, LineValue.I_MST.getValue(row), robustSupplyUseCase.getRsucImstComment());

            // Tmst
            writeCellWhiteBg(s, column, LineValue.T_MST.getValue(row), robustSupplyUseCase.getRsucTmst());
            writeCellWhiteBg(s, column + 1, LineValue.T_MST.getValue(row), robustSupplyUseCase.getRsucTmstComment());

            // Inomstab
            writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), robustSupplyUseCase.getRsucINomStab());
            writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), robustSupplyUseCase.getRsucINomStabComment());

            // Tnomstab
            writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMOY.getValue(row), robustSupplyUseCase.getRsucTNomStab());
            writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMOY.getValue(row), robustSupplyUseCase.getRsucTnomStabComment());

            // IconsoParc
            writeCellWhiteBg(s, column, LineValue.I_PARC_MODE.getValue(row), robustSupplyUseCase.getRsucIConsoParc());
            writeCellWhiteBg(s, column + 1, LineValue.I_PARC_MODE.getValue(row), robustSupplyUseCase.getRsucIConsoParcComment());

            column += 2;
        }
    }

    /**
     * Export the given supply's consolidated stage.
     * 
     * @param edsConsolidateSupply The supply to export.
     * @throws ExcelExportException Thrown if an export error occurs.
     */
    private void writeConsolidatedStage(EdsConsolidateSupply edsConsolidateSupply) throws ExcelExportException {

        if (edsConsolidateSupply == null)
            return;

        writeConsolidatedStageTheoric(edsConsolidateSupply.getEdsConsolidateSupplyTheoritic());
        writeConsolidatedStageMesure(edsConsolidateSupply.getEdsConsolidateSupplyMesure());
    }

    /**
     * Export the given supply's consolidated theoretical measures.
     * 
     * @param edsConsolidateSupply The supply to export.
     * @throws ExcelExportException Thrown if an export error occurs.
     */
    private void writeConsolidatedStageTheoric(EdsConsolidateSupplyTheoritic edsConsolidateSupplyTheoritic) throws ExcelExportException {

        if (edsConsolidateSupplyTheoritic == null)
            return;

        // First step, construct stacks
        Iterator<EdsCourantAppelleActivation> edsCourantAppelleActivationsIt = edsConsolidateSupplyTheoritic.getEdsCourantAppelleActivations()
                .iterator();
        Iterator<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnementsIt = edsConsolidateSupplyTheoritic
                .getEdsCourantBloqueCourantDysfonctionnements().iterator();
        Iterator<EdsCourantMiseSousTension> edsCourantMiseSousTensionsIt = edsConsolidateSupplyTheoritic.getEdsCourantMiseSousTensions().iterator();
        Iterator<EdsCourantNominaleActivation> edsCourantNominaleActivationsIt = edsConsolidateSupplyTheoritic.getEdsCourantNominaleActivations()
                .iterator();
        Iterator<EdsModeMontage> edsModeMontagesIt = edsConsolidateSupplyTheoritic.getEdsModeMontages().iterator();
        Iterator<EdsModeParc> edsModeParcsIt = edsConsolidateSupplyTheoritic.getEdsModeParcs().iterator();
        Iterator<EdsReseauVeilleReveilleOrganeInactif> edsReseauVeilleReveilleOrganeInactifsIt = edsConsolidateSupplyTheoritic
                .getEdsReseauVeilleReveilleOrganeInactifs().iterator();

        writeStageValues(edsCourantAppelleActivationsIt, edsCourantBloqueCourantDysfonctionnementsIt, edsCourantMiseSousTensionsIt,
                edsCourantNominaleActivationsIt, edsModeMontagesIt, edsModeParcsIt, edsReseauVeilleReveilleOrganeInactifsIt, Collections
                        .<EdsCourantCycle> emptyList().iterator(), ColumnType.CONSOLIDATED_THEORIC);
    }

    /**
     * Export the given supply's consolidated theoretical measures.
     * 
     * @param edsConsolidateSupply The supply to export.
     * @throws ExcelExportException Thrown if an export error occurs.
     */
    private void writeConsolidatedStageMesure(EdsConsolidateSupplyMesure edsConsolidateSupplyMesure) throws ExcelExportException {

        if (edsConsolidateSupplyMesure == null)
            return;

        // First step, construct stacks
        Iterator<EdsCourantAppelleActivation> edsCourantAppelleActivationsIt = edsConsolidateSupplyMesure.getEdsCourantAppelleActivationMesures()
                .iterator();
        Iterator<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnementsIt = edsConsolidateSupplyMesure
                .getEdsCourantBloqueCourantDysfonctionnementMesures().iterator();
        Iterator<EdsCourantMiseSousTension> edsCourantMiseSousTensionsIt = edsConsolidateSupplyMesure.getEdsCourantMiseSousTensionMesures()
                .iterator();
        Iterator<EdsCourantNominaleActivation> edsCourantNominaleActivationsIt = edsConsolidateSupplyMesure.getEdsCourantNominaleActivationMesures()
                .iterator();
        Iterator<EdsModeMontage> edsModeMontagesIt = edsConsolidateSupplyMesure.getEdsModeMontageMesures().iterator();
        Iterator<EdsModeParc> edsModeParcsIt = edsConsolidateSupplyMesure.getEdsModeParcMesures().iterator();
        Iterator<EdsReseauVeilleReveilleOrganeInactif> edsReseauVeilleReveilleOrganeInactifsIt = edsConsolidateSupplyMesure
                .getEdsReseauVeilleReveilleOrganeInactifMesures().iterator();

        writeStageValues(edsCourantAppelleActivationsIt, edsCourantBloqueCourantDysfonctionnementsIt, edsCourantMiseSousTensionsIt,
                edsCourantNominaleActivationsIt, edsModeMontagesIt, edsModeParcsIt, edsReseauVeilleReveilleOrganeInactifsIt, Collections
                        .<EdsCourantCycle> emptyList().iterator(), ColumnType.CONSOLIDATED_MESURE);
    }

    /**
     * Export the mesured supply values
     * 
     * @param edsMesureSupply The supply to export from
     * @throws ExcelExportException
     */
    private void writeMeasureStageMesure(EdsPsaMesureSupply edsMesureSupply) throws ExcelExportException {
        if (edsMesureSupply == null)
            return;

        // First step, construct stacks
        Iterator<EdsCourantAppelleActivation> edsCourantAppelleActivationsIt = edsMesureSupply.getEdsPmCourantAppelleActivations().iterator();
        Iterator<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnementsIt = edsMesureSupply
                .getEdsPmCourantBloqueCourantDysfonctionnements().iterator();
        Iterator<EdsCourantMiseSousTension> edsCourantMiseSousTensionsIt = edsMesureSupply.getEdsPmCourantMiseSousTensions().iterator();
        Iterator<EdsCourantNominaleActivation> edsCourantNominaleActivationsIt = edsMesureSupply.getEdsPmCourantNominaleActivations().iterator();
        Iterator<EdsModeMontage> edsModeMontagesIt = edsMesureSupply.getEdsPmModeMontages().iterator();
        Iterator<EdsModeParc> edsModeParcsIt = edsMesureSupply.getEdsPmModeParcs().iterator();
        Iterator<EdsReseauVeilleReveilleOrganeInactif> edsReseauVeilleReveilleOrganeInactifsIt = edsMesureSupply
                .getEdsPmReseauVeilleReveilleOrganeInactifs().iterator();
        Iterator<EdsCourantCycle> edsCourantCycles = edsMesureSupply.getEdsCourantCycles().iterator();

        writeStageValues(edsCourantAppelleActivationsIt, edsCourantBloqueCourantDysfonctionnementsIt, edsCourantMiseSousTensionsIt,
                edsCourantNominaleActivationsIt, edsModeMontagesIt, edsModeParcsIt, edsReseauVeilleReveilleOrganeInactifsIt, edsCourantCycles,
                ColumnType.MESURE);
    }

    /**
     * Export the consolidated stage values
     * 
     * @param edsCourantAppelleActivationsIt
     * @param edsCourantBloqueCourantDysfonctionnementsIt
     * @param edsCourantMiseSousTensionsIt
     * @param edsCourantNominaleActivationsIt
     * @param edsModeMontagesIt
     * @param edsModeParcsIt
     * @param edsReseauVeilleReveilleOrganeInactifsIt
     * @throws ExcelExportException
     */
    private void writeStageValues(Iterator<EdsCourantAppelleActivation> edsCourantAppelleActivationsIt,
            Iterator<EdsCourantBloqueCourantDysfonctionnement> edsCourantBloqueCourantDysfonctionnementsIt,
            Iterator<EdsCourantMiseSousTension> edsCourantMiseSousTensionsIt, Iterator<EdsCourantNominaleActivation> edsCourantNominaleActivationsIt,
            Iterator<EdsModeMontage> edsModeMontagesIt, Iterator<EdsModeParc> edsModeParcsIt,
            Iterator<EdsReseauVeilleReveilleOrganeInactif> edsReseauVeilleReveilleOrganeInactifsIt, Iterator<EdsCourantCycle> edsCourantCycles,
            ColumnType type) throws ExcelExportException {

        // While there is at least a value
        while (edsCourantAppelleActivationsIt.hasNext() || edsCourantBloqueCourantDysfonctionnementsIt.hasNext()
                || edsCourantMiseSousTensionsIt.hasNext() || edsCourantNominaleActivationsIt.hasNext() || edsModeMontagesIt.hasNext()
                || edsModeParcsIt.hasNext() || edsReseauVeilleReveilleOrganeInactifsIt.hasNext() || edsCourantCycles.hasNext()) {

            // Get the values
            EdsCourantAppelleActivation edsCourantAppelleActivation = edsCourantAppelleActivationsIt.hasNext() ? edsCourantAppelleActivationsIt
                    .next() : null;
            EdsCourantBloqueCourantDysfonctionnement edsCourantBloqueCourantDysfonctionnement = edsCourantBloqueCourantDysfonctionnementsIt.hasNext() ? edsCourantBloqueCourantDysfonctionnementsIt
                    .next() : null;
            EdsCourantMiseSousTension edsCourantMiseSousTension = edsCourantMiseSousTensionsIt.hasNext() ? edsCourantMiseSousTensionsIt.next() : null;
            EdsCourantNominaleActivation edsCourantNominaleActivation = edsCourantNominaleActivationsIt.hasNext() ? edsCourantNominaleActivationsIt
                    .next() : null;
            EdsModeMontage edsModeMontage = edsModeMontagesIt.hasNext() ? edsModeMontagesIt.next() : null;
            EdsModeParc edsModeParc = edsModeParcsIt.hasNext() ? edsModeParcsIt.next() : null;
            EdsReseauVeilleReveilleOrganeInactif edsReseauVeilleReveilleOrganeInactif = edsReseauVeilleReveilleOrganeInactifsIt.hasNext() ? edsReseauVeilleReveilleOrganeInactifsIt
                    .next() : null;
            EdsCourantCycle edsCourantCycle = edsCourantCycles.hasNext() ? edsCourantCycles.next() : null;

            // Name of the use case
            StringBuilder sb = new StringBuilder("");
            if (edsReseauVeilleReveilleOrganeInactif != null)
                sb.append("Réseau en veille ou réveillé et organe inactif : " + edsReseauVeilleReveilleOrganeInactif.getRvroiedsName());
            if (edsModeParc != null)
                sb.append("\nMode Parc : " + edsModeParc.getMpedsName());
            if (edsModeMontage != null)
                sb.append("\nMode montagne : " + edsModeMontage.getMmedsModeMontageTitre());
            if (edsCourantAppelleActivation != null)
                sb.append("\nCourant d'appel à l'activation : " + edsCourantAppelleActivation.getCaaedsTitre());
            if (edsCourantBloqueCourantDysfonctionnement != null)
                sb.append("\nCourant couple bloqué / Courant dysfonctionnel : " + edsCourantBloqueCourantDysfonctionnement.getCbcdedsTitre());
            if (edsCourantMiseSousTension != null)
                sb.append("\nCourant de mise sous tension : " + edsCourantMiseSousTension.getCmstedsName());
            if (edsCourantNominaleActivation != null)
                sb.append("\nCourant nominal à l'activation : " + edsCourantNominaleActivation.getCnaedsName());
            if (edsCourantCycle != null)
                sb.append("\nConsommations sur cycle : " + edsCourantCycle.getCcedsName());
            String useCases = sb.toString();

            // === Umin values ===
            if (type == ColumnType.CONSOLIDATED_THEORIC)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nThéorique_1", "Com'FNRth", useCases, "Tmin", "Commentaire Tmin");
            else if (type == ColumnType.CONSOLIDATED_MESURE)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nMesuré_1", "Com'FNRth", useCases, "Tmin", "Commentaire Tmin");
            else
                prepareNewColumn(s, "Closed", "PSA_1", "ComPSA", useCases, "Tmin", "Commentaire Tmin");

            // Imax
            if (edsCourantAppelleActivation != null) {
                writeCellWhiteBg(s, column, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTminImaxPulse());
                writeCellWhiteBg(s, column + 1, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyImaxPulseComment());

                // Imax 66%
                writeCellWhiteBg(s, column, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTminDtPulse());
                writeCellWhiteBg(s, column + 1, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyDtPulseComment());
            }

            if (edsReseauVeilleReveilleOrganeInactif != null) {
                // Iveille (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveilleMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment());

                // Iveille 21j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille21hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment());

                // Iveille 30j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille30hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment());

                // Iveille 8h (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIveille8hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment());

                // Ireveille inactif Umin
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactif10vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif10vMoteurTourantComment());

                // Ireveille inactif Umoy
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactif13vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif13vMoteurTourantComment());

                // Ireveille inactif Umax
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactif15vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif15vMoteurTourantComment());

                // Ireveille inactif
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminIreveilleInactifMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment());

                // Imode eco
                writeCellWhiteBg(s, column, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTminImodeEcoMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsImodeEcoMoteurNonTourantComment());
            }

            if (edsCourantNominaleActivation != null) {
                // Ipirecas Umoy
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsIminPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Tpirecas Umoy
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTminPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Ipirecas Umax
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsIminPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Tpirecas Umax
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTminPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Ipirecas Umin
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsIminPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Tpirecas Umin
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTminPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Ipirecas
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsIminPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tpirecas
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsTminPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom stab
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsIminStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tnom stab
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsTminStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom Dem
                // Tnom Dem
            }

            if (edsCourantBloqueCourantDysfonctionnement != null) {
                // Idysf
                writeCellWhiteBg(s, column, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTminIdys());
                writeCellWhiteBg(s, column + 1, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());

                // Tdysf
                writeCellWhiteBg(s, column, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTminTdys());
                writeCellWhiteBg(s, column + 1, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());
            }

            // Imst
            // Tmst

            if (edsCourantNominaleActivation != null) {
                // InomStab Umoy
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsIminStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // TnomStab Umoy
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTminStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // InomStab Umax
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsIminStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // TnomStab Umax
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTminStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // InomStab Umin
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsIminStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());

                // TnomStab Umin
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTminStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());
            }

            // Ieff
            // Teff
            // Ieff Umin
            // Ieff Umoy
            // Ieff Umax

            // ImodeMontagne

            // Iparc

            // Icycle

            column += 2;

            // === Umoy values ===
            if (type == ColumnType.CONSOLIDATED_THEORIC)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nThéorique_2", "Com'FNRth", useCases, "Tmoy", "Commentaire Tmoy");
            else if (type == ColumnType.CONSOLIDATED_MESURE)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nMesuré_2", "Com'FNRth", useCases, "Tmoy", "Commentaire Tmoy");
            else
                prepareNewColumn(s, "Closed", "PSA_2", "ComPSA", useCases, "Tmoy", "Commentaire Tmoy");

            // Imax
            if (edsCourantAppelleActivation != null) {
                writeCellWhiteBg(s, column, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyImaxPulse());
                writeCellWhiteBg(s, column + 1, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyImaxPulseComment());

                // Imax 66%
                writeCellWhiteBg(s, column, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyDtPulse());
                writeCellWhiteBg(s, column + 1, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyDtPulseComment());
            }

            if (edsReseauVeilleReveilleOrganeInactif != null) {
                // Iveille (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveilleMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment());

                // Iveille 21j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille21hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment());

                // Iveille 30j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille30hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment());

                // Iveille 8h (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIveille8hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment());

                // Ireveille inactif Umin
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactif10vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif10vMoteurTourantComment());

                // Ireveille inactif Umoy
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactif13vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif13vMoteurTourantComment());

                // Ireveille inactif Umax
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactif15vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif15vMoteurTourantComment());

                // Ireveille inactif
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyIreveilleInactifMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment());

                // Imode eco
                writeCellWhiteBg(s, column, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmoyImodeEcoMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsImodeEcoMoteurNonTourantComment());
            }

            if (edsCourantNominaleActivation != null) {
                // Ipirecas Umoy
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsImoyPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Tpirecas Umoy
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Ipirecas Umax
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsImoyPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Tpirecas Umax
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Ipirecas Umin
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsImoyPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Tpirecas Umin
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Ipirecas
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsImoyPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tpirecas
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom stab
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsImoyStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tnom stab
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom Dem
                writeCellWhiteBg(s, column, LineValue.I_NOM_DEM.getValue(row), edsCourantNominaleActivation.getCnaedsImoyDem());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_DEM.getValue(row), edsCourantNominaleActivation.getCnaedsDemComment());

                // Tnom Dem
                writeCellWhiteBg(s, column, LineValue.T_NOM_DEM.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyDem());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_DEM.getValue(row), edsCourantNominaleActivation.getCnaedsDemComment());
            }

            if (edsCourantBloqueCourantDysfonctionnement != null) {
                // Idysf
                writeCellWhiteBg(s, column, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTmoyIdys());
                writeCellWhiteBg(s, column + 1, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());

                // Tdysf
                writeCellWhiteBg(s, column, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTmoyTdys());
                writeCellWhiteBg(s, column + 1, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());
            }

            if (edsCourantMiseSousTension != null) {
                // Imst
                writeCellWhiteBg(s, column, LineValue.I_MST.getValue(row), edsCourantMiseSousTension.getCmstedsTpirecasImst());
                writeCellWhiteBg(s, column + 1, LineValue.I_MST.getValue(row), edsCourantMiseSousTension.getCmstedsTpirecasImstComment());

                // Tmst
                writeCellWhiteBg(s, column, LineValue.T_MST.getValue(row), edsCourantMiseSousTension.getCmstedsTpirecasDt());
                writeCellWhiteBg(s, column + 1, LineValue.T_MST.getValue(row), edsCourantMiseSousTension.getCmstedsTpirecasDtComment());
            }

            if (edsCourantNominaleActivation != null) {
                // InomStab Umoy
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsImoyStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // TnomStab Umoy
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // InomStab Umax
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsImoyStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // TnomStab Umax
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // InomStab Umin
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsImoyStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());

                // TnomStab Umin
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTmoyStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());
            }

            // Ieff
            // Teff
            // Ieff Umin
            // Ieff Umoy
            // Ieff Umax

            if (edsModeMontage != null) {
                // ImodeMontagne
                writeCellWhiteBg(s, column, LineValue.I_MODE_MONTAGNE.getValue(row), edsModeMontage.getMmedsTmoyModeMontage());
                writeCellWhiteBg(s, column + 1, LineValue.I_MODE_MONTAGNE.getValue(row), edsModeMontage.getMmedsTmoyModeMontageComment());
            }

            if (edsModeParc != null) {
                // Iparc
                writeCellWhiteBg(s, column, LineValue.I_PARC_MODE.getValue(row), edsModeParc.getMpedsTmoyModeParc());
                writeCellWhiteBg(s, column + 1, LineValue.I_PARC_MODE.getValue(row), edsModeParc.getMpedsTmoyModeParcComment());
            }

            if (edsCourantCycle != null) {
                // Icycle
                writeCellWhiteBg(s, column, LineValue.I_CYCLE.getValue(row), edsCourantCycle.getCcedsTcycle());
                writeCellWhiteBg(s, column + 1, LineValue.I_CYCLE.getValue(row), edsCourantCycle.getCcedsComent());
            }

            column += 2;

            // === Umax values ===
            if (type == ColumnType.CONSOLIDATED_THEORIC)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nThéorique_3", "Com'FNRth", useCases, "Tmax", "Commentaire Tmax");
            else if (type == ColumnType.CONSOLIDATED_MESURE)
                prepareNewColumn(s, "Consolidated", "Fournisseur\nMesuré_3", "Com'FNRth", useCases, "Tmax", "Commentaire Tmax");
            else
                prepareNewColumn(s, "Closed", "PSA_3", "ComPSA", useCases, "Tmax", "Commentaire Tmax");

            if (edsCourantAppelleActivation != null) {
                // Imax
                writeCellWhiteBg(s, column, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmaxImaxPulse());
                writeCellWhiteBg(s, column + 1, LineValue.I_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyImaxPulseComment());

                // Imax 66%
                writeCellWhiteBg(s, column, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmaxDtPulse());
                writeCellWhiteBg(s, column + 1, LineValue.T_MAX.getValue(row), edsCourantAppelleActivation.getCaaedsTmoyDtPulseComment());
            }

            if (edsReseauVeilleReveilleOrganeInactif != null) {
                // Iveille (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveilleMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveilleMoteurNonTourantComment());

                // Iveille 21j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille21hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_21J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille21hMoteurNonTourantComment());

                // Iveille 30j (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille30hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_30J.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille30hMoteurNonTourantComment());

                // Iveille 8h (moteur non tournant)
                writeCellWhiteBg(s, column, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIveille8hMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_VEILLE_8H.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIveille8hMoteurNonTourantComment());

                // Ireveille inactif Umin
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactif10vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMIN.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif10vMoteurTourantComment());

                // Ireveille inactif Umoy
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactif13vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMOY.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif13vMoteurTourantComment());

                // Ireveille inactif Umax
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactif15vMoteurTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF_UMAX.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactif15vMoteurTourantComment());

                // Ireveille inactif
                writeCellWhiteBg(s, column, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxIreveilleInactifMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_REVEILLE_INACTIF.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsIreveilleInactifMoteurNonTourantComment());

                // Imode eco
                writeCellWhiteBg(s, column, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsTmaxImodeEcoMoteurNonTourant());
                writeCellWhiteBg(s, column + 1, LineValue.I_MODE_ECO.getValue(row),
                        edsReseauVeilleReveilleOrganeInactif.getRvroiedsImodeEcoMoteurNonTourantComment());
            }

            if (edsCourantNominaleActivation != null) {
                // Ipirecas Umoy
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsImaxPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Tpirecas Umoy
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxPireCas13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas13MtComment());

                // Ipirecas Umax
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsImaxPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Tpirecas Umax
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxPireCas15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas15MtComment());

                // Ipirecas Umin
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsImaxPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Tpirecas Umin
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxPireCas10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsPireCas10MtComment());

                // Ipirecas
                writeCellWhiteBg(s, column, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsImaxPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tpirecas
                writeCellWhiteBg(s, column, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxPireCasMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_PIRE_CAS.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom stab
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsImaxStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Tnom stab
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxStabMnt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB.getValue(row), edsCourantNominaleActivation.getCnaedsPireCasMntComment());

                // Inom Dem
                // Tnom Dem
            }

            if (edsCourantBloqueCourantDysfonctionnement != null) {
                // Idysf
                writeCellWhiteBg(s, column, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTmaxIdys());
                writeCellWhiteBg(s, column + 1, LineValue.I_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());

                // Tdysf
                writeCellWhiteBg(s, column, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsTmaxTdys());
                writeCellWhiteBg(s, column + 1, LineValue.T_DYSF.getValue(row), edsCourantBloqueCourantDysfonctionnement.getCbcdedsComment());
            }

            // Imst
            // Tmst

            if (edsCourantNominaleActivation != null) {
                // InomStab Umoy
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsImaxStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // TnomStab Umoy
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxStab13Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMOY.getValue(row), edsCourantNominaleActivation.getCnaedsStab13MtComment());

                // InomStab Umax
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsImaxStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // TnomStab Umax
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxStab15Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMAX.getValue(row), edsCourantNominaleActivation.getCnaedsStab15MtComment());

                // InomStab Umin
                writeCellWhiteBg(s, column, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsImaxStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.I_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());

                // TnomStab Umin
                writeCellWhiteBg(s, column, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsTmaxStab10Mt());
                writeCellWhiteBg(s, column + 1, LineValue.T_NOM_STAB_UMIN.getValue(row), edsCourantNominaleActivation.getCnaedsStab10MtComment());
            }

            // Ieff
            // Teff
            // Ieff Umin
            // Ieff Umoy
            // Ieff Umax

            // ImodeMontagne

            if (edsModeParc != null) {
                // Iparc
                writeCellWhiteBg(s, column, LineValue.I_PARC_MODE.getValue(row), edsModeParc.getMpedsTmaxModeParc());
                writeCellWhiteBg(s, column + 1, LineValue.I_PARC_MODE.getValue(row), edsModeParc.getMpedsTmoyModeParcComment());
            }

            // Icycle

            column += 2;
        }
    }

    /**
     * Prepare the style and the content for a new row.
     * 
     * @param s The sheet to use.
     * @param eds The eds.
     * @param supplyName The supply name.
     * @throws ExcelExportException
     */
    private void prepareNewRow(Sheet s, EdsEds eds, String supplyName) throws ExcelExportException {
        // Style
        copyZoneStyle(s, templateRowColumn, templateRowRow, templateRowHeight, templateRowWidth, column, row);

        // Merge zones
        s.addMergedRegion(new CellRangeAddress(row + 1, row + 2, column + 2, column + 2));
        s.addMergedRegion(new CellRangeAddress(row + 1, row + 2, column + 3, column + 3));
        s.addMergedRegion(new CellRangeAddress(row + 6, row + 46, column + 1, column + 1));

        // Copy content
        for (int i = 0; i < templateRowHeight; i++) {
            for (int j = 0; j < templateRowWidth; j++) {
                String str = getCellStringValue(s, templateRowRow + i, templateRowColumn + j);

                if (str != null && !str.isEmpty()) {
                    writeCell(s, column + j, row + i, str);
                }
            }
        }

        // Write EDS name
        writeCell(s, column + 2, row + 4, eds.getEdsName());

        // Write supply name
        writeCell(s, column + 2, row + 5, supplyName);

        column += 4;
    }

    /**
     * Prepare the style and the content for a new column.
     * 
     * @param s The sheet to use.
     * @param stage The stage name.
     * @param valueTitle The title value.
     * @param valueComment The comment value.
     * @throws ExcelExportException
     */
    private void prepareNewColumn(Sheet s, String stage, String valueTitle, String valueComment) throws ExcelExportException {
        prepareNewColumn(s, stage, valueTitle, valueComment, null, null, null);
    }

    /**
     * Prepare the style for a new column.
     * 
     * @param s The sheet.
     * @param stage The stage name.
     * @param valueTitle The value title.
     * @param valueComment The content title.
     * @param useCases The use case value.
     * @param valueTitle2 The bottom value title (usually tmin/moy/max).
     * @param commentTitle2 The bottom comment title.
     * @throws ExcelExportException
     */
    private void prepareNewColumn(Sheet s, String stage, String valueTitle, String valueComment, String useCases, String valueTitle2,
            String commentTitle2) throws ExcelExportException {
        copyZoneStyle(s, templateColumnColumn, templateColumnRow, templateColumnHeight, templateColumnWidth, column, row);

        // Stage value
        writeCell(s, column, row, stage + " stage");
        writeCell(s, column + 1, row, stage + " stage");

        // Values & comment
        s.addMergedRegion(new CellRangeAddress(row + 1, row + 2, column, column));
        writeCell(s, column, row + 1, valueTitle);
        s.addMergedRegion(new CellRangeAddress(row + 1, row + 2, column + 1, column + 1));
        writeCell(s, column + 1, row + 1, valueComment);

        // Value & comment 2
        if (valueTitle != null)
            writeCell(s, column, row + 5, valueTitle2);
        if (valueComment != null)
            writeCell(s, column + 1, row + 5, commentTitle2);

        // Uses cases
        if (useCases != null) {
            s.addMergedRegion(new CellRangeAddress(row + 3, row + 3, column, column + 1));
            writeCell(s, column, row + 3, useCases);
        }
    }

    /**
     * Write the content of a cell and paint its background white.
     * 
     * @param s
     * @param col
     * @param row
     * @param content
     * @throws ExcelExportException
     */
    private void writeCellWhiteBg(Sheet s, int col, int row, Object content) throws ExcelExportException {

        if (content != null) {
            Cell cell = getCell(s, col, row);
            writeCell(cell, content);

            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());

            cell.setCellStyle(cellStyle);
        }
    }

}

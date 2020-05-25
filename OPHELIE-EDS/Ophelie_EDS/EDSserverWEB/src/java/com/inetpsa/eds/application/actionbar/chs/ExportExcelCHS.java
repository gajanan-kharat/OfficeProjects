/*
 * Creation : 26 mai 2015
 */
package com.inetpsa.eds.application.actionbar.chs;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.actionbar.exportxml.A_ExportExcel;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.util.ProfileType;
import com.inetpsa.eds.dao.I_ValidationFormData;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.EdsBsx;
import com.inetpsa.eds.dao.model.EdsBsxPin;
import com.inetpsa.eds.dao.model.EdsCPProfile;
import com.inetpsa.eds.dao.model.EdsCPProperty;
import com.inetpsa.eds.dao.model.EdsChs;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.dao.model.EdsPinTypeComment;
import com.inetpsa.eds.dao.model.EdsSAPReference;
import com.inetpsa.eds.dao.model.EdsSPProperty;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.dao.model.EdsWIProperty;

/**
 * EDS Excel exporter.
 * 
 * @author G-VILLEREZ
 */
public class ExportExcelCHS extends A_ExportExcel {

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
    private int column = 0;
    private int row = START_ROW;
    private EdsChs chs;

    // Column template position
    private static final int templateColumnRow = 0;
    private static final int templateColumnColumn = 4;
    private static final int templateColumnHeight = 47;
    private static final int templateColumnWidth = 2;

    private static final int templateRowRow = 0;
    private static final int templateRowColumn = 0;
    private static final int templateRowHeight = 48;
    private static final int templateRowWidth = 4;

    private static final int START_ROW = 1;

    /**
     * Constructor for the Excel exporter, to export only one EDS.
     * 
     * @param template The template file to use.
     * @param destination The destination local file.
     * @param controller The EDS controller.
     * @param eds The EDS value.
     */
    public ExportExcelCHS(File template, OutputStream destination, EdsApplicationController controller, EdsEds eds) {
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
    public ExportExcelCHS(File template, OutputStream destination, EdsApplicationController controller, Collection<EdsEds> edses) {
        super(template, destination);

        this.edses = new ArrayList<EdsEds>(edses);
        this.controller = controller;
    }

    @Override
    protected void writeContent() throws IOException, ExcelExportException {

        s = workbook.getSheetAt(0);
        String bsxName;
        // For each EDS to export
        for (EdsEds eds : edses) {
            setCrossedPins(eds);
            Set<Chs> chsSet = eds.getEdsChs();
            Chs chs = null;
            String chsName = "";
            EdsBsx bsx = eds.getEdsBsxUnique();
            if (chsSet != null && chsSet.size() > 0) {
                chs = chsSet.iterator().next();
                chsName = chs.getDescription();
            }
            for (EdsPinConnect pin : eds.getEdsPinConnect()) {

                writeProjectInfo(eds);
                bsxName = bsx != null ? bsx.getName() : "";
                writeCell(s, bsxName); // NOM BSX
                writeCell(s, chsName);
                writeCell(s, pin.getCavity());
                writeCell(s, pin.getMessage());
                writeCell(s, pin.isCrossed() ? "Si" : "Non");
                writeCell(s, null);
                writeCell(s, bsxName);
                writeCell(s, pin.getPinType());
                writeCell(s, null);
                writeProfiles(eds.getEdsTypeComment(), pin);
                copyZoneStyle(s, 0, START_ROW, 1, column, 0, row);
                writeXYValues(pin);

                column = 0;
                row++;
            }
        }
    }

    private void writeXYValues(EdsPinConnect pin) {
        int countProfile = 0;
        List<Double> xValues = new ArrayList<Double>();
        List<Double> yValues = new ArrayList<Double>();

        if (pin.getCpProperty() != null) {
            for (EdsCPProfile cp : pin.getCpProfile()) {
                if (cp != null) {
                    countProfile++;
                    xValues.add(cp.getType());
                    yValues.add(cp.getValue());
                }
            }
        }
        CellStyle style = getCellStyle(s, column, START_ROW);
        applyCellStyle(s, column, row, style);
        writeCell(s, countProfile);
        style = getCellStyle(s, column, START_ROW);
        int columnWidth = s.getColumnWidth(column);
        for (Double x : xValues) {
            applyCellStyle(s, column, row, style);
            s.setColumnWidth(column, columnWidth);
            writeCell(s, x);

        }
        for (Double y : yValues) {
            applyCellStyle(s, column, row, style);
            s.setColumnWidth(column, columnWidth);
            writeCell(s, y);
        }
    }

    private void writeProfiles(Set<EdsPinTypeComment> types, EdsPinConnect pin) {
        List<EdsSPProperty> spProperties = pin.getSpProperty();
        List<EdsCPProperty> cpProperties = pin.getCpProperty();
        List<EdsCPProfile> cpProfile = pin.getCpProfile();
        Set<EdsWIProperty> wiProperties = pin.getWiProperty();
        EdsPinTypeComment type = null;
        String spText = "";
        Double iMax = null;
        Double tMax = null;
        Double iNom = null;
        Double vMin = null;
        Double bMin = null;
        Double bMax = null;
        for (EdsPinTypeComment t : types) {
            if (t.getType().equals(pin.getPinType())) {
                type = t;
            }
        }
        if (type != null) {
            bMin = type.gettMin();
            bMax = type.gettMax();
        }

        if (pin.getSimpleProfile() != null && !pin.getSimpleProfile().isEmpty()) {
            spText = pin.getSimpleProfile();
            for (EdsSPProperty sp : pin.getSpProperty()) {
                if (sp != null) {
                    switch (ProfileType.getProfileFromType(sp.getType())) {
                    case IMAX:
                        iMax = sp.getValue();
                        break;
                    case TMAX:
                        tMax = sp.getValue();
                        break;
                    case INOM:
                        iNom = sp.getValue();
                    case VMIN:
                        vMin = sp.getValue();
                        break;
                    default:
                        break;
                    }
                }

            }
        }

        if (pin.getComplexProfile() != null && !pin.getCpProfile().isEmpty()) {
            spText = pin.getComplexProfile();
            for (EdsCPProperty cp : pin.getCpProperty()) {
                if (cp != null) {
                    switch (ProfileType.getProfileFromType(cp.getType())) {
                    case IMAX:
                        iMax = cp.getValue();
                        break;
                    case TMAX:
                        tMax = cp.getValue();
                        break;
                    case INOM:
                        iNom = cp.getValue();
                    case VMIN:
                        vMin = cp.getValue();
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        writeCell(s, iMax);
        writeCell(s, tMax);
        writeCell(s, iNom);
        writeCell(s, ""); // V nominal?
        writeCell(s, vMin);
        writeCell(s, pin.getMinSection());
        writeCell(s, pin.getIfImpedance());
        writeCell(s, "");

        writeCell(s, bMin);
        writeCell(s, bMax);
        writeCell(s, pin.getrMax());
        writeCell(s, pin.getSupply() != null ? pin.getSupply().getSedsSupplyName() : null);
        writeCell(s, spText);

    }

    /**
     * Sets the value of crossed in each pin if it is crossed with a BSX
     * 
     * @param eds
     */
    private void setCrossedPins(EdsEds eds) {
        EdsBsx bsx = eds.getEdsBsxUnique();
        Set<EdsBsxPin> bsxPins;
        if (bsx != null) {
            for (EdsPinConnect pin : eds.getEdsPinConnect()) {
                bsxPins = bsx.getPin();
                for (EdsBsxPin p : bsxPins) {
                    if (p.isSamePinAs(pin.getCavity())) {
                        pin.setCrossed(true);
                    }
                }
            }
        }
    }

    /**
     * Generate the project
     * 
     * @param eds
     */
    private void writeProjectInfo(EdsEds eds) {
        writeCell(s, eds.getEdsProject().getPName());
        writeCell(s, eds.getEdsRef());
        writeCell(s, eds.getVersionShort());
        writeCell(s, eds.getEdsName());
        writeCell(s, eds.getEdsDescription());
        writeCell(s, eds.getEdsComponentType().getLocaleCtName(controller.getApplication().getLocale()));
        writeCell(s, getProjectStatus(eds));
        writeCell(s, getSapReference(eds.getEdsNumber96fcts()));
        writeCell(s, getSapReference(eds.getEdsNumber96fnrs()));
    }

    private String getProjectStatus(EdsEds eds) {
        I_ValidationFormData validationFormData = null;
        String stade = "";
        switch (eds.getEdsValidLvl()) {
        case EdsEds.VALIDATION_LVL_HIGH:
            validationFormData = (I_ValidationFormData) controller.getFormDataModel(eds, EdsHighValidationFormData.class);
            break;

        case EdsEds.VALIDATION_LVL_LOW:
            validationFormData = (I_ValidationFormData) controller.getFormDataModel(eds, EdsLowValidationFormData.class);
            break;
        }
        if (validationFormData.getClosedStatus() == EdsValidation.VALIDATION_OK) {
            stade = controller.getBundle().getString("project-project-step-clot");
        } else if (validationFormData.getConsolidatedStatus() == EdsValidation.VALIDATION_OK) {
            stade = controller.getBundle().getString("project-project-step-cons");
        } else if (validationFormData.getRobustStatus() == EdsValidation.VALIDATION_OK) {
            stade = controller.getBundle().getString("project-project-step-rob");
        } else if (validationFormData.getPreliminaryStatus() == EdsValidation.VALIDATION_OK) {
            stade = controller.getBundle().getString("project-project-step-prelim");
        }
        return stade;
    }

    private String getSapReference(Set<EdsSAPReference> saps) {
        StringBuilder sapBuilder = new StringBuilder();
        Iterator<EdsSAPReference> it = saps.iterator();
        EdsSAPReference item;
        if (saps.size() > 0) {
            item = it.next();
            sapBuilder.append(item.getReferenceRevisionLabel());
            while (it.hasNext()) {
                item = it.next();
                sapBuilder.append(";");
                sapBuilder.append(item.getReferenceRevisionLabel());
            }
        }

        return sapBuilder.toString();
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

    private void writeCell(Sheet s, Object content) {
        if (content != null) {
            try {
                writeCell(s, column, row, content);
            } catch (ExcelExportException e) {
                e.printStackTrace();
            }
        }
        column++;
    }

}

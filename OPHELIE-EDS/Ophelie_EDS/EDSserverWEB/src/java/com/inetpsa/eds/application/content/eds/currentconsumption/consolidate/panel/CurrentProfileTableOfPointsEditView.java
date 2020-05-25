package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.activationprofile.CurrentStatementEditView;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.dao.model.EdsConsSupProfileListOfPoints;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class defines a Current Profile Table of Points panel for editing.
 * 
 * @author jdsantos @ Alter Frame
 */
public class CurrentProfileTableOfPointsEditView extends HorizontalLayout {

    private static final long serialVersionUID = 5053781617093670213L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Point table **/
    private Table vTBxyDataValues;

    /** Temperature mesure field **/
    private MyTextField vLMesureTemp;

    /** Y axis value field **/
    private MyTextField vLYAxisValue;

    /** Time axis value **/
    private MyTextField vLTimeAxisValue;

    /** Source impedance field **/
    private MyTextField VlSourceImpedance;

    /** Button to add a line for an element **/
    private Button vBTaddXy;

    /** Button to remove a line for an element **/
    private Button vBTremoveXy;

    /** Button to upload an excel/csv file **/
    private Upload vUpload;

    /** Excel file outputstream **/
    private ByteArrayOutputStream outputStream;

    /** Excel filename **/
    private String fileName;

    /** Excel file inputstream **/
    private ByteArrayInputStream inputStream;

    /** Constant variable to store 'time'. **/
    private static final String TIME_ID = "time";

    /** Constant variable to store 'intensity'. **/
    private static final String CURRENT_ID = "intensity";

    /** EDS profile list of options **/
    private EdsConsSupProfileListOfPoints points;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public CurrentProfileTableOfPointsEditView(final EdsApplicationController controller, EdsConsSupProfile profile) {
        this.controller = controller;
        this.points = profile.getCspListOfPoint();

        if (this.points == null) {
            this.points = new EdsConsSupProfileListOfPoints(profile);
            profile.setCspListOfPoint(this.points);
        }

        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {

        setWidth(100, UNITS_PERCENTAGE);
        setCaption(controller.getBundle().getString("profile-table-of-points"));

        HorizontalLayout customProfilView = new HorizontalLayout();
        customProfilView.setMargin(true);
        customProfilView.setSpacing(true);

        vTBxyDataValues = new Table();
        vTBxyDataValues.setEditable(true);
        vTBxyDataValues.setSelectable(true);
        vTBxyDataValues.setMultiSelect(true);
        vTBxyDataValues.setImmediate(true);
        vTBxyDataValues.addContainerProperty(TIME_ID, Double.class, "", controller.getBundle().getString("eds-time-label"), null, null);
        vTBxyDataValues.addContainerProperty(CURRENT_ID, Double.class, "", controller.getBundle().getString("eds-courant-label"), null, null);

        vLMesureTemp = new MyTextField(controller.getBundle().getString("profile-mesure-temperature") + " : ");
        vLMesureTemp.setImmediate(true);
        vLYAxisValue = new MyTextField(controller.getBundle().getString("profile-y-axis-value") + " : ");
        vLYAxisValue.setImmediate(true);
        vLTimeAxisValue = new MyTextField(controller.getBundle().getString("profile-time-axis-value") + " : ");
        vLTimeAxisValue.setImmediate(true);
        VlSourceImpedance = new MyTextField(controller.getBundle().getString("profile-impedance-value") + " : ");
        VlSourceImpedance.setImmediate(true);

        vBTaddXy = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = -1664507052099814219L;

            public void buttonClick(ClickEvent event) {
                vTBxyDataValues.addItem();
            }
        });
        vBTaddXy.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        vBTaddXy.setStyleName(BaseTheme.BUTTON_LINK);
        vBTaddXy.setDescription(controller.getBundle().getString("eds-add-line"));

        vBTremoveXy = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = 8565063049618054490L;

            public void buttonClick(ClickEvent event) {
                for (Object item : (Set<Object>) vTBxyDataValues.getValue()) {
                    vTBxyDataValues.removeItem(item);
                }
            }
        });
        vBTremoveXy.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/delete2.png"));
        vBTremoveXy.setStyleName(BaseTheme.BUTTON_LINK);
        vBTremoveXy.setDescription(controller.getBundle().getString("eds-delete-line"));

        vUpload = new Upload("", new Upload.Receiver() {
            private static final long serialVersionUID = -1454208727437943271L;

            public OutputStream receiveUpload(String filename, String mimeType) {

                outputStream = new ByteArrayOutputStream();
                fileName = filename.toLowerCase();
                return outputStream;
            }
        });

        vUpload.addListener(new Upload.SucceededListener() {
            private static final long serialVersionUID = 7425930652190070418L;

            public void uploadSucceeded(SucceededEvent event) {
                try {
                    vTBxyDataValues.getData();

                    ArrayList<String> times = new ArrayList<String>();
                    ArrayList<String> currents = new ArrayList<String>();
                    inputStream = new ByteArrayInputStream(outputStream.toByteArray());

                    if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                        // If import excel file
                        Workbook wb = WorkbookFactory.create(inputStream);

                        Sheet sheet = wb.getSheetAt(0);
                        Iterator rowIt = sheet.rowIterator();

                        while (rowIt.hasNext()) {
                            Row row = (Row) rowIt.next();
                            int i = row.getRowNum() + 1;
                            Iterator cellIt = row.cellIterator();
                            if (cellIt.hasNext()) {

                                // String time = nf.format( Double.parseDouble( ( (Cell) cellIt.next() ).toString() ) );
                                try {
                                    String time = getFormatedValue("#0.00", ((Cell) cellIt.next()).toString());
                                    times.add(time.replace(",", "."));
                                } catch (NumberFormatException e) {
                                    showNotification(MessageFormat.format(controller.getBundle().getString("profile-activation-msg-error"), i));
                                    inputStream.close();
                                    break;
                                }

                                if (cellIt.hasNext()) {
                                    try {
                                        String current = getFormatedValue("#0.00", (cellIt.next().toString()));
                                        currents.add(current.replace(",", "."));
                                    } catch (NumberFormatException e) {
                                        // showNotification( "La valeur doit être un nombre (ligne :" + i + " )" );
                                        showNotification(MessageFormat.format(controller.getBundle().getString("profile-activation-msg-error"), i));
                                        inputStream.close();
                                        break;
                                    }

                                } else {
                                    currents.add("");
                                }
                            }

                        }

                    } else if (fileName.endsWith(".csv")) {
                        CSVReader reader = new CSVReader(new InputStreamReader(inputStream), ';');
                        String[] nextLine;
                        int i = 1;
                        while ((nextLine = reader.readNext()) != null) {
                            try {
                                times.add(getFormatedValue("#0.00", (nextLine[0].replace(",", "."))));
                            } catch (NumberFormatException e) {
                                showNotification(MessageFormat.format(controller.getBundle().getString("profile-activation-msg-error"), i));
                                inputStream.close();
                                break;
                            }
                            if (nextLine.length > 1) {
                                try {
                                    currents.add(getFormatedValue("#0.00", (nextLine[1].replace(",", "."))));
                                } catch (NumberFormatException e) {
                                    showNotification(MessageFormat.format(controller.getBundle().getString("profile-activation-msg-error"), i));
                                    inputStream.close();
                                    break;
                                }

                            }
                            i++;
                        }
                    } else {
                        controller.getApplication().getMainWindow()
                                .showNotification(controller.getBundle().getString("eds-file-format-error"), Notification.TYPE_ERROR_MESSAGE);
                    }

                    vTBxyDataValues.removeAllItems();
                    for (int i = 0; i < times.size(); i++) {
                        try {
                            Float.parseFloat(times.get(i));
                            Float.parseFloat(currents.get(i));

                            vTBxyDataValues.addItem(new Object[] { times.get(i), currents.get(i) }, new Integer(i));
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e) {
                    controller.getApplication().getMainWindow()
                            .showNotification(controller.getBundle().getString("file-download-error"), Notification.TYPE_ERROR_MESSAGE);
                } catch (InvalidFormatException e) {
                    controller.getApplication().getMainWindow()
                            .showNotification(controller.getBundle().getString("file-download-error"), Notification.TYPE_ERROR_MESSAGE);

                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        inputStream = null;
                    } catch (IOException ex) {
                        Logger.getLogger(CurrentStatementEditView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        vUpload.addListener(new Upload.FailedListener() {

            private static final long serialVersionUID = -493709997705055508L;

            public void uploadFailed(FailedEvent event) {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    inputStream = null;
                } catch (IOException e) {
                }
            }
        });
        vUpload.setButtonCaption(controller.getBundle().getString("file-xls-import"));
        vUpload.setImmediate(true);

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSpacing(true);
        tableLayout.addComponent(vTBxyDataValues);
        tableLayout.addComponent(vUpload);
        tableLayout.setComponentAlignment(vUpload, Alignment.BOTTOM_CENTER);

        VerticalLayout buttonsLayout = new VerticalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.addComponent(vBTaddXy);
        buttonsLayout.addComponent(vBTremoveXy);

        VerticalLayout fieldLayout = new VerticalLayout();
        fieldLayout.addComponent(vLMesureTemp);
        fieldLayout.addComponent(vLYAxisValue);
        fieldLayout.addComponent(vLTimeAxisValue);
        fieldLayout.addComponent(VlSourceImpedance);

        customProfilView.addComponent(tableLayout);
        customProfilView.addComponent(buttonsLayout);
        customProfilView.addComponent(fieldLayout);

        customProfilView.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_LEFT);
        customProfilView.setExpandRatio(fieldLayout, 0.5f);

        // customProfilView.setExpandRatio(vTAxyDataCommentValue, 0.5f);
        tableLayout.setSizeUndefined();
        buttonsLayout.setSizeUndefined();
        customProfilView.setWidth("100%");
        this.setSizeFull();

        refreshTabData();

        this.addComponent(customProfilView);
    }

    /**
     * This method is used to show notification.
     * 
     * @param message Message to be displayed in notification.
     */
    private void showNotification(String message) {
        controller.getApplication().getMainWindow().showNotification(message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method retrieves the decimal formated value.
     * 
     * @param format number format.
     * @param value number to be formated.
     * @return formated number.
     * @throws NumberFormatException if incorrect number format specified.
     */
    private String getFormatedValue(String format, String value) throws NumberFormatException {
        NumberFormat nf = new DecimalFormat(format);
        String ret = "";

        ret = nf.format(Double.parseDouble(value));

        return ret;
    }

    /**
     * This method saves the changes to Databases
     */
    public boolean commitChanges() {
        String xyValues = getXyValue();
        if (!xyValues.equals(points.getCsppXyData() == null ? "" : points.getCsppXyData())) {
            points.setCsppXyData(xyValues);
        }

        String mesureTemp = vLMesureTemp.getString();
        if (!mesureTemp.equals(points.getCsppMesureTemp())) {
            points.setCsppMesureTemp(mesureTemp);
        }

        String yAxisValue = vLYAxisValue.getString();
        if (!yAxisValue.equals(points.getCsppYAxisValue())) {
            points.setCsppYAxisValue(yAxisValue);
        }

        String timeAxisValue = vLTimeAxisValue.getString();
        if (!timeAxisValue.equals(points.getCsppTimeAxisValue())) {
            points.setCsppTimeAxisValue(timeAxisValue);
        }

        String sourceImpedance = VlSourceImpedance.getString();
        if (!sourceImpedance.equals(points.getCsppSourceImpedance())) {
            points.setCsppSourceImpedance(sourceImpedance);
        }

        return true;
    }

    /**
     * This method is used to refresh the 'Complex profile' table.
     */
    private void refreshTabData() {
        String xyValue = points.getCsppXyData();
        if (xyValue != null && !"".equals(xyValue)) {
            for (String xyPoint : xyValue.split(";")) {
                String[] xyData = xyPoint.split(":");
                vTBxyDataValues.addItem(new Object[] { Float.parseFloat(xyData[0]), Float.parseFloat(xyData[1]) }, null);
            }
        }

        vLMesureTemp.setValue(points.getCsppMesureTemp());
        vLYAxisValue.setValue(points.getCsppYAxisValue());
        vLTimeAxisValue.setValue(points.getCsppTimeAxisValue());
        VlSourceImpedance.setValue(points.getCsppSourceImpedance());
    }

    /**
     * This method retrieves the x-y axis value in a semi-colon separated values.
     * 
     * @return semi-colon separated x-y values.
     */
    private String getXyValue() {
        StringBuilder builder = new StringBuilder("");

        for (Object id : vTBxyDataValues.getItemIds()) {
            Item item = vTBxyDataValues.getItem(id);
            if (!"".equals(item.getItemProperty(TIME_ID).toString()) && !"".equals(item.getItemProperty(CURRENT_ID).toString())) {
                builder.append(item.getItemProperty(TIME_ID)).append(":").append(item.getItemProperty(CURRENT_ID)).append(";");
            }
        }
        return builder.toString();
    }

    /**
     * This method checks if the mandatory fields have data.
     * 
     * @return boolean true if all mandatory fields have data, false otherwise.
     */
    boolean isValid() {
        return true;
    }
}

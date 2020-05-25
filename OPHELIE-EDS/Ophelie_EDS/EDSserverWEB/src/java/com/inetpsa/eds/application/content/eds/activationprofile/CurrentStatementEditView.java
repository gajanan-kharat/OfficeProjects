package com.inetpsa.eds.application.content.eds.activationprofile;

import au.com.bytecode.opencsv.CSVReader;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsCurrentStatement;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

/**
 * This class represents 'Current statement' in edit view in activation profile.
 * 
 * @author Geometric Ltd.
 */
public class CurrentStatementEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param statement EDS current statement details.
     * @param controller EDS application controller object.
     */
    public CurrentStatementEditView(EdsCurrentStatement statement, EdsApplicationController controller) {
        this.statement = statement;
        this.controller = controller;
        init();
    }

    /**
     * This method is used to retrieve the current statement details.
     * 
     * @return EDS current statement details.
     */
    public EdsCurrentStatement getStatement() {
        return statement;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (statement.getCsStatementType() == EdsCurrentStatement.CHART_STATEMENT) {
            String xyValues = getXyValue();
            if (!xyValues.equals(statement.getCsXyData() == null ? "" : statement.getCsXyData())) {
                statement.setCsXyData(xyValues);
            }
            statement.setEdsUserId(controller.getAuthenticatedUser());
            statement.setCsComment((String) vTAxyDataCommentValue.getValue());
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        if (statement.getCsStatementType() == EdsCurrentStatement.CHART_STATEMENT) {
            vTAxyDataCommentValue.setValue(statement.getCsComment());
            vTBxyDataValues.removeAllItems();
            refreshTabData();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant variable to store 'time'.
     */
    private static final String TIME_ID = "time";
    /**
     * Constant variable to store 'intensity'.
     */
    private static final String CURRENT_ID = "intensity";
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store EDS current statement details.
     */
    private EdsCurrentStatement statement;
    /**
     * Layout for custom profile view.
     */
    private HorizontalLayout customProfilView;
    /**
     * Table to store time and current details.
     */
    private Table vTBxyDataValues;
    /**
     * Text area for 'Notes'.
     */
    private TextArea vTAxyDataCommentValue;
    /**
     * Button to add Time and current row to table.
     */
    private Button vBTaddXy;
    /**
     * Button to remove Time and current row to table.
     */
    private Button vBTremoveXy;
    /**
     * Button to upload files.
     */
    private Upload vUpload;
    /**
     * Variable to store byte array output stream.
     */
    private ByteArrayOutputStream outputStream;
    /**
     * Variable to store byte array input stream.
     */
    private ByteArrayInputStream inputStream;
    /**
     * Variable to store filename.
     */
    private String fileName;

    /**
     * Initialization method. The method switches between chart view and doc info view.
     */
    private void init() {
        setSpacing(true);
        setMargin(true);
        switch (statement.getCsStatementType()) {
        case EdsCurrentStatement.DOC_INFO_STATEMENT:
            addComponent(getDocInfoView());
            break;
        case EdsCurrentStatement.CHART_STATEMENT:
            addComponent(getCustomProfileView());
            break;
        }
    }

    /**
     * The method builds the chart view. It adds table for taking input of x(time) and y(current) axis from the user. It also has an import
     * functionality to import the values of x-y axis.
     * 
     * @return Layout for chart view.
     */
    private HorizontalLayout getCustomProfileView() {
        if (customProfilView == null) {
            customProfilView = new HorizontalLayout();
            customProfilView.setMargin(true);
            customProfilView.setSpacing(true);

            vTBxyDataValues = new Table();
            vTBxyDataValues.setEditable(true);
            vTBxyDataValues.setSelectable(true);
            vTBxyDataValues.setMultiSelect(true);
            vTBxyDataValues.setImmediate(true);
            vTBxyDataValues.addContainerProperty(TIME_ID, Double.class, "", controller.getBundle().getString("eds-time-label"), null, null);
            vTBxyDataValues.addContainerProperty(CURRENT_ID, Double.class, "", controller.getBundle().getString("eds-courant-label"), null, null);
            vTAxyDataCommentValue = new TextArea(controller.getBundle().getString("eds-comment-label") + " :");
            vTAxyDataCommentValue.setNullRepresentation("");
            vTAxyDataCommentValue.setImmediate(true);

            vBTaddXy = new Button("", new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    vTBxyDataValues.addItem();
                }
            });
            vBTaddXy.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
            vBTaddXy.setStyleName(BaseTheme.BUTTON_LINK);
            vBTaddXy.setDescription(controller.getBundle().getString("eds-add-line"));

            vBTremoveXy = new Button("", new Button.ClickListener() {
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
                public OutputStream receiveUpload(String filename, String mimeType) {

                    outputStream = new ByteArrayOutputStream();
                    fileName = filename.toLowerCase();
                    return outputStream;
                }
            });

            vUpload.addListener(new Upload.SucceededListener() {
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
                                            showNotification(MessageFormat
                                                    .format(controller.getBundle().getString("profile-activation-msg-error"), i));
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

            customProfilView.addComponent(tableLayout);
            customProfilView.addComponent(buttonsLayout);
            customProfilView.addComponent(vTAxyDataCommentValue);
            customProfilView.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_LEFT);
            customProfilView.setExpandRatio(vTAxyDataCommentValue, 0.5f);
            tableLayout.setSizeUndefined();
            buttonsLayout.setSizeUndefined();
            vTAxyDataCommentValue.setWidth("400px");
            customProfilView.setWidth("100%");

        }

        return customProfilView;
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
     * This method is used to refresh the 'Complex profile' table.
     */
    private void refreshTabData() {
        String xyValue = statement.getCsXyData();
        if (xyValue != null) {
            for (String xyPoint : xyValue.split(";")) {
                String[] xyData = xyPoint.split(":");
                vTBxyDataValues.addItem(new Object[] { Float.parseFloat(xyData[0]), Float.parseFloat(xyData[1]) }, null);
            }
        }
    }

    /**
     * This method is used to retrieve the doc-info view.
     * 
     * @return Component for doc-info view.
     */
    private VerticalLayout getDocInfoView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        URL url = null;
        try {
            url = new URL(statement.getCsDocInfoUrl());

        } catch (MalformedURLException ex) {
            getWindow().showNotification(controller.getBundle().getString("eds-url-error"));
        }
        layout.addComponent(new LienDocInfo(url, controller));
        return layout;
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
     * This method is used to show notification.
     * 
     * @param message Message to be displayed in notification.
     */
    private void showNotification(String message) {
        controller.getApplication().getMainWindow().showNotification(message, Notification.TYPE_ERROR_MESSAGE);
    }
}

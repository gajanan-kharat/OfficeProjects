package com.inetpsa.eds.application.content.eds.connectivity.cavity.panel;

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
import java.util.List;
import java.util.ResourceBundle;
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
import com.inetpsa.eds.application.content.eds.connectivity.cavity.util.ProfileType;
import com.inetpsa.eds.application.content.eds.connectivity.cavity.validator.TFDoubleValidator;
import com.inetpsa.eds.dao.model.EdsCPProfile;
import com.inetpsa.eds.dao.model.EdsCPProperty;
import com.inetpsa.eds.dao.model.EdsPinConnect;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class defines a Complex Profile Table of Points panel for editing.
 * 
 * @author jcosta @ Alter Frame
 */
public class ComplexProfileTableOfPointsEditView extends VerticalLayout {

    private static final long serialVersionUID = 5053781617093670213L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Point table **/
    private Table vTBxyDataValues;

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

    /** Resources **/
    private ResourceBundle bundle;

    /** Selected Pin **/
    private EdsPinConnect selectedItem;

    /** Constant variable to store 'time'. **/
    private static final String TIME_ID = "type";

    /** Constant variable to store 'intensity'. **/
    private static final String CURRENT_ID = "value";

    private BeanItemContainer<EdsCPProperty> containerProperty;

    private BeanItemContainer<EdsCPProfile> containerProfile;

    private TextArea commentArea;

    private boolean newItem;

    private List<TextField> fields;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public ComplexProfileTableOfPointsEditView(final EdsApplicationController controller, final EdsPinConnect selected, final boolean newItem) {
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.selectedItem = selected;
        this.newItem = newItem;
        this.fields = new ArrayList<TextField>();
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
        containerProfile = new BeanItemContainer<EdsCPProfile>(EdsCPProfile.class);
        vTBxyDataValues.setContainerDataSource(containerProfile);
        vTBxyDataValues.setVisibleColumns(new Object[] { TIME_ID, CURRENT_ID });
        vTBxyDataValues.setColumnHeaders(new String[] { controller.getBundle().getString("eds-time-label"),
                controller.getBundle().getString("eds-courant-label") });
        vTBxyDataValues.setEditable(true);
        vTBxyDataValues.setSelectable(true);
        vTBxyDataValues.setMultiSelect(true);
        vTBxyDataValues.setImmediate(true);

        vTBxyDataValues.setTableFieldFactory(new DefaultFieldFactory() {

            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);
                if (cls.equals(Double.class)) {
                    TextField tf = new TextField();
                    tf.setNullRepresentation("");
                    tf.addValidator(new TFDoubleValidator(false));
                    fields.add(tf);
                    return tf;
                }

                return super.createField(container, itemId, propertyId, uiContext);
            }
        });
        if (newItem) {
            EdsCPProfile item = new EdsCPProfile();
            item.setWi(selectedItem);
            containerProfile.addItem(item);
            item = new EdsCPProfile();
            item.setWi(selectedItem);
            containerProfile.addItem(item);
        } else {
            containerProfile.addAll(selectedItem.getCpProfile());
        }
        vBTaddXy = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = -1664507052099814219L;

            public void buttonClick(ClickEvent event) {
                EdsCPProfile item = new EdsCPProfile();
                item.setWi(selectedItem);
                containerProfile.addItem(item);
            }
        });
        vBTaddXy.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        vBTaddXy.setStyleName(BaseTheme.BUTTON_LINK);
        vBTaddXy.setDescription(controller.getBundle().getString("eds-add-line"));

        vBTremoveXy = new Button("", new Button.ClickListener() {
            private static final long serialVersionUID = 8565063049618054490L;

            public void buttonClick(ClickEvent event) {
                for (Object item : (Set<Object>) vTBxyDataValues.getValue()) {
                    containerProfile.removeItem(item);
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
                    // vTBxyDataValues.getData();

                    ArrayList<String> times = new ArrayList<String>();
                    ArrayList<String> currents = new ArrayList<String>();
                    inputStream = new ByteArrayInputStream(outputStream.toByteArray());

                    if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                        // If import excel file
                        Workbook wb = WorkbookFactory.create(inputStream);

                        Sheet sheet = wb.getSheetAt(0);
                        Iterator rowIt = sheet.rowIterator();
                        if (rowIt.hasNext()) {
                            rowIt.next();
                        }
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

                    containerProfile.removeAllItems();
                    for (int i = 0; i < times.size(); i++) {
                        try {
                            EdsCPProfile item = new EdsCPProfile();
                            item.setWi(selectedItem);
                            item.setType(Double.parseDouble(times.get(i)));
                            item.setValue(Double.parseDouble(currents.get(i)));
                            containerProfile.addItem(item);

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

        customProfilView.addComponent(tableLayout);
        customProfilView.addComponent(buttonsLayout);

        commentArea = new TextArea(bundle.getString("chs-cavdef-pintype-comment"));
        commentArea.setNullRepresentation("");
        commentArea.setWidth("400px");
        if (!newItem) {
            commentArea.setValue(selectedItem.getComplexComment());
        }
        customProfilView.addComponent(commentArea);

        customProfilView.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_LEFT);

        // customProfilView.setExpandRatio(vTAxyDataCommentValue, 0.5f);
        tableLayout.setSizeUndefined();
        buttonsLayout.setSizeUndefined();
        // customProfilView.setWidth("100%");
        this.setSizeFull();

        this.addComponent(getInomVmin());
        this.addComponent(customProfilView);
    }

    private Table getInomVmin() {
        Table table = new Table();
        containerProperty = new BeanItemContainer<EdsCPProperty>(EdsCPProperty.class);
        table.setContainerDataSource(containerProperty);
        table.setPageLength(0);
        table.setSortDisabled(true);
        table.setCellStyleGenerator(new CellStyleGenerator() {

            @Override
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "rowheader";
                }

                // int row = ((Integer) itemId).intValue();
                // if (row == 0) {
                // return "rowheader";
                // }
                // int col = Integer.parseInt((String) propertyId);
                String property = (String) propertyId;

                for (int x = 1; x < 5; x++) {
                    if (Integer.toString(x).equals(property)) {
                        return "rowheader";
                    }
                }

                return "white";
            }
        });

        table.setTableFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                Class<?> cls = container.getType(propertyId);

                if (cls.equals(String.class) || cls.equals(Double.class)) {
                    TextField cav = new TextField();
                    cav.setNullRepresentation("");

                    if (cls.equals(Double.class)) {
                        cav.addValidator(new TFDoubleValidator(false));
                        fields.add(cav);
                    }
                    if (propertyId.equals("user")) {
                        cav.setReadOnly(true);
                    }
                    return cav;
                }
                return super.createField(container, itemId, propertyId, uiContext);
            }
        });

        table.addGeneratedColumn("typeName", new ColumnGenerator() {

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                EdsCPProperty item = (EdsCPProperty) itemId;
                String name = ProfileType.getProfileFromType(item.getType()).getName();
                return bundle.getString(name);
            }

        });
        table.setEditable(true);
        table.setVisibleColumns(new Object[] { "typeName", "value", "comment" });
        table.setColumnHeaders(new String[] { "", bundle.getString("chs-newprofile-value"), bundle.getString("chs-newprofile-comment") });
        if (newItem) {
            containerProperty.addItem(getCPProperty(ProfileType.INOM));
            containerProperty.addItem(getCPProperty(ProfileType.VMIN));
        } else {
            containerProperty.addAll(selectedItem.getCpProperty());
        }
        table.setWidth("50%");
        table.setColumnExpandRatio(0, 1.0f);
        table.setColumnExpandRatio(1, 2.0f);
        table.setColumnExpandRatio(2, 4.0f);

        return table;

    }

    private EdsCPProperty getCPProperty(ProfileType profile) {
        EdsCPProperty item = new EdsCPProperty();
        item.setWi(selectedItem);
        item.setType(profile.getIndex());
        return item;
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

    public List<EdsCPProperty> getProperties() {
        List<EdsCPProperty> properties = new ArrayList<EdsCPProperty>(containerProperty.getItemIds());
        return properties;
    }

    public List<EdsCPProfile> getProfile() {
        List<EdsCPProfile> profiles = new ArrayList<EdsCPProfile>(containerProfile.getItemIds());
        Iterator<EdsCPProfile> it = profiles.iterator();
        EdsCPProfile item;
        while (it.hasNext()) {
            item = it.next();
            if (item.getType() == null && item.getValue() == null) {
                it.remove();
            }
        }
        return profiles;
    }

    public String getComment() {
        return (String) commentArea.getValue();
    }

    /**
     * This method checks if the mandatory fields have data.
     * 
     * @return boolean true if all mandatory fields have data, false otherwise.
     */
    public boolean isValid() {
        boolean valid = true;

        for (TextField field : fields) {
            field.removeStyleName("validate-chs-error");
            if (!field.isValid()) {
                valid = false;
                field.addStyleName("validate-chs-error");
            }
        }

        return valid;
    }
}

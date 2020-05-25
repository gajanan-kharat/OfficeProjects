/*
 * Creation : May 30, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.psa.PSAAdminMSOutils.UtilityFunctions;
import com.psa.tools.LaunchDate;
import com.psa.tools.PSADatePicker;
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSAMessageDialog;

public class PSAFilterPropertiesPanel extends JDialog implements PSAFilterPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("PSAFilterPropertiesPanel");
    private JTextField textFieldFltrName;
    private JTextField textFieldFltRev;
    private JTextField textFieldFltProject;
    private JTextField textFieldFltDesignation;
    private JComboBox comboBoxFltType;
    public static JTextField textFieldFilterStartDate;
    public static JTextField textFldModeParam;
    public static JTextField textFldFltLaunchDate;
    private JButton btnPSAFilterStartDate;
    private JButton btnModeParam;
    private JButton btnCancel;
    public static JButton btnOk;

    JComboBox comboBoxForMode;
    private Object selected;
    private PSAFilterModel psaFilter;
    private PSAFilterModel filterModel;
    PSACreateFilterWeekPanel weekPanel = null;
    PSACreateFilterMonthPanel monthPanel = null;
    static int countModeParamClick = 0;
    static List<Object> listWeekDaysSelected = new ArrayList<Object>();

    public PSAFilterPropertiesPanel() {
        logger.info("PSAFilterPropertiesPanel : START Constructor of the class");

        prepareFltrPropertiesUI();
        logger.info("PSAFilterPropertiesPanel : END constructor of the class");
    }

    public PSAFilterPropertiesPanel(PSAFilterModel inPsaFilter) {
        logger.info("START Constructor of the class");
        setPsaFilter(inPsaFilter);
        prepareFltrPropertiesUI();
        logger.info("END constructor of the class");
    }

    public PSAFilterModel getPsaFilter() {
        return psaFilter;
    }

    public void setPsaFilter(PSAFilterModel psaFilter) {
        this.psaFilter = psaFilter;
    }

    public PSAFilterModel getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(PSAFilterModel filterModel) {
        this.filterModel = filterModel;
    }

    private boolean statusForModifyAndViewAction() {
        logger.info("START getEnableStatus");
        boolean enableFlag = PSAFilterConstant.FALSE_VAL;
        if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)
                || (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER))) {
            enableFlag = PSAFilterConstant.TRUE_VAL;
        }
        logger.info("END getEnableStatus");
        return enableFlag;
    }

    private boolean statusForViewAction() {
        logger.info("START statusForViewAction");
        boolean enableFlag = PSAFilterConstant.FALSE_VAL;
        if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER)) {
            enableFlag = PSAFilterConstant.TRUE_VAL;
        }

        logger.info("START statusForViewAction");
        return enableFlag;
    }

    private int getSelectedModeIndex() {
        logger.info("START getSelectedModeIndex");
        int modeSelectIndex = 0;
        for (MODE mode : MODE.values()) {
            String strMode = new String("" + mode);
            if (strMode.equals(getPsaFilter().getMode())) {
                return modeSelectIndex;
            }
            modeSelectIndex++;
        }
        logger.info("END getSelectedModeIndex");
        return modeSelectIndex;
    }

    private int getSelectedIndexForModeComboBox() {
        int index;
        String mode = getPsaFilter().getMode();
        if (mode.equals(MODE.DAILY.toString()))
            index = 0;
        else if (mode.equals(MODE.WEEKLY.toString()))
            index = 1;
        else
            index = 2;
        return index;
    }

    private int getSelectedIndexForTypeComboBox() {
        int index;
        String strType = getPsaFilter().getType();
        if (strType.equals("ISO"))
            index = 0;
        else if (strType.equals("CIBLE"))
            index = 1;
        else
            index = 2;//"ISO/CIBLE"
        return index;
    }

    void prepareFltrPropertiesUI() {
        logger.info("START prepareFltrPropertiesUI()");
        final JDialog dialogProperties = new JDialog(this, PSAFilterUIController.objLanguageHandler.Getlabel("Title.SubscriptionsProperties","Subscriptions Properties "));
        JPanel form = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        dialogProperties.getContentPane().setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;

        dialogProperties.getContentPane().add(form, constraints);
        GridBagLayout gridBagLayout = new GridBagLayout();
        form.setLayout(gridBagLayout);
        CreateFilterForm createFltrForm = new CreateFilterForm();
        
        // Filter NAME
        JLabel labelFltName = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterName","Filter Name"));

        createFltrForm.addLabel(labelFltName, form);
        textFieldFltrName = new JTextField(15);
        textFieldFltrName.setDocument(new JTextFieldLimit(40));
        if (statusForModifyAndViewAction()) {
            textFieldFltrName.setText(getPsaFilter().getName());
            textFieldFltrName.setEditable(PSAFilterConstant.FALSE_VAL);
        }
        createFltrForm.addLastField(textFieldFltrName, form);
        
        // Filter REVISION
        JLabel labelFltrRev = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterRevision","Filter Revision"));
        createFltrForm.addLabel(labelFltrRev, form);
        textFieldFltRev = new JTextField(3);
        textFieldFltRev.setDocument(new JTextFieldLimit(3));
        if (statusForModifyAndViewAction()) {
            textFieldFltRev.setText(getPsaFilter().getRevision());
            textFieldFltRev.setEditable(PSAFilterConstant.FALSE_VAL);
        }
        textFieldFltRev.setText("---");
        createFltrForm.addLastField(textFieldFltRev, form);

        // Filter Project
        JLabel labelFltrProject = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterProject","Filter Project"));
        createFltrForm.addLabel(labelFltrProject, form);
        textFieldFltProject = new JTextField(40);
        textFieldFltProject.setDocument(new JTextFieldLimit(40));
        if (statusForModifyAndViewAction())
        {
            textFieldFltProject.setText(getPsaFilter().getProject());
        }
        if (statusForViewAction())
        {
            textFieldFltProject.setEditable(PSAFilterConstant.FALSE_VAL);
        }
        else
        {
            textFieldFltProject.setEditable(PSAFilterConstant.TRUE_VAL);
        }
        createFltrForm.addLastField(textFieldFltProject, form);

        // Filter Designation
        JLabel labelFltrDesignation = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterDesignation","Filter Designation"));
        createFltrForm.addLabel(labelFltrDesignation, form);
        textFieldFltDesignation = new JTextField(40);
        textFieldFltDesignation.setDocument(new JTextFieldLimit(40));
        if (statusForModifyAndViewAction())
        {
            textFieldFltDesignation.setText(getPsaFilter().getDesignation());
        }
        if (statusForViewAction())
        {
            textFieldFltDesignation.setEditable(PSAFilterConstant.FALSE_VAL);
        }
        else
        {
            textFieldFltDesignation.setEditable(PSAFilterConstant.TRUE_VAL);
        }
        createFltrForm.addLastField(textFieldFltDesignation, form);

        // Filter Type
        JLabel labelFltrType = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterType","Filter Type"));
        createFltrForm.addLabel(labelFltrType, form);
        DefaultComboBoxModel comboBoxModelFilterType = new DefaultComboBoxModel();
        comboBoxModelFilterType.addElement("ISO");
        comboBoxModelFilterType.addElement("CIBLE");
        comboBoxModelFilterType.addElement("ISO/CIBLE");
        comboBoxFltType = new JComboBox(comboBoxModelFilterType);
        if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)) {
            comboBoxFltType.setSelectedIndex(getSelectedIndexForTypeComboBox());
        }
        if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER)) {
            comboBoxFltType.setEnabled(PSAFilterConstant.FALSE_VAL);
            comboBoxFltType.setSelectedIndex(getSelectedIndexForTypeComboBox());
        }
        createFltrForm.addLastField(comboBoxFltType, form);
        
        // Start Filter Date
        // Label
        JLabel labelPSAStartFilterDate = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterStartDate","Filter Start Date"));
        createFltrForm.addLabel(labelPSAStartFilterDate, form);

        // Text Field
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        textFieldFilterStartDate = new JTextField(15);
        textFieldFilterStartDate.setEditable(false);
        textFieldFilterStartDate.setDocument(new JTextFieldLimit(15));
        textFieldFilterStartDate.setText("");
        createFltrForm.addMiddleField(textFieldFilterStartDate, form);
        if (statusForModifyAndViewAction()) {
            textFieldFilterStartDate.setText(getPsaFilter().getStartDate());
        }
        // Date Button
        btnPSAFilterStartDate = new JButton();
        btnPSAFilterStartDate.setText(PSAFilterUIController.objLanguageHandler.Getlabel("Button.ChooseButton","..."));
        if (statusForViewAction()) {
            btnPSAFilterStartDate.setEnabled(PSAFilterConstant.FALSE_VAL);
            btnPSAFilterStartDate.setVisible(PSAFilterConstant.FALSE_VAL);
        } else {
            btnPSAFilterStartDate.setEnabled(PSAFilterConstant.TRUE_VAL);
        }

        btnPSAFilterStartDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                btnPSAFilterStartDateActionPerformed(event);
            }
        });
        createFltrForm.addLabel(btnPSAFilterStartDate, panel);
        createFltrForm.addLastField(panel, form);

        // For Mode attribute
        JLabel labelMode = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterMode","Mode"));
        createFltrForm.addLabel(labelMode, form);
        DefaultComboBoxModel comboBoxModelForMode = new DefaultComboBoxModel();
        for (MODE mode : MODE.values()) {
            if (!mode.equals(MODE.ALL))
                comboBoxModelForMode.addElement(mode);
        }
        comboBoxForMode = new JComboBox(comboBoxModelForMode);
        if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)) {
            comboBoxForMode.setSelectedIndex(getSelectedIndexForModeComboBox());
        }
        if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER)) {
            comboBoxForMode.setEnabled(PSAFilterConstant.FALSE_VAL);
            comboBoxForMode.setSelectedIndex(getSelectedIndexForModeComboBox());
        }

        createFltrForm.addLastField(comboBoxForMode, form);
        comboBoxForMode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                textFldModeParam.setText(PSAFilterConstant.EMPTY_STRING);
                textFldFltLaunchDate.setText(PSAFilterConstant.EMPTY_STRING);
                JComboBox comboBox = (JComboBox) event.getSource();
                selected = comboBox.getSelectedItem();
                if ("DAILY".equals(selected.toString())) {
                    textFldModeParam.setText(PSAFilterConstant.EMPTY_STRING);
                    textFldModeParam.setEnabled(PSAFilterConstant.FALSE_VAL);
                    btnModeParam.setEnabled(PSAFilterConstant.FALSE_VAL);

                    // Setting Next Launch Date field
                    String strNextLaunchDate = PSAFilterConstant.EMPTY_STRING;
                    String strStartDateFieldValue = textFieldFilterStartDate.getText();
                    if (!strStartDateFieldValue.isEmpty()) {
                        // Setting Start Date as Execution Date i.e. initial Next Launch Date  
                        textFldFltLaunchDate.setText(strStartDateFieldValue);
                    }
                } else {
                    btnModeParam.setEnabled(PSAFilterConstant.TRUE_VAL);
                }
            }

        });

        JLabel labelModeParam = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.FilterModeParameter","Mode Parameter"));
        createFltrForm.addLabel(labelModeParam, form);
        textFldModeParam = new JTextField();
        textFldModeParam.setColumns(15);
        textFldModeParam.setEnabled(PSAFilterConstant.FALSE_VAL);
        createFltrForm.addMiddleField(textFldModeParam, form);
        btnModeParam = new JButton("...");
        if (statusForModifyAndViewAction())
            textFldModeParam.setText(getPsaFilter().getModeParam());
        if (statusForViewAction()) {
            btnModeParam.setVisible(PSAFilterConstant.FALSE_VAL);
        } else {
            btnModeParam.setVisible(PSAFilterConstant.TRUE_VAL);
            btnModeParam.setEnabled(PSAFilterConstant.FALSE_VAL);
        }

        btnModeParam.addActionListener(new ActionListener() {
            List<String> weekDays = new ArrayList<String>() {
                {
                    add("MONDAY");
                    add("TUESDAY");
                    add("WEDNESDAY");
                    add("THURSDAY");
                    add("FRIDAY");
                    add("SATURDAY");
                    add("SUNDAY");
                }
            };

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("WEEKLY".equals(selected.toString())) {
                    countModeParamClick = countModeParamClick + 1;
                    System.out.println("COUNTER CHECK: " + countModeParamClick);
                    weekPanel = new PSACreateFilterWeekPanel(weekDays);
                    weekPanel.dummy();
                } else {
                    countModeParamClick = 0;
                    PSAFilterPropertiesPanel.listWeekDaysSelected.clear();
                    monthPanel = new PSACreateFilterMonthPanel();
                    monthPanel.dummy();
                }
            }
        });
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridBagLayout());
        createFltrForm.addLabel(btnModeParam, newPanel);
        createFltrForm.addLastField(newPanel, form);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.1;

        // For NEXT LAUNCH DATE attribute
        JLabel labelFltrLaunchDate = new JLabel(PSAFilterUIController.objLanguageHandler.Getlabel("Label.NextLaunchDate","Next Launch Date"));
        createFltrForm.addLabel(labelFltrLaunchDate, form);
        textFldFltLaunchDate = new JTextField();
        textFldFltLaunchDate.setEnabled(PSAFilterConstant.FALSE_VAL);
        if (statusForModifyAndViewAction()) {
            textFldFltLaunchDate.setText(getPsaFilter().getNextLaunchDate().toString());
        }
        createFltrForm.addLastField(textFldFltLaunchDate, form);

        // For OK button
        btnOk = new JButton(PSAFilterUIController.objLanguageHandler.Getlabel("Button.Ok","OK"));
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listWeekDaysSelected.clear();
                if (textFldModeParam.getText().isEmpty() && !(PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER))
                        && (comboBoxForMode.getSelectedIndex() != 0)) {
                    checkModeParam();
                } else {
                    if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER)) {
                        setVisible(PSAFilterConstant.FALSE_VAL);
                    }
                    if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)) {
                        if (checkForDataChange()) {
                            logger.info("Updating data in the data base");
                            setFilterModel(getFilterModelData());
                            try {
                                // Data base call to update the selected filter
                                PSAFilterUIController.getFilterDBHandler().modifyFilter(getFilterModelData());
                                JOptionPane.showMessageDialog(null,
                                        "Successfully updated filter with name " + getFilterModel().getName() + " in the data base!",
                                        "Success in filter updation", JOptionPane.INFORMATION_MESSAGE);
                                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                clearListForUIDisplay();
                                refereshListForUIDisplay();
                                repaint();
                                setVisible(PSAFilterConstant.FALSE_VAL);
                            } catch (PSAErrorHandler e1) {
                                logger.info("Exception :" + e1.m_StrErrorLabel + e1.ErrorCode);
                                JOptionPane.showMessageDialog(null,
                                        "Failed to Update filter with name " + getFilterModel().getName() + " in the data base!",
                                        "Error in filter updation", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Filter Data is not Changed, Please change the data to modify filter!",
                                    "Error in filter Modify", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    if (PSAFilterUIController.getFilterAction().equals(ACTION.CREATE_FILTER)) {
                        if (checkForEmptyFilter()) {
                            JOptionPane.showMessageDialog(null, 
                                    PSAFilterUIController.objLanguageHandler.Getlabel("Error.MandatoryFieldsMissing", "Filter with empty field as Filter Name OR Revision OR Project OR Designation OR Start Date is not allowed to create!"),
                                    PSAFilterUIController.objLanguageHandler.Getlabel("Title.MandatoryFieldsMissing", "Error in filter creation"),
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            logger.info("Creating data in the data base");
                            setFilterModel(getFilterModelData());
                            try {
                                int createFilter = PSAFilterUIController.getFilterDBHandler().createFilter(getFilterModel());
                                if (createFilter == 111) {
                                    logger.info("Filter name already exist in the data base!!!");
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Successfully created filter with name " + getFilterModel().getName() + " in the data base!",
                                            "Success in filter creation", JOptionPane.INFORMATION_MESSAGE);
                                    clearAllPropertiesField();
                                    System.out.println("AFTER modify calling CREATE is Dangerous");
                                    clearListForUIDisplay();
                                    refereshListForUIDisplay();
                                    repaint();
                                    setVisible(PSAFilterConstant.FALSE_VAL);
                                }
                            } catch (PSAErrorHandler ex) {
                                logger.info("Exception :" + ex.m_StrErrorLabel + ex.ErrorCode);
                                JOptionPane.showMessageDialog(null,
                                        "Failed to create filter with name " + getFilterModel().getName() + " in the data base!",
                                        "Error in filter creation", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }

                }

            }

        });
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addLabel(new JLabel(""), form);
        createFltrForm.addMiddleField(btnOk, form);
        // For CANCEL button
        btnCancel = new JButton(PSAFilterUIController.objLanguageHandler.Getlabel("Button.CancelBtn","Cancel"));
        if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER))
            btnCancel.setVisible(PSAFilterConstant.FALSE_VAL);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listWeekDaysSelected.clear();
                dialogProperties.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialogProperties.setVisible(PSAFilterConstant.FALSE_VAL);
                clearFilterModel();
            }
        });
        createFltrForm.addLastField(btnCancel, form);

        form.setBorder(new EmptyBorder(2, 2, 2, 2));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                disposePanel(0);
            }
        });

        dialogProperties.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                logger.info("windowOpened");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                logger.info("windowIconified");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                logger.info("windowDeiconified");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                logger.info("windowDeactivated");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                listWeekDaysSelected.clear();
                logger.info("Window is Closing now");
                if (PSAFilterUIController.getFilterAction().equals(ACTION.CREATE_FILTER))
                    PSAFilterUIController.setFilterAction(null);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                logger.info("Window is Closed ");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                logger.info("windowActivated");

            }
        });
        dialogProperties.setModal(PSAFilterConstant.TRUE_VAL);
        dialogProperties.pack();
        createFltrForm.setModal(PSAFilterConstant.TRUE_VAL);
        dialogProperties.setLocation(PSAUtilityFunctions.getCenterPosition(dialogProperties));
        this.setLocation(PSAUtilityFunctions.getCenterPosition(this));
        dialogProperties.setResizable(PSAFilterConstant.FALSE_VAL);
        dialogProperties.setVisible(PSAFilterConstant.TRUE_VAL);
        this.setModal(PSAFilterConstant.FALSE_VAL);

        logger.info("END prepareFltrPropertiesUI()");
    }

    /**
     * To check For data change in Modify Filter.
     * 
     * @return
     */
    private boolean checkForDataChange() {
        boolean dataChangeFlag = PSAFilterConstant.FALSE_VAL;
        if (!PSAFilterUIController.selectedFilter.getName().equals(textFieldFltrName.getText().toString()))
            dataChangeFlag = PSAFilterConstant.TRUE_VAL;
        if (!PSAFilterUIController.selectedFilter.getRevision().equals(textFieldFltRev.getText().toString()))
            dataChangeFlag = PSAFilterConstant.TRUE_VAL;
        if (!PSAFilterUIController.selectedFilter.getStartDate().equals(textFieldFilterStartDate.getText().toString()))
            dataChangeFlag = PSAFilterConstant.TRUE_VAL;
        if (!PSAFilterUIController.selectedFilter.getMode().equals(comboBoxForMode.getSelectedItem().toString()))
            dataChangeFlag = PSAFilterConstant.TRUE_VAL;
        if (!PSAFilterUIController.selectedFilter.getNextLaunchDate().equals(textFldFltLaunchDate.getText().toString()))
            dataChangeFlag = PSAFilterConstant.TRUE_VAL;
        return dataChangeFlag;
    }

    /**
     * To check mandatory arguments
     * 
     * @return
     */
    private boolean checkForEmptyFilter() {
        boolean statusFlag = false;
        if (PSAFilterConstant.EMPTY_STRING.equals(textFieldFltrName.getText().toString()))
            statusFlag = PSAFilterConstant.TRUE_VAL;
        if (PSAFilterConstant.EMPTY_STRING.equals(textFieldFltRev.getText().toString()))
            statusFlag = PSAFilterConstant.TRUE_VAL;
        if (PSAFilterConstant.EMPTY_STRING.equals(textFieldFltProject.getText().toString()))
            statusFlag = PSAFilterConstant.TRUE_VAL;
        if (PSAFilterConstant.EMPTY_STRING.equals(textFieldFltDesignation.getText().toString()))
            statusFlag = PSAFilterConstant.TRUE_VAL;
        if (PSAFilterConstant.EMPTY_STRING.equals(textFieldFilterStartDate.getText().toString()))
            statusFlag = PSAFilterConstant.TRUE_VAL;
        return statusFlag;

    }

    protected void refereshListForUIDisplay() throws PSAErrorHandler {
        logger.info("START refereshListForUIDisplay()");
        List<PSAFilterModel> filterList = PSAFilterUIController.getFilterDBHandler().getFilterList();
        PSAFilterUIController.arrayListfilters.clear();
        PSAFilterUIController.filterDataModel.clearList();
        PSAFilterUIController.scrollPaneTable.repaint();
        for (PSAFilterModel psaFilterModel : filterList) {
            PSAFilterUIController.arrayListfilters.add(psaFilterModel);
        }
        if (PSAFilterUIController.arrayListfilters != null) {
            PSAFilterUIController.filterDataModel.refreshList(PSAFilterUIController.arrayListfilters);
            PSAFilterUIController.scrollPaneTable.setViewportView(PSAFilterUIController.tableFilter);
            PSAFilterUIController.scrollPaneTable.repaint();
        }
        logger.info("END refereshListForUIDisplay()");
    }

    private void clearAllPropertiesField() {
        textFieldFltrName.setText(PSAFilterConstant.EMPTY_STRING);
        textFieldFltRev.setText(PSAFilterConstant.EMPTY_STRING);
        textFldModeParam.setText(PSAFilterConstant.EMPTY_STRING);
        textFldFltLaunchDate.setText(PSAFilterConstant.EMPTY_STRING);
    }

    private void clearFilterModel() {
        logger.info("START clearFilterModel(");
        if (getFilterModel() != null) {
            getFilterModel().setId(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setName(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setRevision(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setProject(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setDesignation(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setStartDate(PSAFilterConstant.EMPTY_STRING);
            getFilterModel().setNextLaunchDate(PSAFilterConstant.EMPTY_STRING);
        }
        logger.info("END clearFilterModel(");
    }

    @SuppressWarnings("deprecation")
    private PSAFilterModel getFilterModelData() {
        logger.info("START getFilterModelData()");
        PSAFilterModel filterEntity = new PSAFilterModel();
        if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)) {
            System.out.println("=============== Selected Filter ID: " + PSAFilterUIController.selectedFilter.getId());
            filterEntity.setId(PSAFilterUIController.selectedFilter.getId());
        }
        filterEntity.setName(textFieldFltrName.getText());
        filterEntity.setRevision(textFieldFltRev.getText());
        filterEntity.setProject(textFieldFltProject.getText());
        filterEntity.setDesignation(textFieldFltDesignation.getText());
        String strType = comboBoxFltType.getSelectedItem().toString();
        filterEntity.setType(strType);
        String mode = comboBoxForMode.getSelectedItem().toString();
        filterEntity.setMode(mode);
        filterEntity.setModeParam(textFldModeParam.getText());
        String startDate = textFieldFilterStartDate.getText();
        String strStartDate = startDate.replace('/', '-');
        filterEntity.setStartDate(strStartDate);
        filterEntity.setNextLaunchDate(textFldFltLaunchDate.getText());

        logger.info("************************************");
        logger.info("NAME: " + textFieldFltrName.getText());
        logger.info("REVISION: " + textFieldFltRev.getText());
        logger.info("PROJECT: " + textFieldFltProject.getText());
        logger.info("DESIGNATION: " + textFieldFltDesignation.getText());
        logger.info("TYPE: " + strType);
        logger.info("MODE: " + mode);
        logger.info("MODE PARAM: " + textFldModeParam.getText());
        logger.info("START DATE:" + textFieldFilterStartDate.getText());
        logger.info("LAUNCH DATE:" + textFldFltLaunchDate.getText());

        logger.info("END getFilterModelData()");
        return filterEntity;
    }

    public void disposePanel(int iExitValue) {
        dispose();
        PSAFilterUIController.getFilterDBHandler().closeDatabaseConnection();
        System.exit(iExitValue);
    }

    public void dummy() {
        logger.info("START dummy()");
    }

    void clearListForUIDisplay() {
        logger.info("START refereshListForUIDisplay()");
        PSAFilterUIController.arrayListfilters.clear();
        PSAFilterUIController.filterDataModel.clearList();
        PSAFilterUIController.scrollPaneTable.setViewportView(null);
        PSAFilterUIController.scrollPaneTable.repaint();
        logger.info("END refereshListForUIDisplay()");
    }

    /**
     * It display date picker dialog and set selected date to text field.
     * 
     * @param event : ActionEvent
     */
    protected void btnPSAFilterStartDateActionPerformed(ActionEvent event)
    {
        PSADatePicker datePicker = new PSADatePicker(null, PSAFilterUIController.objLanguageHandler);
        String selectedDate = datePicker.GetDateSelected();
        String strStartDate = null;

        // Setting Start Date field
        if (selectedDate != null && selectedDate.length() > 0)
        {
            LaunchDate launchDate = new LaunchDate();
            Object[] tempObject = UtilityFunctions.String_to_date_Conversion(selectedDate, "dd/MM/yyyy", PSAFilterUIController.objLanguageHandler);
            Date dateSelStartDate = (Date) tempObject[1];
            if (dateSelStartDate != null)
            {
                // Current Date
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date dateToday = calendar.getTime();
                int iDateComparison = dateSelStartDate.compareTo(dateToday);
                if (iDateComparison < 0)
                {
                    // Start Date can not be a previous date.
                    String error_msg = PSAFilterUIController.objLanguageHandler.Getlabel("Error.InvalidStartDatePrev",
                            "Start Date can not be a previous date.");
                    PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, error_msg, PSAFilterUIController.objLanguageHandler);
                }
                else
                {
                    strStartDate = UtilityFunctions.Date_to_String_Conversion(dateSelStartDate, "yyyy-MM-dd");
                    textFieldFilterStartDate.setText(strStartDate);

                    setNextLaunchDate(strStartDate);
                }
            }
        }
    }

    /**
     * It display date picker dialog and set selected date to text field.
     * 
     * @param strStartDate : String strStartDate
     */
    private void setNextLaunchDate(String strStartDate) {
        LaunchDate launchDate = new LaunchDate();
        if (!strStartDate.isEmpty()) {
            // Setting Next Launch Date field
            String strNextLaunchDate = "";
            if (null == selected)
            {
                // Defaulting to DAILY - Setting Start Date as Execution Date i.e. initial Next Launch Date  
                strNextLaunchDate = strStartDate;
                logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] NULL = NEXT launch date is: " + strNextLaunchDate);
            }
            else
            {
                logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] ELSE NULL = selected : " + selected);
                // Daily
                if ("DAILY".equals(selected.toString()))
                {
                    // Setting Start Date as Execution Date i.e. initial Next Launch Date  
                    strNextLaunchDate = strStartDate;
                }
                if ("WEEKLY".equals(selected.toString())) {
                    List<String> addArrayList = weekPanel.getModeParamList();
                    logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] WEEKLY = Calulating NEXT launch date with \n Start Date : " + strStartDate + " \n WEEKLY selections : " + addArrayList);
                    strNextLaunchDate = launchDate.nextWeekDayLaunchDate(addArrayList, strStartDate);
                    logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] WEEKLY = NEXT launch date is: " + strNextLaunchDate);
                }
                if ("MONTHLY".equals(selected.toString())) {
                    List<String> addArrayList = monthPanel.getModeParamList();
                    logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] MONTHLY = Calulating NEXT launch date with \n Start Date : " + strStartDate + " \n MONTHLY selections : " + addArrayList);
                    strNextLaunchDate = launchDate.nextWeekDayLaunchDate(addArrayList, strStartDate);
                    logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] MONTHLY = NEXT launch date is: " + strNextLaunchDate);
                }
            }
            textFldFltLaunchDate.setText(strNextLaunchDate);
            logger.info("[PSAFilterPropertiesPanel:setNextLaunchDate] NEXT launch date is: " + strNextLaunchDate);
        }
    }

    /**
     * It display date picker dialog and set selected date to text field.
     * 
     * @param strStartDate : String strStartDate
     */
    private void checkModeParam() {
        String strModeParamFieldValue = textFldModeParam.getText();
        if (strModeParamFieldValue.isEmpty()) {
            String errormsg = PSAFilterUIController.objLanguageHandler.Getlabel("Error.InvalidModeParamVal",
                    "Mode Parameter value is mandatory for WEEKLY and MONTHLY mode.");
            PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, errormsg, PSAFilterUIController.objLanguageHandler);
        }
    }
    
}

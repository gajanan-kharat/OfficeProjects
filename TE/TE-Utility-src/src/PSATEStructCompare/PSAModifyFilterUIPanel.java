/*
 * Creation : May 31, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.psa.tools.PSAErrorHandler;

public class PSAModifyFilterUIPanel extends JDialog implements PSAFilterPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("PSAModifyFilterUIPanel");
    protected static final String DATE_SEPERATOR = "-";
    private JPanel panelMain;
    private JPanel panelFilter;
    private JPanel panelTableData;
    private JPanel panelNextLaunchDate;
    private JPanel panelStartDate;
    private JPanel panelFilterName;
    private JPanel panelRevision;
    private JPanel panelProject;
    private JPanel panelDesignation;
    private JPanel panelType;
    private JPanel panelMode;
    private JPanel panelButton;
    private JPanel panelCreator;
    private JLabel labelLaunchMMYYSlash;
    private JLabel labelLaunchDDMMSlash;
    private JTextField textFieldFilterName;
    private JTextField textFieldRevision;
    private JTextField textFieldProject;
    private JTextField textFieldDesignation;
    private JComboBox comboBoxFilterType;
    private JComboBox comboBoxStatus;
    private JTextField textFieldCreator;
    private JTextField textFieldLaunchMonth;
    private JTextField textFieldStartMonth;
    private JTextField textFieldLaunchDay;
    private JTextField textFieldStartDay;
    private JTextField textFieldLaunchYear;
    private JTextField textFieldStartYear;
    private JButton btnModify;
    private JButton btnView;
    private JButton btnDelete;
    private String strStartDateSearchFilter = PSAFilterConstant.DATE_SEARCH_PATTERN;
    private String strNextLaunchDateSearchFilter = PSAFilterConstant.DATE_SEARCH_PATTERN;
    private int[] tableColWidth = { 100, 70, 60, 100, 100, 110, 80, 130, 130 };

    private List<PSAFilterModel> arrayListFilters;

    public PSAModifyFilterUIPanel() {
        logger.info("PSAModifyFilterUIPanel : START Constructor of the class");
        initGUI();
        logger.info("PSAModifyFilterUIPanel : END constructor of the class");
    }

    private void initGUI() 
    {
        System.out.println("[PSAModifyFilterUIPanel::initGUI] START");
        
        // Initialize Data into table.
        initializeData();
        
        //Launch Filter Table, if filter data populated 
        if(!PSAFilterUIController.arrayListfilters.isEmpty())
        {
            // Main Panel
            panelMain = new JPanel();
            BorderLayout panelMainLayout = new BorderLayout();
            panelMain.setLayout(panelMainLayout);
            getContentPane().add(panelMain, BorderLayout.CENTER);

            // Search Field Panel at Top
            createFilterPanel();
            // Filter Table Panel
            createTablePanel();
            // Buttons at Bottom
            createButtonPanel();

            this.setSize(900, 471);
            this.setResizable(false);
            this.setModal(PSAFilterConstant.TRUE_VAL);
            setLocation(PSAUtilityFunctions.getCenterPosition(this));
            this.setVisible(true);
            checkValidateButton();
            checkViewButtonStatus();
            checkViewDeleteStatus();
        }
        System.out.println("[PSAModifyFilterUIPanel::initGUI] END");
    }


    /**
     * This function is used to initialize data.
     * 
     * @throws PSAErrorHandler
     */
    private void initializeData()
    {
        System.out.println("[PSAModifyFilterUIPanel::initializeData] START");
        // Read the filter list.
        try {
            List<PSAFilterModel> filterList = PSAFilterUIController.getFilterDBHandler().getFilterList();
            if(null != filterList)
            {
                for (PSAFilterModel psaFilterModel : filterList) {
                    PSAFilterUIController.arrayListfilters.add(psaFilterModel);
                }
                if (PSAFilterUIController.arrayListfilters != null) {
                    PSAFilterUIController.filterDataModel.refreshList(PSAFilterUIController.arrayListfilters);
                    PSAFilterUIController.scrollPaneTable.setViewportView(PSAFilterUIController.tableFilter);
                }
            }
        } catch (PSAErrorHandler e) {
            JOptionPane.showMessageDialog(null, "Error occured while retrieving filters from the data base!!!", "Filter retrieving Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.info("Exception occured while retrieving data from Data base!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occured while retrieving filters from the data base!!!", "Filter retrieving Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.info("Exception occured while retrieving data from Data base!");
        }
        System.out.println("[PSAModifyFilterUIPanel::initializeData] END");
    }

    /**
     * ---------------- Search Field Panel at Top ---------------------
     * It creates filter Text Fields.
     */
    private void createFilterPanel() {
        // Create filter panel.
        panelFilter = new JPanel();
        GridBagLayout panelFilterLayout = new GridBagLayout();
        panelFilterLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
        panelFilterLayout.rowHeights = new int[] { 12, 31, -11, 7 };
        panelFilterLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
        panelFilterLayout.columnWidths = new int[] { 5, 100, 70, 60, 100, 60, 60, 80, 130, 130 };
        panelFilter.setLayout(panelFilterLayout);
        panelMain.add(panelFilter, BorderLayout.NORTH);
        panelFilter.setPreferredSize(new java.awt.Dimension(863, 58));
        panelFilter.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        
        // Filter Name Panel.
        //---------------------------------------------------------
        // Column 1 = FILTER_NAME
        //---------------------------------------------------------
        {
            panelFilterName = new JPanel();
            // 1 = Column 1
            panelFilter.add(panelFilterName,
                    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout gbFilterNameLayout = new GridBagLayout();
            gbFilterNameLayout.columnWidths = new int[] { 7 };
            gbFilterNameLayout.rowHeights = new int[] { 7 };
            gbFilterNameLayout.columnWeights = new double[] { 0.1 };
            gbFilterNameLayout.rowWeights = new double[] { 0.1 };
            panelFilterName.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelFilterName.setLayout(gbFilterNameLayout);

            // Filter Name TextField
            textFieldFilterName = new JTextField();
            panelFilterName.add(textFieldFilterName,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 5), 0, 0));
            textFieldFilterName.setPreferredSize(new java.awt.Dimension(80, 24));
            textFieldFilterName.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldFilterName.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filteredByValues(e);
                }
            });

        }
        
        // Filter Revision Panel
        //---------------------------------------------------------
        // Column 2 = FILTER_REVISION
        //---------------------------------------------------------
        {
            panelRevision = new JPanel();
            // 2 = Column 2
            panelFilter.add(panelRevision,
                    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout gbFilterRevLayout = new GridBagLayout();
            gbFilterRevLayout.columnWidths = new int[] { 7 };
            gbFilterRevLayout.rowHeights = new int[] { 7 };
            gbFilterRevLayout.columnWeights = new double[] { 0.1 };
            gbFilterRevLayout.rowWeights = new double[] { 0.1 };
            panelRevision.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelRevision.setLayout(gbFilterRevLayout);

            // Filter Revision TextField
            textFieldRevision = new JTextField();
            panelRevision.add(textFieldRevision,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            textFieldRevision.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldRevision.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filteredByValues(e);
                }
            });

        }
        
        //---------------------------------------------------------
        // Column 3 = FILTER_PROJECT
        //---------------------------------------------------------
        {
            panelProject = new JPanel();
            // 3 = Column 3
            panelFilter.add(panelProject,
                    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout gbFilterProjLayout = new GridBagLayout();
            gbFilterProjLayout.columnWidths = new int[] { 7 };
            gbFilterProjLayout.rowHeights = new int[] { 7 };
            gbFilterProjLayout.columnWeights = new double[] { 0.1 };
            gbFilterProjLayout.rowWeights = new double[] { 0.1 };
            panelProject.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelProject.setLayout(gbFilterProjLayout);

            // Filter Project TextField
            textFieldProject = new JTextField();
            panelProject.add(textFieldProject,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            textFieldProject.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldProject.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filteredByValues(e);
                }
            });

        }
        
        //---------------------------------------------------------
        // Column 4 = FILTER_DESIGNATION
        //---------------------------------------------------------
        {
            panelDesignation = new JPanel();
            // 4 = Column 4
            panelFilter.add(panelDesignation,
                    new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout gbFilterDesigLayout = new GridBagLayout();
            gbFilterDesigLayout.columnWidths = new int[] { 7 };
            gbFilterDesigLayout.rowHeights = new int[] { 7 };
            gbFilterDesigLayout.columnWeights = new double[] { 0.1 };
            gbFilterDesigLayout.rowWeights = new double[] { 0.1 };
            panelDesignation.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelDesignation.setLayout(gbFilterDesigLayout);

            // Filter Project TextField
            textFieldDesignation = new JTextField();
            panelDesignation.add(textFieldDesignation,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            textFieldDesignation.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldDesignation.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filteredByValues(e);
                }
            });

        }
        
        //---------------------------------------------------------
        // Column 5 = FILTER_TYPE
        //---------------------------------------------------------
        {
            panelType = new JPanel();
            // 5 = Column 5
            panelFilter.add(panelType,
                    new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout gbFilterTypeLayout = new GridBagLayout();
            gbFilterTypeLayout.columnWidths = new int[] { 7 };
            gbFilterTypeLayout.rowHeights = new int[] { 7 };
            gbFilterTypeLayout.columnWeights = new double[] { 0.1 };
            gbFilterTypeLayout.rowWeights = new double[] { 0.1 };
            panelType.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelType.setLayout(gbFilterTypeLayout);

            // Filter Type ComboBox
            DefaultComboBoxModel comboBoxModelFilterType = new DefaultComboBoxModel();
            for (FILTER_TYPE filterType : FILTER_TYPE.values()) {
                String strFilterTypeVal = null;
                if(filterType.equals(FILTER_TYPE.ISO_CIBLE))
                {
                    strFilterTypeVal = new String("ISO/CIBLE");
                }
                else
                {
                    strFilterTypeVal = new String(PSAFilterConstant.EMPTY_STRING + filterType);
                }
                comboBoxModelFilterType.addElement(strFilterTypeVal);
            }

            comboBoxFilterType = new JComboBox();
            panelType.add(comboBoxFilterType,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 5), 0, 0));
            comboBoxFilterType.setModel(comboBoxModelFilterType);
            comboBoxFilterType.setSelectedIndex(0);
            comboBoxFilterType.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    filteredByValues(null);
                }
            });

        }

        // Panel Filter Mode
        //---------------------------------------------------------
        // Column 6 = MODE
        //---------------------------------------------------------
        {
            panelMode = new JPanel();
            // 6 = Column 6
            panelFilter.add(panelMode,
                    new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            GridBagLayout jPanel2Layout1 = new GridBagLayout();
            jPanel2Layout1.columnWidths = new int[] { 7 };
            jPanel2Layout1.rowHeights = new int[] { 7 };
            jPanel2Layout1.columnWeights = new double[] { 0.1 };
            jPanel2Layout1.rowWeights = new double[] { 0.1 };
            panelMode.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelMode.setLayout(jPanel2Layout1);

            // Filter MODE ComboBox
            DefaultComboBoxModel comboBoxFilterModel = new DefaultComboBoxModel();
            for (MODE mode : MODE.values()) {
                String modeValue = new String(PSAFilterConstant.EMPTY_STRING + mode);
                comboBoxFilterModel.addElement(modeValue);
            }

            comboBoxStatus = new JComboBox();
            panelMode.add(comboBoxStatus,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 5), 0, 0));
            comboBoxStatus.setModel(comboBoxFilterModel);
            comboBoxStatus.setSelectedIndex(0);
            comboBoxStatus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    filteredByValues(null);
                }
            });

        }

        // Filter Access Panel
        //---------------------------------------------------------
        // Column 7 = USER
        //---------------------------------------------------------
        {
            panelCreator = new JPanel();
            GridBagLayout panelCreatorLayout = new GridBagLayout();
            // 7 = Column 7
            panelFilter.add(panelCreator,
                    new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            panelCreator.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            panelCreatorLayout.rowWeights = new double[] { 0.1 };
            panelCreatorLayout.rowHeights = new int[] { 7 };
            panelCreatorLayout.columnWeights = new double[] { 0.1 };
            panelCreatorLayout.columnWidths = new int[] { 7 };
            panelCreator.setLayout(panelCreatorLayout);

            // Owner TextField m_TextFieldOwner
            textFieldCreator = new JTextField();
            panelCreator.add(textFieldCreator,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 5), 0, 0));
            textFieldCreator.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldCreator.setPreferredSize(new java.awt.Dimension(67, 46));
            textFieldCreator.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    filteredByValues(e);
                }
            });
        }
        
        // Start date panel
        //---------------------------------------------------------
        // Column 8 = STARTDATE yyyy-MM-dd
        //---------------------------------------------------------
        {
            panelStartDate = new JPanel();
            FlowLayout panelEndDateLayout = new FlowLayout();
            panelStartDate.setLayout(panelEndDateLayout);
            // 8 = Column 8
            panelFilter.add(panelStartDate,
                    new GridBagConstraints(8, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            panelStartDate.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));

            // Start Day TextField
            textFieldStartDay = new JTextField();

            textFieldStartDay.setPreferredSize(new java.awt.Dimension(24, 24));
            textFieldStartDay.setDocument(new FixedLengthDocument(2, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldStartDay.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldStartDay.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strStartDateSearchFilter = textFieldStartYear.getText() + DATE_SEPERATOR + textFieldStartMonth.getText() + DATE_SEPERATOR
                            + textFieldStartDay.getText();
                    filteredByValues(e);
                }
            });

            // Start DD-MM hyphen('-') Label
            labelLaunchDDMMSlash = new JLabel();

            labelLaunchDDMMSlash.setText("-");
            labelLaunchDDMMSlash.setFont(new java.awt.Font(PSAFilterConstant.TAHOMA, 1, 16));
            // Start Month TextField
            textFieldStartMonth = new JTextField();

            textFieldStartMonth.setPreferredSize(new java.awt.Dimension(26, 24));
            textFieldStartMonth.setDocument(new FixedLengthDocument(2, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldStartMonth.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldStartMonth.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strStartDateSearchFilter = textFieldStartYear.getText() + DATE_SEPERATOR + textFieldStartMonth.getText() + DATE_SEPERATOR
                            + textFieldStartDay.getText();
                    filteredByValues(e);
                }
            });

            // Start MM-YY hyphen('-') Label
            labelLaunchMMYYSlash = new JLabel();

            labelLaunchMMYYSlash.setText("-");
            labelLaunchMMYYSlash.setFont(new java.awt.Font(PSAFilterConstant.TAHOMA, 1, 16));
            
            // Start Year TextField
            textFieldStartYear = new JTextField();

            textFieldStartYear.setPreferredSize(new java.awt.Dimension(39, 24));
            textFieldStartYear.setDocument(new FixedLengthDocument(4, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldStartYear.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldStartYear.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strStartDateSearchFilter = textFieldStartYear.getText() + DATE_SEPERATOR + textFieldStartMonth.getText() + DATE_SEPERATOR
                            + textFieldStartDay.getText();
                    filteredByValues(e);
                }
            });

            panelStartDate.add(textFieldStartYear);
            panelStartDate.add(labelLaunchMMYYSlash);
            panelStartDate.add(textFieldStartMonth);
            panelStartDate.add(labelLaunchDDMMSlash);
            panelStartDate.add(textFieldStartDay);

        }

        // Next Launch Date Panel.
        //---------------------------------------------------------
        // Column 9 = NEXTLAUNCHDATE yyyy-MM-dd
        //---------------------------------------------------------
        {
            panelNextLaunchDate = new JPanel();
            // 9 = Column 9
            FlowLayout panelLaunchDateLayout = new FlowLayout();
            panelNextLaunchDate.setLayout(panelLaunchDateLayout);
            panelFilter.add(panelNextLaunchDate,
                    new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            panelNextLaunchDate.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));

            // Launch Day TextField
            textFieldLaunchDay = new JTextField();
            textFieldLaunchDay.setPreferredSize(new java.awt.Dimension(24, 24));
            textFieldLaunchDay.setDocument(new FixedLengthDocument(2, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldLaunchDay.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldLaunchDay.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strNextLaunchDateSearchFilter = textFieldLaunchYear.getText() + DATE_SEPERATOR + textFieldLaunchMonth.getText() + DATE_SEPERATOR
                            + textFieldLaunchDay.getText();
                    filteredByValues(e);
                }
            });

            // Launch DDMM Slash Label
            labelLaunchDDMMSlash = new JLabel();

            labelLaunchDDMMSlash.setText("-");
            labelLaunchDDMMSlash.setFont(new java.awt.Font(PSAFilterConstant.TAHOMA, 1, 16));
            // Launch Month TextField
            textFieldLaunchMonth = new JTextField();

            textFieldLaunchMonth.setPreferredSize(new java.awt.Dimension(26, 24));
            textFieldLaunchMonth.setDocument(new FixedLengthDocument(2, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldLaunchMonth.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldLaunchMonth.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strNextLaunchDateSearchFilter = textFieldLaunchYear.getText() + DATE_SEPERATOR + textFieldLaunchMonth.getText() + DATE_SEPERATOR
                            + textFieldLaunchDay.getText();
                    filteredByValues(e);
                }
            });

            // Launch MMYY Slash Label
            labelLaunchMMYYSlash = new JLabel();
            labelLaunchMMYYSlash.setText("-");
            labelLaunchMMYYSlash.setFont(new java.awt.Font(PSAFilterConstant.TAHOMA, 1, 16));
            // Launch Year TextField
            textFieldLaunchYear = new JTextField();

            textFieldLaunchYear.setPreferredSize(new java.awt.Dimension(39, 24));
            textFieldLaunchYear.setDocument(new FixedLengthDocument(4, FixedLengthDocument.NUMERIC_AND_ASTRIC));
            textFieldLaunchYear.setText(PSAFilterConstant.STAR_SYMBOL);
            textFieldLaunchYear.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    strNextLaunchDateSearchFilter = textFieldLaunchYear.getText() + DATE_SEPERATOR + textFieldLaunchMonth.getText() + DATE_SEPERATOR
                            + textFieldLaunchDay.getText();
                    filteredByValues(e);
                }
            });

            panelNextLaunchDate.add(textFieldLaunchYear);
            panelNextLaunchDate.add(labelLaunchMMYYSlash);
            panelNextLaunchDate.add(textFieldLaunchMonth);
            panelNextLaunchDate.add(labelLaunchDDMMSlash);
            panelNextLaunchDate.add(textFieldLaunchDay);
        }

    }

    public void dummy() {
        logger.info("START dummy()");
    }

    private void createTablePanel()
    {
        // Create table panel
        panelTableData = new JPanel();
        BorderLayout panelTableDataLayout = new BorderLayout();
        panelTableData.setLayout(panelTableDataLayout);
        panelMain.add(panelTableData, BorderLayout.CENTER);
        panelTableData.setPreferredSize(new java.awt.Dimension(881, 343));
        panelTableData.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Table Scroll pane
        panelTableData.add(PSAFilterUIController.scrollPaneTable, BorderLayout.CENTER);
        PSAFilterUIController.scrollPaneTable.setPreferredSize(new java.awt.Dimension(855, 314));
        PSAFilterUIController.scrollPaneTable.setFont(new java.awt.Font("Dialog", 0, 16));
        PSAFilterUIController.scrollPaneTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create table
        PSAFilterUIController.tableFilter.setModel(PSAFilterUIController.filterDataModel);
        PSAFilterUIController.scrollPaneTable.setViewportView(PSAFilterUIController.tableFilter);

        // Set the cell alignment.
        DefaultTableCellRenderer filterTableCellRenderer = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 0 || column == 1) {
                    setToolTipText(value.toString());
                } else {
                    setToolTipText(null);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        PSAFilterUIController.tableFilter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Column setting.
        for (int index = 0; index < tableColWidth.length; index++)
        {
            TableColumn col = PSAFilterUIController.tableFilter.getColumnModel().getColumn(index);
            col.setPreferredWidth(tableColWidth[index]);
            col.setResizable(false);
            col.setCellRenderer(filterTableCellRenderer);
        }
        PSAFilterUIController.tableFilter.getMouseListeners();
        MouseListener[] mouseListeners = PSAFilterUIController.tableFilter.getMouseListeners();
        if (mouseListeners.length > 2) {
            PSAFilterUIController.tableFilter.removeMouseListener(mouseListeners[2]);
        }
        // for (MouseListener mouseListener : mouseListeners) {
        // if (mouseListener instanceof PSAModifyFilterUIPanel) {
        // PSAFilterUIController.tableFilter.removeMouseListener(mouseListener);
        // }
        // }
        PSAFilterUIController.tableFilter.getTableHeader().setReorderingAllowed(false);
        
        if (PSAFilterUIController.getFilterAction().equals(ACTION.DELETE_FILTER))
        {
            PSAFilterUIController.tableFilter.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        else
        {
            PSAFilterUIController.tableFilter.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        MouseAdapter modifyFilterUIPanelListner = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int mouseClickCount = mouseEvent.getClickCount();
                if (mouseClickCount == 1) {
                    PSAFilterUIController.selectedFilter = PSAFilterUIController.filterDataModel
                            .getSelectedFilter(PSAFilterUIController.tableFilter.getSelectedRow());
                    checkValidateButton();
                    checkViewButtonStatus();
                    checkViewDeleteStatus();
                } else if (mouseClickCount == 2) {
                    // PSAFilterUIController.selectedFilter = PSAFilterUIController.filterDataModel
                    // .getSelectedFilter(PSAFilterUIController.tableFilter.getSelectedRow());
                    logger.info("********** Double click !!!!!!!!");
                    if (!PSAFilterUIController.getFilterAction().equals(ACTION.DELETE_FILTER))
                        showFilterSummaryPanel();
                    else
                        logger.info("Delete the selected row from the data base!");
                }
            }
        };

        PSAFilterUIController.tableFilter.addMouseListener(modifyFilterUIPanelListner);
        // PSAFilterUIController.tableFilter.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(MouseEvent mouseEvent) {
        // int mouseClickCount = mouseEvent.getClickCount();
        // if (mouseClickCount == 1) {
        // PSAFilterUIController.selectedFilter = PSAFilterUIController.filterDataModel
        // .getSelectedFilter(PSAFilterUIController.tableFilter.getSelectedRow());
        // checkValidateButton();
        // checkViewButtonStatus();
        // checkViewDeleteStatus();
        // } else if (mouseClickCount == 2) {
        // // PSAFilterUIController.selectedFilter = PSAFilterUIController.filterDataModel
        // // .getSelectedFilter(PSAFilterUIController.tableFilter.getSelectedRow());
        // logger.info("********** Double click !!!!!!!!");
        // if (!PSAFilterUIController.getFilterAction().equals(ACTION.DELETE_FILTER))
        // showFilterSummaryPanel();
        // else
        // logger.info("Delete the selected row from the data base!");
        // }
        // }
        // });
    }
    
    /**
     * This function is used to display Filter summary panel.
     */
    private void showFilterSummaryPanel() {
        int rowIndex = PSAFilterUIController.tableFilter.getSelectedRow();

        if (rowIndex != -1) {
            // Get selected filter.
            PSAFilterModel psaFilter = PSAFilterUIController.filterDataModel.getSelectedFilter(rowIndex);
            logger.info("Selected row data: " + psaFilter.getName() + "  " + psaFilter.getRevision() + "  "
                    + psaFilter.getProject() + "  " + psaFilter.getDesignation() + "  " + psaFilter.getType() + "  " 
                    + psaFilter.getMode() + "  " + psaFilter.getUser() + "  " + psaFilter.getStartDate() + "  " + psaFilter.getNextLaunchDate());
            try {
                setCursor(WAIT_CURSOR);
                // Get the filter details.
                // TODO:
                // PSAStructCompareUIController.m_DbHandler.readFilterValues(PSAStructCompareUIController.m_SelectedFilter);
                setCursor(DEFAULT_CURSOR);
                // ACTION summaryAction = ACTION.FILTER_SUMMARY;
                // PSAFilterSubSummaryPanel m_FilterSummaryPanel = new PSAFilterSubSummaryPanel(this, summaryAction);

                PSAFilterPropertiesPanel filterPropertiesPanel = new PSAFilterPropertiesPanel(psaFilter);
                filterPropertiesPanel.dummy();
            } catch (Exception e) {
                setCursor(DEFAULT_CURSOR);
                logger.info("Exception occured: " + e);
            }
        }
    }

    /**
     * This function is used to filter data.
     */
    private void filteredByValues(KeyEvent ae) {
        Map<Integer, String> columnFilter = new HashMap<Integer, String>();
        int filterCount = 0;
        
        //TO REVIEW --> 
        // Remove the previous selection.
        int[] selectedRows = PSAFilterUIController.tableFilter.getSelectedRows();
        try
        {
            if (selectedRows.length != 0)
            {
                PSAFilterUIController.tableFilter.removeRowSelectionInterval(selectedRows[0], selectedRows[selectedRows.length - 1]);
            }
        } catch (Exception e) {
            logger.info("Invalid Row selection: " + e);
        }

        // Read value for filter.
        String[] strValues = new String[9];
        
        //FILTER_NAME
        strValues[0] = textFieldFilterName.getText().trim();

        //FILTER_REVISION
        strValues[1] = textFieldRevision.getText().trim();

        //FILTER_PROJECT
        strValues[2] = textFieldProject.getText().trim();
        
        //FILTER_DESIGNATION
        strValues[3] = textFieldDesignation.getText().trim();
        
        //FILTER_TYPE
        if (FILTER_TYPE.ALL.toString().equals(comboBoxFilterType.getSelectedItem().toString()))
        {
            strValues[4] = PSAFilterConstant.STAR_SYMBOL;
        }
        else
        {
            strValues[4] = (String) comboBoxFilterType.getSelectedItem();
        }
        
        //FILTER_MODE
        if (MODE.ALL.toString().equals(comboBoxStatus.getSelectedItem().toString()))
        {
            strValues[5] = PSAFilterConstant.STAR_SYMBOL;
        }
        else
        {
            strValues[5] = (String) comboBoxStatus.getSelectedItem();
        }
        
        //FILTER_CREATOR
        strValues[6] = textFieldCreator.getText().trim();
        
        if (strStartDateSearchFilter.equals(PSAFilterConstant.DATE_SEARCH_PATTERN))
            strStartDateSearchFilter = PSAFilterConstant.STAR_SYMBOL;
        if (PSAFilterConstant.DATE_SEARCH_PATTERN.equals(strNextLaunchDateSearchFilter))
            strNextLaunchDateSearchFilter = PSAFilterConstant.STAR_SYMBOL;
        
        //FILTER_START_DATE
        strValues[7] = strStartDateSearchFilter;
        
        //FILTER_NEXT_LAUNCH_DATE
        strValues[8] = strNextLaunchDateSearchFilter;

        for (int index = 0; index < strValues.length; index++)
        {
            String strValue = strValues[index];
            if (!strValue.equals(PSAFilterConstant.STAR_SYMBOL)) {
                // Put the value for filter in hash map.
                columnFilter.put(index, strValue);
                filterCount++;
            }
        }
        
        // Filter the list.
        if (filterCount > 0)
            PSAFilterUIController.filterDataModel.filterList(columnFilter);
        else
            PSAFilterUIController.filterDataModel.setFilterFlag(false);
        
        PSAFilterUIController.scrollPaneTable.setViewportView(PSAFilterUIController.tableFilter);
        checkValidateButton();
        checkViewButtonStatus();
        checkViewDeleteStatus();
    }

    /**
     * It creates buttons.
     */
    private void createButtonPanel() {
        // Button Panel
        logger.info("START createButtonPanel()");
        panelButton = new JPanel();
        GridBagLayout panelButtonLayout = new GridBagLayout();
        panelButtonLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
        panelButtonLayout.rowHeights = new int[] { 20, 22, 14 };
        panelButtonLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
        panelButtonLayout.columnWidths = new int[] { 180, 90, 90, 200, 90, 90, 7 };
        panelButton.setLayout(panelButtonLayout);
        panelMain.add(panelButton, BorderLayout.SOUTH);
        panelButton.setPreferredSize(new java.awt.Dimension(932, 67));

        // Cancel Button
        JButton btnCancel = new JButton();
        panelButton.add(btnCancel,
                new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                logger.info("CANCEL button pressed!!!");
                setVisible(PSAFilterConstant.FALSE_VAL);
                PSAFilterUIController.setFilterAction(null);
            }
        });
        
        // Modify Button
        btnModify = new JButton();
        btnModify.setText("MODIFY");
        btnModify.setEnabled(PSAFilterConstant.FALSE_VAL);
        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                logger.info("********MODIFY button pressed*********");
                showFilterSummaryPanel();
            }
        });

        // View button
        btnView = new JButton();
        btnView.setText("VIEW");
        btnView.setEnabled(PSAFilterConstant.FALSE_VAL);
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("********VIEW button pressed*********");
                showFilterSummaryPanel();
            }
        });
        
        // Delete button
        btnDelete = new JButton();
        btnDelete.setText("DELETE");
        btnDelete.setEnabled(PSAFilterConstant.FALSE_VAL);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("********DELETE button pressed*********");
                
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(panelMain, 
                        PSAFilterUIController.objLanguageHandler.Getlabel("Message.ConfirmDeletion","Do you want to delete the selected rows?"), 
                        PSAFilterUIController.objLanguageHandler.Getlabel("Title.ConfirmDeletion","Delete Confirmation Dialog"),
                        dialogButton);
                if (dialogResult == 0)
                {
                    try
                    {
                        //deleteSelectedRow();
                        deleteSelectedRows();
                    }
                    catch(ArrayIndexOutOfBoundsException ex){
                        logger.info("Exception occured while deleting row...");
                    }
                    logger.info("USER selected Yes option");
                    if(PSAFilterUIController.arrayListfilters.isEmpty())
                    {
                        btnDelete.setEnabled(PSAFilterConstant.FALSE_VAL);
                    }
                } else {
                    logger.info("USER selected No Option");
                }
            }
        });
        logger.info("======> ACTION is: " + PSAFilterUIController.getFilterAction());
        if (PSAFilterUIController.getFilterAction().equals(ACTION.MODIFY_FILTER)) {
            panelButton.add(btnModify, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));
            this.setTitle(PSAFilterUIController.objLanguageHandler.Getlabel("Title.ModifySubscription","Modify a Subscription"));
        }
        if (PSAFilterUIController.getFilterAction().equals(ACTION.VIEW_FILTER)) {
            panelButton.add(btnView, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));
            this.setTitle(PSAFilterUIController.objLanguageHandler.Getlabel("Title.ViewSubscription","View a Subscription"));
        }
        if (PSAFilterUIController.getFilterAction().equals(ACTION.DELETE_FILTER)) {
            panelButton.add(btnDelete, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));
            this.setTitle(PSAFilterUIController.objLanguageHandler.Getlabel("Title.DeleteSubscriptions","Delete Subscriptions"));
        }

        logger.info("END createButtonPanel()");

    }


    /**
     * This function is used to handle callback on deletion of selected row(s).
     */
    private void deleteSelectedRows() {
        logger.info("[PSAModifyFilterUIPanel:deleteSelectedRows] START");
        List<PSAFilterModel> listSelectedFilters = new ArrayList<PSAFilterModel>();
        int[] selectedRows = PSAFilterUIController.tableFilter.getSelectedRows();
        listSelectedFilters = PSAFilterUIController.filterDataModel.getListOfSelectedFilters(selectedRows);
        //New method for removeRowSelectionInterval in tableFilter
        PSAFilterUIController.tableFilter.removeRowSelectionInterval(selectedRows[0], selectedRows[selectedRows.length - 1]);//instead of Interval remvela has to be filter removeal
        
        String [] strSelectedPSAFilterIDs = new String[listSelectedFilters.size()];
        // Deleting Selected Filters
        for(int i = 0; i < listSelectedFilters.size(); i++)
        {
            PSAFilterModel curPSAFilterModel = listSelectedFilters.get(i);
            PSAFilterUIController.filterDataModel.removeElement(curPSAFilterModel);
            PSAFilterUIController.scrollPaneTable.setViewportView(PSAFilterUIController.tableFilter);
            strSelectedPSAFilterIDs[i] = curPSAFilterModel.getId();
        }
        logger.info("List of to be deleted PSA Filter IDs : " + strSelectedPSAFilterIDs);
        
        try
        {
            PSAFilterUIController.getFilterDBHandler().deleteFilters(strSelectedPSAFilterIDs);
            JOptionPane.showMessageDialog(null, 
                    PSAFilterUIController.objLanguageHandler.Getlabel("Message.FilterDeletionSuccess","Selected filters deleted successfully in the data base!"),
                    PSAFilterUIController.objLanguageHandler.Getlabel("Title.FilterDeletionSuccess","Success in filter deletion"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (PSAErrorHandler e)
        {
            logger.info("Exception occured while deleting row...");
            JOptionPane.showMessageDialog(null,
                    PSAFilterUIController.objLanguageHandler.Getlabel("Error.FilterDeleteFailed","Failed to delete either of the selected filter in the data base!"),
                    PSAFilterUIController.objLanguageHandler.Getlabel("Title.FilterDeleteFailed","Failed in filter deletion"),
                    JOptionPane.ERROR_MESSAGE);
        }
        

        logger.info("[PSAModifyFilterUIPanel:deleteSelectedRows] END");
    }

    /**
     * This function is used to enable/disable validate button.
     */
    private void checkValidateButton() {
        btnModify.setEnabled((PSAFilterUIController.tableFilter.getSelectedRowCount() == 0) ? false : true);
    }

    /**
     * This function is used to enable/disable View button.
     */
    private void checkViewButtonStatus() {
        btnView.setEnabled((PSAFilterUIController.tableFilter.getSelectedRowCount() == 0) ? false : true);
    }

    /**
     * This function is used to enable/disable Delete button.
     */
    private void checkViewDeleteStatus() {
        btnDelete.setEnabled((PSAFilterUIController.tableFilter.getSelectedRowCount() == 0) ? false : true);
    }
}

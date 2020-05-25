/*
 * Creation : May 29, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.psa.PSATEStructCompare.PSAStructUITools.USER_ACCESS;

public class PSAFilterMainUIDialog extends JDialog implements PSAFilterPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("PSAStructCompareUI");
    GridBagConstraints gridBagConstraints;
    GridBagLayout gridBagLayout;
    JPanel panelFilterMgnt;
    JButton btnCreateFltr;
    JButton btnModifyFltr;
    JButton btnStructCompare;
    JButton btnCreateFilter;
    JButton btnModifyFilter;
    JButton btnViewFilter;
    JButton btnDeleteFilter;
    JButton btnExit;
    PSAFilterPropertiesPanel createFilterUIPanel;
    PSAModifyFilterUIPanel modifyFilterUIPanel;
    PSAFilterDBHandler filterDBHandler = PSAFilterUIController.getFilterDBHandler();

    public PSAFilterMainUIDialog() {
        logger.info("PSAFilterMainUIDialog : START Constructor of the class");
        prepareUiForFilterMgnt();
        logger.info("PSAFilterMainUIDialog : END constructor of the class");
    }

    private void prepareUiForFilterMgnt() {
        logger.info("START prepareUiForFilterMgnt()");
        GridBagLayout thisLayout = new GridBagLayout();

        getContentPane().setLayout(thisLayout);
        getContentPane().setBackground(new java.awt.Color(236, 233, 216));
        this.setTitle(PSAFilterUIController.objLanguageHandler.Getlabel("Title.ManageFilters","Manage Filters"));
        USER_ACCESS userAccess = filterDBHandler.getUserAppAccess();

        int index = 1;

        // CREATE Filter Button
        if ((userAccess == USER_ACCESS.ALL) || (userAccess == USER_ACCESS.WRITE))
        {
            btnCreateFilter = new JButton();
            btnCreateFilter.setText("CREATE FILTER");
            getContentPane().add(btnCreateFilter, new GridBagConstraints(0, index++, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            btnCreateFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Set action as CREATE_FILTER as "CREATE FILTER" button is pressed
                    PSAFilterUIController.setFilterAction(ACTION.CREATE_FILTER);
                    logger.info("Performing create action!");
                    PSAFilterPropertiesPanel filterPropertiesPanel = new PSAFilterPropertiesPanel();
                    filterPropertiesPanel.dummy();
                }
            });
        }

        // VIEW Filter Button
        if ((userAccess == USER_ACCESS.ALL) || (userAccess == USER_ACCESS.WRITE) || (userAccess == USER_ACCESS.READ))
        {
            btnViewFilter = new JButton();
            btnViewFilter.setText("VIEW FILTER");
            getContentPane().add(btnViewFilter, new GridBagConstraints(0, index++, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

            btnViewFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Set action as VIEW_FILTER as "VIEW FILTER" button is pressed
                    PSAFilterUIController.setFilterAction(ACTION.VIEW_FILTER);
                    logger.info("Performing view action!");
                    clearListForUIDisplay();
                    modifyFilterUIPanel = new PSAModifyFilterUIPanel();
                }
            });
        }

        // MODIFY Filter Button
        if ((userAccess == USER_ACCESS.ALL) || (userAccess == USER_ACCESS.WRITE))
        {
            btnModifyFilter = new JButton();
            btnModifyFilter.setText("MODIFY FILTER");
            getContentPane().add(btnModifyFilter, new GridBagConstraints(0, index++, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            btnModifyFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Set action as MODIFY_FILTER as "MODIFY FILTER" button is pressed
                    PSAFilterUIController.setFilterAction(ACTION.MODIFY_FILTER);
                    logger.info("Performing modify action!");
                    clearListForUIDisplay();
                    modifyFilterUIPanel = new PSAModifyFilterUIPanel();
                    modifyFilterUIPanel.dummy();
                }
            });
        }


        // Delete Filter Button
        if (userAccess == USER_ACCESS.ALL) 
        {
            btnDeleteFilter = new JButton();
            btnDeleteFilter.setText("DELETE FILTER");
            getContentPane().add(btnDeleteFilter, new GridBagConstraints(0, index++, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

            btnDeleteFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Set action as DELETE_FILTER as "DELETE FILTER" button is pressed
                    PSAFilterUIController.setFilterAction(ACTION.DELETE_FILTER);
                    logger.info("Performing delete action!");
                    clearListForUIDisplay();
                    modifyFilterUIPanel = new PSAModifyFilterUIPanel();
                }
            });
        }
        
        // Exit Button
        btnExit = new JButton();
        btnExit.setText("Exit");
        getContentPane().add(btnExit, new GridBagConstraints(0, index, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                disposePanel(0);
            }
        });

        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                disposePanel(0);
            }
        });
        setAlwaysOnTop(PSAFilterConstant.FALSE_VAL);
        setResizable(PSAFilterConstant.FALSE_VAL);
        setModal(PSAFilterConstant.FALSE_VAL);
        setVisible(PSAFilterConstant.TRUE_VAL);
        setLocation(PSAUtilityFunctions.getCenterPosition(this));
        logger.info("END addAllFilterMgntBtns()");
    }

    @SuppressWarnings("static-access")
    public void disposePanel(int iExitValue) {
        dispose();
        PSAFilterUIController.getFilterDBHandler().closeDatabaseConnection();
        System.exit(iExitValue);
    }

    public void dummy() {
        logger.info("method dummy()");
    }

    void clearListForUIDisplay() {
        logger.info("START refereshListForUIDisplay()");
        PSAFilterUIController.arrayListfilters.clear();
        PSAFilterUIController.filterDataModel.clearList();
        PSAFilterUIController.scrollPaneTable.setViewportView(null);
        PSAFilterUIController.scrollPaneTable.repaint();
        logger.info("END refereshListForUIDisplay()");
    }
}

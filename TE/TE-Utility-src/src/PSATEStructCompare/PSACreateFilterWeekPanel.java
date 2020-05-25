/*
 * Creation : Jun 7, 2017
 */
package com.psa.PSATEStructCompare;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.psa.tools.LaunchDate;

public class PSACreateFilterWeekPanel extends JDialog {
    private static final Logger logger = Logger.getLogger("PSACreateFilterWeekPanel");
    private JLabel labelAvailable;
    private JLabel labelSelection;
    private FilteredJList listAvailable;
    private List<String> removeArrayList;
    private List<String> addArrayList;
    private JList listSelected;
    private ButtonEnableListener buttonEnableListener;
    private DefaultListModel listModelSelected;
    private JScrollPane scrollPaneAvailable;
    private JScrollPane scrollPaneSelected;
    private JButton buttonSelect;
    private JButton buttonDeselect;
    private JButton buttonOK;

    public PSACreateFilterWeekPanel(List list) {
        logger.info("PSACreateFilterWeekPanel : START Constructor of the class");
        removeArrayList = new ArrayList<String>();
        addArrayList = new ArrayList<String>();
        initFilterWeekPanel(list);
        logger.info("PSACreateFilterWeekPanel : END Constructor of the class");
    }

    private void initFilterWeekPanel(List<Object> list) {
        logger.info("[PSACreateFilterWeekPanel:initFilterWeekPanel] START");
        GridBagLayout jPanelSiteSelectionLayout = new GridBagLayout();
        jPanelSiteSelectionLayout.rowWeights = new double[] { 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.1 };
        jPanelSiteSelectionLayout.rowHeights = new int[] { 9, 30, 7, 35, 24, 35, 7 };
        jPanelSiteSelectionLayout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1 };
        jPanelSiteSelectionLayout.columnWidths = new int[] { 60, 60, 50, 60, 60 };
        this.setLayout(jPanelSiteSelectionLayout);
        this.setPreferredSize(new java.awt.Dimension(398, 210));

        // Available Label
        labelAvailable = new JLabel();
        labelAvailable.setText(PSAFilterUIController.objLanguageHandler.Getlabel("Label.AvailableOptions","Available Option"));
        this.add(labelAvailable,
                new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        labelAvailable.setHorizontalAlignment(SwingConstants.CENTER);

        // Selection Label
        labelSelection = new JLabel();
        labelSelection.setText(PSAFilterUIController.objLanguageHandler.Getlabel("Label.SelectedOptions","Selected Options"));
        this.add(labelSelection,
                new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        labelSelection.setHorizontalAlignment(SwingConstants.CENTER);

        // Available Data List
        listAvailable = new FilteredJList();
        listAvailable.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        listAvailable.setSize(90, 204);

        if (PSAFilterPropertiesPanel.countModeParamClick > 1) {
            list.removeAll(PSAFilterPropertiesPanel.listWeekDaysSelected);
        }
        for (Object object : list) {
            listAvailable.addItem(object);
        }
        listAvailable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listAvailable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                availableListMouseClicked(evt);
            }
        });
        scrollPaneAvailable = new JScrollPane(listAvailable);
        add(scrollPaneAvailable,
                new GridBagConstraints(0, 2, 2, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        // Selected Data list
        listModelSelected = new DefaultListModel();
        listSelected = new JList();
        listSelected.setModel(listModelSelected);
        if (PSAFilterPropertiesPanel.countModeParamClick > 1) {
            for (Object object : PSAFilterPropertiesPanel.listWeekDaysSelected) {
                if (object != null) {
                    System.out.println("************ADDING listModelSelected");
                    listModelSelected.addElement(object);
                }
            }
        }

        listSelected.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        listSelected.setSize(90, 204);
        listSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelected.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                selectedListMouseClicked(evt);
            }
        });
        scrollPaneSelected = new JScrollPane(listSelected);
        add(scrollPaneSelected,
                new GridBagConstraints(3, 2, 2, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        // Select Buttons
        buttonSelect = new JButton();
        add(buttonSelect,
                new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        buttonSelect.setText(">");
        checkSelectBtnStatus();
        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                selectItemFromAvailList();
                checkSelectBtnStatus();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        });

        // De:select button
        buttonDeselect = new JButton();
        this.add(buttonDeselect,
                new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonDeselect.setText("<");
        buttonDeselect.setEnabled(PSAFilterConstant.FALSE_VAL);
        buttonDeselect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                deSelectItemFromSelectList();
                checkDeSelectBtnStatus();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        });
        buttonOK = new JButton(PSAFilterUIController.objLanguageHandler.Getlabel("Label.OKButton","OK"));
        this.add(buttonOK,
                new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        buttonOK.setEnabled(PSAFilterConstant.TRUE_VAL);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = listModelSelected.getSize();
                PSAFilterPropertiesPanel.listWeekDaysSelected.clear();
                for (int i = 0; i < size; i++) {
                    System.out.println("SELECTED ELEMENT: " + listModelSelected.get(i));
                    PSAFilterPropertiesPanel.listWeekDaysSelected.add(listModelSelected.get(i));
                }
                PSAFilterPropertiesPanel.textFldModeParam.setText(getModeParamContent());
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Calculating Next Launch Date
                LaunchDate launchDate = new LaunchDate();
                String tempStartDate = PSAFilterPropertiesPanel.textFieldFilterStartDate.getText();
                logger.info("[PSACreateFilterWeekPanel:initFilterWeekPanel] Calulating NEXT launch date with \n Start Date : " + tempStartDate + " \n WeekDay selections : " + addArrayList);
                String nextWeekDayLaunchDate = launchDate.nextWeekDayLaunchDate(addArrayList, tempStartDate);
                logger.info("[PSACreateFilterWeekPanel:initFilterWeekPanel] NEXT launch date is: " + nextWeekDayLaunchDate);
                PSAFilterPropertiesPanel.textFldFltLaunchDate.setText(nextWeekDayLaunchDate);
                PSAFilterPropertiesPanel.textFldModeParam.setEnabled(false);
                setVisible(PSAFilterConstant.FALSE_VAL);
            }
        });

        this.setSize(400, 300);
        this.setResizable(PSAFilterConstant.FALSE_VAL);
        setModal(PSAFilterConstant.TRUE_VAL);
        setLocation(PSAUtilityFunctions.getCenterPosition(this));
        this.setVisible(PSAFilterConstant.TRUE_VAL);
        logger.info("[PSACreateFilterWeekPanel:initFilterWeekPanel] END");
    }

    private String getModeParamContent() {
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        if (PSAFilterPropertiesPanel.countModeParamClick > 1) {
            for (Object data : PSAFilterPropertiesPanel.listWeekDaysSelected) {
                count++;
                buffer.append(data.toString());
                if (count == addArrayList.size()) {
                    buffer.append("");
                } else {
                    buffer.append(",");
                }
            }

        } else {
            for (String data : addArrayList) {
                count++;
                buffer.append(data);
                if (count == addArrayList.size()) {
                    buffer.append("");
                } else {
                    buffer.append(",");
                }
            }
        }
        logger.info("Mode param content to set: " + buffer.toString());
        return buffer.toString();
    }

    /**
     * Fill values of Available list.
     * 
     * @param dataList : ArrayList<String> Available value list.
     */
    public void setAvailableValues(List<String> dataList) {
        logger.info("START setAvailableValues");
        FilterModel availableListModel = (FilterModel) listAvailable.getModel();
        availableListModel.clear();
        if (null != dataList) {
            int size = dataList.size();

            for (int index = 0; index < size; index++) {
                String strTemp = dataList.get(index);
                if (!listModelSelected.contains(strTemp)) {
                    availableListModel.addElement(strTemp);
                }
            }
            if (availableListModel.getSize() > 0) {
                buttonSelect.setEnabled(true);
            }
        }
        logger.info("END setAvailableValues");
    }

    /**
     * It used to de:select item.
     * 
     * @param evt : MouseEvent
     */
    private void selectedListMouseClicked(MouseEvent evt) {
        checkDeSelectBtnStatus();
        logger.info("START selectedListMouseClicked");
        if (evt.getClickCount() == 2) {
            int index = listSelected.locationToIndex(evt.getPoint());
            logger.info("Double clicked on Item " + index);
            if (-1 != index) {
                deSelectItemFromSelectList();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        }
        logger.info("END selectedListMouseClicked");
    }

    private void checkDeSelectBtnStatus() {
        buttonDeselect.setEnabled(!listSelected.isSelectionEmpty());
    }

    /**
     * DeSelects item from List of Selected to Available List.
     */
    private void deSelectItemFromSelectList() {
        checkDeSelectBtnStatus();
        checkDeSelectBtnStatus();
        logger.info("START deSelectItemFromSelectList");
        // Get selected data from Selected data list.
        Object[] selectedData = listSelected.getSelectedValues();
        if (selectedData.length > 0) {
            // Process to all selected data.
            for (Object object : selectedData) {
                logger.info("DeSelected item =" + object);
                ((FilterModel) listAvailable.getModel()).addElement(object);
                listModelSelected.removeElement(object);
                // Checked selected item is exist in add list or not.
                if (addArrayList.contains(object)) {
                    addArrayList.remove(object);
                } else {
                    removeArrayList.add((String) object);
                }
            }
            // Remove the selected selection.
            int[] index = listAvailable.getSelectedIndices();
            if (index.length > 0) {
                listSelected.removeSelectionInterval(index[0], index[index.length - 1]);
            }
            logger.info("Deleted List = " + removeArrayList.toString());
            logger.info("Added List = " + addArrayList.toString());
        } else {
            logger.info("No item selected in list.");
        }
        logger.info("END deSelectItemFromSelectList");
        FilterModel availableListModel = (FilterModel) listAvailable.getModel();
        sortModel(availableListModel);
    }

    private void checkSelectBtnStatus() {
        buttonSelect.setEnabled(!listAvailable.isSelectionEmpty());
    }

    /**
     * It used to selected item.
     * 
     * @param evt : MouseEvent
     */
    private void availableListMouseClicked(MouseEvent evt) {
        logger.info("START availableListMouseClicked");
        checkDeSelectBtnStatus();
        checkSelectBtnStatus();
        buttonDeselect.setEnabled(PSAFilterConstant.FALSE_VAL);
        if (evt.getClickCount() == 2) {
            int index = listAvailable.locationToIndex(evt.getPoint());
            logger.info("Double clicked on Item " + index);
            if (-1 != index) {
                selectItemFromAvailList();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        }
        logger.info("END availableListMouseClicked");
    }

    /**
     * Selects item from List of Available to Selected List.
     */
    private void selectItemFromAvailList() {
        logger.info("START selectItemFromAvailList");
        FilterModel filterModel = (FilterModel) listAvailable.getModel();
        // Get selected data from available data list.
        Object[] selectedData = listAvailable.getSelectedValues();
        if (selectedData.length > 0) {
            ArrayList<String> deletedList = new ArrayList<String>();
            // Process to all selected data.
            for (Object object : selectedData) {
                logger.info("Selected Item =" + object);

                listModelSelected.addElement(object);
                deletedList.add((String) object);
                // Checked selected item is exist in remove list or not.
                if (removeArrayList.contains(object)) {
                    removeArrayList.remove(object);
                } else {
                    addArrayList.add((String) object);
                }
            }
            // Remove the selected selection.
            int[] index = listAvailable.getSelectedIndices();
            if (index.length > 0) {
                listAvailable.removeSelectionInterval(index[0], index[index.length - 1]);
            }
            // Remove the selected item.
            filterModel.removeSelectedElements(deletedList);
            logger.info("Deleted List = " + removeArrayList.toString());
            logger.info("Added List = " + addArrayList.toString());

        } else {
            logger.info("No item selected in list.");
        }
        logger.info("END selectItemFromAvailList");
        sortModel(listModelSelected);
    }

    private void sortModel(DefaultListModel model) {
        String[] weekDays = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < model.size(); i++) {
            for (int j = 0; j < weekDays.length; j++) {
                if (weekDays[j].equals((String) model.get(i))) {
                    map.put(j, (String) model.get(i));
                }
            }

        }
        SortedSet<Integer> keys = new TreeSet<Integer>(map.keySet());
        model.removeAllElements();
        for (int key : keys) {

            String value = map.get(key);
            model.addElement(value);
        }
    }

    private void sortModel(FilterModel model) {
        String[] weekDays = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < weekDays.length; j++) {
                if (weekDays[j].equals((String) model.getElementAt(i))) {
                    map.put(j, (String) model.getElementAt(i));
                }
            }

        }
        SortedSet<Integer> keys = new TreeSet<Integer>(map.keySet());
        model.clear();
        for (int key : keys) {
            String value = map.get(key);
            model.addElement(value);
        }
    }

    public void dummy() {
        logger.info("Dummy method!");

    }

    public List<String> getModeParamList() {
        return addArrayList;
    }

}

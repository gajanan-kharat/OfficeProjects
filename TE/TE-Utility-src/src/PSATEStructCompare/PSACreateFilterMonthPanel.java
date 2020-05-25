/*
 * Creation : Jun 8, 2017
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
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Arrays;
import java.util.logging.Logger;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import com.psa.tools.LaunchDate;

public class PSACreateFilterMonthPanel extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger("PSACreateFilterMonthPanel");
    private FilteredJList listAvailable;
    private ArrayList<String> removeArrayList;
    private ArrayList<String> addArrayList;
    private JList listSelected;
    private ButtonEnableListener buttonEnableListener;
    private DefaultListModel listModelSelected;
    private JScrollPane scrollPaneSelected;
    private JButton buttonSelect;
    private JButton buttonOK;
    private JButton buttonDelete;
    JComboBox comboBoxWeekDays;
    JComboBox comboBoxWeekCount;
    JComboBox comboBoxForDate;
    DefaultComboBoxModel comboBoxModelForDate;
    static Vector<String> comboBoxItems;
    private JRadioButton radioBtnSpecDayOfWeek;
    private JRadioButton radioBtnSpecDate;
    JPanel panel;

    public PSACreateFilterMonthPanel() {
        logger.info("PSACreateFilterMonthPanel : START Constructor of the class");
        this.setLayout(new GridBagLayout());
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        addArrayList = new ArrayList<String>();
        listAvailable = new FilteredJList();
        listAvailable.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        listAvailable.setSize(90, 204);
        for (int item = 0; item <= 31; item++) {
            listAvailable.addItem(item);
        }
        listAvailable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAvailable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                availableListMouseClicked(evt);
            }
        });

        String[] weekDays = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
        String[] defaultDaysCount = { "FIRST", "SECOND", "THIRD", "FOURTH", "LAST" };
        // Combo box for Week day selection
        DefaultComboBoxModel comboBoxModelWeekDays = new DefaultComboBoxModel();
        for (String day : weekDays) {
            comboBoxModelWeekDays.addElement(day);
        }
        comboBoxWeekDays = new JComboBox(comboBoxModelWeekDays);
        comboBoxWeekDays.setSelectedIndex(0);
        DefaultComboBoxModel comboBoxModelWeekCount = new DefaultComboBoxModel();
        for (String dayCount : defaultDaysCount) {
            comboBoxModelWeekCount.addElement(dayCount);
        }
        comboBoxWeekCount = new JComboBox(comboBoxModelWeekCount);
        comboBoxWeekCount.setSelectedIndex(0);
        comboBoxItems = new Vector<String>();
        for (int item = 0; item <= 30; item++) {
            comboBoxItems.add(Integer.toString(item + 1));
        }

        comboBoxModelForDate = new DefaultComboBoxModel(comboBoxItems);
        comboBoxForDate = new JComboBox(comboBoxModelForDate);
        comboBoxForDate.setModel(comboBoxModelForDate);
        comboBoxForDate.setSelectedIndex(0);

        initFilterMonthPanel();
        logger.info("PSACreateFilterMonthPanel : END Constructor of the class");
    }

    private void initFilterMonthPanel() {
        logger.info("START PSACreateFilterMonthPanel");

        GridBagConstraints constraints = new GridBagConstraints();
        this.setTitle(PSAFilterUIController.objLanguageHandler.Getlabel("Title.MonthlyModeParamSelection","Monthly Selection mode panel for next launch date"));
        // Radio button
        boolean radioBtnFlag = true;
        radioBtnSpecDate = new JRadioButton(PSAFilterUIController.objLanguageHandler.Getlabel("Label.SpecificDayOfMonth", "Specific date of every month                         "));
        radioBtnSpecDate.setSelected(radioBtnFlag);
        comboBoxWeekDays.setEnabled(false);
        comboBoxWeekCount.setEnabled(false);

        radioBtnSpecDayOfWeek = new JRadioButton(PSAFilterUIController.objLanguageHandler.Getlabel("Label.SpecificDayOfWeekOfMonth", "Specific day of specific week of every month"));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioBtnSpecDate);
        buttonGroup.add(radioBtnSpecDayOfWeek);

        panel.add(radioBtnSpecDate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
        radioBtnSpecDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxModelForDate.removeAllElements();
                for (int item = 1; item <= 31; item++) {
                    comboBoxModelForDate.addElement(item);
                }
                listModelSelected.removeAllElements();
                comboBoxForDate.setEnabled(PSAFilterConstant.TRUE_VAL);
                comboBoxWeekDays.setEnabled(PSAFilterConstant.FALSE_VAL);
                comboBoxWeekCount.setEnabled(PSAFilterConstant.FALSE_VAL);
            }
        });
        panel.add(radioBtnSpecDayOfWeek, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
        radioBtnSpecDayOfWeek.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listModelSelected.removeAllElements();
                comboBoxWeekDays.setEnabled(PSAFilterConstant.TRUE_VAL);
                comboBoxWeekCount.setEnabled(PSAFilterConstant.TRUE_VAL);
                comboBoxForDate.setEnabled(PSAFilterConstant.FALSE_VAL);
            }
        });
        // combo box for Date of month
        panel.add(comboBoxForDate, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
        panel.add(comboBoxWeekDays, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        // Combo box for week day count selection
        panel.add(comboBoxWeekCount, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        // Select Buttons
        buttonSelect = new JButton();
        panel.add(buttonSelect, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 30, 5, 5), 0, 0));
        buttonSelect.setText(">");
        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                selectItemFromAvailList();
                checkDeleteBtnStatus();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        });

        // Delete Buttons
        buttonDelete = new JButton();
        panel.add(buttonDelete, new GridBagConstraints(4, 1, 1, 3, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 30, 5, 5), 0, 0));
        buttonDelete.setText("X");
        buttonDelete.setEnabled(PSAFilterConstant.FALSE_VAL);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                deSelectItemFromSelectList();
                checkDeleteBtnStatus();
                if (buttonEnableListener != null) {
                    buttonEnableListener.fireButtonEnableEvent(evt);
                }
            }
        });

        // Selected Data list
        listModelSelected = new DefaultListModel();
        listSelected = new JList();
        listSelected.setModel(listModelSelected);
        listSelected.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        listSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelected.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                selectedListMouseClicked(evt);
                checkDeleteBtnStatus();
            }

        });
        Dimension d = listSelected.getPreferredSize();
        d.width = 250;
        d.height = 120;
        scrollPaneSelected = new JScrollPane(listSelected);
        scrollPaneSelected.setPreferredSize(d);
        panel.add(scrollPaneSelected,
                new GridBagConstraints(5, 0, 0, 0, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 36, 0), -100, 0));

        buttonOK = new JButton("OK");
        panel.add(buttonOK,
                new GridBagConstraints(5, 5, 0, 0, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.CENTER, new Insets(0, 5, 5, 0), 0, 0));

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModelSelected.size(); i++) {
                    addArrayList.add((String) listModelSelected.get(i));
                }
                PSAFilterPropertiesPanel.textFldModeParam.setText(getModeParamContent());
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Calculating Next Launch Date
                LaunchDate launchDate = new LaunchDate();
                String tempStartDate = PSAFilterPropertiesPanel.textFieldFilterStartDate.getText();
                String strNextLaunchDate = null;

                boolean bNextMonthLaunchDate = false;
                if (radioBtnSpecDayOfWeek.isSelected())
                    bNextMonthLaunchDate = true;

                if (bNextMonthLaunchDate) {
                    // "Day Of Week Of Month" i.e. "FIRST MONDAY,LAST FRIDAY"
                    strNextLaunchDate = launchDate.nextMonthLaunchDate(addArrayList, tempStartDate);
                } else {
                    // "Day Of Month" i.e. 1, 22, 30
                    strNextLaunchDate = launchDate.nextMonthLaunchDayOfMonth(addArrayList, tempStartDate);
                }
                logger.info("NEXT Month launch date is: " + strNextLaunchDate);
                PSAFilterPropertiesPanel.textFldFltLaunchDate.setText(strNextLaunchDate);
                PSAFilterPropertiesPanel.textFldModeParam.setEnabled(false);

                setVisible(PSAFilterConstant.FALSE_VAL);
            }
        });
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);

        this.getContentPane().add(panel, constraints);
        this.pack();
        this.setResizable(PSAFilterConstant.FALSE_VAL);
        setModal(PSAFilterConstant.TRUE_VAL);
        setLocation(PSAUtilityFunctions.getCenterPosition(this));
        this.setVisible(PSAFilterConstant.TRUE_VAL);
        logger.info("ENd PSACreateFilterMonthPanel");
    }

    private String getModeParamContent() {
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        for (String data : addArrayList) {
            count++;
            buffer.append(data);
            if (count == addArrayList.size()) {
                buffer.append("");
            } else {
                buffer.append(",");
            }
        }
        logger.info("Mode param content to set: " + buffer.toString());
        return buffer.toString();
    }

    private void checkDeleteBtnStatus() {
        buttonDelete.setEnabled(!listSelected.isSelectionEmpty());
    }

    /**
     * DeSelects item from List of Selected to Available List.
     */
    private void deSelectItemFromSelectList() {
        logger.info("START deSelectItemFromSelectList");
        // Get selected data from Selected data list.
        Object[] selectedData = listSelected.getSelectedValues();
        if (selectedData.length > 0) {
            // Process to all selected data.
            for (Object object : selectedData) {
                logger.info("DeSelected item =" + object);
                if (radioBtnSpecDate.isSelected()) {
                    comboBoxModelForDate.addElement(object);
                    int size = comboBoxModelForDate.getSize();
                    Integer[] elements = new Integer[size];
                    for (int i = 0; i < size; i++) {
                        Integer elementAt = Integer.parseInt(comboBoxModelForDate.getElementAt(i).toString());
                        elements[i] = elementAt;
                    }
                    Arrays.sort(elements);
                    comboBoxModelForDate.removeAllElements();
                    for (Integer element : elements) {
                        comboBoxModelForDate.addElement(element);
                    }
                }
                ((FilterModel) listAvailable.getModel()).addElement(object);
                listModelSelected.removeElement(object);
            }
            // Remove the selected selection.
            int[] index = listAvailable.getSelectedIndices();
            if (index.length > 0) {
                listSelected.removeSelectionInterval(index[0], index[index.length - 1]);
            }
        } else {
            logger.info("No item selected in list.");
        }
        logger.info("END deSelectItemFromSelectList");
    }

    /**
     * It used to de:select item.
     * 
     * @param evt : MouseEvent
     */
    private void selectedListMouseClicked(MouseEvent evt) {
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

    /**
     * Selects item from List of Available to Selected List.
     */
    private void selectItemFromAvailList() {
        logger.info("START selectItemFromAvailList");
        String item;
        StringBuilder countAndWeek = new StringBuilder();
        if (radioBtnSpecDate.isSelected()) {
            item = "" + comboBoxForDate.getSelectedItem();
            countAndWeek.append(item);
            int size = comboBoxModelForDate.getSize();
            Integer selectedItem = Integer.parseInt(item);
            List<Integer> elements = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                Integer elementAt = Integer.parseInt(comboBoxModelForDate.getElementAt(i).toString());
                if (selectedItem != elementAt)
                    elements.add(elementAt);
            }
            Collections.sort(elements);
            comboBoxModelForDate.removeAllElements();
            for (Integer element : elements) {
                comboBoxModelForDate.addElement(element);
            }

        } else {
            String weekDay = (String) comboBoxWeekDays.getSelectedItem();
            String weekCount = (String) comboBoxWeekCount.getSelectedItem();
            countAndWeek.append(weekCount);
            countAndWeek.append(" ");
            countAndWeek.append(weekDay);
            logger.info("Added List = " + addArrayList.toString());
        }
        listAvailable.addItem(countAndWeek.toString());
        listModelSelected.addElement(countAndWeek.toString());
        Set<String> hashSet = new HashSet<String>();
        int size = listModelSelected.size();
        for (int i = 0; i < size; i++) {
            hashSet.add((String) listModelSelected.get(i));
        }
        hashSet.add(countAndWeek.toString());
        listModelSelected.removeAllElements();
        List<String> list = new ArrayList<String>(hashSet);
        Collections.sort(list);
        for (String element : list) {
            listModelSelected.addElement(element);
        }

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
        scrollPaneSelected.repaint();
        scrollPaneSelected.revalidate();
    }

    /**
     * It used to selected item.
     * 
     * @param evt : MouseEvent
     */
    private void availableListMouseClicked(MouseEvent evt) {
        logger.info("START availableListMouseClicked");
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

    public void dummy() {
        logger.info("START dummy");
        logger.info("END dummy");
    }

    public List<String> getModeParamList() {
        return addArrayList;
    }

}

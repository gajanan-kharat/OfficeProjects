/*
 * Creation : May 31, 2017
 */
package com.psa.PSATEStructCompare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class PSAFilterTableDataModel implements TableModel {
    Logger logger = Logger.getLogger("PSAFilterTableDataModel");
    List<PSAFilterModel> arrayListFilterData; // All data list
    String[] colHeaderForTable; // Column Header
    List<PSAFilterModel> arrayListFiltered = null; // Filter Data list
    boolean filterFlag = PSAFilterConstant.FALSE_VAL; // Filter flag
    PSAFilterModel objSelFilter; // Selected filter

    public PSAFilterTableDataModel() {
        logger.info("PSAFilterTableDataModel : START Constructor of the class");
        colHeaderForTable = new String[] { "FILTER NAME", "REVISION", "PROJECT", "DESIGNATION", "TYPE", "MODE", "CREATOR", "START DATE", "NEXT LAUNCH DATE" };
        arrayListFilterData = new ArrayList<PSAFilterModel>();
        arrayListFiltered = new ArrayList<PSAFilterModel>();
        logger.info("PSAFilterTableDataModel : END constructor of the class");
    }

    @Override
    public int getRowCount() {
        int size;
        if (filterFlag) {
            size = arrayListFiltered.size();
        } else {
            size = arrayListFilterData.size();
        }
        return size;
    }

    @Override
    public int getColumnCount() {
        return colHeaderForTable.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colHeaderForTable[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retObj;
        PSAFilterModel filter;
        if (filterFlag) {
            filter = arrayListFiltered.get(rowIndex);
        } else {
            filter = arrayListFilterData.get(rowIndex);
        }
        retObj = getValueAtColumnIndex(filter, columnIndex);
        return retObj;
    }

    /**
     * It returns respective columnIndex value from PSAFilter object.
     * 
     * @param iFilter : PSAFilter
     * @param iColumnIndex : integer
     * @return Object
     */
    private Object getValueAtColumnIndex(PSAFilterModel filterModel, int iColumnIndex) {
        Object retObj = null;
        switch (iColumnIndex) {
        case 0:
            retObj = filterModel.getName();
            break;
        case 1:
            retObj = filterModel.getRevision();
            break;
        case 2:
            retObj = filterModel.getProject();
            break;
        case 3:
            retObj = filterModel.getDesignation();
            break;
        case 4:
            retObj = filterModel.getType();
            break;
        case 5:
            retObj = filterModel.getMode();
            break;
        case 6:
            retObj = filterModel.getUser();
            break;
        case 7:
            retObj = filterModel.getStartDate();
            break;
        case 8:
            retObj = filterModel.getNextLaunchDate();
            break;
        default:
        }
        return retObj;
    }

    /**
     * It used to filter the filter data according to searching field.
     * 
     * @param columnFilter : HashMap<Integer, String>
     */
    public void filterList(Map<Integer, String> columnFilter) {
        filterFlag = true;
        arrayListFiltered.clear();
        if (columnFilter != null) {
            // Get column number.
            Object[] columnNumber = columnFilter.keySet().toArray();
            // Check for all filter data
            for (PSAFilterModel filter : arrayListFilterData) {
                boolean matchFlag = false;
                // Check for each column index.
                for (int index = 0; index < columnNumber.length; index++) {
                    int columnIndex = ((Integer) columnNumber[index]).intValue();
                    // Get value at column index.
                    String value = (String) getValueAtColumnIndex(filter, columnIndex);
                    if (value != null) {
                        // Read pattern
                        String pattern = columnFilter.get(columnNumber[index]);
                        pattern = pattern.replace(PSAFilterConstant.STAR_SYMBOL, "." + PSAFilterConstant.STAR_SYMBOL);
                        // Match Pattern with value.
                        matchFlag = value.matches(pattern);
                        if (matchFlag == PSAFilterConstant.FALSE_VAL)
                            break;
                    }
                }
                if (matchFlag) {
                    arrayListFiltered.add(filter);
                }
            }
        }
    }

    /**
     * It return the selected PSAFilter object present at given rowIndex.
     * 
     * @param iRowIndex : int
     * @return PSAFilter : selected object
     */
    public PSAFilterModel getSelectedFilter(int iRowIndex) {
        if (filterFlag) {
            objSelFilter = arrayListFiltered.get(iRowIndex);
        } else {
            objSelFilter = arrayListFilterData.get(iRowIndex);
        }
        return objSelFilter;
    }

    /**
     * It returns list containing selected PSA Filter objects.
     * 
     * @param iRowIndices : int []
     * @return List<PSAFilterModel> : selected PSA filters
     */
    @SuppressWarnings("null")
    public List<PSAFilterModel> getListOfSelectedFilters(int iRowIndices[])
    {
        List<PSAFilterModel> listSelectedFilters = new ArrayList<PSAFilterModel>();
        for(int i = 0; i < iRowIndices.length; i++)
        {
            PSAFilterModel curSelectedPSAFilter = null;
            if (filterFlag)
            {
                curSelectedPSAFilter = arrayListFiltered.get(iRowIndices[i]);
            }
            else
            {
                curSelectedPSAFilter = arrayListFilterData.get(iRowIndices[i]);
            }
            
            listSelectedFilters.add(curSelectedPSAFilter);
        }
        return listSelectedFilters;
    }

    /**
     * It sets the filter flag value.
     * 
     * @param iFlag : boolean
     */
    public void setFilterFlag(boolean iFlag) {
        filterFlag = iFlag;
    }

    /**
     * It return the value of filterFlag.
     * 
     * @return boolean : filterFlag
     */
    public boolean getFilterFlag() {
        return filterFlag;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        logger.info("START setValueAt()");
        logger.info("END setValueAt()");
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        logger.info("START addTableModelListener()");
        logger.info("END addTableModelListener()");
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        logger.info("START removeTableModelListener()");
        logger.info("END removeTableModelListener()");
    }

    /**
     * It add filter element in list as well as original list.
     * 
     * @param psaFilter : PSAFilter
     */
    public void addElement(PSAFilterModel iPsaFilter) {
        arrayListFilterData.add(iPsaFilter);
    }

    /**
     * It removes element from filter list as well as original list.
     * 
     * @param psaFilter : PSAFilter
     */
    public void removeElement(PSAFilterModel iPsaFilter) {
        arrayListFilterData.remove(iPsaFilter);
        arrayListFiltered.remove(iPsaFilter);
    }

    /**
     * It refresh the PSAFilter list.
     * 
     * @param arrayListfilters : ArrayList<PSAFilter>
     */
    public void refreshList(List<PSAFilterModel> arrayListfilters) {
        arrayListFilterData = arrayListfilters;
    }

    /**
     * It clear the PSAFilter list.
     * 
     * @param arrayList : ArrayList<PSAFilter>
     */
    public void clearList() {
        arrayListFilterData.clear();
        arrayListFiltered.clear();
    }
}

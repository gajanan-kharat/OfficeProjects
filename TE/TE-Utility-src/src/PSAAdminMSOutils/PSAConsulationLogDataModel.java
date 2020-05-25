/**********************************************************************************************************
 *
 * FILE NAME	  : PSAConsulationLogDataModel
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used as data model for table used in Consultaion Log panel.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Class used as data model for table used in Consultaion Log panel.
 */
public class PSAConsulationLogDataModel implements TableModel{

	ArrayList<PSARequestLog> arrayListLogs;
	String []colHeader;
	
	/**
	 * Constructor of class 
	 */
	PSAConsulationLogDataModel(String []header)
	{
		
		colHeader =header;
		arrayListLogs = new ArrayList<PSARequestLog>();
	}
	
	public void refreshList(ArrayList<PSARequestLog> arrayList )
	{
		 arrayListLogs = arrayList;
	}

	public void clearList()
	{
		 arrayListLogs.clear();
	}
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return colHeader.length;
	}

	public String getColumnName(int columnIndex) {
		return colHeader[columnIndex];
	}

	public int getRowCount() {
		return arrayListLogs.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object retObj = null;
		PSARequestLog psaRequestLog = arrayListLogs.get(rowIndex);
		switch (columnIndex) {
		case 0:
			retObj =  psaRequestLog.getRequestName();
			break;
		case 1:
			retObj =  psaRequestLog.getTreatState();
			break;
		case 2:
			//retObj =  psaRequestLog.get;
			break;
		case 3:
			retObj =  psaRequestLog.getDate();
			break;
			
		case 4:
			retObj =  psaRequestLog.getUser();
			break;
		case 5:
			retObj =  psaRequestLog.getMessage();
			break;
		}
		return retObj;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
	}

	public void addTableModelListener(TableModelListener l) {
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	public  PSARequestLog getSelectedLogData(int index) {
		PSARequestLog  psaRequestLog = null;
		if(index >=0 && index < arrayListLogs.size())
		{
			 psaRequestLog = arrayListLogs.get(index);
		}
		return psaRequestLog;

	}
	public ArrayList<PSARequestLog> getRequestLogList()
	{
		return  arrayListLogs;
	}

}

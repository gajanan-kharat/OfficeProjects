/**********************************************************************************************************
 *
 * FILE NAME	  : PSADoubleList.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to display two list .
 *  				 Facility to move data from one list to another and viceversa.
 * 					 
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Class used to display two list for select / Deselect data.
 * Facility to move data from one list to another and viceversa.
 *
 */
@SuppressWarnings("serial")
public class PSADoubleList extends JPanel {

	private JScrollPane m_ScrollPaneAvailable;
	private JScrollPane m_ScrollPaneSelected;
	private FilteredJList m_ListAvailable;
	private JList m_ListSelected;
	private JButton m_ButtonSelect;
	private JButton m_ButtonDeselect;
	private JLabel m_LabelSelection;
	private JLabel m_LabelAvailable;
	
	private ArrayList<String> m_RemoveArrayList;
	private ArrayList<String> m_AddArrayList;
	
	private DefaultListModel m_ListModelSelected;
	private ButtonEnableListener m_ButtonEnableListener;


	/**
	 * Constructor of the class.
	 * @param strLeftLabel : Left list label
	 * @param strRightLabel: Right list label
	 */
	public PSADoubleList(String strLeftLabel,String strRightLabel) {
		super();
		initGUI();
		m_LabelAvailable.setText(strLeftLabel);
		m_LabelSelection.setText(strRightLabel);
		m_RemoveArrayList = new ArrayList<String>();
		m_AddArrayList = new ArrayList<String>();
	}

	/**
	 * It  creates and initialize components. 
	 */
	private void initGUI() {
		try
		{
						
			GridBagLayout jPanelSiteSelectionLayout = new GridBagLayout();
			jPanelSiteSelectionLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.1};
			jPanelSiteSelectionLayout.rowHeights = new int[] {9, 30, 7, 35, 24, 35, 7};
			jPanelSiteSelectionLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
			jPanelSiteSelectionLayout.columnWidths = new int[] {60, 60, 50, 60, 60};
			this.setLayout(jPanelSiteSelectionLayout);
			this.setPreferredSize(new java.awt.Dimension(398, 210));

			//Label Site Availbale
			m_LabelAvailable = new JLabel();
			this.add(m_LabelAvailable, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelAvailable.setHorizontalAlignment(SwingConstants.CENTER);
				
			//Label Site Choosen
			m_LabelSelection = new JLabel();
			this.add(m_LabelSelection, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			m_LabelSelection.setHorizontalAlignment(SwingConstants.CENTER);
				
			//Available Sites
			m_ListAvailable = new FilteredJList();
			m_ListAvailable.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
			m_ListAvailable.setSize(90, 204);
			m_ListAvailable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			//m_ListAvailable.setPreferredSize(new java.awt.Dimension(149, 141));
			m_ListAvailable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent evt) {
						availableListMouseClicked(evt);
						
					}
			});
			m_ScrollPaneAvailable = new JScrollPane(m_ListAvailable);
			add(m_ScrollPaneAvailable, new GridBagConstraints(0, 2, 2, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				
			//Selected list 
			m_ListModelSelected = new DefaultListModel();
			m_ListSelected = new JList();
			m_ListSelected.setModel(m_ListModelSelected);
			m_ListSelected.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
			m_ListSelected.setSize(90, 204);
			m_ListSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m_ListSelected.addMouseListener(new MouseAdapter() {
				@Override
					public void mouseClicked(MouseEvent evt) {
						selectedListMouseClicked(evt);
					}
			});
			m_ScrollPaneSelected = new JScrollPane(m_ListSelected);
			add(m_ScrollPaneSelected, new GridBagConstraints(3, 2, 2, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			
			//Select Buttons
			m_ButtonSelect = new JButton();
			add(m_ButtonSelect, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			//m_ButtonSelect.setText(">");
			m_ButtonSelect.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_next.png")));
			m_ButtonSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					Select();
					if(m_ButtonEnableListener != null)
					{
						m_ButtonEnableListener.fireButtonEnableEvent(evt);
					}
				}
			});
			
			//Deselect button
			m_ButtonDeselect = new JButton();
			this.add(m_ButtonDeselect, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			//m_ButtonDeselect.setText("<");
			m_ButtonDeselect.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/resultset_previous.png")));
			m_ButtonDeselect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					DeSelect();
					if(m_ButtonEnableListener != null)
					{
						m_ButtonEnableListener.fireButtonEnableEvent(evt);
					}
				}
			});
			
			CheckSiteSelectionButtons();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set  tool tip text of the left to right button.
	 * @param strTipText : String 
	 */
	public void setToolTipLeftToRight(String strTipText)
	{
		m_ButtonSelect.setToolTipText(strTipText);
	}
	
	/**
	 * Set  tool tip text of the right to left button.
	 * @param strTipText : String 
	 */
	public void setToolTipRightToLeft(String strTipText)
	{
		m_ButtonDeselect.setToolTipText(strTipText);
	}
	
	/**
	 * Enable or disable left or right button.
	 */
	private void CheckSiteSelectionButtons()
	{
		m_ButtonSelect.setEnabled((m_ListAvailable.getModel().getSize() == 0) ? false : true);
		m_ButtonDeselect.setEnabled((m_ListSelected.getModel().getSize() == 0) ? false : true);			
		
	}
	
	/**
	 * It used to selected item. 
	 * @param evt : MouseEvent
	 */
	private void availableListMouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() == 2))
		{
            int index = m_ListAvailable.locationToIndex(evt.getPoint());
            System.out.println("Double clicked on Item " + index);
            if(-1 != index)
            {
            	Select();
            	if(m_ButtonEnableListener != null)
				{
					m_ButtonEnableListener.fireButtonEnableEvent(evt);
				}
            }
         }
	}
	
	/**
	 * It used to deselected item. 
	 * @param evt : MouseEvent
	 */
	private void selectedListMouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() == 2)) {
            int index = m_ListSelected.locationToIndex(evt.getPoint());
            System.out.println("Double clicked on Item " + index);
            if(-1 != index)
            {
            	DeSelect();
            	if(m_ButtonEnableListener != null)
				{
					m_ButtonEnableListener.fireButtonEnableEvent(evt);
				}
            }
         }
	}
	
	
	/**
	 * Selects item from List of Availables to Selected List.
	 */
	private void Select()
	{
		int index = m_ListAvailable.getSelectedIndex();
		if(index >= 0)
		{
			String str = m_ListAvailable.getSelectedValue().toString();
			System.out.println("Selected Site ="+str);
			m_ListModelSelected.addElement(str);
			((FilterModel)m_ListAvailable.getModel()).removeElementAt(index);
			if(false == m_RemoveArrayList.contains(str))
			{
				m_AddArrayList.add(str);
			}
			else
			{
				m_RemoveArrayList.remove(str);
			}
		} 
		else
		{
			System.out.println("No item selected in list.");
		}
		CheckSiteSelectionButtons();
		System.out.println("Deleted List = "+m_RemoveArrayList.toString());
		System.out.println("Added List = "+m_AddArrayList.toString());
	}

	/**
	 * DeSelects item from List of Selected to Availables List.
	 */
	private void DeSelect()
	{
		int index = m_ListSelected.getSelectedIndex();
		if(index >= 0)
		{
			String str = m_ListSelected.getSelectedValue().toString();
			System.out.println("DeSelected Site ="+str);
			((FilterModel)m_ListAvailable.getModel()).addElement(str);
			m_ListModelSelected.removeElementAt(index);
			if(false == m_AddArrayList.contains(str))
			{
				m_RemoveArrayList.add(str);
			}
			else
			{
				m_AddArrayList.remove(str);
			}
		} 
		else
		{
			System.out.println("No item selected in list.");
		}
		CheckSiteSelectionButtons();
		System.out.println("Deleted List = "+m_RemoveArrayList.toString());
		System.out.println("Added List = "+m_AddArrayList.toString());
	}
	
    /**
     * Fill values of selected list.
     * @param dataList : ArrayList<String> selected value list.
     */
	public void setSelectedValues(ArrayList<String> dataList)
	{
		m_ListModelSelected.clear();
		if(null != dataList)
		{
			int size = dataList.size();
			for(int index=0; index < size; index++)
			{
				m_ListModelSelected.addElement(dataList.get(index));
			}
		}
		if(m_ListModelSelected.size() > 0)
		{
			m_ButtonDeselect.setEnabled(true);
		}
			
	}
	
	/**
     * Fill values of Available list.
     * @param dataList : ArrayList<String> Available value list.
     */
	public void setAvailableValues(ArrayList<String> dataList)
	{
		FilterModel availableListModel = (FilterModel)m_ListAvailable.getModel();
		availableListModel.clear();
		if(null != dataList)
		{
			int size = dataList.size();
			
			for(int index=0; index < size; index++)
			{
				String strTemp =dataList.get(index);
				if(!m_ListModelSelected.contains(strTemp))
				{
					availableListModel.addElement(strTemp);
				}
			}
			if(availableListModel.getSize() > 0)
			{
				m_ButtonSelect.setEnabled(true);
			}
		}
	}
	
	/**
	 * @return deleted item list.
	 */
	public ArrayList<String> getDeletedList()
	{
		return m_RemoveArrayList;
	}

	/**
	 * @return added item list.
	 */
	public ArrayList<String> getAddedList()
	{
		return m_AddArrayList;
	}

    /**
     * 
     * @return selected item list.
     */
	public ArrayList<String> getSelectedList() {
		ArrayList<String> selectedList = new ArrayList<String>();
		int size = m_ListModelSelected.getSize();
		for (int index = 0; index < size; index++) {
			selectedList.add((String)m_ListModelSelected.get(index));
		}
		return selectedList;
		
	}
	
	/**
	 * Register the button Enable Listener.
	 * @param buttonEnableListener : ButtonEnableListener
	 */
	public void addButtonEnabledListener(ButtonEnableListener buttonEnableListener)
	{
		m_ButtonEnableListener =   buttonEnableListener;
	}
	
	public void setWidth(int width)
	{
		m_ListAvailable.setFixedCellWidth(width);
		m_ListSelected.setFixedCellWidth(width);
	}
	
}

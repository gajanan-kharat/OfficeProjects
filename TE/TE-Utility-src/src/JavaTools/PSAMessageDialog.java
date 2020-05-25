//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSAMessageDialog.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for displaying message Dialog.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools;

import com.psa.tools.PSALanguageHandler;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Added scroller bar for message text area. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Added scroller bar for message text area. [Mahendra Chuttar Geometric Limited 10-Dec-2010]


/**
 * Class for displaying message Dialog.
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSAMessageDialog {
	private JButton Button1;
	private JButton Button2;
	private JButton Button3;
	private JLabel Label;
	// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Used text area for message display. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
	private JTextArea TextArea;
	// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Used text area for message display. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
	JDialog dlg;
	
	private OPTION	sel_option;
	public enum OPTION
	{
		OK_OPTION,
		CANCEL_OPTION,
		YES_OPTION,
		NO_OPTION
	}
	public enum BUTTON_OPTION
	{
		OK_BUTTON_OPTION,
		OK_CANCEL_OPTION,
		YES_NO_OPTION,
		YES_NO_CANCEL_OPTION,
		CONTINUE_CANCEL_OPTION
	}
	
	
	/**
	 * Constructor of class
	 */
	public PSAMessageDialog()
	{
		
	}
	
	/**
	 * Displays the Information Message Box with OK button.
	 * @param parent				Parent Window
	 * @param msg					message to be display
	 * @param iLanguageHandler		Language Handler Object
	 * @return	Selected Option 
	 */
	public OPTION showMessageDialog(JFrame parent,String msg,PSALanguageHandler iLanguageHandler){
		System.out.println("PSAMessageDialog::showMessageDialog");
		String title = "Error";
		title = iLanguageHandler.Getlabel("Title.ErrorMessage",title);
		System.out.println(msg);
		String[] message = {msg};
		CreateUI(parent, message, title, "", BUTTON_OPTION.OK_BUTTON_OPTION,iLanguageHandler);
		return sel_option;
	}
	
	/**
	 * Displays the Information Message Box with OK button.
	 * @param parent				Parent Window
	 * @param msg					message array to be display
	 * @param iLanguageHandler		Language Handler Object
	 * @return	Selected Option 
	 */
	public OPTION showMessageDialog(JFrame parent,String[] msg,PSALanguageHandler iLanguageHandler){
		System.out.println("PSAMessageDialog::showMessageDialog");
		String title = "Error";
		title = iLanguageHandler.Getlabel("Title.ErrorMessage",title);
		CreateUI(parent, msg, title, "", BUTTON_OPTION.OK_BUTTON_OPTION,iLanguageHandler);
		return sel_option;
	}

	/**
	 * Displays the message Box with the buttons specified in option.
	 * @param parent				Parent Window
	 * @param msg					message to be display
	 * @param title					Title of the message dialog
	 * @param option				Option for diaplsying buttons in dialog
	 * @param iLanguageHandler		Language Handler Object
	 * @return	selected option
	 */
	public OPTION showMessageDialog(JFrame parent,String msg, String title, BUTTON_OPTION option,PSALanguageHandler iLanguageHandler){
		System.out.println("PSAMessageDialog::showMessageDialog");
		String[] message = {msg};
		CreateUI(parent, message, title, "", option,iLanguageHandler);
		return sel_option;
	}
	
	/**
	 * Displays the message Box with the buttons specified in option.
	 * @param parent				Parent Window
	 * @param msg					message[] to be display
	 * @param title					Title of the message dialog
	 * @param option				Option for diaplsying buttons in dialog
	 * @param iLanguageHandler		Language Handler Object
	 * @return	selected option
	 */
	public OPTION showMessageDialog(JFrame parent,String[] msg, String title, BUTTON_OPTION option,PSALanguageHandler iLanguageHandler){
		System.out.println("PSAMessageDialog::showMessageDialog");
		CreateUI(parent, msg, title, "", option,iLanguageHandler);
		return sel_option;
	}
	
	/**
	 * Displays the message Box with the buttons specified in option.
	 * @param parent				Parent Window
	 * @param msg					message[] to be display
	 * @param title					Title of the message dialog
	 * @param label 				Label to display for multiple messages
	 * @param option				Option for diaplsying buttons in dialog
	 * @param iLanguageHandler		Language Handler Object
	 * @return	selected option
	 */
	public OPTION showMessageDialog(JFrame parent,String[] msg, String title, String label, BUTTON_OPTION option,PSALanguageHandler iLanguageHandler){
		System.out.println("PSAMessageDialog::showMessageDialog");
		CreateUI(parent, msg, title, label, option,iLanguageHandler);
		return sel_option;
	}
	
	/**
	 * Creates UI for displaying message.
	 * @param parent		Parent Window
	 * @param message		message to be display
	 * @param title			Title of the message dialog
	 * @param opt			Option for diaplsying buttons in dialog
	 * @param lang_handler	Language Handler Object
	 */
	private void CreateUI(JFrame parent, String[] message, String title, String label, BUTTON_OPTION opt,PSALanguageHandler lang_handler) {
			try {
				System.out.println("PSAMessageDialog::CreateUI");
				
				dlg = new JDialog();
				dlg.setModal(true);
				// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Set message dialog always on top. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
				dlg.setAlwaysOnTop(true); 
				// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Set message dialog always on top. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
				GridBagLayout thisLayout = new GridBagLayout();
				thisLayout.rowWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.1};
				thisLayout.rowHeights = new int[] {7, 46, 35, 42, 20};
				thisLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.0, 0.1};
				thisLayout.columnWidths = new int[] {75, 75, 75, 75, 75};
				dlg.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				dlg.setBackground(new java.awt.Color(236,233,216));
				dlg.getContentPane().setLayout(thisLayout);
				dlg.setTitle(title);
				int button_pos = 3;
				int dlg_height = 164;
				
				if(label.length() > 0){

					thisLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
					thisLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
					dlg.getContentPane().setLayout(thisLayout);
					
					Label = new JLabel();
					dlg.getContentPane().add(Label, new GridBagConstraints(0, 1, 5, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
					Label.setHorizontalTextPosition(SwingConstants.CENTER);
					Label.setHorizontalAlignment(SwingConstants.CENTER);
					Label.setText(label);
					
					JTextArea TextArea;
					TextArea = new JTextArea(5, 10);
					String temp = "";
					int iLine=0;
					for (int i = 0; i < message.length; i++)
					{
						if(message[i]!=null){
							if(0 != iLine)
							{
								temp += "\n";
							}						
							temp += message[i];
							iLine++;
						}
					}
					System.out.println(temp);
					TextArea.setText(temp);
					TextArea.setEditable(false);
					TextArea.setCaretPosition(0);
					//TextArea.setBackground(new java.awt.Color(236, 233, 216));
					JScrollPane TextscrollPane = new JScrollPane(TextArea);
					dlg.getContentPane().add(TextscrollPane, new GridBagConstraints(0, 2, 5, 2, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
					TextscrollPane.setViewportView(TextArea);
					button_pos = 4;
					dlg_height = 200;
				} else
				{
					Label = new JLabel();
					dlg.getContentPane().add(Label, new GridBagConstraints(0, 1, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 5, 10, 5), 0, 0));
					Label.setAutoscrolls(true);
					String temp = "<HTML>";
					for (int i = 0; i < message.length; i++)
					{
						message[i] = message[i].replaceAll("\\n", "</BR><BR>");
						temp += "<BR>";
						temp += message[i];
						temp += "</BR>";
					}
					temp += "</HTML>";
					System.out.println(temp);
					Label.setText(temp);
					Label.setHorizontalAlignment(SwingConstants.CENTER);
					button_pos = 3;
				}
				
				switch(opt){
				//Option YES_NO_CANCEL_OPTION, so creation of buttons Yes, No and Cancel
				case YES_NO_CANCEL_OPTION:
					Button1 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button1, new GridBagConstraints(1, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button1.setText(lang_handler.Getlabel("Label.YesBtn","Yes"));
					Button1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.YES_OPTION;
						}
					});
					
					Button2 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button2, new GridBagConstraints(2, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button2.setText(lang_handler.Getlabel("Label.NoBtn","Aucun"));
					Button2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.NO_OPTION;
						}
					});
					
					Button3 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button3, new GridBagConstraints(3, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button3.setText(lang_handler.Getlabel("Label.AnnulerBtn","Annuler"));
					Button3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.CANCEL_OPTION;
						}
					});
					break;
					
				//Option YES_NO_OPTION, so creation of buttons Yes and No
				case YES_NO_OPTION:
					Button1 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button1, new GridBagConstraints(1, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button1.setText(lang_handler.Getlabel("Label.YesBtn","Yes"));
					Button1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.YES_OPTION;
						}
					});
					
					Button3 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button3, new GridBagConstraints(3, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button3.setText(lang_handler.Getlabel("Label.NoBtn","Aucun"));
					Button3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.NO_OPTION;
						}
					});
					break;

				//Option OK_CANCEL_OPTION, so creation of buttons OK and CANCEL
				case OK_CANCEL_OPTION:
					Button1 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button1, new GridBagConstraints(1, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button1.setText(lang_handler.Getlabel("Label.OKButton","OK"));
					Button1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.OK_OPTION;
						}
					});
					
					Button3 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button3, new GridBagConstraints(3, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button3.setText(lang_handler.Getlabel("Label.AnnulerBtn","Annuler"));
					Button3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.CANCEL_OPTION;
						}
					});
					break;
					
				//Option OK_BUTTON_OPTION, so creation of button OK.
				case OK_BUTTON_OPTION:
					Button2 = new JButton();
					// --> Debut Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					dlg.getContentPane().add(Button2, new GridBagConstraints(2, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Label Zone and Message Zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
					Button2.setText(lang_handler.Getlabel("Label.OKButton","OK"));
					Button2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option =OPTION.OK_OPTION;
						}
					});
					break;
				// --> Debut Modif [179B0] Added Continue - Cancel option buttons. [M.Chuttar GL 21-May-2012]
				case CONTINUE_CANCEL_OPTION:
					//Set label color to RED. 
					Label.setForeground(Color.RED);
					Button1 = new JButton();
					dlg.getContentPane().add(Button1, new GridBagConstraints(1, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					Button1.setText(lang_handler.Getlabel("Label.ContinueButton","Continuer"));
					Button1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.OK_OPTION;
						}
					});
					
					Button3 = new JButton();
					dlg.getContentPane().add(Button3, new GridBagConstraints(3, button_pos, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
					Button3.setText(lang_handler.Getlabel("Label.AbortBtn","Abandonner"));
					Button3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							sel_option = OPTION.CANCEL_OPTION;
						}
					});
					break;
				// --> Fin Modif [179B0] Added Continue - Cancel option buttons. [M.Chuttar GL 21-May-2012]
				}
				
				
				dlg.setSize(430, dlg_height);
				// --> Fin Modif [159AN - Gestion d'Acces à la MNU] Modified panel for Message Zone, Added scroll bar to message zone. [Mahendra Chuttar Geometric Limited 10-Dec-2010]
				dlg.pack();
				dlg.setLocationRelativeTo(parent);
				dlg.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

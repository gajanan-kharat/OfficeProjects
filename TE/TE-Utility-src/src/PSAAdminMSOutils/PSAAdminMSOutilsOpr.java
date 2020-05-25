/**********************************************************************************************************
 *
 * FILE NAME	  : PSAAdminMSOutilOpr.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to do database operations related to requests.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

//Standard class import
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.JOptionPane;

//User class import
import com.psa.PSAAdminMSOutils.PSARequestBasePanel.ACTION;
import com.psa.DBInterface.*;
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.LaunchDate;

/**
 * Class Used in Estimate REquest, to store the part details. 
 */
class Part_Details
{
	String m_StrCOID;			//COID
	String m_StrCOMPID;			//COMPID
	String m_StrEnv;			//Environmenr
	String m_StrTable;			//Table Name
}

/**
 * Class Used in Estimate REquest, to store the filter part with its child parts. 
 */
class FilterParts
{
	Part_Details filter_part =  new Part_Details();							//Filter part
	ArrayList<Part_Details> child_parts = new ArrayList<Part_Details>();	//Child parts
}

/**
 * Class used to store subscription Environment and tables.
 */
class SubEnvTables{
	String strEnv; 				  //Name of the VPM environment
	ArrayList<String> listTables; //List of tables configured for subscription
	String strCoid; 			  //List of COIDS to be quired for Size (excluding PART_LIST).
	int nCntPart;				  //Part Count
	int nCntV4;					  //V4 count
	int nCntV5;					  //V5 Count
	int nCntDoc;				  //Doc Count
	double dSizeV5;				  //Size for V4 parts
	double dSizeV4;				  //Size for V5 parts
	double dSizeDoc;			  //Size of Doc.
	
	/**
	 * Constructor 
	 */
	SubEnvTables()
	{
		nCntPart = 0;
		nCntV4 = 0;
		nCntV5 = 0;
		nCntDoc = 0;
		dSizeV5 = dSizeV4 = dSizeDoc = 0.0;
		strEnv ="";
		strCoid = "";
		listTables = new ArrayList<String>();
	}
};

/**
 * Class used to get Estimateion Data per site.
 */
class EstimationData
{
	String strSite;			//Site
	int nTotPart;			//Total number of parts
	int nTotV4;				//Total V4 parts
	int nTotV5;				//Total V5 parts
	int nTotDoc;			//Total Doc parts
	double dVolume;			//Volume 
	double maxAllowedSize;	//Max allowed size for site.
	
	/**
	 * Constructor
	 */
	EstimationData()
	{
		strSite = "";
		nTotPart = nTotV4 = nTotV5 = nTotDoc = 0;
		dVolume = 0.0;
		maxAllowedSize = 0;		
	}
}
/**
 * 
 *Class used to read ,insert ,update and delete request information from database.
 *
 */
public class PSAAdminMSOutilsOpr {

	private static final String infinateDate = "9999-12-31"; 					//Infinite Date
	private static String m_StrUser = "";										//User ID
	private static String m_StrRole = "";										//User Role
	private static String m_StrOrganization = "";								//User Organization
	private static String m_StrCurrentSite = "";								//Site
	private static JDBInterface dbCon = new JDBInterface();						//Database connection object.
	public String m_StrReqID = "";												//Request ID
	public String m_StrReqUserID = "";											//Request Creator ID		
	private String m_StrReqName = "";											//Request Name		
	private String m_StrMOD = "";												//Request Mode
	private String m_StrStatus = "";											//Request status
	private String m_StrNamePSN = "";											//PSN File name
	private String m_StrExpiryDate = "";										//Expiry Date
	private String m_StrNextLaunchDate = "";									//Next Launch Date
	private String m_StrLock = "";												//Lock status
	private ReqFreq m_REQFreq = new ReqFreq();									//Frequency of Request

	private ArrayList<String> m_ListSelSite = new ArrayList<String>();			//Selected site list
	private ArrayList<String> m_ListActiveSite = new ArrayList<String>();//Configured site list
		
	private ArrayList<PSNPart> m_ListAllParts = new ArrayList<PSNPart>();		//All parts
	private ArrayList<PSNPart> m_ListFilterParts = new ArrayList<PSNPart>();	//Filtered parts
	private ArrayList<PSNPart> m_ListAddedParts = new ArrayList<PSNPart>();		//Parts added during Update Request
	private ArrayList<PSNPart> m_ListDeleteParts = new ArrayList<PSNPart>();	//Deleted parts
	private ArrayList<PSNPart> m_ListUpdatedParts = new ArrayList<PSNPart>();	//Modified parts

	private ArrayList<String> m_Added_SiteList = new ArrayList<String>(); 		//Added Sites during Update Request
	private ArrayList<String> m_Deleted_SiteList = new ArrayList<String>();		//Deleted Sites during Update Request

	private ArrayList<String> m_Active_RequestList = new ArrayList<String>(); 	//Active Request list
	private ArrayList<String> m_InActive_RequestList = new ArrayList<String>();	//InActive Request list

	static private ArrayList<SubEnvTables> m_listEnvTables = new ArrayList<SubEnvTables>();	//Env Table list  

	ArrayList<PSAFilteredPartDetails> m_filteredpartdetails = new ArrayList<PSAFilteredPartDetails>();
	
	private ReqFreq m_AddedREQFreq = new ReqFreq();								//Added Frequency during Update Request
	private ReqFreq m_DeletedREQFreq = new ReqFreq();							//Deleted Frequency during Update Request
	
	private PSNPart m_RootPart;										//Root Part
	private String m_StrEstimateFileName;							//Estimate file name
	static private String m_EnvLocal = "";
	static private String m_EnvLogname = "";
	static {
		m_EnvLocal = System.getenv("LOCAL");
		m_EnvLogname = System.getenv("LOGNAME");
	}
 
	/**
	 * Constructor of the class.
	 */
	public PSAAdminMSOutilsOpr()
	{
	}

	/**
	 * provides user id of request creator.
	 * @return String : Request User ID.
	 */
	public String getReqUserID() {
		return m_StrReqUserID;
	}
	/**
	 * Set user id of request creator.
	 */
	public void setReqUserID(String iStrReqUserID) {
		m_StrReqUserID = iStrReqUserID;
	}

	/**
	 * Get Request Name
	 * @return String : Request name.
	 */
	public String getRequestName()
	{
		return m_StrReqName;
	}

	/**
	 * Set the request name.
	 * @param iStrReqName : Request name.
	 */
	public void setRequestName(String iStrReqName)
	{
		m_StrReqName = iStrReqName;
	}

	/**
	 * Get Request Mode
	 * @return String : Request Mode.
	 */
	public String getRequestMod()
	{
		return m_StrMOD;
	}

	/**
	 * It set the request mode.
	 * @param iStrReqName : Request Mode.
	 */
	public void setRequestMod(String iStrMOD)
	{
		m_StrMOD = iStrMOD;
	}

	/**
	 * Provides request status
	 * @return String : Request Status.
	 */
	public String getRequestStatus()
	{
		return m_StrStatus;
	}

	/**
	 * It set the request status.
	 * @param iStrReqName : Request Status.
	 */
	public void setRequestStatus(String iStrStatus)
	{
		m_StrStatus = iStrStatus;
	}

	/**
	 * Provides PSN FIle Name
	 * @return String : PSN file name
	 */
	public String getPSNName()
	{
		return m_StrNamePSN;
	}

	/**
	 * It set the  PSN file name
	 * @param iStrPSNName  : PSN file name
	 */
	public void setPSNName(String iStrPSNName)
	{
		m_StrNamePSN = iStrPSNName;
	}

	/**
	 * Provides Expiry date of request
	 * @return String :Expiry Date
	 */
	public String getExpiryDate()
	{
		return m_StrExpiryDate;
	}
	
	/**
	 * It set the Expiry Date.
	 * @param iStrExpiryDate : Expiry Date
	 */
	public void setExpiryDate(String iStrExpiryDate)
	{
		m_StrExpiryDate = iStrExpiryDate;
	}

	/**
	 * Provides Lock status for the request.
	 * @return String : Lock Status
	 */
	public String getLockStatus()
	{
		return m_StrLock;
	}

	/**
	 * Provides size of filtering part list
	 * @return int : Size of filter part list size.
	 */
	public int getFilterPartListSize()
	{
		return m_ListFilterParts.size();
	}

	/**
	 * It returns arraylist of filter psnpart list.
	 * @return ArrayList<PSNPart> : Filter Parts List
	 */
	public ArrayList<PSNPart> getFilterParts() {
		return m_ListFilterParts;
	}
	
	/**
	 * It returns arraylist of filter parts parent list in oFilterpart.
	 * @param PSNPart 				 	child_part		Child Part
	 * @param PSAFilteredPartDetails 	oFilterpart		Filter Part
	 */
	private void getFilterPartParents(PSNPart child_part, PSAFilteredPartDetails oFilterpart)
	{
		int parent_count = child_part.m_listParent.size();
		for(int i=0; i<parent_count; i++)
		{
			PSNPart parent_part = child_part.m_listParent.get(i);
			parent_part.m_bIsInUse = true;
			if(parent_part.m_iDico == 1)
			{
				ParentDetails parentDetails = new ParentDetails();
				parentDetails.m_ParentRef = parent_part.m_StrRef;
				parentDetails.m_ParentCOID = parent_part.m_StrCOID;
				parentDetails.m_parentDesignation = parent_part.m_StrDesignation;
				parentDetails.m_parentEnv = parent_part.m_StrEnv;
				parentDetails.m_listCongHandler = parent_part.m_listConfigHandler;
				oFilterpart.m_parentList.add(parentDetails);
			}
			if(parent_part.m_listParent.size() > 0)
			{
				getFilterPartParents(parent_part, oFilterpart);
			}
		}
	}
	
	/**
	 * It returns arraylist of filter psnpart list.
	 * @return ArrayList<PSNPart> : Filter Parts List
	 */
	public ArrayList<PSAFilteredPartDetails> setFilterPartDetails() {
		
		m_filteredpartdetails.clear();
		int filteredpart_count = m_ListFilterParts.size();
		for(int i=0;i<filteredpart_count;i++)
		{
			PSAFilteredPartDetails filterpart = new PSAFilteredPartDetails();
			filterpart.m_part = m_ListFilterParts.get(i);
			filterpart.m_part.m_bIsInUse = true;
			if(filterpart.m_part.m_listParent.size() > 0)
			{
				filterpart.m_parentList = new ArrayList<ParentDetails>();
				getFilterPartParents(filterpart.m_part, filterpart);
			}
			m_filteredpartdetails.add(filterpart);
		}
		
		return m_filteredpartdetails;
	}

	public ArrayList<PSAFilteredPartDetails> getFilterPartDetails()
	{
		if(m_filteredpartdetails.size() == 0)
		{
			//Search for useful parts.
			setFilterPartDetails();
		}
		return m_filteredpartdetails;
	}
	/**
	 * It sets  filter psnpart list.
	 * @param m_ListFilterParts : Filter Parts List
	 */
	public void setFilterParts(ArrayList<PSNPart> m_ListFilterParts) {
		this.m_ListFilterParts = m_ListFilterParts;
	}

	/**
	 * Function to get the filtered part element from filtered part list
	 * @return PSNPart filtered part from filteredpart list specified at an index.  
	 */
	public PSNPart getFilterPartListElement(int index)
	{
		PSNPart part = null;
		if(index+1 <= m_ListFilterParts.size())
		{
			part = m_ListFilterParts.get(index);
		} 
		return part;
	}

	/**
	 * It used to add  part in deleted list.
	 * @param part :PSNPart
	 */
	public void addDeletePart(PSNPart part) {
		m_ListDeleteParts.add(part);	
		m_ListFilterParts.remove(part);
	}

	/**
	 * It returns deleted part list.
	 * @return ArrayList<PSNPart> 
	 */
	public ArrayList<PSNPart> getDeletedPartList()
	{
		return m_ListDeleteParts;
	}
	
	/**
	 * It returns the size of deleted part list.
	 * @return int
	 */
	public int getDeletedPartListSize()
	{
		return m_ListDeleteParts.size();
	}
	
	/**
	 * Set selected frequency details.
	 * @param String iStrFreqType					Frequency Type
	 * @param ArrayList<String> iListFreqValues		Frequency Values
	 */
	public void setFreq(String iStrFreqType, ArrayList<String> iListFreqValues)
	{
		m_REQFreq.m_StrType = iStrFreqType;
		m_REQFreq.m_listValues = iListFreqValues;
	}

	/**
	 * Set Added frequency details.
	 * @param String iStrFreqType					Frequency Type
	 * @param ArrayList<String> iListFreqValues		Frequency Values
	 */
	public void setAddedFreq(String iStrFreqType, ArrayList<String> iListFreqValues)
	{
		m_AddedREQFreq.m_StrType = iStrFreqType;
		m_AddedREQFreq.m_listValues = iListFreqValues;
	}

	/**
	 * Set Deleted frequency details.
	 * @param String iStrFreqType					Frequency Type
	 * @param ArrayList<String> iListFreqValues		Frequency Values
	 */
	public void setDeletedFreq(String iStrFreqType, ArrayList<String> iListFreqValues)
	{
		m_DeletedREQFreq.m_StrType = iStrFreqType;
		m_DeletedREQFreq.m_listValues = iListFreqValues;
	}

	/**
	 * Provides Frequncy type.
	 * @return String 	Frequency Type.
	 */
	public String getFreqType()
	{
		return m_REQFreq.m_StrType;
	}

	/**
	 * Provides Frequncy list.
	 * @return ArrayList<String> 	Frequency list.
	 */
	public ArrayList<String> getFreqList()
	{
		return m_REQFreq.m_listValues;
	}

	/**
	 * Sets added Sites.
	 * @param ArrayList<String> iListAddedSites		Added Site list
	 */
	public void setAddedSites(ArrayList<String> iListAddedSites)
	{
		m_Added_SiteList = iListAddedSites;
	}

	/**
	 * Sets Deleted Sites.
	 * @param ArrayList<String> iListDeletedSites		Deleted Site list
	 */
	public void setDeletedSites(ArrayList<String> iListDeletedSites)
	{
		m_Deleted_SiteList = iListDeletedSites;
	}

	/**
	 * Sets Selected Sites.
	 * @param ArrayList<String> selectedSiteList		Selected Site list
	 */
	public void setListSelSite(ArrayList<String> iselectedSiteList) 
	{
		m_ListSelSite = iselectedSiteList;
	}

	/**
	 * Provides list of selected Sites.
	 * @retuen ArrayList<String>		list of selected sites for request.
	 */
	public ArrayList<String> getListOfSelectedSites()
	{
		return m_ListSelSite;
	}

	/**
	 * Provides Root part ID.
	 * @return		Root part ID
	 */
	public String getRootPartID() 
	{
		return m_RootPart.m_StrRef;
	}

	/**
	 * Provides Root part Description.
	 * @return 	Root part description
	 */
	public String getRootDescription() {
		return m_RootPart.m_StrDesignation;
	}

	/**
	 * Provides Next Launch Date.
	 * @return 	Next Launch Date
	 */
	public String getNextLaunchDate()
	{
		return m_StrNextLaunchDate;
	}

	/**
	 * Sets Next Launch Date.
	 * @param 	String strNextLaunchDate		Next Launch Date
	 */
	public void setNextLaunchDate(String strNextLaunchDate)
	{
		m_StrNextLaunchDate = strNextLaunchDate;
	}

	/**
	 * THIS IS A STATIC METHOD.
	 * This function is a static method which validated the login user. Following validations are done.
	 * 1)Validates User Role as "REPLICATION" and USer Organization as "ADMIN"
	 * 2)Validates current site as "EUROPE"
	 * 
	 * @throws 	PSAErrorHandler			Returns the error code and the error label if any error is occurred.
	 */

	public static void ValidateUser() throws PSAErrorHandler
	{
		//Validates whether login user belongs to any organization.
		String strSQLQuery = "SELECT USER, R.\"name\", O.\"id\" FROM VPMDB2.\"person\" P, VPMDB2.\"role\" R, VPMDB2.\"organization\" O "
								+" WHERE P.\"id\" = USER AND P.\"current_role\" = R.oid AND R.\"belongs_to\" = O.oid";

		System.out.println("User Validation Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);

		//If there is any error,throw it.
		if((0 != status ) || (0 == dbCon.getRowCount()))
		{
			System.out.println("Unable to retrieve user information");
			throw new PSAErrorHandler(-1,"Error.InvalidUser");
		}

		m_StrUser = dbCon.getValue(1,1).trim();
		m_StrRole = dbCon.getValue(1,2).trim();
		m_StrOrganization = dbCon.getValue(1,3).trim();
		
		System.out.println("User : "+ m_StrUser +" Role :" + m_StrRole +" Organization :" + m_StrOrganization);

		if((m_StrRole.compareTo("REPLICATION") !=0) || (m_StrOrganization.compareTo("ADMIN") !=0))
		{
			System.out.println("User is not valid. To execute application Role must be REPLICATION and Organization must be ADMIN.");
			throw new PSAErrorHandler(-1,"Error.InvalidUser");
			
		}
		
		//Checks whether the current site is "EUROPE"
		strSQLQuery = "SELECT \"id\" FROM VPMDB2.\"current_site\"";
		System.out.println("Current site validation Query : " + strSQLQuery);

		//Execute the query.
		status = dbCon.ExecuteSelect(strSQLQuery);

		//If there is any error,throw it.
		if((0 != status ) || (0 == dbCon.getRowCount()))
		{
			System.out.println("Unable to retrieve current site information.");
			throw new PSAErrorHandler(-1,"Error.SiteRetrieveError");
		}

		m_StrCurrentSite = dbCon.getValue(1,1).trim();
		System.out.println("Current site  : "+ m_StrCurrentSite);
		
		if(m_StrCurrentSite.compareToIgnoreCase("EUROPE") != 0)
		{
			System.out.println("Current Site is not EUROPE.");
			throw new PSAErrorHandler(-1,"Error.InvalidUser");
		}
	}
	
	/**
	 * THIS IS A STATIC METHOD
	 * This function closes the connection if it is already opened.
	 * 
	 */
	public static void CloseDatabaseConnection() 
	{
		System.out.println("Opr->CloseDatabaseConnection");
		//Check if the database connection object is not null.
		if(dbCon != null)
		{
			//Disconnect the database connection.
			dbCon.disConnect();
			//Set the database connection object to null.
			dbCon = null;
		}
		System.out.println("Opr->CloseDatabaseConnection End");
	}

	/**
	 * THIS IS A STATIC METHOD
	 * This functions will read the valid requests of Mode "Init" and "VCO".
	 * For each request opr object will be created.
	 * These ope objects will be used to display requests in request list selection panel.
	 * @return ArrayList<PSAAdminMSOutilsOpr>		Oprs for the valid requests.
	 */
	public static ArrayList<PSAAdminMSOutilsOpr> QueryListOfActiveREQ() throws PSAErrorHandler
	{
		ArrayList<PSAAdminMSOutilsOpr> oprList =  new ArrayList<PSAAdminMSOutilsOpr>();

		//Retrieve Request Data.
		String strSQLQuery = "SELECT REQUEST_ID, REQUEST_NAME, ACTION_STATUS, EXEC_MODE, CREATOR FROM VPMDB2.MSADMIN_REQUEST WHERE ACTION_STATUS = "
							 + "'ACTIVE' AND EXEC_MODE IN ('INIT', 'VCO') AND VALIDITY_DATE >= CURRENT_TIMESTAMP";

		System.out.println("Query List Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		//If there is any error,throw it.
		if((0 != status ))
		{
			System.out.println("Unable to read request data from VPMDB2.MSADMIN_REQUEST.");
			throw new PSAErrorHandler(-1,"Error.UnabletoreadRequestData");
		}
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();
		if((0 == count))
		{
			System.out.println("There are no requests present in database.");
			throw new PSAErrorHandler(-1,"Error.GetRequestList");
		}

		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			PSAAdminMSOutilsOpr oprObj = new PSAAdminMSOutilsOpr();
			oprObj.m_StrReqID = dbCon.getValue(i, 1).trim();
			oprObj.m_StrReqName = dbCon.getValue(i, 2).trim();
			oprObj.m_StrStatus = dbCon.getValue(i, 3).trim();
			oprObj.m_StrMOD = dbCon.getValue(i, 4).trim();
			oprObj.m_StrReqUserID = dbCon.getValue(i, 5).trim();
			
			//Add in the list
			oprList.add(oprObj);
		}
		return oprList;
	}
	
	/**
	 * This function will read requests which are Valid with Mode "INIT" and "VCO" and status od request as "ACTIVE"
	 * or "INACTIVE".
	 * These list will be used in Block / unblock request panel.
	 * @param ArrayList<String> oListActiveRequest			List of Active requests.
	 * @param ArrayList<String> oListInActiveRequest		List of InActive requests.
	 */
	public void QueryListOfREQ(ArrayList<String> oListActiveRequest, ArrayList<String> oListInActiveRequest) throws PSAErrorHandler
	{
		//Retrieve Request Data.
		String strSQLQuery = "SELECT REQUEST_NAME, ACTION_STATUS FROM VPMDB2.MSADMIN_REQUEST WHERE ACTION_STATUS IN "
			 + "('ACTIVE', 'INACTIVE') AND EXEC_MODE IN ('INIT', 'VCO') AND VALIDITY_DATE >= CURRENT_TIMESTAMP";

		System.out.println("Query List Query : " + strSQLQuery);

		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);

		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("There are no requests present in database.");
			throw new PSAErrorHandler(-1,"Error.GetRequestList");
		}

		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			String StrReqName = dbCon.getValue(i, 1).trim();
			String StrStatus = dbCon.getValue(i, 2).trim();
			//Add in the list
			if(StrStatus.compareTo("ACTIVE") == 0)
			{
				oListActiveRequest.add(StrReqName);
			} else 
			{
				oListInActiveRequest.add(StrReqName);
			}
		}
	}	

	/**
	 * THIS IS A STATIS FUNCTION
	 * This function will read a configured sites which are ACTIVE.
	 */
	public ArrayList<String> GetListOfActiveSite() throws PSAErrorHandler
	{
		m_ListActiveSite.clear();
		//Retrieve Sites Configured.
		String strSQLQuery = "SELECT DISTINCT REF_ID, REF_VAL FROM GESTION.FGESTION WHERE $COID="
							 + "(SELECT $COID FROM GESTION.PART_LIST WHERE S_REFERENCE='MSBASEDIST')";
		
		System.out.println("Get Site List Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to retrieve Active Sites Request List.");
			throw new PSAErrorHandler(-1,"Error.RetrieveActiveSites");
		}
		
		String Site = "";
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			Site = dbCon.getValue(i, 1).trim();
			String val = dbCon.getValue(i, 2).trim();
			
			if(val.equals("ACTIVE"))
			{
				//Add in the list
				m_ListActiveSite.add(Site);
				System.out.println(Site);
			}
		}
		return m_ListActiveSite;
	}
	
	/**
	 * Reads the request details for the selected request.
	 */
	public void ReadRequestDetails() throws PSAErrorHandler
	{
		//Retrieve Request Data.
		String strSQLQuery = "SELECT REQUEST_NAME, ACTION_STATUS, EXEC_MODE, CREATOR, VALIDITY_DATE, NEXT_LAUNCH_DATE, LOCK_FLAG, PSN_NAME FROM VPMDB2.MSADMIN_REQUEST WHERE REQUEST_ID = X'" + m_StrReqID + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to get request details.");
			throw new PSAErrorHandler(-1,"Error.GetRequestdetails");
		}
		
		m_StrReqName = dbCon.getValue(1, 1).trim();
		m_StrStatus = dbCon.getValue(1, 2).trim();
		m_StrMOD = dbCon.getValue(1, 3).trim();
		m_StrReqUserID = dbCon.getValue(1, 4).trim();

		//Read Expiry Date
		m_StrExpiryDate = dbCon.getValue(1, 5).trim();
		m_StrExpiryDate = m_StrExpiryDate.substring(0, 10);	//Expiry date in yyyy-MM-dd format.
		if(m_StrExpiryDate.compareTo(infinateDate) == 0)
		{
			//Expiry Date is Infinite so set it to blank.
			m_StrExpiryDate = "";
		} else 
		{
			m_StrExpiryDate = m_StrExpiryDate.replace('-', '/');
		}
		System.out.println("Expiry Date : "+m_StrExpiryDate);
		
		//Read Next Launch Date
		m_StrNextLaunchDate = dbCon.getValue(1, 6).trim();
		m_StrNextLaunchDate = m_StrNextLaunchDate.substring(0, 10);	//Next Launch date date in yyyy-MM-dd format.
		m_StrNextLaunchDate = m_StrNextLaunchDate.replace('-', '/');
		System.out.println("Next Launch Date : "+m_StrNextLaunchDate);

		//Read Lock Status
		m_StrLock = dbCon.getValue(1, 7).trim();
		System.out.println("Lock Flag : "+m_StrLock);

		String PSNFileName = dbCon.getValue(1, 8); 
		if((PSNFileName != null) && (PSNFileName.length() > 0))
		{
			m_StrNamePSN = PSNFileName.trim();
			System.out.println("PSN File Name : "+m_StrNamePSN);
		}
	}

	/**
	 * Read the parent reference.
	 */
	private String GetReference (String strEnv, String strCOID) throws PSAErrorHandler
	{
		//Retrieve part reference.
		String strSQLQuery = "SELECT S_REFERENCE FROM " + strEnv +".PART_LIST WHERE $COID= X'" + strCOID + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to retrieve part reference.");
			throw new PSAErrorHandler(-1,"Error.ReferenceRetrieveError");
		}
		
		//Read part details.
		String strRef = dbCon.getValue(1, 1).trim();
		return strRef;
	}
	
	/**
	 * Reads part details (Reference, Designation etc.) from PART_LIST table required to display in UI.
	 * @param PSNPart part		Part for which details to be read.
	 */
	private void GetPartDetails(PSNPart part, int iReadParentRef) throws PSAErrorHandler
	{
		//Retrieve part reference.
		String strSQLQuery = "SELECT S_REFERENCE, DESIGNATION, $COMPID FROM " + part.m_StrEnv+".PART_LIST WHERE $COID= X'" + part.m_StrCOID + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to retrieve part reference.");
			throw new PSAErrorHandler(-1,"Error.ReferenceRetrieveError");
		}
		
		//Read part details.
		part.m_StrRef = dbCon.getValue(1, 1).trim();
		part.m_StrDesignation = dbCon.getValue(1, 2).trim();
		part.m_StrCOMPID = dbCon.getValue(1, 3).trim();
		
		//Store root part details.
		if(part.m_iLevel == 0)
		{
			m_RootPart = part;
		}
		
		//For estimate Data read parent details
		if((iReadParentRef == 1) && (part.m_StrSelectedParentCOID.length() > 0) && (part.m_StrSelectedParentEnv.length() > 0))
		{
			//Retrieve part reference.
			strSQLQuery = "SELECT S_REFERENCE, DESIGNATION FROM " + part.m_StrSelectedParentEnv+".PART_LIST WHERE $COID= X'" + part.m_StrSelectedParentCOID + "'";
			
			System.out.println("Query : " + strSQLQuery);
			//Execute the Query
			status = dbCon.ExecuteSelect(strSQLQuery);
			
			//Get the resulting count of the executed query.
			count = dbCon.getRowCount();

			//If there is any error,throw it.
			if((0 != status ) || (0 == count))
			{
				System.out.println("Unable to retrieve partent reference.");
				throw new PSAErrorHandler(-1,"Error.ParentReferenceRetrieveError");
			}
			
			//Read part details.
			part.m_StrSelectedParentREF = dbCon.getValue(1, 1).trim();
			part.m_StrSelectedParentDesignation = dbCon.getValue(1, 2).trim();
		}
	}

	/**
	 * This function reads the parent details for part of the CSV file data.
	 * This will get execute in CREATE REQUEST or in case user specified PSN file in UPDATE REQUEST.
	 */
	private int readCSVParentDetails() throws PSAErrorHandler
	{
		System.out.println("readCSVParentDetails start");
		int retVal = 0;
		int j = 0;
		for(PSNPart part :m_ListAllParts )
		{
			//For each link COID's in part.
			for(j = 0; j < part.m_listLinkCOID.size(); j++)
			{
				String strCOID = part.m_listLinkCOID.get(j);
				for(PSNPart parent_part : m_ListAllParts)
				{
					//Compare COID from linkCOID with Child COID's
					if(strCOID.equalsIgnoreCase(parent_part.m_StrCOID))
					{
						//part.m_listParent.add(parent_part);
						//Add part as parent to child.
						boolean bPartFound = false;
						for (PSNPart parts_Parentpart : part.m_listParent)
						{
							if(parts_Parentpart.IsEqual(parent_part))
							{
								bPartFound = true;
								break;
							}	
						}
						if(bPartFound == false)
						{
							//Add and parent and child part details
							part.m_listParent.add(parent_part);
							parent_part.m_listChild.add(part);
						}
						
						break;
					}
				}
			}
		}
		
		
		//Print part details
		for(PSNPart part :m_ListAllParts)
		{
			part.trace();
		}
		System.out.println("readCSVParentDetails End");
		return retVal;
	}
	
	/**
	 * This function reads the parts from CSV file which is output of AT0EXPND.
	 * @param String iStrCSVFIleName			CSV File Name
	 */
	private int readCSVFile(String iStrCSVFIleName, ACTION iActionType)
	{
		System.out.println("readCSVFile Start");
		int retVal = 0;
	    try{
			// Open output file generated from AT0EXPND.
			// command line parameter
			FileInputStream fstream = new FileInputStream(iStrCSVFIleName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			if(iActionType == ACTION.UPDATEREQUEST)
			{
				//Read root part.
				strLine = br.readLine();
				if(strLine.length() > 0)
				{
					if(ValidateRootPart(strLine) == false)
					{
						//Close the input stream
						in.close();
						
						//Delete temporary file
						File f1 = new File(iStrCSVFIleName);
					    boolean success = f1.delete();
					    if(!success)
					    {
					    	System.out.println("Error in deleting temporary file " + iStrCSVFIleName);
					    }

					    System.out.println("Incorrect PSN FIle, invalid root part.");
						throw new PSAErrorHandler(-1,"Error.InvalidRootPart");
					}
				}
			}
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   
			{
				if(strLine.length() > 0)
				{
					String []dataArray = strLine.split(";");
					boolean bDuplicate = false;
					if(dataArray.length >= 9)
					{
						PSNPart part = new PSNPart();
						part.m_StrEnv = dataArray[0].trim();
						part.m_StrTable = dataArray[1].trim();
						part.m_StrCOID = dataArray[2].trim();
						part.m_StrCOMPID = dataArray[3].trim();

						for (PSNPart existing_part : m_ListAllParts)
						{
							if(existing_part.IsEqual(part))
							{
								bDuplicate = true;
								if( ! (existing_part.m_listLinkCOID.contains(dataArray[6]) 
									   && existing_part.m_listLinkCOMPID.contains(dataArray[7])))
								{
									existing_part.m_listLinkCOID.add(dataArray[6]);
									existing_part.m_listLinkCOMPID.add(dataArray[7]);
								}
								break;
							}
						}
						if(false == bDuplicate)
						{
							part.m_iLevel = Integer.parseInt(dataArray[4])-1;	//Check this
							part.m_bSelect = (Integer.parseInt(dataArray[5]) ==  1)? true : false;
							part.m_listLinkCOID.add(dataArray[6]);
							part.m_listLinkCOMPID.add(dataArray[7]);
							part.m_iDico = Integer.parseInt(dataArray[8]);
							if((part.m_iDico  == 1) && dataArray.length == 10)
							{
								String []CongHandlers = dataArray[9].split(":");
								for (String conf : CongHandlers)
								{
									part.m_listConfigHandler.add(conf);
								}
							}
							//Retrieve Part details.
							GetPartDetails(part, 0);
							
							//If part is selected add to Filtered part list.
							if(part.m_bSelect == true)
							{
								part.m_StrType = "FILTERING";
								m_ListFilterParts.add(part);
							} else 
							{
								part.m_StrType = "HIGH LEVEL";
							}
							System.out.println("Part Type: " +part.m_StrType);
							
							
							//Add Part to list
							m_ListAllParts.add(part);
						}
					}
				}
			}
			
			//Close the input stream
			in.close();

			//Retrieve Parts parent details.
			readCSVParentDetails();
			
		}catch (Exception e){
			retVal = -1;
			System.err.println("Error in reading Output file of AT0EXPND.");
		}
		
		//Delete temporary file
		File f1 = new File(iStrCSVFIleName);
	    boolean success = f1.delete();
	    if(!success)
	    {
	    	System.out.println("Error in deleting temporary file " + iStrCSVFIleName);
	    }
	    
		System.out.println("readCSVFile End");
	    return retVal;
	}
	
	/**
	 * This function will read parent details for the parts read from VPMDB2.MSADMIN_ASSLINK 
	 * which are stored during CREATE REQUEST.
	 */
	private void readParentDetails() throws PSAErrorHandler
	{
		System.out.println("readParentDetails Start");
		int nCount = m_ListAllParts.size();
		int i = 0;
		
		//for each part in the list get the children COID and then from the list formulate the link list
		for(i = 0; i < nCount; i++)
		{
			//Skip the processing for the part of type filtering as these are the leaf nodes
			//if(m_ListFilterParts.contains(m_ListAllParts.get(i)) == true)
				//continue;
				
			//Skip Root part
			if(m_ListAllParts.get(i).m_iLevel == 0)
				continue;
			String strSQLQuery = "SELECT $COID, $COMPID FROM " + m_ListAllParts.get(i).m_StrEnv + 
								".$EXT WHERE $TYPE='CATASS' AND $COMPID IN (SELECT LINK_COMPID FROM VPMDB2.MSADMIN_ASSLINK WHERE PART_COID=X'" +
								m_ListAllParts.get(i).m_StrCOID + "' AND REQUEST_ID = X'" + m_StrReqID + "')" ;
			
			System.out.println("Query : " + strSQLQuery);
			
			//Execute the Query
			int status = dbCon.ExecuteSelect(strSQLQuery);
			if(0 != status)
			{
				System.out.println("Unable to Retrive data from VPMDB2.MSADMIN_ASSLINK");
				throw new PSAErrorHandler(-1,"Error.PartDataRetrieve");
			}
			//Get the result from query
			int nParent = dbCon.getRowCount();
			System.out.println("No of parents for " + m_ListAllParts.get(i).m_StrRef + " are :" + nParent);
			//Skip if no children found
			if(0 == nParent)
			{
				System.out.println("No Parent for the HigherLevel part" + m_ListAllParts.get(i).m_StrCOID);
				//throw new PSAErrorHandler(-1,"Error.ChildNotFound");
			}
			else
			{
				//for each child found create the link-list for the given part
				for(int j = 1; j <= nParent; j++)
				{
					String coidparent = dbCon.getValue(j, 1).trim();
					String compidparent = dbCon.getValue(j, 2).trim();
					System.out.println("coidparent : " + coidparent + " compidparent :" + compidparent);
					//Search the given coid in the list to find the exact child
					for(int k = 0; k < nCount; k++)
					{
						//Check if the Coid match to get the child
						if(coidparent.equalsIgnoreCase(m_ListAllParts.get(k).m_StrCOID))
						{
							//Store the part in the concerned child
							m_ListAllParts.get(i).m_listParent.add(m_ListAllParts.get(k));
							
							//Store link COMPID.
							m_ListAllParts.get(i).m_listLinkCOMPID.add(compidparent);
							
							//Store part as a child of its parent.
							m_ListAllParts.get(k).m_listChild.add(m_ListAllParts.get(i));
							System.out.println("Part COID : " + coidparent + " Parent COID :" + m_ListAllParts.get(i).m_StrCOID);
						}
					}
				}
			}
		}
		
		//Display part details with parent and child information
		for(i = 0; i < nCount; i++)
		{
			m_ListAllParts.get(i).trace();
		}
		System.out.println("readParentDetails End");
	}

	/**
	 * This function executes PSAReadRacineConf application to get the config Handlers from VPM.
	 * 
	 * @param PSNPart part	Part for which config Handlers has to read.
	 * 
	 * @return	provides the returned value of PSAReadRacineConf. 
	 */		
	private int GetConfigHandlers(PSNPart part)
	{
		System.out.println("Opr->GetConfigHandlers Start");

		String StrFileName = m_EnvLocal +"/" + m_EnvLogname + "/GetConfigHandler" + "_" + System. currentTimeMillis();
		System.out.println("Output File Name : "+StrFileName);
		int istatus = 0;
        try {
            Runtime rt = Runtime.getRuntime();
            String strCommand = "PSAReadRacineConf " + m_StrUser + " " +m_StrRole + " " + m_StrOrganization + " " + part.m_StrRef + " " + StrFileName;
            System.out.println("Executing command : " + strCommand);
            Process pr = rt.exec(strCommand);

            //Read Input Stream 
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line=null;
            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            //Read Error Stream 
            BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String error_line=null;
            while((error_line=error.readLine()) != null) {
                System.out.println(error_line);
            }
            
            int exitVal = pr.waitFor();
			
            System.out.println("PSAReadRacineConf exited with error code "+exitVal);
            istatus = exitVal;

            //Close all stream buffers.
            pr.getInputStream().close();
            pr.getOutputStream().close();
            pr.getErrorStream().close();
            
            if(istatus == 0)
            {
				// Open output file generated from PSAReadRacineConf.
				// command line parameter
				FileInputStream fstream = new FileInputStream(StrFileName);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)   
				{
					if(strLine.length() > 0){
						part.m_listConfigHandler.add(strLine);
					}
				}
				//Close the input stream
				in.close();
				
				//Delete temporary file
				File f1 = new File(StrFileName);
			    boolean success = f1.delete();
			    if(!success)
			    {
			    	System.out.println("Error in deleting temporary file " + StrFileName);
			    }
            }
        } catch(Exception e) {
            istatus = -1;
            System.out.println("Error in executing PSAReadRacineConf");
        }
		System.out.println("Opr->GetConfigHandlers End");
		return istatus;
	}
	
	/**
	 * This function will read part details from VPMDB2.MSADMIN_PARAM 
	 * which are stored during CREATE REQUEST.
	 * @param ACTION iActionType		Action Type
	 */
	private void readFilteredPartData(ACTION iActionType) throws PSAErrorHandler
	{
		//Retrieve Part Data. in the asending order the level. (this implies that ROOT part is at row one)
		String strSQLQuery = "SELECT ID_PART, ENV_PART, LEVEL_PART, DICO_PART, CH_REQUEST, PART_CH, ENV_PART_CH FROM VPMDB2.MSADMIN_PARAM WHERE REQUEST_ID = X'" + m_StrReqID + "' AND (TYPE_REQ_PART = 'FILTERING' OR LEVEL_PART = 0)";

		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);

		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to Retrive data from VPMDB2.MSADMIN_PARAM");
			throw new PSAErrorHandler(-1,"Error.PartDataRetrieve");
		}

		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			//Read parts details.
			PSNPart part = new PSNPart();
			part.m_StrCOID = dbCon.getValue(i, 1).trim();						// PART COID
			part.m_StrEnv = dbCon.getValue(i, 2).trim();						// PART Env
			part.m_iLevel = Integer.parseInt(dbCon.getValue(i, 3).trim());		// Level 
			part.m_iDico = Integer.parseInt(dbCon.getValue(i, 4).trim());		// DICO Status
			
			part.m_StrSelectedParentCOID = dbCon.getValue(i, 6).trim(); 	// Selected Parent COID
			//Set the values to NULL if no config handler is associated to the Filtering part. 
			if(part.m_StrSelectedParentCOID.equals("0000000000000000")) 
			{ 
				part.m_StrSelectedParentCOID = ""; 
				part.m_StrSelectedParentEnv=""; 
				part.m_StrSelectedConf=""; 
			}
			else 
			{
				//Read selected parent and Config Handler details. 
				String str = dbCon.getValue(i, 5);
				if(str.length() > 0)
					part.m_StrSelectedConf = str.trim(); 			// Selected Config Handler
				
				str = dbCon.getValue(i, 7);
				if(str.length() > 0)
					part.m_StrSelectedParentEnv = str.trim(); 		// Selected parent Env. 
			}

			//Add only Filtering parts
			if(part.m_iLevel != 0)
			{
				m_ListFilterParts.add(part);
			}
			
			//Add Filtering Part and root part
			m_ListAllParts.add(part);
		}
		
		for(PSNPart part : m_ListAllParts)
		{
			//Read part refernece and selected parent reference.
			GetPartDetails(part, 1);
		}
	}
	
	/**
	 * This function will read part details from VPMDB2.MSADMIN_PARAM 
	 * which are stored during CREATE REQUEST.
	 * @param ACTION iActionType		Action Type
	 */
	private void readPartData(ACTION iActionType) throws PSAErrorHandler
	{
		//Retrieve Part Data. in the asending order the level. (this implies that ROOT part is at row one)
		String strSQLQuery = "SELECT ID_PART, ENV_PART, LEVEL_PART, DICO_PART, TYPE_REQ_PART, CH_REQUEST, PART_CH, ENV_PART_CH FROM VPMDB2.MSADMIN_PARAM WHERE REQUEST_ID = X'" + m_StrReqID + "' ORDER BY LEVEL_PART ASC";

		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);

		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to Retrive data from VPMDB2.MSADMIN_PARAM");
			throw new PSAErrorHandler(-1,"Error.PartDataRetrieve");
		}

		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			//Read parts details.
			PSNPart part = new PSNPart();
			part.m_StrCOID = dbCon.getValue(i, 1).trim();						// PART COID
			part.m_StrEnv = dbCon.getValue(i, 2).trim();						// PART Env
			part.m_iLevel = Integer.parseInt(dbCon.getValue(i, 3).trim());		// Level 
			part.m_iDico = Integer.parseInt(dbCon.getValue(i, 4).trim());		// DICO Status
			
			part.m_StrType = dbCon.getValue(i, 5).trim();						// PART Type "FILTERING", "HIGH LEVEL"
			if(part.m_StrType.compareToIgnoreCase("FILTERING") == 0)
			{
				part.m_StrSelectedParentCOID = dbCon.getValue(i, 7).trim(); 	// Selected Parent COID
				//Set the values to NULL if no config handler is associated to the Filtering part. 
				if(part.m_StrSelectedParentCOID.equals("0000000000000000")) 
				{ 
					part.m_StrSelectedParentCOID = ""; 
					part.m_StrSelectedParentEnv=""; 
					part.m_StrSelectedConf=""; 
				} else 
				{
					String str = dbCon.getValue(i, 6);
					if(str.length() > 0)
						part.m_StrSelectedConf = str.trim(); 			// Selected Config Handler
					
					str = dbCon.getValue(i, 8);
					if(str.length() > 0)
						part.m_StrSelectedParentEnv = str.trim();  		// Selected parent Env. 
				}
			}
			
			//If type of part is "FILTERING" add part to filtered part list.
			if(part.m_StrType.compareToIgnoreCase("FILTERING") == 0)
			{
				m_ListFilterParts.add(part);
			}
			//Add Part to list
			m_ListAllParts.add(part);
		}
		
		//Reading parent parts is not required for ESTIMATE DATA.
		if(iActionType == ACTION.UPDATEREQUEST)
		{
			for(PSNPart part : m_ListAllParts)
			{
				GetPartDetails(part, 0);
				if(part.m_iDico == 1)
				{
					//If part has DICO then retrieve Config Handlers
					if(GetConfigHandlers(part) != 0)
					{
						System.out.println("Error retrieving Config Handlers.");
						throw new PSAErrorHandler(-1,"Error.ConfigHandlerRetrieve");
					}
				}
			}
			
			//Read parent details from VPMDB2.MSADMIN_ASSLINK
			readParentDetails();

			//Set selected Reference and Designation values, required to display in UI.
			for(PSNPart filter_part : m_ListFilterParts)
			{
				for(PSNPart part : m_ListAllParts)
				{
					//Check parent COID in All part list
					if(filter_part.m_StrSelectedParentCOID.equalsIgnoreCase(part.m_StrCOID))
					{
						filter_part.m_StrSelectedParentREF = part.m_StrRef;
						filter_part.m_StrSelectedParentDesignation = part.m_StrDesignation;
						break;
					}
				}		
			}
		}
	}
	
	/**
	 * Retrieves the selected sites for selected request.
	 */
	private void ReadSelectedSites() throws PSAErrorHandler
	{
		//Retrieve Selected Sites.
		String strSQLQuery = "SELECT ORGSITE.\"id\" FROM VPMDB2.MSADMIN_REQSITEUS REQ, VPMDB2.\"organizationsite\" ORGSITE "
							 + "WHERE REQ.REQUEST_ID = X'" + m_StrReqID + "' AND ORGSITE.OID = REQ.ID_SITE";
		
		System.out.println("Query : " + strSQLQuery);
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to Retrive Selected sites.");
			throw new PSAErrorHandler(-1,"Error.RetrieveSelectedSites");
		}
		
		System.out.println("Selected Sites: ");
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			String selSite = dbCon.getValue(i, 1).trim();
			System.out.println(selSite);
			
			//Add Selected site in list
			m_ListSelSite.add(selSite);
		}
	}

	
	/**
	 * Retrieves Frequency types and its description.
	 * @param ArrayList<String> oListFreqType			List of Frequency Types
	 * @param ArrayList<String> oListFreqDescription	List of Frequency Descriptions
	 */
	public void ReadRequestDesc(ArrayList<String> oListFreqType, ArrayList<String> oListFreqDescription) throws PSAErrorHandler
	{
		//Retrieve Frequency Type and Description.
		String strSQLQuery = "SELECT TYPE_FREQ, DESC_FREQ FROM VPMDB2.ABO_FREQ";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to Retrive frequency type and Description details.");
			throw new PSAErrorHandler(-1,"Error.RetrieveFrequencyDescription");
		}
		
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			String strType = dbCon.getValue(i, 1).trim();
			String strDesc = dbCon.getValue(i, 2).trim();
			System.out.println("Frequency Type : "+strType + " Description : " +strDesc);
			
			oListFreqType.add(strType);
			oListFreqDescription.add(strDesc);
		}
	}
	
	/**
	 * Retrieves the selected Frequency for selected request.
	 */
	private void ReadRequestFreq() throws PSAErrorHandler
	{
		//Retrieve Frequency Data.
		String strSQLQuery = "SELECT TYPE_FREQ, VALUE_FREQ FROM VPMDB2.MSADMIN_REQFREQUS WHERE REQUEST_ID = X'" + m_StrReqID + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to Retrive frequency details.");
			throw new PSAErrorHandler(-1,"Error.RetrieveFrequencyList");
		}
		
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			String typeFreq = dbCon.getValue(i, 1).trim();
			String value = dbCon.getValue(i, 2).trim();
			System.out.println("Frequency : "+typeFreq + " Value : " +value);
			m_REQFreq.m_StrType = typeFreq;
			m_REQFreq.m_listValues.add(value);
		}
	}
	
	/**
	 * Fills the Opr Object with parts details which are stored during creation of request.
	 * @param ACTION action_type		Action Type
	 */	
	public void FillObject(ACTION action_type) throws PSAErrorHandler
	{
		//Clear early containts if any.
		ClearContaints();
		
		//Retrieve Request Details.
		ReadRequestDetails();
		
		//Updation of lock is applicable for Update Request Only.
		if(action_type == ACTION.UPDATEREQUEST)
		{
			//Check Lock Flag Status
			if(m_StrLock.compareToIgnoreCase("INERT") == 0)
			{
				LockRequestForManip("IN-PROCESS");
			} else 
			{
				System.out.println("Request ID locked.");
				throw new PSAErrorHandler(-1,"Error.RequestLocked");
			}
		}
		try {
				if(action_type == ACTION.VIEWREQUEST)
				{
					//Read Data
					readFilteredPartData(action_type);
				}else 
				{
					//Read Data
					readPartData(action_type);
				}
				
				//Get Selected Sites
				ReadSelectedSites();
			
				//Retrieve Frequency Details
				ReadRequestFreq();
				
				if(action_type == ACTION.UPDATEREQUEST)
				{
					//Calculate Next Launch Date
					CalculateNextLaunchDate();
				}
		}catch(PSAErrorHandler e)
		{
			if(action_type == ACTION.UPDATEREQUEST)
			{
				LockRequestForManip("INERT");
			}
			throw e;
		}
	}
	
	/**
	 * Validate the CVS File parts, that the middle part is selected or not. 
	 * If any middle part or root part is selected then such part will be present in Filteredpartlist
	 * and that part will have childs. 
	 */
	private void ValidateParts() throws PSAErrorHandler
	{
		//Check middle parts or root part is selected or not.
		String strInvalidParts = ""; 
		for(PSNPart filterpart : m_ListFilterParts)
		{
			for(PSNPart child_part : filterpart.m_listChild)
			{
				if(child_part.m_bIsInUse)
				{
					strInvalidParts += filterpart.m_StrRef + "\n";
					break;
				}
			}
		}
		if(strInvalidParts.length() > 0)
		{
			System.out.println("Invalid filter part(s) : " + strInvalidParts);
			System.out.println("PSN File is not valid, middle parts selection is not allowed.");
			throw new PSAErrorHandler(-1,"Error.InvalidPSNFile");
		}
	}
	
	/**
	 * Validates Root Part details from the CSV file generated from output of AT0EXPND and from the database.
	 * @param  String istrRootPartDetails			1st line read from CSV File to comapre valid root part.
	 * @return boolena 	true - Root part is valid
	 * 					false - Root part is not valid
	 */
	private boolean ValidateRootPart(String istrRootPartDetails) throws PSAErrorHandler
	{
		boolean bRootValid = false;
		
		System.out.println("Root part read from Database is Reference : " + m_RootPart.m_StrRef +" COID : "+ m_RootPart.m_StrCOID + " Env : " + m_RootPart.m_StrEnv);
		
		String strCSVRootCOID = "";
		String strCSVRootEnv = "";
		boolean bRootSelectStatus = false;
		
		try 
		{
			String []dataArray = istrRootPartDetails.split(";");
			int level = Integer.parseInt(dataArray[4])-1;
			if(level == 0)
			{
				strCSVRootEnv = dataArray[0];
				strCSVRootCOID =dataArray[2];
				bRootSelectStatus = Integer.parseInt(dataArray[5]) == 1 ? true : false;
				System.out.println("Root part read from CSV File is COID : "+ strCSVRootCOID + " Env : " + strCSVRootEnv);
				
				if((m_RootPart.m_StrCOID.equalsIgnoreCase(strCSVRootCOID)) && (m_RootPart.m_StrEnv.equalsIgnoreCase(strCSVRootEnv)) 
						&& (bRootSelectStatus == false))
				{
					System.out.println("Root part is valid");
					bRootValid = true;
				} else {
					System.out.println("Root part is Invalid");
					bRootValid = false;
				}
			} else {
				System.out.println("Root part is Invalid");
				bRootValid = false;			
			}
		} catch(Exception e)
		{
			System.out.println("Error while reading CSV FIle.");
			throw new PSAErrorHandler(-1,"Error.CSVFilereadError");			
		}
		
		return bRootValid;
	}

	/**
	 * Reads the temporary file created by AT0EXPND.
	 * @param istrAT0EXPNDFileName 	temporary file name created by AT0EXPND during PSN parsing for selected parts. 
	 */
	private void readFilteredParts(String istrAT0EXPNDFileName)
	{
	    try{
			//Open output file generated from AT0EXPND.
			//command line parameter
			FileInputStream fstream = new FileInputStream(istrAT0EXPNDFileName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			int lineCount = 0;
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   
			{
				//Skip the first 4 line in the file opened for reading
				if(lineCount < 4)
				{
					System.out.println("Line read :" + strLine);
					lineCount++;
					continue;
				}
				
				if(strLine.length() > 0)
				{
					System.out.println("Line read :" + strLine);
					String []listToken = strLine.split(" ");
					if((listToken.length >= 5) && (listToken[0].equals("NOMODEL")) )
					{
						System.out.println("Validating for filter parts");
						for (PSNPart part : m_ListAllParts)
						{
							if(part.m_StrEnv.equals(listToken[3]) &&
								part.m_StrCOID.equalsIgnoreCase(listToken[1]) &&
								part.m_StrCOMPID.equalsIgnoreCase(listToken[2]))
							{
								boolean bFilterPartFound = false;
								for (PSNPart filter_part : m_ListFilterParts)
								{
									if(filter_part.m_StrEnv.equals(listToken[3]) &&
										filter_part.m_StrCOID.equalsIgnoreCase(listToken[1]) &&
										filter_part.m_StrCOMPID.equalsIgnoreCase(listToken[2]))
									{
										bFilterPartFound = true;
										break;
									}	
								}
								if(bFilterPartFound == false)
								{
									//Set select flag and type.
									part.m_bSelect = true;
									part.m_StrType = "FILTERING";

									System.out.println("Adding part to Filterpart.");
									part.trace();
									m_ListFilterParts.add(part);
								}
								break;
							}
						}
					}
				}
			}
			
			//Close the input stream
			in.close();
			
		}catch (Exception e){
			System.err.println("Error in reading AT0EXPND file.");
		}
		
		//Delete temporary file
		File f1 = new File(istrAT0EXPNDFileName);
	    boolean success = f1.delete();
	    if(!success)
	    {
	    	System.out.println("Error in deleting temporary file " + istrAT0EXPNDFileName);
	    }
	}
	
	/**
	 * Conversion of PSN File to CSV File filtering using AT0EXPND.
	 * @param 	String iStrPSNFileName		Selected PSN File name
	 * @param 	String StrCSVFileName		Output CSV File Name
	 * @return int 		0 		- Success
	 * 					NonZero - Failure
	 */
	private int FilterAssembly(String iStrPSNFileName, String StrCSVFileName, String istrAT0EXPNDFileName)
	{
		System.out.println("Opr->FilterAssembly");
		int istatus = 0;

		String str = "AT0EXPND -user " + m_StrUser.trim() + " -role " + m_StrRole.trim()+ " -org " + m_StrOrganization.trim() + 
					 " -PSNfile " + iStrPSNFileName.trim() + " -savemode NOMODEL -file " + istrAT0EXPNDFileName.trim() + " -readmode STATIC";
		System.out.println("Executing command :"+ str);
		System.out.println("JAVA_APPVPMMS_PSNPARSE = " + StrCSVFileName);
		
		ProcessBuilder pb = new ProcessBuilder("AT0EXPND", "-user", m_StrUser.trim(), "-role", m_StrRole.trim(), "-org", m_StrOrganization.trim(), 
											   "-PSNfile", iStrPSNFileName.trim(), "-savemode", "NOMODEL", "-file", istrAT0EXPNDFileName.trim(), "-readmode", "STATIC");
		
		Map<String, String> env = pb.environment();
		env.put("JAVA_APPVPMMS_PSNPARSE", StrCSVFileName);
		try {
			pb.redirectErrorStream(true); // merge stdout, stderr of process 
			
			Process pr = pb.start();

            //Read Input Stream 
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line=null;
            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            //Read Error Stream 
            BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String error_line=null;
            while((error_line=error.readLine()) != null) {
                System.out.println(error_line);
            }
			 
			int exitVal = pr.waitFor();
			 
            System.out.println("AT0EXPND exited with error code "+exitVal);
	        istatus = exitVal;
	            
            //Close all stream buffers.
            pr.getInputStream().close();
            pr.getOutputStream().close();
            pr.getErrorStream().close();
            
		} catch (Exception e) {
            istatus = -1;
            System.out.println("Error in executing AT0EXPND");
		}
		return istatus;
	}

	/**
	 * This function will filter the selected PSN File.
	 * Validate the PSN file. 
	 *  - Validation of Root part with Database contents.
	 *  - Validates the selected part that the selected part should not be the root part or the middle part.
	 * 
	 * @param action_type			Action type i'e', Create Request or Update Request.
	 * @param iStrPSNFileName		PSN File name
	 * 
	 * @return int		 Zero 	- PSN file is Valid
	 * 					 NonZero - PSN file is not valid.
	 */
	public void ValidatePSN(ACTION action_type, String iStrPSNFileName) throws PSAErrorHandler
	{
		System.out.println("ValidatePSN Start");
		int retVal = 0;
		String StrCSVFileName = m_EnvLocal +"/" + m_EnvLogname + "/FilterAssembly" + "_" + System. currentTimeMillis() + ".csv";
		System.out.println("Output File Name : "+StrCSVFileName);

		String strAT0EXPNDFileName = m_EnvLocal +"/" + m_EnvLogname + "/AT0EXPNDOutput" + "_" + System. currentTimeMillis() + ".txt";
		System.out.println("AT0EXPND File Name : "+strAT0EXPNDFileName);
		
		//Execute AT0EXPND
		retVal = FilterAssembly(iStrPSNFileName, StrCSVFileName, strAT0EXPNDFileName);
		if(retVal == 0)
		{
			retVal = readCSVFile(StrCSVFileName, action_type);
			
			if(retVal == 0)
			{
				readFilteredParts(strAT0EXPNDFileName);
				
				//Search for useful parts.
				setFilterPartDetails();
				
				if(m_ListFilterParts.size() == 1)
				{
					//Remove root part from filtering part, if only root part is selected.
					if(m_ListFilterParts.get(0).m_iLevel == 0)
					{
						//Set root part as HiGH LEVEL part instead of FILTERING PART.
						//Values set as the smae refernce is there in Allpartlist. So setting values.
						m_ListFilterParts.get(0).m_bSelect = false;
						m_ListFilterParts.get(0).m_StrType = "HIGH LEVEL";

						//Removinf part from filtered part list but same reference is available in Allpartlist.
						m_ListFilterParts.remove(0);
						m_filteredpartdetails.clear();
					}
				} else 
				{
					//Check middle parts or root part is selected or not.
					ValidateParts();
				}
				//Display part details with parent and child information
				for(PSNPart part : m_ListAllParts)
				{
					part.trace();
				}
				
			} else if(retVal != 0)
			{
				System.out.println("Error while reading CSV FIle.");
				throw new PSAErrorHandler(-1,"Error.CSVFilereadError");
			} 
		} else 
		{
			System.out.println("Error during filtering PSN File using AT0EXPND.");
			throw new PSAErrorHandler(-1,"Error.FilterPSNError");
		}
			
		System.out.println("ValidatePSN End");
	}
	
	/** 
	 * Checks Request with same Name is already present in databse or not.
	 * 
	 * @param iStrReuestName		Request name to be validated.
	 * @return boolean 		false - Request Name is valid.
	 * 						true - Request Name in not valid. 
	 */
	public boolean ValidateRequestName(String iStrReuestName) throws PSAErrorHandler
	{
		System.out.println("Opr->ValidateRequestName Start");
		boolean status = true;
		//Retrieve Frequency Data.
		String strSQLQuery = "SELECT COUNT(*)FROM VPMDB2.MSADMIN_REQUEST WHERE REQUEST_NAME = '" + iStrReuestName + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int ret_status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != ret_status ) || (0 == count))
		{
			System.out.println("Unable to validate request name.");
			System.out.println("Opr->ValidateRequestName End");
			throw new PSAErrorHandler(-1,"Error.RequestNameValidation");
		}
		
		String StrRowcount = dbCon.getValue(1, 1).trim();
		System.out.println("StrRowcount : "+ StrRowcount);
		
		int row_count = Integer.parseInt(StrRowcount);
		if(row_count == 0)
		{
			status = false;
		}
		System.out.println("Opr->ValidateRequestName End");
		return status;
	}
	
	/**
	 * Insert site in MSADMIN_REQSITEUS.
	 * @param iSite		Selected Site
	 */
	private void InsertSiteInMSADMIN_REQSITEUS(String iSite) throws PSAErrorHandler
	{
		System.out.println("Opr->InsertSiteInMSADMIN_REQSITEUS Start");
		String sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_REQSITEUS ( ID_SITE, REQUEST_ID, VOLUME_REQUEST ) VALUES (" +
											  "(SELECT OID FROM VPMDB2.\"organizationsite\" WHERE \"id\"='"+ iSite +"'), X'"+ m_StrReqID +"', 0)"); 
		System.out.println("Inserting in the VPMDB2.MSADMIN_REQSITEUS : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be inserted in VPMDB2.MSADMIN_REQSITEUS.");
			dbCon.Rollback();
			System.out.println("Opr->InsertSiteInMSADMIN_REQSITEUS End");
			throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINREQSITEUS");
		}
		System.out.println("Opr->InsertSiteInMSADMIN_REQSITEUS End");
	}
	
	/**
	 * Delete site from MSADMIN_REQSITEUS.
	 * @param iSite		Deleted Site
	 */
	private void DeleteSiteFromMSADMIN_REQSITEUS(String iSite) throws PSAErrorHandler
	{
		System.out.println("Opr->DeleteSiteFromMSADMIN_REQSITEUS Start");
		String sqlInsertQuery = String.format("DELETE FROM VPMDB2.MSADMIN_REQSITEUS WHERE ID_SITE = (SELECT OID FROM VPMDB2.\"organizationsite\" WHERE \"id\"='"+ iSite + "') AND REQUEST_ID = X'" + m_StrReqID +"'");
		System.out.println("Deleting from VPMDB2.MSADMIN_REQSITEUS : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be deleted from VPMDB2.MSADMIN_REQSITEUS.");
			dbCon.Rollback();
			System.out.println("Opr->DeleteSiteFromMSADMIN_REQSITEUS End");
			throw new PSAErrorHandler(-1,"Error.DataDeletionFromErrorForMSADMINREQSITEUS");
		}
		System.out.println("Opr->DeleteSiteFromMSADMIN_REQSITEUS End");
	}

	/**
	 * Insert Frequency in MSADMIN_REQFREQUS.
	 * @param iStrFreqType		Selected Frequency type
	 * @param iStrFreqValue		Selected Frequency
	 */
	private void InsertFreqInMSADMIN_REQFREQUS(String iStrFreqType, String iStrFreqValue) throws PSAErrorHandler
	{
		System.out.println("Opr->InsertSiteInMSADMINREQSITEUS Start");
		String sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_REQFREQUS ( REQUEST_ID, TYPE_FREQ, VALUE_FREQ ) VALUES (X'" +
											  m_StrReqID +"', '"+ iStrFreqType +"', '"+ iStrFreqValue +"')"); 
		System.out.println("Inserting in the VPMDB2.MSADMIN_REQFREQUS : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be inserted in VPMDB2.MSADMIN_REQFREQUS.");
			dbCon.Rollback();
			System.out.println("Opr->InsertFreqInMSADMIN_REQFREQUS End");
			throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINREQFREQUS");
		}
		System.out.println("Opr->InsertFreqInMSADMIN_REQFREQUS End");
	}

	/**
	 * Delete Frequency from MSADMIN_REQFREQUS.
	 * @param iStrFreqType		 Frequency type
	 * @param iStrFreqValue		 Frequency
	 */
	private void DeleteFreqFromMSADMIN_MSADMIN_REQFREQUS(String iStrFreqType, String iStrFreqValue) throws PSAErrorHandler
	{
		System.out.println("Opr->DeleteFreqFromMSADMIN_MSADMIN_REQFREQUS Start");
		String sqlInsertQuery = String.format("DELETE FROM VPMDB2.MSADMIN_REQFREQUS WHERE REQUEST_ID = X'"+ m_StrReqID + "' AND TYPE_FREQ = '" + 
												iStrFreqType +"' AND VALUE_FREQ = '" + iStrFreqValue+ "'");
		System.out.println("Deleting from VPMDB2.MSADMIN_REQFREQUS : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be deleted from VPMDB2.MSADMIN_REQFREQUS.");
			dbCon.Rollback();
			System.out.println("Opr->DeleteFreqFromMSADMIN_MSADMIN_REQFREQUS End");
			throw new PSAErrorHandler(-1,"Error.DataDeletionFromErrorForMSADMINREQFREQUS");
		}
		System.out.println("Opr->DeleteFreqFromMSADMIN_MSADMIN_REQFREQUS End");
	}
	
	/**
	 * Update report table.
	 * @param iStrTreatState	Treat State
	 * @param iStrField			Field to be modified.
	 * @param iStrValue			Value of Modified field.
	 */
	private void Update_MSADMIN_REPORT(String iStrTreatState, String iStrField, String iStrValue) throws PSAErrorHandler
	{
		System.out.println("Opr->Update_MSADMIN_REPORT");
		//Create the query to update VPMDB2.CARAC_VUE_HISTO table
		String sqlInsertQuery = "";
		sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_REPORT (TREAT_STATE, CREATOR, CREATION_DATE, NB_DATA, MESSAGE, REQUEST_ID) " +
									   "VALUES ( '%s', '%s', CURRENT TIMESTAMP, 0, '%s-%s', X'%s')", iStrTreatState, m_StrUser, iStrField, 
										iStrValue, m_StrReqID);

		System.out.println("Query->"+ sqlInsertQuery);
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If there is any error then rollback all the transactions and throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be inserted in VPMDB2.MSADMIN_REPORT");
			dbCon.Rollback();
			System.out.println("Opr->Update_MSADMIN_REPORT End");
			throw new PSAErrorHandler(-1,"Error.DataUpdationInMSADMINREPORT");
		}						
		System.out.println("Opr->Update_MSADMIN_REPORT End");
	}

	/**
	 * Calculate Next Launch from the selected frequencies.
	 */
	public void CalculateNextLaunchDate() {
		System.out.println("Opr->CalculateNextLaunchDate Start");
		//Set Next Launch Date
		Date todaysDate = new Date();
		if(m_ListFilterParts.size() ==0)
		{
			String strdate = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd"); 
			formatter.setLenient(false);
			strdate = formatter.format(todaysDate);
			m_StrNextLaunchDate = strdate; 
		} else 
		{
			
			LaunchDate calc_next_launch_date = new LaunchDate();
			String StrFreqType = m_REQFreq.m_StrType;
			
			if(StrFreqType.compareTo("DPW") == 0)
			{
				m_StrNextLaunchDate =  calc_next_launch_date.nextWeekDayLaunchDate(m_REQFreq.m_listValues);
				
			} else if(StrFreqType.compareTo("DPM") == 0)
			{
				m_StrNextLaunchDate =  calc_next_launch_date.nextMonthLaunchDate(m_REQFreq.m_listValues);
			}
			else if(StrFreqType.compareTo("DPY") == 0)
			{
				m_StrNextLaunchDate =  calc_next_launch_date.nextYearLaunchDate(m_REQFreq.m_listValues);
			}
			
			System.out.println("Calculated Next Launch Date : " + m_StrNextLaunchDate);
		}
		System.out.println("Opr->CalculateNextLaunchDate End");
	}
	
	/**
	 * Delete part from MSADMIN_PARAM and remove its link from MSADMIN_ASSLINK.
	 * @param part 	Part to be deleted.
	 */
	public void DeletePart(PSNPart part, ACTION iActionType)throws PSAErrorHandler
	{
		System.out.println("Opr->DeleteParts Start");
		if(iActionType == ACTION.UPDATEREQUEST)
		{
			String sqlInsertQuery = String.format("Delete from VPMDB2.MSADMIN_PARAM where ID_PART = X'" + part.m_StrCOID + "' AND "
												+ "REQUEST_ID = X'" + m_StrReqID +"'");
	
			System.out.println("Deleting from VPMDB2.MSADMIN_PARAM : " + sqlInsertQuery);
			
			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be deleted from VPMDB2.MSADMIN_PARAM.");
				dbCon.Rollback();
				System.out.println("Opr->DeleteParts End");
				throw new PSAErrorHandler(-1,"Error.DataDeletionErrorFromMSADMINPARAM");
			}		
			Update_MSADMIN_REPORT("DEL", "Part-Ass", part.m_StrRef);
			
			//Delete Part LINKs
			int link_Count = part.m_listLinkCOMPID.size();
			for(int i=0; i<link_Count; i++)
			{
				sqlInsertQuery = String.format("Delete from VPMDB2.MSADMIN_ASSLINK where PART_COID = X'" + part.m_StrCOID + "' AND "
													+ "LINK_COMPID = X'" + part.m_listLinkCOMPID.get(i) + "' AND "
													+ "REQUEST_ID = X'" + m_StrReqID +"'");
				
				System.out.println("Deleting from VPMDB2.MSADMIN_ASSLINK : " + sqlInsertQuery);
				
				//Execute the query
				status = dbCon.ExecuteUpdate(sqlInsertQuery);
				//If any error comes then throw the error.
				if(0 != status )
				{
					System.out.println("Data could not be deleted from VPMDB2.MSADMIN_ASSLINK.");
					dbCon.Rollback();
					System.out.println("Opr->DeleteParts End");
					throw new PSAErrorHandler(-1,"Error.DataDeletionErrorFromMSADMINPARAM");
				}
				Update_MSADMIN_REPORT("DEL", "Part-Link", part.m_StrCOID + " - " + part.m_listLinkCOMPID.get(i));
			}
		}
		
		//Remove from All parts list
		int nTotalParts = m_ListAllParts.size();
		int i = 0;
		for(i=0; i<nTotalParts; i++)
		{
			PSNPart del_Part = m_ListAllParts.get(i);
			if(part == del_Part)
			{
				System.out.println("Deleting part "+part.m_StrRef);
				for(int nParentCount = 0; nParentCount < del_Part.m_listParent.size(); nParentCount++)
				{
					//Delete part which has to be deleted from its parent. 
					del_Part.m_listParent.get(nParentCount).m_listChild.remove(del_Part);
				}
				m_ListAllParts.remove(i);
				break;
			}
		}
		System.out.println("Opr->DeleteParts End");
	}
	
	/**
	 * Validates that the last children is deleted or not.
	 * @param Part 			Part for which validation to be done.
	 * @param iChildCOID	Child COID
	 * 
	 * @return boolean 			true  - If Child is available
	 * 							false - If child is not present.
	 */
	private boolean IsLastChildDeleted(PSNPart part, PSNPart childPart, ACTION iActionType) throws PSAErrorHandler
	{
		System.out.println("Opr->IsLastChildDeleted Start");
		boolean bDelete = false;
		if(iActionType == ACTION.UPDATEREQUEST)
		{
			String sqlQuery = String.format("SELECT COUNT(*) FROM VPMDB2.MSADMIN_ASSLINK WHERE PART_COID IN (" +
											" SELECT DISTINCT $COID_REF FROM " + part.m_StrEnv + ".$EXT WHERE $COID=X'" +
											part.m_StrCOID + "' AND $TYPE='CATASS' AND $COID_REF != X'"+ childPart.m_StrCOID + "') AND REQUEST_ID = X'" + m_StrReqID +"'");
			System.out.println("The Query is : " + sqlQuery);
			
			int ret_status = dbCon.ExecuteSelect(sqlQuery);
			//Get the resulting count of the executed query.
			int count = dbCon.getRowCount();
	
			//If there is any error,throw it.
			if((0 != ret_status ) || (0 == count))
			{
				System.out.println("No children found to the parent !!!");
				System.out.println("Opr->IsLastChildDeleted End");
				throw new PSAErrorHandler(-1,"Error.NoChildrenFoundForParent");
			}
			else
			{
				System.out.println("getRowcount : " + dbCon.getValue(1, 1));
				if(Integer.parseInt(dbCon.getValue(1, 1).trim()) == 1)
					bDelete = true;
				else
					bDelete = false;
			}
		}
		else
		{
			if(part.m_listChild.size()-1 == 0)
				bDelete = true;
			else
				bDelete = false;		
		}
		
		System.out.println("Opr->IsLastChildDeleted End");
		return bDelete;
	}
	
	/** 
	 * Deletes parent link of the parts.
	 * @param part 		Part for which links has to be deleted.
	 */
	public void DeleteParent(PSNPart part, ACTION iActionType) throws PSAErrorHandler
	{
		System.out.println("Opr->DeleteParent Start");
		int nCount = part.m_listParent.size();
		int i = 0;
		for(i = 0; i < nCount; i++)
		{
			if(true == IsLastChildDeleted(part.m_listParent.get(i),part,iActionType))
			{
				DeleteParent(part.m_listParent.get(i), iActionType);				
				DeletePart(part.m_listParent.get(i), iActionType);
			}
		}
		System.out.println("Opr->DeleteParent End");
	}
	
	/**
	 * Updates part in MSADMIN_PARAM.
	 */
	private void UpdatePartsInPARAM() throws PSAErrorHandler
	{
		System.out.println("Opr->UpdatePartsInPARAM Start");
		int nCount = m_ListUpdatedParts.size();
		int i = 0;
		//for each part add entry in VPMDB2.MSADMIN_PARAM table.
		for(i = 0; i < nCount; i++)
		{
			PSNPart part = m_ListUpdatedParts.get(i);
			String strParentCOID = part.m_StrSelectedParentCOID;
			if(strParentCOID.length() == 0)
			{
				strParentCOID = "0000000000000000";
			}
			
			String strConf = (part.m_StrSelectedConf.equals("----------") == true) ? "" : part.m_StrSelectedConf;
			if(strConf.length() == 0)
			{
				//As no conf Hanlder selected reset parent.
				strParentCOID = "0000000000000000";
			}
			
			String sqlInsertQuery = String.format("update VPMDB2.MSADMIN_PARAM set CH_REQUEST = '" + strConf + "', PART_CH = "
													+ "X'" + part.m_StrSelectedParentCOID + "', ENV_PART_CH = '"
													+ part.m_StrSelectedParentEnv + "' where ID_PART = X'" + part.m_StrCOID + "' AND REQUEST_ID = X'"
													+ m_StrReqID + "'");
			
			System.out.println("Inserting in the VPMDB2.MSADMIN_PARAM : " + sqlInsertQuery);
			
			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be inserted in VPMDB2.MSADMIN_PARAM.");
				dbCon.Rollback();
				System.out.println("Opr->UpdatePartsInPARAM End");
				throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINPARAM");
			}
			Update_MSADMIN_REPORT("MOD", "Part-Ass", part.m_StrSelectedParentCOID+ " - " + part.m_StrSelectedConf);
		}
		System.out.println("Opr->UpdatePartsInPARAM End");
		
	}
	
	/*
	 * Inser parts in MSADMIN_PARAM.
	 */
	private void InsertPartsInPARAM(ArrayList<PSNPart> part_list) throws PSAErrorHandler
	{
		System.out.println("Opr->InsertPartsInPARAM Start");
		int nCount = part_list.size();
		int i = 0;
		//for each part add entry in VPMDB2.MSADMIN_PARAM table.
		for(i = 0; i < nCount; i++)
		{
			PSNPart part = part_list.get(i);
			if((part.m_bIsInUse == false) && (m_StrMOD.equals("ONESHOT") == false))
			{
				continue;
			}
			String strParentCOID = part.m_StrSelectedParentCOID;
			if(strParentCOID.length() == 0)
			{
				strParentCOID = "0000000000000000";
			}
			
			String strConf = (part.m_StrSelectedConf.equals("----------") == true) ? "" : part.m_StrSelectedConf;
			if(strConf.length() == 0)
			{
				//As no conf Hanlder selected reset parent.
				strParentCOID = "0000000000000000";
			}
			
			String sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_PARAM ( ID_PART, ENV_PART, TYPE_REQ_PART, LEVEL_PART, DICO_PART, CH_REQUEST, PART_CH, ENV_PART_CH, REQUEST_ID) VALUES "
												+ "(X'" +  part.m_StrCOID + "', '"
												+ part.m_StrEnv + "', '"
												+ part.m_StrType + "', "
												+ part.m_iLevel + ", "
												+ part.m_iDico + ", '"
												+ strConf + "', "
												+ "X'" + strParentCOID + "', '"
												+ part.m_StrSelectedParentEnv + "', "
												+ "X'" + m_StrReqID +"')"); 
			
			System.out.println("Inserting in the VPMDB2.MSADMIN_PARAM : " + sqlInsertQuery);
			
			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be inserted in VPMDB2.MSADMIN_PARAM.");
				dbCon.Rollback();
				System.out.println("Opr->InsertPartsInPARAM End");
				throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINPARAM");
			}
			
			//Add Parent links.
			int LinkCount = part.m_listLinkCOMPID.size();
			for(int j =0; j<LinkCount; j++)
			{
			
					if(part.m_listLinkCOMPID.get(j).equals("0000000000000000"))
						continue;
					sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_ASSLINK ( REQUEST_ID, PART_COID, LINK_COMPID, LINK_ENV) VALUES "
												+ "(X'" + m_StrReqID + "', "
												+ "X'" + part.m_StrCOID + "', X'"
												+ part.m_listLinkCOMPID.get(j) + "', '"
												+ part.m_listParent.get(j).m_StrEnv + "')"); 
	
				System.out.println("Inserting in the VPMDB2.MSADMIN_ASSLINK : " + sqlInsertQuery);
				
				//Execute the query
				status = dbCon.ExecuteUpdate(sqlInsertQuery);
				//If any error comes then throw the error.
				if(0 != status )
				{
					System.out.println("Data could not be inserted in VPMDB2.MSADMIN_ASSLINK.");
					dbCon.Rollback();
					System.out.println("Opr->InsertPartsInPARAM End");
					throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINASSLINK");
				}
			}
		}
		System.out.println("Opr->InsertPartsInPARAM End");
		
	}

	/**
	 * Check Data is set properly or not, if date is not set properly then set to infinite date. If date is set then relace '/' with '-'.
	 * @param String iStrDate		Date to be validate
	 * @retrun String	Modified Date.
	 */
	private String CheckAndFormatDate(String iStrDate)
	{
		if (iStrDate.equals("") == false)
		{
			iStrDate = iStrDate.replace('/', '-');
		}
		else
		{
			iStrDate = infinateDate;
		}
		return iStrDate;
	}

	/**
	 * Create the request in Database.
	 */
	public void CreateRequest() throws PSAErrorHandler
	{
		System.out.println("Opr->CreateRequest Start");
		UUID uuid = new UUID();
		m_StrReqID = uuid.toString();
		System.out.print(m_StrReqID);


		//Convert into correct date format for the database to be updated correctly.
		m_StrExpiryDate = CheckAndFormatDate(m_StrExpiryDate);
    	
    	CalculateNextLaunchDate();
		m_StrNextLaunchDate = CheckAndFormatDate(m_StrNextLaunchDate);

    	System.out.println("PSN File Name :" +m_StrNamePSN);
	    String sqlInsertQuery = "";
		sqlInsertQuery = String.format("INSERT INTO VPMDB2.MSADMIN_REQUEST ( REQUEST_ID, REQUEST_NAME, ACTION_STATUS, EXEC_MODE, CREATOR , CREATE_DATE, LAST_MOD_DATE , " +
									   "VALIDITY_DATE, NEXT_LAUNCH_DATE, LOCK_FLAG, PSN_NAME) VALUES (X'" + m_StrReqID + "', '" + m_StrReqName + "', 'ACTIVE', '" +
									   m_StrMOD.toUpperCase() + "', '" + m_StrUser + "', CURRENT TIMESTAMP, CURRENT TIMESTAMP, TIMESTAMP('" + m_StrExpiryDate + "','00:00:00')," + 
									   "TIMESTAMP('" + m_StrNextLaunchDate + "','00:00:00'), 'INERT', '" +  m_StrNamePSN +"' )" ); 
		System.out.println("Inserting in the VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be inserted in VPMDB2.MSADMIN_REQUEST for the user " +m_StrUser);
			dbCon.Rollback();
			System.out.println("Opr->CreateRequest End");
			throw new PSAErrorHandler(-1,"Error.DataInsertionErrorForMSADMINREQUEST");
		}
		Update_MSADMIN_REPORT("ADD", "REQ", m_StrReqID + " Mode " +  m_StrMOD.toUpperCase());

		System.out.println("Deleting parts");
		int nCount = m_ListDeleteParts.size();
		System.out.println("Deleting parts size :" + nCount);
		//for each part delete entry from VPMDB2.MSADMIN_PARAM table and VPMDB2.MSADMIN_ASSLINK table.
		for(int i = 0; i < nCount; i++)
		{
			DeleteParent(m_ListDeleteParts.get(i), ACTION.CREATEREQUEST);
			DeletePart(m_ListDeleteParts.get(i), ACTION.CREATEREQUEST);
		}

		//Insert Part Details
		InsertPartsInPARAM(m_ListAllParts);
		
		//Insert Site Data
		int selSiteSize = m_ListSelSite.size();
		for(int i = 0; i<selSiteSize; i++)
		{
			InsertSiteInMSADMIN_REQSITEUS(m_ListSelSite.get(i));
			Update_MSADMIN_REPORT("ADD", "Site", m_ListSelSite.get(i));
		}

		//Insert Frequency Data
		int FreqSize = m_REQFreq.m_listValues.size();
		String strFreqType = m_REQFreq.m_StrType;
		for(int i = 0; i<FreqSize; i++)
		{
			InsertFreqInMSADMIN_REQFREQUS(strFreqType, m_REQFreq.m_listValues.get(i));
			Update_MSADMIN_REPORT("ADD", "FREQ", m_REQFreq.m_StrType +"-"+ m_REQFreq.m_listValues.get(i));
		}

		//If everything succeeds then commit.
		dbCon.Commit();
		
		//Clear the contents of the this object.
		ClearContaints();
		
		System.out.println("Opr->CreateRequest End");
	}

	private void UpdatePartData(PSAAdminMSOutilsOpr cloneOpr) throws PSAErrorHandler
	{
		System.out.println("Opr->UpdatePartData Start");
		//Delete parts
		int nCount = m_ListDeleteParts.size();
		//for each part delete entry from VPMDB2.MSADMIN_PARAM table and VPMDB2.MSADMIN_ASSLINK table.
		for(int i = 0; i < nCount; i++)
		{
			DeleteParent(m_ListDeleteParts.get(i), ACTION.UPDATEREQUEST);
			DeletePart(m_ListDeleteParts.get(i), ACTION.UPDATEREQUEST);
		}
		
		for(PSNPart part : m_ListAllParts)
		{
			boolean bPartFound = false;
			PSNPart searched_clone_part = null;
			for(PSNPart clonepart : cloneOpr.m_ListAllParts)
			{
				if(part.IsEqual(clonepart))
				{
					System.out.println("Part found in clone opr.");
					searched_clone_part = clonepart;
					bPartFound = true;
					break;
				}
			}
			if(!bPartFound)
			{
				m_ListAddedParts.add(part);
			} else 
			{
				if((searched_clone_part.m_StrSelectedConf.equals(part.m_StrSelectedConf) == false ) ||
					(searched_clone_part.m_StrSelectedParentCOID.equalsIgnoreCase(part.m_StrSelectedParentCOID) == false))
				{
					System.out.println("Updation required for part " + part.m_StrSelectedParentCOID);
					m_ListUpdatedParts.add(part);
				} else 
				{
					System.out.println("No Modifications in part " + part.m_StrSelectedParentCOID);
				}
			}
		}
		System.out.println("Opr->UpdatePartData End");
	}
	/**
	 * Modified the request in Database.
	 * @param cloneOpr		Opr Object before modification.
	 */
	public void ModifyRequest(PSAAdminMSOutilsOpr cloneOpr) throws PSAErrorHandler
	{
		System.out.println("Opr->ModifyRequest");
		
		String strExpDate = m_StrExpiryDate;
		System.out.println("strExpDate : "+strExpDate);
		System.out.println("Clone Opr ExpDate : "+cloneOpr.m_StrExpiryDate);
		
		CalculateNextLaunchDate();
		//If there is modification in VPMDB2.MSADMIN_REQUEST data then update to table.
		if( (m_StrReqName.compareTo(cloneOpr.m_StrReqName) != 0) ||
			(m_StrStatus.compareTo(cloneOpr.m_StrStatus) != 0) ||
			(m_StrMOD.compareTo(cloneOpr.m_StrMOD) != 0) ||
			(m_StrUser.compareTo(PSAAdminMSOutilsOpr.m_StrUser) != 0) ||
			(strExpDate.compareTo(cloneOpr.m_StrExpiryDate) != 0) ||
			(m_StrNextLaunchDate.compareTo(cloneOpr.m_StrNextLaunchDate) != 0) ||
			(m_StrLock.compareTo(cloneOpr.m_StrLock) != 0) )
		{
			//Convert into correct date format for the database to be updated correctly.
			m_StrExpiryDate = CheckAndFormatDate(m_StrExpiryDate);
	    	
			m_StrNextLaunchDate = CheckAndFormatDate(m_StrNextLaunchDate);

		    String sqlInsertQuery = "";
			sqlInsertQuery = String.format("UPDATE VPMDB2.MSADMIN_REQUEST SET REQUEST_NAME = '" + m_StrReqName + "', ACTION_STATUS = '"
									+ m_StrStatus + "', EXEC_MODE = '" + m_StrMOD.toUpperCase() + "', CREATOR = '" + m_StrUser + "', VALIDITY_DATE = " 
									+ "TIMESTAMP('" + m_StrExpiryDate + "','00:00:00')" + ", NEXT_LAUNCH_DATE = TIMESTAMP('" + m_StrNextLaunchDate + "','00:00:00') " +
									"WHERE REQUEST_ID = X'"+ m_StrReqID + "'");

			System.out.println("Updating in the VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);

			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be updated in VPMDB2.MSADMIN_REQUEST for the user " +m_StrUser);
				dbCon.Rollback();
				System.out.println("Opr->ModifyRequest End");
				throw new PSAErrorHandler(-1,"Error.DataUpdationErrorForMSADMINREQUEST");
			}
		}
		//Update the part date.
		UpdatePartData(cloneOpr);
		
		if (m_StrReqName.compareTo(cloneOpr.m_StrReqName) != 0)
		{
			Update_MSADMIN_REPORT("MOD", "REQUEST NAME", m_StrReqName);
		}

		if (m_StrExpiryDate.compareTo(cloneOpr.m_StrExpiryDate) != 0)
		{
			Update_MSADMIN_REPORT("MOD", "VALIDITY_DATE", m_StrExpiryDate);

		}
		if (m_StrNextLaunchDate.compareTo(cloneOpr.m_StrNextLaunchDate) != 0)
		{
			Update_MSADMIN_REPORT("MOD", "NEXT LAUNCH DATE", m_StrNextLaunchDate);
		}

		
		//Insert new Parts
		if(m_ListAddedParts.size() > 0)
		{
			InsertPartsInPARAM(m_ListAddedParts);
		}
		
		if(m_ListUpdatedParts.size() > 0)
		{
			UpdatePartsInPARAM();
		}
		
		//For each added Sites, Add entry in VPMDB2.MSADMIN_REQSITEUS.
		for(int i=0;i<m_Added_SiteList.size();i++)
		{				
			InsertSiteInMSADMIN_REQSITEUS(m_Added_SiteList.get(i));						
			Update_MSADMIN_REPORT("ADD", "Site", m_Added_SiteList.get(i));
		}

		//For each deleted Sites, remove entry from VPMDB2.MSADMIN_REQSITEUS.
		for(int i=0;i<m_Deleted_SiteList.size();i++)
		{				
			DeleteSiteFromMSADMIN_REQSITEUS(m_Deleted_SiteList.get(i));						
			Update_MSADMIN_REPORT("DEL", "Site", m_Deleted_SiteList.get(i));
		}

		//For each added Frequency, Add entry in VPMDB2.MSADMIN_REQFREQUS.
		for(int i=0;i<m_AddedREQFreq.m_listValues.size();i++)
		{				
			System.out.println("Added Freq item : "+ m_AddedREQFreq.m_listValues.get(i));
			InsertFreqInMSADMIN_REQFREQUS(m_AddedREQFreq.m_StrType, m_AddedREQFreq.m_listValues.get(i));						
			Update_MSADMIN_REPORT("ADD", "FREQ", m_AddedREQFreq.m_StrType +"-"+ m_AddedREQFreq.m_listValues.get(i));
		}

		//For each deleted Frequency, remove entry from VPMDB2.MSADMIN_REQFREQUS.
		for(int i=0;i<m_DeletedREQFreq.m_listValues.size();i++)
		{
			System.out.println("Deleted Freq item : "+ m_DeletedREQFreq.m_listValues.get(i));
			DeleteFreqFromMSADMIN_MSADMIN_REQFREQUS(m_DeletedREQFreq.m_StrType, m_DeletedREQFreq.m_listValues.get(i));						
			Update_MSADMIN_REPORT("DEL", "FREQ", m_DeletedREQFreq.m_StrType +"-"+ m_DeletedREQFreq.m_listValues.get(i));
		}

		dbCon.Commit();
		System.out.println("Opr->ModifyRequest End");
	}

	/**
	 * Delete request from database.
	 */
	public void DeleteRequest() throws PSAErrorHandler
	{
		System.out.println("Opr->DeleteRequest");
		String sqlInsertQuery = "";
		sqlInsertQuery = String.format("UPDATE VPMDB2.MSADMIN_REQUEST SET ACTION_STATUS = 'DELETED' WHERE REQUEST_ID = X'" + m_StrReqID +"'");

		System.out.println("Updating status in the VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);

		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be deleted from VPMDB2.MSADMIN_REQUEST for the user " +m_StrUser);
			dbCon.Rollback();
			System.out.println("Opr->DeleteRequest End");
			throw new PSAErrorHandler(-1,"Error.DataDeletionErrorForMSADMINREQUEST");
		}
		//Update Report table
		Update_MSADMIN_REPORT("DEL", "REQ", m_StrReqID+'-'+m_StrReqName);

		dbCon.Commit();
		System.out.println("Opr->DeleteRequest End");
	}

	/**
	 * Blocks / Unblocks the requests.
	 * @param iActive_RequestList 		Request Which are INACTIVE modified to ACTIVE.
	 * @param iInActive_RequestList 	Request Which are ACTIVE modified to INACTIVE.
	 */
	public void BlockUnblockRequest(ArrayList<String> iActive_RequestList, ArrayList<String> iInActive_RequestList) throws PSAErrorHandler
	{
		System.out.println("Opr->BlockUnblockRequest");
		boolean bModifstatus = false;
		//Process Active Requests.
		int Active_Req_Size = iActive_RequestList.size();
		if(Active_Req_Size > 0)
		{
			String strActiveRequest = "";
			for(int i=0; i<Active_Req_Size; i++)
			{
				strActiveRequest = strActiveRequest + "'"+ iActive_RequestList.get(i) + "'";
				if(i != Active_Req_Size-1)
				{
					strActiveRequest += ", ";
				}
			}

			String sqlInsertQuery = "";
			sqlInsertQuery = String.format("UPDATE VPMDB2.MSADMIN_REQUEST SET ACTION_STATUS = 'ACTIVE' WHERE REQUEST_NAME IN ("+ strActiveRequest + ")");

			System.out.println("Updating status in the VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);

			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be Updated from VPMDB2.MSADMIN_REQUEST for the user " +m_StrUser);
				dbCon.Rollback();
				System.out.println("Opr->BlockUnblockRequest End");
				throw new PSAErrorHandler(-1,"Error.DataUpdationErrorForMSADMINREQUEST");
			}
			
			//Update REPORT table.
			for(int i=0; i<Active_Req_Size; i++)
			{
				Update_MSADMIN_REPORT("MOD", "REQ_STATUS", "ACTIVE"+"-"+iActive_RequestList.get(i));
			}
			bModifstatus = true;
		}

		//Process Inactive requests
		int InActive_Req_Size = iInActive_RequestList.size();
		if(InActive_Req_Size > 0)
		{
			String strInActiveRequest = "";
			for(int i=0; i<InActive_Req_Size; i++)
			{
				strInActiveRequest = strInActiveRequest + "'"+ iInActive_RequestList.get(i) + "'";
				if(i != InActive_Req_Size-1)
				{
					strInActiveRequest += ", ";
				}
			}
		
			String sqlInsertQuery = "";
			sqlInsertQuery = String.format("UPDATE VPMDB2.MSADMIN_REQUEST SET ACTION_STATUS = 'INACTIVE' WHERE REQUEST_NAME IN ("+ strInActiveRequest + ")");

			System.out.println("Updating status in the VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);

			//Execute the query
			int status = dbCon.ExecuteUpdate(sqlInsertQuery);
			//If any error comes then throw the error.
			if(0 != status )
			{
				System.out.println("Data could not be Updated from VPMDB2.MSADMIN_REQUEST for the user " +m_StrUser);
				dbCon.Rollback();
				System.out.println("Opr->BlockUnblockRequest End");
				throw new PSAErrorHandler(-1,"Error.DataUpdationErrorForMSADMINREQUEST");
			}

			//Update REPORT table.
			for(int i=0; i<InActive_Req_Size; i++)
			{
				Update_MSADMIN_REPORT("MOD", "REQ_STATUS", "INACTIVE"+"-"+iInActive_RequestList.get(i));
			}
			bModifstatus = true;
		}

		if(true == bModifstatus)
		{
			dbCon.Commit();
		}
		System.out.println("Opr->BlockUnblockRequest End");
	}

	/**
	 * Provides COMPID for the part from specified Environment.
	 * @param iStrEnv		Environment
	 * @param iStrCOID		COID
	 */
	public String GetCOMPID(String iStrEnv, String iStrCOID)
	{
		//Retrieve part COMPID.
		String strSQLQuery = "SELECT $COMPID FROM " + iStrEnv+".PART_LIST WHERE $COID =X'" + iStrCOID + "'";
		
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to retrieve part COMPID.");
			return "";
		}
		
		//Set part details.
		String compid = dbCon.getValue(1, 1).trim();
		return compid;
	}
	
	/**
	 * Create Estimate File.
	 */
	private String CreateEstimateFile()
	{
		String StrCSVFileName = "";
		if(m_ListFilterParts.size() > 0)
		{
			StrCSVFileName = m_EnvLocal +"/" + m_EnvLogname + "/PSAEstimateRequest" + "_" + System. currentTimeMillis();
			m_StrEstimateFileName = StrCSVFileName; 
			
			StrCSVFileName = StrCSVFileName + ".csv";
			System.out.println("Output File Name : "+StrCSVFileName);
	
			BufferedWriter bufferedWriter = null;
	        try {
	            //Construct the BufferedWriter object
	            bufferedWriter = new BufferedWriter(new FileWriter(StrCSVFileName));
	            
	            for(int i=0; i<m_ListFilterParts.size(); i++)
	            {
	            	PSNPart part = m_ListFilterParts.get(i);
	            	String part_compid = "";
	            	String parent_compid="";
	            	part_compid = GetCOMPID(part.m_StrEnv, part.m_StrCOID);
	            	if(part.m_StrSelectedParentCOID.length() > 0)
	            	{
	            		parent_compid = GetCOMPID(part.m_StrSelectedParentEnv, part.m_StrSelectedParentCOID);
	            	}
	                //Start writing to the output stream
	                bufferedWriter.write(part.m_StrEnv + ":" + part.m_StrCOID + ":" + part_compid + ";" + part.m_StrSelectedConf + ";"
	                					 + part.m_StrSelectedParentEnv + ":" + part.m_StrSelectedParentCOID + ":" + parent_compid);
	                bufferedWriter.newLine();
	            }
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } 
	        
	        finally {
	            //Close the BufferedWriter
	            try {
	                if (bufferedWriter != null) {
	                    bufferedWriter.flush();
	                    bufferedWriter.close();
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
		}
		return StrCSVFileName;
	}
	
	/**
	 * Provides the Environment and Table list where reference is MS_T_A_EUR.
	 */
	static private void GetEnvTables() throws PSAErrorHandler
	{
		//Get the list of ENV and their tables configured for subcription
		//NOTE: The table DOCUMENT, DOC_PFROME, DOC_CALCUL will be treated as DOCUMENT
		String strSQLQuery="SELECT F.REF_ID,F.REF_VAL FROM GESTION.FGESTION F, GESTION.PART_LIST P WHERE P.S_REFERENCE = 'MS_T_A_EUR' AND F.$COID = P.$COID ORDER BY F.REF_ID";
		System.out.println("Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to retrieve env and table information.");
			throw new PSAErrorHandler(-1, "Error.InvaildConfiguration");
		}
		for(int i = 1; i <= count; i++)
		{
			String env = dbCon.getValue(i, 1).trim();
			String table = dbCon.getValue(i, 2).trim();
			
			if(table.equals("PART_LIST"))
			{
				continue;
			}
			boolean bFound = false;
			for(int j=0; j<m_listEnvTables.size(); j++)
			{
				if(m_listEnvTables.get(j).strEnv.compareTo(env) == 0)
				{
					if(m_listEnvTables.get(j).listTables.contains(table) == false)
					{
						System.out.println("Environment : " + env +" Table name : " + table);
						m_listEnvTables.get(j).listTables.add(table);
					}
					bFound = true;
					break;
				}
			}
			if(bFound ==  false)
			{
				SubEnvTables envtable = new SubEnvTables();
				envtable.strEnv = env;
				envtable.listTables.add(table);
				System.out.println("Environment : " + env +" Table name : " + table);
				m_listEnvTables.add(envtable);
			}
			
		}
	}
	
	/**
	 * Estimate data for the COID's read from PSAGetDataToReplicate.
	 */
	private void EstimateData() throws PSAErrorHandler
	{
		System.out.println("Opr->EstimateData");
		int nbEnv = m_listEnvTables.size();
						
		for(int i = 0; i<nbEnv; i++)
		{
			SubEnvTables pEnv = m_listEnvTables.get(i);
			if(pEnv.nCntPart > 0)
			{
				System.out.println("Size of tables in "+ m_listEnvTables.get(i).strEnv +"is :" + pEnv.listTables.size());
				for(int j = 0; j < pEnv.listTables.size(); j++)
				{
					String strQuery = "SELECT SUM(C_SIZE), COUNT(*) FROM " + pEnv.strEnv + "." + 
									pEnv.listTables.get(j)+ " WHERE $COID IN (" + pEnv.strCoid + ")";
					System.out.println("Query : " + strQuery);
					//Execute the Query
					int status = dbCon.ExecuteSelect(strQuery);
					
					//If there is any error,throw it.
					if(0 != status)
					{
						System.out.println("Error while retriving size information.");
						throw new PSAErrorHandler(-1, "Error.InvaildSizeQuery");
					}
					//Get the resulting count of the executed query.
					int count = dbCon.getRowCount();

					if(count > 0)
					{
						String partsize = dbCon.getValue(1, 1);
						String strCount = dbCon.getValue(1, 2);
						
						if( partsize != null && strCount != null && (partsize.length() > 0) && partsize.compareTo("-") != 0
								&& (strCount.length() > 0) && strCount.compareTo("-") != 0)
						{
							System.out.println("Part Size :"+ partsize + " Count :" + strCount);
							if(pEnv.listTables.get(j).compareTo("CATIA_MODEL") == 0)
							{
								pEnv.dSizeV4 += Double.parseDouble(partsize.trim());
								pEnv.nCntV4 += Integer.parseInt(strCount.trim());
							}
							else if(pEnv.listTables.get(j).compareTo("DOC_V5") == 0)
							{
								pEnv.dSizeV5 += Double.parseDouble(partsize.trim());
								pEnv.nCntV5 += Integer.parseInt(strCount.trim());
							}
							else
							{
								pEnv.dSizeDoc += Double.parseDouble(partsize.trim());
								pEnv.nCntDoc += Integer.parseInt(strCount.trim());
		 					}
						} else {
							System.out.println("Incorrect data read from table.");
						}
					}						
				}
			} else {
				System.out.println("No Data added for ENV "+  m_listEnvTables.get(i).strEnv);
			}
		}		
	}
	
	/**
	 * Read Estimate file.
	 */
	private void readEstimateFile() throws PSAErrorHandler
	{
		System.out.println("Opr->readEstimateFile");
		//Clear earlier contents.
		for(int j=0; j<m_listEnvTables.size(); j++)
		{
			m_listEnvTables.get(j).strCoid = "";
		}
		
		//Read Output CSV File
		ArrayList<Part_Details> Child_part_list = new ArrayList<Part_Details>(); 
		try
		{
			String strInfoMsg = "";
			FileInputStream fstream = new FileInputStream(m_StrEstimateFileName + "_output.csv");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   
			{
				if(strLine.length() > 0){
					
					String []dataArray = strLine.split(";");
					String []filter_data = dataArray[0].split(":");
					Part_Details filter_part_details =  new Part_Details();
					filter_part_details.m_StrEnv = filter_data[0];
					String strENV = filter_data[0]; 
					filter_part_details.m_StrCOID = filter_data[1]; 
					String strCOID = filter_data[1]; 
					filter_part_details.m_StrCOMPID = filter_data[2];
					
					if(dataArray[1].equals(""))
					{
						String []Child_data = dataArray[2].split(":");
						if(Child_data.length > 0)
						{
							Part_Details child_part_details =  new Part_Details();
							child_part_details.m_StrEnv = Child_data[0];
							child_part_details.m_StrCOID = Child_data[1]; 
							child_part_details.m_StrCOMPID = Child_data[2];
	
							boolean bFound = false;
							for(int j=0; j<m_listEnvTables.size(); j++)
							{
								if((m_listEnvTables.get(j).strEnv).compareToIgnoreCase(child_part_details.m_StrEnv) == 0)
								{
									if(m_listEnvTables.get(j).strCoid.length() > 0)
									{
										m_listEnvTables.get(j).strCoid = m_listEnvTables.get(j).strCoid + ", X'" + child_part_details.m_StrCOID + "'";
									} else 
									{
										m_listEnvTables.get(j).strCoid = "X'" + child_part_details.m_StrCOID + "'";
									}
									m_listEnvTables.get(j).nCntPart++;
									bFound= true;
									break;
								}
							}
							if(bFound ==  false)
							{
								System.out.println("Invalid Environment found in output file generated by PSAGetDataToReplicate.");
								throw new PSAErrorHandler(-1,"Error.InvalidEnvInOutput");
							}
							Child_part_list.add(child_part_details);
						}
					} else {
						dataArray[1] = dataArray[1].replace('\n',' ');
						
						if(strInfoMsg.length() == 0 )
						{
							strInfoMsg = "Following filtering parts expanded in error :";
						}
						
						String strRef = GetReference(strENV, strCOID);
						strInfoMsg = strInfoMsg + "\n" + strRef + " Error : " + dataArray[1];
					}
				}
			}
			//Close the input stream
			in.close();
			
			//Display Information message.
			if(strInfoMsg.length() > 0)
			{
				JOptionPane.showMessageDialog(null, strInfoMsg, "Information", JOptionPane.NO_OPTION);
			}
			
			//Delete temporary file
			File f1 = new File(m_StrEstimateFileName + "_output.csv");
		    boolean success = f1.delete();
		    if(!success)
		    {
		    	System.out.println("Error in deleting temporary file " + m_StrEstimateFileName + "_output.csv");
		    }
		}catch(Exception e)
		{
			System.out.println("Error in reading output file generated by PSAGetDataToReplicate.");
			throw new PSAErrorHandler(-1,"Error.ReadOutputCSVFile");
		}
	}
	
	/**
	 * Create file for estimation with Filter parts.
	 * Gets childrens using PSAGetDataToReplicate.
	 * If PSAGetDataToReplicate is usccessful, then retrieves Env and tables.
	 * Reads estimate file provided by PSAGetDataToReplicate.
	 * Estimates data for the child parts retrieved.
	 * 
	 */
	public void estimateRequest() throws PSAErrorHandler
	{
		System.out.println("Opr->estimateRequest");
		int istatus = 0;
		try
         {
			String StrFileName = CreateEstimateFile();
            Runtime rt = Runtime.getRuntime();
            String strCommand = "PSAGetDataToReplicate " + m_StrUser + " " +m_StrRole + " " + m_StrOrganization + " " + StrFileName;
            System.out.println("Executing command : " + strCommand);
            Process pr = rt.exec(strCommand);

            //Read Input Stream 
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line=null;
            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            //Read Error Stream 
            BufferedReader error = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String error_line=null;
            while((error_line=error.readLine()) != null) {
                System.out.println(error_line);
            }

            int exitVal = pr.waitFor();
            System.out.println("PSAGetDataToReplicate exited with error code "+exitVal);
            istatus = exitVal;

            //Close all stream buffers.
            pr.getInputStream().close();
            pr.getOutputStream().close();
            pr.getErrorStream().close();
            
			//Delete temporary file
			File f1 = new File(StrFileName);
		    boolean success = f1.delete();
		    if(!success)
		    {
		    	System.out.println("Error in deleting temporary file " + StrFileName);
		    }

        } catch(Exception e) {
            istatus = -1;
            System.out.println("Error in executing PSAGetDataToReplicate.");
            throw new PSAErrorHandler(-1,"Error.PSAGetDataToReplicateExecute");
        }
       
        if(istatus == 0)
        {
        	if(m_listEnvTables.size() == 0)
        		GetEnvTables();
        	
        	readEstimateFile();
       		EstimateData();
        } else 
        {
            System.out.println("Error in executing PSAGetDataToReplicate.");
            throw new PSAErrorHandler(-1,"Error.PSAGetDataToReplicateExecute");
        }
	}
	
	/**
	 * Provides the estimation data required to display in Estimation panel.
	 * @return ArrayList<EstimationData>		Estimation data based on the the sites selected.
	 */
	public ArrayList<EstimationData> getEstimateData() throws PSAErrorHandler
	{
		System.out.println("Opr->getEstimateData");
		ArrayList<EstimationData> estimationDatalist = new ArrayList<EstimationData>();
		for(int site=0; site<m_ListSelSite.size(); site++)
		{
			String strSQLQuery = "SELECT REF_VAL FROM GESTION.FGESTION WHERE REF_ID = '" +  m_ListSelSite.get(site) +"' AND $COID="
				 					+ "(SELECT $COID FROM GESTION.PART_LIST WHERE S_REFERENCE='MSADMINVOL')";
			System.out.println("Query : " + strSQLQuery);
			//Execute the Query
			int status = dbCon.ExecuteSelect(strSQLQuery);
			
			//Get the resulting count of the executed query.
			int count = dbCon.getRowCount();
	
			//If there is any error,throw it.
			if((0 != status ) || (0 == count))
			{
				System.out.println("Error while retrieving max allowed data.");
				throw new PSAErrorHandler(-1, "Error.RetrieveMaxAllowedData");
			}
			EstimationData estimate = new EstimationData();
			
			for(int i = 1; i <= count; i++)
			{
				double maxAllowedSize = Double.parseDouble(dbCon.getValue(i, 1).trim());
				estimate.maxAllowedSize = maxAllowedSize;
				System.out.println("Site : "+m_ListSelSite.get(site) + " Max Allowed Size : " + maxAllowedSize);
				
				for(int j=0; j<m_listEnvTables.size(); j++)
				{
					estimate.strSite = m_ListSelSite.get(site);
					estimate.nTotDoc += m_listEnvTables.get(j).nCntDoc;
					estimate.nTotPart += m_listEnvTables.get(j).nCntPart;
					estimate.nTotV4 += m_listEnvTables.get(j).nCntV4;
					estimate.nTotV5 += m_listEnvTables.get(j).nCntV5;
					estimate.dVolume = estimate.dVolume + m_listEnvTables.get(j).dSizeDoc + m_listEnvTables.get(j).dSizeV4 + m_listEnvTables.get(j).dSizeV5;
				}
			}
			estimationDatalist.add(estimate);
		}
		return estimationDatalist;
	}
	
	/**
	 * Lock / Unlock the reset for manipulation.
	 * @param String iStrLockValue		Value to lock or Unlock the request.		
	 */
	public void LockRequestForManip(String iStrLockValue) throws PSAErrorHandler
	{
		System.out.println("Opr->LockRequestForManip");
		String sqlInsertQuery = "";
		sqlInsertQuery = String.format("UPDATE VPMDB2.MSADMIN_REQUEST SET LOCK_FLAG = '"+iStrLockValue.toUpperCase() +"' WHERE REQUEST_ID = X'"+ m_StrReqID + "'");

		System.out.println("Locaking request in VPMDB2.MSADMIN_REQUEST : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Unable to lock in VPMDB2.MSADMIN_REQUEST for request." + m_StrReqID);
			dbCon.Rollback();
			System.out.println("Opr->LockRequestForManip End");
			throw new PSAErrorHandler(-1,"Error.LockMSADMINREQUEST");
		}
		//Update_MSADMIN_REPORT("MOD", "LOCK_FLAG", iStrLockValue.toUpperCase());
		dbCon.Commit();
		System.out.println("Opr->LockRequestForManip End");
	}

	/*
	 * Clear contents of the Opr.
	 */
	private void ClearContaints()
	{
		m_ListAllParts.clear();
		m_ListFilterParts.clear();
		m_ListAddedParts.clear();
		m_ListDeleteParts.clear();
		m_ListUpdatedParts.clear();
		m_filteredpartdetails.clear();

		m_ListSelSite.clear();
		m_ListActiveSite.clear();
		m_REQFreq.m_listValues.clear();
		m_Added_SiteList.clear();
		m_Deleted_SiteList.clear();
		
		m_AddedREQFreq.m_StrType = "";
		m_AddedREQFreq.m_listValues.clear();
		m_DeletedREQFreq.m_StrType = "";
		m_DeletedREQFreq.m_listValues.clear();
		
		m_Active_RequestList.clear();
		m_InActive_RequestList.clear();
	}

	/**
	 * Clone the opr which will be used while updating request.
	 */
	void Clone(PSAAdminMSOutilsOpr destOpr)
	{
		//Assign the member variables of current object to member variables of temporary object. 
		destOpr.m_StrExpiryDate = this.m_StrExpiryDate;
		destOpr.m_StrNextLaunchDate = this.m_StrNextLaunchDate;
		destOpr.m_StrLock = this.m_StrLock;
		destOpr.m_StrMOD = this.m_StrMOD;
		destOpr.m_StrNamePSN = this.m_StrNamePSN;
		destOpr.m_StrReqID = this.m_StrReqID;
		destOpr.m_StrReqName = this.m_StrReqName;
		destOpr.m_StrReqUserID = this.m_StrReqUserID;
		destOpr.m_StrStatus = this.m_StrStatus;
		
		//Copy PSNPart's
		for(PSNPart part : m_ListAllParts)
		{
			PSNPart dummy_part = new PSNPart();
			dummy_part.m_StrEnv = part.m_StrEnv;
			dummy_part.m_StrCOID = part.m_StrCOID;
			dummy_part.m_StrCOMPID = part.m_StrCOMPID;
			dummy_part.m_StrRef = part.m_StrRef;
			
			dummy_part.m_StrSelectedParentEnv = part.m_StrSelectedParentEnv;
			dummy_part.m_StrSelectedParentREF = part.m_StrSelectedParentREF;
			dummy_part.m_StrSelectedConf = part.m_StrSelectedConf;
			dummy_part.m_StrSelectedParentCOID = part.m_StrSelectedParentCOID;
			destOpr.m_ListAllParts.add(dummy_part);
		}

		destOpr.m_ListSelSite = this.m_ListSelSite;
	}

	/**
	 * Get the list of requests for which log is available.
	 * @param String[]		List of Request names
	 */
	public String[] getLogRequestList() throws PSAErrorHandler
	{
		String[] requestList = null;
		//Retrieve Request Data.
		String strSQLQuery = "SELECT REQUEST_NAME FROM VPMDB2.MSADMIN_REQUEST where REQUEST_ID IN (SELECT DISTINCT REQUEST_ID FROM VPMDB2.MSADMIN_REPORT)";
		
		System.out.println("Query List Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to get Request List");
			throw new PSAErrorHandler(-1,"Error.GetRequestList");
		}
		
		requestList = new String[count+1];
		requestList[0] = "";
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			String requestName = dbCon.getValue(i, 1).trim();
			//Add in the list
			requestList[i] = requestName;
		}
		return requestList;
	}

	/**
	 * Get the log details for the request.
	 * @param String iStrRequestName		Request Name
	 * @return ArrayList<PSARequestLog>		Arraylist of request log.
	 */
	public ArrayList<PSARequestLog> GetRequestDetails(String iStrRequestName) throws PSAErrorHandler
	{
		ArrayList <PSARequestLog> requestLog = new ArrayList<PSARequestLog>();
		
		if(iStrRequestName.length() == 0)
		{
			return requestLog;
		}
		//Retrieve Request Data.
		String strSQLQuery = "SELECT TREAT_STATE, CREATOR, CREATION_DATE, MESSAGE, NB_DATA, REPORT.REQUEST_ID FROM VPMDB2.MSADMIN_REPORT REPORT where REPORT.REQUEST_ID = " +
				"			(SELECT REQUEST.REQUEST_ID FROM VPMDB2.MSADMIN_REQUEST REQUEST where REQUEST.REQUEST_NAME = '" + iStrRequestName + "')";
		
		System.out.println("Query List Query : " + strSQLQuery);
		//Execute the Query
		int status = dbCon.ExecuteSelect(strSQLQuery);
		
		//Get the resulting count of the executed query.
		int count = dbCon.getRowCount();

		//If there is any error,throw it.
		if((0 != status ) || (0 == count))
		{
			System.out.println("Unable to get Request details.");
			throw new PSAErrorHandler(-1,"Error.GetRequestdetails");
		}
		
		//For each retrieved row....
		for(int i = 1; i<=count ; i++)
		{
			PSARequestLog log = new PSARequestLog();
			log.setRequestName(iStrRequestName);
			log.setTreatState(dbCon.getValue(i, 1).trim());
			log.setUser(dbCon.getValue(i, 2).trim());
			log.setDate(dbCon.getValue(i, 3).trim());
			log.setMessage(dbCon.getValue(i, 4).trim());
			//Add reading of number of Data if required.
			log.setRequestID(dbCon.getValue(i, 6).trim());
			
			//Add in the list
			requestLog.add(log);
		}
		
		return requestLog;
	}

	/**
	 * Delete the log details from MSADMIN_REPORT for the specified request.
	 * @param iStrRequestName		Request Name
	 */
	public void DeleteRequestLog(String iStrRequestName) throws PSAErrorHandler
	{
		
		System.out.println("Opr->DeleteRequestLog Start");
		//Delete entries from VPMDB2.MSADMIN_REQSITEUS
		String sqlInsertQuery = String.format("DELETE FROM VPMDB2.MSADMIN_REPORT WHERE REQUEST_ID = (SELECT REQUEST_ID FROM VPMDB2.MSADMIN_REQUEST where REQUEST_NAME = '" + iStrRequestName + "')");
		System.out.println("Deleting from VPMDB2.MSADMIN_REQSITEUS : " + sqlInsertQuery);
		
		//Execute the query
		int status = dbCon.ExecuteUpdate(sqlInsertQuery);
		//If any error comes then throw the error.
		if(0 != status )
		{
			System.out.println("Data could not be deleted from VPMDB2.MSADMIN_REQSITEUS");
			dbCon.Rollback();
			System.out.println("Opr->DeleteRequestLog End");
			throw new PSAErrorHandler(-1,"Error.DataDeletionFromErrorForMSADMINREQSITEUS");
		}
		
		dbCon.Commit();
	}
}

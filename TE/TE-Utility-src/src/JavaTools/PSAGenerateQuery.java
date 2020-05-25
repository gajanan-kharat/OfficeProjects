//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSAINIFileParser.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 14/10/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for generating the query files used in 3DCOM and VPM
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.tools;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class PSAGenerateQuery {
	String strQuery[];
	String VpmEnv;
	int dsLimitation=76;
	int nQueries = 0;
	/**
	 * Constructor of the class.
	 */
	public PSAGenerateQuery()
	{
		//EMPTY
	}
	public  void setDsLimitation(int limit){
		dsLimitation=limit;
	}
	public int SetQueryDataListRefVer(String vpmEnv, ArrayList<String> listRef, ArrayList<String> listVer)
	{
		int nRet = 0;
		
		VpmEnv = vpmEnv;
		
		//Get the total set of query Strings to be generated, with dsLimitation parts in each file (limitation DS)
		int nTotalCnt = listRef.size();
		
		nQueries = (nTotalCnt < dsLimitation)?1:((nTotalCnt/dsLimitation) + ((nTotalCnt % dsLimitation) != 0?1:0));
		System.out.println(getLineInfo()+" Total Queries to be created are : " + nQueries);
		strQuery = new String[nQueries];
		
		//Create the query Strings
		for(int i = 0; i < nQueries; i++)
		{
			CreateSubQuery(i, VpmEnv, listRef, listVer);
		}
		return nRet;
	}
	
	public int DumpInFile(String fileName)
	{
		int nRet = 0;
		System.out.println(getLineInfo()+" Writing the query(s) to the file : " + fileName);
		String tmpFileName = fileName;
		String tmpSplFile = fileName;
		tmpSplFile=tmpSplFile.replaceAll(".qry", "_");
		
		for(int i = 0; i < nQueries; i++)
		{
			BufferedWriter writer = null;
			tmpFileName = tmpSplFile +VpmEnv +"_"+i + "_"  + ".qry";
			try
			{
				
				writer = new BufferedWriter(new FileWriter(tmpFileName, false));
				writer.write(strQuery[i]);
			}
			catch(Exception e)
			{
				System.out.println(getLineInfo()+"Error in writing to the file !!! " + e.toString());
				nRet = -1;
			} finally {                       // always close the file
				 if (writer != null) 
				 {
					 try {
						 writer.close();
					 } catch (Exception e) {
					 }
				 }
			}
			if(nRet == -1)
				break;
		}
		
		return nRet;
	}
	
	private int CreateSubQuery(int nOffset, String VpmEnv, ArrayList<String> listRef, ArrayList<String> listVer)
	{
		int nRet = 0;
		int nTotalCnt = listRef.size();
		int nSet = 0;
		
		System.out.println(getLineInfo()+" Creating query for the parts from "+ dsLimitation*nOffset + " to " + dsLimitation*(nOffset+1));
		
		//Create the first line of the query file...
		strQuery[nOffset]="#CDMA.CATCON."+VpmEnv+".PART\n";
				
		for(int i = dsLimitation*nOffset; (i < dsLimitation*(nOffset+1)) && i < nTotalCnt; i++)
		{
			//Add the Reference and Version of the Part to be searched...
			strQuery[nOffset] += "PART_LIST;S_REFERENCE;=;"+ listRef.get(i) +";;\nPART_LIST;C_PART_VERSION;=;"+ listVer.get(i) +";;\nAND\n";
			if((i-(dsLimitation*nOffset)) > 1)
				nSet ++;
			
			//Add an OR operator for every odd entry of part added, excluding the last entry 
			if((((i-(dsLimitation*nOffset)) & 0x01) == 1) || ((i == (((dsLimitation+1)*(nOffset+1))-1)) && ((nTotalCnt - dsLimitation*(nOffset+1)) > 1)))
				strQuery[nOffset] += "OR\n";
			
			//Add an OR operator for every even entry of part added..
			if((nSet == 2) && ((i-(dsLimitation*nOffset))+1 > 2))
			{
				strQuery[nOffset] += "OR\n";
				nSet = 0;
			}
		}
		//If ended with one line , must add OR statement
		if(nSet==1){
			strQuery[nOffset] += "OR\n";
		}
		//System.out.println(getLineInfo()+" QueryFor["+nOffset + "] is : " + strQuery[nOffset]);		
		return nRet;
	}
	
	public int SetQueryDataListRefVer(String vpmEnv, ArrayList<String> listRef, ArrayList<String> listVer,ArrayList<String> listType)
	{
		int nRet = 0;
		
		VpmEnv = vpmEnv;
		
		//Get the total set of query Strings to be generated, with dsLimitation parts in each file (limitation DS)
		int nTotalCnt = listRef.size();
		
		nQueries = (nTotalCnt < dsLimitation)?1:((nTotalCnt/dsLimitation) + ((nTotalCnt % dsLimitation) != 0?1:0));
		System.out.println(getLineInfo()+" Total Queries to be created are : " + nQueries);
		strQuery = new String[nQueries];
		
		//Create the query Strings
		for(int i = 0; i < nQueries; i++)
		{
			CreateSubQuery(i, VpmEnv, listRef, listVer,listType);
		}
		return nRet;
	}
	
	private int CreateSubQuery(int nOffset, String VpmEnv, ArrayList<String> listRef, ArrayList<String> listVer,ArrayList<String> listType)
	{
		int nRet = 0;
		int nTotalCnt = listRef.size();
		int nSet = 0;
		
		System.out.println(getLineInfo()+" Creating query for the parts from "+ dsLimitation*nOffset + " to " + dsLimitation*(nOffset+1));
		
		//Create the first line of the query file...
		strQuery[nOffset]="#CDMA.CATCON."+VpmEnv+".PART\n";
				
		for(int i = dsLimitation*nOffset; (i < dsLimitation*(nOffset+1)) && i < nTotalCnt; i++)
		{
			//Add the Reference and Version and type of the Part to be searched...
			strQuery[nOffset] += "PART_LIST;S_REFERENCE;=;"+ listRef.get(i) +";;\nPART_LIST;C_PART_VERSION;=;"+ listVer.get(i) +";;\nAND\nPART_LIST;S_PART_TYPE;=;"+ listType.get(i) +";;\nAND\n";
			if((i-(dsLimitation*nOffset)) > 1)
				nSet ++;
			
			//Add an OR operator for every odd entry of part added, excluding the last entry 
			if((((i-(dsLimitation*nOffset)) & 0x01) == 1) || ((i == (((dsLimitation+1)*(nOffset+1))-1)) && ((nTotalCnt - dsLimitation*(nOffset+1)) > 1)))
				strQuery[nOffset] += "OR\n";
			
			//Add an OR operator for every even entry of part added..
			if((nSet == 2) && ((i-(dsLimitation*nOffset))+1 > 2))
			{
				strQuery[nOffset] += "OR\n";
				nSet = 0;
			}
		}
		//If ended with one line , must add OR statement
		if(nSet==1){
			strQuery[nOffset] += "OR\n";
		}
		//System.out.println(getLineInfo()+" QueryFor["+nOffset + "] is : " + strQuery[nOffset]);		
		return nRet;
	}
	
	private static String getLineInfo()
	{
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getFileName() +":" + ste.getClassName() + "::" + ste.getMethodName() + " [" + ste.getLineNumber() + "] : ";
	}
	
}

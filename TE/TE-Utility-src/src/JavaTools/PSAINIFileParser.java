//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSAINIFileParser.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 20/07/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for INI file Parser
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/

package com.psa.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

/**
 * SINGLETON CLASS
 * Class for INI file Parser
 *  
 * @author      Mahendra Chuttar
 *  
 * @version     %I%, %G%
 *  
 * @since       1.0
 */
public class PSAINIFileParser {

		/** Variable to hold the ini file name and full path */
	    private String m_strFileName;

	    /** Variable to hold the sections in an ini file. */
	    private LinkedHashMap mhmapSections;

	    /** Variable to hold environment variables **/
	    private Properties mpropEnv;
	    private static String error_msg;
	    private static PSAINIFileParser objINI = null;
	    
	    /**
	     * Creates the single Object of PSAINIFileParser
	     * @return			SINGLETON PSAINIFileParser Object
	     */
	    public static PSAINIFileParser CreateInstance(String iStrINIFileName)
	    {
	    	if(null == objINI)
	    	{
		        // load strFile if one exists.
		        objINI = new PSAINIFileParser(iStrINIFileName);
	    	}
	    	return objINI;
	    }
	    
	    /**
	     * Returns Error message
	     * @return			Error Message occured during INI File loading or Parsing
	     */
	    public String GetErrorMsg()
	    {
	    	return error_msg;
	    }
	    /**
	     * Create a iniFile object from the file named in the parameter.
	     * @param pstrPathAndName The full path and name of the ini file to be used.
	     */
	    public PSAINIFileParser(String pstrPathAndName)
	    {
	        this.mhmapSections = new LinkedHashMap();
	        this.m_strFileName = pstrPathAndName;
	        // Load the specified INI file.
	        if (checkFile(pstrPathAndName)) loadFile();
	    }

	/*------------------------------------------------------------------------------
	 * Getters
	------------------------------------------------------------------------------*/
	    /**
	     * Returns the ini file name being used.
	     * @return the INI file name.
	     */
	    public String getFileName()
	    {
	        return this.m_strFileName;
	    }

	    /**
	     * Returns the specified string property from the specified section.
	     * @param pstrSection the INI section name.
	     * @param pstrProp the property to be retrieved.
	     * @return the string property value.
	     */
	    public String getStringProperty(String pstrSection, String pstrProp)
	    {
	        String      strRet   = null;
	        INIProperty objProp  = null;
	        INISection  objSec   = null;

	        objSec = (INISection) this.mhmapSections.get(pstrSection);
	        if (objSec != null)
	        {
	            objProp = objSec.getProperty(pstrProp);
	            if (objProp != null)
	            {
	                strRet = objProp.getPropValue();
	                objProp = null;
	            }
	            objSec = null;
	        }
	        return strRet;
	    }

	    public int getTotalSections()
	    {
	        return this.mhmapSections.size();
	    }

	    /**
	     * Returns a string array containing names of all sections in INI file.
	     * @return the string array of section names
	     */
	    public String[] getAllSectionNames()
	    {
	        int        iCntr  = 0;
	        Iterator   iter   = null;
	        String[]   arrRet = null;

	        try
	        {
	            if (this.mhmapSections.size() > 0)
	            {
	                arrRet = new String[this.mhmapSections.size()];
	                for (iter = this.mhmapSections.keySet().iterator();;iter.hasNext())
	                {
	                    arrRet[iCntr] = (String) iter.next();
	                    iCntr++;
	                }
	            }
	        }
	        catch (NoSuchElementException NSEExIgnore)
	        {
	        }
	        finally
	        {
	            if (iter != null) iter = null;
	        }
	        return arrRet;
	    }

	    /**
	     * Returns a string array containing names of all the properties under specified section.
	     * @param pstrSection the name of the section for which names of properties is to be retrieved.
	     * @return the string array of property names.
	     */
	    public String[] getPropertyNames(String pstrSection)
	    {
	        String[]   arrRet = null;
	        INISection objSec = null;

	        objSec = (INISection) this.mhmapSections.get(pstrSection);
	        if (objSec != null)
	        {
	            arrRet = objSec.getPropNames();
	            objSec = null;
	        }
	        return arrRet;
	    }

	    /**
	     * Returns a map containing all the properties under specified section.
	     * @param pstrSection the name of the section for which properties are to be retrieved.
	     * @return the map of properties.
	     */
	    public Map getProperties(String pstrSection)
	    {
	        Map        hmRet = null;
	        INISection objSec = null;

	        objSec = (INISection) this.mhmapSections.get(pstrSection);
	        if (objSec != null)
	        {
	            hmRet = objSec.getProperties();
	            objSec = null;
	        }
	        return hmRet;
	    }

	    /**
	     * Reads the INI file and load its contentens into a section collection after 
	     * parsing the file line by line. 
	     */
	    @SuppressWarnings("unchecked")
		private void loadFile()
	    {
	    	System.out.println("loadFile");
	        int            iPos       = -1;
	        String         strLine    = null;
	        String         strSection = null;
	        String         strRemarks = null;
	        BufferedReader objBRdr    = null;
	        FileReader     objFRdr    = null;
	        INISection     objSec     = null;

	        try
	        {
	            objFRdr = new FileReader(this.m_strFileName);
	            if (objFRdr != null)
	            {
	                objBRdr = new BufferedReader(objFRdr);
	                if (objBRdr != null)
	                {
	                    while (objBRdr.ready())
	                    {
	                        iPos = -1;
	                        strLine  = null;
	                        strLine = objBRdr.readLine().trim();
	                        if (strLine == null)
	                        {
	                        }
	                        else if (0 == strLine.length())
	                        {
	                        }
	                        else if (strLine.substring(0, 1).equals(";"))
	                        {
	                            if (null == strRemarks)
	                                strRemarks = strLine.substring(1);
	                            else if (0 == strRemarks.length())
	                                strRemarks = strLine.substring(1);
	                            else
	                                strRemarks = strRemarks + "\r\n" + strLine.substring(1);
	                        }
	                        else if (strLine.startsWith("[") && strLine.endsWith("]"))
	                        {
	                            // Section start reached create new section
	                            if (null != objSec) 
	                                this.mhmapSections.put(strSection.trim(), objSec);
	                            objSec = null;
	                            strSection = strLine.substring(1, strLine.length() - 1);
	                            objSec = new INISection(strSection.trim(), strRemarks);
	                            strRemarks = null;
	                        }
	                        else if ((iPos = strLine.indexOf("=")) > 0 && objSec != null)
	                        {
	                            // read the key value pair 012345=789
	                            objSec.setProperty(strLine.substring(0, iPos).trim(), 
	                                                strLine.substring(iPos + 1).trim(), 
	                                                strRemarks);
	                            strRemarks = null;
	                        }
	                    }
	                    if (null != objSec)
	                        this.mhmapSections.put(strSection.trim(), objSec);
	                }
	            } else
	            {
	            	error_msg = "Unable to load INI File.";
	            	System.out.println(error_msg);
	            }
	        }
	        catch (FileNotFoundException FNFExIgnore)
	        {
	            this.mhmapSections.clear();
            	error_msg = "Unable to load INI File.";
            	System.out.println(error_msg);
	        }
	        catch (IOException IOExIgnore)
	        {
	            this.mhmapSections.clear();
            	error_msg = "Unable to load INI File.";
            	System.out.println(error_msg);
	        }
	        catch (NullPointerException NPExIgnore)
	        {
	            this.mhmapSections.clear();
            	error_msg = "Unable to load INI File.";
            	System.out.println(error_msg);
	        }
	        finally
	        {
	            if (objBRdr != null)
	            {
	                closeReader(objBRdr);
	                objBRdr = null;
	            }
	            if (objFRdr != null)
	            {
	                closeReader(objFRdr);
	                objFRdr = null;
	            }
	            if (objSec != null) objSec = null;
	        }
	    }

	    /**
	     * Helper function to close a reader object.
	     * @param pobjRdr the reader to be closed.
	     */
	    private void closeReader(Reader pobjRdr)
	    {
	        if (pobjRdr == null) return;
	        try
	        {
	            pobjRdr.close();
	        }
	        catch (IOException IOExIgnore)
	        {
	        }
	    }

	    /**
	     * Helper method to check the existance of a file.
	     * @param the full path and name of the file to be checked.
	     * @return true if file exists, false otherwise.
	     */
	    private boolean checkFile(String pstrFile)
	    {
	        boolean blnRet  = false;
	        File    objFile = null;

	        try
	        {
	            objFile = new File(pstrFile);
	            blnRet = (objFile.exists() && objFile.isFile());
	        }
	        catch (Exception e)
	        {
	            blnRet = false;
	        }
	        finally
	        {
	            if (objFile != null) objFile = null;
	        }
	        if(false == blnRet)
	        {
	        	error_msg = "Settings file not found.";
	        	System.out.println(error_msg);
	        }
	        return blnRet;
	    }

	    /**
	     * This function deletes the remark characters ';' from source string
	     * @param pstrSrc the source  string
	     * @return the converted string
	     */
	    private String delRemChars(String pstrSrc)
	    {
	        int    intPos = 0;

	        if (pstrSrc == null) return null;
	        while ((intPos = pstrSrc.indexOf(";")) >= 0)
	        {
	            if (0 == intPos)
	                pstrSrc = pstrSrc.substring(intPos + 1);
	            else if (intPos > 0)
	                pstrSrc = pstrSrc.substring(0, intPos) + pstrSrc.substring(intPos + 1);
	        }
	        return pstrSrc;
	    }

	    /**
	     * This function adds a remark character ';' in source string.
	     * @param pstrSrc source string
	     * @return converted string.
	     */
	    private String addRemChars(String pstrSrc)
	    {
	        int intLen  = 2;
	        int intPos  = 0;
	        int intPrev = 0;

	        String strLeft  = null;
	        String strRight = null;

	        if (pstrSrc == null) return null;
	        while (intPos >= 0)
	        {
	            intLen = 2;
	            intPos = pstrSrc.indexOf("\r\n", intPrev);
	            if (intPos < 0)
	            {
	                intLen = 1;
	                intPos = pstrSrc.indexOf("\n", intPrev);
	                if (intPos < 0) intPos = pstrSrc.indexOf("\r", intPrev);
	            }
	            if (0 == intPos)
	            {
	                pstrSrc = ";\r\n" + pstrSrc.substring(intPos + intLen);
	                intPrev = intPos + intLen + 1;
	            }
	            else if (intPos > 0)
	            {
	                strLeft = pstrSrc.substring(0, intPos);
	                strRight = pstrSrc.substring(intPos + intLen);
	                if (null == strRight)
	                    pstrSrc = strLeft;
	                else if (0 == strRight.length())
	                    pstrSrc = strLeft;
	                else
	                    pstrSrc = strLeft + "\r\n;" + strRight;
	                intPrev = intPos + intLen + 1;
	            }
	        }
	        if (!pstrSrc.substring(0, 1).equals(";"))
	            pstrSrc = ";" + pstrSrc;
	        pstrSrc = pstrSrc + "\r\n";
	        return pstrSrc;
	    }
	    

	/*------------------------------------------------------------------------------
	 * Private class representing the INI Section.
	 *----------------------------------------------------------------------------*/
	    /**
	     * Class to represent the individual ini file section.
	     * @author Mahendra Chuttar
	     * @version 1.0
	     * @since 1.0
	     */
	    private class INISection
	    {
	        /** Variable to hold any comments associated with this section */
	        private String mstrComment;

	        /** Variable to hold the section name. */
	        private String mstrName;
	        
	        /** Variable to hold the properties falling under this section. */
	        private LinkedHashMap mhmapProps;

	        /**
	         * Construct a new section object identified by the name specified in 
	         * parameter.
	         * @param pstrSection The new sections name.
	         */
	        public INISection(String pstrSection)
	        {
	            this.mstrName =  pstrSection;
	            this.mhmapProps = new LinkedHashMap();
	        }

	        /**
	         * Construct a new section object identified by the name specified in 
	         * parameter and associated comments.
	         * @param pstrSection The new sections name.
	         * @param pstrComments the comments associated with this section.
	         */
	        public INISection(String pstrSection, String pstrComments)
	        {
	            this.mstrName =  pstrSection;
	            this.mstrComment = delRemChars(pstrComments);
	            this.mhmapProps = new LinkedHashMap();
	        }
	        
	        /**
	         * Returns any comments associated with this section
	         * @return the comments
	         */
	        public String getSecComments()
	        {
	            return this.mstrComment;
	        }

	        /**
	         * Returns name of the section.
	         * @return Name of the section.
	         */
	        public String getSecName()
	        {
	            return this.mstrName;
	        }

	        /**
	         * Sets the comments associated with this section.
	         * @param pstrComments the comments
	         */
	        public void setSecComments(String pstrComments)
	        {
	            this.mstrComment = delRemChars(pstrComments);
	        }

	        /**
	         * Sets the section name.
	         * @param pstrName the section name.
	         */
	        public void setSecName(String pstrName)
	        {
	            this.mstrName = pstrName;
	        }

	        /**
	         * Removes specified property value from this section. 
	         * @param pstrProp The name of the property to be removed.
	         */
	        public void removeProperty(String pstrProp)
	        {
	            if (this.mhmapProps.containsKey(pstrProp))
	                this.mhmapProps.remove(pstrProp);
	        }

	        /**
	         * Creates or modifies the specified property value.
	         * @param pstrProp The name of the property to be created or modified. 
	         * @param pstrValue The new value for the property.
	         * @param pstrComments the associated comments
	         */
	        @SuppressWarnings("unchecked")
			public void setProperty(String pstrProp, String pstrValue, String pstrComments)
	        {
	            this.mhmapProps.put(pstrProp, new INIProperty(pstrProp, pstrValue, pstrComments));
	        }

	        /**
	         * Returns a map of all properties.
	         * @return a map of all properties
	         */
	        @SuppressWarnings("unchecked")
			public Map getProperties()
	        {
	            return Collections.unmodifiableMap(this.mhmapProps);
	        }

	        /**
	         * Returns a string array containing names of all the properties under 
	         * this section. 
	         * @return the string array of property names.
	         */
	        public String[] getPropNames()
	        {
	            int      iCntr  = 0;
	            String[] arrRet = null;
	            Iterator iter   = null;

	            try
	            {
	                if (this.mhmapProps.size() > 0)
	                {
	                    arrRet = new String[this.mhmapProps.size()]; 
	                    for (iter = this.mhmapProps.keySet().iterator();iter.hasNext();)
	                    {
	                        arrRet[iCntr] = (String) iter.next();
	                        iCntr++;
	                    }
	                }
	            }
	            catch (NoSuchElementException NSEExIgnore)
	            {
	                arrRet = null;
	            }
	            return arrRet;
	        }

	        /**
	         * Returns underlying value of the specified property. 
	         * @param pstrProp the property whose underlying value is to be etrieved.
	         * @return the property value.
	         */
	        public INIProperty getProperty(String pstrProp)
	        {
	            INIProperty objRet = null;

	            if (this.mhmapProps.containsKey(pstrProp))
	                objRet = (INIProperty) this.mhmapProps.get(pstrProp);
	            return objRet;
	        }

	        /* (non-Javadoc)
	         * @see java.lang.Object#toString()
	         */
	        public String toString()
	        {
	            Set          colKeys = null;
	            String       strRet  = "";
	            Iterator     iter    = null;
	            INIProperty  objProp = null;
	            StringBuffer objBuf  = new StringBuffer();

	            if (this.mstrComment != null)
	                objBuf.append(addRemChars(this.mstrComment));
	            objBuf.append("[" + this.mstrName + "]\r\n");
	            colKeys = this.mhmapProps.keySet();
	            if (colKeys != null)
	            {
	                iter = colKeys.iterator();
	                if (iter != null)
	                {
	                    while (iter.hasNext())
	                    {
	                        objProp = (INIProperty) this.mhmapProps.get(iter.next());
	                        objBuf.append(objProp.toString());
	                        objBuf.append("\r\n");
	                        objProp = null;
	                    }
	                }
	            }
	            strRet = objBuf.toString();

	            objBuf  = null;
	            iter    = null;
	            colKeys = null;
	            return strRet;
	        }
	    }

	/*------------------------------------------------------------------------------
	 * Private class representing the INI Property.
	 *----------------------------------------------------------------------------*/
	    /**
	     * This class represents a key value pair called property in an INI file. 
	     * @author Mahendra Chuttar
	     * @version 1.0
	     * @since 1.0
	     */
	    private class INIProperty
	    {
	        /** Variable to hold name of this property */
	        private String mstrName;
	        /** Variable to hold value of this property */
	        private String mstrValue;
	        /** Variable to hold comments associated with this property */
	        private String mstrComments;

	        /**
	         * Constructor
	         * @param pstrName the name of this property.
	         * @param pstrValue the value of this property.
	         */
	        public INIProperty(String pstrName, String pstrValue)
	        {
	            this.mstrName = pstrName;
	            this.mstrValue = pstrValue;
	        }

	        /**
	         * Constructor
	         * @param pstrName the name of this property.
	         * @param pstrValue the value of this property.
	         * @param pstrComments the comments associated with this property.
	         */
	        public INIProperty(String pstrName, String pstrValue, String pstrComments)
	        {
	            this.mstrName = pstrName;
	            this.mstrValue = pstrValue;
	            this.mstrComments = delRemChars(pstrComments);
	        }

	        /**
	         * Returns the string identifier (key part) of this property.
	         * @return the string identifier of this property.
	         */
	        public String getPropName()
	        {
	            return this.mstrName;
	        }

	        /**
	         * Returns value of this property. If value contains a reference to 
	         * environment avriable then this reference is replaced by actual value
	         * before the value is returned.
	         * @return the value of this property.
	         */
	        public String getPropValue()
	        {
	            int    intStart = 0;
	            int    intEnd   = 0;
	            String strVal   = null;
	            String strVar   = null;
	            String strRet   = null;

	            strRet = this.mstrValue;
	            intStart = strRet.indexOf("%");
	            if (intStart >= 0)
	            {
	                intEnd = strRet.indexOf("%", intStart + 1);
	                strVar = strRet.substring(intStart + 1, intEnd);
	                strVal = mpropEnv.getProperty(strVar);
	                if (strVal != null)
	                {
	                    strRet = strRet.substring(0, intStart) + strVal + 
	                    		strRet.substring(intEnd + 1);
	                }
	            }
	            return strRet;
	        }

	        /**
	         * Returns comments associated with this property.
	         * @return the associated comments if any.
	         */
	        public String getPropComments()
	        {
	            return this.mstrComments;
	        }

	        /* (non-Javadoc)
	         * @see java.lang.Object#toString()
	         */
	        public String toString()
	        {
	            String strRet = "";

	            if (this.mstrComments != null)
	                strRet = addRemChars(mstrComments);
	            strRet = strRet + this.mstrName + " = " + this.mstrValue;
	            return strRet;
	        }
	    }
}



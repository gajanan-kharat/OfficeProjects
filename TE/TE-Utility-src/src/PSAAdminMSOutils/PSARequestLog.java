/**********************************************************************************************************
 *
 * FILE NAME	  : PSARequestLog.java
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to hold info of request.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

/**
 * Class used to hold info of request.
 *
 */
public class PSARequestLog {

	private String m_StrTreat_state = "";
	private String m_SrUser = "";
	private String m_StrDate = "";
	private String m_StrMessage = "";
	private String m_StrRequestID = "";
	private String m_StrRequestName = "";
	
	/**
	 * It is used to set value of Treat State
	 * @param iStrTreatState : String 
	 */
	public void setTreatState(String iStrTreatState)
	{
		m_StrTreat_state = iStrTreatState;
	}

	/**
	 *  It return Treat State.
	 * @return : String
	 */
	public String getTreatState()
	{
		return m_StrTreat_state;
	}

	/**
	 * It is used to set value of TreatState.
	 * @param iStrUser : String 
	 */
	public void setUser(String iStrUser)
	{
		m_SrUser = iStrUser;
	}

	/**
	 *  It return User.
	 * @return : String
	 */
	public String getUser()
	{
		return m_SrUser;
	}

	/**
	 * It is used to set value of Date.
	 * @param iStrDate
	 */
	public void setDate(String iStrDate)
	{
		m_StrDate = iStrDate;
	}

	/**
	 *  It return date.
	 * @return : String
	 */
	public String getDate()
	{
		return m_StrDate;
	}

	/**
	 * It is used to set value of Message.
	 * @param iStrMessage
	 */
	public void setMessage(String iStrMessage)
	{
		m_StrMessage = iStrMessage;
	}

	/**
	 *  It return message.
	 * @return : String
	 */
	public String getMessage()
	{
		return m_StrMessage;
	}
	
	/**
	 * It is used to set value of Request Id.
	 * @param iStrRequestID
	 */
	public void setRequestID(String iStrRequestID)
	{
		m_StrRequestID = iStrRequestID;
	}
	
	/**
	 *  It return request id.
	 * @return : String
	 */
	public String getRequestID()
	{
		return m_StrRequestID;
	}
	
	/**
	 * It is used to set value of Request Name.
	 * @param iStrRequestName : String
	 */
	public void setRequestName(String iStrRequestName)
	{

		m_StrRequestName = iStrRequestName;

	}

	/**
	 * It return request name.
	 * @return : String 
	 */
	public String getRequestName()

	{

		return m_StrRequestName;

	}

	/**
	 * This method override to return string contains  
	 * field value seperated  by semicolon.
	 *  @return : String
	 */
	public String toString()
	{
		String seperator = ";";
		String data ="";
		data = m_StrRequestName+seperator+m_StrTreat_state+seperator+m_StrDate+seperator+m_SrUser+seperator+m_StrMessage+"\n";
		return data;
	}
}

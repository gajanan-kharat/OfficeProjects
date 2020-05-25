
package com.psa.DBInterface;
import java.sql.PreparedStatement;
/**
 * @author Anand
 *
 */
public class JDBInterface {
	JDBConnection dbCon;
	private static boolean isHPUX;
	static
	{
		isHPUX = false;
		IdentifyPlatform();
	}
	
	public static void IdentifyPlatform(){
		
		String os = System.getenv("OS").toLowerCase();

	    if(os.indexOf( "sunos" )>= 0){
	    	System.out.println("This is Solaris platform.");
	    }
	    else if((os.indexOf( "hp" ) >= 0)){
			isHPUX = true;
	    	System.out.println("This is HP platform.");
	    }
	    else{
	    	System.out.println("This is UNSUPPORTED platform.");
	    }
	    	
	}
	
	/**
	 * @param args
	 */
	public JDBInterface()
	{
		if(isHPUX){
			dbCon = new JDBConnection_JNI();
		}
		else{
			dbCon = new JDBConnection();
		}
	}
	
	public JDBInterface(String argv[])
	{
		if(isHPUX){
			dbCon = new JDBConnection_JNI();
		}
		else{
			dbCon = new JDBConnection(argv);
		}
	}
	
	public int ExecuteSelect(String sqlSelect)
	{
		int nRet = -1;
		
		if(null != dbCon)
			nRet = dbCon.ExecSelect(sqlSelect);
		
		return nRet;
	}
	
	public int ExecuteUpdate(String sqlUpdate)
	{
		int nRet = 0;
		
		if(null != dbCon)
			nRet = dbCon.ExecUpdate(sqlUpdate);
		
		return nRet;
	}
	
	public int ExecuteDelete(String sqlDelete)
	{
		int nRet = 0;
		
		if(null != dbCon)
			nRet = dbCon.ExecDelete(sqlDelete);
			
		return nRet;
	}
	public int Rollback()
	{
		int nRet = 0;
		
		if(null != dbCon)
			nRet = dbCon.Rollback();
			
		return nRet;
	}
	public int Commit()
	{
		int nRet = 0;
		
		if(null != dbCon)
			nRet = dbCon.Commit();
			
		return nRet;
	}
	
	public int getRowCount()
	{
		return dbCon.getRowCount();
	}
	
	public int getColumnCount()
	{
		return dbCon.getColumnCount();
	}
	
	public String getValue(int nRow, int nCol)
	{
		return dbCon.getValue(nRow, nCol);
	}
	
	public void disConnect()
	{
		dbCon.disConnect();
	}
	
	public PreparedStatement  createPreparedStatement(String sqlString)
	{
		return dbCon.createPreparedStatement(sqlString);
	}
	public void  closeStatement()
	{
		dbCon.closeStatement();
	}
	public void  clearResultSet()
	{
		dbCon.clearResultSet();
	}
}

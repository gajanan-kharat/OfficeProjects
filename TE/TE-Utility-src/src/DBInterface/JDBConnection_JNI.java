package com.psa.DBInterface;

public class JDBConnection_JNI extends JDBConnection{
	
	static int m_iConnectionExists = -1;
	public String DbAlias;
	public String DbServer;
	public int PortNumber = 0;
	public String UserId;
	public String Password;

	static
	{
		System.out.println("Loading Library");
		System.loadLibrary("DBConnection");
		System.out.println("Loaded Library");
	}

	//Native Methods
	private native int ExecuteSelectQuery(String sqlString);
	private native int ExecuteUpdateDelete(String sqlUpdate);
	private native int ExecuteCommit();
	private native int ExecuteRollback();
	private native int GetRowCount();
	private native int GetColumnCount();
	private native String GetValue(int row, int col);

	/**
	 * Constructor of the class.
	 *
	 */
	public JDBConnection_JNI(){
	}
	
	/**
	 * Constructor of the class.
	 * @param argv	:
	 */
	public JDBConnection_JNI(String argv[]){
	}
	
	/**
	 * This method calls the native method ExecuteSelectQuery which executes SELECT query.
	 * @param sqlString : SELECT query to be executed. 
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 */
	public int ExecSelect(String sqlString){
		
		int nRet = 0;
		System.out.println("In JDBInterface_JNI class");

		try{
			nRet = ExecuteSelectQuery(sqlString);
		}
		catch (Exception e)	{
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method ExecuteUpdateDelete which executes UPDATE query.
	 * @param sqlUpdate : UPDATE query to be executed. 
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 */
	public int ExecUpdate(String sqlUpdate){
		
		int nRet = 0;

		try{
			nRet = ExecuteUpdateDelete(sqlUpdate);
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method ExecuteUpdateDelete which executes DELETE query.
	 * @param sqlDelete : DELETE query to be executed. 
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 */
	public int ExecDelete(String sqlDelete){
		
		int nRet = 0;

		try{
			nRet = ExecuteUpdateDelete(sqlDelete);
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method ExecuteCommit which executes COMMIT query.
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 */
	public int Commit(){
		
		int nRet = 0;

		try{
			nRet = ExecuteCommit();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method ExecuteRollback which executes ROLLBACK query.
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 */
	public int Rollback(){
		
		int nRet = 0;

		try{
			nRet = ExecuteRollback();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method GetRowCount which gives the number of rows
	 * in the resultset of last SELECT query executed. 
	 * @return integer value for number of rows.
	 */
	public int getRowCount(){
		
		int nRet = 0;

		try{
			nRet = GetRowCount();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method GetColumnCount which gives the number of columns
	 * in the resultset of last SELECT query executed. 
	 * @return  integer value for number of columns.
	 */
	public int getColumnCount(){
		
		int nRet = 0;

		try{
			nRet = GetColumnCount();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return nRet;
	}

	/**
	 * This method calls the native method GetValue which gives the value at that position
	 * in the resultset of last SELECT query executed. 
	 * @return  String, Actual value in the ResultSet.
	 */
	public String getValue(int nRow, int nCol){
		
		String str = "";

		try{
			str = GetValue(nRow, nCol);
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return str;
	}
	
	/**
	 * This method commits all the modifications in the database and disconnects.
	 * It actually calls the native method ExecuteCommit which executes COMMIT query.
	 * @return 0		: If query execution is successful.
	 * 		   Non-zero	: If query execution fails.
	 *
	 */
	public void disConnect(){
		
		try{
			ExecuteCommit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}


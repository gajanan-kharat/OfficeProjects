package com.psa.DBInterface;

import com.ibm.db2.jcc.*;

import java.sql.*;

public class JDBConnection {
	public String DbAlias;
	public String DbServer;
	public int PortNumber = 0;
	public String UserId;
	public String Password;
	private Connection con = null;
	private ResultSet resultSet = null;
	private Statement stmt = null;
	
	public JDBConnection()
	{
		DbAlias = System.getenv("DB2DBDFT");
		UserId  = System.getenv("LOGNAME");
		Password = "";
		DbServer = "";
		System.out.println(getLineInfo() + " DB2DBDFT->" + DbAlias + " User->" + UserId);
		System.out.println("DB2 driver version loaded is : " + DB2Version.getVersion());
	}
	
	public JDBConnection(String argv[])
	{
		if(argv.length == 5)
		{
			DbAlias = argv[0];
			DbServer = argv[1];
			PortNumber = Integer.valueOf(argv[2]).intValue();
			UserId = argv[3];
			Password = argv[4];
			System.out.println(getLineInfo() + " DB->" + DbAlias + " Server->" + DbServer + " PortNum->" + PortNumber + " User->" + UserId);
		}
	}
	
	private Connection connect() throws Exception
	{
		String url;
		
		if(null == con)
		{
			if(0 == PortNumber)
			{
				System.out.println(getLineInfo() + " Connecting to the DB with DB2 Universal Type 2 driver DBAlias->" + DbAlias);
				url = "jdbc:db2:" + DbAlias;
				Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			}
			else if (0 < PortNumber)
			{
				System.out.println(getLineInfo() + " Connecting to the DB with DB2 Universal Type 4 driver DBAlias->" + DbAlias);
				url = "jdbc:db2://" + DbServer + ":" + PortNumber + "/" + DbAlias;
				Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			}
			else
			{
				System.out.println(getLineInfo() + " Invalid Port Number !!!" + PortNumber);
				throw new Exception("Invalid driver type");
			}
			
			System.out.println(getLineInfo() + " Trying for DB connection ->" + url);
			con = DriverManager.getConnection(url);//, UserId, Password);
			
			System.out.println(getLineInfo() + " Connecting successful !!! ");
			con.setAutoCommit(false);
		}
		
		return con;
	}

	public int Commit()
	{
		int nRet = 0;
		try
		{
			System.out.println(getLineInfo() + " Performing the commit operation ");
			if(null != con)
				con.commit();
		}
		catch(Exception e)
		{
			System.out.println(getLineInfo() + " Error during commit !!! ");
			e.printStackTrace(System.out);
			nRet = -1;
		}
		
		return nRet;
	}

	public int Rollback()
	{
		int nRet = 0;
		try
		{
			System.out.println(getLineInfo() + " Performing the rollback operation");
			if(null != con)
				con.rollback();
		}
		catch(Exception e)
		{
			System.out.println(getLineInfo() + " Error during rollback !!!");
			e.printStackTrace(System.out);
			nRet = -1;
		}
		return nRet;
	}
	
	public void disConnect()
	{
		try
		{
			System.out.println(getLineInfo() + " closing the connection for " + DbAlias);
			if(null != con)
			{
				con.commit();
				con.close();
			}
		}
		catch(Exception e)
		{
			System.out.println(getLineInfo() + " Error during the closing the connection to " + DbAlias);
			e.printStackTrace(System.out);
		}
	}
	
	public int ExecSelect(String sqlString)
	{
		int nRet = 0;
		if(null == con)
		{
			System.out.println(getLineInfo() + " Get Connection before executing the SELECT statement on " + DbAlias);
			try
			{
				connect();
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}
		
		if(0 == nRet)
		{
			System.out.println(getLineInfo() + " Executing :: " + sqlString);
			try
			{
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				stmt.execute(sqlString);
				resultSet = stmt.getResultSet();
			}
			catch(Exception e)
			{
				System.out.println(getLineInfo() + " Error executing the sql statement ");
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}

		return nRet;
	}
	
	public int ExecUpdate(String sqlUpdate)
	{
		int nRet = 0;
		if(null == con)
		{
			System.out.println(getLineInfo() + " Get the connection to " + DbAlias);
			try
			{
				connect();
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}
		
		if(0 == nRet)
		{
			System.out.println(getLineInfo() + " Executing the update : " + sqlUpdate);
			try
			{
				stmt = con.createStatement();
				stmt.executeUpdate(sqlUpdate);
			}
			catch(Exception e)
			{
				System.out.println(getLineInfo() + " Error executing the update statement !!! ");
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}
		
		return nRet;
	}
	
	public int ExecDelete(String sqlDelete)
	{
		int nRet = 0;
		if(null == con)
		{
			System.out.println(getLineInfo() + " Get the connection to " + DbAlias);
			try
			{
				connect();
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}
		
		if(0 == nRet)
		{
			System.out.println(getLineInfo() + " Executing the delete : " + sqlDelete);
			try
			{
				stmt = con.createStatement();
				stmt.executeUpdate(sqlDelete);
			}
			catch(Exception e)
			{
				System.out.println(getLineInfo() + " Error executing the delete statement !!! ");
				e.printStackTrace(System.out);
				nRet = -1;
			}
		}
		return nRet;
	}
	
	public int getRowCount()
	{
		int nRowCount = 0;
		try
		{
			System.out.println(getLineInfo() + " Setting the record to last for obtaining the row count " );
			if( (null != resultSet) &&(resultSet.last()))
			{
				nRowCount = resultSet.getRow();
				resultSet.first();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			nRowCount = -1;
		}
		return nRowCount;
	}
	
	public int getColumnCount()
	{
		int nColCount = 0;
		try
		{
			if(null != resultSet)
			{
				ResultSetMetaData rsMetaData = resultSet.getMetaData();
				nColCount = rsMetaData.getColumnCount();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace(System.out);
			nColCount = -1;
		}

		return nColCount;
	}
	
	public String getValue(int row, int col)
	{
		String strValue=null;
		//System.out.println(getLineInfo() + " Fetching the value at : [" + row + "," + col +"]" );
		try
		{
			if((null != resultSet) && (resultSet.absolute(row)))
				strValue = resultSet.getString(col);
			else
				System.out.println(getLineInfo() + " failed to set the absolute row !!! " );
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			strValue = null;
		}
		
		return strValue;
	}
	
	public static String getLineInfo()
	{
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return /*ste.getFileName() +":" +*/ ste.getClassName() + "::" + ste.getMethodName() + " [" + ste.getLineNumber() + "] : ";
	}

	public PreparedStatement  createPreparedStatement(String sqlString)
	{
		PreparedStatement preparedStatement = null;
		if(sqlString != null)
		{
			if(null == con)
			{
				System.out.println(getLineInfo() + " Create Prepared statement for clob " + DbAlias);
				try
				{
					connect();
				
				}
				catch(Exception e)
				{
					e.printStackTrace(System.out);
					return null;
				}
			}
			try
			{
			 preparedStatement = con.prepareStatement(sqlString);
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
				return null;
			}
		}
		return preparedStatement;
	}
	public void clearResultSet(){
		resultSet=null;
	}
	public void closeStatement(){
		try{
			stmt.close();
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
}


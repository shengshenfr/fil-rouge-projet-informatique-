package utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Utility class for managing the connection to the database.
 * 
 * Based on Philippe TANGUY's example.
 */
public class DatabaseConnection
{
	  private static String username        = "sshen";
	  private static String password        = "ORA3737";
	  private static String host            = "localhost";
	  private static String numPort         = "54321";
	  private static String base            = "enseig";
	  private static String connectString   = "jdbc:postgresql://" + host + ":" + numPort + "/" + base;
	  //-----------------------------------------------------------------------------
	  // Registration of the PostgreSQL JDBC Driver.
	  static
	  {
	    try
	    {
	      DriverManager.registerDriver(new Driver());
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  }
  /**
   * Obtaining a connection to the database.
   *  
   * @return an instance of the Connection class.
   * @throws SQLException
   */
  public static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(connectString,username,password);
  }
}
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 
 * Utility class for managing the connection to the database.
 * 
 * 
 */
public class DatabaseConnection
{
	private static String username        = "postgres";
	private static String password        = "root";
	  private static String host            = "localhost";
	  private static String numPort         = "5432";
	  private static String base            = "filrouge";
	  private static String connectString   = "jdbc:postgresql://" + host + ":" + numPort + "/" + base;
	  //-----------------------------------------------------------------------------
	  // Registration of the PostgreSQL JDBC Driver.
	  static
	  {
	    try
	    {
	      DriverManager.registerDriver(new org.postgresql.Driver());
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
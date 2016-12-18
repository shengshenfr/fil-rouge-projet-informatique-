/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;

// Start of user code (user defined imports)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Individual.*;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;
import utils.DatabaseConnection;

// End of user code

/**
 * Description of AbstractCompetitorManager.
 * 
 * @author Robin
 */
public class AbstractCompetitorManager {
	
	
	/**
	 * Store a team in the database. This team is not stored
     * yet, so his <code>id</code> value is <code>NULL</code>. Once the
     * team is stored, the method returns the team with the
     * <code>id</code> value setted.
     * 
     * @param name the team to be stored.
     * @return the team with the updated value for the id.
     * @throws SQLException
	 */
	
	public static String persist(String name) throws SQLException
	{
		// Two steps in this methods which must be managed in an atomic
	    // (unique) transaction:
	    //  insert the new team;
	  
	Connection c = DatabaseConnection.getConnection();
	try
	{
		c.setAutoCommit(false);
//		System.out.println("insertbefore");
		PreparedStatement psPersist = c.prepareStatement("insert into AbstractCompetitor (competitorName) values (?) ");
		psPersist.setString(1,name);
//		System.out.println("insertafter");
		
		psPersist.executeUpdate();
		psPersist.close();
		
			
	}
	catch (SQLException e)
	{
		try
		{
			c.rollback();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		c.setAutoCommit(true);
		throw e;
	}
	
	c.setAutoCommit(true);
	c.close();
	
	return name;
	}
		
	
//-----------------------------------------------------------------------------------------
	
	public static void update(AbstractCompetitor abstractCompetitor) throws SQLException
	{
	  // 1 - Get a database connection from the class 'DatabaseConnection' 
	  Connection c = DatabaseConnection.getConnection();

	  // 2 - Creating a Prepared Statement with the SQL instruction.
	  //     The parameters are represented by question marks. 
	  
	  PreparedStatement psUpdate = c.prepareStatement("update AbstractCompetitor set competitorName=? where competitorName=?");
	  
	  // 3 - Supplying values for the prepared statement parameters (question marks).
	  
	  psUpdate.setString(1, abstractCompetitor.getAbstractCompetitorName());
	 
	  
	//Executing the prepared statement object among the database.
	  // If needed, a return value (int) can be obtained. It contains
	  // how many rows of a table were updated.
	  // int nbRows = psUpdate.executeUpdate();
	  
	  psUpdate.executeUpdate();

	  // 6 - Closing the Prepared Statement.
	  
	  psUpdate.close();

	  // 7 - Closing the database connection.
	  
	  c.close();
	}
	
//------------------------------------------------------------------------------------
	
	public static void delete(AbstractCompetitor abstractCompetitor) throws SQLException, BadParametersException, CompetitionException, ExistingCompetitorException
	{
	  Connection c = DatabaseConnection.getConnection();
	  
	  PreparedStatement psUpdate = c.prepareStatement("delete * from AbstractCompetitor where competitorName=?");
	  
	  psUpdate.setString(1, abstractCompetitor.getAbstractCompetitorName());
	  psUpdate.executeUpdate();
	  psUpdate.close();
	  c.close();
	  
	}
	
//------------------------------------------------------------------------------------
	
	public static List<AbstractCompetitor> findAll() throws SQLException
	{
		Connection c = DatabaseConnection.getConnection();
		
	    PreparedStatement psSelect = c.prepareStatement("select * from AbstractCompetitor order by competitorName");
	    ResultSet resultSet = psSelect.executeQuery();
	    List<AbstractCompetitor> abstractCompetitors = new ArrayList<AbstractCompetitor>();
	    while(resultSet.next())
	    {
	    	
			try
			{
			abstractCompetitors.add(new AbstractCompetitor(resultSet.getString("competitorName")));
	    	}
	    	catch (SQLException e) {
				
				e.printStackTrace();
	    	} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    resultSet.close();
	    
	    psSelect.close();
	    
	    c.close();
	    
	    return abstractCompetitors;
	}
	
//-----------------------------------------------------------------------------------------------------
	
	public static AbstractCompetitor findByName (String name)
			throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();	
		PreparedStatement psSelect = c
				.prepareStatement("select * from AbstractCompetitor where competitorName = ?" );
		
		psSelect.setString(1, name);
		
		ResultSet resultSet = psSelect.executeQuery();
		
		AbstractCompetitor abstractCompetitor = null;
		
		while (resultSet.next()) {
			
			try {
				abstractCompetitor = new AbstractCompetitor(name);
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		

		resultSet.close();
		psSelect.close();
		
		c.close();
		return abstractCompetitor;
		
		
	}

}

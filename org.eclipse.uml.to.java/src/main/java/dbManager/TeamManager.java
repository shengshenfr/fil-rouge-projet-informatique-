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

import Individual.Team;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;
import utils.DatabaseConnection;

// End of user code

/**
 * Description of TeamManager.
 * 
 * @author Robin
 */
public class TeamManager {
	
	
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
		PreparedStatement psPersist = c.prepareStatement("insert into teams (teamName) values (?) ");
		psPersist.setString(1,name);
		
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
	
	public static void update(Team team) throws SQLException
	{
	  // 1 - Get a database connection from the class 'DatabaseConnection' 
	  Connection c = DatabaseConnection.getConnection();

	  // 2 - Creating a Prepared Statement with the SQL instruction.
	  //     The parameters are represented by question marks. 
	  
	  PreparedStatement psUpdate = c.prepareStatement("update teams set teamName=?, where teamName=?");
	  
	  // 3 - Supplying values for the prepared statement parameters (question marks).
	  
	  psUpdate.setString(1,  team.getTeamName());
	 
	  
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
	
	public static void delete(Team team) throws SQLException, BadParametersException, CompetitionException, ExistingCompetitorException
	{
	  Connection c = DatabaseConnection.getConnection();
	  
	  PreparedStatement psUpdate = c.prepareStatement("delete * from teams where teamName=?");
	  
	  psUpdate.setString(1, team.getTeamName());
	  psUpdate.executeUpdate();
	  psUpdate.close();
	  c.close();
	  
	}
	
//------------------------------------------------------------------------------------
	
	public static List<Team> findAll() throws SQLException
	{
		Connection c = DatabaseConnection.getConnection();
		
	    PreparedStatement psSelect = c.prepareStatement("select * from teams order by username");
	    ResultSet resultSet = psSelect.executeQuery();
	    List<Team> teams = new ArrayList<Team>();
	    while(resultSet.next())
	    {
	    	
	    	Calendar calendar = Calendar.getInstance();
			calendar.setTime(resultSet.getDate("bornDate"));
			try
			{
	    	teams.add(new Team(resultSet.getString("teamName")));
	    	}
	    	catch (SQLException e) {
				
				e.printStackTrace();
	    	}
	    	
	    }
	    resultSet.close();
	    
	    psSelect.close();
	    
	    c.close();
	    
	    return teams;
	}
	
//-----------------------------------------------------------------------------------------------------
	
	public static Team findByName (String name)
			throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();	
		PreparedStatement psSelect = c
				.prepareStatement("select * from teams where teamName = ?" );
		
		psSelect.setString(1, name);
		
		ResultSet resultSet = psSelect.executeQuery();
		
		Team team = null;
		
		while (resultSet.next()) {
			
			team = new Team(name);
		}		

		resultSet.close();
		psSelect.close();
		
		c.close();
		return team;
		
		
	}

}

/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Individual.Player;
import exceptions.BadParametersException;
import exceptions.CompetitionException;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of playerManager.
 * 
 * @author Robin
 */
public class PlayerManager {
	// Start of user code (user defined attributes for playerManager)

	// End of user code

	/**
	 * The constructor.
	 */
	
//------------------------------------------------------------------------------
	
	public static Player persist(Player player) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		try
		{
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into player (userName,firstName,lastName,bornDate) values (?,?,?,?) ");
			psPersist.setString(1,  player.getUserName());
			psPersist.setString(2,  player.getFirstName());
			psPersist.setString(3,  player.getlastName());
			psPersist.setString(4,  player.getbornDate());
			
			psPersist.executeUpdate();
			psPersist.close();
			System.out.println("playerinformation ok");
				
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
		
		return player;
	}
			
		
	//-----------------------------------------------------------------------------------------
		
		public static void update(Player player) throws SQLException
		{
		  // 1 - Get a database connection from the class 'DatabaseConnection' 
		  Connection c = DatabaseConnection.getConnection();

		  // 2 - Creating a Prepared Statement with the SQL instruction.
		  //     The parameters are represented by question marks. 
		  
		  PreparedStatement psUpdate = c.prepareStatement("update player set firstName=?,lastName=?,bornDate=? where userName=?");
		  
		  // 3 - Supplying values for the prepared statement parameters (question marks).
		  
		  psUpdate.setString(1,  player.getFirstName());
		  psUpdate.setString(2,  player.getlastName());
		  psUpdate.setString(3,  player.getbornDate());
		 
		  
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
		
		public static void delete(Player player) throws SQLException, BadParametersException, CompetitionException
		{
		  Connection c = DatabaseConnection.getConnection();
		  
		  PreparedStatement psUpdate = c.prepareStatement("delete * from player where userName=?");
		  
		  psUpdate.setString(1, player.getUserName());
		  psUpdate.executeUpdate();
		  psUpdate.close();
		  c.close();
		  
		}
		
	//------------------------------------------------------------------------------------
		
		public static List<Player> findAll() throws SQLException
		{
			Connection c = DatabaseConnection.getConnection();
			
		    PreparedStatement psSelect = c.prepareStatement("select * from player order by userName");
		    ResultSet resultSet = psSelect.executeQuery();
		    List<Player> players = new ArrayList<Player>();
		    while(resultSet.next())
		    {
		    	
				try
				{
		    	players.add(new Player(resultSet.getString("firstName")+resultSet.getString("lastName"),
		    			resultSet.getString("firstName"),
		    			resultSet.getString("lastName"),
		    	resultSet.getString("bornDate")));
		    	}
		    	catch (SQLException e) {
					
					e.printStackTrace();
		    	}
		    	
		    }
		    resultSet.close();
		    
		    psSelect.close();
		    
		    c.close();
		    
		    return players;
		}
		
	//-----------------------------------------------------------------------------------------------------
		
		public static Player findByName (String name)
				throws SQLException {
			
			Connection c = DatabaseConnection.getConnection();	
			PreparedStatement psSelect = c.prepareStatement("select * from player where userName = ?" );
			
			psSelect.setString(1, name);
			
			ResultSet resultSet = psSelect.executeQuery();
			
			Player player = null;
			
			while (resultSet.next()) {
				
				player = new Player(resultSet.getString("firstName")+resultSet.getString("lastName"),
						resultSet.getString("firstName"),
		    			resultSet.getString("lastName"),
		    	resultSet.getString("bornDate"));
			}		

			resultSet.close();
			psSelect.close();
			
			c.close();
			return player;
			
			
		}

		

	
	
	
//-------------------------------------------------------------------------------
	//---------------------------------------------------------------------------


	// Start of user code (user defined methods for playerManager)

	// End of user code

}

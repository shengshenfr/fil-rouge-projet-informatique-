package dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Interface.Competitor;
import utils.DatabaseConnection;
import Individual.Player;
import Individual.Team;
import Interface.Competitor;


@SuppressWarnings("unused")

/*
 * @author Robin
 */

public class PlayerTeamManager {
	
	
	// TODO create persist, update, delete, findall, findby, and all the functions
	// TODO between competitiosn , competitors and teams
	
	public ArrayList<Team> findByPlayer(String playername) throws SQLException{
		Connection c = DatabaseConnection.getConnection();
	
		PreparedStatement psSelect = c.prepareStatement("select teamName from Player_Team where playerName = ? ");
		psSelect.setString(1, playername);
		ResultSet resultSet = psSelect.executeQuery();	
		ArrayList<Team> team = new ArrayList<Team>();
	    while(resultSet.next())
	    {
	    	
	    	
			try
			{
	    	team.add(new Team(resultSet.getString("teamName")));
	    	}
	    	catch (SQLException e) {
				
				e.printStackTrace();
	    	}
	    	
	    }
	    resultSet.close();
	    
	    psSelect.close();
	    
	    c.close();
	    return team;
	}
	
	
	public ArrayList<Player> findByTeam(String teamname) throws SQLException{
		Connection c = DatabaseConnection.getConnection();
	
		PreparedStatement psSelect = c.prepareStatement("select userName,firstName,lastName,bornDate,playerName from Player inner join PlayerTeam on playerName = userName  where teamName = ?");
		psSelect.setString(1, teamname);
		ResultSet resultSet = psSelect.executeQuery();	
		ArrayList<Player> player = new ArrayList<Player>();
	    while(resultSet.next())
	    {
	    	
	    	
			try
			{
	    	player.add(new Player(resultSet.getString("firstName"),
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
	    return player;
	}
	
	
	public boolean isPlayerTeam(Player player,Team team) throws SQLException{
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select playerName from PlayerTeam where teamName=?");
		psSelect.setString(1,team.getTeamName());
		ResultSet resultSet = psSelect.executeQuery();	
		boolean isPlayerTeam = false;
	    while(resultSet.next())
	    {
	    	
	    	
			try
			{
	    	   if(resultSet.getString("playerName")==player.getUserName()){
	    		   isPlayerTeam = true;
	    		   break;
	    	   }
	    	}
	    	catch (SQLException e) {
				
				e.printStackTrace();
	    	}
	    	
	    }
	    resultSet.close();
	    
	    psSelect.close();
	    
	    c.close();
	    return isPlayerTeam;
		
	}
	
	
	public void persist(Player player,Team team) throws SQLException{
		Connection c = DatabaseConnection.getConnection();
		try
		{
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into PlayerTeam  values (?,?) ");
			psPersist.setString(1,  player.getUserName());
			psPersist.setString(2,  team.getTeamName());
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
	}

	
	public void delete(Player player,Team team) throws SQLException{
		 Connection c = DatabaseConnection.getConnection();
		  
		  PreparedStatement psdelete = c.prepareStatement("delete * from PlayerTeam where (playerName=?) and (teamName = ?)");
		  
		  psdelete.setString(1, player.getUserName());
		  psdelete.setString(2, team.getTeamName());
		  psdelete.executeUpdate();
		  psdelete.close();
		  c.close();
	}
	
	
	public void updateTeam(Player player,Team team) throws SQLException{
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c.prepareStatement("update PlayerTeam set teamName = ? where playerName = ? ");
		psUpdate.setString(1, team.getTeamName());
		psUpdate.setString(2, player.getUserName());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}
	
	
	
//-----------------------------------------------------------------------------------
	
	
	public static ArrayList<Competitor> findCompetitorinTeam(String teamName) {
		// TODO Auto-generated method stub
		return null;
	}



	public static List<Competitor> findAllByCompetition(String competition) {
		// TODO Auto-generated method stub
		return null;
	}


}







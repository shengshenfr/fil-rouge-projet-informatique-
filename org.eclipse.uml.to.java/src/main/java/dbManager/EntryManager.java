/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;

import Bet.Bet;
import Bet.Competition;
import Bet.Entry;
import Bet.Rank;
import Interface.Competitor;

import Individual.*;
import exceptions.BadParametersException;
import exceptions.MissingCompetitionException;

import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of EntryManager.
 * 
 * DAO class (<i>Data Access Object</i>) for the {@link Entry} class. This class
 * provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new entry in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)entry(s) from the database.
 * <li><b>U</b>: update the values stored in the database for an entry.
 * <li><b>D</b>: delete an entry in the database.
 * </ul>
 * 
 *
 * @author Robin
 */
@SuppressWarnings("unused")


public class EntryManager {
	
	// Start of user code (user defined attributes for EntryManager)

	/**
	 * Store an entry in the database for a specific entry. This entry is not stored yet, so his
	 * <code>id</code> value is <code>NULL</code>. Once the entry is stored, the
	 * method returns the entry with the <code>id</code> value setted.
	 * 
	 * @param entry
	 *            the entry to be stored.
	 * @return the entry with the updated value for the id.
	 * @throws SQLException
	 */
	
	/*******************************************************************/
	
	
		//persist for entry
	
public static Entry persist(Entry entry) throws SQLException {
		
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new entry;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (EntrysWinner_id_seq) in the
		// database.
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			
			
			PreparedStatement psPersist = c.prepareStatement("insert into Entry( competitionName, competitorName, rank,idEntry) values(?,?,?,?)");
			psPersist.setString(1, entry.getCompetition().getName());
			if( entry.getCompetitor() instanceof Player){
				psPersist.setString(2, ((Player)entry.getCompetitor()).getUserName());
				
			}
			else if( entry.getCompetitor() instanceof Team){
				psPersist.setString(2,((Team)entry.getCompetitor()).getTeamName());
			}
//			System.out.println(Rank.getIndex(entry.getRank()));
			psPersist.setInt(3, Rank.getIndex(entry.getRank()));
			psPersist.setInt(4, entry.getId());
//			System.out.println(entry.getId());
//			System.out.println(entry.getId());
			psPersist.executeUpdate();
			
			psPersist.close();

		}
		
		catch (SQLException e) {
			try {
				c.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			c.setAutoCommit(true);
			throw e;
			
		}
		
		c.setAutoCommit(true);
		c.close();

		return entry;		
	}

	// ----------------------------------------------------------------------------
	
	
	
	
	
	// -----------------------------------------------------------------------------
		/**
		 * Find an entry by his id.
		 * 
		 * @param id
		 *            the id of the Entry to retrieve.
		 * @return the Entry or null if the id does not exist in the database.
		 * @throws SQLException
		 */
	
	public static Entry findById(Integer idEntry) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from Entry where idEntry=?");
		psSelect.setInt(1, idEntry);
		ResultSet resultSet = psSelect.executeQuery();
		Entry entry = null;
		while (resultSet.next()) {

			try {
				entry = Entry.createEntry(resultSet.getInt("idEntry"),
						resultSet.getString("competitionName"),
						resultSet.getString("competitorName"),
						resultSet.getInt("rank"));
			} catch (BadParametersException e) {
				
				e.printStackTrace();
			} catch (MissingCompetitionException e) {
				
				e.printStackTrace();
			}

		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return entry;
	}
	
//----------------------------------------------------------------------------------------------------- 
	//delete an entry
	
	
	public static void delete(Entry entry) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from Entry where idEntry=?");
		psUpdate.setInt(1, entry.getId());
		
		psUpdate.executeUpdate();
		
		psUpdate.close();
		
		c.close();
		
	}

	
	//-----------------------------------------------------------------------
	// list all the entry on a competition

	public static List<Entry> findAllByCompetition(String competitionName) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from Entry where competitionName=? order by idEntry");
		psSelect.setString(1, competitionName);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Entry> entrys = new ArrayList<Entry>();
		while (resultSet.next()) {
			


			try {
				entrys.add(Entry.createEntry(resultSet.getInt("idEntry"), resultSet
						.getString("competitionName"), resultSet
						.getString("competitorName"), resultSet.getInt("rank")));
			} catch (BadParametersException e) {
				
				e.printStackTrace();
			} catch (MissingCompetitionException e) {
				
				e.printStackTrace();
			}

		}
		resultSet.close();
		psSelect.close();
		c.close();

		return entrys;
	}
	
	//-----------------------------------------------------------------------
	// list all the competitors in a competition

	public static List<AbstractCompetitor> findCompetitorsByCompetition(String competitionName) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select competitorName from Entry where competitionName=?");
		psSelect.setString(1, competitionName);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<AbstractCompetitor> competitors = new ArrayList<AbstractCompetitor>();
		while (resultSet.next()) {
			
			competitors.add(new AbstractCompetitor(resultSet.getString("competitorName")));

		}
		resultSet.close();
		psSelect.close();
		c.close();

		return competitors;
	}
		
	
	//-----------------------------------------------------------------------
	// list all the entry on a competitor
	
	public static List<Entry> findAllByCompetitor(String competitorName) throws SQLException{

		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "Entry where competitorName=? order by idEntry");
		psSelect.setString(1, competitorName);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Entry> entrys = new ArrayList<Entry>();
		while (resultSet.next()) {
			
			try {
				entrys.add(Entry.createEntry(resultSet.getInt("idEntry"), resultSet
						.getString("competitionName"), resultSet
						.getString("competitorName"), resultSet.getInt("rank")));
			} catch (BadParametersException e) {
				
				e.printStackTrace();
			} catch (MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return entrys;
	}

	//-----------------------------------------------------------------------
	// update entrys
	
	public static void update(Entry entry) throws SQLException {
		
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update Entry set rank= ?, competitioName=?, competitorName = ? where idEntry=?");
		psUpdate.setInt(1, Rank.getIndex(entry.getRank()));
		psUpdate.setString(2, entry.getCompetition().getName());
		psUpdate.setString(4, ((AbstractCompetitor)entry.getCompetitor()).getAbstractCompetitorName());
		psUpdate.setInt(3,  entry.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
	
	

//-----------------------------------------------------------------------------------
	//existing competitor in competition :
	
	public static boolean existCompetitorInCompetition (Competition competition,AbstractCompetitor competitor) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		System.out.println(competitor.getAbstractCompetitorName());
		System.out.println(competition.getName());
		PreparedStatement psSelect = c.prepareStatement("select * from Entry where competitorName=? and competitionName=?");
		psSelect.setString(1,competitor.getAbstractCompetitorName());
		
		psSelect.setString(2,competition.getName());
		ResultSet resultSet = psSelect.executeQuery();
		
		boolean exist = false;
		while (resultSet.next()) {
			exist=true;
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return exist;
		
	}
	
	
	
	
	// End of user code

}

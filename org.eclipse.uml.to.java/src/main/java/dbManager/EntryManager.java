/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;

import Bet.Bet;
import Bet.Entry;
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
			PreparedStatement psPersist = c.prepareStatement("insert into Entrys(competitionName, competitorName, rank) values(?,?,?)");
			psPersist.setString(1, entry.getCompetitionName());
			psPersist.setLong(2, entry.getCompetitorName());
			psPersist.setInt(3, entry.getRank());
			
			psPersist.executeUpdate();

			psPersist.close();

			// Retrieving the value of the id with a request on the
			// sequence (subscribers_id_seq).
			
			PreparedStatement psIdValue = c.prepareStatement("select currval('EntrysWinner_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer idEntry = null;
			while (resultSet.next()){
				idEntry = resultSet.getInt("value_id");
			}
			resultSet.close();
			psIdValue.close();
			c.commit();
			
			entry.setIdEntry(idEntry);
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
				.prepareStatement("select * from Entrys where idEntry=?");
		
		ResultSet resultSet = psSelect.executeQuery();
		Entry entry = null;
		while (resultSet.next()) {
			entry = new Entry(resultSet.getInt("idEntry"),
					resultSet.getString("competitionName"),
					resultSet.getString("competitorName"),
					resultSet.getInt("rank"));
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
				.prepareStatement("delete from Entrys where idEntry=?");
		psUpdate.setInt(1, entry.getIdEntry());
		
		psUpdate.executeUpdate();
		
		psUpdate.close();
		
		c.close();
		
	}

	
	//-----------------------------------------------------------------------
	// list all the entry on a competition

	public static List<Entry> findAllByCompetition(String competitionName) {
		
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "Entrys where competitionName=? order by idEntry");
		psSelect.setString(1, competitionName);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Entry> entrys = new ArrayList<Entry>();
		while (resultSet.next()) {
			
			entrys.add(new Entry(resultSet.getInt("idEntry"), resultSet
					.getString("competitionName"), resultSet
					.getString("competitorName"), resultSet.getInt("rank")));
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return entrys;
	}
		
	
	//-----------------------------------------------------------------------
	// list all the entry on a competitor
	
	public static List<Entry> findAllByCompetitor(String competitorName){

		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "Entrys where competitorName=? order by idEntry");
		psSelect.setString(1, competitorName);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Entry> entrys = new ArrayList<Entry>();
		while (resultSet.next()) {
			
			entrys.add(new Entry(resultSet.getInt("idEntry"), resultSet
					.getString("competitionName"), resultSet
					.getString("competitorName"), resultSet.getInt("rank")));
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
				.prepareStatement("update entrys set rank= ?, competitioName=?, competitorName = ? where idEntry=?");
		psUpdate.setInt(1, entry.getRank());
		psUpdate.setString(2, entry.getCompetitionName());
		psUpdate.setString(4, entry.getCompetitorName());
		psUpdate.setInt(3,  entry.getIdEntry());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	

	// End of user code

}

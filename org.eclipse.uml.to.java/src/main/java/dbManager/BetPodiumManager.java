/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;


import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;
import Bet.Bet;
import Bet.Competition;


// Start of user code (user defined imports)

// End of user code

/**
 * Description of BetPodiumManager.
 * 
 * DAO class (<i>Data Access Object</i>) for the {@link Bet} class. This class
 * provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new betPodium in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)bet(s) from the database.
 * <li><b>U</b>: update the values stored in the database for a betPodium.
 * <li><b>D</b>: delete a betPodium in the database.
 * </ul>
 * 
 *
 * @author Robin
 */

@SuppressWarnings("unused")
public class BetPodiumManager {
	
	// Start of user code (user defined attributes for BetPodiumManager)

		// End of user code

		/**
		 * Store a betPodium in the database for a specific subscriber (the subscriber is
		 * included inside the Bet object). This betPodium is not stored yet, so his
		 * <code>id</code> value is <code>NULL</code>. Once the bet is stored, the
		 * method returns the bet with the <code>id</code> value setted.
		 * 
		 * @param betPodium
		 *            the betPodium to be stored.
		 * @return the betPodium with the updated value for the id.
		 * @throws SQLException
		 */
		
		/*******************************************************************/
	
	//persist for betPodium
	
	public static Bet persist(Bet betPodium) throws SQLException {
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new betPodium;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (bets_id_seq) in the
		// database.
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into betsPodium(betOwner, amount,idEntry,idEntry2,idEntry3) values(?,?,?,?,?)");
			psPersist.setString(1, betPodium.getBetOwner());
			psPersist.setLong(2, betPodium.getAmount());
			psPersist.setInt(3, betPodium.getIdEntry());
			psPersist.setInt(4, betPodium.getIdEntry2());
			psPersist.setInt(5, betPodium.getIdEntry3());
			
			psPersist.executeUpdate();
			psPersist.close();
			
			// Retrieving the value of the id with a request on the
			// sequence (subscribers_id_seq).
			
			PreparedStatement psIdValue = c.prepareStatement("select currval('betsPodium_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer idBet = null;
			while (resultSet.next()){
				idBet = resultSet.getInt("value_id");
			}
			resultSet.close();
			psIdValue.close();
			c.commit();
			betPodium.setIdBet(idBet);
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
		return betPodium;		
	}


	// ----------------------------------------------------------------------------
	
	
	
	
	
	// -----------------------------------------------------------------------------
		/**
		 * Find a betPodium by his id.
		 * 
		 * @param id
		 *            the id of the bet to retrieve.
		 * @return the bet or null if the id does not exist in the database.
		 * @throws SQLException
		 */
	
	public static Bet findById(Integer idBet) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsPodium where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		Bet betPodium = null;
		while (resultSet.next()) {
			betPodium = new Bet(resultSet.getInt("idBet"),
					resultSet.getString("betOwner"),
					resultSet.getLong("amount"),
					resultSet.getInt("idEntry"),
					resultSet.getInt("idEntry2"),
					resultSet.getInt("idEntry3"));
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betPodium;
	}
	
	
//-----------------------------------------------------------------------------------

	/**
	 * function which delete a betPodium in the betPodium table
	 * @param betPodium
	 * @throws SQLException
	 */

	public static void delete(Bet betPodium) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from betsPodium where idBet=?");
		psUpdate.setInt(1, betPodium.getIdBet());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}

//--------------------------------------------------------------------------	

	/**
	 * function which list all the betPodium of an owner
	 * @param betOwner
	 * @throws SQLException
	 */

	public static List<Bet> findByOwner(String betOwner) 
		throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "betsPodium where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Bet> betsPodium = new ArrayList<Bet>();
		while (resultSet.next()) {
			betsPodium.add(new Bet(resultSet.getInt("idBet"), resultSet
					.getString("betOwner"), resultSet
					.getLong("amount"), resultSet.getInt("idEntry"),
					resultSet.getInt("idEntry2"),resultSet.getInt("idEntry3")));
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsPodium;
	}


//--------------------------------------------------------------------------	

	public static void update(Bet betPodium) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update betsPodium set betOwner=?, amount=?, idEntry = ?, idEntry2 = ?, idEntry3 = ? where idBet=?");
		psUpdate.setString(1, betPodium.getBetOwner());
		psUpdate.setLong(2, betPodium.getAmount());
		psUpdate.setInt(6, betPodium.getIdBet());
		psUpdate.setInt(3,  betPodium.getIdEntry());
		psUpdate.setInt(4,  betPodium.getIdEntry2());
		psUpdate.setInt(5,  betPodium.getIdEntry3());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
// -----------------------------------------------------------------------------
		/**
		 * Find all the betsPodium in the database.
		 * 
		 * @return
		 * @throws SQLException
		 */
		public static List<Bet> findAll() throws SQLException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsPodium order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<Bet> betsPodium = new ArrayList<Bet>();
			while (resultSet.next()) {
				betsPodium.add(new Bet(resultSet.getInt("idBet"), resultSet
						.getString("betOwner"), resultSet
						.getLong("amount"), resultSet.getInt("idEntry"),
						resultSet.getInt("idEntry2"), resultSet.getInt("idEntry3")));
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsPodium;
		}
	
	

//-------------------------------------------------------------------------
		
		public static Collection<Bet> findPodiumByCompetition(String competition) {
			// TODO Auto-generated method stub
			return null;
		}

//----------------------------------------------------------------------------

}

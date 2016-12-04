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
 * Description of BetManager.
 * 
 * DAO class (<i>Data Access Object</i>) for the {@link Bet} class. This class
 * provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new bet in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)bet(s) from the database.
 * <li><b>U</b>: update the values stored in the database for a bet.
 * <li><b>D</b>: delete a bet in the database.
 * </ul>
 * 
 *
 * @author Robin
 */
public class BetManager {
	// Start of user code (user defined attributes for BetManager)

	// End of user code

	/**
	 * Store a bet in the database for a specific subscriber (the subscriber is
	 * included inside the Bet object). This bet is not stored yet, so his
	 * <code>id</code> value is <code>NULL</code>. Once the bet is stored, the
	 * method returns the bet with the <code>id</code> value setted.
	 * 
	 * @param betWinner
	 *            the betWinner to be stored.
	 * @return the bet with the updated value for the id.
	 * @throws SQLException
	 */
	
	/*******************************************************************/
	
	
		//persist for BetWinner
	
	public static Bet persist(Bet betWinner) throws SQLException {
		
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new betWinner;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (betsWinner_id_seq) in the
		// database.
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into betsWinner(betOwner, amount,idEntry) values(?,?,?)");
			psPersist.setString(1, betWinner.getBetOwner().getUsername());
			psPersist.setLong(2, betWinner.getAmount());
			psPersist.setInt(3, betWinner.getId());
			
			psPersist.executeUpdate();

			psPersist.close();

			// Retrieving the value of the id with a request on the
			// sequence (subscribers_id_seq).
			
			PreparedStatement psIdValue = c.prepareStatement("select currval('betsWinner_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer idBet = null;
			while (resultSet.next()){
				idBet = resultSet.getInt("value_id");
			}
			resultSet.close();
			psIdValue.close();
			c.commit();
			betWinner.setId(idBet);
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

		return betWinner;		
	}

	// ----------------------------------------------------------------------------
	
	
	
	
	
	// -----------------------------------------------------------------------------
		/**
		 * Find a betWinner by his id.
		 * 
		 * @param id
		 *            the id of the bet to retrieve.
		 * @return the bet or null if the id does not exist in the database.
		 * @throws SQLException
		 */
	
	public static Bet findById(Integer idBet) throws SQLException {
		/*Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsWinner where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		Bet betWinner = null;
		while (resultSet.next()) {
			betWinner = new Bet(resultSet.getInt("idBet"),
					resultSet.getString("betOwner"),
					resultSet.getLong("amount"),
					resultSet.getInt("idEntry"));
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betWinner;*/
		return null; // TODO
	}
	

	public static void delete(Bet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from betsWinner where idBet=?");
		psUpdate.setInt(1, betWinner.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}



	public static List<Bet> findByOwner(String betOwner) 
		/*throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "betsWinner where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<Bet> betsWinner = new ArrayList<Bet>();
		while (resultSet.next()) {
			betsWinner.add(new Bet(resultSet.getInt("idBet"), resultSet
					.getString("betOwner"), resultSet
					.getLong("amount"), resultSet.getInt("idEntry")));
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsWinner;*/
		return null; // TODO
	}

	
	public static void update(Bet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update betsWinner set betOwner=?, amount=?, idEntry = ? where idBet=?");
		psUpdate.setString(1, betWinner.getBetOwner().getUsername());
		psUpdate.setLong(2, betWinner.getAmount());
		psUpdate.setInt(4, betWinner.getId());
		psUpdate.setInt(3,  betWinner.getIdEntry()); // TODO
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
	// -----------------------------------------------------------------------------
		/**
		 * Find all the betsWinner in the database.
		 * 
		 * @return
		 * @throws SQLException
		 */
		public static List<Bet> findAll() throws SQLException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsWinner order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<Bet> betsWinner = new ArrayList<Bet>();
			while (resultSet.next()) {
				betsWinner.add(new Bet(resultSet.getInt("idBet"), resultSet
						.getString("betOwner"), resultSet
						.getLong("amount"), resultSet.getInt("idEntry")));
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsWinner;
		}
	
	
	
	
	
	public static ArrayList<Bet> findAllByCompetition(String competition) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public static void deleleAllBetsOnCompetition(Competition c) {
		// TODO Auto-generated method stub
		
	}


	public static Collection<Bet> findWinnerByCompetition(String competition) {
		// TODO Auto-generated method stub
		return null;
	}

	// Start of user code (user defined methods for BetManager)

	// End of user code

}

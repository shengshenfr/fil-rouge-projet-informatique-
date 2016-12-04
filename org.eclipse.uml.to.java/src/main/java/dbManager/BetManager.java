/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;


import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;
import Bet.Bet;
import Bet.Entry;
import Bet.WinnerBet;
import exceptions.*;
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
@SuppressWarnings("unused")
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
	
	public static WinnerBet persist(WinnerBet betWinner) throws SQLException {
		
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
			PreparedStatement psPersist = c.prepareStatement("insert into betsWinner(idBet, betOwner, amount,idEntry) values(?,?,?,?)");
			psPersist.setString(2, betWinner.getBetOwner().getUsername());
			psPersist.setLong(3, betWinner.getAmount());
			psPersist.setInt(4, betWinner.getWinner().getId());
			psPersist.setInt(1, betWinner.getId());
			
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
	
	public static WinnerBet findById(Integer idBet) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsWinner where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		WinnerBet betWinner = null;
		while (resultSet.next()) {
			try {
				betWinner = Bet.createWinnerBet(resultSet.getInt("idBet"),
						resultSet.getString("betOwner"),
						resultSet.getLong("amount"),
						resultSet.getInt("idEntry"));
			} catch (BadParametersException | MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betWinner;
	}
	

	public static void delete(WinnerBet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from betsWinner where idBet=?");
		psUpdate.setInt(1, betWinner.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}



	public static List<WinnerBet> findByOwner(String betOwner) 
		throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "betsWinner where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<WinnerBet> betsWinner = new ArrayList<WinnerBet>();
		while (resultSet.next()) {
			try {
				betsWinner.add(Bet.createWinnerBet(resultSet.getInt("idBet"), resultSet
						.getString("betOwner"), resultSet
						.getLong("amount"), resultSet.getInt("idEntry")));
			} catch (BadParametersException | MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsWinner;
	}

	
	public static void update(WinnerBet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update betsWinner set betOwner=?, amount=?, idEntry = ? where idBet=?");
		psUpdate.setString(1, betWinner.getBetOwner().getUsername());
		psUpdate.setLong(2, betWinner.getAmount());
		psUpdate.setInt(4, betWinner.getId());
		psUpdate.setInt(3,  betWinner.getWinner().getId()); 
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
		public static List<WinnerBet> findAll() throws SQLException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsWinner order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<WinnerBet> betsWinner = new ArrayList<WinnerBet>();
			while (resultSet.next()) {
				try {
					betsWinner.add(Bet.createWinnerBet(resultSet.getInt("idBet"), resultSet
							.getString("betOwner"), resultSet
							.getLong("amount"), resultSet.getInt("idEntry")));
				} catch (BadParametersException | MissingCompetitionException e) {
					
					e.printStackTrace();
				}
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsWinner;
		}
	
	
//--------------------------------------------------------------------------
		//find all the simple bets on one competition	
	
	public static List<WinnerBet> findAllSimpleBetsByCompetition(Competition competition) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsWinner, entrys where betsWinner.idBet = entrys.idEntry and entrys.competitionName = ? ");
		psSelect.setString(1, competition.getName());
		ResultSet resultSet = psSelect.executeQuery();
		List<WinnerBet> betsOnCompetition = new ArrayList<WinnerBet>();
		while (resultSet.next()) {
			try {
				betsOnCompetition.add(Bet.createWinnerBet(resultSet.getInt("idBet"), resultSet
						.getString("betOwner"), resultSet
						.getLong("amount"), resultSet.getInt("idEntry")));
			} catch (BadParametersException | MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		resultSet.close();
		psSelect.close();
		c.close();
		
		return betsOnCompetition;
	}
	
//-----------------------------------------------------------------------------------
	// delete all the simple bets on a competition
	
	public static void deleleAllWinnerBetsOnCompetition(Competition competition) throws SQLException {
		Connection c1 = DatabaseConnection.getConnection();
		
		List<WinnerBet> betsOnCompetition = findAllSimpleBetsByCompetition(competition);
		int betsOnCompetitionSize = betsOnCompetition.size();
		for(int i =0; i < betsOnCompetitionSize; i++){
			delete(betsOnCompetition.get(i));
		}
		c1.close();
		System.out.println("simple bets on the competition" 
							+ competition + "have been deleted");		
		
	}

//-------------------------------------------------------------------------------------
	
	public static ArrayList<Bet> findWinnerByCompetition(Competition competition) {
		
		return null;
	}

	// Start of user code (user defined methods for BetManager)

	// End of user code

}

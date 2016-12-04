/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;


import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;
import Bet.Bet;
import Bet.Competition;
import Bet.DrawBet;
import exceptions.BadParametersException;
import exceptions.MissingCompetitionException;


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

public class DrawBetManager {

	
	// Start of user code (user defined attributes for BetManager)

		// End of user code

		/**
		 * Store a bet in the database for a specific subscriber (the subscriber is
		 * included inside the Bet object). This bet is not stored yet, so his
		 * <code>id</code> value is <code>NULL</code>. Once the bet is stored, the
		 * method returns the bet with the <code>id</code> value setted.
		 * 
		 * @param betDraw
		 *            the betDraw to be stored.
		 * @return the bet with the updated value for the id.
		 * @throws SQLException
		 */
		
		/*******************************************************************/
		
		
		//persist for betDraw
	public static DrawBet persist(DrawBet betDraw) throws SQLException {
		
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new betDraw;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (betsDraw_id_seq) in the
		// database.
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into betsDraw(idBet, betOwner, amount,competitionName) values(?,?,?,?)");
			psPersist.setString(2, betDraw.getBetOwner().getUsername());
			psPersist.setLong(3, betDraw.getAmount());
			psPersist.setString(4, betDraw.getCompetition().getName());
			psPersist.setInt(1, betDraw.getId());
			
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

		return betDraw;		
	}

// ----------------------------------------------------------------------------
	
	
	
	
	
// -----------------------------------------------------------------------------
	// find a draw bet with his id.
	
		/**
		 * Find a betDraw by his id.
		 * 
		 * @param id
		 *            the id of the bet to retrieve.
		 * @return the bet or null if the id does not exist in the database.
		 * @throws SQLException
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 */
	
	public static DrawBet findById(Integer idBet) throws SQLException, BadParametersException, MissingCompetitionException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsDraw where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		DrawBet betDraw = null;
		while (resultSet.next()) {
			betDraw = Bet.createDrawBet(
					resultSet.getInt("idBet"),
					resultSet.getString("betOwner"),
					resultSet.getLong("amount"),
					resultSet.getString("competitionName")
			);
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betDraw;
	}

	
//--------------------------------------------------------------------------	
	
	/**
	 * function which delete a betDraw in the betPodium table
	 * @param betDraw
	 * @throws SQLException
	 */

	public static void delete(DrawBet betDraw) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from betsDraw where idBet=?");
		psUpdate.setInt(1, betDraw.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}

//-------------------------------------------------------------------------	
	
	/**
	 * function which list all the betDraw of an owner
	 * @param betOwner
	 * @throws SQLException
	 * @throws NotExistingCompetitionException 
	 * @throws BadParametersException 
	 */

	public static List<DrawBet> findByOwner(String betOwner) 
		throws SQLException, BadParametersException, MissingCompetitionException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "betsDraw where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<DrawBet> betsDraw = new ArrayList<DrawBet>();
		while (resultSet.next()) {
			betsDraw.add(Bet.createDrawBet(
					resultSet.getInt("idBet"),
					resultSet.getString("betOwner"),
					resultSet.getLong("amount"),
					resultSet.getString("competitionName")
			));
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsDraw;
	}

//------------------------------------------------------------------------	
	public static void update(DrawBet betDraw) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update betsDraw set betOwner=?, amount=?, competitionName = ? where idBet=?");
		psUpdate.setString(1, betDraw.getBetOwner().getUsername());
		psUpdate.setLong(2, betDraw.getAmount());
		psUpdate.setInt(4, betDraw.getId());
		psUpdate.setString(3,  betDraw.getCompetition().getName());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
	}
	
	
// -----------------------------------------------------------------------------
		/**
		 * Find all the betsDraw in the database.
		 * 
		 * @return
		 * @throws SQLException
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 */
		public static List<DrawBet> findAll() throws SQLException, BadParametersException, MissingCompetitionException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsDraw order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<DrawBet> betsDraw = new ArrayList<DrawBet>();
			while (resultSet.next()) {
				betsDraw.add(Bet.createDrawBet(
						resultSet.getInt("idBet"),
						resultSet.getString("betOwner"),
						resultSet.getLong("amount"),
						resultSet.getString("competitionName")
				));
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsDraw;
		}
	
	//--------------------------------------------------------------------------------
		
	//--------------------------------------------------------------------------------
		//find all the draw bets on one competition	
	
	public static List<DrawBet> findAllDrawBetsByCompetition(Competition competition) throws SQLException, BadParametersException, MissingCompetitionException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsWinner, entrys where betsWinner.idBet = entrys.idEntry and entrys.competitionName = ? ");
		psSelect.setString(1, competition.getName());
		ResultSet resultSet = psSelect.executeQuery();
		List<DrawBet> drawBetsOnCompetition = new ArrayList<DrawBet>();
		while (resultSet.next()) {
			drawBetsOnCompetition.add(Bet.createDrawBet(
					resultSet.getInt("idBet"),
					resultSet.getString("betOwner"),
					resultSet.getLong("amount"),
					resultSet.getString("competitionName")
			));
		}
		resultSet.close();
		psSelect.close();
		c.close();
		
		return drawBetsOnCompetition;
	}
	
//-----------------------------------------------------------------------------------
	// delete all the draw bets on a competition
	
	public static void deleleAllDrawBetsOnCompetition(Competition competition) throws SQLException, BadParametersException, MissingCompetitionException {
		Connection c1 = DatabaseConnection.getConnection();
		
		List<DrawBet> betsOnCompetition = findAllDrawBetsByCompetition(competition);
		int betsOnCompetitionSize = betsOnCompetition.size();
		for(int i =0; i < betsOnCompetitionSize; i++){
			delete(betsOnCompetition.get(i));
		}
		c1.close();
		System.out.println("draw bets on the competition" 
							+ competition + "have been deleted");		
			
	}

//--------------------------------------------------------------------------------


}

/*************************************************************************************/

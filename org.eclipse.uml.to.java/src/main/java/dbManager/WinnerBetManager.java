/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;


import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;
import Bet.Bet;
import Bet.Competition;
import Bet.WinnerBet;
import exceptions.BadParametersException;
import exceptions.MissingCompetitionException;
import exceptions.SubscriberException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of BetWinnerManager.
 * 
 * DAO class (<i>Data Access Object</i>) for the {@link Bet} class. This class
 * provides the CRUD functionalities :<br>
 * <ul>
 * <li><b>C</b>: create a new betWinner in the database.
 * <li><b>R</b>: retrieve (or read) a (list of)bet(s) from the database.
 * <li><b>U</b>: update the values stored in the database for a betWinner.
 * <li><b>D</b>: delete a betWinner in the database.
 * </ul>
 * 
 *
 * @author Robin
 */

public class WinnerBetManager {
	
	// Start of user code (user defined attributes for BetWinnerManager)

		// End of user code

		/**
		 * Store a betWinner in the database for a specific subscriber (the subscriber is
		 * included inside the Bet object). This betWinner is not stored yet, so his
		 * <code>id</code> value is <code>NULL</code>. Once the bet is stored, the
		 * method returns the bet with the <code>id</code> value setted.
		 * 
		 * @param betWinner
		 *            the betWinner to be stored.
		 * @return the betWinner with the updated value for the id.
		 * @throws SQLException
		 */
		
		/*******************************************************************/
	
	//persist for betWinner
	
	public static WinnerBet persist(WinnerBet betWinner) throws SQLException {
		// Two steps in this method which must be managed in an atomic
		// (unique) transaction:
		// 1 - insert the new betWinner;
		// 2 - once the insertion is OK, in order to set up the value
		// of the id, a request is done to get this value by
		// requesting the sequence (bets_id_seq) in the
		// database.
		Connection c = DatabaseConnection.getConnection();
		try {
			c.setAutoCommit(false);
			PreparedStatement psPersist = c.prepareStatement("insert into WinnerBet(amount, idEntry,betOwner,idbet) values(?,?,?,?)");
			psPersist.setString(3, betWinner.getBetOwner().getUsername());
			psPersist.setLong(1, betWinner.getAmount());
			psPersist.setInt(2, betWinner.getWinner().getId());
			psPersist.setInt(4, betWinner.getId());
			
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
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 * @throws MissingCompetitionException 
		 */
	
	public static WinnerBet findById(Integer idBet) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException, MissingCompetitionException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from WinnerBet where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		WinnerBet betWinner = null;
		while (resultSet.next()) {
			betWinner = Bet.createWinnerBet(
					resultSet.getInt("idBet"),
				
					resultSet.getLong("amount"),
					resultSet.getInt("idEntry"),
					resultSet.getString("betOwner")
					
			);
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betWinner;
	}
	
	
//-----------------------------------------------------------------------------------

	/**
	 * function which delete a (list of)betWinner in the betWinner table
	 * @param betWinner
	 * @throws SQLException
	 */

	public static void delete(WinnerBet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from WinnerBet where idBet=?");
		psUpdate.setInt(1, betWinner.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}
	
	
	public static void deleteListWinnerBet(List<WinnerBet> listBetWinner) throws SQLException {
		for (int i=0; i<listBetWinner.size(); i++ ){
			delete(listBetWinner.get(i));
		}
			
	}


//--------------------------------------------------------------------------	

	/**
	 * function which list all the betWinner of an owner
	 * @param betOwner
	 * @throws SQLException
	 * @throws NotExistingCompetitionException 
	 * @throws BadParametersException 
	 * @throws MissingCompetitionException 
	 */

	public static List<WinnerBet> findByOwner(String betOwner) 
		throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException, MissingCompetitionException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from WinnerBet where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<WinnerBet> betsWinner = new ArrayList<WinnerBet>();
		while (resultSet.next()) {
			betsWinner.add(Bet.createWinnerBet(
					resultSet.getInt("idBet"),
					resultSet.getLong("amount"),
					resultSet.getInt("idEntry"),
					resultSet.getString("betOwner")
			));
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsWinner;
	}


//--------------------------------------------------------------------------	

	public static void update(WinnerBet betWinner) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update WinnerBet set betOwner=?, amount=?, idEntry = ? where idBet=?");
		psUpdate.setString(1, betWinner.getBetOwner().getUsername());
		psUpdate.setLong(2, betWinner.getAmount());
		psUpdate.setInt(4, betWinner.getId());
		psUpdate.setInt(3, betWinner.getWinner().getId());
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
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 * @throws MissingCompetitionException 
		 */
		public static List<Bet> findAll() throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException, MissingCompetitionException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from WinnerBet order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<Bet> betsWinner = new ArrayList<Bet>();
			while (resultSet.next()) {
				betsWinner.add(Bet.createWinnerBet(
						resultSet.getInt("idBet"),
						
						resultSet.getLong("amount"),
						resultSet.getInt("idEntry"),
						resultSet.getString("betOwner")
				));
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsWinner;
		}
	
//--------------------------------------------------------------------------------------
		
		//----------------------------------------------------------------------------
		//--------------------------------------------------------------------------
			//find all the Winner bets on one competition	
					
		public static List<WinnerBet> findAllWinnerBetsByCompetition(Competition competition) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from WinnerBet INNER JOIN Entry ON WinnerBet.idEntry = entry.idEntry WHERE Entry.competitionName = ? ");
			psSelect.setString(1, competition.getName());
			ResultSet resultSet = psSelect.executeQuery();
			List<WinnerBet> betsOnCompetition = new ArrayList<WinnerBet>();
			while (resultSet.next()) {
				try {
					betsOnCompetition.add(Bet.createWinnerBet(resultSet.getInt("idBet"), 
							resultSet.getLong("amount"), resultSet.getInt("idEntry"), resultSet.getString("betOwner")));
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
		// delete all the Winner bets on a competition
		
		public static void deleleAllWinnerBetsOnCompetition(Competition competition) throws SQLException,BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException  {
			Connection c1 = DatabaseConnection.getConnection();
			
			List<WinnerBet> betsOnCompetition = findAllWinnerBetsByCompetition(competition);
			int betsOnCompetitionSize = betsOnCompetition.size();
			for(int i =0; i < betsOnCompetitionSize; i++){
				delete(betsOnCompetition.get(i));
			}
			c1.close();
			System.out.println("bets Winner on the competition" 
								+ competition + "have been deleted");		
			
		}
				
		//------------------------------------------------------------


}
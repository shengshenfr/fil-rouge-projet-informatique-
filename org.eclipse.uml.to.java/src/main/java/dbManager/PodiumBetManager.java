/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package dbManager;


import java.sql.*;
import java.util.*;
import utils.DatabaseConnection;
import Bet.Bet;
import Bet.Competition;
import Bet.PodiumBet;
import exceptions.BadParametersException;
import exceptions.MissingCompetitionException;
import exceptions.SubscriberException;
import exceptions.CompetitionException;
import exceptions.ExistingCompetitorException;


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

public class PodiumBetManager {
	
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
	
	public static Bet persist(PodiumBet betPodium) throws SQLException {
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
			PreparedStatement psPersist = c.prepareStatement("insert into betsPodium(idBet, betOwner, amount,idEntry,idEntry2,idEntry3) values(?,?,?,?,?,?)");
			psPersist.setString(2, betPodium.getBetOwner().getUsername());
			psPersist.setLong(3, betPodium.getAmount());
			psPersist.setInt(4, betPodium.getPodium().get(0).getId());
			psPersist.setInt(5, betPodium.getPodium().get(1).getId());
			psPersist.setInt(6, betPodium.getPodium().get(2).getId());
			psPersist.setInt(1, betPodium.getId());
			
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
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 */
	
	public static PodiumBet findById(Integer idBet) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException  {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c
				.prepareStatement("select * from betsPodium where idBet=?");
		ResultSet resultSet = psSelect.executeQuery();
		PodiumBet betPodium = null;
		while (resultSet.next()) {
			try {
				betPodium = Bet.createPodiumBet(
						resultSet.getInt("idBet"),
						resultSet.getString("betOwner"),
						resultSet.getLong("amount"),
						resultSet.getInt("idEntry"),
						resultSet.getInt("idEntry2"),
						resultSet.getInt("idEntry3")
				);
			} catch (MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		
		resultSet.close();
		psSelect.close();
		c.close();

		return betPodium;
	}
	
	
//-----------------------------------------------------------------------------------

	/**
	 * function which delete a (list of)betPodium in the betPodium table
	 * @param betPodium
	 * @throws SQLException
	 */

	public static void delete(PodiumBet betPodium) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("delete from betsPodium where idBet=?");
		psUpdate.setInt(1, betPodium.getId());
		psUpdate.executeUpdate();
		psUpdate.close();
		c.close();
		
	}
	

	public static void deleteListPodiumBet(List<PodiumBet> listPodiumBet) throws SQLException {
		for (int i=0; i<listPodiumBet.size(); i++ ){
			delete(listPodiumBet.get(i));
		}
			
	}


//--------------------------------------------------------------------------	

	/**
	 * function which list all the betPodium of an owner
	 * @param betOwner
	 * @throws SQLException
	 * @throws NotExistingCompetitionException 
	 * @throws BadParametersException 
	 */

	public static List<PodiumBet> findByOwner(String betOwner) 
		throws SQLException, BadParametersException,BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException  {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psSelect = c.prepareStatement("select * from"
				+ "betsPodium where betOwner=? order by idBet");
		psSelect.setString(1, betOwner);
		
		ResultSet resultSet = psSelect.executeQuery();
		List<PodiumBet> betsPodium = new ArrayList<PodiumBet>();
		while (resultSet.next()) {
			try {
				betsPodium.add(Bet.createPodiumBet(
						resultSet.getInt("idBet"),
						resultSet.getString("betOwner"),
						resultSet.getLong("amount"),
						resultSet.getInt("idEntry"),
						resultSet.getInt("idEntry2"),
						resultSet.getInt("idEntry3")
				));
			} catch (MissingCompetitionException e) {
				
				e.printStackTrace();
			}
		}
		resultSet.close();
		psSelect.close();
		c.close();

		return betsPodium;
	}


//--------------------------------------------------------------------------	

	public static void update(PodiumBet betPodium) throws SQLException {
		Connection c = DatabaseConnection.getConnection();
		PreparedStatement psUpdate = c
				.prepareStatement("update betsPodium set betOwner=?, amount=?, idEntry = ?, idEntry2 = ?, idEntry3 = ? where idBet=?");
		psUpdate.setString(1, betPodium.getBetOwner().getUsername());
		psUpdate.setLong(2, betPodium.getAmount());
		psUpdate.setInt(6, betPodium.getId());
		psUpdate.setInt(3,  betPodium.getPodium().get(0).getId());
		psUpdate.setInt(4,  betPodium.getPodium().get(1).getId());
		psUpdate.setInt(5,  betPodium.getPodium().get(2).getId());
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
		 * @throws NotExistingCompetitionException 
		 * @throws BadParametersException 
		 * @throws MissingCompetitionException 
		 */
		public static List<Bet> findAll() throws SQLException, BadParametersException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException, MissingCompetitionException  {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsPodium order by betOwner,idBet");
			ResultSet resultSet = psSelect.executeQuery();
			List<Bet> betsPodium = new ArrayList<Bet>();
			while (resultSet.next()) {
				betsPodium.add(Bet.createPodiumBet(
						resultSet.getInt("idBet"),
						resultSet.getString("betOwner"),
						resultSet.getLong("amount"),
						resultSet.getInt("idEntry"),
						resultSet.getInt("idEntry2"),
						resultSet.getInt("idEntry3")
				));
			}
			resultSet.close();
			psSelect.close();
			c.close();

			return betsPodium;
		}
	


//----------------------------------------------------------------------------
//--------------------------------------------------------------------------
	//find all the Podium bets on one competition	
			
		public static List<PodiumBet> findAllPodiumBetsByCompetition(Competition competition) throws SQLException, BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException {
			Connection c = DatabaseConnection.getConnection();
			PreparedStatement psSelect = c
					.prepareStatement("select * from betsPodium, entrys where betsPodium.idBet = entrys.idEntry and entrys.competitionName = ? ");
			psSelect.setString(1, competition.getName());
			ResultSet resultSet = psSelect.executeQuery();
			List<PodiumBet> betsOnCompetition = new ArrayList<PodiumBet>();
			while (resultSet.next()) {
				try {
					betsOnCompetition.add(Bet.createPodiumBet(resultSet.getInt("idBet"), resultSet
							.getString("betOwner"), resultSet
							.getLong("amount"), resultSet.getInt("idEntry"),
							resultSet.getInt("idEntry2"), resultSet.getInt("idEntry3")));
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
		// delete all the podium bets on a competition
		
		public static void deleleAllPodiumBetsOnCompetition(Competition competition) throws SQLException,BadParametersException, CompetitionException, SubscriberException, ExistingCompetitorException  {
			Connection c1 = DatabaseConnection.getConnection();
			
			List<PodiumBet> betsOnCompetition = findAllPodiumBetsByCompetition(competition);
			int betsOnCompetitionSize = betsOnCompetition.size();
			for(int i =0; i < betsOnCompetitionSize; i++){
				delete(betsOnCompetition.get(i));
			}
			c1.close();
			System.out.println("bets podium on the competition" 
								+ competition + "have been deleted");		
			
		}
		
//------------------------------------------------------------
		
//-------------------------------------------------------------------------
	
			
				
//-----------------------------------------------------------------------------------
				
		
		
}
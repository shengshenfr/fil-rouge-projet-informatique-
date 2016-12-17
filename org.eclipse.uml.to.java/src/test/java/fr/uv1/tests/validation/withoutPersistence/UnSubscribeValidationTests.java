package fr.uv1.tests.validation.withoutPersistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interface.*;
import utils.MyCalendar;

public class UnSubscribeValidationTests {
	private FirstIncrementValidationTests increment;
	private Competitor winner, second, third;
	private Scanner sc;
	private String fanfanPwd;
	private ArrayList<Competitor> competitors;
	private String saltoPwd;

	public UnSubscribeValidationTests() {
		increment = FirstIncrementValidationTests.getIncrement();

		this.setUp();

		this.testUnsubscribeNullParameters();
		System.out.println("  >>>>> Fin tests paramètre non instancié\n");

		this.testUnsubscribeInvalidParameters();
		System.out.println("  >>>>> Fin tests paramètre invalide\n");

		this.testUnsubscribeUsernameNotExist();
		System.out.println("  >>>>> Fin tests joueur non existant\n");

		this.testUnsubscribeOK();
		System.out.println("  >>>>> Fin tests paramètres valides\n");
	}

	private void setUp() {
		try {
			fanfanPwd = increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfin"),
					new String("13-04-1990"), increment.getManagerPassword());

			 increment.getBetting().subscribe(new String("Duran"),
					new String("Morgan"), new String("fanfon"),
					new String("20-10-1980"), increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"),
					new String("06-06-1978"), increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}
	}

	private void setUpBet() {
		try {
			// Current date: 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe, addCompetition, createCompetitor ok
			winner = increment.getBetting().createCompetitor(
					new String("Durant"),
					new String("Miguel"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1984, 12, 13).getTime()),
					increment.getManagerPassword());

			second = increment.getBetting().createCompetitor(
					new String("Duranto"),
					new String("Miguel"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1983, 12, 13).getTime()),
					increment.getManagerPassword());

			third = increment.getBetting().createCompetitor(
					new String("Duranti"),
					new String("Migueli"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1982, 4, 13).getTime()),
					increment.getManagerPassword());

			// Added subscribers : fanfan, fanfon, salto

			// Add a competition
			competitors = new ArrayList<Competitor>();
			competitors.add(winner);
			competitors.add(second);
			competitors.add(third);

			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2017, 2, 16), competitors,
					new String(increment.getManagerPassword()));

			// Credit account subscriber 'fanfan'
			increment.getBetting().creditSubscriber(new String("fanfan"), 1500,
					new String(increment.getManagerPassword()));

			// salto and fanfan bet on competition 'a_compet'
			increment.getBetting().betOnPodium(350, new String("a_compet"),
					winner, second, third, new String("fanfan"), saltoPwd);
			increment.getBetting().betOnPodium(200, new String("a_compet"),
					winner, second, third, new String("fanfan"), fanfanPwd);
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testUnsubscribeNullParameters() {
		try {
			increment.getBetting().unsubscribe(null,
					increment.getManagerPassword());
			System.out
					.println("retirer un joueur avec un pseudo non instancié et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().unsubscribe(new String("fanfan"), null);
			System.out
					.println("retirer un joueur avec un pseudo connu et mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testUnsubscribeInvalidParameters() {
		// Tests parameters: invalid format
		try {
			increment.getBetting().unsubscribe(new String(" "),
					increment.getManagerPassword());
			System.out
					.println("retirer un joueur avec un pseudo invalide (\" \") et un mdp correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().unsubscribe(new String("fanfan"),
					new String(" "));
			System.out
					.println(" retirer un joueur avec un pseudo connu et un mdp gestionnaire invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().unsubscribe(new String("fanfan"),
					new String("jki"));
			System.out
					.println(" retirer un joueur avec un pseudo connu et un mdp gestionnaire invalide (\"jki\") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testUnsubscribeUsernameNotExist() {
		try {
			increment.getBetting().unsubscribe(new String("lolita"),
					increment.getManagerPassword());
			System.out
					.println("un joueur a été retiré (lolita) alors qu'il n'était pas inscrit ");
		} catch (Exception e) {
		}
	}

	private void testUnsubscribeOK() {
		// Unsubscribe an existing subscriber that has no unsettled bets
		try {
			increment.getBetting().unsubscribe(new String("fanfin"),
					increment.getManagerPassword());
		} catch (Exception e) {
			System.out
					.println("retirer un joueur inscrit (fanfin) a levé l'exception "
							+ e.getClass());
		}

		// Unsubscribe an existing subscriber that having unsettled bets
		System.out
				.print("  ----- Supprimer un joueur ayant des paris en cours ? (y/n)\n");
		sc = new Scanner(System.in);
		String c = getResponse();
		if (c.equals("y")) {
			// Setup a competition and at least a bet for a subscriber.
			// Suppose used functions already tested
			setUpBet();

			// Unsubscribe a subscriber
			try {
				long tokens = increment.getBetting().unsubscribe(
						new String("fanfan"), increment.getManagerPassword());
				
				// Verify the specification. Just with number of tokens
				if (tokens != 1500)
					System.out
							.println("Le nombre de jetons à donner au joueur fanfan est incorrect (1500 != "
									+ tokens);

				// Verify the specification. Just with number of subscribers. 
				//Suppose list of subscribers tested
				try {
					List<List<String>> liste = increment.getBetting()
							.listSubscribers(increment.getManagerPassword());
					if (liste.size() != 2) {
						System.out
								.println("le nombre de joueurs est incorrect (2 != "
										+ liste.size() + ")");
					}
				} catch (Exception e) {
					assert (false);
				}

			} catch (Exception e) {
				System.out
						.println("retirer un joueur inscrit (fanfan) a levé l'exception "
								+ e.getClass());
			}
		}
	}

	private String getResponse() {
		String s = "x";
		while (!s.equals("y") && !s.equals("n")) {
			s = sc.next();
		}
		return s;
	}
}

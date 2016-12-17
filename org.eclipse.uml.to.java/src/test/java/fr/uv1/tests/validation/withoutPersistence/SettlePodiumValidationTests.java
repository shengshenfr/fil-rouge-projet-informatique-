package fr.uv1.tests.validation.withoutPersistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import Interface.*;
import utils.MyCalendar;

public class SettlePodiumValidationTests {
	private SecondIncrementValidationTests increment;

	private Competitor winner, second, third;
	private String pwd, pwdBis;
	private Competitor winnerTeam, secondTeam, thirdTeam;
	private ArrayList<Competitor> competitors;
	private ArrayList<Competitor> competitorTeams;

	private Scanner sc = new Scanner(System.in);
	public String c = "x";

	public String getResponse() {
		String s = "x";
		while (!s.equals("y") && !s.equals("n")) {
			s = sc.next();
		}
		return s;
	}

	public SettlePodiumValidationTests() {
		increment = SecondIncrementValidationTests.getIncrement();

		System.out.print("  ----- Solder podium sur une compétition ? (y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.setUp();

			this.testWithNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testWithInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");
		}

		System.out
				.print("  ----- Solder podium avec des équipes sur une compétition ?(y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.setUpTeam();

			this.testTeamWithNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testTeamWithInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testTeamOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

		}

	}

	private void setUp() {

		try {
			// Change simulated date to 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe, addCompetition, and createCompetitor ok
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

			// Add subscribers
			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"),
					new String("11-03-1987"),
					new String(increment.getManagerPassword()));
			pwd = increment.getBetting().subscribe(new String("Mata"),
					new String("Annabelle"), new String("brinco"),
					new String("20-05-1982"),
					new String(increment.getManagerPassword()));
			pwdBis = increment.getBetting().subscribe(new String("Piquer"),
					new String("Annabelle"), new String("aupar"),
					new String("01-07-1985"),
					new String(increment.getManagerPassword()));
			increment.getBetting().subscribe(new String("Piqueras"),
					new String("Rosa"), new String("jefaza"),
					new String("11-10-1979"),
					new String(increment.getManagerPassword()));

			// Add a competition
			competitors = new ArrayList<Competitor>();
			competitors.add(winner);
			competitors.add(second);
			competitors.add(third);

			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2017, 2, 16), competitors,
					new String(increment.getManagerPassword()));

			// Credit subscribers
			increment.getBetting().creditSubscriber(new String("salto"), 890,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("brinco"), 1500,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("aupar"), 550,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("jefaza"), 2210,
					new String(increment.getManagerPassword()));

			// Bet
			increment.getBetting().betOnPodium(350, new String("a_compet"),
					winner, second, third, new String("brinco"), pwd);
			increment.getBetting().betOnPodium(1150, new String("a_compet"),
					winner, second, third, new String("brinco"), pwd);
			increment.getBetting().betOnPodium(300, new String("a_compet"),
					second, winner, third, new String("aupar"), pwdBis);
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testWithNullParameters() {
		// Tests "entries" : null
		try {
			increment.getBetting().settlePodium(null, null, null, null, null);
			System.out
					.println("solder podium d'une compétition avec aucun paramètre instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), null,
					null, null, null);
			System.out
					.println("solder podium d'une compétition connue, tous les autres paramètres non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					null, null, null);
			System.out
					.println("solder podium d'une compétition avec second, troisième mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().settlePodium(new String("a_compet"), null,
					second, null, null);
			System.out
					.println("solder podium d'une compétition avec premier, troisième et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), null,
					null, third, null);
			System.out
					.println("solder podium d'une compétition avec premier, second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					null, third, null);
			System.out
					.println("solder podium d'une compétition avec second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting()
					.settlePodium(null, winner, null, third, null);
			System.out
					.println("solder podium d'une compétition avec compétition, second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(null, winner, second, third,
					null);
			System.out
					.println("solder podium d'une compétition avec compétition et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					second, third, null);
			System.out
					.println("solder podium d'une compétition avec mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}

	}

	private void testWithInvalidParameters() {
		// Tests invalid parameters
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					second, third, new String(" "));
			System.out
					.println("solder podium d'une compétition avec mdp gestionnaire incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String(" "), winner,
					second, third, new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("qdd"), winner,
					second, third, new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					second, second, new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec podium invalide (répétitions) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					winner, second, new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec podium invalide (répétitions) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("a_compet"), winner,
					third, second, new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition qui est encore ouverte n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testOK() {
		try {
			// Change simulated date to make the competition "settable"
			// New "today" date competition date +1 day
			MyCalendar.setDate(2017, 2, 17);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			increment.getBetting().settlePodium(new String("a_compet"), second,
					winner, third, new String(increment.getManagerPassword()));
		} catch (Exception e) {
			System.out
					.println("Solder podium valide (Duranto,Durant,Duranti) a levé l'exception "
							+ e.getMessage());
		}

		// brinco will loose all tokens
		try {
			increment.getBetting().debitSubscriber("brinco", 1,
					new String(increment.getManagerPassword()));
			System.out
					.println("Solder n'est pas valide (\"brinco\" ne doit pas avoir des jetons)");
		} catch (Exception e) {
		}
		// aupar will win (300*1800)/300 = 1800
		try {
			increment.getBetting().debitSubscriber("aupar", 2051,
					new String(increment.getManagerPassword()));
			System.out
					.println("Solder n'est pas valide (\"aupar\" doit avoir 2050 jetons)");
		} catch (Exception e) {
		}
	}

	private void setUpTeam() {
		try {

			// Change simulated date to 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe, addCompetition and createCompetitor ok
	
			winnerTeam = increment.getBetting().createCompetitor(
					new String("Madrid"), increment.getManagerPassword());

			secondTeam = increment.getBetting().createCompetitor(
					new String("Barca"), increment.getManagerPassword());

			thirdTeam = increment.getBetting().createCompetitor(
					new String("Villareal"), increment.getManagerPassword());

			// Add subscribers
			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("aloha"),
					new String("11-03-1987"),
					new String(increment.getManagerPassword()));
			pwd = increment.getBetting().subscribe(new String("Mata"),
					new String("Annabelle"), new String("alohi"),
					new String("20-05-1982"),
					new String(increment.getManagerPassword()));
			pwdBis = increment.getBetting().subscribe(new String("Piquer"),
					new String("Annabelle"), new String("aloho"),
					new String("01-07-1985"),
					new String(increment.getManagerPassword()));
			increment.getBetting().subscribe(new String("Piqueras"),
					new String("Rosa"), new String("alohu"),
					new String("11-10-1979"),
					new String(increment.getManagerPassword()));

			// Add a competition
			competitorTeams = new ArrayList<Competitor>();
			competitorTeams.add(winnerTeam);
			competitorTeams.add(secondTeam);
			competitorTeams.add(thirdTeam);

			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 2, 19), competitorTeams,
					new String(increment.getManagerPassword()));

			// Credit subscribers
			increment.getBetting().creditSubscriber(new String("aloha"), 890,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("alohi"), 1500,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("aloho"), 550,
					new String(increment.getManagerPassword()));
			increment.getBetting().creditSubscriber(new String("alohu"), 2210,
					new String(increment.getManagerPassword()));

			// Bet
			increment.getBetting()
					.betOnPodium(350, new String("otra_compet"), winnerTeam,
							secondTeam, thirdTeam, new String("alohi"), pwd);
			increment.getBetting()
					.betOnPodium(1150, new String("otra_compet"), winnerTeam,
							secondTeam, thirdTeam, new String("alohi"), pwd);
			increment.getBetting().betOnPodium(300, new String("otra_compet"),
					secondTeam, winnerTeam, thirdTeam, new String("aloho"),
					pwdBis);
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testTeamWithNullParameters() {
		// Tests "entries" : null
		try {
			increment.getBetting().settlePodium(null, null, null, null, null);
			System.out
					.println("solder podium d'une compétition avec aucun paramètre instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					null, null, null, null);
			System.out
					.println("solder podium d'une compétition connue, tous les autres paramètres non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, null, null, null);
			System.out
					.println("solder podium d'une compétition avec second, troisième mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					null, secondTeam, null, null);
			System.out
					.println("solder podium d'une compétition avec premier, troisième et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					null, null, thirdTeam, null);
			System.out
					.println("solder podium d'une compétition avec premier, second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, null, thirdTeam, null);
			System.out
					.println("solder podium d'une compétition avec second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(null, winnerTeam, null,
					thirdTeam, null);
			System.out
					.println("solder podium d'une compétition avec compétition, second et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(null, winnerTeam, secondTeam,
					thirdTeam, null);
			System.out
					.println("solder podium d'une compétition avec compétition et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, null);
			System.out
					.println("solder podium d'une compétition avec mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamWithInvalidParameters() {
		// Tests invalid parameters
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String(" "));
			System.out
					.println("solder podium d'une compétition avec mdp gestionnaire incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String(" "), winnerTeam,
					secondTeam, thirdTeam,
					new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("qdd"), winnerTeam,
					secondTeam, thirdTeam,
					new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, secondTeam, secondTeam,
					new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec podium invalide (répétitions) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, winnerTeam, secondTeam,
					new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition avec podium invalide (répétitions) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().settlePodium(new String("otra_compet"),
					winnerTeam, thirdTeam, secondTeam,
					new String(increment.getManagerPassword()));
			System.out
					.println("solder podium d'une compétition qui est encore ouverte n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamOK() {
		try {
			// Change simulated date to make the competition "settable"
			// New "today" date competition date +1 day
			MyCalendar.setDate(2018, 2, 20);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			increment.getBetting().settlePodium(new String("otra_compet"),
					secondTeam, winnerTeam, thirdTeam,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			System.out.println("Solder podium valide a levé l'exception "
					+ e.getClass());
		}

		// alohi will loose all tokens
		try {
			increment.getBetting().debitSubscriber("alohi", 1,
					new String(increment.getManagerPassword()));
			System.out
					.println("Solder n'est pas valide (\"alohi\" ne doit pas avoir des jetons)");
		} catch (Exception e) {
		}
		
		// aloho will win (300*1800)/300 = 1800
		try {
			increment.getBetting().debitSubscriber("aloho", 2051,
					new String(increment.getManagerPassword()));
			System.out
					.println("Solder n'est pas valide (\"aloho\" doit avoir 2050 jetons)");
		} catch (Exception e) {
		}
	}
}
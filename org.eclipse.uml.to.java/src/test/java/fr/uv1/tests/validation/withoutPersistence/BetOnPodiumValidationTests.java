package fr.uv1.tests.validation.withoutPersistence;

import java.text.SimpleDateFormat;
import java.util.*;

import Interface.*;
import exceptions.*;
import utils.MyCalendar;

public class BetOnPodiumValidationTests {
	private SecondIncrementValidationTests increment;

	private String cName;
	private Competitor winner, second, third;
	private Competitor winnerTeam, secondTeam, thirdTeam;
	private String pwd, pwdBis;
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

	public BetOnPodiumValidationTests() {
		increment = SecondIncrementValidationTests.getIncrement();

		System.out
				.print("  ----- Parier podium sur une compétition individuelle ? (y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.setUp();

			this.testWithNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testWithInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			System.out
					.print("  ----- Parier podium par un compétiteur de la compétition ? (y/n)\n");
			String resp = getResponse();
			if (resp.equals("y")) {
				this.testPlayerIsACompetitor();
				System.out
						.println("  >>>>> Fin tests parieur=compétiteur de la compétition\n");
			}

			this.testOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

			this.testNotEnoughTokens();
			System.out.println("  >>>>> Fin tests pas assez de jetons\n");
		}

		System.out
				.print("  ----- Parier podium avec des équipes sans membres sur une compétition ? (y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.setUpTeam();

			this.testTeamWithNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testTeamWithInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testTeamOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

			this.testTeamNotEnoughTokens();
			System.out.println("  >>>>> Fin tests pas assez de jetons\n");
		}
		System.out
				.print("  ----- Parier podium avec des équipes avec membres sur une compétition ?(y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.setUpTeamMembers();

			this.testTeamMembersWithNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testTeamMembersWithInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testTeamMembersOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

			this.testTeamMembersNotEnoughTokens();
			System.out.println("  >>>>> Fin tests pas assez de jetons\n");

			System.out
					.println(" ----- Parier podium équipes par un compétiteur de la compétition ? (y/n)\n");
			String resp = getResponse();
			if (resp.equals("y")) {
				this.testTeamMembersPlayerIsACompetitor();
				System.out
						.println("  >>>>> Fin tests parieur=compétiteur de la compétition\n");
			}
		}
	}

	private void setUp() {
		try {
			// On fixe la date au 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe and addCompetition ok
			// Suppose createCompetitor ok

			cName = new String("a_compet");
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

			// Add a subscriber
			pwd = increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"), new String("11-03-1987"),
					new String(increment.getManagerPassword()));

			// Add a competition
			competitors = new ArrayList<Competitor>();
			competitors.add(winner);
			competitors.add(second);
			competitors.add(third);

			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2016, 2, 1), competitors,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testWithNullParameters() {
		// Tests "entries" : null
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					null, null);
			System.out
					.println("Ajout d'un pari podium avec nom compétition, podium et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, null,
					null, null);
			System.out
					.println("Ajout d'un pari podium avec podium et username/mdp non instanciés  n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winner, null, null,
					null, null);
			System.out
					.println("l'ajout d'un pari podium avec nom compétition, second et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, second, null,
					null, null);
			System.out
					.println("l'ajout d'un pari podium avec premier et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, third,
					null, null);
			System.out
					.println("l'ajout d'un pari podium avec premier et deuxième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {

		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					new String("salto"), null);
			System.out
					.println("l'ajout d'un pari podium avec aucun paramètre instancié sauf username n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					null, pwd);
			System.out
					.println("l'ajout d'un pari podium avec aucun paramètre instancié sauf pwd n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winner, null, null,
					null, null);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winner, null, null,
					new String("salto"), null);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {

		}
		try {
			increment.getBetting().betOnPodium(100, cName, winner, null, null,
					null, pwd);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et username non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winner, null, null,
					new String("salto"), null);
			System.out
					.println("l'ajout d'un pari podium avec compétition, second et troisième et mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winner, null, null,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, premier, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, null,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, second, null,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, second, third,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, third,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et deuxième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, third,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, premier et second non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, third,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et second non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winner, null, third,
					new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec second non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winner, second,
					null, new String("salto"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec troisième non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testWithInvalidParameters() {
		// Tests invalid parameters
		try {
			increment.getBetting().betOnPodium(-10, cName, winner, second,
					third, new String("salto"), pwd);
			System.out
					.println("Ajout d'un pari podium avec un nombre jetons invalide (-10) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().betOnPodium(10, new String(" "), winner,
					second, third, new String("salto"), pwd);
			System.out
					.println("Ajout d'un pari podicum avec une compétition invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("qsdfq^$"),
					winner, second, third, new String("salto"), pwd);
			System.out
					.println("Ajout d'un pari podicum avec une compétition invalide (\"qsdfq^$\") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("a_compet"),
					winner, second, third, new String(" "), pwd);
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("a_compet"),
					winner, second, third, new String("dsfqç£"), pwd);
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("a_compet"),
					winner, second, third, new String("salto"),
					new String("qsdfwdsf"));
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testPlayerIsACompetitor() {
		String pwdTiti = null;
		try {
			pwdTiti = increment.getBetting().subscribe(new String("Duranto"),
					new String("Miguel"), new String("titi"), new String("13-12-1983"),
					new String(increment.getManagerPassword()));

			// Credit tokens
			increment.getBetting().creditSubscriber("titi", 100,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().betOnPodium(9, "a_compet", winner, second,
					third, new String("titi"), pwdTiti);
			System.out
					.println("l'ajout d'un pari podium par un participant à la compétition n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		// On remet la BD comme avant
		try {
			increment.getBetting().unsubscribe(new String("titi"),
					increment.getManagerPassword());
		} catch (AuthentificationException | ExistingSubscriberException e) {
			assert (false);
		}

	}

	private void testOK() {
		// Tests ok
		// before betting credit the subscriber. Suppose creditSubscriber
		// tested
		// Add a subscriber
		try {
			increment.getBetting().creditSubscriber(new String("salto"), 1500,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().betOnPodium(350, new String("a_compet"),
					winner, second, third, new String("salto"), pwd);
		} catch (Exception e) {
			System.out
					.println("Ajout d'un pari podium correct a levé une exception "
							+ e.getClass());
			e.printStackTrace();
		}

		try {
			increment.getBetting().betOnPodium(1150, new String("a_compet"),
					winner, second, third, new String("salto"), pwd);
		} catch (Exception e) {
			System.out
					.println("Ajout d'un pari podium correct a levé une exception ");
		}
	}

	private void testNotEnoughTokens() {
		try {
			increment.getBetting().betOnPodium(1, new String("a_compet"),
					winner, second, third, new String("salto"), pwd);

			System.out
					.println("Ajout d'un pari podium avec trop de jetons (1) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void setUpTeam() {
		try {
			// On fixe la date au 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe and addCompetition ok
			// Suppose createCompetitor ok

			cName = new String("otra_compet");
			winnerTeam = increment.getBetting().createCompetitor(
					new String("Madrid"), increment.getManagerPassword());

			secondTeam = increment.getBetting().createCompetitor(
					new String("Barca"), increment.getManagerPassword());

			thirdTeam = increment.getBetting().createCompetitor(
					new String("Villareal"), increment.getManagerPassword());

			// Add a subscriber
			pwd = increment.getBetting().subscribe(new String("Dida"),
					new String("John"), new String("lolito"), new String("11-03-1987"),
					new String(increment.getManagerPassword()));

			// Add a competition
			competitorTeams = new ArrayList<Competitor>();
			competitorTeams.add(winnerTeam);
			competitorTeams.add(secondTeam);
			competitorTeams.add(thirdTeam);

			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 3, 1), competitorTeams,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testTeamWithNullParameters() {
		// Tests "entries" : null
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					null, null);
			System.out
					.println("Ajout d'un pari podium avec nom compétition, podium et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, null,
					null, null);
			System.out
					.println("Ajout d'un pari podium avec podium et username/mdp non instanciés  n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winnerTeam, null,
					null, null, null);
			System.out
					.println("l'ajout d'un pari podium avec nom compétition, second et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, secondTeam,
					null, null, null);
			System.out
					.println("l'ajout d'un pari podium avec premier et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null,
					thirdTeam, null, null);
			System.out
					.println("l'ajout d'un pari podium avec premier et deuxième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					new String("lolito"), null);
			System.out
					.println("l'ajout d'un pari podium avec aucun paramètre instancié sauf username n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					null, pwd);
			System.out
					.println("l'ajout d'un pari podium avec aucun paramètre instancié sauf pwd n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winnerTeam, null,
					null, null, null);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et username/mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winnerTeam, null,
					null, new String("lolito"), null);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {

		}
		try {
			increment.getBetting().betOnPodium(100, cName, winnerTeam, null,
					null, null, pwd);
			System.out
					.println("l'ajout d'un pari podium avec second et troisième et username non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winnerTeam, null,
					null, new String("lolito"), null);
			System.out
					.println("l'ajout d'un pari podium avec compétition, second et troisième et mdp non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, winnerTeam, null,
					null, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null, null,
					new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, premier, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null, null,
					new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier, second et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, secondTeam,
					null, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et troisième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, secondTeam,
					thirdTeam, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null,
					thirdTeam, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et deuxième non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, null, null, null,
					thirdTeam, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec compétition, premier et second non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, null, null,
					thirdTeam, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec premier et second non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winnerTeam, null,
					thirdTeam, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec second non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(100, cName, winnerTeam,
					secondTeam, null, new String("lolito"), pwd);
			System.out
					.println("l'ajout d'un pari podium avec troisième non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamWithInvalidParameters() {
		// Tests invalid parameters
		try {
			increment.getBetting().betOnPodium(-10, cName, winnerTeam,
					secondTeam, thirdTeam, new String("lolito"), pwd);
			System.out
					.println("Ajout d'un pari podium avec un nombre jetons invalide n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			increment.getBetting().betOnPodium(10, new String(" "), winnerTeam,
					secondTeam, thirdTeam, new String("lolito"), pwd);
			System.out
					.println("Ajout d'un pari podicum avec une compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("qsdfq^$"),
					winnerTeam, secondTeam, thirdTeam, new String("lolito"),
					pwd);
			System.out
					.println("Ajout d'un pari podicum avec une compétition inconnue n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String(" "), pwd);
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String("dsfqç£"),
					pwd);
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().betOnPodium(10, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String("lolito"),
					new String("qsdfwdsf"));
			System.out
					.println("Ajout d'un pari podium un username/mdp incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamOK() {
		// Tests ok
		// before betting credit the subscriber. Suppose creditSubscriber
		// tested
		// Add a subscriber
		try {
			increment.getBetting().creditSubscriber(new String("lolito"), 1500,
					new String(increment.getManagerPassword()));

		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().betOnPodium(350, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String("lolito"),
					pwd);
		} catch (Exception e) {
			System.out
					.println("Ajout d'un pari podium correct (otra_compet [Madrid,Barca,Villareal]) a levé une exception ");
		}

		try {
			increment.getBetting().betOnPodium(1150, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String("lolito"),
					pwd);
		} catch (Exception e) {
			System.out
					.println("Ajout d'un pari podium correct a levé une exception ");
		}
	}

	private void testTeamNotEnoughTokens() {
		try {
			increment.getBetting().betOnPodium(1, new String("otra_compet"),
					winnerTeam, secondTeam, thirdTeam, new String("lolito"),
					pwd);

			System.out
					.println("Ajout d'un pari podium avec trop de jetons (1) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

	}

	private void setUpTeamMembers() {

		try {
			// On fixe la date au 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose subscribe and addCompetition ok
			// Suppose createCompetitor ok

			cName = new String("otra_compet");
			winnerTeam = increment.getBetting().createCompetitor(
					new String("Madrid"), increment.getManagerPassword());
			winnerTeam.addMember(increment.getBetting().createCompetitor(
					new String("Torres"),
					new String("Sergio"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1984, 12, 13).getTime()),
					increment.getManagerPassword()));

			winnerTeam.addMember(increment.getBetting().createCompetitor(
					new String("Lopez"),
					new String("Eduardo"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1983, 12, 13).getTime()),
					increment.getManagerPassword()));

			winnerTeam.addMember(increment.getBetting().createCompetitor(
					new String("Bosque"),
					new String("Verde"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1982, 4, 13).getTime()),
					increment.getManagerPassword()));

			secondTeam = increment.getBetting().createCompetitor(
					new String("Barca"), increment.getManagerPassword());
			secondTeam.addMember(increment.getBetting().createCompetitor(
					new String("Calle"),
					new String("Vicente"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1982, 5, 13).getTime()),
					increment.getManagerPassword()));
			secondTeam.addMember(increment.getBetting().createCompetitor(
					new String("Martinez"),
					new String("Rojo"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1987, 5, 13).getTime()),
					increment.getManagerPassword()));

			thirdTeam = increment.getBetting().createCompetitor(
					new String("Villareal"), increment.getManagerPassword());

			// Add a subscriber
			pwd = increment.getBetting().subscribe(new String("Dida"),
					new String("John"), new String("lolito"), new String("11-03-1987"),
					new String(increment.getManagerPassword()));

			// Add a competition
			competitorTeams = new ArrayList<Competitor>();
			competitorTeams.add(winnerTeam);
			competitorTeams.add(secondTeam);
			competitorTeams.add(thirdTeam);

			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 3, 1), competitorTeams,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testTeamMembersWithNullParameters() {
		this.testTeamWithNullParameters();
	}

	private void testTeamMembersWithInvalidParameters() {
		this.testTeamWithInvalidParameters();
	}

	private void testTeamMembersOK() {
		this.testTeamOK();
	}

	private void testTeamMembersNotEnoughTokens() {
		this.testTeamNotEnoughTokens();
	}

	private void testTeamMembersPlayerIsACompetitor() {
		String pwdTiti = null;
		try {
			pwdTiti = increment.getBetting().subscribe(new String("Torres"),
					new String("Sergio"), new String("titi"), new String("13-12-1984"),
					new String(increment.getManagerPassword()));

			// Credit tokens
			increment.getBetting().creditSubscriber("titi", 100,
					new String(increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().betOnPodium(9, "a_compet", winnerTeam,
					secondTeam, thirdTeam, new String("titi"), pwdTiti);
			System.out
					.println("l'ajout d'un pari podium par un membre d'une équipe de la compétition n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		// On remet la BD comme avant
		try {
			increment.getBetting().unsubscribe(new String("titi"),
					increment.getManagerPassword());
		} catch (AuthentificationException | ExistingSubscriberException e) {
			assert (false);
		}

	}

	public String getPwdBis() {
		return pwdBis;
	}

	public void setPwdBis(String pwdBis) {
		this.pwdBis = pwdBis;
	}

}
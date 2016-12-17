package fr.uv1.tests.validation.withoutPersistence;

import java.text.SimpleDateFormat;
import java.util.*;

import Interface.*;
import utils.MyCalendar;

/**
 * 
 * @author segarra
 * 
 */
public class AddCompetitionValidationTests {
	private FirstIncrementValidationTests increment;
	private boolean addCompetition = false;
	private boolean addTeamCompetition = false;

	private ArrayList<Competitor> competitors;
	private ArrayList<Competitor> competitorTeams;
	private Competitor pc1, pc2, pc3, madrid, barca;

	private Scanner sc = new Scanner(System.in);
	public String c = "x";
	
	public static void main(String[] str) {
		AddCompetitionValidationTests test = new AddCompetitionValidationTests();
	}

	public String getResponse() {
		String s = "x";
		while (!s.equals("y") && !s.equals("n")) {
			s = sc.next();
		}
		return s;
	}

	public AddCompetitionValidationTests() {
		increment = FirstIncrementValidationTests.getIncrement();

		System.out.print("  ----- Compétition sans équipes (y/n) ?\n");
		c = getResponse();
		if (c.equals("y")) {
			this.addCompetition = true;

			this.setUp();

			this.testNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testNotEnoughCompetitors();
			System.out.println("  >>>>> Fin tests pas assez de compétiteurs\n");

			this.testInThePast();
			System.out.println("  >>>>> Fin tests compétition dans le passé\n");

			this.testOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

			this.testExisting();
			System.out.println("  >>>>> Fin tests compétition existante\n");
		}

		// Soit les équipes ont juste un nom
		// Soit les équipes ont des membres
		System.out
				.print("  ----- Compétition avec équipes sans membres ? (y/n)\n");
		c = getResponse();
		if (c.equals("y")) {
			this.addTeamCompetition = true;
			this.setUpTeam();

			this.testTeamNullParameters();
			System.out.println("  >>>>> Fin tests paramètre non instancié\n");

			this.testTeamInvalidParameters();
			System.out.println("  >>>>> Fin tests paramètre invalide\n");

			this.testTeamNotEnoughCompetitors();
			System.out.println("  >>>>> Fin tests pas assez de compétiteurs\n");

			this.testTeamInThePast();
			System.out.println("  >>>>> Fin tests compétition dans le passé\n");

			this.testTeamOK();
			System.out.println("  >>>>> Fin tests paramètres valides\n");

			this.testTeamExisting();
			System.out.println("  >>>>> Fin tests compétition existante\n");
		} else {
			System.out
					.print("  ------- Compétition avec équipes ayant des membres ? (y/n)\n");
			c = this.getResponse();
			if (c.equals("y")) {
				this.addTeamCompetition = true;

				this.setUpTeamWithCompetitors();

				this.testTeamWithCompetitorsNullParameters();
				System.out
						.println("  >>>>> Fin tests paramètre non instancié\n");

				this.testTeamWithCompetitorsInvalidParameters();
				System.out.println("  >>>>> Fin tests paramètre invalide\n");

				this.testTeamWithCompetitorsNotEnoughCompetitors();
				System.out
						.println("  >>>>> Fin tests pas assez de compétiteurs\n");

				this.testTeamWithCompetitorsInThePast();
				System.out
						.println("  >>>>> Fin tests compétition dans le passé\n");

				this.testTeamWithCompetitorsOK();
				System.out.println("  >>>>> Fin tests paramètres valides\n");

				this.testTeamWithCompetitorsExisting();
				System.out.println("  >>>>> Fin tests compétition existante\n");
			}
		}

		System.out.print("  ------- Lister les compétitions ? (y/n)\n");
		c = this.getResponse();
		if (c.equals("y")) {
			new ListCompetitionsValidationTests(addCompetition,
					addTeamCompetition);
		}
	}

	private void setUp() {
		try {
			// On fixe la date au 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			competitors = new ArrayList<Competitor>();
			competitors.add(increment.getBetting().createCompetitor(
					new String("Durant"),
					new String("Miguel"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1984, 7, 20).getTime()),
					increment.getManagerPassword()));
			competitors.add(increment.getBetting().createCompetitor(
					new String("Duranto"),
					new String("Miguel"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1983, 12, 13).getTime()),
					increment.getManagerPassword()));
		} catch (Exception e) {
			System.out.println("Exception imprévue " + e.getClass());
			e.printStackTrace();
		}
	}

	private void testNullParameters() {
		// Tests entries : null
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					competitors, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("une_compet"),
					null, competitors, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une date de clôture non instanciée n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("une_compet"),
					MyCalendar.getDate(), null, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une liste de compétiteurs non instanciée n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("une_compet"),
					MyCalendar.getDate(), competitors, null);
			System.out
					.println("Ajout d'une compétition avec un mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					competitors, null);
			System.out
					.println("Ajout d'une compétition avec un nom et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					null, null);
			System.out
					.println("Ajout d'une competition avec un nom, compétiteurs et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testInvalidParameters() {
		// Tests entries : invalid format
		try {
			increment.getBetting().addCompetition(new String(" "),
					MyCalendar.getDate(), competitors,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("a compet"),
					MyCalendar.getDate(), competitors,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom invalide (a compet) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2014, 2, 1), competitors, new String(" "));
			System.out
					.println("l'ajout d'une compétition avec un mdp gestionnaire invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testNotEnoughCompetitors() {

		// Less than two competitors
		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2014, 2, 1), new ArrayList<Competitor>(),
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec moins de deux compétiteurs (0) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		// Less than two competitors
		ArrayList<Competitor> compts = new ArrayList<Competitor>();
		try {

			compts.add(increment.getBetting().createCompetitor(
					new String("Dupont"),
					new String("Jose"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1981, 1, 6).getTime()),
					increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}
		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2014, 2, 1), compts,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec moins de deux compétiteurs (1) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			// The same two competitors
			compts.add(increment.getBetting().createCompetitor(
					new String("Dupont"),
					new String("Jose"),
					new SimpleDateFormat("dd-MM-yyyy").format(new MyCalendar(
							1981, 1, 6).getTime()),
					increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2014, 2, 1), compts,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec deux compétiteurs identiques n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testInThePast() {
		// Date de clotûre dans le passé
		try {
			// Par rapport à la "date simulée"
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2009, 2, 1), competitors,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une date dans le passé n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testOK() {
		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2020, 2, 1), competitors,
					increment.getManagerPassword());
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 1) {
				System.out
						.println("le nombre de compétitions est incorrect (1 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		} catch (Exception e) {
			System.out
					.println("Ajout d'une compétition valide (a_compet,01-02-2020) a levé l'exception "
							+ e.getClass());
			e.printStackTrace();
		}
		try {
			increment.getBetting().addCompetition(new String("another-compet"),
					new MyCalendar(2020, 2, 1), competitors,
					increment.getManagerPassword());
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 2) {
				System.out
						.println("le nombre de compétitions est incorrect (2 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		} catch (Exception e) {
			System.out
					.println("Ajout d'une compétition valide (another-compet,01-02-2020) a levé l'exception "
							+ e.getClass());
			e.printStackTrace();
		}
	}

	private void testExisting() {

		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2014, 2, 1), competitors,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition déjà existante (a_compet,01-02-2014) n'a pas levé d'exception");
		} catch (Exception e) {
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 2) {
				System.out
						.println("le nombre de compétitions est incorrect (2 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		}

		try {
			increment.getBetting().addCompetition(new String("a_compet"),
					new MyCalendar(2020, 2, 1), competitors,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition déjà existante (a_compet,01-02-2020) n'a pas levé d'exception");
		} catch (Exception e) {
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 2) {
				System.out
						.println("le nombre de compétitions est incorrect (2 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		}
	}

	private void setUpTeam() {

		try {
			// Set current date to 01/08/2011
			MyCalendar.setDate(2011, 8, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());

			// Suppose createCompetitor correct
			competitorTeams = new ArrayList<Competitor>();
			competitorTeams.add(increment.getBetting().createCompetitor(
					new String("Madrid"), increment.getManagerPassword()));
			competitorTeams.add(increment.getBetting().createCompetitor(
					new String("Barca"), increment.getManagerPassword()));
		} catch (Exception e) {
			System.out.println("Exception imprévue " + e.getClass());
			e.printStackTrace();
		}
	}

	private void testTeamNullParameters() {
		// Tests entries : null
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					competitorTeams, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					null, competitorTeams, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une date de clôture non instanciée n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					MyCalendar.getDate(), null, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une liste de competiteurs non instanciée n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					MyCalendar.getDate(), competitorTeams, null);
			System.out
					.println("Ajout d'une compétition avec un mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					competitorTeams, null);
			System.out
					.println("Ajout d'une compétition avec un nom et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(null, MyCalendar.getDate(),
					null, null);
			System.out
					.println("Ajout d'une competition avec un nom, compétiteurs et mdp gestionnaire non instanciés n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamInvalidParameters() {
		// Tests entries : invalid format
		try {
			increment.getBetting().addCompetition(new String(" "),
					MyCalendar.getDate(), competitorTeams,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("otra compet"),
					MyCalendar.getDate(), competitorTeams,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec un nom invalide (a compet) n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2014, 2, 1), competitorTeams,
					new String(" "));
			System.out
					.println("l'ajout d'une compétition avec un mdp gestionnaire incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamNotEnoughCompetitors() {
		// Less than two competitors
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2016, 5, 23), new ArrayList<Competitor>(),
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec moins de deux compétiteurs (0) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		// Less than two competitors
		ArrayList<Competitor> compts = new ArrayList<Competitor>();
		try {

			compts.add(increment.getBetting().createCompetitor(
					new String("Betis"), increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2016, 5, 23), compts,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec moins de deux compétiteurs (1) n'a pas levé d'exception ");
		} catch (Exception e) {
		}

		try {
			// The same two competitors
			compts.add(increment.getBetting().createCompetitor(
					new String("Betis"), increment.getManagerPassword()));
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2016, 5, 23), compts,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec deux compétiteurs identiques n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testTeamInThePast() {
		// Closing date in the past: competition closed
		try {
			// Related to the "simulated date"
			increment.getBetting().addCompetition(new String("otra_compet"),
					new GregorianCalendar(2009, Calendar.FEBRUARY, 01),
					competitorTeams, increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition avec une date dans le passé n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		if (this.addCompetition) {
		} else {
		}
	}

	private void testTeamOK() {
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 8, 15), competitorTeams,
					increment.getManagerPassword());
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 1) {
				System.out
						.println("le nombre de compétitions est incorrect (1 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		} catch (Exception e) {
			System.out
					.println("Ajout d'une compétition valide (otra_compet,15-08-2018) a levé l'exception "
							+ e.getClass());
		}
	}

	private void testTeamExisting() {
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 8, 15), competitorTeams,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition déjà existante (otra_compet,15-08-2018) n'a pas levé d'exception");
		} catch (Exception e) {
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 1) {
				System.out
						.println("le nombre de compétitions est incorrect (1 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		}

		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2016, 2, 16), competitorTeams,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'une compétition déjà existante (otra_compet) n'a pas levé d'exception");
		} catch (Exception e) {
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 1) {
				System.out
						.println("le nombre de compétitions est incorrect (1 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		}
	}

	private void setUpTeamWithCompetitors() {

		try {
			pc1 = increment.getBetting().createCompetitor(new String("Durant"),
					new String("Miguel"), new String("20-07-1984"),
					increment.getManagerPassword());
			pc2 = increment.getBetting().createCompetitor(
					new String("Duranto"), new String("Miguel"),
					new String("13-12-1983"), increment.getManagerPassword());

			madrid = increment.getBetting().createCompetitor(
					new String("Madrid"), increment.getManagerPassword());
			madrid.addMember(pc1);
			madrid.addMember(pc2);

			pc3 = increment.getBetting().createCompetitor(
					new String("Durante"), new String("Miguel"),
					new String("20-07-1980"), increment.getManagerPassword());

			barca = increment.getBetting().createCompetitor(
					new String("Barca"), increment.getManagerPassword());
			barca.addMember(pc3);

			competitorTeams = new ArrayList<Competitor>();
			competitorTeams.add(madrid);
			competitorTeams.add(barca);
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testTeamWithCompetitorsNullParameters() {
		this.testTeamNullParameters();
	}

	private void testTeamWithCompetitorsInvalidParameters() {
		this.testTeamInvalidParameters();
	}

	private void testTeamWithCompetitorsNotEnoughCompetitors() {
		this.testTeamNotEnoughCompetitors();
	}

	private void testTeamWithCompetitorsInThePast() {
		this.testTeamInThePast();
	}

	private void testTeamWithCompetitorsOK() {
		try {
			increment.getBetting().addCompetition(new String("otra_compet"),
					new MyCalendar(2018, 8, 15), competitorTeams,
					increment.getManagerPassword());
			// Suppose listCompetitions correct
			if (increment.getBetting().listCompetitions().size() != 1) {
				System.out
						.println("le nombre de compétitions est incorrect (1 != "
								+ increment.getBetting().listCompetitions()
										.size() + ")");
			}
		} catch (Exception e) {
			System.out
					.println("Ajout d'une compétition valide (otra_compet,15-08-2018) a levé l'exception "
							+ e.getClass());
		}
	}

	private void testTeamWithCompetitorsExisting() {
		this.testTeamExisting();
	}
}
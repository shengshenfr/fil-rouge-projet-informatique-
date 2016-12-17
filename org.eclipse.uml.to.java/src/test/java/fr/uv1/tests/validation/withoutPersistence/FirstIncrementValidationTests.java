package fr.uv1.tests.validation.withoutPersistence;

public abstract class FirstIncrementValidationTests extends ValidationTest {

	private static FirstIncrementValidationTests increment;

	public FirstIncrementValidationTests() {
		super();
		FirstIncrementValidationTests.increment = this;
		launchTests();
	}

	private void launchTests() {

		// Validation de la création de joueurs
		System.out.println("\n ** ------------------------------- **");
		System.out.println(" ** Tests pour inscrire des joueurs **");
		System.out.println(" ** ------------------------------- **\n");
		new SubscribeValidationTests();

		// Validation de la désinscription de joueurs
		System.out.println("\n ** ---------------------------------- **");
		System.out.println(" ** Tests pour désinscrire des joueurs **");
		System.out.println(" ** ---------------------------------- **\n");
		// Création nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new UnSubscribeValidationTests();

		// Validation de lister joueurs
		System.out.println("\n ** ----------------------------- **");
		System.out.println(" ** Tests pour lister les joueurs **");
		System.out.println(" ** ----------------------------- **\n");
		// Création nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new ListPlayersValidationTests();

		// Validation de "ajouter compétition"
		System.out.println("\n ** ---------------------------------- **");
		System.out.println(" ** Tests pour ajouter une compétition **");
		System.out.println(" ** ---------------------------------- **\n");
		// Création nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new AddCompetitionValidationTests();

		// Validation de "créditer joueur"
		System.out.println("\n ** ----------------------------- **");
		System.out.println(" ** Tests pour créditer un joueur **");
		System.out.println(" ** ----------------------------- **\n");
		// Création nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new CreditValidationTests();
		
		// Validation de "débiter joueur"
		System.out.println("\n ** ---------------------------- **");
		System.out.println(" ** Tests pour débiter un joueur **");
		System.out.println(" ** ---------------------------- **\n");
		// Création nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new DebitValidationTests();
		
		// Validation de "annuler compétition"
//		 System.out.println("\n * Tests pour annuler une compétition\n");
		// Création nouvelle instance de BettingSoft
//		 this.setBetting(this.plugToBetting());
//		 new CancelCompetitionValidationTests();

		// Validation de "ajouter compétiteur"
//		 System.out.println("\n * Tests pour ajouter compétiteur\n");
		// Création nouvelle instance de BettingSoft
//		 this.setBetting(this.plugToBetting());
//		 new AddCompetitorValidationTests();

		// Validation de "supprimer compétiteur"
		 //System.out.println("\n * Tests pour supprimer compétiteur\n");
		// Création nouvelle instance de BettingSoft
		 //this.setBetting(this.plugToBetting());
		 //new DeleteCompetitorValidationTests();

		// Validation de la liste des compétiteurs d'une compétition
		// System.out.println("\n * Tests pour lister les compétiteurs d'une compétition\n");
		// Création nouvelle instance de BettingSoft
		 //this.setBetting(this.plugToBetting());
		 //System.out.println("\n * Tests pour supprimer compétiteur\n");
		 //new ListCompetitorsValidationTests();
	}

	public static FirstIncrementValidationTests getIncrement() {
		return increment;
	}
}

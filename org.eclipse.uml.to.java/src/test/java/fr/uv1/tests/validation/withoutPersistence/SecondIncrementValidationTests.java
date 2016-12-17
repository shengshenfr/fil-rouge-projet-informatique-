package fr.uv1.tests.validation.withoutPersistence;

public abstract class SecondIncrementValidationTests extends ValidationTest {
	private static SecondIncrementValidationTests increment;

	public SecondIncrementValidationTests() {
		super();
		SecondIncrementValidationTests.increment = this;
		launchTests();
	}

	private void launchTests() {

		// Validation d'un pari podium
		System.out.println("\n ** ------------------------ **");
		System.out.println(" ** Tests pour parier podium **");
		System.out.println(" ** ------------------------ **\n");
		// Cr�ation nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new BetOnPodiumValidationTests();

		// Validation de solder comp�tition (il n'y a que des podiums)
		System.out.println("\n ** ----------------------------- **");
		System.out.println(" ** Tests pour solder comp�tition **");
		System.out.println(" ** ----------------------------- **\n");
		// Cr�ation nouvelle instance de BettingSoft
		this.setBetting(this.plugToBetting());
		new SettlePodiumValidationTests();
	}

	public static SecondIncrementValidationTests getIncrement() {
		return increment;
	}
}

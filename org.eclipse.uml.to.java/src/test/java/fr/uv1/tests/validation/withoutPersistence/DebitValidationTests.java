package fr.uv1.tests.validation.withoutPersistence;

import java.util.ArrayList;

public class DebitValidationTests {
	private FirstIncrementValidationTests increment;
	private String fanfanPwd, fanfinPwd;
	
	public DebitValidationTests() {
		increment = FirstIncrementValidationTests.getIncrement();

		this.setUp();
		this.testDebitNullParameters();
		System.out.println("  >>>>> Fin tests paramètre non instancié\n");

		this.testDebitInvalidParameters();
		System.out.println("  >>>>> Fin tests paramètre invalide\n");

		this.testDebitUsernameNotExist();
		System.out.println("  >>>>> Fin tests joueur non existant\n");

		this.testDebitNotEnoughTokens();
		System.out.println("  >>>>> Fin tests joueur pas assez jetons\n");

		this.testDebitOK();
		System.out.println("  >>>>> Fin tests paramètres valides\n");
	}

	private void setUp() {
		try {
			fanfanPwd = increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"), new String("11-03-1987"),
					 increment.getManagerPassword());

			fanfinPwd = increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfin"), new String("15-04-1990"),
					 increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Duran"),
					new String("Morgan"), new String("fanfon"), new String("21-06-1980"),
					 increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"), new String("18-10-1978"),
					 increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), 100,
					increment.getManagerPassword());
			increment.getBetting().creditSubscriber(new String("fanfan"), 300,
					increment.getManagerPassword());
			increment.getBetting().creditSubscriber(new String("fanfin"), 300,
					increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testDebitNullParameters() {
		// Tests parameters : null
		try {
			increment.getBetting().debitSubscriber(null, 100, null);
			System.out
					.println("debit avec pseudo et mdp gestionnaire non instanciés et nombre de jetons valide n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), 100,
					null);
			System.out
					.println("debit avec pseudo et nombre de jetons valides et mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(null, 100,
					increment.getManagerPassword());
			System.out
					.println("debit avec pseudo non instancié, nombre de jetons valide et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testDebitInvalidParameters() {
		try {
			increment.getBetting().debitSubscriber(new String(" "), 100,
					increment.getManagerPassword());
			System.out
					.println("debit avec pseudo invalide (\" \"), nombre de jetons valide et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(new String("%£%µ%£%µ"), 100,
					increment.getManagerPassword());
			System.out
					.println("debit avec pseudo invalide, nombre de jetons valide et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), 100,
					new String(" "));
			System.out
					.println(" debit avec pseudo connu, nombre de jetons valide et mdp gestionnaire invalide (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), 100,
					new String("kjkj"));
			System.out
					.println("debit avec pseudo connu, nombre de jetons valide et mdp gestionnaire incorrect n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), -100,
					increment.getManagerPassword());
			System.out
					.println("debit avec pseudo connu, nombre jetons invalide (-100) et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testDebitUsernameNotExist() {
		// Tests parameters: username does not exist
		try {
			increment.getBetting().debitSubscriber(new String("locainaColina"),
					100, increment.getManagerPassword());
			System.out
					.println("debit avec pseudo inconnu, nombre de jetons valide et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testDebitNotEnoughTokens() {
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), 450,
					increment.getManagerPassword());
			System.out
					.println("debit avec pseudo connu (fanfan), nombre de jetons insufisant (450) et mdp gestionnaire correct n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testDebitOK() {
		ArrayList<String> infos;
		
		// Tests ok
		try {
			increment.getBetting().debitSubscriber(new String("fanfan"), 100,
					increment.getManagerPassword());
			// Verify specification
			infos = increment.getBetting().infosSubscriber(
					new String("fanfan"), fanfanPwd);
			if (Integer.parseInt(infos.get(4)) != 300)
				System.out
						.println("le nombre de jetons crédités est incorrect (300 != "
								+ infos.get(4) + ")");
		} catch (Exception e) {
			System.out
					.println("debit avec upseudo connu, nombre de jetons valide et mdp gestionnaire correct a levé l'exception "
							+ e.getClass());
		}
		try {
			increment.getBetting().debitSubscriber(new String("fanfin"), 50,
					increment.getManagerPassword());
			// Verify specification
			infos = increment.getBetting().infosSubscriber(
					new String("fanfin"), fanfinPwd);
			if (Integer.parseInt(infos.get(4)) != 250)
				System.out
						.println("le nombre de jetons crédités est incorrect (250 != "
								+ infos.get(4) + ")");
		} catch (Exception e) {

			System.out
					.println("debit avec pseudo connu, nombre de jetons valide et mdp gestionnaire correct a levé l'exception "
							+ e.getClass());
		}
	}
}
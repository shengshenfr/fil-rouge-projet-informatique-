package fr.uv1.tests.validation.withoutPersistence;

import java.util.ArrayList;

public class CreditValidationTests {
	private FirstIncrementValidationTests increment;
	private String fanfanPwd, fanfinPwd;

	public CreditValidationTests() {
		increment = FirstIncrementValidationTests.getIncrement();

		this.setUp();

		this.testCreditNullParameters();
		System.out.println("  >>>>> Fin tests param�tre non instanci�\n");

		this.testCreditInvalidParameters();
		System.out.println("  >>>>> Fin tests param�tre invalide\n");

		this.testCreditUsernameNotExist();
		System.out.println("  >>>>> Fin tests joueur non existant\n");

		this.testCreditOK();
		System.out.println("  >>>>> Fin tests param�tres valides\n");
	}

	private void setUp() {

		try {
			fanfanPwd = increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			fanfinPwd = increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfin"),
					new String("15-04-1990"), increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Duran"),
					new String("Morgan"), new String("fanfon"),
					new String("21-06-1980"), increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"),
					new String("18-10-1978"), increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}
	}

	private void testCreditNullParameters() {

		// Tests parameters : null
		try {
			increment.getBetting().creditSubscriber(null, 100, null);
			System.out
					.println("cr�diter avec pseudo et mdp gestionnaire non instanci�s et nombre de jetons valide n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), 100,
					null);
			System.out
					.println("cr�diter avec pseudo et nombre de jetons valides et mdp gestionnaire non instanci� n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(null, 100,
					increment.getManagerPassword());
			System.out
					.println("cr�diter avec pseudo non instanci�, nombre de jetons valide et mdp gestionnaire correct n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
	}

	private void testCreditInvalidParameters() {
		try {
			increment.getBetting().creditSubscriber(new String(" "), 100,
					increment.getManagerPassword());
			System.out
					.println("cr�diter avec pseudo invalide (\" \"), nombre de jetons valide et mdp gestionnaire correct n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(new String("%�%�%�%�"),
					100, increment.getManagerPassword());
			System.out
					.println("cr�diter avec pseudo invalide, nombre de jetons valide et mdp gestionnaire correct n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), 100,
					new String(" "));
			System.out
					.println("cr�diter avec pseudo connu, nombre de jetons valide et mdp gestionnaire invalide (\" \")  n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), 100,
					new String("kjkj"));
			System.out
					.println("cr�diter avec pseudo connu, nombre de jetons valide et mdp gestionnaire incorrect n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), -100,
					increment.getManagerPassword());
			System.out
					.println("cr�diter avec pseudo connu, nombre jetons invalide (-100) et mdp gestionnaire correct n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
	}

	private void testCreditUsernameNotExist() {
		// Tests parameters: username does not exist
		try {
			increment.getBetting().creditSubscriber(
					new String("locainaColina"), 100,
					increment.getManagerPassword());
			System.out
					.println("cr�diter avec pseudo inconnu, nombre de jetons valide et mdp gestionnaire correct n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
	}

	private void testCreditOK() {
		ArrayList<String> infos;
		// Tests ok
		try {
			increment.getBetting().creditSubscriber(new String("fanfan"), 100,
					increment.getManagerPassword());
			// Verify specification
			infos = increment.getBetting().infosSubscriber(
					new String("fanfan"), fanfanPwd);
			if (Integer.parseInt(infos.get(4)) != 100)
				System.out
						.println("le nombre de jetons cr�dit�s est incorrect (100 != "
								+ infos.get(4) + ")");

		} catch (Exception e) {
			System.out
					.println("cr�diter avec pseudo connu, nombre de jetons valide et mdp gestionnaire correct a lev� l'exception "
							+ e.getClass());
		}
		try {
			increment.getBetting().creditSubscriber(new String("fanfin"), 50,
					increment.getManagerPassword());
			// Verify specification
			infos = increment.getBetting().infosSubscriber(
					new String("fanfin"), fanfinPwd);
			if (Integer.parseInt(infos.get(4)) != 50)
				System.out
						.println("le nombre de jetons cr�dit�s est incorrect (50 != "
								+ infos.get(4) + ")");
		} catch (Exception e) {
			System.out
					.println("cr�diter avec pseudo connu, nombre de jetons valide et mdp gestionnaire correct a lev� l'exception "
							+ e.getClass());
		}
	}
}
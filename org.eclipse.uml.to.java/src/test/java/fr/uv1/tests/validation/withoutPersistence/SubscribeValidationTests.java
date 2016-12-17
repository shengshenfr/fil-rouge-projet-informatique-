package fr.uv1.tests.validation.withoutPersistence;

import utils.MyCalendar;

/**
 * 
 * @author prou, segarra
 * 
 */
public class SubscribeValidationTests {

	private FirstIncrementValidationTests increment;

	public SubscribeValidationTests() {

		increment = FirstIncrementValidationTests.getIncrement();

		testSubscribeNullParameters();
		System.out.println("  >>>>> Fin tests param�tre non instanci�\n");

		testSubscribeInvalidParameters();
		System.out.println("  >>>>> Fin tests param�tre invalide\n");

		testSubscribeNotMajor();
		System.out.println("  >>>>> Fin tests joueur non majeur\n");

		testSubscribeOK();
		System.out.println("  >>>>> Fin tests param�tres valides\n");

		testSubscribeSameSubscriber();
		System.out.println("  >>>>> Fin tests joueur existant\n");
	}

	private void testSubscribeNullParameters() {
		// Tests entries : null
		try {
			increment.getBetting().subscribe(null, new String("Albert"),
					new String("worldChamp"), new String("11-03-1987"),
					increment.getManagerPassword());

			System.out
					.println("Ajout d'un joueur avec un nom non instanci�, pr�nom, date de naissance et pseudo valides et mdp gestionnaire correct n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
		try {
			increment.getBetting().subscribe(new String("Duran"), null,
					new String("worldChamp"), new String("11-03-1987"),
					increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec un pr�nom non instanci�, nom, date de naissance et pseudo valides et mdp gestionnaire correct n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("worldChamp"), null,
					increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec une date de naissance non instanci�e, nom, pr�nom et pseudo valides et mdp gestionnaire correct n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), null, new String("11-03-1987"),
					increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec un pseudo non instanci�, nom, date de naissance et pr�nom valides et mdp gestionnaire correct n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("worldChamp"),
					new String("11-03-1987"), null);
			System.out
					.println("Ajout d'un joueur avec nom, pr�nom, date de naissnance et pseudo valides et un mdp gestionnaire non instanci� n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
	}

	private void testSubscribeInvalidParameters() {
		// Tests entries : invalid format
		try {
			increment.getBetting().subscribe(new String(" "),
					new String("Albert"), new String("worldChamp"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec un nom invalide ( ) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String(" "), new String("worldChamp"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec un pr�nom invalide ( ) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("67user"), new String("worldChamp"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec un pr�nom invalide ( ) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("worldChamp"),
					new String(" "), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur avec une date de naissance invalide ( ) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("tnt"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide (tnt) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Nobel"),
					new String("Alfred"), new String("tnt"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("l'ajout d'un joueur avec un pseudo invalide (tnt hyu78) n'a pas lev� d'exception ");

		} catch (Exception e) {
		}

		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Roberto"), new String("worldChamp"),
					new String("11-03-1987"), new String("abef"));
			System.out
					.println("Ajout d'un joueur avec un mdp gestionnaire incorrect n'a pas lev� d'exception ");

		} catch (Exception e) {
		}
	}

	private void testSubscribeNotMajor() {
		try {
			// Fixer la date simul�e
			MyCalendar.setDate(1997, 2, 1);
			System.out.println("Nous sommes au " + MyCalendar.getDate());
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur pas majeur n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
	}

	private void testSubscribeOK() {
		// Tests with valid parameters
		try {
			// Fixer la date simul�
			MyCalendar.setDate(2011, 6, 26);
			System.out.println("Nous sommes au " + MyCalendar.getDate());
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

		} catch (Exception e) {
			System.out
					.println("Ajout d'un nouveau joueur [Duran,Albert,11-03-1987,fanfan] a lev� l'exception "
							+ e.getClass());
		}

		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfin"),
					new String("11-03-1987"), increment.getManagerPassword());
		} catch (Exception e) {
			System.out
					.println("Ajout d'un nouveau joueur [Duran,Albert,11-03-1987,fanfin] a lev� l'exception "
							+ e.getClass());
		}

		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Morgan"), new String("fanfon"),
					new String("11-03-1987"), increment.getManagerPassword());

		} catch (Exception e) {
			System.out
					.println("Ajout d'un nouveau joueur [Duran,Morgan,11-03-1987,fanfon] a lev� l'exception "
							+ e.getClass());
		}
		try {
			increment.getBetting().subscribe(new String("Mato"),
					new String("Anna"), new String("salto"),
					new String("11-03-1987"), increment.getManagerPassword());

		} catch (Exception e) {

			System.out
					.println("Ajout d'un nouveau joueur [Mato,Anna,11-03-1987,salto] a lev� une exception "
							+ e.getClass());
		}
	}

	private void testSubscribeSameSubscriber() {

		// The same subscriber
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			System.out
					.println("Ajout d'un joueur d�j� inscrit [Duran,Albert,11-03-1987,fanfan] n'a pas lev� d'exception ");
		} catch (Exception e) {

		}
		// same firstname, username ; different lastname
		try {
			increment.getBetting().subscribe(new String("Durano"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			System.out
					.println("Ajout d'un joueur d�j� inscrit [Durano,Albert,11-03-1987,fanfan] n'a pas lev� d'exception ");
		} catch (Exception e) {

		}

		// same lastname, username; different firstname
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Alfred"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			System.out
					.println("Ajout d'un joueur existant [Duran,Alfred,11-03-1987,fanfan] n'a pas lev� d'exception ");
		} catch (Exception e) {

		}

		// same firstname et username; different lastname
		try {
			increment.getBetting().subscribe(new String("Durano"),
					new String("Albert"), new String("fanfin"),
					new String("11-03-1987"), increment.getManagerPassword());
			System.out
					.println("Ajout d'un joueur existant [Durano,Albert,11-03-1987,fanfin] n'a pas lev� d'exception ");
		} catch (Exception e) {
		}
	}
}

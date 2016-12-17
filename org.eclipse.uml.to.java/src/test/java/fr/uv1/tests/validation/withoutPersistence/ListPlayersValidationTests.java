package fr.uv1.tests.validation.withoutPersistence;

import java.util.List;

public class ListPlayersValidationTests {
	private FirstIncrementValidationTests increment;

	public ListPlayersValidationTests() {
		increment = FirstIncrementValidationTests.getIncrement();

		this.setUp();

		this.testListNullParameters();
		System.out.println("  >>>>> Fin tests paramètre non instancié\n");

		this.testListInvalidParameters();
		System.out.println("  >>>>> Fin tests paramètre invalide\n");

		this.testListOK();
		System.out.println("  >>>>> Fin tests paramètres valides\n");
	}

	private void setUp() {
		try {
			increment.getBetting().subscribe(new String("Duran"),
					new String("Albert"), new String("fanfan"),
					new String("11-03-1987"), increment.getManagerPassword());

			increment.getBetting().subscribe(new String("Duran"),
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

	private void testListNullParameters() {
		// Tests "entries" : null
		try {
			increment.getBetting().listSubscribers(null);
			System.out
					.println("la consultation des joueurs avec mdp gestionnaire non instancié n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testListInvalidParameters() {
		// Tests "entries" : invalid
		try {
			increment.getBetting().listSubscribers(" ");
			System.out
					.println(" la consultation des joueurs avec mdp gestionnaire incorrect (\" \") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().listSubscribers("oi");
			System.out
					.println(" la consultation des joueurs avec mdp gestionnaire incorrect (\"oi\") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
		try {
			increment.getBetting().listSubscribers("dsfqdfqfµ");
			System.out
					.println(" la consultation des joueurs avec mdp gestionnaire incorrect (\"dsfqdfqfµ\") n'a pas levé d'exception ");
		} catch (Exception e) {
		}
	}

	private void testListOK() {
		List<List<String>> liste;
		// Tests number
		// Subscribers : [[Duran, Albert, fanfan], [Duran, Albert, fanfin],
		// [Duran, Morgan, fanfon], [Mato, Anna, salto]]
		try {
			liste = increment.getBetting().listSubscribers(
					increment.getManagerPassword());
			if (liste.size() != 4) {

				System.out.println("le nombre de joueurs est incorrect (4 != "
						+ liste.size() + ")");
			}
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().subscribe(new String("Prou"),
					new String("Bernard"), new String("nanard"),
					new String("11-03-1957"), increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}

		try {
			liste = increment.getBetting().listSubscribers(
					increment.getManagerPassword());
			if (liste.size() != 5) {
				System.out.println("le nombre de joueurs est incorrect (5 != "
						+ liste.size() + ")");
			}
		} catch (Exception e) {
			assert (false);
		}

		try {
			increment.getBetting().unsubscribe(new String("nanard"),
					increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}

		try {
			liste = increment.getBetting().listSubscribers(
					increment.getManagerPassword());
			if (liste.size() != 4) {
				System.out.println("le nombre de joueurs est incorrect (4 != "
						+ liste.size() + ")");
			}
		} catch (Exception e) {
			assert (false);
		}
		
		try {
			increment.getBetting().unsubscribe(new String("fanfan"),
					increment.getManagerPassword());
			increment.getBetting().unsubscribe(new String("fanfin"),
					increment.getManagerPassword());
			increment.getBetting().unsubscribe(new String("fanfon"),
					increment.getManagerPassword());
			increment.getBetting().unsubscribe(new String("salto"),
					increment.getManagerPassword());
		} catch (Exception e) {
			assert (false);
		}

		try {
			liste = increment.getBetting().listSubscribers(
					increment.getManagerPassword());
			if (liste.size() != 0) {
				System.out.println("le nombre de joueurs est incorrect (0 != "
						+ liste.size() + ")");
			}
		} catch (Exception e) {
			assert (false);
		}
	}
}
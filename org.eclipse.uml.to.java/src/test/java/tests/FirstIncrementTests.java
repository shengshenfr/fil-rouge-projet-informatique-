package tests;

import Betting.BettingSoft;
import Interface.Betting;
import fr.uv1.tests.validation.withoutPersistence.FirstIncrementValidationTests;

public class FirstIncrementTests extends FirstIncrementValidationTests {
	
	public static void main(String[] args) {
		FirstIncrementTests ta = new FirstIncrementTests();
	}

	@Override
	public Betting plugToBetting() {
		return new BettingSoft();
	}

	@Override
	public String getManagerPassword() {
		return "root";
	}

}

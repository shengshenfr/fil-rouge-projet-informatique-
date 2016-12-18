package tests;

import Betting.BettingSoft;
import Interface.Betting;
import fr.uv1.tests.validation.withoutPersistence.SecondIncrementValidationTests;

public class SecondIncrementTests extends SecondIncrementValidationTests {
	
	public static void main(String[] args) {
		SecondIncrementTests ta = new SecondIncrementTests();
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

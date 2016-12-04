package Bettingtest;
import Betting.managerBetting;
import exceptions.BadParametersException;
import org.junit.Assert;
import org.junit.Test;

public class managerBettingtest {
	
	public void testManagerBetting() throws BadParametersException{
		managerBetting mgr = new managerBetting("xbowen","xbowen94");
		Assert.assertTrue(mgr.getManagerName().equals("xbowen"));
		Assert.assertTrue(mgr.getPassword().equals("xbowen94"));
	}
	
	@Test(expected = BadParametersException.class)
    public void testmanagerBettingInvalidManagerName() throws BadParametersException {
        managerBetting mgr = new managerBetting("xbowen1","xbowen94");
    }
	
	@Test(expected = BadParametersException.class)
    public void testmanagerBettingInvalidPassword() throws BadParametersException {
        managerBetting mgr = new managerBetting("xbowen","xbowen1994");
    }
	
	
    public void testmanagerBettingGetManagerName() throws BadParametersException {
        managerBetting mgr = new managerBetting("xbowen","xbowen94");
        Assert.assertTrue(mgr.getManagerName().equals("xbowen"));
    }
    
    public void testmanagerBettingGetPassword() throws BadParametersException {
        managerBetting mgr = new managerBetting("xbowen","xbowen94");
        Assert.assertTrue(mgr.getManagerName().equals("xbowen94"));
    }
    
    public static void main(String[] args) throws BadParametersException{
    	managerBettingtest test = new managerBettingtest();
    	test.testManagerBetting();
    	test.testmanagerBettingGetManagerName();
    	test.testmanagerBettingGetPassword();
    	test.testmanagerBettingInvalidManagerName();
    	test.testmanagerBettingInvalidPassword();
    }

	
}


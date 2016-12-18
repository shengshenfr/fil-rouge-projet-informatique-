package Betting;

import exceptions.BadParametersException;

public class managerBetting {
	@SuppressWarnings("unused")
	// length of username needs to be 6 
	
	private int LONG_USERNAME = 6;
	//rule for username
	
	private String REGEX_USERNAME = "^[0-9A-Za-z]{6}$";
	@SuppressWarnings("unused")
	//length of password needs to be 8
	
	private int LONG_PASSWORD = 8;
	// rule for password
	private String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
	private String username;
	private String password;
	
	public managerBetting(String username,String pwdMngr) throws BadParametersException{
		if(username.length()==6&&username.matches(REGEX_USERNAME)){
		this.username = username;
		System.out.println("Username is finish");
		}
		
		//length of username wrong
		if(username.length()!=6){
			throw new BadParametersException("LONG_USERNAME Wrong");
		}
		
		//rule of username wrong
		if(!username.matches(REGEX_USERNAME)){
			throw new BadParametersException("REGEX_USERNAME Wrong");
		}

	
		if(pwdMngr.length()==8&&pwdMngr.matches(REGEX_PASSWORD)){
		this.password = pwdMngr;
		System.out.println("Password is finish");
		}
		
		//length of password wrong
		if(pwdMngr.length()!=8){
			throw new BadParametersException("LONG_PASSWORD Wrong");
		}
		
		//rule of password wrong
		if(!pwdMngr.matches(REGEX_PASSWORD)){
			throw new BadParametersException("REGEX_PASSWORD Wrong");
		}
	}
	
	public String getManagerName(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
}


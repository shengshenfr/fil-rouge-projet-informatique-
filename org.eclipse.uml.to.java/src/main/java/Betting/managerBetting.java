package Individual;

public class managerBetting {
	private int LONG_USERNAME = 6;
	private String REGEX_USERNAME = "^[0-9A-Za-z]{6}$";
	private int LONG_PASSWORD = 8;
	private String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
	private String username;
	private String password;
	
	public managerBetting(String username,String pwdMngr){
		if(username.length()==6&&username.matches(REGEX_USERNAME)){
		this.username = username;
		System.out.println("Username is finish");
		}
		if(username.length()!=6){
			System.out.println("LONG_USERNAME Wrong");
		}
		if(!username.matches(REGEX_USERNAME)){
			System.out.println("REGEX_USERNAME Wrong");
		}

		if(pwdMngr.length()==8&&pwdMngr.matches(REGEX_PASSWORD)){
		this.password = pwdMngr;
		System.out.println("Password is finish");
		}
		if(pwdMngr.length()!=8){
			System.out.println("LONG_PASSWORD Wrong");
		}
		if(!pwdMngr.matches(REGEX_PASSWORD)){
			System.out.println("REGEX_PASSWORD Wrong");
		}
	}
	
	public String getManagerName(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
}


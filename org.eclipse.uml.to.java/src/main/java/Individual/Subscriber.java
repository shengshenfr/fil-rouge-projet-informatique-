/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;


import java.util.Calendar;

import java.util.Random;

import Bet.Bet;
import Individual.Player;
// Start of user code (user defined imports)
import Interface.Competitor;
import exceptions.AuthentificationException;
import exceptions.BadParametersException;
import exceptions.CantBetException;
import exceptions.MissingTokenException;
import utils.MyCalendar;

// End of user code

/**
 * Description of Subscriber.
 *
 * @author Robin
 */
public class Subscriber extends Player {
    /**
     * Description of the property password.
     */
    private String password = null;
    
    /**
     * Description of the property balance.
     */
    private long balance = 0L;
    
    private static int LONG_USERNAMEMIN = 4;
    private static String REGEX_USERNAME = "^[A-Za-z]{*}$";
    private static String REGEX_NAME = "^[A-Z][A-Za-z]{*}$";
    private int LONG_PASSWORD = 8;
    private String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
    private String userName;
    /**
     * Description of the property bornDate.
     */
    private Calendar bornDate = null;
    /**
     * Description of the property bets.
     */
    private String firstName;
    
    private String lastName;
    public Subscriber(String username,String firstName,String lastName,Calendar borndate) throws BadParametersException{
    	System.out.println("creation d'un subscriber");
        if(username==null||firstName==null||lastName==null||bornDate==null){
            throw new BadParametersException("can't have null name or bornDate!!!");
        }
        if(username.length()>LONG_USERNAMEMIN){
            throw new BadParametersException("the length of username should be 6.");
        }
        if(!username.matches(REGEX_USERNAME)||!firstName.matches(REGEX_USERNAME)||!lastName.matches(REGEX_USERNAME)){
            throw new BadParametersException("REGEX_NAME Wrong!");
        }
    	
    	this.userName = username;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.bornDate = borndate;
    }

 
    
    public Subscriber(String username, String firstname, String lastname, Calendar bornDate, long balance) throws BadParametersException{
        System.out.println("creation d'un subscriber");
        if(username==null||firstname==null||lastname==null||bornDate==null){
            throw new BadParametersException("can't have null name or bornDate!!!");
        }
        if(username.length()>LONG_USERNAMEMIN){
            throw new BadParametersException("the length of username should be bigger than 6.");
        }
        if(!username.matches(REGEX_USERNAME)||!firstname.matches(REGEX_USERNAME)||!lastname.matches(REGEX_USERNAME)){
            throw new BadParametersException("REGEX_NAME Wrong!");
        }

        this.firstName = firstname;
        this.lastName = lastname;
        this.bornDate = bornDate;
        this.balance = balance;
        this.password=generatePassword();
    }
    
    public Subscriber(String username,String password, String firstname, String lastname, Calendar bornDate, long balance){
    	System.out.println("creation d'un subscriber");
        if(username==null||firstname==null||lastname==null||bornDate==null){
            try {
				throw new BadParametersException("can't have null name or bornDate!!!");
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if(username.length()>LONG_USERNAMEMIN){
            try {
				throw new BadParametersException("the length of username should be 6.");
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if(!username.matches(REGEX_USERNAME)||!firstname.matches(REGEX_USERNAME)||!lastname.matches(REGEX_USERNAME)){
            try {
				throw new BadParametersException("REGEX_NAME Wrong!");
			} catch (BadParametersException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
   
    	this.firstName = firstname;
        this.lastName = lastname;
        this.bornDate = bornDate;
        this.balance = balance;
        this.password=password;
        this.userName=username;
    }
    
    
    public boolean authenticate(String pwd) throws AuthentificationException {
        // Start of user code for method authenticate
        // End of user code
        if(!this.password.equals(pwd)){
            throw new AuthentificationException("password is incorrect!");
        }
        else{return true;}
       
    }
    
    /**
     
     
     // Start of user code (user defined methods for Subscriber)
     
     // End of user code
     /**
     * Returns password.
     * @return password
     */
    public String generatePassword() {
            String passwordRandom = "passwordInitial";
            String[][] element = {{"0"},{"1"},{"2"},{"3"},{"4"},{"5"},{"6"},{"7"},{"8"},{"9"},
                {"a"},{"b"},{"c"},{"d"},{"e"},{"f"},{"g"},{"h"},{"i"},{"j"},{"k"},{"l"},{"m"},{"n"},
                {"o"},{"p"},{"q"},{"r"},{"s"},{"t"},{"u"},{"v"},{"w"},{"x"},{"y"},{"z"},
                {"M"},{"N"},{"O"},{"P"},{"Q"},{"R"},{"S"},{"T"},{"U"},{"V"},{"W"},{"X"},
                {"A"},{"B"},{"C"},{"D"},{"E"},{"F"},{"G"},{"H"},{"I"},{"J"},{"K"},{"L"},
                {"Y"},{"Z"}};
            while(!(passwordRandom.matches(REGEX_PASSWORD))){
                passwordRandom = "";
                for(int i=0;i<LONG_PASSWORD;i++){
                    Random rand = new Random();
                    int randNum = rand.nextInt(element.length);
                    
                    passwordRandom = passwordRandom + element[randNum][0];
                    
                    
                }
            }
        this.password=passwordRandom;
        return passwordRandom;
    }
    public String getPassword(){
    	return this.password;
    }
    
    /**
     * Sets a value to attribute password.
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    /**
     * Returns balance.
     * @return balance
     */
    public long getBalance() {
        return this.balance;
    }
    
    /**
     * Sets a value to attribute balance.
     * @param newBalance
     */
    public void setBalance(long newBalance) {
        this.balance = newBalance;
    }
    
    /**
     * Returns REGEX_USERNAME.
     * @return REGEX_USERNAME
     */
    public static String getREGEX_USERNAME() {
        return REGEX_USERNAME;
    }
    
    /**
     * Sets a value to attribute REGEX_USERNAME.
     * @param newREGEX_USERNAME
     */
    public static void setREGEX_USERNAME(String newREGEX_USERNAME) {
        REGEX_USERNAME = newREGEX_USERNAME;
    }
    
    /**
     * Returns LONG_USERNAME.
     * @return LONG_USERNAME
     */
    public static int getLONG_USERNAME() {
        return LONG_USERNAMEMIN;
    }
    
    /**
     * Sets a value to attribute LONG_USERNAME.
     * @param newLONG_USERNAME
     */
    public static void setLONG_USERNAME(int newLONG_USERNAME) {
        LONG_USERNAMEMIN = newLONG_USERNAME;
    }
    
    /**
     * Returns bornDate.
     * @return bornDate
     */
    public Calendar getBornDate() {
        return this.bornDate;
    }
    
    /**
     * Sets a value to attribute bornDate.
     * @param newBornDate
     */
    public void setBornDate(Calendar newBornDate) {
        this.bornDate = newBornDate;
    }
    
    /**
     * Returns username.
     * @return username
     */
    public String getUsername() {
        return this.userName;
    }
    
    /**
     * Sets a value to attribute username.
     * @param newUsername
     */
    public void setUsername(String newUsername) {
        this.userName = newUsername;
    }
    
    
    
    /**
     * Returns REGEX_NAME.
     * @return REGEX_NAME
     */
    public static String getREGEX_NAME() {
        return REGEX_USERNAME;
    }
    
    /**
     * Sets a value to attribute REGEX_NAME.
     * @param newREGEX_NAME
     */
    public static void setREGEX_NAME(String newREGEX_NAME) {
        REGEX_USERNAME = newREGEX_NAME;
    }
    
    public String getFirstname(){
        return firstName;
    }
    public String getLastname(){
        return lastName;
    }
    public void setFirstname(String newFirstname) {
        this.firstName = newFirstname;
    }
    public void setLastname(String newLastname) {
        this.lastName = newLastname;
    }
    
    public boolean equalsPlayer(AbstractCompetitor competitor) {
        // TODO Auto-generated method stub
        if(this.userName==competitor.getAbstractCompetitorName()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void creditSubscriber(long numberTokens) throws BadParametersException {
        // TODO Auto-generated method stub
        if(numberTokens<0)
        {
            throw new BadParametersException("Please give me a number positive!");
        }
        else{
            this.balance=this.balance+numberTokens;
        }
        
    }
    
    public void changeSubsPwd(String username2, String currentPwd, String newPwd) throws AuthentificationException, BadParametersException {
        // TODO Auto-generated method stub
        if(this.authenticate(currentPwd)){
        	if(newPwd==null){
                throw new BadParametersException("can't change a null password!!!");
            }
        	else if(newPwd.length()!=LONG_PASSWORD){
                throw new BadParametersException("the length of username should be 8.");
            }
        	else if(!newPwd.matches(REGEX_PASSWORD)){
                throw new BadParametersException("REGEX_PASSWORD Wrong!");
            }
            else{
        	this.password=newPwd;
            }
        }
        else{
            throw new AuthentificationException("You didn't have the right pass word, this change is wrong!");
        }
    }
    
    public void debitSubscriber(long numberTokens) throws MissingTokenException, BadParametersException {
        // TODO Auto-generated method stub
        if(numberTokens<0)
        {
            throw new BadParametersException("Please give me a number positive!");
        }
        else if(this.balance<=numberTokens){
            throw new MissingTokenException("You didn't have enough money for this bet!");
        }
        else{
            this.balance=this.balance-numberTokens;
        }
    }
    
    public void checkBlance(long numberTokens) throws CantBetException{
        if(numberTokens>this.balance){
            throw new CantBetException("Not enough money!");
        }
    }
    
    
    
}

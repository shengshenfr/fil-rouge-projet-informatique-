/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package Individual;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
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
    
    private static int LONG_USERNAME = 6;
    private static String REGEX_USERNAME = "^[0-9A-Za-z]{6}$";
    private int LONG_PASSWORD = 8;
    private String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$";
    private String username;
    /**
     * Description of the property bornDate.
     */
    private Calendar bornDate = null;
    /**
     * Description of the property bets.
     */
    private String firstname;
    
    private String lastname;
    public Subscriber(String username){
    	
    	super(username);
    }

    
    public Subscriber(String username, String firstname, String lastname, Calendar bornDate, long balance) throws BadParametersException{
        System.out.println("creation d'un subscriber");
        if(username.length()!=6){
            throw new BadParametersException("LONG_USERNAME Wrong");
        }
        if(!username.matches(REGEX_USERNAME)){
            throw new BadParametersException("REGEX_USERNAME Wrong");
        }
        if(username.length()==6&&username.matches(REGEX_USERNAME)){
            this.username = username;
            System.out.println("Username is finish");
        }
        this.firstname = firstname;
        this.lastname = lastname;
        this.bornDate = bornDate;
        this.balance = balance;
    }
    
    
    public boolean authenticate(String pwd) throws AuthentificationException {
        // Start of user code for method authenticate
        // End of user code
        if(!(this.password==pwd)){
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
    public String getPassword() {
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
        return LONG_USERNAME;
    }
    
    /**
     * Sets a value to attribute LONG_USERNAME.
     * @param newLONG_USERNAME
     */
    public static void setLONG_USERNAME(int newLONG_USERNAME) {
        LONG_USERNAME = newLONG_USERNAME;
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
        return this.username;
    }
    
    /**
     * Sets a value to attribute username.
     * @param newUsername
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
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
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public void setFirstname(String newFirstname) {
        this.firstname = newFirstname;
    }
    public void setLastname(String newLastname) {
        this.lastname = newLastname;
    }
    
    public boolean equalsPlayer(AbstractCompetitor competitor) {
        // TODO Auto-generated method stub
        if(this.username==competitor.getAbstractCompetitorName()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void debitSubscriber(long numberTokens) throws BadParametersException {
        // TODO Auto-generated method stub
        if(numberTokens<0)
        {
            throw new BadParametersException("Please give me a number positive!");
        }
        else{
            this.balance=this.balance+numberTokens;
        }
        
    }
    
    public void changeSubsPwd(String username2, String currentPwd, String newPwd) throws AuthentificationException {
        // TODO Auto-generated method stub
        if(this.authenticate(currentPwd)){
            this.password=newPwd;
        }
        else{
            throw new AuthentificationException("You didn't have the right pass word, this change is wrong!");
        }
    }
    
    public void creditSubscriber(long numberTokens) throws MissingTokenException, BadParametersException {
        // TODO Auto-generated method stub
        if(numberTokens<0)
        {
            throw new BadParametersException("Please give me a number positive!");
        }
        else if(this.balance<numberTokens){
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

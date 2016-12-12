package utils;

public class dateValide {
	/**
	 * valide string date for a MyCalendar object
	 * The format has to be dd/mm/yyyy.
	 * 
	 * @param s
	 * 		the string to convert into a MyCalendar
	 * @return date
	 * 		the MyCalendar objet corresponding
	 */
	public static MyCalendar change_date(String s){
	
		String[] stringDate = s.split("[ /]");
		
		MyCalendar date = new MyCalendar(Integer.parseInt(stringDate[2]),
				Integer.parseInt(stringDate[1]),Integer.parseInt(stringDate[0]));
		
		return date;
	}
}

/**
 * Universal Logger class
 * 
 * 
 * @author Gaurav
 *
 */
public class Logger {

	public static boolean logEnabled = true;
	
	public static void log (String logMessage){
		
		if (logEnabled){
			
			System.out.println(logMessage);
		}
	}
}

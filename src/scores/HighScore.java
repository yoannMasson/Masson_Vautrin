package scores;
import org.jsoup.*;
/**
 * 
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Classe that opens an HTTP Connection with a ThinkSpeak online server
 */

public class HighScore {
	
	public static String urlConnection = "https://thingspeak.com/login";
	public static String urlChannelFeed = "https://api.thingspeak.com/channels/109203/feeds.json?results=2";
	
	/**
	 * Open an HTTP Connection with the ThingSpeak Server online
	 * @throws Exception related to the HTTP Connection
	 * @return tableau de scores
	 */
	public String[] getScores(){
		String[] score = {"100","150","478","0","4","70","14","46","120","800"};
		return score;
	}
	

}

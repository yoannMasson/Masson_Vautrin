package scores;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


/**
 * @version 1.0.0
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Classe that opens an HTTP Connection with a ThinkSpeak online server
 */

public class HighScore1 {

	public static final int NOMBRE_SCORE_AFFICHE = 10;
	public static final String URL_CONNECTION = "https://thingspeak.com/login";
	public static final String URL_CHANNEL_FEED = "https://api.thingspeak.com/channels/109203/feeds.csv";

	/**
	 * Open an HTTP Connection with the ThingSpeak Server online
	 * @throws Exception related to the HTTP Connection
	 * @return tableau scores
	 */
	public String[] getScores()throws HttpException,UnsupportedEncodingException{

		HttpConnection connection = new HttpConnection();
		// Recuperation des donnï¿½es
		String result = connection.GetPageContent(URL_CHANNEL_FEED);
		System.out.println(result);
		String[] resultat;
		int i = 6;
		int j = 0;
		int nbScore =0;
		String[] split = result.split(",|....-..-.. ..:..:.. UTC"); //Expression rï¿½guliï¿½re, pour sï¿½parer les informations si on rencontre "," ou d'une date

		while(i<split.length && nbScore<NOMBRE_SCORE_AFFICHE){//On compte combien, on aura de score à stocker
			i=i+4;
			nbScore++;
		}

		i=6;
		resultat = new String[nbScore];
		while(i<split.length ){ // On s'aperï¿½oit que les scores sont aux indices suivants 6,10,14,...
			resultat[j]=split[i+1]+": "+split[i];//On concatï¿½ne, le score et le pseudo
			i = i+4;
			j++;
		}
		return resultat;
	}

	//Classe qui va permettre la connection (voir http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/)
	private class HttpConnection{

		private List<String> cookies;
		private HttpsURLConnection conn;

		private final String USER_AGENT = "Mozilla/5.0";

		private String GetPageContent(String url) throws HttpException {
			try{
				URL obj = new URL(url);
				conn = (HttpsURLConnection) obj.openConnection();

				// default is GET
				conn.setRequestMethod("GET");

				conn.setUseCaches(false);

				// act like a browser
				conn.setRequestProperty("User-Agent", USER_AGENT);
				conn.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				if (cookies != null) {
					for (String cookie : this.cookies) {
						conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
					}
				}
				int responseCode = conn.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = 
						new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Get the response cookies
				setCookies(conn.getHeaderFields().get("Set-Cookie"));

				return response.toString();

			}catch(Exception e){
				throw new HttpException();
			}
		}

		public void setCookies(List<String> cookies) {
			this.cookies = cookies;
		}

	}

}



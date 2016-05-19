package scores3;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import java.net.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
/**
 * @version 2.0.0
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Classe that opens an HTTP Connection with a ThinkSpeak online server
 */

public class HighScore3 {

	private static final int NOMBRE_SCORE_AFFICHE = 10;
	private static final String URL_CONNECTION = "https://thingspeak.com/login";
	private static final String URL_CHANNEL_FEED = "https://api.thingspeak.com/channels/109203/feeds.csv";

	/**
	 * Open an HTTP Connection with the ThingSpeak Server online
	 * @throws Exception related to the HTTP Connection
	 * @return tableau online score with the format "pseudo score"
	 */
	public String[] getScores()throws HttpException,UnsupportedEncodingException{

		// make sure cookies is turn on
		/*CookieHandler.setDefault(new CookieManager());
		// 1. Send a "GET" request, so that you can extract the form's data.
		String page = connection.GetPageContent(urlConnection);
		String postParams = connection.getFormParams(page, "yoann.masson95@gmail.com", "javaProjet");

		// 2. Construct above post's content and then send a POST request for
		// authentication
		connection.sendPost(urlConnection, postParams);*/

		// Recuperation des donn�es
		String result = GetPageContent(URL_CHANNEL_FEED);
		System.out.println(result);
		String[] resultat;
		int i = 6;
		int j = 0;
		int nbScore =0;
		String[] split = result.split(",|....-..-.. ..:..:.. UTC"); //Expression r�guli�re, pour s�parer les informations si on rencontre "," ou d'une date

		while(i<split.length ){//On compte combien, on aura de score
			i=i+4;
			nbScore++;
		}

		i=6;
		resultat = new String[nbScore];
		while(i<split.length ){ // On s'aper�oit que les scores sont aux indices suivants 6,10,14,...
			resultat[j]=split[i+1]+" "+split[i];//On concat�ne, le score et le pseudo
			i = i+4;
			j++;
		}
		return resultat;
	}


	/**
	 * M�thode renvoyant un tableau de BestPlayer tri�, 10 au maximum
	 * @param readScores tableau de String de la forme "pseudo score"
	 */
	public BestPlayer3[] tenBestScores(String[] readScores){//Les donn�es en entr�e doivent �tre de la forme"pseudo: score" sous peine de lever une Exception
		
		int i;
		BestPlayer3[] tabPlayer = new BestPlayer3[readScores.length];
		String[] split;

		//Boucle pour remplir le tableau de BestPlayer
		for(i=0;i<readScores.length;i++){
			split = readScores[i].split(" "); // On separe le player de son score pour le mettre dans le tableau
			tabPlayer[i] = new BestPlayer3(split[0],Integer.parseInt(split[1]));
		}

		//Boucle pour trier le tableau. (tri � bulles)
		i=0;
		while(i<tabPlayer.length-1){
			if(tabPlayer[i].compareTo(tabPlayer[i+1]) == 1){ //le player � l'indice i est plus petit que celui � l'indice i+1
				BestPlayer z = tabPlayer[i];
				tabPlayer[i] = tabPlayer[i+1];
				tabPlayer[i+1] = z;
				i=0;
			}else{
				i++;
			}
		}
		
		//Au cas o� on ait plus de 10 scores, on ne garde que les 10 premiers
		if (tabPlayer.length > NOMBRE_SCORE_AFFICHE){
			BestPlayer[] tabPlayerTemp = new BestPlayer[10]; 
			for(i=0;i<10;i++){
				tabPlayerTemp[i]=tabPlayer[i];
			}
		tabPlayer = tabPlayerTemp;
		}
		
		return tabPlayer;
	}
	
	/**
	 * Methode envoyant les données sur thingsSpeak
	 * @param p BestPlayer3
	 */
	public void sendScore(BestPlayer3 p){
    	try {
    
        	String pseudo = p.getPseudo();
        	int score = p.getScore();
        	URL getURL = new URL("https://api.thingspeak.com/update?api_key=U56DEYOQO2B0E62E&field1="+score+ "&field2="+pseudo);
        	getURL.openStream();
        
    	} catch (Exception e) { e.printStackTrace(); }
	}
	





	//Classe qui va permettre la connection (voir http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/)

		private List<String> cookies;
		private HttpsURLConnection conn;

		private final String USER_AGENT = "Mozilla/5.0";


		private void sendPost(String url, String postParams) throws HttpException {
			try{
				URL obj = new URL(url);
				conn = (HttpsURLConnection) obj.openConnection();

				// Acts like a browser
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Host", "accounts.google.com");
				conn.setRequestProperty("User-Agent", USER_AGENT);
				conn.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				for (String cookie : this.cookies) {
					conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
				}
				conn.setRequestProperty("Connection", "keep-alive");
				conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

				conn.setDoOutput(true);
				conn.setDoInput(true);

				// Send post request
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(postParams);
				wr.flush();
				wr.close();

				int responseCode = conn.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + postParams);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = 
						new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				// System.out.println(response.toString());
			}catch(Exception e){
				throw new HttpException();
			}

		}

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

		private String getFormParams(String html, String username, String password)
				throws UnsupportedEncodingException {

			System.out.println("Extracting form's data...");

			Document doc = Jsoup.parse(html);

			Element loginform = doc.getElementById("gaia_loginform");
			Elements inputElements = loginform.getElementsByTag("input");
			List<String> paramList = new ArrayList<String>();
			for (Element inputElement : inputElements) {
				String key = inputElement.attr("name");
				String value = inputElement.attr("value");

				if (key.equals("Email"))
					value = username;
				else if (key.equals("Passwd"))
					value = password;
				paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
			}

			// build parameters list
			StringBuilder result = new StringBuilder();
			for (String param : paramList) {
				if (result.length() == 0) {
					result.append(param);
				} else {
					result.append("&" + param);
				}
			}
			return result.toString();
		}

		private List<String> getCookies() {
			return cookies;
		}

		private void setCookies(List<String> cookies) {
			this.cookies = cookies;
		}

	}




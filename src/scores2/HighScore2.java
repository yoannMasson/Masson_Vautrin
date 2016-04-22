package scores2;

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

public class HighScore2 {

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
		// make sure cookies is turn on
		/*CookieHandler.setDefault(new CookieManager());
		// 1. Send a "GET" request, so that you can extract the form's data.
		String page = connection.GetPageContent(urlConnection);
		String postParams = connection.getFormParams(page, "yoann.masson95@gmail.com", "javaProjet");

		// 2. Construct above post's content and then send a POST request for
		// authentication
		connection.sendPost(urlConnection, postParams);*/

		// Recuperation des données
		String result = connection.GetPageContent(URL_CHANNEL_FEED);

		String[] resultat;
		int i = 5;
		int j = 0;
		int nbScore =0;
		String[] split = result.split(",");

		while(i<split.length && nbScore<NOMBRE_SCORE_AFFICHE){
			i=i+3;
			nbScore++;
		}

		i =5;
		resultat = new String[nbScore];
		while(i<split.length ){ // On s'aperçoit que les scores sont aux indices suivants 5,8,11,14,...
			resultat[j]=split[i];
			i = i+3;
			j++;
		}
		return resultat;
	}

	//Classe qui va permettre la connection (voir http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/)
	private class HttpConnection{

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

		public String getFormParams(String html, String username, String password)
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

		public List<String> getCookies() {
			return cookies;
		}

		public void setCookies(List<String> cookies) {
			this.cookies = cookies;
		}

	}

}



package scores3;

import java.io.*;
import java.util.*;

/**
 * @version 2.0.0
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Test Class for the second Version of the game
 */
public class TestHighScore3 {

	public static void main(String[] args) {

		String nomFichier = "scoreSamples.txt";
		FileInputStream file = null;
		InputStreamReader inputStream = null;
		List<Integer> scores = new LinkedList<>();
		String pseudo;
		Scanner s = new Scanner(System.in);
		HighScore2 hs = new HighScore2();
		BestPlayer[] tabPlayer;
		
		try{
			tabPlayer = hs.tenBestScores(hs.getScores());
			for(int i=0;i<tabPlayer.length;i++){//Affichage des scores en ligne	
					System.out.println(tabPlayer[i]);
			}
			
			System.out.println("Rentrez votre pseudo. (espace non pris en compte) ");//Demande du pseudo
			pseudo = s.next();
			s.close();

			file = new FileInputStream(nomFichier);//Lecture des scores dans le fichier
			inputStream = new InputStreamReader(file);
			BufferedReader buffer = new BufferedReader(inputStream);
			
			LireScoreFichier(scores,buffer);//Remplissage de la liste des scores 
			System.out.println("Voici votre score "+pseudo+": "+scores.get((int)(Math.random()*scores.size())));
			buffer.close();
			
			
			
			
			//Gestion des exceptions
		}catch(FileNotFoundException e){
			System.out.println("Le fichier "+nomFichier+" n'existe pas");
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			System.out.println("Probl�me d'encodage");
		}catch(HttpException e){
			System.out.println("Probl�me avec la connection au serveur");
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("Probl�me lors de la lecture du fichier "+nomFichier);
			e.printStackTrace();
		}catch (Exception e){
			System.out.println("Aucune id�e du pb, you are screwed");
			e.printStackTrace();
		}
		

	}


	/**
	 * Read scores from a file and add them in a List, this method does not erase previus values, just adds the new ones
	 * @param scores list where the scores will be added
	 * @param buffer buffer where are read the scores
	 * @throws IOException in case the buffer is not valid.
	 */
	private static void LireScoreFichier(List<Integer> scores,BufferedReader buffer) throws IOException{
		String score;
		score = buffer.readLine();
		while (score  != null){
			scores.add(Integer.parseInt(score));
			score = buffer.readLine();	
		}

	}

}

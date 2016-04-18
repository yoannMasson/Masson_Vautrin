package scores;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Classe de test de la première version de HighScore
 */
public class TestHighScore {

	public static void main(String[] args) {
		String nomFichier = "scoreSamples.txt";
		FileInputStream file = null;
		InputStreamReader inputStream = null;
		List<Integer> scores = new LinkedList<>();
		String pseudo;
		Scanner s = new Scanner(System.in);

		System.out.println("Rentrez votre pseudo.");//Demande du pseudo
		pseudo = s.next();
		s.close();

		try{
			file = new FileInputStream(nomFichier);
			inputStream = new InputStreamReader(file);
			BufferedReader buffer = new BufferedReader(inputStream);
			//Remplissage de la liste des scores 
			LireScoreFichier(scores,buffer);
			System.out.println("Voici votre score "+pseudo+": "+scores.get((int)(Math.random()*scores.size())));
			buffer.close();
			//Gestion des exceptions
		}catch(FileNotFoundException e){
			System.out.println("Le fichier "+nomFichier+" n'existe pas");
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("Problème lors de la lecture du fichier "+nomFichier);
			e.printStackTrace();
		}
	}
	
	private static void LireScoreFichier(List<Integer> scores,BufferedReader buffer) throws IOException{
		String score;
		score = buffer.readLine();
		while (score  != null){
			scores.add(Integer.parseInt(score));
			score = buffer.readLine();	
		}
		
	}

}

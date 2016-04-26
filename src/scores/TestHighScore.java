package scores;

import java.io.*;
import java.util.*;

/**
 * @version 1.0.0
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Classe de test de la premi�re version de HighScore
 */
public class TestHighScore {

	public static void main(String[] args) {

		String nomFichier = "scoreSamples.txt";
		FileInputStream file = null;
		InputStreamReader inputStream = null;
		List<Integer> scores = new LinkedList<>();
		String pseudo;
		Scanner s = new Scanner(System.in);
		HighScore1 hs = new HighScore1();
		String[] onlineScore;
		try{
			onlineScore = hs.getScores();//R�cup�ration des scores en ligne

			for(int i=0;i<onlineScore.length;i++){//Affichage des scores en ligne	
					System.out.println(onlineScore[i]);
			}

			System.out.println("Rentrez votre pseudo.");//Demande du pseudo
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
	 * Tri un tableau de String par ordre croissant avec la m�thode du tri par bulles.
	 * @param onlineScore
	 */
	public static void tri(String[] tab) throws NotNumberException {
		try{
			int i=0;
			String tampon = null;
			if(tab.length > 1){
				while (i < (tab.length-1)){ //On ne voudrait pas comparer le dernier �l�ment avec le dernier+1
					if(Integer.parseInt(tab[i]) > Integer.parseInt(tab[i+1])){
						tampon = tab[i];
						tab[i]=tab[i+1];
						tab[i+1]=tampon;
						i=0;
					}else{
						i++;	
					}
				}
			}
		}catch(NumberFormatException e){
			throw new NotNumberException();
		}

	}

	/**
	 * Lit les scores d'un fichier et les mets dans la liste scores
	 * @param scores liste dans laquelle les scores sont ajout�s
	 * @param buffer buffer o� sont lu les fichiers
	 * @throws IOException Dans le cas o� le buffer n'est pas valide
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

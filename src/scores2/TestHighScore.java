package scores2;

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
		HighScore2 hs = new HighScore2();
		String[] onlineScore;
		try{
			onlineScore = hs.getScores();//Récupération des scores en ligne

		    //tri(onlineScore);
			for(int i=0;i<onlineScore.length;i++){//Affichage des scores en ligne
				if(i==0){
					System.out.println("1er: "+onlineScore[i]);
				}else{
					System.out.println((i+1)+"eme: "+onlineScore[i]);
				}
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
			System.out.println("Problème d'encodage");
		}catch(HttpException e){
			System.out.println("Problème avec la connection au serveur");
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("Problème lors de la lecture du fichier "+nomFichier);
			e.printStackTrace();
		}catch (Exception e){
			System.out.println("Aucune idée du pb, you are screwed");
			e.printStackTrace();
		}
	}

	/**
	 * Tri un tableau de String par ordre croissant avec la méthode du tri par bulles.
	 * @param onlineScore
	 */
	public static void tri(String[] tab) throws NotNumberException {
		try{
			int i=0;
			String tampon = null;
			if(tab.length > 1){
				while (i < (tab.length-1)){ //On ne voudrait pas comparer le dernier élément avec le dernier+1
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
	 * @param scores liste dans laquelle les scores sont ajoutés
	 * @param buffer buffer où sont lu les fichiers
	 * @throws IOException Dans le cas où le buffer n'est pas valide
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

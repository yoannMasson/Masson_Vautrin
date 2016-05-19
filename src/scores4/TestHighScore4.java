package scores4;

import java.io.*;
import java.util.*;

/**
 * @version 4.0.0
 * @author Yoann Masson & Baptiste Vautrin
 *
 *	Test Class for the second Version of the game
 */
public class TestHighScore4 {

	public static void main(String[] args) {

		int placement = 1;
		String nomFichier = "scoreSamples.txt";
		String reponse;
		FileInputStream file = null;
		InputStreamReader inputStream = null;
		List<Integer> scores = new LinkedList<>();
		String pseudo;
		Scanner s = new Scanner(System.in);
		HighScore4 hs = new HighScore4();
		BestPlayer4[] tabPlayer;
		boolean veutContinuer = true;

		while(veutContinuer){
			try{
				placement = 1;
				tabPlayer = hs.tenBestScores(hs.getScores());
				for(int i=0;i<tabPlayer.length;i++){//Affichage des scores en ligne	
					if(placement == 1){
						System.out.println("1er: "+tabPlayer[i]);
					}else{
						System.out.println(placement+"eme: "+tabPlayer[i]);
					}
					placement++;
				}

				System.out.println("Rentrez votre pseudo. (espace non pris en compte) ");//Demande du pseudo
				pseudo = s.next();

				file = new FileInputStream(nomFichier);//Lecture des scores dans le fichier
				inputStream = new InputStreamReader(file);
				BufferedReader buffer = new BufferedReader(inputStream);

				LireScoreFichier(scores,buffer);//Remplissage de la liste des scores 
				int scoreRand = scores.get((int)(Math.random()*scores.size()));
				System.out.println("Voici votre score "+pseudo+": "+scoreRand);
				buffer.close();

				for (BestPlayer4 p : tabPlayer) { // equivalent foreach
					if (p.getScore() < scoreRand) {
						hs.sendScore(new BestPlayer4(pseudo, scoreRand));
						break;
					}
				}
				System.out.println("Voulez-vous continuer à jouer (oui/non)?");
				reponse = s.next();
				while(!reponse.equals("oui") && !reponse.equals("non")){
					System.out.println("Voulez-vous continuer à jouer (oui/non)?");
					reponse = s.next();
				}
				if(reponse.equals("non")){
					veutContinuer = false;
				}
			}		//Gestion des exceptions
			catch(FileNotFoundException e){
				System.out.println("Le fichier "+nomFichier+" n'existe pas");
				e.printStackTrace();
			}catch(UnsupportedEncodingException e){
				System.out.println("Problï¿½me d'encodage");
			}catch(HttpException e){
				System.out.println("Problï¿½me avec la connection au serveur");
				e.printStackTrace();
			}catch (IOException e) {
				System.out.println("Problï¿½me lors de la lecture du fichier "+nomFichier);
				e.printStackTrace();
			}catch (Exception e){
				System.out.println("Aucune idï¿½e du pb, you are screwed");
				e.printStackTrace();
			}	
		}
		s.close();
		System.out.println("Merci d'être passé !");
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

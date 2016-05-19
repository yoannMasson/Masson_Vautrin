package scores4;

/**
 * @version 4.0.0
 * @author Yoann Masson & Baptiste Vautrin
 * 
 *	Class that represent a player, with a name and a score
 */
public class BestPlayer4 {

	private String pseudo;
	private int score;
	
	/**
	 * Create a BestPlayer instance
	 * @param pseudo pseudo of the BestPlayer
	 * @param score score of the BestPlayer
	 */
	public BestPlayer4(String pseudo, int score){
		this.pseudo = pseudo;
		this.score = score;
	}

	/**
	 * Compare the score of the player with the score of the player given
	 * @param player player compare to this
	 * @return -1 if player has a better score, 0 if player has an equal score, 1 if "this" is better
	 */
	public int compareTo(BestPlayer4 player){
		if (this.getScore() < player.getScore()){
			return -1;
		}
		else if (this.getScore() == player.getScore()){
			return 0;
		}
		else{
			return 1;
		}
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String nom) {
		this.pseudo = nom;
	}
	
	public String toString(){
		return this.getPseudo()+" "+this.getScore();
	}

}

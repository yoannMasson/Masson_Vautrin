package scores3;

/**
 * @version 2.0.0
 * @author Yoann Masson & Baptiste Vautrin
 * 
 *	Class that represent a player, with a name and a score
 */
public class BestPlayer3 {

	private String pseudo;
	private int score;
	
	/**
	 * Create a BestPlayer3 instance
	 * @param pseudo pseudo of the BestPlayer3
	 * @param score score of the BestPlayer3
	 */
	public BestPlayer3(String pseudo, int score){
		this.pseudo = pseudo;
		this.score = score;
	}

	/**
	 * Compare the score of the player with the score of the player given
	 * @param player player compare to this
	 * @return -1 if player has a better score, 0 if player has an equal score, 1 if "this" is better
	 */
	public int compareTo(BestPlayer3 player){
		if (this.getScore()< player.getScore()){
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

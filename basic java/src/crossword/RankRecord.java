package crossword;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

 public class RankRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4450910547842173638L;
	private final  SimpleIntegerProperty rankNumber = new SimpleIntegerProperty();
	private final SimpleStringProperty playerName = new SimpleStringProperty();
	private final SimpleIntegerProperty score = new SimpleIntegerProperty();
	
	
	public RankRecord(int ranknum, String playername, int score) {
		this.setRankNumber(ranknum);
		this.setPlayerName(playername);
		this.setScore(score);
	}
	
	public void setRankNumber(int ranknum) {
		this.rankNumber.set(ranknum);
	}
	
	public void setPlayerName(String name) {
		this.playerName.set(name);
	}
	
	public void setScore(int score) {
		this.score.set(score);
	}

	public int getRankNumber() {
		return this.rankNumber.get();
	}
	
	public String getPlayeName() {
		return this.playerName.get();
	}
	
	public int getScore() {
		return this.score.get();
	}
	
	public SimpleIntegerProperty rankNumberProperty() {
		return this.rankNumber;
	}
	
	public SimpleStringProperty playerNameProperty() {
		return this.playerName;
	}
	
	public SimpleIntegerProperty scoreProperty() {
		return this.score;
	}
	
	public String toString() {
		return "recoder[ " + "rankNumber:" + this.getRankNumber() + 
				" playerName:" + this.getPlayeName() + " score:" + this.getScore() +" ]";
	}
	
	public record toRecord() {
		record r = new record(this.getRankNumber(), this.getPlayeName(), this.getScore());
		return r;
	}
}

 
class record implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2088058632893470750L;
	int rankNum;
	String playerName;
	int score;
	
	public record(int ranknum, String playername, int score) {
		this.rankNum = ranknum;
		this.playerName = playername;
		this.score = score;
	}
	
	public void setRankNum(int ranknum) {
		this.rankNum = ranknum;
	}
	
	public RankRecord toRankRecord() {
		RankRecord rankrecord = new RankRecord(this.rankNum, this.playerName, this.score);
		return rankrecord;
	}
}












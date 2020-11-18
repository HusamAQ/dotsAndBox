package MCTS;

public class State {

	private int[][] state; 
	private int score1, score2;
	private boolean botsTurn;
	
	public State(int[][] matrix, int score1, int score2, boolean botsTurn) {
		this.state=matrix;
		this.score1=score1;
		this.score2=score2;
		this.botsTurn=botsTurn;
	};
	
	public int[][] getBoard(){return this.state;}
	
	public int getScore1() {return score1;}

	public int getScore2() {return score2;}
	
	public boolean getBotTurn() {return botsTurn;}
	
	public int getScoreTotal() {return (score2-score1);}
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other.getClass().getName() != "State") return false;
		State o = (State) other;
		if(o.getBotTurn()==botsTurn && o.getScoreTotal()==(score2-score1) && o.getBoard()==state) return true;
		return false;
	}
}

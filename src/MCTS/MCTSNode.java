package MCTS;

import java.util.ArrayList;

public class MCTSNode {
	private int id;
	private static int lastIDgiven =0;
	
	private int visited=0;
	private int won=0;
	
	private int score;
	
	private int[][] state;
	
	private MCTSNode parent;
	
	private ArrayList<MCTSNode> children= new ArrayList<MCTSNode>();
	
	
	public MCTSNode(int[][] matrix, int score) {
		id=lastIDgiven;
		lastIDgiven++;
		state=matrix;
		this.score=score;
		generateChildren();
	}
	
	//TODO this method should generate all the children of this node.
	private void generateChildren() {}

	public void setParent(MCTSNode parent) {this.parent=parent;}
	
	public MCTSNode getParent() {return this.parent;}
	
	//TODO this function should return the current value of a node in the MCTS.
	public double getValue() {return 0;}
	
	public int getID() {return this.id;}
	
	public int getVisited() {return this.visited;}
	
	public int getWon() {return this.won;}
	
	public int getScore() {return this.score;}
	
	public int[][] getState() {return this.state;}
	
	public ArrayList<MCTSNode> getChildren(){return this.children;}
	
	public boolean equalsState(int[][] matrix) {
		if(matrix==state) return true;
		return false;
	}
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other.getClass().getName() != "MCTSNode") return false;
		MCTSNode o = (MCTSNode) other;
		if(o.getState()==state && o.getScore()==score) return true;
		return false;
	}
}

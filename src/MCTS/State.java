package MCTS;

import java.util.ArrayList;

import game.ELine;
import game.Vertex;

public class State {

	//Represents the board
	private int[][] state; 
	//Score of each player, we are assuming bot is always player 2.
	private int score1, score2;
	//To indicate if at this state it is the bots turn
	private boolean botsTurn;
	
	private ArrayList<ELine> inputAvailLines;
	
	private static ArrayList<ArrayList<ELine>> states = new ArrayList<>();
	private static ArrayList<Integer> playerScores= new ArrayList<>();
	private static ArrayList<Integer> otherPlayerScores= new ArrayList<>();
	
	public State(int[][] matrix, int score1, int score2, boolean botsTurn) {
		this.state=matrix;
		this.score1=score1;
		this.score2=score2;
		this.botsTurn=botsTurn;
	}
	
	public State(int[][] matrix, int score1, int score2, boolean botsTurn, ArrayList<ELine> inputAvailLines) {
		this.state=matrix;
		this.score1=score1;
		this.score2=score2;
		this.botsTurn=botsTurn;
		this.inputAvailLines=inputAvailLines;
	};

	
	public int[][] getBoard(){return this.state;}
	
	public int getScore1() {return score1;}

	public int getScore2() {return score2;}
	
	public boolean getBotTurn() {return botsTurn;}
	
	public int getScoreTotal() {return (score2-score1);}
	
	public ArrayList<ELine> getAvailLines(){return this.inputAvailLines;}
	
	public static void getStates(State state) {
		ArrayList<State> states = new ArrayList<State>();
		states = new ArrayList<>();
		playerScores= new ArrayList<>();
		otherPlayerScores= new ArrayList<>();
		
		possibleStatesAndScores(state.getAvailLines(), state.getBoard(),state.getScore1(), state.getScore2());
	}
	
	private static void possibleStatesAndScores(ArrayList<ELine> inputAvailLines, int[][] inputMatrix,int inputPlayerScore, int inputOtherPlayerScore){
	    for(int a=0;a<inputAvailLines.size();a++){
	        possUtil(a,inputAvailLines,true,inputMatrix,inputPlayerScore,inputOtherPlayerScore);
	    }
	}

	private static void possUtil(int t, ArrayList<ELine> state, boolean turn,int[][] matrix, int playerScore,int otherPlayerScore){
	    if(state.size()!=0) {
	        ELine action = state.remove(t);
	        matrix[action.vertices.get(0).getID()][action.vertices.get(1).getID()] = 2;
	        matrix[action.vertices.get(1).getID()][action.vertices.get(0).getID()] = 2;
	        int tem = checkBox(action, matrix);
	        if (tem > 0) {
	            for (int l = 0; l < tem; l++) {
	                if (turn) {
	                    playerScore++;
	                } else {
	                    otherPlayerScore++;
	                }
	            }
	        } else {
	            turn = !turn;
	        }
	        states.add(state);
	        playerScores.add(playerScore);
	        otherPlayerScores.add(otherPlayerScore);
	        for (int a = 0; a < state.size(); a++) {
	            possUtil(a, state, turn, matrix, playerScore, otherPlayerScore);
	        }
	    }
	}
	private static int checkBox(ELine line,int[][] matrix){
	    ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
	    if(line.getHorizontal()){
	        if(line.vertices.get(0).getUpVertex()!=null){
	            if(matrix[line.vertices.get(0).getID()][line.vertices.get(0).getUpVertex().getID()]==2&&matrix[line.vertices.get(1).getID()][line.vertices.get(1).getUpVertex().getID()]==2&&matrix[line.vertices.get(0).getUpVertex().getID()][line.vertices.get(1).getUpVertex().getID()]==2){
	                ArrayList<Vertex> box = new ArrayList<>();
	                box.add(line.vertices.get(0));
	                box.add(line.vertices.get(1));
	                box.add(line.vertices.get(0).getUpVertex());
	                box.add(line.vertices.get(1).getUpVertex());
	                listOfBoxes.add(box);
	            }
	        }
	        if(line.vertices.get(0).getDownVertex()!=null){
	            if(matrix[line.vertices.get(0).getID()][line.vertices.get(0).getDownVertex().getID()]==2&&matrix[line.vertices.get(1).getID()][line.vertices.get(1).getDownVertex().getID()]==2&&matrix[line.vertices.get(0).getDownVertex().getID()][line.vertices.get(1).getDownVertex().getID()]==2){
	                ArrayList<Vertex> box2 = new ArrayList<>();
	                box2.add(line.vertices.get(0));
	                box2.add(line.vertices.get(1));
	                box2.add(line.vertices.get(0).getDownVertex());
	                box2.add(line.vertices.get(1).getDownVertex());
	                listOfBoxes.add(box2);
	            }
	        }
	    }else{
	        if(line.vertices.get(0).getRightVertex()!=null){
	            if(matrix[line.vertices.get(0).getID()][line.vertices.get(0).getRightVertex().getID()]==2&&matrix[line.vertices.get(1).getID()][line.vertices.get(1).getRightVertex().getID()]==2&&matrix[line.vertices.get(0).getRightVertex().getID()][line.vertices.get(1).getRightVertex().getID()]==2){
	                ArrayList<Vertex> box3 = new ArrayList<>();
	                box3.add(line.vertices.get(0));
	                box3.add(line.vertices.get(1));
	                box3.add(line.vertices.get(0).getRightVertex());
	                box3.add(line.vertices.get(1).getRightVertex());
	                listOfBoxes.add(box3);
	            }
	        }
	        if(line.vertices.get(0).getLeftVertex()!=null){
	            if(matrix[line.vertices.get(0).getID()][line.vertices.get(0).getLeftVertex().getID()]==2&& matrix[line.vertices.get(1).getID()][line.vertices.get(1).getLeftVertex().getID()]==2&& matrix[line.vertices.get(0).getLeftVertex().getID()][line.vertices.get(1).getLeftVertex().getID()]==2){
	                ArrayList<Vertex> box4 = new ArrayList<>();
	                box4.add(line.vertices.get(0));
	                box4.add(line.vertices.get(1));
	                box4.add(line.vertices.get(0).getLeftVertex());
	                box4.add(line.vertices.get(1).getLeftVertex());
	                listOfBoxes.add(box4);
	            }
	        }
	    }
	    // if it creates no boxes, return null.
	    if(listOfBoxes.isEmpty()){
	        return 0;
	    }
	        /*
	        for(ArrayList<Vertex> box:listOfBoxes){
	            System.out.print("BOX: "+box.get(0).id+", "+box.get(1).id+", "+box.get(2).id+", "+box.get(3).id+" ");
	        }
	        System.out.println();

	         */
	    return listOfBoxes.size();
	}

	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other.getClass().getName() != "State") return false;
		State o = (State) other;
		if(o.getBotTurn()==botsTurn && o.getScoreTotal()==(score2-score1) && o.getBoard()==state) return true;
		return false;
	}
}

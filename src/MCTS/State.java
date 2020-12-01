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
	private static ArrayList<int[][]> boards = new ArrayList<>();
	
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

	
	public ELine difference(State O) {
		ArrayList<ELine> otherLines = O.getAvailLines();
		
		for(ELine l: inputAvailLines) {
			if(!otherLines.contains(l)) return l;
		}
		return null;
	}
	
	public int[][] getBoard(){return this.state;}
	
	public int getScore1() {return score1;}

	public int getScore2() {return score2;}
	
	public boolean getBotTurn() {return botsTurn;}
	
	public int getScoreTotal() {return (score2-score1);}
	
	public ArrayList<ELine> getAvailLines(){return this.inputAvailLines;}
	
	public static ArrayList<State> getStates(State state) {
		ArrayList<State> statesNew = new ArrayList<State>();
		states = new ArrayList<>();
		playerScores= new ArrayList<>();
		otherPlayerScores= new ArrayList<>();
		boards = new ArrayList<>();
		
		/*
		int[][] matrix=state.getBoard();
		// creates a copy of the matrix to create the list of edges
        int[][] matrixCopy = new int[matrix.length][matrix[0].length];
        for(int r=0;r<matrix.length;r++){
            for(int q=0;q<matrix[0].length;q++){
                // If a space in the matrix == 1, then it creates and edge and adds it to the edge list,
                // Then it then sets it so the inverse isn't added
                // e.g it adds the edge 0--1 but not the inverse 1--0
                if(matrixCopy[r][q]!=3) {
                    matrixCopy[r][q] = matrix[r][q];
                }
                if(matrixCopy[r][q]==1){
                    Edge ne = new Edge(vertexList.get(r), vertexList.get(q));
                    ne.createLine();
                    availableLines.add(ne.line);
                    edgeList.add(ne);
                    matrixCopy[q][r]=3;
                }
            }
        }*/
		
		possibleStatesAndScores(state.getAvailLines(), state.getBoard(),state.getScore1(), state.getScore2(), state.getBotTurn());
		boolean turn=state.getBotTurn();
		for(int i=0; i<states.size();i++) {
			if(turn) {
				if(state.getScore2()==otherPlayerScores.get(i)) turn=!turn;
			}
			else {
				if(state.getScore1()==playerScores.get(i)) turn=!turn;
			}
			
			statesNew.add(new State(boards.get(i), playerScores.get(i), otherPlayerScores.get(i), turn, states.get(i)));
		}
		return statesNew;
	}
	
	private static void possibleStatesAndScores(ArrayList<ELine> inputAvailLines, int[][] inputMatrix,int inputPlayerScore, int inputOtherPlayerScore, boolean botsTurn){
	    for(int a=0;a<inputAvailLines.size();a++){
	        possUtil(a,inputAvailLines,botsTurn,inputMatrix,inputPlayerScore,inputOtherPlayerScore);
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
	        boards.add(matrix);
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
		if(o.getBotTurn()==botsTurn && o.getScoreTotal()==(score2-score1) && o.getBoard()==state && o.getAvailLines()== this.inputAvailLines) return true;
		return false;
	}
}

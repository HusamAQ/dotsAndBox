package MCTS;
import java.util.ArrayList;

import game.ELine;

public class MCTSTree {
    private MCTSNode root;
    private ArrayList<MCTSNode> treeNodes = new ArrayList<MCTSNode>();
    
    private int runs=100;

    /***Constructor method that creates a tree from the first turn of a bot.
     * 
     * @param matrix The board 
     * @param score1 current score of player 1 (opponent)
     * @param score2 current score of bot
     * @param botsTurn indicate if its the bots turn
     * @param inputAvailLines a list that holds all the available lines that can be drawn.
     */
    public MCTSTree(int[][] matrix, int score1, int score2, boolean botsTurn, ArrayList<ELine> inputAvailLines){
        root = new MCTSNode(new State(matrix, score1, score2, botsTurn, inputAvailLines));
        treeNodes.add(root);
        generateChildren(root);
    }

    /***
     * Method that generates children for a certain node.
     * 
     * @param parent the node we want to generate children for
     */
    private void generateChildren(MCTSNode parent) {
    	ArrayList<State> chil= State.getStates(parent.getState());
    	for(int i=0; i<chil.size();i++) {
    		MCTSNode baby=new MCTSNode(chil.get(i));
    		parent.addChild(baby);
    		treeNodes.add(baby);
    	}
    }
    
    public ELine getNextMove(int[][] matrix, int score1, int score2, boolean botsTurn, ArrayList<ELine> inputAvailLines) {
    	MCTSNode O = new MCTSNode(new State(matrix, score1, score2, botsTurn, inputAvailLines));
    	root= treeNodes.get(inTree(O));
    	
    	simulateGames();
    	MCTSNode next = getBestMove();
    	
    	ELine nextEdge = root.getState().difference(next.getState());
    	
    	return nextEdge;
    }
    
    /***
     * This method return the node with the highest value
     * @return MCTSNode next, with the node that represents our next best possible state
     */
    private MCTSNode getBestMove() {
    	double best=0;
    	int n=numberOfMoves()*runs;
    	MCTSNode next=null;
    	for(int i=0; i<root.getChildren().size(); i++) {
    		double c=root.getChildren().get(i).getValue(n);
    		if(c > best) best=c; next=root.getChildren().get(i);
    	}
    	
    	return next;
    }
    
    /***
     *Method to simulate (runs) number of possible games.
     */
    public void simulateGames() {
    	for(int i=0; i<runs; i++) {
    		selection();
    	}
    }
    
    /***
     * Method that simulates a game by choosing random moves
     */
    private void selection() {
    	MCTSNode currentNode = root;
    	
    	while(currentNode.hasChildren()) {
    		if(currentNode.getChildren().size()==0) {
    			//Generating the tree(expansion)
    			generateChildren(currentNode);
    		}
    		currentNode = currentNode.getChildren().get(((int) Math.random() * (root.getChildren().size())));
    	}
    	//Here we have reached an end game and want to know who won the game
    	boolean win = simulation(currentNode);
    	backTrack(currentNode, win);
    }
    
    /***
     * Method that returns who is winning in a specific game.
     * @param currentNode the node we want to find out who is winning at
     * @return true if bot is winning, false otherwise
     */
    private boolean simulation(MCTSNode currentNode) {
    	if(currentNode.getState().getScoreTotal()>0) return true;
    	return false;
    }
    
    /***
     * Method to backtrack through our expansion and update their values.
     * 
     * @param currentNode
     * @param win
     */
    private void backTrack(MCTSNode currentNode, boolean win) {
    	while(currentNode != root) {
    		currentNode.update(win);
    		currentNode=currentNode.getParent();
    	}
    }
    
    /***
     * Method that returns the number of moves the bot has played so far in this game
     * 
     * @return
     */
    private int numberOfMoves() {
    	int x=0;
    	MCTSNode n = root;
    	while(n.getParent()!=null) {
    		if(n.getState().getBotTurn()) x++;
    	}
    	return x;
    }
    
    private int inTree(MCTSNode O){
        for(int i =0; i < treeNodes.size();i++){
            if(treeNodes.get(i).equals(O)){
                return i;
            }
        }
        return -1;
    }
    
    private void removeExcept(MCTSNode past) {
    	if(!past.hasChildren()) treeNodes.remove(inTree(past));
    	for(MCTSNode p: past.getChildren()) if(p==root) removeExcept(past);
    }

}

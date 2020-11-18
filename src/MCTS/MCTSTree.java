package MCTS;
import java.util.ArrayList;

public class MCTSTree {
    private MCTSNode root;
    private ArrayList<MCTSNode> treeNodes = new ArrayList<MCTSNode>();

    public MCTSTree(int[][] matrix, int score1, int score2, boolean botsTurn){
        root = new MCTSNode(new State(matrix, score1, score2, botsTurn));
        treeNodes.add(root);
        generateChildren(root);
    }

    //TODO this method should generate all the children of this node.
    private void generateChildren(MCTSNode parent) {}

    private MCTSNode inTree(MCTSNode O){
        for(int i =0; i < treeNodes.size();i++){
            if(treeNodes.get(i).equals(O)){
                return treeNodes.get(i);
            }
        }
        return null;
    }

}

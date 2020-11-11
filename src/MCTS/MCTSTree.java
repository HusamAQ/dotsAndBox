package MCTS;
import java.util.ArrayList;

public class MCTSTree {
    private MCTSNode root;
    private ArrayList<MCTSNode> treeNodes = new ArrayList<MCTSNode>();

    public MCTSTree(int[][] matrix, int score){
        root = new MCTSNode(matrix,score);
        treeNodes.add(root);
        generateChildren(root);
    }

    //TODO this method should generate all the children of this node.
    private void generateChildren(MCTSNode parent) {}

    private MCTSNode inTree(int[][] matrix, int score){
        for(int i =0; i < treeNodes.size();i++){
            if(treeNodes.get(i).equalsState(matrix,score)){
                return treeNodes.get(i);
            }
        }
        return null;
    }

}

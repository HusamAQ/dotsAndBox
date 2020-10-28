package neural;

import game.ELine;
import game.GameBoard;
import game.Graph;
import game.Vertex;

import javax.sound.sampled.Line;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Base {
    int numOfEdges;
    static double[][] weights1; // horizontal index = Neuron on the right, vertical index = Neuron on the left
    static double[][] weights2;
    static double[][] dWeights1;
    static double[][] dWeights2;
    int[] input; // 1 if there's an an available edge, 0 if not.
    static double[] hidden;
    static double[] output;
    double[] costArray;
    double cost;
    boolean init=false;

    public static void main(String[] args) throws IOException, InterruptedException {
        GameBoard f = new GameBoard(3,3);
        Graph.neural.turn();
    }
    static int turn=0;
    public void turn() throws IOException {
        fillInput();
        feedForward();
        int edge = checkValid(getMax());
        while(edge==-1){
            System.out.println("COST: "+getCost(0));
            backPropagate();
            feedForward();
           // System.out.println(toString());
            int chosen = getMax();
            System.out.println("Chosen: "+chosen);
            edge=checkValid(chosen);
            System.out.println("EDGE: "+edge);
            if(edge==-1){
                System.out.println(chosen+" = Input: "+input[chosen]+" Hidden: "+hidden[chosen]+" Output: "+output[chosen]);
            }
        }
       // System.out.println("Working edge: "+edge);
        //placeEdge(edge);
    }
    public void fillInput(){
        for(int n=0;n<input.length;n++){
            input[n]=1;
            if(Graph.getEdgeList().get(n).getEline().isActivated()){
                input[n]=0;
            }
        }
    }
    /*
    public void placeEdge(int chosen) throws IOException {
        boolean stop=false;
        Graph.getAvailableLines().get(chosen).setActivated(true);
        Graph.getAvailableLines().get(chosen).setBackground(Color.BLACK);
        Graph.getAvailableLines().get(chosen).repaint();
        Graph.matrix[Graph.getAvailableLines().get(chosen).vertices.get(0).getID()][Graph.getAvailableLines().get(chosen).vertices.get(1).getID()] = 2;
        Graph.matrix[Graph.getAvailableLines().get(chosen).vertices.get(1).getID()][Graph.getAvailableLines().get(chosen).vertices.get(0).getID()] = 2;
        ArrayList<ArrayList<Vertex>> boxes = Graph.getAvailableLines().get(chosen).checkBox();
        if (boxes != null) {
            for (ArrayList<Vertex> box : boxes) {
                Graph.getAvailableLines().get(chosen).checkMatching(box);
                if (Graph.getPlayer1Turn()) {
                    Graph.setPlayer1Score(Graph.getPlayer1Score()+1);
                    Graph.getScore1().setScore();
                } else {
                    Graph.setPlayer2Score(Graph.getPlayer2Score()+1);
                    Graph.getScore2().setScore();
                }
            }
            if(Graph.getAvailableLines().get(chosen).checkFinished()){
                Graph.getScreen().toggle();
                stop=true;
            }
            // if it completes a box, it gets to go again
            if(Graph.neuPlayer1==Graph.getPlayer1Turn()&&!stop){
                stop=true;
                // removes the edge from availableLines
                if(Graph.getAvailableLines().size()>0&&!(Graph.getAvailableLines().size()==1&&chosen>0)) {
                    Graph.getAvailableLines().remove(chosen);
                }
                turn();
            }
        } else {
            if (Graph.getPlayer1Turn()) {
                Graph.setPlayer1Turn(false);
            } else {
                Graph.setPlayer1Turn(true);
            }
        }
        // removes the edge from availableLines so long as it hasn't already removed the edge in the same method call.
        if(Graph.getAvailableLines().size()>0&&!(Graph.getAvailableLines().size()-1<chosen)&&!stop) {
            Graph.getAvailableLines().remove(chosen);
        }
    }
     */
    public int checkValid(int output){
        int g=-1;
        for(ELine r:Graph.getAvailableLines()){
            if(r.getEdgeListIndex()==output){
                g=r.getEdgeListIndex();
            }
        }
        return g;
    }
    public void init(){
        if(!init) {
            numOfEdges = Graph.getEdgeList().size();
            input = new int[numOfEdges];
            hidden = new double[numOfEdges];
            output = new double[numOfEdges];
            weights1 = new double[numOfEdges][numOfEdges];
            weights2 = new double[numOfEdges][numOfEdges];
            dWeights1 = new double[numOfEdges][numOfEdges];
            dWeights2 = new double[numOfEdges][numOfEdges];
            for (int a = 0; a < weights1.length; a++) {
                for (int b = 0; b < weights1[0].length; b++) {
                    weights1[a][b] = 8 * Math.random() - 4;
                    weights2[a][b] = 8 * Math.random() - 4;
                }
            }
        }
        init=true;
        fillInput();
    }
    public double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }
    // each neuron = all the weights in the previous layer * the neuron in previous layer
    public void feedForward(){
        turn++;
        for(int p=0;p<hidden.length;p++){
            int sum=0;
            for(int y=0;y<weights1[p].length;y++){
                sum+=weights1[p][y]*input[y];
            }
            hidden[p]=sigmoid(sum);
        }
        for(int p=0;p<output.length;p++){
            int sum=0;
            for(int y=0;y<weights2[p].length;y++){
                sum+=weights2[p][y]*hidden[y];
            }
            output[p]=sigmoid(sum);
        }
    }
    public int getMax(){
        double max = Double.MIN_VALUE;
        int index=-1;
        for(int h=0;h<output.length;h++){
            if(output[h]>max){
                max=output[h];
                index=h;
            }
        }
        return index;
    }
    //public double r(int a){
    //     }
    public double getCost(int a){
        // if a=0, the network chose a illegal edge. If a=1, the network lost a game.
        costArray=new double[numOfEdges];
        if(a==0){
            for(int l=0;l<output.length;l++){
                boolean available=false;
                for(ELine r:Graph.getAvailableLines()){
                    if(r.getEdgeListIndex()==l){
                        available=true;
                    }
                }
                if(available==false){
                    costArray[l]=Math.pow(output[l],2);
                }else{
                    costArray[l]=-1;
                }
            }
        }
        if(a==1){
            for(int l=0;l<output.length;l++){
                costArray[l]=Math.pow(output[l],2);
            }
        }

        System.out.print("Cost Array: ");
        for(double l:costArray){
            System.out.print(l+", ");
        }
        System.out.println();
        cost=0;
        for(double q:costArray){
            if(q!=-1) {
                cost += q;
            }
        }
        return cost;
    }
    public void backPropagate(){
        for(int e=0;e<dWeights2.length;e++){
            for(int q=0;q<dWeights2[0].length;q++){
                if(costArray[e]!=-1) {
                    dWeights2[e][q] = 2 * (output[e] - costArray[e]) * ((hidden[e] * weights2[e][q]) * (1 - (hidden[e] * weights2[e][q]))) * hidden[e];
                    /*
                    System.out.println("2 * ("+output[e]+" - "+costArray[e]+") * (("+hidden[e]+" * "+weights2[e][q]+") * (1 - "+hidden[e]+" * "+weights2[e][q]+")) *"+hidden[e]);
                    System.out.println("dWeights2["+e+"]["+q+"] = "+dWeights2[e][q]);
                     */
                }
            }
        }
        for(int e=0;e<dWeights1.length;e++){
            for(int q=0;q<dWeights1[0].length;q++){
                if(costArray[e]!=-1) {
                    dWeights1[e][q] = 2 * (output[e] - costArray[e]) * ((input[e] * weights1[e][q]) * (1 - (input[e] * weights1[e][q]))) * input[e];
                    /*
                    System.out.println("2 * ("+output[e]+" - "+costArray[e]+") * (("+input[e]+" * "+weights1[e][q]+") * (1 - "+input[e]+" * "+weights1[e][q]+")) *"+input[e]);
                    System.out.println("dWeights1["+e+"]["+q+"] = "+dWeights1[e][q]);
                     */
                }
            }
        }
        System.out.println("DWeights: 1: ");
        for(int a=0;a<dWeights1.length;a++){
            for(int b=0;b<dWeights1[0].length;b++) {
                System.out.print(dWeights1[a][b] + " | ");
            }
            System.out.println();
        }
        System.out.println("DWeights: 2: ");
        for(int a=0;a<dWeights2.length;a++){
            for(int b=0;b<dWeights2[0].length;b++) {
                System.out.print(dWeights2[a][b] + " | ");
            }
            System.out.println();
        }


        weights2 = matrixAddition(weights2,dWeights2);
        weights1 = matrixAddition(weights1,dWeights1);
        cost=0;
    }
    public double[][] matrixAddition(double[][] a, double[][] b){
        for(int w=0;w<a.length;w++){
            for(int d=0;d<a[0].length;d++){
                a[w][d]+=b[w][d];
            }
        }
        return a;
    }
    public String toString(){
        String toReturn="Values: "+'\n';
        for(int a=0;a<input.length;a++){
            toReturn=toReturn+input[a]+" | "+hidden[a]+" | "+output[a]+" |"+'\n';
        }
        toReturn=toReturn+"Weights: 1: "+'\n';
        for(int a=0;a<weights1.length;a++){
            for(int b=0;b<weights1[0].length;b++) {
                toReturn=toReturn+ weights1[a][b] + " | ";
            }
            toReturn=toReturn+'\n';
        }
        toReturn=toReturn+"Weights: 2: "+'\n';
        for(int a=0;a<weights2.length;a++){
            for(int b=0;b<weights2[0].length;b++) {
                toReturn=toReturn+ weights2[a][b] + " | ";
            }
            toReturn=toReturn+'\n';
        }
        toReturn=toReturn+"Cost: "+cost;
        return toReturn;
    }
}

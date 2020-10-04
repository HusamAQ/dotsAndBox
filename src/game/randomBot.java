package game;

import game.ELine;
import game.Graph;
import game.Vertex;
import game.scoreBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static game.Graph.*;


public class randomBot {
    // places edges randomly except will always complete a box and won't set up boxes for the other player
    public randomBot(){}
    // places the edge
    public void placeRandomEdge() {
        boolean stop=false;
        // chosen is the index in availableLines of the edge it will choose to place
        int chosen;
        // checks to see if it can create a box
        int c=checkForBox();
        if(c!=-1){
            // if it can, it sets that to the index
            chosen=c;
        }else{
            // if not, selects a random edge that doesn't set up a box for the other player.
            // if that's not possible it just selects a random edge
            chosen= checkFor3s(availableLines);
        }
        // effectively mirrors the actionListener in ELine.
        availableLines.get(chosen).activated=true;
        availableLines.get(chosen).setBackground(Color.BLACK);
        availableLines.get(chosen).repaint();
        Graph.matrix[availableLines.get(chosen).vertices.get(0).getID()][availableLines.get(chosen).vertices.get(1).getID()] = 2;
        Graph.matrix[availableLines.get(chosen).vertices.get(1).getID()][availableLines.get(chosen).vertices.get(0).getID()] = 2;
        ArrayList<ArrayList<Vertex>> boxes = availableLines.get(chosen).checkBox();
        if (boxes != null) {
            for (ArrayList<Vertex> box : boxes) {
                availableLines.get(chosen).checkMatching(box);
                if (player1Turn) {
                    Graph.player1Score++;
                    Graph.score1.setScore();
                    } else {
                        Graph.player2Score++;
                        Graph.score2.setScore();
                    }
                }
                if(availableLines.get(chosen).checkFinished()){
                    Graph.screen.toggle();
                    stop=true;
                }
                // if it completes a box, it gets to go again
                if(randBotPlayer1==player1Turn&&!stop){
                    stop=true;
                    // removes the edge from availableLines
                    if(availableLines.size()>0&&!(availableLines.size()==1&&chosen>0)) {
                        availableLines.remove(chosen);
                    }
                    randBot.placeRandomEdge();
                }
            } else {
                if (player1Turn) {
                    player1Turn = false;
                } else {
                    player1Turn = true;
                }
            }
        // removes the edge from availableLines so long as it hasn't already removed the edge in the same method call.
        // e.g makes sure that repeated placeRandomEdge() calls won't remove double the edges when it completes a series of boxes
        if(availableLines.size()>0&&!(availableLines.size()-1<chosen)&&!stop) {
            availableLines.remove(chosen);
        }
    }
    // checks to see if it can create a box
    // returns the edge that creates the box's index in availableLines
    public int checkForBox(){
        // for each box in counterBoxes
        for(scoreBox box: counterBoxes){
            int a = matrix[box.getVertices().get(0).getID()][box.getVertices().get(1).getID()];
            int b = matrix[box.getVertices().get(0).getID()][box.getVertices().get(2).getID()];
            int c = matrix[box.getVertices().get(1).getID()][box.getVertices().get(3).getID()];
            int d = matrix[box.getVertices().get(2).getID()][box.getVertices().get(3).getID()];
            // if each int adds up to 7, there must be 3 lines in a box. A line = 1 when available and = 2 when placed.
            // as 3 completed lines is 3*2=6, +1 for the remaining line == 7
            if(a+b+c+d==7){
                // checks to see which line is the available one, e.g == 1
                if(a==1){
                    return findMatch(box.getVertices().get(0).getID(),box.getVertices().get(1).getID());
                }
                if(b==1){
                    return findMatch(box.getVertices().get(0).getID(),box.getVertices().get(2).getID());
                }
                if(c==1){
                    return findMatch(box.getVertices().get(1).getID(),box.getVertices().get(3).getID());
                }
                if(d==1){
                    return findMatch(box.getVertices().get(2).getID(),box.getVertices().get(3).getID());
                }
            }
        }
        return -1;
    }
    // finds the index in available lines which matches the input vertex id's
    // e.g you input 5 and 4, it returns the index of the edge 4--5.
    public int findMatch(int a, int b){
        for(int p=availableLines.size()-1;p>=0;p--){
            if(availableLines.get(p).vertices.get(0).getID()==a&&availableLines.get(p).vertices.get(1).getID()==b){
                return p;
            }
        }
        for(int p=availableLines.size()-1;p>=0;p--){
            if(availableLines.get(p).vertices.get(0).getID()==b&&availableLines.get(p).vertices.get(1).getID()==a){
                return p;
            }
        }
        for(ELine l: availableLines){
            System.out.println(l.vertices.get(0).getID()+" -- "+l.vertices.get(1).getID());
        }
        return -1;
    }
    // removes every edge which sets up a box for the other player
    public int checkFor3s(ArrayList<ELine> avail){
        ArrayList<Integer> av = new ArrayList<>();
        // goes through each availableLine
        for(int q=0;q<avail.size();q++){
            ELine edge = avail.get(q);
            boolean noBox=true;
            // if the edge is vertical, it can only have a box to the right and left of it.
            if(!edge.horizontal){
                int leftBox=0;
                int rightBox=0;
                if(edge.vertices.get(0).getRightVertex()!=null){
                    rightBox = matrix[edge.vertices.get(0).getID()][edge.vertices.get(0).getID()+1]+matrix[edge.vertices.get(0).getID()+1][edge.vertices.get(1).getID()+1]+matrix[edge.vertices.get(1).getID()][edge.vertices.get(1).getID()+1];
                }
                if(edge.vertices.get(0).getLeftVertex()!=null){
                    leftBox=matrix[edge.vertices.get(0).getID()][edge.vertices.get(0).getID()-1]+matrix[edge.vertices.get(0).getID()-1][edge.vertices.get(1).getID()-1]+matrix[edge.vertices.get(1).getID()][edge.vertices.get(1).getID()-1];
                }
                // it adds up the int value of each edge in each box in the adjacency matrix
                // if it == 5, then placing another edge there will set up a box for the other player
                // it checks the 3 edges around the chosen edge, not the chosen edge itself
                // so if the 3 edge's sum == 5, then they must be 2+2+1 = 5
                // so there's 2 lines in the box, so putting another line there sets up the other player
                if(leftBox==5||rightBox==5){
                    noBox=false;
                }
            }else{
                // does the same but for horizontal edges
                int downBox=0;
                int upBox=0;
                if(edge.vertices.get(0).getDownVertex()!=null){
                    downBox=matrix[edge.vertices.get(0).getID()][edge.vertices.get(0).getID()+ width]+matrix[edge.vertices.get(0).getID()+width][edge.vertices.get(1).getID()+width]+matrix[edge.vertices.get(1).getID()][edge.vertices.get(1).getID()+width];
                }
                if(edge.vertices.get(0).getUpVertex()!=null){
                    upBox=matrix[edge.vertices.get(0).getID()][edge.vertices.get(0).getID()-width]+matrix[edge.vertices.get(0).getID()-width][edge.vertices.get(1).getID()-width]+matrix[edge.vertices.get(1).getID()][edge.vertices.get(1).getID()-width];
                }
                if(upBox==5||downBox==5){
                    noBox=false;
                }
            }
            if(noBox){
                // if the line doesn't create a box it adds the index from availableLines to a new arrayList, av
                av.add(q);
            }
        }
        if(av.size()>0){
            // if there are edges in av, it returns a random entry in av
            // all entries in av are indexes from availableLine
            int ret = av.get((int)(Math.random()*av.size()));
            return ret;
        }else{
            // if not it just returns a random index from availableLine
            return (int)(Math.random()*avail.size());
        }
    }
}

package game;

import game.ELine;
import game.Graph;
import game.Vertex;
import game.scoreBox;

import java.awt.*;
import java.util.ArrayList;

import static game.Graph.*;


public class randomBot {
    public randomBot(){}
    public void placeRandomEdge() {
        boolean stop=false;
        int chosen = (int)(Math.random()*availableLines.size());
        int c=checkForBox();
        if(c!=-1){
            chosen=c;
        }
        availableLines.get(chosen).activated=true;
        availableLines.get(chosen).setBackground(Color.BLACK);
        availableLines.get(chosen).repaint();
        Graph.matrix[availableLines.get(chosen).vertices.get(0).id][availableLines.get(chosen).vertices.get(1).id] = 2;
        Graph.matrix[availableLines.get(chosen).vertices.get(1).id][availableLines.get(chosen).vertices.get(0).id] = 2;
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
                if(randBotPlayer1==player1Turn&&!stop){
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
        if(availableLines.size()>0&&!(availableLines.size()-1<chosen)) {
            availableLines.remove(chosen);
        }
    }
    public int checkForBox(){
        for(scoreBox box: counterBoxes){
            int a = matrix[box.vertices.get(0).id][box.vertices.get(1).id];
            int b = matrix[box.vertices.get(0).id][box.vertices.get(2).id];
            int c = matrix[box.vertices.get(1).id][box.vertices.get(3).id];
            int d = matrix[box.vertices.get(2).id][box.vertices.get(3).id];
            if(a+b+c+d==7){
                if(a==1){
                    return findMatch(box.vertices.get(0).id,box.vertices.get(1).id);
                }
                if(b==1){
                    return findMatch(box.vertices.get(0).id,box.vertices.get(2).id);
                }
                if(c==1){
                    return findMatch(box.vertices.get(1).id,box.vertices.get(3).id);
                }
                if(d==1){
                    return findMatch(box.vertices.get(2).id,box.vertices.get(3).id);
                }
            }
        }
        return -1;
    }
    public int findMatch(int a, int b){
        for(int p=availableLines.size()-1;p>=0;p--){
            if(availableLines.get(p).vertices.get(0).id==a&&availableLines.get(p).vertices.get(1).id==b){
                return p;
            }
        }
        for(int p=availableLines.size()-1;p>=0;p--){
            if(availableLines.get(p).vertices.get(0).id==b&&availableLines.get(p).vertices.get(1).id==a){
                return p;
            }
        }
        System.out.println("ERROR");
        return -1;
    }
}

package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

import static game.Graph.*;


public class ELine extends JLabel {
    // The graphical display of the edges

    // Whether the line has been clicked or not
    public boolean activated = false;
    // the bottom left x and y coordinates of the line
    int startX;
    int startY;
    // the vertices
    public ArrayList<Vertex> vertices;
    // whether it's horizontal
    boolean horizontal;
    public ELine(int w,int h,int x,int y,ArrayList<Vertex> v){
        vertices=v;
        if(vertices.get(1).getID()-vertices.get(0).getID()==1){
            horizontal=true;
        }else{
            horizontal=false;
        }
        startX=x;
        startY=y;
        // the line starts off invisible, e.g White
        setBackground(Color.WHITE);
        setBounds(x,y,w,h);
        setOpaque(true);
        // the mouseListener
        addMouseListener(new MouseAdapter() {
            // when the player hovers over a line it displays it in their colour
            @Override
            public void mouseEntered(MouseEvent e){
                if(!activated) {
                    if (player1Turn) {
                        setBackground(Color.RED);
                    } else {
                        setBackground(Color.BLUE);
                    }
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(!activated) {
                    setBackground(Color.WHITE);
                }
            }
            // when clicked
            @Override
            public void mousePressed(MouseEvent e) {
                //  if the line has not been activated before
                if(!activated) {
                    activated=true;
                    // remove the ELine from availableLines
                    for(int p=availableLines.size()-1;p>=0;p--){
                        if(availableLines.get(p).vertices.get(0).getID()==vertices.get(0).getID()&&availableLines.get(p).vertices.get(1).getID()==vertices.get(1).getID()){
                            availableLines.remove(p);
                        }
                    }
                    // make it black
                    setBackground(Color.BLACK);
                    repaint();
                    // set the adjacency matrix to 2, 2==is a line, 1==is a possible line
                    Graph.matrix[vertices.get(0).getID()][vertices.get(1).getID()] = 2;
                    Graph.matrix[vertices.get(1).getID()][vertices.get(0).getID()] = 2;
                    // gets an arrayList of each box the ELine creates. The box is an arrayList of 4 vertices.
                    ArrayList<ArrayList<Vertex>> boxes = checkBox();
                    if (boxes != null) {
                        for (ArrayList<Vertex> box : boxes) {
                            // looks through the counterBoxes arrayList and sets the matching one visible.
                            checkMatching(box);
                            // updates the score board
                            if (player1Turn) {
                                player1Score++;
                                Graph.score1.setScore();
                            } else {
                                player2Score++;
                                Graph.score2.setScore();
                            }
                        }
                        // if every counterBox has been activated, the game is over
                        if(checkFinished()){
                            screen.toggle();
                        }
                        // if it's the random box's turn and it creates a box, it will have another turn.
                        if(activateRandom&&randBotPlayer1==player1Turn){
                            randBot.placeRandomEdge();
                        }
                    } else {
                        // switches turn. If randomBot is active switches to their turn.
                        if (player1Turn) {
                            player1Turn = false;
                            if(!randBotPlayer1&&activateRandom){
                                randBot.placeRandomEdge();
                            }
                        } else {
                            player1Turn = true;
                            if(randBotPlayer1&&activateRandom){
                                randBot.placeRandomEdge();
                            }
                        }
                    }
                }
            }
        });
    }
    // if every scoreBox is active, the game is over
    public boolean checkFinished(){
        for(scoreBox box:counterBoxes){
            if(!box.activated){
                return false;
            }
        }
        return true;
    }
    // checks to find the matching box in counterBoxes through their average x and y coordinates, then displays it.
    public void checkMatching(ArrayList<Vertex> box){
        int avgX=0;
        int avgY=0;
        for(Vertex v:box){
            avgX+=v.getWidth();
            avgY+=v.getHeight();
        }
        avgX=avgX/4;
        avgY=avgY/4;
        for(scoreBox sc: counterBoxes){
            if(sc.avgX==avgX&&sc.avgY==avgY){
                sc.setText();
            }
        }
    }
    // checks whether an edge creates a box, through the adjacency matrix
    public ArrayList<ArrayList<Vertex>> checkBox(){
        ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
        if(horizontal){
            if(vertices.get(0).getUpVertex()!=null){
                if(Graph.matrix[vertices.get(0).getID()][vertices.get(0).getUpVertex().getID()]==2&&Graph.matrix[vertices.get(1).getID()][vertices.get(1).getUpVertex().getID()]==2&&Graph.matrix[vertices.get(0).getUpVertex().getID()][vertices.get(1).getUpVertex().getID()]==2){
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertices.get(0));
                    box.add(vertices.get(1));
                    box.add(vertices.get(0).getUpVertex());
                    box.add(vertices.get(1).getUpVertex());
                    listOfBoxes.add(box);
                }
            }
            if(vertices.get(0).getDownVertex()!=null){
                if(Graph.matrix[vertices.get(0).getID()][vertices.get(0).getDownVertex().getID()]==2&&Graph.matrix[vertices.get(1).getID()][vertices.get(1).getDownVertex().getID()]==2&&Graph.matrix[vertices.get(0).getDownVertex().getID()][vertices.get(1).getDownVertex().getID()]==2){
                    ArrayList<Vertex> box2 = new ArrayList<>();
                    box2.add(vertices.get(0));
                    box2.add(vertices.get(1));
                    box2.add(vertices.get(0).getDownVertex());
                    box2.add(vertices.get(1).getDownVertex());
                    listOfBoxes.add(box2);
                }
            }
        }else{
            if(vertices.get(0).getRightVertex()!=null){
                if(Graph.matrix[vertices.get(0).getID()][vertices.get(0).getRightVertex().getID()]==2&&Graph.matrix[vertices.get(1).getID()][vertices.get(1).getRightVertex().getID()]==2&&Graph.matrix[vertices.get(0).getRightVertex().getID()][vertices.get(1).getRightVertex().getID()]==2){
                    ArrayList<Vertex> box3 = new ArrayList<>();
                    box3.add(vertices.get(0));
                    box3.add(vertices.get(1));
                    box3.add(vertices.get(0).getRightVertex());
                    box3.add(vertices.get(1).getRightVertex());
                    listOfBoxes.add(box3);
                }
            }
            if(vertices.get(0).getLeftVertex()!=null){
                if(Graph.matrix[vertices.get(0).getID()][vertices.get(0).getLeftVertex().getID()]==2&&Graph.matrix[vertices.get(1).getID()][vertices.get(1).getLeftVertex().getID()]==2&&Graph.matrix[vertices.get(0).getLeftVertex().getID()][vertices.get(1).getLeftVertex().getID()]==2){
                    ArrayList<Vertex> box4 = new ArrayList<>();
                    box4.add(vertices.get(0));
                    box4.add(vertices.get(1));
                    box4.add(vertices.get(0).getLeftVertex());
                    box4.add(vertices.get(1).getLeftVertex());
                    listOfBoxes.add(box4);
                }
            }
        }
        // if it creates no boxes, return null.
        if(listOfBoxes.isEmpty()){
            return null;
        }
        return listOfBoxes;
    }
}

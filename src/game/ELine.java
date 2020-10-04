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
	private boolean activated = false;
    // the bottom left x and y coordinates of the line
	private int startX;
	private int startY;
    // the vertices
    public ArrayList<Vertex> vertices;
    // whether it's horizontal
    private boolean horizontal;
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
                    if (Graph.getPlayer1Turn()) {
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
                    for(int p=Graph.getAvailableLines().size()-1;p>=0;p--){
                        if(Graph.getAvailableLines().get(p).vertices.get(0).getID()==vertices.get(0).getID()&&Graph.getAvailableLines().get(p).vertices.get(1).getID()==vertices.get(1).getID()){
                        	Graph.getAvailableLines().remove(p);
                        }
                    }
                    // make it black
                    setBackground(Color.BLACK);
                    repaint();
                    // set the adjacency matrix to 2, 2==is a line, 1==is a possible line
                    Graph.getMatrix()[vertices.get(0).getID()][vertices.get(1).getID()] = 2;
                    Graph.getMatrix()[vertices.get(1).getID()][vertices.get(0).getID()] = 2;
                    // gets an arrayList of each box the ELine creates. The box is an arrayList of 4 vertices.
                    ArrayList<ArrayList<Vertex>> boxes = checkBox();
                    if (boxes != null) {
                        for (ArrayList<Vertex> box : boxes) {
                            // looks through the counterBoxes arrayList and sets the matching one visible.
                            checkMatching(box);
                            // updates the score board
                            if (Graph.getPlayer1Turn()) {
                                Graph.setPlayer1Score(Graph.getPlayer1Score()+1);
                                Graph.getScore1().setScore();
                            } else {
                            	Graph.setPlayer2Score(Graph.getPlayer2Score()+1);
                                Graph.getScore2().setScore();
                            }
                        }
                        // if every counterBox has been activated, the game is over
                        if(checkFinished()){
                            Graph.getScreen().toggle();
                        }
                        // if it's the random box's turn and it creates a box, it will have another turn.
                        if(Graph.getActivateRandom()&&Graph.getRandBotPlayer1()==Graph.getPlayer1Turn()){
                        	Graph.getRandomBot().placeRandomEdge();
                        }
                    } else {
                        // switches turn. If randomBot is active switches to their turn.
                        if (Graph.getPlayer1Turn()) {
                        	Graph.setPlayer1Turn(false) ;
                            if(!Graph.getRandBotPlayer1()&&Graph.getActivateRandom()){
                            	Graph.getRandomBot().placeRandomEdge();
                            }
                        } else {
                        	Graph.setPlayer1Turn(true);
                            if(Graph.getRandBotPlayer1()&&Graph.getActivateRandom()){
                                Graph.getRandomBot().placeRandomEdge();
                            }
                        }
                    }
                }
            }
        });
    }
    // if every scoreBox is active, the game is over
    public boolean checkFinished(){
        for(scoreBox box: Graph.getCounterBoxes()){
            if(!box.getActivated()){
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
        for(scoreBox sc: Graph.getCounterBoxes()){
            if(sc.getAvgX()==avgX&&sc.getAvgY()==avgY){
                sc.setText();
            }
        }
    }
    // checks whether an edge creates a box, through the adjacency matrix
    public ArrayList<ArrayList<Vertex>> checkBox(){
        ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
        if(horizontal){
            if(vertices.get(0).getUpVertex()!=null){
                if(Graph.getMatrix()[vertices.get(0).getID()][vertices.get(0).getUpVertex().getID()]==2&&Graph.getMatrix()[vertices.get(1).getID()][vertices.get(1).getUpVertex().getID()]==2&&Graph.getMatrix()[vertices.get(0).getUpVertex().getID()][vertices.get(1).getUpVertex().getID()]==2){
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertices.get(0));
                    box.add(vertices.get(1));
                    box.add(vertices.get(0).getUpVertex());
                    box.add(vertices.get(1).getUpVertex());
                    listOfBoxes.add(box);
                }
            }
            if(vertices.get(0).getDownVertex()!=null){
                if(Graph.getMatrix()[vertices.get(0).getID()][vertices.get(0).getDownVertex().getID()]==2&&Graph.getMatrix()[vertices.get(1).getID()][vertices.get(1).getDownVertex().getID()]==2&&Graph.getMatrix()[vertices.get(0).getDownVertex().getID()][vertices.get(1).getDownVertex().getID()]==2){
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
                if(Graph.getMatrix()[vertices.get(0).getID()][vertices.get(0).getRightVertex().getID()]==2&&Graph.getMatrix()[vertices.get(1).getID()][vertices.get(1).getRightVertex().getID()]==2&&Graph.getMatrix()[vertices.get(0).getRightVertex().getID()][vertices.get(1).getRightVertex().getID()]==2){
                    ArrayList<Vertex> box3 = new ArrayList<>();
                    box3.add(vertices.get(0));
                    box3.add(vertices.get(1));
                    box3.add(vertices.get(0).getRightVertex());
                    box3.add(vertices.get(1).getRightVertex());
                    listOfBoxes.add(box3);
                }
            }
            if(vertices.get(0).getLeftVertex()!=null){
                if(Graph.getMatrix()[vertices.get(0).getID()][vertices.get(0).getLeftVertex().getID()]==2&&Graph.getMatrix()[vertices.get(1).getID()][vertices.get(1).getLeftVertex().getID()]==2&&Graph.getMatrix()[vertices.get(0).getLeftVertex().getID()][vertices.get(1).getLeftVertex().getID()]==2){
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

    public void setActivated(boolean b) {
    	activated=b;
    }
    
    public boolean getHorizontal() {
    	return horizontal;
    }
    }

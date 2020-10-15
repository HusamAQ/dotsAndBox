package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static game.Graph.*;

public class ScoreBox extends JLabel {
    // the icons that pop up when a box is completed
    // the average x and y position for each of the vertices.
	private int avgX;
	private int avgY;
	private boolean activated;
	private ArrayList<Vertex> vertices;
    
    public ScoreBox(ArrayList<Vertex> box){
        activated=false;
        // calculates the avgX and avgY
        avgX=0;
        avgY=0;
        vertices=box;
        for(Vertex v:box){
            avgX+=v.getWidth();
            avgY+=v.getHeight();
        }
        avgX=avgX/4;
        avgY=avgY/4;
    }
    
    public int getAvgX() {
    	return avgX;
    }
    
    public int getAvgY() {
    	return avgY;
    }
    
    public boolean getActivated() {
    	return activated;
    }

    public ArrayList<Vertex> getVertices(){
    	return this.vertices;
    }
    
    // displays it.
    public void setText(){
        activated=true;
        this.setBounds(avgX-15, avgY-30, 50, 50);
        this.setFont(new Font("TimesRoman",Font.BOLD,30));
        if(Graph.getPlayer1Turn()){
            this.setForeground(Color.RED);
            // if it's set to initials it displays the first initial of the player name
            if(Graph.getInitials()){
                setText(Character.toString(Graph.getPlayer1Name().charAt(0)));

            }else {
                this.setText(Integer.toString(Graph.getPlayer1Score() + 1));
            }
        }else{
            this.setForeground(Color.BLUE);
            if(Graph.getInitials()){
                setText(Character.toString(Graph.getPlayer2Name().charAt(0)));
            }else {
                this.setText(Integer.toString(Graph.getPlayer2Score() + 1));
            }
        }
    }
}
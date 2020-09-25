package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static game.Graph.*;

public class scoreBox extends JLabel {
    int avgX;
    int avgY;
    boolean activated;
    public ArrayList<Vertex> vertices;
    public scoreBox(ArrayList<Vertex> box){
        activated=false;
        avgX=0;
        avgY=0;
        vertices=box;
        for(Vertex v:box){
            avgX+=v.width;
            avgY+=v.height;
        }
        avgX=avgX/4;
        avgY=avgY/4;
    }
    public void setText(){
        activated=true;
        this.setBounds(avgX-15, avgY-30, 50, 50);
        this.setFont(new Font("TimesRoman",Font.BOLD,30));
        if(player1Turn){
            this.setForeground(Color.RED);
            this.setText(Integer.toString(player1Score+1));
        }else{
            this.setForeground(Color.BLUE);
            this.setText(Integer.toString(player2Score+1));
        }
    }
}

package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static game.Graph.*;

public class scoreBox extends JLabel {
    int avgX;
    int avgY;
    public scoreBox(ArrayList<Vertex> box){
        System.out.println("BOX: "+ box.get(0).id +"; X: "+box.get(0).width+" Y: "+box.get(0).height+" BOX: "+ box.get(1).id +"; X: "+box.get(1).width+" Y: "+box.get(1).height+" BOX: "+ box.get(2).id +"; X: "+box.get(2).width+" Y: "+box.get(2).height+" BOX: "+ box.get(3).id +"; X: "+box.get(3).width+" Y: "+box.get(3).height);
        avgX=0;
        avgY=0;
        for(Vertex v:box){
            avgX+=v.width;
            avgY+=v.height;
        }
        avgX=avgX/4;
        avgY=avgY/4;
        System.out.println("AVGX: "+avgX+" AVGY: "+avgY);
    }
    public void setText(){
        System.out.println("GOOOOOO");
        this.setBounds(avgX-25, avgY-25, 50, 50);
        this.setFont(new Font("TimesRoman",Font.BOLD,50));
        if(player1Turn){
            this.setForeground(Color.RED);
            this.setText(Integer.toString(player1Score+1));
        }else{
            this.setForeground(Color.BLUE);
            this.setText(Integer.toString(player2Score+1));
        }
    }
}

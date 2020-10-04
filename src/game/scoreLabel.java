package game;

import graphics.Paths;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.*;

public class scoreLabel extends JLabel {
    // the score counters

    // if num==1, it's for player 1, if num==2 it's for player 2
    private int num;
    public scoreLabel(int num){
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBackground(Color.LIGHT_GRAY);
        setOpaque(true);
        this.num=num;
        this.setFont(new Font("TimesRoman",Font.PLAIN,30));
        setHorizontalAlignment(SwingConstants.CENTER);
        if(num==1) {
            this.setText(Integer.toString(Graph.getPlayer1Score()));
            this.setForeground(Color.RED);
            this.setBounds(Paths.FRAME_WIDTH / 4, 20, 50, 50);

        }else{
            this.setText(Integer.toString(Graph.getPlayer2Score()));
            this.setForeground(Color.BLUE);
            this.setBounds(3*Paths.FRAME_WIDTH/4, 20,50,50);
        }
    }
    // updates the score
    public void setScore(){
        if(num==1) {
            this.setText(Integer.toString(Graph.getPlayer1Score()));
        }else{
            this.setText(Integer.toString(Graph.getPlayer2Score()));
        }
    }
}

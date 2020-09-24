package game;

import graphics.Paths;

import javax.swing.*;

import java.awt.*;

import static game.Graph.player1Score;
import static game.Graph.player2Score;

public class scoreLabel extends JLabel {
    int num;
    public scoreLabel(int num){
        this.num=num;
        this.setFont(new Font("TimesRoman",Font.PLAIN,30));
        if(num==1) {
            this.setText(Integer.toString(player1Score));
            this.setForeground(Color.RED);
            this.setBounds(Paths.FRAME_WIDTH / 4, 20, 100, 100);

        }else{
            this.setText(Integer.toString(player2Score));
            this.setForeground(Color.BLUE);
            this.setBounds(3*Paths.FRAME_WIDTH/4, 20,50,50);
        }
    }
    public void setScore(){
        if(num==1) {
            this.setText(Integer.toString(player1Score));
        }else{
            this.setText(Integer.toString(player2Score));
        }
    }
}

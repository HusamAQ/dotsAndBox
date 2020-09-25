package game;

import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Graph.player1Score;
import static game.Graph.player2Score;

public class gameOver{
    JFrame frame;
    JFrame next;
    JPanel panel;

    public gameOver(JFrame frame){
        this.frame=frame;
    }
    public void toggle(){
        frame.setVisible(false);
        next= new JFrame();
        panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0,Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        JLabel text = new JLabel();
        text.setText("GAME OVER");
        text.setFont(new Font("TimesRoman",Font.BOLD,100));
        JLabel otherText = new JLabel();
        otherText.setFont(new Font("TimesRoman",Font.PLAIN,70));
        if(player1Score>player2Score){
            text.setForeground(Color.RED);
            otherText.setForeground(Color.RED);
            otherText.setText("Player 1 wins");
        }else{
            if(player2Score>player1Score) {
                text.setForeground(Color.BLUE);
                otherText.setForeground(Color.BLUE);
                otherText.setText("Player 2 wins");
            }else{
                text.setForeground(Color.WHITE);
                otherText.setForeground(Color.WHITE);
                otherText.setText("TIE");
            }
        }
        JButton playAgain = new JButton("Play again?");
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameBoard();
                next.setVisible(false);
            }
        });
        panel.add(text,BorderLayout.NORTH);
        panel.add(otherText,BorderLayout.CENTER);
        panel.add(playAgain,BorderLayout.SOUTH);
        next.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        next.setSize(Paths.FRAME_WIDTH,(int)(Paths.FRAME_HEIGHT/1.5));
        next.setResizable(false);
        next.add(panel);
        next.setVisible(true);

    }
}

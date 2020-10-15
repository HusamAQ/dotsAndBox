package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameOver {
    // This is the game over screen class

    // JFrame for the current game, so it can close it down
    private JFrame frame;
    // game over screen.
    private JFrame next;
    private JPanel panel;

    public GameOver(JFrame frame){
        this.frame=frame;
    }
    // turns it on
    public void toggle(){
        frame.setVisible(false);
        next= new JFrame();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBounds(0, 0,Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        panel.setBackground(Color.DARK_GRAY);
        panel.setOpaque(true);
        JLabel text = new JLabel();
        text.setText("GAME OVER");
        text.setFont(new Font("TimesRoman",Font.BOLD, (int)(Math.sqrt(Paths.FRAME_HEIGHT*Paths.FRAME_WIDTH)/7)));
        JLabel otherText = new JLabel();
        JLabel wonCounter = new JLabel();
        wonCounter.setFont(new Font("TimesRoman",Font.PLAIN,(int)(Math.sqrt(Paths.FRAME_HEIGHT*Paths.FRAME_WIDTH)/13)));
        // sets score counters
        if(Graph.getPlayer1Score()>Graph.getPlayer2Score()){
            text.setForeground(Color.RED);
            otherText.setForeground(Color.RED);
            otherText.setFont(new Font("TimesRoman",Font.PLAIN,(int)(Math.sqrt(Paths.FRAME_HEIGHT*Paths.FRAME_WIDTH)/(0.322*Graph.getPlayer1Name().length()+ 8.33))));
            otherText.setText(Graph.getPlayer1Name()+" wins");
            Graph.setGamesWon1(Graph.getGamesWon1()+1);
            wonCounter.setForeground(Color.RED);
            if(Graph.getGamesWon1()>1) {
                wonCounter.setText("They have won " + Graph.getGamesWon1() + " times. "+'\n');
            }else{
                wonCounter.setText("They have won " + Graph.getGamesWon1() + " time. "+'\n');
            }
        }else{
            if(Graph.getPlayer2Score()>Graph.getPlayer1Score()) {
                text.setForeground(Color.BLUE);
                otherText.setForeground(Color.BLUE);
                otherText.setFont(new Font("TimesRoman",Font.PLAIN,(int)(Math.sqrt(Paths.FRAME_HEIGHT*Paths.FRAME_WIDTH)/(0.322*Graph.getPlayer2Name().length()+ 8.33))));
                otherText.setText(Graph.getPlayer2Name()+" wins");
                Graph.setGamesWon2(Graph.getGamesWon2()+1);
                wonCounter.setForeground(Color.BLUE);
                if(Graph.getGamesWon2()>1) {
                    wonCounter.setText("They have won " + Graph.getGamesWon2() + " times. "+'\n');
                }else{
                    wonCounter.setText("They have won " + Graph.getGamesWon2() + " time. "+'\n');
                }
            }else{
                text.setForeground(Color.WHITE);
                otherText.setForeground(Color.WHITE);
                otherText.setText("TIE");
            }
        }
        JButton playAgain = new JButton("Play again?");
        // creates the next game
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameBoard();
                next.setVisible(false);
            }
        });
        JLabel score = new JLabel();
        score.setFont(new Font("TimesRoman",Font.PLAIN,(int)(Math.sqrt(Paths.FRAME_HEIGHT*Paths.FRAME_WIDTH)/13)));
        score.setForeground(Color.WHITE);
        score.setText("The score was " + Graph.getPlayer1Score()+" : "+Graph.getPlayer2Score());
        panel.add(text);
        panel.add(otherText);
        panel.add(wonCounter);
        panel.add(playAgain);
        panel.add(score);
        next.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        next.setSize(Paths.FRAME_WIDTH,(int)(Paths.FRAME_HEIGHT*0.7));
        next.setResizable(false);
        next.add(panel);
        next.setVisible(true);

    }
}

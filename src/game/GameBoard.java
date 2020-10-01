package game;


import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

import static game.Graph.activateRandom;
import static game.Graph.randBot;

public class GameBoard{
    // Overall launcher for the game
    JFrame frame;
    // Graph is the background of the game
    Graph graph;
    // paintBoard is the JPanel for the edges, score counter and score boxes
    paintBoard panel;
    public GameBoard(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph = new Graph(8,5,frame);
        graph.createGraph();
        panel = new paintBoard(graph);
        // dotDrawer draws the dots over the edges, I used layerUI because it draws over JLabels.
        LayerUI<JComponent> layerUI = new dotDrawer(graph);
        JLayer<JComponent> jlayer = new JLayer<JComponent>(panel,layerUI);
        frame.setSize(Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(jlayer);
        frame.setVisible(true);
        // activate randomBot
        if(activateRandom){
            if(graph.randBotPlayer1){
                randBot.placeRandomEdge();
                if(graph.player1Turn){
                    graph.player1Turn=false;
                }
            }
        }
    }
}

package game;


import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

import static game.Graph.activateRandom;
import static game.Graph.randBot;

public class GameBoard{
    JFrame frame;
    Graph graph;
    paintBoard panel;
    public GameBoard(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph = new Graph(3,4,frame);
        graph.createGraph();
        panel = new paintBoard(graph);
        LayerUI<JComponent> layerUI = new dotDrawer(graph);
        JLayer<JComponent> jlayer = new JLayer<JComponent>(panel,layerUI);
        frame.setSize(Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(jlayer);
        frame.setVisible(true);
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

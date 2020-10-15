package game;


import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class GameBoard{
    // Overall launcher for the game
    // Graphical frame
    private JFrame frame;
    // Graph is the background engine of the game
    private Graph graph;
    // PaintBoard is the JPanel for the edges, score counter and score boxes
    private PaintBoard panel;
    public GameBoard(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph = new Graph(Graph.getHeight(),Graph.getWidth(),frame);
        frame.setTitle("Dots & Boxes");
		ImageIcon img = new ImageIcon(Paths.TOP_ICON);
		frame.setIconImage(img.getImage());
        graph.createGraph();
        panel = new PaintBoard();
        // DotDrawer draws the dots over the edges, I used layerUI because it draws over JLabels.
        LayerUI<JComponent> layerUI = new DotDrawer();
        JLayer<JComponent> jlayer = new JLayer<JComponent>(panel,layerUI);
        frame.setSize(Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(jlayer);
        frame.setVisible(true);
        // activate RandomBot
        if(Graph.getActivateRandom()){
            if(Graph.getRandBotPlayer1()){
                Graph.getRandomBot().placeRandomEdge();
                if(Graph.getPlayer1Turn()){
                    Graph.setPlayer1Turn(false);;
                }
            }
        }
    }
    // overloaded constructor in case you want to call GameBoard without the player having gone through the settings yet
    public GameBoard(int h, int w){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph = new Graph(h,w,frame);
        graph.createGraph();
        panel = new PaintBoard();
        // DotDrawer draws the dots over the edges, I used layerUI because it draws over JLabels.
        LayerUI<JComponent> layerUI = new DotDrawer();
        JLayer<JComponent> jlayer = new JLayer<JComponent>(panel,layerUI);
        frame.setSize(Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(jlayer);
        frame.setVisible(true);
        // activate RandomBot
        if(Graph.getActivateRandom()){
            if(Graph.getRandBotPlayer1()){
                Graph.getRandomBot().placeRandomEdge();
                if(Graph.getPlayer1Turn()){
                    Graph.setPlayer1Turn(false);;
                }
            }
        }
    }
}

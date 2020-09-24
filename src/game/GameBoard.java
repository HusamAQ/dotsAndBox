package game;


import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class GameBoard {
    JFrame frame;
    Graph graph;
    paintBoard panel;
    public GameBoard(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph = new Graph(4,4);
        graph.createGraph();
        panel = new paintBoard(graph);
        LayerUI<JComponent> layerUI = new dotDrawer(graph);
        JLayer<JComponent> jlayer = new JLayer<JComponent>(panel,layerUI);
        frame.setSize(Paths.FRAME_WIDTH,Paths.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(jlayer);
        frame.setVisible(true);
    }
}

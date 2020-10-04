package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static game.Graph.*;

public class paintBoard extends JPanel {
    // Base JPanel
    Graph graph;
    public paintBoard(Graph gr){
        graph=gr;
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        // adds the edges to the JPanel
        for(int w=0;w<Graph.getEdgeList().size();w++){
            this.add(Graph.getEdgeList().get(w).getEline());
        }
        // adds the scoreLabels to the JPanel
        this.add(Graph.getScore1());
        this.add(Graph.getScore2());
        // adds the counter boxes to the JPanel
        for(scoreBox box: Graph.getCounterBoxes()){
            this.add(box);
        }
    }
    // i cant remember if this is useful or not
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
    }
}

package game;

import graphics.Paths;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import static game.Graph.*;

public class paintBoard extends JPanel {
    Graph graph;
    public paintBoard(Graph gr){
        graph=gr;
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        for(int w=0;w<graph.edgeList.size();w++){
            graph.edgeList.get(w).createLine();
            this.add(graph.edgeList.get(w).line);
        }
        this.add(Graph.score1);
        this.add(Graph.score2);
        for(scoreBox box: counterBoxes){
            this.add(box);
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);


    }
}

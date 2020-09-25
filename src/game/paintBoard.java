package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static game.Graph.*;

public class paintBoard extends JPanel {
    Graph graph;
    public paintBoard(Graph gr){
        graph=gr;
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        availableLines= new ArrayList<>();
        for(int w=0;w<graph.edgeList.size();w++){
            graph.edgeList.get(w).createLine();
            availableLines.add(graph.edgeList.get(w).line);
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

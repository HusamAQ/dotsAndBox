package game;

import javax.swing.*;
import java.awt.*;

public class PaintBoard extends JPanel {
    // Base JPanel
    public PaintBoard(){
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
        for(ScoreBox box: Graph.getCounterBoxes()){
            this.add(box);
        }
    }
}

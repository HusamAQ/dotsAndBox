package game;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class dotDrawer extends LayerUI<JComponent> {
    Graph graph;
    public dotDrawer(Graph g){
        graph=g;
    }
    @Override
    public void paint(Graphics g, JComponent c){
        super.paint(g,c);
        Graphics2D g2 = (Graphics2D) g.create();
        for(int h=0;h<graph.vertexList.size();h++) {
            g2.fillOval(graph.vertexList.get(h).width-15,graph.vertexList.get(h).height-15,20,20);
        }
    }
}

package game;

import graphics.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Vertex {
    // the dots class

    // the id of the dot: it goes from top left to bottom right, moving right each time.
    // e.g on a 3x3 board it's:
    // 0 1 2
    // 3 4 5
    // 6 7 8
    public int id;
    // the vertices that it can create an edge with
    Vertex leftVertex=null;
    Vertex rightVertex=null;
    Vertex upVertex=null;
    Vertex downVertex=null;
    int height;
    int width;
    public Vertex(int id){
        this.id=id;
    }
    public void setLeftVertex(Vertex leftVertex) {
        this.leftVertex = leftVertex;
    }

    public void setRightVertex(Vertex rightVertex) {
        this.rightVertex = rightVertex;
    }

    public void setUpVertex(Vertex upVertex) {
        this.upVertex = upVertex;
    }

    public void setDownVertex(Vertex downVertex) {
        this.downVertex = downVertex;
    }
    // sets the position of the dots
    public void setPosition(int Gwidth, int Gheight){
        int widthM=(Paths.FRAME_WIDTH-100)/(Gwidth-1);
        int heightM=(Paths.FRAME_HEIGHT-150)/(Gheight-1);
        width=50+(id%Gwidth)*widthM;
        height=100+(id/Gwidth)*heightM;
    }

    public String toString(){
        String toReturn = "ID: "+id;
        if(rightVertex!=null){
            toReturn+=". Right: "+rightVertex.id;
        }
        if(leftVertex!=null){
            toReturn+=". Left: "+leftVertex.id;
        }
        if(downVertex!=null){
            toReturn+=". Down: "+downVertex.id;
        }
        if(upVertex!=null){
            toReturn+=". Up: "+upVertex.id;
        }
        return toReturn;
    }
}
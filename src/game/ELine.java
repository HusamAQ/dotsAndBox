package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

import static game.Graph.*;


public class ELine extends JLabel {
    public boolean activated = false;
    int startX;
    int startY;
    public ArrayList<Vertex> vertices;
    public ELine(int w,int h,int x,int y,ArrayList<Vertex> v){
        vertices=v;
        startX=x;
        startY=y;
        setBackground(Color.WHITE);
        setBounds(x,y,w,h);
        setOpaque(true);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                if(!activated) {
                    if (player1Turn) {
                        setBackground(Color.RED);
                    } else {
                        setBackground(Color.BLUE);
                    }
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(!activated) {
                    setBackground(Color.WHITE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!activated) {
                    activated=true;
                    for(int p=availableLines.size()-1;p>=0;p--){
                        if(availableLines.get(p).vertices.get(0).id==vertices.get(0).id&&availableLines.get(p).vertices.get(1).id==vertices.get(1).id){
                            availableLines.remove(p);
                        }
                    }
                    setBackground(Color.BLACK);
                    repaint();
                    Graph.matrix[vertices.get(0).id][vertices.get(1).id] = 2;
                    Graph.matrix[vertices.get(1).id][vertices.get(0).id] = 2;
                    ArrayList<ArrayList<Vertex>> boxes = checkBox();
                    if (boxes != null) {
                        for (ArrayList<Vertex> box : boxes) {
                            checkMatching(box);
                            if (player1Turn) {
                                player1Score++;
                                Graph.score1.setScore();
                            } else {
                                player2Score++;
                                Graph.score2.setScore();
                            }
                        }
                        if(checkFinished()){
                            screen.toggle();
                        }
                        if(activateRandom&&randBotPlayer1==player1Turn){
                            randBot.placeRandomEdge();
                        }
                    } else {
                        if (player1Turn) {
                            player1Turn = false;
                            if(!randBotPlayer1&&activateRandom){
                                randBot.placeRandomEdge();
                            }
                        } else {
                            player1Turn = true;
                            if(randBotPlayer1&&activateRandom){
                                randBot.placeRandomEdge();
                            }
                        }
                    }
                }
            }
        });
    }
    public boolean checkFinished(){
        for(scoreBox box:counterBoxes){
            if(!box.activated){
                return false;
            }
        }
        return true;
    }
    public void checkMatching(ArrayList<Vertex> box){
        int avgX=0;
        int avgY=0;
        for(Vertex v:box){
            avgX+=v.width;
            avgY+=v.height;
        }
        avgX=avgX/4;
        avgY=avgY/4;
        for(scoreBox sc: counterBoxes){
            if(sc.avgX==avgX&&sc.avgY==avgY){
                sc.setText();
            }
        }
    }
    public ArrayList<ArrayList<Vertex>> checkBox(){
        ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
        if(vertices.get(1).id-vertices.get(0).id==1){
            if(vertices.get(0).upVertex!=null){
                if(Graph.matrix[vertices.get(0).id][vertices.get(0).upVertex.id]==2&&Graph.matrix[vertices.get(1).id][vertices.get(1).upVertex.id]==2&&Graph.matrix[vertices.get(0).upVertex.id][vertices.get(1).upVertex.id]==2){
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertices.get(0));
                    box.add(vertices.get(1));
                    box.add(vertices.get(0).upVertex);
                    box.add(vertices.get(1).upVertex);
                    listOfBoxes.add(box);
                }
            }
            if(vertices.get(0).downVertex!=null){
                if(Graph.matrix[vertices.get(0).id][vertices.get(0).downVertex.id]==2&&Graph.matrix[vertices.get(1).id][vertices.get(1).downVertex.id]==2&&Graph.matrix[vertices.get(0).downVertex.id][vertices.get(1).downVertex.id]==2){
                    ArrayList<Vertex> box2 = new ArrayList<>();
                    box2.add(vertices.get(0));
                    box2.add(vertices.get(1));
                    box2.add(vertices.get(0).downVertex);
                    box2.add(vertices.get(1).downVertex);
                    listOfBoxes.add(box2);
                }
            }
        }else{
            if(vertices.get(0).rightVertex!=null){
                if(Graph.matrix[vertices.get(0).id][vertices.get(0).rightVertex.id]==2&&Graph.matrix[vertices.get(1).id][vertices.get(1).rightVertex.id]==2&&Graph.matrix[vertices.get(0).rightVertex.id][vertices.get(1).rightVertex.id]==2){
                    ArrayList<Vertex> box3 = new ArrayList<>();
                    box3.add(vertices.get(0));
                    box3.add(vertices.get(1));
                    box3.add(vertices.get(0).rightVertex);
                    box3.add(vertices.get(1).rightVertex);
                    listOfBoxes.add(box3);
                }
            }
            if(vertices.get(0).leftVertex!=null){
                if(Graph.matrix[vertices.get(0).id][vertices.get(0).leftVertex.id]==2&&Graph.matrix[vertices.get(1).id][vertices.get(1).leftVertex.id]==2&&Graph.matrix[vertices.get(0).leftVertex.id][vertices.get(1).leftVertex.id]==2){
                    ArrayList<Vertex> box4 = new ArrayList<>();
                    box4.add(vertices.get(0));
                    box4.add(vertices.get(1));
                    box4.add(vertices.get(0).leftVertex);
                    box4.add(vertices.get(1).leftVertex);
                    listOfBoxes.add(box4);
                }
            }
        }
        if(listOfBoxes.isEmpty()){
            return null;
        }
        return listOfBoxes;
    }
}

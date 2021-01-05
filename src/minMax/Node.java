package minMax;

import game.ELine;
import game.ScoreBox;
import game.Vertex;

import java.util.ArrayList;


public class Node {
    int[][] matrix;
    int botScore;
    int oScore;
    ArrayList<ELine> availLines;
    boolean botTurn;
    boolean terminal;
    boolean bonusTurn;
    public ELine move;

    public String toString(){
        String toReturn = "av: ";
        for(ELine a:availLines){
            toReturn+=a.toString()+" ";
        }
        toReturn+= " |"+" sc: "+botScore+":"+oScore+" next turn: "+botTurn;
        if(bonusTurn){
            toReturn+= " BONUS TURN";
        }
        return toReturn;
    }

    double evaluation(){
        return botScore-oScore;
    }


    public Node(int[][] m, int bs,int os, ArrayList<ELine> av, boolean p1T,boolean bonusTurn,boolean t,ELine move){
        matrix=m;
        botScore=bs;
        oScore=os;
        availLines=av;
        botTurn=p1T;
        terminal=t;
        this.bonusTurn=bonusTurn;
        this.move=move;
    }

    public static int[][] matrixCopy(int[][] m) {
        int[][] newMatrix = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newMatrix[i][j] = m[i][j];
            }
        }
        return newMatrix;
    }
    public static ArrayList<ELine> avCopy(ArrayList<ELine> av){
        ArrayList<ELine> temp = new ArrayList<>();
        for(ELine b:av){
            temp.add(b);
        }
        return temp;
    }

    Node performMove(ELine line) {
        int score = botScore;
        int oScore = this.oScore;
        boolean turn;
        ArrayList<ELine> cp = avCopy(availLines);
        ArrayList<ELine> al = availCheck(cp,line);
        int[][] newMatrix = matrixCopy(matrix);
        newMatrix[line.vertices.get(0).getID()][line.vertices.get(1).getID()] = 2;
        newMatrix[line.vertices.get(1).getID()][line.vertices.get(0).getID()] = 2;
        ArrayList<ArrayList<Vertex>> boxes = checkBox(line, newMatrix);
        boolean bonTurn = false;
        if (boxes != null) {
            bonTurn=true;
            for (ArrayList<Vertex> box : boxes) {
                if (botTurn) {
                    score++;
                } else {
                    oScore++;
                }
            }
        }
        if(!bonTurn) {
            turn = !this.botTurn;
        }else{
            turn = this.botTurn;
        }
        if(al.size()==0){
            return new Node(newMatrix,score,oScore,al,turn,bonTurn,true,line);
        }
        return new Node(newMatrix,score,oScore,al,turn,bonTurn,false,line);
    }

    public ArrayList<ArrayList<Vertex>> checkBox(ELine line, int[][] GState) {
        ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
        if (line.getHorizontal()) {
            if (line.vertices.get(0).getUpVertex() != null) {
                if (GState[line.vertices.get(0).getID()][line.vertices.get(0).getUpVertex().getID()] == 2 && GState[line.vertices.get(1).getID()][line.vertices.get(1).getUpVertex().getID()] == 2 && GState[line.vertices.get(0).getUpVertex().getID()][line.vertices.get(1).getUpVertex().getID()] == 2) {
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(line.vertices.get(0));
                    box.add(line.vertices.get(1));
                    box.add(line.vertices.get(0).getUpVertex());
                    box.add(line.vertices.get(1).getUpVertex());
                    listOfBoxes.add(box);
                }
            }
            if (line.vertices.get(0).getDownVertex() != null) {
                if (GState[line.vertices.get(0).getID()][line.vertices.get(0).getDownVertex().getID()] == 2 && GState[line.vertices.get(1).getID()][line.vertices.get(1).getDownVertex().getID()] == 2 && GState[line.vertices.get(0).getDownVertex().getID()][line.vertices.get(1).getDownVertex().getID()] == 2) {
                    ArrayList<Vertex> box2 = new ArrayList<>();
                    box2.add(line.vertices.get(0));
                    box2.add(line.vertices.get(1));
                    box2.add(line.vertices.get(0).getDownVertex());
                    box2.add(line.vertices.get(1).getDownVertex());
                    listOfBoxes.add(box2);
                }
            }
        } else {
            if (line.vertices.get(0).getRightVertex() != null) {
                if (GState[line.vertices.get(0).getID()][line.vertices.get(0).getRightVertex().getID()] == 2 && GState[line.vertices.get(1).getID()][line.vertices.get(1).getRightVertex().getID()] == 2 && GState[line.vertices.get(0).getRightVertex().getID()][line.vertices.get(1).getRightVertex().getID()] == 2) {
                    ArrayList<Vertex> box3 = new ArrayList<>();
                    box3.add(line.vertices.get(0));
                    box3.add(line.vertices.get(1));
                    box3.add(line.vertices.get(0).getRightVertex());
                    box3.add(line.vertices.get(1).getRightVertex());
                    listOfBoxes.add(box3);
                }
            }
            if (line.vertices.get(0).getLeftVertex() != null) {
                if (GState[line.vertices.get(0).getID()][line.vertices.get(0).getLeftVertex().getID()] == 2 && GState[line.vertices.get(1).getID()][line.vertices.get(1).getLeftVertex().getID()] == 2 && GState[line.vertices.get(0).getLeftVertex().getID()][line.vertices.get(1).getLeftVertex().getID()] == 2) {
                    ArrayList<Vertex> box4 = new ArrayList<>();
                    box4.add(line.vertices.get(0));
                    box4.add(line.vertices.get(1));
                    box4.add(line.vertices.get(0).getLeftVertex());
                    box4.add(line.vertices.get(1).getLeftVertex());
                    listOfBoxes.add(box4);
                }
            }
        }
        // if it creates no boxes, return null.
        if (listOfBoxes.isEmpty()) {
            return null;
        }
        return listOfBoxes;
    }
    /*
    public ArrayList<ScoreBox> checkMatching(ArrayList<Vertex> box, ArrayList<ScoreBox> GState){
        int avgX=0;
        int avgY=0;
        for(Vertex v:box){
            avgX+=v.getWidth();
            avgY+=v.getHeight();
        }
        avgX=avgX/4;
        avgY=avgY/4;
        for(ScoreBox sc: GState){
            if(sc.getAvgX()==avgX&&sc.getAvgY()==avgY){
                sc.setText();
            }
        }
        return GState;
    }

     */
    public static ArrayList<ELine> availCheck(ArrayList<ELine> av,ELine line){
        //  System.out.println("AV CHECK:");
        for(int q=av.size()-1;q>=0;q--){
            if(av.get(q).equals(line)){
                //  System.out.println("REMOVE: "+av.get(q).vertices.get(0).id+" -- "+av.get(q).vertices.get(1).id);
                av.remove(q);
            }
        }
        return av;
    }

}

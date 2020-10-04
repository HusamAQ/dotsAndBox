package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    // Overarching game class
    static randomBot randBot = new randomBot();
    // chooses whether randBot will be player 1 or 2
    public static boolean randBotPlayer1 = false;
    // chooses whether randBot is active
    public static boolean activateRandom = true;
    // Adjacency matrix
    static int[][] matrix;
    // List of dots
    static List<Vertex> vertexList;
    // List of edges
    static List<Edge> edgeList;
    // List of lines (edges) that haven't been activated yet
    static ArrayList<ELine> availableLines;
    // Height and width of the dots
    static int height;
    static int width;
    // tracking how many games each player has won
    static int gamesWon1=0;
    static int gamesWon2=0;
    // The JLabels for displaying the score
    static scoreLabel score1;
    static scoreLabel score2;
    // tracking whether it's player 1's turn or not
    static boolean player1Turn;
    // tracking the score in a game
    static int player1Score = 0;
    static int player2Score = 0;
    // All of the boxes, so if a box is completed this displays, can be either initials or the score counter
    static ArrayList<scoreBox> counterBoxes;
    // Game over screen
    static gameOver screen;
    // initials or score counter in ScoreBox
    static String player1Name = "Gerald";
    static String player2Name = "Alex";
    static boolean initials = true;
    JFrame frame;
    public Graph(int h, int w, JFrame screen){
        height=h;
        width=w;
        frame=screen;
    }
    // Sets up the game
    public void createGraph(){
        player1Turn=true;
        player1Score=0;
        player2Score=0;
        screen = new gameOver(frame);
        counterBoxes= new ArrayList<>();
        score1=new scoreLabel(1);
        score2=new scoreLabel(2);
        vertexList= new ArrayList<>();
        // Creates every vertex and sets it's ID and position
        for(int w=0;w<height*width;w++){
            vertexList.add(new Vertex(w));
            vertexList.get(w).setPosition(width, height);
        }
        int counter =0;
        // Creates adjacency matrix
        matrix= new int[vertexList.size()][vertexList.size()];
        int id=0;
        // sets the adjacent vertices for each vertex
        for(int l=0;l<height;l++){
            for(int e=0;e<width;e++){
                Vertex temp = vertexList.get(id);
                if(e!=0){
                    temp.setLeftVertex(vertexList.get(id-1));
                }
                if(e!=width-1){
                    temp.setRightVertex(vertexList.get(id+1));
                }
                if(l!=0){
                    temp.setUpVertex(vertexList.get(id-width));
                }
                if(l!=height-1){
                    temp.setDownVertex(vertexList.get(id+width));
                }
                vertexList.set(id,temp);
                id++;
            }
        }
        // sets all of the available edges in the adjacency matrix to 1
        for (Vertex a:vertexList) {
            if(a.getLeftVertex()!=null){
                matrix[a.getID()][a.getLeftVertex().getID()]=1;
            }
            if(a.getRightVertex()!=null){
                matrix[a.getID()][a.getRightVertex().getID()]=1;
            }
            if(a.getUpVertex()!=null){
                matrix[a.getID()][a.getUpVertex().getID()]=1;
            }
            if(a.getDownVertex()!=null){
                matrix[a.getID()][a.getDownVertex().getID()]=1;
            }
        }
        edgeList= new ArrayList<>();
        availableLines = new ArrayList<>();
        // creates a copy of the matrix to create the list of edges
        int[][] matrixCopy = new int[matrix.length][matrix[0].length];
        for(int r=0;r<matrix.length;r++){
            for(int q=0;q<matrix[0].length;q++){
                // If a space in the matrix == 1, then it creates and edge and adds it to the edge list, it then sets it so the inverse isn't added
                // e.g it adds the edge 0--1 but not 1--0
                if(matrixCopy[r][q]!=3) {
                    matrixCopy[r][q] = matrix[r][q];
                }
                if(matrixCopy[r][q]==1){
                    Edge ne = new Edge(vertexList.get(r), vertexList.get(q));
                    ne.createLine();
                    availableLines.add(ne.line);
                    edgeList.add(ne);
                    matrixCopy[q][r]=3;
                }
            }
        }
        // creates each available box and adds it to the counterBoxes list. This is for displaying what pops up when you complete a box
        for(int r=0;r<height;r++){
            for(int c=0;c<width;c++){
                if(r<height-1&&c<width-1) {
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertexList.get(counter));
                    box.add(vertexList.get(counter + 1));
                    box.add(vertexList.get(counter + width));
                    box.add(vertexList.get(counter + width + 1));
                    counterBoxes.add(new scoreBox(box));
                }
                counter++;
            }
        }
    }

    public class Edge {
        // overall edge class

        // the vertices in the edge
        ArrayList<Vertex> vertices;
        // The graphical display of the edge and the actionListener is stored in ELine
        ELine line;
        // Whether the edge is horizontal or vertical
        boolean horizontal;
        public Edge(Vertex one, Vertex two){
            vertices=new ArrayList<>();
            vertices.add(one);
            vertices.add(two);
            // if the second vertex id - the first vertex id == 1 then it's horizontal. E.g 3-2=1 but 6-3!=1
            if(two.getID()-one.getID()==1){
                horizontal=true;
            }
            else{
                horizontal=false;
            }
        }
        // Creates the ELine
        public void createLine(){
            if(horizontal){
                int wid=vertices.get(1).getWidth()-vertices.get(0).getWidth();
                int hei=8;
                line = new ELine(wid,hei,vertices.get(0).getWidth(),vertices.get(0).getHeight()-8,vertices);
            }
            else{
                int hei=vertices.get(1).getHeight()-vertices.get(0).getHeight();
                int wid=8;
                line = new ELine(wid,hei,vertices.get(0).getWidth()-8,vertices.get(0).getHeight(),vertices);
            }
        }
        public String toString(){
            return vertices.get(0).getID() + " -- "+ vertices.get(1).getID()+". ";
        }
    }
}
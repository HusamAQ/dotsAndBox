package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    // Overarching game class

    public static randomBot randBot = new randomBot();
    // chooses whether randBot will be player 1 or 2
    public static boolean randBotPlayer1 = false;
    // chooses whether randBot is active
    public static boolean activateRandom = true;
    // Adjacency matrix
    public static int[][] matrix;
    // List of dots
    public static List<Vertex> vertexList;
    // List of edges
    public static List<Edge> edgeList;
    // List of lines (edges) that haven't been activated yet
    public static ArrayList<ELine> availableLines;
    // Height and width of the dots
    public static int height;
    public static int width;
    // tracking how many games each player has won
    static int gamesWon1=0;
    static int gamesWon2=0;
    // The JLabels for displaying the score
    public static scoreLabel score1;
    public static scoreLabel score2;
    // tracking whether it's player 1's turn or not
    public static boolean player1Turn;
    // tracking the score in a game
    public static int player1Score = 0;
    public static int player2Score = 0;
    // All of the boxes, so if a box is completed this displays, can be either initials or the score counter
    public static ArrayList<scoreBox> counterBoxes;
    // Game over screen
    public static gameOver screen;
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
            if(a.leftVertex!=null){
                matrix[a.id][a.leftVertex.id]=1;
            }
            if(a.rightVertex!=null){
                matrix[a.id][a.rightVertex.id]=1;
            }
            if(a.upVertex!=null){
                matrix[a.id][a.upVertex.id]=1;
            }
            if(a.downVertex!=null){
                matrix[a.id][a.downVertex.id]=1;
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
            if(two.id-one.id==1){
                horizontal=true;
            }
            else{
                horizontal=false;
            }
        }
        // Creates the ELine
        public void createLine(){
            if(horizontal){
                int wid=vertices.get(1).width-vertices.get(0).width;
                int hei=8;
                line = new ELine(wid,hei,vertices.get(0).width,vertices.get(0).height-8,vertices);
            }
            else{
                int hei=vertices.get(1).height-vertices.get(0).height;
                int wid=8;
                line = new ELine(wid,hei,vertices.get(0).width-8,vertices.get(0).height,vertices);
            }
        }
        public String toString(){
            return vertices.get(0).id + " -- "+ vertices.get(1).id+". ";
        }
    }
}
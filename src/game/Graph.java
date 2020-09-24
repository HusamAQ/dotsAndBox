package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    static int[][] matrix;
    List<Vertex> vertexList;
    List<Edge> edgeList;
    int height;
    int width;
    static scoreLabel score1;
    static scoreLabel score2;
    static boolean player1Turn = true;
    static int player1Score = 0;
    static int player2Score = 0;
    static ArrayList<scoreBox> counterBoxes;
    public Graph(int h, int w){
        height=h;
        width=w;
    }
    public void createGraph(){
        counterBoxes= new ArrayList<>();
        score1=new scoreLabel(1);
        score2=new scoreLabel(2);

        vertexList= new ArrayList<>();
        for(int w=0;w<height*width;w++){
            vertexList.add(new Vertex(w));
            vertexList.get(w).setPosition(width, height);
        }
        int counter =0;
        matrix= new int[vertexList.size()][vertexList.size()];
        int id=0;
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
        for (Vertex a:vertexList) {
            System.out.println(a);
        }
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
        System.out.println(Arrays.deepToString(matrix));
        edgeList= new ArrayList<>();
        int[][] matrixCopy = matrix;
        for(int r=0;r<matrix.length;r++){
            for(int q=0;q<matrix[0].length;q++){
                if(matrixCopy[r][q]==1){
                    edgeList.add(new Edge(vertexList.get(r), vertexList.get(q)));
                    matrixCopy[q][r]=3;
                }
            }
        }
        for(Edge ed:edgeList){
            System.out.println(ed);
        }
        for(int r=0;r<height;r++){
            for(int c=0;c<width;c++){
                if(r<height-1&&c<width-1) {
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertexList.get(counter));
                    box.add(vertexList.get(counter + 1));
                    box.add(vertexList.get(counter + width));
                    box.add(vertexList.get(counter + width
                            + 1));
                    counterBoxes.add(new scoreBox(box));
                }
                counter++;
            }
        }
    }

    public class Edge {
        ArrayList<Vertex> vertices;
        ELine line;
        boolean horizontal;
        public Edge(Vertex one, Vertex two){
            vertices=new ArrayList<>();
            vertices.add(one);
            vertices.add(two);
            if(two.id-one.id==1){
                horizontal=true;
            }
            else{
                horizontal=false;
            }
        }
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
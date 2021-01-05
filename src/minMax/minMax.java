package minMax;

import game.ELine;

import java.util.ArrayList;

public class minMax {
    public static int depth = 3;
    boolean print=false;
    static int counter=0;

    public Node alphaBeta(Node node, int depth, double a, double b, boolean bot){
        counter++;
        int t=counter;
        if(print) {
            System.out.println(t + "| " + node.toString());
        }
        if(depth==0|| node.terminal){
            return node;
        }
        Node toReturn=null;
        if(bot){
            a = -1 * Double.MAX_VALUE;
            String maxS = "MAX "+t+": ";
            Node temp=null;
            for(ELine v: node.availLines){
                Node tem = node.performMove(v);
                double toCompare = alphaBeta(tem,depth-1,a,b,tem.botTurn).evaluation();
                maxS+= toCompare+", ";
                if(a<toCompare){
                    a=toCompare;
                    temp = tem;
                }
                toReturn=temp;
                a = Math.max(a,toCompare);
                if(a>=b){
                    break;
                }
            }
            maxS+= "== "+a;
            if(print) {
                System.out.println(maxS);
            }
        }else{
            b = Double.MAX_VALUE;
            String minS = "Min "+t+": ";
            Node temp=null;
            for(ELine v: node.availLines){
                Node tem = node.performMove(v);
                double toCompare =  alphaBeta(tem,depth-1,a,b,tem.botTurn).evaluation();
                if(b>toCompare){
                    b=toCompare;
                    temp = tem;
                }
                minS+= toCompare+", ";
                b = Math.min(b,toCompare);
                if(b<=a){
                    break;
                }
            }
            minS+= "== "+b;
            if(print) {
                System.out.println(minS);
            }
            toReturn=temp;
        }
        if(print) {
            System.out.println(t + "| val: " + b);
        }
        return toReturn;
    }


    public Node minMaxFunction(Node node, int depth, boolean bot){
        counter++;
        int t=counter;
        if(print) {
            System.out.println(t + "| " + node.toString());
        }
        if(depth==0|| node.terminal){
            return node;
        }
        double val;
        Node toReturn=null;
        if(bot){
            val = -1 * Double.MAX_VALUE;
            String maxS = "MAX "+t+": ";
            Node temp=null;
            for(ELine a: node.availLines){
                Node tem = node.performMove(a);
                double toCompare = minMaxFunction(tem,depth-1,tem.botTurn).evaluation();
                maxS+= toCompare+", ";
                if(val<toCompare){
                    val=toCompare;
                    temp = tem;
                }
                val = Math.max(val,toCompare);
            }
            maxS+= "== "+val;
            if(print) {
                System.out.println(maxS);
            }
            toReturn=temp;
        }else{
            val = Double.MAX_VALUE;
            String minS = "Min "+t+": ";
            Node temp=null;
            for(ELine a: node.availLines){
                Node tem = node.performMove(a);
                double toCompare =  minMaxFunction(tem,depth-1,tem.botTurn).evaluation();
                if(val>toCompare){
                    val=toCompare;
                    temp = tem;
                }
                minS+= toCompare+", ";
                val = Math.min(val,toCompare);
            }
            minS+= "== "+val;
            if(print) {
                System.out.println(minS);
            }
            toReturn=temp;
        }
        if(print) {
            System.out.println(t + "| val: " + val);
        }
        return toReturn;
    }


}

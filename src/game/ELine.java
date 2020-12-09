package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ELine extends JLabel implements Serializable {

    // The graphical display of the edges

    public boolean isActivated() {
        return activated;
    }

    // Whether the line has been clicked or not
    private boolean activated = false;
    // Whether the line has been calculated
    private boolean calculated = false;

    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    // the vertices
    public ArrayList<Vertex> vertices;
    // whether it's horizontal
    private boolean horizontal;

    public ELine(int w, int h, int x, int y, ArrayList<Vertex> v) {
        vertices = v;
        if (vertices.get(1).getID() - vertices.get(0).getID() == 1) {
            horizontal = true;
        } else {
            horizontal = false;
        }
        // the line starts off invisible, e.g White
        setBackground(Color.WHITE);
        // x and y is the the bottom left x and y coordinates of the line
        // w and h is the width and height
        setBounds(x, y, w, h);
        setOpaque(true);
        // the mouseListener
        addMouseListener(new MouseAdapter() {
            // when the player hovers over a line it displays it in their colour
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!activated) {
                    if (Graph.getPlayer1Turn()) {
                        setBackground(Color.RED);
                    } else {
                        setBackground(Color.BLUE);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!activated) {
                    setBackground(Color.WHITE);
                }
            }

            // when clicked
            @Override
            public void mousePressed(MouseEvent e) {
                //  if the line has not been activated before
                if (!activated) {
                    activated = true;
                    // remove the ELine from availableLines
                    for (int p = Graph.getAvailableLines().size() - 1; p >= 0; p--) {
                        if (Graph.getAvailableLines().get(p).vertices.get(0).getID() == vertices.get(0).getID() && Graph.getAvailableLines().get(p).vertices.get(1).getID() == vertices.get(1).getID()) {
                            Graph.getAvailableLines().remove(p);
                        }
                    }
                    // make it black
                    setBackground(Color.BLACK);
                    repaint();
                    // set the adjacency matrix to 2, 2==is a line, 1==is a possible line
                    Graph.matrix[vertices.get(0).getID()][vertices.get(1).getID()] = 2;
                    Graph.matrix[vertices.get(1).getID()][vertices.get(0).getID()] = 2;
                    // gets an arrayList of each box the ELine creates. The box is an arrayList of 4 vertices.
                    ArrayList<ArrayList<Vertex>> boxes = checkBox(Graph.getMatrix());
                    if (boxes != null) {
                        for (ArrayList<Vertex> box : boxes) {
                            // looks through the counterBoxes arrayList and sets the matching one visible.
                            checkMatching(box);
                            // updates the score board
                            if (Graph.getPlayer1Turn()) {
                                Graph.setPlayer1Score(Graph.getPlayer1Score() + 1);
                                Graph.getScore1().setScore();
                            } else {
                                Graph.setPlayer2Score(Graph.getPlayer2Score() + 1);
                                Graph.getScore2().setScore();
                            }
                        }
                        // if every counterBox has been activated, the game is over
                        if (checkFinished(Graph.getCounterBoxes())) {
                            Graph.getScreen().toggle();
                        }
                    } else {
                        // switches turn. If RandomBot is active switches to their turn.
                        if (Graph.getPlayer1Turn()) {
                            Graph.setPlayer1Turn(false);
                            if (!Graph.getRandBotPlayer1() && Graph.getActivateRandom()) {
                                Graph.getRandomBot().placeRandomEdge();
                            }
                        } else {
                            Graph.setPlayer1Turn(true);
                            if (Graph.getRandBotPlayer1() && Graph.getActivateRandom()) {
                                Graph.getRandomBot().placeRandomEdge();
                            }
                        }
                    }
                }
            }
        });
    }

    // if every ScoreBox is active, the game is over
    public boolean checkFinished(List<ScoreBox> counterBoxes) {
        for (ScoreBox box : counterBoxes) {
            if (!box.getActivated()) {
                return false;
            }
        }
        return true;
    }

    // gets an arrayList of 4 vertices and finds the matching ScoreBox in counterBoxes through their average x and y coordinates, then displays it.
    // for when a box is completed
    // it uses average x and y coordinates because then no matter the order of the arrayList, if they have the same average x and y then they are the same box.
    public void checkMatching(ArrayList<Vertex> box) {
        int avgX = 0;
        int avgY = 0;
        for (Vertex v : box) {
            avgX += v.getWidth();
            avgY += v.getHeight();
        }
        avgX = avgX / 4;
        avgY = avgY / 4;
        for (ScoreBox sc : Graph.getCounterBoxes()) {
            if (sc.getAvgX() == avgX && sc.getAvgY() == avgY) {
                sc.setText();
            }
        }
    }

    // checks whether placing this edge creates a box, through the adjacency matrix
    // adds each box the line does create to an arrayList of 4 vertices, then returns an arrayList of those arrayLists.
    public ArrayList<ArrayList<Vertex>> checkBox(int[][] matrix) {
        ArrayList<ArrayList<Vertex>> listOfBoxes = new ArrayList<>();
        if (horizontal) {
            if (vertices.get(0).getUpVertex() != null) {
                if (matrix[vertices.get(0).getID()][vertices.get(0).getUpVertex().getID()] == 2 && matrix[vertices.get(1).getID()][vertices.get(1).getUpVertex().getID()] == 2 && matrix[vertices.get(0).getUpVertex().getID()][vertices.get(1).getUpVertex().getID()] == 2) {
                    ArrayList<Vertex> box = new ArrayList<>();
                    box.add(vertices.get(0));
                    box.add(vertices.get(1));
                    box.add(vertices.get(0).getUpVertex());
                    box.add(vertices.get(1).getUpVertex());
                    listOfBoxes.add(box);
                }
            }
            if (vertices.get(0).getDownVertex() != null) {
                if (matrix[vertices.get(0).getID()][vertices.get(0).getDownVertex().getID()] == 2 &&matrix[vertices.get(1).getID()][vertices.get(1).getDownVertex().getID()] == 2 &&matrix[vertices.get(0).getDownVertex().getID()][vertices.get(1).getDownVertex().getID()] == 2) {
                    ArrayList<Vertex> box2 = new ArrayList<>();
                    box2.add(vertices.get(0));
                    box2.add(vertices.get(1));
                    box2.add(vertices.get(0).getDownVertex());
                    box2.add(vertices.get(1).getDownVertex());
                    listOfBoxes.add(box2);
                }
            }
        } else {
            if (vertices.get(0).getRightVertex() != null) {
                if (matrix[vertices.get(0).getID()][vertices.get(0).getRightVertex().getID()] == 2 && matrix[vertices.get(1).getID()][vertices.get(1).getRightVertex().getID()] == 2 && matrix[vertices.get(0).getRightVertex().getID()][vertices.get(1).getRightVertex().getID()] == 2) {
                    ArrayList<Vertex> box3 = new ArrayList<>();
                    box3.add(vertices.get(0));
                    box3.add(vertices.get(1));
                    box3.add(vertices.get(0).getRightVertex());
                    box3.add(vertices.get(1).getRightVertex());
                    listOfBoxes.add(box3);
                }
            }
            if (vertices.get(0).getLeftVertex() != null) {
                if (matrix[vertices.get(0).getID()][vertices.get(0).getLeftVertex().getID()] == 2 && matrix[vertices.get(1).getID()][vertices.get(1).getLeftVertex().getID()] == 2 &&matrix[vertices.get(0).getLeftVertex().getID()][vertices.get(1).getLeftVertex().getID()] == 2) {
                    ArrayList<Vertex> box4 = new ArrayList<>();
                    box4.add(vertices.get(0));
                    box4.add(vertices.get(1));
                    box4.add(vertices.get(0).getLeftVertex());
                    box4.add(vertices.get(1).getLeftVertex());
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

    public void setActivated(boolean b) {
        activated = b;
    }

    public boolean getHorizontal() {
        return horizontal;
    }
}

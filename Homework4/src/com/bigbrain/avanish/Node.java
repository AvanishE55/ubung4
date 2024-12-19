package com.bigbrain.avanish;

import java.util.HashMap;

/**
 * Field Class.
 *
 * @author ufkzh
 */
public class Node {
    //private int[] coordinate; // X, Y
    private HashMap<String, Node> edges = new HashMap<>(4);
    private boolean explored = false;
    private boolean goal = false;
    private boolean robot = false;
    private Node parent;
    private String parentDirection;

//    public Node(int i, int j) {
//        coordinate = new int[]{i, j};
//    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

//    public int[] getCoordinate() {
//        return coordinate;
//    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public HashMap<String, Node> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<String, Node> edges) {
        this.edges = edges;
    }

    public String getParentDirection() {
        return parentDirection;
    }

    public void setParentDirection(String parentDirection) {
        this.parentDirection = parentDirection;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }
}

package com.bigbrain.avanish;

import java.util.HashMap;

/**
 * Field Class.
 * @author ufkzh
 */
public class Node {
    private HashMap<String, Node> edges = new HashMap<>(4);
    private boolean explored = false;
    private boolean goal = false;
    private boolean robot = false;
    private Node parent;
    private String parentDirection;

    /**
     * Gets the parent node.
     * @return parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets parent node.
     * @param parent parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Returns true if current node is the goal.
     * @return goal
     */
    public boolean isGoal() {
        return goal;
    }

    /**
     * Returns true if current node is the goal.
     * @param goal boolean
     */
    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    /**
     * Returns true if current node has been explored by BFS.
     * @return explored
     */
    public boolean isExplored() {
        return explored;
    }

    /**
     * Set to true if the current node has been explored by BFS.
     * @param explored boolean
     */
    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    /**
     * Returns the hashmap of edges, which maps a direction to a node.
     * @return edges
     */
    public HashMap<String, Node> getEdges() {
        return edges;
    }

    /**
     * Returns the direction to get to this node FROM the parent.
     * @return direction
     */
    public String getParentDirection() {
        return parentDirection;
    }

    /**
     * Set the direction to get to this node FROM the parent.
     * @param parentDirection direction
     */
    public void setParentDirection(String parentDirection) {
        this.parentDirection = parentDirection;
    }

    /**
     * Returns true if this node is the robot.
     * @return robot
     */
    public boolean isRobot() {
        return robot;
    }

    /**
     * Set to true if this node is the robot.
     * @param robot boolean
     */
    public void setRobot(boolean robot) {
        this.robot = robot;
    }
}

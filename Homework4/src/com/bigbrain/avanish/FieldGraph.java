package com.bigbrain.avanish;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static com.bigbrain.avanish.CMD.PATH_0;
import static com.bigbrain.avanish.CMD.FAIL;
import static com.bigbrain.avanish.CMD.UP;
import static com.bigbrain.avanish.CMD.DOWN;
import static com.bigbrain.avanish.CMD.LEFT;
import static com.bigbrain.avanish.CMD.RIGHT;
import static com.bigbrain.avanish.FieldCharacters.SPACE;

/**
 * FieldGraph Class which contains methods to convert the char array Field to a Node array and to populate it.
 *
 * @author ufkzh
 */
public class FieldGraph {

    private Node[][] myGraph;


    FieldGraph(Field field) {
        initFieldGraph(field);
    }


    /**
     * Prints path determined by BFS.
     *
     * @param goalX X coordinate of goal
     * @param goalY Y coordinate of goal
     * @return returns the current path as an ArrayDeque
     */
    public ArrayDeque<String> printPath(int goalX, int goalY) {
        ArrayDeque<String> pathStack = new ArrayDeque<>();

        Node node = this.myGraph[goalY][goalX];

        //check if robot position is goal
        if (node.isGoal() && node.isRobot()) {
            System.out.print(PATH_0);
            return null;
        }

        //check if no path
        if (node.getParent() == null) {
            System.out.print(FAIL);
            return null;
        }

        while (node.getParent() != null) {
            pathStack.push(node.getParentDirection());
            node = node.getParent();
        }

        int count = 0;
        String temp = "";

        for (String s : pathStack) {
            if (Objects.equals(s, temp)) {
                count++;
            } else {
                if (count > 0) {
                    System.out.println(temp + " " + count);
                }
                temp = s;
                count = 1;
            }
        }
        if (count > 0) {
            System.out.println(temp + " " + count);
        }

        return pathStack;
    }

    private void initFieldGraph(Field field) {
        myGraph = new Node[field.getHeight()][field.getWidth()];

        //initialize field of nodes - setting coordinates
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                myGraph[i][j] = new Node();
            }
        }

        //set robot field to true
        myGraph[field.getRobotY()][field.getRobotX()].setRobot(true);

        //initialize field of nodes - setting edges
        // this only checks for space meaning that the goal - x is OOB - need to set separately
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                Node thisNode = myGraph[i][j];
                if ((i + 1 < field.getHeight()) && field.getMyField()[i + 1][j] == SPACE) {
                    thisNode.getEdges().put(DOWN, myGraph[i + 1][j]);
                }
                if ((i > 0) && field.getMyField()[i - 1][j] == SPACE) {
                    thisNode.getEdges().put(UP, myGraph[i - 1][j]);
                }
                if ((j + 1 < field.getWidth()) && field.getMyField()[i][j + 1] == SPACE) {
                    thisNode.getEdges().put(RIGHT, myGraph[i][j + 1]);
                }
                if ((j > 0) && field.getMyField()[i][j - 1] == SPACE) {
                    thisNode.getEdges().put(LEFT, myGraph[i][j - 1]);
                }
            }
        }

        //set edges of nodes around goal separately
        Node goalNode = myGraph[field.getGoalY()][field.getGoalX()];
        goalNode.setGoal(true);
        if (field.getGoalX() - 1 >= 0) {
            myGraph[field.getGoalY()][field.getGoalX() - 1].getEdges().put(RIGHT, goalNode);
        }
        if (field.getGoalX() + 1 < field.getWidth()) {
            myGraph[field.getGoalY()][field.getGoalX() + 1].getEdges().put(LEFT, goalNode);
        }
        if (field.getGoalY() - 1 >= 0) {
            myGraph[field.getGoalY() - 1][field.getGoalX()].getEdges().put(DOWN, goalNode);
        }
        if (field.getGoalY() + 1 < field.getHeight()) {
            myGraph[field.getGoalY() + 1][field.getGoalX()].getEdges().put(UP, goalNode);
        }
    }

    /**
     * Searches for a path using breitensuche.
     *
     * @param robotX X coordinate of robot position
     * @param robotY Y coordinate of robot position
     */
    public void breadthFirstSearch(int robotX, int robotY) {
        Queue<Node> queue = new LinkedList<Node>();

        //set root (robot) as explored and add to queue
        this.myGraph[robotY][robotX].setExplored(true);
        queue.add(this.myGraph[robotY][robotX]);

        while (!queue.isEmpty()) {
            Node v = queue.remove();
            if (!v.isGoal()) {
                for (String dir : v.getEdges().keySet()) {
                    Node w = v.getEdges().get(dir);
                    if (!w.isExplored()) {
                        w.setExplored(true);
                        w.setParent(v);
                        w.setParentDirection(dir);
                        queue.add(w);
                    }
                }
            } else {
                break;
            }
        }
        System.out.println("Path Calculated");
    }
}




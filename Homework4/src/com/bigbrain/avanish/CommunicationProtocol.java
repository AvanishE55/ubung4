package com.bigbrain.avanish;

import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.bigbrain.avanish.CMD.DOWN;
import static com.bigbrain.avanish.CMD.RIGHT;
import static com.bigbrain.avanish.CMD.LEFT;
import static com.bigbrain.avanish.CMD.UP;
import static com.bigbrain.avanish.CMD.DEBUG;
import static com.bigbrain.avanish.CMD.DEBUG_PATH;
import static com.bigbrain.avanish.CMD.PATH;
import static com.bigbrain.avanish.CMD.NEW;
import static com.bigbrain.avanish.CMD.QUIT;


public final class CommunicationProtocol {
    private static Field field;
    private static Scanner scanner;
    private static FieldGraph fieldGraph;
    private static Deque<String> pathStack;

    private CommunicationProtocol() {
    }

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!Objects.equals(input, QUIT)) {
            performCommand(input.split(" "));
            input = scanner.nextLine();
        }
    }

    public static void performCommand(String[] currentCommand) {
        try {
            switch (currentCommand[0]) {
                case NEW:
                    field = new Field(currentCommand[1], currentCommand[2], scanner);
                    break;

                case DEBUG:
                    field.printField();
                    break;

                case PATH:
                    System.out.println("Calculating Path");
                    fieldGraph = new FieldGraph(field);
                    fieldGraph.breadthFirstSearch(field.getRobotX(), field.getRobotY());

                    pathStack = fieldGraph.printPath(field.getGoalX(), field.getGoalY());
                    break;

                case DEBUG_PATH:
                    System.out.println("Debugging Path");
                    field.debugPath(pathStack);
                    break;

                case UP, DOWN, LEFT, RIGHT://moving - up/down/left/right
                    //distance defaults to 1 if no input
                    int distance = Integer.parseInt((currentCommand.length == 1) ? "1" : currentCommand[1]);
                    System.out.println("Moving " + currentCommand[0] + " by " + distance + " spaces");
                    field.move(currentCommand[0], distance);
                    break;

                default:
                    System.out.println(CMD.ERROR_MESSAGE + ": False command");
            }

        } catch (Exception e) {
            System.out.println(CMD.ERROR_MESSAGE + ": False command");
            System.out.println(e);
        }

    }
}
package com.bigbrain.avanish;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayDeque;
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
import static com.bigbrain.avanish.CMD.ERROR_MESSAGE;


/**
 * Class with main method that runs the program.
 * @author ufkzh
 */
public final class CommunicationProtocol {
    private static Field field;
    private static Scanner scanner;
    private static FieldGraph fieldGraph;
    private static ArrayDeque<String> path;

    private CommunicationProtocol() {
    }

    /**
     * The damn main method?
     * @param args nothing?
     */
    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!Objects.equals(input, QUIT)) {
            performCommand(input.split(" "));
            input = scanner.nextLine();
        }
    }

    /**
     * Method with performs the inputted command/ calls the relevant method.
     * @param currentCommand current inputted command
     */
    public static void performCommand(String[] currentCommand) {
        if (currentCommand.length < 1) {
            System.out.println(ERROR_MESSAGE);
            return;
        }

        try {
            switch (currentCommand[0]) {
                case NEW:
                    field = new Field(currentCommand[1], currentCommand[2], scanner);
                    break;

                case DEBUG:
                    if (field == null) {
                        throw new IllegalCharsetNameException(ERROR_MESSAGE);
                    }
                    field.printField();
                    break;

                case PATH:
                    if (field == null) {
                        throw new IllegalCharsetNameException(ERROR_MESSAGE);
                    }
                    //System.out.println("Calculating Path");
                    fieldGraph = new FieldGraph(field);
                    fieldGraph.breadthFirstSearch(field.getRobotX(), field.getRobotY());

                    path = fieldGraph.printPath(field.getGoalX(), field.getGoalY());
                    break;

                case DEBUG_PATH:
                    if (field == null) {
                        throw new IllegalCharsetNameException(ERROR_MESSAGE);
                    }
                    //System.out.println("Debugging Path");
                    field.debugPath(path);
                    break;

                case UP, DOWN, LEFT, RIGHT://moving - up/down/left/right
                    //sheet said: Die Übertragung ist bei dieser kurzen Nachricht immer fehlerfrei
                    if (field == null) {
                        throw new IllegalCharsetNameException(ERROR_MESSAGE);
                    }
                    //distance defaults to 1 if no input
                    int distance = Integer.parseInt((currentCommand.length == 1) ? "1" : currentCommand[1]);
                    //System.out.println("Moving " + currentCommand[0] + " by " + distance + " spaces");
                    field.move(currentCommand[0], distance);
                    break;

                default:
                    System.out.println(CMD.ERROR_MESSAGE);
                    break;
            }

        } catch (IllegalCharsetNameException e) {
            System.out.println(ERROR_MESSAGE);
        }

    }
}
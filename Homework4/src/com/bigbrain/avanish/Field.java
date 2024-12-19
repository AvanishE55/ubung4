package com.bigbrain.avanish;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayDeque;
import java.util.Scanner;

import static com.bigbrain.avanish.CMD.DOWN;
import static com.bigbrain.avanish.CMD.RIGHT;
import static com.bigbrain.avanish.CMD.LEFT;
import static com.bigbrain.avanish.CMD.UP;
import static com.bigbrain.avanish.CMD.ERROR_MESSAGE;
import static com.bigbrain.avanish.FieldCharacters.ROBOT;
import static com.bigbrain.avanish.FieldCharacters.SPACE;
import static com.bigbrain.avanish.FieldCharacters.OBS1;
import static com.bigbrain.avanish.FieldCharacters.OBS2;
import static com.bigbrain.avanish.FieldCharacters.OBS3;
import static com.bigbrain.avanish.FieldCharacters.OBS4;
import static com.bigbrain.avanish.FieldCharacters.OBS5;
import static com.bigbrain.avanish.FieldCharacters.GOAL;
import static com.bigbrain.avanish.FieldCharacters.DOT;

/**
 * Field Class.
 * @author ufkzh
 */
public class Field {
    //X = j - coordinate starts from top left and is positive towards right - second index
    //Y = i - coordinate starts from top left and is positive downwards - first index
    private final int width;
    private final int height;
    private final char[][] myField;
    private int robotX;
    private int robotY;
    private int goalX;
    private int goalY;
    private boolean isRobotInit;
    private boolean isGoalInit;

    /**
     * Field class which contains the field of characters and methods to manipulate this field.
     * @param widthString  input string for field width
     * @param heightString input string for field height
     * @param scanner      scanner to get next line input
     * @throws IllegalCharsetNameException illegal charset
     */
    public Field(String widthString, String heightString, Scanner scanner) {
        width = Integer.parseInt(widthString);
        height = Integer.parseInt(heightString);
        //System.out.println("Making new field of size " + width + " by " + height);
        myField = new char[height][width];

        receiveField(scanner);

        //return true if not robot init or not goal init
        if (!isRobotInit || !isGoalInit) {
            throw new IllegalCharsetNameException(ERROR_MESSAGE);
        }

    }


    void receiveField(Scanner scanner) throws IllegalCharsetNameException {
        String input;
        char[] inputArr;

        for (int i = 0; i < height; i++) {
            input = scanner.nextLine();
            inputArr = input.toCharArray();
            if (input.length() != width) {
                throw new IllegalCharsetNameException(ERROR_MESSAGE);
            }
            for (int j = 0; j < width; j++) {
                switch (inputArr[j]) {
                    case SPACE, OBS1, OBS2, OBS3, OBS4, OBS5:
                        myField[i][j] = inputArr[j];
                        break;

                    case ROBOT:
                        myField[i][j] = ROBOT;
                        robotX = j;
                        robotY = i;
                        isRobotInit = true;
                        break;

                    case GOAL:
                        myField[i][j] = GOAL;
                        goalX = j;
                        goalY = i;
                        isGoalInit = true;
                        break;

                    default:
                        throw new IllegalCharsetNameException(ERROR_MESSAGE);

                }
            }
        }

        //hope I don't have to implement receiving more lines of terrain after first error without giving error messages.....
    }

    /**
     * Performs the move on the field, editing the field with the relevant characters.
     * @param dir  direction to move in
     * @param dist distance to move in direction (default 1 if no input)
     */
    public void move(String dir, int dist) {

        switch (dir) {
            case UP:
                for (int i = 0; i < dist; i++) {
                    if (myField[robotY - 1][robotX] == SPACE || myField[robotY - 1][robotX] == GOAL) {
                        myField[robotY][robotX] = SPACE;
                        robotY--;
                    } else {
                        break;
                    }
                }
                break;

            case DOWN:
                for (int i = 0; i < dist; i++) {
                    if (myField[robotY + 1][robotX] == SPACE || myField[robotY + 1][robotX] == GOAL) {
                        myField[robotY][robotX] = SPACE;
                        robotY++;
                    } else {
                        break;
                    }
                }
                break;

            case LEFT:
                for (int i = 0; i < dist; i++) {
                    if (myField[robotY][robotX - 1] == SPACE || myField[robotY][robotX - 1] == GOAL) {
                        myField[robotY][robotX] = SPACE;
                        robotX--;
                    } else {
                        break;
                    }
                }
                break;

            case RIGHT:
                for (int i = 0; i < dist; i++) {
                    if (myField[robotY][robotX + 1] == SPACE || myField[robotY][robotX + 1] == GOAL) {
                        myField[robotY][robotX] = SPACE;
                        robotX++;
                    } else {
                        break;
                    }
                }

                break;

            default:
                break;
        }

        myField[robotY][robotX] = ROBOT;

        //check if goal is not occupied by robot and reset the x
        if (!(robotY == goalY && robotX == goalX)) {
            myField[goalY][goalX] = GOAL;
        }
    }

    /**
     * Prints the field with the current path.
     * @param path input path
     */
    public void debugPath(ArrayDeque<String> path) {
        int tempY = robotY;
        int tempX = robotX;
        char[][] tempField = new char[height][width];
        //deep clone of field, otherwise changes are saved to current field
        for (int i = 0; i < height; i++) {
            tempField[i] = myField[i].clone();
        }
        if (path == null) {
            printField();
            return;
        }

        //cloning pathStack so that it is not changed if debug-path is called multiple times
        ArrayDeque<String> tempPathStack = path.clone();
        //removing last move - otherwise goal is replaced by .
        tempPathStack.removeLast();

        for (String s : tempPathStack) {
            switch (s) {
                case UP:
                    tempY--;
                    break;

                case DOWN:
                    tempY++;
                    break;

                case LEFT:
                    tempX--;
                    break;

                case RIGHT:
                    tempX++;
                    break;

                default:
                    break;
            }

            tempField[tempY][tempX] = DOT;
        }
        for (char[] ca : tempField) {
            System.out.println(String.valueOf(ca));
        }

    }

    /**
     * returns field width.
     * @return field width
     */
    public int getWidth() {
        return width;
    }

    /**
     * returns field height.
     * @return field height
     */
    public int getHeight() {
        return height;
    }

    /**
     * returns field.
     * @return field
     */
    public char[][] getMyField() {
        return myField;
    }

    /**
     * returns robot x coord.
     * @return robot x coord
     */
    public int getRobotX() {
        return robotX;
    }

    /**
     * returns robot Y coord.
     * @return robot Y coord
     */
    public int getRobotY() {
        return robotY;
    }

    /**
     * returns goal x coord.
     * @return goal x coord
     */
    public int getGoalX() {
        return goalX;
    }

    /**
     * returns goal Y coord.
     * @return goal Y coord
     */
    public int getGoalY() {
        return goalY;
    }

    /**
     * Prints the currently saved field.
     */
    public void printField() {
        for (char[] ca : myField) {
            System.out.println(String.valueOf(ca));
        }
    }
}


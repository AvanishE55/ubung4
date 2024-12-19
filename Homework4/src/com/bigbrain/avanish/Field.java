package com.bigbrain.avanish;

import java.util.ArrayDeque;
import java.util.Scanner;

import static com.bigbrain.avanish.CMD.DOWN;
import static com.bigbrain.avanish.CMD.RIGHT;
import static com.bigbrain.avanish.CMD.LEFT;
import static com.bigbrain.avanish.CMD.UP;
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
    private int width;
    private int height;
    private char[][] myField;
    private int robotX;
    private int robotY;
    private int goalX;
    private int goalY;
    private boolean isRobotInit;
    private boolean isGoalInit;

    /**
     * Field class which contains the field of characters and methods to manipulate this field.
     * @param s
     * @param s1
     * @param scanner
     */
    public Field(String s, String s1, Scanner scanner) {
        width = Integer.parseInt(s);
        height = Integer.parseInt(s1);
        System.out.println("Making new field of size " + width + " by " + height);
        myField = new char[height][width];
        try {
            receiveField(scanner);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(CMD.ERROR_MESSAGE);
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

    void printField() {
        for (char[] ca : myField) {
            System.out.println(String.valueOf(ca));
        }
    }

    void receiveField(Scanner scanner) throws Exception {
        String input;
        char[] inputArr;

        for (int i = 0; i < height; i++) {
            input = scanner.nextLine();
            inputArr = input.toCharArray();
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
                        throw new Exception(CMD.ERROR_MESSAGE + "Unrecognised Character in map");
                }
            }
        }

        if (!isRobotInit || !isGoalInit) {
            throw new Exception("Not init");
        }


    }

    /**
     * Performs the move on the field, editing the field with the relevant characters.
     * @param s
     * @param dist
     */
    public void move(String s, int dist) {

        switch (s) {
            case UP:
                for (int i = 0; i < dist; i++) {
                    if (myField[robotY - 1][robotX] == SPACE || myField[robotY - 1][robotX] == GOAL) {
                        myField[robotY][robotX] = SPACE;
                        robotY--;
                        myField[robotY][robotX] = ROBOT;
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
                        myField[robotY][robotX] = ROBOT;
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
                        myField[robotY][robotX] = ROBOT;
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
                        myField[robotY][robotX] = ROBOT;
                    } else {
                        break;
                    }
                }

                break;

            default:
                break;
        }
        //check if goal is not occupied by robot and reset the x
        if (!(robotY == goalY && robotX == goalX)) {
            myField[goalY][goalX] = GOAL;
        }
    }

    /**
     * Prints the field with the current path.
     * @param pathStack
     */
    public void debugPath(ArrayDeque<String> pathStack) {
        int tempY = robotY;
        int tempX = robotX;
        char[][] tempField = myField.clone();
        ArrayDeque<String> tempPathStack = pathStack;
        tempPathStack.removeFirst();
        for (String s : pathStack) {
            switch (s) {
                case UP:
                    tempField[tempY--][tempX] = DOT;
                    break;

                case DOWN:
                    tempField[tempY++][tempX] = DOT;
                    break;

                case LEFT:
                    tempField[tempY][tempX--] = DOT;
                    break;

                case RIGHT:
                    tempField[tempY][tempX++] = DOT;
                    break;

                default:
                    break;
            }

        }

        for (char[] ca : tempField) {
            System.out.println(String.valueOf(ca));
        }

    }
}


package com.company.Services;

public enum Direction {
        LEFT(1), DOWN(2),RIGHT(3), UP(4);

    private int numVal;

    Direction(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    public static Direction negateDirection(Direction direction){
        if (direction == RIGHT){return LEFT;}
        if (direction == LEFT){return RIGHT;}
        if (direction == DOWN){return UP;}
        return DOWN;
    }

}

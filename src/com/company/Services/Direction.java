package com.company.Services;

public enum Direction {
        LEFT(1), DOWN(2),RIGHT(3), UP(4) ,DOWNLEFT(5), DOWNRIGHT(6),UPRIGHT(7), UPLEFT(8) ;

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
        if (direction == DOWNLEFT){return DOWNRIGHT;}
        if (direction == DOWNRIGHT){return DOWNLEFT;}
        if (direction == UPRIGHT){return UPLEFT;}
        if (direction == UPLEFT){return UPRIGHT;}
        return DOWN;
    }

}

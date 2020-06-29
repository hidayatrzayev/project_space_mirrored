package com.company.Services;

public enum GameState {
     MAINMENU(1), RUNNING(2), INTMENU(3), RETRYMENU(4), DEAD(5), FINISH(6), PAUSED(7), EXIT(8);

    private int numVal;

    GameState(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}

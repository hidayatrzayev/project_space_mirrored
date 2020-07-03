package com.company.WorldObjects;

import com.company.Services.CircleMesh;

import java.awt.*;
import java.util.concurrent.ExecutionException;

public class BossEnemyShield extends A_InteractableObject {

    public static final Color SHIELD_COLOR = new Color(0.5f, 0.5f, 0.5f, 0.2f);
    public static final Color BORDER_COLOR = Color.LIGHT_GRAY;
    public static final int BORDER_MARGIN = 1;
    public static final int POSITION_MARGIN = 15;

    public BossEnemyShield(int bossPosX, int bossPosY, int bossSizeX, int bossSizeY) {
        super(bossPosX - POSITION_MARGIN, bossPosY - POSITION_MARGIN,
                bossSizeX + POSITION_MARGIN * 2, bossSizeY + POSITION_MARGIN * 2);
        this.mesh = new CircleMesh(0, 0, this.sizeX, this.sizeY);
    }

    public void update(int bossPosX, int bossPosY) {
        this.posX = bossPosX - POSITION_MARGIN;
        this.posY = bossPosY - POSITION_MARGIN;
        this.mesh = this.mesh.getrefreshedMesh(this.posX, this.posY);
    }

    @Override
    public void update(double elapsedTime) {}

    @Override
    public void draw(Graphics gc) {
        this.drawOvalBorder(gc);
        this.fillOval(gc);
    }

    private void drawOvalBorder(Graphics gc) {
        gc.setColor(BORDER_COLOR);
        gc.drawOval(this.posX - BORDER_MARGIN, this.posY - BORDER_MARGIN,
                this.sizeX + BORDER_MARGIN * 2, this.sizeY + BORDER_MARGIN * 2);
    }

    private void fillOval(Graphics gc) {
        gc.setColor(SHIELD_COLOR);
        gc.fillOval(this.posX, this.posY, this.sizeX, this.sizeY);
    }

    @Override
    public void collides(A_InteractableObject a_interactableObject) throws ExecutionException, InterruptedException {}

    @Override
    public int getMaxSize() {
        return Math.max(this.sizeX, this.sizeY);
    }
}

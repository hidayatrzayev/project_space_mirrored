package com.company.UIComponents;

import java.awt.*;

public class BossEnemyHealthIndicator {

    public static final Color FILL_COLOR = Color.RED;
    public static final Color BORDER_COLOR = Color.WHITE;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 10;
    public static final int POS_Y_MARGIN = 20;
    public static final int BORDER_THICKNESS = 16;
    public static final int FILL_PADDING = 2;

    private int totalHealth;
    private int posX;
    private int posY;
    private int bossSizeX;
    private int bossSizeY;

    public BossEnemyHealthIndicator(int totalHealth, int bossPosX, int bossPosY, int bossSizeX, int bossSizeY) {
        this.totalHealth = totalHealth;
        this.bossSizeX = bossSizeX;
        this.bossSizeY = bossSizeY;
        this.posX = (bossPosX + bossSizeX) / 2;
        this.posY = (bossPosY + bossSizeY) + POS_Y_MARGIN;
    }

    public void draw(Graphics gc, int currentHealth) {
        gc.setFont(new Font("default", Font.BOLD, BORDER_THICKNESS));
        gc.setColor(BORDER_COLOR);
        gc.drawRect(posX, posY, WIDTH, HEIGHT);
        gc.setColor(FILL_COLOR);
        gc.fillRect(posX + FILL_PADDING, posY + FILL_PADDING,
                this.calculateFillWidth(currentHealth), HEIGHT - FILL_PADDING);
    }

    public void update(int bossPosX, int bossPosY) {
        this.posX = (bossPosX + this.bossSizeX / 2) - (WIDTH / 2);
        this.posY = bossPosY + this.bossSizeY + POS_Y_MARGIN;
    }

    private int calculateFillWidth(int currentHealth) {
        return Math.round((WIDTH - FILL_PADDING * 2) * ((float) currentHealth / totalHealth));
    }
}

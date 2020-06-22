package com.company.Handlers;

import com.company.Movements.*;
import com.company.Services.BossEnemyDeserializer;
import com.company.Services.Deserializer;
import com.company.Services.GameState;
import com.company.Services.Utilities;
import com.company.Shootings.ShootCircle;
import com.company.Shootings.ShootDeathSpiral;
import com.company.Shootings.ShootStraight;
import com.company.Shootings.ShootStrategy;
import com.company.Systems.SessionSystem;
import com.company.WorldObjects.EnemyShot;
import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.BossEnemy;
import com.company.WorldObjects.Enemy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class EnemyHandler {

    public static final int MAX_SCREEN_ENEMIES = 1;

    private final List<BufferedImage> presets = new ArrayList<>(
            Arrays.asList(
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_1.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_2.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_3.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_4.png")))
            )
    );

    private final List<MoveStrategy> movementPatterns = new ArrayList<>(
            Arrays.asList(
                    new MoveStraightNormal(),
                    new MoveSinusoidNarrow(),
                    new MoveSinusoidWide(),
                    new MoveCircular()
            )
    );

    private final List<ShootStrategy> shootingPatterns = new ArrayList<>(
            Arrays.asList(
                    new ShootStraight(),
                    new ShootCircle(),
                    new ShootDeathSpiral()
            )
    );

    private int complexity;
    private List<A_InteractableObject> enemies;
    private List<A_InteractableObject> screenEnemies;
    private List<A_InteractableObject> enemyShots;
    private String bossEnemyId;
    private boolean bossFight;
    private BossEnemy bossEnemy;


    public EnemyHandler(int complexity, String bossEnemyId) throws IOException {
        this.complexity = complexity;
        this.bossEnemyId = bossEnemyId;
        this.enemies = new ArrayList<>();
        this.screenEnemies = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.bossFight = false;
        this.bossEnemy = this.getBossEnemy();
    }

    /**
     * Initializes the total list of enemies as well as the list of enemies currently being shown on the screen.
     *
     * @param amount - total amount of enemies in the level
     */
    public void spawnEnemies(int amount) throws IOException {
        for (int i = 0; i < MAX_SCREEN_ENEMIES; i++) {
            this.screenEnemies.add(createRandomEnemy());
        }
        for (int i = 0; i < amount - MAX_SCREEN_ENEMIES; i++) {
            this.enemies.add(createRandomEnemy());
        }
    }

    /**
     * Creates and adds the boss enemy to the screen.
     */
    private void spawnBossEnemy() {
        if (bossEnemy != null) {
            bossEnemy.setMoveStrategy(new MoveStraightNormal());
            bossEnemy.setShootStrategy(new ShootCircle());
            this.screenEnemies.add(bossEnemy);
            this.bossFight = true;
        }
    }

    /**
     * Respawns the enemy that flew over the screen without getting destroyed.
     *
     * @param enemy - enemy that is to be respawned
     */
    private void respawnEnemy(Enemy enemy) {
        enemy.setPosY(-enemy.getImg().getHeight());
        enemy.setPosX(50 + new Random().nextInt(Utilities.WIDTH - 100));
    }

    /**
     * Gets a particular boss enemy from the JSON files depending on the level.
     */
    private BossEnemy getBossEnemy() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray bossEnemies = (JSONArray) parser.parse(new FileReader("resources/Data/boss_enemies.json"));
            Iterator iterator = bossEnemies.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) parser.parse(iterator.next().toString());
                String id = (String) jsonObject.get("id");
                if (id.equals(this.bossEnemyId)) {
                    return deserializeBossEnemy(jsonObject);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Draws all the enemies and their shots.
     *
     * @param gc - {@code Graphics} object that draws on the screen
     */
    public void drawAll(Graphics gc) {
        this.drawEnemies(gc);
        this.drawEnemyShots(gc);
    }

    /**
     * Draws all the enemies that are currently to be shown on the screen.
     *
     * @param gc - {@code Graphics} object that draws on the screen
     */
    private void drawEnemies(Graphics gc) {
        this.screenEnemies.forEach((enemy) -> enemy.draw(gc));
    }


    /**
     * Draws all enemy shots
     */
    private void drawEnemyShots(Graphics gc) {
        this.enemyShots.forEach((shot) -> shot.draw(gc));
    }

    /**
     * Updates all the enemies on the screen and their respective shots.
     * Additionally randomly chooses enemies to shoot at the player.
     */
    public void updateAll(double elapsedTime) {
        if (this.screenEnemies.isEmpty() && this.enemies.isEmpty()) {
            if (!bossFight) {
                this.spawnBossEnemy();
            } else {
                return;
            }
        }

        this.shootAll(elapsedTime);
        this.updateEnemies(elapsedTime);
        this.updateEnemyShots(elapsedTime);
    }

    /**
     * Updates all currently rendered enemies on the screen, and if necessary
     * replaces the destroyed enemies with the new ones from the list of total enemies.
     */
    private void updateEnemies(double elapsedTime) {
        for (int i = 0; i < this.screenEnemies.size(); i++) {
            A_InteractableObject currentEnemy = this.screenEnemies.get(i);
            currentEnemy.update(elapsedTime);
            if (currentEnemy.isDestroyed()) {
                this.screenEnemies.remove(currentEnemy);
                if (!enemies.isEmpty()) {
                    this.screenEnemies.add(this.enemies.remove(0));
                }
            } else if (currentEnemy.getPosY() > Utilities.HEIGHT) {
                this.respawnEnemy((Enemy) currentEnemy);
            }
        }
    }

    /**
     * Updates all enemy shots
     */
    private void updateEnemyShots(double elapsedTime) {
        for (int i = this.enemyShots.size() - 1; i >= 0; i--) {
            EnemyShot shot = (EnemyShot) this.enemyShots.get(i);
            shot.update(elapsedTime);
            if (shot.isToRemove()) {
                this.enemyShots.remove(i);
            }
        }
    }

    /**
     * Randomly selects an enemy from the list of enemies currently shown on the screen
     * and initiates a shot. The shot will appear with the probability of 5%.
     */
    private void shootAll(double elapsedTime) {
        for (int i = 0; i < screenEnemies.size(); i++) {
            Enemy randomEnemy = (Enemy) screenEnemies.get(i);
            if (!randomEnemy.isExploding()) {
                randomEnemy.shoot(enemyShots, elapsedTime);
            }
        }
    }

    /**
     * Creates an enemy on a random location on the X axis and with a random image preset.
     *
     * @return newly created {@code Enemy} object
     */
    private Enemy createRandomEnemy() throws IOException {
        Random random = new Random();
        BufferedImage enemyShipImage = this.presets.get(random.nextInt(this.presets.size()));
        Enemy randomEnemy = new Enemy(random.nextInt(Utilities.WIDTH - 500) + 200, -enemyShipImage.getHeight(),
                enemyShipImage.getWidth(), enemyShipImage.getHeight(),
                enemyShipImage, 2);

        MoveStrategy randomMoveStrategy = movementPatterns.get(random.nextInt(movementPatterns.size()));
        ShootStrategy shootStrategy = shootingPatterns.get(random.nextInt(shootingPatterns.size()));
        randomEnemy.setMoveStrategy(randomMoveStrategy);
        randomEnemy.setShootStrategy(shootStrategy);
        if (randomMoveStrategy instanceof MoveCircular) {
            randomEnemy.setSpeed(200);
        }

        return randomEnemy;
    }

    /**
     * Deserializes a particular boss enemy from a JSON object to a Java object.
     */
    private BossEnemy deserializeBossEnemy(JSONObject jsonObject) {
        Deserializer<BossEnemy> deserializer = new BossEnemyDeserializer();
        return deserializer.deserialize(jsonObject);
    }

    /**
     * Returns the list of enemies currently shown on the screen.
     */
    public List<A_InteractableObject> getScreenEnemies() {
        return this.screenEnemies;
    }

    /**
     * Returns the list of enemy shots
     */
    public List<A_InteractableObject> getEnemyShots() {
        return this.enemyShots;
    }

    public boolean fightIsOver(){
        if(this.screenEnemies.isEmpty() && this.enemies.isEmpty() && isBossFight()){
            //TODO SessionSystem.getInstance().setGameState(GameState.INTMENU); WHEN MENU WILL BE IMPLEMENTED
            return true;
        }
        return false;
    }
    /**
     * Checks whether or not currently it is a boss fight.
     */
    public boolean isBossFight() {
        return this.bossFight;
    }
}

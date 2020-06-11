package com.company.Handlers;

import com.company.Services.BossEnemyDeserializer;
import com.company.Services.Deserializer;
import com.company.Services.Utilities;
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

    public static final int MAX_SCREEN_ENEMIES = 5;

    private final List<BufferedImage> presets = new ArrayList<>(
            Arrays.asList(
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_1.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_2.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_3.png"))),
                    ImageIO.read((getClass().getClassLoader().getResourceAsStream("Enemies/enemy_ship_4.png")))
            )
    );

    private int complexity;
    private List<A_InteractableObject> enemies;
    private List<A_InteractableObject> screenEnemies;
    private List<A_InteractableObject> enemyShots;
    private String bossEnemyId;
    private boolean bossFight;

    public EnemyHandler(int complexity, String bossEnemyId) throws IOException {
        this.complexity = complexity;
        this.bossEnemyId = bossEnemyId;
        this.enemies = new ArrayList<>();
        this.screenEnemies = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.bossFight = false;
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
    public void spawnBossEnemy() {
        BossEnemy bossEnemy = this.getBossEnemy();
        if (bossEnemy != null) {
            this.screenEnemies.add(bossEnemy);
            this.bossFight = true;
        }
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
     * Draws all the enemies that are currently to be shown on the screen.
     *
     * @param gc - {@code Graphics} object that draws on the screen
     */
    public void drawEnemies(Graphics gc) {
        for (A_InteractableObject enemy : this.screenEnemies) {
            enemy.draw(gc);
        }
    }

    /**
     * Updates all currently rendered enemies on the screen, and if necessary
     * replaces the destroyed enemies with the new ones from the list of total enemies.
     */
    public void updateEnemies() {
        for (int i = 0; i < this.screenEnemies.size(); i++) {
            A_InteractableObject currentEnemy = this.screenEnemies.get(i);
            currentEnemy.update();
            if (currentEnemy.isDestroyed()) {
                this.screenEnemies.remove(currentEnemy);
                if (!enemies.isEmpty()) {
                    this.screenEnemies.add((Enemy) this.enemies.remove(0));
                }
            }
        }
    }

    /**
     * Randomly selects an enemy from the list of enemies currently shown on the screen
     * and initiates a shot. The shot will appear with the probability of 5%.
     */
    public void shootRandomly() throws IOException {
        Random random = new Random();
        float chance = random.nextFloat();
        if (chance > 0.95 && !this.screenEnemies.isEmpty()) {
            int randomIndex = random.nextInt(this.screenEnemies.size());
            if (this.enemyShots.size() < complexity * 5 / 2) {
                Enemy randomEnemy = (Enemy) this.screenEnemies.get(randomIndex);
                this.enemyShots.add(randomEnemy.shoot());
            }
        }
    }

    /**
     * Draws all enemy shots
     */
    public void drawEnemyShots(Graphics gc) {
        for (A_InteractableObject shot : this.enemyShots) {
            shot.draw(gc);
        }
    }

    /**
     * Updates all enemy shots
     */
    public void updateEnemyShots() {
        for (int i = this.enemyShots.size() - 1; i >= 0; i--) {
            EnemyShot shot = (EnemyShot) this.enemyShots.get(i);
            shot.update();
            if (shot.toRemove) {
                this.enemyShots.remove(i);
            }
        }
    }

//    public void checkCollisions(List<PlayerShot> shots) {
//        for (Enemy enemy : this.screenEnemies) {
//            for (PlayerShot shot : shots) {
//                if (enemy.collides(shot)) {
//                    System.out.println("COLLISION!!!");
//                    enemy.damage();
//                    shot.toRemove = true;
//                }
//            }
//        }
//    }

    /**
     * Creates an enemy on a random location on the X axis and with a random image preset.
     *
     * @return newly created {@code Enemy} object
     */
    private Enemy createRandomEnemy() throws IOException {
        Random random = new Random();
        BufferedImage enemyShipImage = this.presets.get(random.nextInt(this.presets.size()));
        return new Enemy(50 + random.nextInt(Utilities.WIDTH - 100), -enemyShipImage.getHeight(),
                enemyShipImage.getWidth(), enemyShipImage.getHeight(),
                enemyShipImage, 2);
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
     * Checks whether or not currently it is a boss fight.
     */
    public boolean isBossFight() {
        return this.bossFight;
    }

    public List<A_InteractableObject> getEnemyShots() {
        return this.enemyShots;
    }

}

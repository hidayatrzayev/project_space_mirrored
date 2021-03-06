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
import com.company.Systems.BackgroundMusicPlayer;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EnemyHandler {

    public static final int MAX_SCREEN_ENEMIES = 6;
    public static final String BOSS_FIGHT_SOUND = "resources/Audio/boss_fight.wav";

    private static final Map<Integer, List<BufferedImage>> presets = new HashMap<>();
    private static BufferedImage bossFightLabel;


    private final List<MoveStrategy> movementPatterns = new ArrayList<>(
            Arrays.asList(
                    new MoveStraightSlow(),
                    new MoveStraightNormal(),
                    new MoveStraightFast(),
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
    private int currentLevel;
    private boolean bossFight;
    private int levelScreenEnemies;
    private List<A_InteractableObject> enemies;
    private List<A_InteractableObject> screenEnemies;
    private List<A_InteractableObject> enemyShots;
    private BossEnemy bossEnemy;
    private double bossFightSoundDuration = 2.0d;
    private double timeElapsedSinceLastEnemyKilled = 0.0d;


    static {
        try {
            loadPresets();
            bossFightLabel = ImageIO.read(new File("resources/Data/boss_fight_label.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public EnemyHandler(int complexity, String bossEnemyId, int currentLevel) throws IOException {
        this.complexity = complexity;
        this.currentLevel = currentLevel;
        this.bossFight = false;
        this.levelScreenEnemies = MAX_SCREEN_ENEMIES / complexity;
        this.enemies = new ArrayList<>();
        this.screenEnemies = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.bossEnemy = this.getBossEnemy(bossEnemyId);
    }

    private static void loadPresets() throws IOException {
        File dir = new File("resources/Enemies");
        for (File imageFile : Objects.requireNonNull(dir.listFiles())) {
            int level = getPresetLevel(imageFile);
            if (level < 0) {
                throw new IOException("Incorrect image name pattern");
            }

            loadPresetIntoLevel(level, imageFile);

            // add all presets into the 3rd level
            if (level != 3) {
                loadPresetIntoLevel(3, imageFile);
            }
        }
    }

    /**
     * Returns the level which the preset belongs to based on its name
     */
    private static int getPresetLevel(File imageFile) {
        String fileName = imageFile.getName();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }

        return -1;
    }

    /**
     * Adds the image preset to the corresponding level in the hash map.
     */
    private static void loadPresetIntoLevel(int level, File imageFile) {
        presets.computeIfPresent(level, (key, value) -> {
            try {
                value.add(ImageIO.read(imageFile));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return value;
        });
        presets.computeIfAbsent(level, (key) -> {
            List<BufferedImage> levelPresets = new ArrayList<>();
            try {
                levelPresets.add(ImageIO.read(imageFile));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return levelPresets;
        });
    }

    /**
     * Initializes the total list of enemies as well as the list of enemies currently being shown on the screen.
     *
     * @param amount - total amount of enemies in the level
     */
    public void spawnEnemies(int amount) throws IOException {
        levelScreenEnemies = Math.min(levelScreenEnemies, amount);
        for (int i = 0; i < levelScreenEnemies; i++) {
            this.screenEnemies.add(createRandomEnemy());
        }
        for (int i = 0; i < amount - levelScreenEnemies; i++) {
            this.enemies.add(createRandomEnemy());
        }
    }

    /**
     * Creates and adds the boss enemy to the screen.
     */
    private void spawnBossEnemy() {
        bossEnemy.setMoveStrategy(new MoveBossEnemy());
        this.setBossEnemyShootStrategy();
        this.screenEnemies.add(bossEnemy);
        this.bossFight = true;
    }

    private void setBossEnemyShootStrategy() {
        int filteredShootingPatternsAmount = this.filterShootingPatterns().size();
        int patternIndex = Math.min(filteredShootingPatternsAmount, this.shootingPatterns.size() - 1);
        this.bossEnemy.setShootStrategy(this.shootingPatterns.get(patternIndex));
    }

    /**
     * Respawns the enemy that flew over the screen without getting destroyed.
     *
     * @param enemy - enemy that is to be respawned
     */
    private void respawnEnemy(Enemy enemy) {
        enemy.setPosY(-enemy.getImg().getHeight());
        enemy.setPosX(new Random().nextInt(Utilities.WIDTH - 600) + 200);
    }

    /**
     * Gets a particular boss enemy from the JSON files depending on the level.
     */
    private BossEnemy getBossEnemy(String bossEnemyId) {
        JSONParser parser = new JSONParser();

        try {
            JSONArray bossEnemies = (JSONArray) parser.parse(new FileReader("resources/Data/boss_enemies.json"));
            Iterator iterator = bossEnemies.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) parser.parse(iterator.next().toString());
                String id = (String) jsonObject.get("id");
                if (id.equals(bossEnemyId)) {
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
        if (this.bossFightSoundPlaying()) {
            this.drawBossFightLabel(gc);
        }
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

    private void drawBossFightLabel(Graphics gc) {
        gc.drawImage(bossFightLabel,
                Utilities.WIDTH / 2 - bossFightLabel.getWidth() / 2,
                Utilities.HEIGHT / 2 - bossFightLabel.getHeight() / 2,
                null);
    }

    /**
     * Updates all the enemies on the screen and their respective shots.
     * Additionally randomly chooses enemies to shoot at the player.
     */
    public void updateAll(double elapsedTime) {
        if (this.screenEnemies.isEmpty() && this.enemies.isEmpty()) {
            if (!bossFight && !this.bossFightSoundPlaying()) {
                this.playBossFightSound(elapsedTime);
            }

            this.timeElapsedSinceLastEnemyKilled += elapsedTime;

            if (this.isTimeToSpawnBossEnemy()) {
                this.spawnBossEnemy();
                this.timeElapsedSinceLastEnemyKilled = 0;
            }
        }

        try {
            this.shootAll(elapsedTime);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

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
    private void shootAll(double elapsedTime) throws IOException {
        for (A_InteractableObject screenEnemy : screenEnemies) {
            Enemy currentEnemy = (Enemy) screenEnemy;
            if (!currentEnemy.isExploding()) {
                currentEnemy.shoot(enemyShots, elapsedTime);
            }
        }
    }

    /**
     * Creates an enemy on a random location on the X axis and with a random image preset.
     *
     * @return newly created {@code Enemy} object
     */
    private Enemy createRandomEnemy() {
        Random random = new Random();
        List<BufferedImage> currentLevelPresets = presets.get(currentLevel);
        BufferedImage enemyShipImage = currentLevelPresets.get(random.nextInt(currentLevelPresets.size()));
        Enemy randomEnemy = new Enemy(random.nextInt(Utilities.WIDTH - 600) + 200, -enemyShipImage.getHeight(),
                enemyShipImage.getWidth(), enemyShipImage.getHeight(),
                enemyShipImage, 2);

        MoveStrategy randomMoveStrategy = movementPatterns.get(random.nextInt(movementPatterns.size()));

        List<ShootStrategy> filteredShootings = this.filterShootingPatterns();
        ShootStrategy shootStrategy = shootingPatterns.get(random.nextInt(filteredShootings.size()));
        randomEnemy.setMoveStrategy(randomMoveStrategy);
        randomEnemy.setShootStrategy(shootStrategy);

        if (randomMoveStrategy instanceof MoveCircular) {
            randomEnemy.setSpeed(200);
        }

        return randomEnemy;
    }

    /**
     * Filters the shooting patterns according to the complexity of the level and
     * returns a list of acceptable shooting patterns for the current level
     */
    private List<ShootStrategy> filterShootingPatterns() {
        return this.shootingPatterns
                .stream()
                .filter(pattern -> this.shootingPatterns.indexOf(pattern) + 1 <= this.complexity)
                .collect(Collectors.toList());
    }

    /**
     * Deserializes a particular boss enemy from a JSON object to a Java object.
     */
    private BossEnemy deserializeBossEnemy(JSONObject jsonObject) {
        Deserializer<BossEnemy> deserializer = new BossEnemyDeserializer();
        return deserializer.deserialize(jsonObject);
    }

    /**
     * Starts the thread which plays the "Boss Fight" sound
     */
    private void playBossFightSound(double elapsedTime) {
        (new Thread(new BackgroundMusicPlayer(BOSS_FIGHT_SOUND))).start();
        timeElapsedSinceLastEnemyKilled += elapsedTime;
    }

    /**
     * Checks if it's already time to spawn the boss enemy
     */
    private boolean isTimeToSpawnBossEnemy() {
        return this.timeElapsedSinceLastEnemyKilled >= this.bossFightSoundDuration;
    }

    /**
     * Checks if the "Boss Fight" sound is still playing (duration should be approx. 2 seconds)
     * @return
     */
    private boolean bossFightSoundPlaying() {
        return this.timeElapsedSinceLastEnemyKilled > 0 &&
                timeElapsedSinceLastEnemyKilled < this.bossFightSoundDuration;
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

            SessionSystem.getInstance().setGameState(GameState.INTMENU);
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

    public BossEnemy getBossEnemy() {
        return this.bossEnemy;
    }
}

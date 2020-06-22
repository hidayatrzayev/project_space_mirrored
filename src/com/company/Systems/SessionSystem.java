package com.company.Systems;

import com.company.Services.*;
import com.company.WorldObjects.Player;
import com.company.Main.Universe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;



public class SessionSystem {

    private String username;
    private int currentScore;
    private int currentLevel;
    private Player playerState;
    private Universe universe;
    HashMap<String, Universe> levels = new HashMap<>();
    private GameState gameState;


    private static SessionSystem ourInstance = new SessionSystem();

    public static SessionSystem getInstance() {
        return ourInstance;
    }

    private SessionSystem() {

    }

    public Universe getUniverse() {
        return universe;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int levelScore) {
        this.currentScore += levelScore;
    }

    public int getLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Player getPlayerState() {
        return playerState;
    }

    public void setPlayerState(Player playerState) {
        this.playerState = playerState;
    }

    public int getNumberOfLevels(){return this.levels.size();}
    @Override
    public String toString() {
        return "{" +
                "\"username\":\"" + username + '\"' +
                ",\"currentScore\":\"" + currentScore + "\"" +
                ",\"currentLevel\":\"" + currentLevel + "\"" +
                ",\"playerState\":" + playerState.toString() +
                ",\"universe\":" + universe.toString() +
                '}';
    }

    public void saveProgress(){
        try {
            FileWriter myWriter = new FileWriter("resources/Data/progress.json");
            myWriter.write(this.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProgress(){
        this.parseLevels();
        try {
            JSONParser parser = new JSONParser();
            JSONObject sessionSystem = (JSONObject)parser.parse(new FileReader("resources/Data/progress.json"));
            this.username =(String)sessionSystem.get("username");
            this.currentScore = Integer.valueOf((String)sessionSystem.get("currentScore"));
            this.currentLevel = Integer.valueOf((String)sessionSystem.get("currentLevel"));
            this.playerState = deserializePlayer((JSONObject)sessionSystem.get("playerState"));
            this.universe = deserializeUniverse((JSONObject)sessionSystem.get("universe"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gameState = GameState.RUNNING;
    }

    public void startNewGame(){
        this.parseLevels();
        currentLevel = 1;
        universe = levels.get(String.valueOf(currentLevel));
        currentScore = 0;
        this.gameState = GameState.RUNNING;
    }

    public void nextLevel(){
        this.currentLevel++;
        this.universe = levels.get(String.valueOf(currentLevel));
    }


    private Universe deserializeUniverse(JSONObject jsonObject){
        Deserializer deserializer = new UniverseDeserializer();
        return (Universe) deserializer.deserialize(jsonObject);
    }

    private Player deserializePlayer(JSONObject jsonObject){
        Deserializer deserializer = new PlayerDeserializer();
        return (Player) deserializer.deserialize(jsonObject);
    }

    private void parseLevels(){
        JSONParser parser = new JSONParser();
        try {
            JSONArray levels = (JSONArray)parser.parse(new FileReader("resources/Data/universe.json"));
            Iterator iterator = levels.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) parser.parse(iterator.next().toString());
                Universe universe = deserializeUniverse(jsonObject);
                this.levels.put(String.valueOf(universe.getLevel()), universe);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}

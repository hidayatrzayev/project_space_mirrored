package com.company.Services;

import com.company.Main.Universe;
import org.json.simple.JSONObject;

public class UniverseDeserializer implements Deserializer<Universe>{
    @Override
    public Universe deserialize(JSONObject jsonObject) {
        Universe universe = null;
        try {
            int level =  Integer.valueOf((String)jsonObject.get("level"));
            String background = (String)jsonObject.get("backgroundPath");
            int complexity = Integer.valueOf((String)jsonObject.get("complexity"));
            int numberOfEnemies = Integer.valueOf((String)jsonObject.get("numberOfEnemies"));
            String bossEnemy = (String)jsonObject.get("bossEnemy");

            universe = new Universe(level, background, complexity, numberOfEnemies, bossEnemy);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return universe;
    }
}

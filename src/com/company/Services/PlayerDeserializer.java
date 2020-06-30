package com.company.Services;

import com.company.WorldObjects.Player;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;

public class PlayerDeserializer implements Deserializer<Player> {

    @Override
    public Player deserialize(JSONObject jsonObject) {
        Player player = null;
        try {
            int posX =  Integer.valueOf((String)jsonObject.get("posX"));
            int posY = Integer.valueOf((String)jsonObject.get("posY"));
            int sizeX = Integer.valueOf((String)jsonObject.get("sizeX"));
            int sizeY = Integer.valueOf((String)jsonObject.get("sizeY"));
            int health = Integer.valueOf((String)jsonObject.get("health"));
            player = new Player(posX, posY, sizeX, sizeY, ImageIO.read((getClass().getClassLoader().getResourceAsStream("Players/player_ship.png"))), health);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return player;
    }
}

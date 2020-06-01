package com.company;

import org.json.simple.JSONObject;
import javax.imageio.ImageIO;

public class PlayerDeserializer implements Deserializer<Player> {

    @Override
    public Player deserialize(JSONObject jsonObject) {
        Player player = null;
        try {
            int posX =  Integer.valueOf((String)jsonObject.get("posX"));
            int posY = Integer.valueOf((String)jsonObject.get("posY"));
            int size = Integer.valueOf((String)jsonObject.get("size"));
            player = new Player(posX, posY, size, ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/player_ship.png"))));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return player;
    }
}

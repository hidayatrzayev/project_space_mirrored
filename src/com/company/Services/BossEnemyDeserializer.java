package com.company.Services;

import com.company.WorldObjects.BossEnemy;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BossEnemyDeserializer implements Deserializer<BossEnemy> {

    @Override
    public BossEnemy deserialize(JSONObject jsonObject) {
        try {
            int health = Math.toIntExact((long) jsonObject.get("health"));
            int speed = Math.toIntExact((long) jsonObject.get("speed"));
            String presetPath = (String) jsonObject.get("preset");
            BufferedImage preset = ImageIO.read((getClass().getClassLoader().getResourceAsStream(presetPath)));
            return new BossEnemy(Utilities.WIDTH / 2 - preset.getWidth() / 2, -preset.getHeight(),
                    preset.getWidth(), preset.getHeight(), preset, speed, health);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }
}

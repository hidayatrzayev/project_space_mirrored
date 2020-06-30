package com.company.Systems;

import com.company.Services.Utilities;
import com.company.WorldObjects.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PhysicsSystem {

    private List<List<A_InteractableObject>> worldObjects;

    public PhysicsSystem(List<List<A_InteractableObject>> worldObjects) {
        this.worldObjects = worldObjects;
    }

    public synchronized void checkCollisions() {
        (new Thread(() -> {
            try {
            for (int a_interactableObjects = 0; a_interactableObjects < worldObjects.size(); a_interactableObjects++) {
                for (int a_interactableObject = 0; a_interactableObject < worldObjects.get(a_interactableObjects).size(); a_interactableObject++) {
                    if (worldObjects.get(a_interactableObjects).get(a_interactableObject).isDestroyed()) { continue; }
                    for (int worldObjectList = a_interactableObjects; worldObjectList < worldObjects.size(); worldObjectList++) {
                        for (int worldObject = 0; worldObject < worldObjects.get(worldObjectList).size(); worldObject++) {
                            if (worldObjects.get(a_interactableObjects).get(a_interactableObject).equals(worldObjects.get(worldObjectList).get(worldObject)) || worldObjects.get(worldObjectList).get(worldObject).isDestroyed()) { continue; }
                            if (Utilities.distance(worldObjects.get(a_interactableObjects).get(a_interactableObject).getPosX() + worldObjects.get(a_interactableObjects).get(a_interactableObject).getSizeX() / 2,
                                    worldObjects.get(a_interactableObjects).get(a_interactableObject).getPosY() + worldObjects.get(a_interactableObjects).get(a_interactableObject).getSizeY() / 2,
                                    worldObjects.get(worldObjectList).get(worldObject).getPosX() + worldObjects.get(worldObjectList).get(worldObject).getSizeX() / 2,
                                    worldObjects.get(worldObjectList).get(worldObject).getPosY() + worldObjects.get(worldObjectList).get(worldObject).getSizeY() / 2) < Utilities.hypotenuse(worldObjects.get(worldObjectList).get(worldObject).getMaxSize(), worldObjects.get(a_interactableObjects).get(a_interactableObject).getMaxSize())) {
                                check(a_interactableObjects, a_interactableObject, worldObjectList, worldObject);
                                check(worldObjectList, worldObject, a_interactableObjects, a_interactableObject);
                            }
                        }
                    }

                }
        }
            } catch (Exception e) {
                //Object is already destroyed,
            }
    })).start();


    }

    private void check(int a_interactableObjects, int a_interactableObject, int worldObjectList, int worldObject) {
        try {
            worldObjects.get(a_interactableObjects).get(a_interactableObject).collides(worldObjects.get(worldObjectList).get(worldObject));
        } catch (Exception e) {
            //Object is already destroyed,
        }
    }
}

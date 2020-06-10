package com.company;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {

    private List<A_InteractableObject> explodingObjects = new ArrayList<>();
    private List<List<A_InteractableObject>> worldObjects;

    public PhysicsSystem(List<List<A_InteractableObject>> worldObjects) {
        this.worldObjects = worldObjects;
    }

    public void checkCollisions(){
        for(List<A_InteractableObject> objectGroup : worldObjects){
            for(A_InteractableObject object : objectGroup){
                worldObjects.forEach(a_interactableObjects -> a_interactableObjects.forEach(a_interactableObject -> {
                    if (!object.equals(a_interactableObject)) {
                        object.collide(a_interactableObject);
                    }
                }));
            }
        }
        this.updateExplosions();
    }

    private void updateExplosions(){
        explodingObjects.forEach(a_interactableObject -> a_interactableObject.explosionStep++);
    }
}

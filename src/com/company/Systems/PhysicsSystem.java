package com.company.Systems;

import com.company.WorldObjects.*;

import java.util.List;

public class PhysicsSystem {


    private List<List<A_InteractableObject>> worldObjects;

    public PhysicsSystem(List<List<A_InteractableObject>> worldObjects) {
        this.worldObjects = worldObjects;
    }

    public void checkCollisions(){
        for(List<A_InteractableObject> objectGroup : worldObjects){
            for(A_InteractableObject object : objectGroup){
                worldObjects.forEach(a_interactableObjects -> a_interactableObjects.forEach(object::collides));
            }
        }

    }

}

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

    public void checkCollisions() {
        (new Thread(() -> {
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
    })).start();

        }

    private void check(int a_interactableObjects, int a_interactableObject, int worldObjectList, int worldObject) {
        try {
            worldObjects.get(a_interactableObjects).get(a_interactableObject).collides(worldObjects.get(worldObjectList).get(worldObject));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


//        for(List<A_InteractableObject> objectGroup : worldObjects) {
//            for (A_InteractableObject object : objectGroup) {
//                worldObjects.forEach(a_interactableObjects -> a_interactableObjects.forEach(object::collides));
//            }
//        }

//        checkedObjects.clear();
//        tasks.clear();
//        for (int i = 0; i < MAX_T; i++){
//            Runnable runnable = new Task(worldObjects.get(i),worldObjects);
//            tasks.add(runnable);
//        }
//
//
//        tasks.forEach(runnable -> pool.execute(runnable));
////        pool.shutdown();
//    }
//    private class Task implements Runnable {
//
//        private List<List<A_InteractableObject>> worldObjects;
//        private List<A_InteractableObject> a_interactableObjects;
//
//        public Task(List<A_InteractableObject> a_interactableObjects, List<List<A_InteractableObject>> worldObjects) {
//            this.a_interactableObjects = a_interactableObjects;
//            this.worldObjects = worldObjects;
//        }
//
//        public void run() {
//
//            for (int a_interactableObject = 0; a_interactableObject < a_interactableObjects.size()-1; a_interactableObject++){
//                checkedObjects.add(a_interactableObjects.get(a_interactableObject));
//                for (int worldObjectList = 0; worldObjectList < worldObjects.size()-1; worldObjectList++){
//                    if (!worldObjects.get(worldObjectList).equals(a_interactableObjects)){
//                        for (int worldObject = 0; worldObject < worldObjects.get(worldObjectList).size()-1; worldObject++){
//                            int index = worldObjectList;
//                            int index2 = worldObject;
//                            if (!checkedObjects.contains(worldObjects.get(worldObjectList).get(worldObject))) {
//                                a_interactableObjects.get(a_interactableObject).collides(worldObjects.get(worldObjectList).get(worldObject));
//                                worldObjects.get(worldObjectList).get(worldObject).collides(a_interactableObjects.get(a_interactableObject));
//                                checkedObjects.add(worldObjects.get(worldObjectList).get(worldObject));
//                            }
//                        }
//                    }
//                }
//            }
//
//            for(List<A_InteractableObject> objectGroup : worldObjects){
//                for(A_InteractableObject object : objectGroup){
//                    worldObjects.forEach(a_interactableObjects -> a_interactableObjects.forEach(object::collides));
//                }
//            }
//        }
//    }

//    }
}

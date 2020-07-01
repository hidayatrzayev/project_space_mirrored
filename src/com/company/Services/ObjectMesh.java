package com.company.Services;


import java.awt.*;
import java.util.concurrent.ExecutionException;

public interface ObjectMesh<T> {

    boolean intersects(ObjectMesh other) throws ExecutionException, InterruptedException;
    ObjectMesh getrefreshedMesh(int posX, int posY);
    Shape getMesh();
}

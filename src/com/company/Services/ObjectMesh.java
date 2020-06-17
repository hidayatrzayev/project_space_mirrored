package com.company.Services;


import java.awt.*;

public interface ObjectMesh<T> {

    boolean intersects(ObjectMesh other);
    ObjectMesh getrefreshedMesh(int posX, int posY);
    Shape getMesh();
}

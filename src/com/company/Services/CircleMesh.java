package com.company.Services;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


public class CircleMesh implements ObjectMesh<CircleMesh> {

    private Ellipse2D mesh;
    private int posX;
    private int posY;
    private double x;
    private double y;
    private double width;
    private double height;

    public CircleMesh(double x, double y,  double width, double height) {
        this.x= x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.createMesh();
    }

    private void createMesh(){
        mesh = new Ellipse2D.Double(posX + x,  posY + y, width, height);
    }

    public ObjectMesh getrefreshedMesh(int posX, int posY){
        this.posY = posY;
        this.posX = posX;
        this.createMesh();
        return this;
    }

    public boolean intersects(ObjectMesh other) {
        Area thisMesh = new Area(this.mesh);
        thisMesh.intersect(new Area(other.getMesh()));
        return !thisMesh.isEmpty();
    }

    public Ellipse2D getMesh() {
        return mesh;
    }

}

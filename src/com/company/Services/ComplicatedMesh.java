package com.company.Services;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.FlatteningPathIterator;
import java.awt.image.BufferedImage;

public class ComplicatedMesh<T> implements ObjectMesh<T> {


    BufferedImage image;
    private Polygon mesh;
    private int posX;
    private int posY;

    public ComplicatedMesh(BufferedImage image) {
        this.image = image;
        this.mesh = getPolygonOutline(image);
    }


    public Polygon getPolygonOutline(BufferedImage image) {
        Area a  = getOutline(image, new Color(0,0,0,0),false,0); // 10 or whatever color tolerance you want
        Polygon p = new Polygon();
        FlatteningPathIterator fpi = new FlatteningPathIterator(a.getPathIterator(null), 0.1); // 0.1 or how sloppy you want it
        double[] pts = new double[6];
        while (!fpi.isDone()) {
            switch (fpi.currentSegment(pts)) {
                case FlatteningPathIterator.SEG_MOVETO:
                case FlatteningPathIterator.SEG_LINETO:
                    p.addPoint((int) pts[0], (int) pts[1]);
                    break;
            }
            fpi.next();
        }
        return p;
    }


    public ObjectMesh getrefreshedMesh(int positionX, int positionY){
        mesh.translate(positionX - posX, positionY - posY);
        this.posY = positionY;
        this.posX = positionX;
        return this;
    }

    public boolean intersects(ObjectMesh other) {
        Area thisMesh = new Area(this.mesh);
        thisMesh.intersect(new Area(other.getMesh()));
        return !thisMesh.isEmpty();
    }


    public static Area getOutline(BufferedImage image, Color color, boolean include, int tolerance) {
        Area area = new Area();
        for (int x=0; x<image.getWidth(); x+=5) {
            for (int y=0; y<image.getHeight(); y+=5) {
                Color pixel = new Color(image.getRGB(x,y));
                if (include) {
                    if (isIncluded(color, pixel, tolerance)) {
                        Rectangle r = new Rectangle(x,y,5,5);
                        area.add(new Area(r));
                    }
                } else {
                    if (!isIncluded(color, pixel, tolerance)) {
                        Rectangle r = new Rectangle(x,y,5,5);
                        area.add(new Area(r));
                    }
                }
            }
        }
        return area;
    }


    public static boolean isIncluded(Color target, Color pixel, int tolerance) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return(
                (rP-tolerance<=rT) && (rT<=rP+tolerance) &&
                        (gP-tolerance<=gT) && (gT<=gP+tolerance) &&
                        (bP-tolerance<=bT) && (bT<=bP+tolerance) );
    }




    public Polygon getMesh() {
        return mesh;
    }
}

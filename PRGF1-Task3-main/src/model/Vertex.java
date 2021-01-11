package model;

import transforms.Mat4;
import transforms.Point3D;

public class Vertex {
    final private Point3D position;
    //color

    public Vertex(double x, double y, double z) {
        position = new Point3D(x,y,z);
    }

    public Vertex(Point3D p) {
        position = p;
    }

    public Point3D getPosition() {
        return position;
    }

    public Vertex transform(Mat4 model){
        return new Vertex(position.mul(model));
    }
}

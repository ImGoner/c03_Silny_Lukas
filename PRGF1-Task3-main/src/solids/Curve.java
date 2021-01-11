package solids;

import model.Solid;
import model.Vertex;
import org.javatuples.Triplet;
import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Curve extends Solid {
    private double temp =1;
    public Curve() {
        Cubic cub = new Cubic(Cubic.BEZIER,new Point3D(0,0,0),new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(1,1,0));
        for (double i = 0; i <=1; i+=0.05) {getVertices().add(new Vertex(cub.compute(i)));}
        Cubic cub2 = new Cubic(Cubic.BEZIER,new Point3D(0,0,1),new Point3D(1,0,1),new Point3D(0,1,1),new Point3D(1,1,1));
        for (double i = 0; i <=1; i+=0.05) {getVertices().add(new Vertex(cub2.compute(i)));}
        for (int j = 1; j < getVertices().size()-1; j++) {if (!(j==19||j==39)){getIndices().add(new Triplet<>(j,j+1,getColor()));}}
        for (int k = 0; k < 20; k++){if (!(k==20||k==39)){getIndices().add(new Triplet<>(k,k+20,getColor()));}}




    }

    @Override
    protected void animate() {
        super.animate();
        double temp2 = (Math.sin(temp));

        temp+=Math.PI/8;
        if (temp2>0) {
            move(0,0,-0.5);
        }
        else if(temp2<0){
            move(0,0,0.5);
        }
    }
}

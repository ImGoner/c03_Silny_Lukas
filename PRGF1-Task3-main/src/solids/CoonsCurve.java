package solids;

import model.Solid;
import model.Vertex;
import org.javatuples.Triplet;
import transforms.Cubic;
import transforms.Point3D;

public class CoonsCurve extends Solid {
    public CoonsCurve(){
        double x = 0;
        double y = 0;
        double z = 0;


        Cubic cub = new Cubic(Cubic.COONS, new Point3D(x-1,y-1,z-1),new Point3D(x+2,y+1,z+2),new Point3D(x-1,y+2,z-1),new Point3D(x+2,y+2,z+2));
        for (double i = 0; i <=1; i+=0.05) {getVertices().add(new Vertex(cub.compute(i)));}

        for (int j = 1; j < getVertices().size()-1; j++) {if (true){getIndices().add(new Triplet<>(j,j+1,getColor()));}}
    }
}

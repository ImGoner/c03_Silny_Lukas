package solids;

import model.Solid;
import model.Vertex;
import org.javatuples.Triplet;

import java.awt.*;

public class Cube extends Solid {

    public Cube() {
        super();
        getVertices().add(new Vertex(1,0,0));
        getVertices().add(new Vertex(0,1,0));
        getVertices().add(new Vertex(0,0,1));
        getVertices().add(new Vertex(1,1,0));
        getVertices().add(new Vertex(1,1,1));
        getVertices().add(new Vertex(0,1,1));
        getVertices().add(new Vertex(1,0,1));
        getVertices().add(new Vertex(0,0,0));

        addEdge(0,3,this.getColor());
        addEdge(0,7,this.getColor());
        addEdge(0,6,this.getColor());

        addEdge(1,3,this.getColor());
        addEdge(1,7,this.getColor());
        addEdge(1,5,this.getColor());

        addEdge(2,5,this.getColor());
        addEdge(2,6,this.getColor());
        addEdge(2,7,this.getColor());

        addEdge(4,6,this.getColor());
        addEdge(4,5,this.getColor());
        addEdge(4,3,this.getColor());

    }
    @Override
    protected void animate() {
        super.animate();
        rotate(Math.PI/180,Math.PI/180,Math.PI/180);
    }

    @Override
    protected void resetColor() {
        super.resetColor();
    }
}

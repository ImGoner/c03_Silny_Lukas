package solids;

import model.Solid;
import model.Vertex;

import java.awt.*;

public class Axis extends Solid {
    public Axis() {
        getVertices().add(new Vertex(0,0,0));
        getVertices().add(new Vertex(1,0,0));
        getVertices().add(new Vertex(0,0,0));
        getVertices().add(new Vertex(0,1,0));
        getVertices().add(new Vertex(0,0,0));
        getVertices().add(new Vertex(0,0,1));

        addEdge(0,1, Color.RED);
        addEdge(2,3, Color.GREEN);
        addEdge(4,5, Color.BLUE);

    }
    //cervena x (0,0,0) do (10,0,0)
        //zelena y (0,0,0) do (0,10,0)
        //modra z (0,0,0) do (0,0,10)

}

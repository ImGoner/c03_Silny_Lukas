package solids;

import model.Solid;
import model.Vertex;

import java.awt.*;

public class Tetrahedron extends Solid{
    int index =0;
    double temp =1;
    public Tetrahedron() {
        super();
        setColor(Color.WHITE);
        getVertices().add(new Vertex(0,0,0));
        getVertices().add(new Vertex(1,0,0));
        getVertices().add(new Vertex(0,1,0));
        getVertices().add(new Vertex(0,0,1));

        addEdge(0,1,getColor());
        addEdge(0,2,getColor());
        addEdge(0,3,getColor());
        addEdge(2,1,getColor());
        addEdge(3,1,getColor());
        addEdge(3,2,getColor());
    }

    @Override
    protected void animate() {
        super.animate();
        //rotate(Math.PI/180,Math.PI/180,Math.PI/180);

        double temp2 = (Math.sin(temp));

        temp+=Math.PI/8;
        if (temp2>0) {
            move(0,0,-0.5);
        }
        else if(temp2<0)
            move(0,0,0.5);
        }

    }

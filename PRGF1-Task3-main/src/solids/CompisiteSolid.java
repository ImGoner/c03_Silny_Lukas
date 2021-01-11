package solids;

import model.Solid;
import org.javatuples.Triplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CompisiteSolid extends Solid {
    List<Solid> solids;

    public CompisiteSolid(){
        solids = new ArrayList<>();
        solids.add(new Cube());
        Sphere sphere = new Sphere();
        sphere.scale(2,2,2);
        solids.add(sphere);
        mergeSolids();
    }

    private void mergeSolids(){
        for (Solid item:solids
             ) {
            getVertices().addAll(item.getVertices());
            getIndices().addAll(item.getIndices());
        }
    }

    @Override
    protected void resetColor() {
        for (Solid item:solids
             ) {
            item.resetDefaultColor();
        }
        mergeSolids();
    }
}

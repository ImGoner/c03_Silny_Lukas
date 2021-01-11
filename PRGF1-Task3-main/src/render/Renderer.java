package render;

import model.Scene;
import model.Solid;
import model.Vertex;
import rasterize.LineRasterizer;
import rasterize.Raster;
import transforms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private Raster raster;
    private LineRasterizer lineRasterizer;
    private Mat4 model = new Mat4Identity();
    private Mat4 view = new Mat4Identity();
    private Mat4 proj = new Mat4Identity();

    public Renderer(Raster raster, LineRasterizer lineRasterizer) {
        this.raster = raster;
        this.lineRasterizer = lineRasterizer;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    public void render(Scene scene){
        for (Solid solid : scene.getSolids()){
            render(solid);
        }
    }

    public void render(Solid solid){
        //transformace Model View TZO(proj)

        Mat4 m = model.mul(solid.getModel()).mul(view).mul(proj);
        List<Vertex> tempVertices = new ArrayList<>();

        for (Vertex vertex : solid.getVertices()){
            tempVertices.add(vertex.transform(m));
        }
        for (int i = 0;i<solid.getIndices().size();i++){
           Object[] jj =  solid.getIndices().get(i).toArray();
           if (checkView(tempVertices.get((Integer) jj[0])) && checkView(tempVertices.get((Integer) jj[1]))){
               renderEdge(tempVertices.get((Integer) jj[0]), tempVertices.get((Integer) jj[1]), (Color) jj[2]);
            }
        }
    }


    private void renderEdge(Vertex a, Vertex b,Color c){

        Vec3D vA = new Vec3D();
        Vec3D vB = new Vec3D();

        if(a.getPosition().dehomog().isPresent()){
            vA = a.getPosition().dehomog().get();
        }

        if(b.getPosition().dehomog().isPresent()){
            vB = b.getPosition().dehomog().get();
        }
        //if (check(vB)&&check(vA)) {
            int x1 = (int) ((vA.getX() + 1) * (raster.getWidth() - 1) / 2);
            int x2 = (int) ((vB.getX() + 1) * (raster.getWidth() - 1) / 2);
            int y1 = (int) ((1 - vA.getY()) * (raster.getHeight() - 1) / 2);
            int y2 = (int) ((1 - vB.getY()) * (raster.getHeight() - 1) / 2);

            lineRasterizer.rasterize(x1, y1, x2, y2, c);
        //}
    }
/*
    private boolean check(Vec3D vec){
        return vec.getX()<1 && vec.getX()>-1 &&vec.getY()<1 && vec.getY()>-1 &&vec.getZ()<1 && vec.getZ()>0;
    }*/

    private boolean checkView(Vertex v){
        Point3D position = v.getPosition();
        boolean result =
                -position.getW() >= position.getX() ||
                -position.getW() >= position.getY() ||
                position.getX() >= position.getW() ||
                position.getY() >= position.getW() ||
                position.getZ()  <=  0 ||
                position.getZ() >= position.getW();
        return !result;
    }
}

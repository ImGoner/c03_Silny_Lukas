package solids;

import model.Solid;
import model.Vertex;
import org.javatuples.Triplet;
import transforms.Col;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sphere extends Solid {
    private int firstN;
    private int N = 360;
    public int getN() {
        return N;
    }
    public void setN(int n) {
        N = n;
    }
    private int animationVertices = 30;
    private int counter = 1;
    private double temp = 1;
    double colorStep;
    double tempColor;

    private void generate(){
        setVertices(new ArrayList());
        setIndices(new ArrayList<Triplet<Integer,Integer,Color>>());
        colorStep = 16777215;
        tempColor = 0;
        setStartingPosition(new Point3D(0,0,0));
        for (int i=0;i<N;i+=1){
            double y = Math.cos(((2*Math.PI)/N)*i);
            double z = Math.sin(((2*Math.PI)/N)*i);
            getVertices().add(new Vertex(getStartingPosition().getX(),y,z));
        }
        for (int i=0;i<N;i+=1){
            double x = Math.cos(((2*Math.PI)/N)*i);
            double z = Math.sin(((2*Math.PI)/N)*i);
            getVertices().add(new Vertex(x,getStartingPosition().getY(),z));
        }
        for (int i=0;i<N;i+=1){
            double y = Math.cos(((2*Math.PI)/N)*i);
            double x = Math.sin(((2*Math.PI)/N)*i);
            getVertices().add(new Vertex(x,y,getStartingPosition().getZ()));
        }
        colorStep = (double)colorStep/(getVertices().size()-1);

        tempColor =+colorStep;
        for (int j = 0; j<getVertices().size();j +=1) {
            int a = j/N;
            getIndices().add(new Triplet<>(j,((j + 1)%(N))+(a*N), new Color((int)(tempColor))));
            tempColor+=colorStep;
        }
    }

    @Override
    public void startAnimationTimer() {
        firstN = N;
        super.startAnimationTimer();

    }

    @Override
    public void stopAnimationTimer() {
        N=firstN;
        super.stopAnimationTimer();
        update(N);
    }

    public void update(int n){
        setN(n);
        generate();
    }
    public Sphere(){


        generate();
    }

    public Sphere(Point3D start){
        setStartingPosition(start);
        generate();
    }
    public Sphere(int n){
        setN(n);
        generate();
    }

    @Override
    protected void animate() {
        super.animate();
        //rotate(Math.PI/180,Math.PI/180,Math.PI/180);

        double temp2 = (Math.sin(temp));

        temp+=Math.PI/8;
        if (temp2>0) {
            scale(1, 1.1, 1);
            //move(0,0,-0.5);
        }
        else if(temp2<0){
            scale(1,1/1.1,1);
            //move(0,0,0.5);
            //rotate(1,0.5,2);
        }

        if (temp2<0) {
            scale(1, 1, 1.1);
        }
        else if(temp2>0){
            scale(1,1,1/1.1);
        }
        counter++;
        update((int) (counter%animationVertices));

        rotate(Math.PI/180,-Math.PI/180,Math.PI/180);
        }



    @Override
    protected void resetColor() {

        colorStep = 16777215;
        tempColor = 0;
        colorStep = ((double)colorStep/(getVertices().size())*0.9);

        tempColor =+colorStep;/*
        for (int j = 0; j<getVertices().size();j +=1) {
            getIndices().get(j).setAt2(tempColor);
            tempColor+=colorStep;
        }*/

        List<Triplet<Integer,Integer,Color>> tempInd = new ArrayList<>();
        for (Triplet<Integer,Integer,Color> triplt:getIndices()
        ) {
            tempInd.add(new Triplet<Integer,Integer,Color>(triplt.getValue0(),triplt.getValue1(),new Color((int)tempColor)));
            tempColor+=colorStep;
        }
        setIndices(tempInd);
    }
}

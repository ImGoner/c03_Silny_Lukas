package model;

import org.javatuples.Triplet;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Solid {

    private List<Triplet<Integer,Integer,Color>> indices = new ArrayList<>();

    private List<Vertex> vertices = new ArrayList<>();



    private Point3D startingPosition;

    Mat4 model = new Mat4Identity();

    private Color color;
    private Color defaultColor  = Color.white;
    private int timerDelay = 75;

    private Timer animationTimer = new Timer(timerDelay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            animate();
        }
    });

    public Solid(){

    }

    public int getTimerDelay() {
        return timerDelay;
    }

    public void setTimerDelay(int timerDelay) {
        animationTimer.setDelay(timerDelay);
        this.timerDelay = timerDelay;
    }

    public Point3D getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Point3D startingPosition) {
        this.startingPosition = startingPosition;
    }

    public boolean isAnimationRunning(){
        return animationTimer.isRunning();
    }

    public void startAnimationTimer(){
        this.animationTimer.start();
    }
   public void stopAnimationTimer(){
        this.animationTimer.stop();
    }
    public void startStopAnimationTimer (){
        if(isAnimationRunning()){
            stopAnimationTimer();
        }else{
            startAnimationTimer();
        }
    }



    public List<Triplet<Integer, Integer, Color>> getIndices() {
        return indices;
    }

    public void setIndicies(List<Triplet<Integer, Integer, Color>> indicies2) {
        this.indices = indicies2;
    }

    public void setModel(Mat4 model){
        this.model = model;
    }

    public Mat4 getModel(){
        return this.model;
    }



    //index buffer

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        List<Triplet<Integer,Integer,Color>> temp = new ArrayList<>();
        for (Triplet<Integer,Integer,Color> triplt:this.indices
             ) {
            temp.add(new Triplet<>(triplt.getValue0(),triplt.getValue1(),color));
        }
        this.indices = temp;
    }
    public List<Vertex> getVertices() {
        return vertices;
    }
    public void setVertices(ArrayList arrayList) {
        this.vertices = arrayList;
    }

    public void setIndices(List<Triplet<Integer,Integer, Color>> indicies) {
        this.indices = indicies;
    }

    protected void addEdge(int a,int b,Color c){
        indices.add(new Triplet<>(a,b,c));
    }

    public void move(double x, double y, double z ){
        model = new Mat4Transl(x,y,z).mul(model);
    }
    public void scale(double x,double y,double z){
        model = new Mat4Scale(x,y,z).mul(model);
    }
    public  void rotate(double x, double y,double z){
        model = new Mat4RotXYZ(x,y,z).mul(model);
    }

    protected void animate(){

    };
    public void resetDefaultColor(){
        resetColor();
    }
    protected void resetColor(){
        List<Triplet<Integer,Integer,Color>> temp = new ArrayList<>();
        for (Triplet<Integer,Integer,Color> triplt:this.indices
        ) {
            temp.add(new Triplet<>(triplt.getValue0(),triplt.getValue1(),defaultColor));
        }
        this.indices = temp;
    }
}

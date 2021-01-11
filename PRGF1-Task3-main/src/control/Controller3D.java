package control;

import model.Scene;
import model.Solid;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.Raster;
import render.Renderer;
import solids.*;
import transforms.*;
import view.Panel;

import javax.naming.spi.DirObjectFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Controller3D implements Controller {
    private Solid extended;
    private final Panel panel;
    private LineRasterizer rasterizer;
    private Renderer renderer;
    private Tetrahedron tetrahedron;
    private Cube cube;
    private Axis axis;
    private Curve curve;
    private Sphere sphere;
    private double k;
    private Scene scene;
    private Point mousePos = new Point();
    Camera camera;
    private Solid edit;
    private boolean persp = true;
    private Mat4 proj;
    private Timer animationTimer;

    private CoonsCurve cc ;


    public Controller3D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        rasterizer = new LineRasterizerGraphics(raster);
        renderer = new Renderer(raster, rasterizer);
        camera = new Camera().
                withAzimuth(0).
                withZenith(0).
                withPosition(new Vec3D(-10, 0, 0));

        animationTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                update();
            }
        });
        animationTimer.start();

        k = (double) panel.getRaster().getHeight() / panel.getRaster().getWidth();

        makeScene();
        update();
    }

    private void makeScene() {
        scene = new Scene();

        tetrahedron = new Tetrahedron();
        tetrahedron.move(1,1,2);
        tetrahedron.rotate(3,3,3);

        cube = new Cube();
        cube.move(2, 2, 2);
        cube.scale(2, 2, 2);
        edit = cube;
        highlight();

        sphere = new Sphere();
        sphere.move(1,2,1);
        sphere.update(360);
        //sphere.scale(1,4,1);


        curve = new Curve();
        curve.move(2, 2, 2);
        curve.scale(2, 2, 2);
        extended = new CompisiteSolid();
        extended.move(5,5,5);

        cc = new CoonsCurve();
        cc.move(0,-2,0);

        scene.getSolids().add(cc);
        scene.getSolids().add(cube);
        scene.getSolids().add(tetrahedron);
        scene.getSolids().add(curve);
        scene.getSolids().add(sphere);
        scene.getSolids().add(extended);

    }

    private void update() {
        proj = persp ? new Mat4PerspRH((Math.PI / 18)*7 , k, 0.1, 150) : new Mat4OrthoRH(11, 11, 0.1, 20);
        panel.clear();
        renderer.setModel(new Mat4Identity());
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(proj);
        renderer.render(scene);
        axis = new Axis();
        axis.scale(5,5,5);
        renderer.render(axis);
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                if (e.getX() > mousePos.getX()) {
                    camera = camera.addAzimuth(-Math.PI / 360);
                }
                if (e.getX() < mousePos.getX()) {
                    camera = camera.addAzimuth(Math.PI / 360);
                }
                if (e.getY() > mousePos.getY()) {
                    camera = camera.addZenith(-Math.PI / 360);
                }
                if (e.getY() < mousePos.getY()) {
                    camera = camera.addZenith(Math.PI / 360);
                }
                mousePos = new Point(e.getX(), e.getY());
                update();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int[] temp ;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        camera = camera.forward(0.1);
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(0.1);
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.right(0.1);
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(0.1);
                        break;
                    case KeyEvent.VK_SHIFT:
                        camera = camera.down(0.1);
                        break;
                    case KeyEvent.VK_SPACE:
                        camera = camera.up(0.1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        int indexplus = scene.getSolids().indexOf(edit);
                        if (indexplus!=-1){
                        edit = scene.getSolids().get((indexplus + 1 == scene.getSolids().size() ? 0 : indexplus + 1));
                        scene.getSolids().get(indexplus).resetDefaultColor();
                        highlight();}
                        break;
                    case KeyEvent.VK_LEFT:
                        int indexminus = scene.getSolids().indexOf(edit);
                        if (indexminus!=-1) {
                            edit = scene.getSolids().get((indexminus == 0 ? scene.getSolids().size() - 1 : indexminus - 1));
                            scene.getSolids().get(indexminus).resetDefaultColor();
                            highlight();
                        }
                        break;
                    case KeyEvent.VK_E:

                        temp = userDialog("Scale");
                        edit.scale(temp[0], temp[1], temp[2]);
                        update();
                        break;
                    case KeyEvent.VK_R:
                        temp = userDialog("Rotate");
                        edit.rotate(temp[0], temp[1], temp[2]);;
                        update();
                        break;
                    case KeyEvent.VK_T:
                        temp = userDialog("Transl");
                        edit.move(temp[0], temp[1], temp[2]);
                        update();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    case KeyEvent.VK_C:
                        camera = new Camera().
                                withAzimuth(0).
                                withZenith(0).
                                withPosition(new Vec3D(-10, -5, 0));
                        break;
                    case KeyEvent.VK_P:
                        persp = !persp;
                        break;
                    case KeyEvent.VK_V:
                        edit.startStopAnimationTimer();
                        break;
                    case KeyEvent.VK_ADD:
                        JCheckBox cube = new JCheckBox();
                        JCheckBox sphere = new JCheckBox();
                        JCheckBox tetrahedron = new JCheckBox();
                        JCheckBox curve = new JCheckBox();

                        String[] sa= {"Cube","Sphere","Tetrahedron","Curve"};
                        JComboBox jcb = new JComboBox(sa);
                        Object[] message = {
                                "Vyber: ",
                                jcb
                        };
                        int option = JOptionPane.showConfirmDialog(null, message, "Add", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try{
                            switch (jcb.getSelectedIndex()){
                                case 0:


                                    addSolid(new Cube());
                                    break;
                                case 1:
                                    JTextField n = new JTextField();
                                    int nOption = JOptionPane.showConfirmDialog(null, new Object[]{  "N: ", n}, "Add", JOptionPane.OK_CANCEL_OPTION);
                                    if (option == JOptionPane.OK_OPTION) {
                                        try {
                                            addSolid(new Sphere(Integer.parseInt(n.getText())));
                                        }catch (Exception ex){

                                        }
                                    }

                                    break;
                                case 2:
                                    addSolid(new Tetrahedron());
                                        break;
                                case 3:
                                    addSolid(new Curve());
                                        break;
                                default:
                                        break;

                            }
                            }catch(Exception f){

                            }
                        }
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        int editIndex = scene.getSolids().indexOf(edit);

                        scene.getSolids().remove(edit);
                        if (scene.getSolids().size()!=0) {
                            edit = scene.getSolids().get((editIndex + 1 >= scene.getSolids().size() ? 0 : editIndex + 1));
                            highlight();
                        }
                        break;
                    default:
                        break;

                }
                update();
            }

        });


        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });


    }

    private void highlight() {
        edit.setColor(Color.CYAN);
        update();
    }

    private int[] userDialog(String title) {
        JTextField x = new JTextField();
        JTextField y = new JTextField();
        JTextField z = new JTextField();
        Object[] message = {
                "x:", x,
                "y:", y,
                "z:", z
        };

        int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {

                try {
                    int intx = Integer.parseInt(x.getText());
                    int inty = Integer.parseInt(y.getText());
                    int intz = Integer.parseInt(z.getText());
                    return new int[] {Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),Integer.parseInt(z.getText())};
                }catch (NumberFormatException e){

                }

        }
        return null;
    }

    private Object userDialog(String title, Object mess){
        int option = JOptionPane.showConfirmDialog(null, mess, title, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return mess;
        }
        return null;
    }

    public void addSolid(Solid solid){
        if (scene.getSolids().size()!=0){edit.resetDefaultColor();}
        scene.getSolids().add(solid);
        edit = solid;
        highlight();
        update();
    }
}

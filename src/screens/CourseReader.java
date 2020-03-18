package screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Function2d;
import com.mygdx.game.FunctionHeight;
import com.mygdx.game.Vector2d;
import screens.CourseDesigner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseReader implements Screen {


    private Game game;
    private Stage stage;

    private static double gravity;
    private static double mass;
    private static double friction;
    private static double iniSpeed;
    private static double winArea;
    private static double start[] = new double[2];
    private static double goal[] = new double[2];
    private static String heightFun;
    private static double[][] obstacle;

    public CourseReader(Game g) {
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        try {
            File myObj = new File("course1.txt");
            Scanner myReader = new Scanner(myObj);
            gravity = Double.parseDouble(myReader.nextLine());
            mass = Double.parseDouble(myReader.nextLine());
            friction = Double.parseDouble(myReader.nextLine());
            iniSpeed = Double.parseDouble(myReader.nextLine());
            winArea = Double.parseDouble(myReader.nextLine());
            start[0] = myReader.nextDouble();
            start[1] = myReader.nextDouble();
            goal[0] = myReader.nextDouble();
            goal[1] = myReader.nextDouble();
            myReader.nextLine();
            heightFun = myReader.nextLine();
            int i = 0, j = 0;
            while (myReader.hasNextDouble()) {
                obstacle[i][j] = myReader.nextDouble();
                if (j == 1) {
                    j--;
                    i++;
                } else j++;
            }



            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }



    public Function2d get_height(){
        Function2d H = new FunctionHeight(heightFun);
        return H;
    }
    public Vector2d get_flag_position(){
        Vector2d v1 = new Vector2d(goal[0],goal[1]);
        return v1;
    }
    public Vector2d get_start_position()
    {
        Vector2d v1 = new Vector2d(start[0],start[1]);
        return v1;
    }
    public double get_friction_coefficient()
    {
        return friction;
    }
    public double get_maximum_velocity(){
        return iniSpeed;
    }
    public double get_hole_tolerance(){
        return winArea;
    }

    public static double getFriction() {
        return friction;
    }

    public static double getGravity() {
        return gravity;
    }

    public static double getIniSpeed() {
        return iniSpeed;
    }

    public static double getMass() {
        return mass;
    }


    public static Vector2d[] getObstacle() {
        Vector2d[] v1 = new Vector2d[obstacle.length];
        for (int i = 0; i < obstacle.length; i++) {
            v1[i] = new Vector2d(obstacle[i][0], obstacle[i][1]);
        }
        return v1;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}





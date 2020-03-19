package screens;

import Physics.Vector3D;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import Physics.Vector3D;
import Physics.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Vector2d;
import screens.CourseDesigner;
import screens.CourseReader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import java.util.ArrayList;


public class Play implements Screen {

    private Game game;
    private Stage stage;
    public PerspectiveCamera cam;
    public FirstPersonCameraController camController;
    public ModelBatch modelBatch;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    private ModelBuilder modelBuilder;
    public Model golfBall;
    public ModelInstance ourGolfBall,Goal;
    public ArrayList<Vector3D> timeSpace = new ArrayList<Vector3D>();
    boolean start = false;
    int timer;
    CourseReader CR = new CourseReader();
    float updatePosTime = 0;
    float clock = 0;

    Play(Game g){

        game = g;
        stage = new Stage();

        modelBatch = new ModelBatch();

        modelBuilder = new ModelBuilder();
        golfBall = modelBuilder.createSphere(1,1,1,25,25,new Material(ColorAttribute.createDiffuse(Color.WHITE)),VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
        ourGolfBall = new ModelInstance(golfBall, (int) CR.get_start_position().get_x(),(int) CR.get_start_position().get_y(), (float) ((float) CR.get_height().evaluate(new Vector2d(CR.get_start_position().get_x(),CR.get_start_position().get_y())) + 0.5));


        World my_world = new World(CR.get_friction_coefficient(), CR.getGravity(), CR.getMass(), new Vector3D(CR.get_start_position().get_x(),CR.get_start_position().get_y(), CourseReader.get_height().evaluate(new Vector2d(CR.get_start_position().get_x(), CR.get_start_position().get_y()))));
        timeSpace = my_world.hit_ball(3, 0);




        golfBall = modelBuilder.createCylinder((float) CR.get_hole_tolerance()+2,20, (float) CR.get_hole_tolerance()+2,25,GL20.GL_TRIANGLES,new Material(new BlendingAttribute((float) 0.5)),VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
        Goal = new ModelInstance(golfBall, (int) CR.get_flag_position().get_x(),(int) CR.get_flag_position().get_y(), (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y())) );
        Goal.transform.rotate(1, 0, 0, 90);
        Goal.transform.translate((int) CR.get_flag_position().get_x(),(int) CR.get_flag_position().get_y(), (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y())));



        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set((int) CR.get_flag_position().get_x()  ,(int) CR.get_flag_position().get_y() -20 , (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y()))+2 );
        cam.lookAt((int) CR.get_flag_position().get_x(),(int) CR.get_flag_position().get_y(), (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y())) );
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new FirstPersonCameraController(cam);
        Gdx.input.setInputProcessor(camController);

        Model model;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.node().id = "box";
        MeshPartBuilder mpb = modelBuilder.part("box", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.ColorPacked, new Material());
        mpb.setColor(Color.GREEN);



        for (int y = -25; y < 25; y++) {
            for (int x = -25; x < 25; x++) {
                mpb.line(x , y, (int)CR.get_height().evaluate(new Vector2d(x,y)),x , (y + 1) , (int)CR.get_height().evaluate(new Vector2d(x,y+1)));
                mpb.line(x , y, (int)CR.get_height().evaluate(new Vector2d(x,y)), x + 1 , ( y ) , (int)CR.get_height().evaluate(new Vector2d(x+1,y)));
            }}

        model = modelBuilder.end();
        ModelInstance instance = new ModelInstance(model);
        instances.add(instance);
        instances.add(ourGolfBall);
        instances.add(Goal);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clock += Gdx.graphics.getDeltaTime();
        if(clock>1){
            updatePosTime++;
            clock = 0;
        }
		/*if (start == false){
			updatePosTime = 0;
		start = true;
		} else timer++;
*/

        camController.update();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (updatePosTime<60&&updatePosTime!=0) {
            ourGolfBall.transform.translate((float) timeSpace.get((int) updatePosTime).get_x(), (float) timeSpace.get((int) updatePosTime).get_y(), (float) timeSpace.get((int) updatePosTime).get_z());
            if(timeSpace.get((int) updatePosTime).get_x() > CR.get_flag_position().get_x()-CR.get_hole_tolerance() && (timeSpace.get((int) updatePosTime).get_x() < CR.get_flag_position().get_x()+CR.get_hole_tolerance())
            && (timeSpace.get((int) updatePosTime).get_y() > CR.get_flag_position().get_y()-CR.get_hole_tolerance() && (timeSpace.get((int) updatePosTime).get_y() < CR.get_flag_position().get_y()+CR.get_hole_tolerance())))
                if(timeSpace.get((int) updatePosTime+1).get_x() > CR.get_flag_position().get_x()-CR.get_hole_tolerance() && (timeSpace.get((int) updatePosTime+1).get_x() < CR.get_flag_position().get_x()+CR.get_hole_tolerance())
                && (timeSpace.get((int) updatePosTime+1).get_y() > CR.get_flag_position().get_y()-CR.get_hole_tolerance() && (timeSpace.get((int) updatePosTime+1).get_y() < CR.get_flag_position().get_y()+CR.get_hole_tolerance())))
                    game.setScreen(new Win(game));
        }
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
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

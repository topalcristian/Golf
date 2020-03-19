package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import screens.CourseReader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;


public class Golf extends Game {
	public static int width = 1400;
	public static int height = 1000;
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Environment environment;
	private ModelBuilder modelBuilder;
	public Model golfBall;
	public ModelInstance ourGolfBall,Goal;
	CourseReader CR = new CourseReader();

	@Override public void create() {

		//this.setScreen(new CourseDesigner(this));
		modelBatch = new ModelBatch();

		modelBuilder = new ModelBuilder();
		golfBall = modelBuilder.createSphere(1,1,1,25,25,new Material(ColorAttribute.createDiffuse(Color.WHITE)),VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		ourGolfBall = new ModelInstance(golfBall, (int) CR.get_start_position().get_x(),(int) CR.get_start_position().get_y(), (float) ((float) CR.get_height().evaluate(new Vector2d(CR.get_start_position().get_x(),CR.get_start_position().get_y())) + 0.5));

		golfBall = modelBuilder.createCylinder((float) CR.get_hole_tolerance()+2,20, (float) CR.get_hole_tolerance()+2,25,GL20.GL_TRIANGLES,new Material(new BlendingAttribute((float) 0.5)),VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		Goal = new ModelInstance(golfBall, (int) CR.get_flag_position().get_x(),(int) CR.get_flag_position().get_y(), (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y())) );
		Goal.transform.rotate(1, 0, 0, 90);
		Goal.transform.translate((int) CR.get_flag_position().get_x(),(int) CR.get_flag_position().get_y(), (int) CR.get_height().evaluate(new Vector2d(CR.get_flag_position().get_x(),CR.get_flag_position().get_y())));

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(25, 25, 30);
		cam.lookAt(25,25,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
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
	@Override public void render(){
		super.render();

		camController.update();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();


	}


}

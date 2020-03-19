package com.mygdx.game;
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
import screens.CourseDesigner;
import screens.CourseReader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import java.util.ArrayList;


public class Golf extends Game {


	public static int width = 1400;
	public static int height = 1000;

	@Override public void create() {

		this.setScreen(new CourseDesigner(this));


	}

	@Override public void render(){
		super.render();



	}


}

package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import screens.CourseDesigner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Golf extends Game {
	public static int width = 1400;
	public static int height = 1000;




	@Override public void create() {
		this.setScreen(new CourseDesigner(this));

	}
	@Override public void render() {
		super.render();


	}

}

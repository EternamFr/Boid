package com.christyetcie.boid.view;

import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import com.christyetcie.boid.MainActivity;
import com.christyetcie.boid.controller.GameEngine;
import com.christyetcie.boid.model.Boid;
import com.christyetcie.boid.model.World;

public class WorldRenderer implements IRenderer {

	private World mWorld;
	private BoidRenderer mBoidRenderer;
	private GridRenderer mGridRenderer;
	
	public WorldRenderer(World world, GameEngine gameEngine) {
		mWorld = world;
		
		mBoidRenderer = new BoidRenderer(null);
		mGridRenderer = new GridRenderer();
	}
	
	@Override
	public void render(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	    
		// Render the grid
		mGridRenderer.render(MainActivity.mGlGraphics.getGL());
		
		// Render the boids
		// TODO: Replace iterator, it generates objects, thus memory allocation
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			mBoidRenderer.render(MainActivity.mGlGraphics.getGL(), itr.next());
		}
	}

}

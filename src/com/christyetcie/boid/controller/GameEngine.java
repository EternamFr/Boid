package com.christyetcie.boid.controller;

import javax.microedition.khronos.opengles.GL10;

import com.christyetcie.boid.model.World;
import com.christyetcie.boid.view.WorldRenderer;

public class GameEngine {
	
	private World mWorld;
	private WorldRenderer mWorldRenderer;
	
	private WorldController mWorldController;
	
	public GameEngine() {
		mWorld = new World(10);
		
		mWorldRenderer = new WorldRenderer(mWorld, this);
		mWorldController = new WorldController(mWorld);
	}

	/** pre-update the world with the deltaTime in seconds **/
	public void preUpdate(float deltaTime) {
		mWorldController.preUpdate(deltaTime);
	}
	
	/** update the world with the deltaTime in seconds **/
	public void update(float deltaTime) {
		mWorldController.update(deltaTime);
	}
	
	/** render the whole world **/
	public void render(float deltaTime, GL10 gl) {
		mWorldRenderer.render(gl);
	}
	
	/** pause... café?!? **/
	public void pause() {
		
	}
	
	/** time to cleanup **/
	public void dispose() {
		
	}
	
	/** handle the Event passed **/
//	public boolean handleEvent(Event e) {
//		switch (e.id) {
//		case Event.KEY_PRESS:
//		case Event.KEY_ACTION:
//			// key pressed
//			break;
//		case Event.KEY_RELEASE:
//			// key released
//			break;
//		case Event.MOUSE_DOWN:
//			// mouse button pressed
//			break;
//		case Event.MOUSE_UP:
//			// mouse button released
//			controller.onClick(e.x, e.y);
//			break;
//		case Event.MOUSE_MOVE:
//			// mouse is being moved
//			break;
//		case Event.MOUSE_DRAG:
//			// mouse is being dragged (button pressed)
//			break;
//		}
//		return false;
//	}

}

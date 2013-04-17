package com.christyetcie.boid.controller;

import java.util.Iterator;

import com.christyetcie.boid.R;
import com.christyetcie.boid.controller.strategy.BoidMovementDispertion;
import com.christyetcie.boid.model.Boid;
import com.christyetcie.boid.model.World;
import com.christyetcie.boids.util.Vector3;

public class WorldController {

	private World mWorld;
	private BoidController mBoidController;
	
	public WorldController(World world) {
		mWorld = world;
		mBoidController = new BoidController();
		mBoidController.setBoidMovementStrategy(new BoidMovementDispertion());
		
		initializePopulation();
//		mWorld.addBoid(new Boid(R.drawable.a_lion_small, new Vector3(5.0f,2.0f,-25.0f)));
//		mWorld.addBoid(new Boid(R.drawable.a_bird_small, -20.0f));
//		mWorld.addBoid(new Boid(R.drawable.a_bird_small, -30.0f));		
	}

	private void initializePopulation() {
		float maxAxesRange = 25.0f;
		
		for (int i = 0; i < mWorld.getPopulationSize(); i++) {
			Vector3 position = new Vector3();
			position.mX = (float)(Math.random()) * maxAxesRange - maxAxesRange / 2.0f;
			position.mY = (float)(Math.random()) * maxAxesRange - maxAxesRange / 2.0f;
			position.mZ = (float)(Math.random()) * maxAxesRange - 75.0f; // TODO fix that: finally moving the camera +50.0f (something like that) in +z
				
			Vector3 speed = new Vector3();
			speed.mX = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			speed.mY = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			speed.mZ = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			
			Boid b = new Boid(i, R.drawable.a_bird_small, position, speed);
			mWorld.addBoid(b);
		}
	}
	
	public void update(float deltaTime) {
		// TODO: Replace iterator, it generates objects, thus memory allocation
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			mBoidController.update(deltaTime, itr.next());
		}
	}
}

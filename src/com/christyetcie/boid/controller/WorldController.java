package com.christyetcie.boid.controller;

import java.util.Iterator;

import com.christyetcie.boid.R;
import com.christyetcie.boid.controller.strategy.*;
import com.christyetcie.boid.model.Boid;
import com.christyetcie.boid.model.World;
import com.christyetcie.boid.util.Vector3;

public class WorldController {

	private World mWorld;
	private BoidController mBoidController;
	
	public WorldController(World world) {
		mWorld = world;
		mBoidController = new BoidController();		
		//mBoidController.setBoidMovementStrategy(new BoidMovementDispertion());
		mBoidController.setBoidMovementStrategy(new BoidMovementFlock(this, getRandomPosition()));
		
		initializePopulation();
//		mWorld.addBoid(new Boid(R.drawable.a_lion_small, new Vector3(5.0f,2.0f,-25.0f)));
//		mWorld.addBoid(new Boid(R.drawable.a_bird_small, -20.0f));
//		mWorld.addBoid(new Boid(R.drawable.a_bird_small, -30.0f));		
	}

	private void initializePopulation() {
		for (int i = 0; i < mWorld.getPopulationSize(); i++) {
			Vector3 position = getRandomPosition();
				
			Vector3 speed = new Vector3();
			speed.mX = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			speed.mY = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			speed.mZ = (float)(Math.random()) * Boid.MAX_SPEED - Boid.MAX_SPEED / 2.0f;
			
			Boid b = new Boid(i, R.drawable.a_bird_small, position, speed);
			mWorld.addBoid(b);
		}
	}
	
	private Vector3 getRandomPosition() {
		float maxAxesRange = 25.0f;
		
		Vector3 position = new Vector3();
		position.mX = (float)(Math.random()) * maxAxesRange - maxAxesRange / 2.0f;
		position.mY = (float)(Math.random()) * maxAxesRange - maxAxesRange / 2.0f;
		position.mZ = (float)(Math.random()) * maxAxesRange - 75.0f; // TODO fix that: finally moving the camera +50.0f (something like that) in +z
		
		return position;
	}
	
	static int mUpdatesCount;
	public void preUpdate(float deltaTime) {
		// TODO: Replace iterator, it generates objects, thus memory allocation
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			mBoidController.preUpdate(deltaTime, itr.next());
		}
	}
	
	public void update(float deltaTime) {
		// TODO: Replace iterator, it generates objects, thus memory allocation
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			mBoidController.update(deltaTime, itr.next());
		}
		
		mUpdatesCount+=1;
		
		if (mUpdatesCount % 50 == 0) {
			mBoidController.setBoidMovementStrategy(new BoidMovementFlock(this, getRandomPosition()));
		}
	}
	
	public Vector3 deltaCohesion(Boid boid) {
		Vector3 delta = new Vector3();
		
		int neighborCount = 0;
		
		Boid temp;
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			temp = itr.next();
			if (temp.getId() != boid.getId()) {
				// Not so far... a neighbor basically! TODO: Quadtree?!?
				float distance = Vector3.distance(boid.mPosition, temp.mPosition);
				if (distance < BoidController.MaximalDistance) {
					neighborCount++;
					delta.addSelf(Vector3.distanceXYZ(boid.mPosition, temp.mPosition));
				}
			}
		}
		
		if (neighborCount > 0) {
			// Calculate the center of gravity of the nearby flock
			delta.divideSelf(neighborCount);
			delta.divideSelf(BoidController.CohesionFactor);
		}

		return delta;
	}
	
	public Vector3 deltaDodge(Boid boid) {
		Vector3 delta = new Vector3();
		
		Boid temp;
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			temp = itr.next();
			if (temp.getId() != boid.getId()) {
				Vector3 distanceXYZ = Vector3.distanceXYZ(boid.mPosition, temp.mPosition);
				// TODO: check: shouldn't we just calculate the real Distance, if too close, vector created by boid - temp ?!?
				if (distanceXYZ.mX < BoidController.MinimalDistance) {
					delta.mX -= distanceXYZ.mX;
				}
				if (distanceXYZ.mY < BoidController.MinimalDistance) {
					delta.mY -= distanceXYZ.mY;
				}
				if (distanceXYZ.mZ < BoidController.MinimalDistance) {
					delta.mZ -= distanceXYZ.mZ;
				}
			}
		}
		
		delta.divideSelf(BoidController.DodgeFactor);
		
		return delta;
	}
	
	public Vector3 deltaAlignment(Boid boid) {
		// TODO: merge with deltaCohesion
		Vector3 delta = new Vector3();
		
		int neighborCount = 0;
		
		Boid temp;
		Iterator<Boid> itr = mWorld.getBoids().iterator();
		while (itr.hasNext()){
			temp = itr.next();
			if (temp.getId() != boid.getId()) {
				// Not so far... a neighbor basically! TODO: Quadtree?!?
				float distance = Vector3.distance(boid.mPosition, temp.mPosition);
				if (distance < BoidController.MaximalDistance) {
					neighborCount++;
					delta.addSelf(temp.mSpeed);
				}
			}
		}
		
		if (neighborCount > 0) {
			// Calculate the average speed of the nearby flock
			delta.divide(neighborCount);

			// Divide by cohesion factor
			delta.minusSelf(boid.mSpeed);
			delta.divideSelf(BoidController.AlignmentFactor);
		}
		
		return delta;
	}
	
	public Vector3 deltaTarget(Boid boid, Vector3 target) {
		Vector3 delta = new Vector3();
		
		delta = target.minus(boid.mPosition);
		delta.divideSelf(BoidController.TargetFactor);
		
		return delta;
	}
	
}

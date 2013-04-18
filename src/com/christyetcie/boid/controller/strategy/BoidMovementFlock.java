package com.christyetcie.boid.controller.strategy;

import com.christyetcie.boid.controller.WorldController;
import com.christyetcie.boid.model.Boid;
import com.christyetcie.boid.util.Vector3;

public class BoidMovementFlock implements IBoidMovementStrategy {

	private WorldController mWorldController;
	private Vector3 mTarget;
	
	public BoidMovementFlock(WorldController worldController, Vector3 target) {
		mWorldController = worldController;
		mTarget = target;
	}
	
	@Override
	public Vector3 calculateMovement(float deltaTime, Boid boid) {
		Vector3 cohesion = mWorldController.deltaCohesion(boid);
		
		Vector3 dodge = mWorldController.deltaDodge(boid);
		
		Vector3 alignment = mWorldController.deltaAlignment(boid);
		
		// TODO: To Wind or not to Wind?
		
		Vector3 target = mWorldController.deltaTarget(boid, mTarget);
		
		cohesion.addSelf(dodge).addSelf(alignment).addSelf(target);
		
		cohesion.mX = applyMaxSpeed(cohesion.mX, Boid.MAX_SPEED);
		cohesion.mY = applyMaxSpeed(cohesion.mY, Boid.MAX_SPEED);
		cohesion.mZ = applyMaxSpeed(cohesion.mZ, Boid.MAX_SPEED);
		
		return cohesion.multiply(deltaTime);
	}
	
	private float applyMaxSpeed(float movement, float maxSpeed) {
		return (movement>=0 ? Math.min(movement, maxSpeed) : Math.max(movement, -1.0f * maxSpeed));
	}
	
}

package com.christyetcie.boid.controller.strategy;

import com.christyetcie.boid.model.Boid;
import com.christyetcie.boids.util.Vector3;

public class BoidMovementDispertion implements IBoidMovementStrategy {

	@Override
	public Vector3 calculateMovement(float deltaTime, Boid boid) {
		Vector3 movement = new Vector3();
		
		movement = boid.mSpeed.multiply(deltaTime);
		
		return movement;
	}

}

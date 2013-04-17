package com.christyetcie.boid.controller.strategy;

import com.christyetcie.boid.model.Boid;
import com.christyetcie.boids.util.Vector3;

public class NullBoidMovement implements IBoidMovementStrategy {

	@Override
	public Vector3 calculateMovement(float deltaTime, Boid boid) {
		return new Vector3(0.0f, 0.0f, 0.0f);
	}

}

package com.christyetcie.boid.controller.strategy;

import com.christyetcie.boid.model.Boid;
import com.christyetcie.boid.util.Vector3;

public interface IBoidMovementStrategy {

	public Vector3 calculateMovement(float deltaTime, Boid boid);
	
}

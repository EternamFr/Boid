package com.christyetcie.boid.controller;

import com.christyetcie.boid.controller.strategy.IBoidMovementStrategy;
import com.christyetcie.boid.controller.strategy.NullBoidMovement;
import com.christyetcie.boid.model.Boid;

public class BoidController {

	private Boid mBoid;
	private IBoidMovementStrategy mBoidMovementStrategy;
	
	public BoidController () {
		mBoidMovementStrategy = new NullBoidMovement();
	}
	
	public void setBoidMovementStrategy(IBoidMovementStrategy boidMovementStrategy) {
		mBoidMovementStrategy = boidMovementStrategy;
	}
	
	public void update(float deltaTime, Boid boid) {
		mBoid = boid;
		
		// Should calculate all boids' positions before updating any?
		mBoid.mPosition.addSelf(mBoidMovementStrategy.calculateMovement(deltaTime, mBoid));
	}
	
}

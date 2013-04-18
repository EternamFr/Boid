package com.christyetcie.boid.controller;

import com.christyetcie.boid.controller.strategy.IBoidMovementStrategy;
import com.christyetcie.boid.controller.strategy.NullBoidMovement;
import com.christyetcie.boid.model.Boid;

public class BoidController {

	private IBoidMovementStrategy mBoidMovementStrategy;
	
	static final float MaximalDistance = 30.0f;
	static final float MinimalDistance = 4.0f;
	static final float CohesionFactor = 25.0f;
	static final float DodgeFactor = 7.0f;
	static final float AlignmentFactor = 8.0f;
	static final float TargetFactor = 20.0f;
	
	public BoidController () {
		mBoidMovementStrategy = new NullBoidMovement();
	}
	
	public void setBoidMovementStrategy(IBoidMovementStrategy boidMovementStrategy) {
		mBoidMovementStrategy = boidMovementStrategy;
	}
	
	public void preUpdate(float deltaTime, Boid boid) {
		// Should calculate all boids' positions before updating any?
		boid.mUpdatedSpeed = mBoidMovementStrategy.calculateMovement(deltaTime, boid);		
	}
	
	public void update(float deltaTime, Boid boid) {
		// Bad: shouldn't need to convert movement-->speed
		boid.mPosition.addSelf(boid.mUpdatedSpeed);
		boid.mSpeed = boid.mUpdatedSpeed.divideSelf(deltaTime);
		//boid.mPosition.addSelf(mBoidMovementStrategy.calculateMovement(deltaTime, boid));
	}
	
}

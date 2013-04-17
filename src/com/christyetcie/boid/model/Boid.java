package com.christyetcie.boid.model;

import com.christyetcie.boids.util.Vector3;

public class Boid {

	public final static float MAX_SPEED = 25.0f;
	private int mTextureRessourceId;

	public Vector3 mPosition;
	public Vector3 mSpeed;
	
	private int mId;
	
	public Boid(int id, int textureRessourceId, Vector3 position, Vector3 speed) {
		mId = id;
		mTextureRessourceId = textureRessourceId;
		mPosition = position;
		mSpeed = speed;
	}

	public Boid(int id, int textureRessourceId, float maxAxesRange) {
		mId = id;
		mTextureRessourceId = textureRessourceId;
		
		mPosition = new Vector3();
		mPosition.mX = (float)(Math.random()) * maxAxesRange;
		mPosition.mY = (float)(Math.random()) * maxAxesRange;
		mPosition.mZ = - (float)(Math.random()) * maxAxesRange;
		
		mSpeed = new Vector3();
		mSpeed.mX = (float)(Math.random()) * MAX_SPEED;
		mSpeed.mY = (float)(Math.random()) * MAX_SPEED;
		mSpeed.mZ = (float)(Math.random()) * MAX_SPEED;		
	}
	
	public int getTextureRessourceId() {
		return mTextureRessourceId;
	}

	public void setTextureRessourceId(int textureRessourceId) {
		this.mTextureRessourceId = textureRessourceId;
	}
	
	public int getId() {
		return mId;
	}
}

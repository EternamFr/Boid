package com.christyetcie.boid.model;

import java.util.Vector;

public class World {

	Vector<Boid> mBoids;
	private int mPopulationSize;
	
	public World(int populationSize){
		mBoids = new Vector<Boid>();
		mPopulationSize = populationSize;
	}
	
	public void addBoid(Boid boid) {
		if (! mBoids.contains(boid)) {
			mBoids.add(boid);			
		}
	}
	
	public Vector<Boid> getBoids() {
		return mBoids;
	}
	
	public int getPopulationSize() {
		return mPopulationSize;
	}
}

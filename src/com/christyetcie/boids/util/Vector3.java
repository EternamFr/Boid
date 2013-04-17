package com.christyetcie.boids.util;

public class Vector3 {

	public float mX, mY, mZ;
	
	// Constructors
	public Vector3() {}
	
	public Vector3(float x, float y, float z) {
		mX = x;
		mY = y;
		mZ = z;
	}
	
	public Vector3(Vector3 v) {
		mX = v.mX;
		mY = v.mY;
		mZ = v.mZ;
	}	
	
	// Compare
	public boolean equals(Vector3 v) {
		return mX == v.mX && mY == v.mY && mZ == v.mZ;
	}
	
	public boolean differs(Vector3 v) {
		return mX != v.mX || mY != v.mY || mZ != v.mZ;
	}
	
	//
	public void zero() {
		mX = mY = mZ = 0.0f;
	}
	
	// Unary minus returns the negative of the vector
	public Vector3 unaryMinus() {
		return new Vector3(-mX, -mY, -mZ);
	}
	
	// Add, minus, multiply and divide returning a new Vector3
	public Vector3 add(Vector3 v) {
		return new Vector3( mX + v.mX, mY + v.mY, mZ + v.mZ);
	}
	
	public Vector3 minus(Vector3 v) {
		return new Vector3(mX - v.mX, mY - v.mY, mZ - v.mZ);
	}
		
	public Vector3 multiply (float k) {
		return new Vector3(mX * k, mY * k, mZ * k);
	}
	
	public Vector3 divide (float k) {
		float oneOverK = 1.0f / k;
		return new Vector3(mX * oneOverK, mY * oneOverK, mZ * oneOverK);
	}

	// Add, minus, multiply and divide on self, returning self
	public Vector3 addSelf(Vector3 v) {
		mX += v.mX;
		mY += v.mY;
		mZ += v.mZ;
		
		return this;
	}
	
	public Vector3 minusSelf(Vector3 v) {
		mX -= v.mX;
		mY -= v.mY;
		mZ -= v.mZ;
		
		return this;
	}
		
	public Vector3 multiplySelf (float k) {
		mX *= k;
		mY *= k;
		mZ *= k;
		
		return this;
	}
	
	public Vector3 divideSelf (float k) {
		float oneOverK = 1.0f / k;
		
		mX *= oneOverK;
		mY *= oneOverK;
		mZ *= oneOverK;
		
		return this;
	}

	// Normalize the vector
	public void normalize() {
		float magSq = mX * mX + mY * mY + mZ * mZ;
		if (magSq > 0.0f) {
			float oneOverMag = 1.0f / (float) Math.sqrt(magSq);
			mX *= oneOverMag;
			mY *= oneOverMag;
			mZ *= oneOverMag;
		}
	}

	// Vector dot product
	public float dotProduct(Vector3 v) {
		return mX * v.mX + mY * v.mY + mZ * v.mZ;
	}
	
	// Vector magnitude
	public static float magnitude(Vector3 v) {
		return (float) Math.sqrt(v.mX * v.mX + v.mY * v.mY + v.mZ * v.mZ);
	}
	
	// Vector square magnitude
	public static float magnitudeSquare(Vector3 v) {
		return v.mX * v.mX + v.mY * v.mY + v.mZ * v.mZ;
	}
	
	// Vector cross product
	public static Vector3 crossProduct(Vector3 u, Vector3 v) {
		return new Vector3(
				u.mY * v.mZ - u.mZ * v.mY,
				u.mZ * v.mX - u.mX * v.mZ,
				u.mX * v.mY - u.mY * v.mX
			);
	}
	
	// Distance between two points
	public static float distance(Vector3 u, Vector3 v) {
		float x = u.mX - v.mX;
		float y = u.mY - v.mY;
		float z = u.mZ - v.mZ;
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
}

package com.christyetcie.boid.Services;

import com.christyetcie.boid.Services.Textures.ITextureService;
import com.christyetcie.boid.Services.Textures.NullTextureService;

public class ServiceLocator {

	// Texture
	private static ITextureService mTextureService;
	private static NullTextureService mNullTextureService = new NullTextureService();
	
	// Texture
	final public static ITextureService getTextureService() {
		if (mTextureService == null) {
			mTextureService = mNullTextureService;
		}
		
		return mTextureService;
	}
	
	public static void provide(ITextureService service) {
		if (service == null) {
			mTextureService = mNullTextureService;
		} else {
			mTextureService = service;
		}
	}
	
}

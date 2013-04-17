package com.christyetcie.boid.Services.Textures;


import android.util.Log;

public class LoggedTextureService implements ITextureService {

	private ITextureService mTextureService;
	
	public LoggedTextureService(ITextureService service) {
		mTextureService = service;
	}
	
	@Override
	public void registerTexture(int resourceId) {
		Log.d("ITextureService", "RegisterTexture");
		
		mTextureService.registerTexture(resourceId);
	}

	@Override
	public int getTexture(int resourceId) {
		Log.d("ITextureService", "GetTexture");
		
		return mTextureService.getTexture(resourceId);
	}

}

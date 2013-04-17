package com.christyetcie.boid.Services.Textures;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import com.christyetcie.boid.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import android.util.SparseIntArray;


public class GL10TextureService implements ITextureService {

	private SparseIntArray mTextures;
	
	public GL10TextureService() {
		mTextures = new SparseIntArray();
	}

	@Override
	public void registerTexture(int resourceId) {
		Log.d(this.getClass().getName(), "registerTexture");
		if (mTextures.get(resourceId, LoadTexture(resourceId)) < 0) {
			Log.d("RegisterTexture", "failed to load texture."); 
		}
	}

	@Override
	public int getTexture(int resourceId) {
		int textureId = mTextures.get(resourceId);
		if (textureId == 0) {
			textureId = LoadTexture(resourceId);
		}
		return textureId;
	}

	private int LoadTexture(int resourceId) {
		Log.d(this.getClass().getName(), "LoadTexture");
		GL10 gl = MainActivity.mGlGraphics.getGL();
		Context context = MainActivity.mGlGraphics.getContext();
		
		int[] tempTextureId = new int[1];
		
		// Generate texture-ID array
		gl.glGenTextures(1, tempTextureId, 0);
		
		mTextures.append(resourceId, tempTextureId[0]);
		
		// Bind to texture ID
		gl.glBindTexture(GL10.GL_TEXTURE_2D, tempTextureId[0]);
		
		// Set up texture filters
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
  
		InputStream istream = context.getResources().openRawResource(resourceId);
		Bitmap bitmap;
		try {
			// Read and decode input as bitmap
			bitmap = BitmapFactory.decodeStream(istream);
		} finally {
			try {
				istream.close();
			} catch(IOException e) { }
		}
	  
		// Build Texture from loaded bitmap for the currently-bind texture ID
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	  
		return tempTextureId[0]; 
	}
	
}
package com.christyetcie.boid;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.christyetcie.boid.Services.ServiceLocator;
import com.christyetcie.boid.Services.Textures.GL10TextureService;
import com.christyetcie.boid.Services.Textures.ITextureService;
import com.christyetcie.boid.Services.Textures.LoggedTextureService;
import com.christyetcie.boid.controller.GameEngine;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements Renderer {

    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }
    
    private GLSurfaceView glView;
    private GLGameState state = GLGameState.Initialized;
    private Object stateChanged = new Object();
    private long startTime = System.nanoTime();
    private GameEngine mGameEngine;
    
    public static GLGraphics mGlGraphics;
    
    // BEGIN Activity Override
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);
        
        mGlGraphics = new GLGraphics(glView, this.getApplicationContext());
    }
    
    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
        //wakeLock.acquire();
    }
    
    @Override 
    public void onPause() {        
        synchronized(stateChanged) {
            if(isFinishing())            
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true) {
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException e) { 
                }
            }
        }
        //wakeLock.release();
        glView.onPause();  
        super.onPause();
    } 
    // END Activity Override
    
	// BEGIN Renderer implementation
	@Override
	public void onDrawFrame(GL10 gl) {
		GLGameState state = null;
        
        synchronized(stateChanged) {
            state = this.state;
        }
        
        if(state == GLGameState.Running) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            mGameEngine.update(deltaTime);
            mGameEngine.render(deltaTime,gl);
        }
        
        if(state == GLGameState.Paused) {
        	mGameEngine.pause();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        
        if(state == GLGameState.Finished) {
            mGameEngine.pause();
            mGameEngine.dispose();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }            
        }		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;
     
        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);
    
        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix

        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(this.getClass().getName(), "onSurfaceCreated");
		
		mGlGraphics.setGL(gl);
		
        synchronized(stateChanged) {
            if(state == GLGameState.Initialized) {
            	mGameEngine = new GameEngine();
        		
            	this.initGame();
            }
            state = GLGameState.Running;
            //screen.resume();
            startTime = System.nanoTime();
        }   
	}
	// END Renderer implementation
    
	private void initGame() {
		ServiceLocator.provide(new GL10TextureService());
		ServiceLocator.getTextureService().registerTexture(R.drawable.a_bird_small);
		ServiceLocator.getTextureService().registerTexture(R.drawable.a_lion_small);
	}

	public void enableTextureLogging() {
		ITextureService current = ServiceLocator.getTextureService();
		ServiceLocator.provide(new LoggedTextureService(current));
	}

}
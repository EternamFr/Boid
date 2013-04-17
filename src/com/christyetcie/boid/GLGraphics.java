package com.christyetcie.boid;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLGraphics {
    GLSurfaceView mGlView;
    GL10 mGl;
    Context mContext;
    
    GLGraphics(GLSurfaceView glView, Context context) {
        this.mGlView = glView;
        this.mContext = context;
    }
    
    public GL10 getGL() {
        return mGl;
    }
    
    void setGL(GL10 gl) {
        this.mGl = gl;
    }
    
    public int getWidth() {
        return mGlView.getWidth();
    }
    
    public int getHeight() {
        return mGlView.getHeight();
    }

    public Context getContext() {
        return mContext;
    }
    
}
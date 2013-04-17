package com.christyetcie.boid.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.christyetcie.boid.Services.ServiceLocator;
import com.christyetcie.boid.Services.Textures.ITextureService;
import com.christyetcie.boid.model.Boid;

public class BoidRenderer implements IRenderer {

	private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private ByteBuffer mIndexBuffer;
    private Boid mBoid;

    private int mTextureId;

    private float vertices[] = 
    {
    		//Vertices according to faces
            -1.0f, -1.0f, 1.0f, //Vertex 0
            1.0f, -1.0f, 1.0f,  //v1
            -1.0f, 1.0f, 1.0f,  //v2
            1.0f, 1.0f, 1.0f,   //v3

            1.0f, -1.0f, 1.0f,  //...
            1.0f, -1.0f, -1.0f,         
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,            
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,         
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,

            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,         
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,           
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f
    }; 

    /** The initial texture coordinates (u, v) */ 
    private float texture[] = {
            //Mapping coordinates for the vertices
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f, 

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
            };
    
    /** The initial indices definition */   
    private byte indices[] = {	
            0,1,3, 0,3,2,           //Face front
            4,5,7, 4,7,6,           //Face right
            8,9,11, 8,11,10,        //... 
            12,13,15, 12,15,14,     
            16,17,19, 16,19,18,     
            20,21,23, 20,23,22
            };

    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = -0.01f; // rotational speed for cube
    
    public BoidRenderer(Boid boid) {
    	mBoid = boid;
    	
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        
        //
        vbb = ByteBuffer.allocateDirect(texture.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = vbb.asFloatBuffer();
        mTextureBuffer.put(texture);
        mTextureBuffer.position(0);

        //
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
   }
    
	@Override
	public void render(GL10 gl) {
        // Texture id
        ITextureService textureService = ServiceLocator.getTextureService();
        assert(textureService != null);
        try {
        	mTextureId = textureService.getTexture(mBoid.getTextureRessourceId());
        } catch(Exception e) {
        	Log.d(this.getClass().getName(),e.toString());
        }
        
    	//--------------------------
    	gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
		gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
		gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display) 
	      
		// TODO: how to store translate / Rotate / Scale and apply before drawing?    	
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        gl.glTranslatef(mBoid.mPosition.mX, mBoid.mPosition.mY, mBoid.mPosition.mZ);
		//gl.glTranslatef(mBoid.mPosition.mX, mBoid.mPosition.mY, mBoid.mPosition.mZ + 10.0f * (float) Math.cos(angleCube));
		gl.glRotatef(angleCube * 20.0f * 1.0f, 0.1f, 0.9f, 0.2f);
		//gl.glRotatef((float) Math.cos(angleCube) * 0.01f, 1.0f, 0.0f, 0.0f);
		gl.glScalef(1.0f, 1.0f, 1.0f);
        
		//---------------------------------------------------------------
    	gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        //Enable the vertex and texture state
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //Point to our buffers       
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        //Draw the vertices as triangles, based on the Index Buffer information
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        
        gl.glDisable(GL10.GL_TEXTURE_2D);
        
        gl.glDisable(GL10.GL_CULL_FACE);
        //---------------------
        angleCube += speedCube;
	}

	public void render(GL10 gl, Boid boid) {
		//throw new UnsupportedOperationException("Not implemented yet");
		mBoid = boid;
		render(gl);
	}
	
}

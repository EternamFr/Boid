package com.christyetcie.boid.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GridRenderer implements IRenderer {

	private FloatBuffer mVertexBuffer;
	
    //private static float angleGrid = 0;
    //private static float speedGrid = 1.5f;
    
    public GridRenderer() {
    	 float line_vertex[] = {0.0f, -2.0f, 0.0f, 0.0f, 2.0f, 0.0f};
         
         ByteBuffer vbb = ByteBuffer.allocateDirect(line_vertex.length * 4);
         vbb.order(ByteOrder.nativeOrder());
         mVertexBuffer = vbb.asFloatBuffer();
         mVertexBuffer.put(line_vertex);
         mVertexBuffer.position(0);
    }
    
	@Override
	public void render(GL10 gl) {
		//--------------------------
		// TODO: how to store translate / Rotate / Scale and apply before drawing?
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();        
        
        gl.glTranslatef(0.0f, -2.0f, -10.0f);
        gl.glRotatef(-90.0f, 0.5f, 0.0f, 0.0f);
        gl.glScalef(2.0f, 2.0f, 2.0f);
		
		//--------------------------
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glHint(GL10.GL_LINE_SMOOTH, GL10.GL_NICEST);
        
    	gl.glVertexPointer(3,GL10.GL_FLOAT, 0, mVertexBuffer);
    	
    	gl.glLineWidth(3.0f);
    	gl.glColor4f(0.5f, 1.0f, 0.0f, 0.5f);
 
    	for (int i = -4; i < 5; i++) {
	        gl.glPushMatrix();
	        gl.glTranslatef(i * 0.5f, 0.0f, 0.0f);
	        //gl.glRotatef(angleGrid, 0.5f, 0.0f, 0.0f);
	        
	    	gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	    	gl.glPopMatrix();			
		}
    	
    	for (int i = -4; i < 5; i++) {
	        gl.glPushMatrix();
	        gl.glTranslatef(0.0f, i * 0.5f, 0.0f);
	        gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
	        
	    	gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	    	gl.glPopMatrix();			
		}
    	
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_LINE_SMOOTH);
		
		//angleGrid += speedGrid; // Probably to put in the GridController.Update()
	}

}

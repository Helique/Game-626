package graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

public class TextureList {
	ArrayList<ImageData> toDo = new ArrayList<ImageData>();
	Texture texture = null;
	Vector4f[] crops = null;
	public TextureList(Texture t, Vector4f[] crops){
		this.texture =t;
		this.crops = crops;
	}
	public void addToList(Rectangle posRect,int index){
		Vector4f texRect= crops[index];
		toDo.add(new ImageData(posRect,texRect));
	}
	public void clearList(){
		toDo.clear();
	}
	public void destroy(){
		toDo.clear();
		toDo = null;
		texture = null;
	}
	public int getSize(){
		return crops.length;
	}
	public void renderList(int cameraX,int cameraY){
		texture.bind();
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(ImageData.elementCount * toDo.size());
		
		for(ImageData r:toDo){
			verticesBuffer.put(r.getElements());	
		}
		verticesBuffer.flip();
		int vaoId = GL30.glGenVertexArrays();
		
		GL30.glBindVertexArray(vaoId);
		
			int vboId = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STREAM_DRAW);
				
				GL20.glVertexAttribPointer(0, ImageData.positionElementCount, GL11.GL_FLOAT, 
						false, ImageData.stride, ImageData.positionByteOffset);
				// Put the color components in attribute list 1
				GL20.glVertexAttribPointer(1, ImageData.colorElementCount, GL11.GL_FLOAT, 
						false, ImageData.stride, ImageData.colorByteOffset);
				// Put the texture coordinates in attribute list 2
				GL20.glVertexAttribPointer(2, ImageData.textureElementCount, GL11.GL_FLOAT, 
						false, ImageData.stride, ImageData.textureByteOffset);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
		short[] indices = new short[toDo.size() * 4];
		
		for(int i=0; i< (indices.length); i++){
			indices[i] = (short)i;
		}
		
		int indicesCount = indices.length;
		ShortBuffer indicesBuffer = BufferUtils.createShortBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		int vboiId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		//Send the Draw Command
		GL30.glBindVertexArray(vaoId);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			// Bind to the index VBO that has all the information about the order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);		
				// Draw the vertices
				GL11.glDrawElements(GL11.GL_QUADS, indicesCount, GL11.GL_UNSIGNED_SHORT, 0);			
			// Put everything back to default (deselect)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL15.glDeleteBuffers(vboId);
			GL15.glDeleteBuffers(vboiId);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
		
	}
	
}

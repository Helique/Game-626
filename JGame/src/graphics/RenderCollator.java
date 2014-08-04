package graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import mainBootable.boot;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class RenderCollator {
	HashMap<String,TextureList> textures= new HashMap<String,TextureList>();
	private int pId;

	public void loadTexture(String location){
		Texture t = null;
		try {
			t = TextureLoader.getTexture("PNG", new FileInputStream(new File (location)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.put(location + 0, new TextureList(t));
	}
	public void addRender(String TextureID, int renderX, int renderY, int width, int height,Vector4f texCoord){
		textures.get(TextureID).addToList(new Rectangle(renderX,renderY,width,height),texCoord);
	}
	public void render(int cameraX,int cameraY){
		//ArrayList<TextureList> textureLists = (ArrayList<TextureList>)textures.values();
		
		int uniform1 = GL20.glGetUniformLocation(pId, "ortho");

		float xScl = 2f/(float)boot.screenWidth;
		float yScl = -2f/(float)boot.screenHeight;
		float xTfm = (float)(cameraX*2+boot.screenWidth)/boot.screenWidth;
		float yTfm = (float)(cameraY*2+boot.screenHeight)/boot.screenHeight;
		float[] scale = {
				 xScl, 0.0f, 0.0f, 0.0f,
				 0.0f, yScl, 0.0f, 0.0f,
				 0.0f, 0.0f, 1.0f, 0.0f,
				-xTfm, yTfm, 0.0f, 1.0f
		};
		FloatBuffer scaleBuffer = BufferUtils.createFloatBuffer(16);
		scaleBuffer.put(scale);
		scaleBuffer.flip();
		GL20.glUniformMatrix4(uniform1, true, scaleBuffer);
		for(TextureList TL:textures.values()){
			TL.renderList(cameraX, cameraY);
		}
	}
	public void clear(){
		for(TextureList TL:textures.values()){
			TL.clearList();;
		}
	}
	public void setPID(int pId) {
		this.pId =  pId;
		GL20.glUseProgram(pId);
		
	}
}

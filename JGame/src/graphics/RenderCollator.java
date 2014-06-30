package graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class RenderCollator {
	HashMap<String,TextureList> textures= new HashMap<String,TextureList>();

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
	public void addRender(String TextureID, int renderX, int renderY, int height, int width,Vector4f texCoord){
		textures.get(TextureID).addToList(new Rectangle(renderX,renderY,height,width),texCoord);
	}
	public void render(int cameraX,int cameraY){
		//ArrayList<TextureList> textureLists = (ArrayList<TextureList>)textures.values();
		for(TextureList TL:textures.values()){
			TL.renderList(cameraX, cameraY);
		}
	}
	public void clear(){
		//ArrayList<TextureList> textureLists = (ArrayList<TextureList>)textures.values();
		for(TextureList TL:textures.values()){
			TL.clearList();;
		}
	}
}

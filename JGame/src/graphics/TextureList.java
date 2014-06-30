package graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

public class TextureList {
	ArrayList<ImageData> toDo = new ArrayList<ImageData>();
	Texture texture = null;
	public TextureList(Texture t){
		this.texture =t;
	}
	public void addToList(Rectangle posRect,Vector4f texRect){
		
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
	public void renderList(int cameraX,int cameraY){
		texture.bind();
		for(ImageData r:toDo){
			glLoadIdentity();
			glTranslatef(r.getTexturePosition().getX()-cameraX,r.getTexturePosition().getY()-cameraY,0);
			glBegin(GL_QUADS);
				glTexCoord2f(r.getTexCoord().x, r.getTexCoord().y);
				glVertex2f(0,0);
				glTexCoord2f(r.getTexCoord().w, r.getTexCoord().y);
				glVertex2f(r.getTexturePosition().getHeight(),0);
				glTexCoord2f(r.getTexCoord().w, r.getTexCoord().z);
				glVertex2f(r.getTexturePosition().getHeight(),r.getTexturePosition().getWidth());
				glTexCoord2f(r.getTexCoord().x, r.getTexCoord().z);
				glVertex2f(0,r.getTexturePosition().getWidth());
			glEnd();
			glLoadIdentity();
		}
	}
	
}

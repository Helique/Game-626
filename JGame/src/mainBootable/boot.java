package mainBootable;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class boot {
	world welt;
//	public static int screenWidth = 640;
//	public static int screenHeight = 480;
	public static int screenWidth = 960;
	public static int screenHeight = 800;
	public boot(){
		
		try {
			//Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setDisplayMode(new DisplayMode(960, 800));
			
			Display.setTitle("Test Game");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		welt = new world();
		//grid.setAt(10, 10, BlockType.STONE);
		//init code for OGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,screenWidth,screenHeight, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
		while(!Display.isCloseRequested()){
			// Render
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			welt.draw();
			welt.update(0);
			//drawSelectionBox();
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		
	}
//	private void drawSelectionBox(){
//		int x = selector_x * World.BLOCK_SIZE;
//		int y = selector_y * World.BLOCK_SIZE;
//		int x2 = x + World.BLOCK_SIZE;
//		int y2 = y + World.BLOCK_SIZE;
//		
//		if(grid.getAt(selector_x, selector_y).getType() != BlockType.AIR || selection == BlockType.AIR){
//			glBindTexture(GL_TEXTURE_2D,0);
//			glColor4f(1f, 1f, 1f, 0.5f);
//			glBegin(GL_QUADS);
//				glVertex2i(x,y);
//				glVertex2i(x2,y);
//				glVertex2i(x2, y2);
//				glVertex2i(x, y2);
//			glEnd();
//			glColor4f(1f, 1f, 1f, 1f);
//		} else {
//			glColor4f(1f, 1f, 1f, 0.5f);
//			new Block(selection, x , y).draw();
//			glColor4f(1f, 1f, 1f, 1f);
//			
//		}
//	}
	
	public static void main (String[] args){
		new boot();
	}
}
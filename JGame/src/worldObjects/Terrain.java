package worldObjects;


import static mainBootable.world.BLOCK_SIZE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import graphics.AnimationSequence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import players.Hero;
import players.unit;
import utility.Direction;

public class Terrain {
	protected TerrainType type = TerrainType.AIR;
	private AnimationSequence Animation;
	private float x;
	private float y;
	private int logicX;
	private int logicY;
	int logicZ;
	protected float widthTexture = 1;
	protected float heightTexture = 1;
	protected int logicH = 1;
	protected int logicW = 1;
	protected world parent;
	protected int height = 32;
	protected int width = 32;
	boolean walkable = false;
	public Terrain(TerrainType type, int x, int y, int z, world parent, boolean walkable) {
		Animation = new AnimationSequence(type.location);
		this.type = type;
		if(this.type == TerrainType.DRUNKARDTABLE){
			this.logicH = 4;
			this.logicW = 3;
			widthTexture = 3f/4f;
			heightTexture = 1f;
			
		}
		this.setLogicX(x);
		this.setLogicY(y);
		this.logicZ = z;
		this.parent = parent;
		this.walkable = walkable;
		
	}
	public boolean walkThrough(Direction walkingDirection,unit unit){
		return walkable;
	}
	public void activate(Hero activatingPlayer){
		//parent.removeTerrain(this.logicX,this.logicY,this.logicZ);
	}
	public String getTextureID(){
		return null;
		
	}
	public TerrainType getType() {
		return type;
	}
	public void setType(TerrainType type) {
		this.type = type;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void render(){
		world.renderer.addRender(this.type.location, world.BLOCK_SIZE * this.getLogicX(), world.BLOCK_SIZE*this.getLogicY() , world.BLOCK_SIZE*this.logicW,  world.BLOCK_SIZE*this.logicH, new Vector4f(0,0,widthTexture, heightTexture));
	}
	public int getLogicX() {
		return logicX;
	}
	public void setLogicX(int logicX) {
		this.logicX = logicX;
	}
	public int getLogicY() {
		return logicY;
	}
	public void setLogicY(int logicY) {
		this.logicY = logicY;
	}
}

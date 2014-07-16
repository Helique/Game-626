package players;

import graphics.AnimationSequence;
import graphics.RenderCollator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.TextureLoader;

import practiceGame.Bag;
import utility.Direction;
import worldObjects.Area;

public class Hero implements unit{
	int health;
	static int halfBlockSize = world.BLOCK_SIZE/2;
	int maxHealth;
	int logicX;
	private int logicY;
	private double renderX;
	private double renderY;
	int destinationX;
	int destinationY;
	double speed = 4;
	Area grid;
	
	boolean atDestination = true;
	private Bag backPack = new Bag();
	Direction direction = Direction.DOWN;
	AnimationSequence movingRight = new AnimationSequence("resources/heroRight.png");
	public void setArea(Area currentLocation){
		this.grid = currentLocation;
	}
	public Hero(int health,int maxHealth,int logicX,int logicY,Area grid){
		this.grid = grid;
		this.health =  health;
		this.maxHealth = maxHealth;
		this.logicX = logicX;
		this.setLogicY(logicY);
		setRenderX(logicX * world.BLOCK_SIZE);
		setRenderY(logicY * world.BLOCK_SIZE - halfBlockSize);
		//setRenderY(logicY * world.BLOCK_SIZE);
	}
	public void setDirection (Direction direction){
		this.direction = direction;
	}
	public void move(int destinationX,int destinationY){
		if(atDestination){
			setRenderX(logicX * world.BLOCK_SIZE);
			setRenderY(getLogicY() * world.BLOCK_SIZE - halfBlockSize);
			this.destinationX = destinationX;
			this.destinationY = destinationY;
		}
		atDestination = false;
		
		
	}
	public void draw(RenderCollator renderer) {
		if(direction == Direction.LEFT){
			world.renderer.addRender("resources/heroLeft.png0",(int) getRenderX(),(int) getRenderY(), world.BLOCK_SIZE + halfBlockSize, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.RIGHT){
			world.renderer.addRender("resources/heroRight.png0",(int) getRenderX(),(int) getRenderY(), world.BLOCK_SIZE + halfBlockSize, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.DOWN){
			world.renderer.addRender("resources/heroFront.png0",(int) getRenderX(),(int) getRenderY(), world.BLOCK_SIZE + halfBlockSize, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.UP){
			world.renderer.addRender("resources/heroBack.png0",(int) getRenderX(),(int) getRenderY(), world.BLOCK_SIZE + halfBlockSize, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}
	}

	@Override
	public void update(int delta) {
		if(grid.getTerrain(logicX, logicY, 1) != null){
			grid.getTerrain(logicX, logicY, 1).activate(this);
		}
		if(atDestination == false){
			if(grid.getTerrain(logicX +destinationX, getLogicY() + destinationY, 0).walkThrough(null, this)){
				if(destinationX >0){
					setRenderX(getRenderX() + speed);
					direction = Direction.RIGHT;
				}else if(destinationX <0){
					setRenderX(getRenderX() - speed);
					direction = Direction.LEFT;
				}
				if(destinationY >0){
					setRenderY(getRenderY() + speed);
					direction = Direction.DOWN;
				}else if(destinationY <0){
					setRenderY(getRenderY() - speed);
					direction = Direction.UP;
				}
				if(getRenderX() == ((logicX+destinationX) * world.BLOCK_SIZE) && (getRenderY() == (getLogicY()+destinationY) * world.BLOCK_SIZE - halfBlockSize)){
					atDestination = true;
					logicX +=destinationX;
					setLogicY(getLogicY() + destinationY);
				}
			} else if (!grid.getTerrain(logicX +destinationX, getLogicY() + destinationY, 0).walkThrough(null, this)){
				destinationX = 0;
				destinationY = 0;
				atDestination = true;
			}
		}
		
		
	}

	@Override
	public void setLocation(int x, int y) {
		this.atDestination = true;
		this.destinationX = 0;
		this.destinationY = 0;
		this.logicX = x;
		this.logicY = y;
		setRenderX(logicX * world.BLOCK_SIZE);
		setRenderY(logicY * world.BLOCK_SIZE - halfBlockSize);
		
		
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intersects(unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return logicX;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return logicY;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean walkThrough(Direction walkingDirection, unit unit) {
		// TODO Auto-generated method stub
		return false;
	}
	public double getRenderY() {
		return renderY;
	}
	public void setRenderY(double renderY) {
		this.renderY = renderY;
	}
	public int getLogicY() {
		return logicY;
	}
	public void setLogicY(int logicY) {
		this.logicY = logicY;
	}
	public double getRenderX() {
		return renderX;
	}
	public void setRenderX(double renderX) {
		this.renderX = renderX;
	}
	public Bag getBackPack() {
		return backPack;
	}
	public void setBackPack(Bag backPack) {
		this.backPack = backPack;
	}

}

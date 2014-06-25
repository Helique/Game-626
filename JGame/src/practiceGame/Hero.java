package practiceGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.TextureLoader;

public class Hero implements unit{
	int health;
	int maxHealth;
	int logicX;
	int logicY;
	double renderX;
	double renderY;
	int destinationX;
	int destinationY;
	double speed = 2;
	Area grid;
	boolean atDestination = true;
	Bag backPack = new Bag();
	Direction direction = Direction.DOWN;
	AnimationSequence movingRight = new AnimationSequence("resources/heroRight.png");
	
	public Hero(int health,int maxHealth,int logicX,int logicY,Area grid){
		this.grid = grid;
		this.health =  health;
		this.maxHealth = maxHealth;
		this.logicX = logicX;
		this.logicY = logicY;
		renderX = logicX * world.BLOCK_SIZE;
		renderY = logicY * world.BLOCK_SIZE - 16;
	}
	public void setDirection (Direction direction){
		this.direction = direction;
	}
	public void move(int destinationX,int destinationY){
		if(atDestination){
			renderX = logicX * world.BLOCK_SIZE;
			renderY = logicY * world.BLOCK_SIZE - 16;
			this.destinationX = destinationX;
			this.destinationY = destinationY;
		}
		atDestination = false;
		
		
	}
	public void draw(RenderCollator renderer) {
		if(direction == Direction.LEFT){
			world.renderer.addRender("resources/heroLeft.png0",(int) renderX,(int) renderY, world.BLOCK_SIZE + 16, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.RIGHT){
			world.renderer.addRender("resources/heroRight.png0",(int) renderX,(int) renderY, world.BLOCK_SIZE + 16, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.DOWN){
			world.renderer.addRender("resources/heroFront.png0",(int) renderX,(int) renderY, world.BLOCK_SIZE + 16, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}else if(direction == Direction.UP){
			world.renderer.addRender("resources/heroBack.png0",(int) renderX,(int) renderY, world.BLOCK_SIZE + 16, world.BLOCK_SIZE,new Vector4f(0,0,.75f,1f));
		}
	}

	@Override
	public void update(int delta) {
		if(grid.getTerrain(logicX, logicY, 1) != null){
			grid.getTerrain(logicX, logicY, 1).activate(this);
		}
		if(atDestination == false){
			if(grid.getTerrain(logicX +destinationX, logicY + destinationY, 0).walkThrough(null, this)){
				if(destinationX >0){
					renderX += speed;
					direction = Direction.RIGHT;
				}else if(destinationX <0){
					renderX -= speed;
					direction = Direction.LEFT;
				}
				if(destinationY >0){
					renderY += speed;
					direction = Direction.DOWN;
				}else if(destinationY <0){
					renderY -= speed;
					direction = Direction.UP;
				}
				if(renderX == ((logicX+destinationX) * world.BLOCK_SIZE) && (renderY == (logicY+destinationY) * world.BLOCK_SIZE - 16)){
					atDestination = true;
					logicX +=destinationX;
					logicY +=destinationY;
				}
			} else if (!grid.getTerrain(logicX +destinationX, logicY + destinationY, 0).walkThrough(null, this)){
				destinationX = 0;
				destinationY = 0;
				atDestination = true;
			}
		}
		
		
	}

	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		
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

}

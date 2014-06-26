package worldObjects;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;

import players.Hero;
import players.unit;
import utility.Direction;

public class Building extends Terrain{
	int height = 96;
	int width = 128;
	int doorX = 1;
	int doorY = 2;
	int roofHeight = 1;
	public Building(TerrainType type, int x, int y, int z, world parent) {
		super(type, x, y, z, parent);
		logicH = 3;
		logicW = 4;
		// TODO Auto-generated constructor stub
	}
	public void render(){
		
	}
	public void renderBuilding(){
		world.renderer.addRender(this.type.location, world.BLOCK_SIZE * this.logicX, world.BLOCK_SIZE*this.logicY , this.height, this.width,new Vector4f(0,0,.75f,1));
	}
	public boolean walkThrough(Direction walkingDirection,unit unit){
		if(unit.getY() == this.logicY){
			return true;
		}
		return false;
	}
	public void activate(Hero activatingPlayer){
		if(activatingPlayer.getY() == this.logicY + this.doorY && activatingPlayer.getX() == this.logicX +this.doorX){
			System.out.println("ACtivated!");
		}
	}
	
	
	
}


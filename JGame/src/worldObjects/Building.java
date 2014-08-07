package worldObjects;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;

import players.Hero;
import players.unit;
import utility.Direction;

public class Building extends Terrain{
	int height = 96;
	int width = 128;
	private int doorX = 1;
	private int doorY = 2;
	int roofHeight = 1;
	public Building(TerrainType type, int x, int y, int z, world parent, boolean walkable) {
		super(type, x, y, z, parent, walkable);
		logicH = 3;
		logicW = 4;
		// TODO Auto-generated constructor stub
	}
	public void render(){
		
	}
	public void renderBuilding(){
		world.renderer.addRender(this.type.location, world.BLOCK_SIZE * this.getLogicX(), world.BLOCK_SIZE*this.getLogicY() , this.width, this.height,new Vector4f(0,0,1,.75f));
	}
	public boolean walkThrough(Direction walkingDirection,unit unit){
		if(unit.getY() == this.getLogicY()){
			return true;
		}
		return false;
	}
	public void activate(Hero activatingPlayer){
		if(activatingPlayer.getY() == this.getLogicY() + this.getDoorY() && activatingPlayer.getX() == this.getLogicX() +this.getDoorX()){
			//System.out.println("ACtivated!");
		}
	}
	public int getDoorX() {
		return doorX;
	}
	public void setDoorX(int doorX) {
		this.doorX = doorX;
	}
	public int getDoorY() {
		return doorY;
	}
	public void setDoorY(int doorY) {
		this.doorY = doorY;
	}
	
	
	
}


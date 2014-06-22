package practiceGame;

import org.lwjgl.util.vector.Vector4f;

public class Building extends Terrain{
	int height = 96;
	int width = 128;
	int logicH = 3;
	int logicW = 4;
	public Building(TerrainType type, int x, int y, int z, world parent) {
		super(type, x, y, z, parent);
		// TODO Auto-generated constructor stub
	}
	public void render(){
		
	}
	public void renderBuilding(){
		world.renderer.addRender(this.type.location, world.BLOCK_SIZE * this.logicX, world.BLOCK_SIZE*this.logicY , this.height, this.width,new Vector4f(0,0,.75f,1));
	}
	
	
	
	
}


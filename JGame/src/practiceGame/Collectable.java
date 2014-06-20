package practiceGame;

public class Collectable extends Terrain {

	public Collectable(TerrainType type, int x, int y, int z, world parent) {
		super(type, x, y, z, parent);
		// TODO Auto-generated constructor stub
	}
	
	public void activate(Hero activatingPlayer){
		parent.removeTerrain(this.logicX,this.logicY,this.logicZ);
	}

}

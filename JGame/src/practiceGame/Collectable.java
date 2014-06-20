package practiceGame;

public class Collectable extends Terrain {

	public Collectable(TerrainType type, int x, int y, int z, world parent) {
		super(type, x, y, z, parent);
		// TODO Auto-generated constructor stub
	}
	
	public void activate(Hero activatingPlayer){
		parent.removeTerrain(this.logicX,this.logicY,this.logicZ);
		if(this.type == TerrainType.BUD){
			activatingPlayer.backPack.addItem(ItemType.BUD, 1);
			
		}
		if(this.type == TerrainType.TAPE){
			activatingPlayer.backPack.addItem(ItemType.TAPE, 1);
			
		}
		activatingPlayer.backPack.printBag();
	}

}

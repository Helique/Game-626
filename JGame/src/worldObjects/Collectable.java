package worldObjects;

import players.Hero;
import practiceGame.ItemType;
import mainBootable.world;

public class Collectable extends Terrain {

	public Collectable(TerrainType type, int x, int y, int z, world parent, boolean walkable) {
		super(type, x, y, z, parent, walkable);
		// TODO Auto-generated constructor stub
	}
	
	public void activate(Hero activatingPlayer){
		parent.removeTerrain(this.getLogicX(),this.getLogicY(),this.logicZ);
		if(this.type == TerrainType.BUD){
			activatingPlayer.getBackPack().addItem(ItemType.BUD, 1);
			
		}
		if(this.type == TerrainType.TAPE){
			activatingPlayer.getBackPack().addItem(ItemType.TAPE, 1);
			
		}
		activatingPlayer.getBackPack().printBag();
	}

}

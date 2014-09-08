package worldObjects;

public enum TerrainType {
	STONE("resources/terrain/stone"), AIR("resources/terrain/air"), GRASS("resources/terrain/grassTexture"), DIRT("resources/terrain/dirt"), 
	BUD("resources/objects/bud"), TAPE ("resources/objects/tape"), PLAYERHOUSE("resources/objects/heroHouse"), DRUNKARDTABLE("resources/objects/drunkardTable");
	public final String location;
	TerrainType(String location){
		this.location = location;
	}
}

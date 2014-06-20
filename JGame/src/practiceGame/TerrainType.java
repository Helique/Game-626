package practiceGame;

public enum TerrainType {
	STONE("resources/stone.png"), AIR("resources/air.png"), GRASS("resources/grassTexture.png"), DIRT("resources/dirt.png"), 
	BUD("resources/budTile.png"), TAPE ("resources/tape.png");
	public final String location;
	TerrainType(String location){
		this.location = location;
	}
}

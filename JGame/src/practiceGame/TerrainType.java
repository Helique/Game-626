package practiceGame;

public enum TerrainType {
	STONE("resources/stone.png0"), AIR("resources/air.png0"), GRASS("resources/grassTexture.png0"), DIRT("resources/dirt.png0"), 
	BUD("resources/bud.png0"), TAPE ("resources/tape.png0");
	public final String location;
	TerrainType(String location){
		this.location = location;
	}
}

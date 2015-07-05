package FileSaveLoadOperations;

import graphics.RenderCollator;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import mainBootable.world;
import worldObjects.Area;
import worldObjects.Building;
import worldObjects.Collectable;
import worldObjects.Terrain;
import worldObjects.TerrainType;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class MapLoader {
	public static Map jsonData;
	private static boolean isCollectable = false;
	private static boolean isTerrain = false;
	private static boolean isBuilding = false;
	
	public MapLoader(String filepath) {
		Scanner fileScanner = null;
		File file = new File(filepath);
		String jsonString = "";
		try {
			fileScanner = new Scanner(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(fileScanner.hasNextLine()){
			jsonString += fileScanner.nextLine();
		}
		JsonParserFactory factory = JsonParserFactory.getInstance();
		JSONParser parser = factory.newJsonParser();
		jsonData = parser.parseJson(jsonString);
	}
	
	public void loadTerrain(Area area, RenderCollator renderer, world parent){
		for(int i = 0; i < area.getWidth();i++){
			for(int j = 0; j <area.getHeight();j ++){
				TerrainType terainType = getTerrainType(i, j, "worldZ0");
				Boolean walkable = isWalkable(i, j, "worldZ0");
				area.addTerrain(i, j, new Terrain(renderer,terainType, i,j,0,parent, walkable));
			}
		}
	}
	
	public void loadObjects(Area area, RenderCollator renderer, world parent){
		boolean once = false;
		for(int i = 0; i < area.getWidth();i++){
	yloop: for(int j = 0; j <area.getHeight();j ++){
				
				TerrainType terainType = getTerrainType(i, j, "worldZ1");
				System.out.println(terainType);
				if(terainType == null){
					continue yloop;
				}
				Boolean walkable = isWalkable(i, j, "worldZ1");
				if(isCollectable){
					isCollectable = false;
					area.addObject(i, j, new Collectable(renderer,terainType, i,j,1,parent, walkable)); 
				}
				else if(isTerrain){
					isTerrain = false;
					area.addObject(i, j, new Terrain(renderer,terainType, i,j,1,parent, walkable)); 
				}
				else if(isBuilding && !once){
					area.addObject(i, j, new Building(renderer,terainType,i,j,1,parent, walkable));
					once = true;
				}
				else{
				}
			}
		}
	}
	
	public static TerrainType getTerrainType(Integer i, Integer j, String jsonObjectName){
		Integer x = i;
		Integer y = j;
		TerrainType terrain = null;
		Map jsonHydratedObject = (Map) jsonData.get(jsonObjectName);
		
		switch( (((Map)(((Map)(jsonHydratedObject.get( x.toString() ))).get(  y.toString() ))).get("TerrainType")).toString() ){
			case "Stone":
				terrain = TerrainType.STONE;
				isTerrain = true;
				isCollectable = false;
				break;
			case "Air":
				terrain = TerrainType.AIR;
				isTerrain = true;
				isCollectable = false;
				break;
			case "Grass":
				terrain = TerrainType.GRASS;
				isTerrain = true;
				isCollectable = false;
				break;
			case "Dirt":
				terrain = TerrainType.DIRT;
				isTerrain = true;
				isCollectable = false;
				break;
			case "DrunkardTable":
				terrain = TerrainType.DRUNKARDTABLE;
				isTerrain = true;
				isCollectable = false;
				break;
			case "PlayerHouse":
				terrain = TerrainType.PLAYERHOUSE;
				isTerrain = false;
				isCollectable = false;
				isBuilding = true;
				break;
			case "Bud":
				terrain = TerrainType.BUD;
				isTerrain = false;
				isCollectable = true;
				break;
			case "Tape":
				terrain = TerrainType.TAPE;
				isTerrain = false;
				isCollectable = true;
				break;
			default:
				terrain = null;
				break;
		}
		return terrain;
	}
	
	public static Boolean isWalkable(Integer i, Integer j, String jsonObjectName){
		Integer x = i;
		Integer y = j;
		Boolean walkable = null;
		Map jsonHydratedObject = (Map)jsonData.get(jsonObjectName);
		
		switch( (((Map)(((Map)(jsonHydratedObject.get( x.toString() ))).get(  y.toString() ))).get("Walkable")).toString() ){
			case "1.0":
				walkable = true;
				break;
			case "0.0":
				walkable = false;
				break;
		}
		return walkable;
	}
}

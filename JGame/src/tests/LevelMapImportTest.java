package tests;

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

public class LevelMapImportTest {

	public static Map jsonData;
	private static boolean isCollectable = false;
	private static boolean isTerrain = false;
	
	public LevelMapImportTest(String filepath) {
		Scanner fileScanner = null;
		File file = new File(filepath);
		String jsonString = "";
		///////////////////////////////
		try {
			fileScanner = new Scanner(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		///////////////////////////////
		while(fileScanner.hasNextLine()){
			jsonString += fileScanner.nextLine();
		}
		
		JsonParserFactory factory = JsonParserFactory.getInstance();
		JSONParser parser = factory.newJsonParser();
		jsonData = parser.parseJson(jsonString);
	}
	
	public void loadTerrain(Area area, RenderCollator renderer, world parent){

		//load the world into an arraylist
		for(int i = 0; i < area.getWidth();i++){
			for(int j = 0; j <area.getHeight();j ++){
				
				TerrainType terainType = getTerrainType(i, j, "worldZ0");
				Boolean walkable = isWalkable(i, j, "worldZ0");
				
				
				area.addTerrain(i, j, new Terrain(renderer,terainType, i,j,0,parent, walkable)); 
				
			}
		}
	}
	public void loadObjects(Area area, RenderCollator renderer, world parent){

		//load the world into an arraylist
		for(int i = 0; i < area.getWidth();i++){
	yloop: for(int j = 0; j <area.getHeight();j ++){
				
				TerrainType terainType = getTerrainType(i, j, "worldZ1");
				if(terainType == null){
					continue yloop;
				}
				Boolean walkable = isWalkable(i, j, "worldZ1");
				
				if(isCollectable){
					isCollectable = false;
					area.addTerrain(i, j, new Collectable(renderer,terainType, i,j,1,parent, walkable)); 
					
				}
				else if(isTerrain){
					isTerrain = false;
				area.addTerrain(i, j, new Terrain(renderer,terainType, i,j,1,parent, walkable)); 
				}
				else{
					//Building building = null;
					//area.addObject(i, j, building);
				}
			}
		}
	}
	
	public static TerrainType getTerrainType(Integer i, Integer j, String jsonObjectName){
		Integer x = j;//swapped them due to incorrectly formatting my .json file
		Integer y = i;//
		TerrainType terrain = null;
		
		Map jsonHydratedObject = (Map) jsonData.get(jsonObjectName);
		
		switch( (((Map)(((Map)(jsonHydratedObject.get( x.toString() ))).get(  y.toString() ))).get("TerrainType")).toString() ){
			case "Stone":
				terrain = TerrainType.STONE;
				isTerrain = true;
				break;
			case "Air":
				terrain = TerrainType.AIR;
				isTerrain = true;
				break;
			case "Grass":
				terrain = TerrainType.GRASS;
				isTerrain = true;
				break;
			case "Dirt":
				terrain = TerrainType.DIRT;
				isTerrain = true;
				break;
			case "DrunkardTable":
				terrain = TerrainType.DRUNKARDTABLE;
				isTerrain = true;
				break;
			case "PlayerHouse":
				terrain = TerrainType.PLAYERHOUSE;
				isTerrain = true;
				break;
			case "Bud":
				terrain = TerrainType.BUD;
				isCollectable = true;
				break;
			case "Tape":
				terrain = TerrainType.TAPE;
				isCollectable = true;
				break;
			default:
				break;
		}
		return terrain;
	}
	public static Boolean isWalkable(Integer i, Integer j, String jsonObjectName){
		Integer x = j;//swapped them due to incorrectly formatting my .json file
		Integer y = i;//
		Boolean walkable = null;
		
		Map jsonHydratedObject = (Map) jsonData.get(jsonObjectName);
		
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
/*
		for(int i = 0; i < level.getWidth();i++){
			for(int j = 0; j <level.getHeight();j ++){
				level.addTerrain(i, j, new Terrain(renderer,TerrainType.DIRT, i,j,0,this, true)); 
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
		for(int i = 0; i < level.getWidth();i++){
			level.addTerrain(i, 0, new Terrain(renderer,TerrainType.STONE,i,0,0,this, false));
			level.addTerrain(i, level.getHeight()-1, new Terrain(renderer,TerrainType.STONE,  i,level.getHeight()-1, 0, this, false));
		}
		for(int i = 0; i < level.getHeight();i++){
			level.addTerrain(0, i, new Terrain(renderer,TerrainType.STONE,0,i,0,this,false));
			level.addTerrain(level.getWidth()-1,i, new Terrain(renderer,TerrainType.STONE,  level.getWidth()-1,i, 0, this, false));
			
		}
*/
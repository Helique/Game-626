package tests;

import graphics.RenderCollator;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import mainBootable.world;
import worldObjects.Area;
import worldObjects.Terrain;
import worldObjects.TerrainType;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class LevelMapImportTest {

	public static Map jsonData;
	
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
	
	public static void importMap(Area area, RenderCollator renderer, world parent){

		//load the world into an arraylist
		for(int i = 0; i < area.getWidth();i++){
			for(int j = 0; j <area.getHeight();j ++){
				
				TerrainType terainType = getTerrainType(i, j);
				Boolean walkable = isWalkable(i, j);
				
				
				area.addTerrain(i, j, new Terrain(renderer,terainType, i,j,0,parent, walkable)); 
				//renderer.addRender("resources/dirt.png0", world.BLOCK_SIZE *j + offset, world.BLOCK_SIZE*i + offset, BLOCK_SIZE, BLOCK_SIZE,new Vector4f(0,0,1,1));
				
			}
		}
	}
	
	public static TerrainType getTerrainType(Integer i, Integer j){
		Integer x = j;//swapped them due to incorrectly formatting my .json file
		Integer y = i;//
		TerrainType terrain = null;
		
		Map jsonHydratedObject = (Map) jsonData.get("worldZ0");
		
		switch( (((Map)(((Map)(jsonHydratedObject.get( x.toString() ))).get(  y.toString() ))).get("TerrainType")).toString() ){
			case "Stone":
				terrain = TerrainType.STONE;
				break;
			case "Air":
				terrain = TerrainType.AIR;
				break;
			case "Grass":
				terrain = TerrainType.GRASS;
				break;
			case "Dirt":
				terrain = TerrainType.DIRT;
				break;
		}
		return terrain;
	}
	public static Boolean isWalkable(Integer i, Integer j){
		Integer x = j;//swapped them due to incorrectly formatting my .json file
		Integer y = i;//
		Boolean walkable = null;
		
		Map jsonHydratedObject = (Map) jsonData.get("worldZ0");
		
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
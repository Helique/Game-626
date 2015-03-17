package tests;

import graphics.RenderCollator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import mainBootable.world;
import worldObjects.Area;
import worldObjects.Building;
import worldObjects.Collectable;
import worldObjects.Terrain;
import worldObjects.TerrainType;

import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;
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
		
		Map jsonHydratedObject = (Map) jsonData.get("worldZ0");
		saveMapTest(jsonHydratedObject);
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
					area.addObject(i, j, new Collectable(renderer,terainType, i,j,1,parent, walkable)); 
					
				}
				else if(isTerrain){
					isTerrain = false;
					area.addObject(i, j, new Terrain(renderer,terainType, i,j,1,parent, walkable)); 
				}
				else{
					//Building building = null;
					//area.addObject(i, j, building);
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
				isTerrain = true;
				isCollectable = false;
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
	
	public static void saveMapTest(Map loadedJsonMap){
		HashMap<Integer, HashMap<Integer,HashMap<String,String>>> iMap = new HashMap<Integer, HashMap<Integer,HashMap<String,String>>>();
		HashMap<Integer, HashMap<String, String>> jMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> block = null;
		Integer x = null;
		Integer y = null;
		
		
		for(int i = 0; i < loadedJsonMap.size(); i++){
			jMap.clear();
			for(int j = 0; j < loadedJsonMap.size(); j++){
				x = i;
				y = j;
				block = new HashMap<String, String>();
				
				String walkable = "";
				String terrain = (((Map)(((Map)(loadedJsonMap.get( x.toString() ))).get(  y.toString() ))).get("TerrainType")).toString();
				
				switch( (((Map)(((Map)(loadedJsonMap.get( x.toString() ))).get(  y.toString() ))).get("Walkable")).toString() ){
				case "1.0":
					walkable = "true";
					break;
				case "0.0":
					walkable = "false";
					break;
				}
				
				
				System.out.println(terrain+", "+walkable);
				block.put("TerrainType",  terrain);
				block.put("Walkable",  walkable);
				jMap.put(y, block);
				System.out.println("block --> jMap" +"("+y+")");
				
			}
			iMap.put(x, jMap);
			System.out.println("jMap --> iMap");
		}
		
		
		JsonGeneratorFactory factory = JsonGeneratorFactory.getInstance();
		JSONGenerator generator = factory.newJsonGenerator();
		String json = generator.generateJson(iMap);
		json = json.replace('[', ' ');
		json = json.replace(']', ' ');
		json = json.trim();
		System.out.println(json);
		String jsonEntire = "WorldZ0 = " + json;
		PrintWriter out = null;
		try {
			out = new PrintWriter("jsonCreationTest.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.print(jsonEntire);
		System.out.println(jsonEntire);
		out.close();
		
	}

}

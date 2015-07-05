package FileSaveLoadOperations;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;

import worldObjects.Area;
import worldObjects.Building;
import worldObjects.Terrain;
import worldObjects.TerrainType;

public class MapFileGenerator {
	
	public MapFileGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static void saveMap(Area area, String filename){
		/**************************************************************************************************
		 * 
		 * 	WorldZ0
		 * 
		 **************************************************************************************************/
		HashMap<Integer, HashMap<Integer,HashMap<String,String>>> iMap = new HashMap<Integer, HashMap<Integer,HashMap<String,String>>>();
		HashMap<Integer, HashMap<String, String>> jMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> block = null;
		Integer x = null;
		Integer y = null;
		String walkable;
		String terrain;
	
		for(int i = 0; i < area.getWidth(); i++){
			jMap =  new HashMap<Integer, HashMap<String, String>>();
			for(int j = 0; j < area.getHeight(); j++){
				x = i;
				y = j;
				block = new HashMap<String, String>();
				if(area.getTerrain(x, y, 0).isWalkable()){
					walkable = "1.0";
				}
				else{
					walkable = "0.0";
				}
				switch(area.getTerrain(x, y, 0).getType()){
				case STONE:
					terrain = "Stone";
					break;
				case AIR:
					terrain = "Air";
					break;
				case GRASS:
					terrain = "Grass";
					break;
				case DIRT:
					terrain = "Dirt";
					break;
				default:
					terrain = "null";
					break;
			}
				block.put("TerrainType",  terrain);
				block.put("Walkable",  walkable);
				jMap.put(y, block);
			}
			iMap.put(x, jMap);
		}
		JsonGeneratorFactory factory = JsonGeneratorFactory.getInstance();
		JSONGenerator generator = factory.newJsonGenerator();
		String json = generator.generateJson(iMap);
		json = json.replace('[', ' ');
		json = json.replace(']', ' ');
		json = json.trim();
		System.out.println(json);
		String jsonEntire = "worldZ0 = " + json;
		/**************************************************************************************************
		 * 
		 * 	WorldZ1
		 * 
		 **************************************************************************************************/
		iMap = new HashMap<Integer, HashMap<Integer,HashMap<String,String>>>();
		jMap = new HashMap<Integer, HashMap<String, String>>();
		block = null;
		x = null;
		y = null;
		
		for(int i = 0; i < area.getWidth(); i++){
			jMap =  new HashMap<Integer, HashMap<String, String>>();// replaced jMap.clear();
			for(int j = 0; j < area.getHeight(); j++){
				x = i;
				y = j;
				block = new HashMap<String, String>();
				if(area.getTerrain(x, y, 1) != null){
					walkable = "1.0";
					if(area.getTerrain(x, y, 1).isWalkable()){
						walkable = "1.0";
					}
					else{
						walkable = "0.0";
					}
					System.out.println("Terrain: " + area.getTerrain(x, y, 1).getType());
					switch(area.getTerrain(x, y, 1).getType()){
					case DRUNKARDTABLE:
						terrain = "DrunkardTable";
						break;
					case PLAYERHOUSE:
						terrain = "PlayerHouse";
						break;
					case AIR:
						terrain = "Air";
						break;
					case BUD:
						terrain = "Bud";
						break;
					case TAPE:
						terrain = "Tape";
						break;
					default:
						terrain = "null";
						break;
					}
				}
				else{
					walkable = "1.0";
					terrain = "null";
				}
				block.put("TerrainType",  terrain);
				block.put("Walkable",  walkable);
				jMap.put(y, block);
			}
			iMap.put(x, jMap);
		}
		JsonGeneratorFactory factory2 = JsonGeneratorFactory.getInstance();
		JSONGenerator generator2 = factory2.newJsonGenerator();
		String json2 = generator2.generateJson(iMap);
		json2 = json2.replace('[', ' ');
		json2 = json2.replace(']', ' ');
		json2 = json2.trim();
		System.out.println(json2);
		String jsonEntire2 = "worldZ1 = " + json2;

		/**************************************************************************************************
		 * 
		 * 	worldObjects
		 * 
		 **************************************************************************************************/
		iMap = new HashMap<Integer, HashMap<Integer,HashMap<String,String>>>();
		jMap = new HashMap<Integer, HashMap<String, String>>();
		block = null;
		x = null;
		y = null;
		String zLoc = "";
		
		for(int i = 0; i < area.getWidth(); i++){
			jMap =  new HashMap<Integer, HashMap<String, String>>();// replaced jMap.clear();
			for(int j = 0; j < area.getHeight(); j++){
				x = i;
				y = j;
				block = new HashMap<String, String>();
				if(area.getTerrain(x, y, 1) != null){
					walkable = "1.0";
					if(area.getTerrain(x, y, 1).isWalkable()){
						walkable = "1.0";
					}
					else{
						walkable = "0.0";
					}
					switch(area.getTerrain(x, y, 1).getType()){
					case DRUNKARDTABLE:
						terrain = "DrunkardTable";
						break;
					case PLAYERHOUSE:
						terrain = "PlayerHouse";
						break;
					default:
						terrain = "null";
						break;
					}
					zLoc = ((Integer)area.getTerrain(x, y, 1).logicZ).toString();
				}
				else{
					walkable = "1.0";
					terrain = "null";
					zLoc = "null";
				}
				block.put("TerrainType",  terrain);
				block.put("Walkable",  walkable);
				block.put("zLoc",  zLoc);
				jMap.put(y, block);
			}
			iMap.put(x, jMap);
		}
		JsonGeneratorFactory factory3 = JsonGeneratorFactory.getInstance();
		JSONGenerator generator3 = factory3.newJsonGenerator();
		String json3 = generator3.generateJson(iMap);
		json3 = json3.replace('[', ' ');
		json3 = json3.replace(']', ' ');
		json3 = json3.trim();
		System.out.println(json3);
		String jsonEntire3 = "worldObjects = " + json3;
		PrintWriter out = null;
		try {
			out = new PrintWriter("jsonCreationTest_FromArea.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.print(jsonEntire+" "+jsonEntire2+" "+jsonEntire3);
		out.close();
	}
}

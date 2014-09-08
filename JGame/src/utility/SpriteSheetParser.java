package utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class SpriteSheetParser {
	public static JsonParserFactory factory = JsonParserFactory.getInstance();
	public static Vector4f[] parseSheet(String path){
		Scanner fileScanner = null;
		File file = new File(path + ".json");
		try {
			fileScanner = new Scanner(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonObject = "";
		while(fileScanner.hasNextLine()){
			jsonObject += fileScanner.nextLine();
		}
		JSONParser parser = factory.newJsonParser();
		Map jsonData = parser.parseJson(jsonObject);
		ArrayList<Vector4f> crops = new ArrayList<Vector4f>();
		Map jsonHydratedObject = (Map) jsonData.get("sprite");
		for (Object o : jsonHydratedObject.values()) {
			Map index = (Map)o;
			float x = Float.parseFloat((String) index.get("x"));
			float y = Float.parseFloat((String) index.get("y"));
			float z = Float.parseFloat((String) index.get("width"));
			float w = Float.parseFloat((String) index.get("height"));
			Vector4f entry = new Vector4f(x, y, z, w);
			crops.add(entry);
		}
		Vector4f[] result = crops.toArray(new Vector4f[0]);
		return  result;
	}
}

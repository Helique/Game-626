package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector4f;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class jsonTest {
public static void main (String[] args){
	Scanner fileScanner = null;
	File file = new File("resources/hero/heroBack.json");
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
	
	JsonParserFactory factory = JsonParserFactory.getInstance();
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
		System.out.println();
		System.out.println(index.get("height"));
	}
	
	
	
	
	}
}

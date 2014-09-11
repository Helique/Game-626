package tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector4f;

import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;

public class SpriteSheetCreator {

	public static void main(String[] args) {
		float spriteSheetWidth = 256;
		float spriteSheetHeight = 256;
		float horizontalImages = 8;
		float verticalImages = 5;
		float imageWidth = 32;
		float imageHeight = 32;
		HashMap<String, HashMap<String,Float>> data = new HashMap<String, HashMap<String,Float>>();
		HashMap<String, Float> vector = new HashMap<String, Float>();
		vector.put("x", 0f);
		vector.put("y", 0f);
		vector.put("width", 1f);
		vector.put("height", 1f);
		data.put("1", vector);
		JsonGeneratorFactory factory = JsonGeneratorFactory.getInstance();
		JSONGenerator generator = factory.newJsonGenerator();
		String json = generator.generateJson(data);
		System.out.println(json);
		String jsonEntire = "sprite = " + json;
		PrintWriter out = null;
		try {
			out = new PrintWriter("jsonCreationTest.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(jsonEntire);
		out.close();
	}

}

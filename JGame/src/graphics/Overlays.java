package graphics;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;

public class Overlays {
	boolean displayInventory = false;
	public void toggleInventory(){
		displayInventory = !displayInventory;
	}
	public void render(){
		if(displayInventory){
			world.renderer.addRender("resources/inventoryBG.png0", 50, 50, 380, 540, new Vector4f(0f, 0f, .7421875f, .52734375f));
	
		}
	}
}

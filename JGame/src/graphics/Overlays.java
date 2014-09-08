package graphics;

import mainBootable.world;

import org.lwjgl.util.vector.Vector4f;

public class Overlays {
	boolean displayInventory = false;
	private RenderCollator renderer = null;
	private AnimationSequence views = null;
	
	public Overlays(RenderCollator renderer){
		this.renderer = renderer;
		views = renderer.createAnimation("resources/userInterface/inventoryBG");
	}
	public void toggleInventory(){
		displayInventory = !displayInventory;
	}
	public void render(){
		if(displayInventory){
			views.draw(renderer,50,50,380,540);//world.renderer.addRender("resources/inventoryBG.png0", 50, 50, 380, 540, new Vector4f(0f, 0f, .7421875f, .52734375f));
	
		}
	}
}

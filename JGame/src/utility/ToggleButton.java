package utility;

import org.lwjgl.input.Keyboard;

public class ToggleButton {
	boolean isPressed = false;
	boolean toggle = false;
	int key;
	public ToggleButton(int key){
		this.key = key;
	}
	public boolean update(){
		if(!isPressed && Keyboard.isKeyDown(key)){
				isPressed = true;
				toggle = !toggle;
				return true;
		}
		if(isPressed && !Keyboard.isKeyDown(key)){
			isPressed = false;
		}
		return false;
	}
	public boolean state(){
		return toggle;
	}
	
	
}

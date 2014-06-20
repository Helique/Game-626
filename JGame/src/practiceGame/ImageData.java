package practiceGame;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;

public class ImageData {
	public ImageData(Rectangle posTex, Vector4f texCoord){
		this.texturePosition = posTex;
		this.texCoord = texCoord;

	}
	private Rectangle texturePosition;
	public Rectangle getTexturePosition() {
		return texturePosition;
	}
	private Vector4f texCoord;
	public Vector4f getTexCoord() {
		return texCoord;
	}
}

package graphics;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector4f;

public class ImageData {
	public static int elementCount =  40;
	
	public static final int elementBytes = 4;
	
	// Elements per parameter
	public static final int positionElementCount = 4;
	public static final int colorElementCount = 4;
	public static final int textureElementCount = 2;
	
	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int colorByteCount = colorElementCount * elementBytes;
	public static final int textureByteCount = textureElementCount * elementBytes;
	
	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int colorByteOffset = positionByteOffset + positionBytesCount;
	public static final int textureByteOffset = colorByteOffset + colorByteCount;
	
	public static final int stride = positionBytesCount + colorByteCount + 
			textureByteCount;
	
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
	public float[] getElements(){
		float[] out = new float[ImageData.elementCount];
		int i = 0;
		/////////////////////////////////////////////////
		out[i++] = this.texturePosition.getX();
		out[i++] = this.texturePosition.getY();
		out[i++] = 0;
		out[i++] = 1f;
		// RGBA elements
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		// ST elements
		out[i++] = this.texCoord.x;
		out[i++] = this.texCoord.y;
		
		out[i++] = this.texturePosition.getX();
		out[i++] = this.texturePosition.getY()+this.texturePosition.getHeight();
		out[i++] = 0;
		out[i++] = 1f;
		// Insert RGBA elements
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		// ST elements
		out[i++] = this.texCoord.x;
		out[i++] = this.texCoord.w;
		
		out[i++] = this.texturePosition.getX()+this.texturePosition.getWidth();
		out[i++] = this.texturePosition.getY()+this.texturePosition.getHeight();
		out[i++] = 0;
		out[i++] = 1f;
		// RGBA elements
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		// ST elements
		out[i++] = this.texCoord.z;
		out[i++] = this.texCoord.w;
		
		out[i++] = this.texturePosition.getX()+this.texturePosition.getWidth();
		out[i++] = this.texturePosition.getY();
		out[i++] = 0;
		out[i++] = 1f;
		// RGBA elements
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		out[i++] = 1f;
		// ST elements
		out[i++] = this.texCoord.z;
		out[i++] = this.texCoord.y;
		
		return out;
	}
}

package graphics;

import org.lwjgl.util.vector.Vector4f;


public class AnimationSequence {

		int index = 0;
		int lastFrame;
		private Vector4f[] textureIndex;
		private String SpriteSheet = null;
		public AnimationSequence(int lastFrame, String SpriteSheet){
			this.lastFrame = lastFrame;
			this.SpriteSheet = SpriteSheet;
		}
		public Vector4f getTextureCoords(){
			return textureIndex[index];
		}
		
		public int forward(int amount){
			index += amount;
			
			index = index%lastFrame;
			if (index < 0){
				index = 6 + index;
			}
			return index;
		}
		
		public int backward(int amount){
			index -= amount;
			
			index = index%lastFrame;
			if (index < 0){
				index = 6 + index;
			}
			return index;
		}
		public void setIndex(int pos){
			if(pos<0){
				index = 0;
			}else if(pos > lastFrame){
				index = lastFrame;
			} else {
				index = pos;
			}
		}
		public int getIndex(){
			return index;
		}
		public void draw(RenderCollator renderer, int renderX, int renderY, int width, int height) {
			renderer.addRender(SpriteSheet, renderX, renderY, width, height, index);
		}

	

}

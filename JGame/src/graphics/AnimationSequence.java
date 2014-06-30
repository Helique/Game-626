package graphics;


public class AnimationSequence {

		int index = 0;
		int lastFrame;
		private String SpritePath;
		private String[] textureID;
		public AnimationSequence(String SpriteSheetPath){
			SpritePath = SpriteSheetPath;
			textureID = new String[1];
			textureID[0] = SpritePath + 0;
			lastFrame = 0;
		}
		public String getCurrentTexture(){
			return textureID[index];
		}
		public boolean forwardOne(){
			index++;
			if(index > lastFrame){
				index = lastFrame;
				return false;
			} else {
				return true;
			}
		}
		public boolean backOne(){
			index--;
			if(index < 0){
				index = 0;
				return false;
			} else {
				return true;
			}
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

	

}

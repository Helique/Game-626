package audio;

public enum AudioGroup {
	MASTER(0), SOUND_EFFECT(1), BACKGROUND_MUSIC(2); 
	
	public final int data;
	
	AudioGroup(int data){
		this.data = data;
	}
	public int getGroupID(){
		return data;
	}
}

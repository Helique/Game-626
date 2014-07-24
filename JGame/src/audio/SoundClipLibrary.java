package audio;

public enum SoundClipLibrary {
	DOOR_CREAK("resources/doorCreaking.wav");
	
	public final String location;
	
	SoundClipLibrary(String location){
		this.location = location;
	}
	public String getFileLocation(){
		return location;
	}
}

package events;

import java.io.FileNotFoundException;

import audio.*;

public class Event {
	int eventNumber =0;
	private EventType type = null;
	String area;
	int TeleportationLocation;
				//AudioEngine audioEngine;
	//Event
	public Event(EventType t){
		this.type = t;	
				//AudioEngine audioEngine = new AudioEngine();
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	//Teleport
	public String getTeleArea(){
		return area;
	}
	public int getTeleportationLocation() {
		return TeleportationLocation;
	}
	public void setTeleLocation(String area, int TeleLocation){
		this.TeleportationLocation = TeleLocation;
		this.area = area;
	}
	
	//sound effect
	public void playSoundEffect(SoundClipLibrary sound) throws FileNotFoundException{
		String audioFileLocation = sound.getFileLocation();
				//audioEngine.play(audioFileLocation);
		new AudioEngine().play(audioFileLocation);
	}
}

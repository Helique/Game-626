package events;

public class Event {
	int eventNumber =0;
	private EventType type = null;
	String area;
	int TeleportationLocation;
	public int getTeleportationLocation() {
		return TeleportationLocation;
	}
	public Event(EventType t){
		this.type = t;	
	}
	public String getTeleArea(){
		return area;
	}
	public void setTeleLocation(String area, int TeleLocation){
		this.TeleportationLocation = TeleLocation;
		this.area = area;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
}

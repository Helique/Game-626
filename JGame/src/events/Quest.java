package events;

import java.util.ArrayList;
import java.util.HashMap;

public class Quest {
	ArrayList<Trigger> inactiveTriggers = new ArrayList<Trigger>();
	HashMap<Integer,Trigger> activatedTriggers = new HashMap<Integer,Trigger>();
	ArrayList<Event> events = new ArrayList<Event>();
	public Quest(ArrayList<Trigger> triggers,ArrayList<Event> events, int[] startingActivated){
		inactiveTriggers = triggers;
		if(startingActivated != null){
			for(int i: startingActivated){
				activateTrigger(i);
			}
		}
		this.events = events;
	}
	public HashMap<Integer,Trigger> getActivatedTriggers (){
		return activatedTriggers;
	}
	public void deactivateTrigger(int triggerNum){
		//inactiveTriggers.add(triggerNum, activatedTriggers.get(triggerNum));
		activatedTriggers.remove(triggerNum);
		//activatedTriggers.add(triggerNum, null);
	}
	public void activateTrigger(int triggerNum){
		
		activatedTriggers.put(triggerNum,inactiveTriggers.get(triggerNum));
		//activatedTriggers.get(activatedTriggers.size()-1).setActivationNumber(activatedTriggers.size()-1);
		//inactiveTriggers.remove(triggerNum);
	}
	public Event getEvent(int eventNum){
		return events.get(eventNum);
	}
	
}

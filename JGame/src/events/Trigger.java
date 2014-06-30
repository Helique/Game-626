package events;

import practiceGame.ItemStack;

public class Trigger {
	private TriggerType type = null;
	int triggerNumber = 0;
	
	public int getTriggerNumber() {
		return triggerNumber;
	}
	ItemStack itemRequirement = null;
	int triggerX;
	int triggerY;
	private int precedingEventNum;
	int[] triggersToActivate;
	public int[] getTriggersToActivate() {
		return triggersToActivate;
	}
	public void setTriggersToActivate(int[] triggersToActivate) {
		this.triggersToActivate = triggersToActivate;
	}
	public int getProcedingEventNum() {
		return precedingEventNum;
	}
	public void setPrecedingEventNum(int precedingEventNum) {
		this.precedingEventNum = precedingEventNum;
	}
	public int getTriggerX() {
		return triggerX;
	}
	public int getTriggerY() {
		return triggerY;
	}
	public Trigger (TriggerType type, int num){
		this.type = type;
		this.triggerNumber = num;
	}
	public void setLocation(int x, int y) {
		triggerY = y;
		triggerX = x;
		
	}
	public void setItemRequirement(ItemStack itemRequirement){
		this.itemRequirement = itemRequirement;
	}
	public TriggerType getType() {
		return type;
	}
	public void setType(TriggerType type) {
		this.type = type;
	}
	public int EventActivated(){
		return precedingEventNum;
	}
	public ItemStack getItemRequirement(){
		return itemRequirement;
	}
}

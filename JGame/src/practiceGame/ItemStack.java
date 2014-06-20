package practiceGame;

public class ItemStack {
	ItemType item;
	int number;
	public ItemStack(ItemType item, int number){
		this.item = item;
		this.number = number;
	}
	public void changeNumber(int deltaItems){
		this.number += number;
	}
	public void setNumber(int number){
		this.number = number;
	}
	public ItemType getItemType(){
		return this.item;
	}
	public int getNumber(){
		return number;
	}
}

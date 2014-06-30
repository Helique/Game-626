package practiceGame;

import java.util.ArrayList;
import java.util.HashMap;

public class Bag {
	HashMap<ItemType,ItemStack> backpack = new HashMap<ItemType,ItemStack>();
	
	public void addItem(ItemType item, int number){
		
		if(backpack.containsKey(item)){
			backpack.get(item).changeNumber(number);
		} else {
			backpack.put(item, new ItemStack(item, number));
		}
	}
	
	public void addItem(ItemStack item){
		if(backpack.containsKey(item.getItemType())){
			backpack.get(item.getItemType()).changeNumber(item.getNumber());
		} else {
			backpack.put(item.getItemType(), item);
		}
	}
	public boolean removeItem(ItemStack item){
		if(backpack.containsKey(item.getItemType())){
			if(backpack.get(item.getItemType()).getNumber() > item.getNumber()){
				backpack.get(item.getItemType()).changeNumber(-item.getNumber());
				return true;
			}
		}
		return false;
	}
	public void printBag(){
		System.out.println("----[Start Bag Contents]----");
		for( ItemStack v :backpack.values()){
			System.out.println(v.getItemType().toString() + " : " + v.getNumber());
		}
		System.out.println("----[End Bag Contents]----");
	}
	public int getItemCount(ItemType t){
		if(backpack.containsKey(t)){
			return backpack.get(t).number;
		} else {
			return 0;
		}
		
		
	}
	
	
}

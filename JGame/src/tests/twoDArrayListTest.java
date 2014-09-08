package tests;

import java.util.ArrayList;

public class twoDArrayListTest {

	public static void main(String[] args) {
		ArrayList<ArrayList<String>> storageMatrix = new ArrayList<ArrayList<String>>();
		storageMatrix.add(new ArrayList<String>());
		storageMatrix.add(new ArrayList<String>());
		storageMatrix.add(new ArrayList<String>());
		
		
	//TV Show
		//Season1
		storageMatrix.get(0).add("s01e01");
		storageMatrix.get(0).add("s01e02");
		storageMatrix.get(0).add("s01e03");
		
		//Season2
		storageMatrix.get(1).add("s02e01");
		
		//Season3
		storageMatrix.get(2).add("s03e01");
		storageMatrix.get(2).add("s03e02");
		storageMatrix.get(2).add("s03e03");
		
		System.out.println(storageMatrix.get(0).get(2));
		System.out.println(storageMatrix.get(1).get(0));
		System.out.println(storageMatrix.get(2).get(1));
		
	}

}

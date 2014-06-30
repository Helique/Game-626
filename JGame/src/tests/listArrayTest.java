package tests;

import java.util.ArrayList;

public class listArrayTest {

	public static void main(String[] args) {
		ArrayList<String> s = new ArrayList<String>();
		s.add(0,"Hello");
		s.add(1,"World");
		s.add(2,"How is it going?");
		s.remove(1);
		System.out.println(s.get(0));
		System.out.println(s.get(2));
	}

}

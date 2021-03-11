package myTools;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class MyLinkedList extends LinkedList<Character>{
	
	public MyLinkedList() {}
	
	@Override
	public boolean contains(Object o) {
		char trazeni = (Character) o;
		
		for (char c : this.toString().toCharArray()) {
			if (c == trazeni)
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean removeFirstOccurrence(Object o) {
		char trazeni = (Character) o;
		
		for (Character c : this) {
			if (c == trazeni) {
				remove(c);
				return true;
			}
		}
		
		return false;
	}
}
package composition;

import map.NoteMap;

public class Test {
	public static void main(String[] args) {
		
		Composition c = Composition.getInstance();
		c.setCompName("o");
		c.create();
		
		//System.out.println(c.toString());
		System.out.println(NoteMap.getInstance().whiteSymbols().size());
		System.out.println(NoteMap.getInstance().blackSymbols().size());
	}
}

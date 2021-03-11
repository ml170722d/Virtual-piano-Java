package map;

public class Test {
	public static void main(String[] args) {
		NoteMap m = NoteMap.getInstance();
		
		System.out.println(m.whiteSymbols());
		System.out.println(m.blackSymbols());
	}
}

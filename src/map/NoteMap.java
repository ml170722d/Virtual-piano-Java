package map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Stream;


public class NoteMap {
	private Map<Integer, Record> map = new HashMap<>();
	
	private static NoteMap instance;
	private static final String path = "data/map.csv";
	
	private NoteMap() {
		ucitaj();
	}
	
	public Map<Integer, Record> getMap() {
		return map;
	}
	public static NoteMap getInstance() {
		if(instance == null) {
			instance = new NoteMap();
		}
		return instance;
	}
	
	public void ucitaj() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Stream<String> s = reader.lines();
			
			s.forEach(l->{
				Record r = Record.parsiraj(l);
				map.put(r.getMidi(), r);
			});
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Fajl nije pronadjen...");
		} catch (IOException e) {
		}
		
		map.put(1, new Record(' ', "_", 1));
		map.put(2, new Record('|', " | ", 2));
	}
	
	public Record find_by_char(char c) {
		for (Map.Entry<Integer, Record> e : map.entrySet()) {
			if (e.getValue().getSymbol() == c)
				return e.getValue();
		}
		return null;
	}
	
	public Record find_by_note(String s) {
		for (Map.Entry<Integer, Record> c : map.entrySet()) {
			if (c.getValue().getNote().equals(s)) 
				return c.getValue();
		}
		return null;
	}
	
	public Record find_by_midi(int s) {
		return map.get(s);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		for(Map.Entry<Integer, Record> e : map.entrySet()) {
			s.append(e.getKey() + "\t" + e.getValue().toString() + "\n");
		}
		
		return s.toString();
	}
	
	public Vector<Character> whiteSymbols(){
		Vector<Character> vec = new Vector<>();
		
		for (Map.Entry<Integer, Record> e : map.entrySet()) {
			if (e.getValue().getNote().equals("_") || e.getValue().getNote().equals(" | "))
				continue;
			if (e.getValue().getNote().charAt(1) != '#') {
				vec.add(e.getValue().getSymbol());
			}
		}
		return vec;
	}
	
	public Vector<Character> blackSymbols(){
		Vector<Character> vec = new Vector<>();
		
		for (Map.Entry<Integer, Record> e : map.entrySet()) {
			if (e.getValue().getNote().equals("_") || e.getValue().getNote().equals(" | "))
				continue;
			if (!(e.getValue().getNote().charAt(1) != '#')) {
				vec.add(e.getValue().getSymbol());
			}
		}
		return vec;
	}
	
}

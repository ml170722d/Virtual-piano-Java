package composition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import map.NoteMap;
import music_symbols.*;

public class Composition {
	
	private static Composition instance;
	private String compPath;
	private static final NoteMap map = NoteMap.getInstance();
	private boolean created;
	
	private Vector<MusicSymbol> MSVec;
	
	private static final Duration d4 = new Duration(4),
			d8 = new Duration(8);
	
	public Vector<MusicSymbol> getMSVec() {
		return MSVec;
	}
	
	public static Composition getInstance() {
		if (instance == null)
			instance = new Composition();
		
		return instance;
	}
	
	public boolean getCreated(){
		return created;
	}
	
	private Composition() {
		created = false;
	}
	
	public void drop() {
		MSVec.clear();
		created = false;
	}
	
	public void create() {
		MSVec = new Vector<>();
		makeMSVec();
		created = true;
	}
	
	public Composition setCompName(String compName) {
		this.compPath = "data/" + compName + ".txt";
		return this;
	}
	
	public Composition setCompPath(String compPath) {
		this.compPath = compPath;
		return this;
	}
	
	public static boolean existFileName(String name) {
		return existFilePath("data/" + name + ".txt");
	}
	
	public static boolean existFilePath(String path) {
		return (new File(path)).exists();
	}
	
	private void makeMSVec() {
		if (compPath.equals("")) {
			System.out.println("Set name or path to composition");
			return;
		}
		Vector<String> vs = load(compPath);
		
		vs.forEach(s->{
			if (s.equals(" "))
				MSVec.add(new Pause(d8));
			else if (s.equals(" | "))
				MSVec.add(new Pause(d4));
			else {
				if (s.charAt(0) == '[') {
					if (s.charAt(2) == ' ') {//note duzine 1/8
						for (char c : s.toCharArray()) {
							if (c != '[' && c != ']' && c != ' ')
								MSVec.add(new Note(d8, map.find_by_char(c)));
						}
					}else {
						Chored chored = new Chored(d4);
						for (char c : s.toCharArray()) {
							if (c != '[' && c != ']')
								chored.add(new Note(d4, map.find_by_char(c)));
						}
						MSVec.add(chored);
					}
				}else {
					for (char c : s.toCharArray()) {
						MSVec.add(new Note(d4, map.find_by_char(c)));
					}
				}
			}
		});
	}
	
	private Vector<String> load(String path){
		final Vector<String> v = new Vector<>();
		
		try {
			BufferedReader f = new BufferedReader(new FileReader(new File(path)));
			Stream<String> line = f.lines();
	
			Pattern p = Pattern.compile("^.*?( \\| | |\\[[0-9a-z\\s]*\\]|[a-zA-Z0-9]*)([^;]*)$");
			
			line.forEach(l->{
				Matcher m = p.matcher(l);
				while(m.matches() && m.group(0).length() != 0) {
					v.add(m.group(1));
					m=p.matcher(m.group(2));
				}
			});		
			f.close();
		} catch (FileNotFoundException e) {
			System.err.println("Fajl nije pronadjen");
			return null;
		} catch (IOException e) {
			System.out.println("ne mogu da zatvorim fajl");
		}
		return v;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append(compPath + ":\n");
		
		MSVec.forEach(ms->{
			s.append(ms.toString() + "\n");
		});
		
		return s.toString();
	}

	public NoteMap getMap() {
		return map;
	}
	
}

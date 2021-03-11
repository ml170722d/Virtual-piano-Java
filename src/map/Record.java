package map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record {
	private char symbol;
	private String note;
	private int midi;
	
	public Record(char s, String n, int m) {
		super();
		note=n;
		midi=m;
		symbol=s;
	}

	public String getNote() {
		return note;
	}

	public int getMidi() {
		return midi;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public static Record parsiraj(String s) {
		Record l = null;
		
		Pattern p = Pattern.compile("^(.*),(.*),(.*)$");
		Matcher m = p.matcher(s);
		if(m.matches()) {
			String a = m.group(1);
			String b = m.group(2);
			String c = m.group(3);
			
			l = new Record(a.charAt(0),b,Integer.parseInt(c));
		}
		return l;
	}
	
	public boolean equals(Record r) {
		if (midi == r.midi && symbol == r.symbol && note.equals(r.note))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return symbol + "," + note + "," + midi;
	}
}

package music_symbols;

import java.util.Vector;

import map.Record;

public class Note extends Symbol {

	private Record r;
	
	public Note(Duration dd, Record rr) {
		super(dd);
		r=rr;
	}

	@Override
	public String getNoteDesc() {
		return r.getNote();
	}

	@Override
	public int getMidiInt() {
		return r.getMidi();
	}

	@Override
	public Vector<MusicSymbol> add(MusicSymbol s) {
		return null;
	}

	@Override
	public boolean is_Note() {
		return true;
	}

	@Override
	public boolean is_Chord() {
		return false;
	}

	@Override
	public String toString() {
		if (Duration.cmp(getDur(), new Duration(8)) == 0)
			return getNoteDesc() + "*";
		return getNoteDesc();
	}

	@Override
	public String getSymbol() {
		String s = r.getSymbol() + "";
		return s;
	}

	@Override
	public Vector<MusicSymbol> getChored() {
		return null;
	}
}

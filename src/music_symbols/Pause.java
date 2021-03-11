package music_symbols;

import java.util.Vector;

public class Pause extends Symbol {

	public Pause(Duration dd) {
		super(dd);
	}

	@Override
	public String getNoteDesc() {
		return "_";
	}

	@Override
	public int getMidiInt() {
		return 0;
	}

	@Override
	public Vector<MusicSymbol> add(MusicSymbol s) {
		return null;
	}

	@Override
	public boolean is_Note() {
		return false;
	}

	@Override
	public boolean is_Chord() {
		return false;
	}

	@Override
	public String toString() {
		if (Duration.cmp(getDur(), new Duration(4)) == 0)
			return " | ";
		else
			return " ";
	}

	@Override
	public String getSymbol() {
		if (Duration.cmp(new Duration(8), getDur()) == 0)
			return " ";
		else
			return " | ";
	}

	@Override
	public Vector<MusicSymbol> getChored() {
		return null;
	}
	
}

package formatter;

import java.util.Vector;

import music_symbols.MusicSymbol;

public abstract interface Formatter {
	public abstract boolean exportByName(String fileName, Vector<MusicSymbol> msVec);
	public abstract boolean exportByPath(String fileName, Vector<MusicSymbol> msVec);
}

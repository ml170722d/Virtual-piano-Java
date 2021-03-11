package music_symbols;

import java.util.Vector;

public interface MusicSymbol {
	public abstract String getNoteDesc();
	public abstract int getMidiInt();
	public abstract String getSymbol();
	
	public abstract Duration getDur();
	/*
	 * only used for adding notes(symbols) to chord
	 */
	public abstract Vector<MusicSymbol> add(MusicSymbol s);
	/*
	 * checks if they are node/chord
	 */
	public abstract boolean is_Note();
	public abstract boolean is_Chord();
	
	@Override
	String toString();
	public abstract Vector<MusicSymbol> getChored();
}

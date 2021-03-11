package myTools;

import java.util.Vector;

import composition.Composition;
import music_symbols.MusicSymbol;

public class SheredMSVec {
	
	private Vector<MusicSymbol> MSVec;
	
	@SuppressWarnings("unchecked")
	public SheredMSVec(Vector<MusicSymbol> v) {
		MSVec = (Vector<MusicSymbol>) v.clone();
	}
	
	public synchronized void removeFirstMS() {
		if (!MSVec.isEmpty())
			MSVec.remove(0);
	}
	
	public synchronized MusicSymbol getFirst() {
		if (!MSVec.isEmpty())
			return MSVec.get(0);
		return null;
	}
	
	public synchronized boolean isEmpty() {
		return MSVec.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void reset() {
		MSVec = (Vector<MusicSymbol>) Composition.getInstance().getMSVec().clone();
	}
}

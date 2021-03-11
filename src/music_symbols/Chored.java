package music_symbols;


import java.util.Vector;

public class Chored extends Symbol {

	private Vector<MusicSymbol> chored;
	
	public Chored(Duration dd) {
		super(dd);
		chored = new Vector<>();
	}
	
	private Chored(Duration dd, Vector<MusicSymbol> c) {
		super(dd);
		chored=c;
	}

	@Override
	public String getNoteDesc() {
		StringBuilder s = new StringBuilder("");
		
		chored.forEach(n->{
			s.append(n.getNoteDesc()+" ");
		});
		
		return s.toString();
	}

	@Override
	public int getMidiInt() {
		return 1;
	}

	@Override
	public Vector<MusicSymbol> add(MusicSymbol s) {
		chored.add(s);
		return chored;
	}

	/*
	 * (non-Javadoc)
	 * @see music_symbols.MusicSymbol#split()
	 * the vector is not cloned because this is same music symbol in 2 nearby tacts
	 */

	@Override
	public boolean is_Note() {
		return false;
	}

	@Override
	public boolean is_Chord() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append("[");
		chored.forEach(n->{
			s.append(n.getNoteDesc() + " ");
		});
		s.deleteCharAt(s.toString().length()-1);
		s.append("]");
		
		return s.toString();
	}
	
	public boolean isEmpty() {
		return chored.isEmpty();
	}

	@Override
	public String getSymbol() {
		StringBuilder s = new StringBuilder();
		
		s.append("[");
		chored.forEach(n->{
			s.append(n.getSymbol());
		});
		//s.deleteCharAt(s.toString().length()-1);
		s.append("]");
		
		return s.toString();
	}

	@Override
	public Vector<MusicSymbol> getChored() {
		return chored;
	}
}

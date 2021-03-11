package music_symbols;

public abstract class Symbol implements MusicSymbol {
	private Duration d;
	
	public Symbol(Duration dd) {
		d=dd;
	}
	public Duration getDur() { return d;}
	public void setDur(Duration dd)  { d =dd; }
	
	@Override
	public String toString() {
		return "nesto nije u redu";
	}
	
	public static int cmp(MusicSymbol s1, MusicSymbol s2) {
		if (Duration.cmp(s1.getDur(), s2.getDur()) < 0)
			return -1;
		if (Duration.cmp(s1.getDur(), s2.getDur()) > 0)
			return 1;
		return 0;
	}
	
}

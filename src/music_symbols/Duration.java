package music_symbols;

public class Duration {

private int br, im;
	
	public Duration(int brr, int imm) {
		br = brr;
		im = imm;
	}
	
	public Duration(int imm) {
		this(1, imm);
	}
	
	/*
	 * function shortens duration to the min value for br and im
	 */
	public void shorten() {
		while ((br % 2 == 0) && (im % 2 == 0)) {
			br/=2;
			im/=2;
		}
	}
	
	public int getBr() {
		return br;
	}
	
	public int getIm() {
		return im;
	}
	
	/*
	 * function compares 2 objects of this class and returns 
	 * 0 if same, 
	 * 1 if d is grater than this and 
	 * -1 if this is grater than d
	 */
	public int cmp(Duration d) {
		int tmp1 = br * d.im, //for this
				tmp2 = d.br * im; //for d
		
		if (tmp1 < tmp2)
			return 1;
		if (tmp1 > tmp2)
			return -1;
		return 0;
	}
	
	public static int cmp(Duration d1, Duration d2) {
		int tmp1 = d1.br * d2.im,
				tmp2 = d2.br * d1.im;
		if(tmp1 > tmp2)
			return -1;
		if(tmp1 < tmp2)
			return 1;
		return 0;
	}
	
	/*
	 * next 2 functions adds and subs do durations
	 */
	public void add(Duration d) {
		br = br * d.im + d.br * im;
		im = im * d.im;
		this.shorten();
	}
	
	public void sub(Duration d) {
		br = br * d.im - d.br * im;
		im = im * d.im;
		this.shorten();
	}
	
	public static Duration add(Duration d1, Duration d2) {
		int br = d1.br * d2.im + d2.br * d1.im;
		int im = d1.im * d2.im;
		Duration dd = new Duration(br,im);
		dd.shorten();
		return dd;
	}
	
	public static Duration sub(Duration d1, Duration d2) {
		int br = d1.br * d2.im - d2.br * d1.im;
		int im = d1.im * d2.im;
		Duration dd = new Duration(br,im);
		dd.shorten();
		return dd;
	}
	/*
	 * function canSplit() inspects if duration can be split and return true if it can, 
	 * otherwise return false
	 * 
	 * function split() splits duration on 2 equal halfs but it returns new duration 
	 * so it must be catched in place where this function was called
	 */
	public boolean canSplit() {
		if (im % 2 == 0)
			return true;
		return false;
	}
	
	public Duration split() {
		im*=2;
		return new Duration(br,im);
	}
	
	public boolean equals(Duration d) {
		return false;
	}
	
	@Override
	public String toString() {
		int tmpBr = br,
				tmpIm = im;
			
		if(tmpIm < 4) {
			while (tmpIm < 4) {
				tmpBr*=2;
				tmpIm*=2;
			}
		}else 
			if(tmpIm > 4 && tmpIm < 8) {
			while (tmpIm < 8) {
				tmpBr*=2;
				tmpIm*=2;
			}
		}
		
		return tmpBr+"/"+tmpIm;
	}
}

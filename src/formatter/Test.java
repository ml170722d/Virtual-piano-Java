package formatter;

import composition.Composition;

public class Test {

	public static void main(String[] args) {
		Composition c = Composition.getInstance();
		c.setCompName("o");
		c.create();
		
		Formatter f = new MIDIFormatter();
		System.out.println(f.exportByName("probaba_q", c.getMSVec()));
	}
}

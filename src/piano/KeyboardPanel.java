package piano;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import map.NoteMap;
import myTools.BlackButton;
import myTools.MyButton;
import myTools.WhiteButton;

public class KeyboardPanel {
	
	private HashMap<Character, MyButton> keyboardMap;
	private static KeyboardPanel instance;
	private JPanel keyboard;
	private static final NoteMap map = NoteMap.getInstance();
	
	public static KeyboardPanel getInstance() {
		if (instance == null)
			System.out.println("keyboard not initialized");
		
		return instance;
	}
	
	public HashMap<Character, MyButton> getKeyboardMap() {
		return keyboardMap;
	}
	
	public JPanel getKeyboard() {
		return keyboard;
	}

	public KeyboardPanel(Dimension d) {
		instance = this;
		keyboard = new JPanel();
		keyboardMap = new HashMap<>();
		
		keyboard.setPreferredSize(d);
		keyboard.setBackground(Color.WHITE);
		keyboard.setLayout(new GridLayout(1, 5));
		
		Vector<JPanel> octaves = getOctaves(d, 5);
		octaves.forEach(pan->{
			keyboard.add(pan);
		});
		
	}
	
	public void recolor() {
		for (Map.Entry<Character, MyButton> b : keyboardMap.entrySet()) {
			if (b.getValue() instanceof WhiteButton)
				b.getValue().setBackground(Color.WHITE);
			else
				b.getValue().setBackground(Color.BLACK);
		}
	}
	
	private Vector<JPanel> getOctaves(Dimension d, int numOfOctaves){
		Vector<JPanel> octaves = new Vector<>();
		Vector<Character> whiteButtons = map.whiteSymbols(),
				blackButtons = map.blackSymbols();
		
		for (int i = 0; i < numOfOctaves; i++) {
			LinkedList<Character> black = new LinkedList<>();
			LinkedList<Character> white = new LinkedList<>();
			for (int j = 0; j < 5; j++)
				black.add(blackButtons.elementAt(i*5+j));
			for (int j = 0; j < 7; j++)
				white.add(whiteButtons.elementAt(i*7+j));
			
			octaves.add(createOneOctave(d.width/numOfOctaves, d.height, black, white));
		}
		
		return octaves;
	}
	
	private JPanel createOneOctave(int width, int height, LinkedList<Character> black, LinkedList<Character> white) {
		JPanel p = new JPanel();
		p.setLayout(null);
		
		Dimension whiteBtnDim = new Dimension(width/7, height),
				blackBtnDim = new Dimension(whiteBtnDim.width*2/3, whiteBtnDim.height*2/3);
		
		int start = whiteBtnDim.width - (blackBtnDim.width/2),
				offset = whiteBtnDim.width;
		int x = start;
		
		for (int i = 0; i < 6; i++) {
						
			switch (i) {
			case 2:
				break;
			default:
				MyButton btn = new BlackButton(black.getFirst());
				black.removeFirst();
				btn.setBounds(x, 0, blackBtnDim.width, blackBtnDim.height);
				keyboardMap.put(btn.getLabel().charAt(0), btn);
				p.add(btn);
				break;
			}
			
			x+=offset;
		}
		
		x = 0;
		for (int i = 0; i < 7; i++) {
			MyButton btn = new WhiteButton(white.getFirst());
			white.removeFirst();
			btn.setBounds(x, 0, whiteBtnDim.width, whiteBtnDim.height);
			keyboardMap.put(btn.getLabel().charAt(0), btn);
			p.add(btn);
			x+=whiteBtnDim.width;
		}
		
		return p;
	}
	
}

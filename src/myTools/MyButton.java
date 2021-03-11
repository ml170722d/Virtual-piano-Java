package myTools;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import map.NoteMap;
import map.Record;
import midiplayer.MidiPlayer;

@SuppressWarnings("serial")
public abstract class MyButton extends Button implements MyButtonInterface {

	private Record r;
	private static final MidiPlayer midiPlayer =  MidiPlayer.getInstance();
	private static ActionListener actionListener;
	private static KeyListener keyListener;
	private static boolean stop;
	
	public MyButton(char c) {
		super(c+"");
		stop = true;
		NoteMap map = NoteMap.getInstance();
		r = map.find_by_char(c);
		
		actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stop)
					return;
				
				setBackground(Color.RED);
				midiPlayer.play(r.getMidi(), 80);
				if (e.getSource() instanceof WhiteButton)
					setBackground(Color.WHITE);
				else
					setBackground(Color.BLACK);
				
			}
		};
		
		
		keyListener = new KeyListener() {
			
			LinkedList<Character> events = new LinkedList<>();
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (!events.contains(e.getKeyChar()))
					return;
				
				if (stop)
					return;
				
				if (map.find_by_char(e.getKeyChar()) == null)
					return;
				
				events.remove(events.indexOf(e.getKeyChar()));
				midiPlayer.release(map.find_by_char(e.getKeyChar()).getMidi());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (stop)
					return;
				
				if (events.contains(e.getKeyChar()))
					return;
				
				if (map.find_by_char(e.getKeyChar()) == null)
					return;
				
				midiPlayer.play(map.find_by_char(e.getKeyChar()).getMidi());
				events.add(e.getKeyChar());
				}
		};
		
		addActionListener(actionListener);
		addKeyListener(keyListener);
		
		setListeners();
	}
	
	public int getButtonMidi() {
		return r.getMidi();
	}
	
	public char getButtonSymbol() {
		return r.getSymbol();
	}
	
	public String getButtonNote() {
		return r.getNote();
	}
	
	public void removeListeners() {
		stop = true;
	}
	
	public void setListeners() {
		stop = false;
	}
}

package midiplayer; 
 
import java.awt.Color;
import java.util.Map;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem; 
import javax.sound.midi.MidiUnavailableException; 
import javax.sound.midi.Synthesizer;

import map.NoteMap;
import map.Record;
import myTools.WhiteButton;
//import java.util.Scanner; 
import piano.KeyboardPanel;
 
public class MidiPlayer {     
	private static final int DEFAULT_INSTRUMENT = 1;    
	private MidiChannel channel; 
 
	private static MidiPlayer instance;
	
	public static MidiPlayer getInstance() {
		try {
			if(instance == null) {
				instance = new MidiPlayer();
			}
		} catch (MidiUnavailableException e) {}
		return instance;
	}
	
    public MidiPlayer() throws MidiUnavailableException {       
    	this(DEFAULT_INSTRUMENT);   
    	} 
 
    public MidiPlayer(int instrument) throws MidiUnavailableException {         
    	channel = getChannel(instrument);  
    	} 
 
    public void play(final int note) {  
    	channel.noteOn(note, 50);   
    	} 
 
    public void release(final int note) { 
    	channel.noteOff(note, 50);   
    	} 
 
    public void play(final int note, final long length) /*throws InterruptedException*/ {
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					NoteMap map = NoteMap.getInstance();
					KeyboardPanel keyboard = KeyboardPanel.getInstance();
					
					keyboard.getKeyboardMap().get(map.find_by_midi(note).getSymbol()).setBackground(Color.RED);
					
					play(note);       
			    	Thread.sleep(length);   
			    	release(note); 
			    	
			    	if (keyboard.getKeyboardMap().get(map.find_by_midi(note).getSymbol()) instanceof WhiteButton)
						keyboard.getKeyboardMap().get(map.find_by_midi(note).getSymbol()).setBackground(Color.WHITE);
					else
						keyboard.getKeyboardMap().get(map.find_by_midi(note).getSymbol()).setBackground(Color.BLACK);
				}catch (InterruptedException e) {}
			}
		});
    	t.start();
    			
    	}    
    
    private static MidiChannel getChannel(int instrument) throws MidiUnavailableException { 
    	Synthesizer synthesizer = MidiSystem.getSynthesizer();    
    	synthesizer.open();     
    	return synthesizer.getChannels()[instrument];  
    	}
    
    public void releaseAll() {
    	NoteMap map = NoteMap.getInstance();
    	
    	for (Map.Entry<Integer, Record> ent : map.getMap().entrySet()) {
    		release(ent.getValue().getMidi());
    	}
    }
}
package formatter;

import java.io.File;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import music_symbols.Duration;
import music_symbols.MusicSymbol;

public class MIDIFormatter implements Formatter {

	private int trajanje = 0;
	private ShortMessage message;
	private MidiEvent event;
	
	public MIDIFormatter() {}
	
	@Override
	public boolean exportByPath(String path, Vector<MusicSymbol> msVec) {
		if (msVec == null || path == "" || path == " ") {
			return false;
		}
		
		try
		{
	//****  Create a new MIDI sequence with 24 ticks per beat  ****
			Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ,24);

	//****  Obtain a MIDI track from the sequence  ****
			Track t = s.createTrack();

	//****  General MIDI sysex -- turn on General MIDI sound set  ****
			byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm,(long)0);
			t.add(me);

	//****  set tempo (meta event)  ****
			MetaMessage mt = new MetaMessage();
	        byte[] bt = {0x02, (byte)0x00, 0x00};
			mt.setMessage(0x51 ,bt, 3);
			me = new MidiEvent(mt,(long)0);
			t.add(me);

	//****  set track name (meta event)  ****
			mt = new MetaMessage();
			String TrackName = new String("midifile track");
			mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt,(long)0);
			t.add(me);

	//****  set omni on  ****
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D,0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);

	//****  set poly on  ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F,0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);

	//****  set instrument to Piano  ****
			mm = new ShortMessage();
			mm.setMessage(0xC0, 0x00, 0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);
			
			msVec.forEach(e->{
				//press
				if(e.is_Chord()) {
					e.getChored().forEach(n->{
						try {
							
						message = new ShortMessage();
						message.setMessage(0x90,n.getMidiInt(),0x60);
						event = new MidiEvent(message,(long)trajanje);
						t.add(event);
						
						} catch (InvalidMidiDataException e1) {}
					});
					
					
				}else if(e.is_Note()) {
					try {
						
					message = new ShortMessage();
					message.setMessage(0x90,e.getMidiInt(),0x60);
					event = new MidiEvent(message,(long)trajanje);
					t.add(event);
					
					}catch (InvalidMidiDataException a) {}
				}
				
				if(Duration.cmp(e.getDur(), new Duration(4)) == 0){
					trajanje+=80;
				}else {
					trajanje+=40;
				}
				
				//realise
				if (e.is_Chord()) {
					e.getChored().forEach(n->{
						try {
							
						message = new ShortMessage();
						message.setMessage(0x80,n.getMidiInt(),0x40);
						event = new MidiEvent(message,(long)trajanje);
						t.add(event);
						
						}catch (InvalidMidiDataException a) {}
					});
				}else if(e.is_Note()) {
					try {
						
					message = new ShortMessage();
					message.setMessage(0x80,e.getMidiInt(),0x40);
					event = new MidiEvent(message,(long)trajanje);
					t.add(event);
					
					} catch (InvalidMidiDataException a) {}
				}
			});//end of v.foreach
			
	//****  set end of track (meta event) 19 ticks later  ****
			mt = new MetaMessage();
	        byte[] bet = {}; // empty array
			mt.setMessage(0x2F,bet,0);
			me = new MidiEvent(mt, (long)140);
			t.add(me);

	//****  write the MIDI sequence to a MIDI file  ****
			File f = new File(path + ".mid");
			MidiSystem.write(s,1,f);
			System.out.println(f.getPath());
			
		} catch(Exception e) {
		System.out.println("Exception caught " + e.toString());
		}
		
		return true;
	}
	
	@Override
	public boolean exportByName(String fileName, Vector<MusicSymbol> msVec) {
		return exportByPath("saves/" + fileName, msVec);
	}
}

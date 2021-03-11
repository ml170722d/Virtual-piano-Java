package formatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import map.NoteMap;
import music_symbols.Duration;
import music_symbols.MusicSymbol;

public class TXTFormatter implements Formatter {
	private boolean openBrackets = false;
	
	@Override
	public boolean exportByPath(String path, Vector<MusicSymbol> msVec) {
		
		if (msVec == null)
			return false;
		
		try {
			NoteMap m = NoteMap.getInstance();
			if (msVec == null || m == null) {
				return false;
			}
			
			BufferedWriter f = new BufferedWriter(new FileWriter(path + ".txt"));
			
			
			msVec.forEach(ms->{
				if (ms.is_Chord()) {
					try {
						if (openBrackets) {
							openBrackets = false;
							f.append("]");
						}
						
						f.append("[");						
						ms.getChored().forEach(n->{
							try {
								
								char cc = (m.find_by_midi(n.getMidiInt())).getSymbol();
								f.append(cc);
															
							}catch(IOException e) {}
						});
						f.append("]");
						
					}catch(IOException e) {}
				}else if (ms.is_Note()) {
					try {
						if (Duration.cmp(ms.getDur(), new Duration(4)) == 0) {
							if (!openBrackets) {
								f.append(m.find_by_midi(ms.getMidiInt()).getSymbol());
							}else {
								openBrackets = false;
								f.append("]");
								f.append(m.find_by_midi(ms.getMidiInt()).getSymbol());
							}
						}else {
							if (!openBrackets) {
								openBrackets = true;
								f.append("[");
								f.append(m.find_by_midi(ms.getMidiInt()).getSymbol());
							}else {
								f.append(" ");
								f.append(m.find_by_midi(ms.getMidiInt()).getSymbol());
							}
						}
					}catch (IOException e) {}
				}else {
					try {
						
						if (Duration.cmp(ms.getDur(), new Duration(8)) == 0) {
							if (openBrackets) {
								openBrackets = false;
								f.append("]");
							}
							f.append(" ");
						}else {
							if (openBrackets) {
								openBrackets = false;
								f.append("]");
							}
							f.append(" | ");
						}
						
					}catch (IOException e) {}
				}
			});
			if (openBrackets)
				openBrackets = false;
				f.append("]");
			
			f.close();
		} catch (IOException e) {}
		
		
		return true;
	}

	@Override
	public boolean exportByName(String fileName, Vector<MusicSymbol> msVec) {
		return exportByPath("saves/" + fileName, msVec);
	}
	
}

package myTools;

import java.io.File;
import java.util.LinkedList;
import java.util.Vector;

import composition.Composition;
import formatter.TXTFormatter;
import map.NoteMap;
import music_symbols.*;
import piano.ButtonPanel;
import piano.DisplayPanel;

public class Renderer {

	private static Renderer instance;
	private LinkedList<TmpMem> list;
	private Vector<MusicSymbol> msVec;
	
	public static Renderer getInstance() {
		if (instance == null)
			instance = new Renderer();
		
		return instance;
	}
	
	public static void delete() {
		instance = null;
	}
	
	private Renderer() {
		list = new LinkedList<>();
	}
	
	public void pressed(char c, long start) {
		list.add(new TmpMem(c, start));
	}
	
	public void released(char c, long end) {
		for (TmpMem le : list) {
			if (le.getChar() == c && !le.isFinished()) {
				le.setEnd(end);
				break;
			}
		}
	}
	
	public void render() {
		msVec = new Vector<>();
		int i = 0;
		
		while (i < list.size()) {
			MusicSymbol ms;
			TmpMem curElem = list.get(i);
			
			if (curElem.getChar() == ' ') {
				if (curElem.getLenght() < 500)
					ms = new Pause(new Duration(8));
				else
					ms = new Pause(new Duration(4));
			}else if (Math.abs(curElem.getStart() - list.get(i+1).getStart()) >= 500) {
				if (curElem.getLenght() < 500)
					ms = new Note(new Duration(8), NoteMap.getInstance().find_by_char(curElem.getChar()));
				else 
					ms = new Note(new Duration(8), NoteMap.getInstance().find_by_char(curElem.getChar()));
			}else {
				int j = 1;
				ms = new Chored(new Duration(4));
				
				while (i+j < list.size() && Math.abs(curElem.getStart() - list.get(i+j).getStart()) < 500) {
					Note n = new Note(new Duration(4), NoteMap.getInstance().find_by_char(list.get(i+j).getChar()));
					ms.add(n);
					j++;
				}
				i += j;
			}
			
			msVec.add(ms);
		}
		
		TXTFormatter form = new TXTFormatter();
		form.exportByPath("tmp", msVec);
		File file = new File("tmp.txt");
		
		if (file.exists()) {
			System.out.println("file postji");
		}else {
			System.out.println("file ne postoji");
			System.out.println(file.getPath());
		}
		
		Composition.getInstance().setCompPath(file.getPath()).create();
		ButtonPanel.getInstance().updateSheredMSVec();
		DisplayPanel.getInstance().newCompositionSet();
		
		
		if (file.delete()) {
			System.out.println("nasao sam tmp.txt i obrisao ga");
		}else {
			System.out.println("nisam nasao tmp.txt");
		}
		
		msVec.clear();
	}
	
	public Vector<MusicSymbol> exportInitialized(){
		list.clear();
		return msVec;
	}
	//====================
	//classes
	
	private class TmpMem {
		
		private char c;
		private long start, end;
		private boolean finished;
		
		public TmpMem(char cc, long s) {
			c = cc;
			start = s;
			finished = false;
		}
		
		public void setEnd(long end) {
			this.end = end;
			finished = true;
		}
		
		public char getChar() {
			return c;
		}
		
		public long getStart() {
			return start;
		}
		
		public boolean isFinished() {
			return finished;
		}
		
		public long getLenght() {
			return end - start;
		}
	}
}

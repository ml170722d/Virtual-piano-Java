package piano;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import composition.Composition;
import midiplayer.MidiPlayer;
import music_symbols.Duration;
import music_symbols.MusicSymbol;
import myTools.MyButton;
import myTools.MyLinkedList;
import myTools.Renderer;
import myTools.SheredMSVec;
import myTools.WhiteButton;

public class ButtonPanel {
	
	private static final String PLAY = "Play",
			AUTO = "Auto play",
			EXIT = "Exit mode",
			PAUSE = "Pause",
			CONTINUE = "Continue",
			RENDER = "Render",
			EXPORT = "Export";
	
	private static ButtonPanel instance;
	private JPanel foundationPanel;
	private HashMap<String, JButton> buttons;
	private MyKeyListener myKeyListener;
	private Dimension size;
	private int stage;
	private SheredMSVec sheredMSVec;
	
	private AutoPlayerThread autoPlayerThread;
	private ButtonMaintenanceThread buttonMaintenanceThread;
	private PlayerPlayThread playerPlayThread;
	
	
	public static ButtonPanel getInstance() {
		if (instance == null)
			instance = new ButtonPanel();
		
		return instance;
	}
	
	private ButtonPanel() {
		
		myKeyListener = new MyKeyListener();
		myKeyListener.bootUp();
		
		foundationPanel = new JPanel();
		foundationPanel.setBackground(Color.WHITE);
		setStage0();
		
		InitialCheckThread initialCheckThread = new InitialCheckThread();
		initialCheckThread.start();
		
		
		buttons = new HashMap<>();
		for (int i = 0; i < 7; i++) {
			JButton btn;
			
			switch (i) {
			case 0:
				btn = new JButton(PLAY);
				break;
			case 1:
				btn = new JButton(AUTO);
				break;
			case 2:
				btn = new JButton(EXIT);
				break;
			case 3:
				btn = new JButton(PAUSE);
				break;
			case 4:
				btn = new JButton(CONTINUE);
				break;
			case 5:
				btn = new JButton(RENDER);
				break;
			case 6:
				btn = new JButton(EXPORT);
				break;
			default:
				btn = null;
				break;
			}
			btn.setFocusable(false);
			buttons.put(btn.getText(), btn);
		}
		
		setListeners();
		
	}
	
	private void setListeners() {
		setMyKeyListenerToNormalMode();
		KeyboardPanel keyboard = KeyboardPanel.getInstance();
		
		for(Map.Entry<Character, MyButton> b : keyboard.getKeyboardMap().entrySet()) {
			b.getValue().addKeyListener(myKeyListener);
			//TODO add action listener for keyboard
		}
		
		for (Map.Entry<String, JButton> b : buttons.entrySet()) {
			b.getValue().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent a) {
					
					switch (a.getActionCommand()) {
					case PLAY:
						pokreniButtonMaintenanceThread();
						
						switch (stage) {
						case 1:
							setStage2();
							pokreniPlayerPlayThread();
							break;
						case 3:
						case 4:
							setStage2();
							autoPlayerThread.stani();
							pokreniPlayerPlayThread();
							break;
						default:
							break;
						}
						
						break;
					case AUTO:	
						pokreniButtonMaintenanceThread();
						
						switch (stage) {
						case 1:
							
							setStage3();
							pokreniAutoPlayMode();
							
							break;
						case 2:
							setStage3();
							playerPlayThread.stani();
							pokreniAutoPlayMode();
							break;
						default:
							break;
						}
						
						break;
					case EXIT:
						switch (stage) {
						case 2:
						case 3:
						case 4:
							setStage1();
							buttonMaintenanceThread.zavrsi();
							
							if (autoPlayerThread != null && autoPlayerThread.getState() == Thread.State.WAITING) {
								autoPlayerThread.zavrsi();
							}
							
							if (playerPlayThread != null && 
									(playerPlayThread.getState() == Thread.State.WAITING || 
									playerPlayThread.getState() == Thread.State.TIMED_WAITING)) {
								playerPlayThread.zavrsi();
							}
							
							
							break;
						default:
							break;
						}
						
						break;
					case PAUSE:
						setStage4();
						autoPlayerThread.stani();
						
						break;
					case CONTINUE:
						setStage3();
						autoPlayerThread.kreni();
						
						break;
					case RENDER:
						setStage5();
						Renderer.getInstance().render();
						
						break;
					case EXPORT:
						//TODO
						break;
					default:
						break;
					}
					
					updateButtonPanel();
				}
			});
		}
	}
	//done
	public void updateSheredMSVec() {
		sheredMSVec.reset();
	}
	//done
	public JPanel getButtons() {
		return foundationPanel;
	}
	//done
	public void setSize(Dimension d) {
		size = d;
	}
	//done
	private void removeMyKeyListener() {
		myKeyListener.shutDown();
		KeyboardPanel keyboard = KeyboardPanel.getInstance();
		
		for(Map.Entry<Character, MyButton> b : keyboard.getKeyboardMap().entrySet()) {
			b.getValue().removeListeners();
		}
	}
	//done
	private void setMyKeyListenerToNormalMode() {
		myKeyListener.bootUp(1);
		KeyboardPanel keyboard = KeyboardPanel.getInstance();
		
		for(Map.Entry<Character, MyButton> b : keyboard.getKeyboardMap().entrySet()) {
			b.getValue().setListeners();
		}
	}
	//done
	private void setMyKeyListenerToPlayMode() {
		myKeyListener.bootUp(2);
		KeyboardPanel keyboard = KeyboardPanel.getInstance();
		
		for(Map.Entry<Character, MyButton> b : keyboard.getKeyboardMap().entrySet()) {
			b.getValue().setListeners();
		}
	}
	//done
	private void setMyKeyListenerToRecordMode() {
		myKeyListener.bootUp(3);
		KeyboardPanel keyboard = KeyboardPanel.getInstance();
		
		for(Map.Entry<Character, MyButton> b : keyboard.getKeyboardMap().entrySet()) {
			b.getValue().setListeners();
		}
	}
	//done
	private void setMyKeyListenerToAutoPlayMode() {
		removeMyKeyListener();
	}
	//done
	private void setStage0() {
		stage = 0;
		foundationPanel.setPreferredSize(new Dimension(0, 0));
	}
	//done
	private void setStage1() {
		stage = 1;
		foundationPanel.removeAll();
		foundationPanel.setLayout(new GridLayout(2, 1));
		
		foundationPanel.add(buttons.get(PLAY));
		foundationPanel.add(buttons.get(AUTO));
	}
	//done
	private void setStage2() {
		stage = 2;
		foundationPanel.removeAll();
		foundationPanel.setLayout(new GridLayout(2, 1));
		
		foundationPanel.add(buttons.get(EXIT));
		foundationPanel.add(buttons.get(AUTO));
	}
	//done
	private void setStage3() {
		stage = 3;
		foundationPanel.removeAll();
		foundationPanel.setLayout(new GridLayout(3, 1));
		
		foundationPanel.add(buttons.get(PLAY));
		foundationPanel.add(buttons.get(PAUSE));
		foundationPanel.add(buttons.get(EXIT));
	}
	//done
	private void setStage4() {
		stage = 4;
		foundationPanel.removeAll();
		foundationPanel.setLayout(new GridLayout(3, 1));
		
		foundationPanel.add(buttons.get(PLAY));
		foundationPanel.add(buttons.get(CONTINUE));
		foundationPanel.add(buttons.get(EXIT));
	}
	//TODO use in menu bar for frame
	public void setStage5() {
		stage = 5;
		foundationPanel.removeAll();
		foundationPanel.setLayout(new GridLayout(2, 1));
		
		foundationPanel.add(buttons.get(RENDER));
		foundationPanel.add(buttons.get(EXPORT));
		
		setMyKeyListenerToRecordMode();
	}
	//done
	private void updateButtonPanel() {
		foundationPanel.revalidate();
		foundationPanel.repaint();
	}
	//done
	private void pokreniPlayerPlayThread() {
		if (playerPlayThread == null || playerPlayThread.getState() == Thread.State.TERMINATED) {
			playerPlayThread = new PlayerPlayThread(sheredMSVec);
			return;
		}
		
		if (playerPlayThread.getState() == Thread.State.WAITING) {
			playerPlayThread.kreni();
			return;
		}
	}
	//done
	private void pokreniAutoPlayMode() {		
		if (autoPlayerThread == null || autoPlayerThread.getState() == Thread.State.TERMINATED) {
			autoPlayerThread = new AutoPlayerThread(sheredMSVec);
			return;
		}
		
		if (autoPlayerThread.getState() == Thread.State.WAITING) {
			playerPlayThread.kreni();
			return;
		}
	}
	//done
	private void pokreniButtonMaintenanceThread() {
		if (buttonMaintenanceThread == null || buttonMaintenanceThread.getState() == Thread.State.TERMINATED)
			buttonMaintenanceThread = new ButtonMaintenanceThread(foundationPanel);
		
	}
	
	//====================================
	//private classes
	//done
	private class MyKeyListener implements KeyListener {
		
		/*
		 * mode:
		 * 0 -> turned off
		 * 1 -> normal mode
		 * 2 -> play mode
		 * 3 -> creating new composition
		 */
		private int mode;
		private LinkedList<Character> events = new LinkedList<>();
		
		public MyKeyListener() {
			mode = 1;
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			if (mode != 2)
				return;
			
			playerPlayThread.addPressedNote(e.getKeyChar());
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if (mode == 1) {
				
			KeyboardPanel keyboard = KeyboardPanel.getInstance();
			
			if (keyboard.getKeyboardMap().get(e.getKeyChar()) == null)
				return;
			
			MyButton b = keyboard.getKeyboardMap().get(e.getKeyChar());
			if (b instanceof WhiteButton)
				b.setBackground(Color.WHITE);
			else
				b.setBackground(Color.BLACK);
			
			}else if (mode == 3) {
				
				if (events.contains(e.getKeyChar())) {
					events.remove(events.indexOf(e.getKeyChar()));
					Renderer.getInstance().released(e.getKeyChar(), System.currentTimeMillis());
				}
				
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (mode == 1) {
				
				KeyboardPanel keyboard = KeyboardPanel.getInstance();
				
				if (keyboard.getKeyboardMap().get(e.getKeyChar()) == null)
					return;
				
				keyboard.getKeyboardMap().get(e.getKeyChar()).setBackground(Color.RED);
				
			}else if (mode == 3) {
				
				if (!events.contains(e.getKeyChar())) {
					events.add(e.getKeyChar());
					Renderer.getInstance().pressed(e.getKeyChar(), System.currentTimeMillis());
				}

			}
		}
		
		public void shutDown() {
			mode = 0;
		}
		
		public void bootUp() {
			mode = 1;
		}
		
		public void bootUp(int mode) {
			this.mode = mode;
		}
	}
	//done
	private class AutoPlayerThread extends Thread {
		
		private SheredMSVec MSVec;
		private final MidiPlayer midiPlayer = MidiPlayer.getInstance();
		private boolean radi;
		
		public AutoPlayerThread(SheredMSVec vec) {
			MSVec = vec;
			radi = true;
			start();
		}
		
		public synchronized void stani() {
			try{
				radi = false;
			
				sleep(100);
			}catch (InterruptedException e) {}
		}
		
		public synchronized void kreni() {
			radi = true;
			notify();
		}
		
		public void zavrsi() {
			interrupt();
		}
		
		@Override
		public void run() {
			try {
				sleep(100);
				if (playerPlayThread != null)
					playerPlayThread.zavrsi();
				
				setMyKeyListenerToAutoPlayMode();
				
				while (!interrupted()) {
					
					synchronized (this) {
						while (!radi) wait();
					}
					
					if (!MSVec.isEmpty())
						sviraj();
					else 
						zavrsi();
						
				}
			}catch (InterruptedException e) {}
		}
		
		private void svirajDirku(MusicSymbol ms) {
			if (Duration.cmp(ms.getDur(), new Duration(4)) == 0)
				midiPlayer.play(ms.getMidiInt(), 250);
			else
				midiPlayer.play(ms.getMidiInt(), 125);
		}
		
		private void sviraj() {
			try {
				if (MSVec.getFirst().is_Chord()) {
					MSVec.getFirst().getChored().forEach(n->{
						svirajDirku(n);
					});
					sleep(250);
					
				}else if (MSVec.getFirst().is_Note()) {
					svirajDirku(MSVec.getFirst());
					sleep(250);
				}else {
					if (Duration.cmp(MSVec.getFirst().getDur(), new Duration(4)) == 0)
						sleep(250);
					else
						sleep(125);
				}
				
				DisplayPanel.getInstance().removeElem();
				MSVec.removeFirstMS();
				
			}catch (InterruptedException e) {}
		}
	
	}
	//done
	private class PlayerPlayThread extends Thread{
		
		private boolean radi;
		private SheredMSVec MSVec;
		private MyLinkedList pressedNotes;
		
		
		public PlayerPlayThread(SheredMSVec v) {		
			radi = true;
			MSVec = v;
			pressedNotes = new MyLinkedList();
			start();
		}
		
		public synchronized void addPressedNote(char c) {
			if (!pressedNotes.contains(c))
				pressedNotes.add(c);
		}
		
		public void zavrsi() {
			interrupt();
		}
		
		public synchronized void stani() {
			try{
				radi = false;
			
				sleep(100);
			}catch (InterruptedException e) {}
		}
		
		public synchronized void kreni() {
			radi = true;
			notify();
		}
		
		private boolean check() {	
			boolean key = false;

			MusicSymbol ms = MSVec.getFirst();
			if (ms.is_Chord()) {
				for (MusicSymbol n : ms.getChored()) {
					if (pressedNotes.contains(n.getSymbol().charAt(0))) {
						pressedNotes.removeFirstOccurrence(n.getSymbol().charAt(0));
						key = true;
					}
				}
				
			}else if (ms.is_Note()) {
				if (pressedNotes.contains(ms.getSymbol().charAt(0))) {
					pressedNotes.removeFirstOccurrence(ms.getSymbol().charAt(0));
					key = true;
				}
			
			}else if (!ms.is_Chord() && !ms.is_Note()) {
				if (pressedNotes.isEmpty())
					key = true;
			}
			
			if (pressedNotes.isEmpty() && key)
				return true;
			return false;
		}
		
		@Override
		public void run() {
			try {
				sleep(100);
				if (autoPlayerThread != null)
					autoPlayerThread.zavrsi();
				
				setMyKeyListenerToPlayMode();
				
				while(!interrupted()) {
					
					synchronized (this) {
						while (!radi) wait();
					}
					
					if (MSVec.isEmpty())
						zavrsi();

					sleep(1000);
					
					if (check()) {
						DisplayPanel.getInstance().removeElem();
						MSVec.removeFirstMS();
					}
					pressedNotes.clear();
					
				}
			}catch (InterruptedException e) {}
		}
	}
	//done
	private class ButtonMaintenanceThread extends Thread {

		private JPanel panel;
		
		public ButtonMaintenanceThread(JPanel p) {
			panel = p;
			start();
		}
		
		public void zavrsi() {
			try {
				if (autoPlayerThread != null && autoPlayerThread.getState() != Thread.State.TERMINATED)
					autoPlayerThread.stani();
				
				if (playerPlayThread != null && playerPlayThread.getState() != Thread.State.TERMINATED)
					playerPlayThread.stani();
								
				sleep(200);
				
			}catch (InterruptedException e) {}
		}
		
		@Override
		public void run() {
			try {
				while (!interrupted()) {
					sleep(1000);
					if (autoPlayerThread != null || playerPlayThread != null) {
						System.out.println((autoPlayerThread != null? autoPlayerThread.getState() : "null") + "\t" + (playerPlayThread != null? playerPlayThread.getState():"null"));
						if ((autoPlayerThread == null && playerPlayThread.getState() == Thread.State.TERMINATED) || 
								(autoPlayerThread.getState() == Thread.State.TERMINATED && playerPlayThread == null) ||
								//() ||
								(autoPlayerThread.getState() == Thread.State.TERMINATED && playerPlayThread.getState() == Thread.State.TERMINATED)) {
					
							System.out.println("entered reset");
							restoreResources();
							
							interrupt();
						}
					}
					
				}	
			}catch (InterruptedException e) {}
		}
		
		private void restoreResources() {
			
			setStage1();
			setMyKeyListenerToNormalMode();
			panel.revalidate();
			panel.repaint();
			
			updateSheredMSVec();
			
			DisplayPanel.getInstance().reset();
		}
	}
	//done
	private class InitialCheckThread extends Thread {
		
		private boolean flag;
		
		public InitialCheckThread() {
			flag = false;
		}
		
		@Override
		public void run() {
			try {
				
				while (!interrupted() && !flag) {
					if (Composition.getInstance().getCreated() != false)
						flag = !flag;
					
					sleep(1000);
					
				}
				
				sheredMSVec = new SheredMSVec(Composition.getInstance().getMSVec());
				
				foundationPanel.setPreferredSize(size);
				setStage1();
				updateButtonPanel();
				
			} catch (InterruptedException e) {}
		}
	}

}

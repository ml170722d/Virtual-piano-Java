package piano;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import composition.Composition;

public class PianoGUI {

	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PianoGUI window = new PianoGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PianoGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//needs work
	private void initialize() {
		
		frame = new JFrame("Virtual Piano");
		frame.setBounds(30, 100, 1315, 500);
		frame.setMinimumSize(new Dimension(1315, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//===================================================
		//initialize all needed variables
		Composition .getInstance().setCompName("f").create();
		
		//===================================================
		//adding components to frame
		frame.getContentPane().setLayout(new BorderLayout());
		
		JPanel p1 = getKeyboard(),
				p2 = getDisplayPanel(),
				p3 = getButtonPanel();
				
		JMenuBar menuBar = getMenuBar();
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(p1, BorderLayout.SOUTH);
		frame.getContentPane().add(p2, BorderLayout.CENTER);
		frame.getContentPane().add(p3, BorderLayout.EAST);

	}
	
	//done
	private JPanel getButtonPanel() {
		
		ButtonPanel buttonPanel = ButtonPanel.getInstance();
		buttonPanel.setSize(new Dimension(frame.getWidth()/13, frame.getHeight()/3));
		
		return buttonPanel.getButtons();
	}
	//done
	private JPanel getDisplayPanel() {
		
		DisplayPanel displayPanel = DisplayPanel.getInstance();
		displayPanel.setUpEverything(new Dimension(frame.getWidth()*12/13, frame.getHeight()*2/3));
		
		return displayPanel.getDisplaySaSimbolima();
		
	}
	//done
	private JPanel getKeyboard() {
		KeyboardPanel keyboard = new KeyboardPanel(new Dimension(frame.getWidth(), frame.getHeight()/3));
		
		return keyboard.getKeyboard();
	}
	
	//TODO: needs rework, make class
	private JMenuBar getMenuBar() {
		
		return new VirtualPianoMenuBar();
		
	}

	//===================================================
	//private classes
	
	//TODO: make independet class
	@SuppressWarnings("serial")
	private class VirtualPianoMenuBar extends JMenuBar {
		
		//private ActionListener itemListener;
		private JMenu menu;
		
		public VirtualPianoMenuBar() {
			super();

			menu = new JMenu("File");
			add(menu);
			
			for (int i = 0; i < 7; i++) {
				String s = "";
				JMenuItem item = null;
				switch (i) {
				case 0:
					s = "New";
					item = new JMenuItem(s);
					item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
					break;
				case 1:
					s = "Open File...";
					item = new JMenuItem(s);
					break;
				case 2:
					s = "Open";
					item = new JMenuItem(s);
					item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
					break;
				case 3:
					s = "Save";
					item = new JMenuItem(s);
					item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					break;
				case 4:
					s = "Save As...";
					item = new JMenuItem(s);
					break;
				case 5:
					s = "Help";
					item = new JMenuItem(s);
					item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
					break;
				case 6:
					s = "Exit";
					item = new JMenuItem(s);
					item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK));
					break;
				default:
					break;
				}
				
				menu.add(item);
				
			}
		}
		
		//TODO: napraviti lisener za iteme u meniu da rade kako treba
		
	}

}

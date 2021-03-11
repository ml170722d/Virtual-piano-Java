package piano;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import composition.Composition;
import music_symbols.Duration;
import music_symbols.MusicSymbol;

public class DisplayPanel {
	
	private static final Dimension dim4 = new Dimension(100, 50),
			dim8 = new Dimension(50, 50);
	
	private static final Color note4Color = new Color(255, 0, 0),
			note8Color = new Color(255, 60, 60),
			pause4Color = new Color(0, 255, 0),
			pause8Color = new Color(0, 120, 0),
			choredColor = new Color(200, 0, 0),
			transparent = new Color(0, 0, 0, 0);

	private static DisplayPanel instance;
	private LinkedList<JPanel> nList, sList;
	private DisplyHelpingClass note, simboli;
	private JPanel displaySaNotama, displaySaSimbolima;
	private MyCanvas myCanvasNote, myCanvasSimboli;
	private Dimension velicinaDisplaya;
	private JScrollPane scrollPaneNote, scrollPaneSimboli;

	public static DisplayPanel getInstance() {
		if (instance == null) {
			instance = new DisplayPanel();
		}
		return instance;
	}
	
	private DisplayPanel() {}
	
	@SuppressWarnings("unchecked")
	private void makeSList(Vector<MusicSymbol> MSVec) {
		sList = new LinkedList<>();
		
		MSVec.forEach(ms->{
			
			JPanel panel = new JPanel();
			
			if (ms.is_Chord()) {
				
				panel.setBackground(choredColor);
				panel.setPreferredSize(new Dimension(dim4.width, ms.getChored().size()*25));
				panel.setLayout(new GridLayout(ms.getChored().size(), 1));
				
				ms.getChored().forEach(n->{
					
					JLabel label = new JLabel();
					label.setBackground(transparent);
					label.setPreferredSize(dim4);
					label.setText(n.getSymbol());
					
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					
					panel.add(label);
				});
				
			}else if (ms.is_Note()) {
					
					panel.setLayout(new GridLayout(1, 1));
				
					JLabel label = new JLabel();
					label.setBackground(transparent);
					label.setText(ms.getSymbol());
					
					if (Duration.cmp(ms.getDur(), new Duration(4)) == 0) {
						label.setPreferredSize(dim4);
						panel.setPreferredSize(dim4);
						panel.setBackground(note4Color);
					}else {
						label.setPreferredSize(dim8);
						panel.setPreferredSize(dim8);
						panel.setBackground(note8Color);
					}
					
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					
					panel.add(label);
				
			}else {
				panel.setLayout(new GridLayout(1, 1));
				
				JLabel label = new JLabel();
				label.setBackground(transparent);
				
				if (Duration.cmp(ms.getDur(), new Duration(4)) == 0) {
					label.setPreferredSize(dim4);
					panel.setPreferredSize(dim4);
					panel.setBackground(pause4Color);
				}else {
					label.setPreferredSize(dim8);
					panel.setPreferredSize(dim8);
					panel.setBackground(pause8Color);
				}
				
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				
				panel.add(label);
				
			}
			
			sList.add(panel);
		});
		
		simboli = new DisplyHelpingClass((LinkedList<JPanel>)sList.clone());
	}
	
	@SuppressWarnings("unchecked")
	private void makeNList(Vector<MusicSymbol> MSVec) {
		nList = new LinkedList<>();
		
		MSVec.forEach(ms->{
			
			JPanel panel = new JPanel();
			
			if (ms.is_Chord()) {
				
				panel.setBackground(choredColor);
				panel.setPreferredSize(new Dimension(dim4.width, ms.getChored().size()*25));
				panel.setLayout(new GridLayout(ms.getChored().size(), 1));
				
				ms.getChored().forEach(n->{
					
					JLabel label = new JLabel();
					label.setBackground(transparent);
					label.setPreferredSize(dim4);
					label.setText(n.getNoteDesc());
					
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					
					panel.add(label);
				});
				
			}else if (ms.is_Note()) {
					
					panel.setLayout(new GridLayout(1, 1));
				
					JLabel label = new JLabel();
					label.setBackground(transparent);
					label.setText(ms.getNoteDesc());
					
					if (Duration.cmp(ms.getDur(), new Duration(4)) == 0) {
						label.setPreferredSize(dim4);
						panel.setPreferredSize(dim4);
						panel.setBackground(note4Color);
					}else {
						label.setPreferredSize(dim8);
						panel.setPreferredSize(dim8);
						panel.setBackground(note8Color);
					}
					
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					
					panel.add(label);
				
			}else {
				panel.setLayout(new GridLayout(1, 1));
				
				JLabel label = new JLabel();
				label.setBackground(transparent);
				
				if (Duration.cmp(ms.getDur(), new Duration(4)) == 0) {
					label.setPreferredSize(dim4);
					panel.setPreferredSize(dim4);
					panel.setBackground(pause4Color);
				}else {
					label.setPreferredSize(dim8);
					panel.setPreferredSize(dim8);
					panel.setBackground(pause8Color);
				}
				
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				
				panel.add(label);
				
			}
			
			nList.add(panel);
			
		});
		note = new DisplyHelpingClass((LinkedList<JPanel>)nList.clone());
	}

	private void makeDisplayPanes(Vector<MusicSymbol> MSVec) {
		makeNList(MSVec);
		makeSList(MSVec);
	}
	
	private void setSize(Dimension d) {
		note.setPreferredSize(new Dimension(velicinaDisplaya.width, velicinaDisplaya.height*3/4));
		simboli.setPreferredSize(new Dimension(velicinaDisplaya.width, velicinaDisplaya.height*3/4));
		
		myCanvasNote.setPreferredSize(new Dimension(d.width, velicinaDisplaya.height/4));
		myCanvasSimboli.setPreferredSize(new Dimension(d.width, velicinaDisplaya.height/4));
	}
	
	public void setUpEverything(Dimension d) {
		Vector<MusicSymbol> vec = Composition.getInstance().getMSVec();
		int duz = 0;
		for (MusicSymbol ms : vec) {
			if (ms.is_Chord()) {
				duz+=dim4.width;
			}else {
				if (Duration.cmp(ms.getDur(), new Duration(4)) == 0)
					duz+=dim4.width;
				else
					duz+=dim8.width;
			}
		}
		duz+=vec.size()*5;
		velicinaDisplaya = new Dimension(duz, d.height);
		
		
		displaySaNotama = new JPanel();
		displaySaNotama.setLayout(new GridLayout(2, 1));
		displaySaNotama.setBackground(Color.WHITE);
		
		displaySaSimbolima = new JPanel();
		displaySaSimbolima.setLayout(new GridLayout(2, 1));
		displaySaSimbolima.setBackground(Color.WHITE);
		
		
		myCanvasNote = new MyCanvas();
		myCanvasSimboli = new MyCanvas();
		
		makeDisplayPanes(vec);
		setSize(d);
		
		scrollPaneNote = new JScrollPane(note);
        scrollPaneNote.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneNote.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneNote.setBounds(50, 30, 300, 50);
		displaySaNotama.add(scrollPaneNote);
		displaySaNotama.add(myCanvasNote);
		
		scrollPaneSimboli = new JScrollPane(simboli);
        scrollPaneSimboli.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneSimboli.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSimboli.setBounds(50, 30, 300, 50);
		displaySaSimbolima.add(scrollPaneSimboli);
		displaySaSimbolima.add(myCanvasSimboli);
		
	}
	
	public JPanel getDisplaySaNotama() {
		return displaySaNotama;
	}
	
	public JPanel getDisplaySaSimbolima() {
		return displaySaSimbolima;
	}
	
	public void removeElem() {
		note.removeFirstPanel();
		simboli.removeFirstPanel();
		
		update();		
	}
	
	public void update() {
		displaySaNotama.revalidate();
		displaySaNotama.repaint();
		displaySaSimbolima.revalidate();
		displaySaSimbolima.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void reset() {
		note.reset((LinkedList<JPanel>)nList.clone());
		simboli.reset((LinkedList<JPanel>)sList.clone());
		
		update();
	}
	
	public void newCompositionSet() {
		makeDisplayPanes(Composition.getInstance().getMSVec());
		
		update();
	}
	//===========================================
	//private classes
	
	@SuppressWarnings("serial")
 	private class DisplyHelpingClass extends JPanel {
		
		private LinkedList<JPanel> list;
		
		public DisplyHelpingClass(LinkedList<JPanel> l) {
			super();
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setBackground(Color.WHITE);
			
			
			reset(l);
		}
		
		public void reset(LinkedList<JPanel> l) {
			if (list != null)
				list.clear();
			
			list = l;
			list.forEach(p->{
				add(p);
			});
		}
		
		public void removeFirstPanel() {	
			if (list.size() > 0) {
				remove(list.getFirst());
			
				list.removeFirst();
			
				this.revalidate();
			}else {
				System.out.println("Error, 331 displypanel");
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class MyCanvas extends Canvas {
		private int width, height;
		
		public MyCanvas() {
			setBackground(Color.WHITE);
		}
		
		@Override
		public void setPreferredSize(Dimension preferredSize) {
			super.setPreferredSize(preferredSize);
			width = preferredSize.width;
			height = preferredSize.height;
			repaint();
		}
		
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			
			//System.out.println(width);
			int x = -2;
			while (x < width) {
				g.fillRect(x, 10, 5, height-20);
				x+=dim4.width+5;
			}
		}
		
	}
	
}

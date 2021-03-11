package random_testovi;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class Test2 {

	private JFrame frame;
	private Mypanel panel;
	private LinkedList<Button> labels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test2 window = new Test2();
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
	public Test2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		labels = new LinkedList<>();
		
		for (int i = 0; i < 10; i ++) {
			Button label = new Button();
			label.setPreferredSize(new Dimension(20, 20));
			switch (i%3) {
			case 0:
				label.setBackground(Color.GREEN);
				break;
			case 1:
				label.setBackground(Color.BLUE);
				break;
			case 2:
				label.setBackground(Color.RED);
				break;
			default:
				break;
			}
			label.setLabel("Pusi kurac");
			labels.add(label);
		}
		
		
		
		
		panel = new Mypanel();
		
		
		int i = 0;
		for (Button b : labels) {
			if (i%2 == 0) {
				panel.add1(b);
			}else {
				panel.add2(b);
			}
			i++;
		}
		
		PomThread t = new PomThread();
		t.start();
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		//frame.revalidate();
	}
	
	private class PomThread extends Thread{
		@Override
		public void run() {
			int i = 0;
			while (!labels.isEmpty() && i < 10) {
				try {
					
					sleep(1000);
					/*System.out.println(labels.size());
					Button b = labels.getFirst();
					System.out.println("gor first btn");*/
					
					panel.izbaci(i++);
					System.out.println("removed btn from panel");
					
					/*labels.removeFirst();
					System.out.println("removed btn from list");*/
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			panel.change();
			System.out.println("finished");
		}
	}
	
	@SuppressWarnings("serial")
	private class Mypanel extends JPanel{
		public JPanel p1 = new JPanel(),
				p2 = new JPanel();
		
		public Mypanel() {
			super();
			this.setLayout(new GridLayout(1,1));
			add(p1);
			//add(p2);
			p1.setBackground(Color.GREEN);
			p2.setBackground(Color.RED);
		}
		
		public void add1(Component comp) {
			p1.add(comp);
		}
		
		public void add2(Component comp) {
			p2.add(comp);
		}
		
		public void izbaci(int i){
			/*p1.remove(labels.getFirst());
			labels.removeFirst();
			p2.remove(labels.getFirst());
			labels.removeFirst();*/
			p1.remove(labels.get(i));
		}
		
		public void change() {
			remove(p1);
			add(p2);
			this.revalidate();
		}
		
	}

}

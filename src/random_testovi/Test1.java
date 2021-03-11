package random_testovi;

import java.util.Vector;

public class Test1 {

	private Vector<pomThread> pVec = new Vector<>();
	
	public Test1() {
		pocni();
	}
	
	private void pocni() {
		for (int i = 0; i < 10; i++) {
			pomThread pt = new pomThread();
			pVec.add(pt);
			System.out.println(pVec.size());
			pt.start();
		}
		System.out.println("============================");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("============================");
	}
	
	
	public static void main(String[] args) {
		new Test1();
		System.out.println("prog fin");
	}
	
	private class pomThread extends Thread {
		
		public pomThread() {}
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				System.out.println(pVec.size());
				
				System.out.println("Fin");
			} catch (InterruptedException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
	}

}

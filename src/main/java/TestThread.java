
public class TestThread {

	static class MyThread extends Thread {
		
		private String name;
		public MyThread(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			for(int i = 0; i < 10 ; i++) {
				System.out.println(this.name + " reports: i = " + i);
			}
		}
	}

	public static void main(String[] args) {
        new MyThread("Thread A").start();
        new MyThread("Thread B").start();
        new MyThread("Thread C").start();
	}	
}
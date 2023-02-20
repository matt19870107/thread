
public class TestRunnable {

	static class MyCallable implements Runnable {

		private String name;
		public MyCallable(String name) {
			this.name = name;
		}

		@Override
		public  void run() {
			for(int i = 0; i < 10 ; i++) {
				System.out.println(this.name + " reports: i = " + i);
			}
		}
	}

	public static void main(String[] args) {
		MyCallable mycallableA = new MyCallable("Thread A");
		MyCallable mycallableB = new MyCallable("Thread B");

		Thread threadA1 = new Thread(mycallableA);
		threadA1.start();
		System.out.println(threadA1.getState().name());
		Thread threadA2 = new Thread(mycallableB);
		threadA2.start();
		System.out.println(threadA2.getState().name());
	}	
}
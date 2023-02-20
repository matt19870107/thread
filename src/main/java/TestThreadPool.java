import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPool {
	
	// define my thread factory
    static class MyThreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }
	
	// define the rejected handler
    static class MyIgnorePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.err.println(r.toString() + " rejected");
        }
    }
	
	static class MyTask implements Runnable {
		
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
		
		// init parameters
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
		
		// init work queue, a bounded queue. Size is 2
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
		// init thread factory
        ThreadFactory threadFactory = new MyThreadFactory();
		// init rejected policy
        RejectedExecutionHandler handler = new MyIgnorePolicy();
		
		// init the thread pool executor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
				
		// Starts all core threads, causing them to idly wait for work. This overrides the default policy of starting core threads only when new tasks are executed.
        executor.prestartAllCoreThreads();

        for (int i = 1; i <= 10; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            executor.execute(task);
        }

    }
}
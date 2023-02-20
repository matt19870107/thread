import java.util.concurrent.ExecutionException;

public class TestWaitLock {
    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new SampleThread1(1);
        TestWaitLock test = new TestWaitLock();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getId() + " Started!" );
                test.doWait(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getId() + " Started!" );
                test.doWait(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getId() + " Started!" );
                test.doNotify(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getId() + " Started!" );
                test.doNotify(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    private void doWait(Thread thread) throws InterruptedException {
        synchronized(thread) {
            System.out.println("Release the lock for: " + thread.getId());
            thread.wait();
            Thread.sleep(10000);
        }
    }

    private void doNotify(Thread thread) throws InterruptedException {
        synchronized(thread) {
            System.out.println("notify: " + thread.getId());
            thread.notify();
            Thread.sleep(10000);
        }
    }
}


class SampleThread1 extends Thread {
    public int processingCount = 0;

    SampleThread1(int processingCount) {
        this.processingCount = processingCount;
        System.out.println("Thread Created");
    }

    @Override
    public void run() {
        System.out.println("Thread " + this.getName() + " started");
        while (processingCount > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread " + this.getName() + " interrupted");
            }
            processingCount--;
        }
        System.out.println("Thread " + this.getName() + " exiting");
    }
}

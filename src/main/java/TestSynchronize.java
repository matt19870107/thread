public class TestSynchronize {
    static class Sequence{
        private int count = 0;

        public synchronized int getNext(){
            return count ++;
        }

        public int getCount(){
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Sequence sequence = new Sequence();
        Thread thread1 = new Thread(() -> {

            for(int i = 0; i < 20; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sequence.getNext();
            }
        });
        Thread thread2 = new Thread(() -> {
            for(int i = 0; i < 20; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sequence.getNext();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(sequence.getCount());

    }
}

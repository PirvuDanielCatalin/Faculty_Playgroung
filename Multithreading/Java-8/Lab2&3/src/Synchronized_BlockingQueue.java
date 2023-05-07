import Classes.BlockingQueue.Consumer;
import Classes.BlockingQueue.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Synchronized_BlockingQueue {
    static final int ITERATIONS = 5;
    static ArrayBlockingQueue<Integer> arrayBlockingQueue;

    public static void main(String[] args) throws InterruptedException {
        arrayBlockingQueue = new ArrayBlockingQueue<>(ITERATIONS);

        Thread Producer = new Thread(new Producer(arrayBlockingQueue), "Producer");
        Thread Consumer = new Thread(new Consumer(arrayBlockingQueue), "Consumer");

        Producer.start();
        Consumer.start();
    }
}

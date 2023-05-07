import Classes.WaitNotify.Consumer;
import Classes.WaitNotify.Producer;

import java.util.ArrayDeque;

public class Synchronized_WaitNotify {
    static final int ITERATIONS = 5;
    static ArrayDeque<Integer> arrayDeque;

    public static void main(String[] args) throws InterruptedException {
        arrayDeque = new ArrayDeque<>(ITERATIONS);

        Thread Producer = new Thread(new Producer(arrayDeque, ITERATIONS), "Producer");
        Thread Consumer = new Thread(new Consumer(arrayDeque), "Consumer");

        Producer.start();
        Consumer.start();
    }
}

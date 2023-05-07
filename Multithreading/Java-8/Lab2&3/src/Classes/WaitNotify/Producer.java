package Classes.WaitNotify;

import java.util.ArrayDeque;

public class Producer extends Thread {
    private final ArrayDeque<Integer> arrayDeque;
    private final int MAX_CAPACITY;

    public Producer(ArrayDeque<Integer> arrayDeque, int size) {
        this.arrayDeque = arrayDeque;
        this.MAX_CAPACITY = size;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            try {
                produce(counter++);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void produce(int i) throws InterruptedException {
        synchronized (arrayDeque) {
            while (arrayDeque.size() == MAX_CAPACITY) {
                System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + arrayDeque.size());
                arrayDeque.wait();
            }

            // Production time
            Thread.sleep(1000);

            arrayDeque.addLast(i);
            Thread.sleep((long)(Math.random() * 2000));

            System.out.println("Produced: " + i);
            arrayDeque.notifyAll();
        }
    }
}
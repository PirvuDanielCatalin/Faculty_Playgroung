package Classes.WaitNotify;

import java.util.ArrayDeque;

public class Consumer implements Runnable {
    private final ArrayDeque<Integer> arrayDeque;

    public Consumer(ArrayDeque<Integer> arrayDeque) {
        this.arrayDeque = arrayDeque;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (arrayDeque) {
            while (arrayDeque.isEmpty()) {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + arrayDeque.size());
                arrayDeque.wait();
            }

            // Consuming time
            Thread.sleep(1000);

            int i = (Integer) arrayDeque.pollFirst();
            System.out.println("Consumed: " + i);
            arrayDeque.notifyAll();
        }
    }
}
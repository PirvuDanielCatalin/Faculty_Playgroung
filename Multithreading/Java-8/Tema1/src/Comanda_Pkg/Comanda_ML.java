package Comanda_Pkg;

public class Comanda_ML<T> {
    private T cell = null;

    public synchronized boolean put(T message) throws InterruptedException {
        while (cell != null) wait();
        cell = message;
        notifyAll();
        return true;
    }

    public synchronized T take() throws InterruptedException {
        while (cell == null) wait();
        T message = cell;
        cell = null;
        notifyAll();
        return message;
    }
}
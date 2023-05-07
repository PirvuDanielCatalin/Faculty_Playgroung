package Cofetar_Pkg;

public class Cofetar extends Thread {
    private final int DurataPrepararii;

    public Cofetar(int durataPrepararii) {
        DurataPrepararii = durataPrepararii;
    }

    public void run() {
        try {
            // Timpul de preparare a blatului / cremei / decoratiunilor
            Thread.sleep(DurataPrepararii);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

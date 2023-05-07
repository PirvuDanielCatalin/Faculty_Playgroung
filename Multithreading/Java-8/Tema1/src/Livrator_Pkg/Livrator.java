package Livrator_Pkg;

import Comanda_Pkg.Comanda;
import Comanda_Pkg.Comanda_ML;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Livrator extends Thread {
    private final Comanda_ML<Comanda> Lista_ML;
    private final Lock Lacat;
    private final ArrayList<String> ElementeMarcate;

    public Livrator(Comanda_ML<Comanda> lista_ML, Lock lacat, ArrayList<String> elementeMarcate) {
        Lista_ML = lista_ML;
        Lacat = lacat;
        ElementeMarcate = elementeMarcate;
    }

    public void run() {
        while (true) {
            try {
                consume();
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void consume() throws Exception {
        Comanda comanda;
        if (!ElementeMarcate.contains(Thread.currentThread().getName())) {

            // Livratorul preia comanda de la manager
            comanda = Lista_ML.take();

            if (comanda.getNume().equals("STOP")) {
                ElementeMarcate.add(Thread.currentThread().getName());
            } else {

                Lacat.lock();
                System.out.println("Livratorul " + Thread.currentThread().getName() + " a preluat comanda " + comanda.getNume());
                Lacat.unlock();

                // Timpul de livrare a comenzii si de intoarcere inapoi la cofetarie
                Thread.sleep(comanda.getT());

                Lacat.lock();
                System.out.println("Livratorul " + Thread.currentThread().getName() + " a terminat comanda " + comanda.getNume());
                Lacat.unlock();
            }
        }
    }
}

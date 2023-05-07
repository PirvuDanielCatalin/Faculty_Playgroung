package Manager_Pkg;

import Cofetar_Pkg.Cofetar;
import Comanda_Pkg.Comanda;
import Comanda_Pkg.Comanda_ML;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;

public class Manager extends Thread {
    private final ArrayDeque<Comanda> ListaComenzi_Agent_Manageri;
    private final ThreadPoolExecutor Cofetari_Blat_Executor;
    private final ThreadPoolExecutor Cofetari_Crema_Executor;
    private final ThreadPoolExecutor Cofetari_Decoratiuni_Executor;

    private final Comanda_ML<Comanda> Lista_ML;

    private final Lock Lacat;

    private final ArrayList<String> ElementeMarcate;
    private final ArrayList<String> Threaduri;
    private final ArrayList<String> Livratori;
    private final ArrayList<String> Manageri;

    public Manager(ArrayDeque<Comanda> listaComenzi_Agent_Manageri, ThreadPoolExecutor cofetari_Blat_Executor, ThreadPoolExecutor cofetari_Crema_Executor, ThreadPoolExecutor cofetari_Decoratiuni_Executor, Comanda_ML<Comanda> lista_ML, Lock lacat, ArrayList<String> elementeMarcate) {
        ListaComenzi_Agent_Manageri = listaComenzi_Agent_Manageri;

        Cofetari_Blat_Executor = cofetari_Blat_Executor;
        Cofetari_Crema_Executor = cofetari_Crema_Executor;
        Cofetari_Decoratiuni_Executor = cofetari_Decoratiuni_Executor;

        Lista_ML = lista_ML;

        Lacat = lacat;

        ElementeMarcate = elementeMarcate;
        Threaduri = new ArrayList<String>(Arrays.asList("M1", "M2", "M3", "M4", "CB", "CC", "CD", "L1", "L2", "L3", "L4", "L5"));
        Livratori = new ArrayList<String>(Arrays.asList("L1", "L2", "L3", "L4", "L5"));
        Manageri = new ArrayList<String>(Arrays.asList("M1", "M2", "M3", "M4"));
    }

    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException, ExecutionException {
        Comanda comanda;
        synchronized (ListaComenzi_Agent_Manageri) {
            while (ListaComenzi_Agent_Manageri.isEmpty()) {
                ListaComenzi_Agent_Manageri.wait();
            }

            // Managerul scrie pe System.err cand preia o comanda
            comanda = ListaComenzi_Agent_Manageri.removeFirst();
            if (!comanda.getNume().equals("STOP")) {
                System.err.println("Managerul " + Thread.currentThread().getName() + " a preluat comanda " + comanda.getNume());
            }

            ListaComenzi_Agent_Manageri.notifyAll();
        }

        if (comanda.getNume().equals("STOP")) {
            synchronized (ElementeMarcate) {
                // Adaugam managerul curent in lista elementelor marcate
                ElementeMarcate.add(Thread.currentThread().getName());
                ElementeMarcate.notifyAll();

                while (!ElementeMarcate.containsAll(Threaduri)) {

                    // Daca toti managerii sunt marcati, incepem sa marcam cofetarii si livratorii
                    if (ElementeMarcate.containsAll(Manageri)) {

                        // Marcam cofetarii
                        if (!ElementeMarcate.containsAll(new ArrayList<String>(Arrays.asList("CB", "CC", "CD")))) {
                            ElementeMarcate.add("CB");
                            ElementeMarcate.add("CC");
                            ElementeMarcate.add("CD");
                        }

                        Lacat.lock();
                        while (!ElementeMarcate.containsAll(Livratori)) {
                            // Cat timp livratorii sunt activi, pun comanda de oprire pe coada Manageri - Livratori
                            Lista_ML.put(comanda);
                        }
                        Lacat.unlock();
                    } else {
                        // Astept ca urmatorul manager sa fi preluat o comanda de oprire
                        ElementeMarcate.wait();
                    }
                }
            }
        } else {
            // Trimite catre cofetari sa faca cele trei parti
            Future<String> blat = Cofetari_Blat_Executor.submit(new Cofetar(comanda.getX()), "Done");
            Future<String> crema = Cofetari_Crema_Executor.submit(new Cofetar(comanda.getY()), "Done");
            Future<String> decoratiuni = Cofetari_Decoratiuni_Executor.submit(new Cofetar(comanda.getZ()), "Done");

            // Managerul curent asteapta ca cele trei parti ale tortului sa fie gata
            String rezultat_blat = blat.get();
            String rezultat_crema = crema.get();
            String rezultat_decoratiuni = decoratiuni.get();

            // Exista livrator liber si managerul plaseaza comanda spre livrare
            Lista_ML.put(comanda);

            Lacat.lock();
            System.err.println("Managerul " + Thread.currentThread().getName() + " a dat comanda " + comanda.getNume() + " spre livrare.");
            Lacat.unlock();
        }
    }
}

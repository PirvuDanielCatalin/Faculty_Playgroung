import Comanda_Pkg.Comanda;
import Comanda_Pkg.Comanda_ML;
import Livrator_Pkg.Livrator;
import Manager_Pkg.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AgentTelefonic {
    static ArrayDeque<Comanda> ListaComenzi_Agent_Manageri;

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<String> Threaduri = new ArrayList<String>(Arrays.asList("M1", "M2", "M3", "M4", "CB", "CC", "CD", "L1", "L2", "L3", "L4", "L5"));
        ArrayList<String> Manageri = new ArrayList<String>(Arrays.asList("M1", "M2", "M3", "M4"));
        ArrayList<String> ElementeMarcate = new ArrayList<>(12);

        ListaComenzi_Agent_Manageri = new ArrayDeque<>(4);
        Comanda_ML<Comanda> Lista_ML = new Comanda_ML<>();

        ThreadPoolExecutor Cofetari_Blat_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        ThreadPoolExecutor Cofetari_Crema_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        ThreadPoolExecutor Cofetari_Decoratiuni_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        Lock Lacat = new ReentrantLock();

        Thread Livrator1 = new Thread(new Livrator(Lista_ML, Lacat, ElementeMarcate), "L1");
        Thread Livrator2 = new Thread(new Livrator(Lista_ML, Lacat, ElementeMarcate), "L2");
        Thread Livrator3 = new Thread(new Livrator(Lista_ML, Lacat, ElementeMarcate), "L3");
        Thread Livrator4 = new Thread(new Livrator(Lista_ML, Lacat, ElementeMarcate), "L4");
        Thread Livrator5 = new Thread(new Livrator(Lista_ML, Lacat, ElementeMarcate), "L5");

        Livrator1.start();
        Livrator2.start();
        Livrator3.start();
        Livrator4.start();
        Livrator5.start();

        Thread Manager1 = new Thread(new Manager(ListaComenzi_Agent_Manageri, Cofetari_Blat_Executor, Cofetari_Crema_Executor, Cofetari_Decoratiuni_Executor, Lista_ML, Lacat, ElementeMarcate), "M1");
        Thread Manager2 = new Thread(new Manager(ListaComenzi_Agent_Manageri, Cofetari_Blat_Executor, Cofetari_Crema_Executor, Cofetari_Decoratiuni_Executor, Lista_ML, Lacat, ElementeMarcate), "M2");
        Thread Manager3 = new Thread(new Manager(ListaComenzi_Agent_Manageri, Cofetari_Blat_Executor, Cofetari_Crema_Executor, Cofetari_Decoratiuni_Executor, Lista_ML, Lacat, ElementeMarcate), "M3");
        Thread Manager4 = new Thread(new Manager(ListaComenzi_Agent_Manageri, Cofetari_Blat_Executor, Cofetari_Crema_Executor, Cofetari_Decoratiuni_Executor, Lista_ML, Lacat, ElementeMarcate), "M4");

        Manager1.start();
        Manager2.start();
        Manager3.start();
        Manager4.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.equals("STOP")) {
                synchronized (ElementeMarcate) {
                    while (!ElementeMarcate.containsAll(Threaduri)) {
                        if (!ElementeMarcate.containsAll(Manageri)) {
                            synchronized (ListaComenzi_Agent_Manageri) {
                                // Cat timp managerii sunt activi, astept sa pot pune comenzi de oprire pe lista Agent - Manageri
                                while (ListaComenzi_Agent_Manageri.size() == 4) {
                                    ListaComenzi_Agent_Manageri.wait();
                                }

                                // Pun comanda de oprire si notific pt ca un manager sa o poata lua
                                ListaComenzi_Agent_Manageri.addLast(new Comanda("STOP", 0, 0, 0, 0));
                                ListaComenzi_Agent_Manageri.notifyAll();
                            }
                        }

                        // Astept pana cand cel putin un thread a fost marcat pt a se opri
                        ElementeMarcate.wait();
                    }
                }

                // Toate elementele au fost marcate si se pot opri
                Manager1.stop();
                Manager2.stop();
                Manager3.stop();
                Manager4.stop();

                Cofetari_Blat_Executor.shutdown();
                Cofetari_Crema_Executor.shutdown();
                Cofetari_Decoratiuni_Executor.shutdown();

                Livrator1.stop();
                Livrator2.stop();
                Livrator3.stop();
                Livrator4.stop();
                Livrator5.stop();

                Thread.sleep(2000);
                break;
            }

            String Nume = null;
            int X, Y, Z, T;

            String[] variables = line.split(" ");
            if (variables.length != 5) {
                System.out.println("\u001B[31m" + "Sunt necesari 5 parametri dati sub forma: Nume X Y Z T" + "\u001B[0m");
                continue;
            }

            try {
                Nume = variables[0];

                if (!Nume.matches("^[a-zA-Z+\\-_]+$"))
                    throw new Exception();

                X = Integer.parseInt(variables[1]);
                Y = Integer.parseInt(variables[2]);
                Z = Integer.parseInt(variables[3]);
                T = Integer.parseInt(variables[4]);

                System.out.println("\u001B[32m" + "Nume: " + Nume + "\u001B[0m");
                System.out.println("\u001B[32m" + "X: " + X + "\u001B[0m");
                System.out.println("\u001B[32m" + "Y: " + Y + "\u001B[0m");
                System.out.println("\u001B[32m" + "Z: " + Z + "\u001B[0m");
                System.out.println("\u001B[32m" + "T: " + T + "\u001B[0m");

            } catch (Exception e) {
                System.out.println("\u001B[31m" + "Parametrii dati nu au formatul dorit\nFormatul dorit este: String int int int int" + "\u001B[0m");
                continue;
            }

            synchronized (ListaComenzi_Agent_Manageri) {
                while (ListaComenzi_Agent_Manageri.size() == 4) {
                    System.out.println("S-a atins limita de 4 comenzi. Agentul telefonic asteapta un manager disponibil.");
                    ListaComenzi_Agent_Manageri.wait();
                }

                ListaComenzi_Agent_Manageri.addLast(new Comanda(Nume, X, Y, Z, T));
                ListaComenzi_Agent_Manageri.notifyAll();
            }
        }
    }
}
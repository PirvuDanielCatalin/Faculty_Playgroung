package Comanda_Pkg;

public class Comanda {
    String Nume;
    int X, Y, Z, T;

    public Comanda(String nume, int x, int y, int z, int t) {
        Nume = nume;
        X = x;
        Y = y;
        Z = z;
        T = t;
    }

    public String getNume() {
        return Nume;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getZ() {
        return Z;
    }

    public int getT() {
        return T;
    }
}

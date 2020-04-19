package Paczka;

public class Evool extends Thread {

    @Override
    public void run() {
        for(int i = 0; i < 100000000; i++) {
            Math.pow(2,30);
        }
    }
}

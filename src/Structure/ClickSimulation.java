package Structure;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ClickSimulation extends Thread{
    private final Semaphore semaphore;
    private final ArrayBlockingQueue<int[]> arrayBlockingQueue;
    private static final Scanner sc = new Scanner(System.in);

    public ClickSimulation(Semaphore semaphore, ArrayBlockingQueue<int[]> arrayBlockingQueue){
        this.semaphore = semaphore;
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        while (true){
            try {
                semaphore.acquire();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            int x,y;
            System.out.print("Podaj x:");
            x = sc.nextInt();
            sc.nextLine();
            System.out.print("Podaj y:");
            y = sc.nextInt();
            try {
                arrayBlockingQueue.put(new int[]{x, y});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

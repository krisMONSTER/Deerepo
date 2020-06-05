package Structure;

import java.util.concurrent.ArrayBlockingQueue;

public class ToDisplayReceiveSimulation extends Thread{
    private ArrayBlockingQueue<ToDisplay> arrayBlockingQueue;

    public ToDisplayReceiveSimulation(ArrayBlockingQueue<ToDisplay> arrayBlockingQueue){
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        while (true){
            ToDisplay toDisplay = null;
            try {
                toDisplay = arrayBlockingQueue.take();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(toDisplay.getTypeOfAction());
        }
    }
}

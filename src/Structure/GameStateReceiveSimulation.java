package Structure;

import java.util.concurrent.ArrayBlockingQueue;

public class GameStateReceiveSimulation extends Thread{
    private ArrayBlockingQueue<GameState> arrayBlockingQueue;

    public GameStateReceiveSimulation(ArrayBlockingQueue<GameState> arrayBlockingQueue){
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public void run() {
        while (true){
            GameState gameState = null;
            try {
                gameState = arrayBlockingQueue.take();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(gameState);
        }
    }
}

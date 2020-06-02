package GUI;

import Structure.GameState;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class StateSynchronization extends Thread {

    private ArrayBlockingQueue<GameState> gameState;
    Semaphore sem=new Semaphore(0);

    StateSynchronization(ArrayBlockingQueue<GameState> gameState){ this.gameState=gameState;}

    public void run(){
        try {

            while(true) {
                sem.acquire();
                GameState state = gameState.peek();
                gameState.take();

                if (state == GameState.draw) {
                    AlertBox.display("Koniec gry", "Remis!");
                    MainStage.endGame();
                    return;
                }

                if (state == GameState.whiteWon) {
                    AlertBox.display("Koniec gry", "Biale wygraly!");
                    System.out.println("Biale wygraly!");
                    MainStage.endGame();
                    return;
                }

                if (state == GameState.blackWon) {
                    AlertBox.display("Koniec gry", "Czarne wygraly!");
                    MainStage.endGame();
                    return;
                }
            }

        }
        catch(InterruptedException e){e.printStackTrace();}

    }
}

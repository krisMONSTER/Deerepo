package GUI;

import Structure.GameState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.util.concurrent.ArrayBlockingQueue;

public class SynchronizeTask extends Task<Void> {

    private ArrayBlockingQueue<GameState> gameState;

    public SynchronizeTask(ArrayBlockingQueue<GameState> gameState){ this.gameState=gameState; }


    protected Void call() {

        GameState state;

        try {
            while(true) {
                System.out.println("Robie cos");
                state = gameState.take();
                checkState(state);
            }
        }
        catch(InterruptedException e){e.printStackTrace();}
        return null;
    }


    public void checkState(GameState state){
        if (state == GameState.draw) {
            AlertBox.display("Koniec gry", "Remis!");
            MainStage.endGame();
        }

        else if (state == GameState.whiteWon) {
            AlertBox.display("Koniec gry", "Biale wygraly!");
            System.out.println("Biale wygraly!");
            MainStage.endGame();
        }

        else if (state == GameState.blackWon) {
            AlertBox.display("Koniec gry", "Czarne wygraly!");
            MainStage.endGame();
        }
    }

}

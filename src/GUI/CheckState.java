package GUI;

import NET.Game;
import Structure.GameState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.concurrent.ArrayBlockingQueue;

public class CheckState extends Service<Boolean> {

    private ArrayBlockingQueue<GameState> gameState;

    public CheckState(ArrayBlockingQueue<GameState> gameState) {
        this.gameState = gameState;
    }

    protected Task createTask() {
        return new Task() {
            public Boolean call() {

                GameState state;

                try {
                    state = gameState.take();
                    if (state == GameState.active) return false;
                    else return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
    }

}

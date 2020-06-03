package GUI;

import Structure.GameState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.concurrent.ArrayBlockingQueue;

public class CheckState extends Service<GameState> {

    private ArrayBlockingQueue<GameState> gameState;

    public CheckState(ArrayBlockingQueue<GameState> gameState) {
        this.gameState = gameState;

        setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {

                    GameState check = getValue();

                    if (check==GameState.whiteWon) {
                        AlertBox.display("Koniec gry", "Biale wygraly!");
                        MainStage.endGame();
                    }

                    else if (check==GameState.blackWon){
                        AlertBox.display("Koniec gry", "Czarne wygraly!");
                        MainStage.endGame();
                    }
                    
                    else if (check==GameState.draw){
                        AlertBox.display("Koniec gry", "Remis!");
                        MainStage.endGame();
                    }
            }

        });
    }

    protected Task<GameState> createTask() {
        return new Task<>() {
            public GameState call() {
                GameState state = null;

                do {
                    try {
                        state = gameState.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (state == GameState.active);

                return state;
            }
        };
    }

}

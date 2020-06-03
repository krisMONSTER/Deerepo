package GUI;

import NET.Game;
import Structure.GameState;
import Structure.ToDisplay;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.concurrent.ArrayBlockingQueue;

public class CheckState extends Service<Boolean> {

    private ArrayBlockingQueue<GameState> gameState;

    public CheckState(ArrayBlockingQueue<GameState> gameState) {
        this.gameState = gameState;

        setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {

                    Boolean boolcheck = getValue();

                    if (boolcheck==true){
                        AlertBox.display("Koniec gry", "Biale wygraly!");
                        System.out.println("Biale wygraly!");
                        MainStage.endGame();
                    }

            }

        });
    }

    protected Task createTask() {
        return new Task() {
            public Boolean call() {

                GameState state;

                try {
                    state = gameState.take();
                    System.out.println("Checkstate - Dzialam");
                    if (state == GameState.active) return false;
                    else return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
    }

    public void startService(Service<Boolean>serwis)
    {
        if(!serwis.isRunning())
        {
            serwis.reset();
            serwis.start();
        }
    }

}

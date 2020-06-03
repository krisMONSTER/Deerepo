package GUI;

import Structure.GameState;
import Structure.ToDisplay;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.util.concurrent.ArrayBlockingQueue;

public class ToDisplaySync extends Service<ToDisplay> {

    private ArrayBlockingQueue<ToDisplay> display;
    private static ToDisplay toDisplay;

    public ToDisplaySync(ArrayBlockingQueue<ToDisplay> display){
        this.display=display;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected ToDisplay call() throws Exception {
                toDisplay=display.take();
                return toDisplay;
            }
        };
    }

}

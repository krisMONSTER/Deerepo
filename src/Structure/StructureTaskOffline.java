package Structure;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

public class StructureTaskOffline extends Thread{
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final Player white;
    private final Player black;
    private Player currentPlayer;
    private ToDisplay toDisplay = new ToDisplay();
    private int storedX;
    private int storedY;

    public StructureTaskOffline(ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display){
        this.clickCommand = clickCommand;
        this.display = display;
        white = new Player("Mrs/Mr White", true, display);
        black = new Player("Mrs/Mr Black", false, display);
        currentPlayer = black;
    }

    private void sendDisplay(){
        try {
            display.put(toDisplay);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        toDisplay = new ToDisplay();
    }

    public void run(){
        while (true){
            if(currentPlayer==white) currentPlayer = black;
            else currentPlayer = white;
            Board.setPlayer(currentPlayer);
            ClickResult clickResult;
            do {
                int[] coordinates = null;
                try {
                    coordinates = clickCommand.take();
                } catch (InterruptedException e) {
                    //tu chyba bedzie jakies wyjscie z watku
                    e.printStackTrace();
                }
                clickResult = Board.clickOnBoard(Objects.requireNonNull(coordinates)[0], coordinates[1]);
                switch (clickResult) {
                    case nothing -> {
                        toDisplay.setTypeOfAction(TypeOfAction.nothing);
                        sendDisplay();
                    }
                    case pick -> {
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        toDisplay.setTypeOfAction(TypeOfAction.pick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendDisplay();
                    }
                    case repick -> {
                        toDisplay.setTypeOfAction(TypeOfAction.repick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.addCoordinates(new int[]{currentPlayer.getPickedPiece().getX(), currentPlayer.getPickedPiece().getY()});
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        sendDisplay();
                    }
                    case clear -> {
                        toDisplay.setTypeOfAction(TypeOfAction.clear);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendDisplay();
                    }
                }
            }while(clickResult!=ClickResult.move);
        }
    }
}

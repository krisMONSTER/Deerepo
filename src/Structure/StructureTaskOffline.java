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

    private void sendGuiDisplayData(){
        try {
            display.put(toDisplay);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        toDisplay = new ToDisplay();
    }

    //game flow
    public void run(){
        Board.setupBoard();
        while (true){
            if(currentPlayer==white) currentPlayer = black;
            else currentPlayer = white;

            //sprawdzanie stanu gry
            Board.display();
            ClickResult clickResult;
            do {
                //TO NA DOLE MOZE INACZEJ
                int[] coordinates = null;
                try {
                    coordinates = clickCommand.take();
                } catch (InterruptedException e) {
                    //tu chyba bedzie jakies wyjscie z watku
                    e.printStackTrace();
                }
                clickResult = currentPlayer.performOnClick(Objects.requireNonNull(coordinates)[0], coordinates[1]);
                switch (clickResult) {
                    case nothing -> {
                        toDisplay.setTypeOfAction(TypeOfAction.nothing);
                        sendGuiDisplayData();
                    }
                    case pick -> {
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        toDisplay.setTypeOfAction(TypeOfAction.pick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendGuiDisplayData();
                    }
                    case repick -> {
                        toDisplay.setTypeOfAction(TypeOfAction.repick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.addCoordinates(new int[]{currentPlayer.getPickedPiece().getX(), currentPlayer.getPickedPiece().getY()});
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        sendGuiDisplayData();
                    }
                    case clear -> {
                        toDisplay.setTypeOfAction(TypeOfAction.clear);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendGuiDisplayData();
                    }
                    case move -> currentPlayer.makeChanges(coordinates[0],coordinates[1]);
                }
            }while(clickResult!=ClickResult.move);
        }
    }
}

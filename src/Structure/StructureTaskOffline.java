package Structure;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

public class StructureTaskOffline extends Thread{
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final ArrayBlockingQueue<GameState> gameStates;
    private final Player white;
    private final Player black;
    private Player currentPlayer;
    private int storedX;
    private int storedY;

    public StructureTaskOffline(ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display, ArrayBlockingQueue<GameState> gameStates){
        this.clickCommand = clickCommand;
        this.display = display;
        this.gameStates = gameStates;
        white = new Player(true);
        black = new Player(false);
        currentPlayer = black;
    }

    private void sendGuiDisplayData(ToDisplay toDisplay){
        try {
            display.put(toDisplay);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //game flow
    public void run(){
        Board.setupBoard();
        outer:
        while (true){
            if(currentPlayer==white) currentPlayer = black;
            else currentPlayer = white;
            Board.addCurrentBoardState();
            GameState gameState = Board.checkGameState(currentPlayer.getColour());
            Board.display();
            ClickResult clickResult;
            do {
                try{
                    gameStates.put(gameState);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(gameState!=GameState.active){
                    break outer;
                }
                //TO NA DOLE MOZE INACZEJ
                int[] coordinates = null;
                try {
                    coordinates = clickCommand.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clickResult = currentPlayer.performOnClick(Objects.requireNonNull(coordinates)[0], coordinates[1]);
                switch (clickResult) {
                    case nothing -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.nothing);
                        sendGuiDisplayData(toDisplay);
                    }
                    case pick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.pick);
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendGuiDisplayData(toDisplay);
                    }
                    case repick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.repick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.addCoordinates(new int[]{currentPlayer.getPickedPiece().getX(), currentPlayer.getPickedPiece().getY()});
                        storedX = currentPlayer.getPickedPiece().getX();
                        storedY = currentPlayer.getPickedPiece().getY();
                        sendGuiDisplayData(toDisplay);
                    }
                    case clear -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.clear);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendGuiDisplayData(toDisplay);
                    }
                    case move -> {
                        ToDisplay toDisplay = new ToDisplay();
                        DataChanges dataChanges = new DataChanges();
                        currentPlayer.makeChanges(coordinates[0],coordinates[1],dataChanges,toDisplay);
                        Board.executeDataChanges(dataChanges);
                        sendGuiDisplayData(toDisplay);
                    }
                }
            }while(clickResult!=ClickResult.move);

        }
        System.out.println("koniec watku struktury");
    }
    
}
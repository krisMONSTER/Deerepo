package Structure;

import MutableVariables.MutableBoolean;
import NET.Client;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class StructureTaskClient extends StructureTask{
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final ArrayBlockingQueue<GameState> gameStates;
    private final Player player;
    private final Semaphore clickSemaphore;
    private final MutableBoolean isActive;
    private int storedX;
    private int storedY;
    private final Client client;

    public StructureTaskClient(String address, int port, ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display, ArrayBlockingQueue<GameState> gameStates, Semaphore clickSemaphore, MutableBoolean isActive){
        this.clickCommand = clickCommand;
        this.display = display;
        this.gameStates = gameStates;
        this.clickSemaphore = clickSemaphore;
        this.isActive = isActive;
        player = new Player(false);
        client = new Client(address,port);
        this.setDaemon(true);
    }

    void closeClient(){
        try{
            client.close();
        }catch (IOException ignore){}
    }

    private boolean sendGUIGameState(GameState gameState){
        if(isActive.get()) {
            try {
                gameStates.put(gameState);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }
        else return false;
    }

    private boolean sendGuiDisplayData(ToDisplay toDisplay){
        if(isActive.get()) {
            try {
                display.put(toDisplay);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }
        else return false;
    }

    //game flow
    public void run(){
        if (!sendGUIGameState(GameState.tryConnectToHost)) {
            return;
        }
        while (true) {
            if(!isActive.get())
                return;
            try {
                client.setupClient();
            } catch (IOException a) {
                continue;
            }
            break;
        }
        if (!sendGUIGameState(GameState.connected)) {
            closeClient();
            return;
        }
        Board.setupBoard();
        outer:
        while(true){
            //RECEIVE DATAPACKAGE
            Board.display();
            Board.addCurrentBoardState();
            GameState gameState = Board.checkGameState(true);
            if(gameState!=GameState.active){
                sendGUIGameState(gameState);
                break;
            }
            DataPackage dataPackage = null;
            while (true) {
                try {
                    dataPackage = client.receive();
                } catch (SocketTimeoutException e) {
                    if(isActive.get()) continue;
                    else break outer;
                } catch (IOException e) {
                    sendGUIGameState(GameState.disconnected);
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            if(dataPackage!=null){
                DataChanges dataChanges = dataPackage.getDataChanges();
                ToDisplay toDisplay = dataPackage.getToDisplay();
                Board.executeDataChanges(dataChanges);
                if(!sendGuiDisplayData(toDisplay)){
                    break;
                }
            }
            //SEND DATAPACKAGE
            Board.display();
            Board.addCurrentBoardState();
            gameState = Board.checkGameState(false);
            if(gameState!=GameState.active){
                sendGUIGameState(gameState);
                break;
            }
            ClickResult clickResult;
            do {
                int[] coordinates;
                if(isActive.get()) {
                    clickSemaphore.release();
                    try {
                        coordinates = clickCommand.take();
                        clickSemaphore.acquire();
                    } catch (InterruptedException e) {
                        break outer;
                    }
                }
                else break outer;
                clickResult = player.performOnClick(Objects.requireNonNull(coordinates)[0], coordinates[1]);
                switch (clickResult) {
                    case nothing -> {}
                    case pick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.pick);
                        storedX = player.getPickedPiece().getX();
                        storedY = player.getPickedPiece().getY();
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.setPossiblePositions(player.getPickedPiece().getPossiblePositions());
                        if(!sendGuiDisplayData(toDisplay)){
                            break outer;
                        }
                    }
                    case repick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.repick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.addCoordinates(new int[]{player.getPickedPiece().getX(), player.getPickedPiece().getY()});
                        storedX = player.getPickedPiece().getX();
                        storedY = player.getPickedPiece().getY();
                        toDisplay.setPossiblePositions(player.getPickedPiece().getPossiblePositions());
                        if(!sendGuiDisplayData(toDisplay)){
                            break outer;
                        }
                    }
                    case clear -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.clear);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        if(!sendGuiDisplayData(toDisplay)){
                            break outer;
                        }
                    }
                    case move -> {
                        ToDisplay toDisplay = new ToDisplay();
                        DataChanges dataChanges = new DataChanges();
                        player.makeChanges(coordinates[0],coordinates[1],dataChanges,toDisplay);
                        Board.executeDataChanges(dataChanges);
                        if(!sendGuiDisplayData(toDisplay)){
                            break outer;
                        }
                        dataPackage = new DataPackage(dataChanges, toDisplay);
                        try {
                            client.send(dataPackage);
                        }catch (IOException a){
                            sendGUIGameState(GameState.disconnected);
                            break outer;
                        }
                    }
                }
            }while(clickResult!=ClickResult.move);
        }
        closeClient();
        System.out.println("koniec watku struktury");
    }
}

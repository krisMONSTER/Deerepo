package Structure;


import MutableVariables.MutableBoolean;
import NET.Host;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class StructureTaskHost extends StructureTask{
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final ArrayBlockingQueue<GameState> gameStates;
    private final Player player;
    private final Semaphore clickSemaphore;
    private final MutableBoolean isActive;
    private int storedX;
    private int storedY;
    private final Host host;

    public StructureTaskHost(int port, ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display, ArrayBlockingQueue<GameState> gameStates, Semaphore clickSemaphore, MutableBoolean isActive){
        this.clickCommand = clickCommand;
        this.display = display;
        this.gameStates = gameStates;
        this.clickSemaphore = clickSemaphore;
        this.isActive = isActive;
        player = new Player(true);
        host = new Host(port);
        this.setDaemon(true);
    }

    private void closeHost(){
        try{
            host.close();
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
        try{
            host.setupSocketServer();
        }catch (IOException e){
            sendGUIGameState(GameState.hostSetupFail);
            return;
        }
        if(!sendGUIGameState(GameState.waitingForClient)){
            return;
        }
        while (true) {
            if(isActive.get()) {
                try {
                    host.setupSocket();
                } catch (IOException e) { continue; }
                break;
            }
            else {
                try {
                    host.closeSocketServer();
                }catch (IOException ignore){}
                return;
            }
        }
        if(!sendGUIGameState(GameState.connected)){
            closeHost();
            return;
        }
        Board.setupBoard();
        outer:
        while (true){
            //SEND DATAPACKAGE
            Board.display();
            Board.addCurrentBoardState();
            GameState gameState = Board.checkGameState(true);
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
                        DataPackage dataPackage = new DataPackage(dataChanges, toDisplay);
                        try {
                            host.send(dataPackage);
                        }catch (IOException a){
                            sendGUIGameState(GameState.disconnected);
                            break outer;
                        }
                    }
                }
            }while(clickResult!=ClickResult.move);
            Board.display();
            Board.addCurrentBoardState();
            gameState = Board.checkGameState(false);
            if(gameState!=GameState.active){
                sendGUIGameState(gameState);
                break;
            }
            //RECEIVE DATAPACKAGE
            DataPackage dataPackage = null;
            while (true) {
                try {
                    dataPackage = host.receive();
                } catch (SocketTimeoutException e){
                    if(isActive.get()) continue;
                    else break outer;
                } catch (IOException e) {
                    sendGUIGameState(GameState.disconnected);
                    break outer;
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
        }
        closeHost();
        System.out.println("koniec watku struktury");
    }
}

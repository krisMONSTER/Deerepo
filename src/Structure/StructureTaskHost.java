package Structure;


import NET.Host;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class StructureTaskHost extends Thread{
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final ArrayBlockingQueue<GameState> gameStates;
    private final Player player;
    private final Semaphore clickSemaphore;
    private int storedX;
    private int storedY;
    private final Host host;

    public StructureTaskHost(int port, ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display, ArrayBlockingQueue<GameState> gameStates, Semaphore clickSemaphore){
        this.clickCommand = clickCommand;
        this.display = display;
        this.gameStates = gameStates;
        this.clickSemaphore = clickSemaphore;
        player = new Player(true);
        host = new Host(port);
        this.setDaemon(true);
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
        System.out.println("Oczekiwanie na klienta");
        try {
            host.setupHost();
        }catch (IOException a){
            System.out.println("Blad przy uruchamianiu uslug serwera");
            try{
                gameStates.put(GameState.disconnected);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return;
        }
        System.out.println("Polaczono z klientem");
        Board.setupBoard();
        while (true){
            //SEND DATAPACKAGE
            Board.display();
            Board.addCurrentBoardState();
            GameState gameState = Board.checkGameState(true);
            try{
                gameStates.put(gameState);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Gamestates do GUI");
            if(gameState!=GameState.active){
                try {
                    System.out.println("koniec hosta");
                    host.close();
                }catch (IOException ignore){}
                break;
            }
            ClickResult clickResult;
            do {
                int[] coordinates = null;
                clickSemaphore.release();
                try {
                    coordinates = clickCommand.take();
                    clickSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("otrzymano klikniecie");
                clickResult = player.performOnClick(Objects.requireNonNull(coordinates)[0], coordinates[1]);
                switch (clickResult) {
                    case nothing -> sendGuiDisplayData(new ToDisplay(TypeOfAction.nothing));
                    case pick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.pick);
                        storedX = player.getPickedPiece().getX();
                        storedY = player.getPickedPiece().getY();
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        sendGuiDisplayData(toDisplay);
                    }
                    case repick -> {
                        ToDisplay toDisplay = new ToDisplay(TypeOfAction.repick);
                        toDisplay.addCoordinates(new int[]{storedX, storedY});
                        toDisplay.addCoordinates(new int[]{player.getPickedPiece().getX(), player.getPickedPiece().getY()});
                        storedX = player.getPickedPiece().getX();
                        storedY = player.getPickedPiece().getY();
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
                        player.makeChanges(coordinates[0],coordinates[1],dataChanges,toDisplay);
                        Board.executeDataChanges(dataChanges);
                        sendGuiDisplayData(toDisplay);
                        DataPackage dataPackage = new DataPackage(dataChanges, toDisplay);
                        try {
                            host.send(dataPackage);
                            System.out.println("Wyslano do klienta");
                        }catch (IOException a){
                            try{
                                gameStates.put(GameState.disconnected);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            System.out.println("Gamestates do GUI");
                            return;
                        }
                    }
                }
            }while(clickResult!=ClickResult.move);
            Board.display();
            Board.addCurrentBoardState();
            gameState = Board.checkGameState(false);
            try{
                gameStates.put(gameState);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Gamestates do GUI");
            if(gameState!=GameState.active){
                try {
                    System.out.println("koniec hosta");
                    host.close();
                }catch (IOException ignored){}
                break;
            }
            //RECEIVE DATAPACKAGE
            DataPackage dataPackage = null;
            try {
                dataPackage = host.receive();
                System.out.println("Otrzymano od klienta");
            }catch (IOException a){
                try{
                    sendGuiDisplayData(new ToDisplay(TypeOfAction.nothing));
                    gameStates.put(GameState.disconnected);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("koniec hosta");
                return;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            if(dataPackage!=null){
                DataChanges dataChanges = dataPackage.getDataChanges();
                ToDisplay toDisplay = dataPackage.getToDisplay();
                Board.executeDataChanges(dataChanges);
                sendGuiDisplayData(toDisplay);
            }
        }
        System.out.println("koniec watku struktury");
    }
}

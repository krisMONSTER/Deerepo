package Structure;

import MutableVariables.MutableBoolean;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class ClientMain {
    private static final ArrayBlockingQueue<int[]> clickCommand = new ArrayBlockingQueue<>(1);
    private static final ArrayBlockingQueue<ToDisplay> display = new ArrayBlockingQueue<>(1);
    private static final ArrayBlockingQueue<GameState> gameStates = new ArrayBlockingQueue<>(1);
    private static final Scanner sc = new Scanner(System.in);
    private static ToDisplay toDisplay;
    private static GameState gameState;
    private static final Semaphore clickSemaphore = new Semaphore(0);
    private static final MutableBoolean isActive = new MutableBoolean(true);

    public static void main(String[] args) {
        StructureTaskClient t = new StructureTaskClient("localhost", 7172, clickCommand, display, gameStates, clickSemaphore, isActive);
        t.start();
        ClickSimulation clickSimulation = new ClickSimulation(clickSemaphore, clickCommand);
        clickSimulation.start();
        GameStateReceiveSimulation gameStateReceiveSimulation = new GameStateReceiveSimulation(gameStates);
        gameStateReceiveSimulation.start();
        ToDisplayReceiveSimulation toDisplayReceiveSimulation = new ToDisplayReceiveSimulation(display);
        toDisplayReceiveSimulation.start();

        /*while(true){
            try{
                gameState = gameStates.take();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(gameState != GameState.active){
                break;
            }

            try {
                toDisplay = display.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("typ akcji:"+toDisplay.getTypeOfAction());
            for(int[] coordinates : toDisplay.getCoordinates()){
                System.out.println("(x,y):"+coordinates[0]+" "+coordinates[1]);
            }

            try{
                gameState = gameStates.take();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(gameState != GameState.active){
                break;
            }

            do {
                int x, y;
                try{
                    clickSemaphore.acquire();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.print("Podaj x:");
                x = sc.nextInt();
                sc.nextLine();
                System.out.print("Podaj y:");
                y = sc.nextInt();
                try {
                    clickCommand.put(new int[]{x, y});
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clickSemaphore.release();
                try {
                    toDisplay = display.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("typ akcji:"+toDisplay.getTypeOfAction());
                for(int[] coordinates : toDisplay.getCoordinates()){
                    System.out.println("(x,y):"+coordinates[0]+" "+coordinates[1]);
                }
            }while (toDisplay.getTypeOfAction()!=TypeOfAction.move);
        }*/
    }
}

package Structure;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class HostMain {
    private static final ArrayBlockingQueue<int[]> clickCommand = new ArrayBlockingQueue<>(1);
    private static final ArrayBlockingQueue<ToDisplay> display = new ArrayBlockingQueue<>(1);
    private static final ArrayBlockingQueue<GameState> gameStates = new ArrayBlockingQueue<>(1);
    private static final Scanner sc = new Scanner(System.in);
    private static ToDisplay toDisplay;
    private static GameState gameState;
    private static final Semaphore clickSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        StructureTaskHost t = new StructureTaskHost(clickCommand, display, gameStates, clickSemaphore);
        t.start();
        while(true){
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
        }
    }
}

package Structure;

import GUI.Game;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Main extends Application{
	private final ArrayBlockingQueue<int[]> clickCommand = new ArrayBlockingQueue<>(1);
	private final ArrayBlockingQueue<ToDisplay> display = new ArrayBlockingQueue<>(1);
	private final ArrayBlockingQueue<GameState> gameStates = new ArrayBlockingQueue<>(1);
	private static final Scanner sc = new Scanner(System.in);
	private static ToDisplay toDisplay;
	private static GameState gameState;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		StructureTaskOffline t = new StructureTaskOffline(clickCommand, display, gameStates);
		t.start();
		System.out.println("Zaczynaja biale!");
		while(true){
			try{
				gameState = gameStates.take();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("stan gry:"+gameState);
			Board.test();
			if(gameState != GameState.active){
				break;
			}
			int x,y;
			System.out.print("Podaj x:");
			x = sc.nextInt();
			sc.nextLine();
			System.out.print("Podaj y:");
			y = sc.nextInt();
			try {
				clickCommand.put(new int[]{x,y});
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			try {
				toDisplay = display.take();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("typ akcji:"+toDisplay.getTypeOfAction());
			for(int[] coordinates : toDisplay.getCoordinates()){
				System.out.println("(x,y):"+coordinates[0]+" "+coordinates[1]);
			}
		}
	}
}
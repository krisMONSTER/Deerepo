package Structure;

import GUI.AlertBox;
import GUI.PromotionMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

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

	public void start(Stage primaryStage){
		primaryStage.setTitle("aa");
		StackPane layout = new StackPane();
		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
		Task<Void> task = new Task<>() {
			@Override
			protected Void call() throws Exception {
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
						new Thread(() -> Platform.runLater(() -> AlertBox.display("KONIEC GRY","KONIEC GRY"))).start();
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
				return null;
			}
		};
		StructureTaskOffline t = new StructureTaskOffline(clickCommand, display, gameStates);
		t.setDaemon(true);
		t.start();
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		/*try{
			Thread.sleep(1000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}*/
		/*
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

		 */
	}
}
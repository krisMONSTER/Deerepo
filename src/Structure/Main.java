package Structure;

import GUI.AlertBox;
import GUI.PromotionMenu;
import MutableVariables.MutableBoolean;
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
import java.util.concurrent.Semaphore;

public class Main {
	private static final ArrayBlockingQueue<int[]> clickCommand = new ArrayBlockingQueue<>(1);
	private static final ArrayBlockingQueue<ToDisplay> display = new ArrayBlockingQueue<>(1);
	private static final ArrayBlockingQueue<GameState> gameStates = new ArrayBlockingQueue<>(1);
	private static final MutableBoolean active = new MutableBoolean(true);
	private static final Scanner sc = new Scanner(System.in);
	private static ToDisplay toDisplay;
	private static GameState gameState;
	private static final Semaphore clickSemaphore = new Semaphore(0);
	private static final MutableBoolean isActive = new MutableBoolean(true);

	public static void main(String[] args) {
		StructureTaskOffline t = new StructureTaskOffline(clickCommand, display, gameStates, clickSemaphore, isActive);
		t.start();
		ClickSimulation clickSimulation = new ClickSimulation(clickSemaphore, clickCommand);
		clickSimulation.start();
		GameStateReceiveSimulation gameStateReceiveSimulation = new GameStateReceiveSimulation(gameStates);
		gameStateReceiveSimulation.start();
		ToDisplayReceiveSimulation toDisplayReceiveSimulation = new ToDisplayReceiveSimulation(display);
		toDisplayReceiveSimulation.start();
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
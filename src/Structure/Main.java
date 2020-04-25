package Structure;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application{
	private static Scanner sc = new Scanner(System.in);
	private static Player white = new Player("BornToFight", true);
	private static Player black = new Player("Deer", false);
	private static Player current = black;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) {
		System.out.println("Zaczynaja biale!");
		while(true) {
			if(current==white) current = black;
			else current = white;
			Board.setPlayer(current);
			Board.display();
			int x,y;
			byte tmp;
			do {
				System.out.print("Podaj x:");
				x = sc.nextInt();
				sc.nextLine();
				System.out.print("Podaj y:");
				y = sc.nextInt();
				sc.nextLine();
				tmp = Board.clickOnBoard(x, y);
				System.out.println(tmp);
			}while(tmp!=2);
			Board.findAndResetEnPassant(!current.getColour());
		}
	}
}
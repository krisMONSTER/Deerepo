package Structure;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application{
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) {
		System.out.println("Wybierz kolor");
		System.out.println("true - biale");
		System.out.println("false - czarne");
		boolean kolorek = sc.nextBoolean();
		sc.nextLine();
		Player.setColour(kolorek);
		while(true) {
			Player.setColour(kolorek=!kolorek);
			Player.resetEnPassant();
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
			//send_info();
		}
	}
}
package Structure;

import java.util.Scanner;

public class Main {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Wybierz kolor");
		System.out.println("true - biale");
		System.out.println("false - czarne");
		boolean kolorek = sc.nextBoolean();
		sc.nextLine();
		Player.set_colour(kolorek);
		while(true) {
			Player.set_colour(kolorek=!kolorek);
			Player.reset_en_passant();
			Board.display();
			System.out.println(Board.is_check_on_field(5, 4, true));
			int x,y;
			byte tmp;
			do {
				System.out.print("Podaj x:");
				x = sc.nextInt();
				sc.nextLine();
				System.out.print("Podaj y:");
				y = sc.nextInt();
				sc.nextLine();
				tmp = Board.click_on_board(x, y);
				System.out.println(tmp);
			}while(tmp!=2);
			//send_info();
		}
	}
}

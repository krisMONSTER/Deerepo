package GUI;

import Structure.Board;
import Structure.Player;

import java.util.Scanner;

public class Game extends Thread {

    private static Scanner sc = new Scanner(System.in);

    public void run(){
        System.out.println("Wybierz kolor");
        System.out.println("true - biale");
        System.out.println("false - czarne");
        boolean kolorek = sc.nextBoolean();
        sc.nextLine();
        Player.setColour(kolorek);
        while(true) {
            Player.setColour(kolorek = !kolorek);
            Player.resetEnPassant();
            Board.display();
            int x, y;
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
            } while (tmp != 2);
            //send_info();
        }
    }
}

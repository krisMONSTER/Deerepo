package GUI;

import Structure.Board;
import Structure.player;

import java.util.Scanner;

public class Game extends Thread {

    private static Scanner sc = new Scanner(System.in);
    private static player white = new player(true);
    private static player black = new player(false);
    private static player current = black;

    public void run(){
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

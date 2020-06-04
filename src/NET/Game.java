package NET;
import Structure.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Tryb sieciowy");
        System.out.println();
        System.out.println("[1] - Hostuj gre");
        System.out.println("[2] - Dolacz do gry");
        System.out.println("[0] - Wyjscie");

        int wybor;
        int port;
        wybor = scan.nextInt();
        scan.nextLine();
        /*switch(wybor) {
            case 1:
//                System.out.println("Pod jakim portem otworzyc?");
//                port = scan.nextInt();
//                scan.nextLine();
                new Host().start();
                break;
            case 2:
//                System.out.println("Podaj IP: ");
//                String ip = scan.nextLine();
//                System.out.println("Podaj port: ");
//                port = scan.nextInt();
 //               scan.nextLine();
                new Client().start();
                break;
            case 0:
                System.out.println("Do widzenia.");
                break;
        }*/
    }

}

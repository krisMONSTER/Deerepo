package NET;
import Structure.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Game {

    public static void server(int port) throws IOException {

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Oczekiwanie na gracza...");
        Socket s = ss.accept();
        System.out.println("Gracz dolaczyl do sesji");

        try {
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            DataChanges dc = (DataChanges)is.readObject();
            System.out.println("server: " + dc);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        InputStreamReader in = new InputStreamReader(s.getInputStream());
//        BufferedReader bf = new BufferedReader(in);

//        String str = bf.readLine();
//        System.out.println("client: " + str);
//
//        PrintWriter pr = new PrintWriter(s.getOutputStream());
//        pr.println("Serwer wysyla odpowiedz");
//        pr.flush();



    }

    public static void client(String ip, int port) throws IOException {

        System.out.println("Laczenie...");
        Socket s = new Socket(ip, port);
        System.out.println("Nawiazano polaczenie.");

        DataChanges dc = new DataChanges();
        dc.test();

        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

        System.out.println("client: " + dc);
        os.writeObject(dc);

//        PrintWriter pr = new PrintWriter(s.getOutputStream());
//        pr.println("Klient wysyla zapytanie");
//        pr.flush();
//
//        InputStreamReader in = new InputStreamReader(s.getInputStream());
//        BufferedReader bf = new BufferedReader(in);
//
//        String str = bf.readLine();
//        System.out.println("server: " + str);
    }

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

        switch(wybor) {
            case 1:
                System.out.println("Pod jakim portem otworzyc?");
                port = scan.nextInt();
                scan.nextLine();
                server(port);
                break;
            case 2:
                System.out.println("Podaj IP: ");
                String ip = scan.nextLine();
                System.out.println("Podaj port: ");
                port = scan.nextInt();
                scan.nextLine();
                client(ip,port);
                break;
            case 0:
                System.out.println("Papa.");
                break;
        }
    }

}

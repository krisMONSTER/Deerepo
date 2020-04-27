package NET;

import Structure.DataChanges;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {

    // port do otwarcia socketu, domyslnie 7172
    private int port = 7172;
    private ServerSocket ss;
    private Socket s;

    public Host() {

    }

    public Host(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        ss = new ServerSocket(port);
        System.out.println("Oczekiwanie na gracza...");
        s = ss.accept();
        System.out.println("Gracz dolaczyl do sesji");

        try {
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            DataChanges dc = (DataChanges) is.readObject();
            System.out.println("server: " + dc);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            s.close();
            ss.close();
        }
    }
}




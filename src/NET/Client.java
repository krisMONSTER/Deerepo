package NET;

import Structure.DataChanges;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    // dane do polaczenia, domyslnie localhost:7172
    private String address = "localhost";
    private int port = 7172;
    private Socket s;

    public Client() {

    }

    public Client(int port) {
        this.port = port;
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    void start() throws IOException {

        try {

            System.out.println("Laczenie z hostem...");
            s = new Socket(address, port);
            System.out.println("Nawiazano polaczenie.");

            DataChanges dc = new DataChanges();
            dc.test();

            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

            System.out.println("client: " + dc);
            os.writeObject(dc);

        } finally {
            s.close();
        }
    }
}

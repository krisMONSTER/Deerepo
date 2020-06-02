package NET;

import Structure.DataChanges;
import Structure.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Client {

    // dane do polaczenia, domyslnie localhost:7172
    private String address = "localhost";
    private int port = 7172;
    private Socket s;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    private ArrayBlockingQueue<DataChanges> oq = null;
    private ArrayBlockingQueue<DataChanges> iq = null;

    public Client() {
    }

    public Client(int port) {
        this.port = port;
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void setupClient() throws IOException{
        s = new Socket(address, port);
        os = new ObjectOutputStream(s.getOutputStream());
        is = new ObjectInputStream(s.getInputStream());
    }

    public void send(DataPackage dataPackage) throws IOException{
        os.writeObject(dataPackage);
    }

    public DataPackage receive() throws IOException,ClassNotFoundException{
        return (DataPackage)is.readObject();
    }

    public void close() throws IOException{
        s.close();
    }

    void start() throws IOException {

        try {
            oq = new ArrayBlockingQueue<>(1);
            iq = new ArrayBlockingQueue<>(1);

            System.out.println("Laczenie z hostem...");
            s = new Socket(address, port);
            System.out.println("Nawiazano polaczenie.");

            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            DataChanges dc;

            while (true) {

                // Receive
                dc = (DataChanges)is.readObject();
                System.out.println("client received: " + dc);
                iq.add(dc);

                dc = oq.take();
                System.out.println("client send: " + dc);
                // Send
                os.writeObject(dc);

            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }
}

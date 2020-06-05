package NET;

import Structure.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private String address = "localhost";
    private final int port;
    private Socket s;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public Client() {
        port = 7172;
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
        s.setSoTimeout(2000);
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

    /*void start() throws IOException {

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
    }*/
}

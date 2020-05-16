package NET;

import Structure.DataChanges;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Host {

    // port do otwarcia socketu, domyslnie 7172
    private int port = 7172;
    private ServerSocket ss;
    private Socket s;

    private ArrayBlockingQueue<DataChanges> oq = null;
    private ArrayBlockingQueue<DataChanges> iq = null;

    public Host() {

    }

    public Host(int port) {
        this.port = port;
    }

    void start() throws IOException {

        try {
            oq = new ArrayBlockingQueue<>(1);
            iq = new ArrayBlockingQueue<>(1);

            ss = new ServerSocket(port);
            System.out.println("Oczekiwanie na gracza...");
            s = ss.accept();
            System.out.println("Gracz dolaczyl do sesji");

            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            DataChanges dc;

            while (true) {

                dc = oq.take();
                System.out.println("client send: " + dc);
                // Send
                os.writeObject(dc);

                // Receive
                dc = (DataChanges)is.readObject();
                System.out.println("client received: " + dc);
                iq.add(dc);

            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
            ss.close();
        }
    }


}



